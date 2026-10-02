package com.aqutheseal.celestisynth.config.client;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class CSClientConfig {
   public final ConfigValue<Boolean> visibilityOnFirstPerson;
   public final ConfigValue<Boolean> showLeftArmOnAnimate;
   public final ConfigValue<Boolean> showRightArmOnAnimate;

   public CSClientConfig(Builder builder) {
      builder.push("Client-side Configurations");
      this.visibilityOnFirstPerson = builder.comment("Should the weapon attack effects be visible on first person mode?").define("Is Visible?", true);
      this.showLeftArmOnAnimate = builder.comment("Defines if your left arm must be shown during the ability casting process.").define("Show Left Arm", true);
      this.showRightArmOnAnimate = builder.comment("Defines if your right arm must be shown during the ability casting process.")
         .define("Show Right Arm", true);
      builder.pop();
   }
}
