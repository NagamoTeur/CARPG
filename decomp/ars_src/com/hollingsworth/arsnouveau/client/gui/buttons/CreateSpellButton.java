package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.client.gui.book.BaseBook;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CreateSpellButton extends GuiImageButton {
   private final ResourceLocation image = new ResourceLocation("ars_nouveau", "textures/gui/create_icon.png");

   public CreateSpellButton(BaseBook parent, int x, int y, OnPress onPress) {
      super(x, y, 0, 0, 50, 12, 50, 12, "textures/gui/create_icon.png", onPress);
      this.parent = parent;
   }

   @Override
   public void m_6305_(PoseStack ms, int parX, int parY, float partialTicks) {
      if (this.f_93624_) {
         if (this.parent.validationErrors.isEmpty()) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         } else {
            RenderSystem.m_157429_(1.0F, 0.7F, 0.7F, 1.0F);
         }

         GuiSpellBook.drawFromTexture(
            this.image, this.f_93620_, this.f_93621_, this.u, this.v, this.f_93618_, this.f_93619_, this.image_width, this.image_height, ms
         );
         if (this.parent.isMouseInRelativeRange(parX, parY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
            && !this.parent.validationErrors.isEmpty()) {
            List<Component> tooltip = new ArrayList<>();
            boolean foundGlyphErrors = false;
            tooltip.add(Component.m_237115_("ars_nouveau.spell.validation.crafting.invalid").m_130940_(ChatFormatting.RED));

            for (SpellValidationError error : this.parent.validationErrors) {
               if (error.getPosition() < 0) {
                  tooltip.add(error.makeTextComponentExisting());
               } else {
                  foundGlyphErrors = true;
               }
            }

            if (foundGlyphErrors) {
               tooltip.add(Component.m_237115_("ars_nouveau.spell.validation.crafting.invalid_glyphs"));
            }

            this.parent.tooltip = tooltip;
         }
      }
   }
}
