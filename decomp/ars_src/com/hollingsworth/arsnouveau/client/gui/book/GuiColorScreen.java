package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.client.gui.BookSlider;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.RainbowParticleColor;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketUpdateSpellColors;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public class GuiColorScreen extends BaseBook {
   double startRed;
   double startGreen;
   double startBlue;
   public int slot;
   public BookSlider redW;
   public BookSlider greenW;
   public BookSlider blueW;
   public InteractionHand stackHand;

   protected GuiColorScreen(double startRed, double startGreen, double startBlue, int forSpellSlot, InteractionHand stackHand) {
      this.startRed = startRed;
      this.startGreen = startGreen;
      this.startBlue = startBlue;
      this.slot = forSpellSlot;
      this.stackHand = stackHand;
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.redW = this.buildSlider(
         this.bookLeft + 28, this.bookTop + 49, Component.m_237115_("ars_nouveau.color_gui.red_slider"), Component.m_237119_(), this.startRed
      );
      this.greenW = this.buildSlider(
         this.bookLeft + 28, this.bookTop + 89, Component.m_237115_("ars_nouveau.color_gui.green_slider"), Component.m_237119_(), this.startGreen
      );
      this.blueW = this.buildSlider(
         this.bookLeft + 28, this.bookTop + 129, Component.m_237115_("ars_nouveau.color_gui.blue_slider"), Component.m_237119_(), this.startBlue
      );
      this.m_142416_(this.redW);
      this.m_142416_(this.greenW);
      this.m_142416_(this.blueW);
      this.m_142416_(new GuiImageButton(this.bookLeft + 55, this.bookBottom - 36, 0, 0, 37, 12, 37, 12, "textures/gui/save_icon.png", this::onSaveClick));
      this.addPresets();
   }

   public void addPresets() {
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131, this.bookTop + 44, 0, 0, 48, 11, 48, 11, "textures/gui/default_color_icon.png", _2 -> this.setFromPreset(255, 25, 180)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131, this.bookTop + 68, 0, 0, 48, 11, 48, 11, "textures/gui/purple_color_icon.png", _2 -> this.setFromPreset(80, 25, 255)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131, this.bookTop + 92, 0, 0, 48, 11, 48, 11, "textures/gui/blue_color_icon.png", _2 -> this.setFromPreset(30, 25, 255)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131, this.bookTop + 116, 0, 0, 48, 11, 48, 11, "textures/gui/red_color_icon.png", _2 -> this.setFromPreset(255, 25, 25)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131, this.bookTop + 140, 0, 0, 48, 11, 48, 11, "textures/gui/green_color_icon.png", _2 -> this.setFromPreset(25, 255, 25)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 73, this.bookTop + 44, 0, 0, 48, 11, 48, 11, "textures/gui/yellow_color_icon.png", _2 -> this.setFromPreset(255, 255, 25)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 73, this.bookTop + 68, 0, 0, 48, 11, 48, 11, "textures/gui/white_color_icon.png", _2 -> this.setFromPreset(255, 255, 255)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 73, this.bookTop + 92, 0, 0, 48, 11, 48, 11, "textures/gui/orange_color_icon.png", _2 -> this.setFromPreset(255, 90, 1)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 73, this.bookTop + 116, 0, 0, 48, 11, 48, 11, "textures/gui/cyan_color_icon.png", _2 -> this.setFromPreset(25, 255, 255)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 73,
            this.bookTop + 140,
            0,
            0,
            48,
            11,
            48,
            11,
            "textures/gui/white_color_icon.png",
            _2 -> Networking.INSTANCE
                  .sendToServer(new PacketUpdateSpellColors(this.slot, new RainbowParticleColor(0, 0, 0), this.stackHand == InteractionHand.MAIN_HAND))
         )
      );
   }

   public void setFromPreset(int r, int g, int b) {
      this.redW.m_93611_((double)r);
      this.greenW.m_93611_((double)g);
      this.blueW.m_93611_((double)b);
   }

   public void onSaveClick(Button button) {
      Networking.INSTANCE
         .sendToServer(
            new PacketUpdateSpellColors(
               this.slot, new ParticleColor(this.redW.getValue(), this.greenW.getValue(), this.blueW.getValue()), this.stackHand == InteractionHand.MAIN_HAND
            )
         );
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/slider_gilding.png"), 22, 47, 0, 0, 112, 104, 112, 104, stack);
      int color = -8355712;
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.title").getString(), 51.0F, 24.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.presets").getString(), 159.0F, 24.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.default").getString(), 170.0F, 46.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.purple").getString(), 170.0F, 70.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.blue").getString(), 170.0F, 94.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.red").getString(), 170.0F, 118.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.green").getString(), 170.0F, 142.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.yellow").getString(), 228.0F, 46.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.white").getString(), 228.0F, 70.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.orange").getString(), 228.0F, 94.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.cyan").getString(), 228.0F, 118.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.rainbow").getString(), 228.0F, 142.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.save").getString(), 67.0F, 160.0F, color);
   }

   @Override
   public void drawForegroundElements(int mouseX, int mouseY, float partialTicks) {
      super.drawForegroundElements(mouseX, mouseY, partialTicks);
   }
}
