package shadows.apotheosis.adventure.event;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Event.HasResult;

public abstract class ItemSocketingEvent extends Event {
   protected final ItemStack stack;
   protected final ItemStack gem;

   public ItemSocketingEvent(ItemStack stack, ItemStack gem) {
      this.stack = stack.m_41777_();
      this.gem = gem.m_41777_();
   }

   public ItemStack getInputStack() {
      return this.stack;
   }

   public ItemStack getInputGem() {
      return this.gem;
   }

   @HasResult
   public static class CanSocket extends ItemSocketingEvent {
      public CanSocket(ItemStack inputStack, ItemStack inputGem) {
         super(inputStack, inputGem);
      }
   }

   public static class ModifyResult extends ItemSocketingEvent {
      protected ItemStack output;

      public ModifyResult(ItemStack stack, ItemStack gem, ItemStack output) {
         super(stack, gem);
         this.output = output;
      }

      public ItemStack getOutput() {
         return this.output;
      }

      public void setOutput(ItemStack output) {
         if (output.m_41619_()) {
            throw new IllegalArgumentException("Setting an empty output is undefined behavior");
         } else {
            this.output = output;
         }
      }
   }
}
