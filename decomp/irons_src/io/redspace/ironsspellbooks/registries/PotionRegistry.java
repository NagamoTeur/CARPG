package io.redspace.ironsspellbooks.registries;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegistry {
   public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, "irons_spellbooks");
   public static final RegistryObject<Potion> INSTANT_MANA_ONE = POTIONS.register(
      "instant_mana_one", () -> new Potion("mana", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MobEffectRegistry.INSTANT_MANA.get())})
   );
   public static final RegistryObject<Potion> INSTANT_MANA_TWO = POTIONS.register(
      "instant_mana_two", () -> new Potion("mana", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MobEffectRegistry.INSTANT_MANA.get(), 0, 1)})
   );
   public static final RegistryObject<Potion> INSTANT_MANA_THREE = POTIONS.register(
      "instant_mana_three", () -> new Potion("mana", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MobEffectRegistry.INSTANT_MANA.get(), 0, 2)})
   );
   public static final RegistryObject<Potion> INSTANT_MANA_FOUR = POTIONS.register(
      "instant_mana_four", () -> new Potion("mana", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MobEffectRegistry.INSTANT_MANA.get(), 0, 3)})
   );

   public static void register(IEventBus eventBus) {
      POTIONS.register(eventBus);
      eventBus.addListener(PotionRegistry::addRecipes);
   }

   public static void addRecipes(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         PotionBrewing.m_43513_(Potions.f_43602_, (Item)ItemRegistry.ARCANE_ESSENCE.get(), (Potion)INSTANT_MANA_ONE.get());
         PotionBrewing.m_43513_((Potion)INSTANT_MANA_ONE.get(), Items.f_42525_, (Potion)INSTANT_MANA_TWO.get());
         PotionBrewing.m_43513_((Potion)INSTANT_MANA_TWO.get(), Items.f_151049_, (Potion)INSTANT_MANA_THREE.get());
         PotionBrewing.m_43513_((Potion)INSTANT_MANA_THREE.get(), Items.f_151086_, (Potion)INSTANT_MANA_FOUR.get());
      });
   }

   public static void addContainerMix(Item pFrom, Item pIngredient, Item pTo) {
      PotionBrewing.f_43495_.add(new Mix(ForgeRegistries.ITEMS, pFrom, Ingredient.m_43929_(new ItemLike[]{pIngredient}), pTo));
   }
}
