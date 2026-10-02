package com.hollingsworth.arsnouveau.client.events;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.spell.casters.ReactiveCaster;
import java.util.Collections;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class PlayerEvent {
   @SubscribeEvent
   public static void onBlock(RightClickBlock event) {
      Player entity = event.getEntity();
      if (event.getLevel().f_46443_
         && event.getHand() == InteractionHand.MAIN_HAND
         && !(event.getLevel().m_8055_(event.getPos()).m_60734_() instanceof ScribesBlock)) {
         if (entity.m_21120_(event.getHand()).m_41720_() instanceof SpellBook) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onTooltip(ItemTooltipEvent event) {
      ItemStack stack = event.getItemStack();
      int level = EnchantmentHelper.m_44843_((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), stack);
      if (level > 0 && new ReactiveCaster(stack).getSpell().isValid()) {
         Spell spell = new ReactiveCaster(stack).getSpell();
         event.getToolTip().add(Component.m_237113_(spell.getDisplayString()));
      }

      Collections.addAll(event.getToolTip(), ClientInfo.storageTooltip);
   }

   @SubscribeEvent
   public static void onItem(RightClickItem event) {
      Player entity = event.getEntity();
      if (event.getLevel().f_46443_ && event.getHand() == InteractionHand.MAIN_HAND) {
         if (entity.m_21120_(event.getHand()).m_41720_() instanceof SpellBook) {
            event.setCanceled(true);
         }
      }
   }
}
