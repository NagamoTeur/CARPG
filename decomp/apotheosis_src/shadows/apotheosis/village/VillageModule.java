package shadows.apotheosis.village;

import java.io.File;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.village.fletching.ApothFletchingBlock;
import shadows.apotheosis.village.fletching.FletchingContainer;
import shadows.apotheosis.village.fletching.FletchingRecipe;
import shadows.apotheosis.village.fletching.arrows.BroadheadArrowEntity;
import shadows.apotheosis.village.fletching.arrows.BroadheadArrowItem;
import shadows.apotheosis.village.fletching.arrows.ExplosiveArrowEntity;
import shadows.apotheosis.village.fletching.arrows.ExplosiveArrowItem;
import shadows.apotheosis.village.fletching.arrows.IApothArrowItem;
import shadows.apotheosis.village.fletching.arrows.MiningArrowEntity;
import shadows.apotheosis.village.fletching.arrows.MiningArrowItem;
import shadows.apotheosis.village.fletching.arrows.ObsidianArrowEntity;
import shadows.apotheosis.village.fletching.arrows.ObsidianArrowItem;
import shadows.apotheosis.village.wanderer.WandererReplacements;
import shadows.placebo.config.Configuration;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.RegistryEvent.Register;

public class VillageModule {
   public static final RecipeSerializer<FletchingRecipe> FLETCHING_SERIALIZER = new FletchingRecipe.Serializer();
   public static final Logger LOGGER = LogManager.getLogger("Apotheosis : Village");
   public static Configuration config;
   public static BlockInteraction expArrowMode = BlockInteraction.DESTROY;

   @SubscribeEvent
   public void setup(FMLCommonSetupEvent e) {
      config = new Configuration(new File(Apotheosis.configDir, "village.cfg"));
      config.setTitle("Apotheosis Village Module Configuration");
      WandererReplacements.load(config);
      boolean blockDmg = config.getBoolean("Explosive Arrow Block Damage", "arrows", true, "If explosive arrows can break blocks.\nServer-authoritative.");
      expArrowMode = blockDmg ? BlockInteraction.DESTROY : BlockInteraction.NONE;
      if (config.hasChanged()) {
         config.save();
      }

      e.enqueueWork(() -> {
         for (final Item i : ForgeRegistries.ITEMS) {
            if (i instanceof IApothArrowItem) {
               DispenserBlock.m_52672_(i, new AbstractProjectileDispenseBehavior() {
                  protected Projectile m_6895_(Level world, Position pos, ItemStack stack) {
                     return ((IApothArrowItem)i).fromDispenser(world, pos.m_7096_(), pos.m_7098_(), pos.m_7094_());
                  }
               });
            }
         }
      });
   }

   @SubscribeEvent
   public void setup(FMLClientSetupEvent e) {
      e.enqueueWork(VillageModuleClient::init);
   }

   @SubscribeEvent
   public void serializers(Register<RecipeSerializer<?>> e) {
      e.getRegistry().register(FLETCHING_SERIALIZER, FletchingRecipe.Serializer.NAME);
   }

   @SubscribeEvent
   public void blocks(Register<Block> e) {
      PlaceboUtil.registerOverride(Blocks.f_50622_, new ApothFletchingBlock(), "apotheosis");
   }

   @SubscribeEvent
   public void items(Register<Item> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new ObsidianArrowItem(),
               "obsidian_arrow",
               new BroadheadArrowItem(),
               "broadhead_arrow",
               new ExplosiveArrowItem(),
               "explosive_arrow",
               new MiningArrowItem(() -> Items.f_42385_, MiningArrowEntity.Type.IRON),
               "iron_mining_arrow",
               new MiningArrowItem(() -> Items.f_42390_, MiningArrowEntity.Type.DIAMOND),
               "diamond_mining_arrow"
            }
         );
   }

   @SubscribeEvent
   public void entities(Register<EntityType<?>> e) {
      e.getRegistry()
         .register(
            Builder.m_20704_(ObsidianArrowEntity::new, MobCategory.MISC)
               .setShouldReceiveVelocityUpdates(true)
               .setTrackingRange(4)
               .setUpdateInterval(20)
               .m_20699_(0.5F, 0.5F)
               .setCustomClientFactory((se, w) -> new ObsidianArrowEntity(w))
               .m_20712_("obsidian_arrow"),
            "obsidian_arrow"
         );
      e.getRegistry()
         .register(
            Builder.m_20704_(BroadheadArrowEntity::new, MobCategory.MISC)
               .setShouldReceiveVelocityUpdates(true)
               .setTrackingRange(4)
               .setUpdateInterval(20)
               .m_20699_(0.5F, 0.5F)
               .setCustomClientFactory((se, w) -> new BroadheadArrowEntity(w))
               .m_20712_("broadhead_arrow"),
            "broadhead_arrow"
         );
      e.getRegistry()
         .register(
            Builder.m_20704_(ExplosiveArrowEntity::new, MobCategory.MISC)
               .setShouldReceiveVelocityUpdates(true)
               .setTrackingRange(4)
               .setUpdateInterval(20)
               .m_20699_(0.5F, 0.5F)
               .setCustomClientFactory((se, w) -> new ExplosiveArrowEntity(w))
               .m_20712_("explosive_arrow"),
            "explosive_arrow"
         );
      e.getRegistry()
         .register(
            Builder.m_20704_(MiningArrowEntity::new, MobCategory.MISC)
               .setShouldReceiveVelocityUpdates(true)
               .setTrackingRange(4)
               .setUpdateInterval(20)
               .m_20699_(0.5F, 0.5F)
               .setCustomClientFactory((se, w) -> new MiningArrowEntity(w))
               .m_20712_("mining_arrow"),
            "mining_arrow"
         );
   }

   @SubscribeEvent
   public void containers(Register<MenuType<?>> e) {
      e.getRegistry().register(new MenuType(FletchingContainer::new), "fletching");
   }
}
