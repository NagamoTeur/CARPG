package dev.latvian.mods.kubejs.integration.rei;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.EntryType;

public class HideREIEventJS<T> extends EventJS {
   private final EntryRegistry registry;
   private final BasicFilteringRule<?> rule;
   private final EntryType<T> type;
   private final EntryWrapper entryWrapper;
   private final Collection<EntryStack<T>> hidden = new HashSet<>();
   private final Collection<EntryStack<T>> hiddenNoFilter = new HashSet<>();
   private boolean hideAll = false;

   public HideREIEventJS(EntryRegistry registry, BasicFilteringRule<?> rule, EntryType<T> type, EntryWrapper entryWrapper) {
      this.registry = registry;
      this.rule = rule;
      this.type = type;
      this.entryWrapper = entryWrapper;
   }

   private Stream<EntryStack<T>> allEntries() {
      return UtilsJS.cast(this.registry.getEntryStacks().filter(this::filterType));
   }

   public Collection<T> getAllIngredients() {
      return this.allEntries().<T>map(EntryStack::getValue).toList();
   }

   private boolean filterType(EntryStack<?> stack) {
      return stack.getType().equals(this.type);
   }

   public void hide(Object o) {
      if (!this.hideAll) {
         for (EntryStack<?> stack : this.entryWrapper.wrap(o)) {
            this.hidden.add(stack.cast());
         }
      }
   }

   public void hideNoFilter(Object o) {
      if (!this.hideAll) {
         for (EntryStack<?> stack : this.entryWrapper.wrap(o)) {
            this.hiddenNoFilter.add(stack.cast());
         }
      }
   }

   public void hideAll() {
      this.hideAll = true;
   }

   @Override
   protected void afterPosted(EventResult result) {
      if (this.hideAll) {
         this.rule.hide(this.allEntries().toList());
      }

      if (!this.hidden.isEmpty()) {
         this.rule.hide(this.hidden);
      }

      this.hiddenNoFilter.forEach(this.registry::removeEntry);
   }
}
