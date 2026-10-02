package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class BeltofBracingBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22278_)
            .m_22130_(new AttributeModifier(UUID.fromString("f6337b8c-2141-4237-888d-d35f4772422d"), "belt_knockback", 0.5, Operation.ADDITION));
      }
   }
}
