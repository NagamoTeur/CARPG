package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.utils.Icons;
import com.obscuria.obscureapi.util.PlayerUtils;
import com.obscuria.obscureapi.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ShellHornItem extends Item {
   public ShellHornItem() {
      super(new Properties().m_41487_(1).m_41497_(Rarity.UNCOMMON).m_41491_(Aquamirae.TAB));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      if (entity.m_9236_() instanceof ServerLevel level) {
         level.m_5594_(
            null,
            new BlockPos(entity.m_20185_(), entity.m_20186_() + 1.0, entity.m_20189_()),
            (SoundEvent)AquamiraeSounds.ITEM_SHELL_HORN_USE.get(),
            SoundSource.PLAYERS,
            3.0F,
            1.0F
         );
      }

      ItemStack stack = (ItemStack)ar.m_19095_();
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      entity.m_36335_().m_41524_(stack.m_41720_(), 120);
      boolean summon = false;
      BlockPos pos = new BlockPos(0, 0, 0);

      label38:
      for (int ix = -6; ix <= 6; ix++) {
         int sx = entity.m_146903_() + ix;

         for (int iz = -6; iz <= 6; iz++) {
            int sz = entity.m_146907_() + iz;
            if (AquamiraeUtils.isInIceMaze(entity)
               && entity.m_9236_().m_8055_(new BlockPos(sx, 62, sz)).m_60734_() == Blocks.f_49990_
               && entity.m_9236_().m_8055_(new BlockPos(sx, 58, sz)).m_60734_() == Blocks.f_49990_
               && entity.m_9236_().m_8055_(new BlockPos(sx - 1, 62, sz)).m_60734_() == Blocks.f_49990_
               && entity.m_9236_().m_8055_(new BlockPos(sx + 1, 62, sz)).m_60734_() == Blocks.f_49990_
               && entity.m_9236_().m_8055_(new BlockPos(sx, 62, sz - 1)).m_60734_() == Blocks.f_49990_
               && entity.m_9236_().m_8055_(new BlockPos(sx, 62, sz + 1)).m_60734_() == Blocks.f_49990_) {
               summon = true;
               pos = new BlockPos(sx, 58, sz);
               stack.m_41774_(1);
               entity.m_150109_().m_6596_();
               break label38;
            }
         }
      }

      (new Object() {
            private int ticks = 0;
            private float waitTicks;
            private Player summoner;
            private BlockPos pos;
            private boolean summon;

            public void start(int waitTicks, Player summoner, BlockPos pos, boolean summon) {
               this.waitTicks = (float)waitTicks;
               this.summoner = summoner;
               this.pos = pos;
               this.summon = summon;
               MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void tick(ServerTickEvent event) {
               if (event.phase == Phase.END) {
                  this.ticks++;
                  if ((float)this.ticks >= this.waitTicks) {
                     if (this.summon) {
                        this.spawn();
                     } else if (!this.summoner.m_9236_().m_5776_()) {
                        PlayerUtils.sendMessage(this.summoner, Icons.BOSS + TextUtils.translation("info.captain_spawn_fail"));
                     }

                     MinecraftForge.EVENT_BUS.unregister(this);
                  }
               }
            }

            private void spawn() {
               if (this.summoner.m_9236_() instanceof ServerLevel server) {
                  Mob cornelia = new CaptainCornelia((EntityType<CaptainCornelia>)AquamiraeEntities.CAPTAIN_CORNELIA.get(), server);
                  cornelia.m_7678_(
                     (double)this.pos.m_123341_() + 0.5,
                     (double)this.pos.m_123342_(),
                     (double)this.pos.m_123343_() + 0.5,
                     this.summoner.m_9236_().m_213780_().m_188501_() * 360.0F,
                     0.0F
                  );
                  cornelia.m_6518_(server, this.summoner.m_9236_().m_6436_(cornelia.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  this.summoner.m_9236_().m_7967_(cornelia);
               }

               if (!this.summoner.m_9236_().m_5776_()) {
                  PlayerUtils.sendMessage(this.summoner, Icons.BOSS.get() + TextUtils.translation("info.captain_spawn"));
               }
            }
         })
         .start(60, entity, pos, summon);
      return ar;
   }
}
