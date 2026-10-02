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

public class Modelair_elemental<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelair_elemental"), "main");
   public final ModelPart mob;
   public final ModelPart head;
   public final ModelPart storm1;
   public final ModelPart storm2;
   public final ModelPart storm3;
   public final ModelPart misc_storm;
   public final ModelPart lightning_big;
   public final ModelPart lightning_small;
   public final ModelPart large_cloud_particle1;
   public final ModelPart large_cloud_particle2;

   public Modelair_elemental(ModelPart root) {
      this.mob = root.m_171324_("mob");
      this.head = this.mob.m_171324_("head");
      this.storm1 = this.mob.m_171324_("storm1");
      this.storm2 = this.mob.m_171324_("storm2");
      this.storm3 = this.mob.m_171324_("storm3");
      this.misc_storm = this.mob.m_171324_("misc_storm");
      this.lightning_big = this.mob.m_171324_("lightning_big");
      this.lightning_small = this.mob.m_171324_("lightning_small");
      this.large_cloud_particle1 = this.storm2.m_171324_("large_cloud_particle1");
      this.large_cloud_particle2 = this.storm2.m_171324_("large_cloud_particle2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mob = partdefinition.m_171599_("mob", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 29.0F, 0.0F));
      PartDefinition head = mob.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -16.0F, 0.0F)
      );
      PartDefinition storm1 = mob.m_171599_("storm1", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -12.0F, 0.0F));
      PartDefinition large_cloud_particle3 = storm1.m_171599_(
         "large_cloud_particle3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition small_cloud_particle2 = storm1.m_171599_(
         "small_cloud_particle2",
         CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(6.0F, -1.0F, 0.0F)
      );
      PartDefinition small_cloud_particle3 = storm1.m_171599_(
         "small_cloud_particle3",
         CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-6.0F, -1.0F, 0.0F)
      );
      PartDefinition storm2 = mob.m_171599_("storm2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -16.0F, 0.0F));
      PartDefinition large_cloud_particle1 = storm2.m_171599_(
         "large_cloud_particle1",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-8.0F, -1.0F, 0.0F)
      );
      PartDefinition large_cloud_particle2 = storm2.m_171599_(
         "large_cloud_particle2",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(8.0F, -1.0F, 0.0F)
      );
      PartDefinition storm3 = mob.m_171599_("storm3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -22.0F, 0.0F));
      PartDefinition large_cloud_particle4 = storm3.m_171599_(
         "large_cloud_particle4",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -1.0F, 0.0F)
      );
      PartDefinition misc_storm = mob.m_171599_(
         "misc_storm",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 4)
            .m_171488_(5.0F, -1.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(-7.0F, -1.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171480_()
            .m_171488_(-1.0F, 4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, -12.0F, 0.0F)
      );
      PartDefinition lightning_big = mob.m_171599_("lightning_big", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -16.0F, 0.0F));
      PartDefinition big_lightning1 = lightning_big.m_171599_(
         "big_lightning1",
         CubeListBuilder.m_171558_().m_171514_(17, 20).m_171480_().m_171488_(-3.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(4.0F, -2.0F, -1.0F)
      );
      PartDefinition big_lightning2 = lightning_big.m_171599_(
         "big_lightning2",
         CubeListBuilder.m_171558_().m_171514_(17, 17).m_171480_().m_171488_(-3.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(-5.0F, 2.0F, -1.0F)
      );
      PartDefinition lightning_small = mob.m_171599_("lightning_small", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -16.0F, 0.0F));
      PartDefinition small_lightning1 = lightning_small.m_171599_(
         "small_lightning1",
         CubeListBuilder.m_171558_().m_171514_(1, 26).m_171480_().m_171488_(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(-8.5F, -2.0F, 1.0F)
      );
      PartDefinition small_lightning2 = lightning_small.m_171599_(
         "small_lightning2",
         CubeListBuilder.m_171558_().m_171514_(1, 23).m_171480_().m_171488_(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(8.5F, 2.0F, 1.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.misc_storm.f_104204_ = ageInTicks / 6.0F;
      this.storm1.f_104204_ = ageInTicks / -5.0F;
      this.storm2.f_104204_ = ageInTicks / 4.0F;
      this.large_cloud_particle1.f_104204_ = ageInTicks / -4.0F;
      this.large_cloud_particle2.f_104204_ = ageInTicks / -4.0F;
      this.storm3.f_104204_ = ageInTicks / 4.0F;
      this.lightning_big.f_104205_ = ageInTicks / 6.0F;
      this.lightning_small.f_104205_ = ageInTicks / -6.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mob.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
