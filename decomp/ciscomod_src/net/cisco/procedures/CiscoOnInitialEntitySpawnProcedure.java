package net.cisco.procedures;

import net.cisco.network.CiscoModModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class CiscoOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "title @a times 20 100 20"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "title @a subtitle [\"\",{\"text\":\"Light \",\"bold\":true,\"color\":\"yellow\"},{\"text\":\"and \",\"bold\":true,\"color\":\"dark_red\"},{\"text\":\"Dark \",\"bold\":true,\"color\":\"black\"},{\"text\":\"Incarnate\",\"bold\":true,\"color\":\"dark_purple\"}]"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "title @a title {\"text\":\"Cisco\",\"bold\":true,\"color\":\"#EED836\"}"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "stopsound @a music"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "stopsound @a master cisco_mod:bravesoulboss"
            );
      }

      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:bravesoulboss")),
               SoundSource.MASTER,
               3.0F,
               1.0F
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:bravesoulboss")),
               SoundSource.MASTER,
               3.0F,
               1.0F,
               false
            );
         }
      }

      CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle = true;
      CiscoModModVariables.MapVariables.get(world).syncData(world);
   }
}
