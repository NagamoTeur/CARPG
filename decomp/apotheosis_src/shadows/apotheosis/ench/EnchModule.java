package shadows.apotheosis.ench;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ProtectionEnchantment.Type;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.anvil.AnvilTile;
import shadows.apotheosis.ench.anvil.ApothAnvilBlock;
import shadows.apotheosis.ench.anvil.ApothAnvilItem;
import shadows.apotheosis.ench.anvil.ObliterationEnchant;
import shadows.apotheosis.ench.anvil.SplittingEnchant;
import shadows.apotheosis.ench.compat.EnchTOPPlugin;
import shadows.apotheosis.ench.enchantments.ChromaticEnchant;
import shadows.apotheosis.ench.enchantments.IcyThornsEnchant;
import shadows.apotheosis.ench.enchantments.InertEnchantment;
import shadows.apotheosis.ench.enchantments.NaturesBlessingEnchant;
import shadows.apotheosis.ench.enchantments.ReboundingEnchant;
import shadows.apotheosis.ench.enchantments.ReflectiveEnchant;
import shadows.apotheosis.ench.enchantments.ShieldBashEnchant;
import shadows.apotheosis.ench.enchantments.SpearfishingEnchant;
import shadows.apotheosis.ench.enchantments.StableFootingEnchant;
import shadows.apotheosis.ench.enchantments.TemptingEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.BerserkersFuryEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.LifeMendingEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ChainsawEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.CrescendoEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.EarthsBoonEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.EndlessQuiverEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.GrowthSerumEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.KnowledgeEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ScavengerEnchant;
import shadows.apotheosis.ench.enchantments.twisted.ExploitationEnchant;
import shadows.apotheosis.ench.enchantments.twisted.MinersFervorEnchant;
import shadows.apotheosis.ench.library.EnchLibraryBlock;
import shadows.apotheosis.ench.library.EnchLibraryContainer;
import shadows.apotheosis.ench.library.EnchLibraryTile;
import shadows.apotheosis.ench.objects.ExtractionTomeItem;
import shadows.apotheosis.ench.objects.GlowyBlockItem;
import shadows.apotheosis.ench.objects.ImprovedScrappingTomeItem;
import shadows.apotheosis.ench.objects.ScrappingTomeItem;
import shadows.apotheosis.ench.objects.TomeItem;
import shadows.apotheosis.ench.objects.TypedShelfBlock;
import shadows.apotheosis.ench.objects.WardenLootModifier;
import shadows.apotheosis.ench.replacements.BaneEnchant;
import shadows.apotheosis.ench.replacements.DefenseEnchant;
import shadows.apotheosis.ench.table.ApothEnchantBlock;
import shadows.apotheosis.ench.table.ApothEnchantTile;
import shadows.apotheosis.ench.table.ApothEnchantmentMenu;
import shadows.apotheosis.ench.table.EnchantingRecipe;
import shadows.apotheosis.ench.table.EnchantingStatManager;
import shadows.apotheosis.ench.table.KeepNBTEnchantingRecipe;
import shadows.apotheosis.util.ApothMiscUtil;
import shadows.placebo.color.GradientColor;
import shadows.placebo.config.Configuration;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.loot.LootSystem;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.RegistryEvent.Register;

