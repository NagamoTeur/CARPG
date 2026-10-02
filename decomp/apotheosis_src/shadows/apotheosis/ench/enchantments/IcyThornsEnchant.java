package shadows.apotheosis.ench.enchantments;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.util.FakePlayer;

public class IcyThornsEnchant extends Enchantment {
   public IcyThornsEnchant() {
      super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
   }

   public int m_6183_(int level) {
      return 35 + (level - 1) * 20;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_6081_(ItemStack stack) {
      return stack.m_41720_() instanceof ArmorItem ? true : super.m_6081_(stack);
   }

   protected boolean m_5975_(Enchantment pOther) {
      return super.m_5975_(pOther) && pOther != Enchantments.f_44972_;
   }

   public void m_7675_(LivingEntity user, Entity attacker, int level) {
      if (user != null) {
         RandomSource rand = user.m_217043_();
         if (attacker instanceof LivingEntity ent && !(attacker instanceof FakePlayer)) {
            ent.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (100 + rand.m_188503_(100)) * level, level));
         }
      }
   }
}
