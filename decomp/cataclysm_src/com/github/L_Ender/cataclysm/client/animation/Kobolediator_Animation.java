package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Kobolediator_Animation {
   public static final AnimationDefinition SLEEP = Builder.m_232275_(0.0F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-0.1173F, 17.1376F, 2.8163F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-38.8884F, -81.5291F, 72.6215F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.4743F, -0.2897F, 0.5688F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(200.0814F, 9.7215F, -1.6638F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(-2.0F, -1.0F, -2.0F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-76.6802F, 75.9747F, -67.1753F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-45.0215F, -7.4928F, 0.329F), Interpolations.f_232229_)}
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232282_();
   public static final AnimationDefinition AWAKE = Builder.m_232275_(3.5F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-2.5F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-14.6736F, -10.1188F, -0.8631F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-14.9629F, 12.3038F, -2.7623F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-16.0738F, 32.2139F, -4.9666F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-20.1376F, 31.508F, -11.3702F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-10.4748F, 17.2258F, -3.1339F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-11.5084F, 29.4987F, -5.7251F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-13.4502F, 41.7074F, -9.0411F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-4.1594F, -2.6067F, 0.5651F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-0.1173F, 17.1376F, 2.8163F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-2.6173F, 17.1376F, 2.8163F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-7.6173F, 17.1376F, 2.8163F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-7.6021F, 37.1374F, 2.9043F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.427F, 49.6373F, 2.9702F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-4.4342F, 54.5521F, 4.0899F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(0.2771F, -3.4095F, -0.2556F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-38.8884F, -81.5291F, 72.6215F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-34.8514F, -86.2841F, 68.2987F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-38.8197F, -23.9792F, 45.2129F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-23.2175F, -6.4384F, 76.245F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-10.5897F, -62.4339F, 81.2358F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(48.2525F, -35.3996F, 85.1416F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(43.8054F, -25.5982F, 83.0803F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-16.3939F, -4.2171F, -10.0677F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-5.99F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.4743F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.4743F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(25.0257F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(35.2457F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(57.5257F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(80.0257F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(75.0257F, -0.2897F, 0.5688F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(200.0814F, 9.7215F, -1.6638F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(214.6077F, 10.2499F, 0.9642F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(219.7292F, 36.0283F, 3.2362F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(220.2329F, 44.1079F, 2.2689F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(224.7383F, 54.4112F, 4.1646F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(231.2235F, 61.282F, 12.1259F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(217.8873F, 72.088F, -6.122F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(193.9018F, 81.8276F, -31.1775F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(222.9124F, 85.1513F, -1.7143F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(122.9554F, 89.3613F, -102.5353F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(107.4624F, 55.4011F, -90.7573F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(92.792F, 44.3836F, -74.3905F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(24.5912F, 24.8913F, -51.1214F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-4.6451F, 5.5741F, -58.0209F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-74.3582F, 77.7045F, -171.1362F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(-2.0F, -1.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-3.0F, 1.39F, -2.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-2.0F, 2.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-2.0F, -3.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-76.6802F, 75.9747F, -67.1753F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-71.3786F, 80.7225F, -61.6467F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-87.1845F, 4.3339F, -57.1285F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(28.5151F, -6.3186F, -40.0824F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(39.2694F, -3.7128F, -44.4162F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(56.7694F, -3.7128F, -44.4162F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-45.0215F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-40.0215F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-17.5215F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(14.9785F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(27.4785F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(7.4785F, -7.4928F, 0.329F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.5F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-25.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-40.4325F, -15.1443F, 6.4664F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-49.8793F, -40.2978F, 32.7324F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-54.0394F, -44.9266F, 39.9528F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(-39.4656F, -23.3004F, 36.6068F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-15.2158F, 6.9173F, 0.1856F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-2.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(4.7705F, -21.5183F, -13.0407F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(6.731F, -29.0858F, -18.6836F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-11.1177F, 4.3599F, 7.2331F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-16.1177F, 4.3599F, 7.2331F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 2.21F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-27.5057F, -2.9532F, -4.7436F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition IDLE = Builder.m_232275_(3.0F)
      .m_232274_()
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -0.34F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-2.9369F, 0.0191F, 5.019F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-5.9135F, 10.1702F, -9.6665F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-6.79F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition WALK = Builder.m_232275_(1.25F)
      .m_232274_()
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-0.8821F, -0.3942F, -5.9088F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-3.4552F, 4.5211F, -5.6412F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-0.8821F, -0.3942F, -5.9088F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -0.25F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(2.467F, 0.6507F, 0.434F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-1.4979F, -4.4714F, 4.3402F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.0621F, 2.9703F, 6.0915F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-0.9903F, 4.167F, 6.2106F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-4.5183F, 4.5486F, 6.0425F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-1.4979F, -4.4714F, 4.3402F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-9.16F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-6.84F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(3.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.33F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.8939F, 1.6355F, -0.0769F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-6.6297F, -5.5744F, 1.4779F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-7.8939F, 1.6355F, -0.0769F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.87F, 1.87F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.78F, 3.88F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(2.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(11.3717F, -24.6027F, -24.0579F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-15.1847F, -36.0944F, -24.2542F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-25.2419F, -28.5507F, -27.2621F), Interpolations.f_232229_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-15.861F, -27.7898F, -25.8095F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(11.3717F, -24.6027F, -24.0579F), Interpolations.f_232230_)
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
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(-0.5F, -1.07F, -0.63F), Interpolations.f_232230_),
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
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-28.033F, 31.5336F, 24.6551F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-24.5352F, 29.3614F, 24.7448F), Interpolations.f_232229_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(12.291F, 19.1168F, 22.1992F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-26.1528F, 31.7076F, 18.8778F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-28.033F, 31.5336F, 24.6551F), Interpolations.f_232230_)
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
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.1754F, 0.0F, 1.3378F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -0.16F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.0F, -1.22F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -0.16F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWORD1 = Builder.m_232275_(2.5F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-1.6639F, -44.5049F, -8.5188F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-16.8109F, -45.2955F, 4.0393F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(24.7499F, -18.0431F, -3.0721F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(42.2499F, -18.0431F, -3.0721F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(44.7499F, -18.0431F, -3.0721F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(44.7499F, -18.0431F, -3.0721F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-4.3801F, -42.8387F, -2.1103F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-3.3737F, -17.8931F, -4.054F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(1.6263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(4.1263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(4.1263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-7.4947F, -2.5F, -0.0167F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-21.3485F, -14.6919F, 3.0601F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(8.6134F, -16.7746F, -14.582F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(20.1834F, 18.4847F, -8.8408F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(17.6832F, 16.229F, -9.9187F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(17.6832F, 16.229F, -9.9187F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
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
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-64.9967F, 61.5507F, 11.1669F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-79.9967F, 61.5507F, 11.1669F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-103.8726F, 48.8975F, -27.1082F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-128.04F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-120.54F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-120.54F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(6.0289F, 3.2656F, 6.3299F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232302_(0.0F, -2.33F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232302_(0.0F, -0.25F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-67.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(29.9412F, 13.4202F, -2.3696F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(74.9412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(84.9412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(84.9412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(77.3667F, 7.6807F, -7.7061F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.3333F, KeyframeAnimations.m_232331_(4.1088F, -0.4214F, 0.4228F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(4.3961F, 5.3943F, -3.823F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(4.777F, 26.1417F, -8.7868F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-85.7134F, 6.289F, -11.2704F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-85.6756F, -1.1583F, -12.1593F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-85.6756F, -1.1583F, -12.1593F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-57.561F, 19.5634F, 10.6828F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(15.0F, 0.0F, -15.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(17.2197F, 3.4049F, -24.408F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-3.9482F, 8.0298F, -36.0825F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(16.4059F, -6.9859F, 1.1012F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.4392F, -7.761F, 8.6234F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.4392F, -7.761F, 8.6234F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-2.4422F, -4.8847F, -18.5837F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(16.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-7.3454F, 47.2906F, -6.9496F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-24.0984F, 54.7146F, -9.0011F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-56.5984F, 54.7146F, -9.0011F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-84.9664F, 2.4904F, 0.2212F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-74.9664F, 2.4904F, 0.2212F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-74.9664F, 2.4904F, 0.2212F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(11.79F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(15.0F, 37.0548F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(32.0679F, 20.8923F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(-34.8663F, 5.5917F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-37.9146F, 3.4002F, -1.621F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(6.4979F, 6.8503F, -22.9725F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(16.6448F, 1.7975F, -14.6532F), Interpolations.f_232230_),
               new Keyframe(1.9167F, KeyframeAnimations.m_232331_(20.3076F, 0.3455F, -4.0336F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(1.0402F, 0.0658F, -1.1008F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 5.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(65.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 2.5F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 4.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -9.0F), Interpolations.f_232229_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWORD2 = Builder.m_232275_(5.0F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(10.5878F, 31.6927F, 5.7634F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(8.3667F, -37.6272F, -17.051F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(17.1327F, -60.8027F, -29.9269F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(59.5726F, -80.5929F, -76.3207F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(10.7887F, -33.1209F, -28.1884F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(7.5F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(35.0173F, 59.8375F, 35.1278F), Interpolations.f_232230_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232331_(-35.2752F, 71.9138F, -21.6102F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(24.4684F, 0.5522F, 9.6545F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(43.6628F, -12.1844F, 5.1079F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(43.6628F, -12.1844F, 5.1079F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.6667F, KeyframeAnimations.m_232302_(0.0F, -4.92F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, -45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, -52.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-3.3737F, -17.8931F, -4.054F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(1.6263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(4.1263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(4.1263F, -17.8931F, -4.054F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, -17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, -25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 57.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 62.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(8.6134F, -16.7746F, -14.582F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(17.6834F, 18.4847F, -8.8408F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(17.6832F, 16.229F, -9.9187F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(17.6832F, 16.229F, -9.9187F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(84.6061F, 32.1759F, 64.2996F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(94.7143F, 14.6073F, 25.2037F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(58.7799F, -10.439F, 55.2953F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(6.0087F, -26.9902F, 75.9058F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-47.1767F, -42.0918F, 85.328F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-55.2362F, -59.3916F, 82.0442F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(11.6928F, -0.4495F, 72.7759F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(71.2781F, 10.8699F, 46.6524F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(101.6525F, 36.9908F, 97.7669F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-103.8726F, 48.8975F, -27.1082F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-128.04F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(-120.54F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(-120.54F, 8.763F, 8.1006F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5833F, KeyframeAnimations.m_232331_(6.0289F, 3.2656F, 6.3299F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(4.0F, 5.07F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5833F, KeyframeAnimations.m_232302_(0.0F, -2.33F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.6667F, KeyframeAnimations.m_232302_(0.0F, -0.25F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(4.05F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(66.4838F, 11.6099F, -12.0136F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(76.4838F, 11.6099F, -12.0136F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-67.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(43.6651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(76.1651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(16.1651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(13.6651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(25.203F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(88.6651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232331_(69.6647F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(81.4318F, 4.0912F, -4.9831F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(92.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(80.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(127.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(42.27F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(76.2592F, 6.6079F, 21.5561F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(47.0665F, 7.8641F, 1.4182F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(29.9412F, 13.4202F, -2.3696F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(77.4412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(84.9412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(84.9412F, 13.4202F, -2.3696F), Interpolations.f_232229_),
               new Keyframe(4.0833F, KeyframeAnimations.m_232331_(77.3667F, 7.6807F, -7.7061F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5833F, KeyframeAnimations.m_232331_(4.1088F, -0.4214F, 0.4228F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(1.3257F, -9.7239F, -5.1653F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(3.1738F, -24.4706F, -9.1682F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(14.5678F, -45.1839F, -24.3228F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(17.8526F, -52.2915F, -28.3882F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-34.1003F, -1.3322F, -12.2701F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-36.4543F, -56.5448F, -32.1206F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(4.3961F, 5.3943F, -3.823F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(4.777F, 26.1417F, -8.7868F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-85.7134F, 6.289F, -11.2704F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(-85.6756F, -1.1583F, -12.1593F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(-85.6756F, -1.1583F, -12.1593F), Interpolations.f_232229_),
               new Keyframe(4.0833F, KeyframeAnimations.m_232331_(-57.561F, 19.5634F, 10.6828F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.0833F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.25F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.1097F, 3.7317F, -11.9384F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(16.1992F, 7.43F, -21.2755F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(27.6169F, 10.0794F, -28.3754F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(35.1169F, 10.0794F, -28.3754F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-12.3831F, 10.0794F, -28.3754F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(17.2197F, 3.4049F, -24.408F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-3.9482F, 8.0298F, -36.0825F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(16.4059F, -6.9859F, 1.1012F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(0.4392F, -7.761F, 8.6234F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(0.4392F, -7.761F, 8.6234F), Interpolations.f_232229_),
               new Keyframe(4.0833F, KeyframeAnimations.m_232331_(-2.4422F, -4.8847F, -18.5837F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.0833F, KeyframeAnimations.m_232331_(16.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.0F, -40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-13.7379F, -54.9296F, 1.9109F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-9.2699F, -3.3122F, -8.9784F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-10.0466F, -30.4375F, -4.3457F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-28.3353F, 23.7837F, -13.9714F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-25.7188F, -28.3181F, 11.3857F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(-41.921F, -60.1182F, 37.1789F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-32.2696F, -68.1759F, 25.4042F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-24.0984F, 54.7146F, -9.0011F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-56.5984F, 54.7146F, -9.0011F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(-84.9664F, 2.4904F, 0.2212F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(-74.9664F, 2.4904F, 0.2212F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(-74.9664F, 2.4904F, 0.2212F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(65.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(4.1667F, KeyframeAnimations.m_232331_(11.79F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-6.0209F, 37.0159F, -12.0263F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(2.3488F, 8.6965F, 8.4144F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(15.0F, 37.0548F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(32.0679F, 20.8923F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.1667F, KeyframeAnimations.m_232331_(-34.8663F, 5.5917F, 0.0F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(-37.9146F, 3.4002F, -1.621F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(16.265F, 50.283F, 29.9477F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-1.5381F, -27.5115F, -23.3261F), Interpolations.f_232230_),
               new Keyframe(2.4167F, KeyframeAnimations.m_232331_(1.7626F, -31.121F, -31.5988F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(6.4979F, 6.8503F, -22.9725F), Interpolations.f_232230_),
               new Keyframe(3.75F, KeyframeAnimations.m_232331_(16.6448F, 1.7975F, -14.6532F), Interpolations.f_232230_),
               new Keyframe(4.1667F, KeyframeAnimations.m_232331_(20.3076F, 0.3455F, -4.0336F), Interpolations.f_232230_),
               new Keyframe(4.5F, KeyframeAnimations.m_232331_(1.0402F, 0.0658F, -1.1008F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-27.5057F, 2.9532F, 4.7436F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-45.0057F, 2.9532F, 4.7436F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-37.5057F, 2.9532F, 4.7436F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(-27.5057F, 2.9532F, 4.7436F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.54F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.59F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -2.56F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -1.01F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 3.08F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.3333F, KeyframeAnimations.m_232302_(0.0F, 2.92F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-4.0542F, -2.5045F, -11.3583F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-4.0542F, -2.5045F, -11.3583F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-7.1199F, -1.3779F, -5.6029F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(18.8235F, 45.1716F, 4.25F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(8.8235F, 45.1716F, 4.25F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.31F, 0.1F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.56F, 0.34F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 1.38F, 0.44F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, -0.35F, 0.54F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-3.0F, -6.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(3.3333F, KeyframeAnimations.m_232302_(0.13F, -2.57F, -4.66F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, -70.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, -7.45F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-15.0F, -12.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.25F, KeyframeAnimations.m_232331_(8.9266F, -10.5553F, 0.0F), Interpolations.f_232229_),
               new Keyframe(3.5F, KeyframeAnimations.m_232331_(-0.7026F, -9.407F, 0.0F), Interpolations.f_232229_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.25F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(3.5F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -9.0F), Interpolations.f_232230_),
               new Keyframe(3.8333F, KeyframeAnimations.m_232302_(0.0F, -5.0F, -9.0F), Interpolations.f_232229_),
               new Keyframe(4.375F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "legs",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.1667F, KeyframeAnimations.m_232331_(0.0F, 52.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5417F, KeyframeAnimations.m_232331_(0.0F, 65.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0833F, KeyframeAnimations.m_232331_(0.0F, 12.14F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.5417F, KeyframeAnimations.m_232331_(5.0F, 6.1543F, 0.0F), Interpolations.f_232230_),
               new Keyframe(5.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition CHARGE_PREPARE = Builder.m_232275_(2.0F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(15.3663F, 4.5253F, 2.1583F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(25.8649F, -24.6945F, -17.2024F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-0.6264F, -12.4513F, 0.0653F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(11.845F, -43.8064F, -14.31F), Interpolations.f_232230_)
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
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(6.4134F, -2.352F, -4.5443F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-9.6291F, 4.2033F, -8.3364F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(35.575F, -0.1524F, 81.9713F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-55.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-53.6432F, -9.8417F, -7.8342F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-46.1649F, 15.8719F, 8.8615F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(10.8519F, 0.8355F, -16.5753F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(3.9642F, 1.4634F, -6.0843F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(13.241F, 2.9267F, -12.1687F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-9.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-19.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-4.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-4.02F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-42.5F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-45.5436F, -26.9573F, 14.1854F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-57.8804F, 31.3886F, -6.1664F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-143.4876F, 72.6825F, -84.2251F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(5.0F, 1.0F, -3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(13.18F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.2497F, -30.9307F, 13.0095F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(3.0903F, -12.2083F, 5.3952F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-29.53F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(9.1914F, -16.2705F, -11.7914F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-10.64F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-64.0108F, -23.458F, -19.9176F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-64.0108F, -23.458F, -19.9176F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(47.493F, -22.494F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-36.0231F, 17.6233F, 22.8254F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 3.0F, -5.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition CHARGE = Builder.m_232275_(0.5F)
      .m_232274_()
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(25.8649F, -24.6945F, -17.2024F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(21.0624F, -21.858F, -2.8389F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(25.8649F, -24.6945F, -17.2024F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-0.6264F, -12.4513F, 0.0653F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(2.8086F, -24.8106F, -8.0523F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-0.6264F, -12.4513F, 0.0653F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(11.845F, -43.8064F, -14.31F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(14.2452F, -40.5785F, -17.1565F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(11.845F, -43.8064F, -14.31F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.575F, -0.1524F, 81.9713F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(35.575F, -0.1524F, 81.9713F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(35.575F, -0.1524F, 81.9713F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(42.7235F, -6.3202F, -4.0462F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(10.8519F, 0.8355F, -16.5753F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(9.6625F, -1.9354F, -4.3274F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(10.8519F, 0.8355F, -16.5753F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(13.241F, 2.9267F, -12.1687F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(13.2892F, -2.5968F, 4.4433F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(13.241F, 2.9267F, -12.1687F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-4.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(10.98F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-4.02F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-143.4876F, 72.6825F, -84.2251F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-156.0083F, 74.2287F, -98.8575F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-143.4876F, 72.6825F, -84.2251F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(5.0F, 1.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(5.0F, 1.0F, -3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(13.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(23.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(13.18F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-29.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-33.4459F, 17.9161F, -24.4724F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-29.53F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-10.64F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-10.0807F, 14.3412F, 4.4433F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-10.64F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(47.5803F, -27.8921F, -20.7879F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(-15.1847F, -36.0944F, -24.2542F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-72.7419F, -28.5507F, -27.2621F), Interpolations.f_232229_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-15.861F, -27.7898F, -25.8095F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(47.5803F, -27.8921F, -20.7879F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(-0.6F, 2.0F, -0.63F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(-0.5F, 2.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(-0.5F, 0.93F, -2.63F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-0.5F, 0.0F, 0.48F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(51.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(45.23F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(3.18F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(32.7265F, 1.6044F, 0.2666F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-66.4099F, 27.2203F, 25.6653F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-13.411F, 24.2499F, 26.7107F), Interpolations.f_232229_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(12.291F, 19.1168F, 22.1992F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-26.1528F, 31.7076F, 18.8778F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-66.4099F, 27.2203F, 25.6653F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 5.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.5F, -1.0F, -0.54F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 0.48F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.5F, 0.0F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.5F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 5.0F, -6.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(32.7265F, -1.6044F, -0.2666F), Interpolations.f_232229_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(5.52F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(51.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(45.23F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.0F, -0.69F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -0.69F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, 1.02F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 0.88F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, -2.95F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, -3.46F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition CHARGE_END = Builder.m_232275_(2.0F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(25.8649F, -24.6945F, -17.2024F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(7.5F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(35.0173F, 59.8375F, 35.1278F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-35.2752F, 71.9138F, -21.6102F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(0.0F, -4.92F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-0.6264F, -12.4513F, 0.0653F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 35.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(11.845F, -43.8064F, -14.31F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 57.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 62.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(2.5F, 6.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.575F, -0.1524F, 81.9713F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(11.6928F, -0.4495F, 72.7759F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(71.2781F, 10.8699F, 46.6524F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(101.6525F, 36.9908F, 97.7669F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(66.4838F, 11.6099F, -12.0136F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(76.4838F, 11.6099F, -12.0136F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(88.6651F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(69.6647F, 4.3134F, -5.2537F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(81.4318F, 4.0912F, -4.9831F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(76.2592F, 6.6079F, 21.5561F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(47.0665F, 7.8641F, 1.4182F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(10.8519F, 0.8355F, -16.5753F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-34.1003F, -1.3322F, -12.2701F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-36.4543F, -56.5448F, -32.1206F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(13.241F, 2.9267F, -12.1687F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-12.3831F, 10.0794F, -28.3754F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-4.02F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-143.4876F, 72.6825F, -84.2251F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-25.7188F, -28.3181F, 11.3857F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(-41.921F, -60.1182F, 37.1789F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-32.2696F, -68.1759F, 25.4042F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(5.0F, 1.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(13.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-29.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(2.3488F, 8.6965F, 8.4144F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-10.64F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-1.5381F, -27.5115F, -23.3261F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(1.7626F, -31.121F, -31.5988F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(47.5803F, -27.8921F, -20.7879F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, -1.01F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, 3.08F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-66.4099F, 27.2203F, 25.6653F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(18.8235F, 45.1716F, 4.25F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 5.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -0.35F, 0.54F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(-3.0F, -6.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(0.0F, -7.45F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-15.0F, -12.5F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "legs",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 45.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232331_(0.0F, 52.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(0.0F, 65.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition BLOCK = Builder.m_232275_(1.0F)
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-2.1489F, 9.8245F, 2.6804F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-9.9175F, 11.0525F, 3.0154F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-12.4175F, 11.0525F, 3.0154F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-2.488F, 4.9883F, -0.543F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.488F, 4.9883F, -0.543F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-3.0544F, 19.9279F, 1.72F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-3.0544F, 19.9279F, 1.72F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(40.6825F, -16.8979F, 99.7479F), Interpolations.f_232229_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(40.6825F, -16.8979F, 99.7479F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-7.6629F, 3.6197F, -3.6702F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-7.6629F, 3.6197F, -3.6702F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(12.2526F, 12.1993F, -102.7936F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(12.2526F, 12.1993F, -102.7936F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-11.063F, 10.1947F, -15.9463F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-11.063F, 10.1947F, -15.9463F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-20.1212F, 17.4828F, -0.7879F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-20.1212F, 17.4828F, -0.7879F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
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
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-4.9449F, -19.9004F, 2.3616F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.4382F, -34.8397F, 3.8007F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(0.0F, 10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 2.5F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition DEATH = Builder.m_232275_(3.0F)
      .m_232274_()
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-28.4255F, 11.8956F, -3.9566F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-23.4255F, 11.8956F, -3.9566F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(4.0745F, 11.8956F, -3.9566F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(6.5745F, 11.8956F, -3.9566F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.5F, -7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "lower_body",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-7.7213F, -1.5491F, 2.2995F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(1.9257F, 5.8382F, 3.643F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(1.7281F, 18.1614F, 5.7513F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(14.2281F, 18.1614F, 5.7513F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(19.2281F, 18.1614F, 5.7513F), Interpolations.f_232230_)
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
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-10.6874F, 33.3608F, -14.825F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-23.2733F, 35.2364F, -25.8476F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(3.8731F, -6.6041F, -4.8813F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-4.0696F, -9.1179F, -4.0437F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(15.2109F, 1.1248F, 4.8765F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(32.7109F, 1.1248F, 4.8765F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(37.7109F, 1.1248F, 4.8765F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(28.91F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "golden_greatsword",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.1502F, -14.985F, -8.1883F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.5396F, -29.9668F, -9.025F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-6.4653F, -29.8857F, 3.1789F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-28.9653F, -29.8857F, 3.1789F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-33.2623F, -26.2874F, 11.0377F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(15.0F, 0.0F, -12.5F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(29.7197F, 3.4049F, -21.908F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(2.2197F, 3.4049F, -21.908F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-5.2803F, 3.4049F, -21.908F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-4.3884F, 3.3612F, -6.8818F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(15.7732F, -28.5619F, -22.5846F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(13.5168F, -14.6541F, -57.7999F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(11.4406F, -8.1663F, -62.238F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232302_(0.0F, -6.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(0.0F, -20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232302_(-5.58F, -21.72F, -7.97F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-7.0F, -23.0F, -10.0F), Interpolations.f_232230_)
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
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-44.7144F, -9.9668F, 44.7406F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(-42.0487F, 23.2042F, 57.5758F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-66.1648F, 16.3849F, 54.0587F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-78.3069F, -9.0497F, 43.3005F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-82.4221F, -15.1308F, 49.2549F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232302_(4.0F, -7.0F, -32.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(3.0F, -3.0F, -31.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(3.0F, -3.0F, -37.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(27.6027F, -4.094F, -2.8728F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(10.1027F, -4.094F, -2.8728F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-2.3973F, -4.094F, -2.8728F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(23.0909F, -10.5688F, -6.6912F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "tail1",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(25.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-30.0F, -7.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "tail2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, -20.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-47.5F, -20.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-37.1254F, -20.1961F, -9.7537F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-54.6254F, -20.1961F, -9.7537F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-67.1254F, -20.1961F, -9.7537F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 2.0F, -2.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(60.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 2.0F, 3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(30.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -5.51F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(-1.0F, -6.32F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.44F, -6.65F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.98F, -7.06F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(-0.04F, -7.12F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(0.92F, -7.18F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(1.9F, -7.24F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232302_(0.79F, -7.31F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(1.65F, -7.38F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -5.51F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-1.0F, -6.32F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.44F, -6.65F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -7.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232302_(0.98F, -7.06F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-0.04F, -7.12F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.92F, -7.18F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232302_(1.9F, -7.24F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.79F, -7.31F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(1.65F, -7.38F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232302_(2.5F, -7.46F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
}
