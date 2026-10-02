package shadows.apotheosis.core.attributeslib.asm;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import shadows.apotheosis.core.attributeslib.api.ALAttributes;

public class ALCombatRules {
   public static float getDamageAfterProtection(LivingEntity target, DamageSource src, float amount, float protPoints) {
      if (src.m_7639_() instanceof LivingEntity attacker) {
         float shred = (float)attacker.m_21133_((Attribute)ALAttributes.PROT_SHRED.get());
         if (shred > 0.001F) {
            protPoints *= 1.0F - shred;
         }

         float pierce = (float)attacker.m_21133_((Attribute)ALAttributes.PROT_PIERCE.get());
         if (pierce > 0.001F) {
            protPoints -= pierce;
         }
      }

      return protPoints <= 0.0F ? amount : amount * getProtDamageReduction(protPoints);
   }

   public static float getProtDamageReduction(float protPoints) {
      return 1.0F - Math.min(0.025F * protPoints, 0.85F);
   }

   public static float getDamageAfterArmor(LivingEntity target, DamageSource src, float amount, float armor, float toughness) {
      if (src.m_7639_() instanceof LivingEntity attacker) {
         float shred = (float)attacker.m_21133_((Attribute)ALAttributes.ARMOR_SHRED.get());
         float bypassResist = Math.min(toughness * 0.02F, 0.6F);
         if (shred > 0.001F) {
            shred *= 1.0F - bypassResist;
            armor *= 1.0F - shred;
         }

         float pierce = (float)attacker.m_21133_((Attribute)ALAttributes.ARMOR_PIERCE.get());
         if (pierce > 0.001F) {
            pierce *= 1.0F - bypassResist;
            armor -= pierce;
         }
      }

      return armor <= 0.0F ? amount : amount * getArmorDamageReduction(amount, armor);
   }

   public static float getAValue(float damage) {
      return damage < 20.0F ? 10.0F : 10.0F + (damage - 20.0F) / 2.0F;
   }

   public static float getArmorDamageReduction(float damage, float armor) {
      float a = getAValue(damage);
      return a / (a + armor);
   }
}
