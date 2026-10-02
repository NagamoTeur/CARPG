package com.majruszsaccessories.recipes;

import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.items.BoosterItem;
import com.majruszsaccessories.items.CardItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public record RecipeData(List<AccessoryHolder> accessories, List<BoosterItem> boosters, List<CardItem> cards) {
   public RecipeData() {
      this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
   }

   public static RecipeData build(CraftingContainer container) {
      RecipeData data = new RecipeData();

      for (int i = 0; i < container.m_6643_(); i++) {
         ItemStack itemStack = container.m_8020_(i);
         if (!itemStack.m_41619_()) {
            if (itemStack.m_41720_() instanceof AccessoryItem) {
               data.accessories.add(AccessoryHolder.getOrCreate(itemStack));
            } else if (itemStack.m_41720_() instanceof BoosterItem booster) {
               data.boosters.add(booster);
            } else {
               if (!(itemStack.m_41720_() instanceof CardItem card)) {
                  return new RecipeData();
               }

               data.cards.add(card);
            }
         }
      }

      data.accessories.sort((left, right) -> Float.compare(left.getBaseBonus(), right.getBaseBonus()));
      return data;
   }

   public AccessoryHolder getAccessory(int idx) {
      return this.accessories.get(idx);
   }

   public BoosterItem getBooster(int idx) {
      return this.boosters.get(idx);
   }

   public CardItem getCard(int idx) {
      return this.cards.get(idx);
   }

   float getStandardDeviation() {
      float average = this.getAverageBonus();
      double variation = (double)(
         this.accessories.stream().map(AccessoryHolder::getBaseBonus).reduce(0.0F, (sum, bonus) -> sum + (float)Math.pow((double)(bonus - average), 2.0))
            / (float)this.accessories.size()
      );
      return (float)Math.sqrt(variation);
   }

   float getMaxBonus() {
      return this.accessories.get(this.accessories.size() - 1).getBaseBonus();
   }

   float getMinBonus() {
      return this.accessories.get(0).getBaseBonus();
   }

   float getAverageBonus() {
      return this.accessories.stream().map(AccessoryHolder::getBaseBonus).reduce(0.0F, Float::sum) / (float)this.accessories.size();
   }

   int getAccessoriesSize() {
      return this.accessories.size();
   }

   int getBoostersSize() {
      return this.boosters.size();
   }

   int getCardsSize() {
      return this.cards.size();
   }

   boolean hasAccessory(AccessoryItem item) {
      return this.accessories.stream().anyMatch(holder -> holder.getItem().equals(item));
   }

   boolean hasIdenticalItemTypes() {
      return this.accessories.stream().allMatch(holder -> holder.getItem().equals(this.accessories.get(0).getItem()));
   }
}
