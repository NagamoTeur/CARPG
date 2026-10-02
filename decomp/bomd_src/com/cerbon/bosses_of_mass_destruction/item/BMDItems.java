package com.cerbon.bosses_of_mass_destruction.item;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.item.custom.BrimstoneNectarItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.ChargedEnderPearlItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.CrystalFruitItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.EarthdiveSpear;
import com.cerbon.bosses_of_mass_destruction.item.custom.MaterialItem;
import com.cerbon.bosses_of_mass_destruction.item.custom.SoulStarItem;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.GauntletStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.LichStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.ObsidilithStructureRepair;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.VoidBlossomStructureRepair;
import java.util.List;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BMDItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "bosses_of_mass_destruction");
   public static final RegistryObject<Item> SOUL_STAR = ITEMS.register(
      "soul_star", () -> new SoulStarItem(new Properties().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> ANCIENT_ANIMA = ITEMS.register(
      "ancient_anima", () -> new MaterialItem(new Properties().m_41497_(Rarity.RARE).m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> BLAZING_EYE = ITEMS.register(
      "blazing_eye", () -> new MaterialItem(new Properties().m_41497_(Rarity.RARE).m_41486_().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> OBSIDIAN_HEART = ITEMS.register(
      "obsidian_heart", () -> new MaterialItem(new Properties().m_41497_(Rarity.RARE).m_41486_().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> EARTHDIVE_SPEAR = ITEMS.register(
      "earthdive_spear", () -> new EarthdiveSpear(new Properties().m_41486_().m_41503_(250).m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> VOID_THORN = ITEMS.register(
      "void_thorn", () -> new MaterialItem(new Properties().m_41497_(Rarity.RARE).m_41486_().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> CRYSTAL_FRUIT = ITEMS.register(
      "crystal_fruit",
      () -> new CrystalFruitItem(
            new Properties().m_41497_(Rarity.RARE).m_41486_().m_41489_(BMDFoods.CRYSTAL_FRUIT).m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION)
         )
   );
   public static final RegistryObject<Item> CHARGED_ENDER_PEARL = ITEMS.register(
      "charged_ender_pearl", () -> new ChargedEnderPearlItem(new Properties().m_41486_().m_41487_(1).m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> BRIMSTONE_NECTAR = ITEMS.register(
      "brimstone_nectar",
      () -> new BrimstoneNectarItem(
            new Properties().m_41497_(Rarity.RARE).m_41486_().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION),
            List.of(new GauntletStructureRepair(), new LichStructureRepair(), new ObsidilithStructureRepair(), new VoidBlossomStructureRepair())
         )
   );
   public static final RegistryObject<Item> OBSIDILITH_RUNE = ITEMS.register(
      "obsidilith_rune", () -> new BlockItem((Block)BMDBlocks.OBSIDILITH_RUNE.get(), new Properties())
   );
   public static final RegistryObject<Item> CHISELED_STONE_ALTAR = ITEMS.register(
      "chiseled_stone_altar", () -> new BlockItem((Block)BMDBlocks.CHISELED_STONE_ALTAR.get(), new Properties())
   );
   public static final RegistryObject<Item> VOID_BLOSSOM = ITEMS.register(
      "void_blossom", () -> new BlockItem((Block)BMDBlocks.VOID_BLOSSOM.get(), new Properties())
   );
   public static final RegistryObject<Item> VINE_WALL = ITEMS.register("vine_wall", () -> new BlockItem((Block)BMDBlocks.VINE_WALL.get(), new Properties()));
   public static final RegistryObject<Item> OBSIDILITH_SUMMON_BLOCK = ITEMS.register(
      "obsidilith_end_frame", () -> new BlockItem((Block)BMDBlocks.OBSIDILITH_SUMMON_BLOCK.get(), new Properties())
   );
   public static final RegistryObject<Item> GAUNTLET_BLACKSTONE = ITEMS.register(
      "gauntlet_blackstone", () -> new BlockItem((Block)BMDBlocks.GAUNTLET_BLACKSTONE.get(), new Properties())
   );
   public static final RegistryObject<Item> SEALED_BLACKSTONE = ITEMS.register(
      "sealed_blackstone", () -> new BlockItem((Block)BMDBlocks.SEALED_BLACKSTONE.get(), new Properties())
   );
   public static final RegistryObject<Item> MOB_WARD = ITEMS.register(
      "mob_ward", () -> new BlockItem((Block)BMDBlocks.MOB_WARD.get(), new Properties().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> MONOLITH_BLOCK = ITEMS.register(
      "monolith_block", () -> new BlockItem((Block)BMDBlocks.MONOLITH_BLOCK.get(), new Properties().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> LEVITATION_BLOCK = ITEMS.register(
      "levitation_block",
      () -> new BlockItem((Block)BMDBlocks.LEVITATION_BLOCK.get(), new Properties().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );
   public static final RegistryObject<Item> VOID_BLOSSOM_SUMMON_BLOCK = ITEMS.register(
      "void_blossom_block", () -> new BlockItem((Block)BMDBlocks.VOID_BLOSSOM_SUMMON_BLOCK.get(), new Properties())
   );
   public static final RegistryObject<Item> VOID_LILY = ITEMS.register(
      "void_lily", () -> new BlockItem((Block)BMDBlocks.VOID_LILY_BLOCK.get(), new Properties().m_41491_(BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION))
   );

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      ItemProperties.register((Item)EARTHDIVE_SPEAR.get(), new ResourceLocation("throwing"), (stack, level, entity, seed) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.m_6117_() && entity.m_21211_() == stack ? 1.0F : 0.0F;
         }
      });
   }

   public static void register(IEventBus eventBus) {
      ITEMS.register(eventBus);
   }
}
