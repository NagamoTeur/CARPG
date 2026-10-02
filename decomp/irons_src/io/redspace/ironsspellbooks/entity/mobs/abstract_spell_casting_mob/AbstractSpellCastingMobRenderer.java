package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.render.ChargeSpellLayer;
import io.redspace.ironsspellbooks.render.EnergySwirlLayer;
import io.redspace.ironsspellbooks.render.GeoSpinAttackLayer;
import io.redspace.ironsspellbooks.render.GlowingEyesLayer;
import io.redspace.ironsspellbooks.render.SpellRenderingHelper;
import io.redspace.ironsspellbooks.render.SpellTargetingLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public abstract class AbstractSpellCastingMobRenderer extends GeoHumanoidRenderer<AbstractSpellCastingMob> {
   private ResourceLocation textureResource;

   public AbstractSpellCastingMobRenderer(Context renderManager, AbstractSpellCastingMobModel model) {
      super(renderManager, model);
      this.f_114477_ = 0.5F;
      this.addLayer(new EnergySwirlLayer.Geo(this, EnergySwirlLayer.EVASION_TEXTURE, 2L));
      this.addLayer(new EnergySwirlLayer.Geo(this, EnergySwirlLayer.CHARGE_TEXTURE, 64L));
      this.addLayer(new ChargeSpellLayer.Geo(this));
      this.addLayer(new GlowingEyesLayer.Geo(this));
      this.addLayer(new SpellTargetingLayer.Geo(this));
      this.addLayer(new GeoSpinAttackLayer(this));
   }

   protected boolean shouldWeaponBeSheathed(AbstractSpellCastingMob entity) {
      return entity.shouldSheathSword() && !entity.m_5912_();
   }

   protected boolean isBoneMainHand(AbstractSpellCastingMob entity, String boneName) {
      return entity.m_21526_() && boneName.equals("bipedHandLeft") || !entity.m_21526_() && boneName.equals("bipedHandRight");
   }

   @Nullable
   protected ItemStack getHeldItemForBone(String boneName, AbstractSpellCastingMob entity) {
      if (this.isBoneMainHand(entity, boneName)) {
         if (((AbstractSpellCastingMob)this.animatable).isDrinkingPotion()) {
            return this.makePotion(entity);
         }

         if (this.shouldWeaponBeSheathed(entity) && entity.m_6844_(EquipmentSlot.MAINHAND).m_41720_() instanceof SwordItem) {
            return ItemStack.f_41583_;
         }
      }

      return boneName.equals("torso") && this.shouldWeaponBeSheathed(entity) && entity.m_6844_(EquipmentSlot.MAINHAND).m_41720_() instanceof SwordItem
         ? entity.m_6844_(EquipmentSlot.MAINHAND)
         : super.getHeldItemForBone(boneName, entity);
   }

   protected void preRenderItem(PoseStack poseStack, ItemStack itemStack, String boneName, AbstractSpellCastingMob animatable, IBone bone) {
      if (this.isBoneMainHand(animatable, boneName) && animatable.isDrinkingPotion()) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
      }

      if (boneName.equals("torso") && this.shouldWeaponBeSheathed(animatable)) {
         float hipOffset = animatable.m_6844_(EquipmentSlot.CHEST).m_41619_() ? 0.2F : 0.275F;
         poseStack.m_85837_(animatable.m_21526_() ? (double)hipOffset : (double)(-hipOffset), -0.45, -0.225);
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-140.0F));
         poseStack.m_85841_(0.85F, 0.85F, 0.85F);
      }

      super.preRenderItem(poseStack, itemStack, boneName, animatable, bone);
   }

   private ItemStack makePotion(AbstractSpellCastingMob entity) {
      ItemStack healthPotion = new ItemStack(Items.f_42589_);
      return PotionUtils.m_43549_(healthPotion, entity.m_21222_() ? Potions.f_43582_ : Potions.f_43623_);
   }

   public void render(
      GeoModel model,
      AbstractSpellCastingMob animatable,
      float partialTick,
      RenderType type,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85836_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      SpellRenderingHelper.renderSpellHelper(ClientMagicData.getSyncedSpellData(animatable), animatable, poseStack, bufferSource, partialTick);
      poseStack.m_85849_();
   }
}
