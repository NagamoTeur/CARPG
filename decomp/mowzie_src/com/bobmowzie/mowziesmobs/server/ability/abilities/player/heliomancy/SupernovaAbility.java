package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SupernovaAbility extends PlayerAbility {
   private boolean leftClickDown;
   private boolean rightClickDown;
   private Vec3[] particleEmitter = new Vec3[1];

   public SupernovaAbility(AbilityType<Player, SupernovaAbility> abilityType, Player user) {
      super(abilityType, user, EntityUmvuthi.SupernovaAbility.SECTION_TRACK);
   }

   @Override
   public void start() {
      super.start();
      this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_SUPERNOVA_START.get(), 3.0F, 1.0F);
      this.playAnimation("supernova", false);
      if (this.getLevel().f_46443_) {
         this.heldItemMainHandVisualOverride = ItemStack.f_41583_;
         this.heldItemOffHandVisualOverride = ItemStack.f_41583_;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() != null && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : this.getUser().m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get()) && super.canUse();
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() < 84) {
         this.getUser().m_7292_(new MobEffectInstance(MobEffects.f_19597_, 2, 4, false, false));
      }

      if (this.getTicksInUse() == 30) {
         this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_SUPERNOVA_BLACKHOLE.get(), 2.0F, 1.2F);
      }

      if (this.getTicksInUse() < 30) {
         for (LivingEntity inRange : this.getEntityLivingBaseNearby(this.getUser(), 16.0, 16.0, 16.0, 16.0)) {
            if (!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_) {
               Vec3 diff = inRange.m_20182_().m_82546_(this.getUser().m_20182_().m_82520_(0.0, 3.0, 0.0));
               diff = diff.m_82541_().m_82490_(0.03);
               inRange.m_20256_(inRange.m_20184_().m_82546_(diff));
               if (inRange.m_20186_() < this.getUser().m_20186_() + 3.0) {
                  inRange.m_20256_(inRange.m_20184_().m_82520_(0.0, 0.075, 0.0));
               }
            }
         }
      }

      if (this.getLevel().f_46443_) {
         if (this.getUser() == Minecraft.m_91087_().f_91074_ && Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON) {
            GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), GeckoPlayer.Perspective.FIRST_PERSON);
            if (geckoPlayer != null) {
               GeckoFirstPersonRenderer renderPlayer = (GeckoFirstPersonRenderer)geckoPlayer.getPlayerRenderer();
               if (renderPlayer.particleEmitterRoot != null) {
                  this.particleEmitter[0] = renderPlayer.particleEmitterRoot;
               }
            }
         } else {
            GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), GeckoPlayer.Perspective.THIRD_PERSON);
            if (geckoPlayer != null) {
               GeckoRenderPlayer renderPlayer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
               if (renderPlayer.particleEmitterRoot != null) {
                  this.particleEmitter[0] = this.getUser()
                     .m_20182_()
                     .m_82549_(renderPlayer.particleEmitterRoot)
                     .m_82520_(0.0, (double)(this.getUser().m_20206_() / 2.0F + 0.3F), 0.0);
               }
            }
         }

         EntityUmvuthi.SupernovaAbility.superNovaEffects(this, this.particleEmitter, this.getLevel());
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().f_19853_.f_46443_) {
         EntitySuperNova superNova = new EntitySuperNova(
            (EntityType<? extends EntitySuperNova>)EntityHandler.SUPER_NOVA.get(),
            this.getUser().f_19853_,
            this.getUser(),
            this.getUser().m_20185_(),
            this.getUser().m_20186_() + (double)(this.getUser().m_20206_() / 2.0F),
            this.getUser().m_20189_()
         );
         this.getUser().f_19853_.m_7967_(superNova);
         MobEffectInstance sunsBlessingInstance = this.getUser().m_21124_((MobEffect)EffectHandler.SUNS_BLESSING.get());
         if (sunsBlessingInstance != null) {
            int duration = sunsBlessingInstance.m_19557_();
            this.getUser().m_21195_((MobEffect)EffectHandler.SUNS_BLESSING.get());
            int supernovaCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.supernovaCost.get() * 60 * 20;
            if (duration - supernovaCost > 0) {
               this.getUser().m_7292_(new MobEffectInstance((MobEffect)EffectHandler.SUNS_BLESSING.get(), duration - supernovaCost, 0, false, false));
            }
         }
      }
   }

   @Override
   public void onLeftMouseDown(Player player) {
      super.onLeftMouseDown(player);
      if (player == this.getUser()) {
         this.leftClickDown = true;
      }
   }

   @Override
   public void onLeftMouseUp(Player player) {
      super.onLeftMouseUp(player);
      if (player == this.getUser()) {
         this.leftClickDown = false;
      }
   }

   @Override
   public void onRightMouseDown(Player player) {
      super.onRightMouseDown(player);
      if (player == this.getUser()) {
         this.rightClickDown = true;
      }
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (player == this.getUser()) {
         this.rightClickDown = false;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getUser().m_6144_() && this.rightClickDown && this.leftClickDown) {
         AbilityHandler.INSTANCE.sendAbilityMessage(this.getUser(), AbilityHandler.SUPERNOVA_ABILITY);
      }
   }

   @Override
   public boolean canCancelActiveAbility() {
      Ability ability = this.getActiveAbility();
      return ability != null
         && (ability.getAbilityType() == AbilityHandler.SOLAR_FLARE_ABILITY || ability.getAbilityType() == AbilityHandler.SOLAR_BEAM_ABILITY)
         && ability.getTicksInUse() < 5;
   }
}
