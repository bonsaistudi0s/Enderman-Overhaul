package tech.alexnijjar.endermanoverhaul.client.config.info;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColorGradient;

public class EndermanOverhaulConfigColor implements ResourcefulConfigColorGradient {

    public static final EndermanOverhaulConfigColor INSTANCE = new EndermanOverhaulConfigColor();

    @Override
    public String first() {
        return "#e8bff2";
    }

    @Override
    public String second() {
        return "#181a23";
    }

    @Override
    public String degree() {
        return "45deg";
    }
}
