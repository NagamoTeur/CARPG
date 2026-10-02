package com.min01.archaeology.blockentity;

import com.min01.archaeology.container.ContainerSingleItem;
import com.min01.archaeology.container.RandomizableContainer;
import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyItems;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class DecoratedPotBlockEntity extends BlockEntity implements RandomizableContainer, ContainerSingleItem.BlockContainerSingleItem {
   public static final String TAG_SHERDS = "sherds";
   public static final String TAG_ITEM = "item";
   public static final int EVENT_POT_WOBBLES = 1;
   public long wobbleStartedAtTick;
   @Nullable
   public DecoratedPotBlockEntity.WobbleStyle lastWobbleStyle;
   private DecoratedPotBlockEntity.Decorations decorations;
   private ItemStack stack = ItemStack.f_41583_;
   @Nullable
   protected ResourceLocation lootTable;
   protected long lootTableSeed;

   public DecoratedPotBlockEntity(BlockPos position, BlockState state) {
      super((BlockEntityType)ArchaeologyBlockEntityType.DECORATED_POT.get(), position, state);
      this.decorations = DecoratedPotBlockEntity.Decorations.EMPTY;
   }

   protected void m_183515_(@NotNull CompoundTag tag) {
      super.m_183515_(tag);
      this.decorations.save(tag);
      if (!this.trySaveLootTable(tag) && !this.stack.m_41619_()) {
         tag.m_128365_("item", this.stack.m_41739_(new CompoundTag()));
      }
   }

   public void m_142466_(@NotNull CompoundTag tag) {
      super.m_142466_(tag);
      this.decorations = DecoratedPotBlockEntity.Decorations.load(tag);
      if (!this.tryLoadLootTable(tag)) {
         if (tag.m_128425_("item", 10)) {
            this.stack = ItemStack.m_41712_(tag.m_128469_("item"));
         } else {
            this.stack = ItemStack.f_41583_;
         }
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   @NotNull
   public CompoundTag m_5995_() {
      return this.m_187482_();
   }

   public Direction getDirection() {
      return (Direction)this.m_58900_().m_61143_(BlockStateProperties.f_61374_);
   }

   public DecoratedPotBlockEntity.Decorations getDecorations() {
      return this.decorations;
   }

   public void setFromItem(ItemStack stack) {
      this.decorations = DecoratedPotBlockEntity.Decorations.load(BlockItem.m_186336_(stack));
   }

   public ItemStack getPotAsItem() {
      return createDecoratedPotItem(this.decorations);
   }

   public static ItemStack createDecoratedPotItem(DecoratedPotBlockEntity.Decorations decorations) {
      ItemStack stack = ((Item)ArchaeologyItems.DECORATED_POT.get()).m_7968_();
      CompoundTag tag = decorations.save(new CompoundTag());
      BlockItem.m_186338_(stack, (BlockEntityType)ArchaeologyBlockEntityType.DECORATED_POT.get(), tag);
      return stack;
   }

   @Nullable
   @Override
   public ResourceLocation getLootTable() {
      return this.lootTable;
   }

   @Override
   public void setLootTable(@Nullable ResourceLocation lootTable) {
      this.lootTable = lootTable;
   }

   @Override
   public long getLootTableSeed() {
      return this.lootTableSeed;
   }

   @Override
   public void setLootTableSeed(long lootTableSeed) {
      this.lootTableSeed = lootTableSeed;
   }

   @Override
   public ItemStack getTheItem() {
      this.unpackLootTable(null);
      return this.stack;
   }

   @Override
   public ItemStack splitTheItem(int amount) {
      this.unpackLootTable(null);
      ItemStack stack = this.stack.m_41620_(amount);
      if (this.stack.m_41619_()) {
         this.stack = ItemStack.f_41583_;
      }

      return stack;
   }

   @Override
   public void setTheItem(ItemStack stack) {
      this.unpackLootTable(null);
      this.stack = stack;
   }

   @Override
   public BlockEntity getContainerBlockEntity() {
      return this;
   }

   public void wobble(DecoratedPotBlockEntity.WobbleStyle wobbleStyle) {
      if (this.f_58857_ != null && !this.f_58857_.m_5776_()) {
         this.f_58857_.m_7696_(this.m_58899_(), this.m_58900_().m_60734_(), 1, wobbleStyle.ordinal());
      }
   }

   public boolean m_7531_(int event, int duration) {
      if (this.f_58857_ != null && event == 1 && duration >= 0 && duration < DecoratedPotBlockEntity.WobbleStyle.values().length) {
         this.wobbleStartedAtTick = this.f_58857_.m_46467_();
         this.lastWobbleStyle = DecoratedPotBlockEntity.WobbleStyle.values()[duration];
         return true;
      } else {
         return super.m_7531_(event, duration);
      }
   }

   public static record Decorations(Item back, Item left, Item right, Item front) {
      public static final DecoratedPotBlockEntity.Decorations EMPTY = new DecoratedPotBlockEntity.Decorations(
         Items.f_42460_, Items.f_42460_, Items.f_42460_, Items.f_42460_
      );

      public CompoundTag save(CompoundTag tag) {
         ListTag listTag = new ListTag();
         this.sorted().forEach(item -> listTag.add(StringTag.m_129297_(ForgeRegistries.ITEMS.getKey(item).toString())));
         tag.m_128365_("sherds", listTag);
         return tag;
      }

      public Stream<Item> sorted() {
         return Stream.of(this.back, this.left, this.right, this.front);
      }

      public static DecoratedPotBlockEntity.Decorations load(@Nullable CompoundTag tag) {
         if (tag != null && tag.m_128425_("sherds", 9)) {
            ListTag listTag = tag.m_128437_("sherds", 8);
            return new DecoratedPotBlockEntity.Decorations(itemFromTag(listTag, 0), itemFromTag(listTag, 1), itemFromTag(listTag, 2), itemFromTag(listTag, 3));
         } else {
            return EMPTY;
         }
      }

      private static Item itemFromTag(ListTag listTag, int index) {
         return index >= listTag.size() ? Items.f_42460_ : (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(listTag.get(index).m_7916_()));
      }
   }

   public static enum WobbleStyle {
      POSITIVE(7),
      NEGATIVE(10);

      public final int duration;

      private WobbleStyle(int duration) {
         this.duration = duration;
      }
   }
}
