package io.redspace.ironsspellbooks.item;

import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UpgradeOrbItem extends Item {
   private final UpgradeType upgrade;
   private static final Component TOOLTIP_HEADER = Component.m_237115_("tooltip.irons_spellbooks.upgrade_tooltip").m_130940_(ChatFormatting.GRAY);
   private final Component TOOLTIP_TEXT;

   public UpgradeOrbItem(UpgradeType upgrade, Properties pProperties) {
      super(pProperties);
      this.upgrade = upgrade;
      this.TOOLTIP_TEXT = Component.m_237113_(" ")
         .m_7220_(
            Component.m_237110_(
                  "attribute.modifier.plus." + upgrade.getOperation().m_22235_(),
                  new Object[]{
                     ItemStack.f_41584_.format((double)(upgrade.getAmountPerUpgrade() * (float)(upgrade.getOperation() == Operation.ADDITION ? 1 : 100))),
                     Component.m_237115_(upgrade.getAttribute().m_22087_())
                  }
               )
               .m_130940_(ChatFormatting.BLUE)
         );
   }

   public UpgradeType getUpgradeType() {
      return this.upgrade;
   }

   public Component m_7626_(ItemStack pStack) {
      return super.m_7626_(pStack);
   }

   public void m_7373_(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
      pTooltipComponents.add(Component.m_237119_());
      pTooltipComponents.add(TOOLTIP_HEADER);
      pTooltipComponents.add(this.TOOLTIP_TEXT);
   }
}
