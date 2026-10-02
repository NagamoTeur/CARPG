package com.aizistral.enigmaticlegacy.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PermadeathScreen extends Screen {
   public static PermadeathScreen active = null;
   private final Component reason;
   private MultiLineLabel message = MultiLineLabel.f_94331_;
   private final Screen parent;
   private int textHeight;

   public PermadeathScreen(Screen parent, Component title, Component reason) {
      super(title);
      this.parent = parent;
      this.reason = reason;
   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      this.m_169413_();
      this.message = MultiLineLabel.m_94341_(this.f_96547_, ((MutableComponent)this.reason).m_130940_(ChatFormatting.WHITE), this.f_96543_ - 50);
      this.textHeight = this.message.m_5770_() * 9;
      this.m_142416_(
         new Button(
            this.f_96543_ / 2 - 100,
            Math.min(this.f_96544_ / 2 + this.textHeight / 2 + 9, this.f_96544_ - 30),
            200,
            20,
            Component.m_237115_("gui.enigmaticlegacy.toWorldList"),
            p_96002_ -> {
               active = null;
               this.f_96541_.m_91152_(this.parent);
            }
         )
      );
   }

   public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTick) {
      this.m_7856_();
      this.m_7333_(stack);
      m_93215_(stack, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 2 - this.textHeight / 2 - 18, 11184810);
      this.message.m_6276_(stack, this.f_96543_ / 2, this.f_96544_ / 2 - this.textHeight / 2);
      super.m_6305_(stack, mouseX, mouseY, partialTick);
   }
}
