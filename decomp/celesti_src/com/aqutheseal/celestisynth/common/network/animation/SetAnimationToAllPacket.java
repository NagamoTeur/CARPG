package com.aqutheseal.celestisynth.common.network.animation;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.animation.player.CSAnimator;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetAnimationToAllPacket {
   private final boolean isOtherLayer;
   private final int playerId;
   private final int animId;

   public SetAnimationToAllPacket(boolean isOtherLayer, int playerId, int animId) {
      this.isOtherLayer = isOtherLayer;
      this.playerId = playerId;
      this.animId = animId;
   }

   public SetAnimationToAllPacket(FriendlyByteBuf buf) {
      this.isOtherLayer = buf.readBoolean();
      this.playerId = buf.readInt();
      this.animId = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.isOtherLayer);
      buf.writeInt(this.playerId);
      buf.writeInt(this.animId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context context = supplier.get();
      context.enqueueWork(() -> {
         Minecraft instance = Minecraft.m_91087_();
         Entity player = instance.f_91073_.m_6815_(this.playerId);
         animatePlayer(this.isOtherLayer, this.animId, (AbstractClientPlayer)player);
      });
      return true;
   }

   public static void animatePlayer(boolean isOtherLayer, int animId, AbstractClientPlayer player) {
      ModifierLayer<IAnimation> animation = isOtherLayer ? CSAnimator.otherAnimationData.get(player) : CSAnimator.animationData.get(player);
      if (animation != null) {
         AnimationManager.playAnimation(AnimationManager.getAnimFromId(animId).getAnimation(), animation);
      }
   }
}
