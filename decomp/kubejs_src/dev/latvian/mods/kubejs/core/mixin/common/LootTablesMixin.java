package dev.latvian.mods.kubejs.core.mixin.common;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.core.LootTablesKJS;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({LootTables.class})
public abstract class LootTablesMixin implements LootTablesKJS {
   @Redirect(
      method = {"apply*"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V",
         ordinal = 0
      )
   )
   private void kjs$apply(Map<ResourceLocation, JsonElement> map, BiConsumer<ResourceLocation, JsonElement> action) {
      this.kjs$apply0(map, action);
   }
}
