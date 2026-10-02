package shadows.apotheosis.ench.library;

import com.google.common.base.Strings;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.placebo.Placebo;
import shadows.placebo.packets.ButtonClickMessage;

public class EnchLibraryScreen extends AbstractContainerScreen<EnchLibraryContainer> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/library.png");
   protected float scrollOffs;
   protected boolean scrolling;
   protected int startIndex;
   protected List<EnchLibraryScreen.LibrarySlot> data = new ArrayList<>();
   protected EditBox filter = null;
   private static DecimalFormat f = new DecimalFormat("##.#");

   public EnchLibraryScreen(EnchLibraryContainer container, Inventory inv, Component title) {
      super(container, inv, title);
      this.f_96543_ = this.f_97726_ = 176;
      this.f_96544_ = this.f_97727_ = 241;
      this.f_97728_ = this.f_97730_ = 7;
      this.f_97729_ = 4;
      this.f_97731_ = 149;
      this.containerChanged();
      container.setNotifier(this::containerChanged);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.filter = (EditBox)this.m_142416_(
         new EditBox(this.f_96547_, this.getGuiLeft() + 91, this.getGuiTop() + 20 + 9 + 2, 78, 9 + 4, this.filter, Component.m_237113_(""))
      );
      this.filter.m_94151_(t -> this.containerChanged());
   }

   public boolean m_7933_(int pKeyCode, int pScanCode, int pModifiers) {
      Key mouseKey = InputConstants.m_84827_(pKeyCode, pScanCode);
      if (pKeyCode == 256 && this.m_7222_() == this.filter) {
         this.m_7522_(null);
         this.filter.m_94178_(false);
         return true;
      } else {
         return this.f_96541_.f_91066_.f_92092_.isActiveAndMatches(mouseKey) && this.m_7222_() == this.filter
            ? true
            : super.m_7933_(pKeyCode, pScanCode, pModifiers);
      }
   }

   public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      this.m_7333_(stack);
      super.m_6305_(stack, mouseX, mouseY, partialTicks);
      this.m_7025_(stack, mouseX, mouseY);
   }

   protected void m_7025_(PoseStack stack, int mouseX, int mouseY) {
      super.m_7025_(stack, mouseX, mouseY);
      EnchLibraryScreen.LibrarySlot libSlot = this.getHoveredSlot(mouseX, mouseY);
      if (libSlot != null) {
         List<FormattedText> list = new ArrayList<>();
         MutableComponent name = Component.m_237115_(libSlot.ench.m_44704_()).m_6270_(Style.f_131099_.m_131148_(TextColor.m_131266_(16777088)).m_131162_(true));
         if (AttributesLib.getTooltipFlag().m_7050_()) {
            name = name.m_7220_(
               Component.m_237113_(" [" + ForgeRegistries.ENCHANTMENTS.getKey(libSlot.ench) + "]")
                  .m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GRAY).m_131162_(false))
            );
         }

         list.add(name);
         if (I18n.m_118936_(libSlot.ench.m_44704_() + ".desc") || AttributesLib.getTooltipFlag().m_7050_()) {
            Component txt = Component.m_237115_(libSlot.ench.m_44704_() + ".desc").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GRAY).m_131155_(true));
            list.addAll(this.f_96547_.m_92865_().m_92414_(txt, this.getGuiLeft() - 16, txt.m_7383_()));
            list.add(Component.m_237113_(""));
         }

         list.add(
            Component.m_237110_("tooltip.enchlib.max_lvl", new Object[]{Component.m_237115_("enchantment.level." + libSlot.maxLvl)})
               .m_130940_(ChatFormatting.GRAY)
         );
         list.add(
            Component.m_237110_("tooltip.enchlib.points", new Object[]{format(libSlot.points), format(((EnchLibraryContainer)this.f_97732_).getPointCap())})
               .m_130940_(ChatFormatting.GRAY)
         );
         list.add(Component.m_237113_(""));
         ItemStack outSlot = ((EnchLibraryContainer)this.f_97732_).ioInv.m_8020_(1);
         int current = EnchantmentHelper.m_44831_(outSlot).getOrDefault(libSlot.ench, 0);
         boolean shift = Screen.m_96638_();
         int targetLevel = shift
            ? Math.min(libSlot.maxLvl, 1 + (int)(Math.log((double)(libSlot.points + EnchLibraryTile.levelToPoints(current))) / Math.log(2.0)))
            : current + 1;
         if (targetLevel == current) {
            targetLevel++;
         }

         int cost = EnchLibraryTile.levelToPoints(targetLevel) - EnchLibraryTile.levelToPoints(current);
         if (targetLevel > libSlot.maxLvl) {
            list.add(Component.m_237115_("tooltip.enchlib.unavailable").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.RED)));
         } else {
            list.add(
               Component.m_237110_("tooltip.enchlib.extracting", new Object[]{Component.m_237115_("enchantment.level." + targetLevel)})
                  .m_130940_(ChatFormatting.BLUE)
            );
            list.add(
               Component.m_237110_("tooltip.enchlib.cost", new Object[]{cost}).m_130940_(cost > libSlot.points ? ChatFormatting.RED : ChatFormatting.GOLD)
            );
         }

         this.renderComponentTooltip(
            stack, list, this.getGuiLeft() - 16 - list.stream().<Integer>map(this.f_96547_::m_92852_).max(Integer::compare).get(), mouseY, this.f_96547_
         );
      }
   }

   protected void m_7286_(PoseStack stack, float partial, int mouseX, int mouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      int left = this.f_97735_;
      int top = this.f_97736_;
      this.m_93228_(stack, left, top, 0, 0, this.f_97726_, this.f_97727_);
      int scrollbarPos = (int)(118.0F * this.scrollOffs);
      this.m_93228_(stack, left + 75, top + 14 + scrollbarPos, 244, this.isScrollBarActive() ? 0 : 15, 12, 15);

      for (int idx = this.startIndex; idx < this.startIndex + 7 && idx < this.data.size(); idx++) {
         this.renderEntry(stack, this.data.get(idx), this.f_97735_ + 8, this.f_97736_ + 14 + 19 * (idx - this.startIndex), mouseX, mouseY);
      }

      this.f_96547_.m_92889_(stack, Component.m_237115_("tooltip.enchlib.nfilt"), (float)(this.getGuiLeft() + 91), (float)(this.getGuiTop() + 20), 4210752);
      this.f_96547_.m_92889_(stack, Component.m_237115_("tooltip.enchlib.ifilt"), (float)(this.getGuiLeft() + 91), (float)(this.getGuiTop() + 50), 4210752);
   }

   private void renderEntry(PoseStack stack, EnchLibraryScreen.LibrarySlot data, int x, int y, int mouseX, int mouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      boolean hover = this.m_6774_(x - this.f_97735_, y - this.f_97736_, 64, 17, (double)mouseX, (double)mouseY);
      this.m_93228_(stack, x, y, 178, hover ? 19 : 0, 64, 19);
      int progress = (int)Math.round(
         62.0 * Math.sqrt((double)data.points) / (double)((float)Math.sqrt((double)((EnchLibraryContainer)this.f_97732_).getPointCap()))
      );
      this.m_93228_(stack, x + 1, y + 12, 179, 38, progress, 5);
      stack.m_85836_();
      Component txt = Component.m_237115_(data.ench.m_44704_());
      float scale = 1.0F;
      if (this.f_96547_.m_92852_(txt) > 60) {
         scale = 60.0F / (float)this.f_96547_.m_92852_(txt);
      }

      stack.m_85841_(scale, scale, 1.0F);
      this.f_96547_.m_92889_(stack, txt, (float)(x + 2) / scale, (float)(y + 2) / scale, 16777088);
      stack.m_85849_();
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      this.scrolling = false;
      int left = this.f_97735_ + 52;
      int top = this.f_97736_ + 14;
      EnchLibraryScreen.LibrarySlot libSlot = this.getHoveredSlot((int)pMouseX, (int)pMouseY);
      if (libSlot != null) {
         int id = ((ForgeRegistry)ForgeRegistries.ENCHANTMENTS).getID(libSlot.ench);
         if (Screen.m_96638_()) {
            id |= Integer.MIN_VALUE;
         }

         ((EnchLibraryContainer)this.f_97732_).onButtonClick(id);
         Placebo.CHANNEL.sendToServer(new ButtonClickMessage(id));
         this.f_96541_.m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12495_, 1.0F));
      }

      left = this.f_97735_ + 75;
      top = this.f_97736_ + 9;
      if (pMouseX >= (double)left && pMouseX < (double)(left + 12) && pMouseY >= (double)top && pMouseY < (double)(top + 131)) {
         this.scrolling = true;
      }

      return super.m_6375_(pMouseX, pMouseY, pButton);
   }

   public boolean m_7979_(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
      if (this.scrolling && this.isScrollBarActive()) {
         int i = this.f_97736_ + 14;
         int j = i + 131;
         this.scrollOffs = ((float)pMouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.scrollOffs = Mth.m_14036_(this.scrollOffs, 0.0F, 1.0F);
         this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5);
         return true;
      } else {
         return super.m_7979_(pMouseX, pMouseY, pButton, pDragX, pDragY);
      }
   }

   public boolean m_6050_(double pMouseX, double pMouseY, double pDelta) {
      if (this.isScrollBarActive()) {
         int i = this.getOffscreenRows();
         this.scrollOffs = (float)((double)this.scrollOffs - pDelta / (double)i);
         this.scrollOffs = Mth.m_14036_(this.scrollOffs, 0.0F, 1.0F);
         this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5);
      }

      return true;
   }

   private boolean isScrollBarActive() {
      return this.data.size() > 7;
   }

   protected int getOffscreenRows() {
      return this.data.size() - 7;
   }

   private void containerChanged() {
      this.data.clear();

      for (Entry<Enchantment> e : this.filter(((EnchLibraryContainer)this.f_97732_).getPointsForDisplay())) {
         this.data
            .add(
               new EnchLibraryScreen.LibrarySlot(
                  (Enchantment)e.getKey(), e.getIntValue(), ((EnchLibraryContainer)this.f_97732_).getMaxLevel((Enchantment)e.getKey())
               )
            );
      }

      if (!this.isScrollBarActive()) {
         this.scrollOffs = 0.0F;
         this.startIndex = 0;
      }

      Collections.sort(this.data, (a, b) -> I18n.m_118938_(a.ench.m_44704_(), new Object[0]).compareTo(I18n.m_118938_(b.ench.m_44704_(), new Object[0])));
   }

   private List<Entry<Enchantment>> filter(List<Entry<Enchantment>> list) {
      return list.stream().filter(this::isAllowedByItem).filter(this::isAllowedBySearch).toList();
   }

   private boolean isAllowedByItem(Entry<Enchantment> e) {
      ItemStack stack = ((EnchLibraryContainer)this.f_97732_).ioInv.m_8020_(2);
      return stack.m_41619_() || ((Enchantment)e.getKey()).m_6081_(stack);
   }

   private boolean isAllowedBySearch(Entry<Enchantment> e) {
      String name = I18n.m_118938_(((Enchantment)e.getKey()).m_44704_(), new Object[0]).toLowerCase(Locale.ROOT);
      String search = this.filter == null ? "" : this.filter.m_94155_().trim().toLowerCase(Locale.ROOT);
      return Strings.isNullOrEmpty(search) || ChatFormatting.m_126649_(name).contains(search);
   }

   @Nullable
   public EnchLibraryScreen.LibrarySlot getHoveredSlot(int mouseX, int mouseY) {
      for (int i = 0; i < 7; i++) {
         if (this.startIndex + i < this.data.size() && this.m_6774_(8, 14 + 19 * i, 64, 17, (double)mouseX, (double)mouseY)) {
            return this.data.get(this.startIndex + i);
         }
      }

      return null;
   }

   public static String format(int n) {
      int log = (int)StrictMath.log10((double)n);
      if (log <= 4) {
         return String.valueOf(n);
      } else if (log == 5) {
         return f.format((double)n / 1000.0) + "K";
      } else {
         return log <= 8 ? f.format((double)n / 1000000.0) + "M" : f.format((double)n / 1.0E9) + "B";
      }
   }

   private static record LibrarySlot(Enchantment ench, int points, int maxLvl) {
   }
}
