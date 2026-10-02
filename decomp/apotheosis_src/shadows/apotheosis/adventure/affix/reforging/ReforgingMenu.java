package shadows.apotheosis.adventure.affix.reforging;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.util.ApothMiscUtil;
import shadows.placebo.cap.InternalItemHandler;
import shadows.placebo.container.BlockEntityContainer;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.container.PlaceboContainerMenu.UpdatingSlot;
import shadows.placebo.util.EnchantmentUtils;

public class ReforgingMenu extends BlockEntityContainer<ReforgingTableTile> {
   public static final String REFORGE_SEED = "apoth_reforge_seed";
   protected final Player player;
   protected InternalItemHandler itemInv = new InternalItemHandler(1);
   protected final RandomSource random = new XoroshiroRandomSource(0L);
   protected final int[] seed = new int[2];
   protected final int[] costs = new int[3];
   protected DataSlot needsReset = DataSlot.m_39401_();

   public ReforgingMenu(int id, Inventory inv, BlockPos pos) {
      super((MenuType)Apoth.Menus.REFORGING.get(), id, inv, pos);
      this.player = inv.f_35978_;
      this.m_38897_(new UpdatingSlot(this.itemInv, 0, 25, 24, stack -> !LootCategory.forItem(stack).isNone()) {
         public int m_6641_() {
            return 1;
         }

         public int m_5866_(ItemStack pStack) {
            return 1;
         }
      });
      this.m_38897_(new UpdatingSlot(this, ((ReforgingTableTile)this.tile).inv, 0, 15, 45, ((ReforgingTableTile)this.tile)::isValidRarityMat));
      this.m_38897_(new UpdatingSlot(this, ((ReforgingTableTile)this.tile).inv, 1, 35, 45, stack -> stack.m_41720_() == Apoth.Items.GEM_DUST.get()));
      this.addPlayerSlots(inv, 8, 84);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && !LootCategory.forItem(stack).isNone(), 0, 1);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && ((ReforgingTableTile)this.tile).isValidRarityMat(stack), 1, 2);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && stack.m_41720_() == Apoth.Items.GEM_DUST.get(), 2, 3);
      this.mover.registerRule((stack, slot) -> slot < this.playerInvStart, this.playerInvStart, this.hotbarStart + 9);
      this.registerInvShuffleRules();
      this.updateSeed();
      this.m_38895_(this.needsReset);
      this.m_38895_(DataSlot.m_39406_(this.seed, 0));
      this.m_38895_(DataSlot.m_39406_(this.seed, 1));
      this.m_38895_(DataSlot.m_39406_(this.costs, 0));
      this.m_38895_(DataSlot.m_39406_(this.costs, 1));
      this.m_38895_(DataSlot.m_39406_(this.costs, 2));
   }

   public void m_6877_(Player pPlayer) {
      super.m_6877_(pPlayer);
      this.m_150411_(pPlayer, new RecipeWrapper(this.itemInv));
   }

   protected void updateSeed() {
      int seed = this.player.getPersistentData().m_128451_("apoth_reforge_seed");
      if (seed == 0) {
         seed = this.player.f_19796_.m_188502_();
         this.player.getPersistentData().m_128405_("apoth_reforge_seed", seed);
      }

      this.seed[0] = ContainerUtil.split(seed, false);
      this.seed[1] = ContainerUtil.split(seed, true);
   }

   public int getSeed() {
      return ContainerUtil.merge(this.seed[0], this.seed[1], true);
   }

   public boolean m_6366_(Player player, int slot) {
      if (slot >= 0 && slot < 3) {
         ItemStack input = this.m_38853_(0).m_7993_();
         LootRarity rarity = this.getRarity();
         ReforgingRecipe recipe = ((ReforgingTableTile)this.tile).getRecipeFor(rarity);
         if (recipe != null && !input.m_41619_() && !this.needsReset()) {
            int dust = this.getDustCount();
            int dustCost = this.getDustCost(slot);
            int mats = this.getMatCount();
            int matCost = this.getMatCost(slot);
            int levels = this.player.f_36078_;
            int levelCost = this.getLevelCost(slot);
            if ((dust < dustCost || mats < matCost || levels < levelCost) && !player.m_7500_()) {
               return false;
            } else {
               if (!player.f_19853_.f_46443_) {
                  RandomSource rand = this.random;
                  rand.m_188584_((long)(this.getSeed() ^ ForgeRegistries.ITEMS.getKey(input.m_41720_()).hashCode() + slot));
                  ItemStack output = LootController.createLootItem(input.m_41777_(), rarity, rand);
                  this.m_38853_(0).m_5852_(output);
                  if (!player.m_7500_()) {
                     this.m_38853_(1).m_7993_().m_41774_(matCost);
                     this.m_38853_(2).m_7993_().m_41774_(dustCost);
                  }

                  EnchantmentUtils.chargeExperience(player, ApothMiscUtil.getExpCostForSlot(levelCost, slot));
                  player.getPersistentData().m_128405_("apoth_reforge_seed", player.f_19796_.m_188502_());
                  this.updateSeed();
                  this.needsReset.m_6422_(1);
               }

               player.m_5496_(SoundEvents.f_11862_, 0.99F, this.level.f_46441_.m_188501_() * 0.25F + 1.0F);
               player.m_5496_(SoundEvents.f_144054_, 0.34F, this.level.f_46441_.m_188501_() * 0.2F + 0.8F);
               player.m_5496_(SoundEvents.f_12471_, 0.45F, this.level.f_46441_.m_188501_() * 0.5F + 0.75F);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return super.m_6366_(player, slot);
      }
   }

   public int getMatCount() {
      return this.m_38853_(1).m_7993_().m_41613_();
   }

   public int getDustCount() {
      return this.m_38853_(2).m_7993_().m_41613_();
   }

   @Nullable
   public LootRarity getRarity() {
      ItemStack s = this.m_38853_(1).m_7993_();
      return s.m_41619_() ? null : LootRarity.getMaterialRarity(s);
   }

   public int getDustCost(int slot) {
      return this.costs[0] * ++slot;
   }

   public int getMatCost(int slot) {
      return this.costs[1] * ++slot;
   }

   public int getLevelCost(int slot) {
      return this.costs[2] * ++slot;
   }

   public boolean needsReset() {
      return this.needsReset.m_6501_() != 0;
   }

   public void m_6199_(Container pContainer) {
      LootRarity rarity = this.getRarity();
      if (rarity != null) {
         ReforgingRecipe recipe = ((ReforgingTableTile)this.tile).getRecipeFor(rarity);
         if (recipe != null) {
            this.costs[0] = recipe.dustCost();
            this.costs[1] = recipe.matCost();
            this.costs[2] = recipe.levelCost();
         }
      }

      if (this.needsReset()) {
         this.needsReset.m_6422_(0);
      }

      super.m_6199_(pContainer);
      ((ReforgingTableTile)this.tile).m_6596_();
   }
}
