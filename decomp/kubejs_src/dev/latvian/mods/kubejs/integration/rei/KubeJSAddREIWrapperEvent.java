package dev.latvian.mods.kubejs.integration.rei;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import java.util.function.Consumer;
import me.shedaniel.rei.api.common.entry.type.EntryType;

@FunctionalInterface
public interface KubeJSAddREIWrapperEvent {
   Event<Consumer<KubeJSAddREIWrapperEvent>> EVENT = EventFactory.createConsumerLoop(new KubeJSAddREIWrapperEvent[0]);

   void addWrappers(EntryType<?> var1, EntryWrapper var2);
}
