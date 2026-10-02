package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.SwampjawEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SwampjawModel extends EntityModel<SwampjawEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("swampjaw"), "main");
   private final ModelPart bodyMain;
   private final ModelPart finRight;
   private final ModelPart finLeft;
   private final ModelPart tailfinBottom;
   private final ModelPart tailfinTop;
   private final ModelPart tailOuter;
   private final ModelPart tailInner;
   private final ModelPart head;
   private static final float TAILFIN_PITCH = 0.2618F;
   private float tailYaw;
   private float tailPitch;

   public SwampjawModel(ModelPart modelPart) {
      this.bodyMain = modelPart.m_171324_("body");
      this.finRight = this.bodyMain.m_171324_("fin_right");
      this.finLeft = this.bodyMain.m_171324_("fin_left");
      this.tailInner = this.bodyMain.m_171324_("tail_inner");
      this.tailOuter = this.tailInner.m_171324_("tail_outer");
      this.tailfinTop = this.tailOuter.m_171324_("tail_fin_top");
      this.tailfinBottom = this.tailOuter.m_171324_("tail_fin_bottom");
      this.head = this.bodyMain.m_171324_("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_(
         "body", CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(-6.0F, -10.0F, -6.0F, 12.0F, 10.0F, 12.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      body.m_171599_(
         "fin_right",
         CubeListBuilder.m_171558_().m_171514_(0, 28).m_171481_(-8.0F, 0.0F, -2.0F, 8.0F, 1.0F, 4.0F),
         PartPose.m_171423_(-6.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.4363F)
      );
      body.m_171599_(
         "fin_left",
         CubeListBuilder.m_171558_().m_171514_(0, 28).m_171481_(0.0F, 0.0F, -2.0F, 8.0F, 1.0F, 4.0F),
         PartPose.m_171423_(6.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.4363F)
      );
      PartDefinition tailInner = body.m_171599_(
         "tail_inner", CubeListBuilder.m_171558_().m_171514_(40, 22).m_171481_(-5.0F, 0.0F, 0.0F, 10.0F, 8.0F, 8.0F), PartPose.m_171419_(0.0F, -10.0F, 6.0F)
      );
      PartDefinition tailOuter = tailInner.m_171599_(
         "tail_outer", CubeListBuilder.m_171558_().m_171514_(40, 38).m_171481_(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 6.0F), PartPose.m_171419_(0.0F, 0.0F, 8.0F)
      );
      tailOuter.m_171599_(
         "tail_fin_top",
         CubeListBuilder.m_171558_().m_171514_(0, 33).m_171481_(-0.5F, -10.0F, 0.0F, 1.0F, 10.0F, 5.0F),
         PartPose.m_171423_(0.0F, 1.0F, 5.0F, -0.2618F, 0.0F, 0.0F)
      );
      tailOuter.m_171599_(
         "tail_fin_bottom",
         CubeListBuilder.m_171558_().m_171514_(12, 33).m_171488_(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 5.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(0.0F, 1.0F, 5.0F, 0.2618F, 0.0F, 0.0F)
      );
      PartDefinition head = body.m_171599_(
         "head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-5.0F, -4.0F, -10.0F, 10.0F, 6.0F, 10.0F), PartPose.m_171419_(0.0F, -5.0F, -6.0F)
      );
      head.m_171599_(
         "jaw", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-5.0F, 0.0F, -10.0F, 10.0F, 2.0F, 10.0F), PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void prepareMobModel(SwampjawEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
      super.m_6839_(entity, limbSwing, limbSwingAmount, partialTick);
      this.tailYaw = Mth.m_14118_(entity.m_146908_(), entity.getTailYaw(partialTick)) / 3.0F;
      this.tailPitch = -Mth.m_14118_(entity.m_146909_(), entity.getTailPitch(partialTick)) / 1.5F;
      this.tailYaw *= (float) (Math.PI / 180.0);
      this.tailPitch *= (float) (Math.PI / 180.0);
   }

   public void setupAnim(SwampjawEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104203_ = headPitch * (float) (Math.PI / 180.0);
      this.head.f_104204_ = netHeadYaw * (float) (Math.PI / 180.0);
      this.finLeft.f_104204_ = Mth.m_14089_(limbSwing * 0.2F) * 0.8F * limbSwingAmount;
      this.finRight.f_104204_ = Mth.m_14089_(limbSwing * 0.2F + (float) Math.PI) * 0.8F * limbSwingAmount;
      this.tailInner.f_104203_ = this.tailPitch;
      this.tailInner.f_104204_ = this.tailYaw + Mth.m_14089_(limbSwing * 0.35F) * 0.15F * limbSwingAmount;
      this.tailOuter.f_104203_ = this.tailPitch;
      this.tailOuter.f_104204_ = this.tailYaw + Mth.m_14089_(limbSwing * 0.35F + (float) (Math.PI / 3)) * 0.15F * limbSwingAmount;
      this.tailfinTop.f_104204_ = this.tailYaw + Mth.m_14089_(limbSwing * 0.35F + (float) (Math.PI * 2.0 / 3.0)) * 0.15F * limbSwingAmount;
      this.tailfinBottom.f_104204_ = this.tailfinTop.f_104204_;
   }

   public void m_7695_(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.bodyMain.m_104301_(matrixStack, buffer, packedLight, packedOverlay);
   }

   public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
      modelRenderer.f_104203_ = x;
      modelRenderer.f_104204_ = y;
      modelRenderer.f_104205_ = z;
   }
}
