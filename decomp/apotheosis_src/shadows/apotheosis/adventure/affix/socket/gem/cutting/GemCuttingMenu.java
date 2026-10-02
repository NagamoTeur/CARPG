package shadows.apotheosis.adventure.affix.socket.gem.cutting;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.advancements.AdvancementTriggers;
import shadows.apotheosis.adventure.affix.socket.gem.GemInstance;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.cap.InternalItemHandler;
import shadows.placebo.container.PlaceboContainerMenu;
import shadows.placebo.container.PlaceboContainerMenu.UpdatingSlot;

public class GemCuttingMenu extends PlaceboContainerMenu {
   public static final int NEXT_MAT_COST = 1;
   public static final int STD_MAT_COST = 3;
   public static final int PREV_MAT_COST = 9;
   public static final List<GemCuttingMenu.GemCuttingRecipe> RECIPES = new ArrayList<>();
   protected final Player player;
   protected final ContainerLevelAccess access;
   protected final InternalItemHandler inv = new InternalItemHandler(4) {
      public int getSlotLimit(int slot) {
         return slot == 0 ? 1 : super.getSlotLimit(slot);
      }
   };

   public GemCuttingMenu(int id, Inventory playerInv) {
      this(id, playerInv, ContainerLevelAccess.f_39287_);
   }

