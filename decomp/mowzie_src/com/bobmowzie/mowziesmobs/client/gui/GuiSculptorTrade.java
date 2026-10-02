package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import com.bobmowzie.mowziesmobs.server.inventory.InventoryOneInput;
import com.bobmowzie.mowziesmobs.server.inventory.InventorySculptor;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.message.MessageSculptorTrade;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class GuiSculptorTrade extends AbstractContainerScreen<ContainerSculptorTrade> implements InventoryOneInput.ChangeListener {
   private static final ResourceLocation TEXTURE_TRADE = new ResourceLocation("mowziesmobs", "textures/gui/container/barako_trade.png");
   private final EntitySculptor sculptor;
   private final Player player;
   private final InventorySculptor inventory;
   private final ItemStack output = new ItemStack(ItemHandler.EARTHBORE_GAUNTLET);
   private Button beginButton;

   public GuiSculptorTrade(ContainerSculptorTrade screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.sculptor = screenContainer.getSculptor();
      this.player = inv.f_35978_;
      this.inventory = screenContainer.getInventorySculptor();
      this.inventory.addListener(this);
   }

   protected void m_7856_() {
      super.m_7856_();
      String text = I18n.m_118938_("entity.mowziesmobs.sculptor.trade.button.text", new Object[0]);
      this.beginButton = (Button)this.m_142416_(new Button(this.f_97735_ + 115, this.f_97736_ + 52, 56, 20, Component.m_237115_(text), this::actionPerformed));
      this.updateButton();
   }

   protected void actionPerformed(Button button) {
      if (button == this.beginButton) {
         MowziesMobs.NETWORK.sendToServer(new MessageSculptorTrade(this.sculptor));
      }
   }

   protected void m_7286_(PoseStack matrixStack, float partialTicks, int x, int y) {
      RenderSystem.m_69444_(true, true, true, true);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE_TRADE);
      this.m_93228_(matrixStack, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      InventoryScreen.m_98850_(this.f_97735_ + 33, this.f_97736_ + 56, 14, 0.0F, 0.0F, this.sculptor);
   }

   protected void m_7027_(PoseStack matrixStack, int x, int y) {
      String title = I18n.m_118938_("entity.mowziesmobs.sculptor.trade", new Object[0]);
      this.f_96547_.m_92883_(matrixStack, title, (float)this.f_97726_ / 2.0F - (float)this.f_96547_.m_92895_(title) / 2.0F + 30.0F, 6.0F, 4210752);
      this.f_96547_.m_92883_(matrixStack, I18n.m_118938_("container.inventory", new Object[0]), 8.0F, (float)(this.f_97727_ - 96 + 2), 4210752);
   }

   public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
      this.m_7025_(matrixStack, mouseX, mouseY);
      ItemStack inSlot = this.inventory.m_8020_(0);
      matrixStack.m_85836_();
      this.f_96542_.f_115093_ = 100.0F;
      this.f_96542_.m_115203_(this.sculptor.getDesires(), this.f_97735_ + 68, this.f_97736_ + 24);
      this.f_96542_.m_115169_(this.f_96547_, this.sculptor.getDesires(), this.f_97735_ + 68, this.f_97736_ + 24);
      this.f_96542_.m_115203_(this.output, this.f_97735_ + 134, this.f_97736_ + 24);
      this.f_96542_.m_115169_(this.f_96547_, this.output, this.f_97735_ + 134, this.f_97736_ + 24);
      if (this.m_6774_(68, 24, 16, 16, (double)mouseX, (double)mouseY)) {
         this.m_6057_(matrixStack, this.sculptor.getDesires(), mouseX, mouseY);
      } else if (this.m_6774_(134, 24, 16, 16, (double)mouseX, (double)mouseY)) {
         this.m_6057_(matrixStack, this.output, mouseX, mouseY);
      }

      this.f_96542_.f_115093_ = 0.0F;
      if (this.beginButton.m_5953_((double)mouseX, (double)mouseY)) {
         this.m_96570_(matrixStack, this.getHoverText(), mouseX, mouseY);
      }

      matrixStack.m_85849_();
   }

   @Override
   public void onChange(Container inv) {
      this.beginButton.f_93623_ = this.sculptor.doesItemSatisfyDesire(inv.m_8020_(0));
   }

   private void updateButton() {
      this.beginButton.m_93666_(Component.m_237115_(I18n.m_118938_("entity.mowziesmobs.sculptor.trade.button.text", new Object[0])));
   }

   private Style getHoverText() {
      MutableComponent text = Component.m_237115_(I18n.m_118938_("entity.mowziesmobs.sculptor.trade.button.hover", new Object[0]));
      return text.m_7383_().m_131144_(new HoverEvent(Action.f_130831_, text));
   }
}
