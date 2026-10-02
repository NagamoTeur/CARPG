package shadows.apotheosis.adventure;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.AffixManager;
import shadows.apotheosis.adventure.affix.reforging.ReforgingMenu;
import shadows.apotheosis.adventure.affix.reforging.ReforgingRecipe;
import shadows.apotheosis.adventure.affix.reforging.ReforgingTableBlock;
import shadows.apotheosis.adventure.affix.reforging.ReforgingTableTile;
import shadows.apotheosis.adventure.affix.salvaging.SalvageItem;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingMenu;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingRecipe;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingTableBlock;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingTableTile;
import shadows.apotheosis.adventure.affix.socket.AddSocketsRecipe;
import shadows.apotheosis.adventure.affix.socket.ExpulsionRecipe;
import shadows.apotheosis.adventure.affix.socket.ExtractionRecipe;
import shadows.apotheosis.adventure.affix.socket.SocketingRecipe;
import shadows.apotheosis.adventure.affix.socket.UnnamingRecipe;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingBlock;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingMenu;
import shadows.apotheosis.adventure.boss.BossArmorManager;
import shadows.apotheosis.adventure.boss.BossEvents;
import shadows.apotheosis.adventure.boss.BossItemManager;
import shadows.apotheosis.adventure.boss.BossSpawnerBlock;
import shadows.apotheosis.adventure.boss.BossSummonerItem;
import shadows.apotheosis.adventure.boss.Exclusion;
import shadows.apotheosis.adventure.boss.MinibossManager;
import shadows.apotheosis.adventure.client.AdventureModuleClient;
import shadows.apotheosis.adventure.compat.AdventureTOPPlugin;
import shadows.apotheosis.adventure.compat.AdventureTwilightCompat;
import shadows.apotheosis.adventure.compat.GatewaysCompat;
import shadows.apotheosis.adventure.gen.BossDungeonFeature;
import shadows.apotheosis.adventure.gen.BossDungeonFeature2;
import shadows.apotheosis.adventure.gen.ItemFrameGemsProcessor;
import shadows.apotheosis.adventure.gen.RogueSpawnerFeature;
import shadows.apotheosis.adventure.loot.AffixConvertLootModifier;
import shadows.apotheosis.adventure.loot.AffixHookLootModifier;
import shadows.apotheosis.adventure.loot.AffixLootManager;
import shadows.apotheosis.adventure.loot.AffixLootModifier;
import shadows.apotheosis.adventure.loot.AffixLootPoolEntry;
import shadows.apotheosis.adventure.loot.GemLootModifier;
import shadows.apotheosis.adventure.loot.GemLootPoolEntry;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.adventure.loot.LootRarityManager;
import shadows.apotheosis.adventure.spawner.RandomSpawnerManager;
import shadows.apotheosis.ench.objects.GlowyBlockItem;
import shadows.apotheosis.util.NameHelper;
import shadows.placebo.block_entity.TickingBlockEntityType;
import shadows.placebo.config.Configuration;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.loot.LootSystem;
import shadows.placebo.util.RegistryEvent.Register;

public class AdventureModule {
   public static final Logger LOGGER = LogManager.getLogger("Apotheosis : Adventure");
   public static final BiMap<LootRarity, Item> RARITY_MATERIALS = HashBiMap.create();
   public static final boolean STAGES_LOADED = ModList.get().isLoaded("gamestages");
   static final Map<ResourceLocation, LootCategory> IMC_TYPE_OVERRIDES = new HashMap<>();
   public static final StructureProcessorType<ItemFrameGemsProcessor> ITEM_FRAME_LOOT = () -> ItemFrameGemsProcessor.CODEC;
   public static final boolean DEBUG = false;

   @SubscribeEvent
   public void preInit(Apotheosis.ApotheosisConstruction e) {
      ObfuscationReflectionHelper.setPrivateValue(RangedAttribute.class, (RangedAttribute)Attributes.f_22284_, 200.0, "f_22308_");
      ObfuscationReflectionHelper.setPrivateValue(RangedAttribute.class, (RangedAttribute)Attributes.f_22285_, 100.0, "f_22308_");
   }

