package com.malexj.model.base.type;

import java.util.Arrays;

public enum SubscriptionType {
  RSS,
  HTML,
  YOUTUBE,
  TELEGRAM;

  public static SubscriptionType findType(String type) {
    return Arrays.stream(values())
        .filter(val -> val.name().equalsIgnoreCase(type))
        .findFirst()
        .orElseThrow();
  }
}
