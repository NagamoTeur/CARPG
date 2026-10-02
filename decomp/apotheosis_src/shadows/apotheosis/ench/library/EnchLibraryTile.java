package shadows.apotheosis.ench.library;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.placebo.recipe.VanillaPacketDispatcher;

public abstract class EnchLibraryTile extends BlockEntity {
   protected final Object2IntMap<Enchantment> points = new Object2IntOpenHashMap();
   protected final Object2IntMap<Enchantment> maxLevels = new Object2IntOpenHashMap();
   protected final Set<EnchLibraryContainer> activeContainers = new HashSet<>();
   protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new EnchLibraryTile.EnchLibItemHandler());
   protected final int maxLevel;
   protected final int maxPoints;

   public EnchLibraryTile(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxLevel) {
      super(type, pos, state);
      this.maxLevel = maxLevel;
      this.maxPoints = levelToPoints(maxLevel);
   }

   public void depositBook(ItemStack book) {
      if (book.m_41720_() == Items.f_42690_) {
         Map<Enchantment, Integer> enchs = EnchantmentHelper.m_44831_(book);

         for (Entry<Enchantment, Integer> e : enchs.entrySet()) {
            if (e.getKey() != null && e.getValue() != null) {
               int newPoints = Math.min(this.maxPoints, this.points.getInt(e.getKey()) + levelToPoints(e.getValue()));
               if (newPoints < 0) {
                  newPoints = this.maxPoints;
               }

               this.points.put(e.getKey(), newPoints);
               this.maxLevels.put(e.getKey(), Math.min(this.maxLevel, Math.max(this.maxLevels.getInt(e.getKey()), e.getValue())));
            }
         }

         if (enchs.size() > 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
         }

         this.m_6596_();
      }
   }

   public void extractEnchant(ItemStack stack, Enchantment ench, int level) {
      int curLvl = EnchantmentHelper.m_44831_(stack).getOrDefault(ench, 0);
      if (!stack.m_41619_() && this.canExtract(ench, level, curLvl) && level != curLvl) {
         Map<Enchantment, Integer> enchs = EnchantmentHelper.m_44831_(stack);
         enchs.put(ench, level);
         EnchantmentHelper.m_44865_(enchs, stack);
         this.points.put(ench, Math.max(0, this.points.getInt(ench) - levelToPoints(level) + levelToPoints(curLvl)));
         if (!this.f_58857_.m_5776_()) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
         }

         this.m_6596_();
      }
   }

   public boolean canExtract(Enchantment ench, int level, int currentLevel) {
      return this.maxLevels.getInt(ench) >= level && this.points.getInt(ench) >= levelToPoints(level) - levelToPoints(currentLevel);
   }

   public static int levelToPoints(int level) {
      return (int)Math.pow(2.0, (double)(level - 1));
   }

   public void m_183515_(CompoundTag tag) {
      CompoundTag points = new CompoundTag();
      ObjectIterator levels = this.points.object2IntEntrySet().iterator();

      while (levels.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment> e = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment>)levels.next();
         points.m_128405_(ForgeRegistries.ENCHANTMENTS.getKey((Enchantment)e.getKey()).toString(), e.getIntValue());
      }

      tag.m_128365_("Points", points);
      CompoundTag levelsx = new CompoundTag();
      ObjectIterator var7 = this.maxLevels.object2IntEntrySet().iterator();

      while (var7.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment> e = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment>)var7.next();
         levelsx.m_128405_(ForgeRegistries.ENCHANTMENTS.getKey((Enchantment)e.getKey()).toString(), e.getIntValue());
      }

      tag.m_128365_("Levels", levelsx);
      super.m_183515_(tag);
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      CompoundTag points = tag.m_128469_("Points");

      for (String s : points.m_128431_()) {
         Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(s));
         if (ench != null) {
            this.points.put(ench, points.m_128451_(s));
         }
      }

      CompoundTag levels = tag.m_128469_("Levels");

      for (String sx : levels.m_128431_()) {
         Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(sx));
         if (ench != null) {
            this.maxLevels.put(ench, levels.m_128451_(sx));
         }
      }
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      CompoundTag tag = pkt.m_131708_();
      CompoundTag points = tag.m_128469_("Points");

      for (String s : points.m_128431_()) {
         Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(s));
         if (ench != null) {
            this.points.put(ench, points.m_128451_(s));
         }
      }

      CompoundTag levels = tag.m_128469_("Levels");

      for (String sx : levels.m_128431_()) {
         Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(sx));
         if (ench != null) {
            this.maxLevels.put(ench, levels.m_128451_(sx));
         }
      }

      this.activeContainers.forEach(EnchLibraryContainer::onChanged);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public CompoundTag m_5995_() {
      CompoundTag tag = super.m_5995_();
      CompoundTag points = new CompoundTag();
      ObjectIterator levels = this.points.object2IntEntrySet().iterator();

      while (levels.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment> e = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment>)levels.next();
         points.m_128405_(ForgeRegistries.ENCHANTMENTS.getKey((Enchantment)e.getKey()).toString(), e.getIntValue());
      }

      tag.m_128365_("Points", points);
      CompoundTag levelsx = new CompoundTag();
      ObjectIterator var7 = this.maxLevels.object2IntEntrySet().iterator();

      while (var7.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment> e = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Enchantment>)var7.next();
         levelsx.m_128405_(ForgeRegistries.ENCHANTMENTS.getKey((Enchantment)e.getKey()).toString(), e.getIntValue());
      }

      tag.m_128365_("Levels", levelsx);
      return tag;
   }

   public Object2IntMap<Enchantment> getPointsMap() {
      return this.points;
   }

   public Object2IntMap<Enchantment> getLevelsMap() {
      return this.maxLevels;
   }

   public void addListener(EnchLibraryContainer ctr) {
      this.activeContainers.add(ctr);
   }

   public void removeListener(EnchLibraryContainer ctr) {
      this.activeContainers.remove(ctr);
   }

   public int getMax(Enchantment ench) {
      return Math.min(this.maxLevel, this.maxLevels.getInt(ench));
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.itemHandler.cast() : super.getCapability(cap, side);
   }

   public static class BasicLibraryTile extends EnchLibraryTile {
      public BasicLibraryTile(BlockPos pos, BlockState state) {
         super((BlockEntityType<?>)Apoth.Tiles.LIBRARY.get(), pos, state, 16);
      }
   }

   private class EnchLibItemHandler implements IItemHandler {
      public int getSlots() {
         return 1;
      }

      public ItemStack getStackInSlot(int slot) {
         return ItemStack.f_41583_;
      }

      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         if (stack.m_41720_() == Items.f_42690_ && stack.m_41613_() <= 1) {
            if (!simulate) {
               EnchLibraryTile.this.depositBook(stack);
            }

            return ItemStack.f_41583_;
         } else {
            return stack;
         }
      }

      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         return ItemStack.f_41583_;
      }

      public int getSlotLimit(int slot) {
         return 1;
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return slot == 0 && stack.m_41720_() == Items.f_42690_;
      }
   }

   public static class EnderLibraryTile extends EnchLibraryTile {
      public EnderLibraryTile(BlockPos pos, BlockState state) {
         super((BlockEntityType<?>)Apoth.Tiles.ENDER_LIBRARY.get(), pos, state, 31);
      }
   }
}
