package com.hollingsworth.arsnouveau.common.world.tree;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AbstractSupplierBlockStateProvider extends BlockStateProvider {
   protected final ResourceLocation key;
   protected BlockState state = null;

   public static <T extends AbstractSupplierBlockStateProvider> Codec<T> codecBuilder(Function<ResourceLocation, T> builder) {
      return ResourceLocation.f_135803_.fieldOf("key").xmap(builder, provider -> provider.key).codec();
   }

   public AbstractSupplierBlockStateProvider(String namespace, String path) {
      this(new ResourceLocation(namespace, path));
   }

   public AbstractSupplierBlockStateProvider(ResourceLocation key) {
      this.key = key;
   }

   protected abstract BlockStateProviderType<?> m_5923_();

   public BlockState m_213972_(RandomSource randomIn, BlockPos blockPosIn) {
      if (this.state == null) {
         Block block = (Block)ForgeRegistries.BLOCKS.getValue(this.key);
         if (block == null) {
            this.state = Blocks.f_50016_.m_49966_();
         } else {
            this.state = block.m_49966_();
         }
      }

      return this.state;
   }
}
