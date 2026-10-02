package net.cisco.init;

import net.cisco.block.ArcaneBrightsteelBlockBlock;
import net.cisco.block.BrightsteelblockBlock;
import net.cisco.block.BrightsteelslabBlock;
import net.cisco.block.BrightsteelstairsBlock;
import net.cisco.block.DarksteelBlockBlock;
import net.cisco.block.DarksteelSlabBlock;
import net.cisco.block.DarksteelStairsBlock;
import net.cisco.block.PodiumofpurgatoryBlock;
import net.cisco.block.StrucuteblockfixBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CiscoModModBlocks {
   public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "cisco_mod");
   public static final RegistryObject<Block> STRUCUTEBLOCKFIX = REGISTRY.register("strucuteblockfix", () -> new StrucuteblockfixBlock());
   public static final RegistryObject<Block> BRIGHTSTEELBLOCK = REGISTRY.register("brightsteelblock", () -> new BrightsteelblockBlock());
   public static final RegistryObject<Block> BRIGHTSTEELSTAIRS = REGISTRY.register("brightsteelstairs", () -> new BrightsteelstairsBlock());
   public static final RegistryObject<Block> BRIGHTSTEELSLAB = REGISTRY.register("brightsteelslab", () -> new BrightsteelslabBlock());
   public static final RegistryObject<Block> ARCANE_BRIGHTSTEEL_BLOCK = REGISTRY.register("arcane_brightsteel_block", () -> new ArcaneBrightsteelBlockBlock());
   public static final RegistryObject<Block> DARKSTEEL_BLOCK = REGISTRY.register("darksteel_block", () -> new DarksteelBlockBlock());
   public static final RegistryObject<Block> DARKSTEEL_STAIRS = REGISTRY.register("darksteel_stairs", () -> new DarksteelStairsBlock());
   public static final RegistryObject<Block> DARKSTEEL_SLAB = REGISTRY.register("darksteel_slab", () -> new DarksteelSlabBlock());
   public static final RegistryObject<Block> PODIUMOFPURGATORY = REGISTRY.register("podiumofpurgatory", () -> new PodiumofpurgatoryBlock());
}
