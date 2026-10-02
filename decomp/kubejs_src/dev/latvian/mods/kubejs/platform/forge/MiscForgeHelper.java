package dev.latvian.mods.kubejs.platform.forge;

import dev.latvian.mods.kubejs.gui.KubeJSMenu;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import dev.latvian.mods.kubejs.script.PlatformWrapper;
import java.lang.reflect.Field;
import java.util.Optional;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

public class MiscForgeHelper implements MiscPlatformHelper {
   @Override
   public void setModName(PlatformWrapper.ModInfo info, String name) {
      try {
         Optional<? extends ModContainer> mc = ModList.get().getModContainerById(info.getId());
         if (mc.isPresent() && mc.get().getModInfo() instanceof ModInfo i) {
            Field field = ModInfo.class.getDeclaredField("displayName");
            field.setAccessible(true);
            field.set(i, name);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   @Override
   public MobCategory getMobCategory(String name) {
      return MobCategory.byName(name);
   }

   @Override
   public boolean isDataGen() {
      return ModLoader.isDataGenRunning();
   }

   @Override
   public long ingotFluidAmount() {
      return 90L;
   }

   @Override
   public long bottleFluidAmount() {
      return 250L;
   }

   @Override
   public MenuType<KubeJSMenu> createMenuType() {
      return new MenuType(KubeJSMenu::new);
   }
}
