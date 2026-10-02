package com.hollingsworth.arsnouveau.api.source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class SourceManager {
   private Map<String, Set<ISpecialSourceProvider>> posMap = new ConcurrentHashMap<>();
   public static SourceManager INSTANCE = new SourceManager();

   public void addInterface(Level world, ISpecialSourceProvider pos) {
      String key = world.m_46472_().m_135782_().toString();
      if (!this.posMap.containsKey(key)) {
         this.posMap.put(key, new HashSet<>());
      }

      this.posMap.get(key).add(pos);
   }

   public Set<ISpecialSourceProvider> getSetForLevel(Level world) {
      String key = world.m_46472_().m_135782_().toString();
      return this.posMap.computeIfAbsent(key, k -> new HashSet<>());
   }

   public Set<ISpecialSourceProvider> getCopySetForLevel(Level world) {
      return new HashSet<>(this.getSetForLevel(world));
   }

   @Nullable
   public ISpecialSourceProvider takeSourceNearby(BlockPos pos, Level world, int range, int amount) {
      for (ISpecialSourceProvider sourceInterface : this.getCopySetForLevel(world)) {
         if (sourceInterface.isValid() && sourceInterface.getCurrentPos().m_123314_(pos, (double)range)) {
            sourceInterface.getSource().removeSource(amount);
            return sourceInterface;
         }
      }

      return null;
   }

   @Nullable
   public ISpecialSourceProvider hasSourceNearby(BlockPos pos, Level world, int range, int amount) {
      for (ISpecialSourceProvider sourceInterface : this.getCopySetForLevel(world)) {
         if (sourceInterface.isValid() && sourceInterface.getCurrentPos().m_123314_(pos, (double)range) && sourceInterface.getSource().getSource() >= amount) {
            return sourceInterface;
         }
      }

      return null;
   }

   public List<ISpecialSourceProvider> canGiveSourceNearby(BlockPos pos, Level world, int range) {
      List<ISpecialSourceProvider> list = new ArrayList<>();

      for (ISpecialSourceProvider sourceInterface : this.getCopySetForLevel(world)) {
         if (sourceInterface.isValid() && sourceInterface.getCurrentPos().m_123314_(pos, (double)range) && sourceInterface.getSource().canAcceptSource()) {
            list.add(sourceInterface);
         }
      }

      return list;
   }

   public List<ISpecialSourceProvider> canTakeSourceNearby(BlockPos pos, Level world, int range) {
      List<ISpecialSourceProvider> list = new ArrayList<>();

      for (ISpecialSourceProvider sourceInterface : this.getCopySetForLevel(world)) {
         if (sourceInterface.isValid() && sourceInterface.getCurrentPos().m_123314_(pos, (double)range) && sourceInterface.getSource().getSource() >= 0) {
            list.add(sourceInterface);
         }
      }

      return list;
   }

   public void tick(Level level) {
      if (level.m_46467_() % 60L == 0L) {
         Set<ISpecialSourceProvider> stale = new HashSet<>();

         for (ISpecialSourceProvider iSourceInterface : this.getSetForLevel(level)) {
            if (!iSourceInterface.isValid()) {
               stale.add(iSourceInterface);
            }
         }

         Set<ISpecialSourceProvider> set = this.getSetForLevel(level);

         for (ISpecialSourceProvider iSourceInterfacex : stale) {
            set.remove(iSourceInterfacex);
         }
      }
   }

   private SourceManager() {
   }

   @SubscribeEvent
   public static void serverTick(LevelTickEvent e) {
      if (!e.level.f_46443_ && e.phase == Phase.END) {
         INSTANCE.tick(e.level);
      }
   }
}
