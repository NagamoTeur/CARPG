package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class BeltofBracingBaubleIsEquippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22278_)
            .m_22109_(new AttributeModifier(UUID.fromString("f6337b8c-2141-4237-888d-d35f4772422d"), "belt_knockback", 0.5, Operation.ADDITION))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22278_)
               .m_22118_(new AttributeModifier(UUID.fromString("f6337b8c-2141-4237-888d-d35f4772422d"), "belt_knockback", 0.5, Operation.ADDITION));
         }
      }
   }
}
