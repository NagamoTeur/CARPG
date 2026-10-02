package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.lib.LibPotions;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "ars_nouveau");
   public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, "ars_nouveau");
   public static final RegistryObject<MobEffect> SHOCKED_EFFECT = EFFECTS.register("shocked", ShockedEffect::new);
   public static final RegistryObject<MobEffect> MANA_REGEN_EFFECT = EFFECTS.register("mana_regen", ManaRegenEffect::new);
   public static final RegistryObject<MobEffect> SUMMONING_SICKNESS_EFFECT = EFFECTS.register("summoning_sickness", SummoningSicknessEffect::new);
   public static final RegistryObject<MobEffect> HEX_EFFECT = EFFECTS.register("hex", HexEffect::new);
   public static final RegistryObject<MobEffect> SCRYING_EFFECT = EFFECTS.register("scrying", ScryingEffect::new);
   public static final RegistryObject<MobEffect> GLIDE_EFFECT = EFFECTS.register("glide", GlideEffect::new);
   public static final RegistryObject<MobEffect> SNARE_EFFECT = EFFECTS.register("snared", SnareEffect::new);
   public static final RegistryObject<MobEffect> FLIGHT_EFFECT = EFFECTS.register("flight", FlightEffect::new);
   public static final RegistryObject<MobEffect> GRAVITY_EFFECT = EFFECTS.register("gravity", GravityEffect::new);
   public static final RegistryObject<MobEffect> SPELL_DAMAGE_EFFECT = EFFECTS.register(
      "spell_damage", () -> new PublicEffect(MobEffectCategory.BENEFICIAL, new ParticleColor(30, 200, 200).getColor())
   );
   public static final RegistryObject<MobEffect> FAMILIAR_SICKNESS_EFFECT = EFFECTS.register(
      "familiar_sickness", () -> new PublicEffect(MobEffectCategory.NEUTRAL, new ParticleColor(30, 200, 200).getColor(), new ArrayList<>())
   );
   public static final RegistryObject<MobEffect> BOUNCE_EFFECT = EFFECTS.register("bounce", BounceEffect::new);
   public static final RegistryObject<MobEffect> MAGIC_FIND_EFFECT = EFFECTS.register("magic_find", MagicFindEffect::new);
   public static final RegistryObject<MobEffect> RECOVERY_EFFECT = EFFECTS.register("recovery", RecoveryEffect::new);
   public static final RegistryObject<MobEffect> BLAST_EFFECT = EFFECTS.register("blasting", BlastEffect::new);
   public static final RegistryObject<MobEffect> FREEZING_EFFECT = EFFECTS.register("freezing", FreezingEffect::new);
   public static final RegistryObject<MobEffect> DEFENCE_EFFECT = EFFECTS.register("shielding", DefenceEffect::new);
   public static final RegistryObject<Potion> MANA_REGEN_POTION = POTIONS.register(
      LibPotions.potion("mana_regen"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)MANA_REGEN_EFFECT.get(), 3600)})
   );
   public static final RegistryObject<Potion> LONG_MANA_REGEN_POTION = POTIONS.register(
      LibPotions.longPotion("mana_regen"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)MANA_REGEN_EFFECT.get(), 9600)})
   );
   public static final RegistryObject<Potion> STRONG_MANA_REGEN_POTION = POTIONS.register(
      LibPotions.strongPotion("mana_regen"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)MANA_REGEN_EFFECT.get(), 3600, 1)})
   );
   public static final RegistryObject<Potion> SPELL_DAMAGE_POTION = POTIONS.register(
      LibPotions.potion("spell_damage"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)SPELL_DAMAGE_EFFECT.get(), 3600)})
   );
   public static final RegistryObject<Potion> SPELL_DAMAGE_POTION_LONG = POTIONS.register(
      LibPotions.longPotion("spell_damage"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)SPELL_DAMAGE_EFFECT.get(), 9600)})
   );
   public static final RegistryObject<Potion> SPELL_DAMAGE_POTION_STRONG = POTIONS.register(
      LibPotions.strongPotion("spell_damage"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)SPELL_DAMAGE_EFFECT.get(), 3600, 1)})
   );
   public static final RegistryObject<Potion> RECOVERY_POTION = POTIONS.register(
      LibPotions.potion("recovery"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)RECOVERY_EFFECT.get(), 3600)})
   );
   public static final RegistryObject<Potion> LONG_RECOVERY_POTION = POTIONS.register(
      LibPotions.longPotion("recovery"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)RECOVERY_EFFECT.get(), 9600)})
   );
   public static final RegistryObject<Potion> STRONG_RECOVERY_POTION = POTIONS.register(
      LibPotions.strongPotion("recovery"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)RECOVERY_EFFECT.get(), 3600, 1)})
   );
   public static final RegistryObject<Potion> BLAST_POTION = POTIONS.register(
      LibPotions.potion("blasting"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)BLAST_EFFECT.get(), 200)})
   );
   public static final RegistryObject<Potion> LONG_BLAST_POTION = POTIONS.register(
      LibPotions.longPotion("blasting"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)BLAST_EFFECT.get(), 400)})
   );
   public static final RegistryObject<Potion> STRONG_BLAST_POTION = POTIONS.register(
      LibPotions.strongPotion("blasting"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)BLAST_EFFECT.get(), 140, 1)})
   );
   public static final RegistryObject<Potion> FREEZING_POTION = POTIONS.register(
      LibPotions.potion("freezing"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)FREEZING_EFFECT.get(), 1800)})
   );
   public static final RegistryObject<Potion> LONG_FREEZING_POTION = POTIONS.register(
      LibPotions.longPotion("freezing"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)FREEZING_EFFECT.get(), 3600)})
   );
   public static final RegistryObject<Potion> STRONG_FREEZING_POTION = POTIONS.register(
      LibPotions.strongPotion("freezing"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)FREEZING_EFFECT.get(), 1800, 1)})
   );
   public static final RegistryObject<Potion> DEFENCE_POTION = POTIONS.register(
      LibPotions.potion("shielding"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)DEFENCE_EFFECT.get(), 3600)})
   );
   public static final RegistryObject<Potion> LONG_DEFENCE_POTION = POTIONS.register(
      LibPotions.longPotion("shielding"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)DEFENCE_EFFECT.get(), 9600)})
   );
   public static final RegistryObject<Potion> STRONG_DEFENCE_POTION = POTIONS.register(
      LibPotions.strongPotion("shielding"), () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)DEFENCE_EFFECT.get(), 3600, 1)})
   );

   public static void addRecipes() {
      PotionBrewing.m_43513_(Potions.f_43599_, ItemsRegistry.ABJURATION_ESSENCE.get(), Potions.f_43602_);
      PotionBrewing.m_43513_(Potions.f_43602_, ItemsRegistry.MAGE_BLOOM.get(), (Potion)SPELL_DAMAGE_POTION.get());
      PotionBrewing.m_43513_((Potion)SPELL_DAMAGE_POTION.get(), Items.f_42525_, (Potion)SPELL_DAMAGE_POTION_STRONG.get());
      PotionBrewing.m_43513_((Potion)SPELL_DAMAGE_POTION.get(), Items.f_42451_, (Potion)SPELL_DAMAGE_POTION_LONG.get());
      PotionBrewing.m_43513_(Potions.f_43602_, BlockRegistry.SOURCEBERRY_BUSH.m_5456_(), (Potion)MANA_REGEN_POTION.get());
      PotionBrewing.m_43513_((Potion)MANA_REGEN_POTION.get(), Items.f_42525_, (Potion)STRONG_MANA_REGEN_POTION.get());
      PotionBrewing.m_43513_((Potion)MANA_REGEN_POTION.get(), Items.f_42451_, (Potion)LONG_MANA_REGEN_POTION.get());
      PotionBrewing.m_43513_(Potions.f_43602_, BlockRegistry.MENDOSTEEN_POD.m_5456_(), (Potion)RECOVERY_POTION.get());
      PotionBrewing.m_43513_((Potion)RECOVERY_POTION.get(), Items.f_42525_, (Potion)STRONG_RECOVERY_POTION.get());
      PotionBrewing.m_43513_((Potion)RECOVERY_POTION.get(), Items.f_42451_, (Potion)LONG_RECOVERY_POTION.get());
      PotionBrewing.m_43513_(Potions.f_43602_, BlockRegistry.BOMBEGRANTE_POD.m_5456_(), (Potion)BLAST_POTION.get());
      PotionBrewing.m_43513_((Potion)BLAST_POTION.get(), Items.f_42525_, (Potion)STRONG_BLAST_POTION.get());
      PotionBrewing.m_43513_((Potion)BLAST_POTION.get(), Items.f_42451_, (Potion)LONG_BLAST_POTION.get());
      PotionBrewing.m_43513_(Potions.f_43602_, BlockRegistry.FROSTAYA_POD.m_5456_(), (Potion)FREEZING_POTION.get());
      PotionBrewing.m_43513_((Potion)FREEZING_POTION.get(), Items.f_42525_, (Potion)STRONG_FREEZING_POTION.get());
      PotionBrewing.m_43513_((Potion)FREEZING_POTION.get(), Items.f_42451_, (Potion)LONG_FREEZING_POTION.get());
      PotionBrewing.m_43513_(Potions.f_43602_, BlockRegistry.BASTION_POD.m_5456_(), (Potion)DEFENCE_POTION.get());
      PotionBrewing.m_43513_((Potion)DEFENCE_POTION.get(), Items.f_42525_, (Potion)STRONG_DEFENCE_POTION.get());
      PotionBrewing.m_43513_((Potion)DEFENCE_POTION.get(), Items.f_42451_, (Potion)LONG_DEFENCE_POTION.get());
      PotionBrewing.m_43513_(Potions.f_43599_, ItemsRegistry.WILDEN_WING.get(), Potions.f_43607_);
      PotionBrewing.m_43513_(Potions.f_43599_, ItemsRegistry.WILDEN_HORN.get(), Potions.f_43590_);
      PotionBrewing.m_43513_(Potions.f_43599_, ItemsRegistry.WILDEN_SPIKE.get(), Potions.f_43622_);
   }
}
