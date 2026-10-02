package io.redspace.ironsspellbooks.player;

import com.mojang.math.Vector3f;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.gui.EldritchResearchScreen;
import io.redspace.ironsspellbooks.network.ClientboundCastErrorMessage;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.spells.ender.TeleportSpell;
import io.redspace.ironsspellbooks.spells.ice.FrostStepSpell;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class ClientSpellCastHelper {
   private static boolean suppressRightClicks;

   public static boolean shouldSuppressRightClicks() {
      return suppressRightClicks;
   }

   public static void setSuppressRightClicks(boolean suppressRightClicks) {
      ClientSpellCastHelper.suppressRightClicks = suppressRightClicks;
   }

   public static void openEldritchResearchScreen(InteractionHand hand) {
      Minecraft.m_91087_().m_91152_(new EldritchResearchScreen(Component.m_237119_(), hand));
   }

   public static void handleCastErrorMessage(ClientboundCastErrorMessage packet) {
      AbstractSpell spell = SpellRegistry.getSpell(packet.spellId);
      if (packet.errorType == ClientboundCastErrorMessage.ErrorType.COOLDOWN) {
         if (ClientInputEvents.hasReleasedSinceCasting) {
            Minecraft.m_91087_()
               .f_91065_
               .m_93063_(
                  Component.m_237110_("ui.irons_spellbooks.cast_error_cooldown", new Object[]{spell.getDisplayName(Minecraft.m_91087_().f_91074_)})
                     .m_130940_(ChatFormatting.RED),
                  false
               );
         }
      } else {
         Minecraft.m_91087_()
            .f_91065_
            .m_93063_(
               Component.m_237110_("ui.irons_spellbooks.cast_error_mana", new Object[]{spell.getDisplayName(Minecraft.m_91087_().f_91074_)})
                  .m_130940_(ChatFormatting.RED),
               false
            );
      }
   }

   public static void handleClientboundBloodSiphonParticles(Vec3 pos1, Vec3 pos2) {
      if (Minecraft.m_91087_().f_91074_ != null) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         Vec3 direction = pos2.m_82546_(pos1).m_82490_(0.1F);

         for (int i = 0; i < 40; i++) {
            Vec3 scaledDirection = direction.m_82490_(1.0 + Utils.getRandomScaled(0.35));
            Vec3 random = new Vec3(Utils.getRandomScaled(0.08F), Utils.getRandomScaled(0.08F), Utils.getRandomScaled(0.08F));
            level.m_7106_(
               ParticleHelper.BLOOD,
               pos1.f_82479_,
               pos1.f_82480_,
               pos1.f_82481_,
               scaledDirection.f_82479_ + random.f_82479_,
               scaledDirection.f_82480_ + random.f_82480_,
               scaledDirection.f_82481_ + random.f_82481_
            );
         }
      }
   }

   public static void handleClientboundShockwaveParticle(Vec3 pos, float radius, ParticleType<?> particleType) {
      if (Minecraft.m_91087_().f_91074_ != null && particleType instanceof ParticleOptions) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         int count = (int)((float) (Math.PI * 2) * radius) * 2;
         float angle = 360.0F / (float)count * (float) (Math.PI / 180.0);

         for (int i = 0; i < count; i++) {
            Vec3 motion = new Vec3((double)(Mth.m_14089_(angle * (float)i) * radius), 0.0, (double)(Mth.m_14031_(angle * (float)i) * radius))
               .m_82490_((double)Utils.random.m_216332_(50, 70) * 0.00155);
            level.m_7106_(
               (ParticleOptions)particleType,
               pos.f_82479_ + motion.f_82479_ * 4.0,
               pos.f_82480_,
               pos.f_82481_ + motion.f_82481_ * 4.0,
               motion.f_82479_,
               motion.f_82480_,
               motion.f_82481_
            );
         }
      }
   }

   public static void handleClientsideHealParticles(Vec3 pos) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         int i = PotionUtils.m_43559_(Potion.m_43489_("healing"));
         double d0 = (double)(i >> 16 & 0xFF) / 255.0;
         double d1 = (double)(i >> 8 & 0xFF) / 255.0;
         double d2 = (double)(i >> 0 & 0xFF) / 255.0;

         for (int j = 0; j < 15; j++) {
            level.m_7106_(
               ParticleTypes.f_123811_,
               pos.f_82479_ + Utils.getRandomScaled(0.25),
               pos.f_82480_ + Utils.getRandomScaled(1.0) + 1.0,
               pos.f_82481_ + Utils.getRandomScaled(0.25),
               d0,
               d1,
               d2
            );
         }
      }
   }

   public static void handleClientsideAbsorptionParticles(Vec3 pos) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         int i = 16239960;
         double d0 = (double)(i >> 16 & 0xFF) / 255.0;
         double d1 = (double)(i >> 8 & 0xFF) / 255.0;
         double d2 = (double)(i >> 0 & 0xFF) / 255.0;

         for (int j = 0; j < 15; j++) {
            level.m_7106_(
               ParticleTypes.f_123811_,
               pos.f_82479_ + Utils.getRandomScaled(0.25),
               pos.f_82480_ + Utils.getRandomScaled(1.0),
               pos.f_82481_ + Utils.getRandomScaled(0.25),
               d0,
               d1,
               d2
            );
         }
      }
   }

   public static void handleClientboundOakskinParticles(Vec3 pos) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      RandomSource randomsource = player.m_217043_();

      for (int i = 0; i < 50; i++) {
         double d0 = (double)Mth.m_216283_(randomsource, -0.5F, 0.5F);
         double d1 = (double)Mth.m_216283_(randomsource, 0.0F, 2.0F);
         double d2 = (double)Mth.m_216283_(randomsource, -0.5F, 0.5F);
         ParticleOptions particleType = (ParticleOptions)(randomsource.m_188501_() < 0.1F
            ? ParticleHelper.FIREFLY
            : new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_50011_.m_49966_()));
         player.f_19853_.m_7106_(particleType, pos.f_82479_ + d0, pos.f_82480_ + d1, pos.f_82481_ + d2, d0 * 0.05, 0.05, d2 * 0.05);
      }
   }

   public static void handleClientsideRegenCloudParticles(Vec3 pos) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = player.f_19853_;
         int ySteps = 16;
         int xSteps = 48;
         float yDeg = 180.0F / (float)ySteps * (float) (Math.PI / 180.0);
         float xDeg = 360.0F / (float)xSteps * (float) (Math.PI / 180.0);

         for (int x = 0; x < xSteps; x++) {
            for (int y = 0; y < ySteps; y++) {
               Vec3 offset = new Vec3(0.0, 0.0, 5.0)
                  .m_82524_((float)y * yDeg)
                  .m_82496_((float)x * xDeg)
                  .m_82535_((float) (-Math.PI / 2))
                  .m_82542_(1.0, 0.85F, 1.0);
               level.m_7106_(
                  DustParticleOptions.f_123656_, pos.f_82479_ + offset.f_82479_, pos.f_82480_ + offset.f_82480_, pos.f_82481_ + offset.f_82481_, 0.0, 0.0, 0.0
               );
            }
         }
      }
   }

   public static void handleClientsideFortifyAreaParticles(Vec3 pos) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = player.f_19853_;
         int ySteps = 128;
         float yDeg = 180.0F / (float)ySteps * (float) (Math.PI / 180.0);

         for (int y = 0; y < ySteps; y++) {
            Vec3 offset = new Vec3(0.0, 0.0, 16.0).m_82524_((float)y * yDeg);
            Vec3 motion = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).m_82490_(0.1);
            level.m_7106_(
               ParticleHelper.WISP,
               pos.f_82479_ + offset.f_82479_,
               1.0 + pos.f_82480_ + offset.f_82480_,
               pos.f_82481_ + offset.f_82481_,
               motion.f_82479_,
               motion.f_82480_,
               motion.f_82481_
            );
         }
      }
   }

   private static void animatePlayerStart(Player player, ResourceLocation resourceLocation) {
      KeyframeAnimation keyframeAnimation = PlayerAnimationRegistry.getAnimation(resourceLocation);
      if (keyframeAnimation != null) {
         ModifierLayer<IAnimation> animation = (ModifierLayer<IAnimation>)PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer)player)
            .get(SpellAnimations.ANIMATION_RESOURCE);
         if (animation != null) {
            KeyframeAnimationPlayer castingAnimationPlayer = new KeyframeAnimationPlayer(keyframeAnimation);
            ClientMagicData.castingAnimationPlayerLookup.put(player.m_20148_(), castingAnimationPlayer);
            Boolean armsFlag = (Boolean)ClientConfigs.SHOW_FIRST_PERSON_ARMS.get();
            Boolean itemsFlag = (Boolean)ClientConfigs.SHOW_FIRST_PERSON_ITEMS.get();
            if (!armsFlag && !itemsFlag) {
               castingAnimationPlayer.setFirstPersonMode(FirstPersonMode.DISABLED);
            } else {
               castingAnimationPlayer.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
               castingAnimationPlayer.setFirstPersonConfiguration(new FirstPersonConfiguration(armsFlag, armsFlag, itemsFlag, itemsFlag));
            }

            animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(2, Ease.INOUTSINE), castingAnimationPlayer, true);
         }
      }
   }

   public static void handleClientboundOnClientCast(String spellId, int level, CastSource castSource, ICastData castData) {
      AbstractSpell spell = SpellRegistry.getSpell(spellId);
      spell.onClientCast(Minecraft.m_91087_().f_91074_.f_19853_, level, Minecraft.m_91087_().f_91074_, castData);
   }

   public static void handleClientboundTeleport(Vec3 pos1, Vec3 pos2) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         TeleportSpell.particleCloud(level, pos1);
         TeleportSpell.particleCloud(level, pos2);
      }
   }

   public static void handleClientboundFieryExplosion(Vec3 pos, float radius) {
      MinecraftInstanceHelper.ifPlayerPresent(
         player -> {
            Level level = player.f_19853_;
            double x = pos.f_82479_;
            double y = pos.f_82480_;
            double z = pos.f_82481_;
            level.m_7106_(new BlastwaveParticleOptions(new Vector3f(1.0F, 0.6F, 0.3F), radius + 1.0F), x, y, z, 0.0, 0.0, 0.0);
            int c = (int)(6.28 * (double)radius) * 3;
            float step = 360.0F / (float)c * (float) (Math.PI / 180.0);
            float speed = 0.06F + 0.01F * radius;

            for (int i = 0; i < c; i++) {
               Vec3 vec3 = new Vec3((double)Mth.m_14089_(step * (float)i), 0.0, (double)Mth.m_14031_(step * (float)i)).m_82490_((double)speed);
               Vec3 posOffset = Utils.getRandomVec3(0.5).m_82549_(vec3.m_82490_(10.0));
               vec3 = vec3.m_82549_(Utils.getRandomVec3(0.01));
               level.m_7106_(
                  ParticleTypes.f_123777_, x + posOffset.f_82479_, y + posOffset.f_82480_, z + posOffset.f_82481_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_
               );
            }

            int cloudDensity = 50 + (int)(25.0F * radius);

            for (int i = 0; i < cloudDensity; i++) {
               Vec3 posOffset = Utils.getRandomVec3(1.0).m_82490_((double)(radius * 0.4F));
               Vec3 motion = posOffset.m_82541_().m_82490_((double)(speed * 0.5F));
               posOffset = posOffset.m_82549_(motion.m_82490_(Utils.getRandomScaled(1.0)));
               motion = motion.m_82549_(Utils.getRandomVec3(0.02));
               level.m_7106_(
                  ParticleTypes.f_123777_,
                  x + posOffset.f_82479_,
                  y + posOffset.f_82480_,
                  z + posOffset.f_82481_,
                  motion.f_82479_,
                  motion.f_82480_,
                  motion.f_82481_
               );
            }

            for (int i = 0; i < cloudDensity; i += 2) {
               Vec3 posOffset = Utils.getRandomVec3(1.0).m_82490_((double)(radius * 0.4F));
               Vec3 motion = posOffset.m_82541_().m_82490_((double)(speed * 0.5F));
               motion = motion.m_82549_(Utils.getRandomVec3(0.25));
               level.m_6493_(
                  ParticleHelper.EMBERS,
                  true,
                  x + posOffset.f_82479_,
                  y + posOffset.f_82480_,
                  z + posOffset.f_82481_,
                  motion.f_82479_,
                  motion.f_82480_,
                  motion.f_82481_
               );
               level.m_7106_(
                  ParticleHelper.FIRE,
                  x + posOffset.f_82479_ * 0.5,
                  y + posOffset.f_82480_ * 0.5,
                  z + posOffset.f_82481_ * 0.5,
                  motion.f_82479_,
                  motion.f_82480_,
                  motion.f_82481_
               );
            }

            for (int i = 0; i < cloudDensity; i += 2) {
               Vec3 posOffset = Utils.getRandomVec3((double)radius).m_82490_(0.2F);
               Vec3 motion = posOffset.m_82541_().m_82490_(0.6);
               motion = motion.m_82549_(Utils.getRandomVec3(0.18));
               level.m_7106_(
                  ParticleHelper.FIERY_SPARKS,
                  x + posOffset.f_82479_ * 0.5,
                  y + posOffset.f_82480_ * 0.5,
                  z + posOffset.f_82481_ * 0.5,
                  motion.f_82479_,
                  motion.f_82480_,
                  motion.f_82481_
               );
            }
         }
      );
   }

   public static void handleClientboundFrostStep(Vec3 pos1, Vec3 pos2) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Level level = Minecraft.m_91087_().f_91074_.f_19853_;
         FrostStepSpell.particleCloud(level, pos1);
         FrostStepSpell.particleCloud(level, pos2);
      }
   }

   public static void handleClientBoundOnCastStarted(UUID castingEntityId, String spellId, int spellLevel) {
      Player player = Minecraft.m_91087_().f_91074_.f_19853_.m_46003_(castingEntityId);
      AbstractSpell spell = SpellRegistry.getSpell(spellId);
      spell.getCastStartAnimation().getForPlayer().ifPresent(resourceLocation -> animatePlayerStart(player, resourceLocation));
      spell.onClientPreCast(player.f_19853_, spellLevel, player, player.m_7655_(), null);
   }

   public static void handleClientBoundOnCastFinished(UUID castingEntityId, String spellId, boolean cancelled) {
      ClientMagicData.resetClientCastState(castingEntityId);
      Player player = Minecraft.m_91087_().f_91074_.f_19853_.m_46003_(castingEntityId);
      AbstractSpell spell = SpellRegistry.getSpell(spellId);
      AnimationHolder finishAnimation = spell.getCastFinishAnimation();
      if (finishAnimation.getForPlayer().isPresent() && !cancelled) {
         animatePlayerStart(player, finishAnimation.getForPlayer().get());
      } else if (finishAnimation != AnimationHolder.pass() || cancelled) {
         KeyframeAnimationPlayer animationPlayer = ClientMagicData.castingAnimationPlayerLookup.getOrDefault(castingEntityId, null);
         if (animationPlayer != null) {
            animationPlayer.stop();
         }
      }

      if (cancelled && spell.stopSoundOnCancel()) {
         spell.getCastStartSound().ifPresent(soundEvent -> Minecraft.m_91087_().m_91106_().m_120386_(soundEvent.m_11660_(), null));
      }

      if (castingEntityId.equals(Minecraft.m_91087_().f_91074_.m_20148_()) && ClientInputEvents.isUseKeyDown) {
         ClientInputEvents.hasReleasedSinceCasting = false;
      }
   }
}
