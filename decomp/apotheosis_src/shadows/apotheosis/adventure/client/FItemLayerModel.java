package shadows.apotheosis.adventure.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs.EitherCodec;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.CompositeModel.Baked;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class FItemLayerModel implements IUnbakedGeometry<FItemLayerModel> {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   private ImmutableList<Material> textures;
   private final Int2ObjectMap<FItemLayerModel.ForgeFaceData> layerData;
   private final Int2ObjectMap<ResourceLocation> renderTypeNames;
   private final boolean deprecatedLoader;
   private final boolean logWarning;

   @Deprecated(
      forRemoval = true,
      since = "1.20"
   )
   public FItemLayerModel(@Nullable ImmutableList<Material> textures, IntSet emissiveLayers, Int2ObjectMap<ResourceLocation> renderTypeNames) {
      this(
         textures,
         emissiveLayers.intStream()
            .collect(Int2ObjectArrayMap::new, (map, val) -> map.put(val, new FItemLayerModel.ForgeFaceData(-1, 15, 15)), AbstractInt2ObjectMap::putAll),
         renderTypeNames,
         false,
         false
      );
   }

   public FItemLayerModel(
      @Nullable ImmutableList<Material> textures, Int2ObjectMap<FItemLayerModel.ForgeFaceData> layerData, Int2ObjectMap<ResourceLocation> renderTypeNames
   ) {
      this(textures, layerData, renderTypeNames, false, false);
   }

   private FItemLayerModel(
      @Nullable ImmutableList<Material> textures,
      Int2ObjectMap<FItemLayerModel.ForgeFaceData> layerData,
      Int2ObjectMap<ResourceLocation> renderTypeNames,
      boolean deprecatedLoader,
      boolean logWarning
   ) {
      this.textures = textures;
      this.layerData = layerData;
      this.renderTypeNames = renderTypeNames;
      this.deprecatedLoader = deprecatedLoader;
      this.logWarning = logWarning;
   }

   public BakedModel bake(
      IGeometryBakingContext context,
      ModelBakery bakery,
      Function<Material, TextureAtlasSprite> spriteGetter,
      ModelState modelState,
      ItemOverrides overrides,
      ResourceLocation modelLocation
   ) {
      if (this.textures == null) {
         throw new IllegalStateException("Textures have not been initialized. Either pass them in through the constructor or call getMaterials(...) first.");
      } else {
         if (this.deprecatedLoader) {
            LOGGER.warn(
               "Model \""
                  + modelLocation
                  + "\" is using the deprecated loader \"forge:item-layers\" instead of \"forge:item_layers\". This loader will be removed in 1.20."
            );
         }

         if (this.logWarning) {
            LOGGER.warn(
               "Model \""
                  + modelLocation
                  + "\" is using the deprecated \"fullbright_layers\" field in its item layer model instead of \"emissive_layers\". This field will be removed in 1.20."
            );
         }

         TextureAtlasSprite particle = spriteGetter.apply(context.hasMaterial("particle") ? context.getMaterial("particle") : (Material)this.textures.get(0));
         Transformation rootTransform = context.getRootTransform();
         if (!rootTransform.isIdentity()) {
            modelState = new SimpleModelState(modelState.m_6189_().m_121096_(rootTransform), modelState.m_7538_());
         }

         RenderTypeGroup normalRenderTypes = new RenderTypeGroup(RenderType.m_110466_(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
         net.minecraftforge.client.model.CompositeModel.Baked.Builder builder = Baked.builder(context, particle, overrides, context.getTransforms());

         for (int i = 0; i < this.textures.size(); i++) {
            TextureAtlasSprite sprite = spriteGetter.apply((Material)this.textures.get(i));
            List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(i, sprite);
            List<BakedQuad> quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> sprite, modelState, modelLocation);
            if (this.layerData.containsKey(i)) {
               FItemLayerModel.ForgeFaceData data = (FItemLayerModel.ForgeFaceData)this.layerData.get(i);
               applyingLightmap(data.blockLight(), data.skyLight()).processInPlace(quads);
               applyingColor(data.color()).processInPlace(quads);
            }

            ResourceLocation renderTypeName = (ResourceLocation)this.renderTypeNames.get(i);
            RenderTypeGroup renderTypes = renderTypeName != null ? context.getRenderType(renderTypeName) : null;
            builder.addQuads(renderTypes != null ? renderTypes : normalRenderTypes, quads);
         }

         return builder.build();
      }
   }

   public static IQuadTransformer applyingLightmap(int blockLight, int skyLight) {
      return quad -> {
         int[] vertices = quad.m_111303_();

         for (int i = 0; i < 4; i++) {
            vertices[i * IQuadTransformer.STRIDE + IQuadTransformer.UV2] = LightTexture.m_109885_(blockLight, skyLight);
         }
      };
   }

   public static IQuadTransformer applyingColor(int color) {
      int fixedColor = toABGR(color);
      return quad -> {
         int[] vertices = quad.m_111303_();

         for (int i = 0; i < 4; i++) {
            vertices[i * IQuadTransformer.STRIDE + IQuadTransformer.COLOR] = fixedColor;
         }
      };
   }

   public static int toABGR(int argb) {
      return argb & -16711936 | argb >> 16 & 0xFF | argb << 16 & 0xFF0000;
   }

   public Collection<Material> getMaterials(
      IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors
   ) {
      if (this.textures != null) {
         return this.textures;
      } else {
         Builder<Material> builder = ImmutableList.builder();
         if (context.hasMaterial("particle")) {
            builder.add(context.getMaterial("particle"));
         }

         for (int i = 0; context.hasMaterial("layer" + i); i++) {
            builder.add(context.getMaterial("layer" + i));
         }

         return this.textures = builder.build();
      }
   }

   public static record ForgeFaceData(int color, int blockLight, int skyLight) {
      public static final FItemLayerModel.ForgeFaceData DEFAULT = new FItemLayerModel.ForgeFaceData(-1, 0, 0);
      public static final Codec<Integer> COLOR = new EitherCodec(Codec.INT, Codec.STRING)
         .xmap(either -> (Integer)either.map(Function.identity(), str -> (int)Long.parseLong(str, 16)), color -> Either.right(Integer.toHexString(color)));
      public static final Codec<FItemLayerModel.ForgeFaceData> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
                  COLOR.optionalFieldOf("color", -1).forGetter(FItemLayerModel.ForgeFaceData::color),
                  Codec.intRange(0, 15).optionalFieldOf("block_light", 0).forGetter(FItemLayerModel.ForgeFaceData::blockLight),
                  Codec.intRange(0, 15).optionalFieldOf("sky_light", 0).forGetter(FItemLayerModel.ForgeFaceData::skyLight)
               )
               .apply(builder, FItemLayerModel.ForgeFaceData::new)
      );
   }

   public static final class Loader implements IGeometryLoader<FItemLayerModel> {
      public static final FItemLayerModel.Loader INSTANCE = new FItemLayerModel.Loader(false);
      @Deprecated(
         forRemoval = true,
         since = "1.19"
      )
      public static final FItemLayerModel.Loader INSTANCE_DEPRECATED = new FItemLayerModel.Loader(true);
      private final boolean deprecated;

      private Loader(boolean deprecated) {
         this.deprecated = deprecated;
      }

      public FItemLayerModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) {
         Int2ObjectOpenHashMap<ResourceLocation> renderTypeNames = new Int2ObjectOpenHashMap();
         if (jsonObject.has("render_types")) {
            JsonObject renderTypes = jsonObject.getAsJsonObject("render_types");

            for (Entry<String, JsonElement> entry : renderTypes.entrySet()) {
               ResourceLocation renderType = new ResourceLocation(entry.getKey());

               for (JsonElement layer : entry.getValue().getAsJsonArray()) {
                  if (renderTypeNames.put(layer.getAsInt(), renderType) != null) {
                     throw new JsonParseException("Registered duplicate render type for layer " + layer);
                  }
               }
            }
         }

         Int2ObjectArrayMap<FItemLayerModel.ForgeFaceData> emissiveLayers = new Int2ObjectArrayMap();
         this.readUnlit(jsonObject, "forge_data", renderTypeNames, emissiveLayers, false);
         boolean logWarning = this.readUnlit(jsonObject, "emissive_layers", renderTypeNames, emissiveLayers, true);
         logWarning |= this.readUnlit(jsonObject, "fullbright_layers", renderTypeNames, emissiveLayers, true);
         return new FItemLayerModel(null, emissiveLayers, renderTypeNames, this.deprecated, logWarning);
      }

      protected boolean readUnlit(
         JsonObject jsonObject,
         String name,
         Int2ObjectOpenHashMap<ResourceLocation> renderTypeNames,
         Int2ObjectMap<FItemLayerModel.ForgeFaceData> layerData,
         boolean logWarning
      ) {
         if (!jsonObject.has(name)) {
            return false;
         } else {
            JsonElement ele = jsonObject.get(name);
            if (ele.isJsonArray()) {
               JsonArray fullbrightLayers = jsonObject.getAsJsonArray(name);

               for (JsonElement layer : fullbrightLayers) {
                  layerData.put(layer.getAsInt(), new FItemLayerModel.ForgeFaceData(-1, 15, 15));
               }

               return logWarning && !fullbrightLayers.isEmpty();
            } else {
               JsonObject fullbrightLayers = jsonObject.getAsJsonObject(name);

               for (String layerStr : fullbrightLayers.keySet()) {
                  int layer = Integer.parseInt(layerStr);
                  FItemLayerModel.ForgeFaceData data = (FItemLayerModel.ForgeFaceData)FItemLayerModel.ForgeFaceData.CODEC
                     .parse(JsonOps.INSTANCE, fullbrightLayers.get(layerStr))
                     .getOrThrow(false, FItemLayerModel.LOGGER::error);
                  layerData.put(layer, data);
               }

               return false;
            }
         }
      }
   }
}
