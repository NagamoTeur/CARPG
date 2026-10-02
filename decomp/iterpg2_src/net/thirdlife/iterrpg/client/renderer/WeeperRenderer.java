package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelweeper;
import net.thirdlife.iterrpg.entity.WeeperEntity;

public class WeeperRenderer extends MobRenderer<WeeperEntity, Modelweeper<WeeperEntity>> {
   public WeeperRenderer(Context context) {
      super(context, new Modelweeper(context.m_174023_(Modelweeper.LAYER_LOCATION)), 0.6F);
   }

   public ResourceLocation getTextureLocation(WeeperEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/weeper.png");
   }
}
