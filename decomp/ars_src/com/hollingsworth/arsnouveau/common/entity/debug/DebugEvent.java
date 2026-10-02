package com.hollingsworth.arsnouveau.common.entity.debug;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugEvent {
   public String id;
   public String message;
   public long timestamp;

   public DebugEvent(String id, String message) {
      this.id = id;
      this.message = message;
      this.timestamp = System.currentTimeMillis();
   }

   @Override
   public String toString() {
      String localTime = new SimpleDateFormat("HH:mm:ss").format(new Date(this.timestamp));
      return "[" + localTime + "] " + this.id + ": " + this.message;
   }
}
