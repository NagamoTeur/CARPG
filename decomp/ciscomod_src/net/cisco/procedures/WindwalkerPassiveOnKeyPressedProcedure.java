package net.cisco.procedures;

import net.cisco.CiscoModMod;
import net.cisco.init.CiscoModModItems;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class WindwalkerPassiveOnKeyPressedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_8055_(new BlockPos(x, y - 0.5, z)).m_60815_()
            && (entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.TEST_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.TEST_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.TEST_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.TEST_BOOTS.get()
            && !((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .jumpvariable) {
            entity.m_20256_(new Vec3(entity.m_20184_().m_7096_() * 1.0, 1.0, entity.m_20184_().m_7094_() * 1.0));
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 160, 1));
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:jump")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:jump")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                  );
               }
            }

            boolean _setval = true;
            entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.jumpvariable = _setval;
               capability.syncPlayerVariables(entity);
            });
            CiscoModMod.queueServerWork(60, () -> {
               boolean _setvalx = false;
               entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.jumpvariable = _setvalx;
                  capability.syncPlayerVariables(entity);
               });
            });
         }
      }
   }
}
