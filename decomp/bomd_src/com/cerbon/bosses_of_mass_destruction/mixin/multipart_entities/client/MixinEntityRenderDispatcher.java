package com.cerbon.bosses_of_mass_destruction.mixin.multipart_entities.client;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util.OrientedBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderDispatcher.class})
public class MixinEntityRenderDispatcher {
   @Inject(
      method = {"renderHitbox"},
      at = {@At("RETURN")}
   )
   private static void drawOrientedBoxes(PoseStack matrix, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) {
      if (entity.m_20191_() instanceof CompoundOrientedBox compoundOrientedBox) {
         matrix.m_85836_();
         matrix.m_85837_(-entity.m_20185_(), -entity.m_20186_(), -entity.m_20189_());

         for (OrientedBox orientedBox : compoundOrientedBox) {
            matrix.m_85836_();
            Vec3 center = orientedBox.getCenter();
            matrix.m_85837_(center.f_82479_, center.f_82480_, center.f_82481_);
            matrix.m_85845_(orientedBox.getRotation().toFloatQuat());
            LevelRenderer.m_109646_(matrix, vertices, orientedBox.getExtents(), 0.0F, 0.0F, 1.0F, 1.0F);
            matrix.m_85849_();
         }

         compoundOrientedBox.toVoxelShape()
            .m_83286_(
               (minX, minY, minZ, maxX, maxY, maxZ) -> LevelRenderer.m_109608_(matrix, vertices, minX, minY, minZ, maxX, maxY, maxZ, 0.0F, 1.0F, 0.0F, 1.0F)
            );
         matrix.m_85849_();
      }
   }
}
