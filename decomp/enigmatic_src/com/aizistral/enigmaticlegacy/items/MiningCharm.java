package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class MiningCharm extends ItemBaseCurio {
   public static Omniconfig.PerhapsParameter breakSpeedBonus;
   public static Omniconfig.DoubleParameter reachDistanceBonus;
   public static Omniconfig.BooleanParameter enableNightVision;
   public final int nightVisionDuration = 310;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("MiningCharm");
      breakSpeedBonus = builder.comment("Mining speed boost granted by Charm of Treasure Hunter. Defined as percentage.")
         .max(1000.0)
         .getPerhaps("BreakSpeed", 30);
      reachDistanceBonus = builder.comment("Additional block reach granted by Charm of Treasure Hunter.").max(16.0).getDouble("ReachDistance", 2.15);
      enableNightVision = builder.comment("Whether Night Vision ability of Charm of Treasure Hunter should be enabled.").getBoolean("EnableNightVision", true);
      builder.popPrefix();
   }

   public MiningCharm() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.RARE));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      Component mode = Component.m_237115_("tooltip.enigmaticlegacy.enabled");
      if (ItemNBTHelper.verifyExistance(stack, "nightVisionEnabled") && !ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
         mode = Component.m_237115_("tooltip.enigmaticlegacy.disabled");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharm1", ChatFormatting.GOLD, breakSpeedBonus.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharm2", ChatFormatting.GOLD, 1);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharm3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharm4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharm5");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.miningCharmNightVision", null, mode.getString());
   }

   public void removeNightVisionEffect(Player player, int duration) {
      if (player.m_21124_(MobEffects.f_19611_) != null) {
         MobEffectInstance effect = player.m_21124_(MobEffects.f_19611_);
         if (effect.m_19557_() <= duration - 1) {
            player.m_21195_(MobEffects.f_19611_);
         }
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player
         && !context.entity().f_19853_.f_46443_
         && SuperpositionHandler.hasCurio(player, EnigmaticItems.MINING_CHARM)
         && ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
         if (enableNightVision.getValue()) {
            if (player.m_20186_() < 50.0
               && !player.f_19853_.m_46472_().equals(Level.f_46429_)
               && !player.f_19853_.m_46472_().equals(Level.f_46430_)
               && !player.m_204029_(FluidTags.f_13131_)
               && !player.f_19853_.m_46861_(player.m_20183_())) {
               EnigmaticEventHandler.isApplyingNightVision = true;
               player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 310, 0, true, false));
               EnigmaticEventHandler.isApplyingNightVision = false;
            }
         } else {
            ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", false);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand handIn) {
      ItemStack stack = player.m_21120_(handIn);
      if (ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
         ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", false);
         world.m_5594_(
            null,
            player.m_20183_(),
            EnigmaticSounds.CHARGED_OFF,
            SoundSource.PLAYERS,
            (float)(0.8F + Math.random() * 0.2F),
            (float)(0.8F + Math.random() * 0.2F)
         );
      } else if (enableNightVision.getValue()) {
         ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", true);
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
   }

   @Override
   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
      if (context.entity() instanceof Player player) {
         this.removeNightVisionEffect(player, 310);
      }
   }

   @Override
   public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
      return false;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
      atts.put(
         (Attribute)ForgeMod.REACH_DISTANCE.get(),
         new AttributeModifier(UUID.fromString("08c3c83d-7137-4b42-880f-b146bcb64d2e"), "Reach bonus", reachDistanceBonus.getValue(), Operation.ADDITION)
      );
      return atts;
   }

   public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack curio) {
      return super.getFortuneLevel(slotContext, lootContext, curio) + 1;
   }
}
