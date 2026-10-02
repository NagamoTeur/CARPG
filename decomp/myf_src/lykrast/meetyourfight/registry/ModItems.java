package lykrast.meetyourfight.registry;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.BellringerEntity;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import lykrast.meetyourfight.entity.RosalyneEntity;
import lykrast.meetyourfight.entity.SwampjawEntity;
import lykrast.meetyourfight.item.BoneRaker;
import lykrast.meetyourfight.item.CocktailCutlass;
import lykrast.meetyourfight.item.CurioBaseItem;
import lykrast.meetyourfight.item.DepthStar;
import lykrast.meetyourfight.item.LuckCurio;
import lykrast.meetyourfight.item.PassagesToll;
import lykrast.meetyourfight.item.SpectresEye;
import lykrast.meetyourfight.item.SpectresGrasp;
import lykrast.meetyourfight.item.SummonItem;
import lykrast.meetyourfight.item.TwilightsThorn;
import lykrast.meetyourfight.item.WiltedIdeals;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
   public static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, "meetyourfight");
   public static RegistryObject<Item> hauntedBell = REG.register("haunted_bell", () -> new SummonItem(noStack(), BellringerEntity::spawn));
   public static RegistryObject<Item> phantoplasm = REG.register("phantoplasm", () -> new Item(boss()));
   public static RegistryObject<Item> passagesToll = REG.register("passages_toll", () -> new PassagesToll(bossNS()));
   public static RegistryObject<Item> spectresEye = REG.register("spectres_eye", () -> new SpectresEye(bossNS()));
   public static RegistryObject<Item> spectresGrasp = REG.register("spectres_grasp", () -> new SpectresGrasp(bossNS()));
   public static RegistryObject<Item> aetherGlazedCupcake = REG.register(
      "aether_glazed_cupcake",
      () -> new Item(
            boss().m_41489_(new Builder().m_38760_(5).m_38758_(0.6F).m_38765_().effect(() -> new MobEffectInstance(MobEffects.f_19620_, 100), 1.0F).m_38767_())
         )
   );
   public static RegistryObject<Item> devilsAnte = REG.register("devils_ante", () -> new SummonItem(noStack(), DameFortunaEntity::spawn));
   public static RegistryObject<Item> fortunesFavor = REG.register("fortunes_favor", () -> new Item(boss()));
   public static RegistryObject<Item> slicersDice = REG.register("slicers_dice", () -> new LuckCurio(bossNS()));
   public static RegistryObject<Item> aceOfIron = REG.register("ace_of_iron", () -> new LuckCurio(bossNS()));
   public static RegistryObject<Item> cocktailCutlass = REG.register("cocktail_cutlass", () -> new CocktailCutlass(bossNS()));
   public static RegistryObject<Item> velvetFortune = REG.register(
      "velvet_fortune",
      () -> new Item(
            boss()
               .m_41489_(new Builder().m_38760_(2).m_38758_(0.1F).m_38765_().effect(() -> new MobEffectInstance(MobEffects.f_19621_, 12000), 1.0F).m_38767_())
         )
   );
   public static RegistryObject<Item> fossilBait = REG.register("fossil_bait", () -> new SummonItem(noStack(), SwampjawEntity::spawn));
   public static RegistryObject<Item> mossyTooth = REG.register("mossy_tooth", () -> new Item(boss()));
   public static RegistryObject<Item> boneRaker = REG.register("bone_raker", () -> new BoneRaker(bossNS()));
   public static RegistryObject<Item> depthStar = REG.register("depth_star", () -> new DepthStar(bossNS()));
   public static RegistryObject<Item> cagedHeart = REG.register("caged_heart", () -> new CurioBaseItem(bossNS(), true));
   public static RegistryObject<Item> marshyDelight = REG.register(
      "marshy_delight", () -> new Item(boss().m_41489_(new Builder().m_38760_(14).m_38758_(0.9F).m_38757_().m_38767_()))
   );
   public static RegistryObject<Item> duskKey = REG.register("dusk_key", () -> new SummonItem(noStack(), RosalyneEntity::spawn));
   public static RegistryObject<Item> violetBloom = REG.register("violet_bloom", () -> new Item(boss()));
   public static RegistryObject<Item> twilightsThorn = REG.register("twilights_thorn", () -> new TwilightsThorn(bossNS()));
   public static RegistryObject<Item> wiltedIdeals = REG.register("wilted_ideals", () -> new WiltedIdeals(bossNS()));
   public static RegistryObject<Item> blossomingMind = REG.register("blossoming_mind", () -> new CurioBaseItem(bossNS(), true));
   public static RegistryObject<Item> tombPlanter = REG.register("tomb_planter", () -> new CurioBaseItem(bossNS(), true));
   public static RegistryObject<Item> petalCream = REG.register(
      "petal_cream", () -> new Item(boss().m_41489_(new Builder().m_38760_(4).m_38758_(0.8F).m_38767_()))
   );
   public static RegistryObject<Item> discMagnum = REG.register("music_disc_magnum", () -> new RecordItem(1, ModSounds.musicMagnum, disc(), 1818));
   public static RegistryObject<Item> discFrogPunch = REG.register("music_disc_frogpunch", () -> new RecordItem(1, ModSounds.musicFrogPunch, disc(), 4408));
   public static RegistryObject<Item> eggBellringer = REG.register(
      "bellringer_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BELLRINGER, 5636224, 14680057, defP())
   );
   public static RegistryObject<Item> eggDameFortuna = REG.register(
      "dame_fortuna_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DAME_FORTUNA, 16646144, 15658734, defP())
   );
   public static RegistryObject<Item> eggSwampjaw = REG.register(
      "swampjaw_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.SWAMPJAW, 16579565, 7570770, defP())
   );
   public static RegistryObject<Item> eggRosalyne = REG.register(
      "rosalyne_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ROSALYNE, 15461355, 14126847, defP())
   );
   public static RegistryObject<Item> eggVela;

   public static Properties defP() {
      return new Properties().m_41491_(ItemGroupMeetYourFight.INSTANCE);
   }

   public static Properties boss() {
      return new Properties().m_41491_(ItemGroupMeetYourFight.INSTANCE).m_41497_(Rarity.UNCOMMON).m_41486_();
   }

   public static Properties noStack() {
      return new Properties().m_41491_(ItemGroupMeetYourFight.INSTANCE).m_41487_(1);
   }

   public static Properties bossNS() {
      return new Properties().m_41491_(ItemGroupMeetYourFight.INSTANCE).m_41487_(1).m_41497_(Rarity.UNCOMMON).m_41486_();
   }

   public static Properties disc() {
      return noStack().m_41497_(Rarity.RARE).m_41486_();
   }

   static {
      if (MeetYourFight.loadedGunsWithoutRoses()) {
         CompatGWRItems.registerItems();
      }
   }
}
