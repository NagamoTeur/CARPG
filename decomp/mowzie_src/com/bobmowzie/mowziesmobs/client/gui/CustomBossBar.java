package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.minecraftforge.registries.ForgeRegistries;

public class CustomBossBar {
   public static Map<ResourceLocation, CustomBossBar> customBossBars = new HashMap<>();
   private final ResourceLocation baseTexture;
   private final ResourceLocation overlayTexture;
   private final boolean hasOverlay;
   private final int baseHeight;
   private final int baseTextureHeight;
   private final int baseOffsetY;
   private final int overlayOffsetX;
   private final int overlayOffsetY;
   private final int overlayWidth;
   private final int overlayHeight;
   private final int verticalIncrement;
   private final ChatFormatting textColor;

   public CustomBossBar(
      ResourceLocation baseTexture,
      ResourceLocation overlayTexture,
      int baseHeight,
      int baseTextureHeight,
      int baseOffsetY,
      int overlayOffsetX,
      int overlayOffsetY,
      int overlayWidth,
      int overlayHeight,
      int verticalIncrement,
      ChatFormatting textColor
   ) {
      this.baseTexture = baseTexture;
      this.overlayTexture = overlayTexture;
      this.hasOverlay = overlayTexture != null;
      this.baseHeight = baseHeight;
      this.baseTextureHeight = baseTextureHeight;
      this.baseOffsetY = baseOffsetY;
      this.overlayOffsetX = overlayOffsetX;
      this.overlayOffsetY = overlayOffsetY;
      this.overlayWidth = overlayWidth;
      this.overlayHeight = overlayHeight;
      this.verticalIncrement = verticalIncrement;
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
      int k = i / 2 - 91;
      Minecraft.m_91087_().m_91307_().m_6180_("customBossBarBase");
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, this.getBaseTexture());
      this.drawBar(stack, event.getX() + 1, y + this.getBaseOffsetY(), event.getBossEvent());
      Component component = event.getBossEvent().m_18861_().m_6881_().m_130940_(this.getTextColor());
      Minecraft.m_91087_().m_91307_().m_7238_();
      int l = Minecraft.m_91087_().f_91062_.m_92852_(component);
      int i1 = i / 2 - l / 2;
      Minecraft.m_91087_().f_91062_.m_92763_(stack, component, (float)i1, (float)j, 16777215);
      if (this.hasOverlay()) {
         Minecraft.m_91087_().m_91307_().m_6180_("customBossBarOverlay");
         RenderSystem.m_157456_(0, this.getOverlayTexture());
         Gui.m_93133_(
            stack,
            event.getX() + 1 + this.getOverlayOffsetX(),
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
      GuiComponent.m_93133_(stack, x, y, 0.0F, 0.0F, 182, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      int i = (int)(event.m_142717_() * 183.0F);
      if (i > 0) {
         GuiComponent.m_93133_(stack, x, y, 0.0F, (float)this.getBaseHeight(), i, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      }
   }

   static {
      customBossBars.put(
         ForgeRegistries.ENTITY_TYPES.getKey((EntityType)EntityHandler.UMVUTHI.get()),
         new CustomBossBar(
            new ResourceLocation("mowziesmobs", "textures/gui/boss_bar/umvuthi_bar_base.png"),
            new ResourceLocation("mowziesmobs", "textures/gui/boss_bar/umvuthi_bar_overlay.png"),
            4,
            8,
            2,
            -12,
            -6,
            256,
            16,
            21,
            ChatFormatting.GOLD
         )
      );
      customBossBars.put(
         ForgeRegistries.ENTITY_TYPES.getKey((EntityType)EntityHandler.FROSTMAW.get()),
         new CustomBossBar(
            new ResourceLocation("mowziesmobs", "textures/gui/boss_bar/frostmaw_bar_base.png"),
            new ResourceLocation("mowziesmobs", "textures/gui/boss_bar/frostmaw_bar_overlay.png"),
            10,
            32,
            2,
            -4,
            -3,
            256,
            32,
            25,
            ChatFormatting.WHITE
         )
      );
   }
}
