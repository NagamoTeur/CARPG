package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Aptrgangr_Animation {
   public static final AnimationDefinition IDLE = Builder.m_232275_(2.0F)
      .m_232274_()
      .m_232279_(
         "chest",
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
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.2965F, 2.1578F, 1.2968F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232282_();
   public static final AnimationDefinition WALK = Builder.m_232275_(1.3333F)
      .m_232274_()
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 4.3F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.5F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 4.3F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.4F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 1.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -1.5F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(10.0374F, -4.9238F, -0.8704F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(10.0845F, 7.3854F, 1.3096F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(10.0374F, -4.9238F, -0.8704F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.0041F, 14.4775F, 3.9671F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(15.9153F, -19.291F, -5.3815F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(12.0041F, 14.4775F, 3.9671F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-15.1226F, -9.45F, 4.1278F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-13.1168F, 14.0812F, -2.9543F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-15.1226F, -9.45F, 4.1278F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-0.6F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.6F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-43.4907F, 11.0007F, -22.7608F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(21.4345F, -9.9736F, -30.9513F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-43.4907F, 11.0007F, -22.7608F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-65.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232282_();
   public static final AnimationDefinition DEATH = Builder.m_232275_(3.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-11.9409F, -5.7358F, -11.125F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-0.4987F, -2.479F, 3.5571F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-0.4987F, -2.479F, 3.5571F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 3.0F, -3.5F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -1.5F, -6.25F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -7.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.554F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -2.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -2.0F, -4.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-7.4718F, 0.6518F, -10.0426F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(20.493F, 9.1955F, 3.8222F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.4344F, 13.6784F, 5.3916F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(20.4344F, 13.6784F, 5.3916F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "neck",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-24.1105F, -20.0261F, -14.1293F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(7.3959F, -9.8375F, 3.3015F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(8.126F, -10.5179F, -6.7929F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(8.126F, -10.5179F, -6.7929F), Interpolations.f_232230_),
               new Keyframe(2.2083F, KeyframeAnimations.m_232331_(22.4491F, -7.544F, 13.238F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(22.4491F, -7.544F, 13.238F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -2.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, -0.87F, -1.94F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-62.4776F, -2.2174F, -1.1549F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232331_(-62.4776F, -2.2174F, -1.1549F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(7.9753F, -19.8217F, -32.7199F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-17.25F, 2.9932F, -20.4541F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(2.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(1.0F, -3.0F, -3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-25.2403F, 41.43F, 3.1944F), Interpolations.f_232230_),
               new Keyframe(2.625F, KeyframeAnimations.m_232331_(-25.2403F, 41.43F, 3.1944F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-8.4268F, 25.4967F, 28.4835F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(40.1099F, -20.7731F, 9.4233F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(40.1099F, -20.7731F, 9.4233F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 2.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -8.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(97.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(97.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(9.8857F, -5.075F, 63.3706F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(9.8857F, -5.075F, 63.3706F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWING_RIGHT = Builder.m_232275_(2.0F)
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(22.5F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-2.6073F, 1.5952F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(-1.0F, 0.0F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(-1.11F, 4.0F, -2.57F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(-1.0F, 0.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(32.5F, -15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(1.0F, 0.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(54.7946F, -23.6208F, -51.4774F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(54.7946F, -23.6208F, -51.4774F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-25.3211F, 49.2748F, -1.0169F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-25.3211F, 49.2748F, -1.0169F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-46.7718F, 49.5558F, -33.4863F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-31.1525F, -39.2716F, 18.7857F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-11.1871F, -23.2945F, 4.6011F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(39.8537F, 16.6993F, -18.2012F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(14.3461F, -0.3984F, -44.4513F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(33.1452F, -5.8107F, -22.5712F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-46.11F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-39.0759F, -20.475F, 37.2799F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-42.0277F, -14.9519F, 66.9226F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(106.6862F, -34.15F, 31.4058F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(4.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(4.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(4.0F, 0.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(66.5685F, -19.9035F, 19.3763F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(66.5685F, -19.9035F, 19.3763F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(100.1065F, 9.4277F, 24.4539F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(57.738F, 5.9438F, 4.5822F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "cloth2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "cloth",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232298_(1.0, 2.3F, 1.0), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SMASH = Builder.m_232275_(2.0F)
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-42.9129F, 2.927F, 7.3122F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-2.5F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-1.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-174.8084F, -16.0539F, -17.2016F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(-83.2559F, 35.7722F, -6.3006F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-83.2559F, 35.7722F, -6.3006F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(-2.0F, 2.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -9.0F), Interpolations.f_232229_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-23.1898F, -3.0467F, 34.6336F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-18.4281F, 1.7305F, 3.0857F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-37.8109F, -12.4789F, 15.1625F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(19.2454F, -40.5325F, -47.1598F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(36.7454F, -40.5325F, -47.1598F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(3.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -6.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "roots",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -1.8F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 4.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232229_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-2.6117F, 1.2469F, -1.5185F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(32.0017F, 3.2211F, -3.9227F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(32.0017F, 3.2211F, -3.9227F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 3.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(36.1331F, -18.6925F, -3.6544F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(36.1331F, -18.6925F, -3.6544F), Interpolations.f_232229_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 32.5F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.6103F, -2.5857F, 39.5006F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(0.6103F, -2.5857F, 39.5006F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232298_(1.0, 2.1F, 0.7F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition RUSH_START = Builder.m_232275_(1.1667F)
      .m_232279_(
         "roots",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-31.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition RUSHING = Builder.m_232275_(1.0F)
      .m_232274_()
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(9.505F, 48.8322F, -11.8189F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-23.1335F, -36.1181F, 26.9713F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-86.2711F, 27.1064F, -5.1581F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(29.883F, 0.2905F, -5.0862F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(39.7339F, 11.0311F, -28.0756F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition RUSH_END = Builder.m_232275_(1.125F)
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, 3.0F, -4.42F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-17.8981F, -11.9128F, 3.814F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-17.8981F, -11.9128F, 3.814F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-38.3132F, -30.3611F, 7.7457F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-38.3132F, -30.3611F, 7.7457F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-20.2799F, -0.9967F, -3.3981F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-20.2799F, -0.9967F, -3.3981F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(23.7877F, -0.4615F, -45.4199F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(23.7877F, -0.4615F, -45.4199F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(106.6849F, -22.5518F, 19.9839F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-13.478F, -4.0948F, 5.0443F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "neck",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232298_(1.0, 2.3F, 1.0), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition RUSH_HIT = Builder.m_232275_(0.875F)
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 3.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.334F, 10.0559F, 1.3451F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-14.666F, 10.0559F, 1.3451F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(6.6711F, 49.2917F, -15.5697F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-28.5614F, 22.0295F, -20.1233F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "chest",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-21.8845F, -34.3504F, 24.8068F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-86.1823F, 29.601F, -4.9712F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-6.8681F, -11.3623F, -48.2997F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "l_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-98.8297F, 61.5119F, -86.3006F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-94.7017F, 26.7197F, -80.6439F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(29.4316F, -0.9657F, -7.2943F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(48.4182F, 18.173F, 42.6509F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "r_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.2339F, 11.0311F, -28.0756F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 86.42F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "neck",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "axe_head",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232298_(1.0, 1.0, 1.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
}
