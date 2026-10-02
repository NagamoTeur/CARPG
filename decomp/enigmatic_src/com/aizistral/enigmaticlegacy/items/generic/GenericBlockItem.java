package com.aizistral.enigmaticlegacy.items.generic;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class GenericBlockItem extends BlockItem {
   public GenericBlockItem(Block blockIn) {
      super(blockIn, getDefaultProperties());
   }

   public GenericBlockItem(Block blockIn, Properties props) {
      super(blockIn, props);
   }

   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.m_41491_(EnigmaticLegacy.MAIN_TAB);
      props.m_41487_(64);
      props.m_41497_(Rarity.COMMON);
      return props;
   }
}
