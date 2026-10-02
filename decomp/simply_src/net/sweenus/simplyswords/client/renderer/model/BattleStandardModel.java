package net.sweenus.simplyswords.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sweenus.simplyswords.entity.BattleStandardEntity;

public class BattleStandardModel extends EntityModel<BattleStandardEntity> {
   private final ModelPart supports;
   private final ModelPart bb_main;

   public BattleStandardModel(ModelPart root) {
      this.supports = root.m_171324_("supports");
      this.bb_main = root.m_171324_("bb_main");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition modelPartData = modelData.m_171576_();
      PartDefinition supports = modelPartData.m_171599_(
         "supports",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 17)
            .m_171488_(0.0F, -30.0F, 0.0F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 17)
            .m_171488_(0.0F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 17)
            .m_171488_(-4.0F, -28.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 2)
            .m_171488_(-2.0F, -22.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 0)
            .m_171488_(1.0F, -22.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 17)
            .m_171488_(1.0F, -28.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      PartDefinition bb_main = modelPartData.m_171599_(
         "bb_main",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -29.75F, -0.25F, 9.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(modelData, 64, 64);
   }

   public void m_7695_(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
      this.supports.m_104306_(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
      this.bb_main.m_104306_(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
   }

   public void setAngles(BattleStandardEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
   }
}