   public GemCuttingMenu(int id, Inventory playerInv, ContainerLevelAccess access) {
      super((MenuType)Apoth.Menus.GEM_CUTTING.get(), id, playerInv);
      this.player = playerInv.f_35978_;
      this.access = access;
      this.m_38897_(new UpdatingSlot(this, this.inv, 0, 53, 25, stack -> GemItem.getGem(stack) != null));
      this.m_38897_(new UpdatingSlot(this, this.inv, 1, 12, 25, stack -> stack.m_41720_() == Apoth.Items.GEM_DUST.get()));
      this.m_38897_(new UpdatingSlot(this, this.inv, 2, 53, 68, this::matchesMainGem));
      this.m_38897_(new UpdatingSlot(this, this.inv, 3, 94, 25, this::isValidMaterial));
      this.addPlayerSlots(playerInv, 8, 98);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && this.inv.getStackInSlot(0).m_41619_() && isValidMainGem(stack), 0, 1);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && stack.m_41720_() == Apoth.Items.GEM_DUST.get(), 1, 2);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && this.matchesMainGem(stack), 2, 3);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && this.isValidMaterial(stack), 3, 4);
      this.mover.registerRule((stack, slot) -> slot < this.playerInvStart, this.playerInvStart, this.hotbarStart + 9);
      this.registerInvShuffleRules();
   }

   public boolean m_6366_(Player player, int id) {
      if (id == 0) {
         ItemStack gem = this.inv.getStackInSlot(0);
         ItemStack left = this.inv.getStackInSlot(1);
         ItemStack bot = this.inv.getStackInSlot(2);
         ItemStack right = this.inv.getStackInSlot(3);

         for (GemCuttingMenu.GemCuttingRecipe r : RECIPES) {
            if (r.matches(gem, left, bot, right)) {
               ItemStack out = r.getResult(gem, left, bot, right);
               r.decrementInputs(gem, left, bot, right);
               this.inv.setStackInSlot(0, out);
               this.level
                  .m_5594_(
                     player, player.m_20183_(), SoundEvents.f_144242_, SoundSource.BLOCKS, 1.0F, 1.5F + 0.35F * (1.0F - 2.0F * this.level.f_46441_.m_188501_())
                  );
               AdvancementTriggers.GEM_CUT.trigger((ServerPlayer)player, out, GemItem.getLootRarity(out));
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isValidMainGem(ItemStack stack) {
      GemInstance inst = GemInstance.unsocketed(stack);
      return inst.isValidUnsocketed() && !inst.isMaxRarity();
   }

   protected boolean isValidMaterial(ItemStack stack) {
      GemInstance mainGem = GemInstance.unsocketed(this.inv.getStackInSlot(0));
      if (!mainGem.isValidUnsocketed()) {
         return false;
      } else {
         LootRarity rarity = LootRarity.getMaterialRarity(stack);
         return rarity != null && Math.abs(rarity.ordinal() - mainGem.rarity().ordinal()) <= 1;
      }
   }

   protected boolean matchesMainGem(ItemStack stack) {
      GemInstance gem = GemInstance.unsocketed(stack);
      GemInstance mainGem = GemInstance.unsocketed(this.inv.getStackInSlot(0));
      return gem.isValidUnsocketed() && mainGem.isValidUnsocketed() && gem.gem() == mainGem.gem() && gem.rarity() == mainGem.rarity();
   }

   public boolean m_6875_(Player pPlayer) {
      return (Boolean)this.access.m_39299_((level, pos) -> level.m_8055_(pos).m_60734_() == Apoth.Blocks.GEM_CUTTING_TABLE.get(), true);
   }

   public void m_6877_(Player pPlayer) {
      super.m_6877_(pPlayer);
      this.access.m_39292_((level, pos) -> this.m_150411_(pPlayer, new RecipeWrapper(this.inv)));
   }

   public static int getDustCost(LootRarity gemRarity) {
      return 1 + gemRarity.ordinal() * 2;
   }

   static {
      RECIPES.add(new GemCuttingMenu.RarityUpgrade());
   }

   public interface GemCuttingRecipe {
      boolean matches(ItemStack var1, ItemStack var2, ItemStack var3, ItemStack var4);

      ItemStack getResult(ItemStack var1, ItemStack var2, ItemStack var3, ItemStack var4);

      void decrementInputs(ItemStack var1, ItemStack var2, ItemStack var3, ItemStack var4);
   }

   public static class RarityUpgrade implements GemCuttingMenu.GemCuttingRecipe {
      @Override
      public boolean matches(ItemStack gem, ItemStack left, ItemStack bot, ItemStack right) {
         GemInstance g = GemInstance.unsocketed(gem);
         GemInstance g2 = GemInstance.unsocketed(bot);
         if (!g.isValidUnsocketed() || !g2.isValidUnsocketed() || g.gem() != g2.gem() || g.rarity() != g2.rarity()) {
            return false;
         } else if (g.isMaxRarity()) {
            return false;
         } else if (left.m_41720_() != Apoth.Items.GEM_DUST.get() || left.m_41613_() < GemCuttingMenu.getDustCost(g.rarity())) {
            return false;
         } else if (!LootRarity.isRarityMat(right)) {
            return false;
         } else {
            LootRarity matRarity = LootRarity.getMaterialRarity(right);
            LootRarity gemRarity = g.rarity();
            if (matRarity == gemRarity) {
               return right.m_41613_() >= 3;
            } else {
               return matRarity == gemRarity.next() ? right.m_41613_() >= 1 : matRarity == gemRarity.prev() && right.m_41613_() >= 9;
            }
         }
      }

      @Override
      public ItemStack getResult(ItemStack gem, ItemStack left, ItemStack bot, ItemStack right) {
         ItemStack out = gem.m_41777_();
         GemItem.setLootRarity(out, GemItem.getLootRarity(out).next());
         return out;
      }

      @Override
      public void decrementInputs(ItemStack gem, ItemStack left, ItemStack bot, ItemStack right) {
         LootRarity matRarity = LootRarity.getMaterialRarity(right);
         LootRarity gemRarity = GemInstance.unsocketed(gem).rarity();
         gem.m_41774_(1);
         left.m_41774_(GemCuttingMenu.getDustCost(gemRarity));
         bot.m_41774_(1);
         right.m_41774_(matRarity == gemRarity ? 3 : (matRarity == gemRarity.next() ? 1 : 9));
      }
   }
}
