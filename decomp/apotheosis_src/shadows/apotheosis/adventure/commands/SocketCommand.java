package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.loot.LootCategory;

public class SocketCommand {
   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("set_sockets").requires(c -> c.m_6761_(2)))
            .then(Commands.m_82129_("sockets", IntegerArgumentType.integer(0, 16)).executes(c -> {
               Player p = ((CommandSourceStack)c.getSource()).m_81375_();
               ItemStack stack = p.m_21205_();
               LootCategory cat = LootCategory.forItem(stack);
               if (cat.isNone()) {
                  ((CommandSourceStack)c.getSource()).m_81352_(Component.m_237113_("The target item cannot receive sockets!"));
                  return 1;
               } else {
                  int sockets = IntegerArgumentType.getInteger(c, "sockets");
                  SocketHelper.setSockets(stack, sockets);
                  return 0;
               }
            }))
      );
   }
}
