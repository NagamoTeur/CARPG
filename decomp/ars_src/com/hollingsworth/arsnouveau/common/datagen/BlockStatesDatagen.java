package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockStatesDatagen extends BlockStateProvider {
   public BlockStatesDatagen(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
      super(gen, modid, exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.registerOnlyState(BlockRegistry.SOURCE_GEM_BLOCK, "source_gem_block");
      this.registerOnlyState(BlockRegistry.RED_SBED, "red_sbed");
      this.registerOnlyState(BlockRegistry.BLUE_SBED, "blue_sbed");
      this.registerOnlyState(BlockRegistry.GREEN_SBED, "green_sbed");
      this.registerOnlyState(BlockRegistry.YELLOW_SBED, "yellow_sbed");
      this.registerOnlyState(BlockRegistry.ORANGE_SBED, "orange_sbed");
      this.registerOnlyState(BlockRegistry.PURPLE_SBED, "purple_sbed");
      this.registerOnlyState(BlockRegistry.POTION_DIFFUSER, "potion_diffuser");

      for (Entry<Supplier<ResourceLocation>, FlowerPotBlock> pot : BlockRegistry.flowerPots.entrySet()) {
         this.registerOnlyState((Block)pot.getValue(), "pots/" + LibBlockNames.Pot(pot.getKey().get().m_135815_()));
      }

      this.registerDoor(BlockRegistry.ARCHWOOD_DOOR, "archwood_door");
      this.registerNormalCube(BlockRegistry.VOID_PRISM, "void_prism");

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         this.registerNormalCube((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s)), s);
      }

      this.registerNormalCube(BlockRegistry.MAGEBLOOM_BLOCK, "magebloom_block");
      this.registerNormalCube(BlockRegistry.FALSE_WEAVE, "falseweave");
      this.registerNormalCube(BlockRegistry.GHOST_WEAVE, "ghostweave");
      this.registerNormalCube(BlockRegistry.MIRROR_WEAVE, "mirrorweave");

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         ResourceLocation tex = new ResourceLocation("ars_nouveau", "blocks/" + s);
         Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_stairs"));
         this.stairsBlock((StairBlock)block, tex);
         Block slab = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_slab"));
         this.slabBlock((SlabBlock)slab, new ResourceLocation("ars_nouveau", s), tex);
      }
   }

   private void registerOnlyState(Block block, String registry) {
      this.simpleBlock(block, getUncheckedModel(registry));
   }

   private void registerDoor(DoorBlock door, String reg) {
      this.doorBlock(door, reg, this.getBlockLoc(reg + "_bottom"), this.getBlockLoc(reg + "_top"));
   }

   public void signBlock(Block sign, String reg) {
      ModelFile signModel = ((BlockModelBuilder)this.models().withExistingParent(reg, new ResourceLocation("block/air")))
         .texture("particle", new ResourceLocation("ars_nouveau", "block/" + reg));
      this.getVariantBuilder(sign).forAllStates(s -> ConfiguredModel.builder().modelFile(signModel).build());
   }

   public void registerNormalCube(Block block, String registry) {
      this.buildNormalCube(registry);
      if (LibBlockNames.DIRECTIONAL_SOURCESTONE.contains(registry)) {
         this.horizontalBlock(block, getUncheckedModel(registry));
      } else {
         this.simpleBlock(block, getUncheckedModel(registry));
      }
   }

   public static ModelFile getUncheckedModel(String registry) {
      return new UncheckedModelFile("ars_nouveau:block/" + registry);
   }

   public void buildNormalCube(String registryName) {
      ((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(registryName)).parent(new UncheckedModelFile("block/cube_all")))
         .texture("all", this.getBlockLoc(registryName));
   }

   public ResourceLocation getBlockLoc(String registryName) {
      return new ResourceLocation("ars_nouveau", "blocks/" + registryName);
   }
}
