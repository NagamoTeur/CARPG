package dev.latvian.mods.kubejs.recipe;

import com.google.common.base.Stopwatch;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.core.RecipeKJS;
import dev.latvian.mods.kubejs.event.EventExceptionHandler;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientWithCustomPredicate;
import dev.latvian.mods.kubejs.item.ingredient.TagContext;
import dev.latvian.mods.kubejs.platform.RecipePlatformHelper;
import dev.latvian.mods.kubejs.recipe.filter.ConstantFilter;
import dev.latvian.mods.kubejs.recipe.filter.IDFilter;
import dev.latvian.mods.kubejs.recipe.filter.OrFilter;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import dev.latvian.mods.kubejs.recipe.schema.JsonRecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeConstructor;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaType;
import dev.latvian.mods.kubejs.recipe.special.SpecialRecipeSerializerManager;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.server.DataExport;
import dev.latvian.mods.kubejs.server.KubeJSReloadListener;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.WrappedException;
import dev.latvian.mods.rhino.mod.util.JsonUtils;
import dev.latvian.mods.rhino.util.HideFromJS;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ReportedException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public class RecipesEventJS extends EventJS {
   public static final Pattern SKIP_ERROR = Pattern.compile("at.*dev\\.latvian\\.mods\\.kubejs\\.recipe\\.RecipesEventJS\\.post");
   private static final Predicate<RecipeJS> RECIPE_NOT_REMOVED = r -> r != null && !r.removed;
   private static final EventExceptionHandler RECIPE_EXCEPTION_HANDLER = (event, handler, ex) -> {
      if (!(ex instanceof RecipeExceptionJS) && !(ex instanceof JsonParseException)) {
         return ex;
      } else {
         ConsoleJS.SERVER.error("Error while processing recipe event handler: " + handler, ex);
         return null;
      }
   };
   @HideFromJS
   public static final Map<ResourceLocation, ModifyRecipeResultCallback> MODIFY_RESULT_CALLBACKS = new ConcurrentHashMap<>();
   private static final ForkJoinPool PARALLEL_THREAD_POOL = new ForkJoinPool(Math.max(1, Runtime.getRuntime().availableProcessors() - 1), forkJoinPool -> {
      ForkJoinWorkerThread thread = new ForkJoinWorkerThread(forkJoinPool) {
      };
      thread.setContextClassLoader(RecipesEventJS.class.getClassLoader());
      thread.setName(String.format("KubeJS Recipe Event Worker %d", thread.getPoolIndex()));
      return thread;
   }, (thread, ex) -> {
      while (ex instanceof CompletionException | ex instanceof InvocationTargetException | ex instanceof WrappedException && ex.getCause() != null) {
         ex = ex.getCause();
      }

      if (ex instanceof ReportedException crashed) {
         Bootstrap.m_135875_(crashed.m_134761_().m_127526_());
         System.exit(-1);
      }

      ConsoleJS.SERVER.error("Error in thread %s while performing bulk recipe operation!".formatted(thread), ex);
      RecipeExceptionJS rex = ex instanceof RecipeExceptionJS rex1 ? rex1 : new RecipeExceptionJS(null, ex).error();
      if (rex.error) {
         throw rex;
      }
   }, true);
   @HideFromJS
   public static Map<UUID, IngredientWithCustomPredicate> customIngredientMap = null;
   @HideFromJS
   public static RecipesEventJS instance;
   public final Map<ResourceLocation, RecipeJS> originalRecipes;
   public final Collection<RecipeJS> addedRecipes;
   public final AtomicInteger failedCount;
   public final Map<ResourceLocation, RecipeJS> takenIds;
   private final Map<String, Object> recipeFunctions;
   public final transient RecipeTypeFunction vanillaShaped;
   public final transient RecipeTypeFunction vanillaShapeless;
   public final RecipeTypeFunction shaped;
   public final RecipeTypeFunction shapeless;
   public final RecipeTypeFunction smelting;
   public final RecipeTypeFunction blasting;
   public final RecipeTypeFunction smoking;
   public final RecipeTypeFunction campfireCooking;
   public final RecipeTypeFunction stonecutting;
   public final RecipeTypeFunction smithing;
   final RecipeSerializer<?> stageSerializer;

   public RecipesEventJS() {
      ConsoleJS.SERVER.info("Initializing recipe event...");
      this.originalRecipes = new HashMap<>();
      this.addedRecipes = new ConcurrentLinkedQueue<>();
      this.recipeFunctions = new HashMap<>();
      this.takenIds = new ConcurrentHashMap<>();
      this.failedCount = new AtomicInteger(0);
      Map<String, RecipeNamespace> allNamespaces = RecipeNamespace.getAll();

      for (RecipeNamespace namespace : allNamespaces.values()) {
         HashMap<String, RecipeTypeFunction> nsMap = new HashMap<>();
         this.recipeFunctions.put(namespace.name, new NamespaceFunction(namespace, nsMap));

         for (Entry<String, RecipeSchemaType> entry : namespace.entrySet()) {
            RecipeTypeFunction func = new RecipeTypeFunction(this, entry.getValue());
            nsMap.put(entry.getValue().id.m_135815_(), func);
            this.recipeFunctions.put(entry.getValue().id.toString(), func);
         }
      }

      this.vanillaShaped = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:crafting_shaped");
      this.vanillaShapeless = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:crafting_shapeless");
      this.shaped = CommonProperties.get().serverOnly ? this.vanillaShaped : (RecipeTypeFunction)this.recipeFunctions.get("kubejs:shaped");
      this.shapeless = CommonProperties.get().serverOnly ? this.vanillaShapeless : (RecipeTypeFunction)this.recipeFunctions.get("kubejs:shapeless");
      this.smelting = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:smelting");
      this.blasting = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:blasting");
      this.smoking = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:smoking");
      this.campfireCooking = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:campfire_cooking");
      this.stonecutting = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:stonecutting");
      this.smithing = (RecipeTypeFunction)this.recipeFunctions.get("minecraft:smithing");

      for (Entry<String, Object> entry : new ArrayList<>(this.recipeFunctions.entrySet())) {
         if (entry.getValue() instanceof RecipeTypeFunction && entry.getKey().indexOf(58) != -1) {
            String s = UtilsJS.snakeCaseToCamelCase(entry.getKey());
            if (!s.equals(entry.getKey())) {
               this.recipeFunctions.put(s, entry.getValue());
            }
         }
      }

      for (Entry<String, ResourceLocation> entryx : RecipeNamespace.getMappedRecipes().entrySet()) {
         Object type = this.recipeFunctions.get(entryx.getValue().toString());
         if (type instanceof RecipeTypeFunction) {
            this.recipeFunctions.put(entryx.getKey(), type);
         }
      }

      this.recipeFunctions.put("shaped", this.shaped);
      this.recipeFunctions.put("shapeless", this.shapeless);
      this.recipeFunctions.put("smelting", this.smelting);
      this.recipeFunctions.put("blasting", this.blasting);
      this.recipeFunctions.put("smoking", this.smoking);
      this.recipeFunctions.put("campfireCooking", this.campfireCooking);
      this.recipeFunctions.put("stonecutting", this.stonecutting);
      this.recipeFunctions.put("smithing", this.smithing);
      this.stageSerializer = (RecipeSerializer<?>)KubeJSRegistries.recipeSerializers().get(new ResourceLocation("recipestages:stage"));
   }

   @HideFromJS
   public void post(RecipeManager recipeManager, Map<ResourceLocation, JsonElement> datapackRecipeMap) {
      ConsoleJS.SERVER.info("Processing recipes...");
      RecipeJS.itemErrors = false;
      TagContext.INSTANCE.setValue(TagContext.fromLoadResult(KubeJSReloadListener.resources.f_206849_.m_203904_()));
      MODIFY_RESULT_CALLBACKS.clear();
      Stopwatch timer = Stopwatch.createStarted();
      JsonObject exportedRecipes = new JsonObject();

      for (Entry<ResourceLocation, JsonElement> entry : datapackRecipeMap.entrySet()) {
         ResourceLocation recipeId = entry.getKey();
         String recipeIdAndType = recipeId + "[unknown:type]";

         JsonObject json;
         try {
            if (recipeId == null || Platform.isForge() && recipeId.m_135815_().startsWith("_")) {
               continue;
            }

            json = RecipePlatformHelper.get().checkConditions(GsonHelper.m_13918_(entry.getValue(), "top element"));
            if (json == null) {
               if (DevProperties.get().logSkippedRecipes) {
                  ConsoleJS.SERVER.info("Skipping recipe " + recipeId + ", conditions not met");
               }
               continue;
            }

            if (!json.has("type")) {
               if (DevProperties.get().logSkippedRecipes) {
                  ConsoleJS.SERVER.info("Skipping recipe " + recipeId + ", missing type");
               }
               continue;
            }

            if (DataExport.export != null) {
               exportedRecipes.add(recipeId.toString(), JsonUtils.copy(json));
            }
         } catch (Exception var19) {
            if (DevProperties.get().logSkippedRecipes) {
               ConsoleJS.SERVER.warn("Skipping recipe %s, failed to load: ".formatted(recipeId), var19);
            }
            continue;
         }

         String typeStr = GsonHelper.m_13906_(json, "type");
         recipeIdAndType = recipeId + "[" + typeStr + "]";
         RecipeTypeFunction type = this.getRecipeFunction(typeStr);
         if (type == null) {
            if (DevProperties.get().logSkippedRecipes) {
               ConsoleJS.SERVER.info("Skipping recipe " + recipeId + ", unknown type: " + typeStr);
            }
         } else {
            try {
               RecipeJS recipe = type.schemaType.schema.deserialize(type, recipeId, json);
               recipe.afterLoaded();
               this.originalRecipes.put(recipeId, recipe);
               if (ConsoleJS.SERVER.shouldPrintDebug()) {
                  Recipe<?> originalRecipe = recipe.getOriginalRecipe();
                  if (originalRecipe != null && !SpecialRecipeSerializerManager.INSTANCE.isSpecial(originalRecipe)) {
                     ConsoleJS.SERVER.debug("Loaded recipe " + recipeIdAndType + ": " + recipe.getFromToString());
                  } else {
                     ConsoleJS.SERVER.debug("Loaded recipe " + recipeIdAndType + ": <dynamic>");
                  }
               }
            } catch (Throwable var18) {
               if (DevProperties.get().logErroringRecipes || DevProperties.get().debugInfo) {
                  ConsoleJS.SERVER.warn("Failed to parse recipe '" + recipeIdAndType + "'! Falling back to vanilla", var18, SKIP_ERROR);
               }

               try {
                  this.originalRecipes.put(recipeId, JsonRecipeSchema.SCHEMA.deserialize(type, recipeId, json));
               } catch (IllegalArgumentException | JsonParseException | NullPointerException var16) {
                  if (DevProperties.get().logErroringRecipes || DevProperties.get().debugInfo) {
                     ConsoleJS.SERVER.warn("Failed to parse recipe " + recipeIdAndType, var16, SKIP_ERROR);
                  }
               } catch (Exception var17) {
                  ConsoleJS.SERVER.warn("Failed to parse recipe " + recipeIdAndType + ":");
                  ConsoleJS.SERVER.printStackTrace(false, var17, SKIP_ERROR);
               }
            }
         }
      }

      this.takenIds.putAll(this.originalRecipes);
      ConsoleJS.SERVER.info("Found " + this.originalRecipes.size() + " recipes in " + timer.stop());
      timer.reset().start();
      ServerEvents.RECIPES.post(ScriptType.SERVER, this);
      int modifiedCount = 0;
      ConcurrentLinkedQueue<RecipeJS> removedRecipes = new ConcurrentLinkedQueue<>();

      for (RecipeJS r : this.originalRecipes.values()) {
         if (r.removed) {
            removedRecipes.add(r);
         } else if (r.hasChanged()) {
            modifiedCount++;
         }
      }

      ConsoleJS.SERVER.info("Posted recipe events in " + timer.stop());
      timer.reset().start();
      this.addedRecipes.removeIf(RecipesEventJS::addedRecipeRemoveCheck);
      HashMap<ResourceLocation, Recipe<?>> recipesByName = new HashMap<>(this.originalRecipes.size() + this.addedRecipes.size());

      try {
         recipesByName.putAll(
            runInParallel(
               () -> this.originalRecipes
                     .values()
                     .parallelStream()
                     .filter(RECIPE_NOT_REMOVED)
                     .map(this::createRecipe)
                     .filter(Objects::nonNull)
                     .collect(Collectors.toConcurrentMap(Recipe::m_6423_, Function.identity(), (a, b) -> {
                        ConsoleJS.SERVER.warn("Duplicate recipe for id " + a.m_6423_() + "! Using last one encountered.");
                        return b;
                     }))
            )
         );
      } catch (Throwable var15) {
         ConsoleJS.SERVER.error("Error creating datapack recipes", var15, SKIP_ERROR);
      }

      try {
         recipesByName.putAll(
            runInParallel(
               () -> this.addedRecipes
                     .parallelStream()
                     .map(this::createRecipe)
                     .filter(Objects::nonNull)
                     .collect(Collectors.toConcurrentMap(Recipe::m_6423_, Function.identity(), (a, b) -> {
                        ConsoleJS.SERVER.warn("Duplicate recipe for id " + a.m_6423_() + "! Using last one encountered.");
                        ConsoleJS.SERVER.warn("You might want to check your scripts for errors.");
                        return b;
                     }))
            )
         );
      } catch (Throwable var14) {
         ConsoleJS.SERVER.error("Error creating script recipes", var14, SKIP_ERROR);
      }

      KubeJSPlugins.forEachPlugin(p -> p.injectRuntimeRecipes(this, recipeManager, recipesByName));
      HashMap<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newRecipeMap = new HashMap<>();

      for (Entry<ResourceLocation, Recipe<?>> entry : recipesByName.entrySet()) {
         RecipeType<?> type = entry.getValue().m_6671_();
         Map<ResourceLocation, Recipe<?>> recipes = newRecipeMap.computeIfAbsent(type, k -> new HashMap<>());
         recipes.put(entry.getKey(), entry.getValue());
      }

      RecipePlatformHelper.get().pingNewRecipes(newRecipeMap);
      recipeManager.f_199900_ = recipesByName;
      recipeManager.f_44007_ = newRecipeMap;
      ConsoleJS.SERVER
         .info(
            "Added "
               + this.addedRecipes.size()
               + " recipes, removed "
               + removedRecipes.size()
               + " recipes, modified "
               + modifiedCount
               + " recipes, with "
               + this.failedCount.get()
               + " failed recipes in "
               + timer.stop()
         );
      RecipeJS.itemErrors = false;
      if (DataExport.export != null) {
         for (RecipeJS rx : removedRecipes) {
            DataExport.export.addJson("removed_recipes/" + rx.getId() + ".json", rx.json);
         }
      }

      if (DevProperties.get().debugInfo) {
         ConsoleJS.SERVER.info("======== Debug output of all added recipes ========");

         for (RecipeJS rx : this.addedRecipes) {
            ConsoleJS.SERVER.info(rx.getOrCreateId() + ": " + rx.json);
         }

         ConsoleJS.SERVER.info("======== Debug output of all modified recipes ========");

         for (RecipeJS rx : this.originalRecipes.values()) {
            if (!rx.removed && rx.hasChanged()) {
               ConsoleJS.SERVER.info(rx.getOrCreateId() + ": " + rx.json + " FROM " + rx.originalJson);
            }
         }

         ConsoleJS.SERVER.info("======== Debug output of all removed recipes ========");

         for (RecipeJS rxx : removedRecipes) {
            ConsoleJS.SERVER.info(rxx.getOrCreateId() + ": " + rxx.json);
         }
      }
   }

   @Nullable
   private Recipe<?> createRecipe(RecipeJS r) {
      try {
         Recipe<?> rec = r.createRecipe();
         String path = r.kjs$getMod() + "/" + r.getPath();
         if (!r.removed && DataExport.export != null) {
            DataExport.export.addJson("recipes/%s.json".formatted(path), r.json);
            if (r.newRecipe) {
               DataExport.export.addJson("added_recipes/%s.json".formatted(path), r.json);
            }
         }

         return rec;
      } catch (Throwable var4) {
         ConsoleJS.SERVER.warn("Error parsing recipe " + r + ": " + r.json, var4, SKIP_ERROR);
         this.failedCount.incrementAndGet();
         return null;
      }
   }

   private static boolean addedRecipeRemoveCheck(RecipeJS r) {
      return !r.newRecipe;
   }

   public Map<String, Object> getRecipes() {
      return this.recipeFunctions;
   }

   public RecipeJS addRecipe(RecipeJS r, boolean json) {
      if (r instanceof ErroredRecipeJS) {
         ConsoleJS.SERVER.warn("Tried to add errored recipe %s!".formatted(r));
         return r;
      } else {
         this.addedRecipes.add(r);
         if (DevProperties.get().logAddedRecipes) {
            ConsoleJS.SERVER.info("+ " + r.getType() + ": " + r.getFromToString() + (json ? " [json]" : ""));
         } else if (ConsoleJS.SERVER.shouldPrintDebug()) {
            ConsoleJS.SERVER.debug("+ " + r.getType() + ": " + r.getFromToString() + (json ? " [json]" : ""));
         }

         return r;
      }
   }

   public RecipeFilter customFilter(Predicate<RecipeKJS> filter) {
      return filter::test;
   }

   public Stream<RecipeJS> recipeStream(RecipeFilter filter) {
      if (filter == ConstantFilter.FALSE) {
         return Stream.empty();
      } else if (filter instanceof IDFilter id) {
         RecipeJS r = this.originalRecipes.get(id.id);
         return r != null && !r.removed ? Stream.of(r) : Stream.empty();
      } else {
         if (filter instanceof OrFilter or) {
            if (or.list.isEmpty()) {
               return Stream.empty();
            }

            Iterator r = or.list.iterator();

            RecipeFilter recipeFilter;
            do {
               if (!r.hasNext()) {
                  return or.list.stream().map(idf -> this.originalRecipes.get(((IDFilter)idf).id)).filter(RECIPE_NOT_REMOVED);
               }

               recipeFilter = (RecipeFilter)r.next();
            } while (recipeFilter instanceof IDFilter);
         }

         return this.originalRecipes.values().stream().filter(new RecipesEventJS.RecipeStreamFilter(filter));
      }
   }

   @Internal
   private Stream<RecipeJS> recipeStreamAsync(RecipeFilter filter) {
      Stream<RecipeJS> stream = this.recipeStream(filter);
      return CommonProperties.get().allowAsyncStreams ? stream.parallel() : stream;
   }

   private void forEachRecipeAsync(RecipeFilter filter, Consumer<RecipeJS> consumer) {
      runInParallel(() -> this.recipeStreamAsync(filter).forEach(consumer));
   }

   private <T> T reduceRecipesAsync(RecipeFilter filter, Function<Stream<RecipeJS>, T> function) {
      return runInParallel(() -> function.apply(this.recipeStreamAsync(filter)));
   }

   public void forEachRecipe(RecipeFilter filter, Consumer<RecipeJS> consumer) {
      this.recipeStream(filter).forEach(consumer);
   }

   public int countRecipes(RecipeFilter filter) {
      return this.reduceRecipesAsync(filter, s -> (int)s.count());
   }

   public boolean containsRecipe(RecipeFilter filter) {
      return this.reduceRecipesAsync(filter, s -> s.findAny().isPresent());
   }

   public Collection<RecipeJS> findRecipes(RecipeFilter filter) {
      return this.reduceRecipesAsync(filter, Stream::toList);
   }

   public Collection<ResourceLocation> findRecipeIds(RecipeFilter filter) {
      return this.reduceRecipesAsync(filter, s -> s.map(RecipeJS::getOrCreateId).toList());
   }

   public void remove(RecipeFilter filter) {
      if (filter instanceof IDFilter id) {
         RecipeJS r = this.originalRecipes.get(id.id);
         if (r != null) {
            r.remove();
         }
      } else {
         this.forEachRecipeAsync(filter, RecipeJS::remove);
      }
   }

   public void replaceInput(RecipeFilter filter, ReplacementMatch match, InputReplacement with) {
      String dstring = !DevProperties.get().logModifiedRecipes && !ConsoleJS.SERVER.shouldPrintDebug() ? "" : ": IN " + match + " -> " + with;
      this.forEachRecipeAsync(filter, r -> {
         if (r.replaceInput(match, with)) {
            if (DevProperties.get().logModifiedRecipes) {
               ConsoleJS.SERVER.info("~ " + r + dstring);
            } else if (ConsoleJS.SERVER.shouldPrintDebug()) {
               ConsoleJS.SERVER.debug("~ " + r + dstring);
            }
         }
      });
   }

   public void replaceOutput(RecipeFilter filter, ReplacementMatch match, OutputReplacement with) {
      String dstring = !DevProperties.get().logModifiedRecipes && !ConsoleJS.SERVER.shouldPrintDebug() ? "" : ": OUT " + match + " -> " + with;
      this.forEachRecipeAsync(filter, r -> {
         if (r.replaceOutput(match, with)) {
            if (DevProperties.get().logModifiedRecipes) {
               ConsoleJS.SERVER.info("~ " + r + dstring);
            } else if (ConsoleJS.SERVER.shouldPrintDebug()) {
               ConsoleJS.SERVER.debug("~ " + r + dstring);
            }
         }
      });
   }

   public RecipeTypeFunction getRecipeFunction(@Nullable String id) {
      if (id != null && !id.isEmpty()) {
         Object var3 = this.recipeFunctions.get(UtilsJS.getID(id));
         return var3 instanceof RecipeTypeFunction ? (RecipeTypeFunction)var3 : null;
      } else {
         return null;
      }
   }

   public RecipeJS custom(JsonObject json) {
      try {
         if (json != null && json.has("type")) {
            RecipeTypeFunction type = this.getRecipeFunction(json.get("type").getAsString());
            if (type == null) {
               throw new RecipeExceptionJS("Unknown recipe type: " + json.get("type").getAsString());
            } else {
               RecipeJS recipe = type.schemaType.schema.deserialize(type, null, json);
               recipe.afterLoaded();
               return this.addRecipe(recipe, true);
            }
         } else {
            throw new RecipeExceptionJS("JSON must contain 'type'!");
         }
      } catch (RecipeExceptionJS var4) {
         if (var4.error) {
            throw var4;
         } else {
            return new ErroredRecipeJS(this, "Failed to create custom JSON recipe from '%s'".formatted(json), var4, SKIP_ERROR);
         }
      }
   }

   private void printTypes(Predicate<RecipeSchemaType> predicate) {
      int t = 0;
      IdentityHashMap<RecipeSchema, Set<ResourceLocation>> map = new IdentityHashMap<>();

      for (RecipeNamespace ns : RecipeNamespace.getAll().values()) {
         for (RecipeSchemaType type : ns.values()) {
            if (predicate.test(type)) {
               t++;
               map.computeIfAbsent(type.schema, s -> new HashSet<>()).add(type.id);
            }
         }
      }

      for (Entry<RecipeSchema, Set<ResourceLocation>> entry : map.entrySet()) {
         ConsoleJS.SERVER.info("- " + entry.getValue().stream().<CharSequence>map(ResourceLocation::toString).collect(Collectors.joining(", ")));
         ObjectIterator var10 = entry.getKey().constructors().values().iterator();

         while (var10.hasNext()) {
            RecipeConstructor c = (RecipeConstructor)var10.next();
            ConsoleJS.SERVER.info("  - " + c);
         }
      }

      ConsoleJS.SERVER.info(t + " types");
   }

   public void printTypes() {
      ConsoleJS.SERVER.info("== All recipe types [used] ==");
      Set<ResourceLocation> set = this.reduceRecipesAsync(ConstantFilter.TRUE, s -> s.<ResourceLocation>map(r -> r.type.id).collect(Collectors.toSet()));
      this.printTypes(t -> set.contains(t.id));
   }

   public void printAllTypes() {
      ConsoleJS.SERVER.info("== All recipe types [available] ==");
      this.printTypes(t -> KubeJSRegistries.recipeSerializers().get(t.id) != null);
   }

   public void printExamples(String type) {
      List<RecipeJS> list = this.originalRecipes.values().stream().filter(recipeJS -> recipeJS.type.toString().equals(type)).collect(Collectors.toList());
      Collections.shuffle(list);
      ConsoleJS.SERVER.info("== Random examples of '" + type + "' ==");

      for (int i = 0; i < Math.min(list.size(), 5); i++) {
         RecipeJS r = list.get(i);
         ConsoleJS.SERVER.info("- " + r.getOrCreateId() + ":\n" + JsonIO.toPrettyString(r.json));
      }
   }

   public synchronized ResourceLocation takeId(RecipeJS recipe, String prefix, String ids) {
      int i = 2;

      ResourceLocation id;
      for (id = new ResourceLocation(prefix + ids); this.takenIds.containsKey(id); i++) {
         id = new ResourceLocation(prefix + ids + "_" + i);
      }

      this.takenIds.put(id, recipe);
      return id;
   }

   public void setItemErrors(boolean b) {
      RecipeJS.itemErrors = b;
   }

   public void stage(RecipeFilter filter, String stage) {
      this.forEachRecipeAsync(filter, r -> r.stage(stage));
   }

   public static void runInParallel(Runnable runnable) {
      PARALLEL_THREAD_POOL.invoke(ForkJoinTask.adapt(runnable));
   }

   public static <T> T runInParallel(Callable<T> callable) {
      return PARALLEL_THREAD_POOL.invoke(ForkJoinTask.adapt(callable));
   }

   private static record RecipeStreamFilter(RecipeFilter filter) implements Predicate<RecipeJS> {
      public boolean test(RecipeJS r) {
         return r != null && !r.removed && this.filter.test((RecipeKJS)r);
      }
   }
}
