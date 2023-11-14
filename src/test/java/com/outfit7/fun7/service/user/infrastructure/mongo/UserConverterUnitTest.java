package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.dto.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserConverterUnitTest extends UnitTest {

  private final UserConverter userConverter = new UserConverter();

  @Test
  void shouldConvertToEntity() {
    // given
    User user = new User("123", "US", 3);

    // when
    UserEntity userEntity = userConverter.toEntity(user);

    // then
    assertThat(userEntity).isNotNull();
    assertThat(userEntity.getUserId()).isEqualTo(user.getUserId());
    assertThat(userEntity.getCountryCode()).isEqualTo(user.getCountryCode());
    assertThat(userEntity.getGameCount()).isEqualTo(user.getGameCount());
  }

  @Test
  void ShouldConvertToUserInfo() {
    // given
    UserEntity userEntity = new UserEntity("123", "US", 3);

    // when
    User user = userConverter.toUser(userEntity);

    // then
    assertThat(user).isNotNull();
    assertThat(user.getUserId()).isEqualTo(userEntity.getUserId());
    assertThat(user.getCountryCode()).isEqualTo(userEntity.getCountryCode());
    assertThat(user.getGameCount()).isEqualTo(userEntity.getGameCount());
  }
}
