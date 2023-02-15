package com.computicsolutions.openfashion.dto.response;

import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.entity.User;
import lombok.Getter;

/**
 * User DTO for response
 */
@Getter
public class UserResponseDto implements ResponseDto {

    private final String userId;
    private final String userName;
    private final String email;
    private final String password;
    private final String role;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole().getName();
    }

    @Override
    public String toLogJson() {
        return new UserLogResponseDto(this).toLogJson();
    }

    /**
     * This class is used for logging purpose of user DTO for response
     * PII (Personally Identifiable Information) data should be hidden
     */
    @Getter
    private static class UserLogResponseDto implements ResponseDto {

        private final String userId;

        public UserLogResponseDto(UserResponseDto userResponseDto) {
            this.userId = userResponseDto.getUserId();
        }

        @Override
        public String toLogJson() {
            return toJson();
        }
    }
}
