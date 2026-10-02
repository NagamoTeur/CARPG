package lykrast.meetyourfight.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;

public class SpectresEye extends CurioBaseItem {
   public SpectresEye(Properties properties) {
      super(properties, true);
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (livingEntity.f_19797_ % 60 == 0 && livingEntity instanceof Player) {
         for (LivingEntity e : livingEntity.f_19853_.m_6443_(LivingEntity.class, livingEntity.m_20191_().m_82400_(20.0), ex -> ex instanceof Enemy)) {
            e.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 100));
         }
      }
   }
}
