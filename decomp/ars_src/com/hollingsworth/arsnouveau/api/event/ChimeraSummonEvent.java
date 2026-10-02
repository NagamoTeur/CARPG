package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.SummonWolf;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import com.hollingsworth.arsnouveau.common.entity.WildenGuardian;
import com.hollingsworth.arsnouveau.common.entity.WildenHunter;
import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ChimeraSummonEvent implements ITimedEvent {
   int duration;
   int phase;
   Level world;
   BlockPos pos;
   int ownerID;
   public static final String ID = "chimera";

   public ChimeraSummonEvent(int duration, int phase, Level world, BlockPos pos, int ownerID) {
      this.duration = duration;
      this.phase = phase;
      this.world = world;
      this.pos = pos;
      this.ownerID = ownerID;
   }

   public static ChimeraSummonEvent get(CompoundTag tag) {
      return new ChimeraSummonEvent(tag.m_128451_("duration"), tag.m_128451_("phase"), ArsNouveau.proxy.getClientWorld(), NBTUtil.getBlockPos(tag, "loc"), -1);
   }

   @Override
   public void tick(boolean serverSide) {
      this.duration--;
      if (serverSide) {
         if (!(this.world.m_6815_(this.ownerID) instanceof WildenChimera boss)) {
            this.duration = 0;
            return;
         }

         boolean summonedWilden = false;
         if (this.duration % 20 == 0) {
            RandomSource random = boss.m_217043_();
            SummonWolf wolf = new SummonWolf((EntityType<? extends Wolf>)ModEntities.SUMMON_WOLF.get(), this.world);
            wolf.m_6034_((double)this.getPos().m_123341_(), (double)this.getPos().m_123342_(), (double)this.getPos().m_123343_());
            wolf.isWildenSummon = true;
            wolf.ticksLeft = 600 + this.phase * 60;
            this.summon(wolf, this.getPos(), boss.m_5448_());
            int randBound = 8 - boss.getPhase();
            if (boss.hasWings() && boss.f_19853_.f_46441_.m_188503_(randBound) == 0) {
               WildenStalker stalker = new WildenStalker((EntityType<? extends Monster>)ModEntities.WILDEN_STALKER.get(), this.world);
               this.summon(stalker, this.getPos(), boss.m_5448_());
               summonedWilden = true;
            }

            if (!summonedWilden && boss.hasHorns() && boss.f_19853_.f_46441_.m_188503_(randBound) == 0) {
               WildenHunter hunter = new WildenHunter((EntityType<? extends Monster>)ModEntities.WILDEN_HUNTER.get(), this.world);
               this.summon(hunter, this.getPos(), boss.m_5448_());
               summonedWilden = true;
            }

            if (!summonedWilden && boss.hasSpikes() && boss.f_19853_.f_46441_.m_188503_(randBound) == 0) {
               WildenGuardian guardian = new WildenGuardian((EntityType<? extends Monster>)ModEntities.WILDEN_GUARDIAN.get(), this.world);
               this.summon(guardian, this.getPos(), boss.m_5448_());
               summonedWilden = true;
            }
         }
      } else {
         ParticleUtil.spawnRitualAreaEffect(this.pos, this.world, this.world.f_46441_, ParticleColor.defaultParticleColor(), 1 + this.phase * 2);
      }
   }

   public void summon(Mob mob, BlockPos pos, @Nullable LivingEntity target) {
      mob.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      mob.m_6710_(target);
      mob.m_21561_(true);
      mob.f_19853_.m_7967_(mob);
   }

   public BlockPos getPos() {
      double spawnArea = 2.5 + (double)(this.phase * 2);
      return new BlockPos(
         (double)this.pos.m_123341_() + ParticleUtil.inRange(-spawnArea, spawnArea),
         (double)(this.pos.m_123342_() + 2),
         (double)this.pos.m_123343_() + ParticleUtil.inRange(-spawnArea, spawnArea)
      );
   }

   @Override
   public boolean isExpired() {
      return this.duration <= 0;
   }

   @Override
   public CompoundTag serialize(CompoundTag tag) {
      ITimedEvent.super.serialize(tag);
      tag.m_128405_("duration", this.duration);
      tag.m_128405_("phase", this.phase);
      NBTUtil.storeBlockPos(tag, "loc", this.pos);
      return tag;
   }

   @Override
   public String getID() {
      return "chimera";
   }
}
