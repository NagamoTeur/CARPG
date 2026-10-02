package shadows.apotheosis.adventure.boss;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.client.BossSpawnMessage;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;
import shadows.placebo.network.PacketDistro;

public class BossEvents {
   public Object2IntMap<ResourceLocation> bossCooldowns = new Object2IntOpenHashMap();

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void naturalBosses(SpecialSpawn e) {
      if (e.getSpawnReason() == MobSpawnType.NATURAL || e.getSpawnReason() == MobSpawnType.CHUNK_GENERATION) {
         LivingEntity entity = e.getEntity();
         RandomSource rand = e.getLevel().m_213780_();
         if (this.bossCooldowns.getInt(entity.f_19853_.m_46472_().m_135782_()) <= 0
            && !e.getLevel().m_5776_()
            && entity instanceof Monster
            && e.getResult() != Result.DENY) {
            ServerLevelAccessor sLevel = (ServerLevelAccessor)e.getLevel();
            ResourceLocation dimId = sLevel.m_6018_().m_46472_().m_135782_();
            Pair<Float, BossEvents.BossSpawnRules> rules = AdventureConfig.BOSS_SPAWN_RULES.get(dimId);
            if (rules == null) {
               return;
            }

            if (rand.m_188501_() <= (Float)rules.getLeft()
               && ((BossEvents.BossSpawnRules)rules.getRight()).test(sLevel, new BlockPos(e.getX(), e.getY(), e.getZ()))) {
               Player player = sLevel.m_45924_(e.getX(), e.getY(), e.getZ(), -1.0, false);
               if (player == null) {
                  return;
               }

               BossItem item = (BossItem)BossItemManager.INSTANCE
                  .getRandomItem(rand, player.m_36336_(), new Predicate[]{IDimensional.matches(sLevel.m_6018_()), GameStagesCompat.IStaged.matches(player)});
               if (item == null) {
                  AdventureModule.LOGGER
                     .error(
                        "Attempted to spawn a boss in dimension {} using configured boss spawn rule {}/{} but no bosses were made available.",
                        dimId,
                        rules.getRight(),
                        rules.getLeft()
                     );
                  return;
               }

               Mob boss = item.createBoss(sLevel, new BlockPos(e.getX() - 0.5, e.getY(), e.getZ() - 0.5), rand, player.m_36336_());
               if (AdventureConfig.bossAutoAggro && !player.m_7500_()) {
                  boss.m_6710_(player);
               }

               if (canSpawn(sLevel, boss, player.m_20280_(boss))) {
                  sLevel.m_47205_(boss);
                  e.setResult(Result.DENY);
                  AdventureModule.debugLog(boss.m_20183_(), "Surface Boss - " + boss.m_7755_().getString());
                  Component name = this.getName(boss);
                  if (name != null && name.m_7383_().m_131135_() != null) {
                     sLevel.m_6907_()
                        .forEach(
                           p -> {
                              Vec3 tPos = new Vec3(boss.m_20185_(), AdventureConfig.bossAnnounceIgnoreY ? p.m_20186_() : boss.m_20186_(), boss.m_20189_());
                              if (p.m_20238_(tPos) <= (double)(AdventureConfig.bossAnnounceRange * AdventureConfig.bossAnnounceRange)) {
                                 ((ServerPlayer)p)
                                    .f_8906_
                                    .m_9829_(
                                       new ClientboundSetActionBarTextPacket(
                                          Component.m_237110_("info.apotheosis.boss_spawn", new Object[]{name, (int)boss.m_20185_(), (int)boss.m_20186_()})
                                       )
                                    );
                                 TextColor color = name.m_7383_().m_131135_();
                                 PacketDistro.sendTo(
                                    Apotheosis.CHANNEL, new BossSpawnMessage(boss.m_20183_(), color == null ? 16777215 : color.m_131265_()), player
                                 );
                              }
                           }
                        );
                  } else {
                     AdventureModule.LOGGER
                        .warn("A Boss {} ({}) has spawned without a custom name!", boss.m_7755_().getString(), EntityType.m_20613_(boss.m_6095_()));
                  }

                  this.bossCooldowns.put(entity.f_19853_.m_46472_().m_135782_(), AdventureConfig.bossSpawnCooldown);
               }
            }
         }
      }
   }