public class EnchModule {
   public static final Map<Enchantment, EnchantmentInfo> ENCHANTMENT_INFO = new HashMap<>();
   public static final Object2IntMap<Enchantment> ENCH_HARD_CAPS = new Object2IntOpenHashMap();
   public static final String ENCH_HARD_CAP_IMC = "set_ench_hard_cap";
   public static final Logger LOGGER = LogManager.getLogger("Apotheosis : Enchantment");
   public static final List<TomeItem> TYPED_BOOKS = new ArrayList<>();
   public static final EquipmentSlot[] ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   public static final EnchantmentCategory HOE = EnchantmentCategory.create("HOE", i -> i instanceof HoeItem);
   public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("SHIELD", i -> i instanceof ShieldItem);
   public static final EnchantmentCategory ANVIL = EnchantmentCategory.create(
      "ANVIL", i -> i instanceof BlockItem && ((BlockItem)i).m_40614_() instanceof AnvilBlock
   );
   public static final EnchantmentCategory SHEARS = EnchantmentCategory.create("SHEARS", i -> i instanceof ShearsItem);
   public static final EnchantmentCategory PICKAXE = EnchantmentCategory.create("PICKAXE", i -> i.canPerformAction(new ItemStack(i), ToolActions.PICKAXE_DIG));
   public static final EnchantmentCategory AXE = EnchantmentCategory.create("AXE", i -> i.canPerformAction(new ItemStack(i), ToolActions.AXE_DIG));
   public static final EnchantmentCategory CORE_ARMOR = EnchantmentCategory.create(
      "CORE_ARMOR", i -> EnchantmentCategory.ARMOR_CHEST.m_7454_(i) || EnchantmentCategory.ARMOR_LEGS.m_7454_(i)
   );
   static Configuration enchInfoConfig;

   public EnchModule() {
      if (FMLEnvironment.dist.isClient()) {
         FMLJavaModLoadingContext.get().getModEventBus().register(EnchModuleClient.class);
      }
   }

