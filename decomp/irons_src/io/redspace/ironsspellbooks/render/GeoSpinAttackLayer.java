package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.spells.SpinAttackModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class GeoSpinAttackLayer extends GeoLayerRenderer<AbstractSpellCastingMob> {
   private final GeoModelProvider<AbstractSpellCastingMob> modelProvider = new SpinAttackModel();

   public GeoSpinAttackLayer(IGeoRenderer entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractSpellCastingMob entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entity.m_21209_()) {
         for (int i = 0; i < 3; i++) {
            matrixStackIn.m_85836_();
            float f = (float)entity.f_19797_ * (float)(-(45 + i * 5));
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f));
            float f1 = 0.75F * (float)i;
            matrixStackIn.m_85841_(f1, f1, f1);
            matrixStackIn.m_85837_(0.0, (double)(-0.2F + 0.6F * (float)i), 0.0);
            this.renderModel(
               this.modelProvider,
               this.modelProvider.getTextureResource(entity),
               matrixStackIn,
               bufferIn,
               packedLightIn,
               entity,
               partialTicks,
               1.0F,
               1.0F,
               1.0F
            );
            matrixStackIn.m_85849_();
         }
      }
   }
}
