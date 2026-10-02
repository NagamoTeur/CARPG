package com.hollingsworth.arsnouveau.api.client;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class CosmeticRenderUtil {
   public static <T extends LivingEntity & IDecoratable> void renderCosmetic(
      GeoBone bone, PoseStack matrix, MultiBufferSource buffer, T entity, int packedLightIn
   ) {
      ItemStack stack = entity.getCosmeticItem();
      if (stack.m_41720_() instanceof ICosmeticItem cosmetic) {
         matrix.m_85836_();
         RenderUtils.translateToPivotPoint(matrix, bone);
         RenderUtils.rotateMatrixAroundBone(matrix, bone);
         RenderUtils.translateMatrixToBone(matrix, bone);
         Vec3 var9 = cosmetic.getTranslations(entity);
         Vec3 scaling = cosmetic.getScaling(entity);
         matrix.m_85837_(var9.f_82479_, var9.f_82480_, var9.f_82481_);
         matrix.m_85841_((float)scaling.f_82479_, (float)scaling.f_82480_, (float)scaling.f_82481_);
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(stack, cosmetic.getTransformType(), packedLightIn, OverlayTexture.f_118083_, matrix, buffer, (int)entity.m_20097_().m_121878_());
         matrix.m_85849_();
      }
   }
}
