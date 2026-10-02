package com.hollingsworth.arsnouveau.api.perk;

@FunctionalInterface
public interface IPerkProvider<T> {
   IPerkHolder<T> getPerkHolder(T var1);
}
