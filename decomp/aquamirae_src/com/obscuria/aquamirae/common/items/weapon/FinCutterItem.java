package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class FinCutterItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "fin_cutter").var(15, "%").var(150, "%").build(FinCutterItem.class);

   public FinCutterItem() {
      super(AquamiraeTiers.FIN_CUTTER, 3, -2.0F, new Properties().m_41491_(Aquamirae.TAB));
   }
}
