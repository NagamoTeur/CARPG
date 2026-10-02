package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;

public class ShouldDamageProcedure {
   public static boolean execute(Entity entity, Entity sourceentity) {
      return entity != null && sourceentity != null
         ? (!(entity instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
            && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
            && sourceentity != entity
         : false;
   }
}
