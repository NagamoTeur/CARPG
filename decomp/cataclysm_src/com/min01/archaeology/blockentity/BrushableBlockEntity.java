package com.min01.archaeology.blockentity;

import com.min01.archaeology.block.BrushableBlock;
import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class BrushableBlockEntity extends BlockEntity {
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String LOOT_TABLE_TAG = "LootTable";
   private static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
   private static final String HIT_DIRECTION_TAG = "hit_direction";
   private static final String ITEM_TAG = "item";
   private static final int BRUSH_COOLDOWN_TICKS = 10;
   private static final int BRUSH_RESET_TICKS = 40;
   private static final int REQUIRED_BRUSHES_TO_BREAK = 10;
   private int brushCount;
   private long brushCountResetsAtTick;
   private long coolDownEndsAtTick;
   private ItemStack item = ItemStack.f_41583_;
   @Nullable
   private Direction hitDirection;
   @Nullable
   private ResourceLocation lootTable;
   private long lootTableSeed;

   public BrushableBlockEntity(BlockPos position, BlockState state) {
      super((BlockEntityType)ArchaeologyBlockEntityType.BRUSHABLE_BLOCK.get(), position, state);
   }

   public boolean brush(long gameTime, Player player, Direction hitDirection) {
      if (this.hitDirection == null) {
         this.hitDirection = hitDirection;
      }

      this.brushCountResetsAtTick = gameTime + 40L;
      if (gameTime >= this.coolDownEndsAtTick && this.f_58857_ instanceof ServerLevel) {
         this.coolDownEndsAtTick = gameTime + 10L;
         this.unpackLootTable(player);
         int oldState = this.getCompletionState();
         this.brushCount++;
         if (this.brushCount >= 10) {
            this.brushingCompleted(player);
            return true;
         } else {
            this.f_58857_.m_186460_(this.m_58899_(), this.m_58900_().m_60734_(), 40);
            int newState = this.getCompletionState();
            if (oldState != newState) {
               this.f_58857_.m_7731_(this.m_58899_(), (BlockState)this.m_58900_().m_61124_(BrushableBlock.DUSTED, newState), 3);
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public void unpackLootTable(Player player) {
      if (this.lootTable != null && player instanceof ServerPlayer serverPlayer) {
         LootTable loottable = serverPlayer.m_9236_().m_7654_().m_129898_().m_79217_(this.lootTable);
         CriteriaTriggers.f_10563_.m_54597_(serverPlayer, this.lootTable);
         LootContext context = new Builder(serverPlayer.m_9236_())
            .m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(this.f_58858_))
            .m_78963_(player.m_36336_())
            .m_78972_(LootContextParams.f_81455_, player)
            .m_78975_(LootContextParamSets.f_81411_);
         ObjectArrayList<ItemStack> items = loottable.m_230922_(context);

         this.item = switch (items.size()) {
            case 0 -> ItemStack.f_41583_;
            case 1 -> (ItemStack)items.get(0);
            default -> {
               LOGGER.warn("Expected max 1 loot from loot table " + this.lootTable + " got " + items.size());
               yield (ItemStack)items.get(0);
            }
         };
         this.lootTable = null;
         this.m_6596_();
      }
   }

   private void brushingCompleted(Player player) {
      if (this.f_58857_ != null && this.f_58857_.m_7654_() != null) {
         this.dropContent(player);
         BlockState blockstate = this.m_58900_();
         this.f_58857_.m_46796_(3008, this.m_58899_(), Block.m_49956_(blockstate));
         Block brushedBlock = blockstate.m_60734_() instanceof BrushableBlock brushable ? brushable.getTurnsInto() : Blocks.f_50016_;
         this.f_58857_.m_7731_(this.f_58858_, brushedBlock.m_49966_(), 3);
      }
   }

   private void dropContent(Player player) {
      if (this.f_58857_ != null && this.f_58857_.m_7654_() != null) {
         this.unpackLootTable(player);
         if (!this.item.m_41619_()) {
            double width = (double)EntityType.f_20461_.m_20678_();
            double offset = 1.0 - width + width / 2.0;
            Direction direction = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
            BlockPos blockpos = this.f_58858_.m_5484_(direction, 1);
            double x = (double)blockpos.m_123341_() + 0.5 * offset;
            double y = (double)blockpos.m_123342_() + 0.5 + (double)(EntityType.f_20461_.m_20679_() / 2.0F);
            double z = (double)blockpos.m_123343_() + 0.5 * offset;
            ItemEntity itemEntity = new ItemEntity(this.f_58857_, x, y, z, this.item.m_41620_(this.f_58857_.f_46441_.m_188503_(21) + 10));
            itemEntity.m_20256_(Vec3.f_82478_);
            this.f_58857_.m_7967_(itemEntity);
            this.item = ItemStack.f_41583_;
         }
      }
   }

   public void checkReset() {
      if (this.f_58857_ != null) {
         if (this.brushCount != 0 && this.f_58857_.m_46467_() >= this.brushCountResetsAtTick) {
            int oldState = this.getCompletionState();
            this.brushCount = Math.max(0, this.brushCount - 2);
            int newState = this.getCompletionState();
            if (oldState != newState) {
               this.f_58857_.m_7731_(this.m_58899_(), (BlockState)this.m_58900_().m_61124_(BrushableBlock.DUSTED, newState), 3);
            }

            this.brushCountResetsAtTick = this.f_58857_.m_46467_() + 4L;
         }

         if (this.brushCount == 0) {
            this.hitDirection = null;
            this.brushCountResetsAtTick = 0L;
            this.coolDownEndsAtTick = 0L;
         } else {
            this.f_58857_.m_186460_(this.m_58899_(), this.m_58900_().m_60734_(), (int)(this.brushCountResetsAtTick - this.f_58857_.m_46467_()));
         }
      }
   }

   private boolean tryLoadLootTable(CompoundTag tag) {
      if (tag.m_128425_("LootTable", 8)) {
         this.lootTable = new ResourceLocation(tag.m_128461_("LootTable"));
         this.lootTableSeed = tag.m_128454_("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   private boolean trySaveLootTable(CompoundTag tag) {
      if (this.lootTable == null) {
         return false;
      } else {
         tag.m_128359_("LootTable", this.lootTable.toString());
         if (this.lootTableSeed != 0L) {
            tag.m_128356_("LootTableSeed", this.lootTableSeed);
         }

         return true;
      }
   }

   @NotNull
   public CompoundTag m_5995_() {
      CompoundTag compoundtag = super.m_5995_();
      if (this.hitDirection != null) {
         compoundtag.m_128405_("hit_direction", this.hitDirection.ordinal());
      }

      compoundtag.m_128365_("item", this.item.m_41739_(new CompoundTag()));
      return compoundtag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void m_142466_(@NotNull CompoundTag tag) {
      if (!this.tryLoadLootTable(tag) && tag.m_128441_("item")) {
         this.item = ItemStack.m_41712_(tag.m_128469_("item"));
      }

      if (tag.m_128441_("hit_direction")) {
         this.hitDirection = Direction.values()[tag.m_128451_("hit_direction")];
      }
   }

   protected void m_183515_(@NotNull CompoundTag tag) {
      if (!this.trySaveLootTable(tag)) {
         tag.m_128365_("item", this.item.m_41739_(new CompoundTag()));
      }
   }

   public void setLootTable(ResourceLocation lootTable, long lootTableSeed) {
      this.lootTable = lootTable;
      this.lootTableSeed = lootTableSeed;
   }

   private int getCompletionState() {
      if (this.brushCount == 0) {
         return 0;
      } else if (this.brushCount < 3) {
         return 1;
      } else {
         return this.brushCount < 6 ? 2 : 3;
      }
   }

   @Nullable
   public Direction getHitDirection() {
      return this.hitDirection;
   }

   public ItemStack getItem() {
      return this.item;
   }
}
