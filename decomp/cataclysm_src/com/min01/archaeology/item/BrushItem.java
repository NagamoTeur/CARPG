package com.min01.archaeology.item;

import com.min01.archaeology.block.BrushableBlock;
import com.min01.archaeology.blockentity.BrushableBlockEntity;
import com.min01.archaeology.init.ArchaeologyArmPose;
import com.min01.archaeology.init.ArchaeologySounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public class BrushItem extends Item {
   public static final int ANIMATION_DURATION = 10;
   private static final int USE_DURATION = 200;
   private static final double MAX_BRUSH_DISTANCE = Math.sqrt(Mth.m_144952_(6.0)) - 1.0;

   public BrushItem(Properties properties) {
      super(properties);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Player player = context.m_43723_();
      if (player != null && this.calculateHitResult(player).m_6662_() == Type.BLOCK) {
         player.m_6672_(context.m_43724_());
      }

      return InteractionResult.CONSUME;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.CUSTOM;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(
         new IClientItemExtensions() {
            public boolean applyForgeHandTransform(
               PoseStack pose, LocalPlayer player, HumanoidArm arm, ItemStack stackInHand, float partialTick, float equipProcess, float swingProcess
            ) {
               if (player.m_6117_() && player.m_21212_() > 0) {
                  int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                  pose.m_85837_((double)((float)i * 0.56F), (double)(-0.52F + equipProcess * -0.6F), -0.72F);
                  float f = (float)(player.m_21212_() % 10);
                  float f1 = f - partialTick + 1.0F;
                  float f2 = 1.0F - f1 / 10.0F;
                  float f7 = -15.0F + 75.0F * Mth.m_14089_(f2 * 2.0F * (float) Math.PI);
                  if (arm != HumanoidArm.RIGHT) {
                     pose.m_85837_(0.1, 0.83, 0.35);
                     pose.m_85845_(Vector3f.f_122223_.m_122240_(-80.0F));
                     pose.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
                     pose.m_85845_(Vector3f.f_122223_.m_122240_(f7));
                     pose.m_85837_(-0.3, 0.22, 0.35);
                  } else {
                     pose.m_85837_(-0.25, 0.22, 0.35);
                     pose.m_85845_(Vector3f.f_122223_.m_122240_(-80.0F));
                     pose.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
                     pose.m_85845_(Vector3f.f_122227_.m_122240_(0.0F));
                     pose.m_85845_(Vector3f.f_122223_.m_122240_(f7));
                  }
               }

               return false;
            }

            @Nullable
            public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
               return ArchaeologyArmPose.BRUSH;
            }
         }
      );
   }

   public int m_8105_(ItemStack stack) {
      return 200;
   }

   public void m_5929_(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
      if (remainingUseDuration >= 0 && entity instanceof Player player) {
         HitResult hitresult = this.calculateHitResult(entity);
         if (hitresult instanceof BlockHitResult blockhitresult && hitresult.m_6662_() == Type.BLOCK) {
            int useDuration = this.m_8105_(stack) - remainingUseDuration + 1;
            if (useDuration % 10 == 5) {
               BlockPos position = blockhitresult.m_82425_();
               BlockState state = level.m_8055_(position);
               HumanoidArm humanoidarm = entity.m_7655_() == InteractionHand.MAIN_HAND ? player.m_5737_() : player.m_5737_().m_20828_();
               this.spawnDustParticles(level, blockhitresult, state, entity.m_20252_(0.0F), humanoidarm);
               SoundEvent soundevent = state.m_60734_() instanceof BrushableBlock brushable
                  ? brushable.getBrushSound()
                  : (SoundEvent)ArchaeologySounds.BRUSH_GENERIC.get();
               level.m_5594_(player, position, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
               if (!level.m_5776_()
                  && level.m_7702_(position) instanceof BrushableBlockEntity brushableEntity
                  && brushableEntity.brush(level.m_46467_(), player, blockhitresult.m_82434_())) {
                  EquipmentSlot equipmentslot = stack.equals(player.m_6844_(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                  stack.m_41622_(1, entity, breaker -> {
                     breaker.m_21166_(equipmentslot);
                     ForgeEventFactory.onPlayerDestroyItem(player, stack, breaker.m_7655_());
                  });
               }
            }

            return;
         }

         entity.m_21253_();
      } else {
         entity.m_21253_();
      }
   }

   private HitResult calculateHitResult(LivingEntity livingEntity) {
      return getHitResultOnViewVector(livingEntity, entity -> !entity.m_5833_() && entity.m_6087_(), MAX_BRUSH_DISTANCE);
   }

   public static HitResult getHitResultOnViewVector(Entity entity, Predicate<Entity> filter, double distance) {
      Vec3 view = entity.m_20252_(0.0F).m_82490_(distance);
      return getHitResult(entity.m_146892_(), entity, filter, view, entity.f_19853_);
   }

   private static HitResult getHitResult(Vec3 from, Entity entity, Predicate<Entity> filter, Vec3 view, Level level) {
      Vec3 to = from.m_82549_(view);
      HitResult blockHitResult = level.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, entity));
      if (blockHitResult.m_6662_() != Type.MISS) {
         to = blockHitResult.m_82450_();
      }

      HitResult entityHitResult = ProjectileUtil.m_37304_(level, entity, from, to, entity.m_20191_().m_82369_(view).m_82400_(1.0), filter);
      if (entityHitResult != null) {
         blockHitResult = entityHitResult;
      }

      return blockHitResult;
   }

   public void spawnDustParticles(Level level, BlockHitResult hitResult, BlockState state, Vec3 vector, HumanoidArm arm) {
      double speedMultiplier = 3.0;
      double min = 1.0E-6;
      int armType = arm == HumanoidArm.RIGHT ? 1 : -1;
      int particleAmount = level.m_213780_().m_216339_(7, 12);
      BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.f_123794_, state);
      Direction direction = hitResult.m_82434_();
      BrushItem.DustParticlesDelta particleDelta = BrushItem.DustParticlesDelta.fromDirection(vector, direction);
      Vec3 location = hitResult.m_82450_();

      for (int i = 0; i < particleAmount; i++) {
         double x = location.f_82479_ - (direction == Direction.WEST ? min : 0.0);
         double z = location.f_82481_ - (direction == Direction.NORTH ? min : 0.0);
         double xSpeed = particleDelta.xd() * (double)armType * speedMultiplier * level.m_213780_().m_188500_();
         double zSpeed = particleDelta.zd() * (double)armType * speedMultiplier * level.m_213780_().m_188500_();
         level.m_7106_(blockparticleoption, x, location.f_82480_, z, xSpeed, 0.0, zSpeed);
      }
   }

   static record DustParticlesDelta(double xd, double yd, double zd) {
      private static final double ALONG_SIDE_DELTA = 1.0;
      private static final double OUT_FROM_SIDE_DELTA = 0.1;

      public static BrushItem.DustParticlesDelta fromDirection(Vec3 vector, Direction direction) {
         return switch (direction) {
            case DOWN, UP -> new BrushItem.DustParticlesDelta(vector.m_7094_(), 0.0, -vector.m_7096_());
            case NORTH -> new BrushItem.DustParticlesDelta(1.0, 0.0, -0.1);
            case SOUTH -> new BrushItem.DustParticlesDelta(-1.0, 0.0, 0.1);
            case WEST -> new BrushItem.DustParticlesDelta(-0.1, 0.0, -1.0);
            case EAST -> new BrushItem.DustParticlesDelta(0.1, 0.0, 1.0);
            default -> throw new IncompatibleClassChangeError();
         };
      }
   }
}
