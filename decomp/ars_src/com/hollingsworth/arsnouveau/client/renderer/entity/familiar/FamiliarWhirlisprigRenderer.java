package com.hollingsworth.arsnouveau.client.renderer.entity.familiar;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleSparkleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.renderer.entity.WhirlisprigModel;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarWhirlisprig;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;

public class FamiliarWhirlisprigRenderer<T extends FamiliarWhirlisprig> extends GenericFamiliarRenderer<T> {
   public FamiliarWhirlisprigRenderer(Context renderManager) {
      super(renderManager, new WhirlisprigModel());
   }

   public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (!Minecraft.m_91087_().m_91104_()) {
         Level world = entityIn.m_20193_();
         Random rand = ParticleUtil.r;
         Vec3 particlePos = entityIn.m_20182_();
         IBone sylph = ((WhirlisprigModel)this.getGeoModelProvider()).getBone("sylph");
         IBone propellers = ((WhirlisprigModel)this.getGeoModelProvider()).getBone("propellers");
         float offsetY = sylph.getPositionY() / 9.0F;
         float roteAngle = propellers.getRotationY() / 4.0F;
         if (rand.nextInt(5) == 0) {
            for (int i = 0; i < 5; i++) {
               world.m_7106_(
                  ParticleSparkleData.createData(new ParticleColor(52, 255, 36), 0.05F, 60),
                  particlePos.m_7096_() + Math.cos((double)roteAngle) / 2.0,
                  particlePos.m_7098_() + 0.5 + (double)offsetY,
                  particlePos.m_7094_() + Math.sin((double)roteAngle) / 2.0,
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }
   }
}
