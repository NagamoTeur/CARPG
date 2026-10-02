package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import java.util.Collection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EntityChimeraProjectile extends AbstractArrow implements IAnimatable {
   int groundMax;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public EntityChimeraProjectile(double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, Level p_i48547_8_) {
      super((EntityType)ModEntities.ENTITY_CHIMERA_SPIKE.get(), p_i48547_2_, p_i48547_4_, p_i48547_6_, p_i48547_8_);
   }

   public EntityChimeraProjectile(LivingEntity p_i48548_2_, Level p_i48548_3_) {
      super((EntityType)ModEntities.ENTITY_CHIMERA_SPIKE.get(), p_i48548_2_, p_i48548_3_);
   }

   public EntityChimeraProjectile(Level world) {
      super((EntityType)ModEntities.ENTITY_CHIMERA_SPIKE.get(), world);
   }

   public EntityChimeraProjectile(EntityType<EntityChimeraProjectile> entityChimeraProjectileEntityType, Level world) {
      super(entityChimeraProjectileEntityType, world);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.f_36704_ >= 1) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   protected void m_5790_(EntityHitResult rayTraceResult) {
      Entity entity = rayTraceResult.m_82443_();
      float damage = 7.5F;
      Entity entity1 = this.m_37282_();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.m_19367_(this, null);
      } else {
         damagesource = DamageSource.f_19319_;
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).m_21335_(entity);
         }
      }

      boolean isEnderman = entity.m_6095_() == EntityType.f_20566_;
      int k = entity.m_20094_();
      if (this.m_6060_() && !isEnderman) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, damage)) {
         if (isEnderman) {
            return;
         }

         if (entity instanceof LivingEntity livingentity) {
            this.m_7761_(livingentity);
         }

         this.m_5496_(this.m_7239_(), 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
         this.m_142687_(RemovalReason.DISCARDED);
      } else {
         entity.m_7311_(k);
         this.m_20256_(this.m_20184_().m_82490_(-0.1));
         this.m_146922_(this.m_146908_() + 180.0F);
         this.f_19859_ += 180.0F;
         if (!this.f_19853_.f_46443_ && this.m_20184_().m_82556_() < 1.0E-7) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   protected void m_7761_(LivingEntity entity) {
      super.m_7761_(entity);
      if (!this.f_19853_.f_46443_) {
         Collection<MobEffectInstance> effects = entity.m_21220_();
         MobEffectInstance[] array = effects.toArray(new MobEffectInstance[0]);

         for (MobEffectInstance e : array) {
            if (e.m_19544_().m_19486_()) {
               entity.m_21195_(e.m_19544_());
            }
         }

         entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2));
      }
   }

   protected boolean m_5603_(Entity entity) {
      if (entity instanceof EntityChimeraProjectile) {
         return false;
      } else {
         if (entity instanceof LivingEntity entity1
            && (
               entity1 instanceof WildenStalker
                  || entity1 instanceof WildenGuardian
                  || entity instanceof WildenHunter
                  || entity instanceof ISummon summon && summon.m_21805_() != null && summon.m_21805_().equals(this.m_20148_())
                  || entity1 instanceof SummonWolf && ((SummonWolf)entity1).isWildenSummon
            )) {
            return false;
         }

         return !(entity instanceof WildenChimera) && super.m_5603_(entity);
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_CHIMERA_SPIKE.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityChimeraProjectile(SpawnEntity packet, Level world) {
      super((EntityType)ModEntities.ENTITY_CHIMERA_SPIKE.get(), world);
   }
}
