package net.thirdlife.iterrpg.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.MournersAiProcedure;
import net.thirdlife.iterrpg.procedures.SoulsReleaseProcedure;

public class WeeperEntity extends Monster implements RangedAttackMob {
   public WeeperEntity(SpawnEntity packet, Level world) {
      this((EntityType<WeeperEntity>)IterRpgModEntities.WEEPER.get(), world);
   }

   public WeeperEntity(EntityType<WeeperEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 5;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21345_.m_25352_(3, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(1, new RangedAttackGoal(this, 1.25, 32, 10.0F) {
         public boolean m_8045_() {
            return this.m_8036_();
         }
      });
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:weeper_ambient"));
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:weeper_hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:weeper_death"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      SoulsReleaseProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), source.m_7639_());
   }

   public void m_6075_() {
      super.m_6075_();
      MournersAiProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public void m_6504_(LivingEntity target, float flval) {
      WeeperTearEntity.shoot(this, target);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.WEEPER.get(),
         Type.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.m_46791_() != Difficulty.PEACEFUL
               && Monster.m_219009_(world, pos, random)
               && Mob.m_217057_(entityType, world, reason, pos, random)
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.2);
      builder = builder.m_22268_(Attributes.f_22276_, 16.0);
      builder = builder.m_22268_(Attributes.f_22284_, 2.0);
      builder = builder.m_22268_(Attributes.f_22281_, 3.0);
      return builder.m_22268_(Attributes.f_22277_, 32.0);
   }
}
