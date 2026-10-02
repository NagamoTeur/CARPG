package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageJumpToAbilitySection {
   private int entityID;
   private int index;
   private int sectionIndex;

   public MessageJumpToAbilitySection() {
   }

   public MessageJumpToAbilitySection(int entityID, int index, int sectionIndex) {
      this.entityID = entityID;
      this.index = index;
      this.sectionIndex = sectionIndex;
   }

   public static void serialize(MessageJumpToAbilitySection message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.m_130130_(message.index);
      buf.m_130130_(message.sectionIndex);
   }

   public static MessageJumpToAbilitySection deserialize(FriendlyByteBuf buf) {
      MessageJumpToAbilitySection message = new MessageJumpToAbilitySection();
      message.entityID = buf.m_130242_();
      message.index = buf.m_130242_();
      message.sectionIndex = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageJumpToAbilitySection, Supplier<Context>> {
      public void accept(MessageJumpToAbilitySection message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            LivingEntity entity = (LivingEntity)Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity != null) {
               AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.ABILITY_CAPABILITY);
               if (abilityCapability != null) {
                  AbilityType<?, ?> abilityType = abilityCapability.getAbilityTypesOnEntity(entity)[message.index];
                  Ability instance = abilityCapability.getAbilityMap().get(abilityType);
                  if (instance.isUsing()) {
                     instance.jumpToSection(message.sectionIndex);
                  }
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
