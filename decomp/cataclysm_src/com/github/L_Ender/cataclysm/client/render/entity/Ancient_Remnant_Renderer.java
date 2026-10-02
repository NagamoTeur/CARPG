package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ancient_Remnant_Model;
import com.github.L_Ender.cataclysm.client.render.layer.Ancient_Ancient_Remnant_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ancient_Ancient_Remnant_Entity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ancient_Remnant_Renderer extends MobRenderer<Ancient_Ancient_Remnant_Entity, Ancient_Remnant_Model> {
   private static final ResourceLocation REMNANT_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/ancient_remnant/ancient_remnant.png");
   private final RandomSource rnd = RandomSource.m_216327_();

   public Ancient_Remnant_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Ancient_Remnant_Model(), 1.5F);
      this.m_115326_(new Ancient_Ancient_Remnant_Layer(this));
   }

   public Vec3 getRenderOffset(Ancient_Ancient_Remnant_Entity entityIn, float partialTicks) {
      if (entityIn.getAnimation() != Ancient_Ancient_Remnant_Entity.REMNANT_DEATH
         || (entityIn.getAnimationTick() > 52 || entityIn.getAnimationTick() < 43) && (entityIn.getAnimationTick() > 73 || entityIn.getAnimationTick() < 43)) {
         return super.m_7860_(entityIn, partialTicks);
      } else {
         double d0 = 0.04;
         return new Vec3(this.rnd.m_188583_() * d0, 0.0, this.rnd.m_188583_() * d0);
      }
   }

   public ResourceLocation getTextureLocation(Ancient_Ancient_Remnant_Entity entity) {
      return REMNANT_TEXTURES;
   }

   protected float getFlipDegrees(Ancient_Ancient_Remnant_Entity entity) {
      return 0.0F;
   }
}
