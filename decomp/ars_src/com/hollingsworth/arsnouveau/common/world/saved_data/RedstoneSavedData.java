package com.hollingsworth.arsnouveau.common.world.saved_data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class RedstoneSavedData extends SavedData {
   public final Map<BlockPos, RedstoneSavedData.Entry> SIGNAL_MAP = new HashMap<>();

   public RedstoneSavedData() {
   }

   public RedstoneSavedData(CompoundTag tag) {
      ListTag signalList = tag.m_128437_("SignalList", 10);

      for (int i = 0; i < signalList.size(); i++) {
         RedstoneSavedData.Entry signal = new RedstoneSavedData.Entry(signalList.m_128728_(i));
         this.SIGNAL_MAP.put(signal.pos, signal);
      }
   }

   public void tick(ServerLevel serverLevel) {
      List<BlockPos> toRemove = new ArrayList<>();

      for (RedstoneSavedData.Entry signal : this.SIGNAL_MAP.values()) {
         if (serverLevel.m_46749_(signal.pos) && signal.ticks-- <= 0) {
            toRemove.add(signal.pos);
         }
      }

      for (BlockPos pos : toRemove) {
         this.SIGNAL_MAP.remove(pos);
         serverLevel.m_8055_(pos).m_60690_(serverLevel, pos, serverLevel.m_8055_(pos).m_60734_(), pos, false);
         serverLevel.m_46672_(pos, serverLevel.m_8055_(pos).m_60734_());
      }
   }

   public boolean m_77764_() {
      return true;
   }

   public CompoundTag m_7176_(CompoundTag pCompoundTag) {
      ListTag signalList = new ListTag();

      for (RedstoneSavedData.Entry signal : this.SIGNAL_MAP.values()) {
         signalList.add(signal.save(new CompoundTag()));
      }

      pCompoundTag.m_128365_("SignalList", signalList);
      return pCompoundTag;
   }

   public static RedstoneSavedData from(ServerLevel level) {
      return (RedstoneSavedData)level.m_8895_().m_164861_(RedstoneSavedData::new, RedstoneSavedData::new, "an_redstone_signals");
   }

   @SubscribeEvent
   public static void serverTick(LevelTickEvent e) {
      if (!e.level.f_46443_ && e.phase == Phase.END) {
         RedstoneSavedData data = from((ServerLevel)e.level);
         data.tick((ServerLevel)e.level);
      }
   }

   public static class Entry {
      public BlockPos pos;
      public int power;
      public int ticks;

      public Entry(BlockPos pos, int power, int ticks) {
         this.pos = pos;
         this.power = power;
         this.ticks = ticks;
      }

      public Entry(CompoundTag tag) {
         this(new BlockPos(tag.m_128451_("x"), tag.m_128451_("y"), tag.m_128451_("z")), tag.m_128451_("power"), tag.m_128451_("ticks"));
      }

      public CompoundTag save(CompoundTag tag) {
         tag.m_128405_("x", this.pos.m_123341_());
         tag.m_128405_("y", this.pos.m_123342_());
         tag.m_128405_("z", this.pos.m_123343_());
         tag.m_128405_("power", this.power);
         tag.m_128405_("ticks", this.ticks);
         return tag;
      }
   }
}
