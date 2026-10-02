package com.cerbon.bosses_of_mass_destruction.block;

import com.cerbon.bosses_of_mass_destruction.block.custom.ChiseledStoneAltarBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.GauntletBlackstoneBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.LevitationBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.MobWardBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.MonolithBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.ObsidilithRuneBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.ObsidilithSummonBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.VineWallBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidBlossomBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidBlossomSummonBlock;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidLilyBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BMDBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "bosses_of_mass_destruction");
   public static final RegistryObject<Block> OBSIDILITH_RUNE = BLOCKS.register(
      "obsidilith_rune", () -> new ObsidilithRuneBlock(Properties.m_60941_(Material.f_76278_, DyeColor.BLACK).m_60999_().m_60913_(50.0F, 1200.0F))
   );
   public static final RegistryObject<Block> VOID_BLOSSOM = BLOCKS.register(
      "void_blossom",
      () -> new VoidBlossomBlock(
            Properties.m_60941_(Material.f_76300_, DyeColor.BLACK).m_60966_().m_60910_().m_60953_(value -> 11).m_60918_(SoundType.f_154665_)
         )
   );
   public static final RegistryObject<Block> VINE_WALL = BLOCKS.register(
      "vine_wall", () -> new VineWallBlock(Properties.m_60941_(Material.f_76300_, DyeColor.GREEN).m_60918_(SoundType.f_56736_).m_60913_(2.0F, 6.0F))
   );
   public static final RegistryObject<Block> OBSIDILITH_SUMMON_BLOCK = BLOCKS.register(
      "obsidilith_end_frame", () -> new ObsidilithSummonBlock(Properties.m_60926_(Blocks.f_50258_))
   );
   public static final RegistryObject<Block> GAUNTLET_BLACKSTONE = BLOCKS.register(
      "gauntlet_blackstone", () -> new GauntletBlackstoneBlock(Properties.m_60941_(Material.f_76278_, DyeColor.BLACK).m_60999_().m_60913_(50.0F, 1200.0F))
   );
   public static final RegistryObject<Block> SEALED_BLACKSTONE = BLOCKS.register("sealed_blackstone", () -> new Block(Properties.m_60926_(Blocks.f_50752_)));
   public static final RegistryObject<Block> CHISELED_STONE_ALTAR = BLOCKS.register(
      "chiseled_stone_altar",
      () -> new ChiseledStoneAltarBlock(
            Properties.m_60926_(Blocks.f_50752_).m_60953_(blockState -> blockState.m_61143_(BlockStateProperties.f_61443_) ? 11 : 0)
         )
   );
   public static final RegistryObject<Block> MOB_WARD = BLOCKS.register(
      "mob_ward",
      () -> new MobWardBlock(Properties.m_60941_(Material.f_76278_, DyeColor.BLACK).m_60999_().m_60955_().m_60953_(value -> 15).m_60913_(10.0F, 1200.0F))
   );
   public static final RegistryObject<Block> MONOLITH_BLOCK = BLOCKS.register(
      "monolith_block",
      () -> new MonolithBlock(Properties.m_60941_(Material.f_76279_, DyeColor.BLACK).m_60999_().m_60955_().m_60953_(value -> 4).m_60913_(10.0F, 1200.0F))
   );
   public static final RegistryObject<Block> LEVITATION_BLOCK = BLOCKS.register(
      "levitation_block",
      () -> new LevitationBlock(Properties.m_60941_(Material.f_76278_, DyeColor.BLUE).m_60999_().m_60955_().m_60953_(value -> 4).m_60913_(10.0F, 1200.0F))
   );
   public static final RegistryObject<Block> VOID_BLOSSOM_SUMMON_BLOCK = BLOCKS.register(
      "void_blossom_block", () -> new VoidBlossomSummonBlock(Properties.m_60926_(Blocks.f_50752_))
   );
   public static final RegistryObject<Block> VOID_LILY_BLOCK = BLOCKS.register(
      "void_lily",
      () -> new VoidLilyBlock(Properties.m_60939_(Material.f_76300_).m_60910_().m_60977_().m_60966_().m_60918_(SoundType.f_56740_).m_60953_(value -> 8))
   );

   public static void register(IEventBus eventBus) {
      BLOCKS.register(eventBus);
   }
}
