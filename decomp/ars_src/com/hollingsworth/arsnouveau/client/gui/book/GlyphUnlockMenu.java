package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.client.gui.GlyphRecipeTooltip;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.hollingsworth.arsnouveau.client.gui.buttons.CreateSpellButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.GlyphButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.ItemButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.SelectableButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.UnlockGlyphButton;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetScribeRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.client.event.RenderTooltipEvent.Pre;

public class GlyphUnlockMenu extends BaseBook {
   public List<AbstractSpellPart> displayedGlyphs = new ArrayList<>();
   public List<AbstractSpellPart> allParts = new ArrayList<>();
   public int page = 0;
   public PageButton nextButton;
   public PageButton previousButton;
   public List<UnlockGlyphButton> glyphButtons = new ArrayList<>();
   public NoShadowTextField searchBar;
   public String previousString = "";
   int maxPerPage = 78;
   int tier1Row = 0;
   int tier2Row = 0;
   int tier3Row = 0;
   BlockPos scribesPos;
   GlyphUnlockMenu.Filter filterSelected = GlyphUnlockMenu.Filter.ALL;
   public GlyphRecipe hoveredRecipe;
   public GlyphRecipe selectedRecipe;
   String orderingTitle = "";
   List<ItemButton> itemButtons = new ArrayList<>();
   List<SelectableButton> filterButtons = new ArrayList<>();
   SelectableButton all;
   SelectableButton tier1;
   SelectableButton tier2;
   SelectableButton tier3;
   public static Comparator<AbstractSpellPart> COMPARE_TIER_THEN_NAME = COMPARE_GLYPH_BY_TYPE.thenComparingInt(o -> o.getConfigTier().value)
      .thenComparing(AbstractSpellPart::getLocaleName);

   public GlyphUnlockMenu(BlockPos pos) {
      this.allParts = new ArrayList<>(ArsNouveauAPI.getInstance().getSpellpartMap().values().stream().filter(AbstractSpellPart::shouldShowInUnlock).toList());
      this.displayedGlyphs = new ArrayList<>(this.allParts);
      this.scribesPos = pos;
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.orderingTitle = Component.m_237115_("ars_nouveau.all_glyphs").getString();
      this.searchBar = new NoShadowTextField(
         this.f_96541_.f_91062_, this.bookRight - 73, this.bookTop + 2, 54, 12, null, Component.m_237115_("ars_nouveau.spell_book_gui.search")
      );
      this.searchBar.m_94182_(false);
      this.searchBar.m_94202_(12694931);
      this.searchBar.onClear = val -> {
         this.onSearchChanged("", this.filterSelected);
         return null;
      };
      if (this.searchBar.m_94155_().isEmpty()) {
         this.searchBar.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
      }

      this.searchBar.m_94151_(val -> this.onSearchChanged(val, this.filterSelected));
      this.m_142416_(this.searchBar);
      this.m_142416_(new CreateSpellButton(this, this.bookRight - 71, this.bookBottom - 13, this::onSelectClick));
      this.nextButton = (PageButton)this.m_142416_(new PageButton(this.bookRight - 20, this.bookBottom - 10, true, this::onPageIncrease, true));
      this.previousButton = (PageButton)this.m_142416_(new PageButton(this.bookLeft - 5, this.bookBottom - 10, false, this::onPageDec, true));
      this.updateNextPageButtons();
      this.previousButton.f_93623_ = false;
      this.previousButton.f_93624_ = false;
      this.layoutAllGlyphs(0);

      for (int i = 0; i < 10; i++) {
         int offset = i >= 5 ? 14 : 0;
         ItemButton cell = new ItemButton(this, this.bookLeft + 19 + 24 * i + offset, this.bookTop + 194 - 47);
         this.m_142416_(cell);
         this.itemButtons.add(cell);
      }

      this.all = (SelectableButton)new SelectableButton(
            this.bookRight - 8,
            this.bookTop + 22,
            0,
            0,
            23,
            20,
            23,
            20,
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_all.png"),
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_all_selected.png"),
            b -> this.setFilter(GlyphUnlockMenu.Filter.ALL, (SelectableButton)b, Component.m_237115_("ars_nouveau.all_glyphs").getString())
         )
         .withTooltip(this, Component.m_237115_("ars_nouveau.all_glyphs"));
      this.all.isSelected = true;
      this.tier1 = (SelectableButton)new SelectableButton(
            this.bookRight - 8,
            this.bookTop + 46,
            0,
            0,
            23,
            20,
            23,
            20,
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier1.png"),
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier1_selected.png"),
            b -> this.setFilter(GlyphUnlockMenu.Filter.TIER1, (SelectableButton)b, Component.m_237110_("ars_nouveau.tier", new Object[]{1}).getString())
         )
         .withTooltip(this, Component.m_237110_("ars_nouveau.tier", new Object[]{1}));
      this.tier2 = (SelectableButton)new SelectableButton(
            this.bookRight - 8,
            this.bookTop + 70,
            0,
            0,
            23,
            20,
            23,
            20,
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier2.png"),
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier2_selected.png"),
            b -> this.setFilter(GlyphUnlockMenu.Filter.TIER2, (SelectableButton)b, Component.m_237110_("ars_nouveau.tier", new Object[]{2}).getString())
         )
         .withTooltip(this, Component.m_237110_("ars_nouveau.tier", new Object[]{2}));
      this.tier3 = (SelectableButton)new SelectableButton(
            this.bookRight - 8,
            this.bookTop + 94,
            0,
            0,
            23,
            20,
            23,
            20,
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier3.png"),
            new ResourceLocation("ars_nouveau", "textures/gui/filter_tab_tier3_selected.png"),
            b -> this.setFilter(GlyphUnlockMenu.Filter.TIER3, (SelectableButton)b, Component.m_237110_("ars_nouveau.tier", new Object[]{3}).getString())
         )
         .withTooltip(this, Component.m_237110_("ars_nouveau.tier", new Object[]{3}));
      this.filterButtons.add(this.all);
      this.filterButtons.add(this.tier2);
      this.filterButtons.add(this.tier1);
      this.filterButtons.add(this.tier3);

      for (SelectableButton button : this.filterButtons) {
         this.m_142416_(button);
      }
   }

