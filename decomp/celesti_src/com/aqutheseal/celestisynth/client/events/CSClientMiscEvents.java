package com.aqutheseal.celestisynth.client.events;

import com.aqutheseal.celestisynth.api.item.CSArmorItem;
import com.aqutheseal.celestisynth.api.item.CSArmorProperties;
import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.api.mixin.PlayerMixinSupport;
import com.aqutheseal.celestisynth.common.item.weapons.AquafloraItem;
import com.aqutheseal.celestisynth.common.registry.CSRarityTypes;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.client.event.RenderTooltipEvent.GatherComponents;
import net.minecraftforge.client.event.ScreenEvent.Opening;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.ViewportEvent.ComputeFov;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class CSClientMiscEvents {
   @SubscribeEvent
   public static void onScreenRender(Opening event) {
      if (Minecraft.m_91087_().f_91074_ != null) {
         ItemStack itemR = Minecraft.m_91087_().f_91074_.m_21205_();
         ItemStack itemL = Minecraft.m_91087_().f_91074_.m_21206_();
         if (itemR.m_41720_() instanceof CSWeapon && itemR.m_41737_("csController") != null && itemR.m_41737_("csController").m_128471_("cs.hasAnimationBegun")
            )
          {
            event.setCanceled(true);
         }

         if (itemL.m_41720_() instanceof CSWeapon && itemL.m_41737_("csController") != null && itemL.m_41737_("csController").m_128471_("cs.hasAnimationBegun")
            )
          {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onTooltipColor(Color event) {
      int argb = -16777216;
      if (event.getItemStack().m_41791_() == CSRarityTypes.CELESTIAL) {
         event.setBackgroundStart(argb + 925505);
         event.setBackgroundEnd(argb + 920361);
         event.setBorderStart(argb + 15650882);
         event.setBorderEnd(argb + 12538154);
      }
   }

   @SubscribeEvent
   public static void onCameraSetup(ComputeCameraAngles event) {
      Minecraft mc = Minecraft.m_91087_();
      Player player = mc.f_91074_;
      if (player != null) {
         PlayerMixinSupport supportedPlayer = (PlayerMixinSupport)player;
         if (!mc.f_91066_.m_92176_().m_90612_()) {
            checkAndSetAngle(event, player.m_21206_());
            checkAndSetAngle(event, player.m_21205_());
         }

         float delta = Minecraft.m_91087_().m_91296_();
         float ticksExistedDelta = (float)player.f_19797_ + delta;
         float intensity = supportedPlayer.getScreenShakeIntensity();
         float duration = (float)supportedPlayer.getScreenShakeDuration();
         if (duration > 0.0F && !Minecraft.m_91087_().m_91104_() && player.f_19853_.m_5776_()) {
            event.setPitch((float)((double)event.getPitch() + (double)intensity * Math.cos((double)(ticksExistedDelta * 3.0F + 2.0F)) * 25.0));
            event.setYaw((float)((double)event.getYaw() + (double)intensity * Math.cos((double)(ticksExistedDelta * 5.0F + 1.0F)) * 25.0));
            event.setRoll((float)((double)event.getRoll() + (double)intensity * Math.cos((double)(ticksExistedDelta * 4.0F)) * 25.0));
         }
      }
   }

   @SubscribeEvent
   public static void onCameraZoom(ComputeFov event) {
      Minecraft mc = Minecraft.m_91087_();
      if (!mc.f_91066_.m_92176_().m_90612_()) {
         checkAndSetFOV(event, mc.f_91074_.m_21206_());
         checkAndSetFOV(event, mc.f_91074_.m_21205_());
      }
   }

   @SubscribeEvent
   public static void onToolTipComponent(GatherComponents event) {
      ItemStack stack = event.getItemStack();
      String name = ForgeRegistries.ITEMS.getKey(stack.m_41720_()).m_135815_();
      if (!stack.m_41619_() && stack.m_41720_() instanceof CSArmorItem armor) {
         List<Either<FormattedText, TooltipComponent>> elements = event.getTooltipElements();
         List<Either<FormattedText, TooltipComponent>> elementsToAdd = new ObjectArrayList();
         CSArmorProperties properties = armor.getArmorProperties();
         if (properties.isStunImmune()) {
            elementsToAdd.add(Either.left(Component.m_237115_("item.celestisynth.armor_stun_immune").m_130940_(ChatFormatting.LIGHT_PURPLE)));
         }

         if (properties.getDamageReflectionPercent() > 0.0) {
            elementsToAdd.add(
               Either.left(
                  Component.m_237110_("item.celestisynth.armor_damage_reflection_percent", new Object[]{properties.getDamageReflectionPercent()})
                     .m_130940_(ChatFormatting.LIGHT_PURPLE)
               )
            );
         }

         if (properties.getDamageReflectionAddition() > 0.0) {
            elementsToAdd.add(
               Either.left(
                  Component.m_237110_("item.celestisynth.armor_damage_reflection_addition", new Object[]{properties.getDamageReflectionAddition()})
                     .m_130940_(ChatFormatting.LIGHT_PURPLE)
               )
            );
         }

         if (properties.getMobEffectDurationMultiplier() > 0.0) {
            elementsToAdd.add(
               Either.left(
                  Component.m_237110_("item.celestisynth.armor_mob_effect_duration_multiplier", new Object[]{properties.getMobEffectDurationMultiplier()})
                     .m_130940_(ChatFormatting.LIGHT_PURPLE)
               )
            );
         }

         if (properties.getSkillDamageMultiplier() > 0.0) {
            elementsToAdd.add(
               Either.left(
                  Component.m_237110_("item.celestisynth.armor_skill_damage_multiplier", new Object[]{properties.getSkillDamageMultiplier()})
                     .m_130940_(ChatFormatting.LIGHT_PURPLE)
               )
            );
         }

         ListIterator<Either<FormattedText, TooltipComponent>> iterator = elementsToAdd.listIterator(elementsToAdd.size());

         while (iterator.hasPrevious()) {
            elements.add(1, iterator.previous());
         }
      }

      if (!stack.m_41619_() && stack.m_41720_() instanceof CSWeapon cs) {
         List<Either<FormattedText, TooltipComponent>> elementsx = event.getTooltipElements();
         List<Either<FormattedText, TooltipComponent>> elementsToAddx = new ObjectArrayList();
         elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth.celestial_tier").m_130940_(ChatFormatting.AQUA).m_130940_(ChatFormatting.BOLD)));
         elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth.shift_notice").m_130940_(ChatFormatting.GREEN)));
         addBorders(elementsToAddx);
         if (cs.hasPassive()) {
            elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth.passive_notice").m_130940_(ChatFormatting.GOLD)));
            if (Screen.m_96638_() || Screen.m_96637_()) {
               elementsToAddx.add(Either.left(Component.m_237113_(" ")));
            }

            for (int i = 1; i < cs.getPassiveAmount() + 1; i++) {
               elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth." + name + ".passive_" + i).m_130940_(ChatFormatting.LIGHT_PURPLE)));
               if (Screen.m_96638_() || Screen.m_96637_()) {
                  elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth." + name + ".passive_desc_" + i).m_130940_(ChatFormatting.GRAY)));
                  elementsToAddx.add(Either.left(Component.m_237113_(" ")));
               }
            }

            addBorders(elementsToAddx);
         }

         elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth.skill_notice").m_130940_(ChatFormatting.GOLD)));
         if (Screen.m_96638_() || Screen.m_96637_()) {
            elementsToAddx.add(Either.left(Component.m_237113_(" ")));
         }

         for (int ix = 1; ix < cs.getSkillsAmount() + 1; ix++) {
            elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth." + name + ".skill_" + ix).m_130940_(ChatFormatting.LIGHT_PURPLE)));
            if (Screen.m_96638_() || Screen.m_96637_()) {
               elementsToAddx.add(Either.left(Component.m_237115_("item.celestisynth." + name + ".condition_" + ix).m_130940_(ChatFormatting.RED)));
               elementsToAddx.add(
                  Either.left(Component.m_237115_("item.celestisynth." + name + ".desc_" + ix).m_130940_(ChatFormatting.GRAY).m_130940_(ChatFormatting.ITALIC))
               );
               elementsToAddx.add(Either.left(Component.m_237113_(" ")));
            }
         }

         addBorders(elementsToAddx);
         ListIterator<Either<FormattedText, TooltipComponent>> iterator = elementsToAddx.listIterator(elementsToAddx.size());

         while (iterator.hasPrevious()) {
            elementsx.add(1, iterator.previous());
         }
      }
   }

   private static void checkAndSetAngle(ComputeCameraAngles event, ItemStack itemStack) {
      if (itemStack.m_41720_() instanceof AquafloraItem) {
         CompoundTag tagElement = itemStack.m_41737_("csController");
         if (tagElement != null && tagElement.m_128471_("cs.hasAnimationBegun") && tagElement.m_128471_("cs.atkOngoing")) {
            event.setPitch(90.0F);
         }
      }
   }

   private static void checkAndSetFOV(ComputeFov event, ItemStack itemStack) {
      CompoundTag tagElement = itemStack.m_41737_("csController");
      if (tagElement != null
         && itemStack.m_41720_() instanceof AquafloraItem aq
         && tagElement.m_128471_("cs.hasAnimationBegun")
         && tagElement.m_128471_("cs.atkOngoing")) {
         event.setFOV(140.0);
      }
   }

   private static void addBorders(List<Either<FormattedText, TooltipComponent>> list) {
      boolean shouldExpand = Screen.m_96638_() || Screen.m_96637_();
      MutableComponent border = Component.m_237113_(shouldExpand ? "[ -------------------- o -------------------- ]" : "[ ------------ o ------------ ]");
      MutableComponent edge = Component.m_237113_(" ");
      list.add(Either.left(edge));
      list.add(Either.left(border.m_130940_(shouldExpand ? ChatFormatting.GREEN : ChatFormatting.GRAY)));
      list.add(Either.left(edge));
   }
}
