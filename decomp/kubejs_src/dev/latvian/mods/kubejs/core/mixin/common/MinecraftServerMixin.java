package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.core.MinecraftServerKJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.server.IScheduledEventCallback;
import dev.latvian.mods.kubejs.server.KubeJSServerEventHandler;
import dev.latvian.mods.kubejs.server.ScheduledEvent;
import dev.latvian.mods.kubejs.server.ServerEventJS;
import dev.latvian.mods.kubejs.util.AttachedData;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import dev.latvian.mods.kubejs.util.TickDuration;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.MinecraftServer.ReloadableResources;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@RemapPrefixForJS("kjs$")
@Mixin({MinecraftServer.class})
public abstract class MinecraftServerMixin implements MinecraftServerKJS {
   private final CompoundTag kjs$persistentData = new CompoundTag();
   private final List<ScheduledEvent> kjs$scheduledEvents = new LinkedList<>();
   private ServerLevel kjs$overworld;
   private AttachedData<MinecraftServer> kjs$attachedData;

   @Shadow
   protected abstract boolean m_7038_() throws IOException;

   @Shadow
   public abstract void m_129929_();

   @Accessor("resources")
   @Override
   public abstract ReloadableResources kjs$getReloadableResources();

   @Override
   public CompoundTag kjs$getPersistentData() {
      return this.kjs$persistentData;
   }

   @Override
   public AttachedData<MinecraftServer> kjs$getData() {
      if (this.kjs$attachedData == null) {
         this.kjs$attachedData = new AttachedData<>(this.kjs$self());
         KubeJSPlugins.forEachPlugin(plugin -> plugin.attachServerData(this.kjs$attachedData));
      }

      return this.kjs$attachedData;
   }

   @Override
   public ServerLevel kjs$getOverworld() {
      if (this.kjs$overworld == null) {
         this.kjs$overworld = this.kjs$self().m_129783_();
      }

      return this.kjs$overworld;
   }

   @Inject(
      method = {"tickServer"},
      at = {@At("RETURN")}
   )
   private void kjs$postTickServer(BooleanSupplier booleanSupplier, CallbackInfo ci) {
      KubeJSServerEventHandler.tickScheduledEvents(System.currentTimeMillis(), this.kjs$getOverworld().m_46467_(), this.kjs$scheduledEvents);
      if (ServerEvents.TICK.hasListeners()) {
         ServerEvents.TICK.post(ScriptType.SERVER, new ServerEventJS(this.kjs$self()));
      }
   }

   @Override
   public ScheduledEvent kjs$schedule(TemporalAmount timer, IScheduledEventCallback event) {
      if (timer instanceof TickDuration duration) {
         ScheduledEvent.InTicks e = new ScheduledEvent.InTicks(this.kjs$self(), duration, this.kjs$getOverworld().m_46467_() + duration.ticks(), event);
         this.kjs$scheduledEvents.add(e);
         return e;
      } else if (timer instanceof Duration duration) {
         ScheduledEvent.InMs e = new ScheduledEvent.InMs(this.kjs$self(), duration, System.currentTimeMillis() + duration.toMillis(), event);
         this.kjs$scheduledEvents.add(e);
         return e;
      } else {
         throw new IllegalArgumentException("Unsupported TemporalAmount: " + timer);
      }
   }

   @Shadow
   @RemapForJS("isDedicated")
   public abstract boolean m_6982_();

   @Shadow
   @RemapForJS("stop")
   public abstract void m_7041_();
}
