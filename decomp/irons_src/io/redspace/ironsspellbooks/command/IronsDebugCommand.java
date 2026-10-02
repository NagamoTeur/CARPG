package io.redspace.ironsspellbooks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

public class IronsDebugCommand {
   public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
      pDispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ironsDebug").requires(p_138819_ -> p_138819_.m_6761_(2)))
               .then(
                  Commands.m_82129_("dataType", EnumArgument.enumArgument(IronsDebugCommand.IronsDebugCommandTypes.class))
                     .executes(
                        commandContext -> getDataForType(
                              (CommandSourceStack)commandContext.getSource(),
                              (IronsDebugCommand.IronsDebugCommandTypes)commandContext.getArgument("dataType", IronsDebugCommand.IronsDebugCommandTypes.class)
                           )
                     )
               ))
            .then(Commands.m_82127_("spellCount").executes(commandContext -> {
               int i = SpellRegistry.getEnabledSpells().size();
               ((CommandSourceStack)commandContext.getSource()).m_81354_(Component.m_237113_(String.valueOf(i)), true);
               return i;
            }))
      );
   }

   public static int getDataForType(CommandSourceStack source, IronsDebugCommand.IronsDebugCommandTypes ironsDebugCommandTypes) {
      switch (ironsDebugCommandTypes) {
         case RECASTING:
            getReacstingData(source);
         default:
            return 1;
      }
   }

   public static void getReacstingData(CommandSourceStack source) {
      ServerPlayer serverPlayer = source.m_230896_();
      MagicData magicData = MagicData.getPlayerMagicData(serverPlayer);
      writeResults(source, magicData.getPlayerRecasts().toString());
   }

   private static void writeResults(CommandSourceStack source, String results) {
      try {
         File file = new File("irons_debug.txt");
         BufferedWriter writer = new BufferedWriter(new FileWriter(file));
         writer.write(results);
         writer.close();
         Component component = Component.m_237113_(file.getName())
            .m_130940_(ChatFormatting.UNDERLINE)
            .m_130938_(style -> style.m_131142_(new ClickEvent(Action.OPEN_FILE, file.getAbsolutePath())));
         source.m_81354_(Component.m_237110_("commands.irons_spellbooks.irons_debug_command.success", new Object[]{component}), true);
      } catch (Exception var5) {
      }
   }

   public static enum IronsDebugCommandTypes {
      RECASTING;
   }
}
