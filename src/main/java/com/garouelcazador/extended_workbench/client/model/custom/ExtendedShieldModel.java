package com.garouelcazador.extended_workbench.client.model.custom;

import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedShieldModel extends ShieldModel {
   public ExtendedShieldModel(ModelPart p_170911_) {
      super(p_170911_);
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      
      // Now shield is 12x24
      partdefinition.addOrReplaceChild("plate", 
         CubeListBuilder.create()
               .texOffs(0, 0) 
               .addBox(-6.0F, -12.0F, -2.0F, 12.0F, 24.0F, 1.0F), 
         PartPose.ZERO);

      partdefinition.addOrReplaceChild("handle", 
         CubeListBuilder.create()
               .texOffs(26, 0) 
               .addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), 
         PartPose.ZERO);

      return LayerDefinition.create(meshdefinition, 64, 64);
   }
}
