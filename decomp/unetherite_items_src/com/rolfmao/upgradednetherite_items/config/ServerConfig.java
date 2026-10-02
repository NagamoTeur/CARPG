package com.rolfmao.upgradednetherite_items.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

final class ServerConfig {
   final BooleanValue EnableReduceDamageGoldTotem;
   final IntValue ReduceDamageGoldTotem;
   final BooleanValue EnableReduceDamageFireTotem;
   final IntValue ReduceDamageFireTotem;
   final BooleanValue EnableReduceDamageEnderTotem;
   final IntValue ReduceDamageEnderTotem;
   final BooleanValue EnableReduceDamageWaterTotem;
   final IntValue ReduceDamageWaterTotem;
   final BooleanValue EnableReduceDamageWitherTotem;
   final IntValue ReduceDamageWitherTotem;
   final BooleanValue EnableReduceDamagePoisonTotem;
   final IntValue ReduceDamagePoisonTotem;
   final BooleanValue EnableReduceDamagePhantomTotem;
   final IntValue ReduceDamagePhantomTotem;
   final BooleanValue EnableReduceDamageFeatherTotem;
   final IntValue ReduceDamageFeatherTotem;
   final BooleanValue EnableReduceDamageCorruptTotem;
   final IntValue ReduceDamageCorruptTotem;
   final BooleanValue EnableReduceDamageEchoTotem;
   final IntValue ReduceDamageEchoTotem;

   ServerConfig(Builder builder) {
      this.EnableReduceDamageGoldTotem = builder.comment("Enable Gold Upgraded Totem Effect : Reduce Damage ?").define("EnableReduceDamageGoldTotem", true);
      this.ReduceDamageGoldTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageGoldTotem", 10, 1, 100);
      this.EnableReduceDamageFireTotem = builder.comment("Enable Fire Upgraded Totem Effect : Reduce Damage ?").define("EnableReduceDamageFireTotem", true);
      this.ReduceDamageFireTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageFireTotem", 10, 1, 100);
      this.EnableReduceDamageEnderTotem = builder.comment("Enable Ender Upgraded Totem Effect : Reduce Damage ?").define("EnableReduceDamageEnderTotem", true);
      this.ReduceDamageEnderTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageEnderTotem", 10, 1, 100);
      this.EnableReduceDamageWaterTotem = builder.comment("Enable Water Upgraded Totem Effect : Reduce Damage ?").define("EnableReduceDamageWaterTotem", true);
      this.ReduceDamageWaterTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageWaterTotem", 10, 1, 100);
      this.EnableReduceDamageWitherTotem = builder.comment("Enable Wither Upgraded Totem Effect : Reduce Damage ?")
         .define("EnableReduceDamageWitherTotem", true);
      this.ReduceDamageWitherTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageWitherTotem", 10, 1, 100);
      this.EnableReduceDamagePoisonTotem = builder.comment("Enable Poison Upgraded Totem Effect : Reduce Damage ?")
         .define("EnableReduceDamagePoisonTotem", true);
      this.ReduceDamagePoisonTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamagePoisonTotem", 10, 1, 100);
      this.EnableReduceDamagePhantomTotem = builder.comment("Enable Phantom Upgraded Totem Effect : Reduce Damage ?")
         .define("EnableReduceDamagePhantomTotem", true);
      this.ReduceDamagePhantomTotem = builder.comment("By how much is the damage reduced in percentage ?")
         .defineInRange("ReduceDamagePhantomTotem", 10, 1, 100);
      this.EnableReduceDamageFeatherTotem = builder.comment("Enable Feather Upgraded Totem Effect : Reduce Damage ?")
         .define("EnableReduceDamageFeatherTotem", true);
      this.ReduceDamageFeatherTotem = builder.comment("By how much is the damage reduced in percentage ?")
         .defineInRange("ReduceDamageFeatherTotem", 10, 1, 100);
      this.EnableReduceDamageCorruptTotem = builder.comment("Enable Corrupt Upgraded Totem Effect : Reduce Damage ?")
         .define("EnableReduceDamageCorruptTotem", true);
      this.ReduceDamageCorruptTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageCorruptTotem", 5, 1, 100);
      this.EnableReduceDamageEchoTotem = builder.comment("Enable Corrupt Upgraded Totem Effect : Reduce Damage ?").define("EnableReduceDamageEchoTotem", true);
      this.ReduceDamageEchoTotem = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("ReduceDamageEchoTotem", 10, 1, 100);
   }
}
