package com.daminhluxa.demoLuuXa.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
           "cloud_name", "dizu39jwb",
                "api_key", "585317736979912",
                "api_secret", "nhKxMs4kzbKbQm_gt5caWu9iLwk"
        ));
    }
}
