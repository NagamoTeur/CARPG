package com.github.L_Ender.cataclysm.client.gui;

import com.github.L_Ender.cataclysm.inventory.WeaponfusionMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GUIWeponfusion extends ItemCombinerScreen<WeaponfusionMenu> {
   private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("cataclysm", "textures/gui/fusion.png");

   public GUIWeponfusion(WeaponfusionMenu p_99290_, Inventory p_99291_, Component p_99292_) {
      super(p_99290_, p_99291_, p_99292_, SMITHING_LOCATION);
      this.f_97728_ = 66;
      this.f_97729_ = 18;
   }

   protected void renderErrorIcon(PoseStack p_282905_, int p_283237_, int p_282237_) {
      RenderSystem.m_69493_();
      RenderSystem.m_157456_(0, SMITHING_LOCATION);
      if ((((WeaponfusionMenu)this.f_97732_).m_38853_(0).m_6657_() || ((WeaponfusionMenu)this.f_97732_).m_38853_(1).m_6657_())
         && !((WeaponfusionMenu)this.f_97732_).m_38853_(2).m_6657_()) {
         this.m_93228_(p_282905_, p_283237_ + 99, p_282237_ + 45, this.f_97726_, 0, 28, 21);
      }
   }
}
