package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.api.PatchouliAPI;

public class TheAcknowledgment extends ItemBase implements Vanishable {
   private static final ItemStack UNSUSPECTING_DIAMOND_SWORD = new ItemStack(Items.f_42388_);
   private static final ResourceLocation BOOK_ID = new ResourceLocation("enigmaticlegacy", "the_acknowledgment");
   private final Multimap<Attribute, AttributeModifier> attributes;
   private boolean allowAllEnchantments = false;

   protected TheAcknowledgment(Properties props, String name, double attackDamage, double attackSpeed) {
      super(props);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(Item.f_41374_, "Weapon modifier", attackDamage, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(Item.f_41375_, "Weapon modifier", attackSpeed, Operation.ADDITION));
      this.attributes = builder.build();
   }

   public TheAcknowledgment() {
      this(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1), BOOK_ID.m_135815_(), 3.5, -2.1);
   }

   public static boolean isOpen() {
      return BOOK_ID.equals(PatchouliAPI.get().getOpenBookGui());
   }

   public static Component getEdition() {
      return PatchouliAPI.get().getSubtitle(BOOK_ID);
   }

   public static Component getTitle(ItemStack stack) {
      Component title = stack.m_41786_();
      String akashicTomeNBT = "akashictome:displayName";
      if (stack.m_41782_() && stack.m_41783_().m_128441_(akashicTomeNBT)) {
         title = Component.m_237113_(stack.m_41783_().m_128461_(akashicTomeNBT));
      }

      return title;
   }

   protected void setAllowAllEnchantments(boolean allowAllEnchantments) {
      this.allowAllEnchantments = allowAllEnchantments;
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      target.m_20254_(4);
      return super.m_7579_(stack, target, attacker);
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 24;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
      return slot.equals(EquipmentSlot.MAINHAND) ? this.attributes : super.getAttributeModifiers(slot, stack);
   }

   @Nonnull
   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (player instanceof ServerPlayer playerMP) {
         PatchouliAPI.get().openBookGUI(playerMP, BOOK_ID);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      if (!this.allowAllEnchantments) {
         Map<Enchantment, Integer> list = EnchantmentHelper.m_44831_(book);
         return list.size() == 1 && list.containsKey(Enchantments.f_44979_) ? true : super.isBookEnchantable(stack, book);
      } else {
         return !EnchantmentHelper.m_44831_(book).keySet().contains(Enchantments.f_44986_)
            && Items.f_42388_.isBookEnchantable(UNSUSPECTING_DIAMOND_SWORD, book);
      }
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return this.allowAllEnchantments
         ? enchantment != Enchantments.f_44986_ && Items.f_42388_.canApplyAtEnchantingTable(UNSUSPECTING_DIAMOND_SWORD, enchantment)
         : enchantment == Enchantments.f_44979_ || super.canApplyAtEnchantingTable(stack, enchantment);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theAcknowledgment1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theAcknowledgment2");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }
}
