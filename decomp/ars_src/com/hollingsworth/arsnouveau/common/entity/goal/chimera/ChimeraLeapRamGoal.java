package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class ChimeraLeapRamGoal extends Goal {
   WildenChimera boss;
   int timeCharging;
   boolean finished;
   boolean startedCharge;
   boolean isCharging;
   boolean hasHit;

   public ChimeraLeapRamGoal(WildenChimera boss) {
      this.boss = boss;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public void m_8056_() {
      super.m_8056_();
      this.timeCharging = 0;
      this.finished = false;
      this.startedCharge = false;
      this.isCharging = false;
      this.hasHit = false;
      this.boss.isRamGoal = true;
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.boss.m_5448_() == null) {
         this.endRam();
      }

      if (!this.startedCharge) {
         this.boss.setRamPrep(true);
         this.startedCharge = true;
      }

      this.timeCharging++;
      if (this.timeCharging <= 20 && !this.isCharging) {
         LivingEntity livingentity = this.boss.m_5448_();
         if (livingentity != null) {
            this.boss.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         }

         this.boss.m_21573_().m_26573_();
      }

      if (this.timeCharging > 20 && !this.isCharging) {
         this.boss.setRamPrep(false);
         Vec3 vec3 = this.boss.m_20184_();
         Vec3 vec31 = new Vec3(this.boss.m_5448_().m_20185_() - this.boss.m_20185_(), 0.0, this.boss.m_5448_().m_20189_() - this.boss.m_20189_());
         if (vec31.m_82556_() > 1.0E-7) {
            vec31 = vec31.m_82541_().m_82490_(0.4).m_82549_(vec3.m_82490_(0.2));
         }

         this.boss.m_20334_(vec31.f_82479_ * 6.0, 1.2, vec31.f_82481_ * 6.0);
         this.boss.f_19812_ = true;
         this.isCharging = true;
      }

      if (this.isCharging) {
         if (this.boss.m_21573_() == null || this.boss.m_5448_() == null) {
            this.attack();
            return;
         }

         this.breakBlocks();
         if (this.boss.m_20096_()) {
            Path path = this.boss.m_21573_().m_7864_(this.boss.m_5448_().m_20183_().m_7494_(), 1);
            if (path == null) {
               return;
            }

            this.boss.m_21573_().m_26536_(path, 2.0);
         }

         this.attack();
      }

      if (this.boss != null && this.boss.m_5448_() != null && this.hasHit && BlockUtil.distanceFrom(this.boss.f_19825_, this.boss.m_5448_().f_19825_) <= 3.0) {
         this.endRam();
      }
   }

   public void breakBlocks() {
      if (ForgeEventFactory.getMobGriefingEvent(this.boss.f_19853_, this.boss)) {
         Direction facing = this.boss.m_6350_();
         BlockPos facingPos = this.boss.m_20183_().m_7494_().m_121945_(facing);

         for (int i = 0; i < 3; i++) {
            facingPos = facingPos.m_6630_(i);
            this.destroyBlock(facingPos.m_7494_());
            this.destroyBlock(facingPos.m_122029_());
            this.destroyBlock(facingPos.m_122024_());
            this.destroyBlock(facingPos.m_122019_());
            this.destroyBlock(facingPos.m_122012_());
         }
      }
   }

   public void destroyBlock(BlockPos pos) {
      if (SpellUtil.isCorrectHarvestLevel(4, this.boss.f_19853_.m_8055_(pos))) {
         this.boss.f_19853_.m_46961_(pos, true);
      }
   }

   public void endRam() {
      this.finished = true;
      if (this.boss.f_19853_.f_46441_.m_188503_(3) != 0) {
         this.boss.ramCooldown = (int)(400.0 + ParticleUtil.inRange(-100.0, (double)(100 + this.boss.getCooldownModifier())));
      }

      this.boss.isRamGoal = false;
      this.attack();
      this.boss.setRamming(false);
      this.boss.setRamPrep(false);
   }

   protected void attack() {
      for (LivingEntity enemy : this.boss.f_19853_.m_45976_(LivingEntity.class, new AABB(this.boss.m_20183_()).m_82377_(3.0, 3.0, 3.0))) {
         if (!enemy.equals(this.boss)) {
            this.boss.m_7327_(enemy);
            enemy.m_147240_(
               3.0,
               (double)Mth.m_14031_(this.boss.f_19857_ * (float) (Math.PI / 180.0)),
               (double)(-Mth.m_14089_(this.boss.f_19857_ * (float) (Math.PI / 180.0)))
            );
            this.hasHit = true;
         }
      }
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8045_() {
      return !this.finished && !this.boss.getPhaseSwapping();
   }

   public boolean m_8036_() {
      return this.boss.m_5448_() != null && this.boss.ramCooldown <= 0 && this.boss.canRam(true);
   }
}
