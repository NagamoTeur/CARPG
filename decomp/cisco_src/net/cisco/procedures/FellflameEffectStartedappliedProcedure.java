package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class FellflameEffectStartedappliedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double fellflamearmor = 0.0;
         double fellfrost = 0.0;
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22109_(new AttributeModifier(UUID.fromString("f456fe81-a575-4177-b151-f36369449805"), "fell_flamearmor", 0.1, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22284_)
               .m_22118_(new AttributeModifier(UUID.fromString("f456fe81-a575-4177-b151-f36369449805"), "fell_flamearmor", 0.1, Operation.MULTIPLY_TOTAL));
         }

         fellflamearmor = entity instanceof LivingEntity _livEnt ? (double)_livEnt.m_21230_() : 0.0;
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22109_(new AttributeModifier(UUID.fromString("443abb39-03a7-40a8-8810-ffcfa3116803"), "fell_flame", fellflamearmor / 14.0, Operation.ADDITION))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22118_(
                  new AttributeModifier(UUID.fromString("443abb39-03a7-40a8-8810-ffcfa3116803"), "fell_flame", fellflamearmor / 14.0, Operation.ADDITION)
               );
         }
      }
   }
}
