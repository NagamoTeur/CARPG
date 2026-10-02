package shadows.apotheosis.adventure.affix.reforging;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.block_entity.TickingBlockEntity;
import shadows.placebo.cap.InternalItemHandler;

public class ReforgingTableTile extends BlockEntity implements TickingBlockEntity {
   public int time = 0;
   public boolean step1 = true;
   protected InternalItemHandler inv = new InternalItemHandler(2) {
      public boolean isItemValid(int slot, ItemStack stack) {
         return slot == 0 ? ReforgingTableTile.this.isValidRarityMat(stack) : stack.m_150930_((Item)Apoth.Items.GEM_DUST.get());
      }

      protected void onContentsChanged(int slot) {
         ReforgingTableTile.this.m_6596_();
      }
   };
   LazyOptional<IItemHandler> invCap = LazyOptional.of(() -> this.inv);

   public ReforgingTableTile(BlockPos pWorldPosition, BlockState pBlockState) {
      super((BlockEntityType)Apoth.Tiles.REFORGING_TABLE.get(), pWorldPosition, pBlockState);
   }

   public LootRarity getMaxRarity() {
      return ((ReforgingTableBlock)this.m_58900_().m_60734_()).getMaxRarity();
   }

   public boolean isValidRarityMat(ItemStack stack) {
      LootRarity rarity = LootRarity.getMaterialRarity(stack);
      return rarity != null && this.getMaxRarity().isAtLeast(rarity) && this.getRecipeFor(rarity) != null;
   }

   @Nullable
   public ReforgingRecipe getRecipeFor(LootRarity rarity) {
      return this.f_58857_.m_7465_().m_44013_(Apoth.RecipeTypes.REFORGING).stream().filter(r -> r.rarity() == rarity).findFirst().orElse(null);
   }

   public void clientTick(Level pLevel, BlockPos pPos, BlockState pState) {
      Player player = pLevel.m_45924_((double)pPos.m_123341_() + 0.5, (double)pPos.m_123342_() + 0.5, (double)pPos.m_123343_() + 0.5, 4.0, false);
      if (player != null) {
         this.time++;
      } else {
         if (this.time == 0 && this.step1) {
            return;
         }

         this.time++;
      }

      if (this.step1 && this.time == 59) {
         this.step1 = false;
         this.time = 0;
      } else if (this.time == 4 && !this.step1) {
         RandomSource rand = pLevel.f_46441_;

         for (int i = 0; i < 6; i++) {
            pLevel.m_7106_(
               ParticleTypes.f_123797_,
               (double)pPos.m_123341_() + 0.5 - 0.1 * rand.m_188500_(),
               (double)pPos.m_123342_() + 0.8125,
               (double)pPos.m_123343_() + 0.5 + 0.1 * rand.m_188500_(),
               0.0,
               0.0,
               0.0
            );
         }

         pLevel.m_7785_(
            (double)pPos.m_123341_(),
            (double)pPos.m_123342_(),
            (double)pPos.m_123343_(),
            SoundEvents.f_11669_,
            SoundSource.BLOCKS,
            0.03F,
            1.7F + rand.m_188501_() * 0.2F,
            true
         );
         this.step1 = true;
         this.time = 0;
      }
   }

   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("inventory", this.inv.serializeNBT());
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.inv.deserializeNBT(tag.m_128469_("inventory"));
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.invCap.cast() : super.getCapability(cap, side);
   }

   public void invalidateCaps() {
      super.invalidateCaps();
      this.invCap.invalidate();
   }

   public void reviveCaps() {
      super.reviveCaps();
      this.invCap = LazyOptional.of(() -> this.inv);
   }
}
