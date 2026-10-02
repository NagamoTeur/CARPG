package com.aqutheseal.celestisynth.datagen.providers;

import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class CSBlockstateProvider extends BlockStateProvider {
   private static final ResourceLocation RENDER_TYPE_CUTOUT = new ResourceLocation("cutout");

   public CSBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
      super(gen, "celestisynth", exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.crossBlock((Block)CSBlocks.SOLAR_CRYSTAL.get());
      this.simpleBlock((Block)CSBlocks.LUNAR_STONE.get());
      this.simpleBlock((Block)CSBlocks.ZEPHYR_DEPOSIT.get());
   }

   public void crossBlock(Block block) {
      this.getVariantBuilder(block)
         .partialState()
         .setModels(
            new ConfiguredModel[]{
               new ConfiguredModel(((BlockModelBuilder)this.models().cross(this.name(block), this.blockTexture(block))).renderType(RENDER_TYPE_CUTOUT))
            }
         );
   }

   private ResourceLocation key(Block block) {
      return ForgeRegistries.BLOCKS.getKey(block);
   }

   private String name(Block block) {
      return this.key(block).m_135815_();
   }
}
