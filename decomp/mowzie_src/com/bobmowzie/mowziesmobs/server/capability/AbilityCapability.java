package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class AbilityCapability {
   public static ResourceLocation ID = new ResourceLocation("mowziesmobs", "ability_cap");

   public static class AbilityCapabilityImp implements AbilityCapability.IAbilityCapability {
      SortedMap<AbilityType<?, ?>, Ability> abilityInstances = new TreeMap<>();
      Ability activeAbility = null;
      Map<String, Tag> nbtMap = new HashMap<>();

      @Override
      public void instanceAbilities(LivingEntity entity) {
         this.setActiveAbility(null);

         for (AbilityType<? extends LivingEntity, ?> abilityType : this.getAbilityTypesOnEntity(entity)) {
            Ability ability = abilityType.makeInstance(entity);
            this.abilityInstances.put(abilityType, ability);
            if (this.nbtMap.containsKey(abilityType.getName())) {
               ability.readNBT(this.nbtMap.get(abilityType.getName()));
            }
         }
      }

      @Override
      public void activateAbility(LivingEntity entity, AbilityType<?, ?> abilityType) {
         Ability ability = this.abilityInstances.get(abilityType);
         if (ability != null) {
            boolean tryResult = ability.tryAbility();
            if (tryResult) {
               ability.start();
            }
         } else {
            System.out.println("Ability " + abilityType.toString() + " does not exist on mob " + entity.getClass().getSimpleName());
         }
      }

      @Override
      public void tick(LivingEntity entity) {
         for (Ability ability : this.abilityInstances.values()) {
            ability.tick();
         }
      }

      @Override
      public AbilityType<?, ?>[] getAbilityTypesOnEntity(LivingEntity entity) {
         if (entity instanceof Player) {
            return AbilityHandler.PLAYER_ABILITIES;
         } else {
            return entity instanceof MowzieGeckoEntity ? ((MowzieGeckoEntity)entity).getAbilities() : new AbilityType[0];
         }
      }

      @Override
      public Map<AbilityType<?, ?>, Ability> getAbilityMap() {
         return this.abilityInstances;
      }

      @Override
      public Ability getActiveAbility() {
         return this.activeAbility;
      }

      @Override
      public void setActiveAbility(Ability activeAbility) {
         if (this.getActiveAbility() != null && this.getActiveAbility().isUsing()) {
            this.getActiveAbility().interrupt();
         }

         this.activeAbility = activeAbility;
      }

      @Override
      public Collection<Ability> getAbilities() {
         return this.abilityInstances.values();
      }

      @Override
      public boolean attackingPrevented() {
         return this.getActiveAbility() != null && this.getActiveAbility().preventsAttacking();
      }

      @Override
      public boolean blockBreakingBuildingPrevented() {
         return this.getActiveAbility() != null && this.getActiveAbility().preventsBlockBreakingBuilding();
      }

      @Override
      public boolean interactingPrevented() {
         return this.getActiveAbility() != null && this.getActiveAbility().preventsInteracting();
      }

      @Override
      public boolean itemUsePrevented(ItemStack itemStack) {
         return this.getActiveAbility() != null && this.getActiveAbility().preventsItemUse(itemStack);
      }

      @Override
      public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
         return this.getActiveAbility().animationPredicate(e, perspective);
      }

      @Override
      public void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> model, float partialTick) {
         this.getActiveAbility().codeAnimations(model, partialTick);
      }

      public CompoundTag serializeNBT() {
         CompoundTag compound = new CompoundTag();

         for (Entry<AbilityType<?, ?>, Ability> abilityEntry : this.getAbilityMap().entrySet()) {
            CompoundTag nbt = abilityEntry.getValue().writeNBT();
            if (!nbt.m_128456_()) {
               compound.m_128365_(abilityEntry.getKey().getName(), nbt);
            }
         }

         return compound;
      }

      public void deserializeNBT(CompoundTag nbt) {
         CompoundTag compound = nbt;

         for (String abilityName : nbt.m_128431_()) {
            this.nbtMap.put(abilityName, compound.m_128423_(abilityName));
         }
      }
   }

   public static class AbilityProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
      private final LazyOptional<AbilityCapability.IAbilityCapability> instance = LazyOptional.of(AbilityCapability.AbilityCapabilityImp::new);

      public CompoundTag serializeNBT() {
         return (CompoundTag)((AbilityCapability.IAbilityCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
      }

      public void deserializeNBT(CompoundTag nbt) {
         ((AbilityCapability.IAbilityCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
      }

      @Nonnull
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
         return CapabilityHandler.ABILITY_CAPABILITY.orEmpty(cap, this.instance.cast());
      }
   }

   public interface IAbilityCapability extends INBTSerializable<CompoundTag> {
      void activateAbility(LivingEntity var1, AbilityType<?, ?> var2);

      void instanceAbilities(LivingEntity var1);

      void tick(LivingEntity var1);

      AbilityType<?, ?>[] getAbilityTypesOnEntity(LivingEntity var1);

      Map<AbilityType<?, ?>, Ability> getAbilityMap();

      Collection<Ability> getAbilities();

      Ability getActiveAbility();

      void setActiveAbility(Ability var1);

      boolean attackingPrevented();

      boolean blockBreakingBuildingPrevented();

      boolean interactingPrevented();

      boolean itemUsePrevented(ItemStack var1);

      <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> var1, GeckoPlayer.Perspective var2);

      void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> var1, float var2);
   }
}
