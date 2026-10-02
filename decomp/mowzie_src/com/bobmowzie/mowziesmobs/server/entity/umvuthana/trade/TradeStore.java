package com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class TradeStore {
   public static final TradeStore EMPTY = new TradeStore(ImmutableSet.of(), 0);
   private final ImmutableSet<Trade> trades;
   private final int totalWeight;

   private TradeStore(ImmutableSet<Trade> trades, int totalWeight) {
      this.trades = trades;
      this.totalWeight = totalWeight;
   }

   public boolean hasStock() {
      return this.trades.size() > 0;
   }

   public Trade get(RandomSource rng) {
      if (this.totalWeight <= 0) {
         return null;
      } else {
         int w = rng.m_188503_(this.totalWeight);
         UnmodifiableIterator var3 = this.trades.iterator();

         while (var3.hasNext()) {
            Trade t = (Trade)var3.next();
            w -= t.getWeight();
            if (w < 0) {
               return t;
            }
         }

         return null;
      }
   }

   public CompoundTag serialize() {
      CompoundTag compound = new CompoundTag();
      ListTag tradesList = new ListTag();
      UnmodifiableIterator var3 = this.trades.iterator();

      while (var3.hasNext()) {
         Trade trade = (Trade)var3.next();
         tradesList.add(trade.serialize());
      }

      compound.m_128365_("trades", tradesList);
      return compound;
   }

   public static TradeStore deserialize(CompoundTag compound) {
      ListTag tradesList = compound.m_128437_("trades", 10);
      int totalWeight = 0;
      com.google.common.collect.ImmutableSet.Builder<Trade> trades = new com.google.common.collect.ImmutableSet.Builder();

      for (int i = 0; i < tradesList.size(); i++) {
         Trade trade = Trade.deserialize(tradesList.m_128728_(i));
         if (trade != null) {
            trades.add(trade);
            totalWeight += trade.getWeight();
         }
      }

      return new TradeStore(trades.build(), totalWeight);
   }

   public static final class Builder {
      private final com.google.common.collect.ImmutableSet.Builder<Trade> trades = new com.google.common.collect.ImmutableSet.Builder();
      private int totalWeight;

      public TradeStore.Builder addTrade(Item input, int inputCount, Item output, int outputCount, int weight) {
         return this.addTrade(input, inputCount, null, output, outputCount, null, weight);
      }

      public TradeStore.Builder addTrade(Item input, int inputCount, CompoundTag inputMeta, Item output, int outputCount, CompoundTag outputMeta, int weight) {
         return this.addTrade(new ItemStack(input, inputCount, inputMeta), new ItemStack(output, outputCount, outputMeta), weight);
      }

      public TradeStore.Builder addTrade(ItemStack input, ItemStack output, int weight) {
         this.trades.add(new Trade(input, output, weight));
         this.totalWeight += weight;
         return this;
      }

      public TradeStore build() {
         return new TradeStore(this.trades.build(), this.totalWeight);
      }
   }
}
