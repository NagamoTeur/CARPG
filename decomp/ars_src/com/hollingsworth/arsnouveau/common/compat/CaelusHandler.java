package com.hollingsworth.arsnouveau.common.compat;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.caelus.api.CaelusApi;

public class CaelusHandler {
   public static void setFlying(Player entity) {
      AttributeInstance attributeInstance = entity.m_21051_(CaelusApi.getInstance().getFlightAttribute());
      if (attributeInstance != null && !attributeInstance.m_22109_(CaelusApi.getInstance().getElytraModifier())) {
         attributeInstance.m_22118_(CaelusApi.getInstance().getElytraModifier());
      }
   }
}
