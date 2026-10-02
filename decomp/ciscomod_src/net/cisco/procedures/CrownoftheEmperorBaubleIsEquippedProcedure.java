package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class CrownoftheEmperorBaubleIsEquippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22109_(new AttributeModifier(UUID.fromString("b8dc8eb0-c039-4e88-9332-98cafb06edc3"), "crown_armor", 6.0, Operation.ADDITION))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22284_)
               .m_22118_(new AttributeModifier(UUID.fromString("b8dc8eb0-c039-4e88-9332-98cafb06edc3"), "crown_armor", 6.0, Operation.ADDITION));
         }
      }
   }
}
