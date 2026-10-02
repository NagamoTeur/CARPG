package com.bobmowzie.mowziesmobs.server.entity.grottol;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.message.MessageBlackPinkInYourArea;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public final class BlackPinkInYourArea implements BiConsumer<Level, AbstractMinecart> {
   private BlackPinkInYourArea() {
   }

   public void accept(Level world, AbstractMinecart minecart) {
      MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> minecart), new MessageBlackPinkInYourArea(minecart));
   }

   public static BlackPinkInYourArea create() {
      return new BlackPinkInYourArea();
   }
}
