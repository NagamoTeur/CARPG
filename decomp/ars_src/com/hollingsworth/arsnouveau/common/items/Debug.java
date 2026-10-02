package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.common.entity.debug.IDebuggerProvider;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.commons.io.output.FileWriterWithEncoding;

public class Debug extends ModItem {
   public static final Path DEBUG_LOG = Paths.get("ars_nouveau", "augment_compatibility.csv");

   public Debug() {
      super(new Properties());
   }

   public InteractionResult m_6880_(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
      if (!pPlayer.f_19853_.f_46443_ && pInteractionTarget instanceof IDebuggerProvider iDebuggerProvider) {
         try {
            Path path = Paths.get("ars_nouveau", "entity_log_" + System.currentTimeMillis() + ".log");
            File file = path.toFile();
            Files.createDirectories(path.getParent());
            PrintWriter w = new PrintWriter(new FileWriterWithEncoding(file, "UTF-8", false));
            iDebuggerProvider.getDebugger().writeFile(w);
            PortUtil.sendMessage(pPlayer, Component.m_237110_("arsnouveau.debug.log_created", new Object[]{path.toString()}));
            w.close();
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      return super.m_6880_(pStack, pPlayer, pInteractionTarget, pUsedHand);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return super.m_6225_(context);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player playerIn, InteractionHand handIn) {
      return InteractionResultHolder.m_19090_(playerIn.m_21120_(handIn));
   }
}
