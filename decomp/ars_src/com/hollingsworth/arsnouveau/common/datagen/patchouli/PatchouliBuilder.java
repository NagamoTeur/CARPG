package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class PatchouliBuilder {
   JsonObject object = new JsonObject();
   JsonArray pages = new JsonArray();
   int textCounter;
   String name;
   public ResourceLocation category;

   public PatchouliBuilder(ResourceLocation category, String name) {
      this.category = category;
      this.withName(name.contains(".") ? name : "ars_nouveau.page." + name);
      this.name = name;
      this.withCategory(category);
   }

   public PatchouliBuilder(ResourceLocation category, ItemLike itemLike) {
      this.category = category;
      this.withName(itemLike.m_5456_().m_5524_());
      this.name = RegistryHelper.getRegistryName(itemLike.m_5456_()).m_135815_();
      this.withIcon(itemLike);
      this.withCategory(category);
   }

   public PatchouliBuilder(ResourceLocation category, RegistryObject<? extends ItemLike> itemLike) {
      this(category, (ItemLike)itemLike.get());
   }

   public PatchouliBuilder withName(String path) {
      this.object.addProperty("name", path);
      this.name = path;
      return this;
   }

   public PatchouliBuilder withSortNum(int num) {
      this.object.addProperty("sortnum", num);
      return this;
   }

   public PatchouliBuilder withPage(IPatchouliPage page) {
      this.pages.add(page.build());
      return this;
   }

   public PatchouliBuilder withIcon(String path) {
      this.object.addProperty("icon", path);
      return this;
   }

   public PatchouliBuilder withIcon(ItemLike item) {
      this.object.addProperty("icon", RegistryHelper.getRegistryName(item.m_5456_()).toString());
      return this;
   }

   public PatchouliBuilder withIcon(RegistryObject item) {
      this.object.addProperty("icon", RegistryHelper.getRegistryName(((ItemLike)item.get()).m_5456_()).toString());
      return this;
   }

   public PatchouliBuilder withCategory(ResourceLocation path) {
      this.object.addProperty("category", path.toString());
      return this;
   }

   public PatchouliBuilder withTextPage(String contents) {
      this.pages.add(new TextPage(contents).build());
      return this;
   }

   public PatchouliBuilder withLocalizedText(String id) {
      this.textCounter++;
      return this.withTextPage("ars_nouveau.page" + this.textCounter + "." + id);
   }

   public PatchouliBuilder withProperty(String key, String string) {
      this.object.addProperty(key, string);
      return this;
   }

   public PatchouliBuilder withProperty(String key, Number number) {
      this.object.addProperty(key, number);
      return this;
   }

   public PatchouliBuilder withProperty(String key, Boolean bool) {
      this.object.addProperty(key, bool);
      return this;
   }

   public PatchouliBuilder withLocalizedText() {
      return this.withLocalizedText(this.name);
   }

   public JsonObject build() {
      this.object.add("pages", this.pages);
      return this.object;
   }
}
