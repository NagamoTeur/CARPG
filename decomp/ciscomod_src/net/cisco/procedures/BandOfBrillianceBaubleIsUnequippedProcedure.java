package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class BandOfBrillianceBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22276_)
            .m_22130_(new AttributeModifier(UUID.fromString("f314a393-66c9-43fb-a75a-fc79f3169764"), "brilliance_health", 0.2, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22130_(new AttributeModifier(UUID.fromString("d16ef022-8591-46c1-905a-f6c00723df59"), "brilliance_armor", 0.2, Operation.MULTIPLY_TOTAL));
      }
   }
}
