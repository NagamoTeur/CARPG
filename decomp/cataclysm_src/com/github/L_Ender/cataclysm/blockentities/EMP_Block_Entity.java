package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EMP_Block_Entity extends BlockEntity {
   private float chompProgress;
   private float prevChompProgress;
   public int ticksExisted;

   public EMP_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.EMP.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, EMP_Block_Entity entity) {
      entity.tick();
   }

   public void tick() {
      this.prevChompProgress = this.chompProgress;
      boolean powered = false;
      if (this.m_58900_().m_60734_() instanceof EMP_Block) {
         powered = (Boolean)this.m_58900_().m_61143_(EMP_Block.POWERED);
      }

      boolean overload = false;
      if (this.m_58900_().m_60734_() instanceof EMP_Block) {
         overload = (Boolean)this.m_58900_().m_61143_(EMP_Block.OVERLOAD);
      }

      if (powered && this.chompProgress < 15.0F) {
         this.chompProgress++;
      }

      if (!powered && this.chompProgress > 0.0F) {
         this.chompProgress--;
      }

      float x = (float)this.m_58899_().m_123341_() + 0.5F;
      float y = (float)this.m_58899_().m_123342_() + 0.5F;
      float z = (float)this.m_58899_().m_123343_() + 0.5F;
      if (!overload && this.chompProgress == 15.0F) {
         this.f_58857_.m_7106_((ParticleOptions)ModParticle.EM_PULSE.get(), (double)x, (double)y, (double)z, 0.0, 0.0, 0.0);
         ScreenShake_Entity.ScreenShake(this.f_58857_, Vec3.m_82512_(this.m_58899_()), 20.0F, 0.01F, 0, 20);
         this.f_58857_
            .m_5594_(
               (Player)null,
               this.m_58899_(),
               (SoundEvent)ModSounds.EMP_ACTIVATED.get(),
               SoundSource.BLOCKS,
               4.0F,
               this.f_58857_.f_46441_.m_188501_() * 0.2F + 1.0F
            );
         this.f_58857_.m_46597_(this.m_58899_(), (BlockState)this.m_58900_().m_61124_(EMP_Block.OVERLOAD, true));
         AABB screamBox = new AABB(
            (double)((float)this.m_58899_().m_123341_() - 5.0F),
            (double)((float)this.m_58899_().m_123342_() - 5.0F),
            (double)(this.m_58899_().m_123343_() - 5),
            (double)(this.m_58899_().m_123341_() + 5),
            (double)((float)this.m_58899_().m_123342_() + 5.0F),
            (double)((float)this.m_58899_().m_123343_() + 5.0F)
         );

         for (LivingEntity entity : this.f_58857_.m_45976_(LivingEntity.class, screamBox)) {
            entity.m_6469_(CMDamageTypes.EMP, (float)(3 + entity.m_217043_().m_188503_(3)));
         }
      }

      this.ticksExisted++;
   }

   public float getChompProgress(float partialTick) {
      return this.prevChompProgress + (this.chompProgress - this.prevChompProgress) * partialTick;
   }
}
