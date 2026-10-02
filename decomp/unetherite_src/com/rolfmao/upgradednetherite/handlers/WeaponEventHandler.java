package com.rolfmao.upgradednetherite.handlers;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.init.UpgradedNetheriteEffects;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.GoldUtil;
import com.rolfmao.upgradednetherite.utils.check.PhantomUtil;
import com.rolfmao.upgradednetherite.utils.check.PoisonUtil;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import com.rolfmao.upgradednetherite.utils.check.WitherUtil;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "upgradednetherite",
   bus = Bus.FORGE
)
public class WeaponEventHandler {
   @SubscribeEvent
   public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
      if (UpgradedNetheriteConfig.EnableBonusExpEcho && event.getDroppedExperience() > 0 && event.getAttackingPlayer() != null) {
         Player player = event.getAttackingPlayer();
         if (EchoUtil.isEchoToolOrWeapon(player.m_21205_())) {
            int nextInt = 0;
            int exp = 0;
            if (UpgradedNetheriteConfig.MaxExpEcho < UpgradedNetheriteConfig.MinExpEcho) {
               exp = 0;
            } else if (UpgradedNetheriteConfig.MaxExpEcho == UpgradedNetheriteConfig.MinExpEcho) {
               exp = UpgradedNetheriteConfig.MinExpEcho;
            } else {
               nextInt = player.m_217043_().m_188503_(UpgradedNetheriteConfig.MaxExpEcho - UpgradedNetheriteConfig.MinExpEcho + 1);
               exp = UpgradedNetheriteConfig.MinExpEcho + nextInt;
            }

            event.setDroppedExperience(event.getOriginalExperience() + exp);
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public static void onLooting(LootingLevelEvent event) {
      if (event.getDamageSource() != null && event.getDamageSource().m_7639_() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getDamageSource().m_7639_();
         Entity target = event.getEntity();
         ItemStack heldItem = player.m_21205_();
         if (event.getDamageSource().m_7640_() instanceof Projectile) {
            Projectile projectile = (Projectile)event.getDamageSource().m_7640_();
            if (GoldUtil.isGoldProjectile(projectile) && UpgradedNetheriteConfig.EnableLootingBonus) {
               int EnchantBonus = event.getDamageSource().m_7640_().getPersistentData().m_128451_("LootingGoldUpgradedNetheriteBow");
               if ((float)EnchantBonus >= 3.0F) {
                  event.setLootingLevel(event.getLootingLevel() + UpgradedNetheriteConfig.LootingBonus + UpgradedNetheriteConfig.LootingEnchantBonus);
               } else {
                  event.setLootingLevel(event.getLootingLevel() + UpgradedNetheriteConfig.LootingBonus);
               }
            }

            if (CorruptUtil.isCorruptProjectile(projectile) && UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
               event.setLootingLevel(
                  event.getLootingLevel()
                     + event.getDamageSource().m_7640_().getPersistentData().m_128451_("LootingCorruptUpgradedNetheriteBow")
                        * UpgradedNetheriteConfig.LootingBonusCorruptWeapon
               );
            }

            if (EnderUtil.isEnderProjectile(projectile) && target instanceof EnderMan && UpgradedNetheriteConfig.EnableDoubleLootingBonusEnderWeapon) {
               event.setLootingLevel(event.getLootingLevel() * 2);
            }
         } else if (GoldUtil.isGoldWeapon(heldItem) && UpgradedNetheriteConfig.EnableLootingBonus) {
            float EnchantBonus = 0.0F;
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(heldItem);
            if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44982_)) {
               int EnchantLevel = enchantments.get(Enchantments.f_44982_);
               EnchantBonus = (float)EnchantLevel;
            }

            if (EnchantBonus >= 3.0F) {
               event.setLootingLevel(event.getLootingLevel() + UpgradedNetheriteConfig.LootingBonus + UpgradedNetheriteConfig.LootingEnchantBonus);
            } else {
               event.setLootingLevel(event.getLootingLevel() + UpgradedNetheriteConfig.LootingBonus);
            }
         } else if (CorruptUtil.isCorruptWeapon(heldItem) && UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
            event.setLootingLevel(event.getLootingLevel() + CorruptUtil.intWearingCorrupt(player, true) * UpgradedNetheriteConfig.LootingBonusCorruptWeapon);
         }

         if (EnderUtil.isEnderWeapon(heldItem) && target instanceof EnderMan && UpgradedNetheriteConfig.EnableDoubleLootingBonusEnderWeapon) {
            event.setLootingLevel(event.getLootingLevel() * 2);
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public static void onAttackEntityEvent(AttackEntityEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         Player player = event.getEntity();
         Entity target = event.getTarget();
         ItemStack heldItem = player.m_21205_();
         if (target instanceof EnderMan && EnderUtil.isEnderWeapon(heldItem)
            || !target.m_5825_() && FireUtil.isFireWeapon(heldItem)
            || (target.m_5825_() && !(target instanceof WitherBoss) && !(target instanceof EnderDragon) || target instanceof EnderMan)
               && WaterUtil.isWaterWeapon(heldItem)
            || target instanceof Phantom && PhantomUtil.isPhantomWeapon(heldItem)
            || target instanceof PiglinBrute && GoldUtil.isGoldWeapon(heldItem)
            || CorruptUtil.intWearingCorrupt(player, true) > 0 && CorruptUtil.isCorruptWeapon(heldItem)
            || target instanceof Warden && EchoUtil.isEchoWeapon(heldItem)) {
            player.getPersistentData().m_128350_("upgraded_netherite_bonus_damage", player.m_36403_(0.0F));
         }

         if ((double)player.m_36403_(0.0F) >= 1.0
            && (EnderUtil.isEnderWeapon(heldItem) || WitherUtil.isWitherWeapon(heldItem) || PoisonUtil.isPoisonWeapon(heldItem))) {
            player.getPersistentData().m_128379_("upgraded_netherite_fullcharged_attack", true);
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public static void onDamageEntity(LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof Player) {
         if (event.getEntity().f_19853_.f_46443_) {
            return;
         }

         ServerPlayer player = (ServerPlayer)event.getSource().m_7639_();
         ItemStack heldItem = player.m_21205_();
         LivingEntity target = event.getEntity();
         float bonusDamage = 0.0F;
         if (event.getSource().m_7640_() instanceof Projectile) {
            Projectile projectile = (Projectile)event.getSource().m_7640_();
            if (target instanceof PiglinBrute && GoldUtil.isGoldProjectile(projectile) && UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusGoldWeapon;
            }

            if (target.m_6060_() && FireUtil.isFireProjectile(projectile) && UpgradedNetheriteConfig.EnableDamageBonusFireWeapon) {
               if (projectile.m_19880_().contains("FlameFireUpgradedNetheriteBow")) {
                  bonusDamage += (float)(UpgradedNetheriteConfig.DamageBonusFireWeapon + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon);
               } else {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusFireWeapon;
               }
            }

            if (target.m_5825_() && WaterUtil.isWaterProjectile(projectile)) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusWaterWeapon;
            }

            if (WitherUtil.isWitherProjectile(projectile)) {
               if (target.m_21023_(MobEffects.f_19615_) && UpgradedNetheriteConfig.EnableDamageBonusWitherWeapon) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusWitherWeapon;
               }

               if ((double)event.getSource().m_7640_().getPersistentData().m_128457_("getPowerForTime") == 1.0 && UpgradedNetheriteConfig.EnableWitherEffect) {
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19615_, 200, 0, false, true, true));
               }
            }

            if (PoisonUtil.isPoisonProjectile(projectile)) {
               if (target.m_21023_(MobEffects.f_19614_) && UpgradedNetheriteConfig.EnableDamageBonusPoisonWeapon) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusPoisonWeapon;
               }

               if ((double)event.getSource().m_7640_().getPersistentData().m_128457_("getPowerForTime") == 1.0 && UpgradedNetheriteConfig.EnablePoisonEffect) {
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19614_, 140, 0, false, true, true));
               }
            }

