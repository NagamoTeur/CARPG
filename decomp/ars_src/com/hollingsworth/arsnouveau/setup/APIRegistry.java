package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.scrying.CompoundScryer;
import com.hollingsworth.arsnouveau.api.scrying.IScryer;
import com.hollingsworth.arsnouveau.api.scrying.SingleBlockScryer;
import com.hollingsworth.arsnouveau.api.scrying.TagScryer;
import com.hollingsworth.arsnouveau.api.sound.SpellSound;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.familiars.AmethystFamiliarHolder;
import com.hollingsworth.arsnouveau.common.familiars.BookwyrmFamiliarHolder;
import com.hollingsworth.arsnouveau.common.familiars.DrygmyFamiliarHolder;
import com.hollingsworth.arsnouveau.common.familiars.StarbuncleFamiliarHolder;
import com.hollingsworth.arsnouveau.common.familiars.WhirlisprigFamiliarHolder;
import com.hollingsworth.arsnouveau.common.familiars.WixieFamiliarHolder;
import com.hollingsworth.arsnouveau.common.mob_jar.AllayBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.BlazeBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.ChickenBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.CreeperBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.DecoyBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.DragonBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.ElderGuardianBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.FrogBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.GhastBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.GlowSquidBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.MooshroomBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.PandaBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.PiglinBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.PufferfishBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.SheepBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.SquidBehavior;
import com.hollingsworth.arsnouveau.common.mob_jar.VillagerBehavior;
import com.hollingsworth.arsnouveau.common.perk.ChillingPerk;
import com.hollingsworth.arsnouveau.common.perk.DepthsPerk;
import com.hollingsworth.arsnouveau.common.perk.EmptyPerk;
import com.hollingsworth.arsnouveau.common.perk.FeatherPerk;
import com.hollingsworth.arsnouveau.common.perk.GlidingPerk;
import com.hollingsworth.arsnouveau.common.perk.IgnitePerk;
import com.hollingsworth.arsnouveau.common.perk.JumpHeightPerk;
import com.hollingsworth.arsnouveau.common.perk.LootingPerk;
import com.hollingsworth.arsnouveau.common.perk.MagicCapacityPerk;
import com.hollingsworth.arsnouveau.common.perk.MagicResistPerk;
import com.hollingsworth.arsnouveau.common.perk.PotionDurationPerk;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.common.perk.SaturationPerk;
import com.hollingsworth.arsnouveau.common.perk.SpellDamagePerk;
import com.hollingsworth.arsnouveau.common.perk.StarbunclePerk;
import com.hollingsworth.arsnouveau.common.perk.TotemPerk;
import com.hollingsworth.arsnouveau.common.perk.VampiricPerk;
import com.hollingsworth.arsnouveau.common.ritual.ConjureDesertRitual;
import com.hollingsworth.arsnouveau.common.ritual.ConjurePlainsRitual;
import com.hollingsworth.arsnouveau.common.ritual.DenySpawnRitual;
import com.hollingsworth.arsnouveau.common.ritual.FloweringRitual;
import com.hollingsworth.arsnouveau.common.ritual.ForestationRitual;
import com.hollingsworth.arsnouveau.common.ritual.RitualAnimalSummoning;
import com.hollingsworth.arsnouveau.common.ritual.RitualAwakening;
import com.hollingsworth.arsnouveau.common.ritual.RitualBinding;
import com.hollingsworth.arsnouveau.common.ritual.RitualBreed;
import com.hollingsworth.arsnouveau.common.ritual.RitualCloudshaper;
import com.hollingsworth.arsnouveau.common.ritual.RitualDig;
import com.hollingsworth.arsnouveau.common.ritual.RitualDisintegration;
import com.hollingsworth.arsnouveau.common.ritual.RitualFlight;
import com.hollingsworth.arsnouveau.common.ritual.RitualGravity;
import com.hollingsworth.arsnouveau.common.ritual.RitualHarvest;
import com.hollingsworth.arsnouveau.common.ritual.RitualHealing;
import com.hollingsworth.arsnouveau.common.ritual.RitualMobCapture;
import com.hollingsworth.arsnouveau.common.ritual.RitualMoonfall;
import com.hollingsworth.arsnouveau.common.ritual.RitualOvergrowth;
import com.hollingsworth.arsnouveau.common.ritual.RitualPillagerRaid;
import com.hollingsworth.arsnouveau.common.ritual.RitualScrying;
import com.hollingsworth.arsnouveau.common.ritual.RitualSunrise;
import com.hollingsworth.arsnouveau.common.ritual.RitualWarp;
import com.hollingsworth.arsnouveau.common.ritual.RitualWildenSummoning;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectAnimate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBlink;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBounce;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBreak;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBurst;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectColdSnap;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectConjureWater;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCraft;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCrush;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCut;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDelay;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDispel;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectEnderChest;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectEvaporate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectExchange;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectExplosion;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFangs;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFell;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFirework;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFlare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFreeze;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGravity;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGrow;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHarm;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHarvest;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHeal;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHex;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectIgnite;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInfuse;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectIntangible;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInteract;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInvisibility;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectKnockback;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLeap;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLight;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLightning;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLinger;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectName;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPhantomBlock;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPickup;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPlaceBlock;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPull;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRedstone;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRotate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRune;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSenseMagic;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSlowfall;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSmelt;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSnare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonDecoy;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonSteed;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonUndead;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonVex;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonWolves;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectToss;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWall;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWindshear;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWither;
import com.hollingsworth.arsnouveau.common.spell.method.MethodOrbit;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodSelf;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.spell.method.MethodUnderfoot;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class APIRegistry {
   public static void setup() {
      if (!FMLEnvironment.production) {
         registerWip();
      }

      registerSpell(MethodProjectile.INSTANCE);
      registerSpell(MethodTouch.INSTANCE);
      registerSpell(MethodSelf.INSTANCE);
      registerSpell(EffectBreak.INSTANCE);
      registerSpell(EffectHarm.INSTANCE);
      registerSpell(EffectIgnite.INSTANCE);
      registerSpell(EffectPhantomBlock.INSTANCE);
      registerSpell(EffectHeal.INSTANCE);
      registerSpell(EffectGrow.INSTANCE);
      registerSpell(EffectKnockback.INSTANCE);
      registerSpell(EffectLight.INSTANCE);
      registerSpell(EffectDispel.INSTANCE);
      registerSpell(EffectLaunch.INSTANCE);
      registerSpell(EffectPull.INSTANCE);
      registerSpell(EffectBlink.INSTANCE);
      registerSpell(EffectExplosion.INSTANCE);
      registerSpell(EffectLightning.INSTANCE);
      registerSpell(EffectSlowfall.INSTANCE);
      registerSpell(EffectFangs.INSTANCE);
      registerSpell(EffectSummonVex.INSTANCE);
      registerSpell(AugmentAccelerate.INSTANCE);
      registerSpell(AugmentDecelerate.INSTANCE);
      registerSpell(AugmentSplit.INSTANCE);
      registerSpell(AugmentAmplify.INSTANCE);
      registerSpell(AugmentAOE.INSTANCE);
      registerSpell(AugmentExtendTime.INSTANCE);
      registerSpell(AugmentPierce.INSTANCE);
      registerSpell(AugmentDampen.INSTANCE);
      registerSpell(AugmentExtract.INSTANCE);
      registerSpell(AugmentFortune.INSTANCE);
      registerSpell(EffectEnderChest.INSTANCE);
      registerSpell(EffectHarvest.INSTANCE);
      registerSpell(EffectFell.INSTANCE);
      registerSpell(EffectPickup.INSTANCE);
      registerSpell(EffectInteract.INSTANCE);
      registerSpell(EffectPlaceBlock.INSTANCE);
      registerSpell(EffectSnare.INSTANCE);
      registerSpell(EffectSmelt.INSTANCE);
      registerSpell(EffectLeap.INSTANCE);
      registerSpell(EffectDelay.INSTANCE);
      registerSpell(EffectRedstone.INSTANCE);
      registerSpell(EffectIntangible.INSTANCE);
      registerSpell(EffectInvisibility.INSTANCE);
      registerSpell(AugmentDurationDown.INSTANCE);
      registerSpell(EffectWither.INSTANCE);
      registerSpell(EffectExchange.INSTANCE);
      registerSpell(EffectCraft.INSTANCE);
      registerSpell(EffectFlare.INSTANCE);
      registerSpell(EffectColdSnap.INSTANCE);
      registerSpell(EffectConjureWater.INSTANCE);
      registerSpell(EffectGravity.INSTANCE);
      registerSpell(EffectCut.INSTANCE);
      registerSpell(EffectCrush.INSTANCE);
      registerSpell(EffectSummonWolves.INSTANCE);
      registerSpell(EffectSummonSteed.INSTANCE);
      registerSpell(EffectSummonDecoy.INSTANCE);
      registerSpell(EffectHex.INSTANCE);
      registerSpell(MethodUnderfoot.INSTANCE);
      registerSpell(EffectGlide.INSTANCE);
      registerSpell(MethodOrbit.INSTANCE);
      registerSpell(EffectRune.INSTANCE);
      registerSpell(EffectFreeze.INSTANCE);
      registerSpell(EffectName.INSTANCE);
      registerSpell(EffectSummonUndead.INSTANCE);
      registerSpell(EffectFirework.INSTANCE);
      registerSpell(EffectToss.INSTANCE);
      registerSpell(EffectBounce.INSTANCE);
      registerSpell(AugmentSensitive.INSTANCE);
      registerSpell(EffectWindshear.INSTANCE);
      registerSpell(EffectEvaporate.INSTANCE);
      registerSpell(EffectLinger.INSTANCE);
      registerSpell(EffectSenseMagic.INSTANCE);
      registerSpell(EffectInfuse.INSTANCE);
      registerSpell(EffectRotate.INSTANCE);
      registerSpell(EffectWall.INSTANCE);
      registerSpell(EffectAnimate.INSTANCE);
      registerSpell(EffectBurst.INSTANCE);
      registerSpell(AugmentRandomize.INSTANCE);
      registerRitual(new RitualDig());
      registerRitual(new RitualMoonfall());
      registerRitual(new RitualCloudshaper());
      registerRitual(new RitualSunrise());
      registerRitual(new RitualDisintegration());
      registerRitual(new RitualPillagerRaid());
      registerRitual(new RitualOvergrowth());
      registerRitual(new RitualBreed());
      registerRitual(new RitualHealing());
      registerRitual(new RitualWarp());
      registerRitual(new RitualScrying());
      registerRitual(new RitualFlight());
      registerRitual(new RitualGravity());
      registerRitual(new RitualWildenSummoning());
      registerRitual(new RitualAnimalSummoning());
      registerRitual(new RitualBinding());
      registerRitual(new RitualAwakening());
      registerRitual(new RitualHarvest());
      registerRitual(new RitualMobCapture());
      registerRitual(new ConjurePlainsRitual());
      registerRitual(new ForestationRitual());
      registerRitual(new FloweringRitual());
      registerRitual(new ConjureDesertRitual());
      registerRitual(new DenySpawnRitual());
      registerFamiliar(new StarbuncleFamiliarHolder());
      registerFamiliar(new DrygmyFamiliarHolder());
      registerFamiliar(new WhirlisprigFamiliarHolder());
      registerFamiliar(new WixieFamiliarHolder());
      registerFamiliar(new BookwyrmFamiliarHolder());
      registerFamiliar(new AmethystFamiliarHolder());
      registerScryer(SingleBlockScryer.INSTANCE);
      registerScryer(CompoundScryer.INSTANCE);
      registerScryer(TagScryer.INSTANCE);
      registerPerk(EmptyPerk.INSTANCE);
      registerPerk(StarbunclePerk.INSTANCE);
      registerPerk(DepthsPerk.INSTANCE);
      registerPerk(FeatherPerk.INSTANCE);
      registerPerk(GlidingPerk.INSTANCE);
      registerPerk(JumpHeightPerk.INSTANCE);
      registerPerk(LootingPerk.INSTANCE);
      registerPerk(MagicCapacityPerk.INSTANCE);
      registerPerk(MagicResistPerk.INSTANCE);
      registerPerk(PotionDurationPerk.INSTANCE);
      registerPerk(RepairingPerk.INSTANCE);
      registerPerk(SaturationPerk.INSTANCE);
      registerPerk(SpellDamagePerk.INSTANCE);
      registerPerk(ChillingPerk.INSTANCE);
      registerPerk(IgnitePerk.INSTANCE);
      registerPerk(TotemPerk.INSTANCE);
      registerPerk(VampiricPerk.INSTANCE);
   }

   private static void registerWip() {
   }

   public static void postInit() {
      ArsNouveauAPI api = ArsNouveauAPI.getInstance();
      api.getEnchantingRecipeTypes().add((RecipeType<? extends IEnchantingRecipe>)RecipeRegistry.APPARATUS_TYPE.get());
      api.getEnchantingRecipeTypes().add((RecipeType<? extends IEnchantingRecipe>)RecipeRegistry.ENCHANTMENT_TYPE.get());
      api.getEnchantingRecipeTypes().add((RecipeType<? extends IEnchantingRecipe>)RecipeRegistry.REACTIVE_TYPE.get());
      api.getEnchantingRecipeTypes().add((RecipeType<? extends IEnchantingRecipe>)RecipeRegistry.SPELL_WRITE_TYPE.get());
      api.getEnchantingRecipeTypes().add((RecipeType<? extends IEnchantingRecipe>)RecipeRegistry.ARMOR_UPGRADE_TYPE.get());
      api.registerPerkProvider(
         ItemsRegistry.ARCHMAGE_BOOTS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.ARCHMAGE_HOOD,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.ARCHMAGE_LEGGINGS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.THREE))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.ARCHMAGE_ROBES,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(List.of(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.THREE))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.APPRENTICE_HOOD,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.THREE))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.APPRENTICE_BOOTS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.TWO))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.APPRENTICE_LEGGINGS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(
                  Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.THREE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE)
               )
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.APPRENTICE_ROBES,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(
                  Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.THREE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE)
               )
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.NOVICE_BOOTS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE))
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.NOVICE_ROBES,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(
                  Arrays.asList(PerkSlot.TWO), Arrays.asList(PerkSlot.TWO, PerkSlot.THREE), Arrays.asList(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.THREE)
               )
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.NOVICE_LEGGINGS,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(
                  Arrays.asList(PerkSlot.TWO), Arrays.asList(PerkSlot.TWO, PerkSlot.THREE), Arrays.asList(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.THREE)
               )
            )
      );
      api.registerPerkProvider(
         ItemsRegistry.NOVICE_HOOD,
         stack -> new ArmorPerkHolder(
               stack,
               Arrays.asList(Arrays.asList(PerkSlot.ONE), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO), Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE))
            )
      );
      SoundRegistry.DEFAULT_SPELL_SOUND = new SpellSound(
         (SoundEvent)SoundRegistry.DEFAULT_FAMILY.get(), Component.m_237115_("ars_nouveau.sound.default_family")
      );
      SoundRegistry.EMPTY_SPELL_SOUND = new SpellSound((SoundEvent)SoundRegistry.EMPTY_SOUND_FAMILY.get(), Component.m_237115_("ars_nouveau.sound.empty"));
      SoundRegistry.GAIA_SPELL_SOUND = new SpellSound((SoundEvent)SoundRegistry.GAIA_FAMILY.get(), Component.m_237115_("ars_nouveau.sound.gaia_family"));
      SoundRegistry.TEMPESTRY_SPELL_SOUND = new SpellSound(
         (SoundEvent)SoundRegistry.TEMPESTRY_FAMILY.get(), Component.m_237115_("ars_nouveau.sound.tempestry_family")
      );
      SoundRegistry.FIRE_SPELL_SOUND = new SpellSound((SoundEvent)SoundRegistry.FIRE_FAMILY.get(), Component.m_237115_("ars_nouveau.sound.fire_family"));
      ArsNouveauAPI.getInstance().registerSpellSound(SoundRegistry.DEFAULT_SPELL_SOUND);
      ArsNouveauAPI.getInstance().registerSpellSound(SoundRegistry.EMPTY_SPELL_SOUND);
      ArsNouveauAPI.getInstance().registerSpellSound(SoundRegistry.GAIA_SPELL_SOUND);
      ArsNouveauAPI.getInstance().registerSpellSound(SoundRegistry.TEMPESTRY_SPELL_SOUND);
      ArsNouveauAPI.getInstance().registerSpellSound(SoundRegistry.FIRE_SPELL_SOUND);
      JarBehaviorRegistry.register(EntityType.f_20563_, new ElderGuardianBehavior());
      JarBehaviorRegistry.register(EntityType.f_20558_, new CreeperBehavior());
      JarBehaviorRegistry.register(EntityType.f_20555_, new ChickenBehavior());
      JarBehaviorRegistry.register(EntityType.f_20492_, new VillagerBehavior());
      JarBehaviorRegistry.register(EntityType.f_20520_, new SheepBehavior());
      JarBehaviorRegistry.register(EntityType.f_217012_, new FrogBehavior());
      JarBehaviorRegistry.register(EntityType.f_20511_, new PiglinBehavior());
      JarBehaviorRegistry.register(EntityType.f_20453_, new GhastBehavior());
      JarBehaviorRegistry.register(EntityType.f_20480_, new SquidBehavior());
      JarBehaviorRegistry.register(EntityType.f_147034_, new GlowSquidBehavior());
      JarBehaviorRegistry.register(EntityType.f_20551_, new BlazeBehavior());
      JarBehaviorRegistry.register(EntityType.f_20507_, new PandaBehavior());
      JarBehaviorRegistry.register(EntityType.f_20504_, new MooshroomBehavior());
      JarBehaviorRegistry.register(EntityType.f_20565_, new DragonBehavior());
      JarBehaviorRegistry.register(EntityType.f_20516_, new PufferfishBehavior());
      JarBehaviorRegistry.register(EntityType.f_217014_, new AllayBehavior());
      JarBehaviorRegistry.register((EntityType)ModEntities.ENTITY_DUMMY.get(), new DecoyBehavior());
   }

   public static void registerFamiliar(AbstractFamiliarHolder familiar) {
      ArsNouveauAPI.getInstance().registerFamiliar(familiar);
   }

   public static void registerSpell(AbstractSpellPart spellPart) {
      ArsNouveauAPI.getInstance().registerSpell(spellPart);
   }

   public static void registerPerk(IPerk perk) {
      ArsNouveauAPI.getInstance().registerPerk(perk);
   }

   public static void registerScryer(IScryer scryer) {
      ArsNouveauAPI.getInstance().registerScryer(scryer);
   }

   public static void registerRitual(AbstractRitual ritual) {
      ArsNouveauAPI.getInstance().registerRitual(ritual);
   }

   private APIRegistry() {
   }
}
