package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class StopSoundIFDiesProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player.f_19853_, event.player.m_20185_(), event.player.m_20186_(), event.player.m_20189_());
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z) {
      execute(null, world, x, y, z);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
      if (!CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle && world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "stopsound @a music cisco_mod:bravesoulboss"
            );
      }

      if (!CiscoModModVariables.MapVariables.get(world).FellKingLives && world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "stopsound @a music cisco_mod:whereisyourgodboss"
            );
      }
   }
}
