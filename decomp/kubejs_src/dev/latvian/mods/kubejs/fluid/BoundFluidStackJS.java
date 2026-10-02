package dev.latvian.mods.kubejs.fluid;

import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class BoundFluidStackJS extends FluidStackJS {
   private final FluidStack fluidStack;

   public BoundFluidStackJS(FluidStack fs) {
      this.fluidStack = fs;
   }

   @Override
   public String getId() {
      return Registries.getId(this.fluidStack.getFluid(), Registry.f_122899_).toString();
   }

   @Override
   public Fluid getFluid() {
      return this.fluidStack.getFluid();
   }

   @Override
   public FluidStack getFluidStack() {
      return this.fluidStack;
   }

   @Override
   public long kjs$getAmount() {
      return this.fluidStack.getAmount();
   }

   @Override
   public void setAmount(long amount) {
      this.fluidStack.setAmount(amount);
   }

   @Nullable
   @Override
   public CompoundTag getNbt() {
      return this.fluidStack.getTag();
   }

   @Override
   public void setNbt(@Nullable CompoundTag nbt) {
      this.fluidStack.setTag(nbt);
   }

   @Override
   public FluidStackJS kjs$copy(long amount) {
      FluidStack fs = this.fluidStack.copy();
      fs.setAmount(amount);
      return new BoundFluidStackJS(fs);
   }
}
