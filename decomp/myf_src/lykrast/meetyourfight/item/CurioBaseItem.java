package lykrast.meetyourfight.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CurioBaseItem extends Item implements ICurioItem {
   private boolean hasDescription;

   public CurioBaseItem(Properties properties, boolean hasDescription) {
      super(properties);
      this.hasDescription = hasDescription;
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (this.hasDescription) {
         tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
      }
   }
}
