package net.thirdlife.iterrpg.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class TwiffleLumpItem extends Item {
   public TwiffleLumpItem() {
      super(
         new Properties()
            .m_41491_(CreativeModeTab.f_40755_)
            .m_41487_(64)
            .m_41497_(Rarity.COMMON)
            .m_41489_(new Builder().m_38760_(6).m_38758_(12.0F).m_38757_().m_38767_())
      );
   }
}
