package com.hollingsworth.arsnouveau.api.potion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class PotionData implements Cloneable {
   private Potion potion = Potions.f_43598_;
   private List<MobEffectInstance> customEffects = new ArrayList<>();
   private Set<Potion> includedPotions = new HashSet<>();

   public PotionData(Potion potion, List<MobEffectInstance> customEffects, Set<Potion> includedPotions) {
      this.potion = potion;
      this.includedPotions = includedPotions;
      this.setCustomEffects(customEffects);
   }

   public PotionData() {
      this(Potions.f_43598_, new ArrayList<>(), new HashSet<>());
   }

   public PotionData(ItemStack stack) {
      if (stack.m_41720_() instanceof IPotionProvider provider) {
         PotionData data = provider.getPotionData(stack).clone();
         this.potion = data.getPotion();
         this.customEffects = data.getCustomEffects();
      } else {
         this.potion = PotionUtils.m_43579_(stack);
         this.customEffects = new ArrayList<>();
         this.setCustomEffects(PotionUtils.m_43571_(stack));
      }
   }

   public PotionData(Potion potion) {
      this(potion, new ArrayList<>(), new HashSet<>(Collections.singletonList(potion)));
   }

   public PotionData(PotionData data) {
      this(data.getPotion(), new ArrayList<>(data.getCustomEffects()), new HashSet<>(data.getIncludedPotions()));
   }

   public ItemStack asPotionStack() {
      return this.asPotionStack(Items.f_42589_);
   }

   public ItemStack asPotionStack(Item item) {
      ItemStack potionStack = new ItemStack(item);
      if (this.getPotion() == Potions.f_43598_) {
         return potionStack;
      } else {
         PotionUtils.m_43549_(potionStack, this.getPotion());
         PotionUtils.m_43552_(potionStack, this.getCustomEffects());
         return potionStack;
      }
   }

   public static PotionData fromTag(CompoundTag tag) {
      PotionData instance = new PotionData();
      instance.setPotion(PotionUtils.m_43577_(tag));
      instance.getCustomEffects().addAll(PotionUtils.m_43573_(tag));
      ListTag potionTagList = tag.m_128437_("includedPotions", 8);
      Set<Potion> potions = instance.includedPotions;

      for (int i = 0; i < potionTagList.size(); i++) {
         potions.add(Potion.m_43489_(potionTagList.m_128778_(i)));
      }

      return instance;
   }

   public CompoundTag toTag() {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("Potion", Registry.f_122828_.m_7981_(this.getPotion()).toString());
      if (!this.getCustomEffects().isEmpty()) {
         ListTag listnbt = new ListTag();

         for (MobEffectInstance effectinstance : this.getCustomEffects()) {
            listnbt.add(effectinstance.m_19555_(new CompoundTag()));
         }

         tag.m_128365_("CustomPotionEffects", listnbt);
      }

      ListTag potionTagList = new ListTag();

      for (String potion : new ArrayList<>(this.getIncludedPotions().stream().map(potionx -> Registry.f_122828_.m_7981_(potionx).toString()).toList())) {
         potionTagList.add(StringTag.m_129297_(potion));
      }

      tag.m_128365_("includedPotions", potionTagList);
      return tag;
   }

   public List<MobEffectInstance> fullEffects() {
      List<MobEffectInstance> thisEffects = new ArrayList<>(this.getCustomEffects());
      thisEffects.addAll(this.getPotion().m_43488_());
      return thisEffects;
   }

   public void applyEffects(Entity source, Entity inDirectSource, LivingEntity target) {
      for (MobEffectInstance effectinstance : this.fullEffects()) {
         if (effectinstance.m_19544_().m_8093_()) {
            effectinstance.m_19544_().m_19461_(source, inDirectSource, target, effectinstance.m_19564_(), 1.0);
         } else {
            target.m_147207_(new MobEffectInstance(effectinstance), source);
         }
      }
   }

   public boolean areSameEffects(List<MobEffectInstance> effects) {
      List<MobEffectInstance> thisEffects = this.fullEffects();
      if (thisEffects.size() != effects.size()) {
         return false;
      } else {
         effects.sort(Comparator.comparing(MobEffectInstance::toString));
         thisEffects.sort(Comparator.comparing(MobEffectInstance::toString));
         return thisEffects.equals(effects);
      }
   }

   public boolean isEmpty() {
      return this.getPotion() == Potions.f_43598_
         || this.getPotion() == Potions.f_43599_
         || this.getPotion() == Potions.f_43600_
         || this.fullEffects().isEmpty();
   }

   public boolean areSameEffects(PotionData other) {
      return this.areSameEffects(other.fullEffects());
   }

   public PotionData mergeEffects(PotionData other) {
      if (this.areSameEffects(other)) {
         return new PotionData(this.getPotion(), this.getCustomEffects(), this.getIncludedPotions());
      } else {
         Set<MobEffectInstance> set = new HashSet<>();
         set.addAll(this.fullEffects());
         set.addAll(other.fullEffects());
         Set<Potion> potions = new HashSet<>();
         potions.addAll(this.getIncludedPotions());
         potions.addAll(other.getIncludedPotions());
         return new PotionData(this.getPotion(), new ArrayList<>(set), potions);
      }
   }

   public void appendHoverText(List<Component> tooltip) {
      if (this.getPotion() != Potions.f_43598_) {
         ItemStack potionStack = this.asPotionStack();
         tooltip.add(potionStack.m_41786_());
         PotionUtils.m_43555_(potionStack, tooltip, 1.0F);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof PotionData other && this.areSameEffects(other)) {
         return true;
      }

      return false;
   }

   public PotionData clone() {
      try {
         PotionData clone = (PotionData)super.clone();
         clone.setPotion(this.getPotion());
         clone.setCustomEffects(new ArrayList<>(this.getCustomEffects()));
         clone.setIncludedPotions(new HashSet<>(this.getIncludedPotions()));
         return clone;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   public Potion getPotion() {
      return this.potion;
   }

   public void setPotion(Potion potion) {
      this.potion = potion;
   }

   public List<MobEffectInstance> getCustomEffects() {
      return this.customEffects;
   }

   public void setCustomEffects(List<MobEffectInstance> customEffects) {
      this.customEffects = customEffects.stream().filter(e -> !this.potion.m_43488_().contains(e)).collect(Collectors.toList());
   }

   public Set<Potion> getIncludedPotions() {
      this.includedPotions.add(this.getPotion());
      this.includedPotions.removeIf(potion -> potion == Potions.f_43598_);
      return this.includedPotions;
   }

   public void setIncludedPotions(Set<Potion> includedPotions) {
      this.includedPotions = includedPotions;
   }
}
