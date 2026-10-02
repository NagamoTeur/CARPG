package software.bernie.ars_nouveau.geckolib3.renderers.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GeoAbstractTexture extends AbstractTexture {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final ResourceLocation originalLocation;
   protected final ResourceLocation location;

   protected GeoAbstractTexture(ResourceLocation originalLocation, ResourceLocation location) {
      this.originalLocation = originalLocation;
      this.location = location;
   }

   protected static ResourceLocation get(
      ResourceLocation originalLocation, String appendix, BiFunction<ResourceLocation, ResourceLocation, AbstractTexture> constructor
   ) {
      if (!RenderSystem.m_69587_()) {
         throw new IllegalThreadStateException();
      } else {
         ResourceLocation location = appendBeforeEnding(originalLocation, appendix);
         TextureManager texManager = Minecraft.m_91087_().m_91097_();
         if (texManager.m_174786_(location, MissingTextureAtlasSprite.m_118080_()) == null) {
            texManager.m_118495_(location, constructor.apply(originalLocation, location));
         }

         return location;
      }
   }

   public static ResourceLocation appendBeforeEnding(ResourceLocation location, String suffix) {
      String path = location.m_135815_();
      int i = path.lastIndexOf(46);
      return new ResourceLocation(location.m_135827_(), path.substring(0, i) + suffix + path.substring(i));
   }

   public void m_6704_(ResourceManager resourceManager) throws IOException {
      Minecraft mc = Minecraft.m_91087_();
      TextureManager textureManager = mc.m_91097_();

      AbstractTexture originalTexture;
      try {
         originalTexture = (AbstractTexture)mc.m_18691_(() -> textureManager.m_118506_(this.originalLocation)).get();
      } catch (ExecutionException | InterruptedException var11) {
         throw new IOException("Failed loading original texture: " + this.originalLocation, var11);
      }

      NativeImage originalImage = originalTexture instanceof DynamicTexture
         ? ((DynamicTexture)originalTexture).m_117991_()
         : NativeImage.m_85058_(((Resource)resourceManager.m_213713_(this.location).get()).m_215507_());
      Optional<TextureMetadataSection> textureMetadata = ((Resource)resourceManager.m_213713_(this.originalLocation).get())
         .m_215509_()
         .m_214059_(TextureMetadataSection.f_119108_);
      NativeImage newImage = new NativeImage(originalImage.m_84982_(), originalImage.m_85084_(), true);
      boolean updateOriginal = this.onLoadTexture((Resource)resourceManager.m_213713_(this.originalLocation).get(), originalImage, newImage);
      boolean blur = textureMetadata != null && textureMetadata.get().m_119115_();
      boolean clamp = textureMetadata != null && textureMetadata.get().m_119116_();
      if (!FMLEnvironment.production) {
         this.debugWriteGeneratedImageToDisk(originalImage, this.originalLocation);
         this.debugWriteGeneratedImageToDisk(newImage, this.location);
      }

      if (!RenderSystem.m_69587_()) {
         RenderSystem.m_69879_(() -> {
            uploadSimple(this.m_117963_(), newImage, blur, clamp);
            if (updateOriginal) {
               if (originalTexture instanceof DynamicTexture) {
                  ((DynamicTexture)originalTexture).m_117985_();
               } else {
                  uploadSimple(originalTexture.m_117963_(), originalImage, blur, clamp);
               }
            }
         });
      } else {
         uploadSimple(this.m_117963_(), newImage, blur, clamp);
         if (updateOriginal) {
            if (originalTexture instanceof DynamicTexture) {
               ((DynamicTexture)originalTexture).m_117985_();
            } else {
               uploadSimple(originalTexture.m_117963_(), originalImage, blur, clamp);
            }
         }
      }
   }

   private final void debugWriteGeneratedImageToDisk(NativeImage newImage, ResourceLocation id) {
      try {
         File file = new File(FMLPaths.GAMEDIR.get().toFile(), "autoglow-gen");
         if (!file.exists()) {
            file.mkdirs();
         } else if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
         }

         file = new File(file, id.m_135815_().replace('/', '#'));
         if (!file.exists()) {
            file.createNewFile();
         }

         newImage.m_85056_(file);
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }

   protected abstract boolean onLoadTexture(Resource var1, NativeImage var2, NativeImage var3);

   protected static void uploadSimple(int texture, NativeImage image, boolean blur, boolean clamp) {
      TextureUtil.m_85287_(texture, 0, image.m_84982_(), image.m_85084_());
      image.m_85013_(0, 0, 0, 0, 0, image.m_84982_(), image.m_85084_(), blur, clamp, false, true);
   }
}