   @Nullable
   private Component getName(Mob boss) {
      return boss.m_20199_().filter(e -> e.getPersistentData().m_128441_("apoth.boss")).findFirst().<Component>map(Entity::m_7770_).orElse(null);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void minibosses(SpecialSpawn e) {
      LivingEntity entity = e.getEntity();
      RandomSource rand = e.getLevel().m_213780_();
      if (!e.getLevel().m_5776_() && entity instanceof Mob mob && e.getResult() != Result.DENY) {
         ServerLevelAccessor sLevel = (ServerLevelAccessor)e.getLevel();
         Player player = sLevel.m_45924_(e.getX(), e.getY(), e.getZ(), -1.0, false);
         if (player == null) {
            return;
         }

         MinibossItem item = (MinibossItem)MinibossManager.INSTANCE
            .getRandomItem(
               rand,
               player.m_36336_(),
               new Predicate[]{IDimensional.matches(sLevel.m_6018_()), GameStagesCompat.IStaged.matches(player), MinibossManager.IEntityMatch.matches(entity)}
            );
         if (item != null && !item.isExcluded(mob, sLevel, e.getSpawnReason()) && sLevel.m_213780_().m_188501_() <= item.getChance()) {
            mob.getPersistentData().m_128359_("apoth.miniboss", item.getId().toString());
            mob.getPersistentData().m_128350_("apoth.miniboss.luck", player.m_36336_());
            if (!item.shouldFinalize()) {
               e.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void delayedMinibosses(EntityJoinLevelEvent e) {
      if (!e.getLevel().f_46443_ && e.getEntity() instanceof Mob mob) {
         String key = mob.getPersistentData().m_128461_("apoth.miniboss");
         if (key != null) {
            MinibossItem item = (MinibossItem)MinibossManager.INSTANCE.getValue(new ResourceLocation(key));
            if (item != null) {
               item.transformMiniboss((ServerLevel)e.getLevel(), mob, e.getLevel().m_213780_(), mob.getPersistentData().m_128457_("apoth.miniboss.luck"));
            }
         }
      }
   }

   @SubscribeEvent
   public void tick(LevelTickEvent e) {
      if (e.phase == Phase.END) {
         this.bossCooldowns.computeIntIfPresent(e.level.m_46472_().m_135782_(), (key, value) -> Math.max(0, value - 1));
      }
   }

   @SubscribeEvent
   public void load(ServerStartedEvent e) {
      e.getServer().m_129880_(Level.f_46428_).m_8895_().m_164861_(this::loadTimes, () -> new BossEvents.TimerPersistData(), "apotheosis_boss_times");
   }

   private BossEvents.TimerPersistData loadTimes(CompoundTag tag) {
      this.bossCooldowns.clear();

      for (String s : tag.m_128431_()) {
         ResourceLocation id = new ResourceLocation(s);
         int val = tag.m_128451_(s);
         this.bossCooldowns.put(id, val);
      }

      return new BossEvents.TimerPersistData();
   }

   private static boolean canSpawn(LevelAccessor world, Mob entity, double playerDist) {
      return playerDist > (double)(entity.m_6095_().m_20674_().m_21611_() * entity.m_6095_().m_20674_().m_21611_()) && entity.m_6785_(playerDist)
         ? false
         : entity.m_5545_(world, MobSpawnType.NATURAL) && entity.m_6914_(world);
   }

   public static enum BossSpawnRules implements BiPredicate<ServerLevelAccessor, BlockPos> {
      NEEDS_SKY(BlockAndTintGetter::m_45527_),
      NEEDS_SURFACE((level, pos) -> pos.m_123342_() >= level.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, pos.m_123341_(), pos.m_123343_())),
      BELOW_SURFACE((level, pos) -> pos.m_123342_() < level.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, pos.m_123341_(), pos.m_123343_())),
      CANNOT_SEE_SKY((level, pos) -> !level.m_45527_(pos)),
      SURFACE_OUTER_END((level, pos) -> NEEDS_SURFACE.test(level, pos) && (Mth.m_14040_(pos.m_123341_()) > 1024 || Mth.m_14040_(pos.m_123343_()) > 1024)),
      ANY((level, pos) -> true);

      public static final Codec<BossEvents.BossSpawnRules> CODEC = new EnumCodec(BossEvents.BossSpawnRules.class);
      BiPredicate<ServerLevelAccessor, BlockPos> pred;

      private BossSpawnRules(BiPredicate<ServerLevelAccessor, BlockPos> pred) {
         this.pred = pred;
      }

      public boolean test(ServerLevelAccessor t, BlockPos u) {
         return this.pred.test(t, u);
      }
   }

   private class TimerPersistData extends SavedData {
      public CompoundTag m_7176_(CompoundTag tag) {
         ObjectIterator var2 = BossEvents.this.bossCooldowns.object2IntEntrySet().iterator();

         while (var2.hasNext()) {
            Entry<ResourceLocation> e = (Entry<ResourceLocation>)var2.next();
            tag.m_128405_(((ResourceLocation)e.getKey()).toString(), e.getIntValue());
         }

         return tag;
      }
   }
}
