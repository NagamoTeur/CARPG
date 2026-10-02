package lykrast.meetyourfight.renderer;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RosalyneArmorLayer extends EnergySwirlLayer<RosalyneEntity, RosalyneModel> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/rosalyne_armor.png");
   private final RosalyneModel model;

   public RosalyneArmorLayer(RenderLayerParent<RosalyneEntity, RosalyneModel> parent, EntityModelSet modelSet) {
      super(parent);
      this.model = new RosalyneModel(modelSet.m_171103_(RosalyneModel.MODEL_ARMOR));
   }

   protected float m_7631_(float ticks) {
      return Mth.m_14089_(ticks * 0.02F) * 2.0F;
   }

   protected ResourceLocation m_7029_() {
      return TEXTURE;
   }

   protected EntityModel<RosalyneEntity> m_7193_() {
      return this.model;
   }
}
