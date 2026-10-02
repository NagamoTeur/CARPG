package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.ITextureProvider;
import net.minecraft.resources.ResourceLocation;

public class GauntletTextureProvider implements ITextureProvider<GauntletEntity> {
   public ResourceLocation getTexture(GauntletEntity entity) {
      return entity.f_20916_ > 0
         ? new ResourceLocation("bosses_of_mass_destruction", "textures/entity/gauntlet_hurt.png")
         : new ResourceLocation("bosses_of_mass_destruction", "textures/entity/gauntlet.png");
   }
}
