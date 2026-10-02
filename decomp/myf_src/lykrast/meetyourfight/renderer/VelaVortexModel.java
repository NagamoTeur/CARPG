package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.VelaVortexEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class VelaVortexModel extends EntityModel<VelaVortexEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("vortex"), "main");
   private final ModelPart bb_main;

   public VelaVortexModel(ModelPart modelPart) {
      this.bb_main = modelPart.m_171324_("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition bb_main = partdefinition.m_171599_(
         "main", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      bb_main.m_171599_(
         "60deg_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -1.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.0F, -1.0472F, 0.0F)
      );
      bb_main.m_171599_(
         "30deg_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -1.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.05F)),
         PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.0F, -0.5236F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void setupAnim(VelaVortexEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.bb_main.m_104301_(matrixStack, buffer, packedLight, packedOverlay);
   }
}
