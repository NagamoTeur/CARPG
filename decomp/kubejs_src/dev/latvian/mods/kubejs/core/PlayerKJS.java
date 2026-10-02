package dev.latvian.mods.kubejs.core;

import com.mojang.authlib.GameProfile;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.latvian.mods.kubejs.item.ItemHandlerUtils;
import dev.latvian.mods.kubejs.player.KubeJSInventoryListener;
import dev.latvian.mods.kubejs.player.PlayerStatsJS;
import dev.latvian.mods.kubejs.stages.Stages;
import dev.latvian.mods.kubejs.util.NotificationBuilder;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@RemapPrefixForJS("kjs$")
public interface PlayerKJS extends LivingEntityKJS, DataSenderKJS, WithAttachedData<Player> {
   default Player kjs$self() {
      return (Player)this;
   }

   default Stages kjs$getStages() {
      throw new NoMixinException();
   }

   default void kjs$paint(CompoundTag renderer) {
      throw new NoMixinException();
   }

   default PlayerStatsJS kjs$getStats() {
      throw new NoMixinException();
   }

   default boolean kjs$isMiningBlock() {
      throw new NoMixinException();
   }

   @Override
   default boolean kjs$isPlayer() {
      return true;
   }

   default boolean kjs$isFake() {
      return PlayerHooks.isFake(this.kjs$self());
   }

   @Override
   default GameProfile kjs$getProfile() {
      return this.kjs$self().m_36316_();
   }

   default InventoryKJS kjs$getInventory() {
      throw new NoMixinException();
   }

   default InventoryKJS kjs$getCraftingGrid() {
      throw new NoMixinException();
   }

   default void kjs$sendInventoryUpdate() {
      this.kjs$self().m_150109_().m_6596_();
      this.kjs$self().f_36095_.m_39730_().m_6596_();
      this.kjs$self().f_36095_.m_38946_();
   }

   default void kjs$give(ItemStack item) {
      ItemHandlerUtils.giveItemToPlayer(this.kjs$self(), item, -1);
   }

   default void kjs$giveInHand(ItemStack item) {
      ItemHandlerUtils.giveItemToPlayer(this.kjs$self(), item, this.kjs$getSelectedSlot());
   }

   default int kjs$getSelectedSlot() {
      return this.kjs$self().m_150109_().f_35977_;
   }

   default void kjs$setSelectedSlot(int index) {
      this.kjs$self().m_150109_().f_35977_ = Mth.m_14045_(index, 0, 8);
   }

   default ItemStack kjs$getMouseItem() {
      return this.kjs$self().f_36096_.m_142621_();
   }

   default void kjs$setMouseItem(ItemStack item) {
      this.kjs$self().f_36096_.m_142503_(item);
   }

   @Override
   default void kjs$setStatusMessage(Component message) {
      this.kjs$self().m_5661_(message, true);
   }

   @Override
   default void kjs$spawn() {
   }

   default void kjs$addFood(int f, float m) {
      this.kjs$self().m_36324_().m_38707_(f, m);
   }

   default int kjs$getFoodLevel() {
      return this.kjs$self().m_36324_().m_38702_();
   }

   default void kjs$setFoodLevel(int foodLevel) {
      this.kjs$self().m_36324_().m_38705_(foodLevel);
   }

   default float kjs$getSaturation() {
      return this.kjs$self().m_36324_().m_38722_();
   }

   default void kjs$setSaturation(float saturation) {
      this.kjs$self().m_36324_().m_38717_(saturation);
   }

   default void kjs$addExhaustion(float exhaustion) {
      this.kjs$self().m_36399_(exhaustion);
   }

   default void kjs$addXP(int xp) {
      this.kjs$self().m_6756_(xp);
   }

   default void kjs$addXPLevels(int l) {
      this.kjs$self().m_6749_(l);
   }

   default void kjs$setXp(int xp) {
      this.kjs$self().f_36079_ = 0;
      this.kjs$self().f_36080_ = 0.0F;
      this.kjs$self().f_36078_ = 0;
      this.kjs$self().m_6756_(xp);
   }

   default int kjs$getXp() {
      return this.kjs$self().f_36079_;
   }

   default void kjs$setXpLevel(int l) {
      this.kjs$self().f_36079_ = 0;
      this.kjs$self().f_36080_ = 0.0F;
      this.kjs$self().f_36078_ = 0;
      this.kjs$self().m_6749_(l);
   }

   default int kjs$getXpLevel() {
      return this.kjs$self().f_36078_;
   }

   default void kjs$boostElytraFlight() {
      if (this.kjs$self().m_21255_()) {
         Vec3 v = this.kjs$self().m_20154_();
         double d0 = 1.5;
         double d1 = 0.1;
         Vec3 m = this.kjs$self().m_20184_();
         this.kjs$self()
            .m_20256_(
               m.m_82520_(
                  v.f_82479_ * d1 + (v.f_82479_ * d0 - m.f_82479_) * 0.5,
                  v.f_82480_ * d1 + (v.f_82480_ * d0 - m.f_82480_) * 0.5,
                  v.f_82481_ * d1 + (v.f_82481_ * d0 - m.f_82481_) * 0.5
               )
            );
      }
   }

   default AbstractContainerMenu kjs$getOpenInventory() {
      return this.kjs$self().f_36096_;
   }

   default void kjs$addItemCooldown(Item item, int ticks) {
      this.kjs$self().m_36335_().m_41524_(item, ticks);
   }

   default KubeJSInventoryListener kjs$getInventoryChangeListener() {
      throw new NoMixinException();
   }

   default void kjs$notify(NotificationBuilder builder) {
      throw new NoMixinException();
   }

   default void kjs$notify(Component title, Component text) {
      NotificationBuilder n = new NotificationBuilder();
      n.text = Component.m_237119_().m_7220_(title).m_130946_("\n").m_7220_(text);
      this.kjs$notify(n);
   }
}
