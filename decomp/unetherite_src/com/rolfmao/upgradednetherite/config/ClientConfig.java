package com.rolfmao.upgradednetherite.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

final class ClientConfig {
   final BooleanValue DisableTooltips;

   ClientConfig(Builder builder) {
      builder.push("general");
      this.DisableTooltips = builder.comment("Disable Tooltips ?").define("DisableTooltips", false);
      builder.pop();
   }
}
