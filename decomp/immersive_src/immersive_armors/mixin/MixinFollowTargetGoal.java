package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NearestAttackableTargetGoal.class})
public abstract class MixinFollowTargetGoal extends TargetGoal {
   public MixinFollowTargetGoal(Mob mob, boolean checkVisibility) {
      super(mob, checkVisibility);
   }

   @Inject(
      method = {"start()V"},
      at = {@At("TAIL")}
   )
   private void immersiveArmors$injectStart(CallbackInfo ci) {
      if (this.f_26135_ instanceof AbstractSkeleton && this.f_26135_.m_5448_() instanceof Player player) {
         int pieces = 0;

         for (ItemStack item : player.m_6168_()) {
            Item var7 = item.m_41720_();
            if (var7 instanceof ExtendedArmorItem) {
               ExtendedArmorItem armor = (ExtendedArmorItem)var7;
               if (armor.getMaterial().isAntiSkeleton()) {
                  pieces++;
               }
            }
         }

         if (pieces >= 4) {
            this.f_26135_.m_6710_(null);
         }
      }
   }
}
