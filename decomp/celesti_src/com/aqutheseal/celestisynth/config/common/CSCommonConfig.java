package com.aqutheseal.celestisynth.config.common;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.StringUtils;

public class CSCommonConfig {
   public final ConfigValue<Integer> solarisDmg;
   public final ConfigValue<Integer> crescentiaDmg;
   public final ConfigValue<Double> solarisSkillDmg;
   public final ConfigValue<Double> solarisShiftSkillDmg;
   public final ConfigValue<Integer> solarisSkillCD;
   public final ConfigValue<Integer> solarisShiftSkillCD;
   public final ConfigValue<Double> crescentiaSkillDmg;
   public final ConfigValue<Double> crescentiaShiftSkillDmg;
   public final ConfigValue<Integer> crescentiaSkillCD;
   public final ConfigValue<Integer> crescentiaShiftSkillCD;
   public final ConfigValue<Double> breezebreakerSkillDmg;
   public final ConfigValue<Double> breezebreakerShiftSkillDmg;
   public final ConfigValue<Double> breezebreakerSprintSkillDmg;
   public final ConfigValue<Double> breezebreakerMidairSkillDmg;
   public final ConfigValue<Integer> breezebreakerSkillCD;
   public final ConfigValue<Integer> breezebreakerShiftSkillCD;
   public final ConfigValue<Integer> breezebreakerSprintSkillCD;
   public final ConfigValue<Integer> breezebreakerMidairSkillCD;
   public final ConfigValue<Double> poltergeistSkillDmg;
   public final ConfigValue<Double> poltergeistShiftSkillDmg;
   public final ConfigValue<Integer> poltergeistSkillCD;
   public final ConfigValue<Integer> poltergeistShiftSkillCD;
   public final ConfigValue<Double> aquafloraSkillDmg;
   public final ConfigValue<Double> aquafloraShiftSkillDmg;
   public final ConfigValue<Double> aquafloraBloomSkillDmg;
   public final ConfigValue<Double> aquafloraBloomShiftSkillDmg;
   public final ConfigValue<Integer> aquafloraSkillCD;
   public final ConfigValue<Integer> aquafloraShiftSkillCD;
   public final ConfigValue<Integer> aquafloraBloomSkillCD;
   public final ConfigValue<Integer> aquafloraBloomShiftSkillCD;
   public final ConfigValue<Boolean> enablePoltergeistHeightDmg;
   public final ConfigValue<Double> rainfallSerenityArrowDmg;
   public final ConfigValue<Double> rainfallSerenityQuasarArrowDmg;
   public final ConfigValue<Double> rainfallSerenityDrawSpeed;

