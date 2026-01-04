package com.garouelcazador.extended_workbench.screens.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.ResultSlot;

public class ExtendedResultSlot extends Slot {
    private final CraftingContainer craftSlots;
    private final Player player;
    private int removeCount;

    public ExtendedResultSlot(Player player, CraftingContainer craftSlots, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.player = player;
        this.craftSlots = craftSlots;
    }

    @Override
    public boolean mayPlace(ItemStack stack) { return false; }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) this.removeCount += Math.min(amount, this.getItem().getCount());
        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        for (int i = 0; i < craftSlots.getContainerSize(); i++) {
            ItemStack itemstack = craftSlots.getItem(i);
            if (!itemstack.isEmpty()) {
                craftSlots.removeItem(i, 1);
            }
        }
    }
}