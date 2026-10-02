package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class EndArmorSetBonusProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         boolean attack = false;
         boolean hit = false;
         boolean particle = false;
         double chance = 0.0;
         double generator_distance = 0.0;
         double previousRecipe = 0.0;
         double offset = 0.0;
         double dist = 0.0;
         double zdir = 0.0;
         double ydir = 0.0;
         double xdir = 0.0;
         double damage = 0.0;
         double distance = 0.0;
         double amount = 0.0;
         VoidSetRepairProcedure.execute(entity, itemstack);
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.VOID_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.VOID_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.VOID_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == IterRpgModItems.VOID_ARMOR_HELMET.get()) {
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .SetBonusToggle) {
               if (entity.m_6144_()) {
                  offset = 1.85;
               } else {
                  offset = 2.15;
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(), x, y + offset, z, 1, 0.0, 0.0, 0.0, 0.0);
               }

               if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ElementalArmorCooldown
                  <= 1.0) {
                  amount = 0.0;
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (amount < 3.0
                        && entityiterator instanceof Monster
                        && (!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entity != entityiterator) {
                        amount++;
                        double _setval = 65.0;
                        entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                           capability.ElementalArmorCooldown = _setval;
                           capability.syncPlayerVariables(entity);
                        });
                        xdir = entityiterator.m_20185_() - entity.m_20185_();
                        ydir = entityiterator.m_20186_() + (double)(entityiterator.m_20206_() / 2.0F) - (entity.m_20186_() + offset);
                        zdir = entityiterator.m_20189_() - entity.m_20189_();
                        attack = true;
                        dist = 0.0;

                        for (int index0 = 0; index0 < 20; index0++) {
                           if (world.m_8055_(
                                 new BlockPos(entity.m_20185_() + dist * xdir, entity.m_20186_() + offset + ydir * dist, entity.m_20189_() + dist * zdir)
                              )
                              .m_60815_()) {
                              attack = false;
                           }

                           dist += 0.05;
                        }

                        if (attack) {
                           dist = 0.0;

                           for (int index1 = 0; index1 < 20; index1++) {
                              if (world instanceof ServerLevel _level) {
                                 _level.m_8767_(
                                    ParticleTypes.f_123799_,
                                    entity.m_20185_() + dist * xdir,
                                    entity.m_20186_() + offset + ydir * dist,
                                    entity.m_20189_() + dist * zdir,
                                    1,
                                    0.0,
                                    0.0,
                                    0.0,
                                    0.0
                                 );
                              }

                              dist += 0.05;
                           }

                           entityiterator.m_6469_(new EntityDamageSource("generic.player", entity), (float)(6.0 / amount));

                           for (int index2 = 0; index2 < 16; index2++) {
                              if (world instanceof ServerLevel _level) {
                                 _level.m_8767_(
                                    ParticleTypes.f_123799_,
                                    entity.m_20185_() + dist * xdir,
                                    entity.m_20186_() + offset + ydir * dist,
                                    entity.m_20189_() + dist * zdir,
                                    1,
                                    0.1,
                                    0.1,
                                    0.1,
                                    Mth.m_216263_(RandomSource.m_216327_(), 0.025, 0.1)
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (itemstack.m_41784_().m_128459_("voidcooldown") >= 1.0) {
               itemstack.m_41784_().m_128347_("voidcooldown", itemstack.m_41784_().m_128459_("voidcooldown") - 1.0);
            }

            if (itemstack.m_41784_().m_128459_("voidcooldown") <= 1.0
               && (
                  y <= -65.0 && (world instanceof Level _lvlx ? _lvlx.m_46472_() : Level.f_46428_) == Level.f_46428_
                     || y <= 0.0 && (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) != Level.f_46428_
               )) {
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_VOID.get(),
                     entity.m_20185_(),
                     entity.m_20186_() + (double)(entity.m_20206_() / 2.0F),
                     entity.m_20189_(),
                     16,
                     0.5,
                     1.0,
                     0.5,
                     0.0
                  );
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123760_,
                     entity.m_20185_(),
                     entity.m_20186_() + (double)(entity.m_20206_() / 2.0F),
                     entity.m_20189_(),
                     16,
                     0.5,
                     1.0,
                     0.5,
                     0.0
                  );
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 400, 4, true, true));
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 100, 0, true, true));
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 700, 0, true, true));
               }

               itemstack.m_41784_().m_128347_("voidcooldown", 6000.0);
            }
         }
      }
   }
}
