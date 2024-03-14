package com.malexj.model.request;

import com.malexj.model.base.Filter;
import com.malexj.model.base.type.SubscriptionContext;
import com.malexj.model.base.type.SubscriptionType;

public record SubscriptionRequest(
    Long chatId,
    String templateId,
    SubscriptionType type,
    SubscriptionContext context,
    Filter filter,
    boolean isActive) {

  public SubscriptionRequest(
      Long chatId,
      String templateId,
      SubscriptionType type,
      SubscriptionContext context,
      Filter filter) {
    this(chatId, templateId, type, context, filter, true);
  }
}
