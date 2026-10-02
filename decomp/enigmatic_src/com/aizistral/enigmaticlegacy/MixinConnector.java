package com.aizistral.enigmaticlegacy;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
   public void connect() {
      Mixins.addConfigurations(new String[]{"enigmaticlegacy.mixins.json"});
   }
}
