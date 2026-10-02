package net.xylonity.knightquest.common.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.xylonity.knightquest.KnightQuest;
import net.xylonity.knightquest.common.item.weapons.CleaverWeapon;
import net.xylonity.knightquest.common.item.weapons.KhopeshWeapon;
import net.xylonity.knightquest.common.item.weapons.NailWeapon;
import net.xylonity.knightquest.common.item.weapons.PaladinWeapon;
import net.xylonity.knightquest.common.item.weapons.UchigatanaWeapon;
import net.xylonity.knightquest.common.material.KQArmorMaterials;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestItems;
import net.xylonity.knightquest.registry.KnightQuestWeapons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KQArmorItem extends ArmorItem {
   private final String bonusTooltip;
   private static final MobEffectInstance SHIELD_ARMOR = new MobEffectInstance(MobEffects.f_19606_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance BAT_ARMOR = new MobEffectInstance(MobEffects.f_19611_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance PATH_ARMOR = new MobEffectInstance(MobEffects.f_19596_, Integer.MAX_VALUE, 1, false, true, true);
   private static final MobEffectInstance BOW_ARMOR = new MobEffectInstance(MobEffects.f_19596_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance HORN_ARMOR = new MobEffectInstance(MobEffects.f_19600_, 400, 0, false, false, true);
   private static final MobEffectInstance SEA_ARMOR = new MobEffectInstance(MobEffects.f_19593_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance PIRATE_ARMOR = new MobEffectInstance(MobEffects.f_19621_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance SPIDER_ARMOR = new MobEffectInstance(MobEffects.f_19603_, Integer.MAX_VALUE, 1, false, false, false);
   private static final MobEffectInstance PHATOM_ARMOR = new MobEffectInstance(MobEffects.f_19596_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance NETHER_ARMOR = new MobEffectInstance(MobEffects.f_19607_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance HUSK_ARMOR = new MobEffectInstance(MobEffects.f_19606_, Integer.MAX_VALUE, 1, false, false, true);
   private static final MobEffectInstance BAMBOO_BLUE = new MobEffectInstance(MobEffects.f_19596_, Integer.MAX_VALUE, 1, false, false, true);
   private static final MobEffectInstance SILVERFISH_ARMOR = new MobEffectInstance(MobEffects.f_19598_, Integer.MAX_VALUE, 0, false, false, true);
   private static final MobEffectInstance SKULK_ARMOR = new MobEffectInstance(MobEffects.f_19606_, Integer.MAX_VALUE, 1, false, false, true);
   private static final MobEffectInstance STRAWHAT_ARMOR = new MobEffectInstance(MobEffects.f_19608_, Integer.MAX_VALUE, 0, false, false, true);
   private static final Map<UUID, Map<KQArmorMaterials, Boolean>> effectAppliedByArmorMap = new HashMap<>();
   private static final Map<UUID, Map<KQArmorMaterials, Boolean>> tickingEffectAppliedByArmorMap = new ConcurrentHashMap<>();

   public KQArmorItem(KQArmorMaterials pMaterial, EquipmentSlot pSlot, Properties pProperties) {
      super(pMaterial, pSlot, pProperties.m_41491_(KnightQuest.CREATIVE_MODE_TAB));
      this.bonusTooltip = pMaterial.getKeyName();
   }

   private boolean isArmorSetConfigEnabled(String bonusTooltip) {
      try {
         KQArmorItem.ArmorSet armorSet = KQArmorItem.ArmorSet.valueOf(bonusTooltip.toUpperCase());
         return armorSet.isEnabled();
      } catch (Exception var3) {
         return false;
      }
   }

   public void m_7373_(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
      if (this.isArmorSetConfigEnabled(this.bonusTooltip)) {
         if (!Objects.equals(this.bonusTooltip, "chainmail") && !Objects.equals(this.bonusTooltip, "tengu")) {
            if (KQConfigValues.REQUIRED_ARMOR_PIECES < 4) {
               pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest.set_bonus"));
            } else {
               pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest.full_set_bonus"));
            }

            pTooltipComponents.add(
               Component.m_237110_(
                  "tooltip.item.knightquest." + this.bonusTooltip + "_helmet.bonus",
                  new Object[]{
                     "§7§o-" + (int)Math.floor(KQConfigValues.EVOKER_DARKNESS_CHANCE * 100.0) + "%",
                     "§7§o-" + (int)Math.floor(KQConfigValues.BLAZE_FIRE_CHANCE * 100.0) + "%",
                     "§7§o-" + (int)Math.floor(KQConfigValues.DRAGONSET_DAMAGE_MULTIPLIER * 100.0 - 100.0) + "%",
                     "§7§o" + KQConfigValues.SKULK_MAX_LIGHT_LEVEL,
                     "§7§o-" + (int)Math.floor(KQConfigValues.CHANCE_ENDERMANSET * 100.0) + "%",
                     "§7§o" + KQConfigValues.TELEPORT_RADIUS_ENDERMANSET,
                     "§7§o-" + (int)Math.floor(KQConfigValues.FORZESET_DEFLECT_CHANCE * 100.0) + "%",
                     "§7§o" + (100.0 - KQConfigValues.CREEPER_EXPLOSION_DAMAGE_MULTIPLIER * 100.0) + "%",
                     "§7§o-" + (int)Math.floor(KQConfigValues.SILVERSET_BURN_CHANCE * 100.0) + "%",
                     "§7§o" + (int)Math.floor(KQConfigValues.HOLLOWSET_HEALING_MULTIPLIER * 100.0) + "%",
                     "§7§o-" + (int)Math.floor(KQConfigValues.WITHERSET_WITHER_CHANCE * 100.0) + "%",
                     "§7§o" + Math.floor(KQConfigValues.ZOMBIESET_HEALING_AMOUNT),
                     "§7§o" + KQConfigValues.ZOMBIESET_HEALING_TICKS / 20,
                     "§7§o" + KQConfigValues.SILVERFISH_EFFECT_MAX_HEIGHT
                  }
               )
            );
         } else if (Objects.equals(this.bonusTooltip, "tengu")) {
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest.full_helmet_bonus"));
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest." + this.bonusTooltip + "_helmet.bonus"));
         }
      }

      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
   }

   public void m_6883_(@NotNull ItemStack pStack, Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
      if (!pLevel.m_5776_() && pEntity instanceof Player player) {
         UUID playerUUID = player.m_20148_();
         if (KQConfigValues.PATHSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.PATHSET) && pLevel.m_46461_()) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PATHSET, false))) {
                  player.m_7292_(PATH_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PATHSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PATHSET, false))) {
               player.m_21195_(MobEffects.f_19596_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PATHSET, false);
            }
         }

         if (KQConfigValues.BOWSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.BOWSET) && player.m_21205_().m_41720_() instanceof ProjectileWeaponItem) {
               if (!Boolean.TRUE.equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.BOWSET, false))
                  )
                {
                  player.m_7292_(BOW_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.BOWSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.BOWSET, false))) {
               player.m_21195_(MobEffects.f_19596_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.BOWSET, false);
            }
         }

         if (KQConfigValues.BATSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.BATSET) && pLevel.m_46462_()) {
               if (!Boolean.TRUE.equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.BATSET, false))
                  )
                {
                  player.m_7292_(BAT_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.BATSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.BATSET, false))) {
               player.m_21195_(MobEffects.f_19611_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.BATSET, false);
            }
         }

         if (KQConfigValues.SHIELDSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SHIELDSET)) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SHIELDSET, false))) {
                  player.m_7292_(SHIELD_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SHIELDSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SHIELDSET, false))) {
               player.m_21195_(MobEffects.f_19606_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SHIELDSET, false);
            }
         }

         if (KQConfigValues.PHANTOMSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.PHANTOMSET) && pLevel.m_46462_()) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PHANTOMSET, false))) {
                  player.m_7292_(PHATOM_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PHANTOMSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PHANTOMSET, false))) {
               player.m_21195_(MobEffects.f_19596_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PHANTOMSET, false);
            }
         }

         if (KQConfigValues.HORNSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.HORNSET) && player.m_21188_() != null) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.HORNSET, false))) {
                  player.m_7292_(HORN_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.HORNSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.HORNSET, false))) {
               player.m_21195_(MobEffects.f_19600_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.HORNSET, false);
            }
         }

         if (KQConfigValues.SEASET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SEASET) && player.m_5842_()) {
               if (!Boolean.TRUE.equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SEASET, false))
                  )
                {
                  player.m_7292_(SEA_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SEASET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SEASET, false))) {
               player.m_21195_(MobEffects.f_19593_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SEASET, false);
            }
         }

         if (KQConfigValues.PIRATESET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.PIRATESET)) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PIRATESET, false))) {
                  player.m_7292_(PIRATE_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PIRATESET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.PIRATESET, false))) {
               player.m_21195_(MobEffects.f_19621_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.PIRATESET, false);
            }
         }

         if (KQConfigValues.SPIDERSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SPIDERSET) && player.m_6144_()) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SPIDERSET, false))) {
                  player.m_7292_(SPIDER_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SPIDERSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SPIDERSET, false))) {
               player.m_21195_(MobEffects.f_19603_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SPIDERSET, false);
            }
         }

         if (KQConfigValues.NETHERSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.NETHERSET)) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.NETHERSET, false))) {
                  player.m_7292_(NETHER_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.NETHERSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.NETHERSET, false))) {
               player.m_21195_(MobEffects.f_19607_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.NETHERSET, false);
            }
         }

         if (KQConfigValues.SKULK) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SKULK)
               && player.f_19853_.m_46803_(player.m_20183_()) <= KQConfigValues.SKULK_MAX_LIGHT_LEVEL) {
               if (!Boolean.TRUE.equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SKULK, false))) {
                  player.m_7292_(SKULK_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SKULK, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.SKULK, false))) {
               player.m_21195_(MobEffects.f_19606_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.SKULK, false);
            }
         }

         if (KQConfigValues.STRAWHATSET) {
            if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.STRAWHATSET) && player.m_5842_()) {
               if (!Boolean.TRUE
                  .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.STRAWHATSET, false))) {
                  player.m_7292_(STRAWHAT_ARMOR);
                  effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.STRAWHATSET, true);
               }
            } else if (Boolean.TRUE
               .equals(effectAppliedByArmorMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).getOrDefault(KQArmorMaterials.STRAWHATSET, false))) {
               player.m_21195_(MobEffects.f_19608_);
               effectAppliedByArmorMap.get(playerUUID).put(KQArmorMaterials.STRAWHATSET, false);
            }
         }
      }

      super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
   }

   private static boolean isTeleportPositionValid(Level level, BlockPos pos) {
      return !level.m_8055_(pos.m_7495_()).m_60795_() && level.m_8055_(pos).m_60795_() && level.m_8055_(pos.m_7494_()).m_60795_();
   }

   public static enum ArmorSet {
      DEEPSLATE(KQConfigValues.DEEPSLATESET),
      EVOKER(KQConfigValues.EVOKERSET),
      SQUIRE(KQConfigValues.SQUIRESET),
      BLAZE(KQConfigValues.BLAZESET),
      DRAGON(KQConfigValues.DRAGONSET),
      BAMBOO_GREEN(KQConfigValues.BAMBOOSET_GREEN),
      SHINOBI(KQConfigValues.SHINOBI),
      BAMBOO(KQConfigValues.BAMBOOSET),
      PATH(KQConfigValues.PATHSET),
      BOW(KQConfigValues.BOWSET),
      BAT(KQConfigValues.BATSET),
      SHIELD(KQConfigValues.SHIELDSET),
      PHANTOM(KQConfigValues.PHANTOMSET),
      HORN(KQConfigValues.HORNSET),
      SEA(KQConfigValues.SEASET),
      PIRATE(KQConfigValues.PIRATESET),
      SPIDER(KQConfigValues.SPIDERSET),
      NETHER(KQConfigValues.NETHERSET),
      SKULK(KQConfigValues.SKULK),
      STRAWHAT(KQConfigValues.STRAWHATSET),
      ENDERMAN(KQConfigValues.ENDERMANSET),
      VETERAN(KQConfigValues.VETERANSET),
      FORZE(KQConfigValues.FORZESET),
      CREEPER(KQConfigValues.CREEPERSET),
      POLAR(KQConfigValues.POLAR),
      SILVER(KQConfigValues.SILVERSET),
      HOLLOW(KQConfigValues.HOLLOWSET),
      WITHER(KQConfigValues.WITHERSET),
      APPLE(KQConfigValues.APPLE_SET),
      CONQUISTADOR(KQConfigValues.CONQUISTADORSET),
      WITCH(KQConfigValues.WITCH),
      TENGU(KQConfigValues.TENGU_HELMET),
      HUSK(KQConfigValues.HUSKSET),
      BAMBOO_BLUE(KQConfigValues.BAMBOOSET_BLUE),
      WARLORD(KQConfigValues.WARLORDSET),
      ZOMBIE(KQConfigValues.ZOMBIESET),
      SILVERFISH(KQConfigValues.SILVERFISHSET),
      SKELETON(KQConfigValues.SKELETONSET);

      private final Boolean configValue;

      private ArmorSet(Boolean configValue) {
         this.configValue = configValue;
      }

      public boolean isEnabled() {
         return this.configValue;
      }
   }

   @EventBusSubscriber(
      modid = "knightquest"
   )
   public static class ArmorStatusManagerEvents {
      private static final Map<UUID, Boolean> doubleJumpStates = new ConcurrentHashMap<>();

      @SubscribeEvent
      public static void onLivingHurt(LivingHurtEvent event) {
         if (event.getSource().m_7639_() instanceof LivingEntity livingEntity
            && livingEntity.m_21205_().m_41720_() == KnightQuestWeapons.KUKRI.get()
            && KQConfigValues.KUKRI) {
            event.getEntity().m_146917_(event.getEntity().m_146888_() + KQConfigValues.FREEZE_TICKS_KUKRI);
         }

         if (event.getSource().m_7639_() instanceof Player player && event.getEntity() != null) {
            ItemStack stack = player.m_21205_();
            if (stack.m_41720_() instanceof UchigatanaWeapon && KQConfigValues.UCHIGATANA) {
               if (stack.m_41784_().m_128471_("ShouldDoActiveAttack")) {
                  stack.m_41784_().m_128379_("ShouldDoActiveAttack", false);
                  event.setAmount((float)((double)event.getAmount() + (double)event.getAmount() * KQConfigValues.EXTRA_DAMAGE_UCHIGATANA));
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_216964_, 80, 1, false, false));
               }

               if ((double)event.getEntity().m_21223_() < KQConfigValues.ENEMY_HEALTH_PASSIVE_UCHIGATANA * (double)event.getEntity().m_21233_()) {
                  event.setAmount((float)((double)event.getAmount() + (double)event.getAmount() * KQConfigValues.EXTRA_DAMAGE_PASSIVE_UCHIGATANA));
               }
            }

            if (stack.m_41720_() instanceof CleaverWeapon
               && KQConfigValues.CLEAVER
               && (double)event.getEntity().m_21223_() > KQConfigValues.ENEMY_HEALTH_PASSIVE_CLEAVER * (double)event.getEntity().m_21233_()) {
               event.setAmount((float)((double)event.getAmount() + (double)event.getAmount() * KQConfigValues.EXTRA_DAMAGE_PASSIVE_CLEAVER));
            }
         }

         if (event.getEntity() instanceof Player player) {
            ItemStack stackx = player.m_21205_();
            if (stackx.m_41720_() instanceof KhopeshWeapon
               && event.getSource().m_7639_() != null
               && KQConfigValues.KHOPESH
               && player.f_19853_.m_46467_() - stackx.m_41784_().m_128454_("KhopeshActive") < (long)KQConfigValues.REFLECTION_TIME_KHOPESH) {
               event.getSource().m_7639_().m_6469_(event.getSource(), event.getAmount() * 0.5F);
            }
         }

         if (event.getSource().m_7639_() instanceof LivingEntity livingEntity
            && livingEntity.m_21205_().m_41720_() == KnightQuestWeapons.KHOPESH.get()
            && KQConfigValues.KHOPESH
            && (double)livingEntity.m_217043_().m_188501_() <= KQConfigValues.CHANCE_BURN_KHOPESH) {
            event.getEntity().m_20254_(livingEntity.m_217043_().m_188503_(7) + 1);
         }

         if (event.getEntity() instanceof Player playerx) {
            if (KQConfigValues.DEEPSLATESET && event.getSource().m_146707_() && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.DEEPSLATESET)) {
               float originalDamage = event.getAmount();
               float reducedDamage = (float)((double)originalDamage * KQConfigValues.DEEPSLATE_FALL_DAMAGE_MULTIPLIER);
               event.setAmount(reducedDamage);
            }

            if (KQConfigValues.EVOKERSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.EVOKERSET)) {
               Random random = new Random();
               if (event.getSource().m_7639_() != null
                  && event.getSource().m_7639_() instanceof LivingEntity entity
                  && random.nextFloat() < (float)KQConfigValues.EVOKER_DARKNESS_CHANCE) {
                  entity.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 120, 0, false, false, true));
               }
            }

            if (KQConfigValues.SQUIRESET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.SQUIRESET)) {
               event.setAmount((float)((double)event.getAmount() * KQConfigValues.SQUIRE_DAMAGE_RECEIVED_MULTIPLIER));
            }

            if (KQConfigValues.BLAZESET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.BLAZESET)) {
               Random random = new Random();
               if (event.getSource().m_7639_() != null && random.nextFloat() < (float)KQConfigValues.BLAZE_FIRE_CHANCE) {
                  event.getSource().m_7639_().m_20254_(random.nextInt(KQConfigValues.BLAZE_FIRE_DURATION_MIN, KQConfigValues.BLAZE_FIRE_DURATION_MAX));
               }
            }

            if (KQConfigValues.BAMBOOSET_GREEN
               && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.BAMBOOSET_GREEN)
               && playerx.m_21023_(MobEffects.f_19614_)
               && (event.getSource().m_19387_() || event.getSource().m_19379_())) {
               event.setAmount(0.0F);
               playerx.m_21195_(MobEffects.f_19614_);
            }

            if (KQConfigValues.SHINOBI && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.SHINOBI) && event.getSource().m_7639_() != null) {
               playerx.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 120, 1, false, false, true));
            }

            if (KQConfigValues.BAMBOOSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.BAMBOOSET) && event.getSource().m_146707_()) {
               ServerPlayer serverPlayer = (ServerPlayer)playerx;
               int particleCount = 80;
               double particleRadius = 1.2;

               for (int i = 0; i < particleCount; i++) {
                  double angleOffset = (Math.PI * 2) / (double)particleCount * (double)i;
                  double xParticleOffset = particleRadius * Math.cos(angleOffset);
                  double zParticleOffset = particleRadius * Math.sin(angleOffset);
                  serverPlayer.f_8906_
                     .m_9829_(
                        new ClientboundLevelParticlesPacket(
                           ParticleTypes.f_123777_,
                           true,
                           playerx.m_20185_() + xParticleOffset,
                           playerx.m_20186_() + 0.1,
                           playerx.m_20189_() + zParticleOffset,
                           0.2F,
                           0.05F,
                           0.2F,
                           0.0F,
                           1
                        )
                     );
               }

               Class<? extends Entity> classToPush = KQConfigValues.BAMBOOSET_PUSH_PLAYERS ? Entity.class : Monster.class;
               playerx.f_19853_.m_45976_(classToPush, playerx.m_20191_().m_82400_(3.5)).forEach(entity -> {
                  Vec3 direction = entity.m_20182_().m_82546_(player.m_20182_()).m_82541_().m_82490_((double)event.getAmount() * 0.5);
                  entity.m_5997_(direction.f_82479_, direction.f_82480_ + 0.5, direction.f_82481_);
               });
            }

            if (KQConfigValues.ENDERMANSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.ENDERMANSET) && event.getSource().m_7639_() != null) {
               Random random = new Random();
               if ((double)random.nextFloat() < KQConfigValues.CHANCE_ENDERMANSET) {
                  int radius = KQConfigValues.TELEPORT_RADIUS_ENDERMANSET;
                  BlockPos playerPos = playerx.m_20183_();
                  List<BlockPos> validPositions = new ArrayList<>();

                  for (int x = -radius; x <= radius; x++) {
                     for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                           BlockPos targetPos = new BlockPos(playerPos.m_123341_() + x, playerPos.m_123342_() + y, playerPos.m_123343_() + z);
                           if (KQArmorItem.isTeleportPositionValid(playerx.m_9236_(), targetPos)) {
                              validPositions.add(targetPos);
                           }
                        }
                     }
                  }

                  if (!validPositions.isEmpty()) {
                     BlockPos randomPos = validPositions.get(random.nextInt(validPositions.size()));
                     event.setAmount(0.0F);
                     playerx.m_9236_()
                        .m_6263_(null, playerx.m_20185_(), playerx.m_20186_(), playerx.m_20189_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, 1.0F);
                     playerx.m_6021_((double)randomPos.m_123341_(), (double)randomPos.m_123342_(), (double)randomPos.m_123343_());
                  }
               }
            }

            if (KQConfigValues.VETERANSET
               && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.VETERANSET)
               && (double)playerx.m_21223_() < (double)playerx.m_21233_() * 0.5) {
               playerx.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 200, 0, false, false, true));
               playerx.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 200, 1, false, false, true));
            }

            if (KQConfigValues.FORZESET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.FORZESET)) {
               Random random = new Random();
               if (event.getSource().m_7639_() != null && (double)random.nextFloat() < KQConfigValues.FORZESET_DEFLECT_CHANCE) {
                  event.getSource().m_7639_().m_6469_(event.getSource(), event.getAmount() * (float)KQConfigValues.FORZESET_DEFLECT_DAMAGE);
               }
            }

            if (KQConfigValues.CREEPERSET
               && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.CREEPERSET)
               && event.getSource().m_7639_() != null
               && event.getSource().m_19372_()) {
               event.setAmount((float)((double)event.getAmount() * KQConfigValues.CREEPER_EXPLOSION_DAMAGE_MULTIPLIER));
            }
         }

         if (event.getSource().m_7639_() instanceof Player playerx && event.getEntity() != null) {
            if (KQConfigValues.SILVERSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.SILVERSET) && playerx.m_9236_().m_46462_()) {
               Random random = new Random();
               if ((double)random.nextFloat() < KQConfigValues.SILVERSET_BURN_CHANCE) {
                  event.getEntity().m_20254_(random.nextInt(2, 8));
               }
            }

            if (KQConfigValues.HOLLOWSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.HOLLOWSET)) {
               playerx.m_5634_(Math.min((float)((double)event.getAmount() * KQConfigValues.HOLLOWSET_HEALING_MULTIPLIER), event.getEntity().m_21223_()));
            }

            if (KQConfigValues.DRAGONSET && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.DRAGONSET)) {
               event.setAmount((float)((double)event.getAmount() * KQConfigValues.DRAGONSET_DAMAGE_MULTIPLIER));
            }

            if (KQConfigValues.WITHERSET && event.getSource().m_19360_() && KQFullSetChecker.hasFullSetOn(playerx, KQArmorMaterials.WITHERSET)) {
               Random random = new Random();
               if (event.getSource().m_7639_() != null && (double)random.nextFloat() < KQConfigValues.WITHERSET_WITHER_CHANCE) {
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 0, false, false, false));
               }
            }
         }
      }

      @SubscribeEvent
      public static void onLivingUpdate(Finish event) {
         if (event.getEntity() instanceof Player player
            && KQConfigValues.APPLE_SET
            && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.APPLE_SET)
            && event.getItem().m_41720_().equals(Items.f_42436_)) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 1, false, true, true));
            player.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 4800, 1, false, true, true));
            player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 2400, 0, false, true, true));
         }
      }

      @SubscribeEvent
      public static void onLivingDead(LivingDeathEvent event) {
         if (event.getEntity() != null && event.getSource().m_7639_() instanceof Player player) {
            if (KQConfigValues.CONQUISTADORSET && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.CONQUISTADORSET)) {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 200, 1, false, true, true));
            }

            if (KQConfigValues.WITCH && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.WITCH)) {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 80, 1, false, true, true));
            }
         }
      }

      @SubscribeEvent
      public static void onLivingTick(LivingTickEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack helmet = player.m_150109_().m_36052_(3);
            ItemStack stack = player.m_21205_();
            if ((KQConfigValues.TENGU_HELMET || KQConfigValues.NAIL)
               && (
                  helmet.m_41720_().equals(KnightQuestItems.TENGU_HELMET.get())
                     || stack.m_41720_() instanceof NailWeapon && stack.m_41784_().m_128471_("Activated")
               )) {
               boolean canDoubleJump = doubleJumpStates.getOrDefault(player.m_20148_(), true);
               if (!player.m_20096_() && player.m_20184_().f_82480_ < 0.0 && canDoubleJump) {
                  DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClientSideDoubleJump(player));
               }

               if (player.m_20096_()) {
                  doubleJumpStates.put(player.m_20148_(), true);
               }
            }

            if (stack.m_41720_() instanceof PaladinWeapon
               && KQConfigValues.PALADIN
               && player.f_19797_ % KQConfigValues.REGEN_TICKS_PALADIN == 0
               && (double)player.m_21223_() < (double)player.m_21233_() * KQConfigValues.REGEN_MAX_PALADIN
               && stack.m_41784_().m_128471_("Activated")) {
               player.m_5634_((float)KQConfigValues.REGEN_HP_PALADIN);
            }

            if (KQConfigValues.HUSKSET) {
               if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.HUSKSET)
                  && (
                     player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_48203_)
                        || player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_48159_)
                        || player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_48217_)
                  )) {
                  if (!Boolean.TRUE
                     .equals(
                        KQArmorItem.tickingEffectAppliedByArmorMap
                           .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                           .getOrDefault(KQArmorMaterials.HUSKSET, false)
                     )) {
                     player.m_7292_(KQArmorItem.HUSK_ARMOR);
                     KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.HUSKSET, true);
                  }
               } else if (Boolean.TRUE
                  .equals(
                     KQArmorItem.tickingEffectAppliedByArmorMap
                        .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                        .getOrDefault(KQArmorMaterials.HUSKSET, false)
                  )) {
                  player.m_21195_(MobEffects.f_19606_);
                  KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.HUSKSET, false);
               }
            }

            if (KQConfigValues.BAMBOOSET_BLUE) {
               if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.BAMBOOSET_BLUE)
                  && (
                     player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_48222_)
                        || player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_48197_)
                        || player.m_9236_()
                           .m_204166_(new BlockPos((int)player.m_20185_(), (int)player.m_20186_(), (int)player.m_20189_()))
                           .m_203565_(Biomes.f_186769_)
                  )) {
                  if (!Boolean.TRUE
                     .equals(
                        KQArmorItem.tickingEffectAppliedByArmorMap
                           .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                           .getOrDefault(KQArmorMaterials.BAMBOOSET_BLUE, false)
                     )) {
                     player.m_7292_(KQArmorItem.BAMBOO_BLUE);
                     KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.BAMBOOSET_BLUE, true);
                  }
               } else if (Boolean.TRUE
                  .equals(
                     KQArmorItem.tickingEffectAppliedByArmorMap
                        .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                        .getOrDefault(KQArmorMaterials.BAMBOOSET_BLUE, false)
                  )) {
                  player.m_21195_(MobEffects.f_19596_);
                  KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.BAMBOOSET_BLUE, false);
               }
            }

            if (KQConfigValues.WARLORDSET && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.WARLORDSET)) {
               for (Entity entity : player.f_19853_.m_45976_(Player.class, player.m_20191_().m_82400_((double)KQConfigValues.WARLORD_SET_EFFECT_RADIUS))) {
                  if (KQConfigValues.SHOULD_WARLORD_SET_EFFECT_APPLY_TO_ITSELF) {
                     if (entity instanceof Player nearbyPlayer) {
                        nearbyPlayer.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 0, false, false, true));
                     }
                  } else if (entity instanceof Player) {
                     Player nearbyPlayer = (Player)entity;
                     if (entity != player) {
                        nearbyPlayer.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 0, false, false, true));
                     }
                  }
               }
            }

            if (KQConfigValues.ZOMBIESET
               && !player.f_19853_.f_46443_
               && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.ZOMBIESET)
               && player.f_19853_.m_46462_()
               && player.f_19797_ % KQConfigValues.ZOMBIESET_HEALING_TICKS == 0) {
               player.m_5634_((float)KQConfigValues.ZOMBIESET_HEALING_AMOUNT);
            }

            if (KQConfigValues.POLAR && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.POLAR) && player.m_146888_() > 0) {
               player.m_146917_(0);
            }

            if (KQConfigValues.SILVERFISHSET) {
               if (KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SILVERFISHSET)
                  && player.m_20186_() < (double)KQConfigValues.SILVERFISH_EFFECT_MAX_HEIGHT) {
                  if (!Boolean.TRUE
                     .equals(
                        KQArmorItem.tickingEffectAppliedByArmorMap
                           .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                           .getOrDefault(KQArmorMaterials.SILVERFISHSET, false)
                     )) {
                     player.m_7292_(KQArmorItem.SILVERFISH_ARMOR);
                     KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.SILVERFISHSET, true);
                  }
               } else if (Boolean.TRUE
                  .equals(
                     KQArmorItem.tickingEffectAppliedByArmorMap
                        .computeIfAbsent(player.m_20148_(), k -> new HashMap<>())
                        .getOrDefault(KQArmorMaterials.SILVERFISHSET, false)
                  )) {
                  player.m_21195_(MobEffects.f_19598_);
                  KQArmorItem.tickingEffectAppliedByArmorMap.get(player.m_20148_()).put(KQArmorMaterials.SILVERFISHSET, false);
               }
            }
         }
      }

      @SubscribeEvent
      public static void onArrowHit(EntityJoinLevelEvent event) {
         if (KQConfigValues.SKELETONSET
            && event.getEntity() instanceof AbstractArrow arrow
            && arrow.m_37282_() instanceof Player player
            && KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.SKELETONSET)) {
            arrow.m_36767_((byte)5);
         }
      }

      @OnlyIn(Dist.CLIENT)
      private static void handleClientSideDoubleJump(Player player) {
         if (Minecraft.m_91087_().f_91066_.f_92089_.m_90857_()) {
            boolean canDoubleJump = doubleJumpStates.getOrDefault(player.m_20148_(), true);
            if (canDoubleJump) {
               doubleJumpStates.put(player.m_20148_(), false);

               for (int i = 0; i < 360; i += 60) {
                  double angleRadians = Math.toRadians((double)i);
                  double particleX = player.m_20185_() + 0.4 * Math.cos(angleRadians);
                  double particleZ = player.m_20189_() + 0.4 * Math.sin(angleRadians);
                  player.m_9236_().m_7106_(ParticleTypes.f_123796_, particleX, player.m_20186_(), particleZ, 0.0, 0.35, 0.0);
               }

               player.m_6135_();
            }
         }
      }
   }
}
