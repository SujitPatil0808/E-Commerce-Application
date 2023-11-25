package com.bikkadit.electoronic.store.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {

public String imageName;
    public String message;
    public Boolean status;
    public HttpStatus statusCode;

}
