package com.uem.boxit.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("boxit")
public class BoxItApiProperty {
    private String originPermitida = "http://localhost:8000";

    private final S3 s3 = new S3();

    @Getter
    @Setter
    public static class S3 {

        private String accessKeyId;

        private String secretAccessKey;

        private String bucket = "boxit-api";
    }
}
