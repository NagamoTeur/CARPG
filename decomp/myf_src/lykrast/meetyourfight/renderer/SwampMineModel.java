package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.SwampMineEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SwampMineModel extends EntityModel<SwampMineEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("swamp_mine"), "main");
   private final ModelPart spikes;
   private final ModelPart bb_main;

   public SwampMineModel(ModelPart modelPart) {
      this.spikes = modelPart.m_171324_("spikes");
      this.bb_main = modelPart.m_171324_("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_(
         "main", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      PartDefinition spikes = partdefinition.m_171599_("spikes", CubeListBuilder.m_171558_(), PartPose.m_171419_(-6.0F, 10.0F, 6.0F));
      spikes.m_171599_(
         "bottombackright",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(0.0F, 12.0F, 0.0F, -2.3562F, -0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "bottombackleft",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(12.0F, 12.0F, 0.0F, -2.3562F, 0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "bottomfrontright",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(0.0F, 12.0F, -12.0F, 2.3562F, 0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "bottomfrontleft",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(12.0F, 12.0F, -12.0F, 2.3562F, -0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "topbackright",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, -0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "topbackleft",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(12.0F, 0.0F, 0.0F, -0.7854F, 0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "topfrontright",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(0.0F, 0.0F, -12.0F, 0.7854F, 0.7854F, 0.0F)
      );
      spikes.m_171599_(
         "topfrontleft",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F),
         PartPose.m_171423_(12.0F, 0.0F, -12.0F, 0.7854F, -0.7854F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void setupAnim(SwampMineEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.spikes.m_104301_(matrixStack, buffer, packedLight, packedOverlay);
      this.bb_main.m_104301_(matrixStack, buffer, packedLight, packedOverlay);
   }
}
