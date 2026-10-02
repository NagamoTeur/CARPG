package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomSummonBlockEntity extends BlockEntity {
   private int age = 0;

   public VoidBlossomSummonBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)BMDBlockEntities.VOID_BLOSSOM_SUMMON_BLOCK_ENTITY.get(), pos, blockState);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, VoidBlossomSummonBlockEntity entity) {
      if (!level.f_46443_ && entity.age % 20 == 0) {
         boolean playersInBox = !level.m_45976_(Player.class, new AABB(pos).m_82400_(40.0)).isEmpty();
         if (playersInBox) {
            Vec3 spawnPos = VecUtils.asVec3(pos).m_82549_(new Vec3(0.5, 0.0, 0.5));
            VoidBlossomEntity boss = (VoidBlossomEntity)((EntityType)BMDEntities.VOID_BLOSSOM.get()).m_20615_(level);
            if (boss == null) {
               return;
            }

            boss.m_20248_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_);
            level.m_7967_(boss);
            level.m_7471_(pos, false);
         }
      }

      entity.age++;
   }
}
