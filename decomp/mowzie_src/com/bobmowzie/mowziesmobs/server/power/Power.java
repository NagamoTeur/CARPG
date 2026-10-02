package com.bobmowzie.mowziesmobs.server.power;

import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public abstract class Power {
   private final PlayerCapability.PlayerCapabilityImp capability;

   public Power(PlayerCapability.PlayerCapabilityImp capability) {
      this.capability = capability;
   }

   public void tick(PlayerTickEvent event) {
   }

   public void onRightClickEmpty(RightClickEmpty event) {
   }

   public void onRightClickBlock(RightClickBlock event) {
   }

   public void onRightClickWithItem(RightClickItem event) {
   }

   public void onRightClickEntity(EntityInteract event) {
   }

   public void onLeftClickEmpty(LeftClickEmpty event) {
   }

   public void onLeftClickBlock(LeftClickBlock event) {
   }

   public void onLeftClickEntity(AttackEntityEvent event) {
   }

   public void onTakeDamage(LivingHurtEvent event) {
   }

   public void onJump(LivingJumpEvent event) {
   }

   public void onRightMouseDown(Player player) {
   }

   public void onLeftMouseDown(Player player) {
   }

   public void onRightMouseUp(Player player) {
   }

   public void onLeftMouseUp(Player player) {
   }

   public void onSneakDown(Player player) {
   }

   public void onSneakUp(Player player) {
   }

   public boolean canUse(Player player) {
      return true;
   }

   public PlayerCapability.PlayerCapabilityImp getProperties() {
      return this.capability;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(LivingEntity player, double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(player, LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double r) {
      return player.f_19853_.m_6443_(entityClass, player.m_20191_().m_82377_(r, r, r), e -> e != player && (double)player.m_20270_(e) <= r);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return player.f_19853_.m_6443_(entityClass, player.m_20191_().m_82377_(dX, dY, dZ), e -> e != player && (double)player.m_20270_(e) <= r);
   }
}
