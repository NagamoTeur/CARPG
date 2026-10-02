package net.sweenus.simplyswords.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class ImmolationEffect extends MobEffect {
   public ImmolationEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_() && pLivingEntity instanceof Player player && pLivingEntity.f_19797_ % 40 == 0) {
         if (!player.m_7500_() && player.m_21223_() > 4.0F) {
            pLivingEntity.m_21153_(pLivingEntity.m_21223_() - 0.5F);
         }

         player.f_19853_.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.3F, 1.0F);
         HelperMethods.spawnParticle(player.f_19853_, ParticleTypes.f_123756_, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), 0.3, 0.8, 0.2);
         HelperMethods.spawnParticle(player.f_19853_, ParticleTypes.f_123756_, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), -0.2, 0.6, 0.3);
         HelperMethods.spawnParticle(player.f_19853_, ParticleTypes.f_123756_, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), 0.5, 0.3, -0.2);
         HelperMethods.spawnParticle(player.f_19853_, ParticleTypes.f_123762_, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), 0.0, 0.0, 0.0);
         int radius = 3;
         float abilityDamage = player.m_21223_() / 6.0F;
         ItemStack checkMainStack = player.m_21205_();
         ItemStack checkOffStack = player.m_21206_();
         if (!(checkMainStack.m_41720_() instanceof SwordItem) && !(checkOffStack.m_41720_() instanceof SwordItem)) {
            player.m_21195_((MobEffect)EffectRegistry.IMMOLATION.get());
         } else {
            if (player.m_21205_().m_41782_()) {
               CompoundTag rpnbt = player.m_21205_().m_41783_();
               if (rpnbt != null
                  && !player.m_21205_().m_41783_().m_128461_("runic_power").equals("immolation")
                  && !player.m_21205_().m_41783_().m_128461_("nether_power").equals("radiance")) {
                  player.m_21195_((MobEffect)EffectRegistry.IMMOLATION.get());
               }
            }

            if (player.m_21205_().m_41782_()) {
               CompoundTag rpnbt = player.m_21206_().m_41783_();
               if (rpnbt != null
                  && !player.m_21206_().m_41783_().m_128461_("runic_power").equals("immolation")
                  && !player.m_21206_().m_41783_().m_128461_("nether_power").equals("radiance")) {
                  player.m_21195_((MobEffect)EffectRegistry.IMMOLATION.get());
               }
            }
         }

         if (player.m_21223_() < 4.0F) {
            player.m_21195_((MobEffect)EffectRegistry.IMMOLATION.get());
         }

         AABB box = new AABB(
            player.m_20185_() + (double)radius,
            player.m_20186_() + (double)radius,
            player.m_20189_() + (double)radius,
            player.m_20185_() - (double)radius,
            player.m_20186_() - (double)radius,
            player.m_20189_() - (double)radius
         );

         for (Entity entities : player.f_19853_.m_6249_(player, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (HelperMethods.checkFriendlyFire(le, player)) {
                  le.m_6469_(DamageSource.f_19319_, abilityDamage);
                  le.m_20254_(1);
               }
            }
         }
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
