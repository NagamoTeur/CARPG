package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.sound.ConfiguredSpellSound;
import com.hollingsworth.arsnouveau.api.sound.SpellSound;
import com.hollingsworth.arsnouveau.client.gui.BookSlider;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.SoundButton;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetSound;
import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;

public class SoundScreen extends BaseBook {
   public int casterSlot;
   public InteractionHand stackHand;
   public BookSlider volumeSlider;
   public BookSlider pitchSlider;
   public double volume;
   public double pitch;
   public SpellSound selectedSound;
   public SoundButton selectedButton;

   public SoundScreen(ConfiguredSpellSound configuredSpellSound, int slot, InteractionHand stackHand) {
      this.volume = (double)(configuredSpellSound.volume * 100.0F);
      this.pitch = (double)(configuredSpellSound.pitch * 100.0F);
      this.selectedSound = configuredSpellSound.sound;
      this.casterSlot = slot;
      this.stackHand = stackHand;
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.volumeSlider = this.buildSlider(
         this.bookLeft + 28, this.bookTop + 49, Component.m_237115_("ars_nouveau.sounds.volume"), Component.m_237119_(), this.volume
      );
      this.pitchSlider = this.buildSlider(
         this.bookLeft + 28, this.bookTop + 89, Component.m_237115_("ars_nouveau.sounds.pitch"), Component.m_237119_(), this.pitch
      );
      this.m_142416_(this.volumeSlider);
      this.m_142416_(this.pitchSlider);
      this.m_142416_(new GuiImageButton(this.bookLeft + 25, this.bookBottom - 36, 0, 0, 37, 12, 37, 12, "textures/gui/save_icon.png", this::onSaveClick));
      GuiImageButton testButton = new GuiImageButton(
         this.bookLeft + 90, this.bookBottom - 36, 0, 0, 37, 12, 37, 12, "textures/gui/sound_test_icon.png", this::onTestClick
      );
      testButton.soundDisabled = true;
      this.m_142416_(testButton);
      this.selectedButton = new SoundButton(this, this.bookLeft + 69, this.bookTop + 131, this.selectedSound, b -> {
         ((SoundButton)b).sound = SoundRegistry.EMPTY_SPELL_SOUND;
         this.selectedSound = SoundRegistry.EMPTY_SPELL_SOUND;
      });
      this.m_142416_(this.selectedButton);
      this.addPresets();
   }

   public void addPresets() {
      int PER_ROW = 6;
      int MAX_ROWS = 6;
      boolean nextPage = false;
      int xStart = this.bookLeft + 154;
      int adjustedRowsPlaced = 0;
      int yStart = this.bookTop + 22;
      int adjustedXPlaced = 0;
      List<SpellSound> sounds = ArsNouveauAPI.getInstance().getSpellSoundsRegistry().values().stream().toList();

      for (int i = 0; i < sounds.size(); i++) {
         SpellSound part = sounds.get(i);
         if (adjustedXPlaced >= 6) {
            adjustedRowsPlaced++;
            adjustedXPlaced = 0;
         }

         if (adjustedRowsPlaced > 6) {
            if (nextPage) {
               break;
            }

            nextPage = true;
            adjustedRowsPlaced = 0;
         }

         int xOffset = 20 * (adjustedXPlaced % 6) + (nextPage ? 134 : 0);
         int yPlace = adjustedRowsPlaced * 18 + yStart;
         SoundButton cell = new SoundButton(this, xStart + xOffset, yPlace, part, this::onSoundClick);
         this.m_142416_(cell);
         adjustedXPlaced++;
      }
   }

   public void onSoundClick(Button button) {
      if (button instanceof SoundButton soundButton) {
         this.selectedSound = soundButton.sound;
         this.selectedButton.sound = this.selectedSound;
      }
   }

   public void onTestClick(Button button) {
      if (this.selectedSound != null) {
         LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
         BlockPos pos = localPlayer.m_20097_().m_6630_(2);
         localPlayer.f_19853_
            .m_7785_(
               (double)pos.m_123341_(),
               (double)pos.m_123342_(),
               (double)pos.m_123343_(),
               this.selectedSound.getSoundEvent(),
               SoundSource.PLAYERS,
               (float)this.volumeSlider.getValue() / 100.0F,
               (float)this.pitchSlider.getValue() / 100.0F,
               false
            );
      }
   }

   public void onSaveClick(Button button) {
      Networking.INSTANCE
         .sendToServer(
            new PacketSetSound(
               this.casterSlot,
               this.selectedSound == null
                  ? ConfiguredSpellSound.EMPTY
                  : new ConfiguredSpellSound(this.selectedSound, (float)this.volumeSlider.getValue() / 100.0F, (float)this.pitchSlider.getValue() / 100.0F),
               this.stackHand == InteractionHand.MAIN_HAND
            )
         );
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/sound_slider_gilding.png"), 22, 47, 0, 0, 112, 104, 112, 104, stack);
      int color = -8355712;
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.sounds.title").getString(), 51.0F, 24.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.color_gui.save").getString(), 37.0F, 160.0F, color);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.sounds.test").getString(), 102.0F, 160.0F, color);
   }

   @Override
   public void drawForegroundElements(int mouseX, int mouseY, float partialTicks) {
      super.drawForegroundElements(mouseX, mouseY, partialTicks);
   }
}
