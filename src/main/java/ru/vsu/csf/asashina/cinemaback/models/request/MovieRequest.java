package ru.vsu.csf.asashina.cinemaback.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.asashina.cinemaback.annotations.Duration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieRequest {

    @NotBlank
    private String description;

    @DateTimeFormat(pattern = "HH:mm")
    @Duration
    private LocalTime duration;

    @NotBlank
    @Size(max = 100)
    private String title;

    private MultipartFile poster;
}
