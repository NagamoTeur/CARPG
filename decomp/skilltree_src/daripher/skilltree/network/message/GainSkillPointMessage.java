package daripher.skilltree.network.message;

import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.config.Config;
import daripher.skilltree.network.NetworkDispatcher;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

public class GainSkillPointMessage {
   public static GainSkillPointMessage decode(FriendlyByteBuf buf) {
      return new GainSkillPointMessage();
   }

   public static void receive(GainSkillPointMessage message, Supplier<Context> ctxSupplier) {
      Context ctx = ctxSupplier.get();
      ctx.setPacketHandled(true);
      ServerPlayer player = Objects.requireNonNull(ctx.getSender());
      IPlayerSkills capability = PlayerSkillsProvider.get(player);
      int skills = capability.getPlayerSkills().size();
      int points = capability.getSkillPoints();
      int level = skills + points;
      if (level < Config.max_skill_points) {
         int cost = Config.getSkillPointCost(level);
         if (player.f_36079_ >= cost) {
            player.m_6756_(-cost);
            capability.grantSkillPoints(1);
            NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerSkillsMessage(player));
         }
      }
   }

   public void encode(FriendlyByteBuf buf) {
   }
}
