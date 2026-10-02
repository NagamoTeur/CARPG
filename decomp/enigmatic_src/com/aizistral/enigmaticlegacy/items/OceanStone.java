package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class OceanStone extends ItemSpellstoneCurio implements ISpellstone {
   public static Omniconfig.IntParameter spellstoneCooldown;
   public static Omniconfig.PerhapsParameter swimminSpeedBoost;
   public static Omniconfig.PerhapsParameter underwaterCreaturesResistance;
   public static Omniconfig.DoubleParameter xpCostModifier;
   public static Omniconfig.BooleanParameter preventOxygenBarRender;
   public final int xpCostBase = 150;
   public final int nightVisionDuration = 310;

   @SubscribeConfig(
      receiveClient = true
   )
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("OceanStone");
      if (builder.config.getSidedType() != Configuration.SidedConfigType.CLIENT) {
         spellstoneCooldown = builder.comment("Active ability cooldown for Will of the Ocean. Measured in ticks. 20 ticks equal to 1 second.")
            .getInt("Cooldown", 600);
         swimminSpeedBoost = builder.comment("Swimming speed boost provided by Will of the Ocean. Defined as percentage.")
            .max(1000.0)
            .getPerhaps("SwimBoost", 200);
         underwaterCreaturesResistance = builder.comment("Damage resistance against underwater creatures provided by Will of the Ocean. Defined as percentage.")
            .max(100.0)
            .getPerhaps("UnderwaterCreaturesResistance", 40);
         xpCostModifier = builder.comment("Multiplier for experience consumption by active ability of Will of the Ocean.")
            .max(1000.0)
            .getDouble("XPCostModifier", 1.0);
      } else {
         builder.popPrefix();
         preventOxygenBarRender = builder.comment(
               "Whether or not oxygen bar should pe prevented from rendering if Will of the Ocean or Pearl of the Void is equipped."
            )
            .getBoolean("SuppressUnneccessaryOxygenRender", true);
      }

      builder.popPrefix();
   }

   public OceanStone() {
      super(ItemSpellstoneCurio.getDefaultProperties().m_41497_(Rarity.RARE));
      this.immunityList.add(DamageSource.f_19312_.f_19326_);
      this.resistanceList.put(DamageSource.f_19305_.f_19326_, () -> 2.0F);
      this.resistanceList.put(DamageSource.f_19307_.f_19326_, () -> 2.0F);
      this.resistanceList.put(DamageSource.f_19308_.f_19326_, () -> 2.0F);
      this.resistanceList.put(DamageSource.f_19309_.f_19326_, () -> 2.0F);
      this.resistanceList.put("fireball", () -> 2.0F);
   }

   @Override
   public int getCooldown(Player player) {
      return player != null && reducedCooldowns.test(player) ? 300 : spellstoneCooldown.getValue();
   }

   private Multimap<Attribute, AttributeModifier> createAttributeMap(Player player) {
      Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();
      attributesDefault.put(
         (Attribute)ForgeMod.ENTITY_GRAVITY.get(),
         new AttributeModifier(
            UUID.fromString("79e1cc36-fb4e-4c7d-802b-583b8d90648a"),
            "enigmaticlegacy:gravity_bonus",
            player.m_204029_(FluidTags.f_13131_) ? -1.0 : 0.0,
            Operation.MULTIPLY_TOTAL
         )
      );
      return attributesDefault;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.oceanStoneCooldown", ChatFormatting.GOLD, (float)this.getCooldown(Minecraft.m_91087_().f_91074_) / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone4");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.oceanStone5", ChatFormatting.GOLD, underwaterCreaturesResistance.getValue().asPercentage() + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.oceanStone11");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      try {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.currentKeybind",
            ChatFormatting.LIGHT_PURPLE,
            ((Component)KeyMapping.m_90842_("key.spellstoneAbility").get()).getString().toUpperCase()
         );
      } catch (NullPointerException var6) {
      }
   }

   @Override
   public void triggerActiveAbility(Level world, ServerPlayer player, ItemStack stack) {
      if (!SuperpositionHandler.hasSpellstoneCooldown(player)) {
         if (!player.f_19853_.m_46472_().m_135782_().toString().equals("minecraft:the_end")
            && !player.f_19853_.m_46472_().m_135782_().toString().equals("minecraft:the_nether")
            && !world.m_6106_().m_6534_()) {
            boolean paybackReceived = false;
            if (ExperienceHelper.getPlayerXP(player) >= 150 * 2) {
               ExperienceHelper.drainPlayerXP(player, (int)((150.0 + Math.random() * 150.0) * xpCostModifier.getValue()));
               paybackReceived = true;
            }

            if (paybackReceived) {
               if (world instanceof ServerLevel serverworld) {
                  int thunderstormTime = (int)(10000.0 + Math.random() * 20000.0);
                  serverworld.m_8606_(0, thunderstormTime, true, true);
               }

               world.m_5594_(null, player.m_20183_(), SoundEvents.f_12090_, SoundSource.NEUTRAL, 2.0F, (float)(0.7F + Math.random() * 0.3));
               SuperpositionHandler.setSpellstoneCooldown(player, this.getCooldown(player));
            }
         }
      }
   }

   @Override
   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
      if (context.entity() instanceof ServerPlayer player) {
         EnigmaticItems.MINING_CHARM.removeNightVisionEffect(player, 310);
         player.m_21204_().m_22161_(this.createAttributeMap(player));
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof ServerPlayer player && SuperpositionHandler.hasCurio(player, EnigmaticItems.OCEAN_STONE)) {
         if (player.m_204029_(FluidTags.f_13131_)) {
            EnigmaticEventHandler.isApplyingNightVision = true;
            player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 310, 0, true, false));
            EnigmaticEventHandler.isApplyingNightVision = false;
            player.m_20301_(300);
         } else {
            EnigmaticItems.MINING_CHARM.removeNightVisionEffect(player, 310);
         }

         player.m_21204_().m_22178_(this.createAttributeMap(player));
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
      atts.put(
         (Attribute)ForgeMod.SWIM_SPEED.get(),
         new AttributeModifier(
            UUID.fromString("13faf191-bf38-4654-b369-cc1f4f1143bf"),
            "Swim speed bonus",
            swimminSpeedBoost.getValue().asMultiplier(false),
            Operation.MULTIPLY_BASE
         )
      );
      return atts;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      tooltips.clear();
      return tooltips;
   }
}
