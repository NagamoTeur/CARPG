package shadows.apotheosis.ench.enchantments.masterwork;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ScavengerEnchant extends Enchantment {
   private static final MethodHandle dropFromLootTable;

   public ScavengerEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int level) {
      return 55 + level * level * 12;
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

   public void drops(Player p, LivingDropsEvent e) throws Throwable {
      if (!p.f_19853_.f_46443_) {
         int scavenger = p.m_21205_().getEnchantmentLevel(this);
         if (scavenger > 0 && (float)p.f_19853_.f_46441_.m_188503_(100) < (float)scavenger * 2.5F) {
            e.getEntity().captureDrops(new ArrayList());
            dropFromLootTable.invoke((LivingEntity)e.getEntity(), (DamageSource)e.getSource(), (boolean)true);
            e.getDrops().addAll(e.getEntity().captureDrops(null));
         }
      }
   }

   static {
      Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_7625_", new Class[]{DamageSource.class, boolean.class});

      try {
         m.setAccessible(true);
         dropFromLootTable = MethodHandles.lookup().unreflect(m);
      } catch (IllegalAccessException var2) {
         throw new RuntimeException("LivingEntity#dropFromLootTable not located!");
      }
   }
}
