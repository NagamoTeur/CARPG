package com.rolfmao.upgradednetherite.mixin;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({MobEffectUtil.class})
public class MixinMobEffectUtil {
   @ModifyVariable(
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
      ),
      method = {"addEffectToPlayersAround"}
   )
   private static List cleanList(
      List<ServerPlayer> list, ServerLevel p_216947_, @Nullable Entity p_216948_, Vec3 p_216949_, double p_216950_, MobEffectInstance p_216951_, int p_216952_
   ) {
      if (p_216948_ instanceof ElderGuardian && p_216951_.m_19544_() == MobEffects.f_19599_ && UpgradedNetheriteConfig.EnableElderGuardianDebuffImmune) {
         list.removeIf(WaterUtil::isWearingWaterArmor);
      }

      return list;
   }
}
