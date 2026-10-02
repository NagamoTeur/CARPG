package com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data;

import java.util.Collection;

public interface IHistoricalData<T> {
   void set(T var1);

   T get(int var1);

   Collection<T> getAll();
}
