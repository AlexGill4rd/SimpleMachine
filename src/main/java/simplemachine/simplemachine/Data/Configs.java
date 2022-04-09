package simplemachine.simplemachine.Data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import simplemachine.simplemachine.SimpleMachine;

import java.io.File;
import java.io.IOException;

public class Configs {

    public static File customConfigFile1;
    private static FileConfiguration customConfig1;

    private static final SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);

    public static FileConfiguration getCustomConfig1() {
        return customConfig1;
    }

    public static void createCustomConfig1() {
        customConfigFile1 = new File(plugin.getDataFolder(), "Messages.yml");
        if (!customConfigFile1.exists()) {
            customConfigFile1.getParentFile().mkdirs();
            plugin.saveResource("Messages.yml", false);
        }

        customConfig1 = new YamlConfiguration();

        try {
            customConfig1.load(customConfigFile1);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static File customConfigFile2;
    private static FileConfiguration customConfig2;

    public static FileConfiguration getCustomConfig2() {
        return customConfig2;
    }

    public static void createCustomConfig2() {
        customConfigFile2 = new File(plugin.getDataFolder(), "MachineData.yml");
        if (!customConfigFile2.exists()) {
            customConfigFile2.getParentFile().mkdirs();
            plugin.saveResource("MachineData.yml", false);
        }

        customConfig2 = new YamlConfiguration();

        try {
            customConfig2.load(customConfigFile2);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
