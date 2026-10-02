package shadows.apotheosis.ench.anvil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.advancements.AdvancementTriggers;
import shadows.apotheosis.util.INBTSensitiveFallingBlock;

public class ApothAnvilBlock extends AnvilBlock implements INBTSensitiveFallingBlock, EntityBlock {
   public ApothAnvilBlock() {
      super(Properties.m_60944_(Material.f_76281_, MaterialColor.f_76404_).m_60913_(5.0F, 1200.0F).m_60918_(SoundType.f_56749_));
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new AnvilTile(pPos, pState);
   }

   public void m_6240_(Level world, Player player, BlockPos pos, BlockState state, BlockEntity te, ItemStack stack) {
      ItemStack anvil = new ItemStack(this);
      if (te instanceof AnvilTile) {
         Map<Enchantment, Integer> ench = ((AnvilTile)te).getEnchantments();
         ench = ench.entrySet().stream().filter(e -> e.getValue() > 0).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
         EnchantmentHelper.m_44865_(ench, anvil);
      }

      m_49840_(world, pos, anvil);
      super.m_6240_(world, player, pos, state, te, stack);
   }

   public void m_6402_(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      if (world.m_7702_(pos) instanceof AnvilTile anvil) {
         Map<Enchantment, Integer> ench = EnchantmentHelper.m_44831_(stack);
         ench.keySet().removeIf(e -> !this.m_5456_().canApplyAtEnchantingTable(stack, e));
         anvil.getEnchantments().putAll(ench);
      }
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      return Collections.emptyList();
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      ItemStack anvil = new ItemStack(this);
      BlockEntity te = world.m_7702_(pos);
      if (te instanceof AnvilTile) {
         Map<Enchantment, Integer> ench = ((AnvilTile)te).getEnchantments();
         ench = ench.entrySet().stream().filter(e -> e.getValue() > 0).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
         EnchantmentHelper.m_44865_(ench, anvil);
      }

      return anvil;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_5871_(ItemStack stack, BlockGetter world, List<Component> tooltip, TooltipFlag flagIn) {
      if (!stack.m_41790_()) {
         tooltip.add(Component.m_237115_("info.apotheosis.anvil").m_130940_(ChatFormatting.GRAY));
      }
   }

   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!((BlockEntityType)Apoth.Tiles.ANVIL.get()).m_155262_(newState)) {
         world.m_46747_(pos);
      }
   }

   public void m_213897_(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRand) {
      if (m_53241_(pLevel.m_8055_(pPos.m_7495_())) && pPos.m_123342_() >= pLevel.m_141937_()) {
         BlockEntity be = pLevel.m_7702_(pPos);
         FallingBlockEntity e = FallingBlockEntity.m_201971_(pLevel, pPos, pState);
         if (be instanceof AnvilTile anvil) {
            e.f_31944_ = new CompoundTag();
            anvil.m_183515_(e.f_31944_);
         }

         this.m_6788_(e);
      }
   }

   public void m_48792_(Level world, BlockPos pos, BlockState fallState, BlockState hitState, FallingBlockEntity anvil) {
      super.m_48792_(world, pos, fallState, hitState, anvil);
      List<ItemEntity> items = world.m_45976_(ItemEntity.class, new AABB(pos, pos.m_7918_(1, 1, 1)));
      if (anvil.f_31944_ != null) {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44882_(anvil.f_31944_.m_128437_("enchantments", 10));
         int oblit = enchantments.getOrDefault(Apoth.Enchantments.OBLITERATION.get(), 0);
         int split = enchantments.getOrDefault(Apoth.Enchantments.SPLITTING.get(), 0);
         int ub = enchantments.getOrDefault(Enchantments.f_44986_, 0);
         if (split > 0 || oblit > 0) {
            for (ItemEntity entity : items) {
               ItemStack stack = entity.m_32055_();
               if (stack.m_41720_() == Items.f_42690_) {
                  ListTag enchants = EnchantedBookItem.m_41163_(stack);
                  boolean handled = false;
                  if (enchants.size() == 1 && oblit > 0) {
                     handled = this.handleObliteration(world, pos, entity, enchants);
                  } else if (enchants.size() > 1 && split > 0) {
                     handled = this.handleSplitting(world, pos, entity, enchants);
                  }

                  if (handled) {
                     if (world.f_46441_.m_188503_(1 + ub) == 0) {
                        BlockState dmg = m_48824_(fallState);
                        if (dmg == null) {
                           world.m_46597_(pos, Blocks.f_50016_.m_49966_());
                           world.m_46796_(1029, pos, 0);
                        } else {
                           world.m_46597_(pos, dmg);
                        }
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   protected boolean handleSplitting(Level world, BlockPos pos, ItemEntity entity, ListTag enchants) {
      entity.m_142687_(RemovalReason.DISCARDED);

      for (Tag nbt : enchants) {
         CompoundTag tag = (CompoundTag)nbt;
         int level = tag.m_128451_("lvl");
         Enchantment enchant = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(tag.m_128461_("id")));
         if (enchant != null) {
            ItemStack book = EnchantedBookItem.m_41161_(new EnchantmentInstance(enchant, level));
            Block.m_49840_(world, pos.m_7494_(), book);
         }
      }

      world.m_6443_(ServerPlayer.class, new AABB(pos).m_82377_(5.0, 5.0, 5.0), EntitySelector.f_20408_)
         .forEach(p -> AdvancementTriggers.SPLIT_BOOK.trigger(p.m_8960_()));
      return true;
   }

   protected boolean handleObliteration(Level world, BlockPos pos, ItemEntity entity, ListTag enchants) {
      CompoundTag nbt = enchants.m_128728_(0);
      int level = nbt.m_128451_("lvl") - 1;
      if (level <= 0) {
         return false;
      } else {
         Enchantment enchant = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(nbt.m_128461_("id")));
         if (enchant == null) {
            return false;
         } else {
            ItemStack book = EnchantedBookItem.m_41161_(new EnchantmentInstance(enchant, level));
            entity.m_142687_(RemovalReason.DISCARDED);
            Block.m_49840_(world, pos.m_7494_(), book);
            Block.m_49840_(world, pos.m_7494_(), book.m_41777_());
            return true;
         }
      }
   }

   @Override
   public ItemStack toStack(BlockState state, CompoundTag tag) {
      ItemStack anvil = new ItemStack(this);
      Map<Enchantment, Integer> ench = EnchantmentHelper.m_44882_(tag.m_128437_("enchantments", 10));
      ench = ench.entrySet().stream().filter(e -> e.getValue() > 0).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentHelper.m_44865_(ench, anvil);
      return anvil;
   }
}
