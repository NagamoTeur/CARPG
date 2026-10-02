package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Insignia extends ItemBaseCurio {
   public Insignia() {
      super(getDefaultProperties().m_41497_(Rarity.UNCOMMON).m_41487_(1));
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.insignia1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.insignia2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.insignia3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      this.getCustomName(stack).ifPresent(component -> ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.insigniaName", null, component));
      ItemLoreHelper.addLocalizedString(
         list, "tooltip.enigmaticlegacy.insigniaTagDisplay" + (ItemNBTHelper.getBoolean(stack, "tagDisplayEnabled", true) ? "Enabled" : "Disabled")
      );
   }

   public Optional<Component> getCustomName(ItemStack stack) {
      CompoundTag tag = stack.m_41737_("display");
      if (tag != null && tag.m_128425_("Name", 8)) {
         try {
            Component component = Serializer.m_130701_(tag.m_128461_("Name"));
            if (component != null) {
               return Optional.of(component);
            }

            tag.m_128473_("Name");
         } catch (Exception var4) {
            tag.m_128473_("Name");
         }
      }

      return Optional.empty();
   }

   public boolean canSeeTrueName(Player player) {
      return player.m_7500_() || SuperpositionHandler.hasCurio(player, EnigmaticItems.ENIGMATIC_EYE);
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      CuriosApi.getCuriosHelper().addSlotModifier(attributes, "charm", UUID.fromString("4b2533eb-26ca-4470-8805-2faa90735fde"), 1.0, Operation.ADDITION);
      return attributes;
   }

   @Override
   public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
      return false;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand handIn) {
      if (player.m_6047_()) {
         ItemStack stack = player.m_21120_(handIn);
         if (ItemNBTHelper.getBoolean(stack, "tagDisplayEnabled", true)) {
            ItemNBTHelper.setBoolean(stack, "tagDisplayEnabled", false);
            world.m_5594_(
               null,
               player.m_20183_(),
               EnigmaticSounds.CHARGED_OFF,
               SoundSource.PLAYERS,
               (float)(0.8F + Math.random() * 0.2F),
               (float)(0.8F + Math.random() * 0.2F)
            );
         } else {
            ItemNBTHelper.setBoolean(stack, "tagDisplayEnabled", true);
            world.m_5594_(
               null,
               player.m_20183_(),
               EnigmaticSounds.CHARGED_ON,
               SoundSource.PLAYERS,
               (float)(0.8F + Math.random() * 0.2F),
               (float)(0.8F + Math.random() * 0.2F)
            );
         }

         player.m_6674_(handIn);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return super.m_7203_(world, player, handIn);
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_();
   }
}
