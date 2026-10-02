package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.sound.ConfiguredSpellSound;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.Color;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.hollingsworth.arsnouveau.client.gui.buttons.CraftingButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.CreateSpellButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.GlyphButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.GuiSpellSlot;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketUpdateCaster;
import com.hollingsworth.arsnouveau.common.spell.validation.CombinedSpellValidator;
import com.hollingsworth.arsnouveau.common.spell.validation.GlyphMaxTierValidator;
import com.hollingsworth.arsnouveau.common.util.GuiUtils;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

public class GuiSpellBook extends BaseBook {
   public int numLinks = 10;
   public ArsNouveauAPI api = ArsNouveauAPI.getInstance();
   public int selectedSpellSlot = 0;
   public EditBox spell_name;
   public NoShadowTextField searchBar;
   public GuiSpellSlot selected_slot;
   public List<CraftingButton> craftingCells = new ArrayList<>();
   public List<AbstractSpellPart> unlockedSpells;
   public List<AbstractSpellPart> displayedGlyphs;
   public List<GlyphButton> glyphButtons = new ArrayList<>();
   public int page = 0;
   public PageButton nextButton;
   public PageButton previousButton;
   public ISpellValidator spellValidator;
   public String previousString = "";
   public ItemStack bookStack;
   public int formTextRow = 0;
   public int augmentTextRow = 0;
   public int effectTextRow = 0;
   public int glyphsPerPage = 58;
   public InteractionHand hand;
   public int maxManaCache = 0;
   int currentCostCache = 0;
   public boolean setFocusOnLoad = true;
   public Widget hoveredWidget = null;

   @Deprecated(
      forRemoval = true
   )
   public GuiSpellBook(ItemStack bookStack, int tier, List<AbstractSpellPart> unlockedSpells) {
      this(InteractionHand.MAIN_HAND);
   }

