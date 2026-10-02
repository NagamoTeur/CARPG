package dev.latvian.mods.kubejs.client;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSPaths;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.Pack.PackConstructor;

public class KubeJSResourcePackFinder implements RepositorySource {
   public void m_7686_(Consumer<Pack> nameToPackMap, PackConstructor packInfoFactory) {
      if (KubeJSPaths.FIRST_RUN.getValue()) {
         Path blockTextures = KubeJSPaths.dir(KubeJSPaths.ASSETS.resolve("kubejs/textures/block"));
         Path itemTextures = KubeJSPaths.dir(KubeJSPaths.ASSETS.resolve("kubejs/textures/item"));

         try (
            InputStream in = Files.newInputStream((Path)KubeJS.thisMod.findResource(new String[]{"data", "kubejs", "example_block_texture.png"}).get());
            OutputStream out = Files.newOutputStream(blockTextures.resolve("example_block.png"));
         ) {
            in.transferTo(out);
         } catch (Exception var18) {
            var18.printStackTrace();
         }

         try (
            InputStream in = Files.newInputStream((Path)KubeJS.thisMod.findResource(new String[]{"data", "kubejs", "example_item_texture.png"}).get());
            OutputStream out = Files.newOutputStream(itemTextures.resolve("example_item.png"));
         ) {
            in.transferTo(out);
         } catch (Exception var15) {
            var15.printStackTrace();
         }
      }
   }
}
