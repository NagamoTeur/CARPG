package com.rolfmao.upgradednetherite_items.handlers;

import com.rolfmao.upgradedcore.compat.ExternalMods;
import com.rolfmao.upgradednetherite.handlers.ArmorEventHandler;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite_creative.config.UpgradedNetheriteCreativeConfig;
import com.rolfmao.upgradednetherite_creative.utils.CreativeUtil;
import com.rolfmao.upgradednetherite_items.config.UpgradedNetheriteItemsConfig;
import com.rolfmao.upgradednetherite_items.init.ModItems;
import com.rolfmao.upgradednetherite_items.init.UpgradedNetheriteEffects;
import com.rolfmao.upgradednetherite_items.items.NetheriteTotemBase;
import com.rolfmao.upgradednetherite_items.packets.NetheriteTotemEffectPacket;
import com.rolfmao.upgradednetherite_ultimate.utils.UltimateItemDataUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent.Added;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(
   modid = "upgradednetherite_items",
   bus = Bus.FORGE
)
public class TotemEventHandler {
   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onLivingAttackEvent(LivingHurtEvent event) {
      if (event.getEntity() instanceof Player) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         List<ItemStack> listTotem = checkNetheriteTotem(player);
         if (event.getSource().m_7639_() != null && !listTotem.isEmpty()) {
            Entity attacker = event.getSource().m_7639_();
            float bonusreduce = 0.0F;
            if (UpgradedNetheriteItemsConfig.EnableReduceDamageGoldTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && (attacker instanceof Piglin || attacker instanceof PiglinBrute)) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageGoldTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageFireTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && (attacker instanceof Blaze || attacker instanceof MagmaCube)) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageFireTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageEnderTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && (attacker instanceof EnderMan || attacker instanceof Endermite || attacker instanceof Shulker)) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageEnderTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageWaterTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && (attacker instanceof Mob && ((Mob)attacker).m_6336_() == MobType.f_21644_ || attacker instanceof Drowned)) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageWaterTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageWitherTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && attacker instanceof WitherSkeleton) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageWitherTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamagePoisonTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && attacker instanceof Mob
               && ((Mob)attacker).m_6336_() == MobType.f_21642_) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamagePoisonTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamagePhantomTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && attacker instanceof Phantom) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamagePhantomTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageFeatherTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && attacker instanceof Mob
               && ((Mob)attacker).m_6162_()) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageFeatherTotem;
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageCorruptTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && CorruptUtil.intWearingCorrupt(player, true) > 0) {
               bonusreduce += (float)(UpgradedNetheriteItemsConfig.ReduceDamageCorruptTotem * CorruptUtil.intWearingCorrupt(player, true));
            }

            if (UpgradedNetheriteItemsConfig.EnableReduceDamageEchoTotem
               && (
                  containNetheriteTotem(listTotem, (Item)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get())
                     || containNetheriteTotem(listTotem, (Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
               )
               && attacker instanceof Warden) {
               bonusreduce += (float)UpgradedNetheriteItemsConfig.ReduceDamageEchoTotem;
            }

            event.setAmount(event.getAmount() - event.getAmount() * Math.min(1.0F, bonusreduce / 100.0F));
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public static void onLivingDeath(LivingDeathEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)event.getEntity();
            DamageSource damageSource = event.getSource();
            if (checkNetheriteTotemDeathProtection(damageSource, player)) {
               event.setCanceled(true);
               player.m_5496_(SoundEvents.f_12322_, 1.0F, player.m_6100_());
            }
         }
      }
   }

   public static boolean checkNetheriteTotemDeathProtection(DamageSource damageSource, ServerPlayer player) {
      if (!damageSource.m_19378_() || !ExternalMods.UPGRADEDNETHERITE.isLoaded() || damageSource == DamageSource.f_19317_ && EnderUtil.isVoidYLevel(player)) {
         ItemStack itemstack = null;
         ItemStack itemstackcopy = null;

         for (InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack1 = player.m_21120_(interactionhand);
            if (itemstack1.m_41720_() instanceof NetheriteTotemBase
               && ((NetheriteTotemBase)itemstack1.m_41720_()).isEnabled()
               && (
                  damageSource != DamageSource.f_19317_
                     || ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded()
                        && itemstack1.m_150930_((Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())
                        && !UltimateItemDataUtil.getUltimeriteDisableEnder(itemstack1)
                     || ExternalMods.UPGRADEDNETHERITE.isLoaded() && itemstack1.m_150930_((Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get())
               )) {
               itemstack = itemstack1;
               itemstackcopy = itemstack1.m_41777_();
               break;
            }
         }

         if (itemstack == null && ExternalMods.CURIOS.isLoaded()) {
            ItemStack curiostotem = ItemStack.f_41583_;
            if (damageSource != DamageSource.f_19317_) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded() && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded() && damageSource != DamageSource.f_19317_ && curiostotem.m_41619_()) {
               curiostotem = CuriosApi.getCuriosHelper()
                  .findEquippedCurio((Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get(), player)
                  .<ItemStack>map(ImmutableTriple::getRight)
                  .orElse(ItemStack.f_41583_);
            }

            if (!curiostotem.m_41619_()) {
               itemstack = curiostotem;
            }

            itemstackcopy = curiostotem.m_41777_();
         }

         if (itemstack != null) {
            if (damageSource == DamageSource.f_19317_ && !ArmorEventHandler.enderSaveVoid(player)) {
               return false;
            }

            itemstack.m_41774_(1);
            player.m_6278_(Stats.f_12982_.m_12902_(Items.f_42747_), 1);
            player.m_21153_(1.0F);
            player.m_21219_();
            player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 900, 1));
            player.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 100, 1));
            player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 800, 0));
            player.m_7292_(new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get(), 400, 1));
            player.m_7292_(new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_STRENGTH.get(), 400, 0));
            PacketHandler.sendToPlayer(new NetheriteTotemEffectPacket(itemstackcopy, player), player);
            PacketHandler.sendToAllTracking(new NetheriteTotemEffectPacket(itemstackcopy, player), player);
         }

         return itemstack != null;
      } else {
         return false;
      }
   }

   private static List<ItemStack> checkNetheriteTotem(Player player) {
      List<ItemStack> listItem = new ArrayList<>();

      for (InteractionHand interactionhand : InteractionHand.values()) {
         ItemStack itemstack1 = player.m_21120_(interactionhand);
         if (itemstack1.m_41720_() instanceof NetheriteTotemBase) {
            listItem.add(itemstack1);
         }
      }

      if (ExternalMods.CURIOS.isLoaded()) {
         ItemStack itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         itemstack = CuriosApi.getCuriosHelper()
            .findEquippedCurio((Item)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get(), player)
            .<ItemStack>map(ImmutableTriple::getRight)
            .orElse(ItemStack.f_41583_);
         if (!itemstack.m_41619_()) {
            listItem.add(itemstack);
         }

         if (ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded()) {
            itemstack = CuriosApi.getCuriosHelper()
               .findEquippedCurio((Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get(), player)
               .<ItemStack>map(ImmutableTriple::getRight)
               .orElse(ItemStack.f_41583_);
            if (!itemstack.m_41619_()) {
               listItem.add(itemstack);
            }
         }

         if (ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()) {
            itemstack = CuriosApi.getCuriosHelper()
               .findEquippedCurio((Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get(), player)
               .<ItemStack>map(ImmutableTriple::getRight)
               .orElse(ItemStack.f_41583_);
            if (!itemstack.m_41619_()) {
               listItem.add(itemstack);
            }
         }
      }

      return listItem;
   }

   private static boolean containNetheriteTotem(List<ItemStack> list, Item item) {
      for (ItemStack stack : list) {
         if (ItemStack.m_41758_(stack, new ItemStack(item))) {
            return true;
         }
      }

      return false;
   }

   @SubscribeEvent
   public void onLivingUpdate(LivingTickEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         List<ItemStack> listTotem = checkNetheriteTotem(player);
         if (!listTotem.isEmpty()
            && ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()
            && containNetheriteTotem(listTotem, (Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get())) {
            if (UpgradedNetheriteCreativeConfig.EnableCreativeNoHarmful) {
               CreativeUtil.cleanHarmfulEffects(player);
            }

            if (UpgradedNetheriteCreativeConfig.EnableCreativeSaturation) {
               if (player.m_36324_().m_38702_() < 20) {
                  player.m_36324_().m_38705_(player.m_36324_().m_38702_() + 1);
               } else if (player.m_36324_().m_38722_() < 20.0F) {
                  player.m_36324_().m_38717_(player.m_36324_().m_38722_() + 1.0F);
               }
            }

            if (UpgradedNetheriteCreativeConfig.EnableCreativeHeal && player.m_21223_() < player.m_21233_()) {
               player.m_5634_(1.0F);
            }
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public void onApplyEffect(Added event) {
      if (event.getEntity() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         List<ItemStack> listTotem = checkNetheriteTotem(player);
         if (ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()
            && containNetheriteTotem(listTotem, (Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get())
            && event.getEffectInstance().m_19544_().m_19483_() == MobEffectCategory.HARMFUL
            && UpgradedNetheriteCreativeConfig.EnableCreativeNoHarmful) {
            event.setResult(Result.DENY);
            if (event.isCancelable()) {
               event.setCanceled(true);
            }
         }
      }
   }
}
