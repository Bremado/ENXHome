package me.bremado.core.model;

import lombok.*;
import me.bremado.core.model.homes.Home;

import java.util.List;
import java.util.UUID;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnxPlayer {

    private UUID uniqueId;

    private List<Home> homes;
}
