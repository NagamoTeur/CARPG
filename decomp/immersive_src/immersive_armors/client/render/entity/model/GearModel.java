package immersive_armors.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class GearModel extends DecoModel {
   private final String attachTo;
   private final ModelPart part;

   public GearModel(String to, int size) {
      this.attachTo = to;
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_()
         .m_171599_(
            "part",
            CubeListBuilder.m_171558_().m_171481_((float)(-size) / 2.0F, (float)(-size) / 2.0F, 0.0F, (float)size, (float)size, 0.0F),
            PartPose.f_171404_
         );
      ModelPart model = LayerDefinition.m_171565_(modelData, 16, 8).m_171564_();
      this.part = model.m_171324_("part");
   }

   public void m_7695_(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      this.part.m_104306_(matrices, vertices, light, overlay, red, green, blue, alpha);
   }

   @Override
   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.part.m_104315_(getModelPart(model, this.attachTo));
      super.copyFromModel(model, slot);
   }

   public String getAttachTo() {
      return this.attachTo;
   }

   public ModelPart getPart() {
      return this.part;
   }
}
