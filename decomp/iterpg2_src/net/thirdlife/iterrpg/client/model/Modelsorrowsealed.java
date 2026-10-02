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

public class Modelsorrowsealed<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelsorrowsealed"), "main");
   public final ModelPart head;
   public final ModelPart chain1;
   public final ModelPart chain2;
   public final ModelPart chain3;
   public final ModelPart chain4;

   public Modelsorrowsealed(ModelPart root) {
      this.head = root.m_171324_("head");
      this.chain1 = root.m_171324_("chain1");
      this.chain2 = root.m_171324_("chain2");
      this.chain3 = root.m_171324_("chain3");
      this.chain4 = root.m_171324_("chain4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-32.0F, -64.0F, -32.0F, 64.0F, 64.0F, 64.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      PartDefinition chain1 = partdefinition.m_171599_("chain1", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition chain_r1 = chain1.m_171599_(
         "chain_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 129).m_171488_(-31.6F, -55.0F, -34.0F, 68.0F, 8.0F, 68.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F)
      );
      PartDefinition chain2 = partdefinition.m_171599_("chain2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition chain_r2 = chain2.m_171599_(
         "chain_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 129).m_171488_(-36.6F, -14.0F, -34.0F, 68.0F, 8.0F, 68.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition chain3 = partdefinition.m_171599_("chain3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition chain_r3 = chain3.m_171599_(
         "chain_r3",
         CubeListBuilder.m_171558_().m_171514_(0, 129).m_171488_(-34.6F, -17.0F, -66.0F, 68.0F, 8.0F, 68.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, 0.1309F, 0.0F)
      );
      PartDefinition chain4 = partdefinition.m_171599_("chain4", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition chain_r4 = chain4.m_171599_(
         "chain_r4",
         CubeListBuilder.m_171558_().m_171514_(0, 129).m_171488_(-37.6F, 11.0F, -66.0F, 68.0F, 8.0F, 68.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, -0.2182F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 512, 512);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.chain1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.chain2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.chain3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.chain4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
