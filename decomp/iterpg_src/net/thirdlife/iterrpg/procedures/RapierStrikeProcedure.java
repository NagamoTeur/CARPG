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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class RapierStrikeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double pitch_off = 0.0;
         double repeat = 0.0;
         double true_pitch = 0.0;
         double iteration = 0.0;
         double dist = 0.0;
         double distabs = 0.0;
         double pitch = 0.0;
         double yaw = 0.0;
         double yaw_off = 0.0;
         double damage = 0.0;
         double distance = 0.0;
         boolean hit = false;
         boolean particle = false;
         if (((entity instanceof LivingEntity _livEnt ? _livEnt.m_21206_() : ItemStack.f_41583_).m_41720_() instanceof BlockItem _bi
                  ? _bi.m_40614_().m_49966_()
                  : Blocks.f_50016_.m_49966_())
               .m_204336_(BlockTags.create(new ResourceLocation("minecraft:small_flowers")))
            && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MeleeAttackCooldown
               <= 0.0) {
            double _setval = 12.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MeleeAttackCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 12);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F,
                     false
                  );
               }
            }

            damage = 3.0;
            hit = true;

            for (int index0 = 0; index0 < 32; index0++) {
               if (hit) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_8767_(
                        ParticleTypes.f_123797_,
                        x + entity.m_20154_().f_82479_ * distance,
                        y + entity.m_20154_().f_82480_ * distance + 1.6,
                        z + entity.m_20154_().f_82481_ * distance,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
                  }

                  Vec3 _center = new Vec3(
                     x + entity.m_20154_().f_82479_ * distance, y + entity.m_20154_().f_82480_ * distance + 1.6, z + entity.m_20154_().f_82481_ * distance
                  );

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.125), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entity != entityiterator) {
                        entityiterator.m_6469_(new EntityDamageSource("generic.player", entity), (float)damage);
                        hit = false;
                     }
                  }

                  distance += 0.25;
               }
            }
         }
      }
   }
}
