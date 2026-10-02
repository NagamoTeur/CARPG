package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.CiscoModMod;
import net.cisco.entity.DescendedCiscoEntity;
import net.cisco.entity.VengefulAfterImageEntity;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class VengefulAfterImageOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof VengefulAfterImageEntity) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null) {
               entity.getPersistentData().m_128347_("VAA", entity.getPersistentData().m_128459_("VAA") + 2.0);
            }

            if (!world.m_6443_(DescendedCiscoEntity.class, AABB.m_165882_(new Vec3(x, y, z), 60.0, 60.0, 60.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("VAA") == 20.0) {
               if (entity instanceof DescendedCiscoEntity) {
                  ((DescendedCiscoEntity)entity).setAnimation("animation.spellcast.new");
               }

               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:descendedactive")),
                        SoundSource.MASTER,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:descendedactive")),
                        SoundSource.MASTER,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.m_20331_(true);
               CiscoModMod.queueServerWork(80, () -> entity.m_20331_(false));
            }

            if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 60.0, 60.0, 60.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("VAA") == 40.0) {
               if (entity instanceof VengefulAfterImageEntity) {
                  ((VengefulAfterImageEntity)entity).setAnimation("animation.spellcast.new");
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiterator instanceof Player
                     && (double)(entityiterator instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                        > (double)(entityiterator instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9) {
                     if (world instanceof ServerLevel _levelx) {
                        LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_levelx);
                        entityToSpawn.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                        entityToSpawn.m_20874_(true);
                        _levelx.m_7967_(entityToSpawn);
                     }

                     entityiterator.m_6469_(DamageSource.f_19318_, (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 5.0F);
                  }
               }
            }

            if (!world.m_6443_(VengefulAfterImageEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("VAA") == 60.0) {
               if (entity instanceof VengefulAfterImageEntity) {
                  ((VengefulAfterImageEntity)entity).setAnimation("animation.spellcast.new");
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 120, 3));
               }
            }

            if (entity.getPersistentData().m_128459_("VAA") == 80.0) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 140, 3));
               }

               if (world instanceof Level _levelx) {
                  if (!_levelx.m_5776_()) {
                     _levelx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:descendedactive")),
                        SoundSource.MASTER,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _levelx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:descendedactive")),
                        SoundSource.MASTER,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity.getPersistentData().m_128459_("VAA") == 90.0 && world instanceof Level _levelxx) {
                  if (!_levelxx.m_5776_()) {
                     _levelxx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.MASTER,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _levelxx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.MASTER,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("VAA") == 100.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorx instanceof Player
                     && (double)(entityiteratorx instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                        > (double)(entityiteratorx instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9) {
                     if (world instanceof ServerLevel _levelxxx) {
                        _levelxxx.m_7654_()
                           .m_129892_()
                           .m_230957_(
                              new CommandSourceStack(
                                    CommandSource.f_80164_,
                                    new Vec3(entityiteratorx.m_20185_(), entityiteratorx.m_20186_(), entityiteratorx.m_20189_()),
                                    Vec2.f_82462_,
                                    _levelxxx,
                                    4,
                                    "",
                                    Component.m_237113_(""),
                                    _levelxxx.m_7654_(),
                                    null
                                 )
                                 .m_81324_(),
                              "summon irons_spellbooks:dragon_breath_pool ~ ~ ~ {}"
                           );
                     }

                     CiscoModMod.queueServerWork(
                        40,
                        () -> entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           )
                     );
                     CiscoModMod.queueServerWork(
                        40,
                        () -> entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           )
                     );
                     CiscoModMod.queueServerWork(
                        40,
                        () -> {
                           if (world instanceof ServerLevel _levelxxx) {
                              _levelxxx.m_7654_()
                                 .m_129892_()
                                 .m_230957_(
                                    new CommandSourceStack(
                                          CommandSource.f_80164_,
                                          new Vec3(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_()),
                                          Vec2.f_82462_,
                                          _levelxxx,
                                          4,
                                          "",
                                          Component.m_237113_(""),
                                          _levelxxx.m_7654_(),
                                          null
                                       )
                                       .m_81324_(),
                                    "summon irons_spellbooks:dragon_breath_pool ~ ~ ~ {}"
                                 );
                           }

                           entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           );
                        }
                     );
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("VAA") == 160.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxx instanceof Player
                     && (double)(entityiteratorxx instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                        > (double)(entityiteratorxx instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9) {
                     if (world instanceof ServerLevel _levelxxx) {
                        _levelxxx.m_7654_()
                           .m_129892_()
                           .m_230957_(
                              new CommandSourceStack(
                                    CommandSource.f_80164_,
                                    new Vec3(entityiteratorxx.m_20185_(), entityiteratorxx.m_20186_(), entityiteratorxx.m_20189_()),
                                    Vec2.f_82462_,
                                    _levelxxx,
                                    4,
                                    "",
                                    Component.m_237113_(""),
                                    _levelxxx.m_7654_(),
                                    null
                                 )
                                 .m_81324_(),
                              "summon irons_spellbooks:devour_jaw ~ ~ ~ {}"
                           );
                     }

                     CiscoModMod.queueServerWork(
                        60,
                        () -> {
                           entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           );
                           if (world instanceof ServerLevel _levelxxx) {
                              _levelxxx.m_7654_()
                                 .m_129892_()
                                 .m_230957_(
                                    new CommandSourceStack(
                                          CommandSource.f_80164_,
                                          new Vec3(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_()),
                                          Vec2.f_82462_,
                                          _levelxxx,
                                          4,
                                          "",
                                          Component.m_237113_(""),
                                          _levelxxx.m_7654_(),
                                          null
                                       )
                                       .m_81324_(),
                                    "summon irons_spellbooks:devour_jaw ~ ~ ~ {}"
                                 );
                           }
                        }
                     );
                     CiscoModMod.queueServerWork(
                        60,
                        () -> {
                           if (world instanceof ServerLevel _levelxxx) {
                              _levelxxx.m_7654_()
                                 .m_129892_()
                                 .m_230957_(
                                    new CommandSourceStack(
                                          CommandSource.f_80164_,
                                          new Vec3(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_()),
                                          Vec2.f_82462_,
                                          _levelxxx,
                                          4,
                                          "",
                                          Component.m_237113_(""),
                                          _levelxxx.m_7654_(),
                                          null
                                       )
                                       .m_81324_(),
                                    "summon irons_spellbooks:devour_jaw ~ ~ ~ {}"
                                 );
                           }

                           entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           );
                        }
                     );
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("VAA") == 220.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxxx instanceof Player
                     && (double)(entityiteratorxxx instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                        > (double)(entityiteratorxxx instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9) {
                     CiscoModMod.queueServerWork(
                        40,
                        () -> {
                           if (world instanceof ServerLevel _levelxxx) {
                              _levelxxx.m_7654_()
                                 .m_129892_()
                                 .m_230957_(
                                    new CommandSourceStack(
                                          CommandSource.f_80164_,
                                          new Vec3(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_()),
                                          Vec2.f_82462_,
                                          _levelxxx,
                                          4,
                                          "",
                                          Component.m_237113_(""),
                                          _levelxxx.m_7654_(),
                                          null
                                       )
                                       .m_81324_(),
                                    "summon irons_spellbooks:earthquake_aoe ~ ~ ~ {}"
                                 );
                           }

                           entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           );
                        }
                     );
                     CiscoModMod.queueServerWork(
                        40,
                        () -> {
                           entityiterator.m_6469_(
                              DamageSource.f_19318_,
                              (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                                 - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 15.0F
                           );
                           if (world instanceof ServerLevel _levelxxx) {
                              _levelxxx.m_7654_()
                                 .m_129892_()
                                 .m_230957_(
                                    new CommandSourceStack(
                                          CommandSource.f_80164_,
                                          new Vec3(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_()),
                                          Vec2.f_82462_,
                                          _levelxxx,
                                          4,
                                          "",
                                          Component.m_237113_(""),
                                          _levelxxx.m_7654_(),
                                          null
                                       )
                                       .m_81324_(),
                                    "summon irons_spellbooks:earthquake_aoe ~ ~ ~ {}"
                                 );
                           }
                        }
                     );
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("VAA") == 600.0) {
               entity.getPersistentData().m_128347_("VAA", 0.0);
            }
         }

         if (!CiscoModModVariables.MapVariables.get(world).DescendedCiscoLives && entity instanceof VengefulAfterImageEntity) {
            CiscoModMod.queueServerWork(
               40,
               () -> {
                  if (world instanceof ServerLevel _levelxxx) {
                     _levelxxx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelxxx, 4, "", Component.m_237113_(""), _levelxxx.m_7654_(), null
                              )
                              .m_81324_(),
                           "stopsound @a master cisco_mod:chaseboss"
                        );
                  }

                  if (!entity.f_19853_.m_5776_()) {
                     entity.m_146870_();
                  }
               }
            );
         }
      }
   }
}
