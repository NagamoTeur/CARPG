package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.KeystoneRightclickedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class KeystoneItem extends Item {
   public KeystoneItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41503_(50).m_41486_().m_41497_(Rarity.EPIC));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§fA powerful artifact used by mages long ago. Capable of teleporting a user to thier home when in the overworld."));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      ItemStack itemstack = (ItemStack)ar.m_19095_();
      double x = entity.m_20185_();
      double y = entity.m_20186_();
      double z = entity.m_20189_();
      KeystoneRightclickedProcedure.execute(world, x, y, z, entity, itemstack);
      return ar;
   }
}
