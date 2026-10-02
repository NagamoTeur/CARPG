package dev.latvian.mods.kubejs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.util.ClassWrapper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.Commands.CommandSelection;

public class CommandRegistryEventJS extends EventJS {
   public final CommandDispatcher<CommandSourceStack> dispatcher;
   public final CommandBuildContext context;
   public final CommandSelection selection;

   public CommandRegistryEventJS(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, CommandSelection selection) {
      this.dispatcher = dispatcher;
      this.context = context;
      this.selection = selection;
   }

   public boolean isForSinglePlayer() {
      return this.selection.f_82144_;
   }

   public boolean isForMultiPlayer() {
      return this.selection.f_82145_;
   }

   public CommandBuildContext getRegistry() {
      return this.context;
   }

   public LiteralCommandNode<CommandSourceStack> register(LiteralArgumentBuilder<CommandSourceStack> command) {
      return this.dispatcher.register(command);
   }

   public ClassWrapper<Commands> getCommands() {
      return new ClassWrapper<>(Commands.class);
   }

   public ClassWrapper<ArgumentTypeWrapper> getArguments() {
      return new ClassWrapper<>(ArgumentTypeWrapper.class);
   }

   public ClassWrapper<SharedSuggestionProvider> getBuiltinSuggestions() {
      return new ClassWrapper<>(SharedSuggestionProvider.class);
   }
}
