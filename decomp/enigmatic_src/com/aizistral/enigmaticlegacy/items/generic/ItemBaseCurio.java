package com.aizistral.enigmaticlegacy.items.generic;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.Map;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public abstract class ItemBaseCurio extends ItemBase implements ICurioItem, Vanishable, Wearable {
   public ItemBaseCurio() {
      this(getDefaultProperties());
   }

   public ItemBaseCurio(Properties props) {
      super(props);
   }

   public void onEquip(SlotContext context, ItemStack prevStack, ItemStack stack) {
   }

   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
   }

   public void curioTick(SlotContext context, ItemStack stack) {
   }

   @Override
   public void m_7836_(ItemStack stack, Level worldIn, Player playerIn) {
   }

   public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
      return true;
   }

   public boolean canEquip(SlotContext context, ItemStack stack) {
      return !SuperpositionHandler.hasCurio(context.entity(), this);
   }

   public boolean canUnequip(SlotContext context, ItemStack stack) {
      return true;
   }

   public DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
      return DropRule.DEFAULT;
   }

   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.m_41491_(EnigmaticLegacy.MAIN_TAB);
      props.m_41487_(1);
      props.m_41497_(Rarity.COMMON);
      return props;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      Map<Enchantment, Integer> list = EnchantmentHelper.m_44831_(book);
      return list.size() == 1 && list.containsKey(Enchantments.f_44975_) ? true : super.isBookEnchantable(stack, book);
   }
}
