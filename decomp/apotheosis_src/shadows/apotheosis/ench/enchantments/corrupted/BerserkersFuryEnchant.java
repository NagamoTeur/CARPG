package shadows.apotheosis.ench.enchantments.corrupted;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;

public class BerserkersFuryEnchant extends Enchantment {
   public BerserkersFuryEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
   }

   public int m_6183_(int level) {
      return 50 + level * 40;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_6589_() {
      return true;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_RED);
   }

   public void livingHurt(LivingHurtEvent e) {
      LivingEntity user = e.getEntity();
      if (e.getSource().m_7639_() instanceof Entity && user.m_21124_(MobEffects.f_19606_) == null) {
         int level = EnchantmentHelper.m_44836_(this, user);
         if (level > 0) {
            if (Affix.isOnCooldown(Registry.f_122825_.m_7981_(this), 900, user)) {
               return;
            }

            user.f_19802_ = 0;
            user.m_6469_(Apotheosis.CORRUPTED, (float)Math.pow(2.5, (double)level));
            user.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 500, level - 1));
            user.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 500, level - 1));
            user.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 500, level - 1));
            Affix.startCooldown(Registry.f_122825_.m_7981_(this), user);
         }
      }
   }
}
