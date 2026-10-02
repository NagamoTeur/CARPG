package io.redspace.ironsspellbooks.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.attribute.IMagicAttribute;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.item.weapons.IMultihandWeapon;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
   @Unique
   private static final List<EquipmentSlot> handSlots = List.of(EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND);
   @Unique
   private static final Predicate<Attribute> allNonBaseAttackAttributes = attribute -> attribute != ForgeMod.ATTACK_RANGE.get()
         && attribute != Attributes.f_22281_
         && attribute != Attributes.f_22283_
         && attribute != Attributes.f_22282_;
   @Unique
   private static final Predicate<Attribute> onlyIronAttributes = attribute -> attribute instanceof IMagicAttribute;

   @Inject(
      method = {"updateInvisibilityStatus"},
      at = {@At("TAIL")}
   )
   public void updateInvisibilityStatus(CallbackInfo ci) {
      LivingEntity self = (LivingEntity)this;
      if (self.m_21023_((MobEffect)MobEffectRegistry.TRUE_INVISIBILITY.get())) {
         self.m_6842_(true);
      }
   }

   @Inject(
      method = {"getArmorCoverPercentage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getArmorCoverPercentage(CallbackInfoReturnable<Float> cir) {
      if (((LivingEntity)this).m_21023_((MobEffect)MobEffectRegistry.TRUE_INVISIBILITY.get())) {
         cir.setReturnValue(0.0F);
      }
   }

   @Inject(
      method = {"isCurrentlyGlowing"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isCurrentlyGlowing(CallbackInfoReturnable<Boolean> cir) {
      LivingEntity self = (LivingEntity)this;
      if (!self.f_19853_.m_5776_() && self.m_21023_((MobEffect)MobEffectRegistry.GUIDING_BOLT.get())) {
         cir.setReturnValue(true);
      }
   }

   @Shadow
   abstract ItemStack m_21244_(EquipmentSlot var1);

   @Inject(
      method = {"collectEquipmentChanges"},
      at = {@At("RETURN")}
   )
   public void handleEquipmentChanges(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir) {
      Map<EquipmentSlot, ItemStack> changedEquipment = (Map<EquipmentSlot, ItemStack>)cir.getReturnValue();
      if (changedEquipment != null) {
         LivingEntity self = (LivingEntity)this;

         for (EquipmentSlot slot : handSlots) {
            ItemStack currentStack = changedEquipment.get(slot);
            if (currentStack != null) {
               ItemStack oldStack = this.m_21244_(slot);
               boolean selected = currentStack.m_41720_() instanceof IMultihandWeapon;
               boolean deselected = oldStack.m_41720_() instanceof IMultihandWeapon;
               if (selected || deselected) {
                  if (slot == EquipmentSlot.MAINHAND) {
                     ItemStack offhandStack = self.m_21206_();
                     if (offhandStack.m_41720_() instanceof IMultihandWeapon && !ItemStack.m_41746_(offhandStack, currentStack)) {
                        if (selected) {
                           self.m_21204_().m_22161_(filterApplicableAttributes(offhandStack.m_41638_(EquipmentSlot.MAINHAND)));
                        }

                        if (deselected) {
                           self.m_21204_().m_22178_(filterApplicableAttributes(offhandStack.m_41638_(EquipmentSlot.MAINHAND)));
                        }
                     }
                  } else if (slot == EquipmentSlot.OFFHAND) {
                     ItemStack mainhandStack = self.m_21205_();
                     if (selected && !(mainhandStack.m_41720_() instanceof IMultihandWeapon)) {
                        self.m_21204_().m_22178_(filterApplicableAttributes(currentStack.m_41638_(EquipmentSlot.MAINHAND)));
                     }

                     if (deselected && !ItemStack.m_41746_(mainhandStack, oldStack)) {
                        self.m_21204_().m_22161_(filterApplicableAttributes(oldStack.m_41638_(EquipmentSlot.MAINHAND)));
                     }
                  }
               }
            }
         }
      }
   }

   @Unique
   private static Multimap<Attribute, AttributeModifier> filterApplicableAttributes(Multimap<Attribute, AttributeModifier> attributeModifierMap) {
      Multimap<Attribute, AttributeModifier> map = HashMultimap.create();

      for (Attribute attribute : attributeModifierMap.keySet()) {
         Predicate<Attribute> predicate = ServerConfigs.APPLY_ALL_MULTIHAND_ATTRIBUTES.get() ? allNonBaseAttackAttributes : onlyIronAttributes;
         if (predicate.test(attribute)) {
            map.putAll(attribute, attributeModifierMap.get(attribute));
         }
      }

      return map;
   }
}
