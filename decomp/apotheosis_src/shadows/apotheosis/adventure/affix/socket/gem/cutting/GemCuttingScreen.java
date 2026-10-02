package shadows.apotheosis.adventure.affix.socket.gem.cutting;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingScreen;
import shadows.apotheosis.adventure.affix.socket.gem.GemInstance;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.client.GrayBufferSource;
import shadows.apotheosis.adventure.client.SimpleTexButton;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.core.attributeslib.api.AttributeHelper;
import shadows.placebo.screen.PlaceboContainerScreen;

public class GemCuttingScreen extends PlaceboContainerScreen<GemCuttingMenu> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("apotheosis", "textures/gui/gem_cutting.png");
   protected final ItemStack displayDust = ((Item)Apoth.Items.GEM_DUST.get()).m_7968_();
   protected ItemStack displayMat;
   protected SimpleTexButton upgradeBtn;

   public GemCuttingScreen(GemCuttingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
      super(pMenu, pPlayerInventory, pTitle);
      ((GemCuttingMenu)this.f_97732_).addSlotListener((id, stack) -> this.updateBtnStatus());
      this.f_97727_ = 180;
      this.f_97729_ = 5;
      this.f_97731_ = 86;
   }

   protected void m_7856_() {
      super.m_7856_();
      int left = this.getGuiLeft();
      int top = this.getGuiTop();
      this.upgradeBtn = (SimpleTexButton)this.m_142416_(
         new SimpleTexButton(left + 125, top + 30, 40, 40, 216, 0, TEXTURE, 256, 256, this::clickUpgradeBtn, Component.m_237115_("button.apotheosis.upgrade"))
            .setInactiveMessage(Component.m_237115_("button.apotheosis.upgrade.no").m_130940_(ChatFormatting.RED))
      );
      this.updateBtnStatus();
   }

   protected void clickUpgradeBtn(Button btn) {
      this.f_96541_.f_91072_.m_105208_(((GemCuttingMenu)this.f_97732_).f_38840_, 0);
      GemCuttingScreen.GemUpgradeSound.start(((GemCuttingMenu)this.f_97732_).player.m_20183_());
   }

   protected void updateBtnStatus() {
      ItemStack gem = ((GemCuttingMenu)this.f_97732_).m_38853_(0).m_7993_();
      ItemStack left = ((GemCuttingMenu)this.f_97732_).m_38853_(1).m_7993_();
      ItemStack bot = ((GemCuttingMenu)this.f_97732_).m_38853_(2).m_7993_();
      ItemStack right = ((GemCuttingMenu)this.f_97732_).m_38853_(3).m_7993_();

      for (GemCuttingMenu.GemCuttingRecipe r : GemCuttingMenu.RECIPES) {
         if (r.matches(gem, left, bot, right)) {
            this.upgradeBtn.f_93623_ = true;
            return;
         }
      }

      this.displayMat = gem.m_41619_() ? ItemStack.f_41583_ : GemItem.getLootRarity(gem).getMaterial();
      if (this.upgradeBtn != null) {
         this.upgradeBtn.f_93623_ = false;
      }
   }

   protected void m_7286_(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      int xCenter = (this.f_96543_ - this.f_97726_) / 2;
      int yCenter = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(stack, xCenter, yCenter, 0, 0, this.f_97726_, this.f_97727_);
      if (this.hasItem(0) && GemCuttingMenu.isValidMainGem(((GemCuttingMenu)this.f_97732_).m_38853_(0).m_7993_())) {
         if (!this.hasItem(1)) {
            this.renderGrayItem(this.displayDust, ((GemCuttingMenu)this.f_97732_).m_38853_(1));
         }

         if (!this.hasItem(2)) {
            this.renderGrayItem(((GemCuttingMenu)this.f_97732_).m_38853_(0).m_7993_(), ((GemCuttingMenu)this.f_97732_).m_38853_(2));
         }

         if (!this.hasItem(3)) {
            this.renderGrayItem(this.displayMat, ((GemCuttingMenu)this.f_97732_).m_38853_(3));
         }
      }
   }

   protected boolean hasItem(int slot) {
      return ((GemCuttingMenu)this.f_97732_).m_38853_(slot).m_6657_();
   }

   protected void renderGrayItem(ItemStack stack, Slot slot) {
      BakedModel model = this.f_96542_.m_174264_(stack, null, null, 0);
      SalvagingScreen.renderGuiItem(stack, this.getGuiLeft() + slot.f_40220_, this.getGuiTop() + slot.f_40221_, model, GrayBufferSource::new);
   }

   protected void m_7025_(PoseStack poseStack, int pX, int pY) {
      ItemStack gemStack = ((GemCuttingMenu)this.f_97732_).m_38853_(0).m_7993_();
      GemInstance gem = GemInstance.unsocketed(gemStack);
      GemInstance secondary = GemInstance.unsocketed(((GemCuttingMenu)this.f_97732_).m_38853_(2).m_7993_());
      List<Component> list = new ArrayList<>();
      if (gem.isValidUnsocketed()) {
         int dust = ((GemCuttingMenu)this.f_97732_).m_38853_(1).m_7993_().m_41613_();
         LootRarity rarity = gem.rarity();
         if (rarity == LootRarity.ANCIENT) {
            list.add(Component.m_237115_("text.apotheosis.no_upgrade").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE}));
         } else {
            list.add(Component.m_237115_("text.apotheosis.cut_cost").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE}));
            list.add(CommonComponents.f_237098_);
            int dustCost = GemCuttingMenu.getDustCost(rarity);
            boolean hasDust = dust > dustCost;
            list.add(
               Component.m_237110_("text.apotheosis.cost", new Object[]{dustCost, ((Item)Apoth.Items.GEM_DUST.get()).m_7626_(ItemStack.f_41583_)})
                  .m_130940_(hasDust ? ChatFormatting.GREEN : ChatFormatting.RED)
            );
            boolean hasGem2 = secondary.isValidUnsocketed() && gem.gem() == secondary.gem() && rarity == secondary.rarity();
            list.add(
               Component.m_237110_("text.apotheosis.cost", new Object[]{1, gemStack.m_41786_().getString()})
                  .m_130940_(hasGem2 ? ChatFormatting.GREEN : ChatFormatting.RED)
            );
            list.add(Component.m_237115_("text.apotheosis.one_rarity_mat").m_130940_(ChatFormatting.GRAY));
            this.addMatTooltip(rarity.next(), 1, list);
            this.addMatTooltip(rarity, 3, list);
            if (rarity != LootRarity.COMMON) {
               this.addMatTooltip(rarity.prev(), 9, list);
            }
         }
      }

      this.drawOnLeft(poseStack, list, this.getGuiTop() + 16);
      super.m_7025_(poseStack, pX, pY);
   }

   private void addMatTooltip(LootRarity rarity, int cost, List<Component> list) {
      ItemStack rarityMat = rarity.getMaterial();
      ItemStack slotMat = ((GemCuttingMenu)this.f_97732_).m_38853_(3).m_7993_();
      boolean hasMats = slotMat.m_41720_() == rarityMat.m_41720_() && slotMat.m_41613_() >= cost;
      list.add(
         AttributeHelper.list()
            .m_7220_(
               Component.m_237110_("text.apotheosis.cost", new Object[]{cost, rarityMat.m_41786_()})
                  .m_130940_(!hasMats ? ChatFormatting.RED : ChatFormatting.YELLOW)
            )
      );
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
         list.forEach(comp -> {
            if (comp.m_214077_() == ComponentContents.f_237124_) {
               split.add(comp);
            } else {
               split.addAll(this.f_96547_.m_92865_().m_92414_(comp, lambdastupid, comp.m_7383_()));
            }
         });
         this.renderComponentTooltip(stack, split, xPos, y, this.f_96547_);
      }
   }

   protected static class GemUpgradeSound extends AbstractTickableSoundInstance {
      protected int ticks = 0;
      protected float pitchOff;

      public GemUpgradeSound(BlockPos pos) {
         super(SoundEvents.f_144242_, SoundSource.BLOCKS, Minecraft.m_91087_().f_91073_.f_46441_);
         this.f_119575_ = (double)((float)pos.m_123341_() + 0.5F);
         this.f_119576_ = (double)pos.m_123342_();
         this.f_119577_ = (double)((float)pos.m_123343_() + 0.5F);
         this.f_119573_ = 1.5F;
         this.f_119574_ = 1.5F + 0.35F * (1.0F - 2.0F * this.f_235066_.m_188501_());
         this.pitchOff = 0.35F * (1.0F - 2.0F * this.f_235066_.m_188501_());
         this.f_119579_ = 999;
      }

      public void m_7788_() {
         if (this.ticks == 4 || this.ticks == 9) {
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119755_(SoundEvents.f_144242_, this.f_119574_ + this.pitchOff, 1.5F));
            this.pitchOff = -this.pitchOff;
         }

         if (this.ticks++ > 8) {
            this.m_119609_();
         }
      }

      public static void start(BlockPos pos) {
         Minecraft.m_91087_().m_91106_().m_120367_(new GemCuttingScreen.GemUpgradeSound(pos));
      }
   }
}
