package com.bobmowzie.mowziesmobs.server.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockHandler {
   public static final DeferredRegister<Block> REG = DeferredRegister.create(ForgeRegistries.BLOCKS, "mowziesmobs");
   public static final RegistryObject<Block> PAINTED_ACACIA = REG.register(
      "painted_acacia", () -> new Block(Properties.m_60944_(Material.f_76320_, MaterialColor.f_76413_).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_))
   );
   public static final RegistryObject<SlabBlock> PAINTED_ACACIA_SLAB = REG.register(
      "painted_acacia_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)PAINTED_ACACIA.get()))
   );
   public static final RegistryObject<Block> THATCH = REG.register(
      "thatch_block", () -> new HayBlock(Properties.m_60944_(Material.f_76315_, MaterialColor.f_76416_).m_60978_(0.5F).m_60918_(SoundType.f_56740_))
   );
   public static final RegistryObject<Block> GONG = REG.register(
      "gong", () -> new GongBlock(Properties.m_60944_(Material.f_76279_, MaterialColor.f_76366_).m_60999_().m_60978_(3.0F).m_60918_(SoundType.f_56749_))
   );
   public static final RegistryObject<Block> GONG_PART = REG.register(
      "gong_part",
      () -> new GongBlock.GongPartBlock(Properties.m_60944_(Material.f_76279_, MaterialColor.f_76366_).m_60999_().m_60978_(3.0F).m_60918_(SoundType.f_56749_))
   );
   public static final RegistryObject<RakedSandBlock> RAKED_SAND = REG.register(
      "raked_sand",
      () -> new RakedSandBlock(
            14406560, Properties.m_60944_(Material.f_76317_, MaterialColor.f_76400_).m_60978_(0.5F).m_60918_(SoundType.f_56746_), Blocks.f_49992_.m_49966_()
         )
   );
   public static final RegistryObject<RakedSandBlock> RED_RAKED_SAND = REG.register(
      "red_raked_sand",
      () -> new RakedSandBlock(
            11098145, Properties.m_60944_(Material.f_76317_, MaterialColor.f_76413_).m_60978_(0.5F).m_60918_(SoundType.f_56746_), Blocks.f_49993_.m_49966_()
         )
   );
   public static final RegistryObject<Block> CLAWED_LOG = REG.register(
      "clawed_log", () -> new Block(Properties.m_60944_(Material.f_76320_, MaterialColor.f_76408_).m_60978_(2.0F).m_60918_(SoundType.f_56736_))
   );

   public static void init() {
      FireBlock fireblock = (FireBlock)Blocks.f_50083_;
      fireblock.m_53444_((Block)THATCH.get(), 60, 20);
      fireblock.m_53444_((Block)PAINTED_ACACIA.get(), 5, 20);
      fireblock.m_53444_((Block)PAINTED_ACACIA_SLAB.get(), 5, 20);
      fireblock.m_53444_((Block)CLAWED_LOG.get(), 5, 5);
   }
}
