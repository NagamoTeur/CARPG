package com.hollingsworth.arsnouveau.common.command;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.logging.log4j.LogManager;

public class DataDumpCommand {
   public static final Path PATH_AUGMENT_COMPATIBILITY = Paths.get("ars_nouveau", "augment_compatibility.csv");

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ars-data").requires(sender -> sender.m_6761_(2)))
            .then(Commands.m_82127_("dump").then(Commands.m_82127_("augment-compatibility-csv").executes(DataDumpCommand::dumpAugmentCompat)))
      );
   }

   public static int dumpAugmentCompat(CommandContext<CommandSourceStack> context) {
      Map<ResourceLocation, AbstractSpellPart> spells = ArsNouveauAPI.getInstance().getSpellpartMap();
      List<AbstractAugment> augments = spells.values()
         .stream()
         .filter(p -> p instanceof AbstractAugment)
         .map(p -> (AbstractAugment)p)
         .sorted(Comparator.comparing(AbstractSpellPart::getRegistryName))
         .toList();
      List<Tuple<AbstractSpellPart, Set<AbstractAugment>>> augmentCompat = spells.values()
         .stream()
         .filter(partx -> partx instanceof AbstractCastMethod)
         .map(partx -> new Tuple(partx, partx.compatibleAugments))
         .sorted(Comparator.comparing(t -> ((AbstractSpellPart)t.m_14418_()).getRegistryName()))
         .collect(Collectors.toList());
      augmentCompat.addAll(
         spells.values()
            .stream()
            .filter(partx -> partx instanceof AbstractEffect)
            .map(partx -> new Tuple(partx, partx.compatibleAugments))
            .sorted(Comparator.comparing(t -> ((AbstractSpellPart)t.m_14418_()).getRegistryName()))
            .toList()
      );
      File file = PATH_AUGMENT_COMPATIBILITY.toFile();

      try {
         Files.createDirectories(PATH_AUGMENT_COMPATIBILITY.getParent());
         PrintWriter w = new PrintWriter(new FileWriterWithEncoding(file, "UTF-8", false));
         w.println("glyph, " + augments.stream().map(a -> a.getRegistryName().toString()).collect(Collectors.joining(", ")));

         for (Tuple<AbstractSpellPart, Set<AbstractAugment>> row : augmentCompat) {
            AbstractSpellPart part = (AbstractSpellPart)row.m_14418_();
            Set<AbstractAugment> compatibleAugments = (Set<AbstractAugment>)row.m_14419_();
            w.print(part.getRegistryName() + ", ");
            w.print(augments.stream().map(a -> compatibleAugments.contains(a) ? "T" : "F").collect(Collectors.joining(", ")));
            w.println();
         }

         w.close();
      } catch (IOException var10) {
         LogManager.getLogger("ars_nouveau").error("Unable to dump augment compatibility chart", var10);
         ((CommandSourceStack)context.getSource()).m_81352_(Component.m_237113_("Error when trying to produce the data dump.  Check the logs."));
         return 0;
      } catch (Exception var11) {
         LogManager.getLogger("ars_nouveau").error("Exception caught when trying to dump data", var11);
         ((CommandSourceStack)context.getSource()).m_81352_(Component.m_237113_("Error when trying to produce the data dump.  Check the logs."));
         throw var11;
      }

      ((CommandSourceStack)context.getSource()).m_81354_(Component.m_237113_("Dumped data to " + file), true);
      return 1;
   }
}
