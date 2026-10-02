package net.sweenus.simplyswords.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class WatcherEffect extends MobEffect {
   public WatcherEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_()) {
         LivingEntity pPlayer = pLivingEntity.m_21188_();
         if (pPlayer != null && pPlayer instanceof Player) {
            ServerLevel world = (ServerLevel)pLivingEntity.f_19853_;
            BlockPos position = pLivingEntity.m_20183_();
            int hradius = (int)SimplySwordsConfig.getFloatValue("watcher_radius");
            int vradius = (int)(SimplySwordsConfig.getFloatValue("watcher_radius") / 2.0F);
            double x = pLivingEntity.m_20185_();
            double y = pLivingEntity.m_20186_();
            double z = pLivingEntity.m_20189_();
            float rAmount = SimplySwordsConfig.getFloatValue("watcher_restore_amount");
            AABB box = new AABB(x + (double)hradius, y + (double)vradius, z + (double)hradius, x - (double)hradius, y - (double)vradius, z - (double)hradius);

            for (Entity e : world.m_6249_(pPlayer, box, EntitySelector.f_20402_)) {
               if (e instanceof LivingEntity && pPlayer instanceof Player) {
                  Player player = (Player)pPlayer;
                  if (HelperMethods.checkFriendlyFire((LivingEntity)e, player)) {
                     e.m_6469_(DamageSource.f_146701_, rAmount);
                     pPlayer.m_21153_(pPlayer.m_21223_() + rAmount);
                     BlockPos position2 = e.m_20183_();
                     world.m_5594_(null, position2, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.05F, 1.2F);
                  }
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
