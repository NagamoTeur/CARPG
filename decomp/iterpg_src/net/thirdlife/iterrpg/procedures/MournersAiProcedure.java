package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModMobEffects;

public class MournersAiProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity) {
            _entity.m_21195_((MobEffect)IterRpgModMobEffects.CURSED.get());
         }

         if (Mth.m_216271_(RandomSource.m_216327_(), 1, 32) == 16) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) instanceof LivingEntity) {
               entity.getPersistentData().m_128347_("aggr", 32.0);
            }

            if (entity.getPersistentData().m_128459_("aggr") > 0.0) {
               entity.getPersistentData().m_128347_("aggr", entity.getPersistentData().m_128459_("aggr") - 1.0);
            }

            if (entity.getPersistentData().m_128459_("aggr") == 0.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(32.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entityiterator;
                     if (_livEnt.m_21023_((MobEffect)IterRpgModMobEffects.CURSED.get())
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("minecraft:skeletons")))
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:demons")))
                        && !(new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiterator)
                        && !(new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.SPECTATOR;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SPECTATOR
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiterator)
                        && entity instanceof Mob _entity) {
                        _entity.m_21573_()
                           .m_26519_(
                              entityiterator.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                              entityiterator.m_20186_() + Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0),
                              entityiterator.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                              1.0
                           );
                     }
                  }
               }

               _center = new Vec3(x, y, z);

               for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorx instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entityiteratorx;
                     if (_livEnt.m_21023_((MobEffect)IterRpgModMobEffects.CURSED.get())
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("minecraft:skeletons")))
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                        && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:demons")))
                        && !(new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiteratorx)
                        && !(new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.SPECTATOR;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SPECTATOR
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiteratorx)
                        && entity instanceof Mob) {
                        Mob _entity = (Mob)entity;
                        if (entityiteratorx instanceof LivingEntity _ent) {
                           _entity.m_6710_(_ent);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
