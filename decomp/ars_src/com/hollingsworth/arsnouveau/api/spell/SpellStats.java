package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.event.SpellModifierEvent;
import com.hollingsworth.arsnouveau.api.item.ISpellModifierItem;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;

public class SpellStats {
   private boolean sensitive = false;
   private double amplification;
   private float acceleration;
   private double damageModifier;
   private double durationMultiplier;
   private double aoeMultiplier;
   private boolean randomizing;
   private List<AbstractAugment> augments = new ArrayList<>();
   private List<ItemStack> modifierItems = new ArrayList<>();

   private SpellStats() {
   }

   public int getDurationInTicks() {
      return (int)(20.0 * this.durationMultiplier);
   }

   public int getBuffCount(AbstractAugment abstractAugment) {
      return (int)this.augments.stream().filter(abstractAugment::equals).count();
   }

   public boolean hasBuff(AbstractAugment abstractAugment) {
      return this.getBuffCount(abstractAugment) > 0;
   }

   public List<Component> addTooltip(List<Component> components) {
      if (this.damageModifier != 0.0) {
         components.add(
            Component.m_237110_("tooltip.ars_nouveau.spell_damage", new Object[]{this.damageModifier}).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
         );
      }

      if (this.getDurationMultiplier() != 0.0) {
         components.add(
            Component.m_237110_("tooltip.ars_nouveau.duration_modifier", new Object[]{this.getDurationMultiplier()})
               .m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN))
         );
      }

      if (this.amplification != 0.0) {
         components.add(
            Component.m_237110_("tooltip.ars_nouveau.amp_modifier", new Object[]{this.amplification}).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.RED))
         );
      }

      return components;
   }

   public double getAmpMultiplier() {
      return this.amplification;
   }

   public double getAoeMultiplier() {
      return this.aoeMultiplier;
   }

   public void setAmpMultiplier(double amplification) {
      this.amplification = amplification;
   }

   public float getAccMultiplier() {
      return this.acceleration;
   }

   public void setAccMultiplier(float acceleration) {
      this.acceleration = acceleration;
   }

   public double getDamageModifier() {
      return this.damageModifier;
   }

   public void setDamageModifier(double damageModifier) {
      this.damageModifier = damageModifier;
   }

   public boolean isRandomized() {
      return this.randomizing;
   }

   public double getDurationMultiplier() {
      return this.durationMultiplier;
   }

   public void setDurationMultiplier(double durationMultiplier) {
      this.durationMultiplier = durationMultiplier;
   }

   public List<AbstractAugment> getAugments() {
      return this.augments;
   }

   public void setAugments(List<AbstractAugment> augments) {
      this.augments = augments;
   }

   public List<ItemStack> getModifierItems() {
      return this.modifierItems;
   }

   public void setModifierItems(List<ItemStack> modifierItems) {
      this.modifierItems = modifierItems;
   }

   public boolean isSensitive() {
      return this.sensitive;
   }

   public static class Builder {
      private SpellStats spellStats = new SpellStats();

      public SpellStats build(
         AbstractSpellPart spellPart, @Nullable HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellContext spellContext
      ) {
         for (AbstractAugment abstractAugment : this.spellStats.augments) {
            abstractAugment.applyModifiers(this, spellPart, rayTraceResult, world, shooter, spellContext);
         }

         for (ItemStack stack : this.spellStats.modifierItems) {
            if (stack.m_41720_() instanceof ISpellModifierItem) {
               for (int i = 0; i < stack.m_41613_(); i++) {
                  ((ISpellModifierItem)stack.m_41720_()).applyItemModifiers(stack, this, spellPart, rayTraceResult, world, shooter, spellContext);
               }
            }
         }

         SpellModifierEvent modifierEvent = new SpellModifierEvent(shooter, this, spellPart, rayTraceResult, world, spellContext);
         MinecraftForge.EVENT_BUS.post(modifierEvent);
         return this.spellStats;
      }

      public SpellStats build() {
         return this.spellStats;
      }

      public SpellStats.Builder setSensitive() {
         this.spellStats.sensitive = true;
         return this;
      }

      public SpellStats.Builder setDamageModifier(double damageModifier) {
         this.spellStats.damageModifier = damageModifier;
         return this;
      }

      public SpellStats.Builder addDamageModifier(double damageModifier) {
         this.spellStats.damageModifier += damageModifier;
         return this;
      }

      public SpellStats.Builder setAugments(List<AbstractAugment> augments) {
         this.spellStats.augments = augments;
         return this;
      }

      public SpellStats.Builder addItemsFromEntity(@Nullable LivingEntity entity) {
         if (entity == null) {
            return this;
         } else {
            CuriosUtil.getAllWornItems(entity).ifPresent(e -> {
               for (int ix = 0; ix < e.getSlots(); ix++) {
                  ItemStack item = e.getStackInSlot(ix);
                  this.spellStats.modifierItems.add(item);
               }
            });

            for (ItemStack i : entity.m_20158_()) {
               this.spellStats.modifierItems.add(i);
            }

            return this;
         }
      }

      public SpellStats.Builder addAugment(AbstractAugment abstractAugment) {
         this.spellStats.augments.add(abstractAugment);
         return this;
      }

      public SpellStats.Builder setAmplification(double power) {
         this.spellStats.amplification = power;
         return this;
      }

      public SpellStats.Builder addAmplification(double amplification) {
         this.spellStats.amplification += amplification;
         return this;
      }

      public SpellStats.Builder setAOE(double aoe) {
         this.spellStats.aoeMultiplier = aoe;
         return this;
      }

      public SpellStats.Builder addAOE(double aoe) {
         this.spellStats.aoeMultiplier += aoe;
         return this;
      }

      @Deprecated(
         forRemoval = true
      )
      public SpellStats.Builder setDurationModifier(double duration) {
         this.spellStats.durationMultiplier = duration;
         return this;
      }

      public SpellStats.Builder addDurationModifier(double duration) {
         this.spellStats.durationMultiplier += duration;
         return this;
      }

      public SpellStats.Builder setAccelerationModifier(float acceleration) {
         this.spellStats.acceleration = acceleration;
         return this;
      }

      public SpellStats.Builder addAccelerationModifier(float acceleration) {
         this.spellStats.acceleration += acceleration;
         return this;
      }

      public SpellStats.Builder setItems(List<ItemStack> items) {
         this.spellStats.modifierItems = items;
         return this;
      }

      public SpellStats.Builder addItem(ItemStack itemStack) {
         this.spellStats.modifierItems.add(itemStack);
         return this;
      }

      public SpellStats.Builder randomize() {
         this.spellStats.randomizing = true;
         return this;
      }
   }
}
