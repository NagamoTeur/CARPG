package lykrast.meetyourfight.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.BellringerEntity;
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

public class BellringerModel extends HumanoidModel<BellringerEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("bellringer"), "main");
   private final ModelPart bell;

   public BellringerModel(ModelPart modelPart) {
      super(modelPart);
      this.f_102814_.f_104207_ = false;
      this.f_102809_.f_104207_ = false;
      this.bell = modelPart.m_171324_("bell");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_(
         "right_leg", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-1.0F, -1.0F, -2.0F, 6.0F, 10.0F, 4.0F), PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "bell", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-4.0F, 5.0F, 2.0F, 6.0F, 6.0F, 7.0F), PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   protected Iterable<ModelPart> m_5608_() {
      return Iterables.concat(super.m_5608_(), ImmutableList.of(this.bell));
   }

   public void setupAnim(BellringerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_102811_.f_104203_ = (float) (-Math.PI / 2);
      if (this.f_102608_ > 0.0F) {
         float f = 1.0F - this.f_102608_;
         f *= f;
         f *= f;
         f = 1.0F - f;
         float f1 = Mth.m_14031_(f * (float) Math.PI);
         float f2 = Mth.m_14031_(this.f_102608_ * (float) Math.PI) * -(this.f_102808_.f_104203_ - 0.7F) * 0.75F;
         this.f_102811_.f_104203_ -= f1 * 1.2F + f2;
      }

      this.bell.m_104315_(this.f_102811_);
   }
}
