package dev.latvian.mods.kubejs.core.mixin.common.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.mod.util.JsonSerializable;
import dev.latvian.mods.rhino.util.RemapForJS;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ClickEvent.class})
public abstract class ClickEventMixin implements JsonSerializable {
   @Shadow
   public abstract Action m_130622_();

   @Shadow
   public abstract String m_130623_();

   @RemapForJS("toJson")
   public JsonElement toJsonJS() {
      return (JsonElement)Util.m_137469_(new JsonObject(), json -> {
         json.addProperty("action", this.m_130622_().m_130649_());
         json.addProperty("value", this.m_130623_());
      });
   }
}
