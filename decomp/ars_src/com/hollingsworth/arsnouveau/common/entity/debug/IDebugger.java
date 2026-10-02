package com.hollingsworth.arsnouveau.common.entity.debug;

import java.io.IOException;
import java.io.PrintWriter;

public interface IDebugger {
   default void addEntityEvent(DebugEvent event) {
      this.addEntityEvent(event, false);
   }

   void addEntityEvent(DebugEvent var1, boolean var2);

   void writeFile(PrintWriter var1) throws IOException;
}
