package io.redspace.ironsspellbooks.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ManaBarOverlay {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/icons.png");
   static final int DEFAULT_IMAGE_WIDTH = 98;
   static final int XP_IMAGE_WIDTH = 188;
   static final int IMAGE_HEIGHT = 21;
   static final int HOTBAR_HEIGHT = 25;
   static final int ICON_ROW_HEIGHT = 11;
   static final int CHAR_WIDTH = 6;
   static final int HUNGER_BAR_OFFSET = 50;
   static final int SCREEN_BORDER_MARGIN = 20;
   static final int TEXT_COLOR = ChatFormatting.AQUA.m_126665_();

   public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (shouldShowManaBar(player)) {
         int maxMana = (int)player.m_21133_((Attribute)AttributeRegistry.MAX_MANA.get());
         int mana = ClientMagicData.getPlayerMana();
         int configOffsetY = (Integer)ClientConfigs.MANA_BAR_Y_OFFSET.get();
         int configOffsetX = (Integer)ClientConfigs.MANA_BAR_X_OFFSET.get();
         ManaBarOverlay.Anchor anchor = (ManaBarOverlay.Anchor)ClientConfigs.MANA_BAR_ANCHOR.get();
         if (anchor != ManaBarOverlay.Anchor.XP || !(player.m_108634_() > 0.0F)) {
            int barX = getBarX(anchor, screenWidth) + configOffsetX;
            int barY = getBarY(anchor, screenHeight, gui) - configOffsetY;
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.m_157456_(0, TEXTURE);
            int imageWidth = anchor == ManaBarOverlay.Anchor.XP ? 188 : 98;
            int spriteX = anchor == ManaBarOverlay.Anchor.XP ? 68 : 0;
            int spriteY = anchor == ManaBarOverlay.Anchor.XP ? 40 : 0;
            ForgeGui.m_93133_(poseStack, barX, barY, (float)spriteX, (float)spriteY, imageWidth, 21, 256, 256);
            gui.m_93228_(poseStack, barX, barY, spriteX, spriteY + 21, (int)((double)imageWidth * Math.min((double)mana / (double)maxMana, 1.0)), 21);
            String manaFraction = mana + "/" + maxMana;
            int textX = (Integer)ClientConfigs.MANA_TEXT_X_OFFSET.get() + barX + imageWidth / 2 - (int)(((double)(mana + "").length() + 0.5) * 6.0);
            int textY = (Integer)ClientConfigs.MANA_TEXT_Y_OFFSET.get() + barY + (anchor == ManaBarOverlay.Anchor.XP ? 3 : 11);
            if ((Boolean)ClientConfigs.MANA_BAR_TEXT_VISIBLE.get()) {
               gui.m_93082_().m_92750_(poseStack, manaFraction, (float)textX, (float)textY, TEXT_COLOR);
            }
         }
      }
   }

   public static boolean shouldShowManaBar(Player player) {
      ManaBarOverlay.Display display = (ManaBarOverlay.Display)ClientConfigs.MANA_BAR_DISPLAY.get();
      return !player.m_5833_()
         && display != ManaBarOverlay.Display.Never
         && (
            display == ManaBarOverlay.Display.Always
               || player.m_21093_(
                  itemStack -> itemStack.m_41720_() instanceof CastingItem
                        || ISpellContainer.isSpellContainer(itemStack) && !ISpellContainer.get(itemStack).mustEquip()
               )
               || (double)ClientMagicData.getPlayerMana() < player.m_21133_((Attribute)AttributeRegistry.MAX_MANA.get())
         );
   }

   private static int getBarX(ManaBarOverlay.Anchor anchor, int screenWidth) {
      if (anchor == ManaBarOverlay.Anchor.XP) {
         return screenWidth / 2 - 91 - 3;
      } else if (anchor == ManaBarOverlay.Anchor.Hunger || anchor == ManaBarOverlay.Anchor.Center) {
         return screenWidth / 2 - 49 + (anchor == ManaBarOverlay.Anchor.Center ? 0 : 50);
      } else {
         return anchor != ManaBarOverlay.Anchor.TopLeft && anchor != ManaBarOverlay.Anchor.BottomLeft ? screenWidth - 20 - 98 : 20;
      }
   }

   private static int getBarY(ManaBarOverlay.Anchor anchor, int screenHeight, ForgeGui gui) {
      if (anchor == ManaBarOverlay.Anchor.XP) {
         return screenHeight - 32 + 3 - 8;
      } else if (anchor == ManaBarOverlay.Anchor.Hunger) {
         return screenHeight - (getAndIncrementRightHeight(gui) - 2) - 10;
      } else if (anchor == ManaBarOverlay.Anchor.Center) {
         return screenHeight - 25 - 27 - 10;
      } else {
         return anchor != ManaBarOverlay.Anchor.TopLeft && anchor != ManaBarOverlay.Anchor.TopRight ? screenHeight - 20 - 21 : 20;
      }
   }

   private static int getAndIncrementRightHeight(ForgeGui gui) {
      int x = gui.rightHeight;
      gui.rightHeight += 10;
      return x;
   }

   public static enum Anchor {
      Hunger,
      XP,
      Center,
      TopLeft,
      TopRight,
      BottomLeft,
      BottomRight;
   }

   public static enum Display {
      Never,
      Always,
      Contextual;
   }
}
