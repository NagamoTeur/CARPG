package net.sweenus.simplyswords.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.entity.BattleStandardDarkEntity;
import net.sweenus.simplyswords.entity.BattleStandardEntity;
import net.sweenus.simplyswords.registry.ItemsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class HelperMethods {
   public static Entity getTargetedEntity(Entity user, int range) {
      Vec3 rayCastOrigin = user.m_146892_();
      Vec3 userView = user.m_20252_(1.0F).m_82541_().m_82490_((double)range);
      Vec3 rayCastEnd = rayCastOrigin.m_82549_(userView);
      AABB searchBox = user.m_20191_().m_82377_((double)range, (double)range, (double)range);
      EntityHitResult hitResult = ProjectileUtil.m_37287_(
         user, rayCastOrigin, rayCastEnd, searchBox, target -> !target.m_5833_() && target.m_6087_() && target instanceof LivingEntity, (double)(range * range)
      );
      return hitResult != null ? hitResult.m_82443_() : null;
   }

   public static boolean isWalking(Entity entity) {
      return !(entity instanceof Player player) ? false : !player.m_21224_() && (player.m_6069_() || player.m_20184_().m_165924_() > 0.1);
   }

   public static boolean checkFriendlyFire(LivingEntity livingEntity, Player player) {
      if (livingEntity == null || player == null) {
         return false;
      } else if (!checkEntityBlacklist(livingEntity, player)) {
         return false;
      } else if (livingEntity instanceof Player playerEntity) {
         return playerEntity == player ? false : playerEntity.m_7099_(player);
      } else {
         return true;
      }
   }

   public static boolean checkEntityBlacklist(LivingEntity livingEntity, Player player) {
      return livingEntity != null && player != null
         ? !(livingEntity instanceof ArmorStand)
            && !(livingEntity instanceof Villager)
            && !(livingEntity instanceof BattleStandardEntity)
            && !(livingEntity instanceof BattleStandardDarkEntity)
         : false;
   }

   public static void spawnParticle(
      Level world, ParticleOptions particle, double xpos, double ypos, double zpos, double xvelocity, double yvelocity, double zvelocity
   ) {
      if (world.f_46443_) {
         world.m_7106_(particle, xpos, ypos, zpos, xvelocity, yvelocity, zvelocity);
      } else if (world instanceof ServerLevel serverWorld) {
         serverWorld.m_8767_(particle, xpos, ypos, zpos, 1, xvelocity, yvelocity, zvelocity, 0.1);
      }
   }

   public static void playHitSounds(LivingEntity attacker, LivingEntity target) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         boolean impactsounds_enabled = SimplySwordsConfig.getBooleanValue("enable_weapon_impact_sounds");
         float impactsounds_volume = SimplySwordsConfig.getGeneralSettings("impact_sound_effect_volume");
         if (impactsounds_enabled) {
            int choose_sound = (int)(Math.random() * 30.0);
            float choose_pitch = (float)Math.random() * 2.0F;
            if (choose_sound <= 10) {
               world.m_6269_(
                  null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_01.get(), SoundSource.PLAYERS, impactsounds_volume, 1.1F + choose_pitch
               );
            }

            if (choose_sound <= 20 && choose_sound > 10) {
               world.m_6269_(
                  null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_02.get(), SoundSource.PLAYERS, impactsounds_volume, 1.1F + choose_pitch
               );
            }

            if (choose_sound <= 30 && choose_sound > 20) {
               world.m_6269_(
                  null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_03.get(), SoundSource.PLAYERS, impactsounds_volume, 1.1F + choose_pitch
               );
            }

            if (choose_sound <= 40 && choose_sound > 30) {
               world.m_6269_(
                  null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), SoundSource.PLAYERS, impactsounds_volume, 1.1F + choose_pitch
               );
            }
         }
      }
   }

   public static String chooseRunicPower() {
      List<String> runicList = Arrays.asList(
         "active_defence",
         "float",
         "greater_float",
         "freeze",
         "shielding",
         "greater_shielding",
         "slow",
         "greater_slow",
         "stoneskin",
         "greater_stoneskin",
         "swiftness",
         "greater_swiftness",
         "trailblaze",
         "greater_trailblaze",
         "weaken",
         "greater_weaken",
         "zephyr",
         "greater_zephyr",
         "frost_ward",
         "wildfire",
         "unstable",
         "momentum",
         "greater_momentum",
         "imbued",
         "greater_imbued",
         "pincushion",
         "greater_pincushion",
         "ward",
         "immolation"
      );

      for (int i = 0; i < 100; i++) {
         Random choose = new Random();
         int randomIndex = choose.nextInt(runicList.size());
         String runicSelection = runicList.get(randomIndex);
         if (SimplySwordsConfig.getBooleanValue(runicSelection)) {
            return runicSelection;
         }
      }

      return "";
   }

   public static String chooseRunefusedPower() {
      List<String> runicList = Arrays.asList(
         "float",
         "greater_float",
         "freeze",
         "shielding",
         "greater_shielding",
         "slow",
         "greater_slow",
         "stoneskin",
         "greater_stoneskin",
         "swiftness",
         "greater_swiftness",
         "trailblaze",
         "greater_trailblaze",
         "weaken",
         "greater_weaken",
         "zephyr",
         "greater_zephyr",
         "wildfire",
         "imbued",
         "greater_imbued",
         "pincushion",
         "greater_pincushion"
      );

      for (int i = 0; i < 100; i++) {
         Random choose = new Random();
         int randomIndex = choose.nextInt(runicList.size());
         String runicSelection = runicList.get(randomIndex);
         if (SimplySwordsConfig.getBooleanValue(runicSelection)) {
            return runicSelection;
         }
      }

      return "";
   }

   public static String chooseNetherfusedPower() {
      List<String> runicList = Arrays.asList("echo", "berserk", "radiance", "onslaught", "nullification");
      Random choose = new Random();
      int randomIndex = choose.nextInt(runicList.size());
      return runicList.get(randomIndex);
   }

   public static boolean isUniqueTwohanded(ItemStack stack) {
      return stack.m_150930_((Item)ItemsRegistry.SOULPYRE.get())
         || stack.m_150930_((Item)ItemsRegistry.SOULKEEPER.get())
         || stack.m_150930_((Item)ItemsRegistry.TWISTED_BLADE.get())
         || stack.m_150930_((Item)ItemsRegistry.HEARTHFLAME.get())
         || stack.m_150930_((Item)ItemsRegistry.SOULRENDER.get())
         || stack.m_150930_((Item)ItemsRegistry.SLUMBERING_LICHBLADE.get())
         || stack.m_150930_((Item)ItemsRegistry.WAKING_LICHBLADE.get())
         || stack.m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())
         || stack.m_150930_((Item)ItemsRegistry.BRIMSTONE_CLAYMORE.get())
         || stack.m_150930_((Item)ItemsRegistry.ICEWHISPER.get())
         || stack.m_150930_((Item)ItemsRegistry.ARCANETHYST.get())
         || stack.m_150930_((Item)ItemsRegistry.THUNDERBRAND.get())
         || stack.m_150930_((Item)ItemsRegistry.WATCHER_CLAYMORE.get());
   }

   public static void createFootfalls(
      Entity entity,
      ItemStack stack,
      Level world,
      int stepMod,
      SimpleParticleType particle,
      SimpleParticleType sprintParticle,
      SimpleParticleType passiveParticle,
      boolean passiveParticles
   ) {
      if (entity instanceof Player player && SimplySwordsConfig.getBooleanValue("enable_weapon_footfalls")) {
         if (player.m_6844_(EquipmentSlot.MAINHAND) == stack && isWalking(player) && !player.m_6069_() && player.m_20096_()) {
            if (stepMod == 6) {
               if (player.m_20142_()) {
                  world.m_7106_(
                     sprintParticle,
                     player.m_20185_() + player.m_204034_(stack.m_41720_()).m_7096_(),
                     player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.2,
                     player.m_20189_() + player.m_204034_(stack.m_41720_()).m_7094_(),
                     0.0,
                     0.0,
                     0.0
                  );
               } else {
                  world.m_7106_(
                     particle,
                     player.m_20185_() + player.m_204034_(stack.m_41720_()).m_7096_(),
                     player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.2,
                     player.m_20189_() + player.m_204034_(stack.m_41720_()).m_7094_(),
                     0.0,
                     0.0,
                     0.0
                  );
               }
            } else if (stepMod == 3) {
               if (player.m_20142_()) {
                  world.m_7106_(
                     sprintParticle,
                     player.m_20185_() - player.m_204034_(stack.m_41720_()).m_7096_(),
                     player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.2,
                     player.m_20189_() - player.m_204034_(stack.m_41720_()).m_7094_(),
                     0.0,
                     0.0,
                     0.0
                  );
               } else {
                  world.m_7106_(
                     particle,
                     player.m_20185_() - player.m_204034_(stack.m_41720_()).m_7096_(),
                     player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.2,
                     player.m_20189_() - player.m_204034_(stack.m_41720_()).m_7094_(),
                     0.0,
                     0.0,
                     0.0
                  );
               }
            }
         }

         if (player.m_6844_(EquipmentSlot.MAINHAND) == stack && passiveParticles && SimplySwordsConfig.getBooleanValue("enable_passive_particles")) {
            float randomy = (float)Math.random();
            if (stepMod == 1) {
               world.m_7106_(
                  passiveParticle,
                  player.m_20185_() - player.m_204034_(stack.m_41720_()).m_7096_(),
                  player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.4 + (double)randomy,
                  player.m_20189_() - player.m_204034_(stack.m_41720_()).m_7094_(),
                  0.0,
                  0.0,
                  0.0
               );
               world.m_7106_(
                  passiveParticle,
                  player.m_20185_() - player.m_204034_(stack.m_41720_()).m_7096_() + 0.1,
                  player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + (double)randomy,
                  player.m_20189_() - player.m_204034_(stack.m_41720_()).m_7094_() - 0.1,
                  0.0,
                  0.0,
                  0.0
               );
            } else if (stepMod == 4) {
               world.m_7106_(
                  passiveParticle,
                  player.m_20185_() + player.m_204034_(stack.m_41720_()).m_7096_(),
                  player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + 0.4 + (double)randomy,
                  player.m_20189_() + player.m_204034_(stack.m_41720_()).m_7094_(),
                  0.0,
                  0.0,
                  0.0
               );
               world.m_7106_(
                  passiveParticle,
                  player.m_20185_() + player.m_204034_(stack.m_41720_()).m_7096_() - 0.1,
                  player.m_20186_() + player.m_204034_(stack.m_41720_()).m_7098_() + (double)randomy,
                  player.m_20189_() + player.m_204034_(stack.m_41720_()).m_7094_() + 0.1,
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }
   }
}
