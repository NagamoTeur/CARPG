package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityAxeAttack extends EntityMagicEffect {
   private static final EntityDataAccessor<Boolean> VERTICAL = SynchedEntityData.m_135353_(EntityAxeAttack.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<ItemStack> AXE_STACK = SynchedEntityData.m_135353_(EntityAxeAttack.class, EntityDataSerializers.f_135033_);
   public static int SWING_DURATION_HOR = 24;
   public static int SWING_DURATION_VER = 30;
   private float quakeAngle = 0.0F;
   private AABB quakeBB = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

   public EntityAxeAttack(EntityType<? extends EntityAxeAttack> type, Level world) {
      super(type, world);
   }

   public EntityAxeAttack(EntityType<? extends EntityAxeAttack> type, Level world, LivingEntity caster, boolean vertical) {
      super(type, world, caster);
      this.setVertical(vertical);
      this.setAxeStack(caster.m_21205_());
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(VERTICAL, false);
      this.m_20088_().m_135372_(AXE_STACK, ItemHandler.WROUGHT_AXE.m_7968_());
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.caster != null) {
         if (!this.caster.m_6084_()) {
            this.m_146870_();
         }

         this.m_19890_(
            this.caster.m_20185_(),
            this.caster.m_20186_() + (double)this.caster.m_20192_(),
            this.caster.m_20189_(),
            this.caster.m_146908_(),
            this.caster.m_146909_()
         );
      }

      if (!this.f_19853_.f_46443_ && this.f_19797_ == 7) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 0.7F, 1.1F);
      }

      if (!this.f_19853_.f_46443_ && this.caster != null) {
         if (!this.getVertical() && this.f_19797_ == SWING_DURATION_HOR / 2 - 1) {
            this.dealDamage(
               7.0F * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F,
               4.0F,
               160.0F,
               1.2F
            );
         } else if (this.getVertical() && this.f_19797_ == SWING_DURATION_VER / 2 - 1) {
            this.dealDamage(
               ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue(), 4.5F, 40.0F, 0.8F
            );
            this.quakeAngle = this.m_146908_();
            this.quakeBB = this.m_20191_().m_82386_(0.0, (double)(-this.caster.m_20192_()), 0.0);
            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_LAND.get(), 0.3F, 0.5F);
            this.m_5496_(SoundEvents.f_11913_, 2.0F, 0.9F + this.f_19796_.m_188501_() * 0.1F);
         } else if (this.getVertical() && this.f_19797_ == SWING_DURATION_VER / 2 + 1) {
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 10.0F, 0.05F, 0, 10);
         }
      }

      if (this.getVertical() && this.caster != null && this.f_19797_ >= SWING_DURATION_VER / 2) {
         int maxDistance = 16;
         double perpFacing = (double)this.quakeAngle * (Math.PI / 180.0);
         double facingAngle = perpFacing + (Math.PI / 2);
         int hitY = Mth.m_14107_(this.quakeBB.f_82289_ - 0.5);
         int distance = this.f_19797_ - 15;
         double spread = 1.099557410031076;
         int arcLen = Mth.m_14165_((double)distance * spread);
         double minY = this.quakeBB.f_82289_;
         double maxY = this.quakeBB.f_82292_;

         for (int i = 0; i < arcLen; i++) {
            double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
            double vx = Math.cos(theta);
            double vz = Math.sin(theta);
            double px = this.m_20185_() + vx * (double)distance;
            double pz = this.m_20189_() + vz * (double)distance;
            float factor = 1.0F - (float)distance / (float)maxDistance;
            AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);

            for (Entity entity : this.f_19853_.m_45976_(Entity.class, selection)) {
               if (entity.m_20096_() && entity != this && !(entity instanceof FallingBlockEntity) && entity != this.caster) {
                  float applyKnockbackResistance = 0.0F;
                  boolean hitEntity = false;
                  if (entity instanceof LivingEntity) {
                     if (!this.raytraceCheckEntity(entity)) {
                        continue;
                     }

                     if (this.caster instanceof Player) {
                        hitEntity = entity.m_6469_(
                           DamageSource.m_19344_((Player)this.caster),
                           (factor * 5.0F + 1.0F)
                              * (((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F)
                        );
                     } else {
                        hitEntity = entity.m_6469_(
                           DamageSource.m_19370_(this.caster),
                           (factor * 5.0F + 1.0F)
                              * (((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F)
                        );
                     }

                     applyKnockbackResistance = (float)((LivingEntity)entity).m_21051_(Attributes.f_22278_).m_22135_();
                  }

                  if (hitEntity) {
                     double magnitude = -4.0;
                     double x = vx * (double)(1.0F - factor) * magnitude * (double)(1.0F - applyKnockbackResistance);
                     double y = 0.0;
                     if (entity.m_20096_()) {
                        y += 0.15 * (double)(1.0F - applyKnockbackResistance);
                     }

                     double z = vz * (double)(1.0F - factor) * magnitude * (double)(1.0F - applyKnockbackResistance);
                     entity.m_20256_(entity.m_20184_().m_82520_(x, y, z));
                     if (entity instanceof ServerPlayer) {
                        ((ServerPlayer)entity).f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(entity));
                     }
                  }
               }
            }

            if (this.f_19853_.f_46441_.m_188499_()) {
               int hitX = Mth.m_14107_(px);
               int hitZ = Mth.m_14107_(pz);
               BlockPos pos = new BlockPos(hitX, hitY, hitZ);
               BlockPos abovePos = new BlockPos(pos).m_7494_();
               BlockState block = this.f_19853_.m_8055_(pos);
               BlockState blockAbove = this.f_19853_.m_8055_(abovePos);
               if (block.m_60767_() != Material.f_76296_ && block.m_60796_(this.f_19853_, pos) && !block.m_155947_() && !blockAbove.m_60767_().m_76334_()) {
                  EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.f_19853_, block, 0.3F);
                  fallingBlock.m_6034_((double)hitX + 0.5, (double)(hitY + 1), (double)hitZ + 0.5);
                  this.f_19853_.m_7967_(fallingBlock);
               }
            }
         }
      }

      if (this.f_19797_ > SWING_DURATION_HOR) {
         this.m_146870_();
      }
   }

   private void dealDamage(float damage, float range, float arc, float applyKnockback) {
      boolean hit = false;

      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, 2.0, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.m_146908_() % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
               (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
                  + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
            )
            - entityHit.m_20205_() / 2.0F;
         if ((
               entityHit != this.caster
                     && (!(entityHit instanceof Parrot) || entityHit.m_20202_() != this.caster)
                     && entityHitDistance <= range
                     && entityRelativeAngle <= arc / 2.0F
                     && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && this.raytraceCheckEntity(entityHit)) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(this.caster, CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               playerCapability.setAxeCanAttack(true);
               if (this.caster instanceof Player) {
                  this.attackTargetEntityWithCurrentItem(entityHit, (Player)this.caster, damage / ItemHandler.WROUGHT_AXE.m_41008_(), applyKnockback);
               }

               playerCapability.setAxeCanAttack(false);
            } else {
               entityHit.m_6469_(DamageSource.m_19370_(this.caster), damage);
               entityHit.m_20334_(
                  entityHit.m_20184_().f_82479_ * (double)applyKnockback, entityHit.m_20184_().f_82480_, entityHit.m_20184_().f_82481_ * (double)applyKnockback
               );
            }

            hit = true;
         }
      }

      if (hit) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 0.3F, 0.5F);
      }
   }

   public void setVertical(boolean vertical) {
      this.m_20088_().m_135381_(VERTICAL, vertical);
   }

   public boolean getVertical() {
      return (Boolean)this.m_20088_().m_135370_(VERTICAL);
   }

   private List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> list = this.f_19853_.m_45933_(this, this.m_20191_().m_82377_(distanceX, distanceY, distanceZ));
      return list.stream()
         .filter(
            entityNeighbor -> entityNeighbor instanceof LivingEntity
                  && (double)this.m_20270_(entityNeighbor) <= radius + (double)(entityNeighbor.m_20205_() / 2.0F)
         )
         .map(entityNeighbor -> (LivingEntity)entityNeighbor)
         .collect(Collectors.toCollection(ArrayList::new));
   }

   public LivingEntity getCaster() {
      return this.caster;
   }

   public void setAxeStack(ItemStack axeStack) {
      this.m_20088_().m_135381_(AXE_STACK, axeStack);
   }

   public ItemStack getAxeStack() {
      return (ItemStack)this.m_20088_().m_135370_(AXE_STACK);
   }

   public void attackTargetEntityWithCurrentItem(Entity targetEntity, Player player, float damageMult, float knockbackMult) {
      if (ForgeHooks.onPlayerAttackTarget(player, targetEntity)) {
         ItemStack oldStack = player.m_21205_();
         ItemStack newStack = this.getAxeStack();
         player.m_21008_(InteractionHand.MAIN_HAND, newStack);
         player.m_21204_().m_22178_(newStack.m_41638_(EquipmentSlot.MAINHAND));
         if (targetEntity.m_6097_() && !targetEntity.m_7313_(player)) {
            float f = (float)player.m_21133_(Attributes.f_22281_) * damageMult;
            float f1;
            if (targetEntity instanceof LivingEntity) {
               f1 = EnchantmentHelper.m_44833_(player.m_21205_(), ((LivingEntity)targetEntity).m_6336_());
            } else {
               f1 = EnchantmentHelper.m_44833_(player.m_21205_(), MobType.f_21640_);
            }

            float f2 = 1.0F;
            f *= 0.2F + f2 * f2 * 0.8F;
            f1 *= f2;
            if (f > 0.0F || f1 > 0.0F) {
               boolean flag = f2 > 0.9F;
               boolean flag1 = false;
               int i = 0;
               i += EnchantmentHelper.m_44894_(player);
               if (player.m_20142_() && flag) {
                  player.f_19853_
                     .m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12314_, player.m_5720_(), 1.0F, 1.0F);
                  i++;
                  flag1 = true;
               }

               f += f1;
               boolean flag3 = false;
               float f4 = 0.0F;
               boolean flag4 = false;
               int j = EnchantmentHelper.m_44914_(player);
               if (targetEntity instanceof LivingEntity) {
                  f4 = ((LivingEntity)targetEntity).m_21223_();
                  if (j > 0 && !targetEntity.m_6060_()) {
                     flag4 = true;
                     targetEntity.m_20254_(1);
                  }
               }

               Vec3 vector3d = targetEntity.m_20184_();
               boolean flag5 = targetEntity.m_6469_(DamageSource.m_19344_(player), f);
               if (flag5) {
                  if (i > 0) {
                     if (targetEntity instanceof LivingEntity) {
                        ((LivingEntity)targetEntity)
                           .m_147240_(
                              (double)((float)i * 0.5F * knockbackMult),
                              (double)Mth.m_14031_(player.m_146908_() * (float) (Math.PI / 180.0)),
                              (double)(-Mth.m_14089_(player.m_146908_() * (float) (Math.PI / 180.0)))
                           );
                     } else {
                        targetEntity.m_5997_(
                           (double)(-Mth.m_14031_(player.m_146908_() * (float) (Math.PI / 180.0)) * (float)i * 0.5F * knockbackMult),
                           0.1,
                           (double)(Mth.m_14089_(player.m_146908_() * (float) (Math.PI / 180.0)) * (float)i * 0.5F * knockbackMult)
                        );
                     }

                     player.m_20256_(player.m_20184_().m_82542_(0.6, 1.0, 0.6));
                     player.m_6858_(false);
                  }

                  if (targetEntity instanceof ServerPlayer && targetEntity.f_19864_) {
                     ((ServerPlayer)targetEntity).f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(targetEntity));
                     targetEntity.f_19864_ = false;
                     targetEntity.m_20256_(vector3d);
                  }

                  if (flag) {
                     player.f_19853_
                        .m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12316_, player.m_5720_(), 1.0F, 1.0F);
                  } else {
                     player.f_19853_
                        .m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12318_, player.m_5720_(), 1.0F, 1.0F);
                  }

                  if (f1 > 0.0F) {
                     player.m_5700_(targetEntity);
                  }

                  player.m_21335_(targetEntity);
                  if (targetEntity instanceof LivingEntity) {
                     EnchantmentHelper.m_44823_((LivingEntity)targetEntity, player);
                  }

                  EnchantmentHelper.m_44896_(player, targetEntity);
                  ItemStack itemstack1 = player.m_21205_();
                  Entity entity = targetEntity;
                  if (targetEntity instanceof PartEntity) {
                     entity = ((PartEntity)targetEntity).getParent();
                  }

                  if (!player.f_19853_.f_46443_ && !itemstack1.m_41619_() && entity instanceof LivingEntity) {
                     ItemStack copy = itemstack1.m_41777_();
                     itemstack1.m_41640_((LivingEntity)entity, player);
                     if (itemstack1.m_41619_()) {
                        ForgeEventFactory.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
                        player.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
                     }
                  }

                  if (targetEntity instanceof LivingEntity) {
                     float f5 = f4 - ((LivingEntity)targetEntity).m_21223_();
                     player.m_36222_(Stats.f_12928_, Math.round(f5 * 10.0F));
                     if (j > 0) {
                        targetEntity.m_20254_(j * 4);
                     }

                     if (player.f_19853_ instanceof ServerLevel && f5 > 2.0F) {
                        int k = (int)((double)f5 * 0.5);
                        ((ServerLevel)player.f_19853_)
                           .m_8767_(
                              ParticleTypes.f_123798_, targetEntity.m_20185_(), targetEntity.m_20227_(0.5), targetEntity.m_20189_(), k, 0.1, 0.0, 0.1, 0.2
                           );
                     }
                  }

                  player.m_36399_(0.1F);
               } else {
                  player.f_19853_
                     .m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12315_, player.m_5720_(), 1.0F, 1.0F);
                  if (flag4) {
                     targetEntity.m_20095_();
                  }
               }
            }
         }

         player.m_21008_(InteractionHand.MAIN_HAND, oldStack);
         player.m_21204_().m_22178_(oldStack.m_41638_(EquipmentSlot.MAINHAND));
      }
   }

   @Override
   protected void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setAxeStack(ItemStack.m_41712_(compound.m_128469_("axe_stack")));
      this.setVertical(compound.m_128471_("vertical"));
   }

   @Override
   protected void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128365_("axe_stack", this.getAxeStack().m_41739_(new CompoundTag()));
      compound.m_128379_("vertical", this.getVertical());
   }
}
