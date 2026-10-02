package com.rolfmao.upgradednetherite.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

final class ServerConfig {
   final IntValue UpgradedPlayerGoldArmorRequireSet;
   final IntValue UpgradedPlayerFireArmorRequireSet;
   final IntValue UpgradedPlayerEnderArmorRequireSet;
   final IntValue UpgradedPlayerWaterArmorRequireSet;
   final IntValue UpgradedPlayerWitherArmorRequireSet;
   final IntValue UpgradedPlayerPoisonArmorRequireSet;
   final IntValue UpgradedPlayerPhantomArmorRequireSet;
   final IntValue UpgradedPlayerFeatherArmorRequireSet;
   final IntValue UpgradedPlayerEchoArmorRequireSet;
   final BooleanValue EnablePiglinNeutral;
   final BooleanValue EnableLuckBonus;
   final IntValue LuckBonus;
   final BooleanValue EnableDamageBonusGoldWeapon;
   final IntValue DamageBonusGoldWeapon;
   final BooleanValue EnableLootingBonus;
   final IntValue LootingBonus;
   final IntValue LootingEnchantBonus;
   final BooleanValue EnableFortuneBonus;
   final IntValue FortuneBonus;
   final IntValue FortuneEnchantBonus;
   final BooleanValue EnableFireImmune;
   final BooleanValue EnableLavaSpeed;
   final BooleanValue EnableDamageDurabilityFireArmor;
   final IntValue DelayDamageDurabilityFireArmor;
   final IntValue DamageDurabilityFireArmor;
   final BooleanValue EnableAutoSmelt;
   final BooleanValue EnableAutoSmeltFortune;
   final BooleanValue EnableDamageBonusFireWeapon;
   final IntValue DamageBonusFireWeapon;
   final IntValue DamageBonusFireEnchantWeapon;
   final BooleanValue EnableVoidSave;
   final BooleanValue EnablePreventAnger;
   final BooleanValue EnableBreakEnderArmor;
   final BooleanValue EnableDamageBonusEnderWeapon;
   final IntValue DamageBonusEnderWeapon;
   final BooleanValue EnablePreventTeleport;
   final BooleanValue EnableDoubleLootingBonusEnderWeapon;
   final BooleanValue EnableTeleportChest;
   final BooleanValue EnableWaterBreath;
   final BooleanValue EnableWaterSpeed;
   final BooleanValue EnableDamageDurabilityWaterArmor;
   final IntValue DelayDamageDurabilityWaterArmor;
   final IntValue DamageDurabilityWaterArmor;
   final BooleanValue EnableDamageBonusWaterWeapon;
   final IntValue DamageBonusWaterWeapon;
   final BooleanValue EnableDamageBonusWaterEndermanWeapon;
   final IntValue DamageBonusWaterEndermanWeapon;
   final BooleanValue EnableMiningSpeedUnderwater;
   final BooleanValue EnableElderGuardianDebuffImmune;
   final BooleanValue EnableWitherImmune;
   final BooleanValue EnableDamageDurabilityWitherArmor;
   final IntValue DelayDamageDurabilityWitherArmor;
   final IntValue DamageDurabilityWitherArmor;
   final BooleanValue EnableDamageBonusWitherWeapon;
   final IntValue DamageBonusWitherWeapon;
   final BooleanValue EnableWitherEffect;
   final BooleanValue EnablePoisonImmune;
   final BooleanValue EnableClimbWall;
   final BooleanValue EnableDamageDurabilityPoisonArmor;
   final IntValue DelayDamageDurabilityPoisonArmor;
   final IntValue DamageDurabilityPoisonArmor;
   final BooleanValue EnableDamageBonusPoisonWeapon;
   final IntValue DamageBonusPoisonWeapon;
   final BooleanValue EnablePoisonEffect;
   final BooleanValue EnableFallImmune;
   final BooleanValue EnableStepHeight;
   final BooleanValue EnableDamageDurabilityPhantomArmor;
   final IntValue MultiplierDamageDurabilityPhantomArmor;
   final BooleanValue EnableDamageBonusPhantomWeapon;
   final IntValue DamageBonusPhantomWeapon;
   final BooleanValue EnableGlowingEffect;
   final BooleanValue EnableReachEffect;
   final IntValue BonusReachTool;
   final BooleanValue EnableWaterLavaWalking;
   final BooleanValue EnableMultiJump;
   final IntValue MultiJump;
   final BooleanValue EnableReduceFallDamage;
   final BooleanValue EnableWaterDamageDurabilityFeatherArmor;
   final BooleanValue EnableLavaDamageDurabilityFeatherArmor;
   final BooleanValue EnableFallDamageDurabilityFeatherArmor;
   final IntValue DelayDamageDurabilityFeatherArmor;
   final IntValue DamageDurabilityFeatherArmor;
   final BooleanValue EnableAttractItem;
   final BooleanValue EnableLevitationImmune;
   final BooleanValue EnableHealthMalus;
   final IntValue HealthMalus;
   final BooleanValue EnableDamageBonusCorruptWeapon;
   final IntValue DamageBonusCorruptWeapon;
   final BooleanValue EnableLootingBonusCorruptWeapon;
   final IntValue LootingBonusCorruptWeapon;
   final BooleanValue EnableFortuneBonusCorruptTool;
   final IntValue FortuneBonusCorruptTool;
   final BooleanValue EnableSoulbound;
   final BooleanValue EnableKeepItemsChance;
   final IntValue KeepItemsChance;
   final BooleanValue EnableReduceDamageEchoArmor;
   final IntValue ReduceDamageEchoArmor;
   final BooleanValue EnableHealEchoArmor;
   final IntValue HealEchoArmorDelay;
   final BooleanValue EnableDamageBonusEchoWeapon;
   final IntValue DamageBonusEchoWeapon;
   final BooleanValue EnableBonusExpEcho;
   final IntValue MinExpEcho;
   final IntValue MaxExpEcho;

