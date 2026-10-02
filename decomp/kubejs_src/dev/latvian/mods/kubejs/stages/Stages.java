package dev.latvian.mods.kubejs.stages;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.latvian.mods.kubejs.net.AddStageMessage;
import dev.latvian.mods.kubejs.net.RemoveStageMessage;
import dev.latvian.mods.kubejs.net.SyncStagesMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public abstract class Stages {
   private static final Event<Consumer<StageCreationEvent>> OVERRIDE_CREATION = EventFactory.createConsumerLoop(new StageCreationEvent[0]);
   private static final Event<Consumer<StageChangeEvent>> ADDED = EventFactory.createConsumerLoop(new StageChangeEvent[0]);
   private static final Event<Consumer<StageChangeEvent>> REMOVED = EventFactory.createConsumerLoop(new StageChangeEvent[0]);
   public final Player player;

   public static Stages create(Player player) {
      if (PlayerHooks.isFake(player)) {
         return NoStages.NULL_INSTANCE;
      } else {
         StageCreationEvent event = new StageCreationEvent(player);
         ((Consumer)OVERRIDE_CREATION.invoker()).accept(event);
         return (Stages)(event.getPlayerStages() == null ? new TagWrapperStages(player) : event.getPlayerStages());
      }
   }

   public static void overrideCreation(Consumer<StageCreationEvent> event) {
      OVERRIDE_CREATION.register(event);
   }

   public static void added(Consumer<StageChangeEvent> event) {
      ADDED.register(event);
   }

   public static void invokeAdded(Stages stages, String stage) {
      ((Consumer)ADDED.invoker()).accept(new StageChangeEvent(stages, stage));
   }

   public static void removed(Consumer<StageChangeEvent> event) {
      REMOVED.register(event);
   }

   public static void invokeRemoved(Stages stages, String stage) {
      ((Consumer)REMOVED.invoker()).accept(new StageChangeEvent(stages, stage));
   }

   public static Stages get(@Nullable Player player) {
      return (Stages)(player == null ? NoStages.NULL_INSTANCE : player.kjs$getStages());
   }

   public Stages(Player p) {
      this.player = p;
   }

   public abstract boolean addNoUpdate(String var1);

   public abstract boolean removeNoUpdate(String var1);

   public abstract Collection<String> getAll();

   public boolean has(String stage) {
      return this.getAll().contains(stage);
   }

   public boolean add(String stage) {
      if (this.addNoUpdate(stage)) {
         if (this.player instanceof ServerPlayer serverPlayer) {
            new AddStageMessage(this.player.m_20148_(), stage).sendToAll(serverPlayer.f_8924_);
         }

         invokeAdded(this, stage);
         return true;
      } else {
         return false;
      }
   }

   public boolean remove(String stage) {
      if (this.removeNoUpdate(stage)) {
         if (this.player instanceof ServerPlayer serverPlayer) {
            new RemoveStageMessage(this.player.m_20148_(), stage).sendToAll(serverPlayer.f_8924_);
         }

         invokeRemoved(this, stage);
         return true;
      } else {
         return false;
      }
   }

   public final boolean set(String stage, boolean enabled) {
      return enabled ? this.add(stage) : this.remove(stage);
   }

   public final boolean toggle(String stage) {
      return this.set(stage, !this.has(stage));
   }

   public boolean clear() {
      Collection<String> all = this.getAll();
      if (all.isEmpty()) {
         return false;
      } else {
         for (String s : new ArrayList<>(all)) {
            this.remove(s);
         }

         return true;
      }
   }

   public void sync() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         new SyncStagesMessage(this.player.m_20148_(), this.getAll()).sendToAll(serverPlayer.f_8924_);
      }
   }

   public void replace(Collection<String> stages) {
      Collection<String> all = this.getAll();

      for (String s : new ArrayList<>(all)) {
         this.removeNoUpdate(s);
      }

      for (String s : stages) {
         this.addNoUpdate(s);
      }
   }
}
