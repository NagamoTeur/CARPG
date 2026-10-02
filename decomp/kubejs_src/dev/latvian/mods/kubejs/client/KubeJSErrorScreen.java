package dev.latvian.mods.kubejs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.script.ScriptType;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.util.Mth;

public class KubeJSErrorScreen extends Screen {
   public final ScriptType type;
   private MultiLineLabel multilineMessage;

   public KubeJSErrorScreen(ScriptType type) {
      super(Component.m_237119_());
      this.type = type;
      this.multilineMessage = MultiLineLabel.f_94331_;
   }

   public Component m_142562_() {
      return Component.m_237113_("There were KubeJS startup errors!");
   }

   protected void m_7856_() {
      super.m_7856_();
      ArrayList<Component> list = new ArrayList<>();
      list.add(
         Component.m_237113_("There were KubeJS startup errors ")
            .m_7220_(Component.m_237113_("[" + this.type.errors.size() + "]").m_130940_(ChatFormatting.DARK_RED))
            .m_130946_("!")
      );
      Style style = Style.f_131099_.m_178520_(13736083);
      ArrayList<String> errors = new ArrayList<>(this.type.errors);

      for (int i = 0; i < errors.size(); i++) {
         list.add(Component.m_237119_());
         list.add(
            Component.m_237113_(i + 1 + ") ")
               .m_130940_(ChatFormatting.DARK_RED)
               .m_7220_(
                  Component.m_237113_(errors.get(i).replace("Error occurred while handling event ", "Error in ").replace("dev.latvian.mods.kubejs.", "..."))
                     .m_130948_(style)
               )
         );
      }

      this.multilineMessage = MultiLineLabel.m_94341_(this.f_96547_, CommonComponents.m_178391_(list), this.f_96543_ - 12);
      int i = this.f_96544_ - 26;
      if (CommonProperties.get().startupErrorReportUrl.isBlank()) {
         this.m_142416_(new Button(this.f_96543_ / 2 - 155, i, 150, 20, Component.m_237113_("Open startup.log"), this::openLog));
         this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, i, 150, 20, Component.m_237113_("Quit"), this::quit));
      } else {
         this.m_142416_(new Button(this.f_96543_ / 4 - 55, i, 100, 20, Component.m_237113_("Open startup.log"), this::openLog));
         this.m_142416_(new Button(this.f_96543_ / 2 - 50, i, 100, 20, Component.m_237113_("Report"), this::report));
         this.m_142416_(new Button(this.f_96543_ * 3 / 4 - 45, i, 100, 20, Component.m_237113_("Quit"), this::quit));
      }
   }

   private void quit(Button button) {
      this.f_96541_.m_91395_();
   }

   private void report(Button button) {
      this.m_5561_(Style.f_131099_.m_131142_(new ClickEvent(Action.OPEN_URL, CommonProperties.get().startupErrorReportUrl)));
   }

   private void openLog(Button button) {
      this.m_5561_(Style.f_131099_.m_131142_(new ClickEvent(Action.OPEN_FILE, this.type.getLogFile().toAbsolutePath().toString())));
   }

   public void m_6305_(PoseStack stack, int i, int j, float f) {
      this.m_7333_(stack);
      this.multilineMessage.m_6276_(stack, this.f_96543_ / 2, this.messageTop());
      super.m_6305_(stack, i, j, f);
   }

   private int titleTop() {
      int i = (this.f_96544_ - this.messageHeight()) / 2;
      int var10000 = i - 20;
      Objects.requireNonNull(this.f_96547_);
      return Mth.m_14045_(var10000 - 9, 10, 80);
   }

   private int messageTop() {
      return this.titleTop() + 20;
   }

   private int messageHeight() {
      int var10000 = this.multilineMessage.m_5770_();
      Objects.requireNonNull(this.f_96547_);
      return var10000 * 9;
   }

   public boolean m_6913_() {
      return false;
   }
}
