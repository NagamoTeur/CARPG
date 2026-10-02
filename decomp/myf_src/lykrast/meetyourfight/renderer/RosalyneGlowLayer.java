package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RosalyneGlowLayer extends RenderLayer<RosalyneEntity, RosalyneModel> {
   private static final RenderType COFFIN_GLOW = RenderType.m_234335_(MeetYourFight.rl("textures/entity/rosalyne_coffin_glow.png"), false);
   private static final RenderType BASE_GLOW = RenderType.m_234335_(MeetYourFight.rl("textures/entity/rosalyne_glow.png"), false);

   public RosalyneGlowLayer(RenderLayerParent<RosalyneEntity, RosalyneModel> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int p_117351_,
      RosalyneEntity entity,
      float p_117353_,
      float p_117354_,
      float p_117355_,
      float p_117356_,
      float p_117357_,
      float p_117358_
   ) {
      int phase = entity.getPhase();
      VertexConsumer vertexconsumer = buffer.m_6299_(phase != 0 && phase != 1 ? BASE_GLOW : COFFIN_GLOW);
      ((RosalyneModel)this.m_117386_()).m_7695_(poseStack, vertexconsumer, 15728640, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
