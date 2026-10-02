package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class RightVerticalShoulderModel extends DecoModel {
   private final ModelPart part;

   public RightVerticalShoulderModel() {
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_()
         .m_171599_(
            "part",
            CubeListBuilder.m_171558_()
               .m_171481_(-5.0F, -4.0F, -4.0F, 1.0F, 8.0F, 8.0F)
               .m_171481_(-2.5F, -4.0F, -4.0F, 1.0F, 8.0F, 8.0F)
               .m_171481_(0.0F, -4.0F, -4.0F, 1.0F, 8.0F, 8.0F),
            PartPose.f_171404_
         );
      ModelPart model = LayerDefinition.m_171565_(modelData, 32, 16).m_171564_();
      this.part = model.m_171324_("part");
   }

   @Override
   protected Iterable<ModelPart> m_5607_() {
      return Collections.emptyList();
   }

   @Override
   protected Iterable<ModelPart> m_5608_() {
      return Collections.singletonList(this.part);
   }

   @Override
   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.part.m_104315_(model.f_102811_);
      super.copyFromModel(model, slot);
   }
}
