package shadows.apotheosis.ench.table;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantingInfoScreen extends Screen {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/enchanting_info.png");
   protected static ChatFormatting[] colors = new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.BLUE, ChatFormatting.GOLD};
   protected final ApothEnchantScreen parent;
   protected final int imageWidth;
   protected final int imageHeight;
   protected final ItemStack toEnchant;
   protected final int[] costs;
   protected final int[] clues;
   protected final int[][] powers = new int[3][];
   protected int selectedSlot = -1;
   protected int leftPos;
   protected int topPos;
   protected EnchantingInfoScreen.PowerSlider slider;
   protected int currentPower;
   protected float scrollOffs;
   protected boolean scrolling;
   protected int startIndex;
   List<RealEnchantmentHelper.ArcanaEnchantmentData> enchantments = Collections.emptyList();
   Map<Enchantment, List<Enchantment>> exclusions = new HashMap<>();

   public EnchantingInfoScreen(ApothEnchantScreen parent) {
      super(Component.m_237115_("info.apotheosis.enchinfo_title"));
      this.parent = parent;
      this.imageWidth = 240;
      this.imageHeight = 170;
      this.toEnchant = ((ApothEnchantmentMenu)parent.m_6262_()).m_38853_(0).m_7993_();
      this.costs = ((ApothEnchantmentMenu)parent.m_6262_()).f_39446_;
      this.clues = ((ApothEnchantmentMenu)parent.m_6262_()).f_39447_;

      for (int i = 0; i < 3; i++) {
         Enchantment clue = Enchantment.m_44697_(this.clues[i]);
         if (clue != null) {
            int level = this.costs[i];
            float quanta = ((ApothEnchantmentMenu)parent.m_6262_()).quanta.get() / 100.0F;
            float rectification = ((ApothEnchantmentMenu)parent.m_6262_()).rectification.get() / 100.0F;
            int minPow = Math.round(
               Mth.m_14036_((float)level - (float)level * (quanta - quanta * rectification), 1.0F, EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F)
            );
            int maxPow = Math.round(Mth.m_14036_((float)level + (float)level * quanta, 1.0F, EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F));
            this.powers[i] = new int[]{minPow, maxPow};
            this.selectedSlot = i;
         }
      }
   }

   protected void m_7856_() {
      this.leftPos = (this.f_96543_ - this.imageWidth) / 2;
      this.topPos = (this.f_96544_ - this.imageHeight) / 2;
      this.slider = (EnchantingInfoScreen.PowerSlider)this.m_142416_(new EnchantingInfoScreen.PowerSlider(this.leftPos + 5, this.topPos + 80, 80, 20));
   }

   public void m_6305_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      this.m_96558_(pPoseStack, -100);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      pPoseStack.m_85836_();
      pPoseStack.m_85837_((double)this.leftPos, (double)this.topPos, 0.0);
      this.m_93228_(pPoseStack, 0, 0, 0, 0, this.imageWidth, this.imageHeight);

      for (int i = 0; i < 3; i++) {
         Enchantment clue = Enchantment.m_44697_(this.clues[i]);
         int u = 199;
         int v = 225;
         if (clue == null) {
            u += 19;
            v += 16;
         } else if (this.selectedSlot == i || this.isHovering(8, 18 + 19 * i, 18, 18, (double)pMouseX, (double)pMouseY)) {
            u += 38;
         }

         this.m_93228_(pPoseStack, 8, 18 + 19 * i, 224, u, 18, 19);
         this.m_93228_(pPoseStack, 9, 22 + 18 * i + i, 16 * i, v, 16, 16);
      }

      int scrollbarPos = (int)(128.0F * this.scrollOffs);
      this.m_93228_(pPoseStack, 220, 18 + scrollbarPos, 244, 173 + (this.isScrollBarActive() ? 0 : 15), 12, 15);
      RealEnchantmentHelper.ArcanaEnchantmentData hover = this.getHovered((double)pMouseX, (double)pMouseY);

      for (int i = 0; i < 11 && this.enchantments.size() - 1 >= i; i++) {
         int v = 173;
         if (hover == this.enchantments.get(this.startIndex + i)) {
            v += 13;
         }

         this.m_93228_(pPoseStack, 89, 18 + 13 * i, 114, v, 128, 13);
      }

      for (int i = 0; i < 11 && this.enchantments.size() - 1 >= i; i++) {
         RealEnchantmentHelper.ArcanaEnchantmentData data = this.enchantments.get(this.startIndex + i);
         this.f_96547_.m_92883_(pPoseStack, I18n.m_118938_(data.data.f_44947_.m_44704_(), new Object[0]), 91.0F, (float)(21 + 13 * i), 16777088);
      }

      List<Component> list = new ArrayList<>();
      ApothEnchantmentMenu.Arcana a = ApothEnchantmentMenu.Arcana.getForThreshold(((ApothEnchantmentMenu)this.parent.m_6262_()).arcana.get());
      list.add(Component.m_237115_("info.apotheosis.weights").m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.YELLOW}));
      list.add(
         Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.common", new Object[0]), a.rarities[0]})
            .m_130940_(ChatFormatting.GRAY)
      );
      list.add(
         Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.uncommon", new Object[0]), a.rarities[1]})
            .m_130940_(ChatFormatting.GREEN)
      );
      list.add(
         Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.rare", new Object[0]), a.rarities[2]})
            .m_130940_(ChatFormatting.BLUE)
      );
      list.add(
         Component.m_237110_("info.apotheosis.weight", new Object[]{I18n.m_118938_("rarity.enchantment.very_rare", new Object[0]), a.rarities[3]})
            .m_130940_(ChatFormatting.GOLD)
      );
      this.m_96597_(pPoseStack, list, a == ApothEnchantmentMenu.Arcana.MAX ? -2 : 1, 120);
      this.f_96547_.m_92889_(pPoseStack, this.f_96539_, 7.0F, 4.0F, 4210752);
      pPoseStack.m_85849_();
      pPoseStack.m_85837_(0.0, 0.0, 10.0);

      for (int i = 0; i < 3; i++) {
         if (this.isHovering(8, 18 + 19 * i, 18, 18, (double)pMouseX, (double)pMouseY)) {
            list.clear();
            list.add(
               Component.m_237110_("info.apotheosis.enchinfo_slot", new Object[]{i + 1})
                  .m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE})
            );
            list.add(Component.m_237110_("info.apotheosis.enchinfo_level", new Object[]{this.costs[i]}).m_130940_(ChatFormatting.GREEN));
            list.add(Component.m_237110_("info.apotheosis.enchinfo_minpow", new Object[]{this.powers[i][0]}).m_130940_(ChatFormatting.DARK_RED));
            list.add(Component.m_237110_("info.apotheosis.enchinfo_maxpow", new Object[]{this.powers[i][1]}).m_130940_(ChatFormatting.BLUE));
            this.m_96597_(pPoseStack, list, pMouseX, pMouseY);
         }
      }

      if (hover != null) {
         list.clear();
         list.add(Component.m_237115_(hover.data.f_44947_.m_44704_()).m_130944_(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.UNDERLINE}));
         list.add(
            Component.m_237110_("info.apotheosis.enchinfo_level", new Object[]{Component.m_237115_("enchantment.level." + hover.data.f_44948_)})
               .m_130940_(ChatFormatting.DARK_AQUA)
         );
         Component rarity = Component.m_237115_("rarity.enchantment." + hover.data.f_44947_.m_44699_().name().toLowerCase(Locale.ROOT))
            .m_130940_(colors[hover.data.f_44947_.m_44699_().ordinal()]);
         list.add(Component.m_237110_("info.apotheosis.enchinfo_rarity", new Object[]{rarity}).m_130940_(ChatFormatting.DARK_AQUA));
         list.add(
            Component.m_237110_(
                  "info.apotheosis.enchinfo_chance",
                  new Object[]{String.format("%.2f", 100.0F * (float)hover.m_142631_().m_146281_() / (float)WeightedRandom.m_146312_(this.enchantments)) + "%"}
               )
               .m_130940_(ChatFormatting.DARK_AQUA)
         );
         if (I18n.m_118936_(hover.data.f_44947_.m_44704_() + ".desc")) {
            list.add(Component.m_237115_(hover.data.f_44947_.m_44704_() + ".desc").m_130940_(ChatFormatting.DARK_AQUA));
         }

         List<Enchantment> excls = this.exclusions.get(hover.data.f_44947_);
         if (!excls.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (int ix = 0; ix < excls.size(); ix++) {
               sb.append(I18n.m_118938_(excls.get(ix).m_44704_(), new Object[0]));
               if (ix != excls.size() - 1) {
                  sb.append(", ");
               }
            }

            list.add(Component.m_237110_("Exclusive With: %s", new Object[]{sb.toString()}).m_130940_(ChatFormatting.RED));
         }

         this.m_96597_(pPoseStack, list, pMouseX, pMouseY);
      }

      this.m_93250_(2001);
      this.f_96542_.f_115093_ = 2001.0F;
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      Minecraft.m_91087_().m_91291_().m_115203_(this.toEnchant, this.leftPos + 49, this.topPos + 39);
      Minecraft.m_91087_().m_91291_().m_115169_(this.f_96547_, this.toEnchant, this.leftPos + 49, this.topPos + 39);
      this.f_96542_.f_115093_ = 0.0F;
      this.m_93250_(0);
      pPoseStack.m_85837_(0.0, 0.0, -10.0);
      super.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      this.scrolling = false;
      int left = this.leftPos + 220;
      int top = this.topPos + 18;
      if (pMouseX >= (double)left && pMouseX < (double)(left + 12) && pMouseY >= (double)top && pMouseY < (double)(top + 143)) {
         this.scrolling = true;
         this.m_7979_(pMouseX, pMouseY, 0, pMouseX, pMouseY);
      }

      for (int i = 0; i < 3; i++) {
         Enchantment clue = Enchantment.m_44697_(this.clues[i]);
         if (this.selectedSlot != i && clue != null && this.isHovering(8, 18 + 19 * i, 18, 18, pMouseX, pMouseY)) {
            this.selectedSlot = i;
            this.slider.setValue((this.slider.min() + this.slider.max()) / 2);
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0F));
            return true;
         }
      }

      return super.m_6375_(pMouseX, pMouseY, pButton);
   }

   public boolean m_7979_(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
      if (this.scrolling && this.isScrollBarActive()) {
         int i = this.topPos + 18;
         int j = i + 143;
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
      return this.enchantments.size() > 11;
   }

   protected int getOffscreenRows() {
      return this.enchantments.size() - 11;
   }

   protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
      int i = this.leftPos;
      int j = this.topPos;
      pMouseX -= (double)i;
      pMouseY -= (double)j;
      return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
   }

   protected void recomputeEnchantments() {
      ApothEnchantmentMenu.Arcana arc = ApothEnchantmentMenu.Arcana.getForThreshold(((ApothEnchantmentMenu)this.parent.m_6262_()).arcana.get());
      this.enchantments = RealEnchantmentHelper.getAvailableEnchantmentResults(this.currentPower, this.toEnchant, false)
         .stream()
         .map(e -> new RealEnchantmentHelper.ArcanaEnchantmentData(arc, e))
         .collect(Collectors.toList());
      if (this.startIndex + 11 >= this.enchantments.size()) {
         this.startIndex = 0;
         this.scrollOffs = 0.0F;
      }

      this.exclusions.clear();

      for (RealEnchantmentHelper.ArcanaEnchantmentData d : this.enchantments) {
         List<Enchantment> excls = new ArrayList<>();

         for (RealEnchantmentHelper.ArcanaEnchantmentData d2 : this.enchantments) {
            if (d != d2 && !d.data.f_44947_.m_44695_(d2.data.f_44947_)) {
               excls.add(d2.data.f_44947_);
            }
         }

         this.exclusions.put(d.data.f_44947_, excls);
      }
   }

   public boolean m_7043_() {
      return false;
   }

   protected RealEnchantmentHelper.ArcanaEnchantmentData getHovered(double mouseX, double mouseY) {
      for (int i = 0; i < 11 && this.enchantments.size() - 1 >= i; i++) {
         if (this.isHovering(89, 18 + i * 13, 128, 13, mouseX, mouseY)) {
            return this.enchantments.get(this.startIndex + i);
         }
      }

      return null;
   }

   public class PowerSlider extends AbstractSliderButton {
      public PowerSlider(int x, int y, int width, int height) {
         super(x, y, width, height, Component.m_237119_(), 0.0);
         if (EnchantingInfoScreen.this.selectedSlot != -1 && this.f_93577_ == 0.0) {
            this.f_93577_ = this.normalizeValue(
               EnchantingInfoScreen.this.currentPower == 0 ? (double)((this.max() + this.min()) / 2) : (double)EnchantingInfoScreen.this.currentPower
            );
            this.m_5697_();
         }

         this.m_5695_();
      }

      protected void m_5695_() {
         this.m_93666_(Component.m_237110_("info.apotheosis.slider_power", new Object[]{EnchantingInfoScreen.this.currentPower}));
      }

      protected void m_5697_() {
         EnchantingInfoScreen.this.currentPower = this.denormalizeValue(this.f_93577_);
         EnchantingInfoScreen.this.recomputeEnchantments();
      }

      public void setValue(int value) {
         if (!EnchantingInfoScreen.this.m_7282_()) {
            this.f_93577_ = this.normalizeValue((double)value);
            this.m_5697_();
            this.m_5695_();
         }
      }

      public double normalizeValue(double value) {
         return Mth.m_14008_((this.snapToStepClamp(value) - (double)this.min()) / (double)(this.max() - this.min()), 0.0, 1.0);
      }

      public int denormalizeValue(double value) {
         return (int)this.snapToStepClamp(Mth.m_14139_(Mth.m_14008_(value, 0.0, 1.0), (double)this.min(), (double)this.max()));
      }

      private double snapToStepClamp(double valueIn) {
         if (this.step() > 0.0F) {
            valueIn = (double)(this.step() * (float)Math.round(valueIn / (double)this.step()));
         }

         return Mth.m_14008_(valueIn, (double)this.min(), (double)this.max());
      }

      private int min() {
         return EnchantingInfoScreen.this.powers[EnchantingInfoScreen.this.selectedSlot][0];
      }

      private int max() {
         return EnchantingInfoScreen.this.powers[EnchantingInfoScreen.this.selectedSlot][1];
      }

      private float step() {
         return 1.0F / (float)Math.max(this.max() - this.min(), 1);
      }
   }
}
