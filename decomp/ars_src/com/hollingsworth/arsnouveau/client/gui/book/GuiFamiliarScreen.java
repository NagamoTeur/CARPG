package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.client.gui.buttons.FamiliarButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketDispelFamiliars;
import com.hollingsworth.arsnouveau.common.network.PacketSummonFamiliar;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiFamiliarScreen extends BaseBook {
   public ArsNouveauAPI api;
   public List<AbstractFamiliarHolder> familiars;
   public Screen parent;

   public GuiFamiliarScreen(ArsNouveauAPI api, List<AbstractFamiliarHolder> familiars, Screen parent) {
      this.api = api;
      this.familiars = familiars;
      this.parent = parent;
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.layoutParts();
   }

   public void layoutParts() {
      int xStart = this.bookLeft + 20;
      int yStart = this.bookTop + 34;
      int PER_ROW = 6;
      int toLayout = Math.min(this.familiars.size(), 30);

      for (int i = 0; i < toLayout; i++) {
         AbstractFamiliarHolder part = this.familiars.get(i);
         int xOffset = 20 * (i % 6);
         int yOffset = i / 6 * 18;
         FamiliarButton cell = new FamiliarButton(this, xStart + xOffset, yStart + yOffset, part);
         this.m_142416_(cell);
      }

      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 71, this.bookBottom - 13, 0, 0, 41, 12, 41, 12, "textures/gui/clear_icon.png", e -> Minecraft.m_91087_().m_91152_(this.parent)
         )
      );
      this.m_142416_(
         new GuiImageButton(
            this.bookRight - 131,
            this.bookBottom - 13,
            0,
            0,
            41,
            12,
            41,
            12,
            "textures/gui/clear_icon.png",
            e -> Networking.sendToServer(new PacketDispelFamiliars())
         )
      );
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/create_paper.png"), 216, 179, 0, 0, 56, 15, 56, 15, stack);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/create_paper.png"), 156, 179, 0, 0, 56, 15, 56, 15, stack);
      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.familiar").getString(), 20.0F, 24.0F, -8355712);
      this.f_96541_.f_91062_.m_92889_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.close"), 232.0F, 183.0F, -8355712);
      this.f_96541_.f_91062_.m_92889_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.dispel"), 172.0F, 183.0F, -8355712);
   }

   public void onGlyphClick(Button button) {
      FamiliarButton button1 = (FamiliarButton)button;
      Networking.INSTANCE.sendToServer(new PacketSummonFamiliar(button1.familiarHolder.getRegistryName()));
      Minecraft.m_91087_().m_91152_(null);
   }
}
