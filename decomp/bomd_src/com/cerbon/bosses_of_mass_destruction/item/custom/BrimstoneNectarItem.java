package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SendVec3S2CPacket;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.StructureRepair;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VecId;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrimstoneNectarItem extends Item {
   private final List<StructureRepair> structureRepairs;

   public BrimstoneNectarItem(Properties properties, List<StructureRepair> structureRepairs) {
      super(properties);
      this.structureRepairs = structureRepairs;
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.brimstone_nectar.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player user, @NotNull InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (!level.f_46443_ && level instanceof ServerLevel serverLevel) {
         BlockPos usePos = user.m_20183_();
         List<StructureRepair> structuresToRepair = this.findStructuresToRepair(serverLevel, usePos);
         if (!structuresToRepair.isEmpty()) {
            this.scheduleStructureRepair(serverLevel, structuresToRepair, usePos);
            this.playSound(serverLevel, user);
            user.m_36335_().m_41524_(this, 80);
            BMDPacketHandler.sendToAllPlayersTrackingChunk(
               new SendVec3S2CPacket(user.m_20182_(), VecId.BrimstoneParticleEffect.ordinal()), serverLevel, user.m_20182_()
            );
            if (!user.m_150110_().f_35937_) {
               itemStack.m_41774_(1);
            }

            user.m_36246_(Stats.f_12982_.m_12902_(this));
            return InteractionResultHolder.m_19092_(itemStack, false);
         }
      }

      return InteractionResultHolder.m_19098_(itemStack);
   }

   private List<StructureRepair> findStructuresToRepair(ServerLevel level, BlockPos usePos) {
      return this.structureRepairs.stream().filter(structure -> {
         StructureStart structureStart = this.getStructureStart(level, usePos, structure);
         return structureStart.m_73603_() && structure.shouldRepairStructure(level, structureStart);
      }).toList();
   }

   private void scheduleStructureRepair(ServerLevel level, List<StructureRepair> structureToRepair, BlockPos usePos) {
      BMDCapabilities.getLevelEventScheduler(level)
         .addEvent(
            new TimedEvent(() -> structureToRepair.forEach(structure -> structure.repairStructure(level, this.getStructureStart(level, usePos, structure))), 30)
         );
   }

   private StructureStart getStructureStart(ServerLevel level, BlockPos blockPos, StructureRepair it) {
      return level.m_215010_().m_220494_(blockPos, (Structure)level.m_215010_().m_220521_().m_175515_(Registry.f_235725_).m_123013_(it.associatedStructure()));
   }

   private void playSound(Level level, Player user) {
      level.m_6263_(
         null,
         user.m_20185_(),
         user.m_20186_(),
         user.m_20189_(),
         (SoundEvent)BMDSounds.BRIMSTONE.get(),
         SoundSource.NEUTRAL,
         1.0F,
         BMDUtils.randomPitch(level.m_213780_())
      );
   }
}
