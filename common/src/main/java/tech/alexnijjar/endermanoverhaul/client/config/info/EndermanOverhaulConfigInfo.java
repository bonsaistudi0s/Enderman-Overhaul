package tech.alexnijjar.endermanoverhaul.client.config.info;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColor;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;

public class EndermanOverhaulConfigInfo implements ResourcefulConfigInfo {

    public static final TranslatableValue TITLE = new TranslatableValue(
        "Enderman Overhaul",
        "config.endermanoverhaul.title"
    );

    public static final TranslatableValue TITLE_CLIENT = new TranslatableValue(
        "Enderman Overhaul Client",
        "config.endermanoverhaul.client.title"
    );

    public static final TranslatableValue DESCRIPTION = new TranslatableValue(
        "Enderman overhaul adds biome-specific Enderman!",
        "config.endermanoverhaul.description"
    );

    private final boolean isClient;

    public EndermanOverhaulConfigInfo(String id) {
        this.isClient = id.endsWith("-client");
    }

    @Override
    public TranslatableValue title() {
        return isClient ? TITLE_CLIENT : TITLE;
    }

    @Override
    public TranslatableValue description() {
        return DESCRIPTION;
    }

    @Override
    public String icon() {
        return "circle";
    }

    @Override
    public ResourcefulConfigColor color() {
        return EndermanOverhaulConfigColor.INSTANCE;
    }

    @Override
    public ResourcefulConfigLink[] links() {
        return EndermanOverhaulConfigLinks.LINKS;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
