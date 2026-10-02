package net.thirdlife.iterrpg.entity;

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
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.ScallopSpawnConditionProcedure;

public class ScallopEntity extends Monster {
   public ScallopEntity(SpawnEntity packet, Level world) {
      this((EntityType<ScallopEntity>)IterRpgModEntities.SCALLOP.get(), world);
   }

   public ScallopEntity(EntityType<ScallopEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 0;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Guardian.class, true, false));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, true));
      this.f_21345_.m_25352_(3, new LeapAtTargetGoal(this, 0.4F));
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, 1.0, false) {
         protected double m_6639_(LivingEntity entity) {
            return (double)(this.f_25540_.m_20205_() * this.f_25540_.m_20205_() + entity.m_20205_());
         }
      });
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(6, new AvoidEntityGoal(this, Axolotl.class, 8.0F, 1.0, 2.0));
      this.f_21345_.m_25352_(7, new AvoidEntityGoal(this, Dolphin.class, 8.0F, 1.0, 2.0));
      this.f_21346_.m_25352_(8, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(9, new RandomLookAroundGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bone_block.break"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source == DamageSource.f_19314_) {
         return false;
      } else {
         return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
      }
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.SCALLOP.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            return ScallopSpawnConditionProcedure.execute(world, (double)x, (double)y, (double)z);
         }
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.15);
      builder = builder.m_22268_(Attributes.f_22276_, 18.0);
      builder = builder.m_22268_(Attributes.f_22284_, 8.0);
      builder = builder.m_22268_(Attributes.f_22281_, 4.0);
      builder = builder.m_22268_(Attributes.f_22277_, 8.0);
      return builder.m_22268_(Attributes.f_22278_, 0.25);
   }
}
