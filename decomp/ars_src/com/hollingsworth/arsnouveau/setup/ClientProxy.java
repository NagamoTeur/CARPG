package com.hollingsworth.arsnouveau.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientProxy implements IProxy {
   @Override
   public void init() {
   }

   @Override
   public Level getClientWorld() {
      return Minecraft.m_91087_().f_91073_;
   }

   @Override
   public Player getPlayer() {
      return Minecraft.m_91087_().f_91074_;
   }

   @Override
   public Minecraft getMinecraft() {
      return Minecraft.m_91087_();
   }
}
