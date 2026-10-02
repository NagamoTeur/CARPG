package dev.latvian.mods.kubejs.client;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class TagInstance {
   public final ResourceLocation tag;
   public final LinkedHashSet<ResourceKey<? extends Registry<?>>> registries = new LinkedHashSet<>();

   public TagInstance(ResourceLocation tag) {
      this.tag = tag;
   }

   public Component toText() {
      String string = " #"
         + this.tag
         + this.registries
            .stream()
            .<ResourceLocation>map(ResourceKey::m_135782_)
            .map(id -> id.m_135827_().equals("minecraft") ? id.m_135815_() : id.toString())
            .collect(Collectors.joining(" + ", " [", "]"));
      return Component.m_237113_(string).m_130940_(ChatFormatting.DARK_GRAY);
   }
}
