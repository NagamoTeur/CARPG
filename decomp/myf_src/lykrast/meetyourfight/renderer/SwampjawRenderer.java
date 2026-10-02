package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.SwampjawEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SwampjawRenderer extends MobRenderer<SwampjawEntity, SwampjawModel> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/swampjaw.png");

   public SwampjawRenderer(Context context) {
      super(context, new SwampjawModel(context.m_174023_(SwampjawModel.MODEL)), 0.75F);
   }

   public ResourceLocation getTextureLocation(SwampjawEntity entity) {
      return TEXTURE;
   }

   protected void scale(SwampjawEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(2.0F, 2.0F, 2.0F);
   }

   protected void setupRotations(SwampjawEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
      super.m_7523_(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(entityLiving.m_146909_()));
   }
}
