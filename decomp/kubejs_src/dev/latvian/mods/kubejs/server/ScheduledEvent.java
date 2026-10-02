package dev.latvian.mods.kubejs.server;

import dev.latvian.mods.kubejs.util.TickDuration;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import net.minecraft.server.MinecraftServer;

public abstract class ScheduledEvent {
   public final MinecraftServer server;
   public final TemporalAmount duration;
   public final long endTime;
   public final transient IScheduledEventCallback callback;

   private ScheduledEvent(MinecraftServer s, TemporalAmount d, long e, IScheduledEventCallback c) {
      this.server = s;
      this.duration = d;
      this.endTime = e;
      this.callback = c;
   }

   public void reschedule() {
      this.reschedule(this.duration);
   }

   public ScheduledEvent reschedule(TemporalAmount timer) {
      return this.server.kjs$schedule(timer, this.callback);
   }

   public abstract boolean isUsingTicks();

   public abstract boolean check(long var1, long var3);

   public static class InMs extends ScheduledEvent {
      public InMs(MinecraftServer s, Duration timer, long e, IScheduledEventCallback c) {
         super(s, timer, e, c);
      }

      @Override
      public boolean isUsingTicks() {
         return false;
      }

      @Override
      public boolean check(long nowMs, long nowTicks) {
         return nowMs >= this.endTime;
      }

      public ScheduledEvent reschedule(long timer) {
         return this.server.kjs$schedule(Duration.ofMillis(timer), this.callback);
      }
   }

   public static class InTicks extends ScheduledEvent {
      public InTicks(MinecraftServer s, TickDuration t, long e, IScheduledEventCallback c) {
         super(s, t, e, c);
      }

      @Override
      public boolean isUsingTicks() {
         return true;
      }

      @Override
      public boolean check(long nowMs, long nowTicks) {
         return nowTicks >= this.endTime;
      }

      public ScheduledEvent reschedule(long timer) {
         return this.server.kjs$schedule(new TickDuration(timer), this.callback);
      }
   }
}
