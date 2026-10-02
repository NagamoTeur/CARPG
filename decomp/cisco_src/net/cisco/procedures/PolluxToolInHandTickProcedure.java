package net.cisco.procedures;

import java.util.UUID;
import net.cisco.init.CiscoModModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;

public class PolluxToolInHandTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21206_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.CASTOR.get()
            && (entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.POLLUX.get()) {
            if (!((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22109_(new AttributeModifier(UUID.fromString("8176edbb-33b8-48b8-bf80-2995c3b3cba8"), "pollux_attack", 0.3, Operation.MULTIPLY_TOTAL))) {
               ((LivingEntity)entity)
                  .m_21051_(Attributes.f_22281_)
                  .m_22118_(new AttributeModifier(UUID.fromString("8176edbb-33b8-48b8-bf80-2995c3b3cba8"), "pollux_attack", 0.3, Operation.MULTIPLY_TOTAL));
            }

            if (!((LivingEntity)entity)
               .m_21051_(Attributes.f_22283_)
               .m_22109_(new AttributeModifier(UUID.fromString("82e26307-c011-4b78-96fd-86d41110222c"), "pollux_attack_speed", 0.3, Operation.MULTIPLY_TOTAL))) {
               ((LivingEntity)entity)
                  .m_21051_(Attributes.f_22283_)
                  .m_22118_(
                     new AttributeModifier(UUID.fromString("82e26307-c011-4b78-96fd-86d41110222c"), "pollux_attack_speed", 0.3, Operation.MULTIPLY_TOTAL)
                  );
            }
         } else {
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 1, false, false));
            }

            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22130_(new AttributeModifier(UUID.fromString("8176edbb-33b8-48b8-bf80-2995c3b3cba8"), "pollux_attack", 0.3, Operation.MULTIPLY_TOTAL));
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22283_)
               .m_22130_(new AttributeModifier(UUID.fromString("82e26307-c011-4b78-96fd-86d41110222c"), "pollux_attack_speed", 0.3, Operation.MULTIPLY_TOTAL));
         }
      }
   }
}
