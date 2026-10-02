package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class OceanArmorSetBonusProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double chance = 0.0;
         WaterSetRepairProcedure.execute(world, x, y, z, entity, itemstack);
         if (entity instanceof Player
            && (entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.WATER_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.WATER_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.WATER_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.WATER_ARMOR_HELMET.get()) {
            if (entity.m_6144_()
               && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ElementalArmorCooldown
                  <= 1.0
               && entity.m_20146_() < 290) {
               entity.m_20301_(entity.m_20146_() + 10);
               double _setval = 8.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123795_, x, y + 1.0, z, 8, 0.5, 1.0, 0.5, 0.025);
               }
            }

            if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() instanceof LiquidBlock && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()
               )
             {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19593_, 8, 0, false, true));
            }
         }
      }
   }
}
