package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class ChimeraDiveGoal extends Goal {
   WildenChimera boss;
   boolean finished;
   int ticksFlying;
   boolean isDiving;
   BlockPos divePos;
   BlockPos startPos;
   BlockPos hoverPos;

   public ChimeraDiveGoal(WildenChimera boss) {
      this.boss = boss;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.finished = false;
      this.divePos = null;
      this.ticksFlying = 0;
      this.isDiving = false;
      this.startPos = this.boss.m_20183_();
      this.hoverPos = this.startPos.m_6630_(8);
      this.boss.setFlying(true);
      this.boss.m_21573_().m_7008_(true);
      Networking.sendToNearby(this.boss.f_19853_, this.boss, new PacketAnimEntity(this.boss.m_19879_(), WildenChimera.Animations.FLYING.ordinal()));
   }

   public void m_8041_() {
      super.m_8041_();
      this.tearDownNavigation();
   }

   public void m_8037_() {
      super.m_8037_();
      this.ticksFlying++;
      if (this.ticksFlying < 60) {
         this.boss.flyingNavigator.m_26519_((double)this.hoverPos.m_123341_(), (double)this.hoverPos.m_123342_(), (double)this.hoverPos.m_123343_(), 1.0);
         this.boss.m_20256_(this.boss.m_20184_().m_82520_(0.0, 0.005, 0.0));
         if (this.boss.m_5448_() != null) {
            WildenChimera.faceBlock(this.boss.m_5448_().m_20183_(), this.boss);
         }
      }

      if (this.ticksFlying > 60) {
         this.boss.setDiving(true);
         this.isDiving = true;
         this.boss.diving = true;
         if (this.divePos == null && this.boss.m_5448_() != null) {
            this.divePos = this.boss.m_5448_().m_20183_().m_7495_();

            for (int i = 1; i < 50 && this.boss.f_19853_.m_8055_(this.divePos).m_60795_(); i++) {
               this.divePos = this.divePos.m_7495_();
            }
         }

         if (this.divePos != null) {
            this.boss.flyingNavigator.m_26519_((double)this.divePos.m_123341_() + 0.5, (double)this.divePos.m_123342_(), (double)this.divePos.m_123343_(), 4.0);
            this.boss.orbitOffset = new Vec3((double)this.divePos.m_123341_() + 0.5, (double)this.divePos.m_123342_(), (double)this.divePos.m_123343_() + 0.5);
         }
      }

      if ((!this.isDiving || !this.boss.m_20096_() && !(BlockUtil.distanceFrom(this.boss.f_19825_, this.divePos) <= 1.0))
         && (this.boss.orbitOffset == null || !(BlockUtil.distanceFrom(this.boss.f_19825_, this.boss.orbitOffset) <= 1.7))) {
         if (!this.isDiving || !this.boss.m_5830_() && !this.boss.f_19862_ && !this.boss.f_19863_) {
            if (this.isDiving && this.divePos == null && this.boss.m_5448_() == null) {
               this.endGoal();
            }
         } else {
            this.makeExplosion();
            this.endGoal();
         }
      } else {
         this.makeExplosion();
         this.endGoal();
      }
   }

   public void endGoal() {
      this.tearDownNavigation();
      this.boss.diveCooldown = (int)(300.0 + ParticleUtil.inRange(-100.0, 100.0) + (double)this.boss.getCooldownModifier());
      this.boss.diving = false;
      this.finished = true;
      ANCriteriaTriggers.rewardNearbyPlayers(
         ANCriteriaTriggers.CHIMERA_EXPLOSION,
         (ServerLevel)this.boss.f_19853_,
         new BlockPos(this.boss.m_20182_().f_82479_, this.boss.f_19825_.f_82480_, this.boss.f_19825_.f_82481_),
         10
      );

      for (int i = 0; i < 40; i++) {
         if (!this.boss.f_19853_.m_8055_(this.boss.m_20097_().m_6625_(i)).m_60795_()) {
            this.boss.m_6034_(this.boss.m_20185_(), this.boss.m_20186_() - (double)i, this.boss.m_20189_());
            this.boss.m_6853_(true);
            return;
         }
      }
   }

   public void makeExplosion() {
      BlockInteraction mode = ForgeEventFactory.getMobGriefingEvent(this.boss.f_19853_, this.boss) ? BlockInteraction.BREAK : BlockInteraction.NONE;
      if (!(Boolean)Config.CHIMERA_DIVE_DESTRUCTIVE.get()) {
         mode = BlockInteraction.NONE;
      }

      this.boss.f_19853_.m_46511_(this.boss, this.boss.m_20185_() + 0.5, this.boss.m_20186_(), this.boss.m_20189_() + 0.5, 4.5F, mode);
      if (this.boss.hasSpikes()) {
         ChimeraSpikeGoal.spawnAOESpikes(this.boss);
      }
   }

   public void tearDownNavigation() {
      this.boss.m_21573_().m_7008_(false);
      this.boss.m_21573_().m_26573_();
      this.boss.setFlying(false);
      this.boss.setDiving(false);
      this.boss.m_20242_(false);
      this.boss.m_20334_(0.0, 0.0, 0.0);
   }

   public boolean m_8045_() {
      boolean canContinue = !this.finished && !this.boss.getPhaseSwapping();
      if (!canContinue) {
         this.tearDownNavigation();
      }

      return canContinue;
   }

   public boolean m_8036_() {
      return this.boss.canDive() && this.boss.m_5448_() != null;
   }
}
