package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.tile.MageBlockTile;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.slf4j.Logger;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EnchantedFallingBlock extends ColoredProjectile implements IAnimatable {
   private static final Logger LOGGER = LogUtils.getLogger();
   public BlockState blockState = Blocks.f_49992_.m_49966_();
   public int time;
   public boolean dropItem = true;
   public boolean cancelDrop;
   private boolean hurtEntities;
   private int fallDamageMax = 40;
   private float fallDamagePerDistance;
   public int knockback = 2;
   @Nullable
   public CompoundTag blockData;
   public SpellContext context;
   public float baseDamage;
   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.m_135353_(
      EnchantedFallingBlock.class, EntityDataSerializers.f_135038_
   );
   private IntOpenHashSet piercingIgnoreEntityIds = new IntOpenHashSet(5);

   public EnchantedFallingBlock(EntityType<? extends ColoredProjectile> p_31950_, Level p_31951_) {
      super(p_31950_, p_31951_);
   }

   public EnchantedFallingBlock(Level world, double v, double y, double v1, BlockState blockState) {
      this((EntityType<? extends ColoredProjectile>)ModEntities.ENCHANTED_FALLING_BLOCK.get(), world);
      this.blockState = blockState;
      this.f_19850_ = true;
      this.m_6034_(v, y, v1);
      this.m_20256_(Vec3.f_82478_);
      this.f_19854_ = v;
      this.f_19855_ = y;
      this.f_19856_ = v1;
      this.setStartPos(this.m_20183_());
   }

   public EnchantedFallingBlock(Level world, BlockPos pos, BlockState blockState) {
      this(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), blockState);
   }

   public static boolean canFall(Level level, BlockPos pos, LivingEntity owner, SpellStats spellStats) {
      return !level.m_46859_(pos)
            && level.m_6425_(pos).m_76178_()
            && !level.m_8055_(pos).m_204336_(BlockTagProvider.RELOCATION_NOT_SUPPORTED)
            && (level.m_7702_(pos) == null || level.m_7702_(pos) instanceof MageBlockTile || level.m_7702_(pos) instanceof SkullBlockEntity)
         ? BlockUtil.canBlockBeHarvested(spellStats, level, pos) && BlockUtil.destroyRespectsClaim(owner, level, pos)
         : false;
   }

   @Nullable
   public static EnchantedFallingBlock fall(Level level, BlockPos pos, LivingEntity owner, SpellContext context, SpellResolver resolver, SpellStats spellStats) {
      if (!canFall(level, pos, owner, spellStats)) {
         return null;
      } else {
         BlockState blockState = level.m_8055_(pos);
         EnchantedFallingBlock fallingblockentity;
         if (level.m_7702_(pos) instanceof MageBlockTile tile) {
            fallingblockentity = new EnchantedMageblock(
               level,
               (double)pos.m_123341_() + 0.5,
               pos.m_123342_(),
               (double)pos.m_123343_() + 0.5,
               blockState.m_61138_(BlockStateProperties.f_61362_) ? (BlockState)blockState.m_61124_(BlockStateProperties.f_61362_, Boolean.FALSE) : blockState
            );
            fallingblockentity.blockData = tile.m_187482_();
            fallingblockentity.setColor(tile.color);
         } else if (level.m_7702_(pos) instanceof SkullBlockEntity tile) {
            fallingblockentity = new EnchantedSkull(
               level,
               (double)pos.m_123341_() + 0.5,
               (double)pos.m_123342_(),
               (double)pos.m_123343_() + 0.5,
               blockState.m_61138_(BlockStateProperties.f_61362_) ? (BlockState)blockState.m_61124_(BlockStateProperties.f_61362_, Boolean.FALSE) : blockState
            );
            fallingblockentity.blockData = tile.m_187482_();
         } else {
            fallingblockentity = new EnchantedFallingBlock(
               level,
               (double)pos.m_123341_() + 0.5,
               (double)pos.m_123342_(),
               (double)pos.m_123343_() + 0.5,
               blockState.m_61138_(BlockStateProperties.f_61362_) ? (BlockState)blockState.m_61124_(BlockStateProperties.f_61362_, Boolean.FALSE) : blockState
            );
         }

         level.m_7967_(fallingblockentity);
         fallingblockentity.m_5602_(owner);
         fallingblockentity.context = context;
         fallingblockentity.baseDamage = (float)(9.0 + spellStats.getDamageModifier());
         fallingblockentity.dropItem = !blockState.m_204336_(BlockTagProvider.GRAVITY_BLACKLIST);
         if (resolver.hasFocus(ItemsRegistry.SHAPERS_FOCUS.get().m_7968_())) {
            fallingblockentity.hurtEntities = true;
         }

         level.m_7731_(pos, blockState.m_60819_().m_76188_(), 3);
         return fallingblockentity;
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENCHANTED_FALLING_BLOCK.get();
   }

   public boolean m_7337_(Entity pEntity) {
      return super.m_7337_(pEntity) && !(pEntity instanceof FallingBlockEntity) && !(pEntity instanceof EnchantedFallingBlock) && pEntity != this.m_37282_();
   }

   protected boolean m_5603_(Entity p_37250_) {
      return super.m_5603_(p_37250_) && p_37250_ != this.m_37282_() && !this.piercingIgnoreEntityIds.contains(p_37250_.m_19879_());
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.blockState.m_60795_()) {
         this.m_146870_();
      } else {
         Block block = this.blockState.m_60734_();
         this.time++;
         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
         }

         EntityHitResult hitEntity = this.findHitEntity(this.f_19825_, this.f_19825_.m_82549_(this.m_20184_()));
         if (hitEntity != null) {
            this.m_5790_(hitEntity);
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (!this.f_19853_.f_46443_) {
            BlockPos blockpos = this.m_20183_();
            boolean isConcrete = this.blockState.m_60734_() instanceof ConcretePowderBlock;
            boolean isConcreteInWater = isConcrete && this.f_19853_.m_6425_(blockpos).m_205070_(FluidTags.f_13131_);
            double d0 = this.m_20184_().m_82556_();
            if (isConcrete && d0 > 1.0) {
               BlockHitResult blockhitresult = this.f_19853_
                  .m_45547_(
                     new ClipContext(
                        new Vec3(this.f_19854_, this.f_19855_, this.f_19856_),
                        this.m_20182_(),
                        net.minecraft.world.level.ClipContext.Block.COLLIDER,
                        Fluid.SOURCE_ONLY,
                        this
                     )
                  );
               if (blockhitresult.m_6662_() != Type.MISS && this.f_19853_.m_6425_(blockhitresult.m_82425_()).m_205070_(FluidTags.f_13131_)) {
                  blockpos = blockhitresult.m_82425_();
                  isConcreteInWater = true;
               }
            }

            if (this.f_19861_ || isConcreteInWater) {
               this.groundBlock(false);
            } else if (!this.f_19853_.f_46443_
               && (
                  this.time > 100 && (blockpos.m_123342_() <= this.f_19853_.m_141937_() || blockpos.m_123342_() > this.f_19853_.m_151558_()) || this.time > 600
               )) {
               if (this.dropItem && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                  this.m_19998_(block);
               }

               this.m_146870_();
            }
         }

         this.m_20256_(this.m_20184_().m_82490_(0.98));
      }
   }

   public BlockPos groundBlock(boolean ignoreAir) {
      Block block = this.blockState.m_60734_();
      BlockPos blockpos = this.m_20183_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      boolean isConcrete = this.blockState.m_60734_() instanceof ConcretePowderBlock;
      boolean isConcreteInWater = isConcrete && this.f_19853_.m_6425_(blockpos).m_205070_(FluidTags.f_13131_);
      this.m_20256_(this.m_20184_().m_82542_(0.7, -0.5, 0.7));
      if (blockstate.m_60713_(Blocks.f_50110_)) {
         return null;
      } else if (this.cancelDrop) {
         this.m_146870_();
         this.callOnBrokenAfterFall(block, blockpos);
         return null;
      } else {
         boolean canBeReplaced = blockstate.m_60629_(new DirectionalPlaceContext(this.f_19853_, blockpos, Direction.DOWN, ItemStack.f_41583_, Direction.UP));
         boolean isFreeBelow = FallingBlock.m_53241_(this.f_19853_.m_8055_(blockpos.m_7495_())) && (!isConcrete || !isConcreteInWater);
         boolean canSurvive = this.blockState.m_60710_(this.f_19853_, blockpos) && (!isFreeBelow || ignoreAir);
         if (canBeReplaced && canSurvive) {
            if (this.blockState.m_61138_(BlockStateProperties.f_61362_) && this.f_19853_.m_6425_(blockpos).m_76152_() == Fluids.f_76193_) {
               this.blockState = (BlockState)this.blockState.m_61124_(BlockStateProperties.f_61362_, Boolean.TRUE);
            }

            if (this.f_19853_.m_7731_(blockpos, this.blockState, 3)) {
               ((ServerLevel)this.f_19853_).m_7726_().f_8325_.m_140201_(this, new ClientboundBlockUpdatePacket(blockpos, this.f_19853_.m_8055_(blockpos)));
               this.m_146870_();
               if (block instanceof Fallable fallable) {
                  fallable.m_48792_(
                     this.f_19853_,
                     blockpos,
                     this.blockState,
                     blockstate,
                     new FallingBlockEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.blockState)
                  );
               }

               if (this.blockData != null && this.blockState.m_155947_()) {
                  BlockEntity blockentity = this.f_19853_.m_7702_(blockpos);
                  if (blockentity != null) {
                     try {
                        blockentity.m_142466_(this.blockData);
                        if (blockentity instanceof SkullBlockEntity sk && this.blockData != null && this.blockData.m_128441_("SkullOwner")) {
                           sk.m_59769_(new GameProfile(null, this.blockData.m_128461_("SkullOwner")));
                        }
                     } catch (Exception var12) {
                     }

                     blockentity.m_6596_();
                  }
               }

               if (this.f_19853_.m_7702_(blockpos) instanceof MageBlockTile mbt) {
                  mbt.color = this.getParticleColor();
                  mbt.m_6596_();
               }

               return blockpos;
            }

            if (this.dropItem && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
               this.m_146870_();
               this.callOnBrokenAfterFall(block, blockpos);
               ItemStack itemstack = new ItemStack(block);
               if (this.blockData != null && !itemstack.m_41782_() && this.getBlockState().m_60713_(Blocks.f_50316_)) {
                  itemstack.m_41751_(this.blockData);
               }

               this.m_19983_(itemstack);
               return null;
            }
         } else {
            this.m_146870_();
            if (this.dropItem && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
               this.callOnBrokenAfterFall(block, blockpos);
               ItemStack itemstack = new ItemStack(block);
               if (this.blockData != null && !itemstack.m_41782_() && this.getBlockState().m_60713_(Blocks.f_50316_)) {
                  itemstack.m_41751_(this.blockData);
               }

               this.m_19983_(itemstack);
            }
         }

         return null;
      }
   }

   public float getStateDamageBonus() {
      float destroySpeed = 1.0F;

      try {
         destroySpeed = this.blockState.m_60800_(this.f_19853_, this.m_20183_());
      } catch (Exception var3) {
      }

      return destroySpeed;
   }

   protected void m_5790_(EntityHitResult pResult) {
      if (this.hurtEntities) {
         super.m_5790_(pResult);
         Entity entity = pResult.m_82443_();
         float f = (float)this.m_20184_().m_82553_();
         int i = Mth.m_14165_(Mth.m_14008_(Math.min((double)f, 2.5) * (double)this.baseDamage + (double)this.getStateDamageBonus(), 0.0, 2.147483647E9));
         this.piercingIgnoreEntityIds.add(entity.m_19879_());
         Entity owner = this.m_37282_();
         DamageSource damagesource;
         if (owner == null) {
            damagesource = new IndirectEntityDamageSource("an_enchantedBlock", this, owner);
         } else {
            damagesource = new IndirectEntityDamageSource("an_enchantedBlock", this, owner);
            if (owner instanceof LivingEntity livingOwner) {
               livingOwner.m_21335_(entity);
            }
         }

         boolean isEnderman = entity.m_6095_() == EntityType.f_20566_;
         int k = entity.m_20094_();
         if (this.m_6060_() && !isEnderman) {
            entity.m_20254_(5);
         }

         if (entity.m_6469_(damagesource, (float)i)) {
            if (isEnderman) {
               return;
            }

            if (entity instanceof LivingEntity livingentity) {
               if (this.knockback > 0) {
                  Vec3 vec3 = this.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)this.knockback * 0.6);
                  if (vec3.m_82556_() > 0.0) {
                     livingentity.m_5997_(vec3.f_82479_, 0.1, vec3.f_82481_);
                  }
               }

               if (!this.f_19853_.f_46443_ && owner instanceof LivingEntity) {
                  EnchantmentHelper.m_44823_(livingentity, owner);
                  EnchantmentHelper.m_44896_((LivingEntity)owner, livingentity);
               }

               this.doPostHurtEffects(livingentity);
            }

            this.m_5496_(this.blockState.m_60827_().m_56775_(), 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
         } else {
            entity.m_7311_(k);
         }
      }
   }

   private void doPostHurtEffects(LivingEntity livingentity) {
   }

   public void m_7976_(CrashReportCategory pCategory) {
      super.m_7976_(pCategory);
      pCategory.m_128159_("Immitating BlockState", this.blockState.toString());
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   public boolean m_6127_() {
      return true;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this, Block.m_49956_(this.getBlockState()));
   }

   public void m_141965_(ClientboundAddEntityPacket pPacket) {
      super.m_141965_(pPacket);
      this.blockState = Block.m_49803_(pPacket.m_131509_());
      this.f_19850_ = true;
      double d0 = pPacket.m_131500_();
      double d1 = pPacket.m_131501_();
      double d2 = pPacket.m_131502_();
      this.m_6034_(d0, d1, d2);
      this.setStartPos(this.m_20183_());
   }

   public void callOnBrokenAfterFall(Block p_149651_, BlockPos p_149652_) {
      if (p_149651_ instanceof Fallable) {
         ((Fallable)p_149651_)
            .m_142525_(this.f_19853_, p_149652_, new FallingBlockEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.blockState));
      }
   }

   public boolean m_142535_(float pFallDistance, float pMultiplier, DamageSource pSource) {
      if (!this.hurtEntities) {
         return false;
      } else {
         int i = Mth.m_14167_(pFallDistance - 1.0F);
         if (i < 0) {
            return false;
         } else {
            Predicate<Entity> predicate;
            DamageSource damagesource;
            if (this.blockState.m_60734_() instanceof Fallable fallable) {
               predicate = fallable.m_142398_();
               damagesource = fallable.m_142088_();
            } else {
               predicate = EntitySelector.f_20408_;
               damagesource = DamageSource.f_19322_;
            }

            float f = (float)Math.min(Mth.m_14143_((float)i * this.fallDamagePerDistance), this.fallDamageMax);
            this.f_19853_.m_6249_(this, this.m_20191_(), predicate).forEach(p_149649_ -> p_149649_.m_6469_(damagesource, f));
            boolean flag = this.blockState.m_204336_(BlockTags.f_13033_);
            if (flag && f > 0.0F && this.f_19796_.m_188501_() < 0.05F + (float)i * 0.05F) {
               BlockState blockstate = AnvilBlock.m_48824_(this.blockState);
               if (blockstate == null) {
                  this.cancelDrop = true;
               } else {
                  this.blockState = blockstate;
               }
            }

            return false;
         }
      }
   }

   @Override
   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128365_("BlockState", NbtUtils.m_129202_(this.blockState));
      pCompound.m_128405_("Time", this.time);
      pCompound.m_128379_("DropItem", this.dropItem);
      pCompound.m_128379_("HurtEntities", this.hurtEntities);
      pCompound.m_128350_("FallHurtAmount", this.fallDamagePerDistance);
      pCompound.m_128405_("FallHurtMax", this.fallDamageMax);
      if (this.blockData != null) {
         pCompound.m_128365_("TileEntityData", this.blockData);
      }
   }

   @Override
   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
   }

   protected void m_7378_(CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.blockState = NbtUtils.m_129241_(pCompound.m_128469_("BlockState"));
      this.time = pCompound.m_128451_("Time");
      if (pCompound.m_128425_("HurtEntities", 99)) {
         this.hurtEntities = pCompound.m_128471_("HurtEntities");
         this.fallDamagePerDistance = pCompound.m_128457_("FallHurtAmount");
         this.fallDamageMax = pCompound.m_128451_("FallHurtMax");
      } else if (this.blockState.m_204336_(BlockTags.f_13033_)) {
         this.hurtEntities = true;
      }

      if (pCompound.m_128425_("DropItem", 99)) {
         this.dropItem = pCompound.m_128471_("DropItem");
      }

      if (pCompound.m_128425_("TileEntityData", 10)) {
         this.blockData = pCompound.m_128469_("TileEntityData");
      }

      if (this.blockState.m_60795_()) {
         this.blockState = Blocks.f_49992_.m_49966_();
      }
   }

   public void setHurtsEntities(float p_149657_, int p_149658_) {
      this.hurtEntities = true;
      this.fallDamagePerDistance = p_149657_;
      this.fallDamageMax = p_149658_;
   }

   public boolean m_6097_() {
      return false;
   }

   public void setStartPos(BlockPos pOrigin) {
      this.f_19804_.m_135381_(DATA_START_POS, pOrigin);
   }

   public BlockPos getStartPos() {
      return (BlockPos)this.f_19804_.m_135370_(DATA_START_POS);
   }

   protected MovementEmission m_142319_() {
      return MovementEmission.NONE;
   }

   @Override
   public void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(DATA_START_POS, BlockPos.f_121853_);
   }

   public boolean m_6087_() {
      return !this.m_213877_();
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return GeckoLibUtil.createFactory(this);
   }
}