   public GuiSpellBook(InteractionHand hand) {
      this.hand = hand;
      IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(Minecraft.m_91087_().f_91074_).orElse(null);
      ItemStack heldStack = Minecraft.m_91087_().f_91074_.m_21120_(hand);
      List<AbstractSpellPart> parts = cap == null
         ? new ArrayList<>()
         : new ArrayList<>(cap.getKnownGlyphs().stream().filter(AbstractSpellPart::shouldShowInSpellBook).toList());
      this.maxManaCache = ManaUtil.getMaxMana(Minecraft.m_91087_().f_91074_);
      parts.addAll(this.api.getDefaultStartingSpells());
      if (heldStack.m_41720_() == ItemsRegistry.CREATIVE_SPELLBOOK.get()) {
         parts = new ArrayList<>(ArsNouveauAPI.getInstance().getSpellpartMap().values().stream().filter(AbstractSpellPart::shouldShowInSpellBook).toList());
      }

      int tier = 1;
      if (heldStack.m_41720_() instanceof SpellBook book) {
         tier = book.getTier().value;
      }

      this.bookStack = heldStack;
      this.unlockedSpells = parts;
      this.displayedGlyphs = new ArrayList<>(this.unlockedSpells);
      this.validationErrors = new LinkedList<>();
      this.spellValidator = new CombinedSpellValidator(this.api.getSpellCraftingSpellValidator(), new GlyphMaxTierValidator(tier));
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      ISpellCaster caster = CasterUtil.getCaster(this.bookStack);
      int selectedSlot = caster.getCurrentSlot();

      for (int i = 0; i < this.numLinks; i++) {
         int offset = i >= 5 ? 14 : 0;
         CraftingButton cell = new CraftingButton(this, this.bookLeft + 19 + 24 * i + offset, this.bookTop + 194 - 47, i, this::onCraftingSlotClick);
         this.m_142416_(cell);
         this.craftingCells.add(cell);
      }

      this.updateCraftingSlots(selectedSlot);
      this.layoutAllGlyphs(0);
      this.m_142416_(new CreateSpellButton(this, this.bookRight - 71, this.bookBottom - 13, this::onCreateClick));
      this.m_142416_(new GuiImageButton(this.bookRight - 126, this.bookBottom - 13, 0, 0, 41, 12, 41, 12, "textures/gui/clear_icon.png", this::clear));
      this.spell_name = new NoShadowTextField(
         this.f_96541_.f_91062_, this.bookLeft + 32, this.bookTop + 194 - 11, 88, 12, null, Component.m_237115_("ars_nouveau.spell_book_gui.spell_name")
      );
      this.spell_name.m_94182_(false);
      this.spell_name.m_94202_(12694931);
      this.searchBar = new NoShadowTextField(
         this.f_96541_.f_91062_, this.bookRight - 73, this.bookTop + 2, 54, 12, null, Component.m_237115_("ars_nouveau.spell_book_gui.search")
      );
      this.searchBar.m_94182_(false);
      this.searchBar.m_94202_(12694931);
      this.searchBar.onClear = val -> {
         this.onSearchChanged("");
         return null;
      };
      this.spell_name.m_94144_(caster.getSpellName(caster.getCurrentSlot()));
      if (this.spell_name.m_94155_().isEmpty()) {
         this.spell_name.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.spell_name").getString());
      }

      if (this.searchBar.m_94155_().isEmpty()) {
         this.searchBar.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
      }

      this.searchBar.m_94151_(this::onSearchChanged);
      this.m_142416_(this.spell_name);
      this.m_142416_(this.searchBar);

      for (int i = 0; i < 10; i++) {
         GuiSpellSlot slot = new GuiSpellSlot(this, this.bookLeft + 281, this.bookTop + 1 + 15 * (i + 1), i);
         if (i == selectedSlot) {
            this.selected_slot = slot;
            this.selectedSpellSlot = i;
            slot.isSelected = true;
         }

         this.m_142416_(slot);
      }

      this.m_142416_(
         new GuiImageButton(this.bookLeft - 15, this.bookTop + 22, 0, 0, 23, 20, 23, 20, "textures/gui/worn_book_bookmark.png", this::onDocumentationClick)
            .withTooltip(this, Component.m_237115_("ars_nouveau.gui.notebook"))
      );
      this.m_142416_(
         new GuiImageButton(this.bookLeft - 15, this.bookTop + 46, 0, 0, 23, 20, 23, 20, "textures/gui/color_wheel_bookmark.png", this::onColorClick)
            .withTooltip(this, Component.m_237115_("ars_nouveau.gui.color"))
      );
      this.m_142416_(
         new GuiImageButton(this.bookLeft - 15, this.bookTop + 70, 0, 0, 23, 20, 23, 20, "textures/gui/summon_circle_bookmark.png", this::onFamiliarClick)
            .withTooltip(this, Component.m_237115_("ars_nouveau.gui.familiar"))
      );
      this.m_142416_(
         new GuiImageButton(this.bookLeft - 15, this.bookTop + 94, 0, 0, 23, 20, 23, 20, "textures/gui/sounds_tab.png", this::onSoundsClick)
            .withTooltip(this, Component.m_237115_("ars_nouveau.gui.sounds"))
      );
      this.m_142416_(
         new GuiImageButton(
               this.bookLeft - 15,
               this.bookTop + 118,
               0,
               0,
               23,
               20,
               23,
               20,
               "textures/gui/settings_tab.png",
               b -> Minecraft.m_91087_().m_91152_(new GuiSettingsScreen(this))
            )
            .withTooltip(this, Component.m_237115_("ars_nouveau.gui.settings"))
      );
      this.m_142416_(new GuiImageButton(this.bookLeft - 15, this.bookTop + 142, 0, 0, 23, 20, 23, 20, "textures/gui/discord_tab.png", b -> {
         try {
            Util.m_137581_().m_137648_(new URI("https://discord.com/invite/y7TMXZu"));
         } catch (URISyntaxException var2x) {
            throw new RuntimeException(var2x);
         }
      }).withTooltip(this, Component.m_237115_("ars_nouveau.gui.discord")));
      this.nextButton = (PageButton)this.m_142416_(new PageButton(this.bookRight - 20, this.bookBottom - 10, true, this::onPageIncrease, true));
      this.previousButton = (PageButton)this.m_142416_(new PageButton(this.bookLeft - 5, this.bookBottom - 10, false, this::onPageDec, true));
      this.updateNextPageButtons();
      this.previousButton.f_93623_ = false;
      this.previousButton.f_93624_ = false;
      this.validate();
   }

