package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CraftingButton extends GuiImageButton {
   int slotNum;
   public ResourceLocation spellTag;
   public AbstractSpellPart abstractSpellPart;
   public List<SpellValidationError> validationErrors;

   public CraftingButton(GuiSpellBook parent, int x, int y, int slotNum, OnPress onPress) {
      super(x, y, 0, 0, 22, 20, 22, 20, "textures/gui/spell_glyph_slot.png", onPress);
      this.slotNum = slotNum;
      this.spellTag = ArsNouveauAPI.EMPTY_KEY;
      this.resourceIcon = "";
      this.validationErrors = new LinkedList<>();
      this.parent = parent;
   }

   public void clear() {
      this.spellTag = ArsNouveauAPI.EMPTY_KEY;
      this.resourceIcon = "";
      this.validationErrors.clear();
      this.abstractSpellPart = null;
   }

   @Override
   public void m_6305_(PoseStack ms, int parX, int parY, float partialTicks) {
      if (this.f_93624_) {
         if (this.validationErrors.isEmpty()) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         } else {
            RenderSystem.m_157429_(1.0F, 0.7F, 0.7F, 1.0F);
         }

         if (this.abstractSpellPart != null) {
            RenderUtils.drawSpellPart(this.abstractSpellPart, ms, this.f_93620_ + 3, this.f_93621_ + 2, 16, !this.validationErrors.isEmpty());
         }

         if (this.parent.isMouseInRelativeRange(parX, parY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
            && this.parent.api.getSpellpartMap().containsKey(this.spellTag)) {
            List<Component> tooltip = new LinkedList<>();
            tooltip.add(Component.m_237115_(this.parent.api.getSpellpartMap().get(this.spellTag).getLocalizationKey()));

            for (SpellValidationError ve : this.validationErrors) {
               tooltip.add(ve.makeTextComponentExisting().m_130940_(ChatFormatting.RED));
            }

            this.parent.tooltip = tooltip;
         }
      }

      super.m_6305_(ms, parX, parY, partialTicks);
   }
}
