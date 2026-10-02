package com.hollingsworth.arsnouveau.common.command;

import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketToggleLight;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class ToggleLightCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-light").requires(sender -> sender.m_6761_(0)))
               .then(Commands.m_82127_("on").executes(context -> resetPlayers((CommandSourceStack)context.getSource(), true))))
            .then(Commands.m_82127_("off").executes(context -> resetPlayers((CommandSourceStack)context.getSource(), false)))
      );
   }

   private static int resetPlayers(CommandSourceStack source, boolean enable) {
      ServerPlayer player;
      try {
         player = source.m_81375_();
      } catch (CommandSyntaxException var4) {
         var4.printStackTrace();
         return 1;
      }

      Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketToggleLight(enable));
      String path = enable ? "ars_nouveau.lights_on" : "ars_nouveau.lights_off";
      player.m_213846_(Component.m_237110_(path, new Object[]{enable}));
      return 1;
   }
}
