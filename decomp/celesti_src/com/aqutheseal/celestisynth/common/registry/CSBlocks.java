package com.aqutheseal.celestisynth.common.registry;

import com.aqutheseal.celestisynth.common.block.CelestialCraftingTable;
import com.aqutheseal.celestisynth.common.block.SolarCrystalBlock;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "celestisynth");
   public static final RegistryObject<Block> SOLAR_CRYSTAL = registerBlock(
      "solar_crystal",
      () -> new SolarCrystalBlock(
            Properties.m_60944_(Material.f_76278_, MaterialColor.f_76364_)
               .m_60918_(SoundType.f_56744_)
               .m_60999_()
               .m_60913_(3.0F, 9.0F)
               .m_60955_()
               .m_60991_((a, b, c) -> true)
               .m_60953_(a -> 15)
         )
   );
   public static final RegistryObject<Block> LUNAR_STONE = registerBlock(
      "lunar_stone",
      () -> new Block(
            Properties.m_60944_(Material.f_76278_, MaterialColor.f_76415_).m_60918_(SoundType.f_56742_).m_60999_().m_60913_(4.0F, 9.0F).m_60953_(a -> 3)
         )
   );
   public static final RegistryObject<Block> ZEPHYR_DEPOSIT = registerBlock(
      "zephyr_deposit",
      () -> new Block(
            Properties.m_60944_(Material.f_76278_, MaterialColor.f_76385_).m_60918_(SoundType.f_56730_).m_60999_().m_60913_(60.5F, 9.0F).m_60953_(a -> 3)
         )
   );
   public static final RegistryObject<Block> CELESTIAL_CRAFTING_TABLE = registerBlock(
      "celestial_crafting_table",
      () -> new CelestialCraftingTable(
            Properties.m_60944_(Material.f_76281_, MaterialColor.f_76375_)
               .m_60918_(SoundType.f_56725_)
               .m_60955_()
               .m_60999_()
               .m_60913_(60.5F, 9.0F)
               .m_60953_(a -> 7)
         )
   );

   private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
      RegistryObject<T> toReturn = BLOCKS.register(name, block);
      registerBlockItem(name, toReturn);
      return toReturn;
   }

   private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
      return CSItems.ITEMS
         .register(name, () -> new BlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties().m_41491_(CSCreativeTabs.CELESTISYNTH)));
   }
}
