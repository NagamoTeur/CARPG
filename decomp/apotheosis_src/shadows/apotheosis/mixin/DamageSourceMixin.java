package shadows.apotheosis.mixin;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.util.DamageSourceUtil;

@Mixin({DamageSource.class})
public class DamageSourceMixin implements DamageSourceUtil.DmgSrcCopy {
   @Shadow
   private boolean f_146704_;
   @Shadow
   private boolean f_19327_;
   @Shadow
   private boolean f_19328_;
   @Shadow
   private boolean f_19329_;
   @Shadow
   private float f_19330_ = 0.1F;
   @Shadow
   private boolean f_19300_;
   @Shadow
   private boolean f_19301_;
   @Shadow
   private boolean f_19302_;
   @Shadow
   private boolean f_19303_;
   @Shadow
   private boolean f_19304_;
   @Shadow
   private boolean f_146700_;
   @Shadow
   private boolean f_181119_;

   @Override
   public void copyFrom(DamageSource other) {
      DamageSourceMixin mix = (DamageSourceMixin)other;
      this.f_146704_ = mix.f_146704_;
      this.f_19327_ = mix.f_19327_;
      this.f_19328_ = mix.f_19328_;
      this.f_19329_ = mix.f_19329_;
      this.f_19330_ = mix.f_19330_;
      this.f_19300_ = mix.f_19300_;
      this.f_19301_ = mix.f_19301_;
      this.f_19302_ = mix.f_19302_;
      this.f_19303_ = mix.f_19303_;
      this.f_19304_ = mix.f_19304_;
      this.f_146700_ = mix.f_146700_;
      this.f_181119_ = mix.f_181119_;
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"explosion(Lnet/minecraft/world/level/Explosion;)Lnet/minecraft/world/damagesource/DamageSource;"},
      cancellable = true
   )
   private static void apoth_fixMC_92017(@Nullable Explosion src, CallbackInfoReturnable<DamageSource> cir) {
      if (src != null) {
         Entity exploder = src.getExploder();
         if (exploder instanceof PrimedTnt tnt) {
            cir.setReturnValue(new IndirectEntityDamageSource("explosion.player", tnt, tnt.m_32099_()).m_19386_().m_19375_());
         } else if (exploder instanceof Projectile proj) {
            cir.setReturnValue(new IndirectEntityDamageSource("explosion.player", proj, proj.m_37282_()).m_19386_().m_19375_());
         }
      }
   }
}
