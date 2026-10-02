package net.sweenus.simplyswords.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.util.HelperMethods;

public class WildfireEffect extends MobEffect {
   public WildfireEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_()) {
         LivingEntity pPlayer = pLivingEntity.m_21188_();
         if (pPlayer != null && pPlayer instanceof Player) {
            ServerLevel world = (ServerLevel)pLivingEntity.f_19853_;
            BlockPos position = pLivingEntity.m_20183_();
            int hradius = (int)SimplySwordsConfig.getFloatValue("wildfire_radius");
            int vradius = (int)(SimplySwordsConfig.getFloatValue("wildfire_radius") / 2.0F);
            double x = pLivingEntity.m_20185_();
            double y = pLivingEntity.m_20186_();
            double z = pLivingEntity.m_20189_();
            int pduration = (int)SimplySwordsConfig.getFloatValue("wildfire_duration") / 20;
            AABB box = new AABB(x + (double)hradius, y + (double)vradius, z + (double)hradius, x - (double)hradius, y - (double)vradius, z - (double)hradius);

            for (Entity e : world.m_142425_(pLivingEntity.m_6095_(), box, EntitySelector.f_20402_)) {
               if (e instanceof LivingEntity && HelperMethods.checkFriendlyFire((LivingEntity)e, (Player)pPlayer)) {
                  e.m_20254_(pduration);
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
