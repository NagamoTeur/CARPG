package com.majruszsaccessories.events;

import com.majruszlibrary.events.base.Event;
import com.majruszlibrary.events.base.Events;
import com.majruszsaccessories.items.BoosterItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;

public class OnBoosterTooltip {
   public final List<Component> components = new ArrayList<>();
   public final BoosterItem booster;

   public static Event<OnBoosterTooltip> listen(Consumer<OnBoosterTooltip> consumer) {
      return Events.get(OnBoosterTooltip.class).add(consumer);
   }

   public OnBoosterTooltip(BoosterItem booster) {
      this.booster = booster;
   }
}
