package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class PillagersPatrol extends Monster {
   public PillagersPatrol(SpawnEntity packet, Level world) {
      this((EntityType<PillagersPatrol>)AquamiraeEntities.PILLAGERS_PATROL.get(), world);
   }

   public PillagersPatrol(EntityType<PillagersPatrol> type, Level world) {
      super(type, world);
   }

   public void m_6075_() {
      if (this.f_19853_ instanceof ServerLevel server) {
         Mob entity1 = new Pillager(EntityType.f_20513_, server);
         Mob entity2 = new Pillager(EntityType.f_20513_, server);
         Mob entity3 = new Vindicator(EntityType.f_20493_, server);
         entity1.m_7678_(this.m_20185_() + 0.2, this.m_20186_(), this.m_20189_() + 0.2, this.f_19853_.m_213780_().m_188501_() * 360.0F, 0.0F);
         entity2.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19853_.m_213780_().m_188501_() * 360.0F, 0.0F);
         entity3.m_7678_(this.m_20185_() - 0.2, this.m_20186_(), this.m_20189_() + 0.2, this.f_19853_.m_213780_().m_188501_() * 360.0F, 0.0F);
         entity1.m_6518_(server, this.f_19853_.m_6436_(entity1.m_20183_()), MobSpawnType.NATURAL, null, null);
         entity2.m_6518_(server, this.f_19853_.m_6436_(entity2.m_20183_()), MobSpawnType.NATURAL, null, null);
         entity3.m_6518_(server, this.f_19853_.m_6436_(entity3.m_20183_()), MobSpawnType.NATURAL, null, null);
         this.f_19853_.m_7967_(entity1);
         this.f_19853_.m_7967_(entity2);
         this.f_19853_.m_7967_(entity3);
      }

      if (!this.f_19853_.m_5776_()) {
         this.m_146870_();
      }

      super.m_6075_();
   }

   public static SpawnPredicate<PillagersPatrol> getSpawnRules() {
      return Monster::m_219019_;
   }

   public static Builder createAttributes() {
      return Mob.m_21552_();
   }
}
