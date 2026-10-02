package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class GemCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_OP = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         Arrays.stream(Operation.values()).map(Enum::name), builder
      );
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_ATTRIB = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         ForgeRegistries.ATTRIBUTES.getKeys().stream().map(ResourceLocation::toString), builder
      );
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_GEM = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         GemManager.INSTANCE.getKeys().stream().map(ResourceLocation::toString), builder
      );

   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("gem").requires(c -> c.m_6761_(2)))
               .then(Commands.m_82127_("fromPreset").then(Commands.m_82129_("gem", ResourceLocationArgument.m_106984_()).suggests(SUGGEST_GEM).executes(c -> {
                  Gem gem = (Gem)GemManager.INSTANCE.getValue(ResourceLocationArgument.m_107011_(c, "gem"));
                  Player p = ((CommandSourceStack)c.getSource()).m_81375_();
                  ItemStack stack = GemManager.createGemStack(gem, LootRarity.random(p.f_19796_, p.m_36336_()));
                  p.m_36356_(stack);
                  return 0;
               }))))
            .then(
               Commands.m_82127_("random")
                  .executes(
                     c -> {
                        Player p = ((CommandSourceStack)c.getSource()).m_81375_();
                        ItemStack gem = GemManager.createRandomGemStack(
                           p.f_19796_,
                           ((CommandSourceStack)c.getSource()).m_81372_(),
                           p.m_36336_(),
                           IDimensional.matches(p.f_19853_),
                           GameStagesCompat.IStaged.matches(p)
                        );
                        p.m_36356_(gem);
                        return 0;
                     }
                  )
            )
      );
   }
}
