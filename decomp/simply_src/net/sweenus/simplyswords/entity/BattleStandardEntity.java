package net.sweenus.simplyswords.entity;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.Objects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class BattleStandardEntity extends PathfinderMob {
   public static final Supplier<EntityType<BattleStandardEntity>> TYPE = Suppliers.memoize(
      () -> Builder.m_20704_(BattleStandardEntity::new, MobCategory.MISC).m_20712_("battlestandard")
   );
   int abilityDamage = (int)SimplySwordsConfig.getFloatValue("abyssalstandard_damage");
   public Player ownerEntity;
   public String standardType;
   public int decayRate;

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createBattleStandardAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 150.0).m_22268_(Attributes.f_22279_, 0.0).m_22268_(Attributes.f_22278_, 100.0);
   }

   public BattleStandardEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
      super(entityType, world);
   }

   protected boolean m_6107_() {
      return true;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return this.ownerEntity == null;
   }

   public void m_6075_() {
      if (!this.f_19853_.m_5776_()) {
         if (this.f_19797_ % 10 == 0) {
            this.m_21153_(this.m_21223_() - (float)this.decayRate);
            if (this.ownerEntity == null) {
               this.m_21153_(this.m_21223_() - 1000.0F);
            }
         }

         if (this.ownerEntity != null && this.standardType != null) {
            int radius = 6;
            int abilityDamage = 2;
            if (this.f_19797_ % 10 == 0) {
               AABB box = new AABB(
                  this.m_20185_() + (double)radius,
                  this.m_20186_() + (double)((float)radius / 3.0F),
                  this.m_20189_() + (double)radius,
                  this.m_20185_() - (double)radius,
                  this.m_20186_() - (double)((float)radius / 3.0F),
                  this.m_20189_() - (double)radius
               );

               for (Entity entities : this.f_19853_.m_6249_(this, box, EntitySelector.f_20403_)) {
                  if (entities != null && entities instanceof LivingEntity) {
                     LivingEntity le = (LivingEntity)entities;
                     if (HelperMethods.checkFriendlyFire(le, this.ownerEntity)
                        && le != this.ownerEntity
                        && !(le instanceof BattleStandardEntity)
                        && !(le instanceof BattleStandardDarkEntity)) {
                        if (Objects.equals(this.standardType, "sunfire")) {
                           le.m_6469_(DamageSource.f_19319_, (float)abilityDamage);
                           le.m_20254_(1);
                           le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, 1), this);
                        }

                        if (Objects.equals(this.standardType, "nullification")) {
                           for (MobEffectInstance statusEffect : le.m_21220_()) {
                              if (statusEffect != null && statusEffect.m_19544_().m_19486_()) {
                                 le.m_21195_(statusEffect.m_19544_());
                                 break;
                              }
                           }
                        }
                     }
                  }
               }

               HelperMethods.spawnParticle(this.f_19853_, ParticleTypes.f_123756_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
            }

            if (this.m_21223_() > this.m_21233_() - 2.0F && this.m_20096_()) {
               HelperMethods.spawnParticle(this.f_19853_, ParticleTypes.f_123756_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.3, 0.0);
               HelperMethods.spawnParticle(this.f_19853_, ParticleTypes.f_123777_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.1, 0.0);
               AABB box = new AABB(
                  this.m_20185_() + 1.0, this.m_20186_() + 1.0, this.m_20189_() + 1.0, this.m_20185_() - 1.0, this.m_20186_() - 1.0, this.m_20189_() - 1.0
               );

               for (Entity entitiesx : this.f_19853_.m_6249_(this, box, EntitySelector.f_20403_)) {
                  if (entitiesx != null && entitiesx instanceof LivingEntity) {
                     LivingEntity le = (LivingEntity)entitiesx;
                     if (HelperMethods.checkFriendlyFire(le, this.ownerEntity) && le != this.ownerEntity) {
                        le.m_6469_(DamageSource.f_19319_, (float)(abilityDamage * 3));
                        le.m_20254_(1);
                        le.m_20334_((le.m_20185_() - this.m_20185_()) / 4.0, 0.5, (le.m_20189_() - this.m_20189_()) / 4.0);
                     }
                  }
               }
            }

            if (this.f_19797_ % 80 == 0) {
               AABB box = new AABB(
                  this.m_20185_() + (double)radius,
                  this.m_20186_() + (double)((float)radius / 3.0F),
                  this.m_20189_() + (double)radius,
                  this.m_20185_() - (double)radius,
                  this.m_20186_() - (double)((float)radius / 3.0F),
                  this.m_20189_() - (double)radius
               );

               for (Entity entitiesxx : this.f_19853_.m_6249_(this, box, EntitySelector.f_20403_)) {
                  if (entitiesxx != null && entitiesxx instanceof LivingEntity) {
                     LivingEntity le = (LivingEntity)entitiesxx;
                     if (!HelperMethods.checkFriendlyFire(le, this.ownerEntity)) {
                        if (Objects.equals(this.standardType, "sunfire")) {
                           le.m_5634_(3.0F);
                           le.m_147207_(new MobEffectInstance(MobEffects.f_19600_, 90, 1), this);
                        }

                        if (Objects.equals(this.standardType, "nullification")) {
                           for (MobEffectInstance statusEffectx : le.m_21220_()) {
                              if (statusEffectx != null
                                 && !statusEffectx.m_19544_().m_19486_()
                                 && !Objects.equals(statusEffectx.m_19544_(), EffectRegistry.BATTLE_FATIGUE.get())) {
                                 le.m_21195_(statusEffectx.m_19544_());
                                 break;
                              }
                           }
                        }
                     }
                  }
               }

               this.f_19853_.m_6269_(null, this, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.1F, 0.6F);
               double xpos = this.m_20185_() - (double)(radius + 1);
               double ypos = this.m_20186_();
               double zpos = this.m_20189_() - (double)(radius + 1);

               for (int i = radius * 2; i > 0; i--) {
                  for (int j = radius * 2; j > 0; j--) {
                     float choose = (float)(Math.random() * 1.0);
                     if ((double)choose > 0.5) {
                        HelperMethods.spawnParticle(
                           this.f_19853_, ParticleTypes.f_123777_, xpos + (double)i + (double)choose, ypos, zpos + (double)j + (double)choose, 0.0, -0.1, 0.0
                        );
                     }
                  }
               }
            }
         }
      }

      super.m_6075_();
   }
}
