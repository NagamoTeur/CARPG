package shadows.apotheosis.spawn.spawner;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;

public class LyingLevel implements ServerLevelAccessor, WorldGenLevel {
   protected final ServerLevel wrapped;
   protected int fakeLightLevel;

   public LyingLevel(ServerLevel wrapped) {
      this.wrapped = wrapped;
   }

   public void setFakeLightLevel(int light) {
      this.fakeLightLevel = light;
   }

   public long m_183596_() {
      return this.wrapped.m_183596_();
   }

   public LevelTickAccess<Block> m_183326_() {
      return this.wrapped.m_183326_();
   }

   public LevelTickAccess<Fluid> m_183324_() {
      return this.wrapped.m_183324_();
   }

   public LevelData m_6106_() {
      return this.wrapped.m_6106_();
   }

   public DifficultyInstance m_6436_(BlockPos pPos) {
      return this.wrapped.m_6436_(pPos);
   }

   public MinecraftServer m_7654_() {
      return this.wrapped.m_7654_();
   }

   public ChunkSource m_7726_() {
      return this.wrapped.m_7726_();
   }

   public RandomSource m_213780_() {
      return this.wrapped.m_213780_();
   }

   public void m_5594_(Player pPlayer, BlockPos pPos, SoundEvent pSound, SoundSource pCategory, float pVolume, float pPitch) {
      this.wrapped.m_5594_(pPlayer, pPos, pSound, pCategory, pVolume, pPitch);
   }

   public void m_7106_(ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
      this.wrapped.m_7106_(pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
   }

   public void m_5898_(Player pPlayer, int pType, BlockPos pPos, int pData) {
      this.wrapped.m_5898_(pPlayer, pType, pPos, pData);
   }

   public void m_142346_(Entity pEntity, GameEvent pEvent, BlockPos pPos) {
      this.wrapped.m_142346_(pEntity, pEvent, pPos);
   }

   public RegistryAccess m_5962_() {
      return this.wrapped.m_5962_();
   }

   public List<Entity> m_6249_(Entity pEntity, AABB pArea, Predicate<? super Entity> pPredicate) {
      return this.wrapped.m_6249_(pEntity, pArea, pPredicate);
   }

   public <T extends Entity> List<T> m_142425_(EntityTypeTest<Entity, T> pEntityTypeTest, AABB pArea, Predicate<? super T> pPredicate) {
      return this.wrapped.m_142425_(pEntityTypeTest, pArea, pPredicate);
   }

   public List<? extends Player> m_6907_() {
      return this.wrapped.m_6907_();
   }

   public ChunkAccess m_6522_(int pX, int pZ, ChunkStatus pRequiredStatus, boolean pNonnull) {
      return this.wrapped.m_6522_(pX, pZ, pRequiredStatus, pNonnull);
   }

   public int m_6924_(Types pHeightmapType, int pX, int pZ) {
      return this.wrapped.m_6924_(pHeightmapType, pX, pZ);
   }

   public int m_7445_() {
      return this.wrapped.m_7445_();
   }

   public BiomeManager m_7062_() {
      return this.wrapped.m_7062_();
   }

   public Holder<Biome> m_203675_(int pX, int pY, int pZ) {
      return this.wrapped.m_203675_(pX, pY, pZ);
   }

   public boolean m_5776_() {
      return this.wrapped.m_5776_();
   }

   public int m_5736_() {
      return this.wrapped.m_5736_();
   }

   public DimensionType m_6042_() {
      return this.wrapped.m_6042_();
   }

   public float m_7717_(Direction pDirection, boolean pShade) {
      return this.wrapped.m_7717_(pDirection, pShade);
   }

   public LevelLightEngine m_5518_() {
      return this.wrapped.m_5518_();
   }

   public BlockEntity m_7702_(BlockPos pPos) {
      return this.wrapped.m_7702_(pPos);
   }

   public BlockState m_8055_(BlockPos p_45571_) {
      return this.wrapped.m_8055_(p_45571_);
   }

   public FluidState m_6425_(BlockPos pPos) {
      return this.wrapped.m_6425_(pPos);
   }

   public WorldBorder m_6857_() {
      return this.wrapped.m_6857_();
   }

   public boolean m_7433_(BlockPos pPos, Predicate<BlockState> pState) {
      return this.wrapped.m_7433_(pPos, pState);
   }

   public boolean m_142433_(BlockPos pPos, Predicate<FluidState> pPredicate) {
      return this.wrapped.m_142433_(pPos, pPredicate);
   }

   public boolean m_6933_(BlockPos pPos, BlockState pState, int pFlags, int pRecursionLeft) {
      return this.wrapped.m_6933_(pPos, pState, pFlags, pRecursionLeft);
   }

   public boolean m_7471_(BlockPos pPos, boolean pIsMoving) {
      return this.wrapped.m_7471_(pPos, pIsMoving);
   }

   public boolean m_7740_(BlockPos pPos, boolean pDropBlock, Entity pEntity, int pRecursionLeft) {
      return this.wrapped.m_7740_(pPos, pDropBlock, pEntity, pRecursionLeft);
   }

   public ServerLevel m_6018_() {
      return this.wrapped;
   }

   public int m_45517_(LightLayer pLightType, BlockPos pBlockPos) {
      return this.fakeLightLevel;
   }

   public int m_45524_(BlockPos pBlockPos, int pAmount) {
      return this.fakeLightLevel;
   }

   public long m_7328_() {
      return this.wrapped.m_7328_();
   }

   public void m_214171_(GameEvent pEvent, Vec3 pPosition, Context pContext) {
      this.wrapped.m_214171_(pEvent, pPosition, pContext);
   }
}
