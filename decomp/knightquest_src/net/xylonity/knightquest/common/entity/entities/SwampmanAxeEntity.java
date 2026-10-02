package net.xylonity.knightquest.common.entity.entities;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.registry.KnightQuestEntities;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SwampmanAxeEntity extends AbstractSwampmanAxeEntity implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.m_135353_(SwampmanAxeEntity.class, EntityDataSerializers.f_135028_);
   private Potion potion = Potions.f_43598_;
   private final Set<MobEffectInstance> effects = Sets.newHashSet();
   private boolean fixedColor;

   public void registerControllers(AnimationData animationData) {
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public SwampmanAxeEntity(EntityType<? extends AbstractSwampmanAxeEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public SwampmanAxeEntity(Level pLevel, LivingEntity pShooter) {
      super((EntityType<? extends AbstractSwampmanAxeEntity>)KnightQuestEntities.SWAMPMAN_AXE.get(), pShooter, pLevel);
   }

   private void updateColor() {
      this.fixedColor = false;
      if (this.potion == Potions.f_43598_ && this.effects.isEmpty()) {
         this.f_19804_.m_135381_(ID_EFFECT_COLOR, -1);
      } else {
         this.f_19804_.m_135381_(ID_EFFECT_COLOR, PotionUtils.m_43564_(PotionUtils.m_43561_(this.potion, this.effects)));
      }
   }

   public void addEffect(MobEffectInstance pEffectInstance) {
      this.effects.add(pEffectInstance);
      this.m_20088_().m_135381_(ID_EFFECT_COLOR, PotionUtils.m_43564_(PotionUtils.m_43561_(this.potion, this.effects)));
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ID_EFFECT_COLOR, -1);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.inGround) {
            if (this.inGroundTime % 5 == 0) {
               this.makeParticle(1);
            }
         } else {
            this.makeParticle(2);
         }
      } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
         this.f_19853_.m_7605_(this, (byte)0);
         this.potion = Potions.f_43598_;
         this.effects.clear();
         this.f_19804_.m_135381_(ID_EFFECT_COLOR, -1);
      }
   }

   private void makeParticle(int pParticleAmount) {
      int i = this.getColor();
      if (i != -1 && pParticleAmount > 0) {
         double d0 = (double)(i >> 16 & 0xFF) / 255.0;
         double d1 = (double)(i >> 8 & 0xFF) / 255.0;
         double d2 = (double)(i & 0xFF) / 255.0;

         for (int j = 0; j < pParticleAmount; j++) {
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), d0, d1, d2);
         }
      }
   }

   public int getColor() {
      return (Integer)this.f_19804_.m_135370_(ID_EFFECT_COLOR);
   }

   private void setFixedColor(int pFixedColor) {
      this.fixedColor = true;
      this.f_19804_.m_135381_(ID_EFFECT_COLOR, pFixedColor);
   }

   @Override
   public void m_7380_(@NotNull CompoundTag pCompound) {
      super.m_7380_(pCompound);
      if (this.fixedColor) {
         pCompound.m_128405_("Color", this.getColor());
      }

      if (!this.effects.isEmpty()) {
         ListTag listtag = new ListTag();

         for (MobEffectInstance mobeffectinstance : this.effects) {
            listtag.add(mobeffectinstance.m_19555_(new CompoundTag()));
         }

         pCompound.m_128365_("CustomPotionEffects", listtag);
      }
   }

   @Override
   public void m_7378_(@NotNull CompoundTag pCompound) {
      super.m_7378_(pCompound);
      if (pCompound.m_128425_("Potion", 8)) {
         this.potion = PotionUtils.m_43577_(pCompound);
      }

      for (MobEffectInstance mobeffectinstance : PotionUtils.m_43573_(pCompound)) {
         this.addEffect(mobeffectinstance);
      }

      if (pCompound.m_128425_("Color", 99)) {
         this.setFixedColor(pCompound.m_128451_("Color"));
      } else {
         this.updateColor();
      }
   }

   @Override
   protected void doPostHurtEffects(LivingEntity pLiving) {
      super.doPostHurtEffects(pLiving);
      Entity entity = this.m_150173_();
      if (!this.effects.isEmpty()) {
         for (MobEffectInstance mobeffectinstance : this.effects) {
            pLiving.m_147207_(mobeffectinstance, entity);
         }
      }
   }

   public void m_7822_(byte pId) {
      if (pId == 0) {
         int i = this.getColor();
         if (i != -1) {
            double d0 = (double)(i >> 16 & 0xFF) / 255.0;
            double d1 = (double)(i >> 8 & 0xFF) / 255.0;
            double d2 = (double)(i & 0xFF) / 255.0;

            for (int j = 0; j < 20; j++) {
               this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), d0, d1, d2);
            }
         }
      } else {
         super.m_7822_(pId);
      }
   }
}
