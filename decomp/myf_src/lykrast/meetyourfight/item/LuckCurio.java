package lykrast.meetyourfight.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class LuckCurio extends CurioBaseItem {
   public static final String TOOLTIP_LUCK = "item.meetyourfight.desc.luck";

   public LuckCurio(Properties properties) {
      super(properties, true);
   }

   @Override
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_("item.meetyourfight.desc.luck").m_130940_(ChatFormatting.GRAY));
   }
}
