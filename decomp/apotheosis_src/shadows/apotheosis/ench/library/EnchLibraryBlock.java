package shadows.apotheosis.ench.library;

import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.container.SimplerMenuProvider;

public class EnchLibraryBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final Component NAME = Component.m_237115_("apotheosis.ench.library");
   protected final BlockEntitySupplier<? extends EnchLibraryTile> tileSupplier;
   protected final int maxLevel;

   public EnchLibraryBlock(BlockEntitySupplier<? extends EnchLibraryTile> tileSupplier, int maxLevel) {
      super(Properties.m_60944_(Material.f_76278_, MaterialColor.f_76364_).m_60913_(5.0F, 1200.0F));
      this.tileSupplier = tileSupplier;
      this.maxLevel = maxLevel;
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      return ContainerUtil.openGui(player, pos, EnchLibraryContainer::new);
   }

   public MenuProvider m_7246_(BlockState state, Level world, BlockPos pos) {
      return new SimplerMenuProvider(world, pos, EnchLibraryContainer::new);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{f_54117_});
   }

   public BlockState m_5573_(BlockPlaceContext p_196258_1_) {
      return (BlockState)this.m_49966_().m_61124_(f_54117_, p_196258_1_.m_8125_().m_122424_());
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return this.tileSupplier.m_155267_(pPos, pState);
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      ItemStack s = new ItemStack(this);
      BlockEntity te = world.m_7702_(pos);
      if (te != null) {
         s.m_41784_().m_128365_("BlockEntityTag", te.m_187482_());
      }

      return s;
   }

   public void m_6402_(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      BlockEntity te = world.m_7702_(pos);
      if (te != null) {
         te.m_142466_(stack.m_41698_("BlockEntityTag"));
      }
   }

   public List<ItemStack> m_7381_(BlockState state, net.minecraft.world.level.storage.loot.LootContext.Builder ctx) {
      ItemStack s = new ItemStack(this);
      BlockEntity te = (BlockEntity)ctx.m_78970_(LootContextParams.f_81462_);
      if (te != null) {
         s.m_41784_().m_128365_("BlockEntityTag", te.m_187482_());
      }

      return Arrays.asList(s);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_5871_(ItemStack stack, BlockGetter world, List<Component> list, TooltipFlag advanced) {
      list.add(
         Component.m_237110_("tooltip.enchlib.capacity", new Object[]{Component.m_237115_("enchantment.level." + this.maxLevel)})
            .m_130940_(ChatFormatting.GOLD)
      );
      CompoundTag tag = stack.m_41737_("BlockEntityTag");
      if (tag != null && tag.m_128441_("Points")) {
         list.add(Component.m_237110_("tooltip.enchlib.item", new Object[]{tag.m_128469_("Points").m_128440_()}).m_130940_(ChatFormatting.GOLD));
      }
   }

   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (newState.m_60734_() != this) {
         world.m_46747_(pos);
      }
   }
}
