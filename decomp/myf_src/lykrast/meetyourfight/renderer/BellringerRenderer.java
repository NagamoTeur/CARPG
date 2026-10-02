package lykrast.meetyourfight.renderer;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.BellringerEntity;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class BellringerRenderer extends HumanoidMobRenderer<BellringerEntity, BellringerModel> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/bellringer.png");
   private static final ResourceLocation GLOW = MeetYourFight.rl("textures/entity/bellringer_glow.png");

   public BellringerRenderer(Context context) {
      super(context, new BellringerModel(context.m_174023_(BellringerModel.MODEL)), 0.5F);
      this.m_115326_(new GenericGlowLayer(this, GLOW));
   }

   public ResourceLocation getTextureLocation(BellringerEntity entity) {
      return TEXTURE;
   }
}
