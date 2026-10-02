package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.EnchModuleEvents;

public class SpearfishingEnchant extends Enchantment {
   public SpearfishingEnchant() {
      super(Rarity.UNCOMMON, EnchantmentCategory.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int pEnchantmentLevel) {
      return 12 + (pEnchantmentLevel - 1) * 18;
   }

   public int m_6175_(int pEnchantmentLevel) {
      return 200;
   }

   public int m_6586_() {
      return 5;
   }

   public void addFishes(LivingDropsEvent e) {
      DamageSource src = e.getSource();
      if (src.m_7640_() instanceof ThrownTrident trident) {
         if (trident.f_19853_.f_46443_) {
            return;
         }

         ItemStack triStack = ((EnchModuleEvents.TridentGetter)trident).getTridentItem();
         int level = triStack.getEnchantmentLevel(this);
         if (trident.f_19796_.m_188501_() < 3.5F * (float)level) {
            Entity dead = e.getEntity();
            e.getDrops()
               .add(
                  new ItemEntity(
                     trident.f_19853_,
                     dead.m_20185_(),
                     dead.m_20186_(),
                     dead.m_20189_(),
                     new ItemStack(
                        (ItemLike)ForgeRegistries.ITEMS.tags().getTag(Apoth.Tags.SPEARFISHING_DROPS).getRandomElement(trident.f_19796_).orElse(Items.f_41852_),
                        1 + trident.f_19796_.m_188503_(3)
                     )
                  )
               );
         }
      }
   }
}
