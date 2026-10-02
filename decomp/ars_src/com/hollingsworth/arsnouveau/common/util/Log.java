package com.hollingsworth.arsnouveau.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log {
   private static Logger logger = LogManager.getLogger("ars_nouveau");

   private Log() {
   }

   public static Logger getLogger() {
      return logger;
   }
}