   public CSCommonConfig(Builder builder) {
      builder.push("Base Damage Modifications (Temporarily Unusable)");
      this.solarisDmg = this.baseDamage(builder, "solaris", 7);
      this.crescentiaDmg = this.baseDamage(builder, "crescentia", 8);
      builder.pop();
      builder.comment("Some attacks deal a specified amount of damage FOR EVERY HIT, so be careful as you tweak these values!");
      builder.comment(" ");
      builder.push("Value Modifiers - Solaris");
      this.solarisSkillDmg = this.skillDamage(builder, "solaris", "Spinning Flames - Full Round", 0.85);
      this.solarisShiftSkillDmg = this.skillDamage(builder, "solaris", "Spinning Flames - Soul Straight Dash [Shift]", 1.2);
      this.solarisSkillCD = this.skillCooldown(builder, "solaris", "Spinning Flames - Full Round", 70);
      this.solarisShiftSkillCD = this.skillCooldown(builder, "solaris", "Spinning Flames - Soul Straight Dash [Shift]", 130);
      builder.pop();
      builder.push("Value Modifiers - Crescentia");
      this.crescentiaSkillDmg = this.skillDamage(builder, "crescentia", "Lunar Celebration Barrage", 0.5);
      this.crescentiaShiftSkillDmg = this.skillDamage(builder, "crescentia", "Dragon Crescent Boom [Shift]", 0.7);
      this.crescentiaSkillCD = this.skillCooldown(builder, "crescentia", "Lunar Celebration Barrage", 100);
      this.crescentiaShiftSkillCD = this.skillCooldown(builder, "crescentia", "Dragon Crescent Boom [Shift]", 40);
      builder.pop();
      builder.push("Value Modifiers - Breezebreaker");
      this.breezebreakerSkillDmg = this.skillDamage(builder, "breezebreaker", "Galestorm + Dual Galestorm", 7.0);
      this.breezebreakerShiftSkillDmg = this.skillDamage(builder, "breezebreaker", "Full-Force Whirlwind Extravagance [Shift]", 0.85);
      this.breezebreakerSprintSkillDmg = this.skillDamage(builder, "breezebreaker", "Roar of the Wind [Sprint]", 11.5);
      this.breezebreakerMidairSkillDmg = this.skillDamage(builder, "breezebreaker", "Zephyr's Death Wheel [Mid-air]", 8.0);
      this.breezebreakerSkillCD = this.skillCooldown(builder, "breezebreaker", "Galestorm + Dual Galestorm", 15);
      this.breezebreakerShiftSkillCD = this.skillCooldown(builder, "breezebreaker", "Full-Force Whirlwind Extravagance [Shift]", 35);
      this.breezebreakerSprintSkillCD = this.skillCooldown(builder, "breezebreaker", "Roar of the Wind [Sprint]", 15);
      this.breezebreakerMidairSkillCD = this.skillCooldown(builder, "breezebreaker", "Zephyr's Death Wheel [Mid-air]", 40);
      builder.pop();
      builder.push("Value Modifiers - Poltergeist");
      this.poltergeistSkillDmg = this.skillDamage(builder, "Poltergeist", "Cosmic Steel Annihilation", 17.5);
      this.poltergeistShiftSkillDmg = this.skillDamage(builder, "Poltergeist", "Barrier Call", 10.0);
      this.poltergeistSkillCD = this.skillCooldown(builder, "Poltergeist", "Cosmic Steel Annihilation", 200);
      this.poltergeistShiftSkillCD = this.skillCooldown(builder, "Poltergeist", "Barrier Call", 80);
      this.enablePoltergeistHeightDmg = builder.comment("Enables the increasing of Poltergeist's Cosmic Steel Annihilation damage with smash height.")
         .define("Enable Height Damage", true);
      builder.pop();
      builder.push("Value Modifiers - Aquaflora");
      this.aquafloraSkillDmg = this.skillDamage(builder, "Aquaflora", "Petal Pierces", 0.35);
      this.aquafloraShiftSkillDmg = this.skillDamage(builder, "Aquaflora", "Blasting Off Together", 7.0);
      this.aquafloraBloomSkillDmg = this.skillDamage(builder, "Aquaflora", "Exorbitant Slashing Frenzy", 1.15);
      this.aquafloraBloomShiftSkillDmg = this.skillDamage(builder, "Aquaflora", "Flowers Away", 0.0);
      this.aquafloraSkillCD = this.skillCooldown(builder, "Aquaflora", "Petal Pierces", 20);
      this.aquafloraShiftSkillCD = this.skillCooldown(builder, "Aquaflora", "Blasting Off Together", 20);
      this.aquafloraBloomSkillCD = this.skillCooldown(builder, "Aquaflora", "Exorbitant Slashing Frenzy", 200);
      this.aquafloraBloomShiftSkillCD = this.skillCooldown(builder, "Aquaflora", "Flowers Away", 40);
      builder.pop();
      builder.push("Value Modifiers - Rainfall Serenity");
      this.rainfallSerenityArrowDmg = builder.comment("Define how much damage does the Rainfall Serenity's Arrow deal.")
         .defineInRange("Damage: Rainfall Serenity Arrow", 4.0, 0.0, 1000.0);
      this.rainfallSerenityQuasarArrowDmg = builder.comment("Define how much damage does the Rainfall Serenity's Arrow from Quasar Link deal.")
         .defineInRange("Damage: Rainfall Serenity Arrow (Quasar)", 2.0, 0.0, 1000.0);
      this.rainfallSerenityDrawSpeed = builder.comment("Define how much damage does the Rainfall Serenity's Arrow from Quasar Link deal.")
         .defineInRange("Extra: Rainfall Serenity Draw Speed", 7.5, 0.0, 1000.0);
      builder.pop();
   }

   public ConfigValue<Double> skillDamage(Builder builder, String weapon, String skillName, Double dmg) {
      return builder.comment("Define how much damage does the " + StringUtils.capitalize(weapon) + " deal in a specified attack skill.")
         .defineInRange("Damage: " + skillName, dmg, 0.0, 1000.0);
   }

   public ConfigValue<Integer> skillCooldown(Builder builder, String weapon, String skillName, int cooldown) {
      return builder.comment(
            "Define the duration of the cooldown provided by the " + StringUtils.capitalize(weapon) + " in a particular attack skill, measured in ticks."
         )
         .defineInRange("Cooldown: " + skillName, cooldown, 0, 1000);
   }

   public ConfigValue<Integer> baseDamage(Builder builder, String weapon, int dmg) {
      return builder.comment("Define the base attack damage of the " + StringUtils.capitalize(weapon) + ".")
         .defineInRange("Base Damage: " + StringUtils.capitalize(weapon), dmg, 0, 1000);
   }
}
