package daripher.skilltree.network.message;

import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.client.screen.SkillTreeScreen;
import daripher.skilltree.data.reloader.SkillsReloader;
import daripher.skilltree.skill.PassiveSkill;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncPlayerSkillsMessage {
   private List<ResourceLocation> learnedSkills = new ArrayList<>();
   private int skillPoints;

   private SyncPlayerSkillsMessage() {
   }

   public SyncPlayerSkillsMessage(Player player) {
      IPlayerSkills skillsCapability = PlayerSkillsProvider.get(player);
      this.learnedSkills = skillsCapability.getPlayerSkills().stream().map(PassiveSkill::getId).toList();
      this.skillPoints = skillsCapability.getSkillPoints();
   }

   public static SyncPlayerSkillsMessage decode(FriendlyByteBuf buf) {
      SyncPlayerSkillsMessage result = new SyncPlayerSkillsMessage();
      int learnedSkillsCount = buf.readInt();

      for (int i = 0; i < learnedSkillsCount; i++) {
         result.learnedSkills.add(new ResourceLocation(buf.m_130277_()));
      }

      result.skillPoints = buf.readInt();
      return result;
   }

   public static void receive(SyncPlayerSkillsMessage message, Supplier<Context> ctxSupplier) {
      Context ctx = ctxSupplier.get();
      ctx.setPacketHandled(true);
      ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(message, ctx)));
   }

   @OnlyIn(Dist.CLIENT)
   private static void handlePacket(SyncPlayerSkillsMessage message, Context ctx) {
      ctx.setPacketHandled(true);
      Minecraft minecraft = Minecraft.m_91087_();

      assert minecraft.f_91074_ != null;

      IPlayerSkills capability = PlayerSkillsProvider.get(minecraft.f_91074_);
      capability.getPlayerSkills().clear();
      message.learnedSkills.stream().map(SkillsReloader::getSkillById).filter(Objects::nonNull).forEach(capability.getPlayerSkills()::add);
      capability.setSkillPoints(message.skillPoints);
      if (minecraft.f_91080_ instanceof SkillTreeScreen screen) {
         screen.skillPoints = capability.getSkillPoints() - screen.newlyLearnedSkills.size();
         screen.m_7856_();
      }
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.learnedSkills.size());
      this.learnedSkills.stream().map(ResourceLocation::toString).forEach(buf::m_130070_);
      buf.writeInt(this.skillPoints);
   }
}
