package io.redspace.ironsspellbooks.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Vector4f;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.network.ServerboundLearnSpell;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EldritchResearchScreen extends Screen {
   private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("irons_spellbooks", "textures/gui/eldritch_research_screen/window.png");
   private static final ResourceLocation FRAME_LOCATION = new ResourceLocation("irons_spellbooks", "textures/gui/eldritch_research_screen/spell_frame.png");
   public static final int WINDOW_WIDTH = 252;
   public static final int WINDOW_HEIGHT = 140;
   private static final int WINDOW_INSIDE_X = 9;
   private static final int WINDOW_INSIDE_Y = 18;
   public static final int WINDOW_INSIDE_WIDTH = 234;
   public static final int WINDOW_INSIDE_HEIGHT = 113;
   private static final int WINDOW_TITLE_X = 8;
   private static final int WINDOW_TITLE_Y = 6;
   public static final int BACKGROUND_TILE_WIDTH = 16;
   public static final int BACKGROUND_TILE_HEIGHT = 16;
   public static final int BACKGROUND_TILE_COUNT_X = 14;
   public static final int BACKGROUND_TILE_COUNT_Y = 7;
   int leftPos;
   int topPos;
   InteractionHand activeHand;
   List<AbstractSpell> learnableSpells;
   List<EldritchResearchScreen.SpellNode> nodes;
   SyncedSpellData playerData;
   Vec2 maxViewportOffset;
   Vec2 viewportOffset;
   boolean isMouseHoldingSpell;
   boolean isMouseDragging;
   int heldSpellIndex = -1;
   int heldSpellTime = -1;
   int lastPlayerTick;
   static final int TIME_TO_HOLD = 15;
   private static final Component ALREADY_LEARNED = Component.m_237115_("ui.irons_spellbooks.research_already_learned").m_130940_(ChatFormatting.DARK_AQUA);
   private static final Component UNLEARNED = Component.m_237115_("ui.irons_spellbooks.research_warning").m_130940_(ChatFormatting.RED);

   public EldritchResearchScreen(Component pTitle, InteractionHand activeHand) {
      super(pTitle);
      this.activeHand = activeHand;
   }

   protected void m_7856_() {
      this.learnableSpells = SpellRegistry.getEnabledSpells().stream().filter(spell -> !spell.isLearned(null)).toList();
      if (this.f_96541_ != null) {
         this.playerData = ClientMagicData.getSyncedSpellData(this.f_96541_.f_91074_);
      }

      this.viewportOffset = Vec2.f_82462_;
      this.leftPos = (this.f_96543_ - 252) / 2;
      this.topPos = (this.f_96544_ - 140) / 2;
      this.nodes = new ArrayList<>();
      float f = 6.282F / (float)this.learnableSpells.size();

      for (int i = 0; i < this.learnableSpells.size(); i++) {
         float r = 35.0F;
         int x = this.leftPos + 126 - 8 + (int)(r * Mth.m_14089_(f * (float)i));
         int y = this.topPos + 70 - 8 + (int)(r * Mth.m_14031_(f * (float)i));
         this.nodes.add(new EldritchResearchScreen.SpellNode(this.learnableSpells.get(i), x, y));
      }

      float maxDistX = 0.0F;
      float maxDistY = 0.0F;

      for (int i = 0; i < this.nodes.size(); i++) {
         for (int j = 1; j < this.nodes.size(); j++) {
            int x = Math.abs(this.nodes.get(i).x - this.nodes.get(j).x);
            if ((float)x > maxDistX) {
               maxDistX = (float)x;
            }

            int y = Math.abs(this.nodes.get(i).y - this.nodes.get(j).y);
            if ((float)y > maxDistY) {
               maxDistY = (float)y;
            }
         }
      }

      this.maxViewportOffset = new Vec2((float)((int)maxDistX), (float)((int)maxDistY));
   }

   public void m_6305_(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      super.m_6305_(poseStack, mouseX, mouseY, partialTick);
      this.m_93179_(poseStack, 0, 0, this.f_96543_, this.f_96544_, -1072689136, -804253680);
      this.drawBackdrop(this.leftPos + 9, this.topPos + 18);
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         if (player.f_19797_ != this.lastPlayerTick) {
            this.lastPlayerTick = player.f_19797_;
            if (this.isMouseHoldingSpell
               && this.heldSpellIndex >= 0
               && this.heldSpellIndex < this.nodes.size()
               && !this.nodes.get(this.heldSpellIndex).spell.isLearned(player)) {
               if (this.heldSpellTime > 15) {
                  this.heldSpellTime = -1;
                  Messages.sendToServer(new ServerboundLearnSpell(this.activeHand, this.nodes.get(this.heldSpellIndex).spell.getSpellId()));
                  player.m_6330_((SoundEvent)SoundRegistry.LEARN_ELDRITCH_SPELL.get(), SoundSource.MASTER, 1.0F, (float)Utils.random.m_216332_(9, 11) * 0.1F);
               }

               this.heldSpellTime++;
               if (this.lastPlayerTick % 2 == 0) {
                  player.m_6330_(SoundEvents.f_12404_, SoundSource.MASTER, 1.0F, Mth.m_14179_((float)this.heldSpellTime / 15.0F, 0.5F, 1.5F));
                  player.m_6330_((SoundEvent)SoundRegistry.UI_TICK.get(), SoundSource.MASTER, 1.0F, Mth.m_14179_((float)this.heldSpellTime / 15.0F, 0.5F, 1.5F));
               }
            } else if (this.heldSpellTime >= 0) {
               this.heldSpellTime = Math.max(this.heldSpellTime - 3, -1);
            }
         }

         this.handleConnections(poseStack, partialTick);
         List<FormattedCharSequence> tooltip = null;

         for (int i = 0; i < this.nodes.size(); i++) {
            EldritchResearchScreen.SpellNode node = this.nodes.get(i);
            this.drawNode(poseStack, node, player, i == this.heldSpellIndex && this.heldSpellTime > 0);
            if (this.isHoveringNode(node, mouseX, mouseY)) {
               tooltip = buildTooltip(node.spell, this.f_96547_);
            }
         }

         setTranslucentTexture(WINDOW_LOCATION);
         this.m_93228_(poseStack, this.leftPos, this.topPos, 0, 0, 252, 140);
         if (tooltip != null) {
            this.m_96617_(poseStack, tooltip, mouseX, mouseY);
         }
      }
   }

   private void renderProgressOverlay(int x, int y, float progress) {
      RenderSystem.m_69465_();
      RenderSystem.m_69472_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      this.fillRect(bufferbuilder, x, y, Mth.m_14167_(16.0F * progress), 16, 244, 65, 255, 127);
      RenderSystem.m_69493_();
      RenderSystem.m_69482_();
   }

   private void fillRect(BufferBuilder pRenderer, int pX, int pY, int pWidth, int pHeight, int pRed, int pGreen, int pBlue, int pAlpha) {
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      pRenderer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      pRenderer.m_5483_((double)(pX + 0), (double)(pY + 0), 0.0).m_6122_(pRed, pGreen, pBlue, pAlpha).m_5752_();
      pRenderer.m_5483_((double)(pX + 0), (double)(pY + pHeight), 0.0).m_6122_(pRed, pGreen, pBlue, pAlpha).m_5752_();
      pRenderer.m_5483_((double)(pX + pWidth), (double)(pY + pHeight), 0.0).m_6122_(pRed, pGreen, pBlue, pAlpha).m_5752_();
      pRenderer.m_5483_((double)(pX + pWidth), (double)(pY + 0), 0.0).m_6122_(pRed, pGreen, pBlue, pAlpha).m_5752_();
      BufferUploader.m_231202_(pRenderer.m_231175_());
   }

   private void drawNode(PoseStack poseStack, EldritchResearchScreen.SpellNode node, LocalPlayer player, boolean drawProgress) {
      this.drawWithClipping(node.spell.getSpellIconResource(), poseStack, node.x, node.y, 0, 0, 16, 16, 16, 16, this.leftPos + 9, this.topPos + 18, 234, 113);
      if (drawProgress) {
         this.renderProgressOverlay(node.x, node.y, (float)this.heldSpellTime / 15.0F);
      }

      setTexture(FRAME_LOCATION);
      this.drawWithClipping(
         FRAME_LOCATION,
         poseStack,
         node.x - 8,
         node.y - 8,
         node.spell.isLearned(player) ? 32 : 0,
         0,
         32,
         32,
         64,
         32,
         this.leftPos + 9,
         this.topPos + 18,
         234,
         113
      );
   }

   private void drawWithClipping(
      ResourceLocation texture,
      PoseStack poseStack,
      int x,
      int y,
      int uvx,
      int uvy,
      int width,
      int height,
      int imageWidth,
      int imageHeight,
      int bbx,
      int bby,
      int bbw,
      int bbh
   ) {
      x = (int)((float)x + this.viewportOffset.f_82470_);
      if (x < bbx) {
         int xDiff = bbx - x;
         width -= xDiff;
         uvx += xDiff;
         x += xDiff;
      } else if (x > bbx + bbw - width) {
         int xDiff = x - (bbx + bbw - width);
         width -= xDiff;
      }

      y = (int)((float)y + this.viewportOffset.f_82471_);
      if (y < bby) {
         int yDiff = bby - y;
         height -= yDiff;
         uvy += yDiff;
         y += yDiff;
      } else if (y > bby + bbh - height) {
         int yDiff = y - (bby + bbh - height);
         height -= yDiff;
      }

      if (width > 0 && height > 0) {
         setTexture(texture);
         m_93160_(poseStack, x, y, width, height, (float)uvx, (float)uvy, width, height, imageWidth, imageHeight);
      }
   }

   public static List<FormattedCharSequence> buildTooltip(AbstractSpell spell, Font font) {
      boolean learned = spell.isLearned(Minecraft.m_91087_().f_91074_);
      MutableComponent name = spell.getDisplayName(null).m_130940_(learned ? ChatFormatting.DARK_AQUA : ChatFormatting.RED);
      List<FormattedCharSequence> description = font.m_92923_(
         Component.m_237115_(String.format("%s.guide", spell.getComponentId())).m_130940_(ChatFormatting.GRAY), 180
      );
      ArrayList<FormattedCharSequence> hoverText = new ArrayList<>();
      hoverText.add(FormattedCharSequence.m_13714_(name.getString(), name.m_7383_().m_131162_(true)));
      hoverText.addAll(description);
      hoverText.add(FormattedCharSequence.f_13691_);
      hoverText.add((learned ? ALREADY_LEARNED : UNLEARNED).m_7532_());
      return hoverText;
   }

   private void handleConnections(PoseStack poseStack, float partialTick) {
      m_93172_(poseStack, 0, 0, this.f_96543_, this.f_96544_, 0);
      RenderSystem.m_69472_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder buffer = tesselator.m_85915_();
      buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);

      for (int i = 0; i < this.nodes.size() - 1; i++) {
         Vec2 a = new Vec2((float)this.nodes.get(i).x, (float)this.nodes.get(i).y);
         Vec2 b = new Vec2((float)this.nodes.get(i + 1).x, (float)this.nodes.get(i + 1).y);
         Vec2 org = new Vec2(-(b.f_82471_ - a.f_82471_), b.f_82470_ - a.f_82470_).m_165902_().m_165903_(1.5F);
         double x1m1 = (double)(a.f_82470_ + org.f_82470_ + 8.0F + this.viewportOffset.f_82470_);
         double x2m1 = (double)(b.f_82470_ + org.f_82470_ + 8.0F + this.viewportOffset.f_82470_);
         double y1m1 = (double)(a.f_82471_ + org.f_82471_ + 8.0F + this.viewportOffset.f_82471_);
         double y2m1 = (double)(b.f_82471_ + org.f_82471_ + 8.0F + this.viewportOffset.f_82471_);
         double x1m2 = (double)(a.f_82470_ - org.f_82470_ + 8.0F + this.viewportOffset.f_82470_);
         double x2m2 = (double)(b.f_82470_ - org.f_82470_ + 8.0F + this.viewportOffset.f_82470_);
         double y1m2 = (double)(a.f_82471_ - org.f_82471_ + 8.0F + this.viewportOffset.f_82471_);
         double y2m2 = (double)(b.f_82471_ - org.f_82471_ + 8.0F + this.viewportOffset.f_82471_);
         float f = Mth.m_14031_(((float)Minecraft.m_91087_().f_91074_.f_19797_ + partialTick) * 0.1F);
         float glowIntensity = f * f;
         Vector4f color = new Vector4f(0.5294118F, 0.6039216F, 0.68235296F, 0.5F);
         Vector4f glowcolor = new Vector4f(0.95686275F, 0.25490198F, 1.0F, 0.5F);
         Vector4f color1 = lerpColor(color, glowcolor, glowIntensity * (float)(this.nodes.get(i).spell.isLearned(Minecraft.m_91087_().f_91074_) ? 1 : 0));
         Vector4f color2 = lerpColor(color, glowcolor, glowIntensity * (float)(this.nodes.get(i + 1).spell.isLearned(Minecraft.m_91087_().f_91074_) ? 1 : 0));
         double alphaTopLeft = Mth.m_14008_(x1m1 + (double)this.viewportOffset.f_82470_ - (double)this.leftPos, 0.0, 18.0)
            / 9.0
            * 2.0
            * Mth.m_14008_(y1m1 + (double)this.viewportOffset.f_82471_ - (double)this.topPos, 0.0, 36.0)
            / 18.0
            * 2.0;
         buffer.m_5483_(x1m1, y1m1, (double)this.m_93252_())
            .m_85950_(color1.m_123601_(), color1.m_123615_(), color1.m_123616_(), this.fadeOutTowardEdges(poseStack, x1m1, y1m1))
            .m_5752_();
         buffer.m_5483_(x2m1, y2m1, (double)this.m_93252_())
            .m_85950_(color2.m_123601_(), color2.m_123615_(), color2.m_123616_(), this.fadeOutTowardEdges(poseStack, x2m1, y2m1))
            .m_5752_();
         buffer.m_5483_(x2m2, y2m2, (double)this.m_93252_())
            .m_85950_(color2.m_123601_(), color2.m_123615_(), color2.m_123616_(), this.fadeOutTowardEdges(poseStack, x2m2, y2m2))
            .m_5752_();
         buffer.m_5483_(x1m2, y1m2, (double)this.m_93252_())
            .m_85950_(color1.m_123601_(), color1.m_123615_(), color1.m_123616_(), this.fadeOutTowardEdges(poseStack, x1m2, y1m2))
            .m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69461_();
      RenderSystem.m_69493_();
   }

   private float fadeOutTowardEdges(PoseStack poseStack, double x, double y) {
      int px = (int)Mth.m_14008_(x + (double)this.viewportOffset.f_82470_ - (double)this.leftPos, 0.0, 18.0);
      int py = (int)Mth.m_14008_(y + (double)this.viewportOffset.f_82471_ - (double)this.topPos, 0.0, 36.0);
      int px2 = (int)Mth.m_14008_(234.0 - (x + (double)this.viewportOffset.f_82470_ - (double)this.leftPos), 0.0, 18.0);
      int py2 = (int)Mth.m_14008_(113.0 - (y + (double)this.viewportOffset.f_82471_ - (double)this.topPos), 0.0, 36.0);
      return Mth.m_14036_((float)px / 4.5F, 0.0F, 1.0F)
         * Mth.m_14036_((float)py / 9.0F, 0.0F, 1.0F)
         * Mth.m_14036_((float)px2 / 4.5F, 0.0F, 1.0F)
         * Mth.m_14036_((float)py2 / 9.0F, 0.0F, 1.0F);
   }

   private int colorFromRGBA(Vector4f rgba) {
      int r = (int)(rgba.m_123601_() * 255.0F) & 0xFF;
      int g = (int)(rgba.m_123615_() * 255.0F) & 0xFF;
      int b = (int)(rgba.m_123616_() * 255.0F) & 0xFF;
      int a = (int)(rgba.m_123617_() * 255.0F) & 0xFF;
      return (r << 24) + (g << 16) + (b << 8) + a;
   }

   private void drawBackdrop(int left, int top) {
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172755_);
      RenderSystem.m_157456_(0, TheEndPortalRenderer.f_112627_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = Minecraft.m_91087_().f_91074_ != null ? (float)Minecraft.m_91087_().f_91074_.f_19797_ * 0.086F : 0.0F;
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      bufferbuilder.m_5483_((double)((float)left), (double)((float)top + 113.0F), 0.0).m_7421_(f, f).m_6122_(1, 1, 1, 1).m_5752_();
      bufferbuilder.m_5483_((double)((float)left + 234.0F), (double)((float)top + 113.0F), 0.0).m_6122_(1, 1, 1, 1).m_5752_();
      bufferbuilder.m_5483_((double)((float)left + 234.0F), (double)((float)top), 0.0).m_6122_(1, 1, 1, 1).m_5752_();
      bufferbuilder.m_5483_((double)((float)left), (double)((float)top), 0.0).m_6122_(1, 1, 1, 1).m_5752_();
      BufferUploader.m_231202_(bufferbuilder.m_231175_());
      RenderSystem.m_69461_();
   }

   private static Vector4f lerpColor(Vector4f a, Vector4f b, float pDelta) {
      float f = 1.0F - pDelta;
      float x = a.m_123601_() * f + b.m_123601_() * pDelta;
      float y = a.m_123615_() * f + b.m_123615_() * pDelta;
      float z = a.m_123616_() * f + b.m_123616_() * pDelta;
      float w = a.m_123617_() * f + b.m_123617_() * pDelta;
      return new Vector4f(x, y, z, w);
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      int mouseX = (int)pMouseX;
      int mouseY = (int)pMouseY;
      if (Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_21120_(this.activeHand).m_150930_((Item)ItemRegistry.ELDRITCH_PAGE.get())) {
         for (int i = 0; i < this.nodes.size(); i++) {
            if (this.isHoveringNode(this.nodes.get(i), mouseX, mouseY)) {
               this.heldSpellIndex = i;
               this.isMouseHoldingSpell = true;
               break;
            }
         }
      }

      if (!this.isMouseHoldingSpell && this.isHovering(this.leftPos + 9, this.topPos + 18, 234, 113, mouseX, mouseY)) {
         this.isMouseDragging = true;
      }

      return super.m_6375_(pMouseX, pMouseY, pButton);
   }

   public boolean isHoveringNode(EldritchResearchScreen.SpellNode node, int mouseX, int mouseY) {
      return this.isHovering(node.x - 2 + (int)this.viewportOffset.f_82470_, node.y - 2 + (int)this.viewportOffset.f_82471_, 20, 20, mouseX, mouseY);
   }

   public boolean m_6348_(double pMouseX, double pMouseY, int pButton) {
      this.isMouseHoldingSpell = false;
      this.isMouseDragging = false;
      return super.m_6348_(pMouseX, pMouseY, pButton);
   }

   public boolean m_7979_(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
      if (this.isMouseDragging) {
      }

      return super.m_7979_(pMouseX, pMouseY, pButton, pDragX, pDragY);
   }

   public boolean m_7933_(int pKeyCode, int pScanCode, int pModifiers) {
      Key mouseKey = InputConstants.m_84827_(pKeyCode, pScanCode);
      if (this.f_96541_.f_91066_.f_92092_.isActiveAndMatches(mouseKey)) {
         this.m_7379_();
         return true;
      } else {
         return super.m_7933_(pKeyCode, pScanCode, pModifiers);
      }
   }

   public boolean m_7043_() {
      return false;
   }

   private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
      return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
   }

   private static void setTexture(ResourceLocation texture) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, texture);
   }

   private static void setTranslucentTexture(ResourceLocation texture) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172649_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, texture);
   }

   static record NodeConnection(EldritchResearchScreen.SpellNode node1, EldritchResearchScreen.SpellNode node2) {
   }

   static record SpellNode(AbstractSpell spell, int x, int y) {
   }
}
