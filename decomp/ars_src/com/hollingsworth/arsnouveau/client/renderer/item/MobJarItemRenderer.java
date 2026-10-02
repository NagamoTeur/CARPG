package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.items.MobJarItem;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class MobJarItemRenderer extends FixedGeoItemRenderer<MobJarItem> {
   private static MobJarTile jarTile;

   public MobJarItemRenderer() {
      super(new GenericModel("mob_jar"));
   }

   @Override
   public void m_108829_(ItemStack stack, TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
      if (pTransformType == TransformType.GUI) {
         pPackedLight = 15728880;
         pPackedOverlay = 15728880;
      }

      super.m_108829_(stack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
      jarTile = new MobJarTile(Minecraft.m_91087_().f_91074_.m_20097_().m_7494_(), BlockRegistry.MOB_JAR.m_49966_());
      Entity entity = MobJarItem.fromItem(stack, Minecraft.m_91087_().f_91073_);
      if (entity != null) {
         jarTile.m_142339_(Minecraft.m_91087_().f_91073_);
         jarTile.cachedEntity = entity;
         entity.m_6034_(
            (double)Minecraft.m_91087_().f_91074_.m_20097_().m_123341_(),
            (double)(Minecraft.m_91087_().f_91074_.m_20097_().m_123342_() + 1),
            (double)Minecraft.m_91087_().f_91074_.m_20097_().m_123343_()
         );
         pPoseStack.m_85836_();
         pPoseStack.m_85837_(0.0, 0.5, 0.0);
         Minecraft.m_91087_().m_167982_().m_112272_(jarTile, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
         pPoseStack.m_85849_();
      }
   }
}
