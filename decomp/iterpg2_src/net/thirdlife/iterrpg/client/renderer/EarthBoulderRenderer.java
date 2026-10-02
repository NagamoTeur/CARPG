package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.client.model.ModelEarthBoulder;
import net.thirdlife.iterrpg.entity.EarthBoulderEntity;
import net.thirdlife.iterrpg.procedures.DemonspineShakeProcedure;

public class EarthBoulderRenderer extends MobRenderer<EarthBoulderEntity, ModelEarthBoulder<EarthBoulderEntity>> {
   public EarthBoulderRenderer(Context context) {
      super(context, new ModelEarthBoulder(context.m_174023_(ModelEarthBoulder.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(EarthBoulderEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/earthboulder.png");
   }

   protected boolean isShaking(EarthBoulderEntity _ent) {
      Level world = _ent.f_19853_;
      double x = _ent.m_20185_();
      double y = _ent.m_20186_();
      double z = _ent.m_20189_();
      return DemonspineShakeProcedure.execute(_ent);
   }
}