   @SubscribeEvent
   public void init(FMLCommonSetupEvent e) {
      this.reload(null);
      Apotheosis.HELPER
         .registerProvider(
            factory -> {
               Ingredient pot = Apotheosis.potionIngredient(Potions.f_43587_);
               factory.addShaped(
                  Apoth.Blocks.HELLSHELF.get(),
                  3,
                  3,
                  new Object[]{
                     Blocks.f_50197_,
                     Blocks.f_50197_,
                     Blocks.f_50197_,
                     Items.f_42585_,
                     "forge:bookshelves",
                     pot,
                     Blocks.f_50197_,
                     Blocks.f_50197_,
                     Blocks.f_50197_
                  }
               );
               factory.addShaped(
                  Apoth.Items.PRISMATIC_WEB,
                  3,
                  3,
                  new Object[]{null, Items.f_42695_, null, Items.f_42695_, Blocks.f_50033_, Items.f_42695_, null, Items.f_42695_, null}
               );
               ItemStack book = new ItemStack(Items.f_42517_);
               ItemStack stick = new ItemStack(Items.f_42398_);
               ItemStack blaze = new ItemStack(Items.f_42585_);
               factory.addShaped(new ItemStack((ItemLike)Apoth.Items.HELMET_TOME.get(), 5), 3, 2, new Object[]{book, book, book, book, blaze, book});
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.CHESTPLATE_TOME.get(), 8), 3, 3, new Object[]{book, blaze, book, book, book, book, book, book, book}
               );
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.LEGGINGS_TOME.get(), 7), 3, 3, new Object[]{book, null, book, book, blaze, book, book, book, book}
               );
               factory.addShaped(new ItemStack((ItemLike)Apoth.Items.BOOTS_TOME.get(), 4), 3, 2, new Object[]{book, null, book, book, blaze, book});
               factory.addShaped(new ItemStack((ItemLike)Apoth.Items.WEAPON_TOME.get(), 2), 1, 3, new Object[]{book, book, new ItemStack(Items.f_42593_)});
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.PICKAXE_TOME.get(), 3), 3, 3, new Object[]{book, book, book, null, blaze, null, null, stick, null}
               );
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.FISHING_TOME.get(), 2), 3, 3, new Object[]{null, null, blaze, null, stick, book, stick, null, book}
               );
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.BOW_TOME.get(), 3), 3, 3, new Object[]{null, stick, book, blaze, null, book, null, stick, book}
               );
               factory.addShapeless(new ItemStack((ItemLike)Apoth.Items.OTHER_TOME.get(), 6), new Object[]{book, book, book, book, book, book, blaze});
               factory.addShaped(
                  new ItemStack((ItemLike)Apoth.Items.SCRAP_TOME.get(), 8), 3, 3, new Object[]{book, book, book, book, Blocks.f_50322_, book, book, book, book}
               );
               Ingredient maxHellshelf = Ingredient.m_43929_(new ItemLike[]{(ItemLike)Apoth.Blocks.INFUSED_HELLSHELF.get()});
               factory.addShaped(
                  Apoth.Blocks.BLAZING_HELLSHELF.get(),
                  3,
                  3,
                  new Object[]{null, Items.f_42613_, null, Items.f_42613_, maxHellshelf, Items.f_42613_, Items.f_42593_, Items.f_42593_, Items.f_42593_}
               );
               factory.addShaped(
                  Apoth.Blocks.GLOWING_HELLSHELF.get(),
                  3,
                  3,
                  new Object[]{null, Blocks.f_50141_, null, null, maxHellshelf, null, Blocks.f_50141_, null, Blocks.f_50141_}
               );
               factory.addShaped(
                  Apoth.Blocks.SEASHELF.get(),
                  3,
                  3,
                  new Object[]{
                     Blocks.f_50378_,
                     Blocks.f_50378_,
                     Blocks.f_50378_,
                     Apotheosis.potionIngredient(Potions.f_43599_),
                     "forge:bookshelves",
                     Items.f_42529_,
                     Blocks.f_50378_,
                     Blocks.f_50378_,
                     Blocks.f_50378_
                  }
               );
               Ingredient maxSeashelf = Ingredient.m_43929_(new ItemLike[]{(ItemLike)Apoth.Blocks.INFUSED_SEASHELF.get()});
               factory.addShaped(
                  Apoth.Blocks.CRYSTAL_SEASHELF.get(),
                  3,
                  3,
                  new Object[]{null, Items.f_42696_, null, null, maxSeashelf, null, Items.f_42696_, null, Items.f_42696_}
               );
               factory.addShaped(
                  Apoth.Blocks.HEART_SEASHELF.get(),
                  3,
                  3,
                  new Object[]{null, Items.f_42716_, null, Items.f_42695_, maxSeashelf, Items.f_42695_, Items.f_42695_, Items.f_42695_, Items.f_42695_}
               );
               factory.addShaped(
                  Apoth.Blocks.PEARL_ENDSHELF.get(),
                  3,
                  3,
                  new Object[]{
                     Items.f_42001_, null, Items.f_42001_, Items.f_42584_, Apoth.Blocks.ENDSHELF.get(), Items.f_42584_, Items.f_42001_, null, Items.f_42001_
                  }
               );
               factory.addShaped(
                  Apoth.Blocks.DRACONIC_ENDSHELF.get(),
                  3,
                  3,
                  new Object[]{
                     null, Items.f_42683_, null, Items.f_42584_, Apoth.Blocks.ENDSHELF.get(), Items.f_42584_, Items.f_42584_, Items.f_42584_, Items.f_42584_
                  }
               );
               factory.addShaped(
                  Apoth.Blocks.BEESHELF.get(),
                  3,
                  3,
                  new Object[]{
                     Items.f_42784_,
                     Items.f_42786_,
                     Items.f_42784_,
                     Items.f_42788_,
                     "forge:bookshelves",
                     Items.f_42788_,
                     Items.f_42784_,
                     Items.f_42786_,
                     Items.f_42784_
                  }
               );
               factory.addShaped(
                  Apoth.Blocks.MELONSHELF.get(),
                  3,
                  3,
                  new Object[]{
                     Items.f_42028_,
                     Items.f_42028_,
                     Items.f_42028_,
                     Items.f_42546_,
                     "forge:bookshelves",
                     Items.f_42546_,
                     Items.f_42028_,
                     Items.f_42028_,
                     Items.f_42028_
                  }
               );
            }
         );
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.HELLSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.INFUSED_HELLSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.BLAZING_HELLSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.GLOWING_HELLSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.SEASHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.INFUSED_SEASHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.CRYSTAL_SEASHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.HEART_SEASHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.DORMANT_DEEPSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.DEEPSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.ECHOING_DEEPSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.SOUL_TOUCHED_DEEPSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.ECHOING_SCULKSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.SOUL_TOUCHED_SCULKSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.ENDSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.PEARL_ENDSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.DRACONIC_ENDSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.BEESHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.MELONSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.STONESHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.LIBRARY.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.RECTIFIER.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.RECTIFIER_T2.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.RECTIFIER_T3.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.SIGHTSHELF.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.SIGHTSHELF_T2.get());
      LootSystem.defaultBlockTable((Block)Apoth.Blocks.ENDER_LIBRARY.get());
      MinecraftForge.EVENT_BUS.register(new EnchModuleEvents());
      MinecraftForge.EVENT_BUS.addListener(this::reload);
      e.enqueueWork(() -> DispenserBlock.m_52672_(Items.f_42574_, new ShearsDispenseItemBehavior()));
      if (ModList.get().isLoaded("theoneprobe")) {
         EnchTOPPlugin.register();
      }

      EnchantingStatManager.INSTANCE.registerToBus();
      PlaceboUtil.registerCustomColor(EnchModule.Colors.LIGHT_BLUE_FLASH);
   }

   @SubscribeEvent
   public void client(FMLClientSetupEvent e) {
      MinecraftForge.EVENT_BUS.register(new EnchModuleClient());
      e.enqueueWork(EnchModuleClient::init);
   }

   @SubscribeEvent
   public void miscRegistration(RegisterEvent e) {
      if (e.getForgeRegistry() == ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get()) {
         e.getForgeRegistry().register("warden_tendril", WardenLootModifier.CODEC);
      }
   }

   @SubscribeEvent
   public void tiles(Register<BlockEntityType<?>> e) {
      e.getRegistry().register(new BlockEntityType(AnvilTile::new, ImmutableSet.of(Blocks.f_50322_, Blocks.f_50323_, Blocks.f_50324_), null), "anvil");
      BlockEntityType.f_58928_.f_58914_ = ApothEnchantTile::new;
      BlockEntityType.f_58928_.f_58915_ = ImmutableSet.of(Blocks.f_50201_);
      e.getRegistry().register(new BlockEntityType(EnchLibraryTile.BasicLibraryTile::new, ImmutableSet.of((Block)Apoth.Blocks.LIBRARY.get()), null), "library");
      e.getRegistry()
         .register(new BlockEntityType(EnchLibraryTile.EnderLibraryTile::new, ImmutableSet.of((Block)Apoth.Blocks.ENDER_LIBRARY.get()), null), "ender_library");
   }

   @SubscribeEvent
   public void containers(Register<MenuType<?>> e) {
      e.getRegistry().register(new MenuType(ApothEnchantmentMenu::new), "enchanting_table");
      e.getRegistry().register(ContainerUtil.makeType(EnchLibraryContainer::new), "library");
   }

   @SubscribeEvent
   public void recipeSerializers(Register<RecipeSerializer<?>> e) {
      e.getRegistry().register(EnchantingRecipe.SERIALIZER, "enchanting");
      e.getRegistry().register(KeepNBTEnchantingRecipe.SERIALIZER, "keep_nbt_enchanting");
   }

   @SubscribeEvent
   public void particles(Register<ParticleType<?>> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new SimpleParticleType(false),
               "enchant_fire",
               new SimpleParticleType(false),
               "enchant_water",
               new SimpleParticleType(false),
               "enchant_sculk",
               new SimpleParticleType(false),
               "enchant_end"
            }
         );
   }

   @SubscribeEvent
   public void handleIMC(InterModProcessEvent e) {
      e.getIMCStream("set_ench_hard_cap"::equals).forEach(msg -> {
         try {
            EnchantmentInstance data = (EnchantmentInstance)msg.messageSupplier().get();
            if (data != null && data.f_44947_ != null && data.f_44948_ > 0) {
               ENCH_HARD_CAPS.put(data.f_44947_, data.f_44948_);
            } else {
               LOGGER.error("Failed to process IMC message with method {} from {} (invalid values passed).", msg.method(), msg.senderModId());
            }
         } catch (Exception var2) {
            LOGGER.error("Exception thrown during IMC message with method {} from {}.", msg.method(), msg.senderModId());
            var2.printStackTrace();
         }
      });
   }

   @SubscribeEvent
   public void blocks(Register<Block> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new ApothAnvilBlock(),
               new ResourceLocation("minecraft", "anvil"),
               new ApothAnvilBlock(),
               new ResourceLocation("minecraft", "chipped_anvil"),
               new ApothAnvilBlock(),
               new ResourceLocation("minecraft", "damaged_anvil"),
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "hellshelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "infused_hellshelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "blazing_hellshelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "glowing_hellshelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_WATER),
               "seashelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_WATER),
               "infused_seashelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_WATER),
               "crystal_seashelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_WATER),
               "heart_seashelf",
               shelf(Material.f_76278_, 2.5F, Apoth.Particles.ENCHANT_SCULK),
               "dormant_deepshelf",
               shelf(Material.f_76278_, 2.5F, Apoth.Particles.ENCHANT_SCULK),
               "deepshelf",
               shelf(Material.f_76278_, 2.5F, Apoth.Particles.ENCHANT_SCULK),
               "echoing_deepshelf",
               shelf(Material.f_76278_, 2.5F, Apoth.Particles.ENCHANT_SCULK),
               "soul_touched_deepshelf",
               sculkShelf(3.5F, Apoth.Particles.ENCHANT_SCULK),
               "echoing_sculkshelf",
               sculkShelf(3.5F, Apoth.Particles.ENCHANT_SCULK),
               "soul_touched_sculkshelf",
               shelf(Material.f_76278_, 4.5F, Apoth.Particles.ENCHANT_END),
               "endshelf",
               shelf(Material.f_76278_, 4.5F, Apoth.Particles.ENCHANT_END),
               "pearl_endshelf",
               shelf(Material.f_76278_, 5.0F, Apoth.Particles.ENCHANT_END),
               "draconic_endshelf",
               shelf(Material.f_76320_, 0.75F),
               "beeshelf",
               shelf(Material.f_76320_, 0.75F),
               "melonshelf",
               shelf(Material.f_76278_, 1.25F),
               "stoneshelf",
               new EnchLibraryBlock(EnchLibraryTile.BasicLibraryTile::new, 16),
               "library",
               new EnchLibraryBlock(EnchLibraryTile.EnderLibraryTile::new, 31),
               "ender_library",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_WATER),
               "rectifier",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "rectifier_t2",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_END),
               "rectifier_t3",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "sightshelf",
               shelf(Material.f_76278_, 1.5F, Apoth.Particles.ENCHANT_FIRE),
               "sightshelf_t2"
            }
         );
      PlaceboUtil.registerOverride(Blocks.f_50201_, new ApothEnchantBlock(), "apotheosis");
   }

   private static Block shelf(Material mat, float strength) {
      return shelf(mat, strength, () -> ParticleTypes.f_123809_);
   }

   private static Block shelf(Material mat, float strength, Supplier<? extends ParticleOptions> particle) {
      Properties props = Properties.m_60939_(mat).m_60978_(strength);
      props.m_60918_(mat == Material.f_76278_ ? SoundType.f_56742_ : SoundType.f_56736_);
      if (mat == Material.f_76278_) {
         props.m_60999_();
      }

      return new TypedShelfBlock(props, particle);
   }

   private static Block sculkShelf(float strength, Supplier<? extends ParticleOptions> particle) {
      Properties props = Properties.m_60939_(Material.f_76278_).m_60918_(SoundType.f_56742_).m_60978_(strength).m_60977_().m_60999_();
      return new TypedShelfBlock.SculkShelfBlock(props, particle);
   }

   @SubscribeEvent
   public void items(Register<Item> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new Item(new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "prismatic_web",
               new ApothAnvilItem(Blocks.f_50322_),
               new ResourceLocation("minecraft", "anvil"),
               new ApothAnvilItem(Blocks.f_50323_),
               new ResourceLocation("minecraft", "chipped_anvil"),
               new ApothAnvilItem(Blocks.f_50324_),
               new ResourceLocation("minecraft", "damaged_anvil"),
               new TomeItem(Items.f_41852_, null),
               "other_tome",
               new TomeItem(Items.f_42472_, EnchantmentCategory.ARMOR_HEAD),
               "helmet_tome",
               new TomeItem(Items.f_42473_, EnchantmentCategory.ARMOR_CHEST),
               "chestplate_tome",
               new TomeItem(Items.f_42474_, EnchantmentCategory.ARMOR_LEGS),
               "leggings_tome",
               new TomeItem(Items.f_42475_, EnchantmentCategory.ARMOR_FEET),
               "boots_tome",
               new TomeItem(Items.f_42388_, EnchantmentCategory.WEAPON),
               "weapon_tome",
               new TomeItem(Items.f_42390_, EnchantmentCategory.DIGGER),
               "pickaxe_tome",
               new TomeItem(Items.f_42523_, EnchantmentCategory.FISHING_ROD),
               "fishing_tome",
               new TomeItem(Items.f_42411_, EnchantmentCategory.BOW),
               "bow_tome",
               new ScrappingTomeItem(),
               "scrap_tome",
               new ImprovedScrappingTomeItem(),
               "improved_scrap_tome",
               new ExtractionTomeItem(),
               "extraction_tome",
               new BlockItem((Block)Apoth.Blocks.HELLSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "hellshelf",
               new GlowyBlockItem((Block)Apoth.Blocks.INFUSED_HELLSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "infused_hellshelf",
               new BlockItem((Block)Apoth.Blocks.BLAZING_HELLSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "blazing_hellshelf",
               new BlockItem((Block)Apoth.Blocks.GLOWING_HELLSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "glowing_hellshelf",
               new BlockItem((Block)Apoth.Blocks.SEASHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "seashelf",
               new GlowyBlockItem((Block)Apoth.Blocks.INFUSED_SEASHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "infused_seashelf",
               new BlockItem((Block)Apoth.Blocks.CRYSTAL_SEASHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "crystal_seashelf",
               new BlockItem((Block)Apoth.Blocks.HEART_SEASHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "heart_seashelf",
               new BlockItem((Block)Apoth.Blocks.DORMANT_DEEPSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "dormant_deepshelf",
               new GlowyBlockItem((Block)Apoth.Blocks.DEEPSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "deepshelf",
               new BlockItem((Block)Apoth.Blocks.ECHOING_DEEPSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "echoing_deepshelf",
               new BlockItem((Block)Apoth.Blocks.SOUL_TOUCHED_DEEPSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "soul_touched_deepshelf",
               new BlockItem((Block)Apoth.Blocks.ECHOING_SCULKSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "echoing_sculkshelf",
               new BlockItem((Block)Apoth.Blocks.SOUL_TOUCHED_SCULKSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "soul_touched_sculkshelf",
               new BlockItem((Block)Apoth.Blocks.ENDSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "endshelf",
               new BlockItem((Block)Apoth.Blocks.DRACONIC_ENDSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "draconic_endshelf",
               new BlockItem((Block)Apoth.Blocks.PEARL_ENDSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "pearl_endshelf",
               new BlockItem((Block)Apoth.Blocks.BEESHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "beeshelf",
               new BlockItem((Block)Apoth.Blocks.MELONSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "melonshelf",
               new BlockItem((Block)Apoth.Blocks.STONESHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "stoneshelf",
               new BlockItem((Block)Apoth.Blocks.RECTIFIER.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "rectifier",
               new BlockItem((Block)Apoth.Blocks.RECTIFIER_T2.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "rectifier_t2",
               new BlockItem((Block)Apoth.Blocks.RECTIFIER_T3.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "rectifier_t3",
               new BlockItem((Block)Apoth.Blocks.SIGHTSHELF.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "sightshelf",
               new BlockItem((Block)Apoth.Blocks.SIGHTSHELF_T2.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "sightshelf_t2",
               new BlockItem((Block)Apoth.Blocks.LIBRARY.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "library",
               new BlockItem((Block)Apoth.Blocks.ENDER_LIBRARY.get(), new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "ender_library",
               new Item(new net.minecraft.world.item.Item.Properties().m_41487_(1).m_41491_(Apotheosis.APOTH_GROUP)),
               "inert_trident",
               new Item(new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP)),
               "warden_tendril",
               new Item(new net.minecraft.world.item.Item.Properties().m_41491_(Apotheosis.APOTH_GROUP).m_41497_(Rarity.EPIC)),
               "infused_breath"
            }
         );
   }

   @SubscribeEvent
   public void enchants(Register<Enchantment> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new MinersFervorEnchant(),
               "miners_fervor",
               new StableFootingEnchant(),
               "stable_footing",
               new ScavengerEnchant(),
               "scavenger",
               new LifeMendingEnchant(),
               "life_mending",
               new IcyThornsEnchant(),
               "icy_thorns",
               new TemptingEnchant(),
               "tempting",
               new ShieldBashEnchant(),
               "shield_bash",
               new ReflectiveEnchant(),
               "reflective",
               new BerserkersFuryEnchant(),
               "berserkers_fury",
               new KnowledgeEnchant(),
               "knowledge",
               new SplittingEnchant(),
               "splitting",
               new NaturesBlessingEnchant(),
               "natures_blessing",
               new ReboundingEnchant(),
               "rebounding",
               new BaneEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, MobType.f_21642_, EquipmentSlot.MAINHAND),
               new ResourceLocation("minecraft", "bane_of_arthropods"),
               new BaneEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, MobType.f_21641_, EquipmentSlot.MAINHAND),
               new ResourceLocation("minecraft", "smite"),
               new BaneEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.COMMON, MobType.f_21640_, EquipmentSlot.MAINHAND),
               new ResourceLocation("minecraft", "sharpness"),
               new BaneEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, MobType.f_21643_, EquipmentSlot.MAINHAND),
               "bane_of_illagers",
               new DefenseEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.COMMON, Type.ALL, ARMOR),
               new ResourceLocation("minecraft", "protection"),
               new DefenseEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, Type.FIRE, ARMOR),
               new ResourceLocation("minecraft", "fire_protection"),
               new DefenseEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.RARE, Type.EXPLOSION, ARMOR),
               new ResourceLocation("minecraft", "blast_protection"),
               new DefenseEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, Type.PROJECTILE, ARMOR),
               new ResourceLocation("minecraft", "projectile_protection"),
               new DefenseEnchant(net.minecraft.world.item.enchantment.Enchantment.Rarity.UNCOMMON, Type.FALL, EquipmentSlot.FEET),
               new ResourceLocation("minecraft", "feather_falling"),
               new ObliterationEnchant(),
               "obliteration",
               new CrescendoEnchant(),
               "crescendo",
               new InertEnchantment(),
               "infusion",
               new EndlessQuiverEnchant(),
               "endless_quiver",
               new ChromaticEnchant(),
               "chromatic",
               new ExploitationEnchant(),
               "exploitation",
               new GrowthSerumEnchant(),
               "growth_serum",
               new EarthsBoonEnchant(),
               "earths_boon",
               new ChainsawEnchant(),
               "chainsaw",
               new SpearfishingEnchant(),
               "spearfishing"
            }
         );
   }

   public static EnchantmentInfo getEnchInfo(Enchantment ench) {
      if (!Apotheosis.enableEnch) {
         return ENCHANTMENT_INFO.computeIfAbsent(ench, EnchantmentInfo::new);
      } else {
         EnchantmentInfo info = ENCHANTMENT_INFO.get(ench);
         if (enchInfoConfig == null) {
            return new EnchantmentInfo(ench);
         } else {
            if (info == null) {
               info = EnchantmentInfo.load(ench, enchInfoConfig);
               ENCHANTMENT_INFO.put(ench, info);
               if (enchInfoConfig.hasChanged()) {
                  enchInfoConfig.save();
               }

               LOGGER.error(
                  "Had to late load enchantment info for {}, this is a bug in the mod {} as they are registering late!",
                  ForgeRegistries.ENCHANTMENTS.getKey(ench),
                  ForgeRegistries.ENCHANTMENTS.getKey(ench).m_135827_()
               );
            }

            return info;
         }
      }
   }

   public static int getDefaultMax(Enchantment ench) {
      int level = ench.m_6586_();
      if (level == 1) {
         return 1;
      } else {
         EnchantmentInfo.PowerFunc minFunc = EnchantmentInfo.defaultMin(ench);
         int max = (int)(EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F);
         int minPower = minFunc.getPower(level);
         if (minPower >= max) {
            return level;
         } else {
            for (int lastPower = minPower; minPower < max; lastPower = minPower) {
               minPower = minFunc.getPower(++level);
               if (lastPower == minPower) {
                  return level;
               }

               if (minPower > max) {
                  return level - 1;
               }
            }

            return level;
         }
      }
   }

   public void reload(Apotheosis.ApotheosisReloadEvent e) {
      enchInfoConfig = new Configuration(new File(Apotheosis.configDir, "enchantments.cfg"));
      enchInfoConfig.setTitle("Apotheosis Enchantment Information");
      enchInfoConfig.setComment(
         "This file contains configurable data for each enchantment.\nThe names of each category correspond to the registry names of every loaded enchantment."
      );
      ENCHANTMENT_INFO.clear();

      for (Enchantment ench : ForgeRegistries.ENCHANTMENTS) {
         ENCHANTMENT_INFO.put(ench, EnchantmentInfo.load(ench, enchInfoConfig));
      }

      for (Enchantment ench : ForgeRegistries.ENCHANTMENTS) {
         EnchantmentInfo info = ENCHANTMENT_INFO.get(ench);

         for (int i = 1; i <= info.getMaxLevel(); i++) {
            if (info.getMinPower(i) > info.getMaxPower(i)) {
               LOGGER.warn(
                  "Enchantment {} has min/max power {}/{} at level {}, making this level unobtainable.",
                  ForgeRegistries.ENCHANTMENTS.getKey(ench),
                  info.getMinPower(i),
                  info.getMaxPower(i),
                  i
               );
            }
         }
      }

      if (e == null && enchInfoConfig.hasChanged()) {
         enchInfoConfig.save();
      }

      EnchConfig.load(new Configuration(new File(Apotheosis.configDir, "ench.cfg")));
   }

   public static class Colors {
      private static int[] _LIGHT_BLUE_FLASH = new int[]{
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         46079,
         767487,
         1554687,
         2276351,
         2997759,
         3784959,
         4506623,
         5228287,
         6015487,
         6737151
      };
      public static GradientColor LIGHT_BLUE_FLASH = new GradientColor(ApothMiscUtil.doubleUpGradient(_LIGHT_BLUE_FLASH), "light_blue_flash");
   }
}
