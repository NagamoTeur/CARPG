package net.cisco.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class SovereignSplendourEffectExpiresProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double sovereign = 0.0;
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22276_)
            .m_22130_(new AttributeModifier(UUID.fromString("1325dece-6423-11f0-9f99-325096b39f47"), "sovereignhp", 0.4, Operation.MULTIPLY_TOTAL));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22281_)
            .m_22130_(new AttributeModifier(UUID.fromString("f2511df4-8295-490a-a6c4-e33b0a170d41"), "sovereign", sovereign / 1.6, Operation.ADDITION));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22279_)
            .m_22130_(new AttributeModifier(UUID.fromString("89fc12ae-640d-11f0-b377-325096b39f47"), "sovereignspeed", 0.2, Operation.ADDITION));
         ((LivingEntity)entity)
            .m_21051_(Attributes.f_22283_)
            .m_22130_(new AttributeModifier(UUID.fromString("90706720-640d-11f0-bd74-325096b39f47"), "sovereignatkspd", 0.2, Operation.ADDITION));
      }
   }
}
