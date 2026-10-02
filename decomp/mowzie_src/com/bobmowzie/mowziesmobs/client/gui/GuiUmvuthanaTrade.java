package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthanaTrade;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class GuiUmvuthanaTrade extends AbstractContainerScreen<ContainerUmvuthanaTrade> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/gui/container/umvuthana.png");
   private final EntityUmvuthanaMinion umvuthana;

   public GuiUmvuthanaTrade(ContainerUmvuthanaTrade screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.umvuthana = screenContainer.getUmvuthana();
   }

   public boolean m_6348_(double mouseX, double mouseY, int state) {
      return super.m_6348_(mouseX, mouseY, state);
   }

   protected void m_7286_(PoseStack matrixStack, float partialTicks, int x, int y) {
      RenderSystem.m_69444_(true, true, true, true);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      this.m_93228_(matrixStack, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      this.umvuthana.renderingInGUI = true;
      InventoryScreen.m_98850_(this.f_97735_ + 33, this.f_97736_ + 64, 20, (float)(this.f_97735_ + 33 - x), (float)(this.f_97736_ + 21 - y), this.umvuthana);
      this.umvuthana.renderingInGUI = false;
   }

   protected void m_7027_(PoseStack matrixStack, int x, int y) {
      String title = this.f_96539_.getString();
      this.f_96547_.m_92883_(matrixStack, title, (float)this.f_97726_ / 2.0F - (float)this.f_96547_.m_92895_(title) / 2.0F + 26.0F, 6.0F, 4210752);
      this.f_96547_.m_92883_(matrixStack, I18n.m_118938_("container.inventory", new Object[0]), 8.0F, (float)(this.f_97727_ - 96 + 2), 4210752);
   }

   public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
      this.m_7025_(matrixStack, mouseX, mouseY);
      if (this.umvuthana.isOfferingTrade()) {
         Trade trade = this.umvuthana.getOfferingTrade();
         ItemStack input = trade.getInput();
         ItemStack output = trade.getOutput();
         matrixStack.m_85836_();
         this.f_96542_.f_115093_ = 100.0F;
         this.f_96542_.m_115203_(input, this.f_97735_ + 80, this.f_97736_ + 24);
         this.f_96542_.m_115169_(this.f_96547_, input, this.f_97735_ + 80, this.f_97736_ + 24);
         this.f_96542_.m_115203_(output, this.f_97735_ + 134, this.f_97736_ + 24);
         this.f_96542_.m_115169_(this.f_96547_, output, this.f_97735_ + 134, this.f_97736_ + 24);
         this.f_96542_.f_115093_ = 0.0F;
         if (this.m_6774_(80, 24, 16, 16, (double)mouseX, (double)mouseY)) {
            this.m_6057_(matrixStack, input, mouseX, mouseY);
         } else if (this.m_6774_(134, 24, 16, 16, (double)mouseX, (double)mouseY)) {
            this.m_6057_(matrixStack, output, mouseX, mouseY);
         }

         matrixStack.m_85849_();
      }
   }
}
