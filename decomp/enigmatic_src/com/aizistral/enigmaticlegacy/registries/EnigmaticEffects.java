package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.effects.BlazingStrengthEffect;
import com.aizistral.enigmaticlegacy.effects.MoltenHeartEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class EnigmaticEffects extends AbstractRegistry<MobEffect> {
   private static final EnigmaticEffects INSTANCE = new EnigmaticEffects();
   @ObjectHolder(
      value = "enigmaticlegacy:blazing_strength",
      registryName = "mob_effect"
   )
   public static final BlazingStrengthEffect BLAZING_STRENGTH = null;
   @ObjectHolder(
      value = "enigmaticlegacy:molten_heart",
      registryName = "mob_effect"
   )
   public static final MoltenHeartEffect MOLTEN_HEART = null;

   private EnigmaticEffects() {
      super(ForgeRegistries.MOB_EFFECTS);
      this.register("blazing_strength", BlazingStrengthEffect::new);
      this.register("molten_heart", MoltenHeartEffect::new);
   }
}
