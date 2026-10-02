package net.cisco.entity;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModItems;
import net.cisco.procedures.CiscoEntityDiesProcedure;
import net.cisco.procedures.CiscoEntityIsHurtProcedure;
import net.cisco.procedures.CiscoOnInitialEntitySpawnProcedure;
import net.cisco.procedures.CiscoPlayerCollidesWithThisEntityProcedure;
import net.cisco.procedures.CiscoSummonPotionsProcedure;
import net.cisco.procedures.CiscoThisEntityKillsAnotherOneProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class CiscoEntity extends Monster {
   private final ServerBossEvent bossInfo = new ServerBossEvent(this.m_5446_(), BossBarColor.YELLOW, BossBarOverlay.PROGRESS);

   public CiscoEntity(SpawnEntity packet, Level world) {
      this((EntityType<CiscoEntity>)CiscoModModEntities.CISCO.get(), world);
   }

   public CiscoEntity(EntityType<CiscoEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 50;
      this.m_21557_(false);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)CiscoModModItems.EQUILLIBRIUM.get()));
      this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)CiscoModModItems.CISCOS_ARMOR_HELMET.get()));
      this.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)CiscoModModItems.CISCOS_ARMOR_CHESTPLATE.get()));
      this.m_8061_(EquipmentSlot.LEGS, new ItemStack((ItemLike)CiscoModModItems.CISCOS_ARMOR_LEGGINGS.get()));
      this.m_8061_(EquipmentSlot.FEET, new ItemStack((ItemLike)CiscoModModItems.CISCOS_ARMOR_BOOTS.get()));
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(LivingEntity entity) {
            return 9.0;
         }
      });
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 0.3));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(7, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public double m_6049_() {
      return -0.35;
   }

   protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
      super.m_7472_(source, looting, recentlyHitIn);
      this.m_19983_(new ItemStack((ItemLike)CiscoModModItems.CHAMPION_COIN.get()));
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      CiscoEntityIsHurtProcedure.execute(this);
      if (source.m_7640_() instanceof AbstractArrow) {
         return false;
      } else {
         return source.m_19372_() ? false : super.m_6469_(source, amount);
      }
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      CiscoEntityDiesProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      SpawnGroupData retval = super.m_6518_(world, difficulty, reason, livingdata, tag);
      CiscoOnInitialEntitySpawnProcedure.execute(world, this.m_20185_(), this.m_20186_(), this.m_20189_());
      return retval;
   }

   public void m_5993_(Entity entity, int score, DamageSource damageSource) {
      super.m_5993_(entity, score, damageSource);
      CiscoThisEntityKillsAnotherOneProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), entity);
   }

   public void m_6075_() {
      super.m_6075_();
      CiscoSummonPotionsProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public void m_6123_(Player sourceentity) {
      super.m_6123_(sourceentity);
      CiscoPlayerCollidesWithThisEntityProcedure.execute(this);
   }

   public boolean m_6072_() {
      return false;
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public void m_8024_() {
      super.m_8024_();
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.4);
      builder = builder.m_22268_(Attributes.f_22276_, 1000.0);
      builder = builder.m_22268_(Attributes.f_22284_, 60.0);
      builder = builder.m_22268_(Attributes.f_22281_, 20.0);
      builder = builder.m_22268_(Attributes.f_22277_, 25.0);
      builder = builder.m_22268_(Attributes.f_22278_, 1.0);
      return builder.m_22268_(Attributes.f_22282_, 2.0);
   }
}
