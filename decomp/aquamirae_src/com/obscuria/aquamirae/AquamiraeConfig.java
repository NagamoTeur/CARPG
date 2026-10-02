package com.obscuria.aquamirae;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;

public class AquamiraeConfig {
   public static final double DEFAULT_CORNELIA_MAX_HEALTH = 200.0;
   public static final double DEFAULT_CORNELIA_ARMOR = 16.0;
   public static final double DEFAULT_CORNELIA_ATTACK_DAMAGE = 1.0;
   public static final double DEFAULT_CORNELIA_ATTACK_KNOCKBACK = 2.0;
   public static final double DEFAULT_CORNELIA_FOLLOW_RANGE = 128.0;
   public static final double DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE = 0.5;
   public static final double DEFAULT_CORNELIA_MOVEMENT_SPEED = 0.2;
   public static final int DEFAULT_CORNELIA_SKILL_USES = 2;
   public static final double DEFAULT_ANGLERFISH_MAX_HEALTH = 40.0;
   public static final double DEFAULT_ANGLERFISH_ARMOR = 2.0;
   public static final double DEFAULT_ANGLERFISH_ATTACK_DAMAGE = 6.0;
   public static final double DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK = 1.0;
   public static final double DEFAULT_ANGLERFISH_FOLLOW_RANGE = 48.0;
   public static final double DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE = 0.0;
   public static final double DEFAULT_ANGLERFISH_SWIM_SPEED = 3.0;
   public static final double DEFAULT_MAW_MAX_HEALTH = 20.0;
   public static final double DEFAULT_MAW_ARMOR = 0.0;
   public static final double DEFAULT_MAW_ATTACK_DAMAGE = 4.0;
   public static final double DEFAULT_MAW_ATTACK_KNOCKBACK = 0.3;
   public static final double DEFAULT_MAW_FOLLOW_RANGE = 24.0;
   public static final double DEFAULT_MAW_KNOCKBACK_RESISTANCE = 0.0;
   public static final double DEFAULT_MAW_SWIM_SPEED = 5.0;
   public static final double DEFAULT_MAW_MOVEMENT_SPEED = 0.2;
   public static final double DEFAULT_SOUL_MAX_HEALTH = 30.0;
   public static final double DEFAULT_SOUL_ARMOR = 4.0;
   public static final double DEFAULT_SOUL_ATTACK_DAMAGE = 7.0;
   public static final double DEFAULT_SOUL_ATTACK_KNOCKBACK = 0.7;
   public static final double DEFAULT_SOUL_FOLLOW_RANGE = 24.0;
   public static final double DEFAULT_SOUL_KNOCKBACK_RESISTANCE = 0.0;
   public static final double DEFAULT_SOUL_SWIM_SPEED = 3.0;
   public static final double DEFAULT_SOUL_MOVEMENT_SPEED = 0.2;
   public static final double DEFAULT_MOTHER_MAX_HEALTH = 100.0;
   public static final double DEFAULT_MOTHER_ARMOR = 6.0;
   public static final double DEFAULT_MOTHER_ATTACK_DAMAGE = 5.0;
   public static final double DEFAULT_MOTHER_ATTACK_KNOCKBACK = 0.5;
   public static final double DEFAULT_MOTHER_FOLLOW_RANGE = 128.0;
   public static final double DEFAULT_MOTHER_KNOCKBACK_RESISTANCE = 0.2;
   public static final double DEFAULT_MOTHER_SWIM_SPEED = 3.0;
   public static final double DEFAULT_EEL_MAX_HEALTH = 180.0;
   public static final double DEFAULT_EEL_ARMOR = 20.0;
   public static final double DEFAULT_EEL_ATTACK_DAMAGE = 8.0;
   public static final double DEFAULT_EEL_ATTACK_KNOCKBACK = 2.0;
   public static final double DEFAULT_EEL_FOLLOW_RANGE = 32.0;

   public static void register() {
      Path configPath = FMLPaths.CONFIGDIR.get();
      Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "Obscuria");

      try {
         Files.createDirectory(modConfigPath);
      } catch (FileAlreadyExistsException var3) {
      } catch (IOException var4) {
         Aquamirae.LOGGER.warn("Failed to create Obscuria config directory", var4);
      }

