package shadows.apotheosis.ench.compat;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.anvil.AnvilTile;
import shadows.apotheosis.ench.anvil.ApothAnvilBlock;
import shadows.apotheosis.util.CommonTooltipUtil;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class EnchHwylaPlugin implements IWailaPlugin, IBlockComponentProvider, IServerDataProvider<BlockEntity> {
   public void register(IWailaCommonRegistration reg) {
      if (Apotheosis.enableEnch) {
         reg.registerBlockDataProvider(this, AnvilTile.class);
      }
   }

   public void registerClient(IWailaClientRegistration reg) {
      if (Apotheosis.enableEnch) {
         reg.registerBlockComponent(this, Block.class);
      }
   }

   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
      if (accessor.getBlock() instanceof ApothAnvilBlock) {
         CompoundTag tag = accessor.getServerData();
         Map<Enchantment, Integer> enchants = EnchantmentHelper.m_44882_(tag.m_128437_("enchantments", 10));

         for (Entry<Enchantment, Integer> e : enchants.entrySet()) {
            tooltip.add(e.getKey().m_44700_(e.getValue()));
         }
      }

      CommonTooltipUtil.appendBlockStats(accessor.getLevel(), accessor.getBlockState(), tooltip::add);
      if (accessor.getBlock() == Blocks.f_50201_) {
         CommonTooltipUtil.appendTableStats(accessor.getLevel(), accessor.getPosition(), tooltip::add);
      }
   }

   public void appendServerData(CompoundTag tag, ServerPlayer player, Level world, BlockEntity te, boolean something) {
      if (te instanceof AnvilTile) {
         ItemStack stack = new ItemStack(Items.f_42146_);
         EnchantmentHelper.m_44865_(((AnvilTile)te).getEnchantments(), stack);
         tag.m_128365_("enchantments", stack.m_41785_());
      }
   }

   public ResourceLocation getUid() {
      return Apotheosis.loc("ench");
   }
}
