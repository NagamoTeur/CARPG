package com.hollingsworth.arsnouveau.client.gui;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiManaHUD extends GuiComponent {
   public static final IGuiOverlay OVERLAY = GuiManaHUD::renderOverlay;
   private static final Minecraft minecraft = Minecraft.m_91087_();
   public static final Color BLACK = new Color(35, 35, 35).setImmutable();
   static boolean stillBar = true;

   public static boolean shouldDisplayBar() {
      ItemStack mainHand = minecraft.f_91074_.m_21205_();
      ItemStack offHand = minecraft.f_91074_.m_21206_();
      return mainHand.m_41720_() instanceof IDisplayMana iDisplayMana && iDisplayMana.shouldDisplay(mainHand)
         || offHand.m_41720_() instanceof IDisplayMana iDisplayManaOffhand && iDisplayManaOffhand.shouldDisplay(offHand)
         || (double)ManaUtil.getMaxMana(minecraft.f_91074_) > ManaUtil.getCurrentMana(minecraft.f_91074_);
   }

   public static void renderOverlay(ForgeGui gui, PoseStack ms, float pt, int width, int height) {
      if (shouldDisplayBar()) {
         IManaCap mana = (IManaCap)CapabilityRegistry.getMana(minecraft.f_91074_).orElse(null);
         if (mana != null) {
            int maxMana = mana.getMaxMana();
            if (maxMana != 0) {
               int offsetLeft = 10 + (Integer)Config.MANABAR_X_OFFSET.get();
               int manaLength = 96;
               manaLength = (int)((double)manaLength * (mana.getCurrentMana() / ((double)maxMana * (1.0 + (double)ClientInfo.reservedOverlayMana))));
               int yOffset = minecraft.m_91268_().m_85446_() - 5 + (Integer)Config.MANABAR_Y_OFFSET.get();
               RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_border.png"));
               m_93133_(ms, offsetLeft, yOffset - 18, 0.0F, 0.0F, 108, 18, 256, 256);
               int manaOffset = (int)(((float)ClientInfo.ticksInGame + pt) / 3.0F % 33.0F) * 6;
               RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_mana.png"));
               m_93133_(ms, offsetLeft + 9, yOffset - 9, 0.0F, (float)manaOffset, manaLength, 6, 256, 256);
               renderReserveOverlay(ms, offsetLeft, yOffset, manaOffset, maxMana);
               renderRedOverlay(ms, offsetLeft, yOffset, manaOffset, maxMana);
               if (ArsNouveauAPI.ENABLE_DEBUG_NUMBERS) {
                  String text = (int)mana.getCurrentMana() + "  /  " + maxMana;
                  int maxWidth = minecraft.f_91062_.m_92895_(maxMana + "  /  " + maxMana);
                  int offset = 67 - maxWidth / 2 + (maxWidth - minecraft.f_91062_.m_92895_(text));
                  m_93236_(ms, minecraft.f_91062_, text, offset, yOffset - 10, 16777215);
                  m_93236_(ms, minecraft.f_91062_, String.valueOf((int)(ClientInfo.reservedOverlayMana * (float)maxMana)), offset + 69, yOffset - 20, 16777215);
               }

               RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_border.png"));
               m_93133_(ms, offsetLeft, yOffset - 17, 0.0F, 18.0F, 108, 20, 256, 256);
            }
         }
      }
   }

   public static void renderRedOverlay(PoseStack ms, int offsetLeft, int yOffset, int manaOffset, int maxMana) {
      if (ClientInfo.redTicks()) {
         int redManaLength = (int)(98.0F * Mth.m_14036_(0.0F, ClientInfo.redOverlayMana / (float)maxMana, 1.0F));
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_grayscale.png"));
         RenderUtils.colorBlit(
            ms, offsetLeft + 8, yOffset - 10, 0, manaOffset, redManaLength, 8, 256, 256, Color.RED.scaleAlpha((float)ClientInfo.redOverlayTicks / 35.0F)
         );
      }
   }

   public static void renderReserveOverlay(PoseStack ms, int offsetLeft, int yOffset, int manaOffset, int maxMana) {
      if (!(ClientInfo.reservedOverlayMana <= 0.0F)) {
         int reserveManaLength = (int)(96.0F * ClientInfo.reservedOverlayMana);
         int offset = 96 - reserveManaLength;
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_mana.png"));
         RenderUtils.colorBlit(ms, offsetLeft + 10 + offset, yOffset - 10, 0, stillBar ? 0 : manaOffset, reserveManaLength, 8, 256, 256, BLACK);
      }
   }
}
