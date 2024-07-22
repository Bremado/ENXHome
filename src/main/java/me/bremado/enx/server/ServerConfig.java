package me.bremado.enx.server;

import lombok.Getter;
import lombok.Setter;

public class ServerConfig {

    @Getter @Setter
    private static int cooldown;

    @Getter @Setter
    private static boolean seeParticles;

    @Getter @Setter
    private static String particleType;

    @Getter @Setter
    private static String soundType;
}
