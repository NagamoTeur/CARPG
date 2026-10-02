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

public class Modelpeeper_fallen<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelpeeper_fallen"), "main");
   public final ModelPart mob;
   public final ModelPart body;
   public final ModelPart leg_right;
   public final ModelPart leg_left;

   public Modelpeeper_fallen(ModelPart root) {
      this.mob = root.m_171324_("mob");
      this.body = this.mob.m_171324_("body");
      this.leg_left = this.mob.m_171324_("leg_left");
      this.leg_right = this.mob.m_171324_("leg_right");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mob = partdefinition.m_171599_("mob", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 17.0F, 4.0F, 1.5708F, 0.0F, -1.5708F));
      PartDefinition body = mob.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -15.0F, -7.0F, 14.0F, 15.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 3.0F, 0.0F)
      );
      PartDefinition leg_right = mob.m_171599_(
         "leg_right",
         CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-4.0F, 3.0F, 0.0F)
      );
      PartDefinition leg_left = mob.m_171599_(
         "leg_left",
         CubeListBuilder.m_171558_().m_171514_(0, 29).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(4.0F, 3.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.leg_left.f_104203_ = Mth.m_14089_(ageInTicks / 8.0F) / 8.0F;
      this.leg_right.f_104203_ = Mth.m_14089_(ageInTicks / 8.0F) / -8.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mob.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
