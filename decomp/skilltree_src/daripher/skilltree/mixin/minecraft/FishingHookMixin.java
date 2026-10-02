package daripher.skilltree.mixin.minecraft;

import daripher.skilltree.skill.bonus.SkillBonusHandler;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({FishingHook.class})
public abstract class FishingHookMixin {
   @Redirect(
      method = {"retrieve"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
      )
   )
   private boolean multiplyFishingLoot(Level level, Entity entity) {
      if (!(entity instanceof ItemEntity item)) {
         return level.m_7967_(entity);
      } else {
         Player player = this.m_37168_();

         float multiplier;
         for (multiplier = SkillBonusHandler.getLootMultiplier(player, LootDuplicationBonus.LootType.FISHING); multiplier > 1.0F; multiplier--) {
            ItemEntity copy = item.m_32066_();
            copy.m_20256_(item.m_20184_());
            level.m_7967_(copy);
         }

         if (player.m_217043_().m_188501_() < multiplier) {
            ItemEntity copy = item.m_32066_();
            copy.m_20256_(item.m_20184_());
            level.m_7967_(copy);
         }

         return level.m_7967_(entity);
      }
   }

   @Shadow
   public abstract Player m_37168_();
}
