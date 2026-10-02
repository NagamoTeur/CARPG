package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiSpellSlot extends GuiImageButton {
   public int slotNum;
   public boolean isSelected;

   public GuiSpellSlot(GuiSpellBook parent, int x, int y, int slotNum) {
      super(x, y, 0, 0, 18, 13, 18, 13, "textures/gui/spell_tab.png", parent::onSlotChange);
      this.parent = parent;
      this.slotNum = slotNum;
      this.isSelected = false;
   }

   @Override
   public void m_6305_(PoseStack stack, int parX, int parY, float partialTicks) {
      if (this.f_93624_) {
         if (this.parent.isMouseInRelativeRange(parX, parY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)) {
            ISpellCaster caster = CasterUtil.getCaster(((GuiSpellBook)this.parent).bookStack);
            String name = caster.getSpellName(this.slotNum);
            if (!name.isEmpty()) {
               List<Component> tip = new ArrayList<>();
               tip.add(Component.m_237113_(name));
               this.parent.tooltip = tip;
            }
         }

         ResourceLocation image = this.isSelected
            ? new ResourceLocation("ars_nouveau", "textures/gui/spell_tab_selected.png")
            : new ResourceLocation("ars_nouveau", "textures/gui/spell_tab.png");
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         GuiSpellBook.drawFromTexture(
            image, this.f_93620_, this.f_93621_, this.u, this.v, this.f_93618_, this.f_93619_, this.image_width, this.image_height, stack
         );
         m_93208_(stack, Minecraft.m_91087_().f_91062_, String.valueOf(this.slotNum + 1), this.f_93620_ + 8, this.f_93621_ + 3, 16777215);
      }
   }
}
