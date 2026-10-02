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

public class Modelwater_elemental<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelwater_elemental"), "main");
   public final ModelPart head;
   public final ModelPart big_storm;
   public final ModelPart small_bottom_storm;
   public final ModelPart small_upper_storm;

   public Modelwater_elemental(ModelPart root) {
      this.head = root.m_171324_("head");
      this.big_storm = root.m_171324_("big_storm");
      this.small_bottom_storm = root.m_171324_("small_bottom_storm");
      this.small_upper_storm = root.m_171324_("small_upper_storm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -2.5F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 3.5F, 0.0F)
      );
      PartDefinition big_storm = partdefinition.m_171599_(
         "big_storm",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 16)
            .m_171488_(-9.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 16)
            .m_171488_(6.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 16)
            .m_171488_(-1.5F, -1.5F, 6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.5F, 0.0F)
      );
      PartDefinition small_bottom_storm = partdefinition.m_171599_(
         "small_bottom_storm",
         CubeListBuilder.m_171558_()
            .m_171514_(20, 16)
            .m_171488_(-1.0F, 3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 16)
            .m_171488_(-3.0F, -4.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 16)
            .m_171488_(1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 12.0F, 0.0F)
      );
      PartDefinition small_upper_storm = partdefinition.m_171599_(
         "small_upper_storm",
         CubeListBuilder.m_171558_()
            .m_171514_(20, 16)
            .m_171488_(-7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 16)
            .m_171488_(5.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.big_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.small_bottom_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.small_upper_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.small_upper_storm.f_104204_ = ageInTicks / 10.0F;
      this.small_bottom_storm.f_104204_ = ageInTicks / -10.0F;
      this.big_storm.f_104204_ = Mth.m_14031_(ageInTicks / 12.0F) / 4.0F;
   }
}
