package com.majruszsaccessories.events.base;

import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Events;
import com.majruszsaccessories.events.OnAccessoryDropChanceGet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;

public class CustomConditions {
   public static <DataType> Condition<DataType> dropChance(Function<DataType, Float> chance, Function<DataType, Entity> entity) {
      return Condition.predicate(
         data -> ((OnAccessoryDropChanceGet)Events.dispatch(new OnAccessoryDropChanceGet(chance.apply((DataType)data), entity.apply((DataType)data)))).check()
      );
   }

   public static <DataType> Condition<DataType> dropChance(Supplier<Float> chance, Function<DataType, Entity> entity) {
      return dropChance(data -> chance.get(), entity);
   }
}
