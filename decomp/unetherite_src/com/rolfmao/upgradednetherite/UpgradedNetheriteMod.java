package com.rolfmao.upgradednetherite;

import com.mojang.logging.LogUtils;
import com.rolfmao.upgradedcore.client.BowModel;
import com.rolfmao.upgradedcore.client.CrossBowModel;
import com.rolfmao.upgradednetherite.config.ConfigHolder;
import com.rolfmao.upgradednetherite.handlers.ArmorEventHandler;
import com.rolfmao.upgradednetherite.handlers.EventHandler;
import com.rolfmao.upgradednetherite.handlers.HorseArmorEventHandler;
import com.rolfmao.upgradednetherite.handlers.SoulboundEventHandler;
import com.rolfmao.upgradednetherite.handlers.ToolEventHandler;
import com.rolfmao.upgradednetherite.handlers.WeaponEventHandler;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.init.UpgradedNetheriteEffects;
import com.rolfmao.upgradednetherite.modifiers.GlobalLootModifiers;
import com.rolfmao.upgradednetherite.packets.PacketEntityFallDistanceUpdate;
import com.rolfmao.upgradednetherite.packets.PacketPlayerFallDistanceUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

@Mod("upgradednetherite")
public class UpgradedNetheriteMod {
   public static final Logger LOGGER = LogUtils.getLogger();
   public static final String MOD_ID = "upgradednetherite";
   public static final CreativeModeTab TAB = new CreativeModeTab("upgradednetheriteTab") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)ModItems.FIRE_UPGRADED_NETHERITE_CHESTPLATE.get());
      }
   };
   public static SimpleChannel packetInstance;

   public UpgradedNetheriteMod() {
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
      UpgradedNetheriteEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
      ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
      GlobalLootModifiers.GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
      ModEventSubscriber.create(FMLJavaModLoadingContext.get().getModEventBus());
      MinecraftForge.EVENT_BUS.register(this);
      MinecraftForge.EVENT_BUS.register(new EventHandler());
      MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
      MinecraftForge.EVENT_BUS.register(new ToolEventHandler());
      MinecraftForge.EVENT_BUS.register(new WeaponEventHandler());
      MinecraftForge.EVENT_BUS.register(new HorseArmorEventHandler());
      MinecraftForge.EVENT_BUS.register(new SoulboundEventHandler());
      ModLoadingContext.get().registerConfig(Type.CLIENT, ConfigHolder.CLIENT_SPEC);
      ModLoadingContext.get().registerConfig(Type.SERVER, ConfigHolder.SERVER_SPEC);
   }

   private void setup(FMLCommonSetupEvent event) {
      packetInstance = ChannelBuilder.named(new ResourceLocation("upgradednetherite", "main"))
         .networkProtocolVersion(() -> "1")
         .clientAcceptedVersions("1"::equals)
         .serverAcceptedVersions("1"::equals)
         .simpleChannel();
      packetInstance.registerMessage(
         1,
         PacketPlayerFallDistanceUpdate.class,
         PacketPlayerFallDistanceUpdate::encode,
         PacketPlayerFallDistanceUpdate::decode,
         PacketPlayerFallDistanceUpdate::handle
      );
      packetInstance.registerMessage(
         2,
         PacketEntityFallDistanceUpdate.class,
         PacketEntityFallDistanceUpdate::encode,
         PacketEntityFallDistanceUpdate::decode,
         PacketEntityFallDistanceUpdate::handle
      );
   }

   private void doClientStuff(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         BowModel.setupBowModelProperties((Item)ModItems.NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.GOLD_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.FIRE_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.ENDER_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.WATER_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.WITHER_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.POISON_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.FEATHER_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get());
         BowModel.setupBowModelProperties((Item)ModItems.ECHO_UPGRADED_NETHERITE_BOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.GOLD_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.FIRE_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.ENDER_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.WATER_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.WITHER_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.POISON_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.FEATHER_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get());
         CrossBowModel.setupCrossBowModelProperties((Item)ModItems.ECHO_UPGRADED_NETHERITE_CROSSBOW.get());
      });
   }
}
