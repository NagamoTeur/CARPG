package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ProjectileLineEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ProjectileLineRenderer extends EntityRenderer<ProjectileLineEntity> {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      MeetYourFight.rl("textures/entity/projectile_bellringer.png"),
      MeetYourFight.rl("textures/entity/projectile_dame_fortuna.png"),
      MeetYourFight.rl("textures/entity/projectile_rose.png")
   };
   private static final RenderType[] OVERLAYS = new RenderType[TEXTURES.length];
   private final ProjectileLineModel<ProjectileLineEntity> model;

   public ProjectileLineRenderer(Context context) {
      super(context);
      this.model = new ProjectileLineModel(context.m_174023_(ProjectileLineModel.MODEL));
   }

   protected int getBlockLightLevel(ProjectileLineEntity entityIn, BlockPos partialTicks) {
      return 15;
   }

   public void render(
      ProjectileLineEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      matrixStackIn.m_85837_(0.0, 0.15, 0.0);
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(this.model.m_103119_(TEXTURES[this.clampVariant(entityIn)]));
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85841_(1.5F, 1.5F, 1.5F);
      VertexConsumer ivertexbuilder1 = bufferIn.m_6299_(OVERLAYS[this.clampVariant(entityIn)]);
      this.model.m_7695_(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.15F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(ProjectileLineEntity entity) {
      return TEXTURES[this.clampVariant(entity)];
   }

   private int clampVariant(ProjectileLineEntity entity) {
      return Mth.m_14045_(entity.getVariant(), 0, TEXTURES.length);
   }

   static {
      for (int i = 0; i < OVERLAYS.length; i++) {
         OVERLAYS[i] = RenderType.m_110473_(TEXTURES[i]);
      }
   }
}
