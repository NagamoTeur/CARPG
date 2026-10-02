package com.github.L_Ender.cataclysm.items;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class ModernRemantBucket extends MobBucketItem {
   public ModernRemantBucket(Supplier<? extends EntityType<?>> fishTypeIn, Fluid fluid, Properties builder) {
      super(fishTypeIn, () -> fluid, () -> SoundEvents.f_11779_, builder.m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
      ItemStack itemStack = player.m_21120_(interactionHand);
      BlockHitResult blockHitResult = m_41435_(level, player, net.minecraft.world.level.ClipContext.Fluid.NONE);
      if (blockHitResult.m_6662_() == Type.MISS) {
         return InteractionResultHolder.m_19098_(itemStack);
      } else if (blockHitResult.m_6662_() != Type.BLOCK) {
         return InteractionResultHolder.m_19098_(itemStack);
      } else {
         BlockPos blockPos = blockHitResult.m_82425_();
         if (level.m_7966_(player, blockPos)) {
            this.m_142131_(player, level, itemStack, blockPos);
            if (player instanceof ServerPlayer) {
               CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)player, blockPos, itemStack);
            }

            player.m_36246_(Stats.f_12982_.m_12902_(this));
            return InteractionResultHolder.m_19092_(m_40699_(itemStack, player), level.m_5776_());
         } else {
            return InteractionResultHolder.m_19100_(itemStack);
         }
      }
   }

   public boolean m_142073_(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult) {
      BlockState blockstate = level.m_8055_(blockPos);
      if (!blockstate.m_60795_() && !blockstate.m_60722_(Fluids.f_76191_)) {
         return false;
      } else {
         this.m_7718_(player, level, blockPos);
         return true;
      }
   }
}
