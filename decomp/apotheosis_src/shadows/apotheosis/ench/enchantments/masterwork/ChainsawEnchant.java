package shadows.apotheosis.ench.enchantments.masterwork;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import shadows.apotheosis.ench.EnchModule;
import shadows.apotheosis.util.BlockUtil;
import shadows.placebo.util.PlaceboTaskQueue;

public class ChainsawEnchant extends Enchantment {
   public ChainsawEnchant() {
      super(Rarity.VERY_RARE, EnchModule.AXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6586_() {
      return 1;
   }

   public int m_6183_(int level) {
      return 55;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.canPerformAction(ToolActions.AXE_DIG) || super.canApplyAtEnchantingTable(stack);
   }

   public void chainsaw(BreakEvent e) {
      Player player = e.getPlayer();
      Level level = player.f_19853_;
      ItemStack stack = player.m_21205_();
      int enchLevel = stack.getEnchantmentLevel(this);
      if (player.getClass() == ServerPlayer.class
         && enchLevel > 0
         && !level.f_46443_
         && this.isTree(level, e.getPos(), e.getState())
         && !player.m_150110_().f_35937_) {
         PlaceboTaskQueue.submitTask("apotheosis:chainsaw_task", new ChainsawEnchant.ChainsawTask(player.m_20148_(), stack, level, e.getPos()));
      }
   }

   private boolean isTree(Level level, BlockPos pos, BlockState state) {
      if (!state.m_204336_(BlockTags.f_13106_)) {
         return false;
      } else {
         while (state.m_204336_(BlockTags.f_13106_)) {
            state = level.m_8055_(pos = pos.m_7494_());
         }

         for (BlockPos p : BlockPos.m_121940_(pos.m_7918_(-2, -2, -2), pos.m_7918_(2, 2, 2))) {
            if (level.m_8055_(p).m_204336_(BlockTags.f_13035_)) {
               return true;
            }
         }

         return false;
      }
   }

   private static class ChainsawTask implements BooleanSupplier {
      UUID owner;
      ItemStack axe;
      ServerLevel level;
      Int2ObjectMap<Queue<BlockPos>> hits = new Int2ObjectOpenHashMap();
      int ticks = 0;

      public ChainsawTask(UUID owner, ItemStack axe, Level level, BlockPos pos) {
         this.owner = owner;
         this.axe = axe;
         this.level = (ServerLevel)level;
         ((Queue)this.hits.computeIfAbsent(pos.m_123342_(), i -> new ArrayDeque())).add(pos);
      }

      @Override
      public boolean getAsBoolean() {
         if (++this.ticks % 2 != 0) {
            return false;
         } else if (this.axe.m_41619_()) {
            return true;
         } else {
            int minY = this.hits.keySet().intStream().min().getAsInt();
            Queue<BlockPos> queue = (Queue<BlockPos>)this.hits.get(minY);
            int breaks = 0;

            while (!queue.isEmpty()) {
               BlockPos pos = queue.poll();

               for (BlockPos p : BlockPos.m_121940_(pos.m_7918_(-1, 0, -1), pos.m_7918_(1, 1, 1))) {
                  if (!p.equals(pos)) {
                     BlockState state = this.level.m_8055_(p);
                     if (state.m_204336_(BlockTags.f_13106_)) {
                        BlockUtil.breakExtraBlock(this.level, p, this.axe, this.owner);
                        if (!this.level.m_8055_(p).m_204336_(BlockTags.f_13106_)) {
                           ((Queue)this.hits.computeIfAbsent(p.m_123342_(), i -> new ArrayDeque())).add(p.m_7949_());
                           breaks++;
                        }
                     }
                  }
               }

               if (breaks > 5) {
                  break;
               }
            }

            if (queue.isEmpty()) {
               this.hits.remove(minY);
            }

            return this.hits.isEmpty();
         }
      }
   }
}
