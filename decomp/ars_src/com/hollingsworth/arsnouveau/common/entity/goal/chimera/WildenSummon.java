package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.SummonWolf;
import com.hollingsworth.arsnouveau.common.entity.WildenHunter;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;

public class WildenSummon extends Goal {
   private final WildenHunter entity;
   private int ticksSummoning;
   public final int maxSummoning = 45;

   public WildenSummon(WildenHunter entityIn) {
      this.entity = entityIn;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.entity.m_6084_() && this.entity.m_5448_() instanceof Player && this.entity.summonCooldown <= 0;
   }

   public boolean m_8045_() {
      return this.ticksSummoning < 45;
   }

   public void m_8056_() {
      super.m_8056_();
      Networking.sendToNearby(this.entity.f_19853_, this.entity, new PacketAnimEntity(this.entity.m_19879_(), WildenHunter.Animations.HOWL.ordinal()));
      this.entity.f_19853_.m_5594_(null, this.entity.m_20183_(), SoundEvents.f_12620_, SoundSource.HOSTILE, 1.0F, 0.3F);
      this.ticksSummoning = 0;
      this.entity.summonCooldown = 400;
      this.entity.m_20088_().m_135381_(WildenHunter.ANIM_STATE, WildenHunter.Animations.HOWL.name());
   }

   public void m_8041_() {
      super.m_8041_();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8037_() {
      super.m_8037_();
      this.ticksSummoning++;
      if (this.ticksSummoning == 20) {
         SummonWolf wolf = new SummonWolf((EntityType<? extends Wolf>)ModEntities.SUMMON_WOLF.get(), this.entity.f_19853_);
         wolf.ticksLeft = 400;
         wolf.m_6034_(this.entity.m_20208_(1.0), this.entity.m_20186_(), this.entity.m_20262_(1.0));
         SummonWolf wolf2 = new SummonWolf((EntityType<? extends Wolf>)ModEntities.SUMMON_WOLF.get(), this.entity.f_19853_);
         wolf2.ticksLeft = 400;
         wolf2.m_6034_(this.entity.m_20208_(1.0), this.entity.m_20186_(), this.entity.m_20262_(1.0));
         if (this.entity.m_5448_() != null) {
            wolf.m_6710_(this.entity.m_5448_());
            wolf2.m_6710_(this.entity.m_5448_());
         }

         wolf.m_21561_(true);
         wolf2.m_21561_(true);
         wolf.isWildenSummon = true;
         wolf2.isWildenSummon = true;
         this.entity.f_19853_.m_7967_(wolf);
         this.entity.f_19853_.m_7967_(wolf2);
      }

      if (this.ticksSummoning >= 45) {
         this.entity.m_20088_().m_135381_(WildenHunter.ANIM_STATE, WildenHunter.Animations.IDLE.name());
      }
   }
}
