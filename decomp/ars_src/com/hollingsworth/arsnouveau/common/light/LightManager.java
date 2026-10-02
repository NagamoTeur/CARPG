package com.hollingsworth.arsnouveau.common.light;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightManager {
   private static final Set<LambDynamicLight> dynamicLightSources = new HashSet<>();
   private static final ReentrantReadWriteLock lightSourcesLock = new ReentrantReadWriteLock();
   public static long lastUpdate = System.currentTimeMillis();
   public static List<Integer> jarHoldingEntityList = new ArrayList<>();
   public static int lastUpdateCount = 0;
   private static final Map<EntityType<?>, List<Function<?, Integer>>> LIGHT_REGISTRY = new HashMap<>();
   private static final double MAX_RADIUS = 7.75;
   private static final double MAX_RADIUS_SQUARED = 60.0625;

   public static void init() {
      register(EntityType.f_20532_, p -> {
         NonNullList<ItemStack> list = p.f_36093_.f_35974_;

         for (int i = 0; i < 9; i++) {
            ItemStack jar = (ItemStack)list.get(i);
            if (jar.m_41720_() == ItemsRegistry.JAR_OF_LIGHT.m_5456_()) {
               return 15;
            }
         }

         return p != ArsNouveau.proxy.getPlayer() && jarHoldingEntityList.contains(p.m_19879_()) ? 15 : 0;
      });
      register(EntityType.f_20450_, p -> p.m_31980_().getLightEmission(p.f_19853_, p.m_20183_()));
      register((EntityType)ModEntities.ENCHANTED_FALLING_BLOCK.get(), p -> p.getBlockState().getLightEmission(p.f_19853_, p.m_20183_()));
      register((EntityType)ModEntities.ENCHANTED_MAGE_BLOCK.get(), p -> p.getBlockState().getLightEmission(p.f_19853_, p.m_20183_()));
      register((EntityType)ModEntities.ENTITY_FLYING_ITEM.get(), p -> 10);
      register((EntityType)ModEntities.ENTITY_FOLLOW_PROJ.get(), p -> 10);
      register((EntityType)ModEntities.SPELL_PROJ.get(), p -> 15);
      register((EntityType)ModEntities.ORBIT_SPELL.get(), p -> 15);
      register((EntityType)ModEntities.LINGER_SPELL.get(), p -> 15);
      register((EntityType)ModEntities.WALL_SPELL.get(), p -> 15);
      register((EntityType)ModEntities.STARBUNCLE_TYPE.get(), p -> p.f_19853_.m_45517_(LightLayer.BLOCK, p.m_20183_()) < 6 ? 10 : 0);
      register((EntityType)ModEntities.ENTITY_FAMILIAR_STARBUNCLE.get(), p -> p.f_19853_.m_45517_(LightLayer.BLOCK, p.m_20183_()) < 6 ? 10 : 0);
      register(EntityType.f_20566_, enderMan -> enderMan.m_32530_() != null ? DynamLightUtil.fromItemLike(enderMan.m_32530_().m_60734_()) : 0);
      register(EntityType.f_20461_, p -> DynamLightUtil.fromItemLike(p.m_32055_().m_41720_()));
      register(EntityType.f_20462_, p -> DynamLightUtil.fromItemLike(p.m_31822_().m_41720_()));
      register(EntityType.f_147033_, p -> Math.max(14, DynamLightUtil.fromItemLike(p.m_31822_().m_41720_())));
      register(EntityType.f_147034_, p -> (int)Mth.m_144920_(0.0F, 12.0F, 1.0F - (float)p.m_147128_() / 10.0F));
      register((EntityType)ModEntities.ANIMATED_BLOCK.get(), p -> p.getBlockState().getLightEmission(p.f_19853_, p.m_20183_()));
   }

   public static <T extends Entity> void register(EntityType<T> type, Function<T, Integer> luminanceFunction) {
      if (!LIGHT_REGISTRY.containsKey(type)) {
         LIGHT_REGISTRY.put(type, new ArrayList<>());
      }

      LIGHT_REGISTRY.get(type).add(luminanceFunction);
   }

   public static <T extends Entity> Map<EntityType<?>, List<Function<?, Integer>>> getLightRegistry() {
      return LIGHT_REGISTRY;
   }

   public static <T extends Entity> int getValue(T entity) {
      int val = 0;
      if (!LIGHT_REGISTRY.containsKey(entity.m_6095_())) {
         return val;
      } else {
         EntityType<?> type = entity.m_6095_();

         for (Function<?, Integer> function : LIGHT_REGISTRY.get(type)) {
            Integer value = ((Function<T, Integer>)function).apply(entity);
            if (value > val) {
               val = value;
            }
         }

         return val;
      }
   }

   public static boolean containsEntity(EntityType<? extends Entity> type) {
      return LIGHT_REGISTRY.containsKey(type) || Config.ENTITY_LIGHT_MAP.containsKey(ForgeRegistries.ENTITY_TYPES.getKey(type));
   }

   public static void addLightSource(LambDynamicLight lightSource) {
      if (lightSource.getDynamicLightWorld().m_5776_()) {
         if (shouldUpdateDynamicLight()) {
            if (!containsLightSource(lightSource)) {
               lightSourcesLock.writeLock().lock();
               dynamicLightSources.add(lightSource);
               lightSourcesLock.writeLock().unlock();
            }
         }
      }
   }

   public static boolean containsLightSource(@NotNull LambDynamicLight lightSource) {
      if (!lightSource.getDynamicLightWorld().m_5776_()) {
         return false;
      } else {
         lightSourcesLock.readLock().lock();
         boolean result = dynamicLightSources.contains(lightSource);
         lightSourcesLock.readLock().unlock();
         return result;
      }
   }

   public int getLightSourcesCount() {
      lightSourcesLock.readLock().lock();
      int result = dynamicLightSources.size();
      lightSourcesLock.readLock().unlock();
      return result;
   }

   public static void removeLightSource(LambDynamicLight lightSource) {
      lightSourcesLock.writeLock().lock();
      Iterator<LambDynamicLight> sourceIterator = dynamicLightSources.iterator();

      while (sourceIterator.hasNext()) {
         LambDynamicLight it = sourceIterator.next();
         if (it.equals(lightSource)) {
            sourceIterator.remove();
            if (Minecraft.m_91087_().f_91073_ != null) {
               lightSource.lambdynlights$scheduleTrackedChunksRebuild(Minecraft.m_91087_().f_91060_);
            }
            break;
         }
      }

      lightSourcesLock.writeLock().unlock();
   }

   public static void clearLightSources() {
      lightSourcesLock.writeLock().lock();
      Iterator<LambDynamicLight> sourceIterator = dynamicLightSources.iterator();

      while (sourceIterator.hasNext()) {
         LambDynamicLight it = sourceIterator.next();
         sourceIterator.remove();
         if (Minecraft.m_91087_().f_91060_ != null) {
            if (it.getLuminance() > 0) {
               it.resetDynamicLight();
            }

            it.lambdynlights$scheduleTrackedChunksRebuild(Minecraft.m_91087_().f_91060_);
         }
      }

      jarHoldingEntityList = new ArrayList<>();
      lightSourcesLock.writeLock().unlock();
   }

   public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, @NotNull BlockPos chunkPos) {
      scheduleChunkRebuild(renderer, chunkPos.m_123341_(), chunkPos.m_123342_(), chunkPos.m_123343_());
   }

   public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, long chunkPos) {
      scheduleChunkRebuild(renderer, BlockPos.m_121983_(chunkPos), BlockPos.m_122008_(chunkPos), BlockPos.m_122015_(chunkPos));
   }

   public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, int x, int y, int z) {
      if (Minecraft.m_91087_().f_91073_ != null) {
         renderer.m_109770_(x, y, z);
      }
   }

   public static void updateAll(LevelRenderer renderer) {
      long now = System.currentTimeMillis();
      lastUpdate = now;
      lastUpdateCount = 0;
      lightSourcesLock.readLock().lock();

      for (LambDynamicLight lightSource : dynamicLightSources) {
         if (lightSource.lambdynlights$updateDynamicLight(renderer)) {
            lastUpdateCount++;
         }
      }

      lightSourcesLock.readLock().unlock();
   }

   public static void updateTrackedChunks(@NotNull BlockPos chunkPos, @Nullable LongOpenHashSet old, @Nullable LongOpenHashSet newPos) {
      if (old != null || newPos != null) {
         long pos = chunkPos.m_121878_();
         if (old != null) {
            old.remove(pos);
         }

         if (newPos != null) {
            newPos.add(pos);
         }
      }
   }

   public static int getLightmapWithDynamicLight(@NotNull BlockPos pos, int lightmap) {
      return getLightmapWithDynamicLight(getDynamicLightLevel(pos), lightmap);
   }

   public static int getLightmapWithDynamicLight(double dynamicLightLevel, int lightmap) {
      if (dynamicLightLevel > 0.0) {
         int blockLevel = getBlockLightNoPatch(lightmap);
         if (dynamicLightLevel > (double)blockLevel) {
            int luminance = (int)(dynamicLightLevel * 16.0);
            lightmap &= -1048576;
            lightmap |= luminance & 1048575;
         }
      }

      return lightmap;
   }

   public static int getBlockLightNoPatch(int light) {
      return light >> 4 & 65535;
   }

   public static double getDynamicLightLevel(@NotNull BlockPos pos) {
      double result = 0.0;
      lightSourcesLock.readLock().lock();

      for (LambDynamicLight lightSource : dynamicLightSources) {
         result = maxDynamicLightLevel(pos, lightSource, result);
      }

      lightSourcesLock.readLock().unlock();
      return Mth.m_14008_(result, 0.0, 15.0);
   }

   public static double maxDynamicLightLevel(@NotNull BlockPos pos, @NotNull LambDynamicLight lightSource, double currentLightLevel) {
      int luminance = lightSource.getLuminance();
      if (luminance > 0) {
         double dx = (double)pos.m_123341_() - lightSource.getDynamicLightX() + 0.5;
         double dy = (double)pos.m_123342_() - lightSource.getDynamicLightY() + 0.5;
         double dz = (double)pos.m_123343_() - lightSource.getDynamicLightZ() + 0.5;
         double distanceSquared = dx * dx + dy * dy + dz * dz;
         if (distanceSquared <= 60.0625) {
            double multiplier = 1.0 - Math.sqrt(distanceSquared) / 7.75;
            double lightLevel = multiplier * (double)luminance;
            if (lightLevel > currentLightLevel) {
               return lightLevel;
            }
         }
      }

      return currentLightLevel;
   }

   public static void updateTracking(@NotNull LambDynamicLight lightSource) {
      boolean enabled = lightSource.isDynamicLightEnabled();
      int luminance = lightSource.getLuminance();
      if (!enabled && luminance > 0) {
         lightSource.setDynamicLightEnabled(true);
      } else if (enabled && luminance < 1) {
         lightSource.setDynamicLightEnabled(false);
      }
   }

   public static boolean shouldUpdateDynamicLight() {
      return Config.DYNAMIC_LIGHTS_ENABLED != null && (Boolean)Config.DYNAMIC_LIGHTS_ENABLED.get();
   }

   public static void toggleLightsAndConfig(boolean enabled) {
      Config.DYNAMIC_LIGHTS_ENABLED.set(enabled);
      Config.DYNAMIC_LIGHTS_ENABLED.save();
      if (!enabled) {
         clearLightSources();
      }
   }
}
