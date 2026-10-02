package io.redspace.ironsspellbooks.gui.inscription_table;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.player.ClientRenderCache;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/inscription_table.png");
   private static final int INSCRIBE_BUTTON_X = 43;
   private static final int INSCRIBE_BUTTON_Y = 35;
   private static final int EXTRACT_BUTTON_X = 188;
   private static final int EXTRACT_BUTTON_Y = 137;
   private static final int SPELLBOOK_SLOT = 36;
   private static final int SCROLL_SLOT = 37;
   private static final int EXTRACTION_SLOT = 38;
   private static final int SPELL_BG_X = 67;
   private static final int SPELL_BG_Y = 15;
   private static final int SPELL_BG_WIDTH = 95;
   private static final int SPELL_BG_HEIGHT = 57;
   private static final int LORE_PAGE_X = 176;
   private static final int LORE_PAGE_WIDTH = 80;
   private boolean isDirty;
   protected Button inscribeButton;
   private ItemStack lastSpellBookItem = ItemStack.f_41583_;
   protected ArrayList<InscriptionTableScreen.SpellSlotInfo> spellSlots;
   private int selectedSpellIndex = -1;
   private int inscriptionErrorCode = 0;
   private final int[][] LAYOUT = ClientRenderCache.SPELL_LAYOUT;

   public InscriptionTableScreen(InscriptionTableMenu menu, Inventory playerInventory, Component title) {
      super(menu, playerInventory, title);
      this.f_97726_ = 256;
      this.f_97727_ = 166;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.inscribeButton = (Button)this.m_7787_(new Button(0, 0, 14, 14, CommonComponents.f_130655_, p_169820_ -> this.onInscription()));
      this.spellSlots = new ArrayList<>();
      this.generateSpellSlots();
   }

   public void m_7379_() {
      super.m_7379_();
      this.resetSelectedSpell();
   }

   public void m_6305_(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
      try {
         this.m_7333_(pPoseStack);
         super.m_6305_(pPoseStack, mouseX, mouseY, delta);
         this.m_7025_(pPoseStack, mouseX, mouseY);
      } catch (Exception var6) {
         this.m_7379_();
      }
   }

   protected void m_7286_(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
      this.setTexture(TEXTURE);
      this.m_93228_(poseStack, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      this.inscribeButton.f_93623_ = this.isValidInscription() && this.inscriptionErrorCode == 0;
      this.renderButtons(poseStack, mouseX, mouseY);
      if (((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36)).m_7993_() != this.lastSpellBookItem) {
         this.onSpellBookSlotChanged();
         this.lastSpellBookItem = ((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36)).m_7993_();
      }

      this.renderSpells(poseStack, mouseX, mouseY);
      this.renderLorePage(poseStack, partialTick, mouseX, mouseY);
      if (((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36)).m_6657_()) {
         this.inscriptionErrorCode = this.getErrorCode();
      } else {
         this.inscriptionErrorCode = 0;
      }

      if (this.inscriptionErrorCode > 0) {
         this.setTexture(TEXTURE);
         this.m_93228_(poseStack, this.f_97735_ + 35, this.f_97736_ + 51, 0, 213, 28, 22);
         if (this.isHovering(this.f_97735_ + 35, this.f_97736_ + 51, 28, 22, mouseX, mouseY)) {
            this.m_96602_(poseStack, this.getErrorMessage(this.inscriptionErrorCode), mouseX, mouseY);
         }
      }
   }

   private int getErrorCode() {
      if (((InscriptionTableMenu)this.f_97732_).getSpellBookSlot().m_7993_().m_41720_() instanceof SpellBook spellbook
         && ((InscriptionTableMenu)this.f_97732_).getScrollSlot().m_7993_().m_41720_() instanceof Scroll scroll) {
         ISpellContainer scrollContainer = ISpellContainer.get(((InscriptionTableMenu)this.f_97732_).getScrollSlot().m_7993_());
         SpellData spellSlot = scrollContainer.getSpellAtIndex(0);
         if (spellbook.getRarity().compareRarity(spellSlot.getSpell().getRarity(spellSlot.getLevel())) < 0) {
            return 1;
         }
      }

      return 0;
   }

   private Component getErrorMessage(int code) {
      return code == 1 ? Component.m_237115_("ui.irons_spellbooks.inscription_table_rarity_error") : Component.m_237119_();
   }

   private void renderSpells(PoseStack poseStack, int mouseX, int mouseY) {
      if (this.isDirty) {
         this.generateSpellSlots();
      }

      Vec2 center = new Vec2((float)(67 + this.f_97735_ + 47), (float)(15 + this.f_97736_ + 28));

      for (int i = 0; i < this.spellSlots.size(); i++) {
         Button spellSlot = this.spellSlots.get(i).button;
         Vec2 pos = this.spellSlots.get(i).relativePosition.m_165910_(center);
         spellSlot.f_93620_ = (int)pos.f_82470_;
         spellSlot.f_93621_ = (int)pos.f_82471_;
         this.renderSpellSlot(poseStack, pos, mouseX, mouseY, i, this.spellSlots.get(i));
      }
   }

   private void renderButtons(PoseStack poseStack, int mouseX, int mouseY) {
      this.inscribeButton.f_93620_ = this.f_97735_ + 43;
      this.inscribeButton.f_93621_ = this.f_97736_ + 35;
      if (this.inscribeButton.f_93623_) {
         if (this.isHovering(this.inscribeButton.f_93620_, this.inscribeButton.f_93621_, 14, 14, mouseX, mouseY)) {
            this.m_93228_(poseStack, this.inscribeButton.f_93620_, this.inscribeButton.f_93621_, 28, 185, 14, 14);
         } else {
            this.m_93228_(poseStack, this.inscribeButton.f_93620_, this.inscribeButton.f_93621_, 14, 185, 14, 14);
         }
      } else {
         this.m_93228_(poseStack, this.inscribeButton.f_93620_, this.inscribeButton.f_93621_, 0, 185, 14, 14);
      }
   }

   private void renderSpellSlot(PoseStack poseStack, Vec2 pos, int mouseX, int mouseY, int index, InscriptionTableScreen.SpellSlotInfo slot) {
      boolean hovering = this.isHovering((int)pos.f_82470_, (int)pos.f_82471_, 19, 19, mouseX, mouseY);
      int iconToDraw = hovering ? 38 : (slot.hasSpell() ? 19 : 0);
      this.m_93228_(poseStack, (int)pos.f_82470_, (int)pos.f_82471_, iconToDraw, 166, 19, 19);
      if (slot.hasSpell()) {
         this.drawSpellIcon(poseStack, pos, slot);
         this.setTexture(TEXTURE);
         if (hovering && !slot.spellData.canRemove()) {
            this.m_93228_(poseStack, (int)pos.f_82470_, (int)pos.f_82471_, 76, 166, 19, 19);
         }
      }

      if (index == this.selectedSpellIndex) {
         this.m_93228_(poseStack, (int)pos.f_82470_, (int)pos.f_82471_, 57, 166, 19, 19);
      }
   }

   private void drawSpellIcon(PoseStack poseStack, Vec2 pos, InscriptionTableScreen.SpellSlotInfo slot) {
      this.setTexture(slot.spellData.getSpell().getSpellIconResource());
      m_93133_(poseStack, (int)pos.f_82470_ + 2, (int)pos.f_82471_ + 2, 0.0F, 0.0F, 15, 15, 16, 16);
   }

   private void renderLorePage(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
      int x = this.f_97735_ + 176;
      int y = this.f_97736_;
      int margin = 2;
      Style textColor = Style.f_131099_.m_178520_(3288106);
      boolean spellSelected = this.selectedSpellIndex >= 0
         && this.selectedSpellIndex < this.spellSlots.size()
         && this.spellSlots.get(this.selectedSpellIndex).hasSpell();
      MutableComponent title = this.selectedSpellIndex < 0
         ? Component.m_237115_("ui.irons_spellbooks.no_selection")
         : (
            spellSelected
               ? this.spellSlots.get(this.selectedSpellIndex).spellData.getSpell().getDisplayName(Minecraft.m_91087_().f_91074_)
               : Component.m_237115_("ui.irons_spellbooks.empty_slot")
         );
      List<FormattedCharSequence> titleLines = this.f_96547_.m_92923_(title.m_130940_(ChatFormatting.UNDERLINE).m_130948_(textColor), 80);
      int titleY = this.f_97736_ + 10;

      for (FormattedCharSequence line : titleLines) {
         int titleWidth = this.f_96547_.m_92724_(line);
         int titleX = x + (80 - titleWidth) / 2;
         this.f_96547_.m_92877_(poseStack, line, (float)titleX, (float)titleY, 16777215);
         if (spellSelected && this.isHovering(titleX, titleY, titleWidth, 9, mouseX, mouseY)) {
            this.m_96617_(
               poseStack,
               TooltipsUtils.createSpellDescriptionTooltip(this.spellSlots.get(this.selectedSpellIndex).spellData.getSpell(), this.f_96547_),
               mouseX,
               mouseY
            );
         }

         titleY += 9;
      }

      int titleHeight = this.f_96547_.m_239133_(title.m_130940_(ChatFormatting.UNDERLINE).m_130948_(textColor), 80);
      int descLine = titleY + 4;
      if (this.selectedSpellIndex >= 0 && this.selectedSpellIndex < this.spellSlots.size() && this.spellSlots.get(this.selectedSpellIndex).hasSpell()) {
         Style colorMana = Style.f_131099_.m_178520_(17577);
         Style colorCast = Style.f_131099_.m_178520_(1135889);
         Style colorCooldown = Style.f_131099_.m_178520_(1135889);
         AbstractSpell spell = this.spellSlots.get(this.selectedSpellIndex).spellData.getSpell();
         int spellLevel = this.spellSlots.get(this.selectedSpellIndex).spellData.getLevel();
         float textScale = 1.0F;
         float reverseScale = 1.0F / textScale;
         Component school = spell.getSchoolType().getDisplayName();
         poseStack.m_85841_(textScale, textScale, textScale);
         this.drawTextWithShadow(this.f_96547_, poseStack, school, x + (80 - this.f_96547_.m_92895_(school.getString())) / 2, descLine, 16777215, 1.0F);
         descLine = (int)((float)descLine + 9.0F * textScale);
         MutableComponent levelText = Component.m_237110_("ui.irons_spellbooks.level", new Object[]{spellLevel}).m_130948_(textColor);
         this.f_96547_.m_92889_(poseStack, levelText, (float)(x + (80 - this.f_96547_.m_92895_(levelText.getString())) / 2), (float)descLine, 16777215);
         descLine = (int)((float)descLine + 9.0F * textScale * 2.0F);
         descLine += this.drawStatText(
            this.f_96547_,
            poseStack,
            x + margin,
            descLine,
            "ui.irons_spellbooks.mana_cost",
            textColor,
            Component.m_237115_(spell.getManaCost(spellLevel) + ""),
            colorMana,
            textScale
         );
         descLine += this.drawText(
            this.f_96547_,
            poseStack,
            TooltipsUtils.getCastTimeComponent(spell.getCastType(), Utils.timeFromTicks((float)spell.getEffectiveCastTime(spellLevel, null), 1)),
            x + margin,
            descLine,
            textColor.m_131135_().m_131265_(),
            textScale
         );
         descLine += this.drawStatText(
            this.f_96547_,
            poseStack,
            x + margin,
            descLine,
            "ui.irons_spellbooks.cooldown",
            textColor,
            Component.m_237115_(Utils.timeFromTicks((float)spell.getSpellCooldown(), 1)),
            colorCooldown,
            textScale
         );

         for (MutableComponent component : spell.getUniqueInfo(spellLevel, null)) {
            descLine += this.drawText(this.f_96547_, poseStack, component, x + margin, descLine, textColor.m_131135_().m_131265_(), 1.0F);
         }

         poseStack.m_85841_(reverseScale, reverseScale, reverseScale);
      }
   }

   private void drawTextWithShadow(Font font, PoseStack poseStack, Component text, int x, int y, int color, float scale) {
      x = (int)((float)x / scale);
      y = (int)((float)y / scale);
      font.m_92889_(poseStack, text, (float)x, (float)y, color);
      font.m_92763_(poseStack, text, (float)x, (float)y, color);
   }

   private int drawText(Font font, PoseStack poseStack, Component text, int x, int y, int color, float scale) {
      x = (int)((float)x / scale);
      y = (int)((float)y / scale);
      font.m_92857_(text, x, y, 80, color);
      return font.m_239133_(text, 80);
   }

   private int drawStatText(
      Font font, PoseStack poseStack, int x, int y, String translationKey, Style textStyle, MutableComponent stat, Style statStyle, float scale
   ) {
      return this.drawText(
         font, poseStack, Component.m_237110_(translationKey, new Object[]{stat.m_130948_(statStyle)}).m_130948_(textStyle), x, y, 16777215, scale
      );
   }

   private void generateSpellSlots() {
      for (InscriptionTableScreen.SpellSlotInfo s : this.spellSlots) {
         this.m_169411_(s.button);
      }

      this.spellSlots.clear();
      if (this.isSpellBookSlotted()) {
         Slot spellBookSlot = (Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36);
         ItemStack spellBookItemStack = spellBookSlot.m_7993_();
         ISpellContainer spellBookContainer = ISpellContainer.get(spellBookItemStack);
         SpellData[] storedSpells = spellBookContainer.getAllSpells();
         int spellCount = spellBookContainer.getMaxSpellCount();
         if (spellCount > 15) {
            spellCount = 15;
         }

         if (spellCount > 0) {
            int boxSize = 19;
            int[] rowCounts = ClientRenderCache.getRowCounts(spellCount);
            int[] row1 = new int[rowCounts[0]];
            int[] row2 = new int[rowCounts[1]];
            int[] row3 = new int[rowCounts[2]];
            int[] rowWidth = new int[]{boxSize * row1.length, boxSize * row2.length, boxSize * row3.length};
            int[] rowHeight = new int[]{row1.length > 0 ? boxSize : 0, row2.length > 0 ? boxSize : 0, row3.length > 0 ? boxSize : 0};
            int overallHeight = rowHeight[0] + rowHeight[1] + rowHeight[2];
            int[][] display = new int[][]{row1, row2, row3};
            int index = 0;

            for (int row = 0; row < display.length; row++) {
               for (int column = 0; column < display[row].length; column++) {
                  int offset = -rowWidth[row] / 2;
                  Vec2 location = new Vec2((float)(offset + column * boxSize), (float)(row * boxSize - overallHeight / 2));
                  location.m_165908_(-9.0F);
                  int temp_index = index;
                  this.spellSlots
                     .add(
                        new InscriptionTableScreen.SpellSlotInfo(
                           storedSpells[index],
                           location,
                           (Button)this.m_7787_(
                              new Button(
                                 (int)location.f_82470_,
                                 (int)location.f_82471_,
                                 boxSize,
                                 boxSize,
                                 Component.m_237115_(temp_index + ""),
                                 p_169820_ -> this.setSelectedIndex(temp_index)
                              )
                           )
                        )
                     );
                  index++;
               }
            }

            this.isDirty = false;
         }
      }
   }

   private void onSpellBookSlotChanged() {
      this.isDirty = true;
      ItemStack spellBookStack = ((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36)).m_7993_();
      if (spellBookStack.m_41720_() instanceof SpellBook) {
         ISpellContainer spellBookContainer = ISpellContainer.get(spellBookStack);
         if (spellBookContainer.getMaxSpellCount() <= this.selectedSpellIndex) {
            this.resetSelectedSpell();
         }
      } else {
         this.resetSelectedSpell();
      }
   }

   private void onInscription() {
      if (((InscriptionTableMenu)this.f_97732_).getSpellBookSlot().m_7993_().m_41720_() instanceof SpellBook spellBook
         && ((InscriptionTableMenu)this.f_97732_).getScrollSlot().m_7993_().m_41720_() instanceof Scroll scroll) {
         if (this.spellSlots.isEmpty()) {
            return;
         }

         ISpellContainer scrollContainer = ISpellContainer.get(((InscriptionTableMenu)this.f_97732_).getScrollSlot().m_7993_());
         SpellData scrollSlot = scrollContainer.getSpellAtIndex(0);
         if (spellBook.getRarity().compareRarity(scrollSlot.getSpell().getRarity(scrollSlot.getLevel())) < 0) {
            return;
         }

         if (this.selectedSpellIndex < 0 || this.spellSlots.get(this.selectedSpellIndex).hasSpell()) {
            for (int i = this.selectedSpellIndex + 1; i < this.spellSlots.size(); i++) {
               if (!this.spellSlots.get(i).hasSpell()) {
                  this.setSelectedIndex(i);
                  break;
               }
            }
         }

         this.setSelectedIndex(Mth.m_14045_(this.selectedSpellIndex, 0, this.spellSlots.size() - 1));
         if (this.spellSlots.get(this.selectedSpellIndex).hasSpell()) {
            return;
         }

         this.isDirty = true;
         Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12493_, 1.0F));
         this.f_96541_.f_91072_.m_105208_(((InscriptionTableMenu)this.f_97732_).f_38840_, -1);
      }
   }

   private void setSelectedIndex(int index) {
      this.selectedSpellIndex = index;
      this.f_96541_.f_91072_.m_105208_(((InscriptionTableMenu)this.f_97732_).f_38840_, index);
   }

   private void resetSelectedSpell() {
      this.setSelectedIndex(-1);
   }

   private boolean isValidInscription() {
      return this.isSpellBookSlotted() && this.isScrollSlotted();
   }

   private boolean isValidExtraction() {
      return this.selectedSpellIndex >= 0
         && this.spellSlots.get(this.selectedSpellIndex).hasSpell()
         && !((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(38)).m_6657_();
   }

   private boolean isSpellBookSlotted() {
      return ((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(36)).m_7993_().m_41720_() instanceof SpellBook;
   }

   private boolean isScrollSlotted() {
      return ((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(37)).m_6657_()
         && ((Slot)((InscriptionTableMenu)this.f_97732_).f_38839_.get(37)).m_7993_().m_41720_() instanceof Scroll;
   }

   private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
      return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
   }

   private void setTexture(ResourceLocation texture) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, texture);
   }

   private static class SpellSlotInfo {
      public SpellData spellData;
      public Vec2 relativePosition;
      public Button button;

      SpellSlotInfo(SpellData spellData, Vec2 relativePosition, Button button) {
         this.spellData = spellData;
         this.relativePosition = relativePosition;
         this.button = button;
      }

      public boolean hasSpell() {
         return this.spellData != null && !this.spellData.equals(SpellData.EMPTY);
      }
   }
}
