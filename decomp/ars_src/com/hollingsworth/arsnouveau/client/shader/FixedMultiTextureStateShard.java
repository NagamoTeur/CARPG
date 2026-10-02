package com.hollingsworth.arsnouveau.client.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard.EmptyTextureStateShard;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FixedMultiTextureStateShard extends EmptyTextureStateShard {
   private final Optional<ResourceLocation> cutoutTexture;

   public FixedMultiTextureStateShard(List<Texture> textures) {
      super(() -> {
         int i = 0;

         for (Texture texture : textures) {
            TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
            texturemanager.m_118506_(texture.location()).m_117960_(texture.blur(), texture.mipmap());
            RenderSystem.m_157456_(i, texture.location());
            if (i == 0) {
               i = 3;
            } else {
               i++;
            }
         }
      }, () -> {
      });
      this.cutoutTexture = textures.stream().findFirst().map(Texture::location);
   }

   @NotNull
   protected Optional<ResourceLocation> m_142706_() {
      return this.cutoutTexture;
   }
}
