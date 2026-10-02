package com.hollingsworth.arsnouveau.common.command;

import com.hollingsworth.arsnouveau.common.entity.AnimBlockSummon;
import com.hollingsworth.arsnouveau.common.entity.AnimHeadSummon;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

public class SummonAnimHeadCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-skull").requires(sender -> sender.m_6761_(4)))
            .then(
               Commands.m_82129_("player_name", StringArgumentType.word())
                  .then(
                     Commands.m_82129_("duration", IntegerArgumentType.integer())
                        .then(
                           Commands.m_82129_("nbt", CompoundTagArgument.m_87657_())
                              .then(
                                 Commands.m_82129_("dropBlock", BoolArgumentType.bool())
                                    .executes(
                                       context -> summonSkull(
                                             (CommandSourceStack)context.getSource(),
                                             String.valueOf(StringArgumentType.getString(context, "player_name")),
                                             IntegerArgumentType.getInteger(context, "duration"),
                                             CompoundTagArgument.m_87660_(context, "nbt"),
                                             BoolArgumentType.getBool(context, "dropBlock")
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int summonSkull(CommandSourceStack source, String player_name, int duration, CompoundTag compoundTag, boolean dropSkull) {
      try {
         compoundTag.m_128359_("id", new ResourceLocation("ars_nouveau", "animated_head").toString());
         Entity entity = EntityType.m_20645_(compoundTag, source.m_81372_(), p_138828_ -> {
            p_138828_.m_7678_(source.m_81371_().f_82479_, source.m_81371_().f_82480_, source.m_81371_().f_82481_, p_138828_.m_146908_(), p_138828_.m_146909_());
            return p_138828_;
         });
         AnimHeadSummon animHeadSummon = (AnimHeadSummon)entity;
         animHeadSummon.blockState = Blocks.f_50316_.m_49966_();
         animHeadSummon.head_data = AnimHeadSummon.getHeadTagFromName(player_name);
         animHeadSummon.m_146884_(source.m_81371_());
         animHeadSummon.setTicksLeft(duration);
         animHeadSummon.m_20088_().m_135381_(AnimBlockSummon.CAN_WALK, true);
         animHeadSummon.m_20088_().m_135381_(AnimBlockSummon.AGE, 21);
         animHeadSummon.dropItem = dropSkull;
         source.m_81372_().m_7967_(animHeadSummon);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return 1;
   }
}
