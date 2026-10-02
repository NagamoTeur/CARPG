package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.ChampionCoinItemIsDroppedByPlayerProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ChampionCoinItem extends Item {
   public ChampionCoinItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(64).m_41486_().m_41497_(Rarity.EPIC));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§dA pristine coin minted for the celebration of momentous events."));
   }

   public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
      ChampionCoinItemIsDroppedByPlayerProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      return true;
   }
}
