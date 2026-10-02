package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EvasionEffect extends CustomDescriptionMobEffect {
   public static Set<DamageSource> excludeDamageSources = Set.of(
      DamageSource.f_19307_,
      DamageSource.f_19320_,
      DamageSource.f_146701_,
      DamageSources.CAULDRON,
      DamageSource.f_19313_,
      DamageSource.f_19312_,
      DamageSource.f_146703_,
      DamageSource.f_19310_,
      DamageSource.f_19317_
   );

   public EvasionEffect(MobEffectCategory mobEffectCategory, int color) {
      super(mobEffectCategory, color);
   }

   @Override
   public Component getDescriptionLine(MobEffectInstance instance) {
      int amp = instance.m_19564_() + 1;
      return Component.m_237110_("tooltip.irons_spellbooks.evasion_description", new Object[]{amp}).m_130940_(ChatFormatting.BLUE);
   }

   public void m_6386_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(pLivingEntity, pAttributeMap, pAmplifier);
      MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().removeEffects(2L);
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().addEffects(2L);
      MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().setEvasionHitsRemaining(pAmplifier);
   }

   public static boolean doEffect(LivingEntity livingEntity, DamageSource damageSource) {
      if (!livingEntity.f_19853_.f_46443_
         && !excludeDamageSources.contains(damageSource)
         && !damageSource.m_146707_()
         && !damageSource.m_19379_()
         && !damageSource.m_19378_()) {
         SyncedSpellData data = MagicData.getPlayerMagicData(livingEntity).getSyncedData();
         data.subtractEvasionHit();
         if (data.getEvasionHitsRemaining() < 0) {
            livingEntity.m_21195_((MobEffect)MobEffectRegistry.EVASION.get());
         }

         double d0 = livingEntity.m_20185_();
         double d1 = livingEntity.m_20186_();
         double d2 = livingEntity.m_20189_();
         double maxRadius = 18.0;
         Level level = livingEntity.f_19853_;
         RandomSource random = livingEntity.m_217043_();

         for (int i = 0; i < 16; i++) {
            double minRadius = maxRadius / 2.0;
            Vec3 vec = new Vec3((double)random.m_216339_((int)minRadius, (int)maxRadius), 0.0, 0.0);
            int degrees = random.m_188503_(360);
            vec = vec.m_82524_((float)degrees);
            double x = d0 + vec.f_82479_;
            double y = Mth.m_14008_(
               livingEntity.m_20186_() + ((double)livingEntity.m_217043_().m_188503_((int)maxRadius) - maxRadius / 2.0),
               (double)level.m_141937_(),
               (double)(level.m_141937_() + ((ServerLevel)level).m_143344_() - 1)
            );
            double z = d2 + vec.f_82481_;
            if (livingEntity.m_20159_()) {
               livingEntity.m_8127_();
            }

            if (livingEntity.m_20984_(x, y, z, true)) {
               if (damageSource.m_7639_() != null) {
                  livingEntity.m_7618_(Anchor.EYES, damageSource.m_7639_().m_146892_());
               }

               level.m_6263_((Player)null, d0, d1, d2, SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, 1.0F);
               livingEntity.m_5496_(SoundEvents.f_11852_, 2.0F, 1.0F);
               break;
            }

            if (maxRadius > 2.0) {
               maxRadius--;
            }
         }

         particleCloud(livingEntity);
         return true;
      } else {
         return false;
      }
   }

   private static void particleCloud(LivingEntity entity) {
      Vec3 pos = entity.m_20182_().m_82520_(0.0, (double)(entity.m_20206_() / 2.0F), 0.0);
      MagicManager.spawnParticles(
         entity.f_19853_,
         ParticleTypes.f_123760_,
         pos.f_82479_,
         pos.f_82480_,
         pos.f_82481_,
         70,
         (double)(entity.m_20205_() / 4.0F),
         (double)(entity.m_20206_() / 5.0F),
         (double)(entity.m_20205_() / 4.0F),
         0.035,
         false
      );
   }
}
