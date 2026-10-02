package com.github.alexthe666.alexsmobs.client.gui;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ButtonTransmute extends Button {
   private final Screen parent;

   public ButtonTransmute(Screen parent, int x, int y, OnPress onPress) {
      super(x, y, 117, 19, CommonComponents.f_237098_, onPress);
      this.parent = parent;
   }

   public void m_7428_(PoseStack poseStack, int x, int y) {
   }

   public void m_6303_(PoseStack poseStack, int x, int y, float partialTick) {
      int color = 8453920;
      int cost = AMConfig.transmutingExperienceCost;
      if (!this.canBeTransmuted(cost)) {
         color = 16736352;
      } else if (this.f_93623_ && this.m_198029_()) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, GUITransmutationTable.TEXTURE);
         this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 0, 201, 117, 19);
         color = 13107152;
      }

      poseStack.m_85836_();
      m_93243_(
         poseStack,
         Minecraft.m_91087_().f_91062_,
         Component.m_237115_("alexsmobs.container.transmutation_table.cost").m_130946_(" " + cost),
         this.f_93620_ + 21,
         this.f_93621_ + (this.f_93619_ - 8) / 2,
         color
      );
      poseStack.m_85849_();
   }

   public boolean canBeTransmuted(int cost) {
      return Minecraft.m_91087_().f_91074_.f_36078_ >= cost || Minecraft.m_91087_().f_91074_.m_150110_().f_35937_;
   }

   public void m_7435_(SoundManager sounds) {
      if (this.canBeTransmuted(AMConfig.transmutingExperienceCost)) {
         super.m_7435_(sounds);
      }
   }

   public void m_5691_() {
      if (this.canBeTransmuted(AMConfig.transmutingExperienceCost)) {
         super.m_5691_();
      }
   }
}
