package immersive_armors.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import immersive_armors.armorEffects.ArmorEffect;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ExtendedArmorItem extends ArmorItem {
   private static final UUID[] MODIFIERS = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   private Multimap<Attribute, AttributeModifier> attributeModifiers;
   private final ExtendedArmorMaterial material;

   public ExtendedArmorItem(Properties settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
      super(material, slot, settings);
      this.material = material;
      this.refreshAttributes();
   }

   public int m_40404_() {
      return this.material.m_7365_(this.f_40377_);
   }

   public float m_40405_() {
      return this.material.m_6651_();
   }

   public void refreshAttributes() {
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      UUID uUID = MODIFIERS[this.f_40377_.m_20749_()];
      builder.put(Attributes.f_22284_, new AttributeModifier(uUID, "Armor modifier", (double)this.m_40404_(), Operation.ADDITION));
      builder.put(Attributes.f_22285_, new AttributeModifier(uUID, "Armor toughness", (double)this.getMaterial().m_6651_(), Operation.ADDITION));
      builder.put(Attributes.f_22278_, new AttributeModifier(uUID, "Armor knockback resistance", (double)this.material.m_6649_(), Operation.ADDITION));
      builder.put(Attributes.f_22279_, new AttributeModifier(uUID, "Armor movement speed", (double)(-this.material.getWeight()), Operation.MULTIPLY_TOTAL));
      builder.put(Attributes.f_22276_, new AttributeModifier(uUID, "Armor health gain", (double)this.material.getExtraHealth(), Operation.ADDITION));
      builder.put(Attributes.f_22281_, new AttributeModifier(uUID, "Armor attack bonus", (double)this.material.getAttackDamage(), Operation.MULTIPLY_TOTAL));
      builder.put(Attributes.f_22283_, new AttributeModifier(uUID, "Armor attack damage", (double)this.material.getAttackSpeed(), Operation.MULTIPLY_TOTAL));
      builder.put(Attributes.f_22286_, new AttributeModifier(uUID, "Armor luck", (double)this.material.getLuck(), Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot slot) {
      return slot == this.f_40377_ ? this.attributeModifiers : super.m_7167_(slot);
   }

   public ExtendedArmorMaterial getMaterial() {
      return this.material;
   }

   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.m_7373_(stack, world, tooltip, context);

      for (ArmorEffect e : this.getMaterial().getEffects()) {
         e.appendTooltip(stack, world, tooltip, context);
      }

      for (Entry<Enchantment, Integer> entry : this.getMaterial().getEnchantments().entrySet()) {
         tooltip.add(entry.getKey().m_44700_(entry.getValue()));
      }
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      super.m_6883_(stack, world, entity, slot, selected);
      if (entity instanceof LivingEntity livingEntity) {
         ItemStack equippedStack = livingEntity.m_6844_(this.m_40402_());
         if (equippedStack == stack) {
            for (ArmorEffect e : this.getMaterial().getEffects()) {
               e.equippedTick(stack, world, livingEntity, slot);
            }
         }
      }
   }

   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      for (ArmorEffect e : this.getMaterial().getEffects()) {
         amount = e.applyArmorToDamage(entity, source, amount, armor);
      }

      return amount;
   }

   public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
      for (ArmorEffect e : this.getMaterial().getEffects()) {
         amount = e.applyArmorToAttack(target, source, amount, armor);
      }

      return amount;
   }
}
