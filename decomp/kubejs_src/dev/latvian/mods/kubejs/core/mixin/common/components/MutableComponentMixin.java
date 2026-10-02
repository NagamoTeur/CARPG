package dev.latvian.mods.kubejs.core.mixin.common.components;

import dev.latvian.mods.kubejs.core.ComponentKJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@RemapPrefixForJS("kjs$")
@Mixin({MutableComponent.class})
public abstract class MutableComponentMixin implements ComponentKJS {
   @HideFromJS
   @Shadow
   public abstract MutableComponent m_130946_(String var1);

   @Override
   public Iterator<Component> iterator() {
      if (!this.kjs$hasSiblings()) {
         return UtilsJS.cast(List.of(this.kjs$self()).iterator());
      } else {
         List<Component> list = new LinkedList<>();
         list.add(this.kjs$self());

         for (Component child : this.m_7360_()) {
            if (child instanceof ComponentKJS wrapped) {
               wrapped.forEach(list::add);
            } else {
               list.add(child);
            }
         }

         return list.iterator();
      }
   }
}
