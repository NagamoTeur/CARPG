package shadows.apotheosis.adventure.boss;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import shadows.apotheosis.Apotheosis;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.codec.PlaceboCodecs.CodecProvider;
import shadows.placebo.json.NBTAdapter;

public interface Exclusion extends CodecProvider<Exclusion> {
   BiMap<ResourceLocation, Codec<? extends Exclusion>> CODECS = HashBiMap.create();
   Codec<Exclusion> CODEC = PlaceboCodecs.mapBacked("Miniboss Exclusion", CODECS);

   boolean isExcluded(Mob var1, ServerLevelAccessor var2, MobSpawnType var3, @Nullable CompoundTag var4);

   boolean requiresNbtAccess();

   static void initSerializers() {
      register("spawn_type", Exclusion.SpawnTypeExclusion.CODEC);
      register("nbt", Exclusion.NbtExclusion.CODEC);
      register("surface_type", Exclusion.SurfaceTypeExclusion.CODEC);
      register("and", Exclusion.AndExclusion.CODEC);
   }

   private static void register(String id, Codec<? extends Exclusion> codec) {
      CODECS.put(Apotheosis.loc(id), codec);
   }

   public static record AndExclusion(List<Exclusion> exclusions) implements Exclusion {
      public static Codec<Exclusion.AndExclusion> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(Exclusion.CODEC.listOf().fieldOf("exclusions").forGetter(Exclusion.AndExclusion::exclusions))
               .apply(inst, Exclusion.AndExclusion::new)
      );

      public Codec<? extends Exclusion> getCodec() {
         return CODEC;
      }

      @Override
      public boolean isExcluded(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType, CompoundTag entityNbt) {
         return this.exclusions.stream().allMatch(e -> e.isExcluded(mob, level, spawnType, entityNbt));
      }

      @Override
      public boolean requiresNbtAccess() {
         return this.exclusions.stream().anyMatch(Exclusion::requiresNbtAccess);
      }
   }

   public static record NbtExclusion(CompoundTag nbt) implements Exclusion {
      public static Codec<Exclusion.NbtExclusion> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(NBTAdapter.EITHER_CODEC.fieldOf("nbt").forGetter(Exclusion.NbtExclusion::nbt)).apply(inst, Exclusion.NbtExclusion::new)
      );

      public Codec<? extends Exclusion> getCodec() {
         return CODEC;
      }

      @Override
      public boolean isExcluded(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType, CompoundTag entityNbt) {
         return NbtUtils.m_129235_(this.nbt, entityNbt, true);
      }

      @Override
      public boolean requiresNbtAccess() {
         return true;
      }
   }

   public static record SpawnTypeExclusion(Set<MobSpawnType> types) implements Exclusion {
      public static Codec<Exclusion.SpawnTypeExclusion> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(PlaceboCodecs.setOf(new EnumCodec(MobSpawnType.class)).fieldOf("spawn_types").forGetter(Exclusion.SpawnTypeExclusion::types))
               .apply(inst, Exclusion.SpawnTypeExclusion::new)
      );

      public Codec<? extends Exclusion> getCodec() {
         return CODEC;
      }

      @Override
      public boolean isExcluded(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType, CompoundTag entityNbt) {
         return this.types.contains(spawnType);
      }

      @Override
      public boolean requiresNbtAccess() {
         return false;
      }
   }

   public static record SurfaceTypeExclusion(BossEvents.BossSpawnRules rule) implements Exclusion {
      public static Codec<Exclusion.SurfaceTypeExclusion> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(BossEvents.BossSpawnRules.CODEC.fieldOf("rule").forGetter(Exclusion.SurfaceTypeExclusion::rule))
               .apply(inst, Exclusion.SurfaceTypeExclusion::new)
      );

      public Codec<? extends Exclusion> getCodec() {
         return CODEC;
      }

      @Override
      public boolean isExcluded(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType, CompoundTag entityNbt) {
         return !this.rule.test(level, mob.m_20183_());
      }

      @Override
      public boolean requiresNbtAccess() {
         return false;
      }
   }
}
