package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class MagmanumBurnProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
            entity.m_6469_(DamageSource.f_19309_, 1.0F);
         }
      }
   }
}
