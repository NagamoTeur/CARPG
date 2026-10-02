package shadows.apotheosis.spawn.enchantment;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.EnchModule;
import shadows.apotheosis.spawn.SpawnerModule;

public class CapturingEnchant extends Enchantment {
   public CapturingEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6586_() {
      return 5;
   }

   public int m_6183_(int level) {
      return 28 + (level - 1) * 15;
   }

   public int m_6175_(int level) {
      return this.m_6183_(level) + 15;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return super.canApplyAtEnchantingTable(stack) || EnchModule.AXE.m_7454_(stack.m_41720_());
   }

   public void handleCapturing(LivingDropsEvent e) {
      if (e.getSource().m_7639_() instanceof LivingEntity living) {
         int level = living.m_21205_().getEnchantmentLevel((Enchantment)Apoth.Enchantments.CAPTURING.get());
         LivingEntity killed = e.getEntity();
         if (SpawnerModule.bannedMobs.contains(EntityType.m_20613_(killed.m_6095_()))) {
            return;
         }

         if (killed.f_19853_.f_46441_.m_188501_() < (float)level / 250.0F) {
            Item eggItem = ForgeSpawnEggItem.fromEntityType(killed.m_6095_());
            if (eggItem == null) {
               return;
            }

            ItemStack egg = new ItemStack(eggItem);
            e.getDrops().add(new ItemEntity(killed.f_19853_, killed.m_20185_(), killed.m_20186_(), killed.m_20189_(), egg));
         }
      }
   }
}
