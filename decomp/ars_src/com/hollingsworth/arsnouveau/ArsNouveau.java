package com.hollingsworth.arsnouveau;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.registry.CasterTomeRegistry;
import com.hollingsworth.arsnouveau.api.ritual.DispenserRitualBehavior;
import com.hollingsworth.arsnouveau.client.container.CraftingTerminalScreen;
import com.hollingsworth.arsnouveau.client.events.ClientHandler;
import com.hollingsworth.arsnouveau.client.events.TextureEvent;
import com.hollingsworth.arsnouveau.client.gui.book.BaseBook;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.ClientEventHandler;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.FMLEventHandler;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.Pathfinding;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.menu.MenuRegistry;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.world.Terrablender;
import com.hollingsworth.arsnouveau.setup.APIRegistry;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ClientProxy;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.IProxy;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.ModSetup;
import com.hollingsworth.arsnouveau.setup.ServerProxy;
import com.hollingsworth.arsnouveau.setup.config.ANModConfig;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import com.hollingsworth.arsnouveau.setup.reward.Rewards;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("ars_nouveau")
@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class ArsNouveau {
   public static final String MODID = "ars_nouveau";
   public static IProxy proxy = (IProxy)DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
   public static boolean caelusLoaded = false;
   public static boolean terrablenderLoaded = false;
   public static boolean optifineLoaded = false;
   public static CreativeModeTab itemGroup = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "ars_nouveau") {
      public ItemStack m_6976_() {
         return ItemsRegistry.CREATIVE_SPELLBOOK.get().m_7968_();
      }
   };
   public static CreativeModeTab glyphGroup = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "ars_glyphs") {
      public void m_6151_(NonNullList<ItemStack> pItems) {
         super.m_6151_(pItems);
         pItems.sort((i1, i2) -> {
            if (i1.m_41720_() instanceof Glyph g1 && i2.m_41720_() instanceof Glyph g2) {
               return BaseBook.COMPARE_TYPE_THEN_NAME.compare(g1.spellPart, g2.spellPart);
            }

            return -1;
         });
      }

      public ItemStack m_6976_() {
         return ArsNouveauAPI.getInstance().getGlyphItem(MethodProjectile.INSTANCE).m_7968_();
      }
   };

   public ArsNouveau() {
      ((IEventBus)Bus.FORGE.bus().get()).register(FMLEventHandler.class);
      caelusLoaded = ModList.get().isLoaded("caelus");
      terrablenderLoaded = ModList.get().isLoaded("terrablender");
      APIRegistry.setup();
      ANModConfig serverConfig = new ANModConfig(Type.SERVER, ServerConfig.SERVER_CONFIG, ModLoadingContext.get().getActiveContainer(), "ars_nouveau-server");
      ModLoadingContext.get().getActiveContainer().addConfig(serverConfig);
      ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_CONFIG);
      ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_CONFIG);
      DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ((IEventBus)Bus.FORGE.bus().get()).register(ClientEventHandler.class));
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      modEventBus.addListener(ModSetup::registerEvents);
      ModSetup.registers(modEventBus);
      modEventBus.addListener(this::setup);
      modEventBus.addListener(this::postModLoadEvent);
      modEventBus.addListener(this::clientSetup);
      modEventBus.addListener(this::sendImc);
      MinecraftForge.EVENT_BUS.register(this);
      ModSetup.initGeckolib();
      ANCriteriaTriggers.init();

      try {
         Thread thread = new Thread(Rewards::init);
         thread.setDaemon(true);
         thread.start();
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public void setup(FMLCommonSetupEvent event) {
      APIRegistry.postInit();
      Networking.registerMessages();
      event.enqueueWork(ModPotions::addRecipes);
      event.enqueueWork(ModEntities::registerPlacements);
      if (terrablenderLoaded && (Integer)Config.ARCHWOOD_FOREST_WEIGHT.get() > 0) {
         event.enqueueWork(Terrablender::registerBiomes);
      }

      MinecraftForge.EVENT_BUS.addListener(e -> CasterTomeRegistry.reloadTomeData(e.getServer().m_129894_()));
   }

   public void postModLoadEvent(FMLLoadCompleteEvent event) {
      event.enqueueWork(() -> {
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.BLAZING_SAPLING.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.CASCADING_SAPLING.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.FLOURISHING_SAPLING.m_5456_(), 0.5F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.VEXING_SAPLING.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.SOURCEBERRY_BUSH.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent((ItemLike)ItemsRegistry.MAGE_BLOOM.get(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.MAGE_BLOOM_CROP.m_5456_(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.BOMBEGRANTE_POD.m_5456_(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.MENDOSTEEN_POD.m_5456_(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.FROSTAYA_POD.m_5456_(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.BASTION_POD.m_5456_(), 0.65F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.FLOURISHING_LEAVES.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.VEXING_LEAVES.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.CASCADING_LEAVE.m_5456_(), 0.3F);
         ComposterBlock.f_51914_.putIfAbsent(BlockRegistry.BLAZING_LEAVES.m_5456_(), 0.3F);
         FlowerPotBlock flowerPot = (FlowerPotBlock)Blocks.f_50276_;

         for (Entry<Supplier<ResourceLocation>, FlowerPotBlock> pot : BlockRegistry.flowerPots.entrySet()) {
            flowerPot.addPlant(pot.getKey().get(), pot::getValue);
         }

         for (RitualTablet tablet : ArsNouveauAPI.getInstance().getRitualItemMap().values()) {
            DispenserBlock.m_52672_(tablet, new DispenserRitualBehavior());
         }
      });
   }

   public void clientSetup(FMLClientSetupEvent event) {
      FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::init);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(TextureEvent::textEvent);
      event.enqueueWork(() -> MenuScreens.m_96206_((MenuType)MenuRegistry.STORAGE.get(), CraftingTerminalScreen::new));

      try {
         Class.forName("net.optifine.Config");
         optifineLoaded = true;
      } catch (Exception var3) {
         optifineLoaded = false;
      }
   }

   public void sendImc(InterModEnqueueEvent evt) {
      ModSetup.sendIntercoms();
   }

   @SubscribeEvent
   public static void onServerStopped(ServerStoppingEvent event) {
      Pathfinding.shutdown();
   }
}
