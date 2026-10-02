package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.capabilities.IPlaytimeCounter;
import com.aizistral.enigmaticlegacy.api.generic.ConfigurableItem;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.api.quack.IProperShieldUser;
import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.helpers.AdvancedSpawnLocationHelper;
import com.aizistral.enigmaticlegacy.items.GolemHeart;
import com.aizistral.enigmaticlegacy.items.InfernalShield;
import com.aizistral.enigmaticlegacy.items.TheAcknowledgment;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.objects.DimensionalPosition;
import com.aizistral.enigmaticlegacy.objects.EnigmaticTransience;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketUpdateCompass;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEffects;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEnchantments;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.base.Objects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class SuperpositionHandler {
   public static final Random RANDOM = new Random();
   public static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
   public static final UUID SCROLL_SLOT_UUID = UUID.fromString("ae465e52-ffc2-4f57-b09a-066aa0cea3d4");
   public static final UUID SPELLSTONE_SLOT_UUID = UUID.fromString("63df175a-0d6d-4163-8ef1-218bcb42feba");
   public static final UUID RING_SLOT_UUID = UUID.fromString("76012386-aa31-4c17-8d6a-e9dd29affcb0");
   public static final UUID CHARM_SLOT_UUID = UUID.fromString("485121e7-b670-45dc-b014-4c8b8f62283d");
   public static final char[] UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
   public static final char[] LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
   public static final char[] NUMBERS = "0123456789".toCharArray();
   public static final char[] SPECIAL_SYMBOLS = "-+=(){}[]':;./,<>*&^%$#@!?~".toCharArray();

   public static boolean hasAdvancedCurios(LivingEntity entity) {
      return getAdvancedCurios(entity).size() > 0;
   }

   public static boolean unlockSpecialSlot(String slot, Player player) {
      if (!slot.equals("scroll") && !slot.equals("spellstone") && !slot.equals("ring") && !slot.equals("charm")) {
         throw new IllegalArgumentException("Slot type '" + slot + "' is not supported!");
      } else {
         MutableBoolean success = new MutableBoolean(false);
         UUID id = slot.equals("scroll")
            ? SCROLL_SLOT_UUID
            : (slot.equals("spellstone") ? SPELLSTONE_SLOT_UUID : (slot.equals("ring") ? RING_SLOT_UUID : CHARM_SLOT_UUID));
         ICuriosHelper apiHelper = CuriosApi.getCuriosHelper();
         apiHelper.getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(slot).ifPresent(stacks -> {
               Map<UUID, AttributeModifier> map = stacks.getModifiers();
               if (!stacks.getModifiers().containsKey(id)) {
                  stacks.addPermanentModifier(new AttributeModifier(id, "Masterslot", 1.0, Operation.ADDITION));
                  success.setTrue();
               }
            }));
         return success.getValue();
      }
   }

   public static List<ItemStack> getAdvancedCurios(LivingEntity entity) {
      List<ItemStack> stackList = new ArrayList<>();
      ICuriosItemHandler handler = (ICuriosItemHandler)CuriosApi.getCuriosHelper().getCuriosHandler(entity).orElse(null);
      if (handler != null) {
         handler.getCurios().values().forEach(stacksHandler -> {
            IDynamicStackHandler soloStackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stacksHandler.getSlots(); i++) {
               if (soloStackHandler.getStackInSlot(i) != null && soloStackHandler.getStackInSlot(i).m_41720_() instanceof ItemSpellstoneCurio) {
                  stackList.add(soloStackHandler.getStackInSlot(i));
               }
            }
         });
      }

      return stackList;
   }

   public static boolean isSlotLocked(String id, LivingEntity livingEntity) {
      ICuriosItemHandler handler = (ICuriosItemHandler)CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).orElse(null);
      return handler != null ? handler.getLockedSlots().contains(id) : true;
   }

   public static boolean hasSpellstone(LivingEntity entity) {
      return getSpellstone(entity) != null;
   }

   @Nullable
   public static ItemStack getSpellstone(LivingEntity entity) {
      List<ItemStack> spellstoneStack = new ArrayList<>();
      CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(handler -> {
         ICurioStacksHandler stacksHandler = (ICurioStacksHandler)handler.getCurios().get("spellstone");
         if (stacksHandler != null) {
            IDynamicStackHandler soloStackHandler = stacksHandler.getStacks();
            if (soloStackHandler != null) {
               for (int i = 0; i < stacksHandler.getSlots(); i++) {
                  if (soloStackHandler.getStackInSlot(i) != null && soloStackHandler.getStackInSlot(i).m_41720_() instanceof ISpellstone) {
                     spellstoneStack.add(soloStackHandler.getStackInSlot(i));
                     break;
                  }
               }
            }
         }
      });
      return spellstoneStack.isEmpty() ? null : spellstoneStack.get(0);
   }

   public static boolean hasCurio(LivingEntity entity, Item curio) {
      Optional<ImmutableTriple<String, Integer, ItemStack>> data = CuriosApi.getCuriosHelper().findEquippedCurio(curio, entity);
      return data.isPresent();
   }

   @Nullable
   public static ItemStack getCurioStack(LivingEntity entity, Item curio) {
      Optional<ImmutableTriple<String, Integer, ItemStack>> data = CuriosApi.getCuriosHelper().findEquippedCurio(curio, entity);
      return data.isPresent() ? (ItemStack)data.get().getRight() : null;
   }

   public static void destroyCurio(LivingEntity entity, Item curio) {
      CuriosApi.getCuriosHelper().getEquippedCurios(entity).ifPresent(handler -> {
         for (int i = 0; i < handler.getSlots() - 1; i++) {
            if (handler.getStackInSlot(i) != null && handler.getStackInSlot(i).m_41720_() == curio) {
               handler.setStackInSlot(i, ItemStack.f_41583_);
            }
         }
      });
   }

   public static boolean tryForceEquip(LivingEntity entity, ItemStack curio) {
      if (!(curio.m_41720_() instanceof ICurioItem)) {
         throw new IllegalArgumentException("I fear for now this only works with ICurioItem");
      } else {
         MutableBoolean equipped = new MutableBoolean(false);
         ICurioItem item = (ICurioItem)curio.m_41720_();
         CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(handler -> {
            if (!entity.f_19853_.f_46443_) {
               Map<String, ICurioStacksHandler> curios = handler.getCurios();

               for (Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                  IDynamicStackHandler stackHandler = entry.getValue().getStacks();

                  for (int i = 0; i < stackHandler.getSlots(); i++) {
                     ItemStack present = stackHandler.getStackInSlot(i);
                     Set<String> tags = CuriosApi.getCuriosHelper().getCurioTags(curio.m_41720_());
                     String id = entry.getKey();
                     SlotContext context = new SlotContext(id, entity, i, false, entry.getValue().isVisible());
                     if (present.m_41619_() && (tags.contains(id) || tags.contains("curio")) && item.canEquip(context, curio)) {
                        stackHandler.setStackInSlot(i, curio);
                        item.playRightClickEquipSound(entity, curio);
                        equipped.setTrue();
                        return;
                     }
                  }
               }
            }
         });
         return equipped.booleanValue();
      }
   }

   public static void registerCurioType(String identifier, int slots, boolean isHidden, @Nullable ResourceLocation icon) {
      top.theillusivec4.curios.api.SlotTypeMessage.Builder message = new top.theillusivec4.curios.api.SlotTypeMessage.Builder(identifier);
      message.size(slots);
      if (isHidden) {
         message.hide();
      }

      if (icon != null) {
         message.icon(icon);
      }

      InterModComms.sendTo("curios", "register_type", () -> message.build());
   }

   public static SoundEvent registerSound(String soundName) {
      ResourceLocation location = new ResourceLocation("enigmaticlegacy", soundName);
      SoundEvent event = new SoundEvent(location);
      ForgeRegistries.SOUND_EVENTS.register(location, event);
      return event;
   }

   public static AABB getBoundingBoxAroundEntity(Entity entity, double radius) {
      return new AABB(
         entity.m_20185_() - radius,
         entity.m_20186_() - radius,
         entity.m_20189_() - radius,
         entity.m_20185_() + radius,
         entity.m_20186_() + radius,
         entity.m_20189_() + radius
      );
   }

   public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
      Vector3 entityVector = Vector3.fromEntityCenter(entity);
      Vector3 finalVector = originalPosVector.subtract(entityVector);
      if (finalVector.mag() > 1.0) {
         finalVector = finalVector.normalize();
      }

      entity.m_20334_(finalVector.x * (double)modifier, finalVector.y * (double)modifier, finalVector.z * (double)modifier);
   }

   @Nullable
   public static LivingEntity getObservedEntity(Player player, Level world, float range, int maxDist) {
      List<LivingEntity> entities = getObservedEntities(player, world, range, maxDist, true);
      return entities.size() > 0 ? entities.get(0) : null;
   }

   public static List<LivingEntity> getObservedEntities(Player player, Level world, float range, int maxDist, boolean stopWhenFound) {
      Vector3 target = Vector3.fromEntityCenter(player);
      List<LivingEntity> entities = new ArrayList<>();

      for (int distance = 1; distance < maxDist; distance++) {
         target = target.add(new Vector3(player.m_20154_()).multiply((double)distance)).add(0.0, 0.5, 0.0);
         List<LivingEntity> list = player.f_19853_
            .m_45976_(
               LivingEntity.class,
               new AABB(
                  target.x - (double)range,
                  target.y - (double)range,
                  target.z - (double)range,
                  target.x + (double)range,
                  target.y + (double)range,
                  target.z + (double)range
               )
            );
         list.removeIf(entity -> entity == player || !player.m_142582_(entity));
         entities.addAll(list);
         if (stopWhenFound && entities.size() > 0) {
            break;
         }
      }

      return entities;
   }

   @Nullable
   public static <T extends LivingEntity> T getClosestEntity(List<? extends T> entities, Predicate<LivingEntity> predicate, double x, double y, double z) {
      double d0 = -1.0;
      T t = null;

      for (T t1 : entities) {
         if (predicate.test(t1)) {
            double d1 = t1.m_20275_(x, y, z);
            if (d0 == -1.0 || d1 < d0) {
               d0 = d1;
               t = t1;
            }
         }
      }

      return t;
   }

   public static boolean doesObserveEntity(Player player, LivingEntity entity) {
      Vec3 vector3d = player.m_20252_(1.0F).m_82541_();
      Vec3 vector3d1 = new Vec3(entity.m_20185_() - player.m_20185_(), entity.m_20188_() - player.m_20188_(), entity.m_20189_() - player.m_20189_());
      double d0 = vector3d1.m_82553_();
      vector3d1 = vector3d1.m_82541_();
      double d1 = vector3d.m_82526_(vector3d1);
      return d1 > 1.0 - 0.025 / d0 ? player.m_142582_(entity) : false;
   }

   public static int getSpellstoneCooldown(Player player) {
      return TransientPlayerData.get(player).getSpellstoneCooldown();
   }

   public static void setSpellstoneCooldown(Player playerIn, int value) {
      TransientPlayerData.get(playerIn).setSpellstoneCooldown(value);
   }

   public static void tickSpellstoneCooldown(Player player, int decrementedTicks) {
      TransientPlayerData data = TransientPlayerData.get(player);
      data.spellstoneCooldown = data.getSpellstoneCooldown() - decrementedTicks;
   }

   public static boolean hasSpellstoneCooldown(Player player) {
      return TransientPlayerData.get(player).getSpellstoneCooldown() > 0;
   }

   @OnlyIn(Dist.CLIENT)
   public static void lookAt(double px, double py, double pz, LocalPlayer me) {
      double dirx = me.m_20185_() - px;
      double diry = me.m_20186_() - py;
      double dirz = me.m_20189_() - pz;
      double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
      dirx /= len;
      diry /= len;
      dirz /= len;
      double pitch = Math.asin(diry);
      double yaw = Math.atan2(dirz, dirx);
      pitch = pitch * 180.0 / Math.PI;
      yaw = yaw * 180.0 / Math.PI;
      yaw += 90.0;
      me.m_146926_((float)pitch);
      me.m_146922_((float)yaw);
   }

   public static boolean validTeleport(Entity entity, double x_init, double y_init, double z_init, Level world, int checkAxis) {
      int x = (int)x_init;
      int y = (int)y_init;
      int z = (int)z_init;
      BlockState block = world.m_8055_(new BlockPos(x, y - 1, z));
      if (!world.m_46859_(new BlockPos(x, y - 1, z)) && block.m_60815_()) {
         for (int counter = 0; counter <= checkAxis; counter++) {
            if (!world.m_46859_(new BlockPos(x, y + counter - 1, z))
               && world.m_8055_(new BlockPos(x, y + counter - 1, z)).m_60815_()
               && world.m_46859_(new BlockPos(x, y + counter, z))
               && world.m_46859_(new BlockPos(x, y + counter + 1, z))) {
               world.m_5594_(null, entity.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(() -> new TargetPoint(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 128.0, entity.f_19853_.m_46472_())),
                     new PacketPortalParticles(entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_(), 72, 1.0, false)
                  );
               if (entity instanceof ServerPlayer player) {
                  player.m_6021_((double)x + 0.5, (double)(y + counter), (double)z + 0.5);
               } else {
                  ((LivingEntity)entity).m_6021_((double)x + 0.5, (double)(y + counter), (double)z + 0.5);
               }

               world.m_5594_(null, entity.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(() -> new TargetPoint(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 128.0, entity.f_19853_.m_46472_())),
                     new PacketRecallParticles(entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_(), 48, false)
                  );
               return true;
            }
         }
      } else {
         for (int counterx = 0; counterx <= checkAxis; counterx++) {
            if (!world.m_46859_(new BlockPos(x, y - counterx - 1, z))
               && world.m_8055_(new BlockPos(x, y - counterx - 1, z)).m_60815_()
               && world.m_46859_(new BlockPos(x, y - counterx, z))
               && world.m_46859_(new BlockPos(x, y - counterx + 1, z))) {
               world.m_5594_(null, entity.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(() -> new TargetPoint(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 128.0, entity.f_19853_.m_46472_())),
                     new PacketRecallParticles(entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_(), 48, false)
                  );
               if (entity instanceof ServerPlayer player) {
                  player.m_6021_((double)x + 0.5, (double)(y - counterx), (double)z + 0.5);
               } else {
                  ((LivingEntity)entity).m_6021_((double)x + 0.5, (double)(y - counterx), (double)z + 0.5);
               }

               world.m_5594_(null, entity.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(() -> new TargetPoint(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 128.0, entity.f_19853_.m_46472_())),
                     new PacketRecallParticles(entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_(), 48, false)
                  );
               return true;
            }
         }
      }

      return false;
   }

   public static boolean validTeleportRandomly(Entity entity, Level world, int radius) {
      int d = radius * 2;
      double x = entity.m_20185_() + (Math.random() - 0.5) * (double)d;
      double y = entity.m_20186_() + (Math.random() - 0.5) * (double)d;
      double z = entity.m_20189_() + (Math.random() - 0.5) * (double)d;
      return validTeleport(entity, x, y, z, world, radius);
   }

   public static LootPool constructLootPool(String poolName, float minRolls, float maxRolls, @Nullable Builder<?>... entries) {
      net.minecraft.world.level.storage.loot.LootPool.Builder poolBuilder = LootPool.m_79043_();
      poolBuilder.name(poolName);
      poolBuilder.m_165133_(UniformGenerator.m_165780_(minRolls, maxRolls));

      for (Builder<?> entry : entries) {
         if (entry != null) {
            poolBuilder.m_79076_(entry);
         }
      }

      return poolBuilder.m_79082_();
   }

   @Nullable
   public static net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder<?> createOptionalLootEntry(
      Item item, int weight, float minCount, float maxCount
   ) {
      return !OmniconfigHandler.isItemEnabled(item)
         ? null
         : LootItem.m_79579_(item).m_79707_(weight).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(minCount, maxCount)));
   }

   @Nullable
   public static net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder<?> createOptionalLootEntry(Item item, int weight) {
      return !OmniconfigHandler.isItemEnabled(item) ? null : LootItem.m_79579_(item).m_79707_(weight);
   }

   public static net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder<?> itemEntryBuilderED(
      Item item, int weight, float enchantLevelMin, float enchantLevelMax, float damageMin, float damageMax
   ) {
      net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder<?> builder = LootItem.m_79579_(item);
      builder.m_79707_(weight);
      builder.m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(damageMax, damageMin)));
      builder.m_79078_(EnchantWithLevelsFunction.m_165196_(UniformGenerator.m_165780_(enchantLevelMin, enchantLevelMax)).m_80499_());
      return builder;
   }

   public static List<ResourceLocation> getEarthenDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78742_);
      lootChestList.add(BuiltInLootTables.f_78759_);
      lootChestList.add(BuiltInLootTables.f_78745_);
      return lootChestList;
   }

   public static List<ResourceLocation> getWaterDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78691_);
      lootChestList.add(BuiltInLootTables.f_78690_);
      lootChestList.add(BuiltInLootTables.f_78695_);
      lootChestList.add(BuiltInLootTables.f_78692_);
      return lootChestList;
   }

   public static List<ResourceLocation> getLibraries() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78761_);
      lootChestList.add(BuiltInLootTables.f_78693_);
      return lootChestList;
   }

   public static List<ResourceLocation> getBastionChests() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78697_);
      lootChestList.add(BuiltInLootTables.f_78698_);
      lootChestList.add(BuiltInLootTables.f_78699_);
      lootChestList.add(BuiltInLootTables.f_78700_);
      return lootChestList;
   }

   public static List<ResourceLocation> getNetherDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78760_);
      lootChestList.add(BuiltInLootTables.f_78697_);
      lootChestList.add(BuiltInLootTables.f_78698_);
      lootChestList.add(BuiltInLootTables.f_78699_);
      lootChestList.add(BuiltInLootTables.f_78700_);
      lootChestList.add(BuiltInLootTables.f_78701_);
      return lootChestList;
   }

   public static List<ResourceLocation> getAirDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78753_);
      return lootChestList;
   }

   public static List<ResourceLocation> getEnderDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78741_);
      return lootChestList;
   }

   public static List<ResourceLocation> getMergedAir$EarthenDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78764_);
      lootChestList.add(BuiltInLootTables.f_78686_);
      return lootChestList;
   }

   public static List<ResourceLocation> getMergedEnder$EarthenDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78763_);
      lootChestList.add(BuiltInLootTables.f_78762_);
      return lootChestList;
   }

   public static List<ResourceLocation> getOverworldDungeons() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78742_);
      lootChestList.add(BuiltInLootTables.f_78759_);
      lootChestList.add(BuiltInLootTables.f_78762_);
      lootChestList.add(BuiltInLootTables.f_78763_);
      lootChestList.add(BuiltInLootTables.f_78764_);
      lootChestList.add(BuiltInLootTables.f_78686_);
      lootChestList.add(BuiltInLootTables.f_78688_);
      lootChestList.add(BuiltInLootTables.f_78689_);
      lootChestList.add(BuiltInLootTables.f_78690_);
      lootChestList.add(BuiltInLootTables.f_78691_);
      lootChestList.add(BuiltInLootTables.f_78694_);
      lootChestList.add(BuiltInLootTables.f_78696_);
      return lootChestList;
   }

   public static List<ResourceLocation> getVillageChests() {
      List<ResourceLocation> lootChestList = new ArrayList<>();
      lootChestList.add(BuiltInLootTables.f_78743_);
      lootChestList.add(BuiltInLootTables.f_78744_);
      lootChestList.add(BuiltInLootTables.f_78745_);
      lootChestList.add(BuiltInLootTables.f_78746_);
      lootChestList.add(BuiltInLootTables.f_78747_);
      lootChestList.add(BuiltInLootTables.f_78748_);
      lootChestList.add(BuiltInLootTables.f_78749_);
      lootChestList.add(BuiltInLootTables.f_78750_);
      lootChestList.add(BuiltInLootTables.f_78751_);
      lootChestList.add(BuiltInLootTables.f_78752_);
      lootChestList.add(BuiltInLootTables.f_78753_);
      lootChestList.add(BuiltInLootTables.f_78754_);
      lootChestList.add(BuiltInLootTables.f_78755_);
      lootChestList.add(BuiltInLootTables.f_78756_);
      lootChestList.add(BuiltInLootTables.f_78757_);
      lootChestList.add(BuiltInLootTables.f_78758_);
      return lootChestList;
   }

   public static Tag getPersistentTag(Player player, String tag, Tag expectedValue) {
      CompoundTag data = player.getPersistentData();
      CompoundTag persistent;
      if (!data.m_128441_("PlayerPersisted")) {
         data.m_128365_("PlayerPersisted", persistent = new CompoundTag());
      } else {
         persistent = data.m_128469_("PlayerPersisted");
      }

      return persistent.m_128441_(tag) ? persistent.m_128423_(tag) : expectedValue;
   }

   public static void removePersistentTag(Player player, String tag) {
      CompoundTag data = player.getPersistentData();
      CompoundTag persistent;
      if (!data.m_128441_("PlayerPersisted")) {
         data.m_128365_("PlayerPersisted", persistent = new CompoundTag());
      } else {
         persistent = data.m_128469_("PlayerPersisted");
      }

      if (persistent.m_128441_(tag)) {
         persistent.m_128473_(tag);
      }
   }

   public static void setPersistentTag(Player player, String tag, Tag value) {
      CompoundTag data = player.getPersistentData();
      CompoundTag persistent;
      if (!data.m_128441_("PlayerPersisted")) {
         data.m_128365_("PlayerPersisted", persistent = new CompoundTag());
      } else {
         persistent = data.m_128469_("PlayerPersisted");
      }

      persistent.m_128365_(tag, value);
   }

   public static void setPersistentBoolean(Player player, String tag, boolean value) {
      setPersistentTag(player, tag, ByteTag.m_128273_(value));
   }

   public static boolean getPersistentBoolean(Player player, String tag, boolean expectedValue) {
      Tag theTag = getPersistentTag(player, tag, ByteTag.m_128273_(expectedValue));
      return theTag instanceof ByteTag ? ((ByteTag)theTag).m_7063_() != 0 : expectedValue;
   }

   public static void setPersistentInteger(Player player, String tag, int value) {
      setPersistentTag(player, tag, IntTag.m_128679_(value));
   }

   public static int getPersistentInteger(Player player, String tag, int expectedValue) {
      Tag theTag = getPersistentTag(player, tag, IntTag.m_128679_(expectedValue));
      return theTag instanceof IntTag ? ((IntTag)theTag).m_7047_() : expectedValue;
   }

   public static boolean hasPersistentTag(Player player, String tag) {
      CompoundTag data = player.getPersistentData();
      CompoundTag persistent;
      if (!data.m_128441_("PlayerPersisted")) {
         data.m_128365_("PlayerPersisted", persistent = new CompoundTag());
      } else {
         persistent = data.m_128469_("PlayerPersisted");
      }

      return persistent.m_128441_(tag);
   }

   public static boolean hasAdvancement(@Nonnull ServerPlayer player, @Nonnull ResourceLocation location) {
      try {
         if (player.m_8960_().m_135996_(player.f_8924_.m_129889_().m_136041_(location)).m_8193_()) {
            return true;
         }
      } catch (NullPointerException var3) {
      }

      return false;
   }

   public static boolean doesAdvancementExist(@Nonnull ResourceLocation location) {
      return ServerLifecycleHooks.getCurrentServer().m_129889_().m_136041_(location) != null;
   }

   public static void grantAdvancement(@Nonnull ServerPlayer player, @Nonnull ResourceLocation location) {
      Advancement adv = player.f_8924_.m_129889_().m_136041_(location);

      for (String criterion : player.m_8960_().m_135996_(adv).m_8219_()) {
         player.m_8960_().m_135988_(adv, criterion);
      }
   }

   public static void revokeAdvancement(@Nonnull ServerPlayer player, @Nonnull ResourceLocation location) {
      Advancement adv = player.f_8924_.m_129889_().m_136041_(location);

      for (String criterion : player.m_8960_().m_135996_(adv).m_8220_()) {
         player.m_8960_().m_135998_(adv, criterion);
      }
   }

   public static String generateRandomWorldNumber() {
      String number = "";

      while (number.length() < 4) {
         number = number.concat(RANDOM.nextInt(10) + "");
      }

      number = number.concat("-");

      while (number.length() < 7) {
         number = number.concat(ALPHABET[RANDOM.nextInt(ALPHABET.length)] + "");
      }

      while (number.length() < 9) {
         number = number.concat(RANDOM.nextInt(10) + "");
      }

      return number;
   }

   public static Player getPlayerByName(Level world, String name) {
      Player player = null;

      for (Player checkedPlayer : world.m_6907_()) {
         if (checkedPlayer.m_5446_().getString().equals(name)) {
            player = checkedPlayer;
         }
      }

      return player;
   }

   @OnlyIn(Dist.CLIENT)
   public static void addPotionTooltip(List<MobEffectInstance> list, ItemStack itemIn, List<Component> lores, float durationFactor) {
      List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
      if (list.isEmpty()) {
         lores.add(Component.m_237115_("effect.none").m_130940_(ChatFormatting.GRAY));
      } else {
         for (MobEffectInstance effectinstance : list) {
            MutableComponent iformattabletextcomponent = Component.m_237115_(effectinstance.m_19576_());
            MobEffect effect = effectinstance.m_19544_();
            Map<Attribute, AttributeModifier> map = effect.m_19485_();
            if (!map.isEmpty()) {
               for (Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(
                     attributemodifier.m_22214_(), effect.m_7048_(effectinstance.m_19564_(), attributemodifier), attributemodifier.m_22217_()
                  );
                  list1.add(new Pair(entry.getKey(), attributemodifier1));
               }
            }

            if (effectinstance.m_19564_() > 0) {
               iformattabletextcomponent.m_130946_(" ").m_7220_(Component.m_237115_("potion.potency." + effectinstance.m_19564_()));
            }

            if (effectinstance.m_19557_() > 20) {
               iformattabletextcomponent.m_130946_(" (").m_130946_(MobEffectUtil.m_19581_(effectinstance, durationFactor)).m_130946_(")");
            }

            lores.add(iformattabletextcomponent.m_130940_(effect.m_19483_().m_19497_()));
         }
      }

      if (!list1.isEmpty()) {
         lores.add(CommonComponents.f_237098_);
         lores.add(Component.m_237115_("potion.whenDrank").m_130940_(ChatFormatting.DARK_PURPLE));

         for (Pair<Attribute, AttributeModifier> pair : list1) {
            AttributeModifier attributemodifier2 = (AttributeModifier)pair.getSecond();
            double d0 = attributemodifier2.m_22218_();
            double d1;
            if (attributemodifier2.m_22217_() != Operation.MULTIPLY_BASE && attributemodifier2.m_22217_() != Operation.MULTIPLY_TOTAL) {
               d1 = attributemodifier2.m_22218_();
            } else {
               d1 = attributemodifier2.m_22218_() * 100.0;
            }

            if (d0 > 0.0) {
               lores.add(
                  Component.m_237110_(
                        "attribute.modifier.plus." + attributemodifier2.m_22217_().m_22235_(),
                        new Object[]{ItemStack.f_41584_.format(d1), Component.m_237115_(((Attribute)pair.getFirst()).m_22087_())}
                     )
                     .m_130940_(ChatFormatting.BLUE)
               );
            } else if (d0 < 0.0) {
               d1 *= -1.0;
               lores.add(
                  Component.m_237110_(
                        "attribute.modifier.take." + attributemodifier2.m_22217_().m_22235_(),
                        new Object[]{ItemStack.f_41584_.format(d1), Component.m_237115_(((Attribute)pair.getFirst()).m_22087_())}
                     )
                     .m_130940_(ChatFormatting.DARK_RED)
               );
            }
         }
      }
   }

   public static boolean canPickStack(Player player, ItemStack stack) {
      if (player.m_150109_().m_36062_() >= 0) {
         return true;
      } else {
         List<ItemStack> allInventories = new ArrayList<>();
         allInventories.addAll(player.m_150109_().f_35974_);
         allInventories.addAll(player.m_150109_().f_35976_);

         for (ItemStack invStack : allInventories) {
            if (canMergeStacks(invStack, stack, player.m_150109_().m_6893_())) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean canMergeStacks(ItemStack stack1, ItemStack stack2, int invStackLimit) {
      return !stack1.m_41619_()
         && stackEqualExact(stack1, stack2)
         && stack1.m_41753_()
         && stack1.m_41613_() < stack1.m_41741_()
         && stack1.m_41613_() < invStackLimit;
   }

   public static boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
      return stack1.m_41720_() == stack2.m_41720_() && ItemStack.m_41658_(stack1, stack2);
   }

   public static boolean isPermanentlyDead(Player player) {
      return EnigmaticItems.SOUL_CRYSTAL.getLostCrystals(player) >= 10 && OmniconfigHandler.maxSoulCrystalLoss.getValue() >= 10;
   }

   @OnlyIn(Dist.CLIENT)
   public static float getParticleMultiplier() {
      if (Minecraft.m_91087_().f_91066_.m_231929_().m_231551_() == ParticleStatus.MINIMAL) {
         return 0.35F;
      } else {
         return Minecraft.m_91087_().f_91066_.m_231929_().m_231551_() == ParticleStatus.DECREASED ? 0.65F : 1.0F;
      }
   }

   public static boolean isInBeaconRange(Player player) {
      if (player.f_19853_.f_46443_) {
         return false;
      } else {
         List<BeaconBlockEntity> list = new ArrayList<>();
         boolean inRange = false;
         ServerLevel level = (ServerLevel)player.f_19853_;
         ServerChunkCache cache = (ServerChunkCache)player.f_19853_.m_7726_();
         ObjectIterator var5 = cache.f_8325_.f_140130_.values().iterator();

         while (var5.hasNext()) {
            ChunkHolder holder = (ChunkHolder)var5.next();
            ChunkPos pos = holder.m_140092_();
            if (pos != null) {
               LevelChunk chunk = holder.m_140085_();
               if (chunk != null) {
                  for (BlockEntity tile : chunk.m_62954_().values()) {
                     if (tile instanceof BeaconBlockEntity) {
                        list.add((BeaconBlockEntity)tile);
                     }
                  }
               }
            }
         }

         if (list.size() > 0) {
            Iterator var12 = list.iterator();

            while (true) {
               BeaconBlockEntity beacon;
               while (true) {
                  if (!var12.hasNext()) {
                     return inRange;
                  }

                  beacon = (BeaconBlockEntity)var12.next();
                  if (beacon.f_58650_ > 0) {
                     try {
                        if (beacon.m_58702_().isEmpty()) {
                           continue;
                        }
                     } catch (Exception var11) {
                        var11.printStackTrace();
                     }
                     break;
                  }
               }

               int range = (beacon.f_58650_ + 1) * 10;
               double distance = Math.sqrt(beacon.m_58899_().m_203198_(player.m_20185_(), (double)beacon.m_58899_().m_123342_(), player.m_20189_()));
               if (distance <= (double)range) {
                  inRange = true;
               }
            }
         } else {
            return inRange;
         }
      }
   }

   public static boolean hasItem(Player player, Item item) {
      return player.m_150109_().m_36063_(new ItemStack(item));
   }

   public static boolean hasExactStack(Player player, ItemStack stack) {
      for (List<ItemStack> list : player.m_150109_().f_35979_) {
         for (ItemStack inventoryStack : list) {
            if (inventoryStack == stack) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean ifDroplistContainsItem(Collection<ItemEntity> drops, Item item) {
      for (ItemEntity drop : drops) {
         if (drop.m_32055_() != null && drop.m_32055_().m_41720_() == item) {
            return true;
         }
      }

      return false;
   }

   public static boolean isAffectedBySoulLoss(Player player, boolean hadRing) {
      int dropMode = OmniconfigHandler.soulCrystalsMode.getValue();
      boolean keepInventory = player.f_19853_.m_46469_().m_46207_(GameRules.f_46133_);
      if (dropMode == 0) {
         return hadRing;
      } else {
         return dropMode != 1 ? dropMode == 2 : hadRing || keepInventory;
      }
   }

   public static boolean canDropSoulCrystal(Player player, boolean hadRing) {
      if (isAffectedBySoulLoss(player, hadRing)) {
         int maxCrystalLoss = OmniconfigHandler.maxSoulCrystalLoss.getValue();
         return EnigmaticItems.SOUL_CRYSTAL.getLostCrystals(player) < maxCrystalLoss;
      } else {
         return false;
      }
   }

   public static ServerLevel getWorld(ResourceKey<Level> key) {
      return ServerLifecycleHooks.getCurrentServer().m_129880_(key);
   }

   public static ServerLevel getOverworld() {
      return getWorld(EnigmaticLegacy.PROXY.getOverworldKey());
   }

   public static ServerLevel getNether() {
      return getWorld(EnigmaticLegacy.PROXY.getNetherKey());
   }

   public static ServerLevel getEnd() {
      return getWorld(EnigmaticLegacy.PROXY.getEndKey());
   }

   public static void sendToDimension(ServerPlayer player, ResourceKey<Level> dimension, ITeleporter teleporter) {
      if (!player.f_19853_.m_46472_().equals(dimension)) {
         ServerLevel world = getWorld(dimension);
         if (world != null) {
            player.changeDimension(world, teleporter);
         }
      }
   }

   public static void sendToDimension(ServerPlayer player, ResourceKey<Level> dimension) {
      sendToDimension(player, dimension, new RealSmoothTeleporter());
   }

   public static ServerLevel backToSpawn(ServerPlayer serverPlayer) {
      ResourceKey<Level> respawnDimension = AdvancedSpawnLocationHelper.getPlayerRespawnDimension(serverPlayer);
      ServerLevel respawnWorld = getWorld(respawnDimension);
      serverPlayer.f_19853_.m_5594_(null, serverPlayer.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
      EnigmaticLegacy.packetInstance
         .send(
            PacketDistributor.NEAR
               .with(() -> new TargetPoint(serverPlayer.m_20185_(), serverPlayer.m_20186_(), serverPlayer.m_20189_(), 128.0, serverPlayer.f_19853_.m_46472_())),
            new PacketPortalParticles(
               serverPlayer.m_20185_(), serverPlayer.m_20186_() + (double)(serverPlayer.m_20206_() / 2.0F), serverPlayer.m_20189_(), 100, 1.25, false
            )
         );
      Optional<Vec3> vec = AdvancedSpawnLocationHelper.getValidSpawn(respawnWorld, serverPlayer);
      ServerLevel destinationWorld = vec.isPresent() ? respawnWorld : serverPlayer.f_8924_.m_129783_();
      if (!serverPlayer.m_9236_().equals(destinationWorld)) {
         serverPlayer.changeDimension(destinationWorld, new RealSmoothTeleporter());
      }

      Optional<Vec3> vec2;
      if (!respawnWorld.equals(destinationWorld)) {
         vec2 = AdvancedSpawnLocationHelper.getValidSpawn(destinationWorld, serverPlayer);
      } else {
         vec2 = Optional.empty();
      }

      if (vec.isPresent()) {
         Vec3 trueVec = vec.get();
         serverPlayer.m_6021_(trueVec.f_82479_, trueVec.f_82480_, trueVec.f_82481_);
      } else if (vec2.isPresent()) {
         Vec3 trueVec = vec2.get();
         serverPlayer.m_6021_(trueVec.f_82479_, trueVec.f_82480_, trueVec.f_82481_);
      } else {
         AdvancedSpawnLocationHelper.fuckBackToSpawn(serverPlayer.m_9236_(), serverPlayer);
      }

      serverPlayer.f_19853_.m_5594_(null, serverPlayer.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
      EnigmaticLegacy.packetInstance
         .send(
            PacketDistributor.NEAR
               .with(() -> new TargetPoint(serverPlayer.m_20185_(), serverPlayer.m_20186_(), serverPlayer.m_20189_(), 128.0, serverPlayer.f_19853_.m_46472_())),
            new PacketRecallParticles(
               serverPlayer.m_20185_(), serverPlayer.m_20186_() + (double)(serverPlayer.m_20206_() / 2.0F), serverPlayer.m_20189_(), 48, false
            )
         );
      return destinationWorld;
   }

   public static DimensionalPosition getRespawnPoint(ServerPlayer serverPlayer) {
      ResourceKey<Level> respawnDimension = AdvancedSpawnLocationHelper.getPlayerRespawnDimension(serverPlayer);
      ServerLevel respawnWorld = getWorld(respawnDimension);
      Optional<Vec3> currentDimensionRespawnCoords = AdvancedSpawnLocationHelper.getValidSpawn(respawnWorld, serverPlayer);
      ServerLevel destinationWorld = currentDimensionRespawnCoords.isPresent() ? respawnWorld : serverPlayer.f_8924_.m_129783_();
      Optional<Vec3> destinationDimensionRespawnCoords;
      if (!respawnWorld.equals(destinationWorld)) {
         destinationDimensionRespawnCoords = AdvancedSpawnLocationHelper.getValidSpawn(destinationWorld, serverPlayer);
      } else {
         destinationDimensionRespawnCoords = Optional.empty();
      }

      Vec3 trueVec;
      if (currentDimensionRespawnCoords.isPresent()) {
         trueVec = currentDimensionRespawnCoords.get();
      } else if (destinationDimensionRespawnCoords.isPresent()) {
         trueVec = destinationDimensionRespawnCoords.get();
      } else {
         trueVec = new Vec3(
            (double)destinationWorld.m_220360_().m_123341_() + 0.5,
            (double)destinationWorld.m_220360_().m_123342_() + 0.5,
            (double)destinationWorld.m_220360_().m_123343_() + 0.5
         );

         while (!destinationWorld.m_8055_(new BlockPos(trueVec)).m_60795_() && trueVec.f_82480_ < 255.0) {
            trueVec = trueVec.m_82520_(0.0, 1.0, 0.0);
         }
      }

      return new DimensionalPosition(trueVec.f_82479_, trueVec.f_82480_, trueVec.f_82481_, destinationWorld);
   }

   public static void removeAttributeMap(Player player, Multimap<Attribute, AttributeModifier> attributes) {
      AttributeMap map = player.m_21204_();
      map.m_22161_(attributes);
   }

   public static void applyAttributeMap(Player player, Multimap<Attribute, AttributeModifier> attributes) {
      AttributeMap map = player.m_21204_();
      map.m_22178_(attributes);
   }

   public static boolean isTheCursedOne(Player player) {
      return hasCurio(player, EnigmaticItems.CURSED_RING);
   }

   public static boolean isTheBlessedOne(Player player) {
      return EnigmaticLegacy.SOUL_OF_THE_ARCHITECT.equals(player.m_20148_()) ? true : DevotedBelieversHandler.isDevotedBeliever(player);
   }

   public static boolean hasArchitectsFavor(Player player) {
      return isTheBlessedOne(player) && hasCurio(player, EnigmaticItems.COSMIC_SCROLL);
   }

   public static boolean isTheWorthyOne(Player player) {
      if (isTheCursedOne(player)) {
         IPlaytimeCounter counter = IPlaytimeCounter.get(player);
         long timeWithRing = counter.getTimeWithCurses();
         long timeWithoutRing = counter.getTimeWithoutCurses();
         if (timeWithRing <= 0L) {
            return false;
         } else {
            return timeWithoutRing <= 0L ? true : timeWithRing / timeWithoutRing >= 199L;
         }
      } else {
         return false;
      }
   }

   public static String getSufferingTime(@Nullable Player player) {
      if (player == null) {
         return "0%";
      } else {
         IPlaytimeCounter counter = IPlaytimeCounter.get(player);
         long timeWithRing = counter.getTimeWithCurses();
         long timeWithoutRing = counter.getTimeWithoutCurses();
         if (timeWithRing <= 0L) {
            return "0%";
         } else if (timeWithoutRing <= 0L) {
            return "100%";
         } else {
            if (timeWithRing > 100000L || timeWithoutRing > 100000L) {
               timeWithRing /= 100L;
               timeWithoutRing /= 100L;
               if (timeWithRing <= 0L) {
                  return "0%";
               }

               if (timeWithoutRing <= 0L) {
                  return "100%";
               }
            }

            double total = (double)(timeWithRing + timeWithoutRing);
            double ringPercent = (double)timeWithRing / total * 100.0;
            ringPercent = (double)Math.round(ringPercent * 10.0) / 10.0;
            String text = "";
            if (ringPercent - (double)Math.round(ringPercent) == 0.0) {
               text = text + (int)ringPercent + "%";
            } else {
               text = text + ringPercent + "%";
            }

            if ("99.5%".equals(text) && !isTheWorthyOne(player)) {
               text = "99.4%";
            }

            return text;
         }
      }
   }

   public static float getMissingHealthPool(Player player) {
      return (player.m_21233_() - Math.min(player.m_21223_(), player.m_21233_())) / player.m_21233_();
   }

   public static int getCurseAmount(ItemStack stack) {
      Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(stack);
      int totalCurses = 0;

      for (Enchantment enchantment : enchantments.keySet()) {
         if (enchantment.m_6589_() && enchantments.get(enchantment) > 0) {
            totalCurses++;
         }
      }

      if (stack.m_41720_() == EnigmaticItems.CURSED_RING) {
         totalCurses += 7;
      }

      return totalCurses;
   }

   public static int getCurseAmount(Player player) {
      int count = 0;
      boolean ringCounted = false;

      for (ItemStack theStack : getFullEquipment(player)) {
         if (theStack != null && (theStack.m_41720_() != EnigmaticItems.CURSED_RING || !ringCounted)) {
            count += getCurseAmount(theStack);
            if (theStack.m_41720_() == EnigmaticItems.CURSED_RING) {
               ringCounted = true;
            }
         }
      }

      return count;
   }

   public static List<ItemStack> getFullEquipment(Player player) {
      List<ItemStack> equipmentStacks = Lists.newArrayList();
      equipmentStacks.add(player.m_21205_());
      equipmentStacks.add(player.m_21206_());
      equipmentStacks.addAll(player.m_150109_().f_35975_);
      if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
         ICuriosItemHandler handler = (ICuriosItemHandler)CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
         Map<String, ICurioStacksHandler> curios = handler.getCurios();

         for (Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
               ItemStack stack = stackHandler.getStackInSlot(i);
               equipmentStacks.add(stack);
            }
         }
      }

      return equipmentStacks;
   }

   public static double sinFunction(double lowerBound, double upperBound, double value) {
      double range = upperBound - lowerBound;
      double coef = value / range;
      coef *= 90.0;
      coef = Math.toRadians(coef);
      double func = Math.pow(Math.sin(coef), -1.0);
      return Math.pow(Math.sin(coef), -1.0);
   }

   public static double parabolicFunction(double lowerBound, double upperBound, double value) {
      double range = upperBound - lowerBound;
      double coef = value / range;
      return Math.pow(coef, 2.0);
   }

   public static double flippedParabolicFunction(double lowerBound, double upperBound, double value) {
      double range = upperBound - lowerBound;
      double coef = value / range;
      double func = Math.pow(coef - 1.0, 2.0);
      return 1.0 - func;
   }

   public static boolean hasAnyArmor(LivingEntity entity) {
      int armorAmount = 0;

      for (ItemStack stack : entity.m_6168_()) {
         if (!stack.m_41619_() && !GolemHeart.EXCLUDED_ARMOR.stream().anyMatch(stack::m_150930_)) {
            armorAmount++;
         }
      }

      return armorAmount != 0;
   }

   public static boolean areWeDedicatedServer() {
      return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
   }

   public static boolean areWeRemoteServer(Player player) {
      return areWeDedicatedServer() ? true : player.m_20194_() != null && !player.m_20194_().m_7779_(player.m_36316_());
   }

   public static void executeOnServer(Consumer<MinecraftServer> action) {
      if (ServerLifecycleHooks.getCurrentServer() != null) {
         action.accept(ServerLifecycleHooks.getCurrentServer());
      }
   }

   public static List<AnnotationData> retainAnnotations(String modid, Class<?> annotationClass) {
      ModFileScanData modFileInfo = ModList.get().getModFileById(modid).getFile().getScanResult();
      List<AnnotationData> list = new ArrayList<>();

      for (AnnotationData annotation : modFileInfo.getAnnotations()) {
         if (annotation.annotationType().getClassName().equals(annotationClass.getName())) {
            list.add(annotation);
         }
      }

      return list;
   }

   public static List<AnnotationData> retainConfigurableItemAnnotations(String modid) {
      return retainAnnotations(modid, ConfigurableItem.class);
   }

   public static List<AnnotationData> retainConfigHolderAnnotations(String modid) {
      return retainAnnotations(modid, SubscribeConfig.class);
   }

   public static void dispatchWrapperToHolders(String modid, OmniconfigWrapper wrapper) {
      for (AnnotationData annotationData : retainConfigHolderAnnotations(modid)) {
         try {
            Class<?> retainerClass = Class.forName(annotationData.clazz().getClassName());
            String methodName = annotationData.memberName().split("\\(")[0];
            boolean receiveClient;
            if (annotationData.annotationData().get("receiveClient") != null) {
               receiveClient = (Boolean)annotationData.annotationData().get("receiveClient");
            } else {
               receiveClient = false;
            }

            Method method = retainerClass.getDeclaredMethod(methodName, OmniconfigWrapper.class);
            if (wrapper.config.getSidedType() != Configuration.SidedConfigType.CLIENT || receiveClient) {
               method.invoke(null, wrapper);
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }
   }

   public static Multimap<String, Field> retainAccessibilityGeneratorMap(String modid) {
      Multimap<String, Field> accessibilityGeneratorMap = HashMultimap.create();

      for (AnnotationData annotationData : retainConfigurableItemAnnotations(modid)) {
         try {
            Class<?> retainerClass = Class.forName(annotationData.clazz().getClassName());
            String itemName = (String)annotationData.annotationData().get("value");
            String fieldName = annotationData.memberName();
            Field field = retainerClass.getDeclaredField(fieldName);
            if (itemName != null && !itemName.isEmpty()) {
               accessibilityGeneratorMap.put(itemName, field);
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      return accessibilityGeneratorMap;
   }

   public static boolean hasAntiInsectAcknowledgement(Player player) {
      for (ItemStack held : Lists.newArrayList(new ItemStack[]{player.m_21205_(), player.m_21206_()})) {
         if (held != null && held.m_41720_() instanceof TheAcknowledgment && EnchantmentHelper.m_44843_(Enchantments.f_44979_, held) > 0) {
            return true;
         }
      }

      return false;
   }

   public static boolean isStaringAt(Player player, LivingEntity living) {
      Vec3 vector3d = player.m_20252_(1.0F).m_82541_();
      Vec3 vector3d1 = new Vec3(living.m_20185_() - player.m_20185_(), living.m_20188_() - player.m_20188_(), living.m_20189_() - player.m_20189_());
      double d0 = vector3d1.m_82553_();
      vector3d1 = vector3d1.m_82541_();
      double d1 = vector3d.m_82526_(vector3d1);
      return d1 > 1.0 - 0.025 / d0 ? player.m_142582_(living) : false;
   }

   public static double getRandomNegative() {
      return (Math.random() - 0.5) * 2.0;
   }

   public static String minimizeNumber(double num) {
      int intg = (int)num;
      return num - (double)intg == 0.0 ? intg + "" : num + "";
   }

   public static Optional<Tuple<UUID, BlockPos>> updateSoulCompass(ServerPlayer player) {
      Optional<Tuple<UUID, BlockPos>> optional = SoulArchive.getInstance().findNearest(player.f_19853_, player.m_20183_());
      boolean noValid = optional.isEmpty();
      BlockPos pos = noValid ? BlockPos.f_121853_ : (BlockPos)optional.get().m_14419_();
      EnigmaticLegacy.packetInstance
         .send(PacketDistributor.PLAYER.with(() -> player), new PacketUpdateCompass(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), noValid));
      EnigmaticEventHandler.LAST_SOUL_COMPASS_UPDATE.put(player, player.f_19797_);
      return optional;
   }

   public static ItemStack mergeEnchantments(ItemStack input, ItemStack mergeFrom, boolean overmerge, boolean onlyTreasure) {
      ItemStack returnedStack = input.m_41777_();
      Map<Enchantment, Integer> inputEnchants = EnchantmentHelper.m_44831_(returnedStack);
      Map<Enchantment, Integer> mergedEnchants = EnchantmentHelper.m_44831_(mergeFrom);

      for (Enchantment mergedEnchant : mergedEnchants.keySet()) {
         if (mergedEnchant != null) {
            int inputEnchantLevel = inputEnchants.getOrDefault(mergedEnchant, 0);
            int mergedEnchantLevel = mergedEnchants.get(mergedEnchant);
            if (!overmerge) {
               mergedEnchantLevel = inputEnchantLevel == mergedEnchantLevel
                  ? (mergedEnchantLevel + 1 > mergedEnchant.m_6586_() ? mergedEnchant.m_6586_() : mergedEnchantLevel + 1)
                  : Math.max(mergedEnchantLevel, inputEnchantLevel);
            } else {
               mergedEnchantLevel = inputEnchantLevel > 0
                  ? Math.max(mergedEnchantLevel, inputEnchantLevel) + 1
                  : Math.max(mergedEnchantLevel, inputEnchantLevel);
               mergedEnchantLevel = Math.min(mergedEnchantLevel, 10);
            }

            boolean compatible = mergedEnchant.m_6081_(input);
            if (input.m_41720_() instanceof EnchantedBookItem) {
               compatible = true;
            }

            for (Enchantment originalEnchant : inputEnchants.keySet()) {
               if (originalEnchant != mergedEnchant && !mergedEnchant.m_44695_(originalEnchant)) {
                  compatible = false;
               }
            }

            if (compatible && (!onlyTreasure || mergedEnchant.m_6591_() || mergedEnchant.m_6589_())) {
               inputEnchants.put(mergedEnchant, mergedEnchantLevel);
            }
         }
      }

      EnchantmentHelper.m_44865_(inputEnchants, returnedStack);
      return returnedStack;
   }

   @OnlyIn(Dist.CLIENT)
   public static int greatestWidth(Font font, String[] lines) {
      return Arrays.stream(lines).mapToInt(font::m_92895_).reduce((num1, num2) -> num1 > num2 ? num1 : num2).getAsInt();
   }

   public static boolean hasEnigmaticElytra(LivingEntity living) {
      return getEnigmaticElytra(living) != null;
   }

   @Nullable
   public static ItemStack getEnigmaticElytra(LivingEntity living) {
      ItemStack stack = living.m_6844_(EquipmentSlot.CHEST);
      return stack.m_150930_(EnigmaticItems.ENIGMATIC_ELYTRA) ? stack : getCurioStack(living, EnigmaticItems.ENIGMATIC_ELYTRA);
   }

   public static ItemStack maybeApplyEternalBinding(ItemStack stack) {
      if (Math.random() < 0.5 && EnchantmentHelper.m_44843_(Enchantments.f_44975_, stack) > 0) {
         Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(stack);
         map.remove(Enchantments.f_44963_);
         int level = map.remove(Enchantments.f_44975_);
         map.put(EnigmaticEnchantments.ETERNAL_BINDING, level);
         EnchantmentHelper.m_44865_(map, stack);
      }

      return stack;
   }

   public static boolean canUnequipBoundRelics(Player player) {
      return player.m_7500_() || EnigmaticLegacy.SOUL_OF_THE_ARCHITECT.equals(player.m_20148_());
   }

   public static void onDamageSourceBlocking(LivingEntity blocker, ItemStack useItem, DamageSource source, CallbackInfoReturnable<Boolean> info) {
      if (blocker instanceof Player player && useItem != null) {
         boolean blocking = ((IProperShieldUser)blocker).isActuallyReallyBlocking();
         if (blocking && useItem.m_41720_() instanceof InfernalShield) {
            boolean piercingArrow = false;
            if (source.m_7640_() instanceof AbstractArrow abstractarrow && abstractarrow.m_36796_() > 0) {
               piercingArrow = true;
            }

            piercingArrow = false;
            if (!source.m_19376_() && ((IProperShieldUser)blocker).isActuallyReallyBlocking() && !piercingArrow) {
               Vec3 sourcePos = source.m_7270_();
               if (sourcePos != null) {
                  Vec3 lookVec = blocker.m_20252_(1.0F);
                  Vec3 sourceToSelf = sourcePos.m_82505_(blocker.m_20182_()).m_82541_();
                  sourceToSelf = new Vec3(sourceToSelf.f_82479_, 0.0, sourceToSelf.f_82481_);
                  if (sourceToSelf.m_82526_(lookVec) < 0.0) {
                     info.setReturnValue(true);
                     int strength = -1;
                     if (player.m_21023_(EnigmaticEffects.BLAZING_STRENGTH)) {
                        MobEffectInstance effectInstance = player.m_21124_(EnigmaticEffects.BLAZING_STRENGTH);
                        strength = effectInstance.m_19564_();
                        player.m_21195_(EnigmaticEffects.BLAZING_STRENGTH);
                        strength = strength > 2 ? 2 : strength;
                     }

                     player.m_7292_(new MobEffectInstance(EnigmaticEffects.BLAZING_STRENGTH, 1200, strength + 1, true, true));
                     if (source.m_7640_() instanceof LivingEntity living && living.m_6084_() && !living.m_5825_() && !(living instanceof Guardian)) {
                        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
                        if (Arrays.stream(stacktrace).filter(element -> SuperpositionHandler.class.getName().equals(element.getClassName())).count() < 2L) {
                           living.f_19802_ = 0;
                           living.m_6469_(new EntityDamageSource(DamageSource.f_19307_.f_19326_, player), 4.0F);
                           living.m_20254_(4);
                           EnigmaticEventHandler.KNOCKBACK_THAT_BASTARD.remove(living);
                        }
                     }

                     return;
                  }
               }
            }

            info.setReturnValue(false);
            return;
         }
      }
   }

   public static <K, V extends Comparable<? super V>> void sortByKey(Map<K, V> map) {
      List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
      list.sort(Entry.comparingByKey());
      map.clear();

      for (Entry<K, V> entry : list) {
         map.put(entry.getKey(), entry.getValue());
      }
   }

   private static boolean contains(char[] array, char ch) {
      for (char ach : array) {
         if (ach == ch) {
            return true;
         }
      }

      return false;
   }

   public static String obscureString(String string) {
      char[] oldArray = string.toCharArray();
      char[] newArray = new char[oldArray.length];
      boolean code = false;

      for (int i = 0; i < oldArray.length; i++) {
         char ch = oldArray[i];
         newArray[i] = ch;
         if (ch == 167) {
            code = true;
         } else if (code) {
            code = false;
         } else if (ch != ' ') {
            char[] replacements = null;
            if (contains(UPPERCASE_LETTERS, ch)) {
               replacements = UPPERCASE_LETTERS;
            } else if (contains(LOWERCASE_LETTERS, ch)) {
               replacements = LOWERCASE_LETTERS;
            } else if (contains(NUMBERS, ch)) {
               replacements = NUMBERS;
            } else {
               replacements = SPECIAL_SYMBOLS;
            }

            ch = replacements[RANDOM.nextInt(replacements.length)];
            newArray[i] = ch;
         }
      }

      return new String(newArray);
   }

   @OnlyIn(Dist.CLIENT)
   public static void obscureTooltip(List<Component> tooltip) {
      tooltip.replaceAll(component -> Component.m_237113_(obscureString(component.getString())).m_130948_(component.m_7383_()));
   }

   @OnlyIn(Dist.CLIENT)
   public static String[] wrapString(String string, Font font, int width) {
      List<FormattedText> list = font.m_92865_().m_92432_(string, width, Style.f_131099_);
      String[] lines = new String[list.size()];

      for (int i = 0; i < lines.length; i++) {
         FormattedText text = list.get(i);
         lines[i] = text.getString();
      }

      return lines;
   }

   @SafeVarargs
   public static <T> T getRandomElement(List<T> list, T... excluding) {
      List<T> filtered = new ArrayList<>(list);
      Arrays.<Object>stream(excluding).forEach(filtered::remove);
      if (filtered.size() <= 0) {
         throw new IllegalArgumentException("List has no valid elements to choose");
      } else {
         return filtered.size() == 1 ? filtered.get(0) : filtered.get(RANDOM.nextInt(filtered.size()));
      }
   }

   public static String getMD5Hash(String string) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         md.update(string.getBytes());
         return bytesToHex(md.digest()).toUpperCase();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public static String bytesToHex(byte[] bytes) {
      char[] hexArray = "0123456789ABCDEF".toCharArray();
      char[] hexChars = new char[bytes.length * 2];

      for (int j = 0; j < bytes.length; j++) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 15];
      }

      return new String(hexChars);
   }

   @OnlyIn(Dist.CLIENT)
   public static void renderInsigniaNameplate(
      Entity entity, Component name, PoseStack stack, MultiBufferSource buffer, int packedLight, EntityRenderDispatcher entityRenderDispatcher, Font font
   ) {
      double d0 = entityRenderDispatcher.m_114471_(entity);
      if (ForgeHooksClient.isNameplateInRenderDistance(entity, d0)) {
         boolean render = font.m_92852_(name) > 0;
         boolean override = false;
         if (!render && entity != Minecraft.m_91087_().f_91074_ && EnigmaticItems.INSIGNIA.canSeeTrueName(Minecraft.m_91087_().f_91074_)) {
            name = entity.m_5446_();
            override = true;
            render = true;
         }

         boolean discrete = !entity.m_20163_();
         float f = entity.m_20206_() + 0.5F;
         int i = 0;
         stack.m_85836_();
         stack.m_85837_(0.0, (double)f, 0.0);
         stack.m_85845_(entityRenderDispatcher.m_114470_());
         stack.m_85841_(-0.025F, -0.025F, 0.025F);
         Matrix4f matrix4f = stack.m_85850_().m_85861_();
         float f1 = Minecraft.m_91087_().f_91066_.m_92141_(0.25F);
         int j = (int)(f1 * 255.0F) << 24;
         float f2 = (float)(-font.m_92852_(name) / 2);
         if (render) {
            font.m_92841_(name, f2, (float)i, 553648127, false, matrix4f, buffer, discrete, j, packedLight);
            if (discrete) {
               font.m_92841_(name, f2, (float)i, -1, false, matrix4f, buffer, false, 0, packedLight);
            }
         }

         if (!override && entity != Minecraft.m_91087_().f_91074_ && EnigmaticItems.INSIGNIA.canSeeTrueName(Minecraft.m_91087_().f_91074_)) {
            stack.m_85836_();
            Component var20 = Component.m_237113_("(" + entity.m_5446_().getString() + ")");
            f2 = (float)(-font.m_92852_(var20) / 2);
            float scale = 0.4F;
            int offset = (int)(-10.0F * (1.0F / scale));
            stack.m_85841_(scale, scale, scale);
            matrix4f = stack.m_85850_().m_85861_();
            font.m_92841_(var20, f2, (float)(i - offset), 553648127, false, matrix4f, buffer, discrete, j, packedLight);
            if (discrete) {
               font.m_92841_(var20, f2, (float)(i - offset), -1, false, matrix4f, buffer, false, 0, packedLight);
            }

            stack.m_85849_();
         }

         stack.m_85849_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static File getSaveFolder(LevelSummary summary) {
      LevelStorageSource levels = Minecraft.m_91087_().m_91392_();
      return new File(levels.m_78257_().toFile(), summary.m_78358_());
   }

   @OnlyIn(Dist.CLIENT)
   public static Component getAltInfo(LevelSummary summary) {
      Component info = summary.m_78376_();
      File world = getSaveFolder(summary);
      boolean fractured = isWorldFractured(world);
      if (summary.m_78367_() != GameType.SURVIVAL && !fractured) {
         return info;
      } else {
         String key = "gameMode.enigmaticlegacy.";
         if (fractured) {
            key = key + "fractured";
         } else {
            if (!isWorldCursed(world)) {
               return info;
            }

            key = key + "cursed";
            if (summary.m_78368_()) {
               key = key + "Hardcore";
            }
         }

         MutableComponent tcn = Component.m_237115_(key);
         tcn.m_130948_(info.m_7383_());
         if (info instanceof MutableComponent mutable) {
            if (mutable.m_214077_() instanceof TranslatableContents) {
               info.m_7360_().forEach(tcn::m_7220_);
            } else if (mutable.m_214077_() instanceof LiteralContents) {
               for (int i = 1; i < info.m_7360_().size(); i++) {
                  tcn.m_7220_((Component)info.m_7360_().get(i));
               }
            }
         }

         return tcn;
      }
   }

   public static Item findItem(String namespace, String name) {
      return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, name));
   }

   public static Optional<MinecraftServer> getSingleplayerServer() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      return server != null && server.m_129792_() && Objects.equal(server.m_236731_().getName(), EnigmaticLegacy.PROXY.getClientUsername())
         ? Optional.of(server)
         : Optional.empty();
   }

   public static void setCurrentWorldCursed(boolean cursed) {
      getSingleplayerServer().ifPresent(server -> {
         File saveFolder = server.m_129843_(LevelResource.f_78182_).toFile();
         EnigmaticTransience transience = EnigmaticTransience.read(saveFolder);
         transience.setCursed(cursed);
         transience.write(saveFolder);
      });
   }

   public static void setCurrentWorldFractured(boolean fractured) {
      getSingleplayerServer().ifPresent(server -> {
         File saveFolder = server.m_129843_(LevelResource.f_78182_).toFile();
         EnigmaticTransience transience = EnigmaticTransience.read(saveFolder);
         transience.setPermanentlyDead(fractured);
         transience.write(saveFolder);
      });
   }

   public static boolean isWorldCursed(File world) {
      return EnigmaticTransience.read(world).isCursed();
   }

   public static boolean isWorldFractured(File world) {
      return EnigmaticTransience.read(world).isPermanentlyDead();
   }
}
