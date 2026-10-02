package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ElementalAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
      if (entity != null && sourceentity != null) {
         double attackpower = 0.0;
         double rotation = 0.0;
         double distance = 0.0;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double type = 0.0;
         double damage = 0.0;
         double repeat = 0.0;
         double mobamount = 0.0;
         if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("forge:sword")))) {
            attackpower = 1.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("forge:scythe")))) {
            attackpower = 0.75;
            ScytheSplashDamageProcedure.execute(world, x, y, z, sourceentity);
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("forge:flail")))) {
            attackpower = 2.0;
            FlailPlaysoundProcedure.execute(world, x, y, z);
         }

         repeat = 1.0;
         if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:forest_set")))) {
            type = 1.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:ocean_set")))) {
            type = 2.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:sky_set")))) {
            type = 3.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:hell_set")))) {
            type = 4.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:end_set")))) {
            type = 5.0;
         } else if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:elemental_set")))) {
            repeat = 2.0;
         }

         for (int index0 = 0; index0 < (int)repeat; index0++) {
            if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:elemental_set")))) {
               type = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 5);
            }

            if (type == 1.0) {
               distance = 0.25;

               for (int index1 = 0; (long)index1 < Math.round(8.0 * attackpower); index1++) {
                  xpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                  ypos = distance * Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16);
                  zpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(),
                        entity.m_20185_() + xpos,
                        entity.m_20186_() + ypos + (double)entity.m_20206_() * 0.48,
                        entity.m_20189_() + zpos,
                        5,
                        0.32,
                        0.32,
                        0.32,
                        0.05
                     );
                  }

                  Vec3 _center = new Vec3(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.25 * attackpower / 2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && sourceentity != entityiterator) {
                        if (entityiterator instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                           _entity.m_7292_(new MobEffectInstance(MobEffects.f_19614_, (int)(250.0 * attackpower), 0, false, true));
                        }

                        entityiterator.m_6469_(DamageSource.f_19318_, (float)(1.2 * attackpower));
                     }
                  }

                  distance += 0.32;
               }
            }

            if (type == 2.0) {
               distance = 0.0;
               mobamount = 1.0;
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (int)(250.0 * attackpower), 0, false, true));
               }

               Vec3 _center = new Vec3(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos);

               for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(5.0 * attackpower / 2.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((!(entityiteratorx instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                     && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                     && sourceentity != entityiteratorx
                     && ForgeRegistries.ENTITY_TYPES
                        .getKey(entity.m_6095_())
                        .toString()
                        .equals(ForgeRegistries.ENTITY_TYPES.getKey(entityiteratorx.m_6095_()).toString())
                     && mobamount <= (double)Math.round(5.0 * attackpower)) {
                     mobamount++;
                     if (entityiteratorx instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (int)(250.0 * attackpower), 0, false, true));
                     }

                     entityiteratorx.m_6469_(DamageSource.f_19318_, (float)(2.0 * attackpower));
                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123772_,
                           entityiteratorx.m_20185_(),
                           entityiteratorx.m_20186_() + (double)(entityiteratorx.m_20206_() / 2.0F),
                           entityiteratorx.m_20189_(),
                           5,
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           (double)(entityiteratorx.m_20206_() / 3.0F),
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           0.05
                        );
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123795_,
                           entityiteratorx.m_20185_(),
                           entityiteratorx.m_20186_() + (double)(entityiteratorx.m_20206_() / 2.0F),
                           entityiteratorx.m_20189_(),
                           5,
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           (double)(entityiteratorx.m_20206_() / 3.0F),
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           0.05
                        );
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123769_,
                           entityiteratorx.m_20185_(),
                           entityiteratorx.m_20186_() + (double)(entityiteratorx.m_20206_() / 2.0F),
                           entityiteratorx.m_20189_(),
                           5,
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           (double)(entityiteratorx.m_20206_() / 3.0F),
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           0.05
                        );
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_DROPLET.get(),
                           entityiteratorx.m_20185_(),
                           entityiteratorx.m_20186_() + (double)(entityiteratorx.m_20206_() / 2.0F),
                           entityiteratorx.m_20189_(),
                           5,
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           (double)(entityiteratorx.m_20206_() / 3.0F),
                           (double)(entityiteratorx.m_20205_() / 3.0F),
                           0.05
                        );
                     }
                  }
               }
            }

            if (type == 3.0) {
               distance = 0.25;
               if (sourceentity.m_6144_()) {
                  for (int index2 = 0; index2 < 4; index2++) {
                     xpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                     ypos = distance * Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16);
                     zpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123759_,
                           entity.m_20185_() + xpos,
                           entity.m_20186_() + ypos + (double)entity.m_20206_() * 0.48,
                           entity.m_20189_() + zpos,
                           2,
                           0.32,
                           0.32,
                           0.32,
                           0.05
                        );
                     }
                  }

                  entity.m_20256_(new Vec3(sourceentity.m_20154_().f_82479_ * attackpower * 1.16, 0.25, sourceentity.m_20154_().f_82481_ * attackpower * 1.16));
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19620_, (int)(12.0 * attackpower), 0, false, true));
                  }
               } else {
                  for (int index3 = 0; (long)index3 < Math.round(6.0 * attackpower); index3++) {
                     xpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                     ypos = distance * Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16);
                     zpos = distance * Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
                     Vec3 _center = new Vec3(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos);

                     for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.2 * attackpower / 2.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                        .collect(Collectors.toList())) {
                        if ((!(entityiteratorxx instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                           && !entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                           && sourceentity != entityiteratorxx
                           && entityiteratorxx instanceof LivingEntity _entity
                           && !_entity.f_19853_.m_5776_()) {
                           _entity.m_7292_(new MobEffectInstance(MobEffects.f_19620_, (int)(16.0 * attackpower), 0, false, true));
                        }
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123759_,
                           entity.m_20185_() + xpos,
                           entity.m_20186_() + ypos + (double)entity.m_20206_() * 0.48,
                           entity.m_20189_() + zpos,
                           4,
                           0.32,
                           0.32,
                           0.32,
                           0.05
                        );
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123766_,
                           entity.m_20185_() + xpos,
                           entity.m_20186_() + ypos + (double)entity.m_20206_() * 0.48,
                           entity.m_20189_() + zpos,
                           1,
                           0.32,
                           0.32,
                           0.32,
                           0.05
                        );
                     }

                     distance += 0.32;
                  }
               }
            }

            if (type == 4.0) {
               if (world instanceof Level _level && !_level.m_5776_()) {
                  _level.m_46511_(null, entity.m_20185_(), entity.m_20186_() + (double)entity.m_20206_() / 1.5, entity.m_20189_(), 0.1F, BlockInteraction.NONE);
               }

               entity.m_20254_((int)(6.0 * attackpower));
               Vec3 _center = new Vec3(entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_());

               for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(3.0 * attackpower / 2.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((!(entityiteratorxxx instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                     && !entityiteratorxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                     && sourceentity != entityiteratorxxx) {
                     entityiteratorxxx.m_6469_(DamageSource.f_19305_, (float)(0.2 * attackpower));
                     entityiteratorxxx.m_20254_((int)(6.0 * attackpower));
                  }
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123756_,
                     entity.m_20185_(),
                     entity.m_20186_() + (double)(entity.m_20206_() / 2.0F),
                     entity.m_20189_(),
                     6,
                     (double)(entity.m_20205_() / 3.0F),
                     (double)(entity.m_20206_() / 3.0F),
                     (double)(entity.m_20205_() / 3.0F),
                     0.05
                  );
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123744_,
                     entity.m_20185_(),
                     entity.m_20186_() + (double)(entity.m_20206_() / 2.0F),
                     entity.m_20189_(),
                     6,
                     (double)(entity.m_20205_() / 3.0F),
                     (double)(entity.m_20206_() / 3.0F),
                     (double)(entity.m_20205_() / 3.0F),
                     0.05
                  );
               }
            }

            if (type == 5.0
               && ((IterRpgModVariables.PlayerVariables)sourceentity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .MeleeAttackCooldown
                  <= 0.0) {
               double _setval = 13.0;
               sourceentity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.MeleeAttackCooldown = _setval;
                  capability.syncPlayerVariables(sourceentity);
               });
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_VOID.get(),
                     entity.m_20185_(),
                     entity.m_20186_() + (double)(entity.m_20206_() / 2.0F),
                     entity.m_20189_(),
                     8,
                     (double)(entity.m_20205_() / 4.0F),
                     (double)(entity.m_20206_() / 4.0F),
                     (double)(entity.m_20205_() / 4.0F),
                     0.05
                  );
               }

               if ((double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) * 0.075 > 1.5 * attackpower) {
                  damage = (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F) * 0.075;
               } else {
                  damage = 1.5 * attackpower;
               }

               if (damage > 5.0 * attackpower) {
                  damage = 5.0 * attackpower;
               }

               if (entity instanceof LivingEntity _entity) {
                  _entity.m_21153_((float)((double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) - damage));
               }
            }
         }
      }
   }
}
