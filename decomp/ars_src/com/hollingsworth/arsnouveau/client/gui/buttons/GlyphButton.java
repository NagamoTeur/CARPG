package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

@OnlyIn(Dist.CLIENT)
public class GlyphButton extends Button {
   private final int id;
   public boolean isCraftingSlot;
   public AbstractSpellPart abstractSpellPart;
   public String tooltip = "tooltip";
   public List<SpellValidationError> validationErrors;
   GuiSpellBook parent;

   public GlyphButton(GuiSpellBook parent, int x, int y, boolean isCraftingSlot, AbstractSpellPart abstractSpellPart) {
      super(x, y, 16, 16, Component.m_130674_(""), parent::onGlyphClick);
      this.parent = parent;
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.f_93618_ = 16;
      this.f_93619_ = 16;
      this.isCraftingSlot = isCraftingSlot;
      this.abstractSpellPart = abstractSpellPart;
      this.id = 0;
      this.validationErrors = new LinkedList<>();
   }

   public int getId() {
      return this.id;
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_) {
         RenderUtils.drawSpellPart(this.abstractSpellPart, ms, this.f_93620_, this.f_93621_, 16, !this.validationErrors.isEmpty());
         if (this.parent.isMouseInRelativeRange(mouseX, mouseY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
            && this.parent.api.getSpellpartMap().containsKey(this.abstractSpellPart.getRegistryName())) {
            List<Component> tip = new ArrayList<>();
            AbstractSpellPart spellPart = this.parent.api.getSpellpartMap().get(this.abstractSpellPart.getRegistryName());
            tip.add(Component.m_237115_(spellPart.getLocalizationKey()));

            for (SpellValidationError ve : this.validationErrors) {
               tip.add(ve.makeTextComponentAdding().m_130940_(ChatFormatting.RED));
            }

            if (!Screen.m_96638_()) {
               tip.add(Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()}));
               String modName = ModList.get()
                  .getModContainerById(spellPart.getRegistryName().m_135827_())
                  .map(ModContainer::getModInfo)
                  .<String>map(IModInfo::getDisplayName)
                  .orElse(spellPart.getRegistryName().m_135827_());
               tip.add(Component.m_237113_(modName).m_130940_(ChatFormatting.BLUE));
            } else {
               tip.add(
                  Component.m_237110_("tooltip.ars_nouveau.glyph_level", new Object[]{spellPart.getConfigTier().value})
                     .m_6270_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
               );
               tip.add(Component.m_237115_("ars_nouveau.schools"));

               for (SpellSchool s : spellPart.spellSchools) {
                  tip.add(s.getTextComponent());
               }

               tip.add(spellPart.getBookDescLang());
            }

            this.parent.tooltip = tip;
         }
      }
   }
}
