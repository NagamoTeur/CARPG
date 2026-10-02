package com.cerbon.bosses_of_mass_destruction.entity.ai;

import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TargetSwitcher {
   private final Mob entity;
   private final DamageMemory damageMemory;
   private final int ageToRemember = 600;

   public TargetSwitcher(Mob entity, DamageMemory damageMemory) {
      this.entity = entity;
      this.damageMemory = damageMemory;
   }

   public void trySwitchTarget() {
      Entity newTarget = this.damageMemory
         .getDamageHistory()
         .stream()
         .filter(this::filterTargetableEntities)
         .filter(h -> h.ageWhenDamaged() + 600 >= this.entity.f_19797_)
         .filter(damageHistory -> damageHistory.source().m_7639_() != null)
         .collect(Collectors.groupingBy(h -> h.source().m_7639_()))
         .entrySet()
         .stream()
         .max(Comparator.comparingDouble(e -> e.getValue().stream().mapToDouble(DamageMemory.DamageHistory::amount).sum()))
         .map(Entry::getKey)
         .orElse(null);
      if (newTarget != null && !newTarget.equals(this.entity.m_5448_()) && this.entity.m_217043_().m_188503_(2) == 0) {
         this.entity.m_6710_((LivingEntity)newTarget);
      }
   }

   private boolean filterTargetableEntities(DamageMemory.DamageHistory damageHistory) {
      Entity attacker = damageHistory.source().m_7639_();
      if (attacker instanceof LivingEntity) {
         boolean canSee = this.entity.m_21574_().m_148306_(attacker);
         double followRange = this.entity.m_21133_(Attributes.f_22277_);
         boolean inRange = this.entity.m_20280_(attacker) < followRange * followRange;
         return canSee && inRange && this.entity.m_6779_((LivingEntity)attacker);
      } else {
         return false;
      }
   }
}