   public void setFilter(GlyphUnlockMenu.Filter filter, SelectableButton button, String displayTitle) {
      this.displayedGlyphs = this.allParts;

      for (SelectableButton b : this.filterButtons) {
         b.isSelected = false;
      }

      this.filterSelected = filter;
      button.isSelected = true;
      this.orderingTitle = displayTitle;
      this.onSearchChanged(this.searchBar.f_94093_, this.filterSelected);
      this.resetPageState();
   }

   private void onSelectClick(Button button) {
      if (this.selectedRecipe != null) {
         Networking.INSTANCE.sendToServer(new PacketSetScribeRecipe(this.scribesPos, this.selectedRecipe.id));
         Minecraft.m_91087_().m_91152_(null);
      }
   }

   public void updateNextPageButtons() {
      if (this.displayedGlyphs.size() < this.maxPerPage) {
         this.nextButton.f_93624_ = false;
         this.nextButton.f_93623_ = false;
      } else {
         this.nextButton.f_93624_ = true;
         this.nextButton.f_93623_ = true;
      }
   }

   public static void open(BlockPos scribePos) {
      Minecraft.m_91087_().m_91152_(new GlyphUnlockMenu(scribePos));
   }

   public void onSearchChanged(String str, GlyphUnlockMenu.Filter filter) {
      this.previousString = str;
      if (!str.isEmpty()) {
         this.searchBar.m_94167_("");
         this.displayedGlyphs = new ArrayList<>();

         for (AbstractSpellPart spellPart : this.allParts) {
            if (spellPart.getLocaleName().toLowerCase().contains(this.searchBar.f_94093_.toLowerCase())) {
               this.displayedGlyphs.add(spellPart);
            }
         }

         for (Widget w : this.f_169369_) {
            if (w instanceof GlyphButton) {
               GlyphButton glyphButton = (GlyphButton)w;
               if (glyphButton.abstractSpellPart.getRegistryName() != null) {
                  AbstractSpellPart part = this.api.getSpellpartMap().get(glyphButton.abstractSpellPart.getRegistryName());
                  if (part != null) {
                     glyphButton.f_93624_ = part.getLocaleName().toLowerCase().contains(this.searchBar.f_94093_.toLowerCase());
                  }
               }
            }
         }
      } else {
         this.searchBar.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
         this.displayedGlyphs = this.allParts;

         for (Widget wx : this.f_169369_) {
            if (wx instanceof GlyphButton) {
               ((GlyphButton)wx).f_93624_ = true;
            }
         }
      }

      this.displayedGlyphs = this.applyFilter(this.displayedGlyphs);
      this.resetPageState();
   }

   public void resetPageState() {
      this.updateNextPageButtons();
      this.page = 0;
      this.previousButton.f_93623_ = false;
      this.previousButton.f_93624_ = false;
      this.layoutAllGlyphs(this.page);
   }

