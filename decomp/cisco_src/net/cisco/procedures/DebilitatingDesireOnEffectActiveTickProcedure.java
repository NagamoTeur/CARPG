package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DebilitatingDesireOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22276_)
            .m_22109_(new AttributeModifier(UUID.fromString("d89454ca-4ccb-473c-9b30-3a28a0471d7c"), "debhp", -0.8, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22276_)
               .m_22118_(new AttributeModifier(UUID.fromString("d89454ca-4ccb-473c-9b30-3a28a0471d7c"), "debhp", -0.8, Operation.MULTIPLY_TOTAL));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22279_)
            .m_22109_(new AttributeModifier(UUID.fromString("4a50c440-7a0f-4647-b9ba-45fb4fe37b57"), "debspeed", -0.8, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22279_)
               .m_22118_(new AttributeModifier(UUID.fromString("4a50c440-7a0f-4647-b9ba-45fb4fe37b57"), "debspeed", -0.8, Operation.MULTIPLY_TOTAL));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22109_(new AttributeModifier(UUID.fromString("0341c24b-75ce-47a2-9f64-3d705110f8aa"), "debarmor", -0.8, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22284_)
               .m_22118_(new AttributeModifier(UUID.fromString("0341c24b-75ce-47a2-9f64-3d705110f8aa"), "debarmor", -0.8, Operation.MULTIPLY_TOTAL));
         }
      }
   }
}
