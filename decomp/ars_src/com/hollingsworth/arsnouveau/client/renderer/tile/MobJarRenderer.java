package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.block.MobJar;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class MobJarRenderer implements BlockEntityRenderer<MobJarTile> {
   private final EntityRenderDispatcher entityRenderer;

   public MobJarRenderer(Context pContext) {
      this.entityRenderer = pContext.m_234446_();
   }

   public void render(MobJarTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
      Entity entity = pBlockEntity.getEntity();
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.m_20205_(), entity.m_20206_());
         if ((double)f1 > 1.0) {
            f = (float)((double)f / ((double)f1 * 1.0));
         }

         if (entity instanceof LightningBolt bolt) {
            if (bolt.f_19853_.m_46467_() % 20L == 0L) {
               bolt.f_20859_ = (long)ClientInfo.ticksInGame;
            }

            f = 0.0075F;
         }

         AtomicReference<Vec3> adjustedScale = new AtomicReference<>(new Vec3(0.0, 0.0, 0.0));
         AtomicReference<Vec3> adjustedTranslation = new AtomicReference<>(new Vec3(0.0, 0.0, 0.0));
         AtomicReference<Boolean> shouldParticlaTick = new AtomicReference<>(false);
         JarBehaviorRegistry.forEach(entity, jarBehavior -> {
            Vec3 customScale = jarBehavior.scaleOffset(pBlockEntity);
            adjustedScale.set(adjustedScale.get().m_82549_(customScale));
            adjustedTranslation.set(adjustedTranslation.get().m_82549_(jarBehavior.translate(pBlockEntity)));
            if (jarBehavior.shouldUsePartialTicks(pBlockEntity)) {
               shouldParticlaTick.set(true);
            }
         });
         Vec3 scale = new Vec3((double)f, (double)f, (double)f).m_82559_(adjustedScale.get().m_82520_(1.0, 1.0, 1.0));
         Vec3 translate = new Vec3(0.5, 0.0, 0.5).m_82549_(adjustedTranslation.get());
         pPoseStack.m_85837_(translate.f_82479_, translate.f_82480_, translate.f_82481_);
         pPoseStack.m_85841_((float)scale.f_82479_, (float)scale.f_82480_, (float)scale.f_82481_);
         Direction direction = (Direction)pBlockEntity.m_58900_().m_61143_(MobJar.FACING);
         if (direction == Direction.EAST) {
            pPoseStack.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
         } else if (direction == Direction.WEST) {
            pPoseStack.m_85845_(Vector3f.f_122227_.m_122240_(-90.0F));
         } else if (direction == Direction.NORTH) {
            pPoseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         } else if (direction == Direction.SOUTH) {
            pPoseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         }

         pPoseStack.m_85845_(((Direction)pBlockEntity.m_58900_().m_61143_(MobJar.FACING)).m_122406_());
         entity.m_20334_(0.0, 0.0, 0.0);
         if (shouldParticlaTick.get()) {
            entity.f_19854_ = entity.m_20185_();
            entity.f_19855_ = entity.m_20186_();
            entity.f_19856_ = entity.m_20189_();
            entity.f_19860_ = entity.f_19858_;
            entity.f_19859_ = entity.f_19857_;
            if (entity instanceof LivingEntity livingEntity) {
               livingEntity.f_20884_ = livingEntity.f_20883_;
               livingEntity.f_20886_ = livingEntity.f_20885_;
               livingEntity.f_20923_ = livingEntity.f_20924_;
            }
         } else {
            pPartialTick = 0.0F;
         }

         this.entityRenderer.m_114384_(entity, 0.0, 0.0, 0.0, 0.0F, pPartialTick, pPoseStack, pBufferSource, pPackedLight);

         for (Entity entity1 : entity.m_20197_()) {
            this.entityRenderer.m_114384_(entity1, 0.0, 0.0, 0.0, 0.0F, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
         }
      }
   }
}
