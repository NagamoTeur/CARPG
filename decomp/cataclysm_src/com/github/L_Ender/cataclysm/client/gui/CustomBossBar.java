package com.github.L_Ender.cataclysm.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;

public class CustomBossBar {
   public static Map<Integer, CustomBossBar> customBossBars = new HashMap<>();
   private final ResourceLocation baseTexture;
   private final ResourceLocation overlayTexture;
   private final boolean hasOverlay;
   private final int baseHeight;
   private final int baseTextureHeight;
   private final int baseOffsetX;
   private final int baseOffsetY;
   private final int overlayOffsetX;
   private final int overlayOffsetY;
   private final int overlayWidth;
   private final int overlayHeight;
   private final int verticalIncrement;
   private final int getProgress;
   private final ChatFormatting textColor;

   public CustomBossBar(
      ResourceLocation baseTexture,
      ResourceLocation overlayTexture,
      int baseHeight,
      int baseTextureHeight,
      int baseOffsetX,
      int baseOffsetY,
      int overlayOffsetX,
      int overlayOffsetY,
      int overlayWidth,
      int overlayHeight,
      int verticalIncrement,
      int getProgress,
      ChatFormatting textColor
   ) {
      this.baseTexture = baseTexture;
      this.overlayTexture = overlayTexture;
      this.hasOverlay = overlayTexture != null;
      this.baseHeight = baseHeight;
      this.baseTextureHeight = baseTextureHeight;
      this.baseOffsetX = baseOffsetX;
      this.baseOffsetY = baseOffsetY;
      this.overlayOffsetX = overlayOffsetX;
      this.overlayOffsetY = overlayOffsetY;
      this.overlayWidth = overlayWidth;
      this.overlayHeight = overlayHeight;
      this.verticalIncrement = verticalIncrement;
      this.getProgress = getProgress;
      this.textColor = textColor;
   }

   public ResourceLocation getBaseTexture() {
      return this.baseTexture;
   }

   public ResourceLocation getOverlayTexture() {
      return this.overlayTexture;
   }

   public boolean hasOverlay() {
      return this.hasOverlay;
   }

   public int getBaseHeight() {
      return this.baseHeight;
   }

   public int getBaseTextureHeight() {
      return this.baseTextureHeight;
   }

   public int getBaseOffsetX() {
      return this.baseOffsetX;
   }

   public int getBaseOffsetY() {
      return this.baseOffsetY;
   }

   public int getOverlayOffsetX() {
      return this.overlayOffsetX;
   }

   public int getOverlayOffsetY() {
      return this.overlayOffsetY;
   }

   public int getOverlayWidth() {
      return this.overlayWidth;
   }

   public int getOverlayHeight() {
      return this.overlayHeight;
   }

   public int getProgress() {
      return this.getProgress;
   }

   public int getVerticalIncrement() {
      return this.verticalIncrement;
   }

   public ChatFormatting getTextColor() {
      return this.textColor;
   }

   public void renderBossBar(BossEventProgress event) {
      PoseStack stack = event.getPoseStack();
      int y = event.getY();
      int i = Minecraft.m_91087_().m_91268_().m_85445_();
      int j = y - 9;
      Minecraft.m_91087_().m_91307_().m_6180_("CataclysmCustomBossBarBase");
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, this.getBaseTexture());
      this.drawBar(stack, event.getX() + this.getBaseOffsetX(), y + this.getBaseOffsetY(), event.getBossEvent());
      Component component = event.getBossEvent().m_18861_().m_6881_().m_130940_(this.getTextColor());
      Minecraft.m_91087_().m_91307_().m_7238_();
      int l = Minecraft.m_91087_().f_91062_.m_92852_(component);
      int i1 = i / 2 - l / 2;
      Minecraft.m_91087_().f_91062_.m_92877_(stack, component.m_7532_(), (float)i1, (float)j, 16777215);
      if (this.hasOverlay()) {
         Minecraft.m_91087_().m_91307_().m_6180_("CataclysmCustomBossBarOverlay");
         RenderSystem.m_157456_(0, this.getOverlayTexture());
         GuiComponent.m_93133_(
            stack,
            event.getX() + this.getBaseOffsetX() + this.getOverlayOffsetX(),
            y + this.getOverlayOffsetY() + this.getBaseOffsetY(),
            0.0F,
            0.0F,
            this.getOverlayWidth(),
            this.getOverlayHeight(),
            this.getOverlayWidth(),
            this.getOverlayHeight()
         );
         Minecraft.m_91087_().m_91307_().m_7238_();
      }

      event.setIncrement(this.getVerticalIncrement());
   }

   private void drawBar(PoseStack stack, int x, int y, BossEvent event) {
      RenderSystem.m_157456_(0, this.getBaseTexture());
      GuiComponent.m_93133_(stack, x, y, 0.0F, 0.0F, this.getProgress(), this.getBaseHeight(), 256, this.getBaseTextureHeight());
      int i = (int)(event.m_142717_() * (float)(this.getProgress() + 1));
      if (i > 0) {
         RenderSystem.m_157456_(0, this.getBaseTexture());
         GuiComponent.m_93133_(stack, x, y, 0.0F, (float)this.getBaseHeight(), i, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      }
   }

   static {
      customBossBars.put(
         0,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/monstrosity_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/monstrosity_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.RED
         )
      );
      customBossBars.put(
         1,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ender_guardian_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ender_guardian_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.LIGHT_PURPLE
         )
      );
      customBossBars.put(
         2,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ignis_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ignis_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.YELLOW
         )
      );
      customBossBars.put(
         3,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ignis_soul_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/ignis_soul_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_AQUA
         )
      );
      customBossBars.put(
         4,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/harbinger_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/harbinger_bar_overlay.png"),
            5,
            16,
            1,
            7,
            -2,
            -8,
            256,
            32,
            25,
            182,
            ChatFormatting.DARK_RED
         )
      );
      customBossBars.put(
         5,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/leviathan_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/leviathan_bar_overlay.png"),
            5,
            16,
            1,
            2,
            -4,
            -4,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         6,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/leviathan_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/leviathan_meltdown_bar_overlay.png"),
            5,
            16,
            1,
            4,
            -4,
            -6,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         7,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/remnant_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/remnant_bar_overlay.png"),
            5,
            16,
            1,
            7,
            -4,
            -10,
            256,
            32,
            30,
            182,
            ChatFormatting.WHITE
         )
      );
      customBossBars.put(
         8,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/remnant_rage_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/remnant_rage_bar_overlay.png"),
            5,
            16,
            69,
            -8,
            -6,
            -8,
            256,
            16,
            15,
            48,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         9,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/maledictus_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/maledictus_bar_overlay.png"),
            5,
            16,
            1,
            7,
            -6,
            -9,
            256,
            32,
            25,
            182,
            ChatFormatting.DARK_GREEN
         )
      );
      customBossBars.put(
         10,
         new CustomBossBar(
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/maledictus_rage_bar_base.png"),
            new ResourceLocation("cataclysm", "textures/gui/boss_bar/maledictus_rage_bar_overlay.png"),
            5,
            16,
            69,
            -3,
            -6,
            -8,
            256,
            16,
            15,
            48,
            ChatFormatting.DARK_PURPLE
         )
      );
   }
}
