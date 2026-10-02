package com.hollingsworth.arsnouveau.api.client;

public interface IVariantColorProvider<T> extends IVariantTextureProvider<T> {
   void setColor(String var1, T var2);

   default String getColor(T object) {
      return "";
   }
}
