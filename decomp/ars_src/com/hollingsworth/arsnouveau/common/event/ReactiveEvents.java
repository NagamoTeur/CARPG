package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketReactiveSpell;
import com.hollingsworth.arsnouveau.common.spell.casters.ReactiveCaster;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class ReactiveEvents {
   @SubscribeEvent
   public static void livingHitEvent(LivingHurtEvent e) {
      LivingEntity entity = e.getEntity();
      if (!entity.m_20193_().f_46443_) {
         for (ItemStack s : entity.m_6168_()) {
            castSpell(entity, s);
         }
      }
   }

   public static void castSpell(LivingEntity playerIn, ItemStack s) {
      if ((double)s.getEnchantmentLevel((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()) * 0.25 >= Math.random()
         && new ReactiveCaster(s).getSpell().isValid()) {
         ReactiveCaster reactiveCaster = new ReactiveCaster(s);
         reactiveCaster.castSpell(playerIn.m_20193_(), playerIn, InteractionHand.MAIN_HAND, null);
      }
   }

   @SubscribeEvent
   public static void leftClickBlock(LeftClickBlock e) {
      Player entity = e.getEntity();
      if (!entity.m_20193_().f_46443_) {
         ItemStack s = e.getItemStack();
         castSpell(entity, s);
      }
   }

   @SubscribeEvent
   public static void playerAttackEntity(AttackEntityEvent e) {
      Player entity = e.getEntity();
      if (entity != null && !entity.m_20193_().f_46443_) {
         ItemStack s = e.getEntity().m_21205_();
         castSpell(entity, s);
      }
   }

   @SubscribeEvent
   public static void leftClickAir(LeftClickEmpty e) {
      if (e.getItemStack().getEnchantmentLevel((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()) > 0) {
         Networking.INSTANCE.sendToServer(new PacketReactiveSpell());
      }
   }
}
