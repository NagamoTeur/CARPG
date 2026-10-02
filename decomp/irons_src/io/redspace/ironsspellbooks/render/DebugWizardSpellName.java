package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.debug_wizard.DebugWizard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class DebugWizardSpellName extends GeoLayerRenderer<AbstractSpellCastingMob> {
   Font font = Minecraft.m_91087_().f_91062_;

   public DebugWizardSpellName(IGeoRenderer entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractSpellCastingMob abstractSpellCastingMob,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (abstractSpellCastingMob instanceof DebugWizard debugWizard) {
         String pDisplayName = debugWizard.getSpellInfo();
         if (pDisplayName != null) {
            boolean flag = !debugWizard.m_20163_();
            float f = debugWizard.m_20206_() + 0.5F;
            int i = 0;
            matrixStackIn.m_85836_();
            matrixStackIn.m_85837_(0.0, (double)f, 0.0);
            matrixStackIn.m_85841_(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.m_85850_().m_85861_();
            float f1 = Minecraft.m_91087_().f_91066_.m_92141_(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            float f2 = (float)(-this.font.m_92895_(pDisplayName) / 2);
            this.font.m_92811_(pDisplayName, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag, j, packedLightIn);
            if (flag) {
               this.font.m_92811_(pDisplayName, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }
         }

         matrixStackIn.m_85849_();
      }
   }
}
