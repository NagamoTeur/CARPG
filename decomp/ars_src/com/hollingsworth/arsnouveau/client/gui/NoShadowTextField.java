package com.hollingsworth.arsnouveau.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class NoShadowTextField extends EditBox {
   public Function<String, Void> onClear;

   public NoShadowTextField(Font p_i232260_1_, int p_i232260_2_, int p_i232260_3_, int p_i232260_4_, int p_i232260_5_, Component p_i232260_6_) {
      super(p_i232260_1_, p_i232260_2_, p_i232260_3_, p_i232260_4_, p_i232260_5_, p_i232260_6_);
   }

   public NoShadowTextField(
      Font p_i232259_1_, int p_i232259_2_, int p_i232259_3_, int p_i232259_4_, int p_i232259_5_, @Nullable EditBox p_i232259_6_, Component p_i232259_7_
   ) {
      super(p_i232259_1_, p_i232259_2_, p_i232259_3_, p_i232259_4_, p_i232259_5_, p_i232259_6_, p_i232259_7_);
   }

   public void m_6303_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      if (this.m_94213_()) {
         int i2 = this.f_94098_ ? this.f_94103_ : this.f_94104_;
         int j = this.f_94101_ - this.f_94100_;
         int k = this.f_94102_ - this.f_94100_;
         String s = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
         boolean flag = j >= 0 && j <= s.length();
         boolean flag1 = this.m_93696_() && this.f_94095_ / 6 % 2 == 0 && flag;
         int l = this.f_94096_ ? this.f_93620_ + 4 : this.f_93620_;
         int i1 = this.f_94096_ ? this.f_93621_ + (this.f_93619_ - 8) / 2 : this.f_93621_;
         int j1 = l;
         if (k > s.length()) {
            k = s.length();
         }

         if (!s.isEmpty()) {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = this.f_94092_.m_92883_(matrixStack, s1, (float)l, (float)i1, -8355712);
         }

         boolean flag2 = this.f_94101_ < this.f_94093_.length() || this.f_94093_.length() >= 32;
         int k1 = j1;
         if (!flag) {
            k1 = j > 0 ? l + this.f_93618_ : l;
         } else if (flag2) {
            k1 = j1 - 1;
            j1--;
         }

         if (!s.isEmpty() && flag && j < s.length()) {
            this.f_94092_.m_92883_(matrixStack, s.substring(j), (float)j1, (float)i1, i2);
         }

         if (!flag2 && this.f_94088_ != null) {
            this.f_94092_.m_92883_(matrixStack, this.f_94088_, (float)(k1 - 1), (float)i1, -8355712);
         }

         if (flag1) {
            if (flag2) {
               GuiComponent.m_93172_(matrixStack, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
            } else {
               this.f_94092_.m_92883_(matrixStack, "_", (float)k1, (float)i1, i2);
            }
         }
      }
   }

   public boolean m_6375_(double clickedX, double clickedY, int mouseButton) {
      if (!this.m_94213_()) {
         return false;
      } else {
         boolean clickedThis = clickedX >= (double)this.f_93620_
            && clickedX < (double)(this.f_93620_ + this.f_93618_)
            && clickedY >= (double)this.f_93621_
            && clickedY < (double)(this.f_93621_ + this.f_93619_);
         if (this.f_94097_) {
            this.m_94178_(clickedThis);
         }

         if (this.m_93696_() && clickedThis && mouseButton == 0) {
            int i = Mth.m_14107_(clickedX) - this.f_93620_;
            if (this.f_94096_) {
               i -= 4;
            }

            String s = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
            this.m_94192_(this.f_94092_.m_92834_(s, i).length() + this.f_94100_);
            return true;
         } else if (!this.m_93696_() || mouseButton != 1) {
            return false;
         } else if (this.f_94093_.isEmpty()) {
            return clickedThis;
         } else {
            if (this.onClear != null) {
               this.onClear.apply("");
            }

            this.m_94144_("");
            return clickedThis;
         }
      }
   }

   public void setY(int i) {
      this.f_93621_ = i;
   }

   public int getX() {
      return this.f_93620_;
   }

   public int getY() {
      return this.f_93621_;
   }
}
