package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.Nullable;

public class StarbyPotionBehavior extends StarbyListBehavior {
   public static final ResourceLocation POTION_ID = new ResourceLocation("ars_nouveau", "starby_potion");
   @Nullable
   private PotionData heldPotion = null;
   private int amount;

   public StarbyPotionBehavior(Starbuncle entity, CompoundTag tag) {
      super(entity, tag);
      this.heldPotion = PotionData.fromTag(tag.m_128469_("potionData"));
      this.amount = tag.m_128451_("amount");
      this.goals.add(new WrappedGoal(3, new PotionTakeGoal(entity, this)));
      this.goals.add(new WrappedGoal(3, new PotionStoreGoal(entity, this)));
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      super.onFinishedConnectionFirst(storedPos, storedEntity, playerEntity);
      if (storedPos != null && this.level.m_7702_(storedPos) instanceof PotionJarTile) {
         this.addToPos(storedPos);
         this.syncTag();
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.potion_to"));
      }
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      super.onFinishedConnectionLast(storedPos, storedEntity, playerEntity);
      if (storedPos != null && this.level.m_7702_(storedPos) instanceof PotionJarTile) {
         this.addFromPos(storedPos);
         this.syncTag();
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.potion_from"));
      }
   }

   @Nullable
   public BlockPos getJarForTake() {
      for (BlockPos pos : this.FROM_LIST) {
         if (this.isPositionValidTake(pos)) {
            return pos;
         }
      }

      return null;
   }

   public boolean isPositionValidTake(BlockPos p) {
      if (p == null) {
         return false;
      } else {
         return !(this.level.m_7702_(p) instanceof PotionJarTile jar) ? false : jar.getAmount() >= 100 && this.getJarForStorage(jar.getData()) != null;
      }
   }

   @Nullable
   public BlockPos getJarForStorage(PotionData data) {
      for (BlockPos pos : this.TO_LIST) {
         if (this.level.m_7702_(pos) instanceof PotionJarTile && this.isPositionValidStore(pos, data)) {
            return pos;
         }
      }

      return null;
   }

   public boolean isPositionValidStore(BlockPos p, PotionData data) {
      if (p != null && data != null) {
         if (this.level.m_7702_(p) instanceof PotionJarTile jar && jar.canAccept(data, 100)) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public PotionData getHeldPotion() {
      return this.heldPotion == null ? new PotionData() : this.heldPotion;
   }

   public void setHeldPotion(PotionData data) {
      this.heldPotion = data;
      this.syncTag();
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public int getAmount() {
      return this.amount;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      super.getTooltip(tooltip);
      tooltip.add(Component.m_237110_("ars_nouveau.starbuncle.storing_potions", new Object[]{this.TO_LIST.size()}));
      tooltip.add(Component.m_237110_("ars_nouveau.starbuncle.taking_potions", new Object[]{this.FROM_LIST.size()}));
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      if (this.heldPotion != null) {
         tag.m_128365_("potionData", this.heldPotion.toTag());
      }

      tag.m_128405_("amount", this.amount);
      return super.toTag(tag);
   }

   @Override
   public ItemStack getStackForRender() {
      return this.heldPotion != null && this.heldPotion.getPotion() != Potions.f_43598_ ? this.heldPotion.asPotionStack() : super.getStackForRender();
   }

   @Override
   protected ResourceLocation getRegistryName() {
      return POTION_ID;
   }
}
