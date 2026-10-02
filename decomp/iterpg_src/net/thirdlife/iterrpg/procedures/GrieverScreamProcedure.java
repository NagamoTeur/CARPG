package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModMobEffects;

public class GrieverScreamProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (entity.getPersistentData().m_128459_("screamTime") >= 1200.0 && sourceentity instanceof Player) {
            entity.getPersistentData().m_128347_("screamTime", 0.0);
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:griever_scream")),
                     SoundSource.HOSTILE,
                     5.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:griever_scream")),
                     SoundSource.HOSTILE,
                     5.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_123746_, x, y + 1.25, z, 24, 0.4, 1.0, 0.4, 0.025);
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                  && entityiterator instanceof LivingEntity _entity
                  && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance((MobEffect)IterRpgModMobEffects.CURSED.get(), 1200, 0, false, false));
               }

               if (entityiterator instanceof ServerPlayer _player) {
                  Advancement _adv = _player.f_8924_.m_129889_().m_136041_(new ResourceLocation("iter_rpg:iter_cursed"));
                  AdvancementProgress _ap = _player.m_8960_().m_135996_(_adv);
                  if (!_ap.m_8193_()) {
                     Iterator _iterator = _ap.m_8219_().iterator();

                     while (_iterator.hasNext()) {
                        _player.m_8960_().m_135988_(_adv, (String)_iterator.next());
                     }
                  }
               }
            }

            _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof LivingEntity) {
                  LivingEntity _entity = (LivingEntity)entityiterator;
                  if (!_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 120, 0, false, false));
                  }
               }
            }
         }
      }
   }
}