   public void layoutAllGlyphs(int page) {
      this.clearButtons(this.glyphButtons);
      this.tier1Row = -1;
      this.tier2Row = -1;
      this.tier3Row = -1;
      int PER_ROW = 6;
      int MAX_ROWS = 6;
      boolean nextPage = false;
      int xStart = nextPage ? this.bookLeft + 154 : this.bookLeft + 20;
      int adjustedRowsPlaced = 0;
      int yStart = this.bookTop + 20;
      List<AbstractSpellPart> sorted = new ArrayList<>(this.displayedGlyphs);
      sorted.sort(COMPARE_TIER_THEN_NAME);
      sorted = sorted.subList(this.maxPerPage * page, Math.min(sorted.size(), this.maxPerPage * (page + 1)));
      int adjustedXPlaced = 0;
      this.tier1Row = 0;
      adjustedRowsPlaced++;

      for (int i = 0; i < sorted.size(); i++) {
         AbstractSpellPart part = sorted.get(i);
         if (adjustedXPlaced >= 6) {
            adjustedRowsPlaced++;
            adjustedXPlaced = 0;
         }

         if (adjustedRowsPlaced > 6) {
            if (nextPage) {
               break;
            }

            nextPage = true;
            adjustedXPlaced = 0;
            adjustedRowsPlaced = 0;
         }

         int xOffset = 20 * (adjustedXPlaced % 6) + (nextPage ? 134 : 0);
         int yPlace = adjustedRowsPlaced * 18 + yStart;
         UnlockGlyphButton cell = new UnlockGlyphButton(this, xStart + xOffset, yPlace, false, part);
         IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(Minecraft.m_91087_().f_91074_).orElse(null);
         if (cap != null && (cap.knowsGlyph(part) || this.api.getDefaultStartingSpells().contains(part))) {
            cell.playerKnows = true;
         }

         this.m_142416_(cell);
         this.glyphButtons.add(cell);
         adjustedXPlaced++;
      }
   }

   public List<AbstractSpellPart> applyFilter(List<AbstractSpellPart> spellParts) {
      if (this.filterSelected == GlyphUnlockMenu.Filter.ALL) {
         return spellParts;
      } else if (this.filterSelected == GlyphUnlockMenu.Filter.TIER1) {
         return spellParts.stream().filter(a -> a.getConfigTier().value == 1).collect(Collectors.toList());
      } else {
         return this.filterSelected == GlyphUnlockMenu.Filter.TIER2
            ? spellParts.stream().filter(a -> a.getConfigTier().value == 2).collect(Collectors.toList())
            : spellParts.stream().filter(a -> a.getConfigTier().value == 3).collect(Collectors.toList());
      }
   }

   public void onGlyphClick(Button button) {
      for (ItemButton itemButton : this.itemButtons) {
         itemButton.f_93624_ = false;
         itemButton.ingredient = Ingredient.f_43901_;
      }

      for (UnlockGlyphButton button1 : this.glyphButtons) {
         button1.selected = false;
      }

      if (button instanceof UnlockGlyphButton unlockGlyphButton) {
         this.selectedRecipe = unlockGlyphButton.recipe;
         unlockGlyphButton.selected = true;
         if (this.selectedRecipe == null) {
            return;
         }

         for (int i = 0; i < this.selectedRecipe.inputs.size() && i <= this.itemButtons.size(); i++) {
            this.itemButtons.get(i).f_93624_ = true;
            this.itemButtons.get(i).ingredient = this.selectedRecipe.inputs.get(i);
         }
      }
   }

   public void clearButtons(List<UnlockGlyphButton> glyphButtons) {
      for (UnlockGlyphButton b : glyphButtons) {
         this.f_169369_.remove(b);
         this.m_6702_().remove(b);
      }

      glyphButtons.clear();
   }

   public void onPageIncrease(Button button) {
      this.page++;
      if (this.displayedGlyphs.size() < this.maxPerPage * (this.page + 1)) {
         this.nextButton.f_93624_ = false;
         this.nextButton.f_93623_ = false;
      }

      this.previousButton.f_93623_ = true;
      this.previousButton.f_93624_ = true;
      this.layoutAllGlyphs(this.page);
   }

   public void onPageDec(Button button) {
      this.page--;
      if (this.page == 0) {
         this.previousButton.f_93623_ = false;
         this.previousButton.f_93624_ = false;
      }

      if (this.displayedGlyphs.size() > this.maxPerPage * (this.page + 1)) {
         this.nextButton.f_93624_ = true;
         this.nextButton.f_93623_ = true;
      }

      this.layoutAllGlyphs(this.page);
   }

