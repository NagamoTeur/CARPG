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

public class Modelmournstone<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelmournstone"), "main");
   public final ModelPart the;
   public final ModelPart body;
   public final ModelPart chains;
   public final ModelPart head;
   public final ModelPart hanging_chains;
   public final ModelPart hanging_chain1;
   public final ModelPart hanging_chain2;
   public final ModelPart hanging_chain3;

   public Modelmournstone(ModelPart root) {
      this.the = root.m_171324_("the");
      this.body = this.the.m_171324_("body");
      this.chains = this.the.m_171324_("chains");
      this.head = this.body.m_171324_("head");
      this.hanging_chains = this.body.m_171324_("hanging_chains");
      this.hanging_chain1 = this.hanging_chains.m_171324_("hanging_chain1");
      this.hanging_chain2 = this.hanging_chains.m_171324_("hanging_chain2");
      this.hanging_chain3 = this.hanging_chains.m_171324_("hanging_chain3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition the = partdefinition.m_171599_("the", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition body = the.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -33.0F, 0.0F)
      );
      PartDefinition phantom = head.m_171599_(
         "phantom",
         CubeListBuilder.m_171558_().m_171514_(64, 0).m_171488_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition hanging_chains = body.m_171599_("hanging_chains", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -25.0F, 0.0F));
      PartDefinition hanging_chain1 = hanging_chains.m_171599_(
         "hanging_chain1",
         CubeListBuilder.m_171558_().m_171514_(5, 40).m_171488_(0.0F, 0.0F, -1.5F, 0.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition chain_r1 = hanging_chain1.m_171599_(
         "chain_r1",
         CubeListBuilder.m_171558_().m_171514_(5, 37).m_171488_(0.0F, -8.0F, -1.5F, 0.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition hanging_chain2 = hanging_chains.m_171599_(
         "hanging_chain2",
         CubeListBuilder.m_171558_().m_171514_(5, 46).m_171480_().m_171488_(0.0F, 0.0F, -1.5F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(-4.0F, 0.0F, 4.0F)
      );
      PartDefinition chain_r2 = hanging_chain2.m_171599_(
         "chain_r2",
         CubeListBuilder.m_171558_().m_171514_(5, 42).m_171488_(0.0F, -9.0F, -1.5F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition hanging_chain3 = hanging_chains.m_171599_(
         "hanging_chain3",
         CubeListBuilder.m_171558_().m_171514_(5, 47).m_171480_().m_171488_(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(4.0F, 0.0F, -3.0F)
      );
      PartDefinition chain_r3 = hanging_chain3.m_171599_(
         "chain_r3",
         CubeListBuilder.m_171558_().m_171514_(5, 43).m_171488_(0.0F, -9.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition chains = the.m_171599_("chains", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -33.0F, 0.0F));
      PartDefinition chains_r1 = chains.m_171599_(
         "chains_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 99).m_171488_(-13.0F, -1.5F, -13.0F, 26.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -13.5F, 0.0F, -3.1416F, 0.829F, 0.0436F)
      );
      PartDefinition chains_r2 = chains.m_171599_(
         "chains_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 99).m_171488_(-13.0F, -0.5F, -13.0F, 26.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.5F, 0.0F, 3.1416F, 0.5672F, -0.2182F)
      );
      PartDefinition chains_r3 = chains.m_171599_(
         "chains_r3",
         CubeListBuilder.m_171558_().m_171514_(0, 99).m_171488_(-13.0F, -1.5F, -13.0F, 26.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 12.5F, 0.0F, 3.1416F, 0.0F, 0.0873F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = ageInTicks / 20.0F + Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.hanging_chains.f_104204_ = ageInTicks / 20.0F + Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.chains.f_104204_ = ageInTicks / -20.0F - Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.hanging_chain2.f_104203_ = -0.17453294F - Mth.m_14031_(ageInTicks / 8.0F) / 6.0F;
      this.hanging_chain3.f_104203_ = (float) (Math.PI / 12) + Mth.m_14031_(ageInTicks / 6.0F) / 4.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.the.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
