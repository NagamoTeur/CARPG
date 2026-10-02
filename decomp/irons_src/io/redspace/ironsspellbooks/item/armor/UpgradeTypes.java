package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;

public enum UpgradeTypes implements UpgradeType {
   FIRE_SPELL_POWER("fire_power", ItemRegistry.FIRE_UPGRADE_ORB, (Attribute)AttributeRegistry.FIRE_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   ICE_SPELL_POWER("ice_power", ItemRegistry.ICE_UPGRADE_ORB, (Attribute)AttributeRegistry.ICE_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   LIGHTNING_SPELL_POWER(
      "lightning_power", ItemRegistry.LIGHTNING_UPGRADE_ORB, (Attribute)AttributeRegistry.LIGHTNING_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F
   ),
   HOLY_SPELL_POWER("holy_power", ItemRegistry.HOLY_UPGRADE_ORB, (Attribute)AttributeRegistry.HOLY_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   ENDER_SPELL_POWER("ender_power", ItemRegistry.ENDER_UPGRADE_ORB, (Attribute)AttributeRegistry.ENDER_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   BLOOD_SPELL_POWER("blood_power", ItemRegistry.BLOOD_UPGRADE_ORB, (Attribute)AttributeRegistry.BLOOD_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   EVOCATION_SPELL_POWER(
      "evocation_power", ItemRegistry.EVOCATION_UPGRADE_ORB, (Attribute)AttributeRegistry.EVOCATION_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F
   ),
   NATURE_SPELL_POWER("nature_power", ItemRegistry.NATURE_UPGRADE_ORB, (Attribute)AttributeRegistry.NATURE_SPELL_POWER.get(), Operation.MULTIPLY_BASE, 0.05F),
   COOLDOWN("cooldown", ItemRegistry.COOLDOWN_UPGRADE_ORB, (Attribute)AttributeRegistry.COOLDOWN_REDUCTION.get(), Operation.MULTIPLY_BASE, 0.05F),
   SPELL_RESISTANCE("spell_resistance", ItemRegistry.PROTECTION_UPGRADE_ORB, (Attribute)AttributeRegistry.SPELL_RESIST.get(), Operation.MULTIPLY_BASE, 0.05F),
   MANA("mana", ItemRegistry.MANA_UPGRADE_ORB, (Attribute)AttributeRegistry.MAX_MANA.get(), Operation.ADDITION, 50.0F),
   ATTACK_DAMAGE("melee_damage", Optional.empty(), Attributes.f_22281_, Operation.MULTIPLY_BASE, 0.05F),
   ATTACK_SPEED("melee_speed", Optional.empty(), Attributes.f_22283_, Operation.MULTIPLY_BASE, 0.05F),
   HEALTH("health", Optional.empty(), Attributes.f_22276_, Operation.ADDITION, 2.0F);

   final Attribute attribute;
   final Operation operation;
   final float amountPerUpgrade;
   final ResourceLocation id;
   final Optional<Supplier<Item>> containerItem;

   private UpgradeTypes(String key, Supplier<Item> containerItem, Attribute attribute, Operation operation, float amountPerUpgrade) {
      this(key, Optional.of(containerItem), attribute, operation, amountPerUpgrade);
   }

   private UpgradeTypes(String key, Optional<Supplier<Item>> containerItem, Attribute attribute, Operation operation, float amountPerUpgrade) {
      this.id = IronsSpellbooks.id(key);
      this.attribute = attribute;
      this.operation = operation;
      this.amountPerUpgrade = amountPerUpgrade;
      this.containerItem = containerItem;
      UpgradeType.registerUpgrade(this);
   }

   @Override
   public Attribute getAttribute() {
      return this.attribute;
   }

   @Override
   public Operation getOperation() {
      return this.operation;
   }

   @Override
   public float getAmountPerUpgrade() {
      return this.amountPerUpgrade;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public Optional<Supplier<Item>> getContainerItem() {
      return this.containerItem;
   }
}
