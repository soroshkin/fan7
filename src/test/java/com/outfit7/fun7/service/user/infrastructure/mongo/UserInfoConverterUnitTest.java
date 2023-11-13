package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoConverterUnitTest extends UnitTest {

  private final UserInfoConverter userInfoConverter = new UserInfoConverter();

  @Test
  void shouldConvertToEntity() {
    // given
    UserInfo userInfo = new UserInfo("123", "US", 3);

    // when
    UserInfoEntity userInfoEntity = userInfoConverter.toEntity(userInfo);

    // then
    assertThat(userInfoEntity).isNotNull();
    assertThat(userInfoEntity.getUserId()).isEqualTo(userInfo.getUserId());
    assertThat(userInfoEntity.getCountryCode()).isEqualTo(userInfo.getCountryCode());
    assertThat(userInfoEntity.getGameCount()).isEqualTo(userInfo.getGameCount());
  }

  @Test
  void ShouldConvertToUserInfo() {
    // given
    UserInfoEntity userInfoEntity = new UserInfoEntity("123", "US", 3);

    // when
    UserInfo userInfo = userInfoConverter.toUserInfo(userInfoEntity);

    // then
    assertThat(userInfo).isNotNull();
    assertThat(userInfo.getUserId()).isEqualTo(userInfoEntity.getUserId());
    assertThat(userInfo.getCountryCode()).isEqualTo(userInfoEntity.getCountryCode());
    assertThat(userInfo.getGameCount()).isEqualTo(userInfoEntity.getGameCount());
  }
}
