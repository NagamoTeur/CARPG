package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import top.theillusivec4.curios.api.SlotContext;

public class TheCube extends ItemSpellstoneCurio implements ISpellstone {
   private final List<MobEffect> randomBuffs;
   private final List<MobEffect> randomDebuffs;
   private final List<ResourceKey<Level>> worlds;
   private final Map<ServerPlayer, Future<TheCube.CachedTeleportationLocation>> locationCache = new WeakHashMap<>();
   private final ExecutorService executor = Executors.newCachedThreadPool();

   public TheCube() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41486_());
      this.worlds = ImmutableList.of(Level.f_46428_, Level.f_46429_, Level.f_46430_);
      this.randomBuffs = ImmutableList.of(
         MobEffects.f_19617_,
         MobEffects.f_19600_,
         MobEffects.f_19605_,
         MobEffects.f_19598_,
         MobEffects.f_19603_,
         MobEffects.f_19596_,
         MobEffects.f_19606_,
         MobEffects.f_19591_
      );
      this.randomDebuffs = ImmutableList.of(
         MobEffects.f_19610_,
         MobEffects.f_19604_,
         MobEffects.f_19599_,
         MobEffects.f_19612_,
         MobEffects.f_19620_,
         MobEffects.f_19597_,
         MobEffects.f_19613_,
         MobEffects.f_19614_,
         MobEffects.f_19615_
      );
      this.immunityList.add(DamageSource.f_19307_.f_19326_);
      this.immunityList.add(DamageSource.f_19305_.f_19326_);
      this.immunityList.add(DamageSource.f_19308_.f_19326_);
      this.immunityList.add(DamageSource.f_19309_.f_19326_);
      this.immunityList.add(DamageSource.f_19311_.f_19326_);
      this.immunityList.add(DamageSource.f_19312_.f_19326_);
      this.immunityList.add(DamageSource.f_19315_.f_19326_);
      this.immunityList.add(DamageSource.f_19316_.f_19326_);
      this.immunityList.add(DamageSource.f_19314_.f_19326_);
      this.immunityList.add(DamageSource.f_19310_.f_19326_);
      this.immunityList.add(DamageSource.f_19322_.f_19326_);
      this.immunityList.add(DamageSource.f_19325_.f_19326_);
   }

   @Override
   public int getCooldown(Player player) {
      return player != null && reducedCooldowns.test(player) ? 1600 : 3200;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         boolean cursed = Minecraft.m_91087_().f_91074_ != null && SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube3", ChatFormatting.GOLD, 120);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube11", ChatFormatting.GOLD, (int)this.getDamageLimit(cursed));
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube12");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube13");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube14");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube15");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube16");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube17");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube18");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube19");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube20");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube21");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theCube22");
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

   public void applyRandomEffect(LivingEntity entity, boolean positive) {
      List<MobEffect> effects = positive ? this.randomBuffs : this.randomDebuffs;
      MobEffect effect = effects.get(random.nextInt(effects.size()));
      if (positive) {
         int time = 100 + random.nextInt(500);
         int amplifier = random.nextDouble() <= 0.25 ? 1 : 0;
         entity.m_7292_(new MobEffectInstance(effect, time, amplifier, false, true));
      } else {
         int time = 200 + random.nextInt(1000);
         int amplifier = random.nextDouble() <= 0.15 ? 2 : (random.nextDouble() <= 0.4 ? 1 : 0);
         entity.m_7292_(new MobEffectInstance(effect, time, amplifier, false, true));
      }
   }

   public float getDamageLimit(Player player) {
      return this.getDamageLimit(SuperpositionHandler.isTheCursedOne(player));
   }

   private float getDamageLimit(boolean cursed) {
      return cursed ? 150.0F : 100.0F;
   }

   public Multimap<Attribute, AttributeModifier> getCurrentModifiers(Player player) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      attributes.put(
         Attributes.f_22279_,
         new AttributeModifier(
            UUID.fromString("a601a528-fbf3-49bb-84af-f65023c1a188"), "enigmaticlegacy:sprint_bonus", player.m_20142_() ? 0.35F : 0.0, Operation.MULTIPLY_TOTAL
         )
      );
      return attributes;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      attributes.put(
         Attributes.f_22283_,
         new AttributeModifier(UUID.fromString("d171890c-ba68-42e3-ba2e-ac275e8de595"), "enigmaticlegacy:attack_speed_modifier", 0.4F, Operation.MULTIPLY_TOTAL)
      );
      attributes.put(
         (Attribute)ForgeMod.SWIM_SPEED.get(),
         new AttributeModifier(UUID.fromString("7652a7d5-1e7c-4c8e-8bd2-b0dd38411581"), "enigmaticlegacy:swim_bonus", 1.0, Operation.MULTIPLY_TOTAL)
      );
      attributes.put(
         Attributes.f_22286_,
         new AttributeModifier(UUID.fromString("290d5f76-87aa-4f7c-9c1a-9aef2fe25d05"), "enigmaticlegacy:luck_bonus", 1.0, Operation.ADDITION)
      );
      return attributes;
   }

   public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
      return super.getFortuneLevel(slotContext, lootContext, stack) + 1;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      tooltips.clear();
      return tooltips;
   }

   @Override
   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
      if (context.entity() instanceof Player player) {
         AttributeMap map = player.m_21204_();
         map.m_22161_(this.getCurrentModifiers(player));
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player) {
         if (player.m_20146_() < 300) {
            player.m_20301_(300);
         }

         if (player.m_6060_()) {
            player.m_20095_();
         }

         AttributeMap map = player.m_21204_();
         map.m_22178_(this.getCurrentModifiers(player));
         if (context.entity() instanceof ServerPlayer) {
            if (!this.locationCache.containsKey(player)) {
               this.generateCachedLocation((ServerPlayer)player);
            } else {
               Future<TheCube.CachedTeleportationLocation> future = this.locationCache.get(player);
               if (future.isDone() && !future.isCancelled()) {
                  try {
                     TheCube.CachedTeleportationLocation location = future.get();
                     if (location.dimension() == player.f_19853_.m_46472_()) {
                        this.generateCachedLocation((ServerPlayer)player);
                     }
                  } catch (Exception var7) {
                     throw new RuntimeException(var7);
                  }
               }
            }
         }
      }
   }

   @Override
   public void triggerActiveAbility(Level world, ServerPlayer player, ItemStack stack) {
      if (!SuperpositionHandler.hasSpellstoneCooldown(player)) {
         TheCube.CachedTeleportationLocation location = null;
         if (this.locationCache.containsKey(player)) {
            try {
               Future<TheCube.CachedTeleportationLocation> future = this.locationCache.get(player);
               if (future.isDone()) {
                  location = this.locationCache.get(player).get();
               } else {
                  future.cancel(true);
               }

               this.locationCache.remove(player);
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         if (location == null) {
            EnigmaticLegacy.LOGGER.getInternal().info("No cached location found for {}, generating new one synchronously.", player.m_36316_().getName());
            location = this.findRandomLocation(player);
         }

         ResourceKey<Level> key = location.dimension();
         world.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
         EnigmaticLegacy.packetInstance
            .send(
               PacketDistributor.NEAR.with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
               new PacketRecallParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 48, false)
            );
         player.m_6021_(location.x(), location.y(), location.z());
         if (player.f_19853_.m_46472_() != key) {
            SuperpositionHandler.sendToDimension(player, key);
            player.m_6021_(location.x(), location.y(), location.z());
         }

         world.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
         EnigmaticLegacy.packetInstance
            .send(
               PacketDistributor.NEAR.with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
               new PacketRecallParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 48, false)
            );
         SuperpositionHandler.setSpellstoneCooldown(player, this.getCooldown(player));
         EnigmaticLegacy.LOGGER
            .getInternal()
            .info(
               "Player {} triggered active ability of Non-Euclidean Cube. Teleported to D: {}, X: {}, Y: {}, Z: {}.",
               player.m_36316_().getName(),
               player.f_19853_.m_46472_(),
               player.m_20185_(),
               player.m_20186_(),
               player.m_20189_()
            );
      }
   }

   private void generateCachedLocation(ServerPlayer player) {
      Future<TheCube.CachedTeleportationLocation> future = this.executor.submit(() -> {
         try {
            TheCube.CachedTeleportationLocation location = this.findRandomLocation(player);
            EnigmaticLegacy.LOGGER.debug("Found random location: " + location);
            return location;
         } catch (Exception var3) {
            EnigmaticLegacy.LOGGER.error("Could not find random location for:" + player.m_36316_().getName());
            var3.printStackTrace();
            throw var3;
         }
      });
      this.locationCache.put(player, future);
   }

   private TheCube.CachedTeleportationLocation findRandomLocation(ServerPlayer player) {
      ResourceKey<Level> key = SuperpositionHandler.getRandomElement(this.worlds, player.f_19853_.m_46472_());
      ServerLevel level = SuperpositionHandler.getWorld(key);
      if (level == null) {
         EnigmaticLegacy.LOGGER.error("Could not find world: " + key);
         EnigmaticLegacy.LOGGER.error("This is never supposed to happen!");
         key = Level.f_46428_;
         level = SuperpositionHandler.getOverworld();
      }

      int border = (int)level.m_6857_().m_61959_() / 2;
      int attempts = 0;
      int radius = border < 10000 ? border : 10000;

      do {
         BlockPos pos = new BlockPos(radius - random.nextInt(radius * 2), key == Level.f_46429_ ? 100 : 200, radius - random.nextInt(radius * 2));
         level.m_46745_(pos);

         for (int i = 0; i < 4; i++) {
            if (i > 0) {
               pos = new BlockPos((pos.m_123341_() >> 4) * 16 + random.nextInt(16), pos.m_123342_(), (pos.m_123343_() >> 4) * 16 + random.nextInt(16));
            }

            Optional<Vector3> location = this.findValidPosition(player, level, pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
            if (!location.isEmpty()) {
               return new TheCube.CachedTeleportationLocation(key, location.get().x, location.get().y, location.get().z);
            }
         }
      } while (++attempts <= 100);

      return this.findRandomLocation(player);
   }

   private Optional<Vector3> findValidPosition(ServerPlayer player, Level world, int x, int y, int z) {
      int checkAxis = y - 10;

      for (int counter = 0; counter <= checkAxis; counter++) {
         BlockPos below = new BlockPos(x, y - counter - 1, z);
         BlockPos feet = new BlockPos(x, y - counter, z);
         BlockPos head = new BlockPos(x, y - counter + 1, z);
         if (world.m_141937_() >= below.m_123342_()) {
            return Optional.empty();
         }

         if (!world.m_46859_(below) && world.m_8055_(below).m_60815_() && world.m_46859_(feet) && world.m_46859_(head)) {
            return Optional.of(new Vector3((double)feet.m_123341_() + 0.5, (double)feet.m_123342_(), (double)feet.m_123343_() + 0.5));
         }
      }

      return Optional.empty();
   }

   public void clearLocationCache() {
      this.locationCache.clear();
   }

   private static record CachedTeleportationLocation(ResourceKey<Level> dimension, double x, double y, double z) {
   }
}
