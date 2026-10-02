package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin extends Entity implements LivingMixinSupport {
   private static final String PHANTOM_TAG_BY = "cs.phantomTagByID";
   private static final String QUASAR_IMBUED_BY = "cs.quasarImbuedByID";
   private int tagTimer;
   private int quasarTimer;

   private LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   public void celestisynth$tick(CallbackInfo ci) {
      if (this.tagTimer > 0) {
         this.tagTimer--;
      } else {
         this.setPhantomTagger(null);
      }

      if (this.quasarTimer > 0) {
         this.quasarTimer--;
      } else {
         this.setQuasarImbued(null);
      }
   }

   @Nullable
   @Override
   public Player getPhantomTagger() {
      return this.f_19853_.m_6815_(this.getPhantomTagFrom()) instanceof Player ? (Player)this.f_19853_.m_6815_(this.getPhantomTagFrom()) : null;
   }

   @Override
   public void setPhantomTagger(@Nullable Player tagger) {
      if (tagger != null) {
         this.setPhantomTagFrom(tagger.m_19879_());
      } else {
         this.setPhantomTagFrom(0);
      }
   }

   private int getPhantomTagFrom() {
      return this.getPersistentData().m_128451_("cs.phantomTagByID");
   }

   private void setPhantomTagFrom(int phantomTagFrom) {
      this.getPersistentData().m_128405_("cs.phantomTagByID", phantomTagFrom);
      this.tagTimer = 100;
   }

   @Nullable
   @Override
   public Player getQuasarImbued() {
      return this.f_19853_.m_6815_(this.getQuasarImbuedFrom()) instanceof Player ? (Player)this.f_19853_.m_6815_(this.getQuasarImbuedFrom()) : null;
   }

   @Override
   public void setQuasarImbued(@Nullable Player imbuedTo) {
      if (imbuedTo != null) {
         this.setQuasarImbuedFrom(imbuedTo.m_19879_());
      } else {
         this.setQuasarImbuedFrom(0);
      }
   }

   private int getQuasarImbuedFrom() {
      return this.getPersistentData().m_128451_("cs.quasarImbuedByID");
   }

   private void setQuasarImbuedFrom(int imbuedFrom) {
      this.getPersistentData().m_128405_("cs.quasarImbuedByID", imbuedFrom);
      this.quasarTimer = 100;
   }
}
