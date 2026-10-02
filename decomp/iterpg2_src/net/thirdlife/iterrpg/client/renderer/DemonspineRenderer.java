package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.client.model.Modeldemonspine;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.procedures.DemonspineShakeProcedure;

public class DemonspineRenderer extends MobRenderer<DemonspineEntity, Modeldemonspine<DemonspineEntity>> {
   public DemonspineRenderer(Context context) {
      super(context, new Modeldemonspine(context.m_174023_(Modeldemonspine.LAYER_LOCATION)), 0.2F);
   }

   public ResourceLocation getTextureLocation(DemonspineEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/spine.png");
   }

   protected boolean isShaking(DemonspineEntity _ent) {
      Level world = _ent.f_19853_;
      double x = _ent.m_20185_();
      double y = _ent.m_20186_();
      double z = _ent.m_20189_();
      return DemonspineShakeProcedure.execute(_ent);
   }
}
