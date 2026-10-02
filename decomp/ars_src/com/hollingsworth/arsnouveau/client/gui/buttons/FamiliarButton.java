package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.client.gui.book.GuiFamiliarScreen;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FamiliarButton extends Button {
   public GuiFamiliarScreen parent;
   public AbstractFamiliarHolder familiarHolder;

   public FamiliarButton(GuiFamiliarScreen parent, int x, int y, AbstractFamiliarHolder familiar) {
      super(x, y, 16, 16, Component.m_130674_(""), parent::onGlyphClick);
      this.parent = parent;
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.f_93618_ = 16;
      this.f_93619_ = 16;
      this.familiarHolder = familiar;
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_) {
         RenderUtils.drawItemAsIcon(this.familiarHolder.getOutputItem().m_41720_(), ms, this.f_93620_, this.f_93621_, 16, false);
         if (this.parent.isMouseInRelativeRange(mouseX, mouseY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)) {
            List<Component> tip = new ArrayList<>();
            if (Screen.m_96638_()) {
               tip.add(this.familiarHolder.getLangDescription());
            } else {
               tip.add(this.familiarHolder.getLangName());
               tip.add(Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()}));
            }

            this.parent.tooltip = tip;
         }
      }
   }
}