      ModLoadingContext.get().registerConfig(Type.COMMON, AquamiraeConfig.Common.COMMON_SPEC, "Obscuria/aquamirae-common.toml");
      ModLoadingContext.get().registerConfig(Type.CLIENT, AquamiraeConfig.Client.CLIENT_SPEC, "Obscuria/aquamirae-client.toml");
   }

   public static class Client {
      public static final Builder BUILDER = new Builder();
      public static final ForgeConfigSpec CLIENT_SPEC = BUILDER.build();
      public static final BooleanValue stylizedBossbar = BUILDER.worldRestart().define("stylizedBossbar", true);
      public static final BooleanValue particles = BUILDER.worldRestart().define("spawnParticles", true);
      public static final BooleanValue ambientSounds = BUILDER.worldRestart().define("playAmbientSounds", true);
      public static final BooleanValue biomeMusic = BUILDER.worldRestart().define("playBiomeMusic", true);
      public static final BooleanValue bossMusic = BUILDER.worldRestart().define("playCorneliaMusic", true);
      public static final BooleanValue overlay = BUILDER.worldRestart().define("renderThreeBoltHelmetOverlay", true);

      static {
         BUILDER.push("General");
         BUILDER.pop();
         BUILDER.push("IceMazeAmbient");
         BUILDER.pop();
      }
   }

   public static class Common {
      public static final Builder BUILDER = new Builder();
      public static final ForgeConfigSpec COMMON_SPEC = BUILDER.build();
      public static final BooleanValue notifications = BUILDER.worldRestart().define("chatNotifications", true);
      public static final DoubleValue corneliaMovementSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", 0.2, 0.0, 10.0);
      public static final DoubleValue corneliaMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 200.0, 1.0, 100000.0);
      public static final DoubleValue corneliaArmor = BUILDER.worldRestart().defineInRange("armor", 16.0, 0.0, 1000.0);
      public static final DoubleValue corneliaAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 1.0, 1.0, 1000.0);
      public static final DoubleValue corneliaKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", 0.5, 0.0, 10.0);
      public static final DoubleValue corneliaAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 2.0, 0.0, 10.0);
      public static final DoubleValue corneliaFollowRange = BUILDER.worldRestart().defineInRange("followRange", 128.0, 1.0, 256.0);
      public static final BooleanValue corneliaSpinAbility = BUILDER.worldRestart().define("pullAndSpinTargets", true);
      public static final IntValue corneliaRegenerationAbility = BUILDER.worldRestart().defineInRange("regenerationSkillUses", 2, 0, 1000);
      public static final DoubleValue eelMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 180.0, 1.0, 100000.0);
      public static final DoubleValue eelArmor = BUILDER.worldRestart().defineInRange("armor", 20.0, 0.0, 1000.0);
      public static final DoubleValue eelAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 8.0, 1.0, 1000.0);
      public static final DoubleValue eelAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 2.0, 0.0, 10.0);
      public static final DoubleValue eelFollowRange = BUILDER.worldRestart().defineInRange("followRange", 32.0, 1.0, 256.0);
      public static final DoubleValue anglerfishMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 40.0, 1.0, 100000.0);
      public static final DoubleValue anglerfishArmor = BUILDER.worldRestart().defineInRange("armor", 2.0, 0.0, 1000.0);
      public static final DoubleValue anglerfishAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 6.0, 1.0, 1000.0);
      public static final DoubleValue anglerfishAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 1.0, 0.0, 10.0);
      public static final DoubleValue anglerfishFollowRange = BUILDER.worldRestart().defineInRange("followRange", 48.0, 1.0, 256.0);
      public static final DoubleValue anglerfishKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", 0.0, 0.0, 10.0);
      public static final DoubleValue anglerfishSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", 3.0, 0.0, 100.0);
      public static final DoubleValue mawMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 20.0, 1.0, 100000.0);
      public static final DoubleValue mawArmor = BUILDER.worldRestart().defineInRange("armor", 0.0, 0.0, 1000.0);
      public static final DoubleValue mawAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 4.0, 1.0, 1000.0);
      public static final DoubleValue mawAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 0.3, 0.0, 10.0);
      public static final DoubleValue mawFollowRange = BUILDER.worldRestart().defineInRange("followRange", 24.0, 1.0, 256.0);
      public static final DoubleValue mawKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", 0.0, 0.0, 10.0);
      public static final DoubleValue mawSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", 5.0, 0.0, 100.0);
      public static final DoubleValue mawSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", 0.2, 0.0, 10.0);
      public static final DoubleValue soulMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 30.0, 1.0, 100000.0);
      public static final DoubleValue soulArmor = BUILDER.worldRestart().defineInRange("armor", 4.0, 0.0, 1000.0);
      public static final DoubleValue soulAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 7.0, 1.0, 1000.0);
      public static final DoubleValue soulAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 0.7, 0.0, 10.0);
      public static final DoubleValue soulFollowRange = BUILDER.worldRestart().defineInRange("followRange", 24.0, 1.0, 256.0);
      public static final DoubleValue soulKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", 0.0, 0.0, 10.0);
      public static final DoubleValue soulSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", 3.0, 0.0, 100.0);
      public static final DoubleValue soulSpeed = BUILDER.worldRestart().defineInRange("movementSpeed", 0.2, 0.0, 10.0);
      public static final DoubleValue motherMaxHealth = BUILDER.worldRestart().defineInRange("maxHealth", 100.0, 1.0, 100000.0);
      public static final DoubleValue motherArmor = BUILDER.worldRestart().defineInRange("armor", 6.0, 0.0, 1000.0);
      public static final DoubleValue motherAttackDamage = BUILDER.worldRestart().defineInRange("attackDamage", 5.0, 1.0, 1000.0);
      public static final DoubleValue motherAttackKnockback = BUILDER.worldRestart().defineInRange("attackKnockback", 0.5, 0.0, 10.0);
      public static final DoubleValue motherFollowRange = BUILDER.worldRestart().defineInRange("followRange", 128.0, 1.0, 256.0);
      public static final DoubleValue motherKnockbackResistance = BUILDER.worldRestart().defineInRange("knockbackResistance", 0.2, 0.0, 10.0);
      public static final DoubleValue motherSwimSpeed = BUILDER.worldRestart().defineInRange("swimSpeed", 3.0, 0.0, 100.0);

      static {
         BUILDER.push("General");
         BUILDER.pop();
         BUILDER.push("Mobs");
         BUILDER.push("GhostOfCaptainCornelia");
         BUILDER.pop();
         BUILDER.push("Anglerfish");
         BUILDER.pop();
         BUILDER.push("Maw");
         BUILDER.pop();
         BUILDER.push("TorturedSoul");
         BUILDER.pop();
         BUILDER.push("MotherOfTheMaze");
         BUILDER.pop();
         BUILDER.push("Eel");
         BUILDER.pop();
         BUILDER.pop();
      }
   }
}
