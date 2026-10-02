package shadows.apotheosis.village.fletching.arrows;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.apotheosis.Apotheosis;

public class MiningArrowItem extends ArrowItem implements IApothArrowItem {
   protected final Supplier<Item> breakerItem;
   protected final MiningArrowEntity.Type arrowType;

   public MiningArrowItem(Supplier<Item> breakerItem, MiningArrowEntity.Type arrowType) {
      super(new Properties().m_41491_(Apotheosis.APOTH_GROUP));
      this.breakerItem = breakerItem;
      this.arrowType = arrowType;
   }

   public AbstractArrow m_6394_(Level world, ItemStack stack, LivingEntity shooter) {
      return new MiningArrowEntity(shooter, world, new ItemStack((ItemLike)this.breakerItem.get()), this.arrowType);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("info.apotheosis.mining_arrow." + this.arrowType.name().toLowerCase(Locale.ROOT)).m_130940_(ChatFormatting.GOLD));
   }

   @Override
   public AbstractArrow fromDispenser(Level world, double x, double y, double z) {
      return new MiningArrowEntity(world, x, y, z, new ItemStack((ItemLike)this.breakerItem.get()), this.arrowType);
   }
}
