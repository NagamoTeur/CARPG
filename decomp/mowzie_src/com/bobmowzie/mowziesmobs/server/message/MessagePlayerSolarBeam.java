package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessagePlayerSolarBeam {
   public static void serialize(MessagePlayerSolarBeam message, FriendlyByteBuf buf) {
   }

   public static MessagePlayerSolarBeam deserialize(FriendlyByteBuf buf) {
      return new MessagePlayerSolarBeam();
   }

   public static class Handler implements BiConsumer<MessagePlayerSolarBeam, Supplier<Context>> {
      public void accept(MessagePlayerSolarBeam message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(
            () -> {
               EntitySolarBeam solarBeam = new EntitySolarBeam(
                  (EntityType<? extends EntitySolarBeam>)EntityHandler.SOLAR_BEAM.get(),
                  player.f_19853_,
                  player,
                  player.m_20185_(),
                  player.m_20186_() + 1.2F,
                  player.m_20189_(),
                  (float)((double)(player.f_20885_ + 90.0F) * Math.PI / 180.0),
                  (float)((double)(-player.m_146909_()) * Math.PI / 180.0),
                  55
               );
               solarBeam.setHasPlayer(true);
               player.f_19853_.m_7967_(solarBeam);
               player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 80, 2, false, false));
               int duration = player.m_21124_((MobEffect)EffectHandler.SUNS_BLESSING.get()).m_19557_();
               player.m_21195_((MobEffect)EffectHandler.SUNS_BLESSING.get());
               int solarBeamCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get() * 60 * 20;
               if (duration - solarBeamCost > 0) {
                  player.m_7292_(new MobEffectInstance((MobEffect)EffectHandler.SUNS_BLESSING.get(), duration - solarBeamCost, 0, false, false));
               }
            }
         );
         context.setPacketHandled(true);
      }
   }
}
