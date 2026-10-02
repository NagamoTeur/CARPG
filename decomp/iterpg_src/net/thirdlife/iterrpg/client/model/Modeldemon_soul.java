package net.thirdlife.iterrpg.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modeldemon_soul<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modeldemon_soul"), "main");
   public final ModelPart head;
   public final ModelPart jaw;

   public Modeldemon_soul(ModelPart root) {
      this.head = root.m_171324_("head");
      this.jaw = root.m_171324_("jaw");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-5.0F, -3.0F, -3.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 15)
            .m_171488_(-3.0F, 4.0F, -3.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 24)
            .m_171488_(5.0F, -1.0F, -1.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 0)
            .m_171488_(7.0F, -6.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 28)
            .m_171488_(-10.0F, -6.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(-10.0F, -1.0F, -1.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 14.0F, -1.0F)
      );
      PartDefinition jaw = partdefinition.m_171599_("jaw", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 21.0F, 0.0F));
      PartDefinition jaw_r1 = jaw.m_171599_(
         "jaw_r1",
         CubeListBuilder.m_171558_().m_171514_(16, 18).m_171488_(-3.0F, 0.0F, -4.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.jaw.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.jaw.f_104203_ = (float) (Math.PI / 12) * Mth.m_14031_(ageInTicks / 2.0F);
   }
}
