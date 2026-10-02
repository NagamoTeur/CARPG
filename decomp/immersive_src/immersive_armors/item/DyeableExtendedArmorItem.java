package immersive_armors.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DyeableExtendedArmorItem extends ExtendedArmorItem implements DyeableLeatherItem {
   public DyeableExtendedArmorItem(Properties settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
      super(settings, slot, material);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.m_7373_(stack, world, tooltip, context);
      tooltip.add(Component.m_237115_("immersive_armors.dyeable").m_130940_(ChatFormatting.GOLD));
   }

   public int m_41121_(ItemStack stack) {
      CompoundTag nbtCompound = stack.m_41737_("display");
      return nbtCompound != null && nbtCompound.m_128425_("color", 99) ? nbtCompound.m_128451_("color") : this.getMaterial().getColor();
   }
}
