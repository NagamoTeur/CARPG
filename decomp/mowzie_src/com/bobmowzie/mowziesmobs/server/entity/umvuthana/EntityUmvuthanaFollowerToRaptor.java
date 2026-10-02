package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public class EntityUmvuthanaFollowerToRaptor extends EntityUmvuthanaFollower<EntityUmvuthanaRaptor> implements LeaderSunstrikeImmune, Enemy {
   public EntityUmvuthanaFollowerToRaptor(EntityType<? extends EntityUmvuthanaFollowerToRaptor> type, Level world) {
      this(type, world, null);
   }

   public EntityUmvuthanaFollowerToRaptor(EntityType<? extends EntityUmvuthanaFollowerToRaptor> type, Level world, EntityUmvuthanaRaptor leader) {
      super(type, world, EntityUmvuthanaRaptor.class, leader);
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(3, new UmvuthanaHurtByTargetAI(this));
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.leader != null) {
         this.m_6710_(this.leader.m_5448_());
      }

      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
         this.m_146870_();
      }
   }

   @Override
   protected int getGroupCircleTick() {
      return this.leader == null ? 0 : this.leader.circleTick;
   }

   @Override
   protected int getPackSize() {
      return this.leader == null ? 0 : this.leader.getPackSize();
   }

   @Override
   protected void addAsPackMember() {
      if (this.leader != null) {
         this.leader.addPackMember(this);
      }
   }

   @Override
   protected void removeAsPackMember() {
      if (this.leader != null) {
         this.leader.removePackMember(this);
      }
   }

   public void removeLeader() {
      this.setLeaderUUID(ABSENT_LEADER);
      this.leader = null;
      this.m_6710_(null);
   }

   @Override
   public void setLeaderUUID(Optional<UUID> uuid) {
      super.setLeaderUUID(uuid);
      if (uuid == ABSENT_LEADER) {
         this.registerHuntingTargetGoals();
      }
   }
}
