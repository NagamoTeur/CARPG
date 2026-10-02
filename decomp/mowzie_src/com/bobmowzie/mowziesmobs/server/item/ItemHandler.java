package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.creativetab.CreativeTabHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityDart;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.ForgeRegistries.Keys;

@EventBusSubscriber(
   modid = "mowziesmobs",
   bus = Bus.MOD
)
public final class ItemHandler {
   @ObjectHolder(
      value = "mowziesmobs:foliaath_seed",
      registryName = "minecraft:item"
   )
   public static final ItemFoliaathSeed FOLIAATH_SEED = null;
   @ObjectHolder(
      value = "mowziesmobs:mob_remover",
      registryName = "minecraft:item"
   )
   public static final ItemMobRemover MOB_REMOVER = null;
   @ObjectHolder(
      value = "mowziesmobs:wrought_axe",
      registryName = "minecraft:item"
   )
   public static final ItemWroughtAxe WROUGHT_AXE = null;
   @ObjectHolder(
      value = "mowziesmobs:wrought_helmet",
      registryName = "minecraft:item"
   )
   public static final ItemWroughtHelm WROUGHT_HELMET = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_fury",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_FURY = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_fear",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_FEAR = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_rage",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_RAGE = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_bliss",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_BLISS = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_misery",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_MISERY = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_mask_faith",
      registryName = "minecraft:item"
   )
   public static final ItemUmvuthanaMask UMVUTHANA_MASK_FAITH = null;
   @ObjectHolder(
      value = "mowziesmobs:sol_visage",
      registryName = "minecraft:item"
   )
   public static final ItemSolVisage SOL_VISAGE = null;
   @ObjectHolder(
      value = "mowziesmobs:dart",
      registryName = "minecraft:item"
   )
   public static final ItemDart DART = null;
   @ObjectHolder(
      value = "mowziesmobs:spear",
      registryName = "minecraft:item"
   )
   public static final ItemSpear SPEAR = null;
   @ObjectHolder(
      value = "mowziesmobs:blowgun",
      registryName = "minecraft:item"
   )
   public static final ItemBlowgun BLOWGUN = null;
   @ObjectHolder(
      value = "mowziesmobs:grant_suns_blessing",
      registryName = "minecraft:item"
   )
   public static final ItemGrantSunsBlessing GRANT_SUNS_BLESSING = null;
   @ObjectHolder(
      value = "mowziesmobs:ice_crystal",
      registryName = "minecraft:item"
   )
   public static final ItemIceCrystal ICE_CRYSTAL = null;
   @ObjectHolder(
      value = "mowziesmobs:earth_talisman",
      registryName = "minecraft:item"
   )
   public static final ItemEarthTalisman EARTH_TALISMAN = null;
   @ObjectHolder(
      value = "mowziesmobs:captured_grottol",
      registryName = "minecraft:item"
   )
   public static final ItemCapturedGrottol CAPTURED_GROTTOL = null;
   @ObjectHolder(
      value = "mowziesmobs:glowing_jelly",
      registryName = "minecraft:item"
   )
   public static final ItemGlowingJelly GLOWING_JELLY = null;
   @ObjectHolder(
      value = "mowziesmobs:naga_fang",
      registryName = "minecraft:item"
   )
   public static final ItemNagaFang NAGA_FANG = null;
   @ObjectHolder(
      value = "mowziesmobs:naga_fang_dagger",
      registryName = "minecraft:item"
   )
   public static final ItemNagaFangDagger NAGA_FANG_DAGGER = null;
   @ObjectHolder(
      value = "mowziesmobs:earthbore_gauntlet",
      registryName = "minecraft:item"
   )
   public static final ItemEarthboreGauntlet EARTHBORE_GAUNTLET = null;
   @ObjectHolder(
      value = "mowziesmobs:logo",
      registryName = "minecraft:item"
   )
   public static final Item LOGO = null;
   @ObjectHolder(
      value = "mowziesmobs:music_disc_petiole",
      registryName = "minecraft:item"
   )
   public static final RecordItem PETIOLE_MUSIC_DISC = null;
   @ObjectHolder(
      value = "mowziesmobs:foliaath_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem FOLIAATH_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:wroughtnaut_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem WROUGHTNAUT_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem UMVUTHANA_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_raptor_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem UMVUTHANA_RAPTOR_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthana_crane_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem UMVUTHANA_CRANE_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:umvuthi_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem UMVUTHI_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:frostmaw_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem FROSTMAW_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:grottol_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem GROTTOL_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:lantern_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem LANTERN_SPAWN_EGG = null;
   @ObjectHolder(
      value = "mowziesmobs:naga_spawn_egg",
      registryName = "minecraft:item"
   )
   public static final ForgeSpawnEggItem NAGA_SPAWN_EGG = null;
   public static Style TOOLTIP_STYLE = Style.f_131099_.m_131148_(TextColor.m_131270_(ChatFormatting.GRAY));

   private ItemHandler() {
   }

   @SubscribeEvent
   public static void register(RegisterEvent event) {
      event.register(
         Keys.ITEMS,
         helper -> {
            helper.register("foliaath_seed", new ItemFoliaathSeed(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("mob_remover", new ItemMobRemover(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("wrought_axe", new ItemWroughtAxe(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41497_(Rarity.UNCOMMON)));
            helper.register("wrought_helmet", new ItemWroughtHelm(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41497_(Rarity.UNCOMMON)));
            helper.register("umvuthana_mask_fury", new ItemUmvuthanaMask(MaskType.FURY, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("umvuthana_mask_fear", new ItemUmvuthanaMask(MaskType.FEAR, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("umvuthana_mask_rage", new ItemUmvuthanaMask(MaskType.RAGE, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("umvuthana_mask_bliss", new ItemUmvuthanaMask(MaskType.BLISS, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("umvuthana_mask_misery", new ItemUmvuthanaMask(MaskType.MISERY, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("umvuthana_mask_faith", new ItemUmvuthanaMask(MaskType.FAITH, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("sol_visage", new ItemSolVisage(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41497_(Rarity.RARE)));
            helper.register("dart", new ItemDart(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("spear", new ItemSpear(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1)));
            helper.register("blowgun", new ItemBlowgun(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1).m_41503_(300)));
            helper.register(
               "grant_suns_blessing",
               new ItemGrantSunsBlessing(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1).m_41497_(Rarity.EPIC))
            );
            helper.register(
               "ice_crystal",
               new ItemIceCrystal(
                  new Properties()
                     .m_41491_(CreativeTabHandler.INSTANCE.creativeTab)
                     .m_41499_(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durabilityValue)
                     .m_41497_(Rarity.RARE)
               )
            );
            helper.register(
               "earth_talisman", new ItemEarthTalisman(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1).m_41497_(Rarity.EPIC))
            );
            helper.register("captured_grottol", new ItemCapturedGrottol(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1)));
            helper.register(
               "glowing_jelly",
               new ItemGlowingJelly(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41489_(ItemGlowingJelly.GLOWING_JELLY_FOOD))
            );
            helper.register("naga_fang", new ItemNagaFang(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register("naga_fang_dagger", new ItemNagaFangDagger(new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab)));
            helper.register(
               "earthbore_gauntlet",
               new ItemEarthboreGauntlet(
                  new Properties()
                     .m_41491_(CreativeTabHandler.INSTANCE.creativeTab)
                     .m_41499_(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.durabilityValue)
                     .m_41497_(Rarity.RARE)
               )
            );
            helper.register("logo", new Item(new Properties()));
            helper.register(
               "music_disc_petiole",
               new RecordItem(
                  14, MMSounds.MUSIC_PETIOLE, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab).m_41487_(1).m_41497_(Rarity.RARE), 0
               )
            );
            helper.register(
               "foliaath_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.FOLIAATH, 4705339, 12598220, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "wroughtnaut_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.WROUGHTNAUT, 9211020, 16777215, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "umvuthana_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_MINION, 12214046, 3813167, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "umvuthana_raptor_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_RAPTOR, 12214046, 16184049, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "umvuthana_crane_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_CRANE, 12214046, 16637046, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "umvuthi_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.UMVUTHI, 16184049, 12214046, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "frostmaw_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.FROSTMAW, 16251647, 11521535, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "grottol_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.GROTTOL, 7829367, 12378367, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "lantern_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.LANTERN, 7203328, 2316816, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "naga_spawn_egg",
               new ForgeSpawnEggItem(EntityHandler.NAGA, 1394768, 9295705, new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "painted_acacia", new BlockItem((Block)BlockHandler.PAINTED_ACACIA.get(), new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register(
               "painted_acacia_slab",
               new BlockItem((Block)BlockHandler.PAINTED_ACACIA_SLAB.get(), new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
            helper.register("thatch_block", new BlockItem((Block)BlockHandler.THATCH.get(), new Properties()));
            helper.register(
               "clawed_log", new BlockItem((Block)BlockHandler.CLAWED_LOG.get(), new Properties().m_41491_(CreativeTabHandler.INSTANCE.creativeTab))
            );
         }
      );
   }

   public static void initializeAttributes() {
      WROUGHT_AXE.getAttributesFromConfig();
      WROUGHT_HELMET.getAttributesFromConfig();
      UMVUTHANA_MASK_FURY.getAttributesFromConfig();
      UMVUTHANA_MASK_FEAR.getAttributesFromConfig();
      UMVUTHANA_MASK_RAGE.getAttributesFromConfig();
      UMVUTHANA_MASK_BLISS.getAttributesFromConfig();
      UMVUTHANA_MASK_MISERY.getAttributesFromConfig();
      UMVUTHANA_MASK_FAITH.getAttributesFromConfig();
      SOL_VISAGE.getAttributesFromConfig();
      SPEAR.getAttributesFromConfig();
      NAGA_FANG_DAGGER.getAttributesFromConfig();
      EARTHBORE_GAUNTLET.getAttributesFromConfig();
   }

   public static void initializeDispenserBehaviors() {
      DispenserBlock.m_52672_(
         DART,
         new AbstractProjectileDispenseBehavior() {
            protected Projectile m_6895_(Level worldIn, Position position, ItemStack stackIn) {
               EntityDart dartentity = new EntityDart(
                  (EntityType<? extends EntityDart>)EntityHandler.DART.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_()
               );
               dartentity.f_36705_ = Pickup.ALLOWED;
               return dartentity;
            }
         }
      );
   }
}
