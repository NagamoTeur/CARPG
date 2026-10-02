package com.aizistral.omniconfig;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLPaths;

public class Configuration {
   public static final String CATEGORY_GENERAL = "general";
   public static final String ALLOWED_CHARS = "._-";
   public static final String DEFAULT_ENCODING = "UTF-8";
   public static final String CATEGORY_SPLITTER = ".";
   public static final String NEW_LINE = System.getProperty("line.separator");
   public static final String COMMENT_SEPARATOR = "##########################################################################################################";
   private static final String CONFIG_VERSION_MARKER = "@CONFIG_VERSION";
   private static final Pattern CONFIG_START = Pattern.compile("START: \"([^\\\"]+)\"");
   private static final Pattern CONFIG_END = Pattern.compile("END: \"([^\\\"]+)\"");
   public static final CharMatcher allowedProperties = CharMatcher.javaLetterOrDigit().or(CharMatcher.anyOf("._-"));
   private static Configuration PARENT = null;
   public boolean isOverloading = false;
   private boolean pushSynchronized = false;
   private File file;
   private Map<String, ConfigCategory> categories = new TreeMap<>();
   private Map<String, Configuration> children = new TreeMap<>();
   private boolean caseSensitiveCustomCategories;
   public String defaultEncoding = "UTF-8";
   private String fileName = null;
   public boolean isChild = false;
   private boolean changed = false;
   private String definedConfigVersion = null;
   private String loadedConfigVersion = null;
   private Consumer<Configuration> overloadingAction = null;
   private Configuration.SidedConfigType sidedType = Configuration.SidedConfigType.COMMON;
   private Configuration.VersioningPolicy versioningPolicy = Configuration.VersioningPolicy.DISMISSIVE;
   private boolean firstLoadPassed = false;
   private boolean terminateNonInvokedKeys = false;
   protected Configuration.ConfigBeholder associatedBeholder = null;

   public Configuration() {
   }

   public Configuration(File file) {
      this(file, null);
   }

   public Configuration(File file, String configVersion) {
      this.file = file;
      this.definedConfigVersion = configVersion;
      String basePath = FMLPaths.GAMEDIR.get().toFile().getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
      String path = file.getAbsolutePath().replace(File.separatorChar, '/').replace("/./", "/").replace(basePath, "");
      if (PARENT != null) {
         PARENT.setChild(path, this);
         this.isChild = true;
      } else {
         this.fileName = path;
      }
   }

   public Configuration(File file, String configVersion, boolean caseSensitiveCustomCategories) {
      this(file, configVersion);
      this.caseSensitiveCustomCategories = caseSensitiveCustomCategories;
   }

   public Configuration(File file, boolean caseSensitiveCustomCategories) {
      this(file, null, caseSensitiveCustomCategories);
   }

   public void pushSynchronized(boolean value) {
      this.pushSynchronized = value;
   }

   private String pullSynchronized() {
      String was = this.pushSynchronized ? "yes" : "no";
      this.pushSynchronized = false;
      return was;
   }

   private String getSynchronizedComment() {
      String comment = "";
      if (!this.sidedType.isSided()) {
         comment = ", synchronized: " + this.pullSynchronized();
      }

      return comment;
   }

   public Configuration.SidedConfigType getSidedType() {
      return this.sidedType;
   }

   public void setSidedType(Configuration.SidedConfigType sidedType) {
      this.sidedType = sidedType;
   }

   public void setVersioningPolicy(Configuration.VersioningPolicy versioningPolicy) {
      this.versioningPolicy = versioningPolicy;
   }

   public Configuration.VersioningPolicy getVersioningPolicy() {
      return this.versioningPolicy;
   }

   public void setTerminateNonInvokedKeys(boolean terminateNonInvokedKeys) {
      this.terminateNonInvokedKeys = terminateNonInvokedKeys;
   }

   public boolean terminateNonInvokedKeys() {
      return this.terminateNonInvokedKeys;
   }

   @Override
   public String toString() {
      return this.file.getAbsolutePath();
   }

   public String getDefinedConfigVersion() {
      return this.definedConfigVersion;
   }

   public String getLoadedConfigVersion() {
      return this.loadedConfigVersion;
   }

