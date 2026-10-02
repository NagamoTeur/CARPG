package com.rolfmao.upgradednetherite_items.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {
   public static void bakeClient(ModConfig config) {
      UpgradedNetheriteItemsConfig.DisableTooltips = (Boolean)ConfigHolder.CLIENT.DisableTooltips.get();
   }

   public static void bakeServer(ModConfig config) {
      UpgradedNetheriteItemsConfig.EnableReduceDamageGoldTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageGoldTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageGoldTotem = (Integer)ConfigHolder.SERVER.ReduceDamageGoldTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageFireTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageFireTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageFireTotem = (Integer)ConfigHolder.SERVER.ReduceDamageFireTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageEnderTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageEnderTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageEnderTotem = (Integer)ConfigHolder.SERVER.ReduceDamageEnderTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageWaterTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageWaterTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageWaterTotem = (Integer)ConfigHolder.SERVER.ReduceDamageWaterTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageWitherTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageWitherTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageWitherTotem = (Integer)ConfigHolder.SERVER.ReduceDamageWitherTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamagePoisonTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamagePoisonTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamagePoisonTotem = (Integer)ConfigHolder.SERVER.ReduceDamagePoisonTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamagePhantomTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamagePhantomTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamagePhantomTotem = (Integer)ConfigHolder.SERVER.ReduceDamagePhantomTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageFeatherTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageFeatherTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageFeatherTotem = (Integer)ConfigHolder.SERVER.ReduceDamageFeatherTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageCorruptTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageCorruptTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageCorruptTotem = (Integer)ConfigHolder.SERVER.ReduceDamageCorruptTotem.get();
      UpgradedNetheriteItemsConfig.EnableReduceDamageEchoTotem = (Boolean)ConfigHolder.SERVER.EnableReduceDamageEchoTotem.get();
      UpgradedNetheriteItemsConfig.ReduceDamageEchoTotem = (Integer)ConfigHolder.SERVER.ReduceDamageEchoTotem.get();
   }
}
