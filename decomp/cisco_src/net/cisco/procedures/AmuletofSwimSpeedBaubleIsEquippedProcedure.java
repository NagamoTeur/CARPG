package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;

public class AmuletofSwimSpeedBaubleIsEquippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_((Attribute)ForgeMod.SWIM_SPEED.get())
            .m_22109_(new AttributeModifier(UUID.fromString("b299aa8f-8677-409d-befb-1e52d18536b0"), "amulet_swim", 0.2, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_((Attribute)ForgeMod.SWIM_SPEED.get())
               .m_22118_(new AttributeModifier(UUID.fromString("b299aa8f-8677-409d-befb-1e52d18536b0"), "amulet_swim", 0.2, Operation.MULTIPLY_TOTAL));
         }
      }
   }
}
