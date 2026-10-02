package net.thirdlife.iterrpg.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thirdlife.iterrpg.world.inventory.MobPlacerGUIMenu;
import net.thirdlife.iterrpg.world.inventory.SpellbookGuiMenu;

public class IterRpgModMenus {
   public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "iter_rpg");
   public static final RegistryObject<MenuType<MobPlacerGUIMenu>> MOB_PLACER_GUI = REGISTRY.register(
      "mob_placer_gui", () -> IForgeMenuType.create(MobPlacerGUIMenu::new)
   );
   public static final RegistryObject<MenuType<SpellbookGuiMenu>> SPELLBOOK_GUI = REGISTRY.register(
      "spellbook_gui", () -> IForgeMenuType.create(SpellbookGuiMenu::new)
   );
}
