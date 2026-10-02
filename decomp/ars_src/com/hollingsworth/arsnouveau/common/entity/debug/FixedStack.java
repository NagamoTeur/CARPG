package com.hollingsworth.arsnouveau.common.entity.debug;

import java.util.Stack;

public class FixedStack<T> extends Stack<T> {
   private int maxSize;

   public FixedStack(int size) {
      this.maxSize = size;
   }

   @Override
   public T push(T object) {
      while (this.size() >= this.maxSize) {
         this.remove(0);
      }

      return super.push(object);
   }
}
