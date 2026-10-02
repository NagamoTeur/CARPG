package daripher.skilltree.item;

import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.config.Config;
import daripher.skilltree.init.PSTCreativeTabs;
import daripher.skilltree.network.NetworkDispatcher;
import daripher.skilltree.network.message.SyncPlayerSkillsMessage;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class WisdomScrollItem extends Item {
   public WisdomScrollItem() {
      super(new Properties().m_41491_(PSTCreativeTabs.SKILLTREE));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack itemInHand = player.m_21120_(hand);
      IPlayerSkills skillsCapability = PlayerSkillsProvider.get(player);
      int totalSkillPoints = skillsCapability.getPlayerSkills().size() + skillsCapability.getSkillPoints();
      if (totalSkillPoints >= Config.max_skill_points) {
         return InteractionResultHolder.m_19100_(itemInHand);
      } else {
         if (!player.m_150110_().f_35937_) {
            itemInHand.m_41774_(1);
         }

         if (!level.f_46443_) {
            level.m_6269_(null, player, SoundEvents.f_11713_, player.m_5720_(), 0.9F, 0.7F + player.m_217043_().m_188501_() * 0.3F);
            level.m_6269_(null, player, SoundEvents.f_12275_, player.m_5720_(), 0.4F, 0.2F + player.m_217043_().m_188501_() * 0.3F);
            skillsCapability.grantSkillPoints(1);
            NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new SyncPlayerSkillsMessage(player));
            if (Config.show_chat_messages) {
               player.m_213846_(Component.m_237115_("skilltree.message.point_command").m_130940_(ChatFormatting.YELLOW));
            }
         }

         return InteractionResultHolder.m_19092_(itemInHand, level.f_46443_);
      }
   }

   public void m_7373_(@NotNull ItemStack itemStack, Level level, List<Component> components, @NotNull TooltipFlag tooltipFlag) {
      components.add(Component.m_237115_(this.m_5524_() + ".tooltip").m_130940_(ChatFormatting.GOLD));
   }
}
