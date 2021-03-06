package org.apexie.lobbype;

import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import dev.waterdog.ProxyServer;
import dev.waterdog.command.Command;
import dev.waterdog.command.CommandSender;
import dev.waterdog.command.CommandSettings;
import dev.waterdog.network.ServerInfo;
import dev.waterdog.player.ProxiedPlayer;
import dev.waterdog.utils.Configuration;

import java.util.Collections;

public class LobbyCommand extends Command {

    public LobbyCommand() {
        super("lobby", CommandSettings.builder()
                .setDescription("Allows you to teleport back to the lobby server")
                .setPermission("lobby.command")
                .setAliases(new String[]{"hub"})
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String s, String[] strings) {
        Configuration configuration = LobbyPE.getInstance().getConfig();
        String lobby = configuration.getString("lobby-server");
        String inLobby = configuration.getString("messages.in-lobby");
        String playersOnly = configuration.getString("messages.players-only");
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(playersOnly);
            return true;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServerInfo().toString().equals(lobby)) {
            player.sendMessage(inLobby);
            return true;
        }
        ServerInfo target = ProxyServer.getInstance().getServerInfo(lobby);
        player.connect(target);
        return true;
    }

    @Override
    public CommandData craftNetwork() {
        CommandParamData[][] parameterData = new CommandParamData[][]{{
        }};
        return new CommandData(this.getName(), this.getDescription(), Collections.emptyList(), (byte) 0, null, parameterData);
    }
}
