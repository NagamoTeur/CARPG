package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class AmuletofVitalityBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22276_)
            .m_22130_(new AttributeModifier(UUID.fromString("a18d1975-bd0d-4c23-bc77-599d4bafeb9e"), "amulet_health", 10.0, Operation.ADDITION));
      }
   }
}
