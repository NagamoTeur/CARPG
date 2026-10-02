package com.hollingsworth.arsnouveau.client.gui.radial_menu;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber({Dist.CLIENT})
public class GuiRadialMenu<T> extends Screen {
   private static final float PRECISION = 5.0F;
   private static final int MAX_SLOTS = 20;
   private boolean closing;
   private RadialMenu<T> radialMenu;
   private List<RadialMenuSlot<T>> radialMenuSlots;
   final float OPEN_ANIMATION_LENGTH = 0.4F;
   private float totalTime;
   private float prevTick;
   private float extraTick;
   private int selectedItem;

   public GuiRadialMenu(RadialMenu<T> radialMenu) {
      super(Component.m_237113_(""));
      this.radialMenu = radialMenu;
      this.radialMenuSlots = this.radialMenu.getRadialMenuSlots();
      this.closing = false;
      this.f_96541_ = Minecraft.m_91087_();
      this.selectedItem = -1;
   }

   public GuiRadialMenu() {
      super(Component.m_237113_(""));
   }

   @SubscribeEvent
   public static void updateInputEvent(MovementInputUpdateEvent event) {
      if (Minecraft.m_91087_().f_91080_ instanceof GuiRadialMenu) {
         Options settings = Minecraft.m_91087_().f_91066_;
         Input eInput = event.getInput();
         eInput.f_108568_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92085_.getKey().m_84873_());
         eInput.f_108569_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92087_.getKey().m_84873_());
         eInput.f_108570_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92086_.getKey().m_84873_());
         eInput.f_108571_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92088_.getKey().m_84873_());
         eInput.f_108567_ = eInput.f_108568_ == eInput.f_108569_ ? 0.0F : (eInput.f_108568_ ? 1.0F : -1.0F);
         eInput.f_108566_ = eInput.f_108570_ == eInput.f_108571_ ? 0.0F : (eInput.f_108570_ ? 1.0F : -1.0F);
         eInput.f_108572_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92089_.getKey().m_84873_());
         eInput.f_108573_ = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), settings.f_92090_.getKey().m_84873_());
         if (Minecraft.m_91087_().f_91074_.m_108635_()) {
            eInput.f_108566_ = (float)((double)eInput.f_108566_ * 0.3);
            eInput.f_108567_ = (float)((double)eInput.f_108567_ * 0.3);
         }
      }
   }

   public void m_86600_() {
      if (this.totalTime != 0.4F) {
         this.extraTick++;
      }
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(ms, mouseX, mouseY, partialTicks);
      float openAnimation = this.closing ? 1.0F - this.totalTime / 0.4F : this.totalTime / 0.4F;
      float currTick = this.f_96541_.m_91296_();
      this.totalTime = this.totalTime + (currTick + this.extraTick - this.prevTick) / 20.0F;
      this.extraTick = 0.0F;
      this.prevTick = currTick;
      float animProgress = Mth.m_14036_(openAnimation, 0.0F, 1.0F);
      animProgress = (float)(1.0 - Math.pow((double)(1.0F - animProgress), 3.0));
      float radiusIn = Math.max(0.1F, 45.0F * animProgress);
      float radiusOut = radiusIn * 2.0F;
      float itemRadius = (radiusIn + radiusOut) * 0.5F;
      int centerOfScreenX = this.f_96543_ / 2;
      int centerOfScreenY = this.f_96544_ / 2;
      int numberOfSlices = Math.min(20, this.radialMenuSlots.size());
      double mousePositionInDegreesInRelationToCenterOfScreen = Math.toDegrees(
         Math.atan2((double)(mouseY - centerOfScreenY), (double)(mouseX - centerOfScreenX))
      );
      double mouseDistanceToCenterOfScreen = Math.sqrt(Math.pow((double)(mouseX - centerOfScreenX), 2.0) + Math.pow((double)(mouseY - centerOfScreenY), 2.0));
      float slot0 = (-0.5F / (float)numberOfSlices + 0.25F) * 360.0F;
      if (mousePositionInDegreesInRelationToCenterOfScreen < (double)slot0) {
         mousePositionInDegreesInRelationToCenterOfScreen += 360.0;
      }

      ms.m_85836_();
      RenderSystem.m_69478_();
      RenderSystem.m_69472_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder buffer = tessellator.m_85915_();
      buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      boolean hasMouseOver = false;
      int mousedOverSlot = -1;
      if (!this.closing) {
         this.selectedItem = -1;

         for (int i = 0; i < numberOfSlices; i++) {
            float sliceBorderLeft = (((float)i - 0.5F) / (float)numberOfSlices + 0.25F) * 360.0F;
            float sliceBorderRight = (((float)i + 0.5F) / (float)numberOfSlices + 0.25F) * 360.0F;
            if (mousePositionInDegreesInRelationToCenterOfScreen >= (double)sliceBorderLeft
               && mousePositionInDegreesInRelationToCenterOfScreen < (double)sliceBorderRight
               && mouseDistanceToCenterOfScreen >= (double)radiusIn
               && mouseDistanceToCenterOfScreen < (double)radiusOut) {
               this.selectedItem = i;
               break;
            }
         }
      }

      for (int ix = 0; ix < numberOfSlices; ix++) {
         float sliceBorderLeft = (((float)ix - 0.5F) / (float)numberOfSlices + 0.25F) * 360.0F;
         float sliceBorderRight = (((float)ix + 0.5F) / (float)numberOfSlices + 0.25F) * 360.0F;
         if (this.selectedItem == ix) {
            this.drawSlice(
               buffer, (float)centerOfScreenX, (float)centerOfScreenY, 10.0F, radiusIn, radiusOut, sliceBorderLeft, sliceBorderRight, 63, 161, 191, 60
            );
            hasMouseOver = true;
            mousedOverSlot = this.selectedItem;
         } else {
            this.drawSlice(buffer, (float)centerOfScreenX, (float)centerOfScreenY, 10.0F, radiusIn, radiusOut, sliceBorderLeft, sliceBorderRight, 0, 0, 0, 64);
         }
      }

      tessellator.m_85914_();
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
      if (hasMouseOver && mousedOverSlot != -1) {
         int adjusted = (mousedOverSlot + numberOfSlices / 2 + 1) % numberOfSlices - 1;
         adjusted = adjusted == -1 ? numberOfSlices - 1 : adjusted;
         m_93208_(ms, this.f_96547_, this.radialMenuSlots.get(adjusted).slotName(), this.f_96543_ / 2, (this.f_96544_ - 9) / 2, 16777215);
      }

      ms.m_85849_();

      for (int ixx = 0; ixx < numberOfSlices; ixx++) {
         ItemStack stack = new ItemStack(Blocks.f_50493_);
         float angle1 = ((float)ixx / (float)numberOfSlices - 0.25F) * 2.0F * (float) Math.PI;
         if (numberOfSlices % 2 != 0) {
            angle1 = (float)((double)angle1 + Math.PI / (double)numberOfSlices);
         }

         float posX = (float)(centerOfScreenX - 8) + itemRadius * (float)Math.cos((double)angle1);
         float posY = (float)(centerOfScreenY - 8) + itemRadius * (float)Math.sin((double)angle1);
         RenderSystem.m_69465_();
         T primarySlotIcon = this.radialMenuSlots.get(ixx).primarySlotIcon();
         List<T> secondarySlotIcons = this.radialMenuSlots.get(ixx).secondarySlotIcons();
         if (primarySlotIcon != null) {
            this.radialMenu.drawIcon(primarySlotIcon, ms, (int)posX, (int)posY, 16);
            if (secondarySlotIcons != null && !secondarySlotIcons.isEmpty()) {
               this.drawSecondaryIcons(ms, (int)posX, (int)posY, secondarySlotIcons);
            }
         }

         this.drawSliceName(String.valueOf(ixx + 1), stack, (int)posX, (int)posY);
      }

      if (mousedOverSlot != -1) {
         int adjusted = (mousedOverSlot + numberOfSlices / 2 + 1) % numberOfSlices - 1;
         adjusted = adjusted == -1 ? numberOfSlices - 1 : adjusted;
         this.selectedItem = adjusted;
      }
   }

   public void drawSecondaryIcons(PoseStack ms, int positionXOfPrimaryIcon, int positionYOfPrimaryIcon, List<T> secondarySlotIcons) {
      if (!this.radialMenu.isShowMoreSecondaryItems()) {
         this.drawSecondaryIcon(
            ms, secondarySlotIcons.get(0), positionXOfPrimaryIcon, positionYOfPrimaryIcon, this.radialMenu.getSecondaryIconStartingPosition()
         );
      } else {
         SecondaryIconPosition currentSecondaryIconPosition = this.radialMenu.getSecondaryIconStartingPosition();

         for (T secondarySlotIcon : secondarySlotIcons) {
            this.drawSecondaryIcon(ms, secondarySlotIcon, positionXOfPrimaryIcon, positionYOfPrimaryIcon, currentSecondaryIconPosition);
            currentSecondaryIconPosition = SecondaryIconPosition.getNextPositon(currentSecondaryIconPosition);
         }
      }
   }

   public void drawSecondaryIcon(
      PoseStack poseStack, T item, int positionXOfPrimaryIcon, int positionYOfPrimaryIcon, SecondaryIconPosition secondaryIconPosition
   ) {
      int offset = this.radialMenu.getOffset();
      switch (secondaryIconPosition) {
         case NORTH:
            this.radialMenu.drawIcon(item, poseStack, positionXOfPrimaryIcon + offset, positionYOfPrimaryIcon - 14 + offset, 10);
            break;
         case EAST:
            this.radialMenu.drawIcon(item, poseStack, positionXOfPrimaryIcon + 14 + offset, positionYOfPrimaryIcon + offset, 10);
            break;
         case SOUTH:
            this.radialMenu.drawIcon(item, poseStack, positionXOfPrimaryIcon + offset, positionYOfPrimaryIcon + 14 + offset, 10);
            break;
         case WEST:
            this.radialMenu.drawIcon(item, poseStack, positionXOfPrimaryIcon - 14 + offset, positionYOfPrimaryIcon + offset, 10);
      }
   }

   public void drawSliceName(String sliceName, ItemStack stack, int posX, int posY) {
      if (!this.radialMenu.isShowMoreSecondaryItems()) {
         this.f_96542_.m_115174_(this.f_96547_, stack, posX + 5, posY, sliceName);
      } else {
         this.f_96542_.m_115174_(this.f_96547_, stack, posX + 5, posY + 5, sliceName);
      }
   }

   public boolean m_7933_(int key, int scanCode, int modifiers) {
      int adjustedKey = key - 48;
      if (adjustedKey >= 0 && adjustedKey < this.radialMenuSlots.size()) {
         this.selectedItem = adjustedKey == 0 ? this.radialMenuSlots.size() : adjustedKey;
         this.selectedItem--;
         this.m_6375_(0.0, 0.0, 0);
         return true;
      } else {
         return super.m_7933_(key, scanCode, modifiers);
      }
   }

   public boolean m_6375_(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (this.selectedItem != -1) {
         this.radialMenu.setCurrentSlot(this.selectedItem);
         this.f_96541_.f_91074_.m_6915_();
      }

      return true;
   }

   public void drawSlice(
      BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int r, int g, int b, int a
   ) {
      float angle = endAngle - startAngle;
      int sections = Math.max(1, Mth.m_14167_(angle / 5.0F));
      startAngle = (float)Math.toRadians((double)startAngle);
      endAngle = (float)Math.toRadians((double)endAngle);
      angle = endAngle - startAngle;

      for (int i = 0; i < sections; i++) {
         float angle1 = startAngle + (float)i / (float)sections * angle;
         float angle2 = startAngle + (float)(i + 1) / (float)sections * angle;
         float pos1InX = x + radiusIn * (float)Math.cos((double)angle1);
         float pos1InY = y + radiusIn * (float)Math.sin((double)angle1);
         float pos1OutX = x + radiusOut * (float)Math.cos((double)angle1);
         float pos1OutY = y + radiusOut * (float)Math.sin((double)angle1);
         float pos2OutX = x + radiusOut * (float)Math.cos((double)angle2);
         float pos2OutY = y + radiusOut * (float)Math.sin((double)angle2);
         float pos2InX = x + radiusIn * (float)Math.cos((double)angle2);
         float pos2InY = y + radiusIn * (float)Math.sin((double)angle2);
         buffer.m_5483_((double)pos1OutX, (double)pos1OutY, (double)z).m_6122_(r, g, b, a).m_5752_();
         buffer.m_5483_((double)pos1InX, (double)pos1InY, (double)z).m_6122_(r, g, b, a).m_5752_();
         buffer.m_5483_((double)pos2InX, (double)pos2InY, (double)z).m_6122_(r, g, b, a).m_5752_();
         buffer.m_5483_((double)pos2OutX, (double)pos2OutY, (double)z).m_6122_(r, g, b, a).m_5752_();
      }
   }

   public boolean m_7043_() {
      return false;
   }
}
