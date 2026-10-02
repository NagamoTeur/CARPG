package shadows.apotheosis.adventure.affix.reforging;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ReforgingTableTileRenderer implements BlockEntityRenderer<ReforgingTableTile> {
   private static final ResourceLocation HAMMER = new ResourceLocation("apotheosis", "item/hammer");

   public void render(ReforgingTableTile tile, float partials, PoseStack matrix, MultiBufferSource pBufferSource, int light, int overlay) {
      ItemRenderer irenderer = Minecraft.m_91087_().m_91291_();
      BakedModel base = irenderer.m_115103_().m_109393_().getModel(HAMMER);
      matrix.m_85836_();
      double px = 0.0625;
      matrix.m_85841_(1.25F, 1.25F, 1.25F);
      matrix.m_85837_(8.5 * px / 1.25, 16.0 * px / 1.25 - 0.015, 7.0 * px / 1.25);
      matrix.m_85845_(Vector3f.f_122225_.m_122240_(45.0F));
      matrix.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      if (tile.step1) {
         float factor = (float)(tile.time % 60) + partials;
         float sin = Mth.m_14031_(factor * (float) Math.PI / 120.0F);
         float sinSq = sin * sin;
         matrix.m_85837_(0.125 * (double)sinSq, 0.0, -0.15 * (double)sinSq);
         matrix.m_85845_(Vector3f.f_122224_.m_122240_(45.0F * sinSq));
      } else {
         float factor = (float)(tile.time % 5) + partials;
         float sin = Mth.m_14031_((float) (Math.PI / 2) + factor * (float) Math.PI / 10.0F);
         float sinSq = sin * sin;
         matrix.m_85837_(0.125 * (double)sinSq, 0.0, -0.15 * (double)sinSq);
         matrix.m_85845_(Vector3f.f_122224_.m_122240_(45.0F * sinSq));
      }

      BufferSource src = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
      irenderer.m_115189_(
         base, ItemStack.f_41583_, light, overlay, matrix, ItemRenderer.m_115222_(src, ItemBlockRenderTypes.m_109284_(tile.m_58900_(), true), true, false)
      );
      src.m_109911_();
      matrix.m_85849_();
   }
}
