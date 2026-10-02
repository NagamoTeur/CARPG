package shadows.apotheosis.adventure.affix.reforging;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;

public class ReforgingScreen extends AbstractContainerScreen<ReforgingMenu> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("apotheosis", "textures/gui/reforge.png");
   protected ItemStack[] choices = new ItemStack[3];
   protected ItemStack lastInput = ItemStack.f_41583_;
   protected LootRarity lastRarity = null;
   protected Component title;

   public ReforgingScreen(ReforgingMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.f_97729_ = 5;
      Arrays.fill(this.choices, ItemStack.f_41583_);
      this.title = Component.m_237115_("container.apotheosis.reforge");
   }

   public boolean shouldRecompute() {
      ItemStack input = ((ReforgingMenu)this.f_97732_).m_38853_(0).m_7993_();
      LootRarity rarity = ((ReforgingMenu)this.m_6262_()).getRarity();
      return !ItemStack.m_150942_(input, this.lastInput) || this.lastRarity != rarity;
   }

   public void recomputeChoices() {
      ItemStack input = ((ReforgingMenu)this.f_97732_).m_38853_(0).m_7993_();
      LootRarity rarity = ((ReforgingMenu)this.m_6262_()).getRarity();
      if (!input.m_41619_() && rarity != null) {
         RandomSource rand = ((ReforgingMenu)this.f_97732_).random;

         for (int i = 0; i < 3; i++) {
            rand.m_188584_((long)(((ReforgingMenu)this.f_97732_).getSeed() ^ ForgeRegistries.ITEMS.getKey(input.m_41720_()).hashCode() + i));
            this.choices[i] = LootController.createLootItem(input.m_41777_(), rarity, rand);
         }
      } else {
         Arrays.fill(this.choices, ItemStack.f_41583_);
      }

      this.lastInput = input.m_41777_();
      this.lastRarity = rarity;
   }

   public void m_6305_(PoseStack pPoseStack, int x, int y, float pPartialTick) {
      if (this.shouldRecompute()) {
         this.recomputeChoices();
      }

      this.m_7333_(pPoseStack);
      super.m_6305_(pPoseStack, x, y, pPartialTick);
      RenderSystem.m_69461_();
      this.m_7025_(pPoseStack, x, y);
      int xCenter = (this.f_96543_ - this.f_97726_) / 2;
      int yCenter = (this.f_96544_ - this.f_97727_) / 2;
      int dust = ((ReforgingMenu)this.f_97732_).getDustCount();
      int mats = ((ReforgingMenu)this.f_97732_).getMatCount();
      int levels = ((ReforgingMenu)this.f_97732_).player.f_36078_;

      for (int slot = 0; slot < 3; slot++) {
         ItemStack choice = this.choices[slot];
         if (!choice.m_41619_() && !((ReforgingMenu)this.f_97732_).needsReset()) {
            List<Component> tooltips = new ArrayList<>();
            int dustCost = ((ReforgingMenu)this.f_97732_).getDustCost(slot);
            int matCost = ((ReforgingMenu)this.f_97732_).getMatCost(slot);
            int levelCost = ((ReforgingMenu)this.f_97732_).getLevelCost(slot);
            tooltips.add(Component.m_237115_("text.apotheosis.reforge_cost").m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.UNDERLINE}));
            tooltips.add(CommonComponents.f_237098_);
            if (dustCost > 0) {
               tooltips.add(
                  Component.m_237110_("%s %s", new Object[]{dustCost, ((Item)Apoth.Items.GEM_DUST.get()).m_7626_(ItemStack.f_41583_)})
                     .m_130940_(dust < dustCost ? ChatFormatting.RED : ChatFormatting.GRAY)
               );
            }

            if (matCost > 0) {
               tooltips.add(
                  Component.m_237110_("%s %s", new Object[]{matCost, ((ReforgingMenu)this.f_97732_).m_38853_(1).m_7993_().m_41786_()})
                     .m_130940_(mats < matCost ? ChatFormatting.RED : ChatFormatting.GRAY)
               );
            }

            String key = levels >= levelCost
               ? (levelCost == 1 ? "container.enchant.level.one" : "container.enchant.level.many")
               : "container.enchant.level.requirement";
            tooltips.add(Component.m_237110_(key, new Object[]{levelCost}).m_130940_(levels < levelCost ? ChatFormatting.RED : ChatFormatting.GRAY));
            int k2 = x - (xCenter + 60);
            int l2 = y - (yCenter + 14 + 19 * slot);
            if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
               this.renderTooltip(pPoseStack, choice.m_41651_(((ReforgingMenu)this.f_97732_).player, Default.NORMAL), Optional.empty(), x, y, choice);
               pPoseStack.m_85837_(0.0, 0.0, -100.0);
               this.drawOnLeft(pPoseStack, tooltips, this.getGuiTop() + 29);
               pPoseStack.m_85837_(0.0, 0.0, 100.0);
            }
         }
      }
   }

   protected void m_7286_(PoseStack stack, float partials, int x, int y) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      int xCenter = (this.f_96543_ - this.f_97726_) / 2;
      int slotsX = xCenter + 60;
      int yCenter = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(stack, xCenter, yCenter, 0, 0, this.f_97726_, this.f_97727_);
      LootRarity rarity = ((ReforgingMenu)this.f_97732_).getRarity();
      if (!((ReforgingMenu)this.f_97732_).m_38853_(0).m_7993_().m_41619_() && rarity != null && !((ReforgingMenu)this.f_97732_).needsReset()) {
         int dust = ((ReforgingMenu)this.f_97732_).getDustCount();
         int mats = ((ReforgingMenu)this.f_97732_).getMatCount();
         int levels = ((ReforgingMenu)this.f_97732_).player.f_36078_;
         EnchantmentNames.m_98734_().m_98735_((long)((ReforgingMenu)this.f_97732_).getSeed());

         for (int slot = 0; slot < 3; slot++) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.m_157456_(0, TEXTURE);
            int dustCost = ((ReforgingMenu)this.f_97732_).getDustCost(slot);
            int matCost = ((ReforgingMenu)this.f_97732_).getMatCost(slot);
            int levelCost = ((ReforgingMenu)this.f_97732_).getLevelCost(slot);
            int maxCost = ((ReforgingMenu)this.f_97732_).getMatCost(2);
            String levelStr = levelCost + "";
            String costStr = matCost + "";
            int width = 86 - this.f_96547_.m_92895_(levelStr + maxCost);
            int randTextX = slotsX + 15 + this.f_96547_.m_92895_(maxCost + "");
            FormattedText randText = EnchantmentNames.m_98734_().m_98737_(this.f_96547_, width);
            int color = 5329233;
            if ((dust < dustCost || levels < levelCost || mats < matCost) && !this.f_96541_.f_91074_.m_150110_().f_35937_) {
               this.m_93228_(stack, slotsX, yCenter + 14 + 19 * slot, 0, 185, 108, 19);
               this.m_93228_(stack, slotsX + 1, yCenter + 15 + 19 * slot, 16 * slot, 239, 16, 16);
               this.f_96547_.m_92857_(randText, randTextX, yCenter + 16 + 19 * slot, width, color);
               color = this.darken(rarity.color().m_131265_(), 2);
            } else {
               int k2 = x - (xCenter + 60);
               int l2 = y - (yCenter + 14 + 19 * slot);
               if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                  this.m_93228_(stack, slotsX, yCenter + 14 + 19 * slot, 0, 204, 108, 19);
                  color = 16777088;
               } else {
                  this.m_93228_(stack, slotsX, yCenter + 14 + 19 * slot, 0, 166, 108, 19);
                  color = 13487565;
               }

               this.m_93228_(stack, slotsX + 1, yCenter + 15 + 19 * slot, 16 * slot, 223, 16, 16);
               this.f_96547_.m_92857_(randText, randTextX, yCenter + 16 + 19 * slot, width, color);
               color = rarity.color().m_131265_();
            }

            this.drawBorderedString(stack, costStr, slotsX + 10, yCenter + 21 + 19 * slot, color, this.darken(color, 4));
            this.drawBorderedString(
               stack, levelStr, slotsX + 106 - this.f_96547_.m_92895_(levelStr), yCenter + 16 + 19 * slot + 7, color, this.darken(color, 4)
            );
         }
      } else {
         for (int slot = 0; slot < 3; slot++) {
            this.m_93228_(stack, slotsX, yCenter + 14 + 19 * slot, 0, 185, 108, 19);
         }
      }
   }

   protected int darken(int rColor, int factor) {
      int r = rColor >> 16 & 0xFF;
      int g = rColor >> 8 & 0xFF;
      int b = rColor & 0xFF;
      r /= factor;
      g /= factor;
      b /= factor;
      return r << 16 | g << 8 | b;
   }

   protected void drawBorderedString(PoseStack stack, String str, int x, int y, int color, int shadowColor) {
      Component comp = Component.m_237113_(str);
      this.f_96547_.m_92889_(stack, comp, (float)x, (float)(y - 1), shadowColor);
      this.f_96547_.m_92889_(stack, comp, (float)(x - 1), (float)y, shadowColor);
      this.f_96547_.m_92889_(stack, comp, (float)x, (float)(y + 1), shadowColor);
      this.f_96547_.m_92889_(stack, comp, (float)(x + 1), (float)y, shadowColor);
      this.f_96547_.m_92889_(stack, comp, (float)x, (float)y, color);
   }

   protected void m_7027_(PoseStack stack, int x, int y) {
      this.f_96547_.m_92889_(stack, this.title, (float)this.f_97728_, (float)this.f_97729_, 4210752);
      this.f_96547_.m_92889_(stack, this.f_169604_, (float)this.f_97730_, (float)this.f_97731_, 4210752);
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;

      for (int k = 0; k < 3; k++) {
         double d0 = pMouseX - (double)(i + 60);
         double d1 = pMouseY - (double)(j + 14 + 19 * k);
         if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && ((ReforgingMenu)this.f_97732_).m_6366_(this.f_96541_.f_91074_, k)) {
            this.f_96541_.f_91072_.m_105208_(((ReforgingMenu)this.f_97732_).f_38840_, k);
            return true;
         }
      }

      return super.m_6375_(pMouseX, pMouseY, pButton);
   }

   public void drawOnLeft(PoseStack stack, List<Component> list, int y) {
      if (!list.isEmpty()) {
         int xPos = this.getGuiLeft() - 16 - list.stream().<Integer>map(this.f_96547_::m_92852_).max(Integer::compare).get();
         int maxWidth = 9999;
         if (xPos < 0) {
            maxWidth = this.getGuiLeft() - 6;
            xPos = -8;
         }

         List<FormattedText> split = new ArrayList<>();
         int lambdastupid = maxWidth;
         list.forEach(comp -> split.addAll(this.f_96547_.m_92865_().m_92414_(comp, lambdastupid, comp.m_7383_())));
         this.renderComponentTooltip(stack, split, xPos, y, this.f_96547_);
      }
   }
}
