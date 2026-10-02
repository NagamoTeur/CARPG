package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RoseSpiritEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RoseSpiritGlowLayer extends RenderLayer<RoseSpiritEntity, RoseSpiritModel> {
   private static final RenderType NEUTRAL = RenderType.m_234335_(MeetYourFight.rl("textures/entity/rose_spirit_neutral.png"), false);
   private static final RenderType SHOOTING = RenderType.m_234335_(MeetYourFight.rl("textures/entity/rose_spirit_shooting.png"), false);
   private static final RenderType HURT = RenderType.m_234335_(MeetYourFight.rl("textures/entity/rose_spirit_hurt.png"), false);

   public RoseSpiritGlowLayer(RenderLayerParent<RoseSpiritEntity, RoseSpiritModel> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int p_117351_,
      RoseSpiritEntity entity,
      float p_117353_,
      float p_117354_,
      float p_117355_,
      float p_117356_,
      float p_117357_,
      float p_117358_
   ) {
      int status = entity.getStatus();
      RenderType texture = NEUTRAL;
      if (status == 3) {
         texture = SHOOTING;
      } else if (status == 5 || status == 6) {
         texture = HURT;
      }

      VertexConsumer vertexconsumer = buffer.m_6299_(texture);
      ((RoseSpiritModel)this.m_117386_()).m_7695_(poseStack, vertexconsumer, 15728640, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
