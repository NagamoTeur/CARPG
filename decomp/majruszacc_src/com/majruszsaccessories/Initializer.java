package com.majruszsaccessories;

import com.majruszlibrary.item.CreativeModeTabHelper;
import com.majruszsaccessories.items.CreativeModeTabs;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

@Mod("majruszsaccessories")
public class Initializer {
   public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab("majruszsaccessories.primary") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)MajruszsAccessories.ANGLER_TROPHY.get());
      }

      public void m_6151_(NonNullList<ItemStack> stacks) {
         CreativeModeTabs.definePrimaryItems(stacks::add);
      }
   };

   public Initializer() {
      MajruszsAccessories.HELPER.register();
      MinecraftForge.EVENT_BUS.register(this);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(Initializer::onEnqueueIMC);
      DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(Initializer::onTextureStitch));
      CreativeModeTabHelper.createItemIconReplacer(CreativeModeTabs::getPrimaryIcons, CREATIVE_MODE_TAB.m_40786_());
   }

   private static void onEnqueueIMC(InterModEnqueueEvent event) {
      if (MajruszsAccessories.SLOT_INTEGRATION.isInstalled()) {
         InterModComms.sendTo(
            "majruszsaccessories",
            "curios",
            "register_type",
            () -> new Builder("pocket_left").priority(220).icon(MajruszsAccessories.POCKET_SLOT_TEXTURE).build()
         );
         InterModComms.sendTo(
            "majruszsaccessories",
            "curios",
            "register_type",
            () -> new Builder("pocket_right").priority(220).icon(MajruszsAccessories.POCKET_SLOT_TEXTURE).build()
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   private static void onTextureStitch(Pre event) {
      TextureAtlas map = event.getAtlas();
      if (InventoryMenu.f_39692_.equals(map.m_118330_())) {
         event.addSprite(MajruszsAccessories.HELPER.getLocation("slot/pocket"));
      }
   }
}
