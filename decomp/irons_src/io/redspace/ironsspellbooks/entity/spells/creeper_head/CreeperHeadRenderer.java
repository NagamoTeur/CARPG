package io.redspace.ironsspellbooks.entity.spells.creeper_head;

import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.WitherSkull;

public class CreeperHeadRenderer extends WitherSkullRenderer {
   ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/creeper_head.png");

   public CreeperHeadRenderer(Context pContext) {
      super(pContext);
   }

   public ResourceLocation m_5478_(WitherSkull pEntity) {
      return this.TEXTURE;
   }
}
