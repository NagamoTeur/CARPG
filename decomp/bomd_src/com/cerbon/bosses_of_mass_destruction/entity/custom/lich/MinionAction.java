package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.ModRandom;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.CompoundTagEntityProvider;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.IMobSpawner;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobEntitySpawnPredicate;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobPlacementLogic;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.RangedSpawnPosition;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.SimpleMobSpawner;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class MinionAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   public static final int minionSummonCooldown = 80;
   public static final int minionSummonParticleDelay = 10;
   public static final int minionSummonDelay = 40;
   public static final int minionRuneToMinionSpawnDelay = 40;
   public static final String summonId = "minecraft:phantom";
   public static final CompoundTag summonNbt;
   public static final EntityType<?> summonEntityType;

   public MinionAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      Entity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.performMinionSummon((ServerPlayer)target);
         return 80;
      }
   }

   private void performMinionSummon(ServerPlayer target) {
      this.eventScheduler.addEvent(new TimedEvent(() -> this.beginSummonSingleMob(target), 40, 1, this.shouldCancel));
   }

   void beginSummonSingleMob(ServerPlayer target) {
      CompoundTag compoundTag = summonNbt.m_6426_();
      compoundTag.m_128359_("id", "minecraft:phantom");
      SimpleMobSpawner mobSpawner = new SimpleMobSpawner(target.m_9236_());
      CompoundTagEntityProvider entityProvider = new CompoundTagEntityProvider(compoundTag, target.m_9236_());
      MobEntitySpawnPredicate spawnPredicate = new MobEntitySpawnPredicate(target.f_19853_);
      IMobSpawner summonCircleBeforeSpawn = (pos, summon) -> {
         BMDUtils.spawnParticle(target.m_9236_(), (ParticleOptions)BMDParticles.MAGIC_CIRCLE.get(), pos, Vec3.f_82478_, 0, 0.0);
         BMDUtils.playSound(target.m_9236_(), pos, (SoundEvent)BMDSounds.MINION_RUNE.get(), SoundSource.HOSTILE, 1.0F, 64.0, null);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            mobSpawner.spawn(pos, summon);
            this.entity.m_5496_((SoundEvent)BMDSounds.MINION_SUMMON.get(), 0.7F, 1.0F);
         }, 40, 1, this.shouldCancel));
      };
      new MobPlacementLogic(new RangedSpawnPosition(target.m_20182_(), 4.0, 8.0, new ModRandom()), entityProvider, spawnPredicate, summonCircleBeforeSpawn)
         .tryPlacement(30);
   }

   static {
      try {
         summonNbt = TagParser.m_129359_("{Health:14,Size:2,Attributes:[{Name:\"generic.max_health\",Base:14f}]}");
      } catch (CommandSyntaxException var1) {
         throw new RuntimeException(var1);
      }

      summonEntityType = (EntityType<?>)ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.m_135820_("minecraft:phantom"));
   }
}
