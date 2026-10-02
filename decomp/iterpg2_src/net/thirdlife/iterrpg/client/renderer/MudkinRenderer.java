package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelmudkin;
import net.thirdlife.iterrpg.entity.MudkinEntity;

public class MudkinRenderer extends MobRenderer<MudkinEntity, Modelmudkin<MudkinEntity>> {
   public MudkinRenderer(Context context) {
      super(context, new Modelmudkin(context.m_174023_(Modelmudkin.LAYER_LOCATION)), 0.75F);
   }

   public ResourceLocation getTextureLocation(MudkinEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/witchmud_golem.png");
   }
}
