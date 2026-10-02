package io.redspace.ironsspellbooks.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
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
import software.bernie.geckolib3.util.GeckoLibUtil;

public abstract class ExtendedArmorItem extends GeoArmorItem implements IAnimatable {
   private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   private final Multimap<Attribute, AttributeModifier> ARMOR_ATTRIBUTES;
   private AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP = new com.google.common.collect.ImmutableMap.Builder().build();

   public ExtendedArmorItem(IronsExtendedArmorMaterial material, EquipmentSlot slot, Properties settings) {
      super(material, slot, settings);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      float defense = (float)material.m_7365_(slot);
      float toughness = material.m_6651_();
      float knockbackResistance = material.m_6649_();
      UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.m_20749_()];
      builder.put(Attributes.f_22284_, new AttributeModifier(uuid, "Armor modifier", (double)defense, Operation.ADDITION));
      builder.put(Attributes.f_22285_, new AttributeModifier(uuid, "Armor toughness", (double)toughness, Operation.ADDITION));
      if (knockbackResistance > 0.0F) {
         builder.put(Attributes.f_22278_, new AttributeModifier(uuid, "Armor knockback resistance", (double)knockbackResistance, Operation.ADDITION));
      }

      for (Entry<Attribute, AttributeModifier> modifierEntry : material.getAdditionalAttributes().entrySet()) {
         AttributeModifier atr = modifierEntry.getValue();
         atr = new AttributeModifier(uuid, atr.m_22214_(), atr.m_22218_(), atr.m_22217_());
         builder.put(modifierEntry.getKey(), atr);
      }

      this.ARMOR_ATTRIBUTES = builder.build();
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot pEquipmentSlot) {
      return (Multimap<Attribute, AttributeModifier>)(pEquipmentSlot == this.f_40377_ ? this.ARMOR_ATTRIBUTES : ImmutableMultimap.of());
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
