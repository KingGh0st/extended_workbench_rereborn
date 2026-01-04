package com.garouelcazador.extended_workbench.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.world.item.crafting.CraftingInput;

@OnlyIn(Dist.CLIENT)
public class ExtendedCraftingScreen extends AbstractContainerScreen<ExtendedCraftingMenu> implements RecipeUpdateListener {
   public static final ResourceLocation CRAFTING_TABLE_LOCATION = ResourceLocation.fromNamespaceAndPath("extended_workbench", "textures/gui/container/extended_crafting_table.png");
   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
   private boolean widthTooNarrow;

   public ExtendedCraftingScreen(ExtendedCraftingMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 176;
      this.imageHeight = 222;
   }

   @Override
   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookComponent;
   }

   @Override
   protected void init() {
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
      this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
      this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
         this.recipeBookComponent.toggleVisibility();
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         button.setPosition(this.leftPos + 5, this.height / 2 - 49);
      }));
      this.titleLabelX = 28; 
      this.titleLabelY = 5; 
      this.inventoryLabelY = 128; 
   }

   @Override
   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
         this.renderBackground(graphics, mouseX, mouseY, partialTick);
         this.recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
      } else {
         super.render(graphics, mouseX, mouseY, partialTick);
         this.recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
      }
      this.renderTooltip(graphics, mouseX, mouseY);
      this.recipeBookComponent.renderTooltip(graphics, this.leftPos, this.topPos, mouseX, mouseY);
   }

   @Override
   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      int x = this.leftPos;
      int y = (this.height - this.imageHeight) / 2;
      graphics.blit(CRAFTING_TABLE_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
   }

   @Override
   public void recipesUpdated() {
      this.recipeBookComponent.recipesUpdated();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
         return true;
      }
      return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
   }

   @Override
   public boolean charTyped(char codePoint, int modifiers) {
      return this.recipeBookComponent.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
   }

   @Override
   protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
      return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(x, y, width, height, mouseX, mouseY);
   }
}