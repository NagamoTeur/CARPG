package com.hollingsworth.arsnouveau.common.command;

import com.google.common.collect.ImmutableList;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketTogglePathing;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;

public class PathCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-pathing").requires(sender -> sender.m_6761_(2)))
            .executes(context -> setPathing((CommandSourceStack)context.getSource(), ImmutableList.of(((CommandSourceStack)context.getSource()).m_81374_())))
      );
   }

   private static int setPathing(CommandSourceStack source, ImmutableList<? extends Entity> of) {
      Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)of.get(0)), new PacketTogglePathing());
      return 1;
   }
}
