package com.rolfmao.upgradednetherite_items.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {
   public static final ForgeConfigSpec CLIENT_SPEC;
   public static final ForgeConfigSpec SERVER_SPEC;
   static final ClientConfig CLIENT;
   static final ServerConfig SERVER;

   static {
      Pair<ClientConfig, ForgeConfigSpec> specPair = new Builder().configure(ClientConfig::new);
      CLIENT = (ClientConfig)specPair.getLeft();
      CLIENT_SPEC = (ForgeConfigSpec)specPair.getRight();
      specPair = new Builder().configure(ServerConfig::new);
      SERVER = (ServerConfig)specPair.getLeft();
      SERVER_SPEC = (ForgeConfigSpec)specPair.getRight();
   }
}
