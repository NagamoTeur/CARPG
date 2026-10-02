package com.min01.archaeology.init;

import com.min01.archaeology.block.BrushableBlock;
import com.min01.archaeology.block.DecoratedPotBlock;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArchaeologyBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
   public static final RegistryObject<Block> SUSPICIOUS_SAND = BLOCKS.register(
      "suspicious_sand",
      () -> new BrushableBlock(
            Blocks.f_49992_,
            Properties.m_60939_(Material.f_76317_).m_155949_(MaterialColor.f_76400_).m_60978_(0.25F).m_60918_(ArchaeologySounds.SUSPICIOUS_SAND),
            (SoundEvent)ArchaeologySounds.BRUSH_SAND.get(),
            (SoundEvent)ArchaeologySounds.BRUSH_SAND_COMPLETED.get()
         )
   );
   public static final RegistryObject<Block> SUSPICIOUS_GRAVEL = BLOCKS.register(
      "suspicious_gravel",
      () -> new BrushableBlock(
            Blocks.f_49994_,
            Properties.m_60939_(Material.f_76317_).m_155949_(MaterialColor.f_76409_).m_60978_(0.25F).m_60918_(ArchaeologySounds.SUSPICIOUS_GRAVEL),
            (SoundEvent)ArchaeologySounds.BRUSH_GRAVEL.get(),
            (SoundEvent)ArchaeologySounds.BRUSH_GRAVEL_COMPLETED.get()
         )
   );
   public static final RegistryObject<DecoratedPotBlock> DECORATED_POT = BLOCKS.register(
      "decorated_pot", () -> new DecoratedPotBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76386_).m_60913_(0.0F, 0.0F).m_60955_())
   );
}
