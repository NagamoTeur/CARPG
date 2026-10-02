package net.thirdlife.iterrpg.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.DemonsConfigConditionProcedure;
import net.thirdlife.iterrpg.procedures.RevenantDeathProcedure;

public class RevenantEntity extends Monster {
   public RevenantEntity(SpawnEntity packet, Level world) {
      this((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), world);
   }

   public RevenantEntity(EntityType<RevenantEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 6;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Villager.class, false, false));
      this.f_21345_.m_25352_(5, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(LivingEntity entity) {
            return (double)(this.f_25540_.m_20205_() * this.f_25540_.m_20205_() + entity.m_20205_());
         }
      });
      this.f_21345_.m_25352_(6, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither_skeleton.ambient"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither_skeleton.step")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither_skeleton.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither_skeleton.death"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      RevenantDeathProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.REVENANT.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            return DemonsConfigConditionProcedure.execute();
         }
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.27);
      builder = builder.m_22268_(Attributes.f_22276_, 30.0);
      builder = builder.m_22268_(Attributes.f_22284_, 6.0);
      builder = builder.m_22268_(Attributes.f_22281_, 6.0);
      builder = builder.m_22268_(Attributes.f_22277_, 32.0);
      builder = builder.m_22268_(Attributes.f_22278_, 0.3);
      return builder.m_22268_(Attributes.f_22282_, 0.6);
   }
}
