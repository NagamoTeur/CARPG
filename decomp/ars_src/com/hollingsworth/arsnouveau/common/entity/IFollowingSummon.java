package com.hollingsworth.arsnouveau.common.entity;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public interface IFollowingSummon {
   EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.m_135353_(TamableAnimal.class, EntityDataSerializers.f_135041_);

   Level getWorld();

   PathNavigation getPathNav();

   LivingEntity getSummoner();

   Mob getSelfEntity();

   public static class CopyOwnerTargetGoal<I extends PathfinderMob & IFollowingSummon> extends TargetGoal {
      public CopyOwnerTargetGoal(I creature) {
         super(creature, false);
      }

      public boolean m_8036_() {
         return !(this.f_26135_ instanceof IFollowingSummon summon) ? false : summon.getSummoner() != null && summon.getSummoner().m_21214_() != null;
      }

      public void m_8056_() {
         if (this.f_26135_ instanceof IFollowingSummon summon && summon.getSummoner() != null) {
            this.f_26135_.m_6710_(summon.getSummoner().m_21214_());
         }

         super.m_8056_();
      }
   }
}
