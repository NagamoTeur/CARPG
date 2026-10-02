package net.cisco.procedures;

import net.cisco.network.CiscoModModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class KeystoneRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity.f_19853_.m_46472_() == Level.f_46428_) {
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:teleport")),
                     SoundSource.NEUTRAL,
                     0.7F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:teleport")),
                     SoundSource.NEUTRAL,
                     0.7F,
                     1.0F,
                     false
                  );
               }
            }

            entity.m_6021_(
               ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new CiscoModModVariables.PlayerVariables()))
                  .PosX,
               ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new CiscoModModVariables.PlayerVariables()))
                  .PosY,
               ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new CiscoModModVariables.PlayerVariables()))
                  .PosZ
            );
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_
                  .m_9774_(
                     ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new CiscoModModVariables.PlayerVariables()))
                        .PosX,
                     ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new CiscoModModVariables.PlayerVariables()))
                        .PosY,
                     ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new CiscoModModVariables.PlayerVariables()))
                        .PosZ,
                     entity.m_146908_(),
                     entity.m_146909_()
                  );
            }

            if (itemstack.m_220157_(1, RandomSource.m_216327_(), null)) {
               itemstack.m_41774_(1);
               itemstack.m_41721_(0);
            }

            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 300);
            }
         } else if (entity instanceof Player _player && !_player.f_19853_.m_5776_()) {
            _player.m_5661_(Component.m_237113_("Spatial magic doesn't seem to work here."), true);
         }
      }
   }
}
