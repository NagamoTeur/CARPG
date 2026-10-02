package shadows.apotheosis.ench.enchantments.masterwork;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class KnowledgeEnchant extends Enchantment {
   public KnowledgeEnchant() {
      super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int level) {
      return 55 + (level - 1) * 45;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public int m_6586_() {
      return 3;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   public void drops(Player p, LivingDropsEvent e) {
      int knowledge = p.m_21205_().getEnchantmentLevel(this);
      if (knowledge > 0 && !(e.getEntity() instanceof Player)) {
         int items = 0;

         for (ItemEntity i : e.getDrops()) {
            items += i.m_32055_().m_41613_();
         }

         if (items > 0) {
            e.getDrops().clear();
         }

         items *= knowledge * 25;
         Entity ded = e.getEntity();

         while (items > 0) {
            int i = ExperienceOrb.m_20782_(items);
            items -= i;
            p.f_19853_.m_7967_(new ExperienceOrb(p.f_19853_, ded.m_20185_(), ded.m_20186_(), ded.m_20189_(), i));
         }
      }
   }
}
