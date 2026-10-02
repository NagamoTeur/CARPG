package dev.latvian.mods.kubejs.core;

import dev.latvian.mods.kubejs.net.SendDataFromServerMessage;
import dev.latvian.mods.kubejs.player.AdvancementJS;
import dev.latvian.mods.kubejs.player.EntityArrayList;
import dev.latvian.mods.kubejs.server.IScheduledEventCallback;
import dev.latvian.mods.kubejs.server.ScheduledEvent;
import dev.latvian.mods.kubejs.util.TickDuration;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.time.temporal.TemporalAmount;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.MinecraftServer.ReloadableResources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface MinecraftServerKJS extends WithAttachedData<MinecraftServer>, MessageSenderKJS, WithPersistentData, DataSenderKJS {
   default MinecraftServer kjs$self() {
      return (MinecraftServer)this;
   }

   ReloadableResources kjs$getReloadableResources();

   ServerLevel kjs$getOverworld();

   ScheduledEvent kjs$schedule(TemporalAmount var1, IScheduledEventCallback var2);

   default ScheduledEvent kjs$scheduleInTicks(long ticks, IScheduledEventCallback event) {
      return this.kjs$schedule(new TickDuration(ticks), event);
   }

   @Override
   default Component kjs$getName() {
      return Component.m_237113_(this.kjs$self().m_7326_());
   }

   @Override
   default Component kjs$getDisplayName() {
      return this.kjs$self().m_129893_().m_81357_();
   }

   @Override
   default void kjs$tell(Component message) {
      this.kjs$self().m_213846_(message);

      for (ServerPlayer player : this.kjs$self().m_6846_().m_11314_()) {
         player.kjs$tell(message);
      }
   }

   @Override
   default void kjs$setStatusMessage(Component message) {
      for (ServerPlayer player : this.kjs$self().m_6846_().m_11314_()) {
         player.kjs$setStatusMessage(message);
      }
   }

   @Override
   default int kjs$runCommand(String command) {
      return this.kjs$self().m_129892_().m_230957_(this.kjs$self().m_129893_(), command);
   }

   @Override
   default int kjs$runCommandSilent(String command) {
      return this.kjs$self().m_129892_().m_230957_(this.kjs$self().m_129893_().m_81324_(), command);
   }

   default ServerLevel kjs$getLevel(ResourceLocation dimension) {
      return this.kjs$self().m_129880_(ResourceKey.m_135785_(Registry.f_122819_, dimension));
   }

   @Nullable
   default ServerPlayer kjs$getPlayer(PlayerSelector selector) {
      return selector.getPlayer(this.kjs$self());
   }

   default EntityArrayList kjs$getPlayers() {
      return new EntityArrayList(this.kjs$self().m_129783_(), this.kjs$self().m_6846_().m_11314_());
   }

   default EntityArrayList kjs$getEntities() {
      EntityArrayList list = new EntityArrayList(this.kjs$self().m_129783_(), 10);

      for (ServerLevel level : this.kjs$self().m_129785_()) {
         list.addAllIterable(level.m_8583_());
      }

      return list;
   }

   @Nullable
   default AdvancementJS kjs$getAdvancement(ResourceLocation id) {
      Advancement a = this.kjs$self().m_129889_().m_136041_(id);
      return a == null ? null : new AdvancementJS(a);
   }

   @Override
   default void kjs$sendData(String channel, @Nullable CompoundTag data) {
      new SendDataFromServerMessage(channel, data).sendToAll(this.kjs$self());
   }
}
