package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class CiscoRageOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22109_(new AttributeModifier(UUID.fromString("0890c51b-1c23-48aa-a3f3-25a155bbc357"), "rage_armor", 0.5, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22284_)
               .m_22118_(new AttributeModifier(UUID.fromString("0890c51b-1c23-48aa-a3f3-25a155bbc357"), "rage_armor", 0.5, Operation.MULTIPLY_TOTAL));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22109_(new AttributeModifier(UUID.fromString("b549087e-179b-4b8d-83b1-00bec9f8a3a6"), "rage_attack", 0.5, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22118_(new AttributeModifier(UUID.fromString("b549087e-179b-4b8d-83b1-00bec9f8a3a6"), "rage_attack", 0.5, Operation.MULTIPLY_TOTAL));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22285_)
            .m_22109_(new AttributeModifier(UUID.fromString("5e60e364-34b3-4e7e-8234-a4679eabb283"), "rage_toughness", 0.5, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22285_)
               .m_22118_(new AttributeModifier(UUID.fromString("5e60e364-34b3-4e7e-8234-a4679eabb283"), "rage_toughness", 0.5, Operation.MULTIPLY_TOTAL));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22279_)
            .m_22109_(new AttributeModifier(UUID.fromString("96ad45af-7478-4386-bea7-982ad672d5f7"), "rage_speed", 0.6, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22279_)
               .m_22118_(new AttributeModifier(UUID.fromString("96ad45af-7478-4386-bea7-982ad672d5f7"), "rage_speed", 0.6, Operation.MULTIPLY_TOTAL));
         }
      }
   }
}
