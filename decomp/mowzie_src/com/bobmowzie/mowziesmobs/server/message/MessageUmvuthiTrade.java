package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageUmvuthiTrade {
   private int entityID;

   public MessageUmvuthiTrade() {
   }

   public MessageUmvuthiTrade(LivingEntity sender) {
      this.entityID = sender.m_19879_();
   }

   public static void serialize(MessageUmvuthiTrade message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
   }

   public static MessageUmvuthiTrade deserialize(FriendlyByteBuf buf) {
      MessageUmvuthiTrade message = new MessageUmvuthiTrade();
      message.entityID = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageUmvuthiTrade, Supplier<Context>> {
      public void accept(MessageUmvuthiTrade message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(
            () -> {
               if (player != null) {
                  if (!(player.f_19853_.m_6815_(message.entityID) instanceof EntityUmvuthi barako)) {
                     return;
                  }

                  if (barako.getCustomer() != player) {
                     return;
                  }

                  AbstractContainerMenu container = player.f_36096_;
                  if (!(container instanceof ContainerUmvuthiTrade)) {
                     return;
                  }

                  boolean satisfied = barako.hasTradedWith(player);
                  if (!satisfied && (satisfied = barako.fulfillDesire(container.m_38853_(0)))) {
                     barako.rememberTrade(player);
                     ((ContainerUmvuthiTrade)container).returnItems();
                     container.m_38946_();
                  }

                  if (satisfied) {
                     player.m_7292_(
                        new MobEffectInstance(
                           (MobEffect)EffectHandler.SUNS_BLESSING.get(),
                           (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get() * 60 * 20,
                           0,
                           false,
                           false
                        )
                     );
                     if (barako.getActiveAbilityType() != EntityUmvuthi.BLESS_ABILITY) {
                        barako.sendAbilityMessage(EntityUmvuthi.BLESS_ABILITY);
                        barako.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BLESS.get(), 2.0F, 1.0F);
                     }
                  }
               }
            }
         );
         context.setPacketHandled(true);
      }
   }
}
