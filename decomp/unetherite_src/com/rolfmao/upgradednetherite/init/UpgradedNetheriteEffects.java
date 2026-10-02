package com.rolfmao.upgradednetherite.init;

import com.rolfmao.upgradednetherite.effects.Attraction;
import com.rolfmao.upgradednetherite.effects.EnderAnchor;
import com.rolfmao.upgradednetherite.effects.NetheriteCorruption;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "upgradednetherite",
   bus = Bus.MOD
)
public class UpgradedNetheriteEffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "upgradednetherite");
   public static final RegistryObject<MobEffect> ENDER_ANCHOR = EFFECTS.register("ender_anchor", EnderAnchor::new);
   public static final RegistryObject<MobEffect> ATTRACTION = EFFECTS.register("attraction", Attraction::new);
   public static final RegistryObject<MobEffect> NETHERITE_CORRUPTION = EFFECTS.register("netherite_corruption", NetheriteCorruption::new);
}
