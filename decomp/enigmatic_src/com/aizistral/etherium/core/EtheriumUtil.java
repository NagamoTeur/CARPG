package com.aizistral.etherium.core;

import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.etherium.blocks.BlockEtherium;
import com.aizistral.etherium.items.EnderRod;
import com.aizistral.etherium.items.EtheriumArmor;
import com.aizistral.etherium.items.EtheriumAxe;
import com.aizistral.etherium.items.EtheriumIngot;
import com.aizistral.etherium.items.EtheriumNugget;
import com.aizistral.etherium.items.EtheriumOre;
import com.aizistral.etherium.items.EtheriumPickaxe;
import com.aizistral.etherium.items.EtheriumScraps;
import com.aizistral.etherium.items.EtheriumScythe;
import com.aizistral.etherium.items.EtheriumShovel;
import com.aizistral.etherium.items.EtheriumSword;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class EtheriumUtil {
   public static Properties defaultProperties(Class<?> itemClass) {
      IEtheriumConfig config = EtheriumConfigHandler.instance();
      Properties props = new Properties();
      props.m_41487_(1);
      props.m_41497_(Rarity.RARE);
      if (config.isStandalone()) {
         if (isAmong(itemClass, EnderRod.class, EtheriumOre.class, EtheriumIngot.class, EtheriumScraps.class, EtheriumNugget.class)) {
            props.m_41491_(CreativeModeTab.f_40759_);
         } else if (isAmong(itemClass, EtheriumAxe.class, EtheriumPickaxe.class, EtheriumScythe.class, EtheriumShovel.class)) {
            props.m_41491_(CreativeModeTab.f_40756_);
         } else if (isAmong(itemClass, EtheriumSword.class, EtheriumArmor.class)) {
            props.m_41491_(CreativeModeTab.f_40757_);
         } else if (itemClass == BlockEtherium.class) {
            props.m_41491_(CreativeModeTab.f_40749_);
         } else {
            props.m_41491_(CreativeModeTab.f_40753_);
         }
      } else {
         props.m_41491_(config.getCreativeTab());
      }

      return props;
   }

   private static boolean isAmong(Class<?> theClass, Class<?>... classList) {
      for (Class<?> someClass : classList) {
         if (theClass == someClass) {
            return true;
         }
      }

      return false;
   }
}
