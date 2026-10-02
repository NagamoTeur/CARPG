package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import shadows.apotheosis.adventure.boss.BossItem;
import shadows.apotheosis.adventure.boss.BossItemManager;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class BossCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_BOSS = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         BossItemManager.INSTANCE.getKeys().stream().map(ResourceLocation::toString), builder
      );

   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder<CommandSourceStack>)Commands.m_82127_("spawn_boss")
         .requires(c -> c.m_6761_(2));
      builder.then(
         ((RequiredArgumentBuilder)Commands.m_82129_("pos", Vec3Argument.m_120841_())
               .then(
                  ((RequiredArgumentBuilder)Commands.m_82129_("boss", ResourceLocationArgument.m_106984_())
                        .suggests(SUGGEST_BOSS)
                        .then(
                           Commands.m_82129_("rarity", StringArgumentType.word())
                              .suggests(LootifyCommand.SUGGEST_RARITY)
                              .executes(
                                 c -> spawnBoss(
                                       c,
                                       Vec3Argument.m_120844_(c, "pos"),
                                       ResourceLocationArgument.m_107011_(c, "boss"),
                                       StringArgumentType.getString(c, "rarity")
                                    )
                              )
                        ))
                     .executes(c -> spawnBoss(c, Vec3Argument.m_120844_(c, "pos"), ResourceLocationArgument.m_107011_(c, "boss"), null))
               ))
            .executes(c -> spawnBoss(c, Vec3Argument.m_120844_(c, "pos"), null, null))
      );
      builder.then(
         ((RequiredArgumentBuilder)Commands.m_82129_("entity", EntityArgument.m_91449_())
               .then(
                  ((RequiredArgumentBuilder)Commands.m_82129_("boss", ResourceLocationArgument.m_106984_())
                        .suggests(SUGGEST_BOSS)
                        .then(
                           Commands.m_82129_("rarity", StringArgumentType.word())
                              .suggests(LootifyCommand.SUGGEST_RARITY)
                              .executes(
                                 c -> spawnBoss(
                                       c,
                                       EntityArgument.m_91452_(c, "entity").m_20182_(),
                                       ResourceLocationArgument.m_107011_(c, "boss"),
                                       StringArgumentType.getString(c, "rarity")
                                    )
                              )
                        ))
                     .executes(c -> spawnBoss(c, EntityArgument.m_91452_(c, "entity").m_20182_(), ResourceLocationArgument.m_107011_(c, "boss"), null))
               ))
            .executes(c -> spawnBoss(c, EntityArgument.m_91452_(c, "entity").m_20182_(), null, null))
      );
      root.then(builder);
   }

   public static int spawnBoss(CommandContext<CommandSourceStack> c, Vec3 pos, @Nullable ResourceLocation bossId, @Nullable String rarityId) {
      Entity nullableSummoner = ((CommandSourceStack)c.getSource()).m_81373_();
      Player summoner = nullableSummoner instanceof Player
         ? (Player)nullableSummoner
         : ((CommandSourceStack)c.getSource()).m_81372_().m_45924_(pos.m_7096_(), pos.m_7098_(), pos.m_7094_(), 64.0, false);
      if (summoner == null) {
         ((CommandSourceStack)c.getSource()).m_81352_(Component.m_237113_("No available player context!"));
         return -1;
      } else {
         BossItem boss = bossId == null
            ? (BossItem)BossItemManager.INSTANCE
               .getRandomItem(
                  summoner.f_19796_, summoner.m_36336_(), new Predicate[]{IDimensional.matches(summoner.f_19853_), GameStagesCompat.IStaged.matches(summoner)}
               )
            : (BossItem)BossItemManager.INSTANCE.getValue(bossId);
         if (boss == null) {
            if (bossId == null) {
               ((CommandSourceStack)c.getSource()).m_81352_(Component.m_237113_("Unknown boss: " + bossId));
            } else {
               ((CommandSourceStack)c.getSource()).m_81352_(Component.m_237113_("No bosses available for the current context!"));
            }

            return -2;
         } else {
            Mob bossEntity;
            if (rarityId != null) {
               LootRarity rarity = LootRarity.byId(rarityId);
               if (rarity == null) {
                  ((CommandSourceStack)c.getSource()).m_81352_(Component.m_237113_("Unknown rarity: " + rarityId));
                  return -3;
               }

               bossEntity = boss.createBoss((ServerLevelAccessor)summoner.f_19853_, new BlockPos(pos), summoner.f_19796_, summoner.m_36336_(), rarity);
            } else {
               bossEntity = boss.createBoss((ServerLevelAccessor)summoner.f_19853_, new BlockPos(pos), summoner.f_19796_, summoner.m_36336_());
            }

            ((CommandSourceStack)c.getSource()).m_81372_().m_47205_(bossEntity);
            return 0;
         }
      }
   }
}
