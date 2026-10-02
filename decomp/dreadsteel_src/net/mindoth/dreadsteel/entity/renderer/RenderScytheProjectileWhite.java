package net.mindoth.dreadsteel.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileWhite;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class RenderScytheProjectileWhite extends EntityRenderer<EntityScytheProjectileWhite> {
   private ItemStack PROJECTILE = new ItemStack((ItemLike)DreadsteelItems.SCYTHE_PROJECTILE_WHITE.get());

   public RenderScytheProjectileWhite(Context renderManager) {
      super(renderManager);
   }

   public ResourceLocation getTextureLocation(EntityScytheProjectileWhite entity) {
      return TextureAtlas.f_118259_;
   }

   public void render(
      EntityScytheProjectileWhite entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      matrixStackIn.m_85837_(0.0, 0.5, 0.0);
      matrixStackIn.m_85841_(2.0F, 2.0F, 2.0F);
      matrixStackIn.m_85837_(0.0, -0.15F, 0.0);
      Minecraft.m_91087_().m_91291_().m_174269_(this.PROJECTILE, TransformType.GROUND, 240, 0, matrixStackIn, bufferIn, 0);
      matrixStackIn.m_85849_();
   }
}
