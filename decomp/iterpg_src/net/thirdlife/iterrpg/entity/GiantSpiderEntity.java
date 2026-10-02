package net.thirdlife.iterrpg.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.GiantSpiderSpawnConditionProcedure;
import net.thirdlife.iterrpg.procedures.ReleaseSpiderlingsProcedure;
import net.thirdlife.iterrpg.procedures.SpiderHealthConditionProcedure;
import net.thirdlife.iterrpg.procedures.SpiderPlaceWebProcedure;
import net.thirdlife.iterrpg.procedures.SpiderSaturationProcedure;

public class GiantSpiderEntity extends Monster {
   public GiantSpiderEntity(SpawnEntity packet, Level world) {
      this((EntityType<GiantSpiderEntity>)IterRpgModEntities.GIANT_SPIDER.get(), world);
   }

   public GiantSpiderEntity(EntityType<GiantSpiderEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 8;
      this.m_21557_(false);
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
      });
      this.f_21345_.m_25352_(2, new LeapAtTargetGoal(this, 0.5F));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(6, new NearestAttackableTargetGoal(this, Villager.class, false, false));
      this.f_21346_.m_25352_(7, new NearestAttackableTargetGoal(this, Animal.class, false, false));
      this.f_21346_.m_25352_(8, new NearestAttackableTargetGoal(this, Zombie.class, false, false) {
         public boolean m_8036_() {
            double x = GiantSpiderEntity.this.m_20185_();
            double y = GiantSpiderEntity.this.m_20186_();
            double z = GiantSpiderEntity.this.m_20189_();
            Entity entity = GiantSpiderEntity.this;
            Level world = GiantSpiderEntity.this.f_19853_;
            return super.m_8036_() && SpiderHealthConditionProcedure.execute(entity);
         }
      });
      this.f_21346_.m_25352_(9, new NearestAttackableTargetGoal(this, Husk.class, false, false) {
         public boolean m_8036_() {
            double x = GiantSpiderEntity.this.m_20185_();
            double y = GiantSpiderEntity.this.m_20186_();
            double z = GiantSpiderEntity.this.m_20189_();
            Entity entity = GiantSpiderEntity.this;
            Level world = GiantSpiderEntity.this.f_19853_;
            return super.m_8036_() && SpiderHealthConditionProcedure.execute(entity);
         }
      });
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(11, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   public double m_6048_() {
      return super.m_6048_() + -0.1;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.ambient"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.step")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.death"));
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      ReleaseSpiderlingsProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
   }

   public void m_5993_(Entity entity, int score, DamageSource damageSource) {
      super.m_5993_(entity, score, damageSource);
      SpiderSaturationProcedure.execute(entity, this);
   }

   public void m_6075_() {
      super.m_6075_();
      SpiderPlaceWebProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.GIANT_SPIDER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            return GiantSpiderSpawnConditionProcedure.execute(world, (double)x, (double)y, (double)z);
         }
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.25);
      builder = builder.m_22268_(Attributes.f_22276_, 40.0);
      builder = builder.m_22268_(Attributes.f_22284_, 0.0);
      builder = builder.m_22268_(Attributes.f_22281_, 8.0);
      builder = builder.m_22268_(Attributes.f_22277_, 32.0);
      builder = builder.m_22268_(Attributes.f_22278_, 0.25);
      return builder.m_22268_(Attributes.f_22282_, 0.5);
   }
}
