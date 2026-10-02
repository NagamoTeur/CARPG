package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class Maledictus_Grab_Attack_Animation {
   public static final AnimationDefinition GRAB_START = Builder.m_232275_(1.375F)
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232302_(0.0F, -4.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232302_(0.0F, -5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(34.3216F, -8.4147F, 12.5609F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(36.8216F, -8.4147F, 12.5609F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-37.1029F, 1.6039F, -16.1224F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-42.1029F, 1.6039F, -16.1224F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(52.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(57.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(32.9889F, 23.6766F, 19.6059F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(34.3269F, 27.8395F, 22.6822F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(14.0019F, -44.136F, -9.8511F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(20.0703F, 4.6978F, 1.7139F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(12.7936F, -12.1991F, -2.7471F), Interpolations.f_232230_)
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
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(25.0F, 0.0F, 17.5F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(37.8492F, -3.5513F, 21.0375F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-172.2494F, 25.4103F, -29.2438F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "bow",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)})
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -22.5F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-20.0001F, 14.871F, -20.5313F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232252_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)})
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.7917F, KeyframeAnimations.m_232331_(-21.967F, -28.74F, 2.7045F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-21.7728F, -36.6239F, 2.0429F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-63.8967F, 58.4755F, -13.4376F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-56.0794F, 37.5189F, -50.4811F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-154.8851F, 33.3623F, -144.7622F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -60.88F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.8847F, -8.0835F, -7.5228F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(5.3602F, -12.7686F, -13.5994F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(-20.1202F, 5.3599F, 38.2207F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 73.09F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.375F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition GRAB_LOOP = Builder.m_232275_(0.75F)
      .m_232274_()
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(-75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(14.0019F, -44.136F, -9.8511F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(14.6277F, -46.5585F, -10.7305F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(14.0019F, -44.136F, -9.8511F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.7936F, -12.1991F, -2.7471F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(12.2538F, -12.7413F, -0.2477F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(12.7936F, -12.1991F, -2.7471F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-172.2494F, 25.4103F, -29.2438F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-173.8311F, 26.0373F, -32.1348F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-172.2494F, 25.4103F, -29.2438F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(2.5F, 0.0F, -27.5F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-63.8967F, 58.4755F, -13.4376F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-67.4519F, 59.7946F, -17.5787F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-63.8967F, 58.4755F, -13.4376F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-154.8851F, 33.3623F, -144.7622F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-151.6313F, 32.2774F, -140.8051F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-154.8851F, 33.3623F, -144.7622F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -25.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.1202F, 5.3599F, 38.2207F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-18.5974F, 3.6162F, 33.2933F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-20.1202F, 5.3599F, 38.2207F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, 25.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition GRAB_FAIL = Builder.m_232275_(1.5F)
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(42.5F, -17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(42.5F, -17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -6.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -6.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-102.6772F, -1.2238F, -0.1158F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-102.6772F, -1.2238F, -0.1158F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(80.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(75.2763F, -1.155F, 1.2122F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(75.2763F, -1.155F, 1.2122F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(14.0019F, -44.136F, -9.8511F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-12.512F, -41.9011F, -4.114F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-14.2863F, -49.1984F, -1.6337F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-14.2863F, -49.1984F, -1.6337F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.7936F, -12.1991F, -2.7471F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(37.7121F, -31.3184F, -6.0921F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(33.5785F, -33.4118F, -7.7104F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(33.5785F, -33.4118F, -7.7104F), Interpolations.f_232230_),
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
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-172.2494F, 25.4103F, -29.2438F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-166.4613F, 22.2745F, -18.1256F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(17.4993F, 0.0014F, -50.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-17.4567F, 31.2481F, 8.1697F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-63.8967F, 58.4755F, -13.4376F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-74.2355F, 62.5078F, -25.8477F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(-48.1087F, 73.3029F, -8.5375F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-48.1087F, 73.3029F, -8.5375F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-154.8851F, 33.3623F, -144.7622F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-3.65F, 18.4099F, 23.4528F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-0.4389F, 5.4378F, 43.6748F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, -57.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -75.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.1202F, 5.3599F, 38.2207F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-41.887F, 82.0461F, -7.0045F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-29.6427F, 69.9423F, 8.1088F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 50.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 42.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition GRAB_SUCCESS_FLY = Builder.m_232275_(3.0F)
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(42.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2917F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-7.923F, -14.8632F, 2.0445F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, -4.0F, -3.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -7.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -6.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -7.0F, -2.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232302_(0.0F, -6.0F, -1.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232302_(0.0F, -7.4F, 1.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(0.0F, -7.4F, 1.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232302_(0.0F, -6.4F, 1.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(0.0F, -7.4F, 1.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232302_(0.0F, 4.79F, 1.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(0.0F, 4.79F, 1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-70.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-115.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-49.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-40.696F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-76.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-79.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-66.946F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-79.446F, 12.4879F, 2.5606F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-62.6301F, 2.6629F, 0.574F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-50.1301F, 2.6629F, 0.574F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-40.1301F, 2.6629F, 0.574F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(77.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(82.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(80.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(61.25F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(102.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(110.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(110.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-71.946F, -12.4879F, -2.5606F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(-120.973F, -11.244F, -1.2803F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-5.0F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-2.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(1.6568F, -9.9123F, 2.2089F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-2.5F, -10.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(35.6138F, -10.0508F, -8.1197F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(43.1138F, -10.0508F, -8.1197F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(60.6138F, -10.0508F, -8.1197F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(22.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(62.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(75.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(133.75F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(14.0019F, -44.136F, -9.8511F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(8.6228F, -46.7765F, -9.1863F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-12.512F, -41.9011F, -4.114F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-23.2296F, -29.4295F, -6.2191F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-10.7296F, -29.4295F, -6.2191F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(2.5F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(5.0F, -15.0F, 12.5F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(5.0F, -15.0F, 12.5F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.7936F, -12.1991F, -2.7471F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(6.3055F, -10.7119F, 5.5976F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(37.7121F, -31.3184F, -6.0921F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(24.4097F, -12.4959F, -5.2641F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(18.3804F, -16.9078F, -0.788F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(0.0F, 17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-5.7686F, 29.8742F, -2.8807F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-1.8305F, 29.9854F, 0.0013F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-5.7686F, 29.8742F, -2.8807F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-172.2494F, 25.4103F, -29.2438F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-148.9613F, 22.2745F, -18.1256F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-166.4613F, 22.2745F, -18.1256F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-161.7417F, 33.5722F, -8.1205F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-194.7704F, 50.5318F, -52.9935F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-131.0637F, -10.8153F, -7.7734F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-133.5637F, -10.8153F, -7.7734F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-138.5637F, -10.8153F, -7.7734F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-133.5637F, -10.8153F, -7.7734F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-80.1733F, 7.0512F, 1.2952F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-77.6733F, 7.0512F, 1.2952F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-77.6733F, 7.0512F, 1.2952F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(17.4993F, 0.0014F, -50.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "bow",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-10.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(
            Targets.f_232250_, new Keyframe[]{new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)}
         )
      )
      .m_232279_(
         "halberd",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-52.5F, 0.0F, -25.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-17.4567F, 31.2481F, 8.1697F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-12.7001F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-4.1835F, 20.2281F, -15.7069F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(7.2999F, -34.6292F, -11.5422F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(8.8654F, -21.2558F, -13.2878F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(10.5831F, -22.9003F, -16.8839F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(8.8654F, -21.2558F, -13.2878F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(23.3252F, -16.1126F, -29.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(50.8252F, -16.1126F, -29.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(52.2542F, -9.674F, -33.1051F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.875F, KeyframeAnimations.m_232331_(-94.53F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-20.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.6667F, KeyframeAnimations.m_232331_(-35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-27.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(45.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-63.8967F, 58.4755F, -13.4376F), Interpolations.f_232230_),
               new Keyframe(0.0833F, KeyframeAnimations.m_232331_(-59.3107F, 57.345F, -8.6509F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-74.2355F, 62.5078F, -25.8477F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(-60.2372F, 60.9565F, -17.3325F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-34.8812F, 49.1684F, -21.3996F), Interpolations.f_232230_),
               new Keyframe(0.9167F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.1667F, KeyframeAnimations.m_232331_(-53.4153F, 19.291F, -5.3815F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(-30.7651F, 24.7716F, -3.483F), Interpolations.f_232230_),
               new Keyframe(1.8333F, KeyframeAnimations.m_232331_(-27.3346F, 27.5311F, 4.2887F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-30.7651F, 24.7716F, -3.483F), Interpolations.f_232230_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(-16.7965F, 16.9636F, -20.2704F), Interpolations.f_232230_),
               new Keyframe(2.5F, KeyframeAnimations.m_232331_(-7.9091F, 17.4791F, -7.1923F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-7.9091F, 17.4791F, -7.1923F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-154.8851F, 33.3623F, -144.7622F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(-32.5946F, 43.0903F, -16.2548F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-3.65F, 18.4099F, 23.4528F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-0.3279F, -10.4158F, 47.2801F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-1.1338F, 14.9756F, -2.9187F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-6.8871F, -35.7432F, -9.0988F), Interpolations.f_232230_),
               new Keyframe(1.3333F, KeyframeAnimations.m_232331_(-4.1183F, -13.0819F, -11.3883F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-3.672F, -21.3267F, -20.7325F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-3.3117F, -10.1761F, -6.5267F), Interpolations.f_232230_),
               new Keyframe(2.2917F, KeyframeAnimations.m_232331_(-8.0536F, 39.5594F, -13.6426F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(-12.7197F, 49.3704F, -18.6948F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(0.0F, -17.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, -57.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, -40.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, -30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, -142.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(0.0F, -32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(0.0F, -27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-20.1202F, 5.3599F, 38.2207F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(-32.7777F, 74.1734F, 3.9946F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(-41.887F, 82.0461F, -7.0045F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-37.5195F, 65.2164F, -22.8791F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-1.1338F, -14.9756F, 2.9187F), Interpolations.f_232230_),
               new Keyframe(0.9583F, KeyframeAnimations.m_232331_(-6.8871F, 35.7432F, 9.0988F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-17.7428F, -32.8771F, 25.335F), Interpolations.f_232230_),
               new Keyframe(1.7083F, KeyframeAnimations.m_232331_(-34.9497F, -32.6173F, 44.8983F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(-8.0516F, -39.5597F, 13.6395F), Interpolations.f_232230_),
               new Keyframe(2.375F, KeyframeAnimations.m_232331_(25.5016F, -45.236F, -24.2224F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(42.2575F, -53.7342F, -41.7835F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.125F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(0.0F, 50.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(0.0F, 37.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(0.0F, 30.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.0417F, KeyframeAnimations.m_232331_(0.0F, 145.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.25F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.5F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.7917F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(2.4583F, KeyframeAnimations.m_232331_(0.0F, 27.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(3.0F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_particle",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232229_),
               new Keyframe(2.25F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232229_)
            }
         )
      )
      .m_232282_();
   public static final AnimationDefinition GRAB_DIVE_LOOP = Builder.m_232275_(0.75F)
      .m_232274_()
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(47.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 4.79F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232302_(0.0F, 1.79F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(0.0F, 4.79F, 1.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-40.1301F, 2.6629F, 0.574F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-25.1301F, 2.6629F, 0.574F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-40.1301F, 2.6629F, 0.574F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(110.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(110.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(60.6138F, -10.0508F, -8.1197F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(63.1138F, -10.0508F, -8.1197F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(60.6138F, -10.0508F, -8.1197F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, -15.0F, 12.5F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(5.0F, -15.0F, 12.5F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(19.981F, -4.9809F, 0.4369F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-77.6733F, 7.0512F, 1.2952F), Interpolations.f_232230_),
               new Keyframe(0.375F, KeyframeAnimations.m_232331_(-77.8252F, 4.8277F, 0.4889F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-77.6733F, 7.0512F, 1.2952F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(52.2542F, -9.674F, -33.1051F), Interpolations.f_232230_),
               new Keyframe(0.3333F, KeyframeAnimations.m_232331_(53.1726F, -3.1583F, -36.9331F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(52.2542F, -9.674F, -33.1051F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.5F, KeyframeAnimations.m_232331_(0.0F, 0.0F, -7.5F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.9091F, 17.4791F, -7.1923F), Interpolations.f_232230_),
               new Keyframe(0.4167F, KeyframeAnimations.m_232331_(-0.4091F, 17.4791F, -7.1923F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-7.9091F, 17.4791F, -7.1923F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.7197F, 49.3704F, -18.6948F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(4.9285F, 59.8903F, -0.2259F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(-2.4557F, 58.4295F, -7.931F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-12.7197F, 49.3704F, -18.6948F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.2917F, KeyframeAnimations.m_232331_(0.0F, -7.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.625F, KeyframeAnimations.m_232331_(0.0F, -1.59F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.2575F, -53.7342F, -41.7835F), Interpolations.f_232230_),
               new Keyframe(0.2083F, KeyframeAnimations.m_232331_(44.2823F, -55.6606F, -48.9866F), Interpolations.f_232230_),
               new Keyframe(0.5417F, KeyframeAnimations.m_232331_(31.9606F, -50.3264F, -32.8088F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(42.2575F, -53.7342F, -41.7835F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.4583F, KeyframeAnimations.m_232331_(0.0F, 5.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_particle",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232282_();
   public static final AnimationDefinition GRAB_LAND = Builder.m_232275_(1.75F)
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(50.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(43.8352F, -10.4866F, -5.2979F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(23.1437F, -21.6792F, -7.6773F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(13.1437F, -21.6792F, -7.6773F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(13.1437F, -21.6792F, -7.6773F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "berserker",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(0.0F, 4.79F, 1.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232302_(0.0F, -11.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232302_(0.0F, -11.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232302_(0.0F, -11.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-40.1301F, 2.6629F, 0.574F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-100.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-117.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(-112.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(-112.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(110.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(107.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(105.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(60.6138F, -10.0508F, -8.1197F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(40.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_leg",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(35.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(7.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "pelvis",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(5.0F, -15.0F, 12.5F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 32.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(17.5F, -22.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(24.5346F, -19.5333F, -4.5921F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(17.2091F, -20.1138F, 0.8004F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_),
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(17.481F, -4.9809F, 0.4369F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(6.2706F, 37.353F, 3.3773F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(17.5239F, 7.4904F, 0.2197F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(15.0239F, 7.4904F, 0.2197F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "body",
         new AnimationChannel(Targets.f_232250_, new Keyframe[]{new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-77.6733F, 7.0512F, 1.2952F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-78.5637F, -10.8153F, -7.7734F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_shoulder",
         new AnimationChannel(
            Targets.f_232250_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232302_(2.6074F, 3.0948F, -2.9366F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232302_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(15.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_shoulder",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(52.2542F, -9.674F, -33.1051F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-29.7928F, 54.7448F, -20.9667F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-62.7721F, 27.3965F, -17.4226F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(12.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_front_arm",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(-60.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(17.5F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(5.0F, 0.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)})
      )
      .m_232279_(
         "left_mace",
         new AnimationChannel(
            Targets.f_232252_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232229_),
               new Keyframe(1.75F, KeyframeAnimations.m_232298_(0.0, 0.0, 0.0), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "head",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-7.9091F, 17.4791F, -7.1923F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(14.8787F, -47.2626F, -5.4333F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(29.5224F, 23.2083F, -6.1313F), Interpolations.f_232230_),
               new Keyframe(0.6667F, KeyframeAnimations.m_232331_(32.0224F, 23.2083F, -6.1313F), Interpolations.f_232230_),
               new Keyframe(1.125F, KeyframeAnimations.m_232331_(32.0224F, 23.2083F, -6.1313F), Interpolations.f_232230_),
               new Keyframe(1.5417F, KeyframeAnimations.m_232331_(22.7289F, 6.8765F, -1.8167F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(-2.5F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(-12.7197F, 49.3704F, -18.6948F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, -69.98F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(-27.719F, 7.7033F, 10.3442F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(-20.9476F, 23.4966F, -1.3266F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(-20.9476F, 23.4966F, -1.3266F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "left_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, -12.5F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, -85.8F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, -91.82F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, -57.39F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, -57.39F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(42.2575F, -53.7342F, -41.7835F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 5.55F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(6.2161F, -24.6478F, -18.4273F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(3.1108F, -18.4505F, -10.1164F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(3.1108F, -18.4505F, -10.1164F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_wing2",
         new AnimationChannel(
            Targets.f_232251_,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.m_232331_(0.0F, 15.0F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.1667F, KeyframeAnimations.m_232331_(0.0F, 71.15F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.25F, KeyframeAnimations.m_232331_(0.0F, 29.69F, 0.0F), Interpolations.f_232230_),
               new Keyframe(0.75F, KeyframeAnimations.m_232331_(0.0F, 63.79F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.2083F, KeyframeAnimations.m_232331_(0.0F, 63.79F, 0.0F), Interpolations.f_232230_),
               new Keyframe(1.75F, KeyframeAnimations.m_232331_(0.0F, 0.0F, 0.0F), Interpolations.f_232230_)
            }
         )
      )
      .m_232279_(
         "right_particle",
         new AnimationChannel(Targets.f_232251_, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.m_232331_(90.0F, 0.0F, 0.0F), Interpolations.f_232229_)})
      )
      .m_232282_();
}
