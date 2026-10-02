package com.majruszsaccessories.common;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.events.base.Events;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.registry.Registries;
import com.majruszsaccessories.config.Config;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.config.RangedInteger;
import com.majruszsaccessories.events.OnAccessoryExtraBonusGet;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.items.BoosterItem;
import com.majruszsaccessories.mixininterfaces.IMixinItemStack;
import com.majruszsaccessories.particles.BonusParticleType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class AccessoryHolder {
   public static final AccessoryHolder EMPTY = new AccessoryHolder(ItemStack.f_41583_);
   final ItemStack itemStack;
   final AccessoryItem item;
   final AccessoryHolder.Data data;
   boolean isBonusDisabled = false;

   public static AccessoryHolder create(Item item) {
      return new AccessoryHolder(new ItemStack(item));
   }

   public static AccessoryHolder create(ItemStack itemStack) {
      return new AccessoryHolder(itemStack);
   }

   public static AccessoryHolder getOrCreate(ItemStack itemStack) {
      return ((IMixinItemStack)itemStack).majruszsaccessories$getOrCreateAccessoryHolder();
   }

   public static int apply(float bonus, RangedInteger value, int multiplier) {
      return (Integer)value.getRange().clamp(Math.round((1.0F + (float)multiplier * bonus) * (float)value.get()));
   }

   public static float apply(float bonus, RangedFloat value, float multiplier) {
      return (Float)value.getRange().clamp((1.0F + multiplier * bonus) * value.get());
   }

   public static ChatFormatting getBonusFormatting(float bonus) {
      if (bonus > (Float)Config.Efficiency.RANGE.to) {
         return ChatFormatting.DARK_AQUA;
      } else if (bonus == (Float)Config.Efficiency.RANGE.to) {
         return ChatFormatting.GOLD;
      } else if (bonus > 0.0F) {
         return ChatFormatting.GREEN;
      } else {
         return bonus == 0.0F ? ChatFormatting.GRAY : ChatFormatting.RED;
      }
   }

   public static Rarity getRarity(float bonus) {
      if (bonus >= (Float)Config.Efficiency.RANGE.to) {
         return Rarity.EPIC;
      } else {
         return bonus >= 0.0F ? Rarity.RARE : Rarity.UNCOMMON;
      }
   }

   public static ParticleEmitter getParticleEmitter(float bonus) {
      return ParticleEmitter.of(new BonusParticleType.Options(getRarity(bonus).f_43022_.m_126665_()));
   }

   private AccessoryHolder(ItemStack itemStack, AccessoryHolder.Data data) {
      this.itemStack = itemStack;
      this.item = itemStack.m_41720_() instanceof AccessoryItem item ? item : null;
      this.data = data;
      this.data.extraBonus = round(((OnAccessoryExtraBonusGet)Events.dispatch(new OnAccessoryExtraBonusGet(this))).bonus);
      ((IMixinItemStack)itemStack).majruszsaccessories$setAccessoryHolder(this);
   }

   private AccessoryHolder(ItemStack itemStack) {
      this(
         itemStack,
         itemStack.m_41783_() != null ? (AccessoryHolder.Data)Serializables.read(new AccessoryHolder.Data(), itemStack.m_41783_()) : new AccessoryHolder.Data()
      );
   }

   public AccessoryHolder copy() {
      return new AccessoryHolder(new ItemStack(this.item), new AccessoryHolder.Data(this.data));
   }

   public int apply(RangedInteger value, int multiplier) {
      return apply(this.getBonus(), value, multiplier);
   }

   public int apply(RangedInteger value) {
      return this.apply(value, 1);
   }

   public float apply(RangedFloat value, float multiplier) {
      return apply(this.getBonus(), value, multiplier);
   }

   public float apply(RangedFloat value) {
      return this.apply(value, 1.0F);
   }

   public AccessoryHolder setRandomBonus() {
      if (this.hasBonusRangeDefined()) {
         float bonus = Optional.ofNullable(this.data.randomType).orElse(AccessoryHolder.RandomType.RANDOM).get(this.data.range);
         return this.setBonus((Float)Config.Efficiency.RANGE.clamp(bonus));
      } else {
         return this.setBonus(Config.Efficiency.getRandom());
      }
   }

   public AccessoryHolder setBonus(float bonus) {
      return this.save(() -> {
         this.data.baseBonus = round(bonus);
         this.data.extraBonus = round(((OnAccessoryExtraBonusGet)Events.dispatch(new OnAccessoryExtraBonusGet(this))).bonus);
      });
   }

   public AccessoryHolder setBonus(Range<Float> bonus, AccessoryHolder.RandomType randomType) {
      return (Float)bonus.to - (Float)bonus.from > 1.0E-5F ? this.save(() -> {
         this.data.baseBonus = null;
         this.data.extraBonus = null;
         this.data.randomType = randomType;
         this.data.range = Range.of(round((Float)bonus.from), round((Float)bonus.to));
      }) : this.setBonus((Float)bonus.from);
   }

   public AccessoryHolder setBonus(Range<Float> bonus) {
      return this.setBonus(bonus, null);
   }

   public AccessoryHolder addBooster(BoosterItem item) {
      return this.save(() -> this.data.boosters.add(new AccessoryHolder.BoosterDef(item)));
   }

   public AccessoryHolder removeBonus() {
      return this.save(() -> {
         this.data.baseBonus = null;
         this.data.extraBonus = null;
      });
   }

   public AccessoryHolder removeBoosters() {
      return this.save(() -> this.data.boosters.clear());
   }

   public AccessoryHolder disableBonus() {
      this.isBonusDisabled = true;
      return this;
   }

   public float getBonus() {
      return round(this.getBaseBonus() + this.getExtraBonus());
   }

   public float getBaseBonus() {
      return this.data.baseBonus != null ? this.data.baseBonus : 0.0F;
   }

   public float getExtraBonus() {
      return this.data.extraBonus;
   }

   public Range<Float> getBonusRange() {
      return this.data.range;
   }

   public Range<Float> getClampedBonusRange() {
      return Config.Efficiency.RANGE.clamp(this.data.range);
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public AccessoryItem getItem() {
      return this.item;
   }

   public List<BoosterItem> getBoosters() {
      return this.data.boosters.stream().map(booster -> booster.item).toList();
   }

   public int getBoosterSlotsLeft() {
      return Math.max(this.item.getBoosterSlotsCount() - this.data.boosters.size(), 0);
   }

   public int getTier() {
      return this.item.getTier();
   }

   public ChatFormatting getBonusFormatting() {
      return getBonusFormatting(this.getBonus());
   }

   public Rarity getRarity() {
      return getRarity(this.getBonus());
   }

   public ParticleEmitter getParticleEmitter() {
      return getParticleEmitter(this.getBonus());
   }

   public boolean is(AccessoryItem item) {
      return this.item == item;
   }

   public boolean isValid() {
      return this.item != null;
   }

   public boolean isBonusDisabled() {
      return this.isBonusDisabled;
   }

   public boolean hasBonusDefined() {
      return this.data.baseBonus != null;
   }

   public boolean hasBonusRangeDefined() {
      return this.data.range != null;
   }

   public boolean hasMaxBonus() {
      return this.getBaseBonus() == (Float)Config.Efficiency.RANGE.to;
   }

   public boolean has(BoosterItem item) {
      return this.data.boosters.stream().anyMatch(booster -> booster.item == item);
   }

   public boolean hasAnyBooster() {
      return !this.data.boosters.isEmpty();
   }

   private AccessoryHolder save(Runnable runnable) {
      runnable.run();
      Serializables.write(this.data, this.itemStack.m_41784_());
      return this;
   }

   private static float round(float value) {
      return (float)Math.round(100.0F * value) / 100.0F;
   }

   private static class BoosterDef {
      BoosterItem item = null;
      ResourceLocation id = null;

      public BoosterDef(BoosterItem item) {
         this.item = item;
         this.id = Registries.ITEMS.getId(item);
      }

      public BoosterDef() {
      }

      static {
         Serializables.get(AccessoryHolder.BoosterDef.class).define("Id", Reader.location(), s -> s.id, (s, v) -> {
            s.item = Registries.ITEMS.get(v) instanceof BoosterItem booster ? booster : null;
            s.id = v;
         });
      }
   }

   private static class Data {
      Float baseBonus = null;
      Float extraBonus = 0.0F;
      Range<Float> range = null;
      AccessoryHolder.RandomType randomType = null;
      List<AccessoryHolder.BoosterDef> boosters = List.of();

      public Data(AccessoryHolder.Data data) {
         this.baseBonus = data.baseBonus;
         this.range = data.range != null ? Range.of((Float)data.range.from, (Float)data.range.to) : null;
         this.randomType = data.randomType;
         this.boosters = new ArrayList<>(data.boosters);
      }

      public Data() {
      }

      static {
         Serializables.get(AccessoryHolder.Data.class)
            .define(
               "Bonus",
               subconfig -> {
                  subconfig.define("Value", Reader.optional(Reader.number()), s -> s.baseBonus, (s, v) -> s.baseBonus = v);
                  subconfig.define("ValueRange", Reader.optional(Reader.range(Reader.number())), s -> s.range, (s, v) -> s.range = v);
                  subconfig.define(
                     "ValueRandomType", Reader.optional(Reader.enumeration(AccessoryHolder.RandomType::values)), s -> s.randomType, (s, v) -> s.randomType = v
                  );
                  subconfig.define("Boosters", Reader.list(Reader.custom(AccessoryHolder.BoosterDef::new)), s -> s.boosters, (s, v) -> s.boosters = v);
               }
            );
      }
   }

   public static enum RandomType {
      RANDOM(() -> Random.nextFloat(0.0F, 1.0F)),
      NORMAL_DISTRIBUTION(() -> Config.Efficiency.getGaussianRatio());

      private final Supplier<Float> ratio;

      private RandomType(Supplier<Float> ratio) {
         this.ratio = ratio;
      }

      float get(Range<Float> range) {
         return range.lerp(this.ratio.get());
      }
   }
}
