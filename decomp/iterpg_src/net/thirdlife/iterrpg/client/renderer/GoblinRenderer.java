package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelgoblin;
import net.thirdlife.iterrpg.entity.GoblinEntity;

public class GoblinRenderer extends MobRenderer<GoblinEntity, Modelgoblin<GoblinEntity>> {
   public GoblinRenderer(Context context) {
      super(context, new Modelgoblin(context.m_174023_(Modelgoblin.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(GoblinEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/goblin.png");
   }
}
