package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootRarity;

public class RarityCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_RARITY = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         LootRarity.ids().stream(), builder
      );

   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("loot_rarity").requires(c -> c.m_6761_(2)))
            .then(Commands.m_82129_("rarity", StringArgumentType.word()).suggests(SUGGEST_RARITY).executes(c -> {
               Player p = ((CommandSourceStack)c.getSource()).m_81375_();
               String type = (String)c.getArgument("rarity", String.class);
               LootRarity rarity = LootRarity.byId(type);
               ItemStack stack = p.m_21205_();
               AffixHelper.setRarity(stack, rarity);
               return 0;
            }))
      );
   }
}
