package me.bremado.core.model.homes;

import lombok.*;
import me.bremado.core.utils.LocationInfo;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Home {

    private String name;
    private LocationInfo locationInfo;
}
