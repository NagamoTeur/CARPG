package com.majruszsaccessories.common;

import com.majruszlibrary.client.ClientHelper;
import com.majruszlibrary.data.SerializableClass;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.events.OnItemDecorationsRendered;
import com.majruszlibrary.text.TextHelper;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.events.OnAccessoryTooltip;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.items.BoosterItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BonusHandler<Type extends Item> {
   protected final List<BonusComponent<Type>> components = new ArrayList<>();
   protected final Supplier<Type> item;
   protected final Class<?> clazz;
   protected final String id;

   public BonusHandler(Supplier<Type> item, Class<?> clazz, String id) {
      this.item = item;
      this.clazz = clazz;
      this.id = id;
   }

   public BonusHandler<Type> add(BonusComponent.ISupplier<Type> supplier) {
      this.components.add(supplier.apply(this));
      return this;
   }

   public List<? extends BonusComponent<Type>> getComponents() {
      return Collections.unmodifiableList(this.components);
   }

   public Type getItem() {
      return this.item.get();
   }

   public SerializableClass<?> getConfig() {
      return Serializables.getStatic(this.clazz);
   }

   public String getId() {
      return this.id;
   }

   protected void addTooltip(OnAccessoryTooltip data) {
      this.components
         .stream()
         .map(BonusComponent::getTooltipProviders)
         .flatMap(Collection::stream)
         .map(provider -> {
            if (data.holder.hasBonusRangeDefined() && !data.holder.hasBonusDefined()) {
               return provider.getRangeTooltip(data.holder);
            } else {
               return ClientHelper.isShiftDown() ? provider.getDetailedTooltip(data.holder) : provider.getTooltip(data.holder);
            }
         })
         .map(
            component -> data.holder.isBonusDisabled() && this.item.get() instanceof AccessoryItem
                  ? TextHelper.literal(component.getString()).m_130944_(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH})
                  : component.m_130940_(ChatFormatting.GRAY)
         )
         .forEach(data.components::add);
   }

   protected void addBoosterIcon(OnItemDecorationsRendered data) {
      ItemStack overlay = getOverlay(data.itemStack);
      if (!overlay.m_41619_()) {
         float blitOffset = data.gui.f_115093_;
         data.gui.f_115093_ += 50.0F;
         data.gui.m_115123_(overlay, data.x, data.y);
         data.gui.f_115093_ = blitOffset;
      }
   }

   private static ItemStack getOverlay(ItemStack itemStack) {
      if (itemStack.m_41720_() instanceof BoosterItem) {
         return new ItemStack((ItemLike)MajruszsAccessories.BOOSTER_OVERLAY_SINGLE.get());
      } else {
         AccessoryHolder holder = AccessoryHolder.getOrCreate(itemStack);

         return switch (holder.getBoosters().size()) {
            case 1 -> new ItemStack((ItemLike)MajruszsAccessories.BOOSTER_OVERLAY_SINGLE.get());
            case 2 -> new ItemStack((ItemLike)MajruszsAccessories.BOOSTER_OVERLAY_DOUBLE.get());
            case 3 -> new ItemStack((ItemLike)MajruszsAccessories.BOOSTER_OVERLAY_TRIPLE.get());
            default -> ItemStack.f_41583_;
         };
      }
   }
}
