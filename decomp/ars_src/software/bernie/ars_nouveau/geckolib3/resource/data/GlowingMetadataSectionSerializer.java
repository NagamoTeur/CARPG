package software.bernie.ars_nouveau.geckolib3.resource.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import software.bernie.ars_nouveau.geckolib3.util.json.JsonUtil;

public class GlowingMetadataSectionSerializer implements MetadataSectionSerializer<GlowingMetadataSection> {
   public String m_7991_() {
      return "glowsections";
   }

   public GlowingMetadataSection fromJson(JsonObject jsonobject) {
      if (jsonobject.has("sections")) {
         JsonArray jsonarray = GsonHelper.m_13924_(jsonobject.get("sections"), "sections");
         GlowingMetadataSection result = new GlowingMetadataSection(JsonUtil.stream(jsonarray, JsonObject.class).map(jsonObj -> {
            int x1 = GsonHelper.m_13824_(jsonObj, "x1", GsonHelper.m_13824_(jsonObj, "x", 0));
            int y1 = GsonHelper.m_13824_(jsonObj, "y1", GsonHelper.m_13824_(jsonObj, "y", 0));
            int width = GsonHelper.m_13824_(jsonObj, "w", 0);
            int height = GsonHelper.m_13824_(jsonObj, "h", 0);
            int x2 = GsonHelper.m_13824_(jsonObj, "x2", x1 + width);
            int y2 = GsonHelper.m_13824_(jsonObj, "y2", y1 + height);
            return new GlowingMetadataSection.Section(x1, y1, x2, y2);
         }));
         if (!result.isEmpty()) {
            return result;
         }
      }

      return null;
   }
}
