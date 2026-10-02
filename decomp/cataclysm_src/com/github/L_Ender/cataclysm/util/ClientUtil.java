package com.github.L_Ender.cataclysm.util;

import java.util.List;
import net.minecraft.client.multiplayer.ServerData;

public class ClientUtil {
   public static boolean set(ServerData p_233840_, List<ServerData> p_233841_) {
      for (int i = 0; i < p_233841_.size(); i++) {
         ServerData serverdata = p_233841_.get(i);
         if (serverdata.f_105362_.equals(p_233840_.f_105362_) && serverdata.f_105363_.equals(p_233840_.f_105363_)) {
            p_233841_.set(i, p_233840_);
            return true;
         }
      }

      return false;
   }
}
