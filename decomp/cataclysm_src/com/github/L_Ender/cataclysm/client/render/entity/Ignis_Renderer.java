package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ignis_Model;
import com.github.L_Ender.cataclysm.client.render.RenderUtils;
import com.github.L_Ender.cataclysm.client.render.layer.Ignis_Armor_Crack_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.Ignis_Shield_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ignis_Renderer extends MobRenderer<Ignis_Entity, Ignis_Model> {
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[8];
   private static final ResourceLocation[] TEXTURE_SOUL_PROGRESS = new ResourceLocation[8];

   public Ignis_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Ignis_Model(), 1.0F);
      this.m_115326_(new Ignis_Armor_Crack_Layer(this));
      this.m_115326_(new Ignis_Shield_Layer(this));

      for (int i = 0; i < 8; i++) {
         TEXTURE_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/entity/ignis/ignis_idle_" + i + ".png");
         TEXTURE_SOUL_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/entity/ignis/ignis_soul_idle_" + i + ".png");
      }
   }

   public ResourceLocation getTextureLocation(Ignis_Entity entity) {
      return this.getGrowingTexture(entity, (int)((float)entity.f_19797_ * 0.5F % 7.0F));
   }

   public ResourceLocation getGrowingTexture(Ignis_Entity entity, int age) {
      return entity.getBossPhase() > 0 ? TEXTURE_SOUL_PROGRESS[Mth.m_14045_(age, 0, 7)] : TEXTURE_PROGRESS[Mth.m_14045_(age, 0, 7)];
   }

   public void render(Ignis_Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == Ignis_Entity.HORIZONTAL_SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SWING_ATTACK_SOUL
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK_SOUL
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK_BERSERK
         || entity.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR
         || entity.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR_SOUL
         || entity.getAnimation() == Ignis_Entity.PHASE_3
         || entity.getAnimation() == Ignis_Entity.SPIN_ATTACK
         || entity.getAnimation() == Ignis_Entity.ULTIMATE_ATTACK
         || entity.getAnimation() == Ignis_Entity.STRIKE
         || entity.getAnimation() == Ignis_Entity.COMBO1
         || entity.getAnimation() == Ignis_Entity.COMBO2
         || entity.getAnimation() == Ignis_Entity.SHIELD_BREAK_STRIKE
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ALT_ATTACK2
         || entity.getAnimation() == Ignis_Entity.SWING_UPPERSLASH) {
         Vec3 bladePos = RenderUtils.matrixStackFromCitadelModel(entity, entityYaw, ((Ignis_Model)this.f_115290_).blade2);
         entity.setSocketPosArray(0, bladePos);
      }
   }

   protected int getBlockLightLevel(Ignis_Entity entityIn, BlockPos pos) {
      return 15;
   }

   protected float getFlipDegrees(Ignis_Entity entity) {
      return 0.0F;
   }
}
