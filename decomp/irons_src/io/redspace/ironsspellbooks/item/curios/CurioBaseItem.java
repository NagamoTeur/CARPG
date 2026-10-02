package io.redspace.ironsspellbooks.item.curios;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class CurioBaseItem extends Item implements ICurioItem {
   public CurioBaseItem(Properties properties) {
      super(properties);
   }

   public boolean isEquippedBy(@Nullable LivingEntity entity) {
      return entity != null && CuriosApi.getCuriosHelper().findFirstCurio(entity, this).isPresent();
   }

   @NotNull
   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      return new SoundInfo(SoundEvents.f_11672_, 1.0F, 1.0F);
   }
}
