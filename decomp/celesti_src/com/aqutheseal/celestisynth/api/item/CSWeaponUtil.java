package com.aqutheseal.celestisynth.api.item;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.network.util.ChangeCameraTypePacket;
import com.aqutheseal.celestisynth.common.network.util.ShakeScreenServerPacket;
import com.aqutheseal.celestisynth.manager.CSNetworkManager;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public interface CSWeaponUtil {
   String CS_CONTROLLER_TAG_ELEMENT = "csController";
   String CS_EXTRAS_ELEMENT = "csExtras";
   String ANIMATION_TIMER_KEY = "cs.animationTimer";
   String ANIMATION_BEGUN_KEY = "cs.hasAnimationBegun";

   default void hurtNoKB(Player holder, LivingEntity target, float damage, boolean isBlockable) {
      if (damage != 0.0F) {
         double preAttribute = target.m_21051_(Attributes.f_22278_).m_22135_();
         target.m_21051_(Attributes.f_22278_).m_22100_(100.0);
         target.f_19802_ = 0;
         if (isBlockable && target.m_21211_().m_41720_() instanceof ShieldItem) {
            this.useAndDamageItem(target.m_21211_(), target.f_19853_, target, (int)(damage / 3.0F));
         } else {
            target.m_6469_(DamageSource.m_19344_(holder), damage);
         }

         target.m_19970_(holder, target);
         target.m_21051_(Attributes.f_22278_).m_22100_(preAttribute);
      }
   }

   default void hurtNoKB(Player holder, LivingEntity target, float damage) {
      this.hurtNoKB(holder, target, damage, false);
   }

   default void setDeltaPlayer(Player player, double x, double y, double z) {
      player.f_19864_ = true;
      player.m_20334_(x, y, z);
   }

   default void setDeltaPlayer(Player player, Vec3 vec) {
      player.f_19864_ = true;
      player.m_20256_(vec);
   }

   default float getSharpnessValue(ItemStack stack, float multiplier) {
      return (float)EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44977_, stack) * multiplier;
   }

   default void useAndDamageItem(ItemStack pStack, Level pLevel, LivingEntity targetOwnerEntity, int damageAmount) {
      if (!pLevel.f_46443_) {
         pStack.m_41622_(damageAmount, targetOwnerEntity, ownerEntity -> {
            if (targetOwnerEntity.m_21205_() == pStack) {
               ownerEntity.m_21190_(InteractionHand.MAIN_HAND);
            } else if (targetOwnerEntity.m_21206_() == pStack) {
               ownerEntity.m_21190_(InteractionHand.OFF_HAND);
            }
         });
      }

      if (targetOwnerEntity instanceof Player ownerPlayer) {
         ownerPlayer.m_36246_(Stats.f_12982_.m_12902_(pStack.m_41720_()));
      }
   }

   default void sendExpandingParticles(Level level, ParticleType<?> particleType, double x, double y, double z, int amount, float expansionMultiplier) {
      for (int i = 0; i < amount; i++) {
         RandomSource random = level.m_213780_();
         float offX = (-0.5F + random.m_188501_()) * expansionMultiplier;
         float offY = (-0.5F + random.m_188501_()) * expansionMultiplier;
         float offZ = (-0.5F + random.m_188501_()) * expansionMultiplier;
         ParticleUtil.sendParticles(level, particleType, x, y, z, 0, (double)offX, (double)offY, (double)offZ);
      }
   }

   default void sendExpandingParticles(Level level, ParticleType<?> particleType, BlockPos origin, int amount, float expansionMultiplier) {
      this.sendExpandingParticles(
         level, particleType, (double)origin.m_123341_(), (double)origin.m_123342_(), (double)origin.m_123343_(), amount, expansionMultiplier
      );
   }

   default AABB createAABB(BlockPos pos, double range) {
      return this.createAABB((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), range);
   }

   default AABB createAABB(double x, double y, double z, double range) {
      return new AABB(x + range, y + range, z + range, x - range, y - range, z - range);
   }

   default List<Entity> iterateEntities(Level level, AABB aabb) {
      return level.m_45976_(Entity.class, aabb);
   }

   default boolean checkDualWield(Player player, Class<? extends CSWeapon> weapon) {
      return weapon.isInstance(player.m_21205_().m_41720_()) && weapon.isInstance(player.m_21206_().m_41720_());
   }

   default Entity getLookedAtEntity(Player player, double range) {
      double distance = range * range;
      Vec3 eyePos = player.m_20299_(1.0F);
      Vec3 viewVec = player.m_20252_(1.0F);
      Vec3 targetVec = eyePos.m_82520_(viewVec.f_82479_ * range, viewVec.f_82480_ * range, viewVec.f_82481_ * range);
      AABB aabb = player.m_20191_().m_82369_(viewVec.m_82490_(range)).m_82377_(4.0, 4.0, 4.0);
      EntityHitResult hitResult = expandedHitResult(player, eyePos, targetVec, aabb, entity -> !entity.m_5833_(), distance);
      return hitResult != null ? hitResult.m_82443_() : null;
   }

   default void shakeScreensForNearbyPlayers(Player holder, Level level, double range, int maxDuration, int startFadingOut, float maxIntensity) {
      if (level.m_5776_()) {
         for (Player entity : level.m_45976_(Player.class, holder.m_20191_().m_82377_(range, range, range))) {
            this.shakeScreens(entity, maxDuration, startFadingOut, Math.max(0.0F, maxIntensity - (float)(entity.m_20280_(holder) * 1.0E-4)));
         }
      }
   }

   default void shakeScreens(Player target, int duration, int startFadingOut, float intensity) {
      if (target != null) {
         CSNetworkManager.sendToServer(new ShakeScreenServerPacket(target.m_20148_(), duration, startFadingOut, intensity));
      }
   }

   default double calculateXLook(Player player) {
      return player.m_20154_().m_7096_();
   }

   default double calculateYLook(Player player, double yMult) {
      double lookY = player.m_20154_().m_7098_();
      return lookY > 0.0 ? lookY * yMult : lookY * 0.5;
   }

   default double calculateYLook(Player player) {
      return player.m_20154_().m_7098_();
   }

   default double calculateZLook(Player player) {
      return player.m_20154_().m_7094_();
   }

   default void setCameraAngle(Player player, int ordinal) {
      if (!player.f_19853_.m_5776_()) {
         CSNetworkManager.sendToAll(new ChangeCameraTypePacket(player.m_19879_(), ordinal));
      }
   }

   static void disableRunningWeapon(Entity owner) {
      if (owner instanceof Player playerOwner) {
         AnimationManager.playAnimation(owner.f_19853_, AnimationManager.AnimationsList.CLEAR);

         for (EquipmentSlot slot : EquipmentSlot.values()) {
            Item data = playerOwner.m_6844_(slot).m_41720_();
            if (data instanceof CSWeapon) {
               CSWeapon cs = (CSWeapon)data;
               CompoundTag datax = playerOwner.m_6844_(slot).m_41737_("csController");
               CompoundTag dataAlt = playerOwner.m_6844_(slot).m_41737_("csExtras");
               if (datax != null) {
                  datax.m_128431_().clear();
               }

               if (dataAlt != null) {
                  dataAlt.m_128431_().clear();
               }

               cs.resetExtraValues(playerOwner.m_6844_(slot), playerOwner);
            }
         }

         if (playerOwner.m_6117_()) {
            playerOwner.m_21253_();
         }
      }
   }

   @Nullable
   static EntityHitResult expandedHitResult(Entity pShooter, Vec3 pStartVec, Vec3 pEndVec, AABB pBoundingBox, Predicate<Entity> pFilter, double pDistance) {
      Level level = pShooter.f_19853_;
      double range = pDistance;
      Entity confirmedTarget = null;
      Vec3 clipVec = null;

      for (Entity potentialTarget : level.m_6249_(pShooter, pBoundingBox, pFilter)) {
         AABB boundsHitbox = potentialTarget.m_20191_().m_82400_((double)potentialTarget.m_6143_() + 1.5);
         Optional<Vec3> potentialClippedVec = boundsHitbox.m_82371_(pStartVec, pEndVec);
         if (boundsHitbox.m_82390_(pStartVec)) {
            if (range >= 0.0) {
               confirmedTarget = potentialTarget;
               clipVec = potentialClippedVec.orElse(pStartVec);
               range = 0.0;
            }
         } else if (potentialClippedVec.isPresent()) {
            Vec3 vec31 = potentialClippedVec.get();
            double distToTargetPos = pStartVec.m_82557_(vec31);
            if (distToTargetPos < range || range == 0.0) {
               if (potentialTarget.m_20201_() != pShooter.m_20201_() || potentialTarget.canRiderInteract()) {
                  confirmedTarget = potentialTarget;
                  clipVec = vec31;
                  range = distToTargetPos;
               } else if (range == 0.0) {
                  confirmedTarget = potentialTarget;
                  clipVec = vec31;
               }
            }
         }
      }

      return confirmedTarget == null ? null : new EntityHitResult(confirmedTarget, clipVec);
   }
}
