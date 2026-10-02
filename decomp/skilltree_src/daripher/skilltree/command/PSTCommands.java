package daripher.skilltree.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.network.NetworkDispatcher;
import daripher.skilltree.network.message.SyncPlayerSkillsMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(
   modid = "skilltree"
)
public class PSTCommands {
   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent event) {
      LiteralArgumentBuilder<CommandSourceStack> resetCommand = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.m_82127_(
               "skilltree"
            )
            .then(Commands.m_82127_("reset").then(Commands.m_82129_("player", EntityArgument.m_91466_()).executes(PSTCommands::executeResetCommand))))
         .requires(PSTCommands::hasPermission);
      event.getDispatcher().register(resetCommand);
      LiteralArgumentBuilder<CommandSourceStack> addPointsCommand = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.m_82127_(
               "skilltree"
            )
            .then(
               Commands.m_82127_("points")
                  .then(
                     Commands.m_82127_("add")
                        .then(
                           Commands.m_82129_("player", EntityArgument.m_91466_())
                              .then(Commands.m_82129_("chance", IntegerArgumentType.integer()).executes(PSTCommands::executeAddPointsCommand))
                        )
                  )
            ))
         .requires(PSTCommands::hasPermission);
      event.getDispatcher().register(addPointsCommand);
      LiteralArgumentBuilder<CommandSourceStack> setPointsCommand = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.m_82127_(
               "skilltree"
            )
            .then(
               Commands.m_82127_("points")
                  .then(
                     Commands.m_82127_("set")
                        .then(
                           Commands.m_82129_("player", EntityArgument.m_91466_())
                              .then(Commands.m_82129_("chance", IntegerArgumentType.integer()).executes(PSTCommands::executeSetPointsCommand))
                        )
                  )
            ))
         .requires(PSTCommands::hasPermission);
      event.getDispatcher().register(setPointsCommand);
   }

   private static int executeResetCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer player = EntityArgument.m_91474_(ctx, "player");
      IPlayerSkills skillsCapability = PlayerSkillsProvider.get(player);
      skillsCapability.resetTree(player);
      player.m_213846_(Component.m_237115_("skilltree.message.reset_command").m_130940_(ChatFormatting.YELLOW));
      NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerSkillsMessage(player));
      return 1;
   }

   private static int executeAddPointsCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer player = EntityArgument.m_91474_(ctx, "player");
      int amount = IntegerArgumentType.getInteger(ctx, "chance");
      IPlayerSkills skillsCapability = PlayerSkillsProvider.get(player);
      skillsCapability.setSkillPoints(amount + skillsCapability.getSkillPoints());
      player.m_213846_(Component.m_237115_("skilltree.message.point_command").m_130940_(ChatFormatting.YELLOW));
      NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerSkillsMessage(player));
      return 1;
   }

   private static int executeSetPointsCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer player = EntityArgument.m_91474_(ctx, "player");
      int amount = IntegerArgumentType.getInteger(ctx, "chance");
      IPlayerSkills skillsCapability = PlayerSkillsProvider.get(player);
      skillsCapability.setSkillPoints(amount);
      NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerSkillsMessage(player));
      return 1;
   }

   private static boolean hasPermission(CommandSourceStack commandSourceStack) {
      return commandSourceStack.m_6761_(2);
   }
}
