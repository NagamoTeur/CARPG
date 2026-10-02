package dev.latvian.mods.kubejs.item.custom;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShearsItemBuilder extends ItemBuilder {
   public static final ResourceLocation TAG = new ResourceLocation(Platform.isForge() ? "forge:shears" : "c:shears");
   public transient float speedBaseline;

   public static boolean isCustomShears(ItemStack stack) {
      return stack.m_41720_() instanceof ShearsItemBuilder.ShearsItemKJS;
   }

   public ShearsItemBuilder(ResourceLocation i) {
      super(i);
      this.speedBaseline(5.0F);
      this.parentModel("minecraft:item/handheld");
      this.unstackable();
      this.tag(TAG);
   }

   public ShearsItemBuilder speedBaseline(float f) {
      this.speedBaseline = f;
      return this;
   }

   public Item createObject() {
      ShearsItemBuilder.ShearsItemKJS item = new ShearsItemBuilder.ShearsItemKJS(this);
      DispenserBlock.m_52672_(item, new ShearsDispenseItemBehavior());
      return item;
   }

   public static class ShearsItemKJS extends ShearsItem {
      public final ShearsItemBuilder builder;

      public ShearsItemKJS(ShearsItemBuilder builder) {
         super(builder.createItemProperties());
         this.builder = builder;
      }

      public float m_8102_(ItemStack itemStack, BlockState blockState) {
         if (blockState.m_204336_(BlockTags.f_13035_)) {
            return 15.0F;
         } else if (blockState.m_60713_(Blocks.f_50033_)) {
            return this.builder.speedBaseline * 3.0F;
         } else if (blockState.m_60713_(Blocks.f_50191_) || blockState.m_60713_(Blocks.f_152475_)) {
            return this.builder.speedBaseline / 2.5F;
         } else {
            return blockState.m_204336_(BlockTags.f_13089_) ? this.builder.speedBaseline : super.m_8102_(itemStack, blockState);
         }
      }
   }
}
