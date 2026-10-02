package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class HorizontalHeadModel extends DecoHeadModel {
   private final ModelPart part;

   public HorizontalHeadModel() {
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_().m_171599_("part", CubeListBuilder.m_171558_().m_171481_(-10.0F, -17.0F, 0.0F, 20.0F, 12.0F, 0.0F), PartPose.f_171404_);
      ModelPart model = LayerDefinition.m_171565_(modelData, 64, 16).m_171564_();
      this.part = model.m_171324_("part");
   }

   @Override
   ModelPart getPart() {
      return this.part;
   }
}
