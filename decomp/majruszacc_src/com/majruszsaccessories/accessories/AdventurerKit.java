package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.MoreChestLoot;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;
import net.minecraft.world.entity.Entity;

@AutoInstance
public class AdventurerKit extends AccessoryHandler {
   public AdventurerKit() {
      super(MajruszsAccessories.ADVENTURER_KIT, AdventurerKit.class);
      this.add(MoreChestLoot.create(1.2F))
         .add(AdventurerKit.AnyChestDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.ADVENTURER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class AnyChestDropChance extends BonusComponent<AccessoryItem> {
      float chance = 0.025F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return AdventurerKit.AnyChestDropChance::new;
      }

      protected AnyChestDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         MoreChestLoot.OnChestOpened.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(CustomConditions.dropChance(() -> this.chance, data -> (Entity)MoreChestLoot.OnChestOpened.findPlayer(data).orElse(null)));
         handler.getConfig().define("any_chest_spawn_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }
}
