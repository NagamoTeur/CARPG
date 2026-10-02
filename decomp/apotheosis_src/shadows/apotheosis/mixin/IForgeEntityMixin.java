package shadows.apotheosis.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({IForgeEntity.class})
public interface IForgeEntityMixin {
   @Overwrite(
      remap = false
   )
   default float getStepHeight() {
      float legacyStep = ((Entity)this).f_19793_;
      if (this instanceof Player player) {
         return (float)player.m_21133_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get());
      } else {
         if (this instanceof LivingEntity living) {
            AttributeInstance stepHeightAttribute = living.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get());
            if (stepHeightAttribute != null) {
               return (float)Math.max(0.0, (double)legacyStep + stepHeightAttribute.m_22135_());
            }
         }

         return legacyStep;
      }
   }
}
