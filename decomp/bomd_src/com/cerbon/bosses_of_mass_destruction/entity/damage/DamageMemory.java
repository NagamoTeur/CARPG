package com.cerbon.bosses_of_mass_destruction.entity.damage;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DamageMemory implements IDamageHandler {
   private final HistoricalData<DamageMemory.DamageHistory> historicalData;
   private final LivingEntity entity;

   public DamageMemory(int hitsToRemember, LivingEntity entity) {
      this.entity = entity;
      DamageMemory.DamageHistory defaultDamageHistory = new DamageMemory.DamageHistory(0.0F, DamageSource.f_19317_, 0);
      this.historicalData = new HistoricalData<>(defaultDamageHistory, hitsToRemember);
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      int minimumDamageToNotice = 4;
      if (result && damageSource.m_7639_() != null && amount > (float)minimumDamageToNotice) {
         this.historicalData.set(new DamageMemory.DamageHistory(amount, damageSource, this.entity.f_19797_));
      }
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }

   public List<DamageMemory.DamageHistory> getDamageHistory() {
      return this.historicalData.getAll();
   }

   public static record DamageHistory(float amount, DamageSource source, int ageWhenDamaged) {
   }
}
