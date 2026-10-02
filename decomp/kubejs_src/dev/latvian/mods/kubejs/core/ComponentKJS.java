package dev.latvian.mods.kubejs.core;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.bindings.TextWrapper;
import dev.latvian.mods.kubejs.util.WrappedJS;
import dev.latvian.mods.rhino.mod.util.JsonSerializable;
import dev.latvian.mods.rhino.mod.util.color.Color;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Iterator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface ComponentKJS extends Component, Iterable<Component>, JsonSerializable, WrappedJS {
   @Override
   default Iterator<Component> iterator() {
      throw new NoMixinException();
   }

   default MutableComponent kjs$self() {
      return (MutableComponent)this;
   }

   @RemapForJS("toJson")
   default JsonElement toJsonJS() {
      return Serializer.m_130716_(this.kjs$self());
   }

   default boolean kjs$hasStyle() {
      return this.m_7383_() != null && !this.m_7383_().m_131179_();
   }

   default boolean kjs$hasSiblings() {
      return !this.m_7360_().isEmpty();
   }

   default MutableComponent kjs$black() {
      return this.kjs$self().m_130940_(ChatFormatting.BLACK);
   }

   default MutableComponent kjs$darkBlue() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_BLUE);
   }

   default MutableComponent kjs$darkGreen() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_GREEN);
   }

   default MutableComponent kjs$darkAqua() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_AQUA);
   }

   default MutableComponent kjs$darkRed() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_RED);
   }

   default MutableComponent kjs$darkPurple() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_PURPLE);
   }

   default MutableComponent kjs$gold() {
      return this.kjs$self().m_130940_(ChatFormatting.GOLD);
   }

   default MutableComponent kjs$gray() {
      return this.kjs$self().m_130940_(ChatFormatting.GRAY);
   }

   default MutableComponent kjs$darkGray() {
      return this.kjs$self().m_130940_(ChatFormatting.DARK_GRAY);
   }

   default MutableComponent kjs$blue() {
      return this.kjs$self().m_130940_(ChatFormatting.BLUE);
   }

   default MutableComponent kjs$green() {
      return this.kjs$self().m_130940_(ChatFormatting.GREEN);
   }

   default MutableComponent kjs$aqua() {
      return this.kjs$self().m_130940_(ChatFormatting.AQUA);
   }

   default MutableComponent kjs$red() {
      return this.kjs$self().m_130940_(ChatFormatting.RED);
   }

   default MutableComponent kjs$lightPurple() {
      return this.kjs$self().m_130940_(ChatFormatting.LIGHT_PURPLE);
   }

   default MutableComponent kjs$yellow() {
      return this.kjs$self().m_130940_(ChatFormatting.YELLOW);
   }

   default MutableComponent kjs$white() {
      return this.kjs$self().m_130940_(ChatFormatting.WHITE);
   }

   default MutableComponent kjs$color(@Nullable Color c) {
      TextColor col = c == null ? null : c.createTextColorJS();
      return this.kjs$self().m_6270_(this.m_7383_().m_131148_(col));
   }

   default MutableComponent kjs$noColor() {
      return this.kjs$color(null);
   }

   default MutableComponent kjs$bold(@Nullable Boolean value) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131136_(value));
   }

   default MutableComponent kjs$bold() {
      return this.kjs$bold(true);
   }

   default MutableComponent kjs$italic(@Nullable Boolean value) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131155_(value));
   }

   default MutableComponent kjs$italic() {
      return this.kjs$italic(true);
   }

   default MutableComponent kjs$underlined(@Nullable Boolean value) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131162_(value));
   }

   default MutableComponent kjs$underlined() {
      return this.kjs$underlined(true);
   }

   default MutableComponent kjs$strikethrough(@Nullable Boolean value) {
      return this.kjs$self().m_6270_(this.m_7383_().m_178522_(value));
   }

   default MutableComponent kjs$strikethrough() {
      return this.kjs$strikethrough(true);
   }

   default MutableComponent kjs$obfuscated(@Nullable Boolean value) {
      return this.kjs$self().m_6270_(this.m_7383_().m_178524_(value));
   }

   default MutableComponent kjs$obfuscated() {
      return this.kjs$obfuscated(true);
   }

   default MutableComponent kjs$insertion(@Nullable String s) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131138_(s));
   }

   default MutableComponent kjs$font(@Nullable ResourceLocation s) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131150_(s));
   }

   default MutableComponent kjs$click(@Nullable ClickEvent s) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131142_(s));
   }

   default MutableComponent kjs$clickRunCommand(String command) {
      return this.kjs$click(new ClickEvent(Action.RUN_COMMAND, command));
   }

   default MutableComponent kjs$clickSuggestCommand(String command) {
      return this.kjs$click(new ClickEvent(Action.SUGGEST_COMMAND, command));
   }

   default MutableComponent kjs$clickCopy(String text) {
      return this.kjs$click(new ClickEvent(Action.COPY_TO_CLIPBOARD, text));
   }

   default MutableComponent kjs$clickChangePage(String page) {
      return this.kjs$click(new ClickEvent(Action.CHANGE_PAGE, page));
   }

   default MutableComponent kjs$clickOpenUrl(String url) {
      return this.kjs$click(new ClickEvent(Action.OPEN_URL, url));
   }

   default MutableComponent kjs$clickOpenFile(String path) {
      return this.kjs$click(new ClickEvent(Action.OPEN_FILE, path));
   }

   default MutableComponent kjs$hover(@Nullable Component s) {
      return this.kjs$self().m_6270_(this.m_7383_().m_131144_(s == null ? null : new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130831_, s)));
   }

   default boolean kjs$isEmpty() {
      return TextWrapper.isEmpty(this.kjs$self());
   }

   @Deprecated(
      forRemoval = true
   )
   default MutableComponent kjs$rawComponent() {
      KubeJS.LOGGER.warn("Using rawComponent() is deprecated, since components no longer need to be wrapped to Text! You can safely remove this method.");
      return this.kjs$self();
   }

   @Deprecated(
      forRemoval = true
   )
   default MutableComponent kjs$rawCopy() {
      KubeJS.LOGGER.warn("Using rawCopy() is deprecated, since components no longer need to be wrapped to Text! Use copy() instead.");
      return this.m_6881_();
   }

   @Deprecated(
      forRemoval = true
   )
   default Component kjs$component() {
      KubeJS.LOGGER.warn("Using component() is deprecated, since components no longer need to be wrapped to Text! You can safely remove this method.");
      return this.kjs$self();
   }
}