            if (target instanceof Phantom && PhantomUtil.isPhantomProjectile(projectile) && UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusPhantomWeapon;
            }

            if (FeatherUtil.isFeatherProjectile(projectile)) {
               event.getEntity().m_7292_(new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.ATTRACTION.get(), 200, 0, false, true, true));
            }

            if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon
               && CorruptUtil.isCorruptProjectile(projectile)
               && projectile.getPersistentData().m_128441_("LootingCorruptUpgradedNetheriteBow")
               && projectile.getPersistentData().m_128451_("LootingCorruptUpgradedNetheriteBow") > 0) {
               bonusDamage += (float)(
                  UpgradedNetheriteConfig.DamageBonusCorruptWeapon
                     * event.getSource().m_7640_().getPersistentData().m_128451_("LootingCorruptUpgradedNetheriteBow")
               );
            }

            if (target instanceof Warden && EchoUtil.isEchoProjectile(projectile) && UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusEchoWeapon;
            }

            if (bonusDamage > 0.0F) {
               if (event.getSource().m_7640_().getPersistentData().m_128441_("getPowerForTime")
                  && event.getSource().m_7640_().getPersistentData().m_128457_("getPowerForTime") < 1.0F) {
                  bonusDamage = bonusDamage * event.getSource().m_7640_().getPersistentData().m_128457_("getPowerForTime") / 2.0F;
               }

               event.setAmount(event.getAmount() + event.getAmount() * (bonusDamage / 100.0F));
            }
         } else {
            if (target instanceof PiglinBrute && GoldUtil.isGoldMeleeWeapon(heldItem) && UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusGoldWeapon;
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                  heldItem.m_41784_().m_128473_("shield_bonusdamage");
               }
            }

            if (target.m_6060_() && FireUtil.isFireMeleeWeapon(heldItem) && UpgradedNetheriteConfig.EnableDamageBonusFireWeapon) {
               float EnchantBonus = 0.0F;
               Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(heldItem);
               if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44981_)) {
                  int EnchantLevel = enchantments.get(Enchantments.f_44981_);
                  EnchantBonus = (float)EnchantLevel;
               }

               if (EnchantBonus >= 2.0F) {
                  bonusDamage += (float)(UpgradedNetheriteConfig.DamageBonusFireWeapon + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon);
               } else {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusFireWeapon;
               }

               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                  heldItem.m_41784_().m_128473_("shield_bonusdamage");
               }
            }

            if (EnderUtil.isEnderMeleeWeapon(heldItem) && UpgradedNetheriteConfig.EnableDamageBonusEnderWeapon) {
               if (player.getPersistentData().m_128441_("upgraded_netherite_fullcharged_attack")
                  && player.getPersistentData().m_128471_("upgraded_netherite_fullcharged_attack")
                  && UpgradedNetheriteConfig.EnablePreventTeleport) {
                  event.getEntity().m_7292_(new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.ENDER_ANCHOR.get(), 200, 0, false, true, true));
               }

               if (target instanceof EnderMan) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusEnderWeapon;
                  if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                     bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                     heldItem.m_41784_().m_128473_("shield_bonusdamage");
                  }
               }
            }

            if (WaterUtil.isWaterMeleeWeapon(heldItem)) {
               if (target.m_5825_()) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusWaterWeapon;
                  if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                     bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                     heldItem.m_41784_().m_128473_("shield_bonusdamage");
                  }
               }

               if (target instanceof EnderMan) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusWaterEndermanWeapon;
                  if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                     bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                     heldItem.m_41784_().m_128473_("shield_bonusdamage");
                  }
               }
            }

            if (WitherUtil.isWitherMeleeWeapon(heldItem)) {
               if (target.m_21023_(MobEffects.f_19615_) && UpgradedNetheriteConfig.EnableDamageBonusWitherWeapon) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusWitherWeapon;
               }

               if (player.getPersistentData().m_128441_("upgraded_netherite_fullcharged_attack")
                  && player.getPersistentData().m_128471_("upgraded_netherite_fullcharged_attack")
                  && UpgradedNetheriteConfig.EnableWitherEffect) {
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19615_, 200, 0, false, true, true));
               }
            }

            if (PoisonUtil.isPoisonMeleeWeapon(heldItem)) {
               if (target.m_21023_(MobEffects.f_19614_) && UpgradedNetheriteConfig.EnableDamageBonusPoisonWeapon) {
                  bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusPoisonWeapon;
               }

               if (player.getPersistentData().m_128441_("upgraded_netherite_fullcharged_attack")
                  && player.getPersistentData().m_128471_("upgraded_netherite_fullcharged_attack")
                  && UpgradedNetheriteConfig.EnablePoisonEffect) {
                  event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19614_, 140, 0, false, true, true));
               }
            }

            if (target instanceof Phantom && UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon && PhantomUtil.isPhantomMeleeWeapon(heldItem)) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusPhantomWeapon;
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                  heldItem.m_41784_().m_128473_("shield_bonusdamage");
               }
            }

            if (CorruptUtil.intWearingCorrupt(player, true) > 0
               && CorruptUtil.isCorruptMeleeWeapon(heldItem)
               && UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon) {
               bonusDamage += (float)(UpgradedNetheriteConfig.DamageBonusCorruptWeapon * CorruptUtil.intWearingCorrupt(player, true));
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                  heldItem.m_41784_().m_128473_("shield_bonusdamage");
               }
            }

            if (target instanceof Warden && UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon && EchoUtil.isEchoMeleeWeapon(heldItem)) {
               bonusDamage += (float)UpgradedNetheriteConfig.DamageBonusEchoWeapon;
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  bonusDamage += heldItem.m_41784_().m_128457_("shield_bonusdamage");
                  heldItem.m_41784_().m_128473_("shield_bonusdamage");
               }
            }

            if (bonusDamage > 0.0F) {
               if (player.getPersistentData().m_128441_("upgraded_netherite_bonus_damage")
                  && player.getPersistentData().m_128457_("upgraded_netherite_bonus_damage") < 1.0F) {
                  bonusDamage = bonusDamage * player.getPersistentData().m_128457_("upgraded_netherite_bonus_damage") / 2.0F;
               }

               event.setAmount(event.getAmount() + event.getAmount() * (bonusDamage / 100.0F));
            }

            if (player.getPersistentData().m_128441_("upgraded_netherite_fullcharged_attack")) {
               player.getPersistentData().m_128473_("upgraded_netherite_fullcharged_attack");
            }

            if (player.getPersistentData().m_128441_("upgraded_netherite_bonus_damage")) {
               player.getPersistentData().m_128473_("upgraded_netherite_bonus_damage");
            }
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public void onLivingAttackEvent(LivingAttackEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (event.getSource().m_7639_() != null && event.getSource().m_7639_() instanceof LivingEntity) {
            Entity attacker = event.getSource().m_7639_();
            ItemStack stackShield = player.m_21211_();
            Item shield = stackShield.m_41720_();
            ItemStack heldItem = player.m_21205_();
            if (GoldUtil.isGoldShield(stackShield) && attacker instanceof PiglinBrute && GoldUtil.isGoldMeleeWeapon(heldItem) && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusGoldWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusGoldWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusGoldWeapon / 20.0F);
               }
            }

            if (FireUtil.isFireShield(stackShield) && attacker.m_6060_() && FireUtil.isFireMeleeWeapon(heldItem) && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusFireWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusFireWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusFireWeapon / 20.0F);
               }
            }

            if (EnderUtil.isEnderShield(stackShield) && attacker instanceof EnderMan && EnderUtil.isEnderMeleeWeapon(heldItem) && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusEnderWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusEnderWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusEnderWeapon / 20.0F);
               }
            }

            if (WaterUtil.isWaterShield(stackShield)
               && (attacker.m_5825_() || attacker instanceof EnderMan)
               && WaterUtil.isWaterMeleeWeapon(heldItem)
               && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusWaterWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusWaterWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusWaterWeapon / 20.0F);
               }
            }

            if (WitherUtil.isWitherShield(stackShield)
               && WitherUtil.isWitherMeleeWeapon(heldItem)
               && ((LivingEntity)attacker).m_21023_(MobEffects.f_19615_)
               && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusWitherWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusWitherWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusWitherWeapon / 20.0F);
               }
            }

            if (PoisonUtil.isPoisonShield(stackShield)
               && PoisonUtil.isPoisonMeleeWeapon(heldItem)
               && ((LivingEntity)attacker).m_21023_(MobEffects.f_19614_)
               && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusPoisonWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusPoisonWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusPoisonWeapon / 20.0F);
               }
            }

            if (PhantomUtil.isPhantomShield(stackShield)
               && attacker instanceof Phantom
               && PhantomUtil.isPhantomWeapon(heldItem)
               && !ItemStack.m_41758_(heldItem, new ItemStack((ItemLike)ModItems.PHANTOM_UPGRADED_NETHERITE_BOW.get()))
               && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusPhantomWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusPhantomWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusPhantomWeapon / 20.0F);
               }
            }

            if (FeatherUtil.isFeatherShield(stackShield) && player.m_21254_()) {
               Double rand = Math.random();
               if (rand <= 0.5) {
                  double px = player.m_20185_();
                  double py = player.m_20186_();
                  double pz = player.m_20189_();
                  List<Entity> entitys = player.f_19853_.m_45976_(Entity.class, new AABB(px - 1.0, py - 1.0, pz - 1.0, px + 1.0, py + 1.0, pz + 1.0));
                  int bumped = 0;

                  for (Entity entity : entitys) {
                     if (entity.m_6084_()
                        && !(entity instanceof WitherBoss)
                        && !(entity instanceof EnderDragon)
                        && (!(entity instanceof Player) || attacker instanceof Player)) {
                        if (bumped > 32) {
                           break;
                        }

                        Vec3 entityVector = new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
                        Vec3 finalVector = new Vec3(px, py + 0.5, pz).m_82546_(entityVector);
                        Double distance = Math.sqrt(
                           finalVector.f_82479_ * finalVector.f_82479_
                              + finalVector.f_82480_ * finalVector.f_82480_
                              + finalVector.f_82481_ * finalVector.f_82481_
                        );
                        if (distance > 1.0) {
                           finalVector = finalVector.m_82541_();
                        }

                        entity.m_20334_(-finalVector.f_82479_ * 1.25, 0.25, -finalVector.f_82481_ * 1.25);
                        bumped++;
                     }
                  }
               }
            }

            if (CorruptUtil.isCorruptShield(stackShield) && CorruptUtil.isCorruptMeleeWeapon(heldItem) && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusCorruptWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusCorruptWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusCorruptWeapon / 20.0F);
               }
            }

            if (EchoUtil.isEchoShield(stackShield) && attacker instanceof Warden && EchoUtil.isEchoMeleeWeapon(heldItem) && player.m_21254_()) {
               if (heldItem.m_41784_().m_128441_("shield_bonusdamage")) {
                  heldItem.m_41784_()
                     .m_128350_(
                        "shield_bonusdamage",
                        Math.min(
                           heldItem.m_41784_().m_128457_("shield_bonusdamage") + (float)UpgradedNetheriteConfig.DamageBonusEchoWeapon / 20.0F,
                           (float)UpgradedNetheriteConfig.DamageBonusEchoWeapon / 2.0F
                        )
                     );
               } else {
                  heldItem.m_41784_().m_128350_("shield_bonusdamage", (float)UpgradedNetheriteConfig.DamageBonusEchoWeapon / 20.0F);
               }
            }
         }
      }
   }
}
