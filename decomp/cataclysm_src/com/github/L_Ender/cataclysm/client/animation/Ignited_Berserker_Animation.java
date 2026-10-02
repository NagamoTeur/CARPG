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
public class Ignited_Berserker_Animation {
   public static final AnimationDefinition IDLE = Builder.m_232275_(1.5F)
      .m_232274_()
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition WALK = Builder.m_232275_(1.5F)
      .m_232274_()
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-16.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 2.0F), Interpolations.f_232229_)})
      )
      .m_232282_();
   public static final AnimationDefinition X_SLASH = Builder.m_232275_(2.5F)
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-8.95F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-4.35F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-25.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 3.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -6.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, -3.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 7.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(9.9265F, -0.1125F, 0.589F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-22.5735F, -0.1125F, 0.589F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-25.0735F, -0.1125F, 0.589F), Interpolations.f_232229_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(9.9265F, -0.1125F, 0.589F), Interpolations.f_232229_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(17.4265F, -0.1125F, 0.589F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(22.4265F, -0.1125F, 0.589F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(12.4265F, -0.1125F, 0.589F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-68.6451F, 5.862F, -110.733F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-72.7845F, -5.9493F, -116.558F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-75.8627F, -25.2264F, -122.7332F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-31.7864F, 13.4682F, -56.5656F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(74.4126F, -13.8007F, 44.1735F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(67.22F, -71.7923F, 60.1278F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(53.057F, -17.6319F, 65.4305F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(40.532F, -7.9557F, 56.0506F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(40.0221F, -4.6774F, 50.3455F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(61.8319F, -77.2792F, 103.2867F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(78.7897F, -52.8222F, 97.7098F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(-4.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-3.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-3.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(1.93F, 17.63F, 3.26F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(4.0F, 1.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(4.0F, 9.0F, -11.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(4.0F, 16.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(3.0F, 17.0F, -11.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 15.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 29.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-68.6451F, -5.862F, 110.733F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-72.7845F, 5.9493F, 116.558F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-75.8627F, 25.2264F, 122.7332F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-31.7864F, -13.4682F, 56.5656F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(74.4126F, 13.8007F, -44.1735F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(67.22F, 71.7923F, -60.1278F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(53.057F, 17.6319F, -65.4305F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(40.532F, 7.9557F, -56.0506F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(40.0221F, 4.6774F, -50.3455F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(61.8319F, 77.2792F, -103.2867F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(78.7897F, 52.8222F, -97.7098F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(4.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(3.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(3.0F, 20.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-1.93F, 17.63F, 3.26F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(-4.0F, 1.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-4.0F, 9.0F, -11.0F), Interpolations.f_232229_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-4.0F, 16.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-3.0F, 17.0F, -11.0F), Interpolations.f_232229_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(0.0F, 15.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(0.0F, 29.0F, 10.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-65.0067F, 22.5389F, 13.2179F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-97.5067F, 22.5389F, 13.2179F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-66.962F, 39.9019F, 10.9575F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-109.462F, 39.9019F, 10.9575F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-140.7494F, 75.6805F, -25.5957F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-59.0601F, -12.8627F, -38.8675F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(2.6471F, -29.6601F, -93.236F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(58.7446F, 9.089F, -154.1614F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(56.6328F, 64.6631F, -187.5421F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-67.4197F, 77.8257F, -330.7041F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-82.442F, 72.7817F, -352.1569F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(-95.4399F, 35.8374F, -364.7647F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, -360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(1.0F, 0.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(1.0F, 8.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-65.0067F, -22.5389F, -13.2179F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-97.5067F, -22.5389F, -13.2179F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-66.962F, -39.9019F, -10.9575F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-109.462F, -39.9019F, -10.9575F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-140.7494F, -75.6805F, 25.5957F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-59.0601F, 12.8627F, 38.8675F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(2.6471F, 29.6601F, 93.236F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(58.7446F, -9.089F, 154.1614F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(56.6328F, -64.6631F, 187.5421F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-67.4197F, -77.8257F, 330.7041F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-82.442F, -72.7817F, 352.1569F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(-95.4399F, -35.8374F, 364.7647F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, 360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(-1.0F, 0.0F, 6.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(-1.0F, 8.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(-2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(-6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition MIXER_START = Builder.m_232275_(1.5F)
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(12.59F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 90.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition MIXER_IDLE = Builder.m_232275_(0.75F)
      .m_232274_()
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5833F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(-32.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, 0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 90.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 450.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232282_();
   public static final AnimationDefinition MIXER_FINISH = Builder.m_232275_(2.0F)
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-1.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(12.59F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(37.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 90.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 585.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 3.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "mid_root",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition X_BLADE_SHOT = Builder.m_232275_(2.25F)
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-8.34F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-18.45F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-18.45F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(25.15F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(15.99F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-73.87F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "jaw",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(16.47F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(82.01F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-14.28F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-27.83F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-27.83F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(33.07F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(0.0F, -2.0F, 2.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "edges",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(19.76F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(38.3F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-45.05F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(8.0F, 0.0F, 8.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(8.0F, 0.0F, 8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-17.0F, 0.0F, -17.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(-22.0F, 0.0F, -22.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(4.0F, 0.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(5.63F, 0.0F, 5.63F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(8.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(8.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-17.0F, 0.0F, 17.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(-22.0F, 0.0F, 22.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(4.0F, 0.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(5.63F, 0.0F, -5.62F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-8.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-8.0F, 0.0F, -8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(17.0F, 0.0F, 17.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(22.0F, 0.0F, 22.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-4.0F, 0.0F, -4.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(-5.62F, 0.0F, -5.62F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232302_(-8.0F, 0.0F, 8.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-8.0F, 0.0F, 8.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(17.0F, 0.0F, -17.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(22.0F, 0.0F, -22.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(-4.0F, 0.0F, 4.0F), Interpolations.f_232230_),
               new Keyframe(2.125F, KeyframeAnimations.m_232302_(-5.62F, 0.0F, 5.63F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWORD_DANCE_LEFT = Builder.m_232275_(2.5F)
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-0.7298F, 17.4987F, -0.2194F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-0.7298F, 17.4987F, -0.2194F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-0.3649F, -8.7503F, 0.0013F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(8.9675F, -15.8784F, -14.5232F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(8.9675F, -15.8784F, -14.5232F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(32.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(3.4741F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-140.7494F, -75.6805F, 25.5957F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-59.0601F, 12.8627F, 38.8675F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.6471F, 29.6601F, 93.236F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(58.7446F, -9.089F, 154.1614F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(56.6328F, -64.6631F, 187.5421F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-67.4197F, -77.8257F, 330.7041F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-82.442F, -72.7817F, 352.1569F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-95.4399F, -35.8374F, 364.7647F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, 360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(-5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(-1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(-2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(-0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-140.7494F, 75.6805F, -25.5957F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-59.0601F, -12.8627F, -38.8675F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(2.6471F, -29.6601F, -93.236F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(58.7446F, 9.089F, -154.1614F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(56.6328F, 64.6631F, -187.5421F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-67.4197F, 77.8257F, -330.7041F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-82.442F, 72.7817F, -352.1569F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-95.4399F, 35.8374F, -364.7647F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, -360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(33.63F, 2.05F, -1.21F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-140.7494F, -75.6805F, 25.5957F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-59.0601F, 12.8627F, 38.8675F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(2.6471F, 29.6601F, 93.236F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(58.7446F, -9.089F, 154.1614F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(56.6328F, -64.6631F, 187.5421F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-67.4197F, -77.8257F, 330.7041F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-82.442F, -72.7817F, 352.1569F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-95.4399F, -35.8374F, 364.7647F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, 360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-140.7494F, 75.6805F, -25.5957F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(-59.0601F, -12.8627F, -38.8675F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(2.6471F, -29.6601F, -93.236F), Interpolations.f_232230_),
               new Keyframe(0.7083F, KeyframeAnimations.m_232331_(58.7446F, 9.089F, -154.1614F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(56.6328F, 64.6631F, -187.5421F), Interpolations.f_232230_),
               new Keyframe(0.8333F, KeyframeAnimations.m_232331_(-67.4197F, 77.8257F, -330.7041F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-82.442F, 72.7817F, -352.1569F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(-95.4399F, 35.8374F, -364.7647F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, -360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232302_(1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(30.79F, 1.83F, -0.01F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(24.33F, 1.46F, 0.12F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(22.29F, 1.35F, 0.11F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition SWORD_DANCE_RIGHT = Builder.m_232275_(2.75F)
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-0.7298F, 17.4987F, -0.2194F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-0.7298F, 17.4987F, -0.2194F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-0.3649F, -8.7503F, 0.0013F), Interpolations.f_232230_),
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
               new Keyframe(0.5F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -0.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232302_(0.0F, 0.76F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(8.9675F, -15.8784F, -14.5232F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(8.9675F, -15.8784F, -14.5232F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-3.87F, 0.0F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -0.3F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, 0.71F, 0.0F), Interpolations.f_232230_),
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
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(32.18F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(3.4741F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "rod",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(0.0F, 1.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(0.0F, -1.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(-140.7494F, -75.6805F, 25.5957F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-59.0601F, 12.8627F, 38.8675F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232331_(2.6471F, 29.6601F, 93.236F), Interpolations.f_232230_),
               new Keyframe(1.4583F, KeyframeAnimations.m_232331_(58.7446F, -9.089F, 154.1614F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(56.6328F, -64.6631F, 187.5421F), Interpolations.f_232230_),
               new Keyframe(1.5833F, KeyframeAnimations.m_232331_(-67.4197F, -77.8257F, 330.7041F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-82.442F, -72.7817F, 352.1569F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(-95.4399F, -35.8374F, 364.7647F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, 360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(-5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(-1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.4167F, KeyframeAnimations.m_232302_(-2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232302_(-0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(-140.7494F, 75.6805F, -25.5957F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-59.0601F, -12.8627F, -38.8675F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.6471F, -29.6601F, -93.236F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(58.7446F, 9.089F, -154.1614F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(56.6328F, 64.6631F, -187.5421F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-67.4197F, 77.8257F, -330.7041F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-82.442F, 72.7817F, -352.1569F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(-95.4399F, 35.8374F, -364.7647F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, -360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232302_(6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-140.7494F, -75.6805F, 25.5957F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232331_(-59.0601F, 12.8627F, 38.8675F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(2.6471F, 29.6601F, 93.236F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(58.7446F, -9.089F, 154.1614F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(56.6328F, -64.6631F, 187.5421F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-67.4197F, -77.8257F, 330.7041F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232331_(-82.442F, -72.7817F, 352.1569F), Interpolations.f_232230_),
               new Keyframe(2.0417F, KeyframeAnimations.m_232331_(-95.4399F, -35.8374F, 364.7647F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, 360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_b_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(-5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(1.625F, KeyframeAnimations.m_232302_(-1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232302_(-2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(-6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.875F, KeyframeAnimations.m_232302_(-34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.75F, KeyframeAnimations.m_232302_(-0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-140.7494F, 75.6805F, -25.5957F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-59.0601F, -12.8627F, -38.8675F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(2.6471F, -29.6601F, -93.236F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(58.7446F, 9.089F, -154.1614F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232331_(56.6328F, 64.6631F, -187.5421F), Interpolations.f_232230_),
               new Keyframe(1.0833F, KeyframeAnimations.m_232331_(-67.4197F, 77.8257F, -330.7041F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-82.442F, 72.7817F, -352.1569F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-95.4399F, 35.8374F, -364.7647F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.1654F, 0.0F, -360.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_f_blade",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(5.0F, -4.0F, -5.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232302_(1.0F, 5.0F, -23.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232302_(2.48F, 2.0F, -26.41F), Interpolations.f_232230_),
               new Keyframe(1.0F, KeyframeAnimations.m_232302_(6.0F, 2.0F, -30.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(34.16F, 2.13F, -2.1F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.16F, 0.13F, -0.1F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
}
