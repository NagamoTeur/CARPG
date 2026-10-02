package com.obscuria.aquamirae.common;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entities.Eel;
import com.obscuria.aquamirae.network.ScrollMessage;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ScrollEffects {
   private final Player PLAYER;
   private int tick;

   private ScrollEffects(Player player) {
      this.PLAYER = player;
   }

   public static void create(@NotNull Player player) {
      if (!player.f_19853_.f_46443_) {
         MinecraftForge.EVENT_BUS.register(new ScrollEffects(player));
      }
   }

   @SubscribeEvent
   public void tick(@NotNull ServerTickEvent event) {
      if (event.phase == Phase.END) {
         if (this.tick >= 80) {
            MinecraftForge.EVENT_BUS.unregister(this);
            this.effect();
         }

         this.tick++;
      }
   }

   private void effect() {
      int type = (int)Math.round(7.0 * Math.random());
      if (this.PLAYER instanceof ServerPlayer player) {
         Aquamirae.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new ScrollMessage(type));
      }

      switch (type) {
         case 1:
            this.abyss();
            break;
         case 2:
            this.mimic();
            break;
         case 3:
            ScrollEffects.MoveUp.create(this.PLAYER);
            break;
         case 4:
            ScrollEffects.MoveSide.create(this.PLAYER);
            break;
         case 5:
            if (!this.shelter()) {
               ScrollEffects.MoveUp.create(this.PLAYER);
            }
            break;
         case 6:
            this.chakras();
            break;
         case 7:
            ScrollEffects.Bones.create(this.PLAYER);
            break;
         default:
            this.PLAYER.m_36176_(Items.f_42415_.m_7968_(), false);
      }
   }

   private void abyss() {
      for (int ix = -1; ix <= 1; ix++) {
         for (int iy = 1; iy >= -10; iy--) {
            for (int iz = -1; iz <= 1; iz++) {
               BlockPos pos = new BlockPos(this.PLAYER.m_146903_() + ix, this.PLAYER.m_146904_() + iy, this.PLAYER.m_146907_() + iz);
               if (this.PLAYER.f_19853_.m_8055_(pos).m_204336_(Aquamirae.SCROLL_DESTROY)) {
                  this.PLAYER.f_19853_.m_46961_(pos, true);
               }
            }
         }
      }
   }

   private void mimic() {
      Drowned drowned = new Drowned(EntityType.f_20562_, this.PLAYER.f_19853_);
      if (this.PLAYER.f_19853_ instanceof ServerLevel serverLevel) {
         drowned.m_6518_(serverLevel, this.PLAYER.f_19853_.m_6436_(this.PLAYER.m_20183_()), MobSpawnType.EVENT, null, null);
         drowned.m_20219_(this.PLAYER.m_20182_());
         drowned.m_8061_(EquipmentSlot.HEAD, this.PLAYER.m_6844_(EquipmentSlot.HEAD));
         drowned.m_8061_(EquipmentSlot.CHEST, this.PLAYER.m_6844_(EquipmentSlot.CHEST));
         drowned.m_8061_(EquipmentSlot.LEGS, this.PLAYER.m_6844_(EquipmentSlot.LEGS));
         drowned.m_8061_(EquipmentSlot.FEET, this.PLAYER.m_6844_(EquipmentSlot.FEET));
         drowned.m_21008_(InteractionHand.MAIN_HAND, this.PLAYER.m_21120_(InteractionHand.MAIN_HAND));
         drowned.m_21008_(InteractionHand.OFF_HAND, this.PLAYER.m_21120_(InteractionHand.OFF_HAND));
         drowned.m_21508_(EquipmentSlot.HEAD);
         drowned.m_21508_(EquipmentSlot.CHEST);
         drowned.m_21508_(EquipmentSlot.LEGS);
         drowned.m_21508_(EquipmentSlot.FEET);
         drowned.m_21508_(EquipmentSlot.MAINHAND);
         drowned.m_21508_(EquipmentSlot.OFFHAND);
         this.PLAYER.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
         this.PLAYER.m_8061_(EquipmentSlot.CHEST, ItemStack.f_41583_);
         this.PLAYER.m_8061_(EquipmentSlot.LEGS, ItemStack.f_41583_);
         this.PLAYER.m_8061_(EquipmentSlot.FEET, ItemStack.f_41583_);
         this.PLAYER.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
         this.PLAYER.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
         serverLevel.m_7967_(drowned);
      }
   }

   private boolean shelter() {
      Vec3 center = this.PLAYER.m_20182_();
      List<Eel> eels = this.PLAYER
         .f_19853_
         .m_6443_(Eel.class, new AABB(center, center).m_82400_(128.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(ent -> ent.m_20238_(center)))
         .toList();
      if (!eels.isEmpty()) {
         this.PLAYER.m_20219_(eels.get(0).m_20182_());
         return true;
      } else {
         return false;
      }
   }

   private void chakras() {
      for (float i = 0.0F; i < 1.0F; i += 0.1F) {
         DynamicProjectile.create((EntityType)AquamiraeEntities.POISONED_CHAKRA.get(), this.PLAYER, this.PLAYER.f_19853_, null, 20, i, 6000, 1);
      }
   }

   public static class Bones {
      private final Player PLAYER;
      private int tick;

      private Bones(Player player) {
         this.PLAYER = player;
      }

      public static void create(@NotNull Player player) {
         if (!player.f_19853_.f_46443_) {
            MinecraftForge.EVENT_BUS.register(new ScrollEffects.Bones(player));
         }
      }

      @SubscribeEvent
      public void tick(@NotNull ServerTickEvent event) {
         if (event.phase == Phase.END) {
            if (this.tick >= 200) {
               MinecraftForge.EVENT_BUS.unregister(this);
            }

            if (this.tick % 20 == 0) {
               this.PLAYER
                  .f_19853_
                  .m_6269_(null, this.PLAYER, SoundEvents.f_12321_, SoundSource.PLAYERS, 1.0F, 0.9F + 0.2F * this.PLAYER.m_217043_().m_188501_());
               this.PLAYER.m_36176_(((Item)AquamiraeItems.SHARP_BONES.get()).m_7968_(), false);
            }

            this.tick++;
         }
      }
   }

   public static class MoveSide {
      private final Player PLAYER;
      private int tick;

      private MoveSide(Player player) {
         this.PLAYER = player;
      }

      public static void create(@NotNull Player player) {
         if (!player.f_19853_.f_46443_) {
            MinecraftForge.EVENT_BUS.register(new ScrollEffects.MoveSide(player));
         }
      }

      @SubscribeEvent
      public void tick(@NotNull ServerTickEvent event) {
         if (event.phase == Phase.END) {
            if (this.tick >= 20) {
               MinecraftForge.EVENT_BUS.unregister(this);
            }

            if (this.tick == 0) {
               this.PLAYER.m_20256_(this.PLAYER.m_20184_().m_82549_(new Vec3(0.0, 1.5, 0.0)));
               this.PLAYER.f_19864_ = true;
            }

            if (this.tick == 20) {
               this.PLAYER
                  .m_20256_(
                     this.PLAYER
                        .m_20184_()
                        .m_82549_(
                           this.PLAYER
                              .m_20182_()
                              .m_82505_(
                                 new Vec3(
                                    this.PLAYER.m_20185_() + Math.cos((double)this.PLAYER.m_146909_()) * 3.0,
                                    this.PLAYER.m_20186_() + 0.5,
                                    this.PLAYER.m_20189_() + Math.sin((double)this.PLAYER.m_146909_()) * 3.0
                                 )
                              )
                        )
                  );
               this.PLAYER.f_19864_ = true;
            }

            this.tick++;
         }
      }
   }

   public static class MoveUp {
      private final Player PLAYER;
      private int tick;

      private MoveUp(Player player) {
         this.PLAYER = player;
      }

      public static void create(@NotNull Player player) {
         if (!player.f_19853_.f_46443_) {
            MinecraftForge.EVENT_BUS.register(new ScrollEffects.MoveUp(player));
         }
      }

      @SubscribeEvent
      public void tick(@NotNull ServerTickEvent event) {
         if (event.phase == Phase.END) {
            if (this.tick >= 60) {
               MinecraftForge.EVENT_BUS.unregister(this);
            }

            if (this.tick == 0 || this.tick == 40) {
               this.PLAYER.m_20256_(this.PLAYER.m_20184_().m_82549_(new Vec3(0.0, 1.5, 0.0)));
               this.PLAYER.f_19864_ = true;
            }

            if (this.tick == 20 || this.tick == 60) {
               this.PLAYER.m_20256_(this.PLAYER.m_20184_().m_82549_(new Vec3(0.0, -1.0, 0.0)));
               this.PLAYER.f_19864_ = true;
            }

            this.tick++;
         }
      }
   }
}
