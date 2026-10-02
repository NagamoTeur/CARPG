package lykrast.meetyourfight.registry;

import lykrast.meetyourfight.item.compat.CocktailShotgun;
import lykrast.meetyourfight.item.compat.PhantasmalRifle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class CompatGWRItems {
   public static RegistryObject<Item> phantasmalRifle;
   public static RegistryObject<Item> cocktailShotgun;

   public static void registerItems() {
      phantasmalRifle = ModItems.REG
         .register(
            "phantasmal_rifle",
            () -> new PhantasmalRifle(ModItems.bossNS().m_41503_(2376), 0, 1.6, 22, 0.0, 22)
                  .fireSound(lykrast.gunswithoutroses.registry.ModSounds.sniper)
                  .repair(() -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.phantoplasm.get()}))
         );
      cocktailShotgun = ModItems.REG
         .register(
            "cocktail_shotgun",
            () -> new CocktailShotgun(ModItems.bossNS().m_41503_(3473), 0, 0.45, 16, 5.0, 14, 6)
                  .ignoreInvulnerability(true)
                  .fireSound(lykrast.gunswithoutroses.registry.ModSounds.shotgun)
                  .repair(() -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.fortunesFavor.get()}))
         );
   }
}
