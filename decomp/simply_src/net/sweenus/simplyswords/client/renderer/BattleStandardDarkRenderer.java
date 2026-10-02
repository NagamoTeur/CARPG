package net.sweenus.simplyswords.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.client.renderer.model.BattleStandardDarkModel;
import net.sweenus.simplyswords.entity.BattleStandardDarkEntity;

@OnlyIn(Dist.CLIENT)
public class BattleStandardDarkRenderer extends MobRenderer<BattleStandardDarkEntity, BattleStandardDarkModel> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("simplyswords", "textures/entity/battlestandard/battlestandarddark_texture.png");

   public BattleStandardDarkRenderer(Context context) {
      super(context, new BattleStandardDarkModel(context.m_174023_(SimplySwords.Client.BATTLESTANDARD_DARK_MODEL)), 0.1F);
   }

   public ResourceLocation getTexture(BattleStandardDarkEntity entity) {
      return TEXTURE;
   }
}
