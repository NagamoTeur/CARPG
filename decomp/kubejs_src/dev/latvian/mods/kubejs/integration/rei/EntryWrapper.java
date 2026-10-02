package dev.latvian.mods.kubejs.integration.rei;

import java.util.Collection;
import me.shedaniel.rei.api.common.entry.EntryStack;

public interface EntryWrapper {
   Collection<EntryStack<?>> wrap(Object var1);
}
