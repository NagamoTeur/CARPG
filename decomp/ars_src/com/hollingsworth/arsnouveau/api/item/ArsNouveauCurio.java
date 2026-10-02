package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class ArsNouveauCurio extends ModItem implements ICurioItem {
   public ArsNouveauCurio() {
      this(new Properties().m_41487_(1).m_41491_(ArsNouveau.itemGroup));
   }

   public ArsNouveauCurio(Properties properties) {
      super(properties);
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }
}
