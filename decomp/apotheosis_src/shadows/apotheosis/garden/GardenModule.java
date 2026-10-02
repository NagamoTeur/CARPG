package shadows.apotheosis.garden;

import java.io.File;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.placebo.config.Configuration;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.RegistryEvent.Register;

public class GardenModule {
   public static int maxCactusHeight = 5;
   public static int maxReedHeight = 255;
   public static int maxBambooHeight = 32;

   @SubscribeEvent
   public void setup(FMLCommonSetupEvent e) {
      this.reload(null);
      Apotheosis.HELPER.registerProvider(factory -> factory.addShapeless(Apoth.Items.ENDER_LEAD, new Object[]{Items.f_42584_, Items.f_42655_, Items.f_42417_}));
      MinecraftForge.EVENT_BUS.addListener(this::reload);
   }

   @SubscribeEvent
   public void blocks(Register<Block> e) {
      PlaceboUtil.registerOverride(Blocks.f_50128_, new ApothCactusBlock(), "apotheosis");
      PlaceboUtil.registerOverride(Blocks.f_50130_, new ApothSugarcaneBlock(), "apotheosis");
      PlaceboUtil.registerOverride(Blocks.f_50571_, new ApothBambooBlock(), "apotheosis");
   }

   @SubscribeEvent
   public void items(Register<Item> e) {
      e.getRegistry().register(new EnderLeadItem(), "ender_lead");
      ComposterBlock.f_51914_.put(Blocks.f_50128_.m_5456_(), 0.5F);
      ComposterBlock.f_51914_.put(Blocks.f_50130_.m_5456_(), 0.5F);
   }

   public void reload(Apotheosis.ApotheosisReloadEvent e) {
      Configuration c = new Configuration(new File(Apotheosis.configDir, "garden.cfg"));
      c.setTitle("Apotheosis Garden Module Configuration");
      maxCactusHeight = c.getInt(
         "Cactus Height",
         "general",
         maxCactusHeight,
         1,
         512,
         "The max height a stack of cacti may grow to.  Vanilla is 3.  Values greater than 32 are uncapped growth.\nServer-authoritative."
      );
      maxReedHeight = c.getInt(
         "Reed Height",
         "general",
         maxReedHeight,
         1,
         512,
         "The max height a stack of reeds may grow to.  Vanilla is 3.  Values greater than 32 are uncapped growth.\nServer-authoritative."
      );
      maxBambooHeight = c.getInt(
         "Bamboo Height", "general", maxBambooHeight, 1, 64, "The max height a stack of bamboo may grow to.  Vanilla is 16.\nServer-authoritative."
      );
      if (e == null && c.hasChanged()) {
         c.save();
      }
   }
}
