package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.json;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.Version;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.Versioned;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion implements Versioned {
   public static final Version VERSION = VersionUtil.parseVersion("2.9.0", "software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core", "jackson-core");

   @Override
   public Version version() {
      return VERSION;
   }
}
