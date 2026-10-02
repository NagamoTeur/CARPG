package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LightBlockRemover implements IEntityTick<ServerLevel> {
   private final LivingEntity entity;
   public static final float deathMaxAge = 70.0F;

   public LightBlockRemover(LivingEntity entity) {
      this.entity = entity;
   }

   public void tick(ServerLevel level) {
      this.entity.f_20919_++;
      float interceptedTime = MathUtils.ratioLerp((float)this.entity.f_20919_, 0.5F, 70.0F, 0.0F) * 0.7F;
      level.m_46597_(
         this.entity.m_20183_(), (BlockState)Blocks.f_152480_.m_49966_().m_61124_(LightBlock.f_153657_, Math.round((1.0F - interceptedTime) * 15.0F))
      );
      if (this.entity.f_20919_ == 49) {
         BMDUtils.playSound(level, this.entity.m_20182_(), (SoundEvent)BMDSounds.VOID_BLOSSOM_FALL.get(), SoundSource.HOSTILE, 1.5F, 32.0, null);
      }

      if ((float)this.entity.f_20919_ == 70.0F) {
         if (level.m_8055_(this.entity.m_20183_()).m_60734_() == Blocks.f_152480_) {
            level.m_46597_(this.entity.m_20183_(), Blocks.f_50016_.m_49966_());
         }

         level.m_7605_(this.entity, (byte)60);
         this.entity.m_142687_(RemovalReason.KILLED);
      }
   }
}
