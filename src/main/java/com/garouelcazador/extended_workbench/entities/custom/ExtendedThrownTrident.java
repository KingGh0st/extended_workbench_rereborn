package com.garouelcazador.extended_workbench.entities.custom;

import com.garouelcazador.extended_workbench.entities.EWEntities;
import com.garouelcazador.extended_workbench.items.EWItems;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ExtendedThrownTrident extends AbstractArrow {
   private static final EntityDataAccessor<Byte> ID_LOYALTY;
   private static final EntityDataAccessor<Boolean> ID_FOIL;
   private boolean dealtDamage;
   public int clientSideReturnTridentTickCount;

   public ExtendedThrownTrident(EntityType<? extends ExtendedThrownTrident> p_37561_, Level p_37562_) {
      super(p_37561_, p_37562_);
   }

   public ExtendedThrownTrident(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
      super((EntityType)EWEntities.EXTENDED_TRIDENT.get(), p_37570_, p_37569_, p_37571_, (ItemStack)null);
      this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(p_37571_));
      this.entityData.set(ID_FOIL, p_37571_.hasFoil());
   }

   public ExtendedThrownTrident(Level p_334242_, double p_336226_, double p_330090_, double p_331538_, ItemStack p_333817_) {
      super((EntityType)EWEntities.EXTENDED_TRIDENT.get(), p_336226_, p_330090_, p_331538_, p_334242_, p_333817_, p_333817_);
      this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(p_333817_));
      this.entityData.set(ID_FOIL, p_333817_.hasFoil());
   }

   protected void defineSynchedData(@NotNull Builder p_332339_) {
      super.defineSynchedData(p_332339_);
      p_332339_.define(ID_LOYALTY, (byte)0);
      p_332339_.define(ID_FOIL, false);
   }

   public void tick() {
      if (this.inGroundTime > 4) {
         this.dealtDamage = true;
      }

      Entity entity = this.getOwner();
      int i = (Byte)this.entityData.get(ID_LOYALTY);
      if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
         if (!this.isAcceptibleReturnOwner()) {
            if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
               this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
         } else {
            this.setNoPhysics(true);
            Vec3 vec3 = entity.getEyePosition().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)i, this.getZ());
            if (this.level().isClientSide) {
               this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)i;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if (this.clientSideReturnTridentTickCount == 0) {
               this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.clientSideReturnTridentTickCount;
         }
      }

      super.tick();
   }

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.getOwner();
      return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
   }

   public boolean isFoil() {
      return (Boolean)this.entityData.get(ID_FOIL);
   }

   @Nullable
   protected EntityHitResult findHitEntity(@NotNull Vec3 p_37575_, @NotNull Vec3 p_37576_) {
      return this.dealtDamage ? null : super.findHitEntity(p_37575_, p_37576_);
   }

   protected void onHitEntity(EntityHitResult p_37573_) {
      Entity entity = p_37573_.getEntity();
      float f = 12.0F;
      Entity entity1 = this.getOwner();
      DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
      Level var7 = this.level();
      ServerLevel serverlevel1;
      if (var7 instanceof ServerLevel) {
         serverlevel1 = (ServerLevel)var7;
         f = EnchantmentHelper.modifyDamage(serverlevel1, this.getWeaponItem(), entity, damagesource, f);
      }

      this.dealtDamage = true;
      if (entity.hurt(damagesource, f)) {
         if (entity.getType() == EntityType.ENDERMAN) {
            return;
         }

         var7 = this.level();
         if (var7 instanceof ServerLevel) {
            serverlevel1 = (ServerLevel)var7;
            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            this.doKnockback(livingentity, damagesource);
            this.doPostHurtEffects(livingentity);
         }
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
      this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
   }

   protected void hitBlockEnchantmentEffects(ServerLevel p_344367_, BlockHitResult p_343898_, ItemStack p_344547_) {
      Vec3 vec3 = p_343898_.getBlockPos().clampLocationWithin(p_343898_.getLocation());
      Entity var6 = this.getOwner();
      LivingEntity var10002;
      if (var6 instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)var6;
         var10002 = livingentity;
      } else {
         var10002 = null;
      }

      EnchantmentHelper.onHitBlock(p_344367_, p_344547_, var10002, this, (EquipmentSlot)null, vec3, p_344367_.getBlockState(p_343898_.getBlockPos()), (p_343806_) -> {
         this.kill();
      });
   }

   public ItemStack getWeaponItem() {
      return this.getPickupItemStackOrigin();
   }

   protected boolean tryPickup(Player p_150196_) {
      return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
   }

   @NotNull
   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)EWItems.EXTENDED_TRIDENT.get());
   }

   @NotNull
   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.TRIDENT_HIT_GROUND;
   }

   public void playerTouch(@NotNull Player p_37580_) {
      if (this.ownedBy(p_37580_) || this.getOwner() == null) {
         super.playerTouch(p_37580_);
      }

   }

   public void readAdditionalSaveData(@NotNull CompoundTag p_37578_) {
      super.readAdditionalSaveData(p_37578_);
      this.dealtDamage = p_37578_.getBoolean("DealtDamage");
      this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
   }

   public void addAdditionalSaveData(@NotNull CompoundTag p_37582_) {
      super.addAdditionalSaveData(p_37582_);
      p_37582_.putBoolean("DealtDamage", this.dealtDamage);
   }

   private byte getLoyaltyFromItem(ItemStack p_343400_) {
      Level var3 = this.level();
      byte var10000;
      if (var3 instanceof ServerLevel) {
         ServerLevel serverlevel = (ServerLevel)var3;
         var10000 = (byte)Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, p_343400_, this), 0, 127);
      } else {
         var10000 = 0;
      }

      return var10000;
   }

   public void tickDespawn() {
      int i = (Byte)this.entityData.get(ID_LOYALTY);
      if (this.pickup != Pickup.ALLOWED || i <= 0) {
         super.tickDespawn();
      }

   }

   protected float getWaterInertia() {
      return 0.99F;
   }

   public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
      return true;
   }

   static {
      ID_LOYALTY = SynchedEntityData.defineId(ExtendedThrownTrident.class, EntityDataSerializers.BYTE);
      ID_FOIL = SynchedEntityData.defineId(ExtendedThrownTrident.class, EntityDataSerializers.BOOLEAN);
   }
}
