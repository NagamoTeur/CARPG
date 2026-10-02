package lykrast.meetyourfight.item.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import lykrast.gunswithoutroses.item.IBullet;
import lykrast.gunswithoutroses.item.ShotgunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

public class CocktailShotgun extends ShotgunItem {
   private static final List<Triple<MobEffect, Integer, Boolean>> EFFECTS = new ArrayList<>();

   public static void initEffects() {
      EFFECTS.add(Triple.of(MobEffects.f_19597_, 400, false));
      EFFECTS.add(Triple.of(MobEffects.f_19613_, 400, false));
      EFFECTS.add(Triple.of(MobEffects.f_19614_, 400, false));
      EFFECTS.add(Triple.of(MobEffects.f_19615_, 400, false));
      EFFECTS.add(Triple.of(MobEffects.f_19619_, 400, true));
      EFFECTS.add(Triple.of(MobEffects.f_19620_, 100, false));
   }

   public CocktailShotgun(
      Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount
   ) {
      super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability, bulletCount);
   }

   protected void shoot(Level world, Player player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
      super.shoot(world, player, gun, ammo, bulletItem, bulletFree);
      float luck = player.m_36336_();
      double chance = 0.3333333333333333;
      if (luck >= 0.0F) {
         chance = (2.0 + (double)luck) / (6.0 + (double)luck);
      } else {
         chance = 1.0 / (3.0 - (double)luck);
      }

      int effectLevel = -1;
      if (world.m_213780_().m_188500_() <= chance) {
         effectLevel = 0;

         for (int i = 0; i < 2; i++) {
            chance *= 0.5;
            if (!(world.m_213780_().m_188500_() <= chance)) {
               break;
            }

            effectLevel++;
         }
      }

      if (effectLevel >= 0) {
         Triple<MobEffect, Integer, Boolean> triple = EFFECTS.get(world.m_213780_().m_188503_(EFFECTS.size()));
         int duration = triple.getRight() ? (Integer)triple.getMiddle() * (1 + effectLevel) : (Integer)triple.getMiddle();
         int potency = triple.getRight() ? 0 : effectLevel;
         ThrownPotion potionentity = new ThrownPotion(world, player);
         potionentity.m_37446_(
            PotionUtils.m_43552_(new ItemStack(Items.f_42736_), Collections.singleton(new MobEffectInstance((MobEffect)triple.getLeft(), duration, potency)))
         );
         potionentity.m_37251_(player, player.m_146909_(), player.m_146908_(), -5.0F, (float)this.getProjectileSpeed(gun, player), 1.0F);
         world.m_7967_(potionentity);
         world.m_6263_(
            null,
            player.m_20185_(),
            player.m_20186_(),
            player.m_20189_(),
            SoundEvents.f_12437_,
            SoundSource.PLAYERS,
            0.5F,
            0.4F / (world.m_213780_().m_188501_() * 0.4F + 0.8F)
         );
      }
   }

   protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
      super.addExtraStatsTooltip(stack, world, tooltip);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.meetyourfight.desc.luck").m_130940_(ChatFormatting.GRAY));
   }
}
