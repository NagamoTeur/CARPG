package com.majruszsaccessories.common.components;

import com.majruszlibrary.events.OnWanderingTradesUpdated;
import com.majruszlibrary.math.Random;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class TradeOffer<Type extends Item> extends BonusComponent<Type> {
   private static final List<TradeOffer<?>> OFFERS = new ArrayList<>();

   public static <Type extends Item> BonusComponent.ISupplier<Type> create() {
      return TradeOffer::new;
   }

   protected TradeOffer(BonusHandler<Type> handler) {
      super(handler);
      OFFERS.add(this);
   }

   public MerchantOffer toMerchantOffer() {
      return new MerchantOffer(new ItemStack(this.getItem(), 1), this.getItemStack(), 2, 40, 0.05F) {
         public boolean m_45355_(ItemStack itemStack1, ItemStack itemStack2) {
            return itemStack1.m_150930_(TradeOffer.this.getItem()) && itemStack2.m_41619_();
         }
      };
   }

   private ItemStack getItemStack() {
      return (ItemStack)Random.next(
         List.of(
            new ItemStack((ItemLike)MajruszsAccessories.GAMBLING_CARD.get(), 1),
            new ItemStack((ItemLike)MajruszsAccessories.GAMBLING_CARD.get(), 1),
            new ItemStack((ItemLike)MajruszsAccessories.REMOVAL_CARD.get(), 1),
            new ItemStack((ItemLike)MajruszsAccessories.REVERSE_CARD.get(), 1),
            new ItemStack(Items.f_42616_, 7)
         )
      );
   }

   private static void addTrades(OnWanderingTradesUpdated data) {
      data.offers.addAll(Random.next(OFFERS.stream().map(TradeOffer::toMerchantOffer).toList(), 5));
   }

   static {
      OnWanderingTradesUpdated.listen(TradeOffer::addTrades);
   }
}
