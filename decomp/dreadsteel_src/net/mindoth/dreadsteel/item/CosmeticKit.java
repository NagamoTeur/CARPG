package net.mindoth.dreadsteel.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CosmeticKit extends Item {
   public CosmeticKit(Properties p_i48487_1_) {
      super(p_i48487_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("tooltip.dreadsteel.cosmetic_kit"));
      super.m_7373_(stack, world, tooltip, flagIn);
   }
}
