package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Maledictus_Animation {
   public static final AnimationDefinition IDLE = Builder.m_232275_(2.0F)
      .m_232274_()
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -0.2F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -2.5F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, -42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SMASH_LEAP = Builder.m_232275_(0.8333F)
      .m_232279_(
         "roots",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -6.6F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-49.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-40.696F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-76.946F, 12.4879F, 2.5606F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(61.25F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-71.946F, -12.4879F, -2.5606F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-120.973F, -11.244F, -1.2803F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-5.0F, -10.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(133.75F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-4.1835F, -20.2281F, 15.7069F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.2999F, 34.6292F, 11.5422F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-94.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.7001F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-4.1835F, 20.2281F, -15.7069F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.2999F, -34.6292F, -11.5422F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-94.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-1.1338F, 14.9756F, -2.9187F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-6.8871F, -35.7432F, -9.0988F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-4.3005F, 14.7384F, -8.8319F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -142.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-1.1338F, -14.9756F, 2.9187F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-6.8871F, 35.7432F, 9.0988F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-4.3005F, -14.7384F, 8.8319F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 145.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SMASH_DESENT = Builder.m_232275_(1.5F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-76.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-77.8497F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-69.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(109.66F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-5.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-57.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-17.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, 0.09F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(122.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(85.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(38.4635F, -11.8491F, -9.2643F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(62.8783F, 4.9809F, 8.6822F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.2999F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-127.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-58.0224F, -5.2483F, 11.3608F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.2999F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-79.3975F, -3.762F, 20.2676F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-57.6541F, -9.3955F, -18.0283F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-18.6966F, 16.9567F, -5.4884F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(50.064F, -7.4355F, -0.9845F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-4.3005F, 14.7384F, -8.8319F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-6.2935F, 37.148F, -11.6747F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-3.688F, -14.2071F, -6.7734F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, -85.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-4.3005F, -14.7384F, 8.8319F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-6.7115F, -39.632F, 12.1795F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-3.7611F, 2.7192F, 7.4956F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 85.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition WALK = Builder.m_232275_(1.25F)
      .m_232274_()
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.1754F, 0.0F, 1.3378F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -1.16F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -1.22F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -0.16F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(13.9906F, 3.795F, -3.2653F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-18.214F, 0.5031F, -2.5834F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-22.8228F, -0.938F, -5.4F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-17.4684F, -0.2742F, -2.9822F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(13.9906F, 3.795F, -3.2653F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.6F, 2.0F, -0.63F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.5F, -1.07F, -2.63F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.48F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.52F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(51.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(45.23F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(3.18F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(32.7265F, 1.6044F, 0.2666F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(5.52F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-22.8228F, 0.938F, 5.4F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.4684F, 0.2742F, 2.9822F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(16.4877F, -3.9922F, 3.1723F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-18.214F, -0.5031F, 2.5834F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-22.8228F, 0.938F, 5.4F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.5F, -1.0F, -0.54F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.48F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.5F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.5F, 0.0F, -1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(3.18F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(32.7265F, -1.6044F, -0.2666F), Interpolations.f_232229_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(5.52F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(51.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(45.23F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(3.18F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(2.5F, 10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(4.8079F, 10.178F, 0.1633F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 10.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -0.6F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(25.2393F, 15.2713F, 15.7485F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(11.1123F, 0.0605F, 8.7421F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-10.3315F, -9.7465F, 4.2248F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-2.8351F, 12.6032F, -4.8288F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(25.2393F, 15.2713F, 15.7485F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.8351F, 12.6032F, -4.8288F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(2.0161F, -4.1219F, -11.6371F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(25.9215F, -19.7819F, -18.0119F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(2.0161F, -4.1219F, -11.6371F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-2.8351F, 12.6032F, -4.8288F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(4.9267F, -5.9673F, -0.5903F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(2.4881F, -5.0024F, 0.1094F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(4.9267F, -5.9673F, -0.5903F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.0031F, -2.6313F, -7.7614F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(5.9299F, 32.2409F, 7.7508F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-2.0031F, -2.6313F, -7.7614F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -77.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, -77.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-4.7535F, -14.6883F, 8.8722F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(5.9299F, -32.2409F, -7.7508F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-4.7535F, -14.6883F, 8.8722F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 77.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 77.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SHOOT = Builder.m_232275_(2.3333F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-45.0542F, 28.8066F, 1.9515F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-27.9221F, 67.5369F, -6.1779F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-27.9221F, 67.5369F, -6.1779F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-24.1703F, 52.1852F, 1.0788F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(60.72F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-6.9444F, -12.4808F, 2.4284F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-11.4266F, 0.2525F, -5.6778F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-11.265F, 1.4752F, -3.2608F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-8.1081F, -2.3922F, -5.3791F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(-25.7237F, -10.921F, -3.0884F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(12.28F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(16.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(39.06F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-2.9325F, 22.4097F, 5.4096F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-5.8536F, 23.0103F, 10.7909F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-4.7903F, 23.2422F, 13.4977F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-4.6346F, 18.2591F, 13.9368F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(16.7357F, -41.2675F, -11.2179F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(16.7357F, -41.2675F, -11.2179F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(20.8172F, -15.2092F, -27.8057F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(7.564F, 7.4355F, 10.9845F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-80.0F, 20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-100.5212F, 58.7909F, -5.9364F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-101.7495F, 62.0045F, -7.8159F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-102.4018F, 62.0492F, -7.2192F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-101.7495F, 62.0045F, -7.8159F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-102.4018F, 62.0492F, -7.2192F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-100.5212F, 58.7909F, -5.9364F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-64.571F, 44.3606F, 45.173F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 37.5F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 37.5F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(43.3818F, 0.0F, 32.6455F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow_string",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "string1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "string2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.3208F, -12.511F, 0.5541F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(7.564F, -7.4355F, -10.9845F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-74.4633F, 8.7219F, 11.8862F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-110.2334F, 6.4893F, -7.7716F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-110.2334F, 6.4893F, -7.7716F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-112.5267F, -26.2414F, 5.0057F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-8.801F, -1.154F, 92.0517F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-7.0863F, -2.1143F, 82.7458F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)})
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(12.5F, 42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(26.0187F, 39.5608F, 9.1544F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(24.1121F, 40.6202F, 6.1942F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-6.5717F, 45.102F, -9.0961F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_horn",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 17.86F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, -20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-1.0351F, 4.9774F, -2.7057F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 30.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, -71.41F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, -54.29F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -25.36F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 52.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-1.0351F, -4.9774F, 2.7057F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -23.21F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 60.86F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWING = Builder.m_232275_(2.1667F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -15.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, -12.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-74.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.543F, -5.0113F, 2.3934F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-19.5623F, 4.2453F, 11.7678F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-40.7832F, -36.7644F, -16.9573F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.5232F, -45.5258F, -33.2013F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-105.1437F, -34.0544F, 8.6177F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-121.4534F, -0.6794F, 9.0817F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(122.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(42.2963F, -25.231F, -21.1975F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-7.4366F, -0.9762F, 5.0634F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(5.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-6.3144F, -2.4682F, 32.6107F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-84.7523F, 34.0143F, 13.1645F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-109.7523F, 34.0143F, 13.1645F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(30.9805F, 0.1326F, 20.0773F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-21.5195F, 0.1326F, 20.0773F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-41.0211F, 24.0971F, -22.272F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(22.6746F, -6.9262F, -2.8842F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(22.6746F, -6.9262F, -2.8842F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-44.59F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(8.8722F, -32.1883F, -14.7535F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-8.3799F, -47.3886F, -17.9205F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-85.5995F, -27.582F, 11.3234F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-83.0595F, 4.4419F, 20.0714F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(22.6746F, 6.9262F, 2.8842F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(22.6746F, 6.9262F, 2.8842F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-66.7808F, -13.9954F, 14.4328F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-45.6632F, 0.9599F, -0.9899F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-19.5684F, 26.128F, -8.8967F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(37.1326F, 3.8167F, -11.9305F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-1.415F, 29.972F, -3.3528F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.4812F, 62.4356F, -7.7073F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-1.0784F, -45.0151F, -1.8116F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-1.2052F, 19.9746F, -3.0432F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-1.2052F, 19.9746F, -3.0432F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, -115.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -65.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -125.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, -40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, -40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-1.415F, -29.972F, 3.3528F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-3.1743F, -54.9554F, 5.3369F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-1.1678F, 52.5137F, 1.6398F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-1.2481F, -22.474F, 3.1118F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-1.2481F, -22.474F, 3.1118F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 115.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 65.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 125.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, 40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition TAKE_DOWN = Builder.m_232275_(3.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-71.6587F, 49.9854F, 3.0852F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-77.3784F, -19.7153F, 0.0888F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(12.9934F, -10.411F, 9.92F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-127.3898F, 16.1909F, 7.9732F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-17.8416F, -27.1492F, 8.9216F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(-36.5122F, -24.1818F, 20.5302F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-17.8416F, -27.1492F, 8.9216F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 40.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-47.0675F, 7.6443F, 6.4664F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-40.16F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-91.58F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-96.23F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-62.5894F, -2.3064F, 4.4375F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(207.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(187.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232331_(187.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(12.2286F, 7.9243F, -8.9173F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-126.512F, -16.996F, -2.9463F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-30.0828F, 22.3301F, -6.9786F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(-45.6888F, 17.1578F, -16.4899F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-30.0828F, 22.3301F, -6.9786F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, -40.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-45.7471F, -5.57F, -10.4263F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-35.6011F, -13.017F, -11.329F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-26.25F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-81.7896F, -0.2631F, -0.6919F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-57.7032F, 4.4384F, 11.6727F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-90.9141F, 4.9152F, 12.9266F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-60.0913F, 13.5322F, -3.483F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(207.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(185.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232331_(185.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-1.1103F, -47.3784F, -1.7584F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-1.3641F, 27.6088F, -3.278F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-1.0632F, 7.6132F, -2.7628F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-1.0632F, 7.6132F, -2.7628F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-1.9317F, 42.6029F, -3.9863F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-0.9779F, -7.3845F, -2.4833F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -108.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, -22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(0.0F, -35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, -61.11F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-1.0245F, 36.7686F, 1.9742F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-1.4516F, -30.7194F, 3.3949F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-1.0976F, -10.7241F, 2.8314F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-1.0976F, -10.7241F, 2.8314F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-2.1462F, -45.7123F, 4.2284F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-0.9935F, 4.2734F, 2.5396F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 114.96F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, 62.9F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition DEATH = Builder.m_232275_(3.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.02F, 3.56F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.4F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, -9.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -8.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -8.07F, -3.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, 4.0F, -4.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-28.2467F, 16.3205F, -5.4163F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-12.4722F, -1.0445F, 0.3466F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-75.959F, 0.0709F, -0.0235F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-74.4606F, 0.0535F, -0.0177F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-13.9218F, 9.1879F, 21.5875F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(13.6027F, -12.1729F, 7.5501F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-1.3673F, -14.8772F, 5.3366F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(24.3335F, -0.9265F, 3.3973F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(40.9827F, 0.0593F, -0.2174F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(82.7511F, -0.0037F, 0.0136F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-38.43F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-45.47F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(16.68F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(-5.0F, 10.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(81.7039F, 19.2789F, -7.2708F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(81.7039F, 19.2789F, -7.2708F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(92.6078F, 15.3628F, -5.7939F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(28.467F, 8.4771F, -3.197F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "front_cloth1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-38.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-33.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "front_cloth2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-36.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-4.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(10.0182F, -1.6097F, -0.4324F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(22.5547F, -4.8292F, -1.2972F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(25.506F, 2.3349F, 2.0406F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-15.2207F, 9.6559F, -2.613F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-20.2207F, 9.6559F, -2.613F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-2.4552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-4.9552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-4.8773F, 5.979F, 1.2566F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-4.9552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-5.0332F, 3.8137F, -0.5698F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-4.9552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-4.8773F, 5.979F, 1.2566F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-4.9552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-5.0332F, 3.8137F, -0.5698F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-4.9552F, 4.8969F, 0.3419F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-5.0332F, 3.8137F, -0.5698F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-10.5332F, 3.8137F, -0.5698F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-5.0332F, 3.8137F, -0.5698F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -0.2F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(10.548F, 26.2049F, 19.3614F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(13.0041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(15.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(15.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(20.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(90.5041F, 14.4775F, 3.9671F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(18.4938F, -26.7658F, -18.5407F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -22.5F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.7011F, -17.3362F, -24.9986F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(7.856F, -19.9082F, -1.9321F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(10.356F, -19.9082F, -1.9321F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(10.356F, -19.9082F, -1.9321F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(17.856F, -19.9082F, -1.9321F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(105.356F, -19.9082F, -1.9321F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(39.7026F, 1.3197F, 15.5338F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-40.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-50.1317F, -1.5664F, 3.0857F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-49.9219F, -4.9472F, -0.6045F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-50.1317F, -1.5664F, 3.0857F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-49.9219F, -4.9472F, -0.6045F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-50.1317F, -1.5664F, 3.0857F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-50.0542F, -3.2585F, 1.2437F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-49.9219F, -4.9472F, -0.6045F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232229_),
               new Keyframe(1.499F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, -19.24F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -2.68F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(0.0F, -18.59F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, -32.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, -46.87F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, -24.39F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, -29.65F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 15.96F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 18.81F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -15.84F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(0.0F, 13.94F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 39.37F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, 20.35F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 18.2F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, -25.2F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWING_LEFT = Builder.m_232275_(1.125F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(12.4541F, 36.8345F, -4.7621F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-7.3584F, -31.4564F, -9.9203F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(39.6306F, -5.4039F, 38.7854F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(27.9879F, -6.8442F, 26.1723F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-7.14F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-78.7657F, 33.8682F, -49.7473F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(20.9723F, 0.8525F, -47.0255F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-37.1665F, 13.4716F, -18.1914F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(2.1921F, 9.3762F, -12.6612F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(39.98F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(13.0238F, -29.6589F, -2.0999F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(19.3364F, 11.1816F, 11.6431F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition swing_attack_right = Builder.m_232275_(1.125F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, -20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 2.84F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(30.92F, -18.9477F, 2.2213F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-18.0307F, 17.6929F, -7.484F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-84.9218F, -21.8243F, 44.1778F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(26.4907F, -18.339F, 42.6343F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.15F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-40.3483F, -20.4366F, 29.0527F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-7.14F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(47.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(33.5119F, 6.743F, -27.941F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(4.2643F, -9.2743F, -26.4761F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-22.1665F, 13.4716F, -18.1914F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(2.1921F, 9.3762F, -12.6612F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(62.48F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-5.5334F, 11.1441F, -5.154F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(21.6628F, -10.3422F, 1.9509F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition FLYING_SHOOT = Builder.m_232275_(3.375F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232302_(0.0F, 3.07F, 8.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-71.6587F, 49.9854F, 3.0852F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-60.1598F, 33.4267F, -7.2121F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-60.497F, 57.9213F, -3.5104F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-115.9737F, 71.8212F, -60.7332F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-115.9737F, 71.8212F, -60.7332F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(60.72F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(102.35F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(102.35F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(65.54F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-77.3784F, -19.7153F, 0.0888F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-6.9444F, -12.4808F, 2.4284F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-36.3716F, 35.7618F, -29.5407F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(-53.8716F, 35.7618F, -29.5407F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232331_(-36.206F, 23.3736F, -22.8133F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-25.7237F, -10.921F, -3.0884F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(12.28F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5833F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232331_(26.74F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(39.06F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(112.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-2.9325F, 22.4097F, 5.4096F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-5.8536F, 23.0103F, 10.7909F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-4.5732F, 15.7671F, 14.1467F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-4.6346F, 18.2591F, 13.9368F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(12.9934F, -10.411F, 9.92F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(16.7357F, -41.2675F, -11.2179F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(16.7357F, -41.2675F, -11.2179F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(20.8172F, -15.2092F, -27.8057F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(7.564F, 7.4355F, 10.9845F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 20.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 40.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-80.0F, 20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-100.5212F, 58.7909F, -5.9364F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-101.7495F, 62.0045F, -7.8159F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-102.4018F, 62.0492F, -7.2192F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-101.7495F, 62.0045F, -7.8159F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(-102.4018F, 62.0492F, -7.2192F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-100.5212F, 58.7909F, -5.9364F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-64.571F, 44.3606F, 45.173F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(2.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 37.5F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 37.5F), Interpolations.f_232230_),
               new Keyframe(2.7083F, KeyframeAnimations.m_232331_(43.3818F, 0.0F, 32.6455F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(3.374F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(3.375F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(12.2286F, 7.9243F, -8.9173F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(2.3208F, -12.511F, 0.5541F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(7.564F, -7.4355F, -10.9845F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(-3.0F, -3.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, -40.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-74.4633F, 8.7219F, 11.8862F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-110.2334F, 6.4893F, -7.7716F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-110.2334F, 6.4893F, -7.7716F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-112.5267F, -26.2414F, 5.0057F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(6.199F, -1.154F, 92.0517F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(5.4137F, -2.1143F, 82.7458F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 90.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(3.374F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(3.375F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(12.5F, 42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(26.0187F, 39.5608F, 9.1544F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(24.1121F, 40.6202F, 6.1942F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-6.5717F, 45.102F, -9.0961F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-1.1103F, -47.3784F, -1.7584F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-1.3641F, 27.6088F, -3.278F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-1.0632F, 7.6132F, -2.7628F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 17.86F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, -20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-1.0351F, 4.9774F, -2.7057F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -108.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, -22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, -35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 30.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, -71.41F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(0.0F, -54.29F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-1.0245F, 36.7686F, 1.9742F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-1.4516F, -30.7194F, 3.3949F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-1.0976F, -10.7241F, 2.8314F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, -25.36F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, 52.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-1.0351F, -4.9774F, 2.7057F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -10.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 114.96F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, -23.21F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, 60.86F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -37.5F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -37.5F), Interpolations.f_232230_),
               new Keyframe(3.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow_string",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232302_(0.0F, 8.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "string1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "string2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_horn",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_)}
         )
      )
      .m_232282_();
   public static final AnimationDefinition FALL_LOOP = Builder.m_232275_(1.0F)
      .m_232274_()
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-72.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(112.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(112.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 20.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 25.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 20.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 7.5F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -10.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_)})
      )
      .m_232282_();
   public static final AnimationDefinition FALL_END = Builder.m_232275_(1.3333F)
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-77.5F, 37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-77.5F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(112.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(112.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_, new Keyframe[]{new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 20.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 42.5F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -20.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(35.0F, 0.0F, -50.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-67.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 10.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -55.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 10.0F, -10.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 50.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, -4.06F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition MASS_EFFECT = Builder.m_232275_(3.2917F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, -9.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, -9.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-141.172F, 15.3202F, -17.6352F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-84.6375F, 14.7392F, -3.5482F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-84.6375F, 14.7392F, -3.5482F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(87.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-12.5F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 12.5F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(47.1665F, -13.4716F, -5.6914F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(47.1665F, -13.4716F, -5.6914F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-31.4196F, 63.5714F, 74.0039F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-31.4196F, 63.5714F, 74.0039F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-92.6002F, 14.1038F, -21.6251F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-92.9471F, -8.3197F, -23.5229F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-19.6009F, -12.6796F, 18.7423F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-19.6009F, -12.6796F, 18.7423F), Interpolations.f_232230_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232331_(-32.3004F, -6.3398F, 9.3711F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(177.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(177.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.5F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-31.4196F, -63.5714F, -74.0039F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-31.4196F, -63.5714F, -74.0039F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-77.4165F, 16.4486F, 5.9956F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 30.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-35.411F, 5.7162F, -8.2189F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-35.411F, 5.7162F, -8.2189F), Interpolations.f_232230_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232331_(-40.2055F, 2.8581F, -4.1094F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(182.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(182.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(2.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.5F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, -47.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -15.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 47.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition FLYING_SMASH_2 = Builder.m_232275_(1.1667F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-77.8497F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-69.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(109.66F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-57.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-17.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, 0.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.09F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(122.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(85.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(38.46F, -11.85F, -9.26F), Interpolations.f_232230_),
               new Keyframe(0.0417F, KeyframeAnimations.m_232331_(38.4635F, -11.8491F, -9.2643F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(62.8783F, 4.9809F, 8.6822F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_, new Keyframe[]{new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-127.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-58.0224F, -5.2483F, 11.3608F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-79.3975F, -3.762F, 20.2676F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-57.6541F, -9.3955F, -18.0283F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-18.6966F, 16.9567F, -5.4884F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(50.064F, -7.4355F, -0.9845F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-6.2935F, 37.148F, -11.6747F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-3.688F, -14.2071F, -6.7734F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, -85.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-6.7115F, -39.632F, 12.1795F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-3.7611F, 2.7192F, 7.4956F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 85.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition FLYING_SMASH_1 = Builder.m_232275_(5.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -5.6F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -6.6F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232302_(0.0F, 0.55F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232302_(0.0F, 0.55F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-41.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-49.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-40.696F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-76.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-76.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(2.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(61.25F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-64.446F, -12.4879F, -2.5606F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-71.946F, -12.4879F, -2.5606F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-120.973F, -11.244F, -1.2803F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-5.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-5.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-57.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-57.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(2.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(67.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(133.75F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(122.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(122.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(34.23F, -11.73F, -9.38F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(34.23F, -11.73F, -9.38F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(8.2651F, 24.7716F, 13.483F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-12.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-4.1835F, -20.2281F, 15.7069F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(7.2999F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(7.2999F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-127.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-127.7001F, 34.6292F, 11.5422F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(5.73F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(5.73F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-94.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(8.2651F, -24.7716F, -13.483F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-12.7001F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-4.1835F, 20.2281F, -15.7069F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(7.2999F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(7.2999F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-79.3975F, -3.762F, 20.2676F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-79.3975F, -3.762F, 20.2676F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-94.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-18.6966F, 16.9567F, -5.4884F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-18.6966F, 16.9567F, -5.4884F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-1.352F, 27.4727F, -3.2656F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-1.1338F, 14.9756F, -2.9187F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-1.1338F, 14.9756F, -2.9187F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-6.8871F, -35.7432F, -9.0988F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-4.3005F, 14.7384F, -8.8319F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-4.3005F, 14.7384F, -8.8319F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-6.2935F, 37.148F, -11.6747F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-6.2935F, 37.148F, -11.6747F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, -142.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-1.352F, -27.4727F, 3.2656F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-1.1338F, -14.9756F, 2.9187F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-1.1338F, -14.9756F, 2.9187F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-6.8871F, 35.7432F, 9.0988F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-4.3005F, -14.7384F, 8.8319F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-4.3005F, -14.7384F, 8.8319F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(-6.7115F, -39.632F, 12.1795F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-6.7115F, -39.632F, 12.1795F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 145.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition CHARGE_BACKSTEP = Builder.m_232275_(0.75F)
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-80.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "front_cloth2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 40.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(33.0897F, 7.9713F, 27.9733F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(33.0897F, -7.9713F, -27.9733F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, -22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, -55.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 55.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
}
