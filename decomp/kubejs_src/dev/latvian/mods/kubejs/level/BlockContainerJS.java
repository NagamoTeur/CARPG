package dev.latvian.mods.kubejs.level;

import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.architectury.registry.registries.Registries;
import dev.latvian.mods.kubejs.block.MaterialJS;
import dev.latvian.mods.kubejs.block.MaterialListJS;
import dev.latvian.mods.kubejs.core.InventoryKJS;
import dev.latvian.mods.kubejs.platform.LevelPlatformHelper;
import dev.latvian.mods.kubejs.player.EntityArrayList;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.Tags;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.SpecialEquality;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class BlockContainerJS implements SpecialEquality {
   private static final ResourceLocation AIR_ID = new ResourceLocation("minecraft:air");
   public final Level minecraftLevel;
   private final BlockPos pos;
   public transient BlockState cachedState;
   public transient BlockEntity cachedEntity;

   public BlockContainerJS(Level w, BlockPos p) {
      this.minecraftLevel = w;
      this.pos = p;
   }

   public BlockContainerJS(BlockEntity blockEntity) {
      this.minecraftLevel = blockEntity.m_58904_();
      this.pos = blockEntity.m_58899_();
      this.cachedEntity = blockEntity;
   }

   public void clearCache() {
      this.cachedState = null;
      this.cachedEntity = null;
   }

   public Level getLevel() {
      return this.minecraftLevel;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public ResourceLocation getDimension() {
      return this.minecraftLevel.m_46472_().m_135782_();
   }

   public int getX() {
      return this.getPos().m_123341_();
   }

   public int getY() {
      return this.getPos().m_123342_();
   }

   public int getZ() {
      return this.getPos().m_123343_();
   }

   public BlockContainerJS offset(Direction f, int d) {
      return new BlockContainerJS(this.minecraftLevel, this.getPos().m_5484_(f, d));
   }

   public BlockContainerJS offset(Direction f) {
      return this.offset(f, 1);
   }

   public BlockContainerJS offset(int x, int y, int z) {
      return new BlockContainerJS(this.minecraftLevel, this.getPos().m_7918_(x, y, z));
   }

   public BlockContainerJS getDown() {
      return this.offset(Direction.DOWN);
   }

   public BlockContainerJS getUp() {
      return this.offset(Direction.UP);
   }

   public BlockContainerJS getNorth() {
      return this.offset(Direction.NORTH);
   }

   public BlockContainerJS getSouth() {
      return this.offset(Direction.SOUTH);
   }

   public BlockContainerJS getWest() {
      return this.offset(Direction.WEST);
   }

   public BlockContainerJS getEast() {
      return this.offset(Direction.EAST);
   }

   public BlockState getBlockState() {
      if (this.cachedState == null) {
         this.cachedState = this.minecraftLevel.m_8055_(this.getPos());
      }

      return this.cachedState;
   }

   public void setBlockState(BlockState state, int flags) {
      this.minecraftLevel.m_7731_(this.getPos(), state, flags);
      this.clearCache();
      this.cachedState = state;
   }

   public String getId() {
      return Registries.getId(this.getBlockState().m_60734_(), Registry.f_122901_).toString();
   }

   public Collection<ResourceLocation> getTags() {
      return Tags.byBlockState(this.getBlockState()).<ResourceLocation>map(TagKey::f_203868_).collect(Collectors.toSet());
   }

   public boolean hasTag(ResourceLocation tag) {
      return this.getBlockState().m_204336_(Tags.block(tag));
   }

   public void set(ResourceLocation id, Map<?, ?> properties, int flags) {
      Block block = (Block)KubeJSRegistries.blocks().get(id);
      BlockState state = block.m_49966_();
      if (!properties.isEmpty() && state.m_60734_() != Blocks.f_50016_) {
         Map<String, Property<?>> pmap = new HashMap<>();

         for (Property<?> property : state.m_61147_()) {
            pmap.put(property.m_61708_(), property);
         }

         for (Entry<?, ?> entry : properties.entrySet()) {
            Property<? extends Comparable<?>> property = (Property<? extends Comparable<?>>)pmap.get(String.valueOf(entry.getKey()));
            if (property != null) {
               state = (BlockState)state.m_61124_(property, UtilsJS.cast(property.m_6215_(String.valueOf(entry.getValue())).orElseThrow()));
            }
         }
      }

      this.setBlockState(state, flags);
   }

   public void set(ResourceLocation id, Map<?, ?> properties) {
      this.set(id, properties, 3);
   }

   public void set(ResourceLocation id) {
      this.set(id, Collections.emptyMap());
   }

   public Map<String, String> getProperties() {
      Map<String, String> map = new HashMap<>();
      BlockState state = this.getBlockState();

      for (Property property : state.m_61147_()) {
         map.put(property.m_61708_(), property.m_6940_(state.m_61143_(property)));
      }

      return map;
   }

   @Nullable
   public BlockEntity getEntity() {
      if (this.cachedEntity == null || this.cachedEntity.m_58901_()) {
         this.cachedEntity = this.minecraftLevel.m_7702_(this.pos);
      }

      return this.cachedEntity;
   }

   public String getEntityId() {
      BlockEntity entity = this.getEntity();
      return entity == null ? "minecraft:air" : Registries.getId(entity.m_58903_(), Registry.f_122907_).toString();
   }

   @Nullable
   public CompoundTag getEntityData() {
      BlockEntity entity = this.getEntity();
      return entity != null ? entity.m_187480_() : null;
   }

   public void setEntityData(@Nullable CompoundTag tag) {
      if (tag != null) {
         BlockEntity entity = this.getEntity();
         if (entity != null) {
            entity.m_142466_(tag);
         }
      }
   }

   public void mergeEntityData(@Nullable CompoundTag tag) {
      CompoundTag t = this.getEntityData();
      if (t == null) {
         this.setEntityData(tag);
      } else if (tag != null && !tag.m_128456_()) {
         for (String s : tag.m_128431_()) {
            t.m_128365_(s, tag.m_128423_(s));
         }
      }

      this.setEntityData(t);
   }

   public int getLight() {
      return this.minecraftLevel.m_46803_(this.pos);
   }

   public int getSkyLight() {
      return this.minecraftLevel.m_45517_(LightLayer.SKY, this.pos) - this.minecraftLevel.m_7445_();
   }

   public int getBlockLight() {
      return this.minecraftLevel.m_45517_(LightLayer.BLOCK, this.pos);
   }

   public boolean getCanSeeSky() {
      return this.minecraftLevel.m_45527_(this.pos);
   }

   public boolean canSeeSkyFromBelowWater() {
      return this.minecraftLevel.m_46861_(this.pos);
   }

   @Override
   public String toString() {
      String id = this.getId();
      Map<String, String> properties = this.getProperties();
      if (properties.isEmpty()) {
         return id;
      } else {
         StringBuilder builder = new StringBuilder(id);
         builder.append('[');
         boolean first = true;

         for (Entry<String, String> entry : properties.entrySet()) {
            if (first) {
               first = false;
            } else {
               builder.append(',');
            }

            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
         }

         builder.append(']');
         return builder.toString();
      }
   }

   public ExplosionJS createExplosion() {
      return new ExplosionJS(this.minecraftLevel, (double)this.getX() + 0.5, (double)this.getY() + 0.5, (double)this.getZ() + 0.5);
   }

   @Nullable
   public Entity createEntity(ResourceLocation id) {
      Entity entity = this.getLevel().kjs$createEntity(id);
      if (entity != null) {
         entity.kjs$setPosition(this);
      }

      return entity;
   }

   public void spawnLightning(boolean effectOnly, @Nullable ServerPlayer player) {
      if (this.minecraftLevel instanceof ServerLevel) {
         LightningBolt e = (LightningBolt)EntityType.f_20465_.m_20615_(this.minecraftLevel);
         e.m_6027_((double)this.getX() + 0.5, (double)this.getY() + 0.5, (double)this.getZ() + 0.5);
         e.m_20879_(player);
         e.m_20874_(effectOnly);
         this.minecraftLevel.m_7967_(e);
      }
   }

   public void spawnLightning(boolean effectOnly) {
      this.spawnLightning(effectOnly, null);
   }

   public void spawnLightning() {
      this.spawnLightning(false);
   }

   public void spawnFireworks(FireworksJS fireworks) {
      this.minecraftLevel
         .m_7967_(fireworks.createFireworkRocket(this.minecraftLevel, (double)this.getX() + 0.5, (double)this.getY() + 0.5, (double)this.getZ() + 0.5));
   }

   @Nullable
   public InventoryKJS getInventory() {
      return this.getInventory(Direction.UP);
   }

   @Nullable
   public InventoryKJS getInventory(Direction facing) {
      BlockEntity entity = this.getEntity();
      if (entity != null) {
         InventoryKJS c = LevelPlatformHelper.get().getInventoryFromBlockEntity(entity, facing);
         if (c != null) {
            return c;
         }

         if (entity instanceof InventoryKJS) {
            return (InventoryKJS)entity;
         }
      }

      return null;
   }

   public MaterialJS getMaterial() {
      return MaterialListJS.INSTANCE.get(this.getBlockState().m_60767_());
   }

   public ItemStack getItem() {
      BlockState state = this.getBlockState();
      return state.m_60734_().m_7397_(this.minecraftLevel, this.pos, state);
   }

   public List<ItemStack> getDrops() {
      return this.getDrops(null, ItemStack.f_41583_);
   }

   public List<ItemStack> getDrops(@Nullable Entity entity, ItemStack heldItem) {
      return this.minecraftLevel instanceof ServerLevel s ? Block.m_49874_(this.getBlockState(), s, this.pos, this.getEntity(), entity, heldItem) : null;
   }

   public void popItem(ItemStack item) {
      Block.m_49840_(this.minecraftLevel, this.pos, item);
   }

   public void popItemFromFace(ItemStack item, Direction dir) {
      Block.m_152435_(this.minecraftLevel, this.pos, dir, item);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return !(obj instanceof CharSequence) && !(obj instanceof ResourceLocation) ? super.equals(obj) : this.getId().equals(obj.toString());
      }
   }

   private static boolean isReal(Player p) {
      return !PlayerHooks.isFake(p);
   }

   public EntityArrayList getPlayersInRadius(double radius) {
      return new EntityArrayList(
         this.minecraftLevel,
         this.minecraftLevel
            .m_6443_(
               Player.class,
               new AABB(
                  (double)this.pos.m_123341_() - radius,
                  (double)this.pos.m_123342_() - radius,
                  (double)this.pos.m_123343_() - radius,
                  (double)this.pos.m_123341_() + 1.0 + radius,
                  (double)this.pos.m_123342_() + 1.0 + radius,
                  (double)this.pos.m_123343_() + 1.0 + radius
               ),
               BlockContainerJS::isReal
            )
      );
   }

   public EntityArrayList getPlayersInRadius() {
      return this.getPlayersInRadius(8.0);
   }

   public ResourceLocation getBiomeId() {
      return this.minecraftLevel.m_204166_(this.pos).m_203543_().orElse(Biomes.f_48202_).m_135782_();
   }

   public boolean specialEquals(Object o, boolean shallow) {
      return !(o instanceof CharSequence) && !(o instanceof ResourceLocation) ? this.equals(o) : this.getId().equals(o.toString());
   }

   public CompoundTag getTypeData() {
      return this.getBlockState().m_60734_().kjs$getTypeData();
   }
}
