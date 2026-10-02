package com.aqutheseal.celestisynth.common.attack.aquaflora;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.mixin.PlayerMixinSupport;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.item.weapons.AquafloraItem;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.FireworkRocketItem.Shape;
import net.minecraft.world.level.Level;

public class AquafloraSlashFrenzyAttack extends AquafloraAttack {
   public static final String ATTACK_ONGOING = "cs.atkOngoing";
   public static final String INITIAL_PERSPECTIVE = "cs.initPerspective";
   public static final String INITIAL_VIEW_ANGLE = "cs.initViewAngle";

   public AquafloraSlashFrenzyAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_AQUAFLORA_ASSASSINATE;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.aquafloraBloomShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 120;
   }

   @Override
   public boolean getCondition() {
      return this.getTagController().m_128471_("cs.checkPassiveIfBlooming") && !this.player.m_6047_();
   }

   @Override
   public void startUsing() {
      this.getTagController().m_128379_("cs.atkOngoing", true);
      if (this.player instanceof PlayerMixinSupport pms && this.getPlayer().f_19853_.m_5776_()) {
         pms.setCameraAngleOrdinal(Minecraft.m_91087_().f_91066_.m_92176_().ordinal());
      }

      this.getTagController().m_128350_("cs.initViewAngle", this.getPlayer().m_146909_());
   }

   @Override
   public void tickAttack() {
      this.getPlayer().m_146926_(90.0F);
      this.setCameraAngle(this.player, 1);
      if (this.getTimerProgress() >= 15 && this.getTimerProgress() % (this.checkDualWield(this.player, AquafloraItem.class) ? 2 : 5) == 0) {
         Predicate<Entity> filter = e -> {
            if (e != this.player
               && e instanceof LivingEntity le
               && (this.player.m_142582_(le) || le.m_142582_(this.player))
               && le.m_6084_()
               && !this.player.m_7307_(le)) {
               return true;
            }

            return false;
         };
         List<LivingEntity> entities = this.iterateEntities(this.getPlayer().f_19853_, this.createAABB(this.player.m_20183_(), 12.0))
            .stream()
            .filter(filter)
            .map(LivingEntity.class::cast)
            .toList();
         LivingEntity target = entities.size() > 0 ? entities.get(this.getPlayer().f_19853_.f_46441_.m_188503_(entities.size())) : null;
         if (target == this.player || target == null) {
            AnimationManager.playAnimation(this.getPlayer().f_19853_, AnimationManager.AnimationsList.CLEAR);
            this.getTagController().m_128405_("cs.animationTimer", 0);
            this.getTagController().m_128379_("cs.hasAnimationBegun", false);
            this.getPlayer().m_146926_(this.getTagController().m_128457_("cs.initViewAngle"));
            this.setCameraAngle(this.player, this.getTagController().m_128451_("cs.initPerspective"));
            return;
         }

         double offsetX = (double)(-4 + this.getPlayer().f_19853_.f_46441_.m_188503_(8));
         double offsetZ = (double)(-4 + this.getPlayer().f_19853_.f_46441_.m_188503_(8));
         if (this.getPlayer().f_19853_.m_5776_()) {
            double dx = target.m_20185_() - (this.player.m_20185_() + offsetX);
            double dz = target.m_20189_() - (this.player.m_20189_() + offsetZ);
            double yaw = -Math.atan2(dx, dz);
            yaw *= 180.0 / Math.PI;
            yaw += (double)(yaw < 0.0 ? 360 : 0);
            this.getPlayer().m_146922_((float)yaw);
         }

         CSEffectEntity.createInstance(this.player, null, (CSVisualType)CSVisualTypes.AQUAFLORA_DASH.get(), 0.0, 0.55, 0.0);
         this.getPlayer().m_20035_(target.m_20183_().m_7637_(offsetX, 1.0, offsetZ), this.getPlayer().m_146908_(), this.getPlayer().m_146909_());
         CSEffectEntity.createInstance(this.player, target, (CSVisualType)CSVisualTypes.AQUAFLORA_ASSASSINATE.get(), 0.0, -0.2, 0.0);
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_BLING.get(), 0.15F, 0.5F);
         double dualWieldMultiplier = this.checkDualWield(this.player, AquafloraItem.class) ? 0.52 : 1.0;
         this.hurtNoKB(
            this.player,
            target,
            (float)((Double)CSConfigManager.COMMON.aquafloraBloomSkillDmg.get() * dualWieldMultiplier)
               + this.getSharpnessValue(this.getStack(), (float)(0.65 * dualWieldMultiplier))
         );
         createAquafloraFirework(this.getStack(), this.getPlayer().f_19853_, this.player, target.m_20185_(), target.m_20186_() + 1.0, target.m_20189_());
      }
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128379_("cs.atkOngoing", false);
      this.getPlayer().m_146926_(this.getTagController().m_128457_("cs.initViewAngle"));
      this.setCameraAngle(this.player, this.getTagController().m_128451_("cs.initPerspective"));
   }

   public static void createAquafloraFirework(ItemStack itemStack, Level level, Player player, double x, double y, double z) {
      ItemStack fireworkStarStack = new ItemStack(Items.f_42689_);
      CompoundTag starExplosionDataTag = fireworkStarStack.m_41698_("Explosion");
      List<Integer> list = Lists.newArrayList();
      DyeColor[] allowedColors = new DyeColor[]{DyeColor.PINK, DyeColor.GREEN, DyeColor.WHITE};
      list.add(allowedColors[level.f_46441_.m_188503_(allowedColors.length)].m_41070_());
      starExplosionDataTag.m_128408_("Colors", list);
      starExplosionDataTag.m_128344_("Type", (byte)Shape.SMALL_BALL.m_41236_());
      CompoundTag fireworkDataTag = itemStack.m_41698_("Fireworks");
      ListTag starDataListTag = new ListTag();
      CompoundTag explosionDataTag = fireworkStarStack.m_41737_("Explosion");
      if (explosionDataTag != null) {
         starDataListTag.add(explosionDataTag);
      }

      fireworkDataTag.m_128344_("Flight", (byte)3);
      if (!starDataListTag.isEmpty()) {
         fireworkDataTag.m_128365_("Explosions", starDataListTag);
      }

      player.f_19853_.m_7228_(x, y, z, 0.01, 0.01, 0.01, fireworkDataTag);
   }
}
