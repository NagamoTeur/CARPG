package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelgoblin_warrior;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;

public class GoblinWarriorRenderer extends MobRenderer<GoblinWarriorEntity, Modelgoblin_warrior<GoblinWarriorEntity>> {
   public GoblinWarriorRenderer(Context context) {
      super(context, new Modelgoblin_warrior(context.m_174023_(Modelgoblin_warrior.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(GoblinWarriorEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/goblin_warrior.png");
   }
}
