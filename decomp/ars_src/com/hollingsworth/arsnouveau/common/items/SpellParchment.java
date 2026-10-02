package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class SpellParchment extends ModItem implements ICasterTool {
   public SpellParchment(Properties properties) {
      super(properties);
   }

   public SpellParchment() {
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      ISpellCaster caster = this.getSpellCaster(stack);
      if (caster.getSpell().isEmpty()) {
         tooltip2.add(Component.m_237115_("ars_nouveau.tooltip.from_blank"));
      } else {
         this.getInformation(stack, worldIn, tooltip2, flagIn);
         super.m_7373_(stack, worldIn, tooltip2, flagIn);
      }
   }
}
