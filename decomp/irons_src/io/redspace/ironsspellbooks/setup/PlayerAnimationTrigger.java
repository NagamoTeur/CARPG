package io.redspace.ironsspellbooks.setup;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "irons_spellbooks",
   bus = Bus.FORGE,
   value = {Dist.CLIENT}
)
public class PlayerAnimationTrigger {
   @SubscribeEvent
   public static void onChatReceived(ClientChatReceivedEvent event) {
      if (event.getMessage().m_240452_(Component.m_237113_("waving"))) {
         Player player = Minecraft.m_91087_().f_91073_.m_46003_(event.getMessageSigner().f_240864_());
         if (player == null) {
            return;
         }

         ModifierLayer<IAnimation> animation = (ModifierLayer<IAnimation>)PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer)player)
            .get(new ResourceLocation("irons_spellbooks", "animation"));
         if (animation != null) {
            animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("irons_spellbooks", "waving"))));
         }
      }
   }
}
