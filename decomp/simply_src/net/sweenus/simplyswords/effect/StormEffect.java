package net.sweenus.simplyswords.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.util.HelperMethods;

public class StormEffect extends MobEffect {
   public StormEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)pLivingEntity.f_19853_;
         BlockPos position = pLivingEntity.m_20183_();
         int hradius = (int)SimplySwordsConfig.getFloatValue("storm_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("storm_radius") / 2.0F);
         double x = pLivingEntity.m_20185_();
         double y = pLivingEntity.m_20186_();
         double z = pLivingEntity.m_20189_();
         LivingEntity pPlayer = pLivingEntity.m_21188_();
         AABB box = new AABB(x + (double)hradius, y + (double)vradius, z + (double)hradius, x - (double)hradius, y - (double)vradius, z - (double)hradius);

         for (Entity e : world.m_6249_(pPlayer, box, EntitySelector.f_20403_)) {
            if (e instanceof LivingEntity) {
               LivingEntity ee = (LivingEntity)e;
               if (pPlayer instanceof Player) {
                  Player player = (Player)pPlayer;
                  if (ee.m_20070_() && HelperMethods.checkFriendlyFire(ee, player)) {
                     BlockPos stormtarget = ee.m_20183_();
                     if (ee.m_20270_(pPlayer) >= 5.0F) {
                        Entity var20 = EntityType.f_20465_.m_20600_(world, null, null, null, stormtarget, MobSpawnType.TRIGGERED, true, true);
                     }
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
