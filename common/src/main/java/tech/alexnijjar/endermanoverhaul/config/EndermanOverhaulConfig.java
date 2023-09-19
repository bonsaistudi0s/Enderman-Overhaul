package tech.alexnijjar.endermanoverhaul.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.web.annotations.Gradient;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Config("endermanoverhaul")
@WebInfo(
    title = "Enderman Overhaul",
    description = "Enderman overhaul adds biome-specific Enderman!",

    icon = "cog",
    gradient = @Gradient(value = "45deg", first = "#7F4DEE", second = "#E7797A")
)
public final class EndermanOverhaulConfig {

}
