package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {
   private static final char[] HC = "0123456789ABCDEF".toCharArray();
   private static final byte[] HB;
   private static final int[] sInputCodes;
   private static final int[] sInputCodesUTF8;
   private static final int[] sInputCodesJsNames;
   private static final int[] sInputCodesUtf8JsNames;
   private static final int[] sInputCodesComment;
   private static final int[] sInputCodesWS;
   private static final int[] sOutputEscapes128;
   private static final int[] sHexValues;

   public static int[] getInputCodeLatin1() {
      return sInputCodes;
   }

   public static int[] getInputCodeUtf8() {
      return sInputCodesUTF8;
   }

   public static int[] getInputCodeLatin1JsNames() {
      return sInputCodesJsNames;
   }

   public static int[] getInputCodeUtf8JsNames() {
      return sInputCodesUtf8JsNames;
   }

   public static int[] getInputCodeComment() {
      return sInputCodesComment;
   }

   public static int[] getInputCodeWS() {
      return sInputCodesWS;
   }

   public static int[] get7BitOutputEscapes() {
      return sOutputEscapes128;
   }

   public static int charToHex(int ch) {
      return ch > 127 ? -1 : sHexValues[ch];
   }

   public static void appendQuoted(StringBuilder sb, String content) {
      int[] escCodes = sOutputEscapes128;
      int escLen = escCodes.length;
      int i = 0;

      for (int len = content.length(); i < len; i++) {
         char c = content.charAt(i);
         if (c < escLen && escCodes[c] != 0) {
            sb.append('\\');
            int escCode = escCodes[c];
            if (escCode < 0) {
               sb.append('u');
               sb.append('0');
               sb.append('0');
               sb.append(HC[c >> 4]);
               sb.append(HC[c & 15]);
            } else {
               sb.append((char)escCode);
            }
         } else {
            sb.append(c);
         }
      }
   }

   public static char[] copyHexChars() {
      return (char[])HC.clone();
   }

   public static byte[] copyHexBytes() {
      return (byte[])HB.clone();
   }

   static {
      int len = HC.length;
      HB = new byte[len];

      for (int i = 0; i < len; i++) {
         HB[i] = (byte)HC[i];
      }

      int[] table = new int[256];

      for (int i = 0; i < 32; i++) {
         table[i] = -1;
      }

      table[34] = 1;
      table[92] = 1;
      sInputCodes = table;
      int[] tablex = new int[sInputCodes.length];
      System.arraycopy(sInputCodes, 0, tablex, 0, tablex.length);

      for (int c = 128; c < 256; c++) {
         int code;
         if ((c & 224) == 192) {
            code = 2;
         } else if ((c & 240) == 224) {
            code = 3;
         } else if ((c & 248) == 240) {
            code = 4;
         } else {
            code = -1;
         }

         tablex[c] = code;
      }

      sInputCodesUTF8 = tablex;
      int[] tablexx = new int[256];
      Arrays.fill(tablexx, -1);

      for (int i = 33; i < 256; i++) {
         if (Character.isJavaIdentifierPart((char)i)) {
            tablexx[i] = 0;
         }
      }

      tablexx[64] = 0;
      tablexx[35] = 0;
      tablexx[42] = 0;
      tablexx[45] = 0;
      tablexx[43] = 0;
      sInputCodesJsNames = tablexx;
      int[] tablexxx = new int[256];
      System.arraycopy(sInputCodesJsNames, 0, tablexxx, 0, tablexxx.length);
      Arrays.fill(tablexxx, 128, 128, 0);
      sInputCodesUtf8JsNames = tablexxx;
      int[] buf = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, buf, 128, 128);
      Arrays.fill(buf, 0, 32, -1);
      buf[9] = 0;
      buf[10] = 10;
      buf[13] = 13;
      buf[42] = 42;
      sInputCodesComment = buf;
      int[] bufx = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, bufx, 128, 128);
      Arrays.fill(bufx, 0, 32, -1);
      bufx[32] = 1;
      bufx[9] = 1;
      bufx[10] = 10;
      bufx[13] = 13;
      bufx[47] = 47;
      bufx[35] = 35;
      sInputCodesWS = bufx;
      int[] tablexxxx = new int[128];

      for (int ix = 0; ix < 32; ix++) {
         tablexxxx[ix] = -1;
      }

      tablexxxx[34] = 34;
      tablexxxx[92] = 92;
      tablexxxx[8] = 98;
      tablexxxx[9] = 116;
      tablexxxx[12] = 102;
      tablexxxx[10] = 110;
      tablexxxx[13] = 114;
      sOutputEscapes128 = tablexxxx;
      sHexValues = new int[128];
      Arrays.fill(sHexValues, -1);
      len = 0;

      while (len < 10) {
         sHexValues[48 + len] = len++;
      }

      for (int ix = 0; ix < 6; ix++) {
         sHexValues[97 + ix] = 10 + ix;
         sHexValues[65 + ix] = 10 + ix;
      }
   }
}
