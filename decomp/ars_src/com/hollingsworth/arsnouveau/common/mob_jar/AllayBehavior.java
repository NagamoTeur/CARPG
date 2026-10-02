package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.MultiInsertReference;
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.api.util.InvUtil;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AllayBehavior extends JarBehavior<Allay> {
   @Override
   public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, MobJarTile tile) {
      super.use(state, world, pos, player, handIn, hit, tile);
      Allay allay = this.entityFromJar(tile);
   }

   @Override
   public void tick(MobJarTile tile) {
      super.tick(tile);
      if (tile.m_58904_().f_46443_) {
         Allay allay = this.entityFromJar(tile);
         allay.f_19797_++;
         allay.f_218305_ = allay.f_218304_;
         if (allay.m_218389_()) {
            allay.f_218304_ = Mth.m_14036_(allay.f_218304_ + 1.0F, 0.0F, 5.0F);
         } else {
            allay.f_218304_ = Mth.m_14036_(allay.f_218304_ - 1.0F, 0.0F, 5.0F);
         }

         if (allay.m_239559_()) {
            allay.f_238687_++;
            allay.f_238552_ = allay.f_238541_;
            if (allay.m_239302_()) {
               allay.f_238541_++;
            } else {
               allay.f_238541_--;
            }

            allay.f_238541_ = Mth.m_14036_(allay.f_238541_, 0.0F, 15.0F);
         } else {
            allay.f_238687_ = 0.0F;
            allay.f_238541_ = 0.0F;
            allay.f_238552_ = 0.0F;
         }
      } else {
         Level level = tile.m_58904_();
         if (level.m_46467_() % 40L == 0L) {
            Allay allayx = this.entityFromJar(tile);
            ItemStack heldStack = allayx.m_21120_(InteractionHand.MAIN_HAND);
            List<FilterableItemHandler> inventories = InvUtil.adjacentInventories(level, tile.m_58899_());
            if (inventories.isEmpty()) {
               return;
            }

            if (heldStack.m_41720_() instanceof ItemScroll) {
               for (FilterableItemHandler filterableItemHandler : inventories) {
                  filterableItemHandler.addFilterScroll(heldStack);
               }
            }

            InventoryManager manager = new InventoryManager(inventories);

            for (ItemEntity entity : level.m_45976_(ItemEntity.class, new AABB(tile.m_58899_()).m_82400_(5.0))) {
               if (entity.m_6084_()
                  && !entity.m_32055_().m_41619_()
                  && (heldStack.m_41619_() || heldStack.m_41720_() instanceof ItemScroll || entity.m_32055_().m_41726_(heldStack))) {
                  MultiInsertReference reference = manager.insertStackWithReference(entity.m_32055_());
                  if (!reference.isEmpty()) {
                     ItemStack remainder = reference.getRemainder();
                     entity.m_32045_(remainder);
                     level.m_5594_(null, tile.m_58899_(), SoundEvents.f_12019_, SoundSource.BLOCKS, 0.8F, 1.0F);
                     return;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean shouldUsePartialTicks(MobJarTile pBlockEntity) {
      return true;
   }

   @Override
   public Vec3 translate(MobJarTile pBlockEntity) {
      return new Vec3(0.0, 0.2, 0.0);
   }

   @Override
   public void getTooltip(MobJarTile tile, List<Component> tooltips) {
      super.getTooltip(tile, tooltips);
      Allay allay = this.entityFromJar(tile);
      if (allay.m_21205_().m_41720_() instanceof ItemScroll scroll) {
         scroll.m_7373_(allay.m_21205_(), tile.m_58904_(), tooltips, Default.NORMAL);
      }
   }
}
