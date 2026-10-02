package net.cisco.entity;

import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModItems;
import net.cisco.procedures.AfterImageMovesProcedure;
import net.cisco.procedures.AfterImageThisEntityKillsAnotherOneProcedure;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class AfterImageEntity extends Monster {
   public AfterImageEntity(SpawnEntity packet, Level world) {
      this((EntityType<AfterImageEntity>)CiscoModModEntities.AFTER_IMAGE.get(), world);
   }

   public AfterImageEntity(EntityType<AfterImageEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 10;
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
      this.f_21345_.m_25352_(3, new RandomStrollGoal(this, 1.0));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(6, new FloatGoal(this));
      this.f_21345_.m_25352_(7, new PanicGoal(this, 0.2));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public double m_6049_() {
      return -0.35;
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
   }

   public void m_5993_(Entity entity, int score, DamageSource damageSource) {
      super.m_5993_(entity, score, damageSource);
      AfterImageThisEntityKillsAnotherOneProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), entity);
   }

   public void m_6075_() {
      super.m_6075_();
      AfterImageMovesProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.3);
      builder = builder.m_22268_(Attributes.f_22276_, 100.0);
      builder = builder.m_22268_(Attributes.f_22284_, 35.0);
      builder = builder.m_22268_(Attributes.f_22281_, 10.0);
      return builder.m_22268_(Attributes.f_22277_, 20.0);
   }
}
