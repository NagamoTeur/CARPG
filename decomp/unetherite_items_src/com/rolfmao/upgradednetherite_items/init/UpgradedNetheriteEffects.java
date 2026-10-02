package com.rolfmao.upgradednetherite_items.init;

import com.rolfmao.upgradednetherite_items.effects.NetheriteLuck;
import com.rolfmao.upgradednetherite_items.effects.NetheriteResistance;
import com.rolfmao.upgradednetherite_items.effects.NetheriteStrength;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "upgradednetherite_items",
   bus = Bus.MOD
)
public class UpgradedNetheriteEffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "upgradednetherite_items");
   public static final RegistryObject<MobEffect> NETHERITE_STRENGTH = EFFECTS.register(
      "netherite_strength", () -> new NetheriteStrength().m_19472_(Attributes.f_22281_, "7208bdc3-d226-4f3b-a592-e8d525c6c0f8", 0.5, Operation.MULTIPLY_BASE)
   );
   public static final RegistryObject<MobEffect> NETHERITE_RESISTANCE = EFFECTS.register("netherite_resistance", NetheriteResistance::new);
   public static final RegistryObject<MobEffect> NETHERITE_LUCK = EFFECTS.register(
      "netherite_luck", () -> new NetheriteLuck().m_19472_(Attributes.f_22286_, "d63042c2-3a1d-4a54-a209-e7c3e6f737ef", 0.5, Operation.MULTIPLY_TOTAL)
   );
}
