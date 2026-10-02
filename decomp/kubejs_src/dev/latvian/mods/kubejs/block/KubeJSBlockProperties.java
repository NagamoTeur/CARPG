package dev.latvian.mods.kubejs.block;

import java.util.function.Function;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class KubeJSBlockProperties extends Properties {
   public final BlockBuilder blockBuilder;

   public KubeJSBlockProperties(BlockBuilder blockBuilder, Material material, Function<BlockState, MaterialColor> function) {
      super(material, function);
      this.blockBuilder = blockBuilder;
   }
}
