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
import net.minecraft.world.entity.Entity;

public class Modelearth_elemental<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelearth_elemental"), "main");
   public final ModelPart head;
   public final ModelPart small_storm;
   public final ModelPart medium_storm;
   public final ModelPart big_storm;

   public Modelearth_elemental(ModelPart root) {
      this.head = root.m_171324_("head");
      this.small_storm = root.m_171324_("small_storm");
      this.medium_storm = root.m_171324_("medium_storm");
      this.big_storm = root.m_171324_("big_storm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 10.0F, 0.0F)
      );
      PartDefinition branch_left_r1 = head.m_171599_(
         "branch_left_r1",
         CubeListBuilder.m_171558_().m_171514_(14, 16).m_171488_(0.0F, -3.5F, 0.0F, 7.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(4.0F, -2.5F, 0.0F, 0.0F, -0.48F, 0.0F)
      );
      PartDefinition branch_right_r1 = head.m_171599_(
         "branch_right_r1",
         CubeListBuilder.m_171558_().m_171514_(14, 16).m_171480_().m_171488_(-7.0F, -3.5F, 0.0F, 7.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-4.0F, -2.5F, 0.0F, 0.0F, 0.48F, 0.0F)
      );
      PartDefinition small_storm = partdefinition.m_171599_("small_storm", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 10.0F, 0.0F));
      PartDefinition small_particle1 = small_storm.m_171599_(
         "small_particle1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-6.0F, 6.0F, 0.0F)
      );
      PartDefinition small_particle2 = small_storm.m_171599_(
         "small_particle2",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(6.0F, 6.0F, 0.0F)
      );
      PartDefinition small_particle3 = small_storm.m_171599_(
         "small_particle3",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(6.0F, -6.0F, 0.0F)
      );
      PartDefinition small_particle4 = small_storm.m_171599_(
         "small_particle4",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-6.0F, -6.0F, 0.0F)
      );
      PartDefinition medium_storm = partdefinition.m_171599_("medium_storm", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 10.0F, 0.0F));
      PartDefinition medium_particle1 = medium_storm.m_171599_(
         "medium_particle1",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(7.5F, 0.0F, 0.0F)
      );
      PartDefinition medium_particle2 = medium_storm.m_171599_(
         "medium_particle2",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-7.5F, 0.0F, 0.0F)
      );
      PartDefinition medium_particle3 = medium_storm.m_171599_(
         "medium_particle3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 7.5F, 0.0F)
      );
      PartDefinition medium_particle4 = medium_storm.m_171599_(
         "medium_particle4",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -7.5F, 0.0F)
      );
      PartDefinition big_storm = partdefinition.m_171599_("big_storm", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 10.0F, 0.0F));
      PartDefinition big_particle1 = big_storm.m_171599_(
         "big_particle1",
         CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(11.5F, -3.0F, 0.0F)
      );
      PartDefinition big_particle2 = big_storm.m_171599_(
         "big_particle2",
         CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, -11.5F)
      );
      PartDefinition big_particle3 = big_storm.m_171599_(
         "big_particle3",
         CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-11.5F, 3.0F, 0.0F)
      );
      PartDefinition big_particle4 = big_storm.m_171599_(
         "big_particle4",
         CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -1.0F, 11.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.small_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.medium_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.big_storm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.small_storm.f_104205_ = ageInTicks / 10.0F;
      this.medium_storm.f_104203_ = ageInTicks / 10.0F;
      this.medium_storm.f_104205_ = ageInTicks / -10.0F;
      this.big_storm.f_104204_ = ageInTicks / 10.0F;
   }
}
