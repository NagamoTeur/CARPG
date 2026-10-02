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

public class Modelvoid_portal<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelvoid_portal"), "main");
   public final ModelPart base;
   public final ModelPart rift;
   public final ModelPart vortex;

   public Modelvoid_portal(ModelPart root) {
      this.base = root.m_171324_("base");
      this.rift = this.base.m_171324_("rift");
      this.vortex = this.base.m_171324_("vortex");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition base = partdefinition.m_171599_("base", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 17.0F, 0.0F));
      PartDefinition vortex = base.m_171599_(
         "vortex",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -7.0F, 0.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition rift = base.m_171599_(
         "rift",
         CubeListBuilder.m_171558_().m_171514_(0, 14).m_171488_(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -0.1F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.base.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.base.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.base.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.vortex.f_104205_ = ageInTicks / 8.0F;
      this.rift.f_104205_ = ageInTicks / -8.0F;
   }
}
