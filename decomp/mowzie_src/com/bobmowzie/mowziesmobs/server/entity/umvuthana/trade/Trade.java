package com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class Trade {
   private final ItemStack input;
   private final ItemStack output;
   private final int weight;

   public Trade(ItemStack input, ItemStack output, int weight) {
      this.input = input.m_41777_();
      this.output = output.m_41777_();
      this.weight = weight;
   }

   public Trade(Trade trade) {
      this(trade.input, trade.output, trade.weight);
   }

   public ItemStack getInput() {
      return this.input.m_41777_();
   }

   public ItemStack getOutput() {
      return this.output.m_41777_();
   }

   public int getWeight() {
      return this.weight;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof Trade trade)
            ? false
            : this.weight == trade.weight && ItemStack.m_41746_(this.input, trade.input) && ItemStack.m_41746_(this.output, trade.output);
      }
   }

   @Override
   public int hashCode() {
      return 961 * this.input.hashCode() + 31 * this.output.hashCode() + this.weight;
   }

   public CompoundTag serialize() {
      CompoundTag compound = new CompoundTag();
      compound.m_128365_("input", this.input.m_41739_(new CompoundTag()));
      compound.m_128365_("output", this.output.m_41739_(new CompoundTag()));
      compound.m_128405_("weight", this.weight);
      return compound;
   }

   public static Trade deserialize(CompoundTag compound) {
      ItemStack input = ItemStack.m_41712_(compound.m_128469_("input"));
      ItemStack output = ItemStack.m_41712_(compound.m_128469_("output"));
      int weight = compound.m_128451_("weight");
      return !input.m_41619_() && !output.m_41619_() && weight >= 1 ? new Trade(input, output, weight) : null;
   }
}
