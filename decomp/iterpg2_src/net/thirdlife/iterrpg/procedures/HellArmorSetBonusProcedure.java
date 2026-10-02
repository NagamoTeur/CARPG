package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class HellArmorSetBonusProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double chance = 0.0;
         double generator_distance = 0.0;
         double previousRecipe = 0.0;
         FireSetRepairProcedure.execute(entity, itemstack);
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_HELMET.get()) {
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ElementalArmorCooldown
                  <= 1.0
               && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .SetBonusToggle
               && entity.m_20096_()
               && entity.m_6144_()
               && entity.m_20142_()) {
               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 12, 2, true, true));
               }

               (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128347_("dash", 12.0);
               double _setval = 64.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }

            if (!entity.m_20077_() && !entity.m_6060_() && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 64, 0, true, true));
            }

            if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) <= 5.0F) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 16, 0, true, true));
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 16, 0, true, true));
               }
            }

            if ((entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128459_("dash")
               > 0.0) {
               if ((entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128459_("dash")
                  == 1.0) {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        && entityiterator != entity) {
                        if (world instanceof Level) {
                           Level _levelx = (Level)world;
                           if (!_levelx.m_5776_()) {
                              _levelx.m_5594_(
                                 null,
                                 new BlockPos(x, y, z),
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                                 SoundSource.PLAYERS,
                                 0.25F,
                                 1.0F
                              );
                           } else {
                              _levelx.m_7785_(
                                 x,
                                 y,
                                 z,
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                                 SoundSource.PLAYERS,
                                 0.25F,
                                 1.0F,
                                 false
                              );
                           }
                        }

                        entityiterator.m_20254_(8);
                        entityiterator.m_6469_(DamageSource.f_19307_, 6.0F);
                        if (world instanceof ServerLevel _levelx) {
                           _levelx.m_8767_(
                              ParticleTypes.f_123813_,
                              entityiterator.m_20185_(),
                              entityiterator.m_20186_() + 1.0,
                              entityiterator.m_20189_(),
                              2,
                              0.16,
                              0.16,
                              0.16,
                              0.0
                           );
                        }

                        if (world instanceof ServerLevel _levelx) {
                           _levelx.m_8767_(
                              ParticleTypes.f_123756_,
                              entityiterator.m_20185_(),
                              entityiterator.m_20186_() + 1.0,
                              entityiterator.m_20189_(),
                              8,
                              0.16,
                              0.16,
                              0.16,
                              0.0
                           );
                        }

                        entityiterator.m_20256_(
                           new Vec3(Mth.m_216263_(RandomSource.m_216327_(), -0.64, 0.64), 0.64, Mth.m_216263_(RandomSource.m_216327_(), -0.64, 0.64))
                        );
                     }
                  }
               }

               (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128347_(
                     "dash",
                     (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                           .m_41784_()
                           .m_128459_("dash")
                        - 1.0
                  );
               entity.m_20256_(new Vec3(entity.m_20154_().f_82479_, -0.32, entity.m_20154_().f_82481_));
               if (world instanceof ServerLevel _levelx) {
                  _levelx.m_8767_(ParticleTypes.f_123744_, x, y, z, 8, 0.16, 0.0, 0.16, 0.0);
               }

               for (int index0 = 0; index0 < 8; index0++) {
                  world.m_7106_(
                     ParticleTypes.f_123744_,
                     entity.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), (double)(entity.m_20205_() * -1.0F), (double)entity.m_20205_()),
                     entity.m_20186_() + Mth.m_216263_(RandomSource.m_216327_(), (double)(entity.m_20206_() * -1.0F), (double)entity.m_20206_()),
                     entity.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), (double)(entity.m_20205_() * -1.0F), (double)entity.m_20205_()),
                     entity.m_20154_().f_82479_ * -0.32,
                     0.0,
                     entity.m_20154_().f_82481_ * -0.32
                  );
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (!entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                     && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                     && entityiteratorx != entity) {
                     entityiteratorx.m_20254_(6);
                     entityiteratorx.m_6469_(DamageSource.f_19307_, 4.0F);
                     entityiteratorx.m_20256_(
                        new Vec3(Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5), 0.32, Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5))
                     );
                  }
               }
            }
         }

         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.FIRE_ARMOR_HELMET.get()) {
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ElementalArmorPassiveCooldown
                  <= 1.0
               && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .SetBonusToggle) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((entityiteratorxx instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) == entity) {
                     entityiteratorxx.m_20254_(1);
                     double _setval = 10.0;
                     entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                        capability.ElementalArmorPassiveCooldown = _setval;
                        capability.syncPlayerVariables(entity);
                     });
                  }
               }
            }

            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ElementalArmorPassiveCooldown
                  > 1.0
               && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .SetBonusToggle) {
               world.m_7106_(
                  ParticleTypes.f_123744_,
                  x + Mth.m_216263_(RandomSource.m_216327_(), (double)entity.m_20205_() / -1.75, (double)entity.m_20205_() / 1.75),
                  y + Mth.m_216263_(RandomSource.m_216327_(), 0.0, (double)entity.m_20206_() / 1.75),
                  z + Mth.m_216263_(RandomSource.m_216327_(), (double)entity.m_20205_() / -1.75, (double)entity.m_20205_() / 1.75),
                  0.0,
                  Mth.m_216263_(RandomSource.m_216327_(), 0.04, 0.1),
                  0.0
               );
            }
         }
      }
   }
}
