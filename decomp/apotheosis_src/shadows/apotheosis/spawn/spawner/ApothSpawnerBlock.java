package shadows.apotheosis.spawn.spawner;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import shadows.apotheosis.advancements.AdvancementTriggers;
import shadows.apotheosis.spawn.SpawnerModule;
import shadows.apotheosis.spawn.modifiers.SpawnerModifier;
import shadows.apotheosis.spawn.modifiers.SpawnerStats;
import shadows.placebo.util.IReplacementBlock;

public class ApothSpawnerBlock extends SpawnerBlock implements IReplacementBlock {
   protected StateDefinition<Block, BlockState> container;

   public ApothSpawnerBlock() {
      super(Properties.m_60939_(Material.f_76278_).m_60978_(5.0F).m_60918_(SoundType.f_56743_).m_60955_());
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
      if (te != null && stack.m_41782_()) {
         te.m_142466_(stack.m_41698_("BlockEntityTag"));
      }
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ApothSpawnerTile(pPos, pState);
   }

   public void m_6240_(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
      super.m_6240_(world, player, pos, state, te, stack);
      if (SpawnerModule.spawnerSilkLevel != -1 && stack.getEnchantmentLevel(Enchantments.f_44985_) >= SpawnerModule.spawnerSilkLevel) {
         if (SpawnerModule.spawnerSilkDamage > 1) {
            player.m_21205_().m_41622_(SpawnerModule.spawnerSilkDamage - 1, player, pl -> pl.m_21166_(EquipmentSlot.MAINHAND));
         }

         player.m_36246_(Stats.f_12949_.m_12902_(this));
         player.m_36399_(0.035F);
      }
   }

   @Deprecated
   public List<ItemStack> m_7381_(BlockState state, Builder params) {
      ItemStack tool = (ItemStack)params.m_78970_(LootContextParams.f_81463_);
      if (SpawnerModule.spawnerSilkLevel != -1 && tool.getEnchantmentLevel(Enchantments.f_44985_) >= SpawnerModule.spawnerSilkLevel) {
         ItemStack s = new ItemStack(this);
         BlockEntity te = (BlockEntity)params.m_78982_(LootContextParams.f_81462_);
         if (te != null) {
            s.m_41784_().m_128365_("BlockEntityTag", te.m_187482_());
         }

         return List.of(s);
      } else {
         return super.m_7381_(state, params);
      }
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      BlockEntity te = world.m_7702_(pos);
      ItemStack stack = player.m_21120_(hand);
      ItemStack otherStack = player.m_21120_(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
      if (te instanceof ApothSpawnerTile tile) {
         SpawnerModifier match = SpawnerModifier.findMatch(tile, stack, otherStack);
         if (match != null && match.apply(tile)) {
            if (world.f_46443_) {
               return InteractionResult.SUCCESS;
            }

            if (!player.m_7500_()) {
               stack.m_41774_(1);
               if (match.consumesOffhand()) {
                  otherStack.m_41774_(1);
               }
            }

            AdvancementTriggers.SPAWNER_MODIFIER.trigger((ServerPlayer)player, tile, match);
            world.m_7260_(pos, state, state, 3);
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public void m_5871_(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (stack.m_41782_() && stack.m_41783_().m_128425_("BlockEntityTag", 10)) {
         if (Screen.m_96638_()) {
            CompoundTag tag = stack.m_41783_().m_128469_("BlockEntityTag");
            if (tag.m_128441_("MinSpawnDelay")) {
               tooltip.add(concat(SpawnerStats.MIN_DELAY.name(), tag.m_128448_("MinSpawnDelay")));
            }

            if (tag.m_128441_("MaxSpawnDelay")) {
               tooltip.add(concat(SpawnerStats.MAX_DELAY.name(), tag.m_128448_("MaxSpawnDelay")));
            }

            if (tag.m_128441_("SpawnCount")) {
               tooltip.add(concat(SpawnerStats.SPAWN_COUNT.name(), tag.m_128448_("SpawnCount")));
            }

            if (tag.m_128441_("MaxNearbyEntities")) {
               tooltip.add(concat(SpawnerStats.MAX_NEARBY_ENTITIES.name(), tag.m_128448_("MaxNearbyEntities")));
            }

            if (tag.m_128441_("RequiredPlayerRange")) {
               tooltip.add(concat(SpawnerStats.REQ_PLAYER_RANGE.name(), tag.m_128448_("RequiredPlayerRange")));
            }

            if (tag.m_128441_("SpawnRange")) {
               tooltip.add(concat(SpawnerStats.SPAWN_RANGE.name(), tag.m_128448_("SpawnRange")));
            }

            if (tag.m_128471_("ignore_players")) {
               tooltip.add(SpawnerStats.IGNORE_PLAYERS.name().m_130940_(ChatFormatting.DARK_GREEN));
            }

            if (tag.m_128471_("ignore_conditions")) {
               tooltip.add(SpawnerStats.IGNORE_CONDITIONS.name().m_130940_(ChatFormatting.DARK_GREEN));
            }

            if (tag.m_128471_("redstone_control")) {
               tooltip.add(SpawnerStats.REDSTONE_CONTROL.name().m_130940_(ChatFormatting.DARK_GREEN));
            }

            if (tag.m_128471_("ignore_light")) {
               tooltip.add(SpawnerStats.IGNORE_LIGHT.name().m_130940_(ChatFormatting.DARK_GREEN));
            }

            if (tag.m_128471_("no_ai")) {
               tooltip.add(SpawnerStats.NO_AI.name().m_130940_(ChatFormatting.DARK_GREEN));
            }

            if (tag.m_128471_("silent")) {
               tooltip.add(SpawnerStats.SILENT.name().m_130940_(ChatFormatting.DARK_GREEN));
            }
         } else {
            tooltip.add(Component.m_237115_("misc.apotheosis.shift_stats").m_130940_(ChatFormatting.GRAY));
         }
      }
   }

   public static Component concat(Object... args) {
      return Component.m_237110_("misc.apotheosis.value_concat", new Object[]{args[0], Component.m_237113_(args[1].toString()).m_130940_(ChatFormatting.GRAY)})
         .m_130940_(ChatFormatting.GREEN);
   }

   public Item m_5456_() {
      return Items.f_42007_;
   }

   public int getExpDrop(BlockState state, LevelReader world, RandomSource randomSource, BlockPos pos, int fortune, int silktouch) {
      return silktouch == 0 ? super.getExpDrop(state, world, randomSource, pos, fortune, silktouch) : 0;
   }

   public void _setDefaultState(BlockState state) {
      this.m_49959_(state);
   }

   public void setStateContainer(StateDefinition<Block, BlockState> container) {
      this.container = container;
   }

   public StateDefinition<Block, BlockState> m_49965_() {
      return this.container == null ? super.m_49965_() : this.container;
   }
}
