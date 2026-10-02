package shadows.apotheosis.village.fletching.arrows;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.apotheosis.Apotheosis;

public class BroadheadArrowItem extends ArrowItem implements IApothArrowItem {
   public BroadheadArrowItem() {
      super(new Properties().m_41491_(Apotheosis.APOTH_GROUP));
   }

   public AbstractArrow m_6394_(Level world, ItemStack stack, LivingEntity shooter) {
      return new BroadheadArrowEntity(shooter, world).bleed();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("info.apotheosis.broadhead_arrow").m_130940_(ChatFormatting.RED));
   }

   @Override
   public AbstractArrow fromDispenser(Level world, double x, double y, double z) {
      AbstractArrow e = new BroadheadArrowEntity(world, x, y, z).bleed();
      e.f_36705_ = Pickup.ALLOWED;
      return e;
   }
}
