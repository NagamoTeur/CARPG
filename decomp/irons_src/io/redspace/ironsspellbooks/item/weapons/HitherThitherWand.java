package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class HitherThitherWand extends Item implements IPresetSpellContainer, UniqueItem {
   public HitherThitherWand(Properties pProperties) {
      super(pProperties);
   }

   public void m_7373_(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
      pTooltipComponents.add(Component.m_237115_("tooltip.irons_spellbooks.spellbook_unique").m_130948_(TooltipsUtils.UNIQUE_STYLE));
   }

   @Override
   public void initializeSpellContainer(ItemStack itemStack) {
      if (itemStack != null) {
         if (!ISpellContainer.isSpellContainer(itemStack)) {
            ISpellContainer spellContainer = ISpellContainer.create(1, true, false);
            spellContainer.addSpell((AbstractSpell)SpellRegistry.PORTAL_SPELL.get(), 1, true, itemStack);
            spellContainer.save(itemStack);
         }
      }
   }
}
