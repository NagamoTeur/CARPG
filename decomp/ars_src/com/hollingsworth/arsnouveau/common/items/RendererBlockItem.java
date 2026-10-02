package com.hollingsworth.arsnouveau.common.items;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public abstract class RendererBlockItem extends AnimBlockItem {
   public RendererBlockItem(Block block, Properties props) {
      super(block, props);
   }

   public abstract Supplier<BlockEntityWithoutLevelRenderer> getRenderer();

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return RendererBlockItem.this.getRenderer().get();
         }
      });
   }
}
