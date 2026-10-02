package com.ilexiconn.llibrary.server.animation;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.ilexiconn.llibrary.server.event.AnimationEvent;
import com.ilexiconn.llibrary.server.network.AnimationMessage;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.ArrayUtils;

public enum AnimationHandler {
   INSTANCE;

   public <T extends Entity & IAnimatedEntity> void sendAnimationMessage(T entity, Animation animation) {
      if (!entity.f_19853_.f_46443_) {
         entity.setAnimation(animation);
         MowziesMobs.NETWORK
            .send(
               PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
               new AnimationMessage(entity.m_19879_(), ArrayUtils.indexOf(entity.getAnimations(), animation))
            );
      }
   }

   public <T extends Entity & IAnimatedEntity> void updateAnimations(T entity) {
      if (entity.getAnimation() == null) {
         entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
      } else if (entity.getAnimation() != IAnimatedEntity.NO_ANIMATION) {
         if (entity.getAnimationTick() == 0) {
            AnimationEvent event = new AnimationEvent.Start(entity, entity.getAnimation());
            if (!MinecraftForge.EVENT_BUS.post(event)) {
               this.sendAnimationMessage(entity, event.getAnimation());
            }
         }

         if (entity.getAnimationTick() < entity.getAnimation().getDuration()) {
            entity.setAnimationTick(entity.getAnimationTick() + 1);
            MinecraftForge.EVENT_BUS.post(new AnimationEvent.Tick(entity, entity.getAnimation(), entity.getAnimationTick()));
         }

         if (entity.getAnimationTick() == entity.getAnimation().getDuration()) {
            if (!entity.getAnimation().doesLoop()) {
               entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
            }

            entity.setAnimationTick(0);
         }
      }
   }
}
