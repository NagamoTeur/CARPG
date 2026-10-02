package dev.latvian.mods.kubejs.block;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class MaterialListJS {
   public static final MaterialListJS INSTANCE = new MaterialListJS();
   public final Map<String, MaterialJS> map = new HashMap<>();
   public final MaterialJS air = this.add("air", Material.f_76296_, SoundType.f_56742_);
   public final MaterialJS wood = this.add("wood", Material.f_76320_, SoundType.f_56736_);

   private MaterialListJS() {
      this.add("stone", Material.f_76278_, SoundType.f_56742_);
      this.add("metal", Material.f_76279_, SoundType.f_56743_);
      this.add("grass", Material.f_76315_, SoundType.f_56740_);
      this.add("crop", Material.f_76300_, SoundType.f_56758_);
      this.add("dirt", Material.f_76314_, SoundType.f_56739_);
      this.add("water", Material.f_76305_, SoundType.f_56742_);
      this.add("lava", Material.f_76307_, SoundType.f_56742_);
      this.add("leaves", Material.f_76274_, SoundType.f_56740_);
      this.add("plant", Material.f_76300_, SoundType.f_56740_);
      this.add("sponge", Material.f_76318_, SoundType.f_56740_);
      this.add("wool", Material.f_76272_, SoundType.f_56745_);
      this.add("sand", Material.f_76317_, SoundType.f_56746_);
      this.add("glass", Material.f_76275_, SoundType.f_56744_);
      this.add("explosive", Material.f_76273_, SoundType.f_56740_);
      this.add("ice", Material.f_76276_, SoundType.f_56744_);
      this.add("snow", Material.f_76308_, SoundType.f_56747_);
      this.add("clay", Material.f_76313_, SoundType.f_56739_);
      this.add("vegetable", Material.f_76285_, SoundType.f_56740_);
      this.add("dragon_egg", Material.f_76286_, SoundType.f_56742_);
      this.add("portal", Material.f_76298_, SoundType.f_56742_);
      this.add("cake", Material.f_76287_, SoundType.f_56745_);
      this.add("web", Material.f_76311_, SoundType.f_56745_);
      this.add("slime", Material.f_76313_, SoundType.f_56750_);
      this.add("honey", Material.f_76313_, SoundType.f_56751_);
      this.add("berry_bush", Material.f_76300_, SoundType.f_56757_);
      this.add("lantern", Material.f_76279_, SoundType.f_56762_);
      this.add("powder_snow", Material.f_164532_, SoundType.f_154681_);
      this.add("anvil", Material.f_76281_, SoundType.f_56749_);
      this.add("kelp", Material.f_76301_, SoundType.f_56752_);
      this.add("sea_grass", Material.f_76304_, SoundType.f_56752_);
      this.add("coral", Material.f_76278_, SoundType.f_56753_);
      this.add("bamboo", Material.f_76271_, SoundType.f_56754_);
      this.add("bamboo_sapling", Material.f_76270_, SoundType.f_56755_);
      this.add("scaffolding", Material.f_76310_, SoundType.f_56756_);
      this.add("crop", Material.f_76300_, SoundType.f_56758_);
      this.add("hard_crop", Material.f_76300_, SoundType.f_56759_);
      this.add("vine", Material.f_76302_, SoundType.f_56760_);
      this.add("nether_wart", Material.f_76300_, SoundType.f_56761_);
      this.add("nylium", Material.f_76278_, SoundType.f_56710_);
      this.add("roots", Material.f_76303_, SoundType.f_56712_);
      this.add("shroomlight", Material.f_76315_, SoundType.f_56713_);
      this.add("weeping_vines", Material.f_76300_, SoundType.f_56714_);
      this.add("twisting_vines", Material.f_76300_, SoundType.f_56715_);
      this.add("soul_sand", Material.f_76317_, SoundType.f_56716_);
      this.add("soul_soil", Material.f_76314_, SoundType.f_56717_);
      this.add("basalt", Material.f_76278_, SoundType.f_56718_);
      this.add("wart_block", Material.f_76315_, SoundType.f_56719_);
      this.add("netherrack", Material.f_76278_, SoundType.f_56720_);
      this.add("nether_bricks", Material.f_76278_, SoundType.f_56721_);
      this.add("nether_sprouts", Material.f_76303_, SoundType.f_56722_);
      this.add("nether_ore", Material.f_76278_, SoundType.f_56723_);
      this.add("nether_gold_ore", Material.f_76278_, SoundType.f_56729_);
      this.add("bone", Material.f_76278_, SoundType.f_56724_);
      this.add("netherite", Material.f_76279_, SoundType.f_56725_);
      this.add("ancient_debris", Material.f_76279_, SoundType.f_56726_);
      this.add("lodestone", Material.f_76281_, SoundType.f_56727_);
      this.add("chain", Material.f_76279_, SoundType.f_56728_);
      this.add("gilded_blackstone", Material.f_76278_, SoundType.f_56730_);
      this.add("candle", Material.f_76310_, SoundType.f_154653_);
      this.add("amethyst", Material.f_164531_, SoundType.f_154654_);
      this.add("amethyst_cluster", Material.f_164531_, SoundType.f_154655_);
      this.add("small_amethyst_bud", Material.f_164531_, SoundType.f_154656_);
      this.add("medium_amethyst_bud", Material.f_164531_, SoundType.f_154657_);
      this.add("large_amethyst_bud", Material.f_164531_, SoundType.f_154658_);
      this.add("tuff", Material.f_76278_, SoundType.f_154659_);
      this.add("calcite", Material.f_76278_, SoundType.f_154660_);
      this.add("dripstone", Material.f_76278_, SoundType.f_154661_);
      this.add("pointed_dripstone", Material.f_76278_, SoundType.f_154662_);
      this.add("copper", Material.f_76279_, SoundType.f_154663_);
      this.add("cave_vines", Material.f_76300_, SoundType.f_154664_);
      this.add("spore_blossom", Material.f_76300_, SoundType.f_154665_);
      this.add("azalea", Material.f_76300_, SoundType.f_154666_);
      this.add("flowering_azalea", Material.f_76300_, SoundType.f_154667_);
      this.add("moss_carpet", Material.f_76300_, SoundType.f_154668_);
      this.add("moss", Material.f_164530_, SoundType.f_154669_);
      this.add("big_dripleaf", Material.f_76300_, SoundType.f_154670_);
      this.add("small_dripleaf", Material.f_76300_, SoundType.f_154671_);
      this.add("rooted_dirt", Material.f_76314_, SoundType.f_154672_);
      this.add("hanging_roots", Material.f_76302_, SoundType.f_154673_);
      this.add("azalea_leaves", Material.f_76274_, SoundType.f_154674_);
      this.add("sculk_sensor", Material.f_164533_, SoundType.f_154675_);
      this.add("sculk_catalyst", Material.f_164533_, SoundType.f_222472_);
      this.add("sculk", Material.f_164533_, SoundType.f_222473_);
      this.add("sculk_vein", Material.f_164533_, SoundType.f_222474_);
      this.add("sculk_shrieker", Material.f_164533_, SoundType.f_222475_);
      this.add("glow_lichen", Material.f_76302_, SoundType.f_154676_);
      this.add("deepslate", Material.f_76278_, SoundType.f_154677_);
      this.add("deepslate_bricks", Material.f_76278_, SoundType.f_154678_);
      this.add("deepslate_tiles", Material.f_76278_, SoundType.f_154679_);
      this.add("polished_deepslate", Material.f_76278_, SoundType.f_154680_);
      this.add("froglight", Material.f_230577_, SoundType.f_222465_);
      this.add("frogspawn", Material.f_230576_, SoundType.f_222466_);
      this.add("mangrove_roots", Material.f_76320_, SoundType.f_222467_);
      this.add("muddy_mangrove_roots", Material.f_76314_, SoundType.f_222468_);
      this.add("mud", Material.f_76314_, SoundType.f_222469_);
      this.add("mud_bricks", Material.f_76278_, SoundType.f_222470_);
      this.add("packed_mud", Material.f_76314_, SoundType.f_222471_);
   }

   public MaterialJS of(Object o) {
      return o instanceof MaterialJS mat ? mat : this.map.getOrDefault(String.valueOf(o).toLowerCase(), this.wood);
   }

   public MaterialJS add(MaterialJS m) {
      this.map.put(m.getId(), m);
      return m;
   }

   public MaterialJS add(String s, Material m, SoundType e) {
      return this.add(new MaterialJS(s, m, e));
   }

   public MaterialJS get(String id) {
      MaterialJS m = this.map.get(id);
      return m == null ? this.air : m;
   }

   public MaterialJS get(Material minecraftMaterial) {
      for (MaterialJS materialJS : this.map.values()) {
         if (materialJS.getMinecraftMaterial() == minecraftMaterial) {
            return materialJS;
         }
      }

      return this.air;
   }
}
