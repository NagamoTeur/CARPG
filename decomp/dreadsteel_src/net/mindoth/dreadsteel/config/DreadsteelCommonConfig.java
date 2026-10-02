package net.mindoth.dreadsteel.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class DreadsteelCommonConfig {
   public static final Builder BUILDER = new Builder();
   public static final ForgeConfigSpec SPEC = BUILDER.build();
   public static final ConfigValue<Integer> HELMET_ARMOR = BUILDER.comment("Dreadsteel helmet's armor value (Default = 10)").define("Helmet armor", 10);
   public static final ConfigValue<Integer> CHESTPLATE_ARMOR = BUILDER.comment("Dreadsteel chestplate's armor value (Default = 15)")
      .define("Chestplate armor", 15);
   public static final ConfigValue<Integer> LEGGINGS_ARMOR = BUILDER.comment("Dreadsteel leggings's armor value (Default = 12)").define("Leggings armor", 12);
   public static final ConfigValue<Integer> BOOTS_ARMOR = BUILDER.comment("Dreadsteel boots' armor value (Default = 9)").define("Boots armor", 9);
   public static final ConfigValue<Integer> ARMOR_TOUGHNESS = BUILDER.comment("Dreadsteel armor piece toughness (Default = 8)").define("Armor toughness", 8);
   public static final ConfigValue<Double> ARMOR_KNOCKBACK_RESISTANCE = BUILDER.comment("Dreadsteel armor piece knockback resistance (Default = 0.25)")
      .define("Armor knockback resistance", 0.25);
   public static final ConfigValue<Integer> SCYTHE_DAMAGE = BUILDER.comment(
         "Dreadsteel Scythe's attack damage. Note that +1 damage comes from your hand so 49 = 50 damage on tootltip (Default = 49)"
      )
      .define("Scythe attack damage", 49);
   public static final ConfigValue<Double> SCYTHE_SPEED = BUILDER.comment("Dreadsteel Scythe's attack speed (Default = 1.6)")
      .define("Scythe attack speed", 1.6);

   static {
      BUILDER.push("Configs for Dreadsteel");
      BUILDER.pop();
   }
}