   public Property get(String category, String key, boolean defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, boolean defaultValue, String comment) {
      Property prop = this.get(category, key, Boolean.toString(defaultValue), comment, Property.Type.BOOLEAN);
      prop.setDefaultValue(Boolean.toString(defaultValue));
      if (!prop.isBooleanValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, boolean[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null);
   }

   public Property get(String category, String key, boolean[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, false, -1);
   }

   public Property get(String category, String key, boolean[] defaultValues, String comment, boolean isListLengthFixed, int maxListLength) {
      String[] values = new String[defaultValues.length];

      for (int i = 0; i < defaultValues.length; i++) {
         values[i] = Boolean.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.BOOLEAN);
      prop.setDefaultValues(values);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      if (!prop.isBooleanList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, int defaultValue) {
      return this.get(category, key, defaultValue, (String)null, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public Property get(String category, String key, int defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public Property get(String category, String key, int defaultValue, String comment, int minValue, int maxValue) {
      Property prop = this.get(category, key, Integer.toString(defaultValue), comment, Property.Type.INTEGER);
      prop.setDefaultValue(Integer.toString(defaultValue));
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      if (!prop.isIntValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, int[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null);
   }

   public Property get(String category, String key, int[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, Integer.MIN_VALUE, Integer.MAX_VALUE, false, -1);
   }

   public Property get(String category, String key, int[] defaultValues, String comment, int minValue, int maxValue) {
      return this.get(category, key, defaultValues, comment, minValue, maxValue, false, -1);
   }

   public Property get(
      String category, String key, int[] defaultValues, String comment, int minValue, int maxValue, boolean isListLengthFixed, int maxListLength
   ) {
      String[] values = new String[defaultValues.length];

      for (int i = 0; i < defaultValues.length; i++) {
         values[i] = Integer.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.INTEGER);
      prop.setDefaultValues(values);
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      if (!prop.isIntList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, double defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, double defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, -Double.MAX_VALUE, Double.MAX_VALUE);
   }

   public Property get(String category, String key, double defaultValue, String comment, double minValue, double maxValue) {
      Property prop = this.get(category, key, Double.toString(defaultValue), comment, Property.Type.DOUBLE);
      prop.setDefaultValue(Double.toString(defaultValue));
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      if (!prop.isDoubleValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, double[] defaultValues) {
      return this.get(category, key, defaultValues, null);
   }

   public Property get(String category, String key, double[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, -Double.MAX_VALUE, Double.MAX_VALUE, false, -1);
   }

   public Property get(String category, String key, double[] defaultValues, String comment, double minValue, double maxValue) {
      return this.get(category, key, defaultValues, comment, minValue, maxValue, false, -1);
   }

   public Property get(
      String category, String key, double[] defaultValues, String comment, double minValue, double maxValue, boolean isListLengthFixed, int maxListLength
   ) {
      String[] values = new String[defaultValues.length];

      for (int i = 0; i < defaultValues.length; i++) {
         values[i] = Double.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.DOUBLE);
      prop.setDefaultValues(values);
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      if (!prop.isDoubleList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, String defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, String defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, Property.Type.STRING);
   }

   public Property get(String category, String key, String defaultValue, String comment, Pattern validationPattern) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidationPattern(validationPattern);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, String[] validValues) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidValues(validValues);
      return prop;
   }

   public Property get(String category, String key, String[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null, false, -1, (Pattern)null);
   }

   public Property get(String category, String key, String[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, false, -1, (Pattern)null);
   }

   public Property get(String category, String key, String[] defaultValues, String comment, Pattern validationPattern) {
      return this.get(category, key, defaultValues, comment, false, -1, validationPattern);
   }

   public Property get(
      String category, String key, String[] defaultValues, String comment, boolean isListLengthFixed, int maxListLength, Pattern validationPattern
   ) {
      Property prop = this.get(category, key, defaultValues, comment, Property.Type.STRING);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      prop.setValidationPattern(validationPattern);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, Property.Type type) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      ConfigCategory cat = this.getCategory(category);
      cat.initialized = true;
      if (cat.containsKey(key)) {
         Property prop = cat.get(key);
         prop.initialized = true;
         if (prop.getType() == null) {
            prop = new Property(prop.getName(), prop.getString(), type);
            cat.put(key, prop);
         }

         prop.setDefaultValue(defaultValue);
         prop.comment = comment;
         return prop;
      } else if (defaultValue != null) {
         Property prop = new Property(key, defaultValue, type);
         prop.initialized = true;
         prop.setValue(defaultValue);
         cat.put(key, prop);
         prop.setDefaultValue(defaultValue);
         prop.comment = comment;
         return prop;
      } else {
         return null;
      }
   }

   public Property get(String category, String key, String[] defaultValues, String comment, Property.Type type) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      ConfigCategory cat = this.getCategory(category);
      cat.initialized = true;
      if (cat.containsKey(key)) {
         Property prop = cat.get(key);
         prop.initialized = true;
         if (prop.getType() == null) {
            prop = new Property(prop.getName(), prop.getString(), type);
            cat.put(key, prop);
         }

         prop.setDefaultValues(defaultValues);
         prop.comment = comment;
         return prop;
      } else if (defaultValues != null) {
         Property prop = new Property(key, defaultValues, type);
         prop.initialized = true;
         prop.setDefaultValues(defaultValues);
         prop.comment = comment;
         cat.put(key, prop);
         return prop;
      } else {
         return null;
      }
   }

   public boolean hasCategory(String category) {
      return this.categories.get(category) != null;
   }

   public boolean hasKey(String category, String key) {
      ConfigCategory cat = this.categories.get(category);
      return cat != null && cat.containsKey(key);
   }

   public void loadFile() {
      if (PARENT == null || PARENT == this) {
         BufferedReader buffer = null;
         Configuration.UnicodeInputStreamReader input = null;

         try {
            if (this.file.getParentFile() != null) {
               this.file.getParentFile().mkdirs();
            }

            if (!this.file.exists()) {
               this.categories.clear();
               this.children.clear();
               if (!this.file.createNewFile()) {
                  return;
               }
            }

            if (this.file.canRead()) {
               input = new Configuration.UnicodeInputStreamReader(new FileInputStream(this.file), this.defaultEncoding);
               this.defaultEncoding = input.getEncoding();
               buffer = new BufferedReader(input);
               ConfigCategory currentCat = null;
               Property.Type type = null;
               ArrayList<String> tmpList = null;
               int lineNum = 0;
               String name = null;
               this.loadedConfigVersion = null;

               while (true) {
                  lineNum++;
                  String line = buffer.readLine();
                  if (line == null) {
                     if (lineNum == 1) {
                        this.loadedConfigVersion = this.definedConfigVersion;
                     }
                     break;
                  }

                  Matcher start = CONFIG_START.matcher(line);
                  Matcher end = CONFIG_END.matcher(line);
                  if (start.matches()) {
                     this.fileName = start.group(1);
                     this.categories = new TreeMap<>();
                  } else if (!end.matches()) {
                     int nameStart = -1;
                     int nameEnd = -1;
                     boolean skip = false;
                     boolean quoted = false;
                     boolean isFirstNonWhitespaceCharOnLine = true;

                     for (int i = 0; i < line.length() && !skip; i++) {
                        if (!Character.isLetterOrDigit(line.charAt(i)) && "._-".indexOf(line.charAt(i)) == -1 && (!quoted || line.charAt(i) == '"')) {
                           if (!Character.isWhitespace(line.charAt(i))) {
                              switch (line.charAt(i)) {
                                 case '"':
                                    if (tmpList == null) {
                                       if (quoted) {
                                          quoted = false;
                                       }

                                       if (!quoted && nameStart == -1) {
                                          quoted = true;
                                       }
                                    }
                                    break;
                                 case '#':
                                    if (tmpList == null) {
                                       skip = true;
                                       continue;
                                    }
                                    break;
                                 case ':':
                                    if (tmpList == null) {
                                       type = Property.Type.tryParse(line.substring(nameStart, nameEnd + 1).charAt(0));
                                       nameEnd = -1;
                                       nameStart = -1;
                                    }
                                    break;
                                 case '<':
                                    if (tmpList != null && i + 1 == line.length() || tmpList == null && i + 1 != line.length()) {
                                       throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", this.fileName, lineNum));
                                    }

                                    if (i + 1 == line.length()) {
                                       name = line.substring(nameStart, nameEnd + 1);
                                       if (currentCat == null) {
                                          throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, this.fileName, lineNum));
                                       }

                                       tmpList = new ArrayList<>();
                                       skip = true;
                                    }
                                    break;
                                 case '=':
                                    if (tmpList == null) {
                                       name = line.substring(nameStart, nameEnd + 1);
                                       if (currentCat == null) {
                                          throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, this.fileName, lineNum));
                                       }

                                       Property prop = new Property(name, line.substring(i + 1), type, true);
                                       i = line.length();
                                       currentCat.put(name, prop);
                                    }
                                    break;
                                 case '>':
                                    if (tmpList == null) {
                                       throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", this.fileName, lineNum));
                                    }

                                    if (isFirstNonWhitespaceCharOnLine) {
                                       if (currentCat == null) {
                                          throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", this.fileName, lineNum));
                                       }

                                       currentCat.put(name, new Property(name, tmpList.toArray(new String[tmpList.size()]), type));
                                       name = null;
                                       tmpList = null;
                                       type = null;
                                    }
                                    break;
                                 case '@':
                                    if (tmpList == null && line.startsWith("@CONFIG_VERSION")) {
                                       int colon = line.indexOf(58);
                                       if (colon != -1) {
                                          this.loadedConfigVersion = line.substring(colon + 1).trim();
                                       }

                                       skip = true;
                                    }
                                    break;
                                 case '{':
                                    if (tmpList == null) {
                                       name = line.substring(nameStart, nameEnd + 1);
                                       String qualifiedName = ConfigCategory.getQualifiedName(name, currentCat);
                                       ConfigCategory cat = this.categories.get(qualifiedName);
                                       if (cat == null) {
                                          currentCat = new ConfigCategory(name, currentCat);
                                          this.categories.put(qualifiedName, currentCat);
                                       } else {
                                          currentCat = cat;
                                       }

                                       name = null;
                                    }
                                    break;
                                 case '}':
                                    if (tmpList == null) {
                                       if (currentCat == null) {
                                          throw new RuntimeException(
                                             String.format("Config file corrupt, attempted to close to many categories '%s:%d'", this.fileName, lineNum)
                                          );
                                       }

                                       currentCat = currentCat.parent;
                                    }
                                    break;
                                 default:
                                    if (tmpList == null) {
                                       throw new RuntimeException(String.format("Unknown character '%s' in '%s:%d'", line.charAt(i), this.fileName, lineNum));
                                    }
                              }

                              isFirstNonWhitespaceCharOnLine = false;
                           }
                        } else {
                           if (nameStart == -1) {
                              nameStart = i;
                           }

                           nameEnd = i;
                           isFirstNonWhitespaceCharOnLine = false;
                        }
                     }

                     if (quoted) {
                        throw new RuntimeException(String.format("Unmatched quote in '%s:%d'", this.fileName, lineNum));
                     }

                     if (tmpList != null && !skip) {
                        tmpList.add(line.trim());
                     }
                  } else {
                     this.fileName = end.group(1);
                     Configuration child = new Configuration();
                     child.categories = this.categories;
                     this.children.put(this.fileName, child);
                  }
               }
            }
         } catch (IOException var35) {
            var35.printStackTrace();
         } finally {
            if (buffer != null) {
               try {
                  buffer.close();
               } catch (IOException var34) {
               }
            }

            if (input != null) {
               try {
                  input.close();
               } catch (IOException var33) {
               }
            }
         }

         if (!Objects.equals(this.loadedConfigVersion, this.definedConfigVersion) && !this.firstLoadPassed) {
            EnigmaticLegacy.LOGGER.info("Loaded config version does not match defined version!");
            EnigmaticLegacy.LOGGER.info("Loaded version: " + this.loadedConfigVersion + ", provider-defined version: " + this.definedConfigVersion);
            if (this.versioningPolicy == Configuration.VersioningPolicy.AGGRESSIVE) {
               EnigmaticLegacy.LOGGER
                  .info("The config updating policy is defined as " + this.versioningPolicy.toString() + "; full reset of config file will be executed.");
               this.categories.clear();
               this.children.clear();
            } else if (this.versioningPolicy == Configuration.VersioningPolicy.DISMISSIVE) {
               EnigmaticLegacy.LOGGER
                  .info(
                     "The config updating policy is defined as "
                        + this.versioningPolicy.toString()
                        + "; everythying in the config file will be left untouched, apart from config version parameter being updated."
                  );
            } else if (this.versioningPolicy != Configuration.VersioningPolicy.RESPECTFUL && this.versioningPolicy == Configuration.VersioningPolicy.NOBLE) {
            }

            this.loadedConfigVersion = this.definedConfigVersion;
         }

         if (!this.firstLoadPassed) {
            this.firstLoadPassed = true;
         }

         this.resetChangedState();
      }
   }

   public synchronized void load() {
      this.executeSided(
         () -> {
            this.isOverloading = true;
            LockSupport.parkNanos(10000L);

            try {
               this.loadFile();
            } catch (Throwable var3) {
               File fileBak = new File(this.file.getAbsolutePath() + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".errored");
               EnigmaticLegacy.LOGGER
                  .error(
                     "An exception occurred while loading config file "
                        + this.file.getName()
                        + ". This file will be renamed to "
                        + fileBak.getName()
                        + " and a new config file will be generated."
                  );
               var3.printStackTrace();
               this.file.renameTo(fileBak);
               this.loadFile();
            }

            if (this.associatedBeholder != null) {
               this.associatedBeholder.lastCall = System.currentTimeMillis();
            }

            this.isOverloading = false;
         }
      );
   }

   public synchronized void save() {
      this.executeSided(() -> {
         this.isOverloading = true;
         this.saveFile();
         if (this.associatedBeholder != null) {
            this.associatedBeholder.lastCall = System.currentTimeMillis();
         }

         this.isOverloading = false;
      });
   }

   private void saveFile() {
      if (PARENT != null && PARENT != this) {
         PARENT.saveFile();
      } else {
         try {
            if (this.file.getParentFile() != null) {
               this.file.getParentFile().mkdirs();
            }

            if (!this.file.exists() && !this.file.createNewFile()) {
               return;
            }

            if (this.file.canWrite()) {
               FileOutputStream fos = new FileOutputStream(this.file);
               BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, this.defaultEncoding));
               buffer.write("# Configuration File" + NEW_LINE + NEW_LINE);
               String configVersion = null;
               if (this.loadedConfigVersion != null) {
                  configVersion = this.loadedConfigVersion;
               } else if (this.definedConfigVersion != null) {
                  configVersion = this.definedConfigVersion;
               }

               if (configVersion != null) {
                  buffer.write("@CONFIG_VERSION: " + configVersion + NEW_LINE + NEW_LINE);
               }

               if (this.children.isEmpty()) {
                  this.save(buffer);
               } else {
                  for (Entry<String, Configuration> entry : this.children.entrySet()) {
                     buffer.write("START: \"" + entry.getKey() + "\"" + NEW_LINE);
                     entry.getValue().save(buffer);
                     buffer.write("END: \"" + entry.getKey() + "\"" + NEW_LINE + NEW_LINE);
                  }
               }

               buffer.close();
               fos.close();
            }
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      }
   }

   private void save(BufferedWriter out) throws IOException {
      for (ConfigCategory cat : this.categories.values()) {
         if ((cat.initialized || !this.terminateNonInvokedKeys) && !cat.isChild()) {
            cat.write(out, 0);
            out.newLine();
         }
      }
   }

   public ConfigCategory getCategory(String category) {
      ConfigCategory ret = this.categories.get(category);
      if (ret == null) {
         if (category.contains(".")) {
            String[] hierarchy = category.split("\\.");
            ConfigCategory parent = this.categories.get(hierarchy[0]);
            if (parent == null) {
               parent = new ConfigCategory(hierarchy[0]);
               this.categories.put(parent.getQualifiedName(), parent);
               this.changed = true;
            }

            for (int i = 1; i < hierarchy.length; i++) {
               String name = ConfigCategory.getQualifiedName(hierarchy[i], parent);
               ConfigCategory child = this.categories.get(name);
               if (child == null) {
                  child = new ConfigCategory(hierarchy[i], parent);
                  this.categories.put(name, child);
                  this.changed = true;
               }

               ret = child;
               parent = child;
            }
         } else {
            ret = new ConfigCategory(category);
            this.categories.put(category, ret);
            this.changed = true;
         }
      }

      return ret;
   }

   public void removeCategory(ConfigCategory category) {
      for (ConfigCategory child : category.getChildren()) {
         this.removeCategory(child);
      }

      if (this.categories.containsKey(category.getQualifiedName())) {
         this.categories.remove(category.getQualifiedName());
         if (category.parent != null) {
            category.parent.removeChild(category);
         }

         this.changed = true;
      }
   }

   public Configuration setCategoryComment(String category, String comment) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setComment(comment);
      return this;
   }

   public void addCustomCategoryComment(String category, String comment) {
      this.setCategoryComment(category, comment);
   }

   public Configuration setCategoryLanguageKey(String category, String langKey) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setLanguageKey(langKey);
      return this;
   }

   public Configuration setCategoryRequiresWorldRestart(String category, boolean requiresWorldRestart) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setRequiresWorldRestart(requiresWorldRestart);
      return this;
   }

   public Configuration setCategoryRequiresMcRestart(String category, boolean requiresMcRestart) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setRequiresMcRestart(requiresMcRestart);
      return this;
   }

   public Configuration setCategoryPropertyOrder(String category, List<String> propOrder) {
      if (!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setPropertyOrder(propOrder);
      return this;
   }

   private void setChild(String name, Configuration child) {
      if (!this.children.containsKey(name)) {
         this.children.put(name, child);
         this.changed = true;
      } else {
         Configuration old = this.children.get(name);
         child.categories = old.categories;
         child.fileName = old.fileName;
         old.changed = true;
      }
   }

   public static void enableGlobalConfig() {
      try {
         PARENT = new Configuration(new File(FMLPaths.CONFIGDIR.get().toFile().getCanonicalFile(), "global.cfg"));
      } catch (IOException var1) {
         throw new RuntimeException("Something broken", var1);
      }

      PARENT.load();
   }

   public boolean hasChanged() {
      if (this.changed) {
         return true;
      } else {
         for (ConfigCategory cat : this.categories.values()) {
            if (cat.hasChanged()) {
               return true;
            }
         }

         for (Configuration child : this.children.values()) {
            if (child.hasChanged()) {
               return true;
            }
         }

         return false;
      }
   }

   private void resetChangedState() {
      this.changed = false;

      for (ConfigCategory cat : this.categories.values()) {
         cat.resetChangedState();
      }

      for (Configuration child : this.children.values()) {
         child.resetChangedState();
      }
   }

   public Set<String> getCategoryNames() {
      return ImmutableSet.copyOf(this.categories.keySet());
   }

   public boolean renameProperty(String category, String oldPropName, String newPropName) {
      if (this.hasCategory(category) && this.getCategory(category).containsKey(oldPropName) && !oldPropName.equalsIgnoreCase(newPropName)) {
         this.get(category, newPropName, this.getCategory(category).get(oldPropName).getString(), "");
         this.getCategory(category).remove(oldPropName);
         return true;
      } else {
         return false;
      }
   }

   public boolean moveProperty(String oldCategory, String propName, String newCategory) {
      if (!oldCategory.equals(newCategory) && this.hasCategory(oldCategory) && this.getCategory(oldCategory).containsKey(propName)) {
         this.getCategory(newCategory).put(propName, this.getCategory(oldCategory).remove(propName));
         return true;
      } else {
         return false;
      }
   }

   public void copyCategoryProps(Configuration fromConfig, String[] ctgys) {
      if (ctgys == null) {
         ctgys = this.getCategoryNames().toArray(new String[this.getCategoryNames().size()]);
      }

      for (String ctgy : ctgys) {
         if (fromConfig.hasCategory(ctgy) && this.hasCategory(ctgy)) {
            ConfigCategory thiscc = this.getCategory(ctgy);
            ConfigCategory fromcc = fromConfig.getCategory(ctgy);

            for (Entry<String, Property> entry : thiscc.getValues().entrySet()) {
               if (fromcc.containsKey(entry.getKey())) {
                  thiscc.put(entry.getKey(), fromcc.get(entry.getKey()));
               }
            }
         }
      }
   }

   public <V extends Enum<V>> V getEnum(String name, String category, V defaultValue, String comment, V[] validValues) {
      String[] values = new String[validValues.length];
      int i = 0;

      for (V val : validValues) {
         values[i] = val.toString();
         i++;
      }

      return Enum.valueOf(defaultValue.getDeclaringClass(), this.getString(name, category, defaultValue.toString(), comment, values));
   }

   public String getString(String name, String category, String defaultValue, String comment) {
      return this.getString(name, category, defaultValue, comment, name, null);
   }

   public String getString(String name, String category, String defaultValue, String comment, String langKey) {
      return this.getString(name, category, defaultValue, comment, langKey, null);
   }

   public String getString(String name, String category, String defaultValue, String comment, Pattern pattern) {
      return this.getString(name, category, defaultValue, comment, name, pattern);
   }

   public String getString(String name, String category, String defaultValue, String comment, String langKey, Pattern pattern) {
      Property prop = this.get(category, name, defaultValue);
      prop.setLanguageKey(langKey);
      prop.setValidationPattern(pattern);
      prop.comment = comment + " [default: " + defaultValue + this.getSynchronizedComment() + "]";
      return prop.getString();
   }

   public String getString(String name, String category, String defaultValue, String comment, String[] validValues) {
      return this.getString(name, category, defaultValue, comment, validValues, name);
   }

   public String getString(String name, String category, String defaultValue, String comment, String[] validValues, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setValidValues(validValues);
      prop.setLanguageKey(langKey);
      prop.comment = comment
         + " [default: "
         + defaultValue
         + this.getSynchronizedComment()
         + "]"
         + NEW_LINE
         + "Valid values: "
         + Arrays.stream(validValues).collect(Collectors.joining(", "));
      return prop.getString();
   }

   public String[] getStringList(String name, String category, String[] defaultValues, String comment) {
      return this.getStringList(name, category, defaultValues, comment, (String[])null, name);
   }

   public String[] getStringList(String name, String category, String[] defaultValue, String comment, String[] validValues) {
      return this.getStringList(name, category, defaultValue, comment, validValues, name);
   }

   public String[] getStringList(String name, String category, String[] defaultValue, String comment, String[] validValues, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setLanguageKey(langKey);
      prop.setValidValues(validValues);
      prop.comment = comment + " [default: " + prop.getDefault() + this.getSynchronizedComment() + "]";
      return prop.getStringList();
   }

   public boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
      return this.getBoolean(name, category, defaultValue, comment, name);
   }

   public boolean getBoolean(String name, String category, boolean defaultValue, String comment, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setLanguageKey(langKey);
      prop.comment = comment + " [default: " + defaultValue + this.getSynchronizedComment() + "]";
      return prop.getBoolean(defaultValue);
   }

   public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
      return this.getInt(name, category, defaultValue, minValue, maxValue, comment, name);
   }

   public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setLanguageKey(langKey);
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + this.getSynchronizedComment() + "]";
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      return prop.getInt(defaultValue) < minValue ? minValue : (prop.getInt(defaultValue) > maxValue ? maxValue : prop.getInt(defaultValue));
   }

   public double getDouble(String name, String category, double defaultValue, double minValue, double maxValue, String comment) {
      return this.getDouble(name, category, defaultValue, minValue, maxValue, comment, name);
   }

   public double getDouble(String name, String category, double defaultValue, double minValue, double maxValue, String comment, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setLanguageKey(langKey);
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + this.getSynchronizedComment() + "]";
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);

      try {
         return Double.parseDouble(prop.getString()) < minValue
            ? minValue
            : (Double.parseDouble(prop.getString()) > maxValue ? maxValue : Double.parseDouble(prop.getString()));
      } catch (Exception var13) {
         EnigmaticLegacy.LOGGER.warn("Invalid value specified for '" + name + "' in category '" + category + "': " + prop.getString());
         EnigmaticLegacy.LOGGER.warn("Default value will be used: " + defaultValue);
         EnigmaticLegacy.LOGGER.warn("Stacktrace: ");
         EnigmaticLegacy.LOGGER.catching(var13);
         return defaultValue;
      }
   }

   public File getConfigFile() {
      return this.file;
   }

   public void attachBeholder() {
      this.executeSided(() -> {
         try {
            this.associatedBeholder = new Configuration.ConfigBeholder(this, Thread.currentThread().getContextClassLoader());
            FileWatcher.defaultInstance().addWatch(this.getConfigFile(), this.associatedBeholder);
         } catch (IOException var2) {
            throw new RuntimeException("Couldn't watch config file", var2);
         }
      });
   }

   public void detachBeholder() {
      this.executeSided(() -> FileWatcher.defaultInstance().removeWatch(this.getConfigFile()));
   }

   public void attachOverloadingAction(Consumer<Configuration> action) {
      this.executeSided(() -> this.overloadingAction = action);
   }

   private void executeSided(Runnable run) {
      if (this.sidedType.isSided()) {
         DistExecutor.unsafeRunWhenOn(this.sidedType.getDist(), () -> run);
      } else {
         run.run();
      }
   }

   public boolean isFirstLoadPassed() {
      return this.firstLoadPassed;
   }

   private static class ConfigBeholder implements Runnable {
      private final Configuration config;
      private final ClassLoader realClassLoader;
      private long lastCall;

      protected ConfigBeholder(Configuration config, ClassLoader classLoader) {
         this.config = config;
         this.realClassLoader = classLoader;
         this.lastCall = System.currentTimeMillis();
      }

      @Override
      public void run() {
         if (!this.config.isOverloading && System.currentTimeMillis() - this.lastCall >= 200L) {
            this.lastCall = System.currentTimeMillis();
            Thread.currentThread().setContextClassLoader(this.realClassLoader);
            System.out.println("File just got changed: " + this.config.getConfigFile());
            System.out.println("Initiating overloading procedure...");
            if (this.config.overloadingAction != null) {
               this.config.overloadingAction.accept(this.config);
            }
         }
      }
   }

   public static enum SidedConfigType {
      CLIENT,
      SERVER,
      COMMON;

      public boolean isSided() {
         return this != COMMON;
      }

      @Nullable
      public Dist getDist() {
         if (this == CLIENT) {
            return Dist.CLIENT;
         } else {
            return this == SERVER ? Dist.DEDICATED_SERVER : null;
         }
      }
   }

   public static class UnicodeInputStreamReader extends Reader {
      private final InputStreamReader input;
      private final String defaultEnc;

      public UnicodeInputStreamReader(InputStream source, String encoding) throws IOException {
         this.defaultEnc = encoding;
         String enc = encoding;
         byte[] data = new byte[4];
         PushbackInputStream pbStream = new PushbackInputStream(source, data.length);
         int read = pbStream.read(data, 0, data.length);
         int size = 0;
         int bom16 = (data[0] & 255) << 8 | data[1] & 255;
         int bom24 = bom16 << 8 | data[2] & 255;
         int bom32 = bom24 << 8 | data[3] & 255;
         if (bom24 == 15711167) {
            enc = "UTF-8";
            size = 3;
         } else if (bom16 == 65279) {
            enc = "UTF-16BE";
            size = 2;
         } else if (bom16 == 65534) {
            enc = "UTF-16LE";
            size = 2;
         } else if (bom32 == 65279) {
            enc = "UTF-32BE";
            size = 4;
         } else if (bom32 == -131072) {
            enc = "UTF-32LE";
            size = 4;
         }

         if (size < read) {
            pbStream.unread(data, size, read - size);
         }

         this.input = new InputStreamReader(pbStream, enc);
      }

      public String getEncoding() {
         return this.input.getEncoding();
      }

      @Override
      public int read(char[] cbuf, int off, int len) throws IOException {
         return this.input.read(cbuf, off, len);
      }

      @Override
      public void close() throws IOException {
         this.input.close();
      }
   }

   public static enum VersioningPolicy {
      AGGRESSIVE,
      RESPECTFUL,
      NOBLE,
      DISMISSIVE;
   }
}
