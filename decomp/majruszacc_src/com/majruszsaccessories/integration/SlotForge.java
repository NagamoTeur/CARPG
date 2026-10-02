package com.majruszsaccessories.integration;

import com.majruszlibrary.platform.Integration;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class SlotForge implements ISlotPlatform {
   @Override
   public boolean isInstalled() {
      return Integration.isLoaded("curios");
   }

   @Override
   public List<ItemStack> find(LivingEntity entity, Predicate<ItemStack> predicate) {
      return CuriosApi.getCuriosHelper().findCurios(entity, predicate).stream().<ItemStack>map(SlotResult::stack).toList();
   }
}
