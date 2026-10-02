package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.client.gui.book.GlyphUnlockMenu;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

@OnlyIn(Dist.CLIENT)
public class UnlockGlyphButton extends Button {
   public boolean isCraftingSlot;
   public AbstractSpellPart spellPart;
   private int id;
   public String tooltip = "";
   public GlyphRecipe recipe;
   public boolean playerKnows;
   GlyphUnlockMenu parent;
   public boolean selected;

   public UnlockGlyphButton(GlyphUnlockMenu parent, int x, int y, boolean isCraftingSlot, AbstractSpellPart spellPart) {
      super(x, y, 16, 16, Component.m_130674_(""), parent::onGlyphClick);
      this.parent = parent;
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.f_93618_ = 16;
      this.f_93619_ = 16;
      this.isCraftingSlot = isCraftingSlot;
      this.spellPart = spellPart;
      this.id = 0;
      Recipe recipe = (Recipe)Minecraft.m_91087_().f_91073_.m_7465_().m_44043_(spellPart.getRegistryName()).orElse(null);
      this.recipe = recipe instanceof GlyphRecipe ? (GlyphRecipe)recipe : null;
   }

   public int getId() {
      return this.id;
   }

   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_) {
         if (this.spellPart != null) {
            RenderUtils.drawSpellPart(this.spellPart, ms, this.f_93620_, this.f_93621_, 16, !this.playerKnows);
            if (this.selected) {
               GuiSpellBook.drawFromTexture(
                  new ResourceLocation("ars_nouveau", "textures/gui/glyph_selected.png"), this.f_93620_, this.f_93621_, 0, 0, 16, 16, 16, 16, ms
               );
            }
         }

         if (this.parent.isMouseInRelativeRange(mouseX, mouseY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
            && this.parent.api.getSpellpartMap().containsKey(this.spellPart.getRegistryName())) {
            List<Component> tip = new ArrayList<>();
            AbstractSpellPart spellPart = this.parent.api.getSpellpartMap().get(this.spellPart.getRegistryName());
            tip.add(Component.m_237115_(spellPart.getLocalizationKey()));
            if (Screen.m_96638_()) {
               tip.add(spellPart.getBookDescLang());
            } else {
               tip.add(
                  Component.m_237110_("ars_nouveau.tier", new Object[]{spellPart.getConfigTier().value})
                     .m_130948_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
               );
               tip.add(Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()}));
               String modName = ModList.get()
                  .getModContainerById(spellPart.getRegistryName().m_135827_())
                  .map(ModContainer::getModInfo)
                  .<String>map(IModInfo::getDisplayName)
                  .orElse(spellPart.getRegistryName().m_135827_());
               tip.add(Component.m_237113_(modName).m_130940_(ChatFormatting.BLUE));
            }

            this.parent.tooltip = tip;
            this.parent.hoveredRecipe = this.recipe;
         }
      }
   }
}
