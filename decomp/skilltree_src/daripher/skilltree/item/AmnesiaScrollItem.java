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

public class AmnesiaScrollItem extends Item {
   public AmnesiaScrollItem() {
      super(new Properties().m_41491_(PSTCreativeTabs.SKILLTREE));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack scroll = player.m_21120_(hand);
      IPlayerSkills skills = PlayerSkillsProvider.get(player);
      if (!player.m_150110_().f_35937_) {
         scroll.m_41774_(1);
      }

      if (!level.f_46443_) {
         level.m_6269_(null, player, SoundEvents.f_11713_, player.m_5720_(), 0.9F, 0.7F + player.m_217043_().m_188501_() * 0.3F);
         level.m_6269_(null, player, SoundEvents.f_215671_, player.m_5720_(), 0.4F, 0.2F + player.m_217043_().m_188501_() * 0.2F);
         skills.resetTree((ServerPlayer)player);
         skills.setSkillPoints((int)((double)skills.getSkillPoints() * (1.0 - Config.amnesia_scroll_penalty)));
         player.m_213846_(Component.m_237115_("skilltree.message.reset_command").m_130940_(ChatFormatting.YELLOW));
         NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new SyncPlayerSkillsMessage(player));
      }

      return InteractionResultHolder.m_19092_(scroll, level.f_46443_);
   }

   public void m_7373_(@NotNull ItemStack itemStack, Level level, List<Component> components, @NotNull TooltipFlag tooltipFlag) {
      components.add(Component.m_237115_(this.m_5524_() + ".tooltip").m_130940_(ChatFormatting.GOLD));
      double penalty = Config.amnesia_scroll_penalty;
      if (penalty > 0.0) {
         int textPenalty = (int)(penalty * 100.0);
         components.add(Component.m_237110_(this.m_5524_() + ".warning", new Object[]{textPenalty}).m_130940_(ChatFormatting.RED));
      }
   }
}
