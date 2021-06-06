package org.twitter.service.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.twitter.service.annotations.EncodedMapping;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordEncoderMapper {

    final PasswordEncoder passwordEncoder;

    @EncodedMapping
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
