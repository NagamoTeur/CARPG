package com.aqutheseal.celestisynth.common.item.weapons;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualAnimation;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.projectile.RainfallArrow;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class RainfallSerenityItem extends BowItem implements CSWeapon {
   public static CSVisualAnimation SPECIAL_RAINFALL = new CSVisualAnimation("animation.cs_effect.special_rainfall", 50);

   public RainfallSerenityItem(Properties pProperties) {
      super(pProperties);
   }

   @Override
   public boolean hasPassive() {
      return true;
   }

   @Override
   public int getPassiveAmount() {
      return 1;
   }

   @Override
   public int getSkillsAmount() {
      return 2;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pHand) {
      ItemStack heldStack = pPlayer.m_21120_(pHand);
      CompoundTag elementData = heldStack.m_41698_("csController");
      InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(heldStack, pLevel, pPlayer, pHand, true);
      if (ret != null) {
         return ret;
      } else if (!pPlayer.m_6144_()) {
         pPlayer.m_6672_(pHand);
         elementData.m_128379_("cs.hasAnimationBegun", true);
         if (pHand == InteractionHand.MAIN_HAND) {
            AnimationManager.playAnimation(pPlayer.f_19853_, AnimationManager.AnimationsList.ANIM_RAINFALL_AIM_RIGHT);
         } else {
            AnimationManager.playAnimation(pPlayer.f_19853_, AnimationManager.AnimationsList.ANIM_RAINFALL_AIM_LEFT);
         }

         return InteractionResultHolder.m_19096_(heldStack);
      } else if (!pPlayer.m_36335_().m_41519_(this)) {
         this.shiftSkill(pLevel, pPlayer);
         pPlayer.m_36335_().m_41524_(this, 200);
         return InteractionResultHolder.m_19090_(heldStack);
      } else {
         return InteractionResultHolder.m_19100_(heldStack);
      }
   }

   public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
      super.onUsingTick(stack, player, count);
      player.m_5618_(player.m_146908_());
   }

   public void shiftSkill(Level level, Player player) {
      CSEffectEntity.createInstance(
         player, null, (CSVisualType)CSVisualTypes.RAINFALL_VANISH.get(), this.calculateXLook(player) * 3.0, 1.0, this.calculateZLook(player) * 3.0
      );
      CSEffectEntity.createInstance(player, null, (CSVisualType)CSVisualTypes.RAINFALL_VANISH_CIRCLE.get(), 0.0, -1.5, 0.0);
      player.m_216990_((SoundEvent)CSSoundEvents.CS_VANISH.get());
      player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 100, 3, true, false, false));
      player.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 100, 0, true, false, false));
   }

   public void m_5551_(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
      if (pEntityLiving instanceof Player player) {
         CompoundTag elementData = pStack.m_41698_("csController");
         int useDuration = this.m_8105_(pStack) - pTimeLeft;
         double curPowerFromUse = (double)getPowerForTime(pStack, useDuration);
         AnimationManager.playAnimation(pLevel, AnimationManager.AnimationsList.CLEAR);
         elementData.m_128379_("cs.hasAnimationBegun", false);
         if (curPowerFromUse >= 1.0) {
            if (curPowerFromUse == 1.0) {
               CSEffectEntity.createInstance(
                  player,
                  null,
                  (CSVisualType)CSVisualTypes.RAINFALL_SHOOT.get(),
                  this.calculateXLook(player) * 2.0,
                  0.5 + this.calculateYLook(player, 5.0) * 1.0,
                  this.calculateZLook(player) * 2.0
               );
               player.m_20256_(player.m_20184_().m_82492_(this.calculateXLook(player) * 0.5, 0.0, this.calculateZLook(player) * 0.5));
            }

            int amount = 50;
            float expansionMultiplier = 0.5F;

            for (int e = 0; e < amount; e++) {
               RandomSource random = player.m_217043_();
               double angle = random.m_188500_() * 2.0 * Math.PI;
               float offX = (float)Math.cos(angle) * expansionMultiplier;
               float offY = (-0.5F + random.m_188501_()) * expansionMultiplier;
               float offZ = (float)Math.sin(angle) * expansionMultiplier;
               ParticleUtil.sendParticles(
                  player.f_19853_,
                  (SimpleParticleType)CSParticleTypes.RAINFALL_ENERGY_SMALL.get(),
                  player.m_20185_(),
                  player.m_20186_(),
                  player.m_20189_(),
                  0,
                  (double)offX,
                  (double)offY,
                  (double)offZ
               );
            }

            FloatArrayList angles = new FloatArrayList();
            angles.add(0.0F);
            if (EnchantmentHelper.m_44843_(Enchantments.f_44959_, pStack) > 0) {
               if (pLevel.f_46441_.m_188499_()) {
                  angles.add(-15.0F);
                  angles.add(15.0F);
               } else {
                  angles.add(-30.0F);
                  angles.add(30.0F);
               }

               if (pLevel.f_46441_.m_188503_(3) == 0) {
                  angles.add(-30.0F + pLevel.f_46441_.m_188501_() * 60.0F);
               }
            }

            for (FloatListIterator var28 = angles.iterator();
               var28.hasNext();
               pStack.m_41622_(1, player, livingOwner -> livingOwner.m_21190_(player.m_7655_()))
            ) {
               float angle = (Float)var28.next();
               if (!pLevel.f_46443_) {
                  BlockPos originPos = new BlockPos(player.m_20185_(), player.m_20186_() + 1.5, player.m_20189_());
                  RainfallArrow rainfallArrow = new RainfallArrow(pLevel, player);
                  rainfallArrow = (RainfallArrow)this.customArrow(rainfallArrow);
                  rainfallArrow.f_36705_ = Pickup.CREATIVE_ONLY;
                  rainfallArrow.setOrigin(originPos);
                  rainfallArrow.m_36767_((byte)3);
                  rainfallArrow.m_36781_((Double)CSConfigManager.COMMON.rainfallSerenityArrowDmg.get() + pLevel.f_46441_.m_188500_() * 3.0);
                  rainfallArrow.setImbueQuasar(true);
                  Vec3 vec31 = pEntityLiving.m_20289_(1.0F);
                  Vector3f wrappedVec = new Vector3f(vec31);
                  Quaternion quaternion = new Quaternion(wrappedVec, angle, true);
                  Vec3 viewVec = pEntityLiving.m_20252_(1.0F);
                  Vector3f wrappedViewVec = new Vector3f(viewVec);
                  wrappedViewVec.m_122251_(quaternion);
                  rainfallArrow.m_6686_(
                     (double)wrappedViewVec.m_122239_(),
                     (double)wrappedViewVec.m_122260_(),
                     (double)wrappedViewVec.m_122269_(),
                     (float)(curPowerFromUse * 3.0),
                     0.0F
                  );
                  int powerEnchLvl = EnchantmentHelper.m_44843_(Enchantments.f_44988_, pStack);
                  int piercingEnchLvl = EnchantmentHelper.m_44843_(Enchantments.f_44961_, pStack);
                  int multishotEnchLvl = EnchantmentHelper.m_44843_(Enchantments.f_44959_, pStack);
                  int punchEnchLvl = EnchantmentHelper.m_44843_(Enchantments.f_44989_, pStack);
                  int flameEnchLvl = EnchantmentHelper.m_44843_(Enchantments.f_44990_, pStack);
                  if (curPowerFromUse == 1.0) {
                     rainfallArrow.setStrong(true);
                  }

                  if (powerEnchLvl > 0) {
                     rainfallArrow.m_36781_(rainfallArrow.m_36789_() + (double)powerEnchLvl);
                  }

                  if (piercingEnchLvl > 0) {
                     rainfallArrow.m_36781_(rainfallArrow.m_36789_() + (double)(piercingEnchLvl * 4));
                  }

                  if (multishotEnchLvl > 0) {
                     rainfallArrow.m_36781_(rainfallArrow.m_36789_() * 0.75);
                  }

                  if (punchEnchLvl > 0) {
                     rainfallArrow.m_36735_(punchEnchLvl);
                  }

                  if (flameEnchLvl > 0) {
                     rainfallArrow.setFlaming(true);
                  }

                  pLevel.m_7967_(rainfallArrow);
               }
            }

            pLevel.m_6263_(
               null,
               player.m_20185_(),
               player.m_20186_(),
               player.m_20189_(),
               (SoundEvent)CSSoundEvents.CS_LASER_SHOOT.get(),
               SoundSource.PLAYERS,
               (float)(0.7F * curPowerFromUse),
               (float)((double)(1.0F / (pLevel.m_213780_().m_188501_() * 0.4F + 1.2F)) + curPowerFromUse * 0.5)
            );
            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }
      }
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      return super.customArrow(arrow);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      List<Enchantment> enchantments = new ObjectArrayList();
      enchantments.add(Enchantments.f_44988_);
      enchantments.add(Enchantments.f_44989_);
      enchantments.add(Enchantments.f_44990_);
      enchantments.add(Enchantments.f_44959_);
      enchantments.add(Enchantments.f_44961_);
      if (enchantment == Enchantments.f_44961_) {
         if (EnchantmentHelper.m_44843_(Enchantments.f_44960_, stack) > 0) {
            return false;
         }

         if (EnchantmentHelper.m_44843_(Enchantments.f_44959_, stack) > 0) {
            return false;
         }
      }

      if ((enchantment == Enchantments.f_44959_ || enchantment == Enchantments.f_44960_) && EnchantmentHelper.m_44843_(Enchantments.f_44961_, stack) > 0) {
         return false;
      } else {
         return enchantments.contains(enchantment) ? true : super.canApplyAtEnchantingTable(stack, enchantment);
      }
   }

   public float getDrawSpeed(ItemStack stack) {
      float piercingEnchLvl = (float)EnchantmentHelper.m_44843_(Enchantments.f_44961_, stack);
      float quickEnchLvl = (float)EnchantmentHelper.m_44843_(Enchantments.f_44960_, stack);
      return (float)((Double)CSConfigManager.COMMON.rainfallSerenityDrawSpeed.get() + (double)(piercingEnchLvl * 10.0F)) / ((quickEnchLvl + 1.0F) * 0.6F);
   }

   public static float getPowerForTime(ItemStack stack, int pCharge) {
      float totalCharge = (float)pCharge / ((RainfallSerenityItem)stack.m_41720_()).getDrawSpeed(stack);
      totalCharge = (totalCharge * totalCharge + totalCharge * 2.0F) / 3.0F;
      if (totalCharge > 1.0F) {
         totalCharge = 1.0F;
      }

      return totalCharge;
   }

   public int m_8105_(ItemStack pStack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack pStack) {
      return UseAnim.NONE;
   }
}
