package com.aizistral.enigmaticlegacy.triggers;

import com.aizistral.enigmaticlegacy.api.items.IEldritch;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;

public class CursedInventoryChangedTrigger extends SimpleCriterionTrigger<CursedInventoryChangedTrigger.TriggerInstance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "cursed_inventory_changed");
   public static final CursedInventoryChangedTrigger INSTANCE = new CursedInventoryChangedTrigger();

   public ResourceLocation m_7295_() {
      return ID;
   }

   public CursedInventoryChangedTrigger.TriggerInstance createInstance(JsonObject pJson, Composite pEntityPredicate, DeserializationContext pConditionsParser) {
      JsonObject jsonobject = GsonHelper.m_13841_(pJson, "slots", new JsonObject());
      Ints minmaxbounds$ints = Ints.m_55373_(jsonobject.get("occupied"));
      Ints minmaxbounds$ints1 = Ints.m_55373_(jsonobject.get("full"));
      Ints minmaxbounds$ints2 = Ints.m_55373_(jsonobject.get("empty"));
      ItemPredicate[] aitempredicate = ItemPredicate.m_45055_(pJson.get("items"));
      return new CursedInventoryChangedTrigger.TriggerInstance(pEntityPredicate, minmaxbounds$ints, minmaxbounds$ints1, minmaxbounds$ints2, aitempredicate);
   }

   public void trigger(ServerPlayer pPlayer, Inventory pInventory, ItemStack pStack) {
      int i = 0;
      int j = 0;
      int k = 0;

      for (int l = 0; l < pInventory.m_6643_(); l++) {
         ItemStack itemstack = pInventory.m_8020_(l);
         if (itemstack.m_41619_()) {
            j++;
         } else {
            k++;
            if (itemstack.m_41613_() >= itemstack.m_41741_()) {
               i++;
            }
         }
      }

      this.trigger(pPlayer, pInventory, pStack, i, j, k);
   }

   private void trigger(ServerPlayer pPlayer, Inventory pInventory, ItemStack pStack, int pFull, int pEmpty, int pOccupied) {
      this.m_66234_(pPlayer, p_43166_ -> p_43166_.matches(pInventory, pStack, pFull, pEmpty, pOccupied));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Ints slotsOccupied;
      private final Ints slotsFull;
      private final Ints slotsEmpty;
      private final ItemPredicate[] predicates;

      public TriggerInstance(Composite pPlayer, Ints pSlotsOccupied, Ints pSlotsFull, Ints pSlotsEmpty, ItemPredicate[] pPredicates) {
         super(CursedInventoryChangedTrigger.ID, pPlayer);
         this.slotsOccupied = pSlotsOccupied;
         this.slotsFull = pSlotsFull;
         this.slotsEmpty = pSlotsEmpty;
         this.predicates = pPredicates;
      }

      public static CursedInventoryChangedTrigger.TriggerInstance hasItems(ItemPredicate... pItems) {
         return new CursedInventoryChangedTrigger.TriggerInstance(Composite.f_36667_, Ints.f_55364_, Ints.f_55364_, Ints.f_55364_, pItems);
      }

      public static CursedInventoryChangedTrigger.TriggerInstance hasItems(ItemLike... pItems) {
         ItemPredicate[] aitempredicate = new ItemPredicate[pItems.length];

         for (int i = 0; i < pItems.length; i++) {
            aitempredicate[i] = new ItemPredicate(
               (TagKey)null,
               ImmutableSet.of(pItems[i].m_5456_()),
               Ints.f_55364_,
               Ints.f_55364_,
               EnchantmentPredicate.f_30465_,
               EnchantmentPredicate.f_30465_,
               (Potion)null,
               NbtPredicate.f_57471_
            );
         }

         return hasItems(aitempredicate);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         if (!this.slotsOccupied.m_55327_() || !this.slotsFull.m_55327_() || !this.slotsEmpty.m_55327_()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("occupied", this.slotsOccupied.m_55328_());
            jsonobject1.add("full", this.slotsFull.m_55328_());
            jsonobject1.add("empty", this.slotsEmpty.m_55328_());
            jsonobject.add("slots", jsonobject1);
         }

         if (this.predicates.length > 0) {
            JsonArray jsonarray = new JsonArray();

            for (ItemPredicate itempredicate : this.predicates) {
               jsonarray.add(itempredicate.m_45048_());
            }

            jsonobject.add("items", jsonarray);
         }

         return jsonobject;
      }

      public boolean matches(Inventory pInventory, ItemStack pStack, int pFull, int pEmpty, int pOccupied) {
         if (!this.slotsFull.m_55390_(pFull)) {
            return false;
         } else if (!this.slotsEmpty.m_55390_(pEmpty)) {
            return false;
         } else if (!this.slotsOccupied.m_55390_(pOccupied)) {
            return false;
         } else {
            int i = this.predicates.length;
            if (i == 0) {
               return true;
            } else if (i != 1) {
               List<ItemPredicate> list = new ObjectArrayList(this.predicates);
               int j = pInventory.m_6643_();

               for (int k = 0; k < j; k++) {
                  if (list.isEmpty()) {
                     return true;
                  }

                  ItemStack itemstack = pInventory.m_8020_(k);
                  if (!itemstack.m_41619_()) {
                     list.removeIf(
                        predicate -> !(itemstack.m_41720_() instanceof IEldritch)
                              ? predicate.m_45049_(itemstack)
                              : predicate.m_45049_(itemstack) && SuperpositionHandler.isTheWorthyOne(pInventory.f_35978_)
                     );
                  }
               }

               return list.isEmpty();
            } else if (!pStack.m_41619_()) {
               return !(pStack.m_41720_() instanceof IEldritch)
                  ? this.predicates[0].m_45049_(pStack)
                  : this.predicates[0].m_45049_(pStack) && SuperpositionHandler.isTheWorthyOne(pInventory.f_35978_);
            } else {
               return false;
            }
         }
      }
   }
}
