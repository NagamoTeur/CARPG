package shadows.apotheosis.potion;

import java.io.File;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.core.mobfx.api.MFEffects;
import shadows.apotheosis.potion.compat.CuriosCompat;
import shadows.placebo.config.Configuration;
import shadows.placebo.util.RegistryEvent.Register;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

public class PotionModule {
   public static final Logger LOG = LogManager.getLogger("Apotheosis : Potion");
   public static final ResourceLocation POTION_TEX = new ResourceLocation("apotheosis", "textures/potions.png");
   public static int knowledgeMult = 4;
   static boolean charmsInCuriosOnly = false;

   @SubscribeEvent
   public void preInit(Apotheosis.ApotheosisConstruction e) {
      this.reload(null);
      if (FMLEnvironment.dist.isClient()) {
         FMLJavaModLoadingContext.get().getModEventBus().register(PotionModuleClient.class);
      }

      InterModComms.sendTo("curios", "REGISTER_TYPE", () -> new Builder("charm").size(1).build());
   }

   @SubscribeEvent
   public void init(FMLCommonSetupEvent e) {
      e.enqueueWork(() -> {
         PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42748_, (Potion)Apoth.Potions.RESISTANCE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.RESISTANCE.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_RESISTANCE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.RESISTANCE.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_RESISTANCE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.RESISTANCE.get(), Items.f_42592_, (Potion)Apoth.Potions.SUNDERING.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.LONG_RESISTANCE.get(), Items.f_42592_, (Potion)Apoth.Potions.LONG_SUNDERING.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.STRONG_RESISTANCE.get(), Items.f_42592_, (Potion)Apoth.Potions.STRONG_SUNDERING.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.SUNDERING.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_SUNDERING.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.SUNDERING.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_SUNDERING.get());
         PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42436_, (Potion)Apoth.Potions.ABSORPTION.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.ABSORPTION.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_ABSORPTION.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.ABSORPTION.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_ABSORPTION.get());
         PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42400_, (Potion)Apoth.Potions.HASTE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.HASTE.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_HASTE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.HASTE.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_HASTE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.HASTE.get(), Items.f_42592_, (Potion)Apoth.Potions.FATIGUE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.LONG_HASTE.get(), Items.f_42592_, (Potion)Apoth.Potions.LONG_FATIGUE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.STRONG_HASTE.get(), Items.f_42592_, (Potion)Apoth.Potions.STRONG_FATIGUE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.FATIGUE.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_FATIGUE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.FATIGUE.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_FATIGUE.get());
         if (Apoth.Items.SKULL_FRAGMENT.isPresent()) {
            PotionBrewing.m_43513_(Potions.f_43602_, (Item)Apoth.Items.SKULL_FRAGMENT.get(), (Potion)Apoth.Potions.WITHER.get());
         } else {
            PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42679_, (Potion)Apoth.Potions.WITHER.get());
         }

         PotionBrewing.m_43513_((Potion)Apoth.Potions.WITHER.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_WITHER.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.WITHER.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_WITHER.get());
         PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42612_, (Potion)Apoth.Potions.KNOWLEDGE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.KNOWLEDGE.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_KNOWLEDGE.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.KNOWLEDGE.get(), Items.f_42612_, (Potion)Apoth.Potions.STRONG_KNOWLEDGE.get());
         PotionBrewing.m_43513_(Potions.f_43602_, (Item)Apoth.Items.LUCKY_FOOT.get(), Potions.f_43595_);
         PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42780_, (Potion)Apoth.Potions.VITALITY.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.VITALITY.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_VITALITY.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.VITALITY.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_VITALITY.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.VITALITY.get(), Items.f_42592_, (Potion)Apoth.Potions.GRIEVOUS.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.LONG_VITALITY.get(), Items.f_42592_, (Potion)Apoth.Potions.LONG_GRIEVOUS.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.STRONG_VITALITY.get(), Items.f_42592_, (Potion)Apoth.Potions.STRONG_GRIEVOUS.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.GRIEVOUS.get(), Items.f_42451_, (Potion)Apoth.Potions.LONG_GRIEVOUS.get());
         PotionBrewing.m_43513_((Potion)Apoth.Potions.GRIEVOUS.get(), Items.f_42525_, (Potion)Apoth.Potions.STRONG_GRIEVOUS.get());
      });
      Apotheosis.HELPER.registerProvider(factory -> {
         Ingredient fireRes = Apotheosis.potionIngredient(Potions.f_43610_);
         Ingredient abs = Apotheosis.potionIngredient((Potion)Apoth.Potions.STRONG_ABSORPTION.get());
         Ingredient res = Apotheosis.potionIngredient((Potion)Apoth.Potions.RESISTANCE.get());
         Ingredient regen = Apotheosis.potionIngredient(Potions.f_43589_);
         factory.addShaped(Items.f_42437_, 3, 3, new Object[]{fireRes, regen, fireRes, abs, Items.f_42436_, abs, res, abs, res});
      });
      MinecraftForge.EVENT_BUS.addListener(this::drops);
      MinecraftForge.EVENT_BUS.addListener(this::reload);
   }

   @SubscribeEvent
   public void items(Register<Item> e) {
      e.getRegistry().registerAll(new Object[]{new LuckyFootItem(), "lucky_foot", new PotionCharmItem(), "potion_charm"});
   }

   @SubscribeEvent
   public void types(Register<Potion> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new Potion("resistance", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19606_, 3600)}),
               "resistance",
               new Potion("resistance", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19606_, 9600)}),
               "long_resistance",
               new Potion("resistance", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19606_, 1800, 1)}),
               "strong_resistance",
               new Potion("absorption", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19617_, 1200, 1)}),
               "absorption",
               new Potion("absorption", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19617_, 3600, 1)}),
               "long_absorption",
               new Potion("absorption", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19617_, 600, 3)}),
               "strong_absorption",
               new Potion("haste", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19598_, 3600)}),
               "haste",
               new Potion("haste", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19598_, 9600)}),
               "long_haste",
               new Potion("haste", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19598_, 1800, 1)}),
               "strong_haste",
               new Potion("fatigue", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19599_, 3600)}),
               "fatigue",
               new Potion("fatigue", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19599_, 9600)}),
               "long_fatigue",
               new Potion("fatigue", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19599_, 1800, 1)}),
               "strong_fatigue",
               new Potion("wither", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19615_, 3600)}),
               "wither",
               new Potion("wither", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19615_, 9600)}),
               "long_wither",
               new Potion("wither", new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19615_, 1800, 1)}),
               "strong_wither",
               new Potion("sundering", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.SUNDERING.get(), 3600)}),
               "sundering",
               new Potion("sundering", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.SUNDERING.get(), 9600)}),
               "long_sundering",
               new Potion("sundering", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.SUNDERING.get(), 1800, 1)}),
               "strong_sundering",
               new Potion("knowledge", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.KNOWLEDGE.get(), 2400)}),
               "knowledge",
               new Potion("knowledge", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.KNOWLEDGE.get(), 4800)}),
               "long_knowledge",
               new Potion("knowledge", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.KNOWLEDGE.get(), 1200, 1)}),
               "strong_knowledge",
               new Potion("vitality", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.VITALITY.get(), 4800)}),
               "vitality",
               new Potion("vitality", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.VITALITY.get(), 14400)}),
               "long_vitality",
               new Potion("vitality", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.VITALITY.get(), 3600, 1)}),
               "strong_vitality",
               new Potion("grievous", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.GRIEVOUS.get(), 4800)}),
               "grievous",
               new Potion("grievous", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.GRIEVOUS.get(), 14400)}),
               "long_grievous",
               new Potion("grievous", new MobEffectInstance[]{new MobEffectInstance((MobEffect)MFEffects.GRIEVOUS.get(), 3600, 1)}),
               "strong_grievous"
            }
         );
   }

   @SubscribeEvent
   public void serializers(Register<RecipeSerializer<?>> e) {
      e.getRegistry().register(PotionCharmRecipe.Serializer.INSTANCE, Apoth.Items.POTION_CHARM.getId());
      e.getRegistry().register(PotionEnchantingRecipe.SERIALIZER, "potion_charm_enchanting");
   }

   @SubscribeEvent
   public void imcEvent(InterModEnqueueEvent e) {
      if (ModList.get().isLoaded("curios")) {
         CuriosCompat.sendIMC();
      }
   }

   public void drops(LivingDropsEvent e) {
      if (e.getEntity() instanceof Rabbit rabbit && rabbit.f_19853_.f_46441_.m_188501_() < 0.045F + 0.045F * (float)e.getLootingLevel()) {
         e.getDrops().clear();
         e.getDrops()
            .add(
               new ItemEntity(rabbit.f_19853_, rabbit.m_20185_(), rabbit.m_20186_(), rabbit.m_20189_(), new ItemStack((ItemLike)Apoth.Items.LUCKY_FOOT.get()))
            );
      }
   }

   public void reload(Apotheosis.ApotheosisReloadEvent e) {
      Configuration config = new Configuration(new File(Apotheosis.configDir, "potion.cfg"));
      config.setTitle("Apotheosis Potion Module Configuration");
      knowledgeMult = config.getInt(
         "Knowledge XP Multiplier",
         "general",
         knowledgeMult,
         1,
         Integer.MAX_VALUE,
         "The strength of Ancient Knowledge.  This multiplier determines how much additional xp is granted.\nServer-authoritative."
      );
      charmsInCuriosOnly = config.getBoolean(
         "Restrict Charms to Curios", "general", charmsInCuriosOnly, "If Potion Charms will only work when in a curios slot, instead of in the inventory."
      );
      String[] defExt = new String[]{
         ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.f_19611_).toString(), ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.f_19616_).toString()
      };
      String[] names = config.getStringList(
         "Extended Potion Charms",
         "general",
         defExt,
         "A list of effects that, when as charms, will be applied and reapplied at a longer threshold to avoid issues at low durations, like night vision.\nServer-authoritative."
      );
      PotionCharmItem.EXTENDED_POTIONS.clear();

      for (String s : names) {
         try {
            PotionCharmItem.EXTENDED_POTIONS.add(new ResourceLocation(s));
         } catch (ResourceLocationException var10) {
            LOG.error("Invalid extended potion charm entry {} will be ignored.", s);
         }
      }

      if (e == null && config.hasChanged()) {
         config.save();
      }
   }
}
