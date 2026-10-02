package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.function.Consumer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class Black_Steel_Targe extends ShieldItem {
   public Black_Steel_Targe(Properties properties) {
      super(properties);
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return repair.m_150930_((Item)ModItems.BLACK_STEEL_INGOT.get()) || !repair.m_204117_(ItemTags.f_13168_) && super.m_6832_(toRepair, repair);
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }
}
