package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.ArchwoodChestTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ArchwoodChest extends ChestBlock {
   public ArchwoodChest() {
      super(Properties.m_60939_(Material.f_76320_).m_60978_(2.5F).m_60918_(SoundType.f_56736_), () -> BlockRegistry.ARCHWOOD_CHEST_TILE);
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new ArchwoodChestTile(pos, state);
   }

   public static class Item extends BlockItem {
      public Item(Block block, net.minecraft.world.item.Item.Properties props) {
         super(block, props);
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         super.initializeClient(consumer);
         consumer.accept(new IClientItemExtensions() {
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               final Minecraft mc = Minecraft.m_91087_();
               return new BlockEntityWithoutLevelRenderer(mc.m_167982_(), mc.m_167973_()) {
                  private final BlockEntity tile = new ArchwoodChestTile(BlockPos.f_121853_, Item.this.m_40614_().m_49966_());

                  public void m_108829_(ItemStack stack, TransformType transformType, PoseStack pose, MultiBufferSource buffer, int x, int y) {
                     mc.m_167982_().m_112272_(this.tile, pose, buffer, x, y);
                  }
               };
            }
         });
      }
   }
}
