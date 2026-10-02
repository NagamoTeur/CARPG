package io.redspace.ironsspellbooks.item.armor;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class WizardArmorItem extends GeoArmorItem implements IAnimatable {
   private AnimationFactory factory = new AnimationFactory(this);
   private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP = new Builder().build();

   public WizardArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
      super(material, slot, settings);
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "controller", 20.0F, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
      return PlayState.CONTINUE;
   }

   public void onArmorTick(ItemStack stack, Level world, Player player) {
      if (!world.m_5776_() && this.hasFullSuitOfArmorOn(player)) {
         this.evaluateArmorEffects(player);
      }
   }

   private void evaluateArmorEffects(Player player) {
      for (Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
         ArmorMaterial mapArmorMaterial = entry.getKey();
         MobEffectInstance mapStatusEffect = entry.getValue();
         if (this.hasCorrectArmorOn(mapArmorMaterial, player)) {
            this.addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
         }
      }
   }

   private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
      boolean hasPlayerEffect = player.m_21023_(mapStatusEffect.m_19544_());
      if (this.hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
         player.m_7292_(new MobEffectInstance(mapStatusEffect.m_19544_(), mapStatusEffect.m_19557_(), mapStatusEffect.m_19564_()));
      }
   }

   private boolean hasFullSuitOfArmorOn(Player player) {
      ItemStack boots = player.m_150109_().m_36052_(0);
      ItemStack leggings = player.m_150109_().m_36052_(1);
      ItemStack breastplate = player.m_150109_().m_36052_(2);
      ItemStack helmet = player.m_150109_().m_36052_(3);
      return !helmet.m_41619_() && !breastplate.m_41619_() && !leggings.m_41619_() && !boots.m_41619_();
   }

   private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
      for (ItemStack armorStack : player.m_150109_().f_35975_) {
         if (!(armorStack.m_41720_() instanceof ArmorItem)) {
            return false;
         }
      }

      ArmorItem boots = (ArmorItem)player.m_150109_().m_36052_(0).m_41720_();
      ArmorItem leggings = (ArmorItem)player.m_150109_().m_36052_(1).m_41720_();
      ArmorItem breastplate = (ArmorItem)player.m_150109_().m_36052_(2).m_41720_();
      ArmorItem helmet = (ArmorItem)player.m_150109_().m_36052_(3).m_41720_();
      return helmet.m_40401_() == material && breastplate.m_40401_() == material && leggings.m_40401_() == material && boots.m_40401_() == material;
   }
}
