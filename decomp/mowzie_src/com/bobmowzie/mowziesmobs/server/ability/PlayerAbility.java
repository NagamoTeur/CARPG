package com.bobmowzie.mowziesmobs.server.ability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class PlayerAbility extends Ability<Player> {
   protected AnimationBuilder activeFirstPersonAnimation;
   protected ItemStack heldItemMainHandVisualOverride;
   protected ItemStack heldItemOffHandVisualOverride;
   protected PlayerAbility.HandDisplay firstPersonMainHandDisplay;
   protected PlayerAbility.HandDisplay firstPersonOffHandDisplay;

   public PlayerAbility(AbilityType<Player, ? extends Ability> abilityType, Player user, AbilitySection[] sectionTrack, int cooldownMax) {
      super(abilityType, user, sectionTrack, cooldownMax);
      if (user.f_19853_.f_46443_) {
         this.activeAnimation = new AnimationBuilder().addAnimation("idle");
         this.heldItemMainHandVisualOverride = null;
         this.heldItemOffHandVisualOverride = null;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
      }
   }

   public PlayerAbility(AbilityType<Player, ? extends Ability> abilityType, Player user, AbilitySection[] sectionTrack) {
      this(abilityType, user, sectionTrack, 0);
   }

   public void playAnimation(String animationName, GeckoPlayer.Perspective perspective, boolean shouldLoop) {
      if (this.getUser() != null && this.getUser().f_19853_.m_5776_()) {
         AnimationBuilder newActiveAnimation = new AnimationBuilder().addAnimation(animationName, shouldLoop);
         if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
            this.activeFirstPersonAnimation = newActiveAnimation;
         } else {
            this.activeAnimation = newActiveAnimation;
         }

         MowzieAnimationController<GeckoPlayer> controller = GeckoPlayer.getAnimationController(this.getUser(), perspective);
         GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), perspective);
         if (controller != null && geckoPlayer != null) {
            controller.playAnimation(geckoPlayer, newActiveAnimation);
         }
      }
   }

   @Override
   public void playAnimation(String animationName, boolean shouldLoop) {
      this.playAnimation(animationName, GeckoPlayer.Perspective.FIRST_PERSON, shouldLoop);
      this.playAnimation(animationName, GeckoPlayer.Perspective.THIRD_PERSON, shouldLoop);
   }

   @Override
   public void end() {
      super.end();
      if (this.getUser().f_19853_.f_46443_) {
         this.heldItemMainHandVisualOverride = null;
         this.heldItemOffHandVisualOverride = null;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
      }
   }

   @Override
   public boolean canUse() {
      return super.canUse() && !this.getUser().m_5833_();
   }

   @Override
   protected boolean canContinueUsing() {
      return super.canContinueUsing() && !this.getUser().m_5833_();
   }

   @Override
   public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
      AnimationBuilder whichAnimation;
      if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
         whichAnimation = this.activeFirstPersonAnimation;
      } else {
         whichAnimation = this.activeAnimation;
      }

      if (whichAnimation != null && !whichAnimation.getRawAnimationList().isEmpty()) {
         e.getController().setAnimation(whichAnimation);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack heldItemMainHandOverride() {
      return this.heldItemMainHandVisualOverride;
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack heldItemOffHandOverride() {
      return this.heldItemOffHandVisualOverride;
   }

   @OnlyIn(Dist.CLIENT)
   public PlayerAbility.HandDisplay getFirstPersonMainHandDisplay() {
      return this.firstPersonMainHandDisplay;
   }

   @OnlyIn(Dist.CLIENT)
   public PlayerAbility.HandDisplay getFirstPersonOffHandDisplay() {
      return this.firstPersonOffHandDisplay;
   }

   public void onRightClickEmpty(RightClickEmpty event) {
   }

   public void onRightClickBlock(RightClickBlock event) {
   }

   public void onRightClickWithItem(RightClickItem event) {
   }

   public void onRightClickEntity(EntityInteract event) {
   }

   public void onLeftClickEmpty(LeftClickEmpty event) {
   }

   public void onLeftClickBlock(LeftClickBlock event) {
   }

   public void onLeftClickEntity(AttackEntityEvent event) {
   }

   @Override
   public void onTakeDamage(LivingHurtEvent event) {
   }

   public void onJump(LivingJumpEvent event) {
   }

   public void onRightMouseDown(Player player) {
   }

   public void onLeftMouseDown(Player player) {
   }

   public void onRightMouseUp(Player player) {
   }

   public void onLeftMouseUp(Player player) {
   }

   public void onSneakDown(Player player) {
   }

   public void onSneakUp(Player player) {
   }

   public static enum HandDisplay {
      DEFAULT,
      DONT_RENDER,
      FORCE_RENDER;
   }
}
