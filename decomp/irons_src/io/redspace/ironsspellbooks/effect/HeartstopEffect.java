package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HeartstopEffect extends MagicMobEffect {
   private int duration;

   public HeartstopEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().addEffects(4L);
   }

   public void m_6386_(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(pLivingEntity, pAttributeMap, pAmplifier);
      MagicData playerMagicData = MagicData.getPlayerMagicData(pLivingEntity);
      playerMagicData.getSyncedData().removeEffects(4L);
      if (pLivingEntity.f_19797_ > 60) {
         pLivingEntity.m_6469_(DamageSources.HEARTSTOP, playerMagicData.getSyncedData().getHeartstopAccumulatedDamage());
      } else {
         pLivingEntity.m_6074_();
      }

      playerMagicData.getSyncedData().setHeartstopAccumulatedDamage(0.0F);
   }

   public void m_6742_(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
      if (pLivingEntity.f_19853_.f_46443_ && pLivingEntity instanceof Player player) {
         float damage = ClientMagicData.getSyncedSpellData(player).getHeartstopAccumulatedDamage();
         float f = 1.0F - Mth.m_14036_(damage / player.m_21223_(), 0.0F, 1.0F);
         int i = (int)(10.0F + 30.0F * f);
         if (this.duration % Math.max(i, 1) == 0) {
            player.m_5496_(SoundEvents.f_215762_, 1.0F, 0.85F);
         }
      }
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      this.duration = pDuration;
      return true;
   }
}
