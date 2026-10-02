package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.ModelHobGoblin;
import net.thirdlife.iterrpg.entity.HobgoblinEntity;

public class HobgoblinRenderer extends MobRenderer<HobgoblinEntity, ModelHobGoblin<HobgoblinEntity>> {
   public HobgoblinRenderer(Context context) {
      super(context, new ModelHobGoblin(context.m_174023_(ModelHobGoblin.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(HobgoblinEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/hobgoblin.png");
   }
}
