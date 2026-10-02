package com.hollingsworth.arsnouveau.common.command;

import com.google.common.collect.ImmutableList;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ResetCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-reset").requires(sender -> sender.m_6761_(2)))
               .executes(
                  context -> resetPlayers((CommandSourceStack)context.getSource(), ImmutableList.of(((CommandSourceStack)context.getSource()).m_81374_()))
               ))
            .then(
               Commands.m_82129_("targets", EntityArgument.m_91460_())
                  .executes(context -> resetPlayers((CommandSourceStack)context.getSource(), EntityArgument.m_91461_(context, "targets")))
            )
      );
   }

   private static int resetPlayers(CommandSourceStack source, Collection<? extends Entity> entities) {
      for (Entity e : entities) {
         if (e instanceof LivingEntity) {
            CapabilityRegistry.getMana((LivingEntity)e).ifPresent(iMana -> {
               iMana.setBookTier(0);
               iMana.setGlyphBonus(0);
            });
            CapabilityRegistry.getPlayerDataCap((LivingEntity)e).ifPresent(iPlayerCap -> iPlayerCap.setKnownGlyphs(new ArrayList<>()));
            CapabilityRegistry.getPlayerDataCap((LivingEntity)e).ifPresent(ifam -> ifam.setUnlockedFamiliars(new ArrayList<>()));
         }
      }

      source.m_81354_(Component.m_237115_("ars_nouveau.reset.cleared"), true);
      return 1;
   }
}
