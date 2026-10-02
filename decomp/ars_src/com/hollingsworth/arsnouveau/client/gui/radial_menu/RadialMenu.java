package com.hollingsworth.arsnouveau.client.gui.radial_menu;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.IntConsumer;

public class RadialMenu<T> {
   private final IntConsumer setSelectedSlot;
   private final List<RadialMenuSlot<T>> radialMenuSlots;
   private final boolean showMoreSecondaryItems;
   private final SecondaryIconPosition secondaryIconStartingPosition;
   private final DrawCallback<T> drawCallback;
   private final int offset;

   public RadialMenu(IntConsumer setSelectedSlot, List<RadialMenuSlot<T>> radialMenuSlots, DrawCallback<T> drawCallback, int offset) {
      this.setSelectedSlot = setSelectedSlot;
      this.radialMenuSlots = radialMenuSlots;
      this.showMoreSecondaryItems = false;
      this.secondaryIconStartingPosition = SecondaryIconPosition.NORTH;
      this.drawCallback = drawCallback;
      this.offset = offset;
   }

   public RadialMenu(
      IntConsumer setSelectedSlot,
      List<RadialMenuSlot<T>> radialMenuSlots,
      SecondaryIconPosition secondaryIconStartingPosition,
      DrawCallback<T> drawCallback,
      int offset
   ) {
      this.setSelectedSlot = setSelectedSlot;
      this.radialMenuSlots = radialMenuSlots;
      this.showMoreSecondaryItems = true;
      this.secondaryIconStartingPosition = secondaryIconStartingPosition;
      this.drawCallback = drawCallback;
      this.offset = offset;
   }

   public List<RadialMenuSlot<T>> getRadialMenuSlots() {
      return this.radialMenuSlots;
   }

   public void setCurrentSlot(int slot) {
      this.setSelectedSlot.accept(slot);
   }

   public boolean isShowMoreSecondaryItems() {
      return this.showMoreSecondaryItems;
   }

   public SecondaryIconPosition getSecondaryIconStartingPosition() {
      return this.secondaryIconStartingPosition;
   }

   public void drawIcon(T objectToBeDrawn, PoseStack poseStack, int positionX, int positionY, int size) {
      this.drawCallback.accept(objectToBeDrawn, poseStack, positionX, positionY, size, false);
   }

   public int getOffset() {
      return this.offset;
   }
}
