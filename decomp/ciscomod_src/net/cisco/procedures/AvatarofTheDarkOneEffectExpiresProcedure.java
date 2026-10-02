package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class AvatarofTheDarkOneEffectExpiresProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double avatar = 0.0;
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22130_(new AttributeModifier(UUID.fromString("42ef2b00-071d-4d25-b4cd-444a595dd45f"), "avatarhp", 0.7, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22130_(new AttributeModifier(UUID.fromString("570f3344-5de4-4f6f-a685-992dc6b191c0"), "avatar", avatar, Operation.ADDITION));
      }
   }
}
