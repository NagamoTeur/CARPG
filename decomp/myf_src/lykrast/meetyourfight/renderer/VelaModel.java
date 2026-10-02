package lykrast.meetyourfight.renderer;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.VelaEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class VelaModel extends HumanoidModel<VelaEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("vela"), "main");

   public VelaModel(ModelPart modelPart) {
      super(modelPart);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }
}
