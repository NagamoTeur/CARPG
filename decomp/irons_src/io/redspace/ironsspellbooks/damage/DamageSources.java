package io.redspace.ironsspellbooks.damage;

import io.redspace.ironsspellbooks.api.entity.NoKnockbackProjectile;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import java.util.HashMap;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DamageSources {
   public static DamageSource CAULDRON = new DamageSource("blood_cauldron");
   public static DamageSource HEARTSTOP = new DamageSource("heartstop").m_19380_().m_19382_();
   private static final HashMap<LivingEntity, Integer> knockbackImmunes = new HashMap<>();

   public static boolean applyDamage(Entity target, float baseAmount, DamageSource damageSource) {
      if (target instanceof LivingEntity livingTarget && damageSource instanceof ISpellDamageSource spellDamageSource) {
         SpellDamageEvent e = new SpellDamageEvent(livingTarget, baseAmount, spellDamageSource);
         if (MinecraftForge.EVENT_BUS.post(e)) {
            return false;
         }

         baseAmount = e.getAmount();
         float adjustedDamage = baseAmount * getResist(livingTarget, spellDamageSource.schoolType());
         MagicSummon fromSummon = damageSource.m_7640_() instanceof MagicSummon summonx
            ? summonx
            : (damageSource.m_7639_() instanceof MagicSummon summon ? summon : null);
         if (fromSummon != null) {
            if (fromSummon.getSummoner() != null) {
               adjustedDamage *= (float)fromSummon.getSummoner().m_21133_((Attribute)AttributeRegistry.SUMMON_DAMAGE.get());
            }
         } else if (damageSource.m_7640_() instanceof NoKnockbackProjectile) {
            ignoreNextKnockback(livingTarget);
         }

         if (damageSource.m_7639_() instanceof LivingEntity livingAttacker) {
            if (isFriendlyFireBetween(livingAttacker, livingTarget)) {
               return false;
            }

            livingAttacker.m_21335_(target);
         }

         boolean flag = livingTarget.m_6469_(damageSource, adjustedDamage);
         if (fromSummon instanceof LivingEntity livingSummon) {
            livingTarget.m_6703_(livingSummon);
         }

         return flag;
      }

      return target.m_6469_(damageSource, baseAmount);
   }

   public static void ignoreNextKnockback(LivingEntity livingEntity) {
      if (!livingEntity.f_19853_.f_46443_) {
         knockbackImmunes.put(livingEntity, livingEntity.f_19797_);
      }
   }

   @SubscribeEvent
   public static void cancelKnockback(LivingKnockBackEvent event) {
      if (knockbackImmunes.containsKey(event.getEntity())) {
         LivingEntity entity = event.getEntity();
         if (entity.f_19797_ - knockbackImmunes.get(entity) <= 1) {
            event.setCanceled(true);
         }

         knockbackImmunes.remove(entity);
      }
   }

   @SubscribeEvent
   public static void postHitEffects(LivingDamageEvent event) {
      if (event.getSource() instanceof ISpellDamageSource spellDamageSource && spellDamageSource.hasPostHitEffects()) {
         float actualDamage = event.getAmount();
         LivingEntity target = event.getEntity();
         if (event.getSource().m_7639_() instanceof LivingEntity livingAttacker && spellDamageSource.getLifestealPercent() > 0.0F) {
            livingAttacker.m_5634_(spellDamageSource.getLifestealPercent() * actualDamage);
         }

         if (spellDamageSource.getFreezeTicks() > 0 && target.m_142079_()) {
            target.m_146917_(target.m_146888_() + spellDamageSource.getFreezeTicks() * 2);
         }

         if (spellDamageSource.getFireTime() > 0) {
            target.m_20254_(spellDamageSource.getFireTime());
         }
      }
   }

   public static boolean isFriendlyFireBetween(Entity attacker, Entity target) {
      if (attacker == null || target == null) {
         return false;
      } else if (attacker.m_20365_(target)) {
         return true;
      } else {
         Team team = attacker.m_5647_();
         return team == null ? attacker.m_7307_(target) : team.m_83536_(target.m_5647_()) && !team.m_6260_();
      }
   }

   public static DamageSource directDamageSource(DamageSource source, Entity attacker) {
      return new EntityDamageSource(source.m_19385_(), attacker);
   }

   public static DamageSource indirectDamageSource(DamageSource source, Entity projectile, @Nullable Entity attacker) {
      return new IndirectEntityDamageSource(source.f_19326_, projectile, attacker);
   }

   public static float getResist(LivingEntity entity, SchoolType damageSchool) {
      double baseResist = entity.m_21133_((Attribute)AttributeRegistry.SPELL_RESIST.get());
      return damageSchool == null
         ? 2.0F - (float)Utils.softCapFormula(baseResist)
         : 2.0F - (float)Utils.softCapFormula(damageSchool.getResistanceFor(entity) * baseResist);
   }
}
