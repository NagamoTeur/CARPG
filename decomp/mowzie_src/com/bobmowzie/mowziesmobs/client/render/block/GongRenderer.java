package com.bobmowzie.mowziesmobs.client.render.block;

import com.bobmowzie.mowziesmobs.client.model.LayerHandler;
import com.bobmowzie.mowziesmobs.server.block.entity.GongBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GongRenderer implements BlockEntityRenderer<GongBlockEntity> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/block/gong.png");
   private final ModelPart gongBase;
   private final ModelPart chain;

   public GongRenderer(Context context) {
      ModelPart modelpart = context.m_173582_(LayerHandler.GONG_LAYER);
      this.gongBase = modelpart.m_171324_("root");
      this.chain = this.gongBase.m_171324_("chain");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition root = partdefinition.m_171599_(
         "root",
         CubeListBuilder.m_171558_()
            .m_171514_(69, 68)
            .m_171488_(-35.75F, -23.25F, 5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 65)
            .m_171488_(0.25F, -57.25F, 7.0F, 4.0F, 34.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(69, 85)
            .m_171488_(-1.75F, -59.25F, 5.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 69)
            .m_171488_(-1.75F, -23.25F, 5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 68)
            .m_171488_(-33.75F, -57.25F, 7.0F, 4.0F, 34.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 86)
            .m_171488_(-35.75F, -59.25F, 5.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 17)
            .m_171488_(-37.75F, -56.25F, 7.5F, 46.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-38.75F, -63.25F, 3.0F, 48.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 23)
            .m_171488_(-27.75F, -59.25F, 8.5F, 26.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(14.75F, 39.25F, -8.5F)
      );
      PartDefinition chain = root.m_171599_(
         "chain",
         CubeListBuilder.m_171558_().m_171514_(51, 27).m_171488_(-11.0F, 0.0F, 0.0F, 22.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-14.75F, -54.25F, 8.5F)
      );
      PartDefinition gong = chain.m_171599_(
         "gong",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 24)
            .m_171488_(-11.75F, -11.75F, -1.0F, 22.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 36)
            .m_171488_(-3.75F, -3.75F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 47)
            .m_171488_(-9.75F, -9.75F, -1.0F, 18.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 49)
            .m_171488_(-9.75F, -9.75F, -0.5F, 18.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.75F, 18.75F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void render(GongBlockEntity entity, float delta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay) {
      poseStack.m_85836_();
      poseStack.m_85837_(0.5, 1.485, 0.5);
      poseStack.m_85845_(new Quaternion(0.0F, 0.0F, 180.0F, true));
      if (entity.facing.m_122434_() == Axis.X) {
         poseStack.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
      }

      float f = (float)entity.ticks + delta;
      float f1 = 0.0F;
      if (entity.shaking) {
         float f3 = Mth.m_14031_(f / (float) Math.PI) / (4.0F + f / 2.0F);
         if (entity.clickDirection == Direction.NORTH) {
            f1 = f3;
         } else if (entity.clickDirection == Direction.SOUTH) {
            f1 = -f3;
         } else if (entity.clickDirection == Direction.EAST) {
            f1 = f3;
         } else if (entity.clickDirection == Direction.WEST) {
            f1 = -f3;
         }
      }

      this.chain.f_104203_ = f1;
      VertexConsumer vertexconsumer = buffer.m_6299_(RenderType.m_110458_(TEXTURE));
      this.gongBase.m_104301_(poseStack, vertexconsumer, packedLight, overlay);
      poseStack.m_85849_();
   }
}
