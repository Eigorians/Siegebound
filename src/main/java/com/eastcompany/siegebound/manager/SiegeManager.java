package com.eastcompany.siegebound.manager;

import com.eastcompany.siegebound.manager.player.PlayerManager;
import com.eastcompany.siegebound.manager.team.TeamManager;

public class SiegeManager {

    private final PlayerManager playerManager;
    private final TeamManager teamManager;

    public SiegeManager() {
        this.playerManager = new PlayerManager();
        this.teamManager = new TeamManager();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
}
