package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.client.model.Modelforest_vines;
import net.thirdlife.iterrpg.entity.ForestVinesEntity;
import net.thirdlife.iterrpg.procedures.DemonspineShakeProcedure;

public class ForestVinesRenderer extends MobRenderer<ForestVinesEntity, Modelforest_vines<ForestVinesEntity>> {
   public ForestVinesRenderer(Context context) {
      super(context, new Modelforest_vines(context.m_174023_(Modelforest_vines.LAYER_LOCATION)), 0.2F);
   }

   public ResourceLocation getTextureLocation(ForestVinesEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/forest_vines.png");
   }

   protected boolean isShaking(ForestVinesEntity _ent) {
      Level world = _ent.f_19853_;
      double x = _ent.m_20185_();
      double y = _ent.m_20186_();
      double z = _ent.m_20189_();
      return DemonspineShakeProcedure.execute(_ent);
   }
}
