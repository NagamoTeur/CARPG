package com.aizistral.enigmaticlegacy.client;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.gui.GUIUtils;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent.Post;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class QuoteHandler {
   public static final QuoteHandler INSTANCE = new QuoteHandler();
   private static final RandomSource RANDOM = RandomSource.m_216327_();
   private Quote currentQuote = null;
   private long startedPlaying = -1L;
   private int delayTicks = -1;
   private boolean shownExperimentalInfo = false;

   private QuoteHandler() {
   }

   private double getPlayTime() {
      long millis = System.currentTimeMillis() - this.startedPlaying;
      return (double)millis / 1000.0;
   }

   public void playQuote(Quote quote, int delayTicks) {
      if (this.currentQuote == null) {
         this.currentQuote = quote;
         this.delayTicks = delayTicks;
      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.player == Minecraft.m_91087_().f_91074_
         && this.delayTicks > 0
         && !(Minecraft.m_91087_().f_91080_ instanceof LevelLoadingScreen)
         && !(Minecraft.m_91087_().f_91080_ instanceof ReceivingLevelScreen)) {
         this.delayTicks--;
         if (this.delayTicks == 0) {
            SimpleSoundInstance instance = new SimpleSoundInstance(
               this.currentQuote.getSound().m_11660_(), SoundSource.VOICE, 0.7F, 1.0F, RANDOM, false, 0, Attenuation.NONE, 0.0, 0.0, 0.0, true
            );
            Minecraft.m_91087_().m_91106_().m_120367_(instance);
            this.startedPlaying = System.currentTimeMillis();
         }
      }
   }

   @SubscribeEvent
   public void onOverlayRender(Post event) {
      if (Minecraft.m_91087_().f_91080_ == null && this.currentQuote != null && this.delayTicks <= 0) {
         this.drawQuote(event.getPoseStack(), event.getWindow());
      }
   }

   @SubscribeEvent
   public void onScreenRender(net.minecraftforge.client.event.ScreenEvent.Render.Post event) {
      if (this.currentQuote != null && this.delayTicks <= 0) {
         this.drawQuote(event.getPoseStack(), Minecraft.m_91087_().m_91268_());
         Minecraft.m_91087_().m_91106_().m_120407_();
      }
   }

   private void sendExperimentalInfo(Player player) {
   }

   private void drawQuote(PoseStack stack, Window window) {
      if (this.currentQuote.getSubtitles().getDuration() - this.getPlayTime() <= 0.1) {
         if (Quote.NARRATOR_INTROS.contains(this.currentQuote) && Minecraft.m_91087_().f_91074_ != null) {
            this.sendExperimentalInfo(Minecraft.m_91087_().f_91074_);
         }

         this.currentQuote = null;
         this.startedPlaying = (long)(this.delayTicks = -1);
      } else if (!(this.getPlayTime() < 0.05)) {
         if (!OmniconfigHandler.disableQuoteSubtitles.getValue()) {
            Subtitles subtitles = this.currentQuote.getSubtitles();
            Font font = Minecraft.m_91087_().f_91062_;
            String[] text = SuperpositionHandler.wrapString(subtitles.getLine(this.getPlayTime()), font, 260);
            int alphaMod = 255;
            if (this.getPlayTime() < 0.5) {
               alphaMod = (int)((double)alphaMod * (this.getPlayTime() / 0.5));
            } else if (this.currentQuote.getSubtitles().getDuration() - this.getPlayTime() < 0.5) {
               alphaMod = (int)((double)alphaMod * ((this.currentQuote.getSubtitles().getDuration() - this.getPlayTime()) / 0.5));
            }

            if (alphaMod < 0) {
               alphaMod = 255;
            }

            int width = window.m_85445_() / 2 - SuperpositionHandler.greatestWidth(font, text) / 2;
            int height = window.m_85446_() - 70 - (9 + 2) * (text.length - 1);
            stack.m_85836_();
            stack.m_85841_(1.0F, 1.0F, 1.0F);
            int toX = width + SuperpositionHandler.greatestWidth(font, text);
            int toY = height + 9 * text.length + 2 * text.length - 1;
            int color1 = 0 | (int)((double)alphaMod * 0.266) << 24;
            int color2 = ChatFormatting.YELLOW.m_126665_() | alphaMod << 24;
            GUIUtils.drawGradientRect(stack.m_85850_().m_85861_(), 0, width - 4, height - 4, toX + 4, toY + 4, color1, color1);
            GUIUtils.drawGradientRect(stack.m_85850_().m_85861_(), 0, width - 6, height - 6, toX + 6, toY + 6, color1, color1);
            GUIUtils.drawGradientRect(stack.m_85850_().m_85861_(), 0, width - 8, height - 8, toX + 8, toY + 8, color1, color1);
            int counter = 0;

            for (String line : text) {
               font.m_92756_(stack, line, (float)(window.m_85445_() / 2 - font.m_92895_(line) / 2), (float)(height + counter * (9 + 2)), color2, true);
               counter++;
            }

            stack.m_85849_();
         }
      }
   }
}
