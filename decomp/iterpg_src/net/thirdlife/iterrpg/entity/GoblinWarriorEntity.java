package net.thirdlife.iterrpg.entity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.CoinTimerProcedure;
import net.thirdlife.iterrpg.procedures.CoinTimerTickProcedure;
import net.thirdlife.iterrpg.procedures.EnemyExpandedGrantProcedure;
import net.thirdlife.iterrpg.procedures.GoblinSpawnProcedure;
import net.thirdlife.iterrpg.procedures.GoblinsCampRememberProcedure;
import net.thirdlife.iterrpg.procedures.HurtTimerDownProcedure;

public class GoblinWarriorEntity extends Monster {
   public GoblinWarriorEntity(SpawnEntity packet, Level world) {
      this((EntityType<GoblinWarriorEntity>)IterRpgModEntities.GOBLIN_WARRIOR.get(), world);
   }

   public GoblinWarriorEntity(EntityType<GoblinWarriorEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 2;
      this.m_21557_(false);
      this.m_21530_();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(LivingEntity entity) {
            return (double)(this.f_25540_.m_20205_() * this.f_25540_.m_20205_() + entity.m_20205_());
         }

         public boolean m_8036_() {
            double x = GoblinWarriorEntity.this.m_20185_();
            double y = GoblinWarriorEntity.this.m_20186_();
            double z = GoblinWarriorEntity.this.m_20189_();
            Entity entity = GoblinWarriorEntity.this;
            Level world = GoblinWarriorEntity.this.f_19853_;
            return super.m_8036_() && CoinTimerProcedure.execute(entity);
         }

         public boolean m_8045_() {
            double x = GoblinWarriorEntity.this.m_20185_();
            double y = GoblinWarriorEntity.this.m_20186_();
            double z = GoblinWarriorEntity.this.m_20189_();
            Entity entity = GoblinWarriorEntity.this;
            Level world = GoblinWarriorEntity.this.f_19853_;
            return super.m_8045_() && CoinTimerProcedure.execute(entity);
         }
      });
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Player.class, true, false) {
         public boolean m_8036_() {
            double x = GoblinWarriorEntity.this.m_20185_();
            double y = GoblinWarriorEntity.this.m_20186_();
            double z = GoblinWarriorEntity.this.m_20189_();
            Entity entity = GoblinWarriorEntity.this;
            Level world = GoblinWarriorEntity.this.f_19853_;
            return super.m_8036_() && CoinTimerProcedure.execute(entity);
         }

         public boolean m_8045_() {
            double x = GoblinWarriorEntity.this.m_20185_();
            double y = GoblinWarriorEntity.this.m_20186_();
            double z = GoblinWarriorEntity.this.m_20189_();
            Entity entity = GoblinWarriorEntity.this;
            Level world = GoblinWarriorEntity.this.f_19853_;
            return super.m_8045_() && CoinTimerProcedure.execute(entity);
         }
      });
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Villager.class, true, false));
      this.f_21345_.m_25352_(6, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      HurtTimerDownProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this, source.m_7639_());
      return super.m_6469_(source, amount);
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      EnemyExpandedGrantProcedure.execute(source.m_7639_());
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      SpawnGroupData retval = super.m_6518_(world, difficulty, reason, livingdata, tag);
      GoblinsCampRememberProcedure.execute(this.m_20185_(), this.m_20189_(), this);
      return retval;
   }

   public void m_6075_() {
      super.m_6075_();
      CoinTimerTickProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.GOBLIN_WARRIOR.get(),
         Type.NO_RESTRICTIONS,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> {
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            return GoblinSpawnProcedure.execute(world, (double)x, (double)y, (double)z);
         }
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.25);
      builder = builder.m_22268_(Attributes.f_22276_, 16.0);
      builder = builder.m_22268_(Attributes.f_22284_, 6.0);
      builder = builder.m_22268_(Attributes.f_22281_, 4.0);
      return builder.m_22268_(Attributes.f_22277_, 24.0);
   }
}
