package com.bikkadit.electoronic.store.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

     private String JwtToken;

     private UserDto user;
}
