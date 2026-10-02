package dev.latvian.mods.kubejs.core;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.registry.registries.Registries;
import dev.latvian.mods.kubejs.entity.RayTraceResultJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.EntityArrayList;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.ScriptTypeHolder;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface EntityKJS extends WithPersistentData, MessageSenderKJS, ScriptTypeHolder {
   default Entity kjs$self() {
      return (Entity)this;
   }

   @Nullable
   default MinecraftServer kjs$getServer() {
      return this.kjs$self().m_9236_().m_7654_();
   }

   default String kjs$getType() {
      return String.valueOf(Registries.getId(this.kjs$self().m_6095_(), Registry.f_122903_));
   }

   default GameProfile kjs$getProfile() {
      return new GameProfile(this.kjs$self().m_20148_(), this.kjs$self().m_6302_());
   }

   @Override
   default Component kjs$getName() {
      return this.kjs$self().m_7755_();
   }

   @Override
   default Component kjs$getDisplayName() {
      return this.kjs$self().m_5446_();
   }

   @Override
   default void kjs$tell(Component message) {
      this.kjs$self().m_213846_(message);
   }

   @Override
   default int kjs$runCommand(String command) {
      return this.kjs$self().m_9236_() instanceof ServerLevel level ? level.m_7654_().m_129892_().m_230957_(this.kjs$self().m_20203_(), command) : 0;
   }

   @Override
   default int kjs$runCommandSilent(String command) {
      return this.kjs$self().m_9236_() instanceof ServerLevel level ? level.m_7654_().m_129892_().m_230957_(this.kjs$self().m_20203_().m_81324_(), command) : 0;
   }

   default boolean kjs$isPlayer() {
      return false;
   }

   @Nullable
   default ItemStack kjs$getItem() {
      return null;
   }

   default boolean kjs$isFrame() {
      return this instanceof ItemFrame;
   }

   default boolean kjs$isLiving() {
      return false;
   }

   default boolean kjs$isMonster() {
      return !this.kjs$self().m_6095_().m_20674_().m_21609_();
   }

   default boolean kjs$isAnimal() {
      return this.kjs$self().m_6095_().m_20674_().m_21610_();
   }

   default boolean kjs$isAmbientCreature() {
      return this.kjs$self().m_6095_().m_20674_() == MobCategory.AMBIENT;
   }

   default boolean kjs$isWaterCreature() {
      return this.kjs$self().m_6095_().m_20674_() == MobCategory.WATER_CREATURE;
   }

   default boolean kjs$isPeacefulCreature() {
      return this.kjs$self().m_6095_().m_20674_().m_21609_();
   }

   default void kjs$setX(double x) {
      this.kjs$setPosition(x, this.kjs$self().m_20186_(), this.kjs$self().m_20189_());
   }

   default void kjs$setY(double y) {
      this.kjs$setPosition(this.kjs$self().m_20185_(), y, this.kjs$self().m_20189_());
   }

   default void kjs$setZ(double z) {
      this.kjs$setPosition(this.kjs$self().m_20185_(), this.kjs$self().m_20186_(), z);
   }

   default double kjs$getMotionX() {
      return this.kjs$self().m_20184_().f_82479_;
   }

   default void kjs$setMotionX(double x) {
      Vec3 m = this.kjs$self().m_20184_();
      this.kjs$self().m_20334_(x, m.f_82480_, m.f_82481_);
   }

   default double kjs$getMotionY() {
      return this.kjs$self().m_20184_().f_82480_;
   }

   default void kjs$setMotionY(double y) {
      Vec3 m = this.kjs$self().m_20184_();
      this.kjs$self().m_20334_(m.f_82479_, y, m.f_82481_);
   }

   default double kjs$getMotionZ() {
      return this.kjs$self().m_20184_().f_82481_;
   }

   default void kjs$setMotionZ(double z) {
      Vec3 m = this.kjs$self().m_20184_();
      this.kjs$self().m_20334_(m.f_82479_, m.f_82480_, z);
   }

   default void kjs$teleportTo(ResourceLocation dimension, double x, double y, double z, float yaw, float pitch) {
      Level previousLevel = this.kjs$self().m_9236_();
      ServerLevel level = this.kjs$getServer().m_129880_(ResourceKey.m_135785_(Registry.f_122819_, dimension));
      if (level == null) {
         throw new IllegalArgumentException("Invalid dimension!");
      } else if (!Level.m_46741_(new BlockPos(x, y, z))) {
         throw new IllegalArgumentException("Invalid coordinates!");
      } else if (Float.isNaN(yaw) || Float.isNaN(pitch)) {
         throw new IllegalArgumentException("Invalid rotation!");
      } else if (level == previousLevel) {
         this.kjs$setPositionAndRotation(x, y, z, yaw, pitch);
      } else {
         try {
            TeleportCommand.m_139014_(this.kjs$self().m_20203_(), this.kjs$self(), level, x, y, z, Set.of(), yaw, pitch, null);
         } catch (CommandSyntaxException var13) {
            throw new IllegalArgumentException(var13.getRawMessage().getString());
         }
      }
   }

   default void kjs$setPosition(BlockContainerJS block) {
      this.kjs$teleportTo(
         block.getDimension(), (double)block.getX(), (double)block.getY(), (double)block.getZ(), this.kjs$self().m_146908_(), this.kjs$self().m_146909_()
      );
   }

   default void kjs$setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
      this.kjs$self().m_7678_(x, y, z, yaw, pitch);
   }

   default void kjs$setPosition(double x, double y, double z) {
      this.kjs$setPositionAndRotation(x, y, z, this.kjs$self().m_146908_(), this.kjs$self().m_146909_());
   }

   default void kjs$setRotation(float yaw, float pitch) {
      this.kjs$setPositionAndRotation(this.kjs$self().m_20185_(), this.kjs$self().m_20186_(), this.kjs$self().m_20189_(), yaw, pitch);
   }

   default EntityArrayList kjs$getPassengers() {
      return new EntityArrayList(this.kjs$self().m_9236_(), this.kjs$self().m_20197_());
   }

   default String kjs$getTeamId() {
      Team team = this.kjs$self().m_5647_();
      return team == null ? "" : team.m_5758_();
   }

   default boolean kjs$isOnScoreboardTeam(String teamId) {
      Team team = this.kjs$self().m_20193_().m_6188_().m_83489_(teamId);
      return team != null && this.kjs$self().m_20031_(team);
   }

   default Direction kjs$getFacing() {
      if (this.kjs$self().m_146909_() > 45.0F) {
         return Direction.DOWN;
      } else {
         return this.kjs$self().m_146909_() < -45.0F ? Direction.UP : this.kjs$self().m_6350_();
      }
   }

   default BlockContainerJS kjs$getBlock() {
      return new BlockContainerJS(this.kjs$self().m_9236_(), this.kjs$self().m_20183_());
   }

   @Deprecated
   default CompoundTag kjs$getFullNBT() {
      ConsoleJS.getCurrent(ScriptManager.getCurrentContext()).error("getFullNBT() and fullNBT are deprecated. Use getNbt() or nbt instead!");
      return this.kjs$getNbt();
   }

   @Deprecated
   default void kjs$setFullNBT(@Nullable CompoundTag nbt) {
      ConsoleJS.getCurrent(ScriptManager.getCurrentContext()).error("setFullNBT() and fullNBT are deprecated. Use setNbt(CompoundTag) or nbt instead!");
      this.kjs$setNbt(nbt);
   }

   @Deprecated
   default Entity kjs$mergeFullNBT(@Nullable CompoundTag tag) {
      ConsoleJS.getCurrent(ScriptManager.getCurrentContext()).error("mergeFullNBT() is deprecated. Use mergeNbt instead!");
      return this.kjs$mergeNbt(tag);
   }

   default CompoundTag kjs$getNbt() {
      CompoundTag nbt = new CompoundTag();
      this.kjs$self().m_20240_(nbt);
      return nbt;
   }

   default void kjs$setNbt(@Nullable CompoundTag nbt) {
      if (nbt != null) {
         this.kjs$self().m_20258_(nbt);
      }
   }

   default Entity kjs$mergeNbt(@Nullable CompoundTag tag) {
      if (tag != null && !tag.m_128456_()) {
         CompoundTag nbt = this.kjs$getNbt();

         for (String k : tag.m_128431_()) {
            Tag t = tag.m_128423_(k);
            if (t != null && t != EndTag.f_128534_) {
               nbt.m_128365_(k, tag.m_128423_(k));
            } else {
               nbt.m_128473_(k);
            }
         }

         this.kjs$setNbt(nbt);
         return this.kjs$self();
      } else {
         return this.kjs$self();
      }
   }

   default void kjs$playSound(SoundEvent id, float volume, float pitch) {
      this.kjs$self()
         .f_19853_
         .m_6263_(null, this.kjs$self().m_20185_(), this.kjs$self().m_20186_(), this.kjs$self().m_20189_(), id, this.kjs$self().m_5720_(), volume, pitch);
   }

   default void kjs$playSound(SoundEvent id) {
      this.kjs$playSound(id, 1.0F, 1.0F);
   }

   default void kjs$spawn() {
      this.kjs$self().m_9236_().m_7967_(this.kjs$self());
   }

   default void kjs$attack(float hp) {
      this.kjs$self().m_6469_(DamageSource.f_19318_, hp);
   }

   default double kjs$getDistance(double x, double y, double z) {
      return Math.sqrt(this.kjs$self().m_20275_(x, y, z));
   }

   default double kjs$getDistanceSq(BlockPos pos) {
      return this.kjs$self().m_20275_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5);
   }

   default double kjs$getDistance(BlockPos pos) {
      return Math.sqrt(this.kjs$getDistanceSq(pos));
   }

   default RayTraceResultJS kjs$rayTrace(double distance, boolean fluids) {
      Entity entity = this.kjs$self();
      HitResult hitResult = entity.m_19907_(distance, 0.0F, fluids);
      Vec3 eyePosition = entity.m_146892_();
      Vec3 lookVector = entity.m_20252_(1.0F);
      Vec3 traceEnd = eyePosition.m_82520_(lookVector.f_82479_ * distance, lookVector.f_82480_ * distance, lookVector.f_82481_ * distance);
      AABB bound = entity.m_20191_().m_82369_(lookVector.m_82490_(distance)).m_82377_(1.0, 1.0, 1.0);
      double distanceSquared = hitResult.m_6662_() != Type.MISS ? hitResult.m_82450_().m_82557_(eyePosition) : distance * distance;
      EntityHitResult entityHitResult = ProjectileUtil.m_37287_(entity, eyePosition, traceEnd, bound, ent -> !ent.m_5833_() && ent.m_6087_(), distanceSquared);
      if (entityHitResult != null) {
         double entityDistanceSquared = eyePosition.m_82557_(entityHitResult.m_82450_());
         if (entityDistanceSquared < distanceSquared || hitResult.m_6662_() == Type.MISS) {
            hitResult = entityHitResult;
         }
      }

      return new RayTraceResultJS(entity, hitResult, distance);
   }

   default RayTraceResultJS kjs$rayTrace(double distance) {
      return this.kjs$rayTrace(distance, true);
   }

   @HideFromJS
   @Nullable
   default CompoundTag kjs$getRawPersistentData() {
      throw new NoMixinException();
   }

   @HideFromJS
   default void kjs$setRawPersistentData(@Nullable CompoundTag tag) {
      throw new NoMixinException();
   }

   @Override
   default ScriptType kjs$getScriptType() {
      return this.kjs$self().f_19853_.kjs$getScriptType();
   }
}
