package dev.latvian.mods.kubejs.recipe.special;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class SpecialRecipeSerializerManager extends EventJS {
   public static final SpecialRecipeSerializerManager INSTANCE = new SpecialRecipeSerializerManager();
   public static final Event<Runnable> EVENT = EventFactory.createLoop(new Runnable[0]);
   private final Map<ResourceLocation, Boolean> data = new HashMap<>();

   public void reset() {
      synchronized (this.data) {
         this.data.clear();
      }
   }

   @Override
   protected void afterPosted(EventResult result) {
      ((Runnable)EVENT.invoker()).run();
   }

   public boolean isSpecial(Recipe<?> recipe) {
      return this.data.getOrDefault(KubeJSRegistries.recipeSerializers().getId(recipe.m_7707_()), recipe.m_5598_());
   }

   public void ignoreSpecialFlag(ResourceLocation id) {
      synchronized (this.data) {
         this.data.put(id, false);
      }
   }

   public void addSpecialFlag(ResourceLocation id) {
      synchronized (this.data) {
         this.data.put(id, true);
      }
   }

   public void ignoreSpecialMod(String modid) {
      synchronized (this.data) {
         KubeJSRegistries.recipeSerializers().getIds().forEach(id -> {
            if (id.m_135827_().equals(modid)) {
               this.data.put(id, false);
            }
         });
      }
   }

   public void addSpecialMod(String modid) {
      synchronized (this.data) {
         KubeJSRegistries.recipeSerializers().getIds().forEach(id -> {
            if (id.m_135827_().equals(modid)) {
               this.data.put(id, true);
            }
         });
      }
   }
}