   @SubscribeEvent
   public void init(FMLCommonSetupEvent e) {
      this.reload(null);
      MinecraftForge.EVENT_BUS.register(new AdventureEvents());
      MinecraftForge.EVENT_BUS.register(new BossEvents());
      MinecraftForge.EVENT_BUS.addListener(this::reload);
      AffixManager.INSTANCE.registerToBus();
      GemManager.INSTANCE.registerToBus();
      AffixLootManager.INSTANCE.registerToBus();
      BossArmorManager.INSTANCE.registerToBus();
      BossItemManager.INSTANCE.registerToBus();
      RandomSpawnerManager.INSTANCE.registerToBus();
      LootRarityManager.INSTANCE.registerToBus();
      MinibossManager.INSTANCE.registerToBus();
      Apotheosis.HELPER.registerProvider(f -> {
         f.addRecipe(new SocketingRecipe());
         f.addRecipe(new ExpulsionRecipe());
         f.addRecipe(new ExtractionRecipe());
         f.addRecipe(new UnnamingRecipe());
      });
      e.enqueueWork(() -> {
         if (ModList.get().isLoaded("gateways")) {
            GatewaysCompat.register();
         }

         if (ModList.get().isLoaded("theoneprobe")) {
            AdventureTOPPlugin.register();
         }

         if (ModList.get().isLoaded("twilightforest")) {
            AdventureTwilightCompat.register();
         }

         LootSystem.defaultBlockTable((Block)Apoth.Blocks.SIMPLE_REFORGING_TABLE.get());
         LootSystem.defaultBlockTable((Block)Apoth.Blocks.REFORGING_TABLE.get());
         LootSystem.defaultBlockTable((Block)Apoth.Blocks.SALVAGING_TABLE.get());
         LootSystem.defaultBlockTable((Block)Apoth.Blocks.GEM_CUTTING_TABLE.get());
         AdventureGeneration.init();
         Registry.m_122965_(Registry.f_122875_, new ResourceLocation("apotheosis", "random_affix_item"), AffixLootPoolEntry.TYPE);
         Registry.m_122965_(Registry.f_122875_, new ResourceLocation("apotheosis", "random_gem"), GemLootPoolEntry.TYPE);
         Exclusion.initSerializers();
         GemBonus.initCodecs();
         MobEffects.f_19610_.m_19472_(Attributes.f_22277_, "f8c3de3d-1fea-4d7c-a8b0-22f63c4c3454", -0.75, Operation.MULTIPLY_TOTAL);
      });
   }

   @SubscribeEvent
   public void register(Register<Feature<?>> e) {
      e.getRegistry().register(BossDungeonFeature.INSTANCE, "boss_dng");
      e.getRegistry().register(BossDungeonFeature2.INSTANCE, "boss_dng_2");
      e.getRegistry().register(RogueSpawnerFeature.INSTANCE, "rogue_spawner");
      MinecraftForge.EVENT_BUS.register(AdventureGeneration.class);
      Registry.m_122961_(Registry.f_122891_, "apotheosis:item_frame_gems", ITEM_FRAME_LOOT);
      Registry.m_122961_(
         BuiltinRegistries.f_123863_,
         "apotheosis:item_frame_gems",
         new StructureProcessorList(ImmutableList.of(new ItemFrameGemsProcessor(new ResourceLocation("a"))))
      );
   }

