package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.EntityChimeraProjectile;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ChimeraSpikeGoal extends Goal {
   WildenChimera boss;
   boolean finished;
   int ticks;

   public ChimeraSpikeGoal(WildenChimera boss) {
      this.boss = boss;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public void m_8056_() {
      this.finished = false;
      this.ticks = 0;
   }

   public void m_8041_() {
      super.m_8041_();
      this.tearDownGoal();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8037_() {
      super.m_8037_();
      this.ticks++;
      this.boss.setDefensiveMode(true);
      this.boss.m_20334_(0.0, 0.0, 0.0);
      if (this.ticks % 20 == 0) {
         spawnAOESpikes(this.boss);

         for (int i = 0; i < 3; i++) {
            if (this.boss.m_5448_() != null) {
               EntityChimeraProjectile abstractarrowentity = new EntityChimeraProjectile(this.boss.f_19853_);
               abstractarrowentity.m_6034_(this.boss.m_20185_(), this.boss.m_20186_(), this.boss.m_20189_());
               double d0 = this.boss.m_5448_().m_20185_() - this.boss.m_20185_();
               double d1 = this.boss.m_5448_().m_20227_(0.3333333333333333) - abstractarrowentity.m_20186_();
               double d2 = this.boss.m_5448_().m_20189_() - this.boss.m_20189_();
               double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
               abstractarrowentity.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.6F, 1.0F);
               this.boss.f_19853_.m_7967_(abstractarrowentity);
            }
         }
      }

      if (this.ticks >= 120) {
         this.finished = true;
         this.boss.spikeCooldown = (int)(500.0 + ParticleUtil.inRange(-100.0, 100.0) + (double)this.boss.getCooldownModifier());
         this.tearDownGoal();
      }
   }

   public static void spawnAOESpikes(WildenChimera boss) {
      for (int i = 0; i < 100; i++) {
         EntityChimeraProjectile entity = new EntityChimeraProjectile(boss.f_19853_);
         entity.m_37251_(
            boss,
            (float)boss.f_19853_.f_46441_.m_188503_(360),
            (float)boss.f_19853_.f_46441_.m_188503_(360),
            0.0F,
            (float)(1.0 + ParticleUtil.inRange(0.0, 0.5)),
            1.0F
         );
         entity.m_6034_(boss.f_19825_.f_82479_, boss.f_19825_.f_82480_ + 2.0, boss.f_19825_.f_82481_);
         boss.f_19853_.m_7967_(entity);
      }
   }

   public void tearDownGoal() {
      this.boss.setDefensiveMode(false);
   }

   public boolean m_8045_() {
      boolean canContinue = !this.finished && !this.boss.getPhaseSwapping();
      if (!canContinue) {
         this.tearDownGoal();
      }

      return canContinue;
   }

   public boolean m_8036_() {
      return this.boss.canSpike();
   }
}
