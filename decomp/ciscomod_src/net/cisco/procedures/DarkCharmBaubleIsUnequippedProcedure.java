package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DarkCharmBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22130_(new AttributeModifier(UUID.fromString("50fa845e-0949-4394-8e6f-eda9aa86d965"), "dark_attack", 0.1, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22130_(new AttributeModifier(UUID.fromString("b546fcb0-fdf7-460a-b45c-d906cca89158"), "dark_armor", 0.1, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22285_)
            .m_22130_(new AttributeModifier(UUID.fromString("8b78d30a-bef1-44f9-8e09-742d39a16254"), "dark_armortough", 0.1, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22279_)
            .m_22130_(new AttributeModifier(UUID.fromString("673bb2d1-77bc-460a-9fe8-80e3e0feae21"), "dark_speed", 0.1, Operation.MULTIPLY_BASE));
      }
   }
}
