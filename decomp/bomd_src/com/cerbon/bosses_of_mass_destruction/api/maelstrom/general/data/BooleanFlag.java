package com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data;

public class BooleanFlag {
   private boolean flag = false;

   public void flag() {
      this.flag = true;
   }

   public boolean getAndReset() {
      boolean toReturn = this.flag;
      if (this.flag) {
         this.flag = false;
      }

      return toReturn;
   }
}
