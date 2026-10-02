package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtradimensionalEye extends ItemBase implements Vanishable {
   public float range = 3.0F;

   public ExtradimensionalEye() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.UNCOMMON).m_41487_(1));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEye7");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (ItemNBTHelper.verifyExistance(stack, "BoundDimension")) {
         String boundDimensionName = null;
         String dimensionID = ItemNBTHelper.getString(stack, "BoundDimension", "minecraft:overworld");
         if (dimensionID.equals("minecraft:overworld")) {
            boundDimensionName = "tooltip.enigmaticlegacy.overworld";
         } else if (dimensionID.equals("minecraft:the_nether")) {
            boundDimensionName = "tooltip.enigmaticlegacy.nether";
         } else if (dimensionID.equals("minecraft:the_end")) {
            boundDimensionName = "tooltip.enigmaticlegacy.end";
         }

         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEyeLocation");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEyeX", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundX", 0));
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEyeY", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundY", 0));
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEyeZ", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundZ", 0));
         if (boundDimensionName != null) {
            ItemLoreHelper.addLocalizedString(
               list, "tooltip.enigmaticlegacy.extradimensionalEyeDimension", null, Component.m_237115_(boundDimensionName).getString()
            );
         } else {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.extradimensionalEyeDimension", ChatFormatting.GOLD, dimensionID);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      if (playerIn.m_6047_() && ItemNBTHelper.getString(itemstack, "BoundDimension", null) == null) {
         ItemNBTHelper.setDouble(itemstack, "BoundX", playerIn.m_20185_());
         ItemNBTHelper.setDouble(itemstack, "BoundY", playerIn.m_20186_());
         ItemNBTHelper.setDouble(itemstack, "BoundZ", playerIn.m_20189_());
         ItemNBTHelper.setString(itemstack, "BoundDimension", playerIn.f_19853_.m_46472_().m_135782_().toString());
         playerIn.m_6674_(handIn);
         return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
      } else {
         return new InteractionResultHolder(InteractionResult.FAIL, itemstack);
      }
   }
}
