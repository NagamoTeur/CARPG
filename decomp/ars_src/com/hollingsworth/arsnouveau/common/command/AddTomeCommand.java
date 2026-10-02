package com.hollingsworth.arsnouveau.common.command;

import com.hollingsworth.arsnouveau.api.registry.CasterTomeRegistry;
import com.hollingsworth.arsnouveau.common.tomes.CasterTomeData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Optional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;

public class AddTomeCommand {
   private static final SuggestionProvider<CommandSourceStack> sugg = (ctx, builder) -> SharedSuggestionProvider.m_82957_(
         CasterTomeRegistry.getTomeData().stream().map(CasterTomeData::m_6423_), builder
      );

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-tome").requires(sender -> sender.m_6761_(2)))
            .then(
               Commands.m_82129_("tome", ResourceLocationArgument.m_106984_())
                  .suggests(sugg)
                  .executes(context -> spawnTome((CommandSourceStack)context.getSource(), String.valueOf(ResourceLocationArgument.m_107011_(context, "tome"))))
            )
      );
   }

   private static int spawnTome(CommandSourceStack source, String tome) {
      Optional<CasterTomeData> data = CasterTomeRegistry.getTomeData().stream().filter(t -> t.m_6423_().toString().equals(tome)).findFirst();
      if (data.isPresent() && source.m_230896_() != null) {
         source.m_230896_().m_36356_(data.get().m_8043_().m_41777_());
      }

      return 1;
   }
}
