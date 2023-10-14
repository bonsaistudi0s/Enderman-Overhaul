package tech.alexnijjar.endermanoverhaul.client.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Config("endermanoverhaul-client")
public final class EndermanOverhaulClientConfig {

    @ConfigEntry(
        id = "replaceDefaultEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.replaceDefaultEnderman"
    )
    public static boolean replaceDefaultEnderman = true;
}