   public int getNumPages() {
      return (int)Math.ceil((double)this.displayedGlyphs.size() / 58.0);
   }

   private void layoutAllGlyphs(int page) {
      this.clearButtons(this.glyphButtons);
      this.formTextRow = 0;
      this.augmentTextRow = 0;
      this.effectTextRow = 0;
      int PER_ROW = 6;
      int MAX_ROWS = 6;
      boolean nextPage = false;
      int xStart = nextPage ? this.bookLeft + 154 : this.bookLeft + 20;
      int adjustedRowsPlaced = 0;
      int yStart = this.bookTop + 20;
      boolean foundForms = false;
      boolean foundAugments = false;
      boolean foundEffects = false;
      List<AbstractSpellPart> sorted = new ArrayList<>();
      sorted.addAll(this.displayedGlyphs.stream().filter(s -> s instanceof AbstractCastMethod).toList());
      sorted.addAll(this.displayedGlyphs.stream().filter(s -> s instanceof AbstractAugment).toList());
      sorted.addAll(this.displayedGlyphs.stream().filter(s -> s instanceof AbstractEffect).toList());
      sorted.sort(COMPARE_TYPE_THEN_NAME);
      sorted = sorted.subList(this.glyphsPerPage * page, Math.min(sorted.size(), this.glyphsPerPage * (page + 1)));
      int adjustedXPlaced = 0;
      int totalRowsPlaced = 0;
      int row_offset = page == 0 ? 2 : 0;

      for (int i = 0; i < sorted.size(); i++) {
         AbstractSpellPart part = sorted.get(i);
         if (!foundForms && part instanceof AbstractCastMethod) {
            foundForms = true;
            adjustedRowsPlaced++;
            totalRowsPlaced++;
            this.formTextRow = page != 0 ? 0 : totalRowsPlaced;
            adjustedXPlaced = 0;
         }

         if (!foundAugments && part instanceof AbstractAugment) {
            foundAugments = true;
            adjustedRowsPlaced += row_offset;
            totalRowsPlaced += row_offset;
            this.augmentTextRow = page != 0 ? 0 : totalRowsPlaced - 1;
            adjustedXPlaced = 0;
         } else if (!foundEffects && part instanceof AbstractEffect) {
            foundEffects = true;
            adjustedRowsPlaced += row_offset;
            totalRowsPlaced += row_offset;
            this.effectTextRow = page != 0 ? 0 : totalRowsPlaced - 1;
            adjustedXPlaced = 0;
         } else if (adjustedXPlaced >= 6) {
            adjustedRowsPlaced++;
            totalRowsPlaced++;
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
         GlyphButton cell = new GlyphButton(this, xStart + xOffset, yPlace, false, part);
         this.m_142416_(cell);
         this.glyphButtons.add(cell);
         adjustedXPlaced++;
      }
   }

   public void resetPageState() {
      this.updateNextPageButtons();
      this.page = 0;
      this.previousButton.f_93623_ = false;
      this.previousButton.f_93624_ = false;
      this.layoutAllGlyphs(this.page);
      this.validate();
   }

   public void onSearchChanged(String str) {
      if (!str.equals(this.previousString)) {
         this.previousString = str;
         if (!str.isEmpty()) {
            this.searchBar.m_94167_("");
            this.displayedGlyphs = new ArrayList<>();

            for (AbstractSpellPart spellPart : this.unlockedSpells) {
               if (spellPart.getLocaleName().toLowerCase().contains(str.toLowerCase())) {
                  this.displayedGlyphs.add(spellPart);
               }
            }

            for (Widget w : this.f_169369_) {
               if (w instanceof GlyphButton) {
                  GlyphButton glyphButton = (GlyphButton)w;
                  if (glyphButton.abstractSpellPart.getRegistryName() != null) {
                     AbstractSpellPart part = this.api.getSpellpartMap().get(glyphButton.abstractSpellPart.getRegistryName());
                     if (part != null) {
                        glyphButton.f_93624_ = part.getLocaleName().toLowerCase().contains(str.toLowerCase());
                     }
                  }
               }
            }
         } else {
            this.searchBar.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
            this.displayedGlyphs = this.unlockedSpells;

            for (Widget wx : this.f_169369_) {
               if (wx instanceof GlyphButton) {
                  ((GlyphButton)wx).f_93624_ = true;
               }
            }
         }

         this.resetPageState();
      }
   }

   public void updateNextPageButtons() {
      if (this.displayedGlyphs.size() < this.glyphsPerPage) {
         this.nextButton.f_93624_ = false;
         this.nextButton.f_93623_ = false;
      } else {
         this.nextButton.f_93624_ = true;
         this.nextButton.f_93623_ = true;
      }
   }

   public void clearButtons(List<GlyphButton> glyphButtons) {
      for (GlyphButton b : glyphButtons) {
         this.f_169369_.remove(b);
         this.m_6702_().remove(b);
      }

      glyphButtons.clear();
   }

   public void onPageIncrease(Button button) {
      if (this.page + 1 < this.getNumPages()) {
         this.page++;
         if (this.displayedGlyphs.size() < this.glyphsPerPage * (this.page + 1)) {
            this.nextButton.f_93624_ = false;
            this.nextButton.f_93623_ = false;
         }

         this.previousButton.f_93623_ = true;
         this.previousButton.f_93624_ = true;
         this.layoutAllGlyphs(this.page);
         this.validate();
      }
   }

   public void onPageDec(Button button) {
      if (this.page <= 0) {
         this.page = 0;
      } else {
         this.page--;
         if (this.page == 0) {
            this.previousButton.f_93623_ = false;
            this.previousButton.f_93624_ = false;
         }

         if (this.displayedGlyphs.size() > this.glyphsPerPage * (this.page + 1)) {
            this.nextButton.f_93624_ = true;
            this.nextButton.f_93623_ = true;
         }

         this.layoutAllGlyphs(this.page);
         this.validate();
      }
   }

   public boolean m_6050_(double mouseX, double mouseY, double scroll) {
      SoundManager manager = Minecraft.m_91087_().m_91106_();
      if (scroll < 0.0 && this.nextButton.f_93623_) {
         this.onPageIncrease(this.nextButton);
         manager.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_11713_, 1.0F));
      } else if (scroll > 0.0 && this.previousButton.f_93623_) {
         this.onPageDec(this.previousButton);
         manager.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_11713_, 1.0F));
      }

      return true;
   }

   public void onDocumentationClick(Button button) {
      PatchouliAPI.get().openBookGUI(ForgeRegistries.ITEMS.getKey(ItemsRegistry.WORN_NOTEBOOK.m_5456_()));
   }

   public void onColorClick(Button button) {
      ParticleColor.IntWrapper color = CasterUtil.getCaster(this.bookStack).getColor(this.selectedSpellSlot).toWrapper();
      Minecraft.m_91087_().m_91152_(new GuiColorScreen((double)color.r, (double)color.g, (double)color.b, this.selectedSpellSlot, this.hand));
   }

   public void onSoundsClick(Button button) {
      ConfiguredSpellSound spellSound = CasterUtil.getCaster(this.bookStack).getSound(this.selectedSpellSlot);
      Minecraft.m_91087_().m_91152_(new SoundScreen(spellSound, this.selectedSpellSlot, this.hand));
   }

   public void onFamiliarClick(Button button) {
      Collection<ResourceLocation> familiarHolders = new ArrayList<>();
      IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(ArsNouveau.proxy.getPlayer()).orElse(null);
      if (cap != null) {
         familiarHolders = cap.getUnlockedFamiliars().stream().map(s -> s.familiarHolder.getRegistryName()).collect(Collectors.toList());
      }

      Collection<ResourceLocation> finalFamiliarHolders = familiarHolders;
      Minecraft.m_91087_()
         .m_91152_(
            new GuiFamiliarScreen(
               this.api,
               ArsNouveauAPI.getInstance()
                  .getFamiliarHolderMap()
                  .values()
                  .stream()
                  .filter(f -> finalFamiliarHolders.contains(f.getRegistryName()))
                  .collect(Collectors.toList()),
               this
            )
         );
   }

   public void onCraftingSlotClick(Button button) {
      ((CraftingButton)button).clear();
      this.validate();
   }

   public void onGlyphClick(Button button) {
      GlyphButton button1 = (GlyphButton)button;
      if (button1.validationErrors.isEmpty()) {
         for (CraftingButton b : this.craftingCells) {
            if (b.abstractSpellPart == null) {
               b.abstractSpellPart = button1.abstractSpellPart;
               b.spellTag = button1.abstractSpellPart.getRegistryName();
               this.validate();
               return;
            }
         }
      }
   }

   public void onSlotChange(Button button) {
      this.selected_slot.isSelected = false;
      this.selected_slot = (GuiSpellSlot)button;
      this.selected_slot.isSelected = true;
      this.selectedSpellSlot = this.selected_slot.slotNum;
      this.updateCraftingSlots(this.selectedSpellSlot);
      this.spell_name.m_94144_(CasterUtil.getCaster(this.bookStack).getSpellName(this.selectedSpellSlot));
      this.validate();
   }

   public boolean m_5534_(char pCodePoint, int pModifiers) {
      if (this.hoveredWidget instanceof GlyphButton glyphButton && glyphButton.validationErrors.isEmpty() && pCodePoint >= '0' && pCodePoint <= '9') {
         int num = Integer.parseInt(String.valueOf(pCodePoint));
         if (num == 0) {
            num = 10;
         }

         num--;
         this.craftingCells.get(num).abstractSpellPart = glyphButton.abstractSpellPart;
         this.craftingCells.get(num).spellTag = glyphButton.abstractSpellPart.getRegistryName();
         this.validate();
         return true;
      }

      return super.m_5534_(pCodePoint, pModifiers);
   }

   public void updateCraftingSlots(int bookSlot) {
      List<AbstractSpellPart> recipe = CasterUtil.getCaster(this.bookStack).getSpell(bookSlot).recipe;

      for (int i = 0; i < this.craftingCells.size(); i++) {
         CraftingButton slot = this.craftingCells.get(i);
         slot.spellTag = ArsNouveauAPI.EMPTY_KEY;
         slot.abstractSpellPart = null;
         if (recipe != null && i < recipe.size()) {
            slot.spellTag = recipe.get(i).getRegistryName();
            slot.abstractSpellPart = recipe.get(i);
         }
      }
   }

   public void clear(Button button) {
      boolean allWereEmpty = true;

      for (CraftingButton slot : this.craftingCells) {
         if (!slot.spellTag.equals(ArsNouveauAPI.EMPTY_KEY)) {
            allWereEmpty = false;
         }

         slot.clear();
      }

      if (allWereEmpty) {
         this.spell_name.m_94144_("");
      }

      this.validate();
   }

   public void onCreateClick(Button button) {
      this.validate();
      if (this.validationErrors.isEmpty()) {
         Spell spell = new Spell();

         for (CraftingButton slot : this.craftingCells) {
            AbstractSpellPart spellPart = ArsNouveauAPI.getInstance().getSpellpartMap().get(slot.spellTag);
            if (spellPart != null) {
               spell.add(spellPart);
            }
         }

         Networking.INSTANCE
            .sendToServer(new PacketUpdateCaster(spell, this.selectedSpellSlot, this.spell_name.m_94155_(), this.hand == InteractionHand.MAIN_HAND));
      }
   }

   @Deprecated
   public static void open(ItemStack stack, int tier) {
      open(InteractionHand.MAIN_HAND);
   }

   public static void open(InteractionHand hand) {
      Minecraft.m_91087_().m_91152_(new GuiSpellBook(hand));
   }

   @Override
   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      if (this.formTextRow >= 1) {
         this.f_96541_
            .f_91062_
            .m_92883_(
               stack,
               Component.m_237115_("ars_nouveau.spell_book_gui.form").getString(),
               this.formTextRow > 6 ? 154.0F : 20.0F,
               (float)(5 + 18 * (this.formTextRow + (this.formTextRow == 1 ? 0 : 1))),
               -8355712
            );
      }

      if (this.effectTextRow >= 1) {
         this.f_96541_
            .f_91062_
            .m_92883_(
               stack,
               Component.m_237115_("ars_nouveau.spell_book_gui.effect").getString(),
               this.effectTextRow > 6 ? 154.0F : 20.0F,
               (float)(5 + 18 * (this.effectTextRow + 1)),
               -8355712
            );
      }

      if (this.augmentTextRow >= 1) {
         this.f_96541_
            .f_91062_
            .m_92883_(
               stack,
               Component.m_237115_("ars_nouveau.spell_book_gui.augment").getString(),
               this.augmentTextRow > 6 ? 154.0F : 20.0F,
               (float)(5 + 18 * (this.augmentTextRow + 1)),
               -8355712
            );
      }

      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/spell_name_paper.png"), 16, 179, 0, 0, 109, 15, 109, 15, stack);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/search_paper.png"), 203, 0, 0, 0, 72, 15, 72, 15, stack);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/clear_paper.png"), 161, 179, 0, 0, 47, 15, 47, 15, stack);
      drawFromTexture(new ResourceLocation("ars_nouveau", "textures/gui/create_paper.png"), 216, 179, 0, 0, 56, 15, 56, 15, stack);
      if (this.validationErrors.isEmpty()) {
         this.f_96541_.f_91062_.m_92889_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.create"), 233.0F, 183.0F, -8355712);
      } else {
         Component textComponent = Component.m_237115_("ars_nouveau.spell_book_gui.create")
            .m_130938_(s -> s.m_178522_(true).m_131148_(TextColor.m_131268_("#FFB2B2")));
         this.f_96541_.f_91062_.m_92889_(stack, textComponent, 233.0F, 183.0F, -8355712);
      }

      this.f_96541_.f_91062_.m_92883_(stack, Component.m_237115_("ars_nouveau.spell_book_gui.clear").getString(), 177.0F, 183.0F, -8355712);
      int manaLength = 96;
      if (this.maxManaCache > 0) {
         manaLength = (int)((float)manaLength * ((float)(this.maxManaCache - this.currentCostCache) / (float)this.maxManaCache));
      } else {
         manaLength = 0;
      }

      int offsetLeft = 89;
      int yOffset = 210;
      stack.m_85836_();
      stack.m_85841_(1.2F, 1.2F, 1.2F);
      stack.m_85837_(-25.0, -30.0, 0.0);
      RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_border.png"));
      m_93133_(stack, offsetLeft, yOffset - 18, 0.0F, 0.0F, 108, 18, 256, 256);
      int manaOffset = (int)(((float)ClientInfo.ticksInGame + partialTicks) / 3.0F % 33.0F) * 6;
      if (manaLength >= 0) {
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_mana.png"));
         m_93133_(stack, offsetLeft + 9, yOffset - 9, 0.0F, (float)manaOffset, manaLength, 6, 256, 256);
      } else {
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_grayscale.png"));
         RenderUtils.colorBlit(
            stack, offsetLeft + 8, yOffset - 10, 0, manaOffset, 100, 8, 256, 256, manaLength < 0 ? Color.RED : Color.rainbowColor(ClientInfo.ticksInGame)
         );
      }

      if (ArsNouveauAPI.ENABLE_DEBUG_NUMBERS) {
         String text = this.currentCostCache + "  /  " + this.maxManaCache;
         int maxWidth = this.f_96541_.f_91062_.m_92895_(this.maxManaCache + "  /  " + this.maxManaCache);
         int offset = offsetLeft - maxWidth / 2 + (maxWidth - this.f_96541_.f_91062_.m_92895_(text));
         m_93236_(stack, this.f_96541_.f_91062_, text, offset + 55, yOffset - 10, 16777215);
      }

      RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/manabar_gui_border.png"));
      m_93133_(stack, offsetLeft, yOffset - 17, 0.0F, 18.0F, 108, 20, 256, 256);
      stack.m_85849_();
   }

   private int getCurrentManaCost() {
      Spell spell = new Spell();

      for (CraftingButton button : this.craftingCells) {
         if (button.spellTag != ArsNouveauAPI.EMPTY_KEY) {
            AbstractSpellPart part = ArsNouveauAPI.getInstance().getSpellpartMap().get(button.spellTag);
            if (part != null) {
               spell.add(part);
            }
         }
      }

      int cost = spell.getFinalCostAndReset() - ManaUtil.getPlayerDiscounts(Minecraft.m_91087_().f_91074_, spell);
      return Math.max(cost, 0);
   }

   private void validate() {
      List<AbstractSpellPart> recipe = new LinkedList<>();
      int firstBlankSlot = -1;

      for (int i = 0; i < this.craftingCells.size(); i++) {
         CraftingButton b = this.craftingCells.get(i);
         b.validationErrors.clear();
         if (b.spellTag == ArsNouveauAPI.EMPTY_KEY) {
            recipe.add(null);
            if (firstBlankSlot < 0) {
               firstBlankSlot = i;
            }
         } else {
            recipe.add(this.api.getSpellpartMap().get(b.spellTag));
         }
      }

      List<SpellValidationError> errors = this.spellValidator.validate(recipe);

      for (SpellValidationError ve : errors) {
         if (ve.getPosition() >= 0 && ve.getPosition() <= this.craftingCells.size()) {
            CraftingButton b = this.craftingCells.get(ve.getPosition());
            b.validationErrors.add(ve);
         }
      }

      this.validationErrors = errors;
      if (firstBlankSlot >= 0) {
         recipe = new ArrayList<>(recipe.subList(0, firstBlankSlot));
      }

      for (GlyphButton button : this.glyphButtons) {
         this.validateGlyphButton(recipe, button);
      }

      this.currentCostCache = this.getCurrentManaCost();
      this.maxManaCache = ManaUtil.getMaxMana(Minecraft.m_91087_().f_91074_);
   }

   private void validateGlyphButton(List<AbstractSpellPart> recipe, GlyphButton glyphButton) {
      glyphButton.validationErrors.clear();
      recipe.add(this.api.getSpellpartMap().get(glyphButton.abstractSpellPart.getRegistryName()));
      glyphButton.validationErrors.addAll(this.spellValidator.validate(recipe).stream().filter(ve -> ve.getPosition() >= recipe.size() - 1).toList());
      recipe.remove(recipe.size() - 1);
   }

   @Override
   public void m_6305_(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(ms, mouseX, mouseY, partialTicks);
      if (this.setFocusOnLoad) {
         this.setFocusOnLoad = false;
         this.m_94718_(this.searchBar);
      }

      this.hoveredWidget = null;

      for (Widget widget : this.f_169369_) {
         if (widget instanceof AbstractWidget abstractWidget && GuiUtils.isMouseInRelativeRange(mouseX, mouseY, abstractWidget)) {
            this.hoveredWidget = widget;
            break;
         }
      }

      this.spell_name.m_94167_(this.spell_name.m_94155_().isEmpty() ? Component.m_237115_("ars_nouveau.spell_book_gui.spell_name").getString() : "");
   }
}
