package shadows.apotheosis.village.fletching.arrows;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.util.BlockUtil;

public class MiningArrowEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
   protected int blocksBroken = 0;
   protected UUID playerId = null;
   protected ItemStack breakerItem = ItemStack.f_41583_;
   protected MiningArrowEntity.Type type = MiningArrowEntity.Type.IRON;

   public MiningArrowEntity(EntityType<? extends AbstractArrow> t, Level world) {
      super(t, world);
      this.f_36705_ = Pickup.DISALLOWED;
   }

   public MiningArrowEntity(Level world) {
      this((EntityType<? extends AbstractArrow>)Apoth.Entities.MINING_ARROW.get(), world);
   }

   public MiningArrowEntity(LivingEntity shooter, Level world, ItemStack breakerItem, MiningArrowEntity.Type type) {
      super((EntityType)Apoth.Entities.MINING_ARROW.get(), shooter, world);
      this.breakerItem = breakerItem;
      this.f_36705_ = Pickup.DISALLOWED;
      this.type = type;
      this.playerId = shooter.m_20148_();
   }

   public MiningArrowEntity(Level world, double x, double y, double z, ItemStack breakerItem, MiningArrowEntity.Type type) {
      super((EntityType)Apoth.Entities.MINING_ARROW.get(), x, y, z, world);
      this.f_36705_ = Pickup.DISALLOWED;
      this.breakerItem = breakerItem;
      this.type = type;
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.m_20115_(6, this.m_142038_());
      }

      this.m_6075_();
      boolean noClip = this.m_36797_();
      Vec3 motion = this.m_20184_();
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double d0 = motion.m_165924_();
         this.m_146922_((float)(Mth.m_14136_(motion.f_82479_, motion.f_82481_) * 180.0F / (float)Math.PI));
         this.m_146926_((float)(Mth.m_14136_(motion.f_82480_, d0) * 180.0F / (float)Math.PI));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }

      BlockPos blockpos = this.m_20183_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      if (!blockstate.m_60795_() && !noClip) {
         VoxelShape voxelshape = blockstate.m_60812_(this.f_19853_, blockpos);
         if (!voxelshape.m_83281_()) {
            Vec3 vec31 = this.m_20182_();

            for (AABB aabb : voxelshape.m_83299_()) {
               if (aabb.m_82338_(blockpos).m_82390_(vec31)) {
                  this.f_36703_ = true;
                  break;
               }
            }
         }
      }

      if (this.f_36706_ > 0) {
         this.f_36706_--;
      }

      if (this.m_20070_() || blockstate.m_60713_(Blocks.f_152499_)) {
         this.m_20095_();
      }

      if (this.f_36703_) {
         this.m_146870_();
      } else {
         this.f_36704_ = 0;
         Vec3 pos = this.m_20182_();
         Vec3 posNextTick = pos.m_82549_(motion);
         int iterations = 0;

         while (!this.f_19853_.f_46443_ && this.m_6084_()) {
            HitResult traceResult = this.f_19853_.m_45547_(new ClipContext(pos, posNextTick, Block.COLLIDER, Fluid.NONE, this));
            if (traceResult.m_6662_() == net.minecraft.world.phys.HitResult.Type.MISS) {
               break;
            }

            if (traceResult.m_6662_() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
               this.m_6532_(traceResult);
            }

            if (iterations++ > 10) {
               break;
            }
         }

         motion = this.m_20184_();
         double dX = motion.f_82479_;
         double dY = motion.f_82480_;
         double dZ = motion.f_82481_;
         if (this.m_36792_()) {
            for (int i = 0; i < 4; i++) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123797_,
                     this.m_20185_() + dX * (double)i / 4.0,
                     this.m_20186_() + dY * (double)i / 4.0,
                     this.m_20189_() + dZ * (double)i / 4.0,
                     -dX,
                     -dY + 0.2,
                     -dZ
                  );
            }
         }

         double nextX = this.m_20185_() + dX;
         double nextY = this.m_20186_() + dY;
         double nextZ = this.m_20189_() + dZ;
         double hDist = motion.m_165924_();
         if (noClip) {
            this.m_146922_((float)(Mth.m_14136_(-dX, -dZ) * 180.0F / (float)Math.PI));
         } else {
            this.m_146922_((float)(Mth.m_14136_(dX, dZ) * 180.0F / (float)Math.PI));
         }

         this.m_146926_((float)(Mth.m_14136_(dY, hDist) * 180.0F / (float)Math.PI));
         this.m_146926_(m_37273_(this.f_19860_, this.m_146909_()));
         this.m_146922_(m_37273_(this.f_19859_, this.m_146908_()));
         float f = 0.99F;
         if (this.m_20069_()) {
            for (int j = 0; j < 4; j++) {
               this.f_19853_.m_7106_(ParticleTypes.f_123795_, nextX - dX * 0.25, nextY - dY * 0.25, nextZ - dZ * 0.25, dX, dY, dZ);
            }

            f = this.m_6882_();
         }

         this.m_20256_(motion.m_82490_((double)f));
         if (!this.m_20068_() && !noClip) {
            Vec3 vec34 = this.m_20184_();
            this.m_20334_(vec34.f_82479_, vec34.f_82480_ - 0.05F, vec34.f_82481_);
         }

         this.m_6034_(nextX, nextY, nextZ);
      }
   }

   protected void m_8060_(BlockHitResult res) {
      this.breakBlock(res.m_82425_());
   }

   public boolean m_20068_() {
      return false;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("blocks_broken", this.blocksBroken);
      if (this.playerId != null) {
         compound.m_128362_("player_id", this.playerId);
      }

      compound.m_128365_("breaker_item", this.breakerItem.serializeNBT());
      compound.m_128344_("arrow_type", (byte)this.type.ordinal());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.blocksBroken = compound.m_128451_("blocks_broken");
      if (compound.m_128441_("player_id")) {
         this.playerId = compound.m_128342_("player_id");
      }

      this.breakerItem = ItemStack.m_41712_(compound.m_128469_("breaker_item"));
      this.type = MiningArrowEntity.Type.values()[compound.m_128445_("arrow_type")];
   }

   public void writeSpawnData(FriendlyByteBuf buf) {
      buf.writeByte(this.type.ordinal());
   }

   public void readSpawnData(FriendlyByteBuf buf) {
      this.type = MiningArrowEntity.Type.values()[buf.readByte()];
   }

   protected void breakBlock(BlockPos pos) {
      if (!this.f_19853_.f_46443_ && !this.f_19853_.m_8055_(pos).m_60795_()) {
         if (BlockUtil.breakExtraBlock((ServerLevel)this.f_19853_, pos, this.breakerItem, this.playerId)) {
            if (++this.blocksBroken >= 12) {
               this.m_146870_();
            }
         } else {
            this.m_5496_(SoundEvents.f_11669_, 1.0F, 1.5F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
            this.m_146870_();
         }
      }
   }

   public static enum Type {
      IRON(new ResourceLocation("apotheosis", "textures/entity/iron_mining_arrow.png")),
      DIAMOND(new ResourceLocation("apotheosis", "textures/entity/diamond_mining_arrow.png"));

      private final ResourceLocation texture;

      private Type(ResourceLocation texture) {
         this.texture = texture;
      }

      public ResourceLocation getTexture() {
         return this.texture;
      }
   }
}
