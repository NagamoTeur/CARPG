package dev.latvian.mods.kubejs.integration.rei;

import com.google.common.collect.Lists;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.util.ListJS;
import java.util.Collection;
import java.util.List;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class AddREIEventJS extends EventJS {
   private final EntryRegistry registry;
   private final EntryWrapper entryWrapper;
   private final List<EntryStack<?>> added = Lists.newArrayList();

   public AddREIEventJS(EntryRegistry registry, EntryWrapper entryWrapper) {
      this.registry = registry;
      this.entryWrapper = entryWrapper;
   }

   public void add(Object o) {
      for (Object o1 : ListJS.orSelf(o)) {
         Collection<EntryStack<?>> stacks = this.entryWrapper.wrap(o1);
         if (stacks != null && !stacks.isEmpty()) {
            for (EntryStack<?> stack : stacks) {
               if (stack != null && !stack.isEmpty()) {
                  this.added.add(stack);
               }
            }
         }
      }
   }

   @Override
   protected void afterPosted(EventResult result) {
      if (!this.added.isEmpty()) {
         this.registry.addEntries(this.added);
      }
   }
}
