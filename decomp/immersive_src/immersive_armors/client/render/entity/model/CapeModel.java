package immersive_armors.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapeModel<T extends LivingEntity> extends EntityModel<T> {
   private final ModelPart cape;

   public CapeModel() {
      MeshDefinition modelData = new MeshDefinition();
      modelData.m_171576_().m_171599_("cape", CubeListBuilder.m_171558_().m_171481_(-5.0F, 0.0F, -2.0F, 10.0F, 16.0F, 1.0F), PartPose.f_171404_);
      ModelPart model = LayerDefinition.m_171565_(modelData, 32, 32).m_171564_();
      this.cape = model.m_171324_("cape");
   }

   public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
   }

   public void m_7695_(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      this.cape.m_104306_(matrices, vertices, light, overlay, red, green, blue, alpha);
   }
}
