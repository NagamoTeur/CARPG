package shadows.apotheosis.adventure.affix.salvaging;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import shadows.apotheosis.Apoth;
import shadows.placebo.cap.InternalItemHandler;
import shadows.placebo.container.BlockEntityContainer;
import shadows.placebo.container.FilteredSlot;
import shadows.placebo.container.PlaceboContainerMenu.UpdatingSlot;

public class SalvagingMenu extends BlockEntityContainer<SalvagingTableTile> {
   protected final Player player;
   protected final InternalItemHandler inputInv = new InternalItemHandler(15);

   public SalvagingMenu(int id, Inventory inv, BlockPos pos) {
      super((MenuType)Apoth.Menus.SALVAGE.get(), id, inv, pos);
      this.player = inv.f_35978_;

      for (int i = 0; i < 15; i++) {
         this.m_38897_(new UpdatingSlot(this.inputInv, i, 8 + i % 5 * 18, 17 + i / 5 * 18, s -> findMatch(this.level, s) != null) {
            public int m_6641_() {
               return 1;
            }

            public int m_5866_(ItemStack stack) {
               return 1;
            }
         });
      }

      for (int i = 0; i < 6; i++) {
         this.m_38897_(new FilteredSlot(((SalvagingTableTile)this.tile).output, i, 134 + i % 2 * 18, 17 + i / 2 * 18, Predicates.alwaysFalse()));
      }

      this.addPlayerSlots(inv, 8, 84);
      this.mover.registerRule((stack, slot) -> slot >= this.playerInvStart && findMatch(this.level, stack) != null, 0, 15);
      this.mover.registerRule((stack, slot) -> slot < this.playerInvStart, this.playerInvStart, this.hotbarStart + 9);
      this.registerInvShuffleRules();
   }

   public boolean m_6875_(Player player) {
      return this.level.f_46443_ ? true : this.level.m_8055_(this.pos).m_60734_() == Apoth.Blocks.SALVAGING_TABLE.get();
   }

   public void m_6877_(Player pPlayer) {
      super.m_6877_(pPlayer);
      if (!this.level.f_46443_) {
         this.m_150411_(pPlayer, new RecipeWrapper(this.inputInv));
      }
   }

   public boolean m_6366_(Player player, int id) {
      if (id == 0) {
         this.salvageAll();
         player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11862_, SoundSource.BLOCKS, 0.99F, this.level.f_46441_.m_188501_() * 0.25F + 1.0F);
         player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_144054_, SoundSource.BLOCKS, 0.34F, this.level.f_46441_.m_188501_() * 0.2F + 0.8F);
         player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_12471_, SoundSource.BLOCKS, 0.45F, this.level.f_46441_.m_188501_() * 0.5F + 0.75F);
         return true;
      } else {
         return super.m_6366_(player, id);
      }
   }

   protected void giveItem(Player player, ItemStack stack) {
      if (player.m_6084_() && (!(player instanceof ServerPlayer) || !((ServerPlayer)player).m_9232_())) {
         Inventory inventory = player.m_150109_();
         if (inventory.f_35978_ instanceof ServerPlayer) {
            inventory.m_150079_(stack);
         }
      } else {
         player.m_36176_(stack, false);
      }
   }

   protected void salvageAll() {
      for (int inSlot = 0; inSlot < 15; inSlot++) {
         Slot s = this.m_38853_(inSlot);
         ItemStack stack = s.m_7993_();
         List<ItemStack> outputs = salvageItem(this.level, stack);
         s.m_5852_(ItemStack.f_41583_);

         for (ItemStack out : outputs) {
            for (int outSlot = 0; outSlot < 6 && !out.m_41619_(); outSlot++) {
               out = ((SalvagingTableTile)this.tile).output.insertItem(outSlot, out, false);
            }

            if (!out.m_41619_()) {
               this.giveItem(this.player, out);
            }
         }
      }
   }

   public static int getSalvageCount(SalvagingRecipe.OutputData output, ItemStack stack, RandomSource rand) {
      int[] counts = getSalvageCounts(output, stack);
      return rand.m_216339_(counts[0], counts[1] + 1);
   }

   public static int[] getSalvageCounts(SalvagingRecipe.OutputData output, ItemStack stack) {
      int[] out = new int[]{output.min, output.max};
      if (stack.m_41763_()) {
         out[1] = Math.max(out[0], Math.round((float)(out[1] * (stack.m_41776_() - stack.m_41773_()) / stack.m_41776_())));
      }

      return out;
   }

   public static List<ItemStack> salvageItem(Level level, ItemStack stack) {
      SalvagingRecipe recipe = findMatch(level, stack);
      if (recipe == null) {
         return Collections.emptyList();
      } else {
         List<ItemStack> outputs = new ArrayList<>();

         for (SalvagingRecipe.OutputData d : recipe.getOutputs()) {
            ItemStack out = d.stack.m_41777_();
            out.m_41764_(getSalvageCount(d, stack, level.f_46441_));
            outputs.add(out);
         }

         return outputs;
      }
   }

   public static List<ItemStack> getBestPossibleSalvageResults(Level level, ItemStack stack) {
      SalvagingRecipe recipe = findMatch(level, stack);
      if (recipe == null) {
         return Collections.emptyList();
      } else {
         List<ItemStack> outputs = new ArrayList<>();

         for (SalvagingRecipe.OutputData d : recipe.getOutputs()) {
            ItemStack out = d.stack.m_41777_();
            out.m_41764_(getSalvageCounts(d, stack)[1]);
            outputs.add(out);
         }

         return outputs;
      }
   }

   @Nullable
   public static SalvagingRecipe findMatch(Level level, ItemStack stack) {
      for (SalvagingRecipe recipe : level.m_7465_().m_44013_(Apoth.RecipeTypes.SALVAGING)) {
         if (recipe.matches(stack)) {
            return recipe;
         }
      }

      return null;
   }
}
