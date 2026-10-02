package dev.latvian.mods.kubejs.core;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import dev.latvian.mods.kubejs.gui.KubeJSGUI;
import dev.latvian.mods.kubejs.gui.KubeJSMenu;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.net.NotificationMessage;
import dev.latvian.mods.kubejs.net.PaintMessage;
import dev.latvian.mods.kubejs.net.SendDataFromServerMessage;
import dev.latvian.mods.kubejs.player.AdvancementJS;
import dev.latvian.mods.kubejs.player.PlayerStatsJS;
import dev.latvian.mods.kubejs.util.NotificationBuilder;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Date;
import java.util.function.Consumer;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface ServerPlayerKJS extends PlayerKJS {
   default ServerPlayer kjs$self() {
      return (ServerPlayer)this;
   }

   @Override
   default void kjs$sendData(String channel, @Nullable CompoundTag data) {
      if (!channel.isEmpty()) {
         new SendDataFromServerMessage(channel, data).sendTo(this.kjs$self());
      }
   }

   @Override
   default void kjs$paint(CompoundTag renderer) {
      new PaintMessage(renderer).sendTo(this.kjs$self());
   }

   @Override
   default PlayerStatsJS kjs$getStats() {
      return new PlayerStatsJS(this.kjs$self(), this.kjs$self().m_8951_());
   }

   @Override
   default boolean kjs$isMiningBlock() {
      return this.kjs$self().f_8941_.f_9249_;
   }

   @Override
   default void kjs$setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
      PlayerKJS.super.kjs$setPositionAndRotation(x, y, z, yaw, pitch);
      this.kjs$self().f_8906_.m_9774_(x, y, z, yaw, pitch);
   }

   default void kjs$setCreativeMode(boolean mode) {
      this.kjs$self().m_143403_(mode ? GameType.CREATIVE : GameType.SURVIVAL);
   }

   default boolean kjs$isOp() {
      return this.kjs$self().f_8924_.m_6846_().m_11303_(this.kjs$self().m_36316_());
   }

   default void kjs$kick(Component reason) {
      this.kjs$self().f_8906_.m_9942_(reason);
   }

   default void kjs$kick() {
      this.kjs$kick(Component.m_237115_("multiplayer.disconnect.kicked"));
   }

   default void kjs$ban(String banner, String reason, long expiresInMillis) {
      Date date = new Date();
      UserBanListEntry userlistbansentry = new UserBanListEntry(
         this.kjs$self().m_36316_(), date, banner, new Date(date.getTime() + (expiresInMillis <= 0L ? 315569260000L : expiresInMillis)), reason
      );
      this.kjs$self().f_8924_.m_6846_().m_11295_().m_11381_(userlistbansentry);
      this.kjs$kick(Component.m_237115_("multiplayer.disconnect.banned"));
   }

   default boolean kjs$isAdvancementDone(ResourceLocation id) {
      AdvancementJS a = this.kjs$self().f_8924_.kjs$getAdvancement(id);
      return a != null && this.kjs$self().m_8960_().m_135996_(a.advancement).m_8193_();
   }

   default void kjs$unlockAdvancement(ResourceLocation id) {
      AdvancementJS a = this.kjs$self().f_8924_.kjs$getAdvancement(id);
      if (a != null) {
         AdvancementProgress advancementprogress = this.kjs$self().m_8960_().m_135996_(a.advancement);

         for (String s : advancementprogress.m_8219_()) {
            this.kjs$self().m_8960_().m_135988_(a.advancement, s);
         }
      }
   }

   default void kjs$revokeAdvancement(ResourceLocation id) {
      AdvancementJS a = this.kjs$self().f_8924_.kjs$getAdvancement(id);
      if (a != null) {
         AdvancementProgress advancementprogress = this.kjs$self().m_8960_().m_135996_(a.advancement);
         if (advancementprogress.m_8206_()) {
            for (String s : advancementprogress.m_8220_()) {
               this.kjs$self().m_8960_().m_135998_(a.advancement, s);
            }
         }
      }
   }

   @Override
   default void kjs$setSelectedSlot(int index) {
      int p = this.kjs$getSelectedSlot();
      PlayerKJS.super.kjs$setSelectedSlot(index);
      int n = this.kjs$getSelectedSlot();
      if (p != n && this.kjs$self().f_8906_ != null) {
         this.kjs$self().f_8906_.m_9829_(new ClientboundSetCarriedItemPacket(n));
      }
   }

   @Override
   default void kjs$setMouseItem(ItemStack item) {
      PlayerKJS.super.kjs$setMouseItem(item);
      if (this.kjs$self().f_8906_ != null) {
         this.kjs$self().f_36095_.m_38946_();
      }
   }

   @Nullable
   default BlockContainerJS kjs$getSpawnLocation() {
      BlockPos pos = this.kjs$self().m_8961_();
      return pos == null ? null : new BlockContainerJS(this.kjs$self().f_19853_, pos);
   }

   default void kjs$setSpawnLocation(BlockContainerJS c) {
      this.kjs$self().m_9158_(c.minecraftLevel.m_46472_(), c.getPos(), 0.0F, true, false);
   }

   @Override
   default void kjs$notify(NotificationBuilder builder) {
      new NotificationMessage(builder).sendTo(this.kjs$self());
   }

   default void kjs$openGUI(Consumer<KubeJSGUI> gui) {
      final KubeJSGUI data = new KubeJSGUI();
      gui.accept(data);
      MenuRegistry.openExtendedMenu(this.kjs$self(), new ExtendedMenuProvider() {
         public void saveExtraData(FriendlyByteBuf buf) {
            data.write(buf);
         }

         public Component m_5446_() {
            return data.title;
         }

         public AbstractContainerMenu m_7208_(int i, Inventory inventory, Player player) {
            return new KubeJSMenu(i, inventory, data);
         }
      });
   }

   default void kjs$openInventoryGUI(InventoryKJS inventory, Component title) {
      this.kjs$openGUI(gui -> {
         gui.title = title;
         gui.setInventory(inventory);
      });
   }
}
