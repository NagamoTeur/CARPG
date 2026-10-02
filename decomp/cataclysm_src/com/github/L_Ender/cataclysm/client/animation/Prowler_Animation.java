package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Prowler_Animation {
   public static final AnimationDefinition MELEE = Builder.m_232275_(2.5F)
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(5.0F, -45.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(10.0F, -75.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(10.0F, -80.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(10.0F, -80.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(6.1448F, -54.659F, -3.9992F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(5.2282F, -47.1956F, -2.821F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(4.9978F, -44.7055F, -2.5006F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.2305F, -47.2001F, -2.8206F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(5.2305F, -47.2001F, -2.8206F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(5.2305F, -47.2001F, -2.8206F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 2.5F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-10.5202F, -3.3467F, 14.8053F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-24.841F, -15.06F, 66.6238F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-29.8408F, -15.06F, 66.6229F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-29.65F, -12.47F, 65.7F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-29.163F, -6.5612F, 62.661F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-26.5436F, -4.3755F, 61.4386F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-26.4801F, -2.1383F, 60.3209F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-26.4801F, -2.1383F, 60.3209F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-26.48F, -2.14F, 60.32F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-1.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(65.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(64.9791F, -2.2656F, 1.0571F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(64.9791F, -2.2656F, 1.0571F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(64.98F, -2.27F, 1.06F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "saw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-887.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-1067.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-5747.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition STRONG_ATTACK = Builder.m_232275_(2.25F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-5.019F, -4.9809F, 0.4369F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(25.6288F, 21.9184F, 4.7966F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(35.6076F, 30.5855F, 10.365F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(39.0129F, 32.6066F, 12.0926F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(39.0129F, 32.6066F, 12.0926F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(37.6446F, 28.0644F, 9.5368F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-31.369F, -40.5497F, 16.175F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-45.8059F, -35.613F, 23.3561F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-47.1462F, -37.3349F, 25.6103F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-47.15F, -37.33F, 25.61F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-52.15F, -37.33F, 25.61F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-8.4326F, -13.9815F, 3.0092F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(23.6651F, -4.1864F, 9.0998F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(24.2477F, -6.2797F, 13.6497F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(24.25F, -6.28F, 13.65F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(29.25F, -6.28F, 13.65F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-62.259F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-104.759F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-109.759F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-109.759F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-114.759F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-44.759F, 16.0208F, 25.0645F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(2.5F, -1.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, -7.5F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(50.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(50.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(55.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(22.5F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(20.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(20.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(12.5F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "chainsaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -12.5F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -12.5F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "saw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(180.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-180.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(3240.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SPIN = Builder.m_232275_(2.5F)
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, -50.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, -52.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -52.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -52.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, -62.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, -67.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 367.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 475.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 507.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 510.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(0.0F, 512.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 445.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 392.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 362.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, 360.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.99F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -0.25F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, -0.25F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 1.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -5.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -2.5F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 5.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 45.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 42.72F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 48.05F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 50.0F), Interpolations.f_232229_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 38.33F), Interpolations.f_232229_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 14.17F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "chainsaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 40.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "saw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(540.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-4320.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition PIERCE = Builder.m_232275_(4.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -2.0F), Interpolations.f_232229_),
               new Keyframe(3.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 47.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, -42.08F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(0.0F, -43.87F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, -43.87F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, -45.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(0.0F, -50.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232331_(0.0F, -60.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(0.0F, -80.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232331_(0.0F, -120.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.9167F, KeyframeAnimations.m_232331_(0.0F, -115.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, -102.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, -77.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(0.0F, 72.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.3333F, KeyframeAnimations.m_232331_(0.0F, 77.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.4167F, KeyframeAnimations.m_232331_(0.0F, 80.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(0.0F, 80.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(0.0F, 75.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.9167F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -2.0F), Interpolations.f_232229_),
               new Keyframe(3.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(360.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile2",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile3",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(50.17F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.2083F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(-45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.9167F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232229_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 32.5F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232229_),
               new Keyframe(3.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-19.3309F, 2.5148F, -0.7528F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(15.0F, 15.0F, -5.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(35.0F, 25.0F, -10.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(40.0F, 25.0F, -15.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(67.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(67.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.9167F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "chainsaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 22.5F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 42.5F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 42.5F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 55.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 70.0F), Interpolations.f_232229_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 110.0F), Interpolations.f_232229_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 90.0F), Interpolations.f_232229_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 90.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 45.0F), Interpolations.f_232229_),
               new Keyframe(3.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "saw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(-4352.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition LASER = Builder.m_232275_(4.5F)
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-55.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-32.22F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-30.87F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-36.52F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-55.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.125F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 3.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 3.0F), Interpolations.f_232229_),
               new Keyframe(3.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 2.25F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.125F, KeyframeAnimations.m_232302_(0.0F, 2.25F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-360.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile2",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "missile3",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(13.63F, 12.14F, 5.93F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(57.27F, 24.29F, 11.87F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(63.4009F, 36.4313F, 17.8023F), Interpolations.f_232229_),
               new Keyframe(4.25F, KeyframeAnimations.m_232331_(63.4009F, 36.4313F, 17.8023F), Interpolations.f_232229_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_joint",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition IDLE = Builder.m_232275_(1.0F)
      .m_232274_()
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -0.75F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0417F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "saw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-360.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "pipe2",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0417F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "pipe",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition STUN = Builder.m_232275_(3.0F)
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-42.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.875F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.9167F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.9583F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.375F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232302_(-0.75F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(-0.25F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition DEATH = Builder.m_232275_(1.125F)
      .m_232279_(
         "upperbody",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-42.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "eye_blow",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "rocket_luncher",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(55.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-87.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-95.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-97.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-72.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
}
