package com.hollingsworth.arsnouveau.common.util;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class RegistryWrapper<T> implements Supplier<T>, ItemLike {
   public RegistryObject<T> registryObject;

   public RegistryWrapper(RegistryObject<T> registryObject) {
      this.registryObject = registryObject;
   }

   @NotNull
   @Override
   public T get() {
      return (T)this.registryObject.get();
   }

   public Item m_5456_() {
      if (this.registryObject.get() instanceof ItemLike itemLike) {
         return itemLike.m_5456_();
      } else {
         throw new IllegalStateException("RegistryWrapper is not an Item");
      }
   }

   public String getRegistryName() {
      return this.registryObject.getId().m_135815_();
   }
}