   ServerConfig(Builder builder) {
      builder.push("Armor");
      builder.push("Set Option");
      this.UpgradedPlayerGoldArmorRequireSet = builder.comment("How many pieces of Gold Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerGoldArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerFireArmorRequireSet = builder.comment("How many pieces of Fire Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerFireArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerEnderArmorRequireSet = builder.comment("How many pieces of Ender Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerEnderArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerWaterArmorRequireSet = builder.comment("How many pieces of Water Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerWaterArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerWitherArmorRequireSet = builder.comment("How many pieces of Wither Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerWitherArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerPoisonArmorRequireSet = builder.comment("How many pieces of Poison Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerPoisonArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerPhantomArmorRequireSet = builder.comment("How many pieces of Phantom Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerPhantomArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerFeatherArmorRequireSet = builder.comment("How many pieces of Feather Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerFeatherArmorRequireSet", 4, 1, 4);
      this.UpgradedPlayerEchoArmorRequireSet = builder.comment("How many pieces of Echo Upgraded armor are needed to activate the effects?")
         .defineInRange("UpgradedPlayerEchoArmorRequireSet", 4, 1, 4);
      builder.pop();
      builder.pop();
      builder.push("Effect");
      builder.push("Gold");
      builder.push("Armor");
      this.EnablePiglinNeutral = builder.comment("Enable Gold Upgraded Armor Effect : Piglin become neutral ?").define("EnablePiglinNeutral", true);
      this.EnableLuckBonus = builder.comment("Enable Gold Upgraded Armor Effect : Increase Luck ?").define("EnableLuckBonus", true);
      this.LuckBonus = builder.comment("How much is the luck bonus increased ?").defineInRange("LuckBonus", 2, 1, 99);
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusGoldWeapon = builder.comment("Enable Gold Upgraded Weapon Effect : Increase Damage ?").define("EnableDamageBonusGoldWeapon", true);
      this.DamageBonusGoldWeapon = builder.comment("By how much are the damages increased in percentage ?").defineInRange("DamageBonusGoldWeapon", 10, 1, 999);
      this.EnableLootingBonus = builder.comment("Enable Gold Upgraded Weapon Effect : Increase Looting ?").define("EnableLootingBonus", true);
      this.LootingBonus = builder.comment("How much is the looting bonus increased ?").defineInRange("LootingBonus", 1, 1, 99);
      this.LootingEnchantBonus = builder.comment("How much is the looting bonus increased if enchanted ?").defineInRange("LootingEnchantBonus", 1, 1, 99);
      this.EnableFortuneBonus = builder.comment("Enable Gold Upgraded Weapon Effect : Increase Fortune ?").define("EnableFortuneBonus", true);
      this.FortuneBonus = builder.comment("How much is the fortune bonus increased ?").defineInRange("FortuneBonus", 1, 1, 99);
      this.FortuneEnchantBonus = builder.comment("How much is the fortune bonus increased if enchanted ?").defineInRange("FortuneEnchantBonus", 1, 1, 99);
      builder.pop();
      builder.pop();
      builder.push("Fire");
      builder.push("Armor");
      this.EnableFireImmune = builder.comment("Enable Fire Upgraded Armor Effect : Fire damage immune ?").define("EnableFireImmune", true);
      this.EnableLavaSpeed = builder.comment("Enable Fire Upgraded Armor Effect : Lava Speed ?").define("EnableLavaSpeed", true);
      builder.push("Durability");
      this.EnableDamageDurabilityFireArmor = builder.comment("Fire Upgraded Armor loose durability when protect the player ? (Require EnableFireImmune = True)")
         .define("EnableDamageDurabilityFireArmor", false);
      this.DelayDamageDurabilityFireArmor = builder.comment(
            "How many tick between durability damage for the Fire Upgraded Armor when it protect the player ? (20ticks = 1sec)"
         )
         .defineInRange("DelayDamageDurabilityFireArmor", 20, 1, 1000);
      this.DamageDurabilityFireArmor = builder.comment("How many durability damage take the Fire Upgraded Armor when protect the player ? ")
         .defineInRange("DamageDurabilityFireArmor", 1, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableAutoSmelt = builder.comment("Enable Fire Upgraded Tool&Weapon Effect : Auto-Smelt ?").define("EnableAutoSmelt", true);
      this.EnableAutoSmeltFortune = builder.comment("Auto-Smelt is affected by Fortune ? (Only Ingot & Charcoal)").define("EnableAutoSmeltFortune", false);
      this.EnableDamageBonusFireWeapon = builder.comment("Enable Fire Upgraded Weapon Effect : Increase Damage ?").define("EnableDamageBonusFireWeapon", true);
      this.DamageBonusFireWeapon = builder.comment("By how much are the damages increased in percentage ?").defineInRange("DamageBonusFireWeapon", 4, 1, 999);
      this.DamageBonusFireEnchantWeapon = builder.comment("By how much are the damages increased in percentage if enchanted ?")
         .defineInRange("DamageBonusFireEnchantWeapon", 3, 1, 999);
      builder.pop();
      builder.pop();
      builder.push("Ender");
      builder.push("Armor");
      this.EnableVoidSave = builder.comment("Enable Ender Upgraded Armor Effect : Saves from the void ?").define("EnableVoidSave", true);
      this.EnablePreventAnger = builder.comment("Enable Ender Upgraded Armor Effect : Prevent Enderman Anger ?").define("EnablePreventAnger", true);
      builder.push("Durability");
      this.EnableBreakEnderArmor = builder.comment("Ender Upgraded Armor Break when save the player ? ").define("EnableBreakEnderArmor", false);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusEnderWeapon = builder.comment("Enable Ender Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusEnderWeapon", true);
      this.DamageBonusEnderWeapon = builder.comment("By how much are the damages increased in percentage ?").defineInRange("DamageBonusEnderWeapon", 4, 1, 999);
      this.EnablePreventTeleport = builder.comment("Enable Ender Upgraded Weapon Effect : Prevent Teleport ?").define("EnablePreventTeleport", true);
      this.EnableDoubleLootingBonusEnderWeapon = builder.comment("Enable Ender Upgraded Weapon Effect : Double Looting against Enderman ?")
         .define("EnableDoubleLootingBonusEnderWeapon", true);
      this.EnableTeleportChest = builder.comment("Enable Ender Upgraded Weapon Effect : Teleports Loot ?").define("EnableTeleportChest", true);
      builder.pop();
      builder.pop();
      builder.push("Water");
      builder.push("Armor");
      this.EnableWaterBreath = builder.comment("Enable Water Upgraded Armor Effect : Water Breathing ?").define("EnableWaterBreath", true);
      this.EnableWaterSpeed = builder.comment("Enable Water Upgraded Armor Effect : Water Speed ?").define("EnableWaterSpeed", true);
      this.EnableElderGuardianDebuffImmune = builder.comment(
            "Enable Water Upgraded Armor Effect : Mining Fatigue Immune ?(Only from the Elder Guardian and does not remove the effects already present)"
         )
         .define("EnableElderGuardianDebuffImmune", true);
      builder.push("Durability");
      this.EnableDamageDurabilityWaterArmor = builder.comment("Water Upgraded Armor loose durability when protect the player ? ")
         .define("EnableDamageDurabilityWaterArmor", false);
      this.DelayDamageDurabilityWaterArmor = builder.comment(
            "How many tick between durability damage for the Water Upgraded Armor when it protect the player ? (20ticks = 1sec)"
         )
         .defineInRange("DelayDamageDurabilityWaterArmor", 20, 1, 1000);
      this.DamageDurabilityWaterArmor = builder.comment("How many durability damage take the Water Upgraded Armor when protect the player ? ")
         .defineInRange("DamageDurabilityWaterArmor", 1, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusWaterWeapon = builder.comment("Enable Water Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusWaterWeapon", true);
      this.DamageBonusWaterWeapon = builder.comment("By how much are the damages increased in percentage ?")
         .defineInRange("DamageBonusWaterWeapon", 10, 1, 999);
      this.EnableDamageBonusWaterEndermanWeapon = builder.comment("Enable Water Upgraded Weapon Effect : Increase against Enderman Damage ?")
         .define("EnableDamageBonusWaterEndermanWeapon", true);
      this.DamageBonusWaterEndermanWeapon = builder.comment("By how much are the damages against increased Enderman in percentage ?")
         .defineInRange("DamageBonusWaterEndermanWeapon", 10, 1, 999);
      this.EnableMiningSpeedUnderwater = builder.comment("Enable Water Upgraded Tool Effect : Increase Mining Speed underwater ?")
         .define("EnableMiningSpeedUnderwater", true);
      builder.pop();
      builder.pop();
      builder.push("Wither");
      builder.push("Armor");
      this.EnableWitherImmune = builder.comment("Enable Wither Upgraded Armor Effect : Wither Immune ?").define("EnableWitherImmune", true);
      builder.push("Durability");
      this.EnableDamageDurabilityWitherArmor = builder.comment("Wither Upgraded Armor loose durability when protect the player ? ")
         .define("EnableDamageDurabilityWitherArmor", false);
      this.DelayDamageDurabilityWitherArmor = builder.comment(
            "How many tick between durability damage for the Wither Upgraded Armor when it protect the player ? (20ticks = 1sec)"
         )
         .defineInRange("DelayDamageDurabilityWitherArmor", 20, 1, 1000);
      this.DamageDurabilityWitherArmor = builder.comment("How many durability damage take the Wither Upgraded Armor when protect the player ? ")
         .defineInRange("DamageDurabilityWitherArmor", 5, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusWitherWeapon = builder.comment("Enable Wither Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusWitherWeapon", true);
      this.DamageBonusWitherWeapon = builder.comment("By how much are the damages increased in percentage ?")
         .defineInRange("DamageBonusWitherWeapon", 4, 1, 999);
      this.EnableWitherEffect = builder.comment("Enable Wither Upgraded Weapon Effect : Wither Effect ?").define("EnableWitherEffect", true);
      builder.pop();
      builder.pop();
      builder.push("Poison");
      builder.push("Armor");
      this.EnablePoisonImmune = builder.comment("Enable Poison Upgraded Armor Effect : Poison Immune ?").define("EnablePoisonImmune", true);
      this.EnableClimbWall = builder.comment("Enable Poison Upgraded Armor Effect : Climbs Walls ?").define("EnableClimbWall", true);
      builder.push("Durability");
      this.EnableDamageDurabilityPoisonArmor = builder.comment("Poison Upgraded Armor loose durability when protect the player ? ")
         .define("EnableDamageDurabilityPoisonArmor", false);
      this.DelayDamageDurabilityPoisonArmor = builder.comment(
            "How many tick between durability damage for the Poison Upgraded Armor when it protect the player ? (20ticks = 1sec)"
         )
         .defineInRange("DelayDamageDurabilityPoisonArmor", 20, 1, 1000);
      this.DamageDurabilityPoisonArmor = builder.comment("How many durability damage take the Poison Upgraded Armor when protect the player ? ")
         .defineInRange("DamageDurabilityPoisonArmor", 5, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusPoisonWeapon = builder.comment("Enable Poison Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusPoisonWeapon", true);
      this.DamageBonusPoisonWeapon = builder.comment("By how much are the damages increased in percentage ?")
         .defineInRange("DamageBonusPoisonWeapon", 4, 1, 999);
      this.EnablePoisonEffect = builder.comment("Enable Poison Upgraded Weapon Effect : Poison Effect ?").define("EnablePoisonEffect", true);
      builder.pop();
      builder.pop();
      builder.push("Phantom");
      builder.push("Armor");
      this.EnableFallImmune = builder.comment("Enable Phantom Upgraded Armor Effect : Fall damage Immune ?").define("EnableFallImmune", true);
      this.EnableStepHeight = builder.comment("Enable Phantom Upgraded Armor Effect : Step Height ?").define("EnableStepHeight", true);
      builder.push("Durability");
      this.EnableDamageDurabilityPhantomArmor = builder.comment("Phantom Upgraded Armor loose durability when protect the player ? (Depend on fall distance)")
         .define("EnableDamageDurabilityPhantomArmor", false);
      this.MultiplierDamageDurabilityPhantomArmor = builder.comment(
            "How many durability damage take the Phantom Upgraded Armor when protect the player ? (Fall distance * Value)"
         )
         .defineInRange("MultiplierDamageDurabilityPhantomArmor", 1, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusPhantomWeapon = builder.comment("Enable Phantom Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusPhantomWeapon", true);
      this.DamageBonusPhantomWeapon = builder.comment("By how much are the damages increased in percentage ?")
         .defineInRange("DamageBonusPhantomWeapon", 10, 1, 999);
      this.EnableGlowingEffect = builder.comment("Enable Phantom Upgraded Tool Effect : Glowing Effect ?").define("EnableGlowingEffect", true);
      this.EnableReachEffect = builder.comment("Enable Phantom Upgraded Tool Effect : Reach ?").define("EnableReachEffect", true);
      this.BonusReachTool = builder.comment("By how much are the reach increased ?").defineInRange("BonusReachTool", 2, 1, 99);
      builder.pop();
      builder.pop();
      builder.push("Feather");
      builder.push("Armor");
      this.EnableWaterLavaWalking = builder.comment("Enable Feather Upgraded Armor Effect : Water & Lava Walking ?").define("EnableWaterLavaWalking", true);
      this.EnableMultiJump = builder.comment("Enable Feather Upgraded Armor Effect : Multi Jump ?").define("EnableMultiJump", true);
      this.MultiJump = builder.comment("How many Multi Jump ?").defineInRange("MultiJump", 2, 1, 99);
      this.EnableReduceFallDamage = builder.comment("Enable Feather Upgraded Armor Effect : Fall Damage Reduction ?").define("EnableReduceFallDamage", true);
      this.EnableLevitationImmune = builder.comment("Enable Feather Upgraded Armor Effect : Levitation Immune ?").define("EnableLevitationImmune", true);
      builder.push("Durability");
      this.EnableWaterDamageDurabilityFeatherArmor = builder.comment("Feather Upgraded Armor loose durability when the player walk on water ? ")
         .define("EnableWaterDamageDurabilityFeatherArmor", false);
      this.EnableLavaDamageDurabilityFeatherArmor = builder.comment("Feather Upgraded Armor loose durability when the player walk on lava ? ")
         .define("EnableLavaDamageDurabilityFeatherArmor", false);
      this.EnableFallDamageDurabilityFeatherArmor = builder.comment("Feather Upgraded Armor loose durability when reducing fall damage ? ")
         .define("EnableFallDamageDurabilityFeatherArmor", false);
      this.DelayDamageDurabilityFeatherArmor = builder.comment(
            "How many tick between durability damage for the Feather Upgraded Armor when the player walk on water/lava or slow falling ? (20ticks = 1sec)"
         )
         .defineInRange("DelayDamageDurabilityFeatherArmor", 20, 1, 1000);
      this.DamageDurabilityFeatherArmor = builder.comment(
            "How many durability damage take the Feather Upgraded Armor when the player walk on water/lava or slow falling ? "
         )
         .defineInRange("DamageDurabilityFeatherArmor", 1, 1, 100);
      builder.pop();
      builder.pop();
      builder.push("Tool&Weapon");
      builder.pop();
      this.EnableAttractItem = builder.comment("Enable Feather Upgraded Tool Effect : Attracts Items ?").define("EnableAttractItem", true);
      builder.pop();
      builder.push("Corrupt");
      builder.push("Armor");
      this.EnableHealthMalus = builder.comment("Enable Corrupt Upgraded Armor Effect : Health Malus ?").define("EnableHealthMalus", true);
      this.HealthMalus = builder.comment("By how much are the health reduced in percentage ?").defineInRange("HealthMalus", 10, 1, 99);
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusCorruptWeapon = builder.comment("Enable Corrupt Upgraded Weapon Effect : Increase Damage ?")
         .define("EnableDamageBonusCorruptWeapon", true);
      this.DamageBonusCorruptWeapon = builder.comment("By how much are the damages increased in percentage ?")
         .defineInRange("DamageBonusCorruptWeapon", 7, 1, 999);
      this.EnableLootingBonusCorruptWeapon = builder.comment("Enable Corrupt Upgraded Weapon Effect : Increase Looting ?")
         .define("EnableLootingBonusCorruptWeapon", true);
      this.LootingBonusCorruptWeapon = builder.comment("How much is the looting bonus increased ?").defineInRange("LootingBonusCorruptWeapon", 2, 1, 99);
      this.EnableFortuneBonusCorruptTool = builder.comment("Enable Gold Upgraded Weapon Effect : Increase Fortune ?")
         .define("EnableFortuneBonusCorruptTool", true);
      this.FortuneBonusCorruptTool = builder.comment("How much is the fortune bonus increased ?").defineInRange("FortuneBonusCorruptTool", 2, 1, 99);
      builder.pop();
      builder.pop();
      builder.push("Echo");
      builder.push("Armor");
      this.EnableSoulbound = builder.comment("Enable Echo Upgraded Armor Effect : Soulbound").define("EnableSoulbound", true);
      this.EnableKeepItemsChance = builder.comment("Enable Echo Upgraded Armor Effect : Possibility that items remain in the inventory upon death ?")
         .define("EnableKeepItemsChance", true);
      this.KeepItemsChance = builder.comment("What are the chances that your items will remain in your inventory when you die?")
         .defineInRange("KeepItemsChance", 50, 1, 100);
      this.EnableReduceDamageEchoArmor = builder.comment("Enable Echo Upgraded Armor Effect : Reduce Damage ?").define("EnableDamageBonusEchoArmor", true);
      this.ReduceDamageEchoArmor = builder.comment("By how much is the damage reduced in percentage ?").defineInRange("DamageBonusEchoArmor", 50, 1, 100);
      this.EnableHealEchoArmor = builder.comment("Enable Echo Upgraded Armor Effect : Heals ?").define("EnableHealEchoArmor", true);
      this.HealEchoArmorDelay = builder.comment("Delay between heals in seconds ?").defineInRange("HealEchoArmorDelay", 20, 1, 999);
      builder.pop();
      builder.push("Tool&Weapon");
      this.EnableDamageBonusEchoWeapon = builder.comment("Enable Echo Upgraded Weapon Effect : Increase Damage ?").define("EnableDamageBonusEchoWeapon", true);
      this.DamageBonusEchoWeapon = builder.comment("By how much are the damages increased in percentage ?").defineInRange("DamageBonusEchoWeapon", 20, 1, 999);
      this.EnableBonusExpEcho = builder.comment("Enable Echo Upgraded Tool Effect : Exp Bonus ?").define("EnableBonusExpEcho", true);
      this.MinExpEcho = builder.comment("How many exp minimum?").defineInRange("MinExpEcho", 2, 0, 999);
      this.MaxExpEcho = builder.comment("How many exp maximum?").defineInRange("MaxExpEcho", 5, 0, 999);
      builder.pop();
      builder.pop();
      builder.pop();
   }
}
