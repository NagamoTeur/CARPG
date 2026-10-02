package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessagePlayerSummonSunstrike {
   private static final double REACH = 15.0;

   private static BlockHitResult rayTrace(LivingEntity entity, double reach) {
      Vec3 pos = entity.m_20299_(0.0F);
      Vec3 segment = entity.m_20154_();
      segment = pos.m_82520_(segment.f_82479_ * reach, segment.f_82480_ * reach, segment.f_82481_ * reach);
      return entity.f_19853_.m_45547_(new ClipContext(pos, segment, Block.COLLIDER, Fluid.NONE, entity));
   }

   public static void serialize(MessagePlayerSummonSunstrike message, FriendlyByteBuf buf) {
   }

   public static MessagePlayerSummonSunstrike deserialize(FriendlyByteBuf buf) {
      return new MessagePlayerSummonSunstrike();
   }

   public static class Handler implements BiConsumer<MessagePlayerSummonSunstrike, Supplier<Context>> {
      public void accept(MessagePlayerSummonSunstrike message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(
            () -> {
               BlockHitResult raytrace = MessagePlayerSummonSunstrike.rayTrace(player, 15.0);
               if (raytrace.m_6662_() == Type.BLOCK
                  && raytrace.m_82434_() == Direction.UP
                  && player.m_150109_().m_36056_().m_41619_()
                  && player.m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get())) {
                  BlockPos hit = raytrace.m_82425_();
                  EntitySunstrike sunstrike = new EntitySunstrike(
                     (EntityType<? extends EntitySunstrike>)EntityHandler.SUNSTRIKE.get(),
                     player.f_19853_,
                     player,
                     hit.m_123341_(),
                     hit.m_123342_(),
                     hit.m_123343_()
                  );
                  sunstrike.onSummon();
                  player.f_19853_.m_7967_(sunstrike);
               }
            }
         );
         context.setPacketHandled(true);
      }
   }
}
