package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceRenderer;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowRenderer;
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrowRenderer;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class ChargeSpellLayer {
   public static HumanoidArm getArmFromUseHand(LivingEntity livingEntity) {
      return livingEntity.m_7655_() == InteractionHand.MAIN_HAND ? livingEntity.m_5737_() : livingEntity.m_5737_().m_20828_();
   }

   public static class Geo extends GeoLayerRenderer<AbstractSpellCastingMob> {
      public Geo(IGeoRenderer entityRenderer) {
         super(entityRenderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource bufferSource,
         int packedLight,
         AbstractSpellCastingMob entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         SyncedSpellData syncedSpellData = ClientMagicData.getSyncedSpellData(entity);
         String spellId = syncedSpellData.getCastingSpellId();
         ResourceLocation modelResource = this.entityRenderer.getGeoModelProvider().getModelResource(entity);
         GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(modelResource);
         GeoBone bone = (GeoBone)model.getBone("bipedHandRight").get();
         poseStack.m_85836_();
         RenderUtils.translateToPivotPoint(poseStack, bone);
         RenderUtils.rotateMatrixAroundBone(poseStack, (GeoBone)model.getBone("right_arm").get());
         RenderUtils.translateAwayFromPivotPoint(poseStack, bone);
         HumanoidArm arm = ChargeSpellLayer.getArmFromUseHand(entity);
         boolean flag = arm == HumanoidArm.LEFT;
         if (spellId.equals(((AbstractSpell)SpellRegistry.LIGHTNING_LANCE_SPELL.get()).getSpellId())) {
            poseStack.m_85837_(-((double)((float)(flag ? -1 : 1) / 32.0F) - 0.125), 0.5, 0.0);
            poseStack.m_85837_(0.0, (double)(-bone.getPivotY() / 16.0F), 0.0);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            LightningLanceRenderer.renderModel(poseStack, bufferSource, entity.f_19797_);
         } else if (spellId.equals(((AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get()).getSpellId())) {
            poseStack.m_85837_(0.0, (double)(-bone.getPivotY() / 16.0F), 0.0);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
            poseStack.m_85837_((double)(-((float)(flag ? -1 : 1) / 32.0F)), 0.5, -0.55);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            MagicArrowRenderer.renderModel(poseStack, bufferSource);
         } else if (spellId.equals(((AbstractSpell)SpellRegistry.POISON_ARROW_SPELL.get()).getSpellId())) {
            poseStack.m_85837_(0.0, (double)(-bone.getPivotY() / 16.0F), 0.0);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
            poseStack.m_85837_((double)(-((float)(flag ? -1 : 1) / 32.0F)), 0.5, -0.55);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            PoisonArrowRenderer.renderModel(poseStack, bufferSource, packedLight);
         }

         poseStack.m_85849_();
      }
   }

   public static class Vanilla<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
      public Vanilla(RenderLayerParent<T, M> pRenderer) {
         super(pRenderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource bufferSource,
         int pPackedLight,
         T entity,
         float pLimbSwing,
         float pLimbSwingAmount,
         float pPartialTick,
         float pAgeInTicks,
         float pNetHeadYaw,
         float pHeadPitch
      ) {
         SyncedSpellData syncedSpellData = ClientMagicData.getSyncedSpellData(entity);
         if (syncedSpellData.isCasting()) {
            String spellId = syncedSpellData.getCastingSpellId();
            poseStack.m_85836_();
            HumanoidArm arm = ChargeSpellLayer.getArmFromUseHand(entity);
            ((HumanoidModel)this.m_117386_()).m_6002_(arm, poseStack);
            boolean flag = arm == HumanoidArm.LEFT;
            if (spellId.equals(((AbstractSpell)SpellRegistry.LIGHTNING_LANCE_SPELL.get()).getSpellId())) {
               poseStack.m_85837_((double)((float)(flag ? -1 : 1) / 32.0F) - 0.125, 0.5, 0.0);
               poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
               float castCompletion = Utils.smoothstep(0.35F, 1.0F, ClientMagicData.getCastCompletionPercent());
               poseStack.m_85841_(castCompletion, castCompletion, castCompletion);
               LightningLanceRenderer.renderModel(poseStack, bufferSource, entity.f_19797_);
            } else if (spellId.equals(((AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get()).getSpellId())) {
               poseStack.m_85837_((double)((float)(flag ? -1 : 1) / 32.0F), 0.5, 0.0);
               poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
               poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
               float castCompletion = Utils.smoothstep(0.65F, 1.0F, ClientMagicData.getCastCompletionPercent());
               poseStack.m_85841_(castCompletion, castCompletion, castCompletion);
               MagicArrowRenderer.renderModel(poseStack, bufferSource);
            } else if (spellId.equals(((AbstractSpell)SpellRegistry.POISON_ARROW_SPELL.get()).getSpellId())) {
               poseStack.m_85837_((double)((float)(flag ? -1 : 1) / 32.0F), 1.0, 0.0);
               poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
               poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
               float castCompletion = Utils.smoothstep(0.65F, 1.0F, ClientMagicData.getCastCompletionPercent());
               poseStack.m_85841_(castCompletion, castCompletion, castCompletion);
               PoisonArrowRenderer.renderModel(poseStack, bufferSource, pPackedLight);
            }

            poseStack.m_85849_();
         }
      }
   }
}