   @Override
   public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.hoveredRecipe = null;
      super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      this.f_96541_
         .f_91062_
         .m_92883_(stack, this.orderingTitle, this.tier1Row > 7 ? 154.0F : 20.0F, (float)(5 + 18 * (this.tier1Row + (this.tier1Row == 1 ? 0 : 1))), -8355712);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/create_paper.png"), 216, 179, 0, 0, 56, 15, 56, 15, stack);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/search_paper.png"), 203, 0, 0, 0, 72, 15, 72, 15, stack);
      this.f_96541_.f_91062_.m_92889_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.select"), 233.0F, 183.0F, -8355712);
   }

   @Override
   public void drawTooltip(PoseStack stack, int mouseX, int mouseY) {
      if (this.tooltip != null && !this.tooltip.isEmpty()) {
         if (this.hoveredRecipe != null) {
            MutableComponent component = Component.m_237110_("ars_nouveau.levels_required", new Object[]{ScribesTile.getLevelsFromExp(this.hoveredRecipe.exp)})
               .m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GREEN));
            this.tooltip.add(component);
         }

         List<ClientTooltipComponent> components = new ArrayList<>(
            ForgeHooksClient.gatherTooltipComponents(ItemStack.f_41583_, this.tooltip, mouseX, this.f_96543_, this.f_96544_, this.f_96547_, this.f_96547_)
         );
         if (this.hoveredRecipe != null) {
            components.add(new GlyphRecipeTooltip(this.hoveredRecipe.inputs));
         }

         this.m_169383_(stack, components, mouseX, mouseY);
      }
   }

   public void m_169383_(PoseStack pPoseStack, List<ClientTooltipComponent> pClientTooltipComponents, int pMouseX, int pMouseY) {
      if (!pClientTooltipComponents.isEmpty()) {
         Pre preEvent = ForgeHooksClient.onRenderTooltipPre(
            ItemStack.f_41583_, pPoseStack, pMouseX, pMouseY, this.f_96543_, this.f_96544_, pClientTooltipComponents, this.f_96547_, this.f_96547_
         );
         if (preEvent.isCanceled()) {
            return;
         }

         int i = 0;
         int j = pClientTooltipComponents.size() == 1 ? -2 : 0;

         for (ClientTooltipComponent clienttooltipcomponent : pClientTooltipComponents) {
            int k = clienttooltipcomponent.m_142069_(preEvent.getFont());
            if (k > i) {
               i = k;
            }

            j += clienttooltipcomponent.m_142103_();
         }

         int j2 = preEvent.getX() + 12;
         int k2 = preEvent.getY() - 12;
         if (j2 + i > this.f_96543_) {
            j2 -= 28 + i;
         }

         if (k2 + j + 6 > this.f_96544_) {
            k2 = this.f_96544_ - j - 6;
         }

         pPoseStack.m_85836_();
         float f = this.f_96542_.f_115093_;
         this.f_96542_.f_115093_ = 400.0F;
         Tesselator tesselator = Tesselator.m_85913_();
         BufferBuilder bufferbuilder = tesselator.m_85915_();
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
         Matrix4f matrix4f = pPoseStack.m_85850_().m_85861_();
         Color colorEvent = ForgeHooksClient.onRenderTooltipColor(ItemStack.f_41583_, pPoseStack, j2, k2, preEvent.getFont(), pClientTooltipComponents);
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 4, j2 + i + 3, k2 - 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundStart());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 + j + 3, j2 + i + 3, k2 + j + 4, 400, colorEvent.getBackgroundEnd(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 4, k2 - 3, j2 - 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 + i + 3, k2 - 3, j2 + i + 4, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
         m_93123_(matrix4f, bufferbuilder, j2 + i + 2, k2 - 3 + 1, j2 + i + 3, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 - 3 + 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderStart());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 + j + 2, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBorderEnd(), colorEvent.getBorderEnd());
         RenderSystem.m_69482_();
         RenderSystem.m_69472_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         BufferUploader.m_231202_(bufferbuilder.m_231175_());
         RenderSystem.m_69461_();
         RenderSystem.m_69493_();
         BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
         pPoseStack.m_85837_(0.0, 0.0, 400.0);
         int l1 = k2;

         for (int i2 = 0; i2 < pClientTooltipComponents.size(); i2++) {
            ClientTooltipComponent clienttooltipcomponent1 = pClientTooltipComponents.get(i2);
            clienttooltipcomponent1.m_142440_(preEvent.getFont(), j2, l1, matrix4f, multibuffersource$buffersource);
            l1 += clienttooltipcomponent1.m_142103_() + (i2 == 0 ? 2 : 0);
         }

         multibuffersource$buffersource.m_109911_();
         pPoseStack.m_85849_();
         l1 = k2;

         for (int l2 = 0; l2 < pClientTooltipComponents.size(); l2++) {
            ClientTooltipComponent clienttooltipcomponent2 = pClientTooltipComponents.get(l2);
            clienttooltipcomponent2.m_183452_(preEvent.getFont(), j2, l1, pPoseStack, this.f_96542_, 400);
            l1 += clienttooltipcomponent2.m_142103_() + (l2 == 0 ? 2 : 0);
         }

         this.f_96542_.f_115093_ = f;
      }
   }

   static enum Filter {
      ALL,
      TIER1,
      TIER2,
      TIER3;
   }
}
