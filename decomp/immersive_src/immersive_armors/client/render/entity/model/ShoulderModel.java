package immersive_armors.client.render.entity.model;

import java.util.Arrays;
import java.util.Collections;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class ShoulderModel extends DecoModel {
   private final ModelPart left;
   private final ModelPart right;

   public ShoulderModel() {
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_()
         .m_171599_("left", CubeListBuilder.m_171558_(), PartPose.f_171404_)
         .m_171599_(
            "left",
            CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-0.5F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F),
            PartPose.m_171423_(5.0F, -1.0F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 8))
         );
      modelData.m_171576_()
         .m_171599_("right", CubeListBuilder.m_171558_(), PartPose.f_171404_)
         .m_171599_(
            "right",
            CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-0.5F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F),
            PartPose.m_171423_(-5.0F, -1.0F, 0.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 8))
         );
      ModelPart model = LayerDefinition.m_171565_(modelData, 16, 16).m_171564_();
      this.left = model.m_171324_("left");
      this.right = model.m_171324_("right");
   }

   @Override
   protected Iterable<ModelPart> m_5607_() {
      return Collections.emptyList();
   }

   @Override
   protected Iterable<ModelPart> m_5608_() {
      return Arrays.asList(this.left, this.right);
   }

   @Override
   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.left.m_104315_(model.f_102812_);
      this.right.m_104315_(model.f_102811_);
      super.copyFromModel(model, slot);
   }
}
