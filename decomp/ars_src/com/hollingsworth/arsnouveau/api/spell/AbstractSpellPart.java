package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.util.SpellPartConfigUtil;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSpellPart implements Comparable<AbstractSpellPart> {
   private final ResourceLocation registryName;
   public String name;
   public Glyph glyphItem;
   public List<SpellSchool> spellSchools = new CopyOnWriteArrayList<>();
   public Set<AbstractAugment> compatibleAugments = ConcurrentHashMap.newKeySet();
   public SpellPartConfigUtil.ComboLimits invalidCombinations = new SpellPartConfigUtil.ComboLimits(null);
   @Nullable
   public ForgeConfigSpec CONFIG;
   @Nullable
   public IntValue COST;
   @Nullable
   public BooleanValue ENABLED;
   @Nullable
   public BooleanValue STARTER_SPELL;
   @Nullable
   public IntValue PER_SPELL_LIMIT;
   @Nullable
   public IntValue GLYPH_TIER;
   public SpellPartConfigUtil.AugmentLimits augmentLimits;

   public abstract Integer getTypeIndex();

   public ResourceLocation getRegistryName() {
      return this.registryName;
   }

   public AbstractSpellPart(String registryName, String name) {
      this(new ResourceLocation("ars_nouveau", registryName), name);
   }

   public AbstractSpellPart(ResourceLocation registryName, String name) {
      this.registryName = registryName;
      this.name = name;

      for (SpellSchool spellSchool : this.getSchools()) {
         spellSchool.addSpellPart(this);
         this.spellSchools.add(spellSchool);
      }

      this.compatibleAugments.addAll(this.getCompatibleAugments());
   }

   public abstract int getDefaultManaCost();

   public int getCastingCost() {
      return this.COST == null ? this.getDefaultManaCost() : (Integer)this.COST.get();
   }

   public String getName() {
      return this.name;
   }

   public SpellTier getConfigTier() {
      return this.GLYPH_TIER == null ? this.defaultTier() : SpellTier.SPELL_TIER_MAP.get(this.GLYPH_TIER.get());
   }

   @Deprecated(
      forRemoval = true
   )
   public SpellTier getTier() {
      return SpellTier.ONE;
   }

   public SpellTier defaultTier() {
      return this.getTier();
   }

   public Glyph getGlyph() {
      if (this.glyphItem == null) {
         this.glyphItem = new Glyph(this);
      }

      return this.glyphItem;
   }

   @NotNull
   protected abstract Set<AbstractAugment> getCompatibleAugments();

   protected Set<AbstractAugment> augmentSetOf(AbstractAugment... augments) {
      return this.setOf(augments);
   }

   @NotNull
   protected Set<SpellSchool> getSchools() {
      return this.setOf();
   }

   protected <T> Set<T> setOf(T... list) {
      return Set.of(list);
   }

   public int compareTo(AbstractSpellPart o) {
      return this.getConfigTier().value - o.getConfigTier().value;
   }

   public Component getBookDescLang() {
      return Component.m_237115_(this.getRegistryName().m_135827_() + ".glyph_desc." + this.getRegistryName().m_135815_());
   }

   public void buildConfig(Builder builder) {
      builder.comment("General settings").push("general");
      this.ENABLED = builder.comment("Is Enabled?").define("enabled", true);
      this.COST = builder.comment("Cost").defineInRange("cost", this.getDefaultManaCost(), Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.STARTER_SPELL = builder.comment("Is Starter Glyph?").define("starter", this.defaultedStarterGlyph());
      this.PER_SPELL_LIMIT = builder.comment("The maximum number of times this glyph may appear in a single spell")
         .defineInRange("per_spell_limit", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
      this.GLYPH_TIER = builder.comment("The tier of the glyph").defineInRange("glyph_tier", this.defaultTier().value, 1, 99);
   }

   public boolean shouldShowInUnlock() {
      return this.isEnabled();
   }

   public boolean shouldShowInSpellBook() {
      return this.isEnabled();
   }

   public boolean isEnabled() {
      return this.ENABLED == null || (Boolean)this.ENABLED.get();
   }

   public int getAugmentLimit(ResourceLocation augmentTag) {
      return this.augmentLimits == null ? Integer.MAX_VALUE : this.augmentLimits.getAugmentLimit(augmentTag);
   }

   protected void buildAugmentLimitsConfig(Builder builder, Map<ResourceLocation, Integer> defaults) {
      this.augmentLimits = SpellPartConfigUtil.buildAugmentLimitsConfig(builder, defaults);
   }

   protected void buildInvalidCombosConfig(Builder builder, Set<ResourceLocation> defaults) {
      this.invalidCombinations = SpellPartConfigUtil.buildInvalidCombosConfig(builder, defaults);
   }

   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      this.addDefaultAugmentLimits(defaults);
      return defaults;
   }

   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
   }

   protected Set<ResourceLocation> getDefaultInvalidCombos(Set<ResourceLocation> defaults) {
      this.addDefaultInvalidCombos(defaults);
      return defaults;
   }

   protected void addDefaultInvalidCombos(Set<ResourceLocation> defaults) {
   }

   public boolean defaultedStarterGlyph() {
      return false;
   }

   public String getBookDescription() {
      return "";
   }

   public String getLocalizationKey() {
      return this.registryName.m_135827_() + ".glyph_name." + this.registryName.m_135815_();
   }

   public String getLocaleName() {
      return Component.m_237115_(this.getLocalizationKey()).getString();
   }
}
