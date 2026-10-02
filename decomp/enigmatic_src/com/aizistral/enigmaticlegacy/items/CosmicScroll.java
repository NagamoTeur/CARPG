package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.IHidden;
import com.aizistral.enigmaticlegacy.handlers.DevotedBelieversHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.packets.clients.PacketCosmicRevive;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class CosmicScroll extends ItemBaseCurio implements IHidden {
   private static final ResourceLocation ADVANCEMENT = new ResourceLocation("enigmaticlegacy", "main/cosmic_scroll");
   public static Omniconfig.PerhapsParameter unchosenDamageBonus;
   public static Omniconfig.PerhapsParameter unchosenKnockbackBonus;
   public static Omniconfig.PerhapsParameter etheriumShieldThreshold;
   public static Omniconfig.IntParameter deathProtectionCooldown;

   @SubscribeConfig
   public static void handleConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("CosmicScroll");
      etheriumShieldThreshold = builder.comment(
            "Alternative Etherium Shield health requirement for those who bear The Architect's Favor. Defined as percentage."
         )
         .max(100.0)
         .getPerhaps("EtheriumShieldThreshold", 80);
      unchosenDamageBonus = builder.comment("Attack damage bonus of The Architect's Favor against non-chosen players.").getPerhaps("UnchosenDamageBonus", 100);
      unchosenKnockbackBonus = builder.comment("Knockback bonus of The Architect's Favor against non-chosen players.")
         .getPerhaps("UnchosenKnockbackBonus", 100);
      deathProtectionCooldown = builder.comment("Cooldown of death protection ability of The Architect's Favor. Measured in seconds.")
         .getInt("DeathProtectionCooldown", 600);
      builder.popPrefix();
   }

   public CosmicScroll() {
      super(getDefaultProperties().m_41491_(null).m_41497_(Rarity.EPIC));
      MinecraftForge.EVENT_BUS.register(this);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll4", ChatFormatting.GOLD, etheriumShieldThreshold + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll11", ChatFormatting.GOLD, deathProtectionCooldown);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.cosmicScroll12", ChatFormatting.GOLD, unchosenDamageBonus + "%", unchosenKnockbackBonus + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll13");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScroll14");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScrollLore1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScrollLore2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (this.hasCooldown(stack)) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.cosmicScrollCooldown", ChatFormatting.GOLD, this.getCooldown(stack) / 20);
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateBlessedOnesOnly(list);
   }

   public boolean hasCooldown(ItemStack scroll) {
      return this.getCooldown(scroll) > 0;
   }

   public int getCooldown(ItemStack scroll) {
      return scroll.m_150930_(this) ? ItemNBTHelper.getInt(scroll, "ReviveCooldown", 0) : 0;
   }

   public void setCooldown(ItemStack scroll, int cooldown) {
      if (scroll.m_150930_(this)) {
         ItemNBTHelper.setInt(scroll, "ReviveCooldown", cooldown);
      }
   }

   @Override
   public boolean canEquip(SlotContext context, ItemStack stack) {
      return super.canEquip(context, stack) && context.entity() instanceof Player player && SuperpositionHandler.isTheBlessedOne(player);
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      tooltips.clear();
      return tooltips;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      attributes.put(
         Attributes.f_22286_,
         new AttributeModifier(UUID.fromString("290d5f76-87aa-4f7c-9c1a-9aef2fe25d05"), "enigmaticlegacy:luck_bonus", 1.0, Operation.ADDITION)
      );
      CuriosApi.getCuriosHelper().addSlotModifier(attributes, "scroll", UUID.fromString("fb4dfa90-1df4-4e26-86a9-481dcdd830c5"), 1.0, Operation.ADDITION);
      return attributes;
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (!context.entity().f_19853_.f_46443_ && this.hasCooldown(stack)) {
         this.setCooldown(stack, this.getCooldown(stack) - 1);
      }
   }

   public void m_6883_(ItemStack stack, Level level, Entity pEntity, int pSlotId, boolean pIsSelected) {
      if (!level.f_46443_ && pEntity instanceof LivingEntity entity) {
         ItemStack scrollStack = SuperpositionHandler.getCurioStack(entity, this);
         if (stack != scrollStack && this.hasCooldown(stack)) {
            this.setCooldown(stack, this.getCooldown(stack) - 1);
         }
      }
   }

   @Override
   public void onEquip(SlotContext context, ItemStack prevStack, ItemStack stack) {
      if (context.entity() instanceof ServerPlayer player
         && DevotedBelieversHandler.isDevotedBeliever(player)
         && !SuperpositionHandler.hasAdvancement(player, ADVANCEMENT)) {
         SuperpositionHandler.grantAdvancement(player, ADVANCEMENT);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onTooltip(ItemTooltipEvent event) {
      if (event.getItemStack().m_150930_(this)) {
         if (EnigmaticLegacy.PROXY.getClientPlayer() != null && SuperpositionHandler.isTheBlessedOne(EnigmaticLegacy.PROXY.getClientPlayer())) {
            return;
         }

         List<Component> tooltip = event.getToolTip();

         for (int i = 0; i < tooltip.size(); i++) {
            Component component = tooltip.get(i);
            String text = component.getString();
            tooltip.set(i, Component.m_237113_(SuperpositionHandler.obscureString(text)).m_6270_(component.m_7383_()));
         }
      }
   }

   @SubscribeEvent
   public void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player && !event.getEntity().f_19853_.f_46443_) {
         Player player = (Player)event.getEntity();
         if (SuperpositionHandler.isTheBlessedOne(player)) {
            ItemStack scroll = SuperpositionHandler.getCurioStack(player, this);
            if (scroll != null && !this.hasCooldown(scroll)) {
               event.setCanceled(true);
               player.m_21153_(player.m_21233_() / 2.0F);
               player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 1200, 2));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 1200, 1));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 1200, 0));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 1200, 1));
               this.setCooldown(scroll, deathProtectionCooldown.getValue() * 20);
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                     new PacketCosmicRevive(player.m_19879_(), 0)
                  );
            }
         }
      }
   }

   @SubscribeEvent
   public void onEntityHurt(LivingHurtEvent event) {
      if (!(event.getAmount() >= Float.MAX_VALUE)) {
         if (event.getSource().m_7640_() instanceof Player && "player".equals(event.getSource().f_19326_)) {
            Player player = (Player)event.getSource().m_7640_();
            float bonusDamage = 0.0F;
            if (event.getEntity() instanceof Player victimPlayer
               && SuperpositionHandler.hasArchitectsFavor(player)
               && !SuperpositionHandler.isTheBlessedOne(victimPlayer)) {
               bonusDamage += event.getAmount() * unchosenDamageBonus.getValue().asModifier(false);
               event.getEntity().m_20254_(4);
            }

            event.setAmount(event.getAmount() + bonusDamage);
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onPlayerJoin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player
         && SuperpositionHandler.isTheBlessedOne(player)
         && !SuperpositionHandler.hasAdvancement(player, new ResourceLocation("enigmaticlegacy", "book/accessories/cosmic_scroll_obscure"))) {
         SuperpositionHandler.grantAdvancement(player, new ResourceLocation("enigmaticlegacy", "book/accessories/cosmic_scroll_obscure"));
      }
   }
}
