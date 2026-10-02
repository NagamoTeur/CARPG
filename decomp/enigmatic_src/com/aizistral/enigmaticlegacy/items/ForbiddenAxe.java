package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseTool;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class ForbiddenAxe extends SwordItem {
   public static Omniconfig.PerhapsParameter beheadingBase;
   public static Omniconfig.PerhapsParameter beheadingBonus;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("ForbiddenAxe");
      beheadingBase = builder.comment("Default chance to behead an enemy with Axe of Executioner. Defined as percentage.")
         .max(100.0)
         .getPerhaps("BeheadingBase", 10);
      beheadingBonus = builder.comment("Bonus percantage to beheading chance from each Looting level applied to Axe of Executioner.")
         .max(100.0)
         .getPerhaps("BeheadingBonus", 5);
      builder.popPrefix();
   }

   public ForbiddenAxe() {
      super(EnigmaticMaterials.FORBIDDENAXE, 6, -2.4F, ItemBaseTool.getDefaultProperties().m_41499_(2000).m_41497_(Rarity.EPIC).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.forbiddenAxe1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.forbiddenAxe2", ChatFormatting.GOLD, beheadingBonus.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.forbiddenAxe3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      int looting = 0;
      if (worldIn != null) {
         looting = EnchantmentHelper.m_44843_(Enchantments.f_44982_, stack);
      }

      if (Minecraft.m_91087_().f_91074_ != null) {
         ICuriosItemHandler handler = (ICuriosItemHandler)CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.m_91087_().f_91074_).orElse(null);
         if (handler != null) {
            looting += handler.getLootingLevel(DamageSource.f_19318_, Minecraft.m_91087_().f_91074_, looting);
         }
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.addLocalizedString(
         list,
         "tooltip.enigmaticlegacy.forbiddenAxeBeheadingChance",
         ChatFormatting.GOLD,
         beheadingBase.getValue().asPercentage() + beheadingBonus.getValue().asPercentage() * looting + "%"
      );
   }

   public boolean m_8096_(BlockState blockIn) {
      return false;
   }
}
