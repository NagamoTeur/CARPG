package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.ISpellDamageSource;
import io.redspace.ironsspellbooks.entity.spells.EchoingStrikeEntity;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EchoingStrikesEffect extends MagicMobEffect {
   public EchoingStrikesEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   @SubscribeEvent
   public static void createEcho(LivingHurtEvent event) {
      DamageSource damageSource = event.getSource();
      if (damageSource.m_7639_() instanceof LivingEntity attacker
         && (damageSource.m_7640_() == attacker || damageSource.m_7640_() instanceof AbstractArrow)
         && !(damageSource instanceof ISpellDamageSource)) {
         MobEffectInstance effect = attacker.m_21124_((MobEffect)MobEffectRegistry.ECHOING_STRIKES.get());
         if (effect != null) {
            float percent = getDamageModifier(effect.m_19564_(), attacker);
            EchoingStrikeEntity echo = new EchoingStrikeEntity(attacker.f_19853_, attacker, event.getAmount() * percent, 3.0F);
            echo.m_146884_(event.getEntity().m_20191_().m_82399_().m_82492_(0.0, (double)(echo.m_20206_() * 0.5F), 0.0));
            attacker.f_19853_.m_7967_(echo);
         }
      }
   }

   public static float getDamageModifier(int effectAmplifier, @Nullable LivingEntity caster) {
      float power = caster == null ? 1.0F : ((AbstractSpell)SpellRegistry.ECHOING_STRIKES_SPELL.get()).getEntityPowerMultiplier(caster);
      return ((float)(effectAmplifier - 4) * power + 5.0F) * 0.1F;
   }
}
