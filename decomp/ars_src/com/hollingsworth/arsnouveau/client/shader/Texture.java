package com.hollingsworth.arsnouveau.client.shader;

import net.minecraft.resources.ResourceLocation;

public record Texture(ResourceLocation location, boolean blur, boolean mipmap) {
   public Texture(ResourceLocation location, boolean blur) {
      this(location, blur, false);
   }

   public Texture(ResourceLocation location) {
      this(location, false, false);
   }
}
