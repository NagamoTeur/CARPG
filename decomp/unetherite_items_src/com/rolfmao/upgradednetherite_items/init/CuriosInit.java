package com.rolfmao.upgradednetherite_items.init;

import com.rolfmao.upgradednetherite_items.items.NetheriteTotemBase;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosInit {
   public static void attachCaps(AttachCapabilitiesEvent<ItemStack> event) {
      if (ModList.get() != null && ModList.get().getModContainerById("curios").isPresent()) {
         final ItemStack stack = (ItemStack)event.getObject();
         Item item = stack.m_41720_();
         if (ForgeRegistries.ITEMS.getResourceKey(item).isPresent() && item instanceof NetheriteTotemBase) {
            event.addCapability(new ResourceLocation(ForgeRegistries.ITEMS.getKey(item) + "_curios"), new ICapabilityProvider() {
               final ICurio curio = new ICurio() {
                  public ItemStack getStack() {
                     return stack;
                  }

                  public boolean canEquipFromUse(SlotContext ctx) {
                     return true;
                  }
               };

               @Nonnull
               public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                  return CuriosCapability.ITEM.orEmpty(cap, LazyOptional.of(() -> this.curio));
               }
            });
         }
      }
   }
}
