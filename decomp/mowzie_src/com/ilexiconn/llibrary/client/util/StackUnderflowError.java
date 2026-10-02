package com.ilexiconn.llibrary.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StackUnderflowError extends Error {
   public StackUnderflowError() {
   }

   public StackUnderflowError(String s) {
      super(s);
   }
}
