package lykrast.meetyourfight.entity;

import javax.annotation.Nullable;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraftforge.network.NetworkHooks;

public class SwampMineEntity extends Entity {
   private LivingEntity bomber;
   public int fuse = 200;

   public SwampMineEntity(EntityType<? extends SwampMineEntity> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
      this.f_19850_ = true;
   }

   public SwampMineEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
      this((EntityType<? extends SwampMineEntity>)ModEntities.SWAMP_MINE.get(), worldIn);
      this.m_6034_(x, y, z);
      double angle = worldIn.f_46441_.m_188500_() * Math.PI * 2.0;
      this.m_20334_(-Math.sin(angle) * 0.06, 0.05, -Math.cos(angle) * 0.06);
      this.fuse = 200;
      this.f_19854_ = x;
      this.f_19855_ = y;
      this.f_19856_ = z;
      this.bomber = igniter;
   }

   public boolean m_6087_() {
      return !this.m_213877_();
   }

   public void m_8119_() {
      if (!this.m_20068_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      this.m_20256_(this.m_20184_().m_82490_(0.98));
      if (this.f_19861_) {
         this.fuse = 0;
      } else {
         this.fuse--;
      }

      if (this.fuse <= 0) {
         this.m_142687_(RemovalReason.KILLED);
         if (!this.f_19853_.f_46443_) {
            this.explode();
         }
      } else {
         this.m_20073_();
         if (this.f_19853_.f_46443_) {
            this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 0.0, 0.0, 0.0);
         }
      }
   }

   protected void explode() {
      this.f_19853_
         .m_46511_((Entity)(this.bomber != null ? this.bomber : this), this.m_20185_(), this.m_20227_(0.0625), this.m_20189_(), 3.0F, BlockInteraction.NONE);
   }

   protected void m_8097_() {
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128376_("Fuse", (short)this.fuse);
   }

   protected void m_7378_(CompoundTag compound) {
      this.fuse = compound.m_128448_("Fuse");
   }

   protected float m_6380_(Pose poseIn, EntityDimensions sizeIn) {
      return 0.15F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
