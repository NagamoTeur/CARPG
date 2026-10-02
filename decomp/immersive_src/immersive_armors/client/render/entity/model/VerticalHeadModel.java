package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class VerticalHeadModel extends DecoHeadModel {
   private final ModelPart part;

   public VerticalHeadModel() {
      this(0.0F, 0.0F, 0.0F);
   }

   public VerticalHeadModel(float x, float y, float z) {
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_().m_171599_("part", CubeListBuilder.m_171558_().m_171481_(0.0F, -17.0F, -10.0F, 0.0F, 12.0F, 20.0F), PartPose.m_171419_(x, y, z));
      ModelPart model = LayerDefinition.m_171565_(modelData, 64, 16).m_171564_();
      this.part = model.m_171324_("part");
   }

   @Override
   ModelPart getPart() {
      return this.part;
   }
}
