package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RoseSpiritEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class RoseSpiritModel extends EntityModel<RoseSpiritEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("rose_spirit"), "main");
   private final ModelPart blob;
   private final ModelPart hat;
   private final ModelPart bb_main;

   public RoseSpiritModel(ModelPart root) {
      this.blob = root.m_171324_("blob");
      this.hat = root.m_171324_("hat");
      this.bb_main = root.m_171324_("bb_main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_(
         "blob",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 18.0F, 0.0F)
      );
      PartDefinition hat = partdefinition.m_171599_(
         "hat",
         CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 14.0F, 0.0F)
      );
      hat.m_171599_(
         "top",
         CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "bb_main",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(RoseSpiritEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float partial = ageInTicks - (float)entity.f_19797_;
      float animProgress = entity.getAnimProgress(partial);
      int status = entity.prevStatus;
      switch (status) {
         case 0:
         default:
            this.blob.f_104201_ = 18.0F;
            this.hat.f_104201_ = 14.0F;
            break;
         case 1:
            this.blob.f_104201_ = 18.0F - 8.0F * animProgress;
            this.hat.f_104201_ = 14.0F - 8.0F * animProgress;
            break;
         case 2:
         case 3:
         case 5:
            this.blob.f_104201_ = 10.0F;
            this.hat.f_104201_ = 6.0F;
            break;
         case 4:
            this.blob.f_104201_ = 10.0F + 8.0F * animProgress;
            this.hat.f_104201_ = 6.0F + 8.0F * animProgress;
            break;
         case 6:
            if ((double)animProgress < 0.5) {
               this.blob.f_104201_ = 10.0F + 16.0F * animProgress;
            } else {
               this.blob.f_104201_ = 18.0F;
            }

            this.hat.f_104201_ = 6.0F + 8.0F * animProgress * animProgress;
      }
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.blob.m_104301_(poseStack, buffer, packedLight, packedOverlay);
      this.hat.m_104301_(poseStack, buffer, packedLight, packedOverlay);
      this.bb_main.m_104301_(poseStack, buffer, packedLight, packedOverlay);
   }
}
