package shadows.apotheosis.ench.anvil;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import shadows.apotheosis.Apoth;

public class AnvilTile extends BlockEntity {
   protected final Object2IntMap<Enchantment> enchantments = new Object2IntOpenHashMap();

   public AnvilTile(BlockPos pos, BlockState state) {
      super((BlockEntityType)Apoth.Tiles.ANVIL.get(), pos, state);
   }

   public void m_183515_(CompoundTag tag) {
      ItemStack stack = new ItemStack(Items.f_42146_);
      EnchantmentHelper.m_44865_(this.enchantments, stack);
      tag.m_128365_("enchantments", stack.m_41785_());
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      ListTag enchants = tag.m_128437_("enchantments", 10);
      Map<Enchantment, Integer> map = EnchantmentHelper.m_44882_(enchants);
      this.enchantments.clear();
      this.enchantments.putAll(map);
   }

   public Object2IntMap<Enchantment> getEnchantments() {
      return this.enchantments;
   }
}
