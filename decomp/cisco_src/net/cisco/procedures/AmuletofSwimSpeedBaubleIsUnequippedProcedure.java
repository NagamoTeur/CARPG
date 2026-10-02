package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;

public class AmuletofSwimSpeedBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_((Attribute)ForgeMod.SWIM_SPEED.get())
            .m_22130_(new AttributeModifier(UUID.fromString("b299aa8f-8677-409d-befb-1e52d18536b0"), "amulet_swim", 0.2, Operation.MULTIPLY_TOTAL));
      }
   }
}
