package shadows.apotheosis.adventure.affix.salvaging;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.color.GradientColor;

public class SalvageItem extends Item {
   protected final LootRarity rarity;

   public SalvageItem(LootRarity rarity, Properties pProperties) {
      super(pProperties);
      this.rarity = rarity;
   }

   public Component m_7626_(ItemStack pStack) {
      return this.rarity == LootRarity.ANCIENT
         ? Component.m_237115_(this.m_5671_(pStack)).m_130940_(ChatFormatting.OBFUSCATED).m_130938_(s -> s.m_131148_(GradientColor.RAINBOW))
         : Component.m_237115_(this.m_5671_(pStack)).m_130948_(Style.f_131099_.m_131148_(this.rarity.color()));
   }

   public void m_7373_(ItemStack pStack, Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
      list.add(Component.m_237110_("info.apotheosis.rarity_material", new Object[]{this.rarity.toComponent()}).m_130940_(ChatFormatting.GRAY));
   }
}
