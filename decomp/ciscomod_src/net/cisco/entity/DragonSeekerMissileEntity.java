package net.cisco.entity;

import net.cisco.init.CiscoModModEntities;
import net.cisco.procedures.AfterImageThisEntityKillsAnotherOneProcedure;
import net.cisco.procedures.ParticleentOnEntityTickUpdateProcedure;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class DragonSeekerMissileEntity extends Monster {
   public DragonSeekerMissileEntity(SpawnEntity packet, Level world) {
      this((EntityType<DragonSeekerMissileEntity>)CiscoModModEntities.DRAGON_SEEKER_MISSILE.get(), world);
   }

   public DragonSeekerMissileEntity(EntityType<DragonSeekerMissileEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 10;
      this.m_21557_(false);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected PathNavigation m_6037_(Level world) {
      return new FlyingPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(LivingEntity entity) {
            return (double)(this.f_25540_.m_20205_() * this.f_25540_.m_20205_() + entity.m_20205_());
         }
      });
      this.f_21345_.m_25352_(3, new RandomStrollGoal(this, 1.0));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(6, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public double m_6049_() {
      return -0.35;
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
   }

   public boolean m_142535_(float l, float d, DamageSource source) {
      return false;
   }

   public void m_5993_(Entity entity, int score, DamageSource damageSource) {
      super.m_5993_(entity, score, damageSource);
      AfterImageThisEntityKillsAnotherOneProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), entity);
   }

   public void m_6075_() {
      super.m_6075_();
      ParticleentOnEntityTickUpdateProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void m_20242_(boolean ignored) {
      super.m_20242_(true);
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_20242_(true);
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 1.0);
      builder = builder.m_22268_(Attributes.f_22276_, 100.0);
      builder = builder.m_22268_(Attributes.f_22284_, 20.0);
      builder = builder.m_22268_(Attributes.f_22281_, 5.0);
      builder = builder.m_22268_(Attributes.f_22277_, 20.0);
      builder = builder.m_22268_(Attributes.f_22278_, 1.0);
      return builder.m_22268_(Attributes.f_22280_, 1.0);
   }
}
