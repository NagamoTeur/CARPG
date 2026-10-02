package com.aizistral.enigmaticlegacy.proxy;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class CommonProxy {
   protected final Map<Player, TransientPlayerData> commonTransientPlayerData = new WeakHashMap<>();

   public void displayPermadeathScreen() {
   }

   public void clearTransientData() {
      this.commonTransientPlayerData.clear();
   }

   public Map<Player, TransientPlayerData> getTransientPlayerData(boolean clientOnly) {
      return this.commonTransientPlayerData;
   }

   public void handleItemPickup(int pickuper_id, int item_id) {
   }

   public void loadComplete(FMLLoadCompleteEvent event) {
   }

   public void initAuxiliaryRender() {
   }

   public boolean isInVanillaDimension(Player player) {
      ServerPlayer serverPlayer = (ServerPlayer)player;
      return serverPlayer.m_9236_().m_46472_().equals(this.getOverworldKey())
         || serverPlayer.m_9236_().m_46472_().equals(this.getNetherKey())
         || serverPlayer.m_9236_().m_46472_().equals(this.getEndKey());
   }

   public boolean isInDimension(Player player, ResourceKey<Level> world) {
      ServerPlayer serverPlayer = (ServerPlayer)player;
      return serverPlayer.m_9236_().m_46472_().equals(world);
   }

   public Level getCentralWorld() {
      return SuperpositionHandler.getOverworld();
   }

   public ResourceKey<Level> getOverworldKey() {
      return Level.f_46428_;
   }

   public ResourceKey<Level> getNetherKey() {
      return Level.f_46429_;
   }

   public ResourceKey<Level> getEndKey() {
      return Level.f_46430_;
   }

   public UseAnim getVisualBlockAction() {
      return UseAnim.BOW;
   }

   public Player getPlayer(UUID playerID) {
      return ServerLifecycleHooks.getCurrentServer() != null ? ServerLifecycleHooks.getCurrentServer().m_6846_().m_11259_(playerID) : null;
   }

   public Player getClientPlayer() {
      return null;
   }

   public String getClientUsername() {
      return null;
   }

   public void pushRevelationToast(ItemStack renderedStack, int xp, int knowledge) {
   }

   public void initEntityRendering() {
   }

   public void spawnBonemealParticles(Level world, BlockPos pos, int data) {
   }

   public void updateInfinitumCounters() {
   }

   public void displayReviveAnimation(int entityID, int reviveType) {
   }
}
