package com.malexj.service;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.base.type.SubscriptionType;
import com.malexj.model.request.SubscriptionRequest;
import com.malexj.model.response.SubscriptionResponse;
import com.malexj.repository.SubscriptionRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

  private final ObjectMapper mapper;
  private final SubscriptionRepository repository;

  public Flux<SubscriptionResponse> findAll(String type, Boolean isActive) {
    if (Objects.isNull(type) && Objects.isNull(isActive)) {
      return repository
          .findAll()
          .map(mapper::entityToResponse)
          .doOnNext(resp -> log.info("Find all subscription"));
    }
    if (Objects.isNull(type)) {
      return repository
          .findAllByActive(isActive)
          .map(mapper::entityToResponse)
          .doOnNext(resp -> log.info("Find all active - {} subscription", isActive));
    }
    var subscriptionType = SubscriptionType.findType(type);
    if (Objects.isNull(isActive)) {
      return repository
          .findAllByType(subscriptionType)
          .map(mapper::entityToResponse)
          .doOnNext(resp -> log.info("Find all subscription with type - {} ", subscriptionType));
    }

    return repository
        .findAllByTypeAndActive(subscriptionType, isActive)
        .map(mapper::entityToResponse)
        .doOnNext(
            resp ->
                log.info(
                    "Find all active - {} subscription with type - {}",
                    isActive,
                    subscriptionType));
  }

  public Mono<SubscriptionResponse> createSubscription(SubscriptionRequest request) {
    return Mono.fromSupplier(() -> mapper.requestToEntity(request))
        .flatMap(repository::save)
        .map(mapper::entityToResponse);
  }

  public Mono<Long> updateSubscription(String id) {
    return repository
        .updateSubscriptionEntity(id)
        .doOnNext(resp -> log.info("Records updated - {}", resp));
  }
}
