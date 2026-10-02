package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Netherite_Monstrosity_Animation {
   public static final AnimationDefinition IDLE = Builder.m_232275_(2.25F)
      .m_232274_()
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232282_();
   public static final AnimationDefinition WALK = Builder.m_232275_(2.2083F)
      .m_232274_()
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -5.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-0.329F, 7.4928F, 4.9785F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(0.0F, -5.0F, -5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(11.847F, 7.0494F, -8.7386F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(7.4366F, -0.9762F, 7.4366F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(11.847F, 7.0494F, -8.7386F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.4F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 4.5F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.5F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-5.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 9.5F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SMASH = Builder.m_232275_(2.875F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-22.5131F, -4.6691F, -7.7096F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(2.4621F, -0.434F, 9.9907F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-142.448F, -31.7449F, -3.357F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-111.3411F, 9.9509F, -9.0921F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-111.3411F, 9.9509F, -9.0921F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 15.0F, -12.4F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-14.5182F, 4.7511F, 11.5752F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-14.5182F, 4.7511F, 11.5752F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-142.5206F, 26.4438F, -1.9855F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-111.3411F, -9.9509F, 9.0921F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-111.3411F, -9.9509F, 9.0921F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 15.0F, -12.4F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-14.5182F, -4.7511F, -11.5752F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-14.5182F, -4.7511F, -11.5752F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 7.07F, -11.85F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -19.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -19.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SLEEP = Builder.m_232275_(0.0F)
      .m_232274_()
      .m_232279_(
         "lowerbody",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, 37.5F, -90.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(13.0F, 0.0F, -7.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, -37.5F, 90.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(-13.0F, 0.0F, -7.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -7.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232282_();
   public static final AnimationDefinition AWAKE = Builder.m_232275_(2.0F)
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, 37.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-90.0F, 32.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-90.0F, 37.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(13.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(13.0F, -3.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(13.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, -37.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-90.0F, -32.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-90.0F, -37.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-13.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(-13.0F, -3.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-13.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition PHASE = Builder.m_232275_(2.6667F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-22.5131F, -4.6691F, -7.7096F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-47.6463F, -15.2879F, 16.9096F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-66.946F, 17.1391F, -16.4022F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 9.01F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(2.4621F, -0.434F, 9.9907F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(54.0188F, 9.8466F, 17.4952F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(34.121F, -4.5529F, -7.5438F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(31.9476F, 10.8012F, 6.2734F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-142.448F, -31.7449F, -3.357F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-53.2072F, 24.2886F, 1.4437F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-53.2072F, 24.2886F, 1.4437F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-111.3411F, 9.9509F, -9.0921F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 15.0F, -12.4F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -9.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, -9.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-51.4145F, 18.6346F, -9.7203F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-14.5182F, 4.7511F, 11.5752F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-142.5206F, 26.4438F, -1.9855F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-47.926F, -23.3339F, -2.7127F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-47.926F, -23.3339F, -2.7127F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-111.3411F, -9.9509F, 9.0921F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 15.0F, -12.4F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(7.0F, 0.0F, -26.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(7.0F, 0.0F, -26.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -18.0F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-50.722F, -17.3307F, 7.4776F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-14.5182F, -4.7511F, -11.5752F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(12.5F, 0.0F, -32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 7.07F, -11.85F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -19.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -19.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition FIRE = Builder.m_232275_(2.1667F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(90.0F, -57.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(90.0F, -57.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(90.0F, -42.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(90.0F, -42.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(90.0F, -57.5F, -90.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(14.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(14.0F, -14.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(14.0F, -20.0F, 7.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(14.0F, -14.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 9.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 6.74F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 6.74F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(90.0F, 57.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(90.0F, 57.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(90.0F, 42.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(90.0F, 42.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(90.0F, 57.5F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-14.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-14.0F, -14.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-14.0F, -21.0F, 7.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(-14.0F, -14.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 7.38F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 7.38F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SHOULDER_CHECK = Builder.m_232275_(3.5F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(28.4032F, -39.8557F, -22.9098F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(28.4032F, -39.8557F, -22.9098F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(49.0085F, -46.3034F, -44.1462F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(49.0085F, -46.3034F, -44.1462F), Interpolations.f_232230_),
               new Keyframe(2.9167F, KeyframeAnimations.m_232331_(53.5827F, -49.0814F, -50.3268F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-24.0065F, 38.3962F, -7.3262F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-24.0065F, 38.3962F, -7.3262F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(-24.0065F, 38.3962F, -7.3262F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(-36.5065F, 38.3962F, -7.3262F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-6.11F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232331_(-6.11F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(26.331F, -11.1448F, -48.5787F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(26.331F, -11.1448F, -48.5787F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(26.331F, -11.1448F, -48.5787F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(26.331F, -11.1448F, -48.5787F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(1.2246F, 3.7662F, -46.9397F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-16.7549F, -31.4118F, 46.1789F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-16.7549F, -31.4118F, 46.1789F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(-23.7805F, -38.3299F, 49.7147F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(10.2463F, 25.2711F, 50.6947F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -14.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -14.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, -0.54F, 10.92F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 35.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(-92.5F, 0.0F, 35.0F), Interpolations.f_232230_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(-5.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.4F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 4.5F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.5F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 4.5F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.5F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 4.5F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 7.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -10.5F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-5.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-5.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-5.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 9.5F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 9.5F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 9.5F, -1.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.3F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition OVERPOWER = Builder.m_232275_(3.75F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-24.113F, 5.1752F, 11.3939F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-42.6415F, -8.2478F, -2.5673F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232331_(-42.6415F, -8.2478F, -2.5673F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(8.7664F, 0.8233F, -7.4549F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-1.2336F, 0.8233F, -7.4549F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(12.4F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-140.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-140.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, -33.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, -32.18F, 10.05F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 18.0F, -27.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 18.0F, -27.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(12.4F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-140.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-140.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, -33.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, -31.18F, 10.05F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 18.0F, -27.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 18.0F, -27.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 9.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 9.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 5.0F, -8.68F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -13.0F), Interpolations.f_232230_),
               new Keyframe(3.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition DRAIN = Builder.m_232275_(2.9583F)
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 90.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, 90.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-90.0F, 60.0F, -90.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-90.0F, 60.0F, -90.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(6.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(6.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(8.0F, -20.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(8.0F, -20.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -90.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, -90.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-90.0F, -60.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(-90.0F, -60.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-6.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-6.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-8.0F, -20.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(-8.0F, -20.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition DEATH = Builder.m_232275_(2.5F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, -1.17F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, -18.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -17.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(8.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-7.5095F, 4.9952F, -0.2187F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-9.7127F, -1.8627F, 0.3718F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-7.5095F, 4.9952F, -0.2187F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(23.7256F, -9.869F, -5.5144F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(23.7256F, -9.869F, -5.5144F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-5.85F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(24.9011F, -2.6841F, 4.22F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(28.3882F, -3.2722F, -13.7069F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(23.7348F, 13.7408F, -3.2204F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(1.1069F, -12.4517F, -5.1208F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.7471F, -12.1991F, -12.7936F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(3.7757F, -44.6505F, -14.8681F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(3.7757F, -44.6505F, -14.8681F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, -21.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, -14.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-51.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(15.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(15.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, -27.5F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, -27.5F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(42.2835F, 10.8512F, 30.5305F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(56.7259F, -18.5362F, 13.8987F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(1.1069F, 12.4517F, 5.1208F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.7471F, 12.1991F, 12.7936F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(3.4887F, 39.6605F, 14.4409F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(3.4887F, 39.6605F, 14.4409F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, -21.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, -17.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-53.8F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(48.4599F, -15.026F, -22.5679F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(40.7307F, 13.3598F, -15.0213F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-90.0F, 22.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-60.0F, -6.25F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-90.0F, -12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition FLARE_SHOT = Builder.m_232275_(3.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lowerbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(28.627F, 15.4697F, 8.2834F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(28.627F, 15.4697F, 8.2834F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(28.627F, 15.4697F, 8.2834F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(27.5358F, 7.0841F, 2.7225F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(28.627F, 15.4697F, 8.2834F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-23.2433F, -10.7008F, -11.6839F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-23.2433F, -10.7008F, -11.6839F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(27.4106F, -2.3064F, 4.4375F), Interpolations.f_232230_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-89.333F, -14.5264F, -16.6989F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-89.333F, -14.5264F, -16.6989F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-96.8523F, -4.527F, -16.5828F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-89.333F, -14.5264F, -16.6989F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 9.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.4864F, -0.6469F, -2.4149F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-7.4864F, -0.6469F, -2.4149F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-7.4864F, -0.6469F, -2.4149F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lefthand",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_hand_blast_4",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(36.9776F, -5.2483F, 36.3608F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_hand_blast_3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(25.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(25.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(36.8383F, 7.5713F, -34.9753F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-46.7368F, -24.3683F, 43.7546F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-46.7368F, -24.3683F, 43.7546F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_hand_blast_2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-36.9776F, -5.2483F, -36.3608F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(34.2368F, 24.3683F, 43.7546F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(34.2368F, 24.3683F, 43.7546F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_hand_blast_1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-36.9776F, 5.2483F, 36.3608F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(30.0F, 0.0F, -62.5F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(30.0F, 0.0F, -62.5F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_cannon",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -14.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_cannon",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_core",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(1440.0F, 1440.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_core",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_core",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232298_(3.0, 3.0, 3.0), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(15.2207F, 9.6559F, 15.113F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(21.2006F, 23.222F, 30.5946F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(21.2006F, 23.222F, 30.5946F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightarm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-0.5373F, 16.404F, 23.0456F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "righthand",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-23.1277F, 19.6834F, -3.6171F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-23.1277F, 19.6834F, -3.6171F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightfinger3",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(15.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(15.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rightleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 9.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 9.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "leftleg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 6.0F, -17.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -17.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -17.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.04F, -0.38F, -0.07F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(-0.04F, -0.38F, -0.07F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(-0.04F, -0.33F, 1.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.96F, -0.28F, 0.83F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-0.24F, -0.23F, 0.66F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(-0.19F, -0.18F, -0.51F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-0.14F, -0.14F, 0.66F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(0.9F, -0.1F, 0.33F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(-0.05F, -0.05F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(1.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-0.07F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(-0.06F, 0.0F, 1.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-0.06F, 0.0F, -0.08F), Interpolations.f_232229_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(-1.05F, 0.0F, -0.07F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(-0.95F, 0.0F, -1.06F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-0.85F, 0.0F, 0.05F), Interpolations.f_232229_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.24F, 0.0F, 0.04F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(-1.79F, 0.0F, 0.04F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-1.62F, -0.07F, 11.02F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-1.79F, 0.0F, 0.04F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(-0.04F, -0.38F, -0.07F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
}
