package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.util.StringRepresentable;

public enum ThreePartBlock implements StringRepresentable {
   HEAD("head"),
   FOOT("foot"),
   OTHER("other");

   private final String name;

   private ThreePartBlock(String pName) {
      this.name = pName;
   }

   @Override
   public String toString() {
      return this.name;
   }

   public String m_7912_() {
      return this.name;
   }
}
