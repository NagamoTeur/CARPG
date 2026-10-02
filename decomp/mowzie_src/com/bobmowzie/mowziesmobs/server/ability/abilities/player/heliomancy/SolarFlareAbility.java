package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

public class SolarFlareAbility extends PlayerAbility {
   public SolarFlareAbility(AbilityType<Player, SolarFlareAbility> abilityType, Player user) {
      super(abilityType, user, EntityUmvuthi.SolarFlareAbility.SECTION_TRACK);
   }

   @Override
   public void start() {
      super.start();
      this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BURST.get(), 1.7F, 1.5F);
      this.playAnimation("solar_flare", false);
      if (this.getLevel().f_46443_) {
         this.heldItemMainHandVisualOverride = ItemStack.f_41583_;
         this.heldItemOffHandVisualOverride = ItemStack.f_41583_;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() != null && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : this.getUser().m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get()) && super.canUse();
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() < 16) {
         this.getUser().m_7292_(new MobEffectInstance(MobEffects.f_19597_, 2, 2, false, false));
      }

      if (this.getTicksInUse() <= 6 && this.getLevel().f_46443_) {
         int particleCount = 8;

         while (--particleCount != 0) {
            double radius = 2.0;
            double yaw = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
            double pitch = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
            double ox = radius * Math.sin(yaw) * Math.sin(pitch);
            double oy = radius * Math.cos(pitch);
            double oz = radius * Math.cos(yaw) * Math.sin(pitch);
            this.getLevel()
               .m_7106_(
                  new ParticleOrb.OrbData(
                     (float)this.getUser().m_20185_(),
                     (float)this.getUser().m_20186_() + this.getUser().m_20206_() / 2.0F,
                     (float)this.getUser().m_20189_(),
                     6.0F
                  ),
                  this.getUser().m_20185_() + ox,
                  this.getUser().m_20186_() + (double)(this.getUser().m_20206_() / 2.0F) + oy,
                  this.getUser().m_20189_() + oz,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (this.getTicksInUse() == 10 && this.getLevel().f_46443_) {
         for (int i = 0; i < 30; i++) {
            float velocity = 0.25F;
            float yaw = (float)i * (float) (Math.PI / 15);
            float vy = this.rand.nextFloat() * 0.1F - 0.05F;
            float vx = 0.25F * Mth.m_14089_(yaw);
            float vz = 0.25F * Mth.m_14031_(yaw);
            this.getLevel()
               .m_7106_(
                  ParticleTypes.f_123744_,
                  this.getUser().m_20185_(),
                  this.getUser().m_20186_() + 1.0,
                  this.getUser().m_20189_(),
                  (double)vx,
                  (double)vy,
                  (double)vz
               );
         }
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         Player user = this.getUser();
         float radius = 3.2F;

         for (LivingEntity aHit : this.getEntityLivingBaseNearby(user, (double)radius, (double)radius, (double)radius, (double)radius)) {
            if (aHit != this.getUser()) {
               float damage = 2.0F;
               float knockback = 3.0F;
               damage = (float)((double)damage * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
               if (aHit.m_6469_(DamageSource.m_19344_(user), damage) && knockback > 0.0F) {
                  Vec3 vec3 = aHit.m_20182_().m_82546_(user.m_20182_()).m_82541_().m_82490_((double)knockback * 0.6);
                  if (vec3.m_82556_() > 0.0) {
                     aHit.m_5997_(vec3.f_82479_, 0.1, vec3.f_82481_);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onLeftClickEmpty(LeftClickEmpty event) {
      super.onLeftClickEmpty(event);
      if (event.getEntity() == this.getUser() && event.getEntity().m_6144_()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_FLARE_ABILITY);
      }
   }

   @Override
   public void onLeftClickEntity(AttackEntityEvent event) {
      super.onLeftClickEntity(event);
      if (event.getEntity() == this.getUser() && event.getEntity().m_6144_()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_FLARE_ABILITY);
      }
   }
}
