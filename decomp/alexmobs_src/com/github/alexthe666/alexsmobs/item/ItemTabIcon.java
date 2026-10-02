package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemTabIcon extends Item {
   public ItemTabIcon(Properties properties) {
      super(properties);
   }

   public static boolean hasCustomEntityDisplay(ItemStack stack) {
      return stack.m_41783_() != null && stack.m_41783_().m_128441_("DisplayEntityType");
   }

   public static String getCustomDisplayEntityString(ItemStack stack) {
      return stack.m_41783_().m_128461_("DisplayEntityType");
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)AlexsMobs.PROXY.getISTERProperties());
   }

   @Nullable
   public static EntityType getEntityType(@Nullable CompoundTag tag) {
      if (tag != null && tag.m_128441_("DisplayEntityType")) {
         String entityType = tag.m_128461_("DisplayEntityType");
         return (EntityType)Registry.f_122826_.m_6612_(ResourceLocation.m_135820_(entityType)).orElse(null);
      } else {
         return null;
      }
   }
}
