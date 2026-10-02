package io.redspace.ironsspellbooks.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class CastBarOverlay extends GuiComponent {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/icons.png");
   static final int IMAGE_WIDTH = 54;
   static final int COMPLETION_BAR_WIDTH = 44;
   static final int IMAGE_HEIGHT = 21;

   public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
      if (ClientMagicData.isCasting() && (!ClientMagicData.isCasting() || ClientMagicData.getCastType() != CastType.INSTANT)) {
         float castCompletionPercent = ClientMagicData.getCastCompletionPercent();
         String castTimeString = Utils.timeFromTicks((1.0F - castCompletionPercent) * (float)ClientMagicData.getCastDuration(), 1);
         if (ClientMagicData.getCastType() == CastType.CONTINUOUS) {
            castCompletionPercent = 1.0F - castCompletionPercent;
         }

         int barX = screenWidth / 2 - 27;
         int barY = screenHeight / 2 + screenHeight / 8;
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, TEXTURE);
         ForgeGui.m_93133_(poseStack, barX, barY, 0.0F, 42.0F, 54, 21, 256, 256);
         gui.m_93228_(poseStack, barX, barY, 0, 63, (int)(44.0F * castCompletionPercent + 5.0F), 21);
         ChatFormatting textColor = ChatFormatting.WHITE;
         Font font = gui.m_93082_();
         int textX = barX + (54 - font.m_92895_(castTimeString)) / 2;
         int textY = barY + 10 - 9 / 2 + 1;
         gui.m_93082_().m_92883_(poseStack, castTimeString, (float)textX, (float)textY, textColor.m_126665_());
      }
   }
}
