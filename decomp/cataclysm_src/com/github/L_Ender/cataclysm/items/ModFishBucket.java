package com.github.L_Ender.cataclysm.items;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

public class ModFishBucket extends MobBucketItem {
   public ModFishBucket(Supplier<? extends EntityType<?>> fishTypeIn, Fluid fluid, Properties builder) {
      super(fishTypeIn, () -> fluid, () -> SoundEvents.f_11779_, builder.m_41487_(1));
   }

   public void m_142131_(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
      if (level instanceof ServerLevel) {
         this.spawnFish((ServerLevel)level, stack, pos);
         level.m_142346_(player, GameEvent.f_157810_, pos);
      }
   }

   private void spawnFish(ServerLevel serverLevel, ItemStack stack, BlockPos pos) {
      if (this.getFishType().m_20592_(serverLevel, stack, (Player)null, pos, MobSpawnType.BUCKET, true, false) instanceof Bucketable bucketable) {
         bucketable.m_142278_(stack.m_41784_());
         bucketable.m_27497_(true);
      }
   }
}
