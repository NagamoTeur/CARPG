package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;

public class LootifyCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_RARITY = RarityCommand.SUGGEST_RARITY;

   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("lootify").requires(c -> c.m_6761_(2)))
            .then(Commands.m_82129_("rarity", StringArgumentType.word()).suggests(SUGGEST_RARITY).executes(c -> {
               Player p = ((CommandSourceStack)c.getSource()).m_81375_();
               String type = (String)c.getArgument("rarity", String.class);
               LootRarity rarity = LootRarity.byId(type);
               ItemStack stack = p.m_21205_();
               AffixHelper.setAffixes(stack, Collections.emptyMap());
               LootController.createLootItem(stack, rarity, p.f_19853_.f_46441_);
               return 0;
            }))
      );
   }
}
