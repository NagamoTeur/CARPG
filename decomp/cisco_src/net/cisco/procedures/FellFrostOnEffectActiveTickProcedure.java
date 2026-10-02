package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class FellFrostOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double fellfrost = 0.0;
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22276_)
            .m_22109_(new AttributeModifier(UUID.fromString("b83dae61-cc0d-4f39-ac9c-2cb55c553ee9"), "fell_frosthp", 0.1, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22276_)
               .m_22118_(new AttributeModifier(UUID.fromString("b83dae61-cc0d-4f39-ac9c-2cb55c553ee9"), "fell_frosthp", 0.1, Operation.MULTIPLY_TOTAL));
         }

         fellfrost = entity instanceof LivingEntity _livEnt ? (double)_livEnt.m_21233_() : -1.0;
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22109_(new AttributeModifier(UUID.fromString("f14549c7-bf67-47fe-9cd5-a67fe1eab61b"), "fell_frost", fellfrost / 14.0, Operation.ADDITION))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22118_(new AttributeModifier(UUID.fromString("f14549c7-bf67-47fe-9cd5-a67fe1eab61b"), "fell_frost", fellfrost / 14.0, Operation.ADDITION));
         }
      }
   }
}
