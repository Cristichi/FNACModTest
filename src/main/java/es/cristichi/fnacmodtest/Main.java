package es.cristichi.fnacmodtest;

import es.cristichi.fnac.cnight.CustomNightRegistry;
import es.cristichi.fnacmodtest.anims.Spyro;
import es.cristichi.fnac.exception.NightException;
import es.cristichi.fnac.gui.MenuJC;
import es.cristichi.fnac.gui.NightJC;
import es.cristichi.fnac.io.Settings;
import es.cristichi.fnac.obj.Jumpscare;
import es.cristichi.fnac.obj.cams.CrisRestaurantMap;
import es.cristichi.fnac.obj.nights.NightFactory;
import es.cristichi.fnac.obj.nights.NightRegistry;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CustomNightRegistry.registerPackage("es.cristichi.fnacmodtest.anims");
        
        NightRegistry.registerNight(0, null);
        NightRegistry.registerNight(1, new NightFactory() {
            @Override
            public MenuJC.Item getItem() {
                return new MenuJC.Item("night1", "Let's play with Spyro", "Night 1", null);
            }
            
            @Override
            public NightJC createNight(Settings settings, Jumpscare powerOutage,
                                       Random rng) throws IOException, NightException {
                CrisRestaurantMap nightMap = new CrisRestaurantMap();
                nightMap.addCamAnimatronics("kitchen", new Spyro("spyro", Map.of(0,20), List.of(
                        List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                        List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                ), rng));
                return new NightJC("Spyro's Night", settings.getFps(), nightMap, "night/n1/paper.png", powerOutage, rng, 20,
                        .45f, "night/general/completed.wav");
            }
        });
        
        new es.cristichi.fnac.Main().run();
    }
}