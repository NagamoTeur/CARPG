package net.thirdlife.iterrpg.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.procedures.WeeperTearHitBlockProcedure;
import net.thirdlife.iterrpg.procedures.WeeperTearHitProcedure;

@OnlyIn(
   value = Dist.CLIENT,
   _interface = ItemSupplier.class
)
public class WeeperTearEntity extends AbstractArrow implements ItemSupplier {
   public WeeperTearEntity(SpawnEntity packet, Level world) {
      super((EntityType)IterRpgModEntities.WEEPER_TEAR.get(), world);
   }

   public WeeperTearEntity(EntityType<? extends WeeperTearEntity> type, Level world) {
      super(type, world);
   }

   public WeeperTearEntity(EntityType<? extends WeeperTearEntity> type, double x, double y, double z, Level world) {
      super(type, x, y, z, world);
   }

   public WeeperTearEntity(EntityType<? extends WeeperTearEntity> type, LivingEntity entity, Level world) {
      super(type, entity, world);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack m_7846_() {
      return new ItemStack((ItemLike)IterRpgModItems.WEEPER_TEAR.get());
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   protected void m_7761_(LivingEntity entity) {
      super.m_7761_(entity);
      entity.m_21317_(entity.m_21234_() - 1);
   }

   public void m_5790_(EntityHitResult entityHitResult) {
      super.m_5790_(entityHitResult);
      WeeperTearHitProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public void m_8060_(BlockHitResult blockHitResult) {
      super.m_8060_(blockHitResult);
      WeeperTearHitBlockProcedure.execute(
         this.f_19853_,
         (double)blockHitResult.m_82425_().m_123341_(),
         (double)blockHitResult.m_82425_().m_123342_(),
         (double)blockHitResult.m_82425_().m_123343_(),
         this.m_37282_()
      );
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_36703_) {
         this.m_146870_();
      }
   }

   public static WeeperTearEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
      WeeperTearEntity entityarrow = new WeeperTearEntity((EntityType<? extends WeeperTearEntity>)IterRpgModEntities.WEEPER_TEAR.get(), entity, world);
      entityarrow.m_6686_(entity.m_20252_(1.0F).f_82479_, entity.m_20252_(1.0F).f_82480_, entity.m_20252_(1.0F).f_82481_, power * 2.0F, 0.0F);
      entityarrow.m_20225_(true);
      entityarrow.m_36762_(false);
      entityarrow.m_36781_(damage);
      entityarrow.m_36735_(knockback);
      world.m_7967_(entityarrow);
      world.m_6263_(
         null,
         entity.m_20185_(),
         entity.m_20186_(),
         entity.m_20189_(),
         (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
         SoundSource.PLAYERS,
         1.0F,
         1.0F / (random.m_188501_() * 0.5F + 1.0F) + power / 2.0F
      );
      return entityarrow;
   }

   public static WeeperTearEntity shoot(LivingEntity entity, LivingEntity target) {
      WeeperTearEntity entityarrow = new WeeperTearEntity((EntityType<? extends WeeperTearEntity>)IterRpgModEntities.WEEPER_TEAR.get(), entity, entity.f_19853_);
      double dx = target.m_20185_() - entity.m_20185_();
      double dy = target.m_20186_() + (double)target.m_20192_() - 1.1;
      double dz = target.m_20189_() - entity.m_20189_();
      entityarrow.m_6686_(dx, dy - entityarrow.m_20186_() + Math.hypot(dx, dz) * 0.2F, dz, 1.5F, 12.0F);
      entityarrow.m_20225_(true);
      entityarrow.m_36781_(2.0);
      entityarrow.m_36735_(0);
      entityarrow.m_36762_(false);
      entity.f_19853_.m_7967_(entityarrow);
      entity.f_19853_
         .m_6263_(
            null,
            entity.m_20185_(),
            entity.m_20186_(),
            entity.m_20189_(),
            (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (RandomSource.m_216327_().m_188501_() * 0.5F + 1.0F)
         );
      return entityarrow;
   }
}
