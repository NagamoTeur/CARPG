package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

public class AnimationFWNStompAttackAI extends SimpleAnimationAI<EntityWroughtnaut> {
   public AnimationFWNStompAttackAI(EntityWroughtnaut entity, Animation animation) {
      super(entity, animation, true);
   }

   public void m_8037_() {
      this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
      double perpFacing = (double)this.entity.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.m_14107_(this.entity.m_20191_().f_82289_ - 0.5);
      int tick = this.entity.getAnimationTick();
      int maxDistance = 6;
      ServerLevel world = (ServerLevel)this.entity.f_19853_;
      if (tick == 6) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_SHOUT_2.get(), 1.0F, 1.0F);
      } else if (tick > 9 && tick < 17) {
         if (tick == 10) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), 1.2F, 0.5F + this.entity.m_217043_().m_188501_() * 0.1F);
         } else if (tick == 12) {
            this.entity.m_5496_(SoundEvents.f_11913_, 2.0F, 1.0F + this.entity.m_217043_().m_188501_() * 0.1F);
            EntityCameraShake.cameraShake(this.entity.f_19853_, this.entity.m_20182_(), 25.0F, 0.1F, 0, 20);
         }

         if (tick % 2 == 0) {
            int distance = tick / 2 - 2;
            double spread = Math.PI * 2;
            int arcLen = Mth.m_14165_((double)distance * spread);
            double minY = this.entity.m_20191_().f_82289_;
            double maxY = this.entity.m_20191_().f_82292_;

            for (int i = 0; i < arcLen; i++) {
               double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
               double vx = Math.cos(theta);
               double vz = Math.sin(theta);
               double px = this.entity.m_20185_() + vx * (double)distance;
               double pz = this.entity.m_20189_() + vz * (double)distance;
               float factor = 1.0F - (float)distance / 6.0F;
               AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);

               for (Entity entity : world.m_45976_(Entity.class, selection)) {
                  if (entity.m_20096_() && entity != this.entity && !(entity instanceof EntityFallingBlock)) {
                     float applyKnockbackResistance = 0.0F;
                     if (entity instanceof LivingEntity) {
                        entity.m_6469_(
                           DamageSource.m_19370_(this.entity),
                           (factor * 5.0F + 1.0F) * ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.combatConfig.attackMultiplier.get()).floatValue()
                        );
                        applyKnockbackResistance = (float)((LivingEntity)entity).m_21051_(Attributes.f_22278_).m_22135_();
                     }

                     double magnitude = world.f_46441_.m_188500_() * 0.15 + 0.1;
                     float x = 0.0F;
                     float y = 0.0F;
                     float z = 0.0F;
                     x = (float)((double)x + vx * (double)factor * magnitude * (double)(1.0F - applyKnockbackResistance));
                     y = (float)(
                        (double)y + 0.1 * (double)(1.0F - applyKnockbackResistance) + (double)factor * 0.15 * (double)(1.0F - applyKnockbackResistance)
                     );
                     z = (float)((double)z + vz * (double)factor * magnitude * (double)(1.0F - applyKnockbackResistance));
                     entity.m_20256_(entity.m_20184_().m_82520_((double)x, (double)y, (double)z));
                     if (entity instanceof ServerPlayer) {
                        ((ServerPlayer)entity).f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(entity));
                     }
                  }
               }

               if (world.f_46441_.m_188499_()) {
                  int hitX = Mth.m_14107_(px);
                  int hitZ = Mth.m_14107_(pz);
                  BlockPos pos = new BlockPos(hitX, hitY, hitZ);
                  BlockPos abovePos = new BlockPos(pos).m_7494_();
                  BlockState block = world.m_8055_(pos);
                  BlockState blockAbove = world.m_8055_(abovePos);
                  if (block.m_60767_() != Material.f_76296_ && block.m_60796_(world, pos) && !block.m_155947_() && !blockAbove.m_60767_().m_76334_()) {
                     EntityFallingBlock fallingBlock = new EntityFallingBlock(
                        (EntityType<?>)EntityHandler.FALLING_BLOCK.get(), world, block, (float)(0.4 + (double)factor * 0.2)
                     );
                     fallingBlock.m_6034_((double)hitX + 0.5, (double)(hitY + 1), (double)hitZ + 0.5);
                     world.m_7967_(fallingBlock);
                  }
               }
            }
         }
      }
   }
}
