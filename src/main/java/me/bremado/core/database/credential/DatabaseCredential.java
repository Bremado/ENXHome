package me.bremado.core.database.credential;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseCredential {

    private String host;
    private Integer port;

    private String database;
    private String username, password;
}
