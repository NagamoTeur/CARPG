package com.hollingsworth.arsnouveau.common.world.tree;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class SupplierBlockStateProvider extends AbstractSupplierBlockStateProvider {
   public static final Codec<SupplierBlockStateProvider> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.STRING.fieldOf("key").forGetter(d -> d.key.m_135815_())).apply(instance, SupplierBlockStateProvider::new)
   );

   public SupplierBlockStateProvider(String path) {
      this(new ResourceLocation("ars_nouveau", path));
   }

   public SupplierBlockStateProvider(ResourceLocation path) {
      super(path);
   }

   @Override
   protected BlockStateProviderType<?> m_5923_() {
      return BlockRegistry.stateProviderType;
   }
}
