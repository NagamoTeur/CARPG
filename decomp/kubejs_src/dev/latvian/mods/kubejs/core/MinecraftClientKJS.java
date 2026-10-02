package dev.latvian.mods.kubejs.core;

import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.bindings.event.ItemEvents;
import dev.latvian.mods.kubejs.client.ClientProperties;
import dev.latvian.mods.kubejs.item.ItemClickedEventJS;
import dev.latvian.mods.kubejs.net.FirstClickMessage;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface MinecraftClientKJS {
   default Minecraft kjs$self() {
      return (Minecraft)this;
   }

   @Nullable
   default Screen kjs$getCurrentScreen() {
      return this.kjs$self().f_91080_;
   }

   default void kjs$setCurrentScreen(Screen gui) {
      this.kjs$self().m_91152_(gui);
   }

   default void kjs$setTitle(String t) {
      ClientProperties.get().title = t.trim();
      this.kjs$self().m_91341_();
   }

   default String kjs$getCurrentWorldName() {
      ServerData server = this.kjs$self().m_91089_();
      return server == null ? "Singleplayer" : server.f_105362_;
   }

   default boolean kjs$isKeyDown(int key) {
      return InputConstants.m_84830_(this.kjs$self().m_91268_().m_85439_(), key);
   }

   default boolean kjs$isShiftDown() {
      return Screen.m_96638_();
   }

   default boolean kjs$isCtrlDown() {
      return Screen.m_96637_();
   }

   default boolean kjs$isAltDown() {
      return Screen.m_96639_();
   }

   @HideFromJS
   default void kjs$startAttack0() {
      if (ItemEvents.FIRST_LEFT_CLICKED.hasListeners()) {
         LocalPlayer player = this.kjs$self().f_91074_;
         ItemStack stack = player.m_21120_(InteractionHand.MAIN_HAND);
         ItemEvents.FIRST_LEFT_CLICKED.post(ScriptType.CLIENT, stack.m_41720_(), new ItemClickedEventJS(player, InteractionHand.MAIN_HAND, stack));
      }

      new FirstClickMessage(0).sendToServer();
   }

   @HideFromJS
   default void kjs$startUseItem0() {
      if (ItemEvents.FIRST_RIGHT_CLICKED.hasListeners()) {
         LocalPlayer player = this.kjs$self().f_91074_;

         for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.m_21120_(hand);
            ItemEvents.FIRST_RIGHT_CLICKED.post(ScriptType.CLIENT, stack.m_41720_(), new ItemClickedEventJS(player, hand, stack));
         }
      }

      new FirstClickMessage(1).sendToServer();
   }
}
