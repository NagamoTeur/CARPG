package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownCoral_Spear_Entity extends AbstractArrow {
   private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.m_135353_(ThrownCoral_Spear_Entity.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.m_135353_(ThrownCoral_Spear_Entity.class, EntityDataSerializers.f_135035_);
   private ItemStack tridentItem = new ItemStack((ItemLike)ModItems.CORAL_SPEAR.get());
   private boolean dealtDamage;
   public int clientSideReturnTridentTickCount;

   public ThrownCoral_Spear_Entity(EntityType<? extends ThrownCoral_Spear_Entity> p_37561_, Level p_37562_) {
      super(p_37561_, p_37562_);
   }

   public ThrownCoral_Spear_Entity(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
      super((EntityType)ModEntities.CORAL_SPEAR.get(), p_37570_, p_37569_);
      this.tridentItem = p_37571_.m_41777_();
      this.f_19804_.m_135381_(ID_LOYALTY, (byte)EnchantmentHelper.m_44928_(p_37571_));
      this.f_19804_.m_135381_(ID_FOIL, p_37571_.m_41790_());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ID_LOYALTY, (byte)0);
      this.f_19804_.m_135372_(ID_FOIL, false);
   }

   public void m_8119_() {
      if (this.f_36704_ > 4) {
         this.dealtDamage = true;
      }

      Entity entity = this.m_37282_();
      int i = (Byte)this.f_19804_.m_135370_(ID_LOYALTY);
      if (i > 0 && (this.dealtDamage || this.m_36797_()) && entity != null) {
         if (!this.isAcceptibleReturnOwner()) {
            if (!this.f_19853_.f_46443_ && this.f_36705_ == Pickup.ALLOWED) {
               this.m_5552_(this.m_7941_(), 0.1F);
            }

            this.m_146870_();
         } else {
            this.m_36790_(true);
            Vec3 vec3 = entity.m_146892_().m_82546_(this.m_20182_());
            this.m_20343_(this.m_20185_(), this.m_20186_() + vec3.f_82480_ * 0.015 * (double)i, this.m_20189_());
            if (this.f_19853_.f_46443_) {
               this.f_19791_ = this.m_20186_();
            }

            double d0 = 0.05 * (double)i;
            this.m_20256_(this.m_20184_().m_82490_(0.95).m_82549_(vec3.m_82541_().m_82490_(d0)));
            if (this.clientSideReturnTridentTickCount == 0) {
               this.m_5496_(SoundEvents.f_12516_, 10.0F, 1.0F);
            }

            this.clientSideReturnTridentTickCount++;
         }
      }

      super.m_8119_();
   }

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.m_37282_();
      return entity != null && entity.m_6084_() ? !(entity instanceof ServerPlayer) || !entity.m_5833_() : false;
   }

   protected ItemStack m_7941_() {
      return this.tridentItem.m_41777_();
   }

   public boolean isFoil() {
      return (Boolean)this.f_19804_.m_135370_(ID_FOIL);
   }

   @Nullable
   protected EntityHitResult m_6351_(Vec3 p_37575_, Vec3 p_37576_) {
      return this.dealtDamage ? null : super.m_6351_(p_37575_, p_37576_);
   }

   protected void m_5790_(EntityHitResult p_37573_) {
      Entity entity = p_37573_.m_82443_();
      float f = 6.5F;
      if (entity instanceof LivingEntity livingentity) {
         f += EnchantmentHelper.m_44833_(this.tridentItem, livingentity.m_6336_());
      }

      Entity entity1 = this.m_37282_();
      DamageSource damagesource = DamageSource.m_19337_(this, (Entity)(entity1 == null ? this : entity1));
      this.dealtDamage = true;
      SoundEvent soundevent = SoundEvents.f_12514_;
      if (entity.m_6469_(damagesource, f)) {
         if (entity.m_6095_() == EntityType.f_20566_) {
            return;
         }

         if (entity instanceof LivingEntity livingentity1) {
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity1, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity1);
            }

            this.m_7761_(livingentity1);
         }
      }

      this.m_20256_(this.m_20184_().m_82542_(-0.01, -0.1, -0.01));
      float f1 = 1.0F;
      if (this.f_19853_ instanceof ServerLevel && this.f_19853_.m_46470_() && this.isChanneling()) {
         BlockPos blockpos = entity.m_20183_();
         if (this.f_19853_.m_45527_(blockpos)) {
            LightningBolt lightningbolt = (LightningBolt)EntityType.f_20465_.m_20615_(this.f_19853_);
            lightningbolt.m_20219_(Vec3.m_82539_(blockpos));
            lightningbolt.m_20879_(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
            this.f_19853_.m_7967_(lightningbolt);
            soundevent = SoundEvents.f_12521_;
            f1 = 5.0F;
         }
      }

      this.m_5496_(soundevent, f1, 1.0F);
   }

   public boolean isChanneling() {
      return EnchantmentHelper.m_44936_(this.tridentItem);
   }

   protected boolean m_142470_(Player p_150196_) {
      return super.m_142470_(p_150196_) || this.m_36797_() && this.m_150171_(p_150196_) && p_150196_.m_150109_().m_36054_(this.m_7941_());
   }

   protected SoundEvent m_7239_() {
      return SoundEvents.f_12515_;
   }

   public void m_6123_(Player p_37580_) {
      if (this.m_150171_(p_37580_) || this.m_37282_() == null) {
         super.m_6123_(p_37580_);
      }
   }

   public void m_7378_(CompoundTag p_37578_) {
      super.m_7378_(p_37578_);
      if (p_37578_.m_128425_("CoralSpear", 10)) {
         this.tridentItem = ItemStack.m_41712_(p_37578_.m_128469_("CoralSpear"));
      }

      this.dealtDamage = p_37578_.m_128471_("DealtDamage");
      this.f_19804_.m_135381_(ID_LOYALTY, (byte)EnchantmentHelper.m_44928_(this.tridentItem));
   }

   public void m_7380_(CompoundTag p_37582_) {
      super.m_7380_(p_37582_);
      p_37582_.m_128365_("CoralSpear", this.tridentItem.m_41739_(new CompoundTag()));
      p_37582_.m_128379_("DealtDamage", this.dealtDamage);
   }

   public void m_6901_() {
      int i = (Byte)this.f_19804_.m_135370_(ID_LOYALTY);
      if (this.f_36705_ != Pickup.ALLOWED || i <= 0) {
         super.m_6901_();
      }
   }

   protected float m_6882_() {
      return 1.0F;
   }

   public boolean m_6000_(double p_37588_, double p_37589_, double p_37590_) {
      return true;
   }
}
