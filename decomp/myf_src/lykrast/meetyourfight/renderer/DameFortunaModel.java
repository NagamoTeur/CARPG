package lykrast.meetyourfight.renderer;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DameFortunaModel extends HumanoidModel<DameFortunaEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("dame_fortuna"), "main");
   public float headProgress;

   public DameFortunaModel(ModelPart modelPart) {
      super(modelPart);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_(
         "head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.m_171419_(0.0F, -8.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void prepareMobModel(DameFortunaEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      this.headProgress = entityIn.getHeadRotationProgress(partialTick);
      super.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTick);
   }

   public void setupAnim(DameFortunaEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float headX = this.f_102808_.f_104203_;
      float headY = this.f_102808_.f_104204_;
      float headZ = this.f_102808_.f_104205_;
      super.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_102808_.f_104201_ = -8.0F + Mth.m_14031_(ageInTicks * (float) Math.PI / 50.0F);
      this.f_102808_.f_104203_ = this.m_102835_(this.headProgress, headX, (float)entityIn.headTargetPitch * (float) (Math.PI / 2));
      this.f_102808_.f_104204_ = this.m_102835_(this.headProgress, headY, (float)entityIn.headTargetYaw * (float) (Math.PI / 2));
      this.f_102808_.f_104205_ = this.m_102835_(this.headProgress, headZ, (float)entityIn.headTargetRoll * (float) (Math.PI / 2));
      int attack = entityIn.getAttack();
      if (attack == 1) {
         this.f_102812_.f_104202_ = 0.0F;
         this.f_102812_.f_104200_ = 5.0F;
         this.f_102812_.f_104203_ = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.f_102812_.f_104205_ = (float) (-Math.PI * 3.0 / 4.0);
         this.f_102812_.f_104204_ = 0.0F;
      } else if (attack == 2) {
         this.f_102811_.f_104202_ = 0.0F;
         this.f_102811_.f_104200_ = -5.0F;
         this.f_102811_.f_104203_ = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.f_102811_.f_104205_ = (float) (Math.PI * 3.0 / 4.0);
         this.f_102811_.f_104204_ = 0.0F;
      }
   }
}
