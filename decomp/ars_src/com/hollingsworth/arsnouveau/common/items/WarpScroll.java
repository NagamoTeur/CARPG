package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class WarpScroll extends ModItem {
   public WarpScroll() {
      super(ItemsRegistry.defaultItemProperties());
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      if (entity.m_20193_().f_46443_) {
         return false;
      } else {
         String displayName = stack.m_41788_() ? stack.m_41786_().getString() : "";
         WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(stack);
         if (data.isValid()
            && data.canTeleportWithDim(entity.m_20193_().m_46472_().m_135782_().toString())
            && SourceUtil.hasSourceNearby(entity.m_20183_(), entity.m_20193_(), 10, 9000)
            && BlockRegistry.PORTAL_BLOCK.trySpawnPortal(entity.m_20193_(), entity.m_20183_(), data, displayName)
            && SourceUtil.takeSourceWithParticles(entity.m_20183_(), entity.m_20193_(), 10, 9000) != null) {
            BlockPos pos = entity.m_20183_();
            ServerLevel world = (ServerLevel)entity.m_20193_();
            world.m_8767_(
               ParticleTypes.f_123760_,
               (double)pos.m_123341_(),
               (double)pos.m_123342_() + 1.0,
               (double)pos.m_123343_(),
               10,
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               -world.f_46441_.m_188500_(),
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               0.1F
            );
            world.m_5594_(null, pos, SoundEvents.f_12049_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.CREATE_PORTAL, world, pos, 4);
            stack.m_41774_(1);
            return true;
         } else {
            return false;
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      WarpScroll.WarpScrollData data = new WarpScroll.WarpScrollData(stack);
      if (hand == InteractionHand.OFF_HAND) {
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else if (world instanceof ServerLevel serverLevel) {
         if (data.isValid()) {
            if (!data.canTeleportWithDim(player.m_20193_().m_46472_().m_135782_().toString())) {
               player.m_213846_(Component.m_237115_("ars_nouveau.warp_scroll.wrong_dim"));
               return InteractionResultHolder.m_19100_(stack);
            } else {
               BlockPos pos = data.getPos();
               player.m_20324_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
               Vec2 rotation = data.getRotation();
               player.m_146926_(rotation.f_82470_);
               player.m_146922_(rotation.f_82471_);
               serverLevel.m_8767_(
                  ParticleTypes.f_123760_,
                  (double)pos.m_123341_(),
                  (double)pos.m_123342_() + 1.0,
                  (double)pos.m_123343_(),
                  10,
                  (world.f_46441_.m_188500_() - 0.5) * 2.0,
                  -world.f_46441_.m_188500_(),
                  (world.f_46441_.m_188500_() - 0.5) * 2.0,
                  0.1F
               );
               world.m_5594_(null, pos, SoundEvents.f_12049_, SoundSource.NEUTRAL, 1.0F, 1.0F);
               stack.m_41774_(1);
               return InteractionResultHolder.m_19098_(stack);
            }
         } else {
            if (player.m_6144_()) {
               ItemStack newWarpStack = new ItemStack((ItemLike)ItemsRegistry.WARP_SCROLL.get());
               WarpScroll.WarpScrollData newData = new WarpScroll.WarpScrollData(newWarpStack);
               newData.setData(player.m_20183_(), player.m_20193_().m_46472_().m_135782_().toString(), player.m_20155_());
               boolean didAdd;
               if (stack.m_41613_() == 1) {
                  stack = newWarpStack;
                  didAdd = true;
               } else {
                  didAdd = player.m_36356_(newWarpStack);
                  if (didAdd) {
                     stack.m_41774_(1);
                  }
               }

               if (!didAdd) {
                  player.m_213846_(Component.m_237115_("ars_nouveau.warp_scroll.inv_full"));
                  return InteractionResultHolder.m_19100_(stack);
               }

               player.m_213846_(Component.m_237115_("ars_nouveau.warp_scroll.recorded"));
            }

            return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
         }
      } else {
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag p_77624_4_) {
      WarpScroll.WarpScrollData data = new WarpScroll.WarpScrollData(stack);
      if (!data.isValid()) {
         tooltip.add(Component.m_237115_("ars_nouveau.warp_scroll.no_location"));
      } else {
         BlockPos pos = data.pos;
         tooltip.add(Component.m_237110_("ars_nouveau.position", new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}));
         if (!(Boolean)ServerConfig.ENABLE_WARP_PORTALS.get()) {
            tooltip.add(
               Component.m_237115_("ars_nouveau.warp_scroll.disabled_warp_portal")
                  .m_130944_(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
            );
         }
      }
   }

   public static class WarpScrollData extends ItemstackData {
      @Nullable
      private BlockPos pos;
      private String dimension;
      private Vec2 rotation;

      @Deprecated(
         forRemoval = false
      )
      public WarpScrollData(ItemStack stack) {
         super(stack);
         CompoundTag tag1 = this.getItemTag(stack);
         if (tag1 != null && !tag1.m_128456_()) {
            this.pos = tag1.m_128441_("x") ? new BlockPos(tag1.m_128451_("x"), tag1.m_128451_("y"), tag1.m_128451_("z")) : null;
            this.dimension = tag1.m_128461_("dim");
            this.rotation = new Vec2(tag1.m_128457_("xRot"), tag1.m_128457_("yRot"));
         }
      }

      public static WarpScroll.WarpScrollData get(ItemStack stack) {
         return (WarpScroll.WarpScrollData)(stack.m_41720_() instanceof StableWarpScroll
            ? new StableWarpScroll.StableScrollData(stack)
            : new WarpScroll.WarpScrollData(stack));
      }

      public boolean isValid() {
         return this.pos != null && this.dimension != null && this.rotation != null;
      }

      public boolean canTeleportWithDim(String dim) {
         return this.dimension != null && this.dimension.equals(dim);
      }

      public boolean canTeleportWithDim(Level level) {
         return this.canTeleportWithDim(level.m_46472_().m_135782_().toString());
      }

      public void setData(BlockPos pos, String dimension, Vec2 rotation) {
         this.pos = pos;
         this.dimension = dimension;
         this.rotation = rotation;
         this.writeItem();
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         if (this.pos != null) {
            tag.m_128405_("x", this.pos.m_123341_());
            tag.m_128405_("y", this.pos.m_123342_());
            tag.m_128405_("z", this.pos.m_123343_());
         }

         if (this.dimension != null) {
            tag.m_128359_("dim", this.dimension);
         }

         if (this.rotation != null) {
            tag.m_128350_("xRot", this.rotation.f_82470_);
            tag.m_128350_("yRot", this.rotation.f_82471_);
         }
      }

      @Override
      public String getTagString() {
         return "an_warp_scroll";
      }

      public String getDimension() {
         return this.dimension;
      }

      public void setDimension(String dimension) {
         this.dimension = dimension;
         this.writeItem();
      }

      @Nullable
      public BlockPos getPos() {
         return this.pos;
      }

      public void setPos(@Nullable BlockPos pos) {
         this.pos = pos;
         this.writeItem();
      }

      public Vec2 getRotation() {
         return this.rotation;
      }

      public void setRotation(Vec2 rotation) {
         this.rotation = rotation;
         this.writeItem();
      }

      public void copyFrom(WarpScroll.WarpScrollData warpScrollData) {
         this.pos = warpScrollData.pos;
         this.dimension = warpScrollData.dimension;
         this.rotation = warpScrollData.rotation;
         this.writeItem();
      }
   }
}
