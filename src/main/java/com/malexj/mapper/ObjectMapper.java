package com.malexj.mapper;

import com.malexj.model.entity.SubscriptionEntity;
import com.malexj.model.request.SubscriptionRequest;
import com.malexj.model.response.SubscriptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModified", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(source = "isActive", target = "active")
  SubscriptionEntity requestToEntity(SubscriptionRequest request);

  @Mapping(source = "active", target = "isActive")
  SubscriptionResponse entityToResponse(SubscriptionEntity entity);
}
