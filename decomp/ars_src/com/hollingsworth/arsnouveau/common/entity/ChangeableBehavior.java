package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.item.IWandable;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ChangeableBehavior implements IWandable {
   public List<WrappedGoal> goals = new ArrayList<>();
   public Level level;
   public Entity entity;

   public ChangeableBehavior(Entity entity, CompoundTag tag) {
      this.level = entity.f_19853_;
      this.entity = entity;
   }

   public void getTooltip(List<Component> tooltip) {
   }

   public CompoundTag toTag(CompoundTag tag) {
      tag.m_128359_("id", this.getRegistryName().toString());
      return tag;
   }

   public double getX() {
      return this.entity.m_20185_();
   }

   public double getY() {
      return this.entity.m_20186_();
   }

   public double getZ() {
      return this.entity.m_20189_();
   }

   public void pickUpItem(ItemEntity entity) {
   }

   protected abstract ResourceLocation getRegistryName();

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      return InteractionResult.PASS;
   }

   public ItemStack getStackForRender() {
      return ItemStack.f_41583_;
   }

   public boolean clearOrRemove() {
      return true;
   }

   public void syncTag() {
   }
}
