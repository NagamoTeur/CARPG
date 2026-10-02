package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.ChangeableBehavior;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class StarbyBehavior extends ChangeableBehavior {
   public Starbuncle starbuncle;

   public StarbyBehavior(Starbuncle entity, CompoundTag tag) {
      super(entity, tag);
      this.starbuncle = entity;
      this.goals.add(new WrappedGoal(4, new GoToBedGoal(this.starbuncle, this)));
      this.goals.add(new WrappedGoal(8, new LookAtPlayerGoal(this.starbuncle, Player.class, 3.0F, 0.01F)));
      this.goals.add(new WrappedGoal(8, new NonHoggingLook(this.starbuncle, Mob.class, 3.0F, 0.01F)));
      this.goals.add(new WrappedGoal(1, new OpenDoorGoal(this.starbuncle, true)));
   }

   public boolean canGoToBed() {
      return true;
   }

   public boolean isBedPowered() {
      if (this.starbuncle.data.bedPos != null && this.starbuncle.f_19853_.m_46749_(this.starbuncle.data.bedPos)) {
         BlockState state = this.starbuncle.f_19853_.m_8055_(this.starbuncle.data.bedPos);
         return !state.m_204336_(BlockTagProvider.SUMMON_SLEEPABLE)
            ? false
            : state.m_61138_(BlockStateProperties.f_61448_) && (Boolean)state.m_61143_(BlockStateProperties.f_61448_);
      } else {
         return false;
      }
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      super.onFinishedConnectionFirst(storedPos, storedEntity, playerEntity);
      if (storedPos != null && playerEntity.f_19853_.m_8055_(storedPos).m_204336_(BlockTagProvider.SUMMON_SLEEPABLE)) {
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.set_bed"));
         this.starbuncle.data.bedPos = storedPos.m_7949_();
      }
   }

   @Override
   protected ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", "starby");
   }

   @Override
   public void syncTag() {
      this.starbuncle.syncBehavior();
   }

   @Override
   public ItemStack getStackForRender() {
      return this.starbuncle.getHeldStack();
   }
}
