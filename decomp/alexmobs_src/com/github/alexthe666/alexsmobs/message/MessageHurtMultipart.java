package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IHurtableMultipart;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageHurtMultipart {
   public int part;
   public int parent;
   public float damage;
   public String damageType;

   public MessageHurtMultipart(int part, int parent, float damage) {
      this.part = part;
      this.parent = parent;
      this.damage = damage;
      this.damageType = "";
   }

   public MessageHurtMultipart(int part, int parent, float damage, String damageType) {
      this.part = part;
      this.parent = parent;
      this.damage = damage;
      this.damageType = damageType;
   }

   public MessageHurtMultipart() {
   }

   public static MessageHurtMultipart read(FriendlyByteBuf buf) {
      return new MessageHurtMultipart(buf.readInt(), buf.readInt(), buf.readFloat(), buf.m_130277_());
   }

   public static void write(MessageHurtMultipart message, FriendlyByteBuf buf) {
      buf.writeInt(message.part);
      buf.writeInt(message.parent);
      buf.writeFloat(message.damage);
      buf.m_130070_(message.damageType);
   }

   public static class Handler {
      public static void handle(MessageHurtMultipart message, Supplier<Context> context) {
         context.get().setPacketHandled(true);
         Player player = context.get().getSender();
         if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            player = AlexsMobs.PROXY.getClientSidePlayer();
         }

         if (player != null && player.f_19853_ != null) {
            Entity part = player.f_19853_.m_6815_(message.part);
            Entity parent = player.f_19853_.m_6815_(message.parent);
            if (part instanceof IHurtableMultipart && parent instanceof LivingEntity) {
               ((IHurtableMultipart)part).onAttackedFromServer((LivingEntity)parent, message.damage, new DamageSource(message.damageType));
            }

            if (part == null && parent != null && parent.isMultipartEntity()) {
               parent.m_6469_(new DamageSource(message.damageType), message.damage);
            }
         }
      }
   }
}
