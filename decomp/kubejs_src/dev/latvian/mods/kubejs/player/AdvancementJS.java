package dev.latvian.mods.kubejs.player;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AdvancementJS {
   public final Advancement advancement;

   public AdvancementJS(Advancement a) {
      this.advancement = a;
   }

   @Override
   public boolean equals(Object o) {
      return o == this || o instanceof AdvancementJS && this.advancement.equals(((AdvancementJS)o).advancement);
   }

   @Override
   public int hashCode() {
      return this.advancement.hashCode();
   }

   @Override
   public String toString() {
      return this.getId().toString();
   }

   public ResourceLocation id() {
      return this.getId();
   }

   public ResourceLocation getId() {
      return this.advancement.m_138327_();
   }

   @Nullable
   public AdvancementJS getParent() {
      return this.advancement.m_138319_() == null ? null : new AdvancementJS(this.advancement.m_138319_());
   }

   public Set<AdvancementJS> getChildren() {
      Set<AdvancementJS> set = new LinkedHashSet<>();

      for (Advancement a : this.advancement.m_138322_()) {
         set.add(new AdvancementJS(a));
      }

      return set;
   }

   public void addChild(AdvancementJS a) {
      this.advancement.m_138317_(a.advancement);
   }

   public Component getDisplayText() {
      return this.advancement.m_138330_();
   }

   public boolean hasDisplay() {
      return this.advancement.m_138320_() != null;
   }

   public Component getTitle() {
      return (Component)(this.advancement.m_138320_() != null ? this.advancement.m_138320_().m_14977_() : Component.m_237119_());
   }

   public Component getDescription() {
      return (Component)(this.advancement.m_138320_() != null ? this.advancement.m_138320_().m_14985_() : Component.m_237119_());
   }
}
