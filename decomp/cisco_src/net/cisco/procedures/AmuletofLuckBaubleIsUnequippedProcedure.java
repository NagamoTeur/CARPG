package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class AmuletofLuckBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22286_)
            .m_22130_(new AttributeModifier(UUID.fromString("c0daa9f9-0161-46b6-aebb-2d9edb21ff4c"), "amulet_luck", 5.0, Operation.ADDITION));
      }
   }
}
