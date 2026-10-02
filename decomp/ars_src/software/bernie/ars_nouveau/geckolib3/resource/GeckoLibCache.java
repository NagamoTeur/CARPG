package software.bernie.ars_nouveau.geckolib3.resource;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.file.AnimationFile;
import software.bernie.ars_nouveau.geckolib3.file.AnimationFileLoader;
import software.bernie.ars_nouveau.geckolib3.file.GeoModelLoader;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;

public class GeckoLibCache {
   private static GeckoLibCache INSTANCE;
   private static final Set<String> excludedNamespaces = ObjectOpenHashSet.of("moreplayermodels", "customnpcs", "gunsrpg");
   private final AnimationFileLoader animationLoader;
   private final GeoModelLoader modelLoader;
   public final MolangParser parser = new MolangParser();
   private Map<ResourceLocation, AnimationFile> animations = Collections.emptyMap();
   private Map<ResourceLocation, GeoModel> geoModels = Collections.emptyMap();

   public Map<ResourceLocation, AnimationFile> getAnimations() {
      if (!GeckoLib.hasInitialized) {
         throw new RuntimeException("GeckoLib was never initialized! Please read the documentation!");
      } else {
         return this.animations;
      }
   }

   public Map<ResourceLocation, GeoModel> getGeoModels() {
      if (!GeckoLib.hasInitialized) {
         throw new RuntimeException("GeckoLib was never initialized! Please read the documentation!");
      } else {
         return this.geoModels;
      }
   }

   protected GeckoLibCache() {
      this.animationLoader = new AnimationFileLoader();
      this.modelLoader = new GeoModelLoader();
   }

   public static GeckoLibCache getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new GeckoLibCache();
         return INSTANCE;
      } else {
         return INSTANCE;
      }
   }

   public CompletableFuture<Void> reload(
      PreparationBarrier stage,
      ResourceManager resourceManager,
      ProfilerFiller preparationsProfiler,
      ProfilerFiller reloadProfiler,
      Executor backgroundExecutor,
      Executor gameExecutor
   ) {
      Map<ResourceLocation, AnimationFile> animations = new Object2ObjectOpenHashMap();
      Map<ResourceLocation, GeoModel> geoModels = new Object2ObjectOpenHashMap();
      return CompletableFuture.allOf(
            loadResources(
               backgroundExecutor,
               resourceManager,
               "animations",
               animation -> this.animationLoader.loadAllAnimations(this.parser, animation, resourceManager),
               animations::put
            ),
            loadResources(backgroundExecutor, resourceManager, "geo", resource -> this.modelLoader.loadModel(resourceManager, resource), geoModels::put)
         )
         .<Void>thenCompose(stage::m_6769_)
         .thenAcceptAsync(empty -> {
            this.animations = animations;
            this.geoModels = geoModels;
         }, gameExecutor);
   }

   private static <T> CompletableFuture<Void> loadResources(
      Executor executor, ResourceManager resourceManager, String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map
   ) {
      return CompletableFuture.<Map>supplyAsync(() -> resourceManager.m_214159_(type, fileName -> fileName.toString().endsWith(".json")), executor)
         .thenApplyAsync(resources -> {
            Map<ResourceLocation, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap();

            for (ResourceLocation resource : resources.keySet()) {
               CompletableFuture<T> existing = tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
               if (existing != null) {
                  System.err.println("Duplicate resource for " + resource);
                  existing.cancel(false);
               }
            }

            return tasks;
         }, executor)
         .thenAcceptAsync(tasks -> {
            for (Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
               if (!excludedNamespaces.contains(entry.getKey().m_135827_().toLowerCase(Locale.ROOT))) {
                  map.accept(entry.getKey(), entry.getValue().join());
               }
            }
         }, executor);
   }
}
