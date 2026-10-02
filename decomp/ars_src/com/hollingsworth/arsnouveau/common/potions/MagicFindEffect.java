package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MagicFindEffect extends MobEffect {
   protected MagicFindEffect() {
      super(MobEffectCategory.BENEFICIAL, new ParticleColor(30, 200, 200).getColor());
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      Level level = pLivingEntity.f_19853_;
      if (!level.f_46443_ && level.m_46467_() % 60L == 0L) {
         for (Entity e : level.m_45933_(pLivingEntity, new AABB(pLivingEntity.m_20183_()).m_82400_(75.0))) {
            if (e instanceof LivingEntity) {
               LivingEntity living = (LivingEntity)e;
               if (living.m_6095_().m_204039_(EntityTags.MAGIC_FIND)) {
                  living.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 1200));
               }
            }
         }
      }
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
