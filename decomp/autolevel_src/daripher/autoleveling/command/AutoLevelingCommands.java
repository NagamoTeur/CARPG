package daripher.autoleveling.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import daripher.autoleveling.saveddata.GlobalLevelingData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "autoleveling"
)
public class AutoLevelingCommands {
   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      LiteralArgumentBuilder<CommandSourceStack> addGlobalLevelCommand = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.m_82127_(
               "autoleveling"
            )
            .then(
               Commands.m_82127_("level")
                  .then(
                     Commands.m_82127_("add")
                        .then(Commands.m_82129_("value", IntegerArgumentType.integer()).executes(AutoLevelingCommands::executeAddLevelCommand))
                  )
            ))
         .requires(AutoLevelingCommands::hasPermission);
      event.getDispatcher().register(addGlobalLevelCommand);
   }

   private static int executeAddLevelCommand(CommandContext<CommandSourceStack> ctx) {
      MinecraftServer server = ((CommandSourceStack)ctx.getSource()).m_81377_();
      GlobalLevelingData globalLevelingData = GlobalLevelingData.get(server);
      Integer levelBonus = (Integer)ctx.getArgument("value", Integer.class);
      int oldLevelBonus = globalLevelingData.getLevelBonus();
      globalLevelingData.setLevel(oldLevelBonus + levelBonus);
      return 1;
   }

   private static boolean hasPermission(CommandSourceStack commandSourceStack) {
      return commandSourceStack.m_6761_(2);
   }
}
