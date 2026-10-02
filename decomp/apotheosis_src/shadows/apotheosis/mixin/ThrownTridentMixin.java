package shadows.apotheosis.mixin;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.EnchModuleEvents;

@Mixin({ThrownTrident.class})
public abstract class ThrownTridentMixin extends AbstractArrow implements EnchModuleEvents.TridentGetter {
   int pierces = 0;
   Vec3 oldVel = null;
   @Shadow
   private boolean f_37556_;

   protected ThrownTridentMixin(EntityType<? extends AbstractArrow> type, Level level) {
      super(type, level);
   }

   @Accessor
   @Override
   public abstract ItemStack getTridentItem();

   @Inject(
      method = {"<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V"},
      at = {@At("TAIL")},
      require = 1,
      remap = false
   )
   private void init(CallbackInfo ci) {
      this.m_36767_((byte)this.getTridentItem().getEnchantmentLevel(Enchantments.f_44961_));
   }

   @Inject(
      method = {"onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   public void startHitEntity(EntityHitResult res, CallbackInfo ci) {
      if (this.m_36796_() > 0) {
         if (this.f_36701_ == null) {
            this.f_36701_ = new IntOpenHashSet(this.m_36796_());
         }

         if (this.f_36701_.contains(res.m_82443_().m_19879_())) {
            ci.cancel();
         }
      }

      this.oldVel = this.m_20184_();
   }

   @Inject(
      method = {"onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V"},
      at = {@At("TAIL")},
      cancellable = true,
      require = 1
   )
   public void endHitEntity(EntityHitResult res, CallbackInfo ci) {
      if (this.m_36796_() > 0) {
         this.f_36701_.add(res.m_82443_().m_19879_());
         if (this.f_36701_.size() <= this.m_36796_()) {
            this.f_37556_ = false;
            this.m_20256_(this.oldVel);
         }
      }
   }
}
