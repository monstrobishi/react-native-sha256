package com.sha256lib;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.UUID;

public class Sha256Module extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public Sha256Module(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "sha256Lib";
  }

  public static byte[] hexToByteArray(String hex) {
      hex = hex.length()%2 != 0?"0"+hex:hex;

      byte[] b = new byte[hex.length() / 2];

      for (int i = 0; i < b.length; i++) {
          int index = i * 2;
          int v = Integer.parseInt(hex.substring(index, index + 2), 16);
          b[i] = (byte) v;
      }
      return b;
  }

  @ReactMethod
  public void sha256(final String toHash, Promise promise) {
      MessageDigest md = null;
      try {
          md = MessageDigest.getInstance("SHA-256");
          md.update(hexToByteArray(toHash));
          byte[] digest = md.digest();
          String hash = String.format("%064x", new java.math.BigInteger(1, digest));
          promise.resolve(hash);

      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
          promise.reject("sha256", e.getMessage());
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          promise.reject("sha256", e.getMessage());
      }
  }

}
