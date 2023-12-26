package com.bikkadit.electoronic.store.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {


    private String email;

    private String password;
}
