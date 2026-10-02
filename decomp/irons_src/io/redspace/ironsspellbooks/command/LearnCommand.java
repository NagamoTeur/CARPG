package io.redspace.ironsspellbooks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class LearnCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      LiteralCommandNode<CommandSourceStack> command = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("learnSpell").requires(p -> p.m_6761_(2)))
               .then(Commands.m_82127_("forget").executes(context -> forget((CommandSourceStack)context.getSource()))))
            .then(
               Commands.m_82127_("learn")
                  .then(
                     Commands.m_82129_("spell", SpellArgument.spellArgument())
                        .executes(
                           commandContext -> learn((CommandSourceStack)commandContext.getSource(), (String)commandContext.getArgument("spell", String.class))
                        )
                  )
            )
      );
   }

   private static int forget(CommandSourceStack source) {
      MagicData.getPlayerMagicData(source.m_230896_()).getSyncedData().forgetAllSpells();
      return 1;
   }

   private static int learn(CommandSourceStack source, String spellId) {
      if (!spellId.contains(":")) {
         spellId = "irons_spellbooks:" + spellId;
      }

      AbstractSpell spell = SpellRegistry.getSpell(spellId);
      MagicData.getPlayerMagicData(source.m_230896_()).getSyncedData().learnSpell(spell);
      return 1;
   }
}