   @SubscribeEvent
   public void items(Register<Item> e) {
      e.getRegistry().register(new GemItem(new Properties()), "gem");
      e.getRegistry().register(new BossSummonerItem(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "boss_summoner");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "gem_dust");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "vial_of_extraction");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "vial_of_expulsion");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "vial_of_unnaming");

      for (LootRarity r : LootRarity.values()) {
         Item material = new SalvageItem(r, new Properties().m_41491_(Apotheosis.APOTH_GROUP));
         e.getRegistry().register(material, r.id() + "_material");
         RARITY_MATERIALS.put(r, material);
      }

      e.getRegistry()
         .register(new BlockItem((Block)Apoth.Blocks.SIMPLE_REFORGING_TABLE.get(), new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "simple_reforging_table");
      e.getRegistry().register(new BlockItem((Block)Apoth.Blocks.REFORGING_TABLE.get(), new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "reforging_table");
      e.getRegistry().register(new BlockItem((Block)Apoth.Blocks.SALVAGING_TABLE.get(), new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "salvaging_table");
      e.getRegistry()
         .register(new BlockItem((Block)Apoth.Blocks.GEM_CUTTING_TABLE.get(), new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "gem_cutting_table");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "sigil_of_socketing");
      e.getRegistry().register(new GlowyBlockItem.GlowyItem(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "superior_sigil_of_socketing");
      e.getRegistry().register(new Item(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "sigil_of_enhancement");
      e.getRegistry().register(new GlowyBlockItem.GlowyItem(new Properties().m_41491_(Apotheosis.APOTH_GROUP)), "superior_sigil_of_enhancement");
   }

   @SubscribeEvent
   public void blocks(Register<Block> e) {
      e.getRegistry()
         .register(
            new BossSpawnerBlock(
               net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60913_(-1.0F, 3600000.0F).m_222994_()
            ),
            "boss_spawner"
         );
      e.getRegistry()
         .register(
            new ReforgingTableBlock(
               net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60999_().m_60913_(2.0F, 20.0F), LootRarity.RARE
            ),
            "simple_reforging_table"
         );
      e.getRegistry()
         .register(
            new ReforgingTableBlock(
               net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60999_().m_60913_(4.0F, 1000.0F),
               LootRarity.MYTHIC
            ),
            "reforging_table"
         );
      e.getRegistry()
         .register(
            new SalvagingTableBlock(
               net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60978_(2.5F)
            ),
            "salvaging_table"
         );
      e.getRegistry()
         .register(
            new GemCuttingBlock(
               net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60978_(2.5F)
            ),
            "gem_cutting_table"
         );
   }

   @SubscribeEvent
   public void tiles(Register<BlockEntityType<?>> e) {
      e.getRegistry()
         .register(
            new TickingBlockEntityType(BossSpawnerBlock.BossSpawnerTile::new, ImmutableSet.of((Block)Apoth.Blocks.BOSS_SPAWNER.get()), false, true),
            "boss_spawner"
         );
      e.getRegistry()
         .register(
            new TickingBlockEntityType(
               ReforgingTableTile::new,
               ImmutableSet.of((Block)Apoth.Blocks.SIMPLE_REFORGING_TABLE.get(), (Block)Apoth.Blocks.REFORGING_TABLE.get()),
               true,
               false
            ),
            "reforging_table"
         );
      e.getRegistry()
         .register(new BlockEntityType(SalvagingTableTile::new, ImmutableSet.of((Block)Apoth.Blocks.SALVAGING_TABLE.get()), null), "salvaging_table");
   }

   @SubscribeEvent
   public void serializers(Register<RecipeSerializer<?>> e) {
      e.getRegistry().register(SocketingRecipe.Serializer.INSTANCE, "socketing");
      e.getRegistry().register(ExpulsionRecipe.Serializer.INSTANCE, "expulsion");
      e.getRegistry().register(ExtractionRecipe.Serializer.INSTANCE, "extraction");
      e.getRegistry().register(UnnamingRecipe.Serializer.INSTANCE, "unnaming");
      e.getRegistry().register(AddSocketsRecipe.Serializer.INSTANCE, "add_sockets");
      e.getRegistry().register(SalvagingRecipe.Serializer.INSTANCE, "salvaging");
      e.getRegistry().register(ReforgingRecipe.Serializer.INSTANCE, "reforging");
   }

   @SubscribeEvent
   public void miscRegistration(RegisterEvent e) {
      if (e.getForgeRegistry() == ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get()) {
         e.getForgeRegistry().register("gems", GemLootModifier.CODEC);
         e.getForgeRegistry().register("affix_loot", AffixLootModifier.CODEC);
         e.getForgeRegistry().register("affix_conversion", AffixConvertLootModifier.CODEC);
         e.getForgeRegistry().register("affix_hook", AffixHookLootModifier.CODEC);
      }

      if (e.getForgeRegistry() == ForgeRegistries.BIOME_MODIFIER_SERIALIZERS.get()) {
         e.getForgeRegistry().register("blacklist", AdventureGeneration.BlacklistModifier.CODEC);
      }
   }

   @SubscribeEvent
   public void containers(Register<MenuType<?>> e) {
      e.getRegistry().register(ContainerUtil.makeType(ReforgingMenu::new), "reforging");
      e.getRegistry().register(ContainerUtil.makeType(SalvagingMenu::new), "salvage");
      e.getRegistry().register(new MenuType(GemCuttingMenu::new), "gem_cutting");
   }

   @SubscribeEvent
   public void client(FMLClientSetupEvent e) {
      e.enqueueWork(AdventureModuleClient::init);
      FMLJavaModLoadingContext.get().getModEventBus().register(new AdventureModuleClient());
   }

   @SubscribeEvent
   public void imc(InterModProcessEvent e) {
      e.getIMCStream().forEach(msg -> {
         String var1x = msg.method().toLowerCase(Locale.ROOT);
         byte var2 = -1;
         switch (var1x.hashCode()) {
            case 2104206358:
               if (var1x.equals("loot_category_override")) {
                  var2 = 0;
               }
            default:
               switch (var2) {
                  case 0:
                     try {
                        Entry<Item, String> categoryOverride = (Entry<Item, String>)msg.messageSupplier().get();
                        ResourceLocation item = ForgeRegistries.ITEMS.getKey(categoryOverride.getKey());
                        LootCategory cat = LootCategory.byId(categoryOverride.getValue());
                        if (cat == null) {
                           throw new NullPointerException("Invalid loot category ID: " + categoryOverride.getValue());
                        }

                        IMC_TYPE_OVERRIDES.put(item, cat);
                        LOGGER.info("Mod {} has overriden the loot category of {} to {}.", msg.senderModId(), item, cat.getName());
                     } catch (Exception var6) {
                        LOGGER.error(var6.getMessage());
                        var6.printStackTrace();
                     }
                     break;
                  default:
                     LOGGER.error("Unknown or invalid IMC Message: {}", msg);
               }
         }
      });
   }

   public void reload(Apotheosis.ApotheosisReloadEvent e) {
      Configuration mainConfig = new Configuration(new File(Apotheosis.configDir, "adventure.cfg"));
      Configuration nameConfig = new Configuration(new File(Apotheosis.configDir, "names.cfg"));
      AdventureConfig.load(mainConfig);
      NameHelper.load(nameConfig);
      if (e == null && mainConfig.hasChanged()) {
         mainConfig.save();
      }

      if (e == null && nameConfig.hasChanged()) {
         nameConfig.save();
      }
   }

   public static void debugLog(BlockPos pos, String name) {
   }

   public static class ApothUpgradeRecipe extends UpgradeRecipe {
      public ApothUpgradeRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
         super(pId, pBase, pAddition, pResult);
      }
   }
}
