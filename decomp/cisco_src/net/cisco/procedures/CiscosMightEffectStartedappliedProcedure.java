package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class CiscosMightEffectStartedappliedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22109_(new AttributeModifier(UUID.fromString("db44ca47-69fe-4a68-90f4-6a51e04693b8"), "cisco_might", 0.4, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22118_(new AttributeModifier(UUID.fromString("db44ca47-69fe-4a68-90f4-6a51e04693b8"), "cisco_might", 0.4, Operation.MULTIPLY_TOTAL));
         }
      }
   }
}
