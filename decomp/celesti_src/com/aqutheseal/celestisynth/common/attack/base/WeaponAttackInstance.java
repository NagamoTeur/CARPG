package com.aqutheseal.celestisynth.common.attack.base;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class WeaponAttackInstance implements CSWeaponUtil {
   protected final Player player;
   protected final ItemStack stack;
   protected final int heldDuration;
   public static final SoundEvent[] BASE_WEAPON_EFFECTS = new SoundEvent[]{
      (SoundEvent)CSSoundEvents.CS_SWORD_SWING.get(),
      (SoundEvent)CSSoundEvents.CS_SWORD_SWING_FIRE.get(),
      (SoundEvent)CSSoundEvents.CS_AIR_SWING.get(),
      (SoundEvent)CSSoundEvents.CS_SWORD_CLASH.get(),
      (SoundEvent)CSSoundEvents.CS_FIRE_SHOOT.get(),
      (SoundEvent)CSSoundEvents.CS_IMPACT_HIT.get()
   };

   public WeaponAttackInstance(Player player, ItemStack stack, int heldDuration) {
      this.player = player;
      this.stack = stack;
      this.heldDuration = heldDuration;
   }

   public WeaponAttackInstance(Player player, ItemStack stack) {
      this(player, stack, 0);
   }

   public abstract AnimationManager.AnimationsList getAnimation();

   public abstract int getCooldown();

   public abstract int getAttackStopTime();

   public abstract boolean getCondition();

   public abstract void startUsing();

   public abstract void tickAttack();

   public abstract void stopUsing();

   public void baseStop() {
      this.stopUsing();
      this.getTagController().m_128405_("cs.AttackIndex", -1);
      this.getTagController().m_128405_("cs.animationTimer", 0);
      this.getTagController().m_128379_("cs.hasAnimationBegun", false);
   }

   public void baseTickSkill() {
      this.tickAttack();
      if (this.getTimerProgress() >= this.getAttackStopTime()) {
         this.baseStop();
      }
   }

   public Player getPlayer() {
      return this.player;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public int getHeldDuration() {
      return this.heldDuration;
   }

   public int getTimerProgress() {
      return this.getTagController().m_128451_("cs.animationTimer");
   }

   public CompoundTag getTagController() {
      return this.stack.m_41698_("csController");
   }

   public CompoundTag getTagExtras() {
      return this.stack.m_41698_("csExtras");
   }

   public static void playRandomBladeSound(Entity entity, int length) {
      SoundEvent randomSound = BASE_WEAPON_EFFECTS[entity.f_19853_.m_213780_().m_188503_(length)];
      entity.m_5496_(randomSound, 0.35F, 0.5F + entity.f_19853_.m_213780_().m_188501_());
   }
}
