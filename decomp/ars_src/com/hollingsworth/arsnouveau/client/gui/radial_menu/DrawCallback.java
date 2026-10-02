package com.hollingsworth.arsnouveau.client.gui.radial_menu;

import com.mojang.blaze3d.vertex.PoseStack;

public interface DrawCallback<T> {
   void accept(T var1, PoseStack var2, int var3, int var4, int var5, boolean var6);
}
