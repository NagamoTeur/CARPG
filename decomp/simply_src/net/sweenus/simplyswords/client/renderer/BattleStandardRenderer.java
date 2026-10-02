package net.sweenus.simplyswords.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.client.renderer.model.BattleStandardModel;
import net.sweenus.simplyswords.entity.BattleStandardEntity;

@OnlyIn(Dist.CLIENT)
public class BattleStandardRenderer extends MobRenderer<BattleStandardEntity, BattleStandardModel> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("simplyswords", "textures/entity/battlestandard/battlestandard_texture.png");

   public BattleStandardRenderer(Context context) {
      super(context, new BattleStandardModel(context.m_174023_(SimplySwords.Client.BATTLESTANDARD_MODEL)), 0.1F);
   }

   public ResourceLocation getTexture(BattleStandardEntity entity) {
      return TEXTURE;
   }
}
