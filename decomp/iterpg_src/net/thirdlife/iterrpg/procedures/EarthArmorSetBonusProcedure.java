package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class EarthArmorSetBonusProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double chance = 0.0;
         EarthSetRepairProcedure.execute(world, x, y, z, itemstack);
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .SetBonusToggle
            && entity.m_6144_()
            && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ElementalArmorCooldown
               == 0.0
            && (entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_HELMET.get()) {
            if (world.m_8055_(
                     new BlockPos(entity.m_20185_() + entity.m_20154_().f_82479_ * 0.8, entity.m_20186_(), entity.m_20189_() + entity.m_20154_().f_82481_ * 0.8)
                  )
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))
               && world.m_8055_(
                     new BlockPos(
                        entity.m_20185_() + entity.m_20154_().f_82479_ * 0.8, entity.m_20186_() + 1.0, entity.m_20189_() + entity.m_20154_().f_82481_ * 0.8
                     )
                  )
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))) {
               entity.m_6021_(entity.m_20185_() + entity.m_20154_().f_82479_ * 0.001, entity.m_20186_(), entity.m_20189_() + entity.m_20154_().f_82481_ * 0.001);
               if (entity instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.f_8906_
                     .m_9774_(
                        entity.m_20185_() + entity.m_20154_().f_82479_ * 0.001,
                        entity.m_20186_(),
                        entity.m_20189_() + entity.m_20154_().f_82481_ * 0.001,
                        entity.m_146908_(),
                        entity.m_146909_()
                     );
               }

               double _setval = 16.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
            } else if (world.m_8055_(new BlockPos(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_()))
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))
               && entity.m_20154_().f_82480_ < -0.92) {
               double _setval = 20.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
               entity.m_6021_(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_());
               if (entity instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.f_8906_.m_9774_(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
               }
            }
         }

         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .SetBonusToggle
            && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ElementalArmorPassiveCooldown
               <= 0.0
            && (entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.EARTH_ARMOR_HELMET.get()) {
            double _setval = 320.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ElementalArmorPassiveCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 50, 0, true, false));
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof LivingEntity
                  && (entityiterator instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != entity
                  && !(entityiterator instanceof Monster)
                  && entityiterator instanceof LivingEntity) {
                  LivingEntity _entity = (LivingEntity)entityiterator;
                  if (!_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 50, 0, true, false));
                  }
               }
            }
         }
      }
   }
}
