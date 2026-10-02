package dev.latvian.mods.kubejs.fluid;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class FluidBlockBuilder extends BlockBuilder {
   private final FluidBuilder fluidBuilder;

   public FluidBlockBuilder(FluidBuilder b) {
      super(b.id);
      this.fluidBuilder = b;
      this.defaultTranslucent();
      this.noItem();
      this.noDrops();
   }

   public Block createObject() {
      return new ArchitecturyLiquidBlock(
         () -> Objects.requireNonNull(this.fluidBuilder.flowingFluid.get(), "Flowing Fluid is null!"),
         Properties.m_60939_(Material.f_76305_).m_60910_().m_60978_(100.0F).m_222994_()
      );
   }

   @Override
   public void generateAssetJsons(AssetJsonGenerator generator) {
      generator.blockState(this.id, m -> m.simpleVariant("", this.id.m_135827_() + ":block/" + this.id.m_135815_()));
      generator.blockModel(this.id, m -> {
         m.parent("");
         m.texture("particle", this.fluidBuilder.stillTexture.toString());
      });
   }

   @Override
   public BlockBuilder item(@Nullable Consumer<BlockItemBuilder> i) {
      if (i != null) {
         throw new IllegalStateException("Fluid blocks cannot have items!");
      } else {
         return super.item(null);
      }
   }
}
