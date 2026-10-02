package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.entity.AttributeHandler;
import com.majruszlibrary.events.OnAnimalTamed;
import com.majruszlibrary.events.OnBabySpawned;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.horse.Horse;

public class TamingStrongerAnimals extends BonusComponent<AccessoryItem> {
   final AttributeHandler health;
   final AttributeHandler damage;
   final AttributeHandler speed;
   final AttributeHandler jumpHeight;
   RangedFloat bonus = new RangedFloat().id("bonus").maxRange(Range.of(0.0F, 10.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float bonus) {
      return handler -> new TamingStrongerAnimals(handler, bonus);
   }

   protected TamingStrongerAnimals(BonusHandler<AccessoryItem> handler, float bonus) {
      super(handler);
      this.health = new AttributeHandler("%s_health_multiplier".formatted(handler.getId()), () -> Attributes.f_22276_, Operation.MULTIPLY_BASE);
      this.damage = new AttributeHandler("%s_damage_multiplier".formatted(handler.getId()), () -> Attributes.f_22281_, Operation.MULTIPLY_BASE);
      this.speed = new AttributeHandler("%s_speed_multiplier".formatted(handler.getId()), () -> Attributes.f_22279_, Operation.MULTIPLY_BASE);
      this.jumpHeight = new AttributeHandler("%s_jump_height_multiplier".formatted(handler.getId()), () -> Attributes.f_22288_, Operation.MULTIPLY_BASE);
      this.bonus.set(bonus, Range.of(0.0F, 1.0F));
      OnAnimalTamed.listen(this::applyBonuses).addCondition(Condition.isLogicalServer());
      OnBabySpawned.listen(this::applyBonuses)
         .addCondition(Condition.isLogicalServer())
         .addCondition(data -> this.hasModifier(data.parentA) || this.hasModifier(data.parentB));
      this.addTooltip("majruszsaccessories.bonuses.animal_attributes", new ITooltipProvider[]{TooltipHelper.asPercent(this.bonus)});
      handler.getConfig().define("animal_bonus", this.bonus::define);
   }

   private void applyBonuses(OnAnimalTamed data) {
      AccessoryHolder holder = AccessoryHolders.get(data.tamer).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         this.applyBonuses(holder.apply(this.bonus), data.animal);
         this.spawnEffects(data, holder);
      }
   }

   private void applyBonuses(OnBabySpawned data) {
      this.applyBonuses((float)Math.max(this.getModifierValue(data.parentA), this.getModifierValue(data.parentB)), data.child);
   }

   private void applyBonuses(float bonus, LivingEntity entity) {
      this.health.setValue((double)bonus).apply(entity);
      if (this.damage.hasAttribute(entity)) {
         this.damage.setValue((double)bonus).apply(entity);
      }

      if (entity instanceof Horse horse) {
         this.jumpHeight.setValue((double)bonus).apply(horse);
         this.speed.setValue((double)bonus).apply(horse);
      }

      entity.m_21153_(entity.m_21233_());
   }

   private void spawnEffects(OnAnimalTamed data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(4).sizeBased(data.animal).emit(data.getServerLevel());
   }

   private double getModifierValue(LivingEntity entity) {
      return entity.m_21204_().m_22173_(Attributes.f_22276_, this.health.getUUID());
   }

   private boolean hasModifier(LivingEntity entity) {
      return entity.m_21204_().m_22154_(Attributes.f_22276_, this.health.getUUID());
   }
}
