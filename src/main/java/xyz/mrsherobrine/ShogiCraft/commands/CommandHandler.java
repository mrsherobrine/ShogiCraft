package xyz.mrsherobrine.ShogiCraft.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.mrsherobrine.ShogiCraft.shogi.Board;
import xyz.mrsherobrine.ShogiCraft.shogi.Game;
import xyz.mrsherobrine.ShogiCraft.shogi.Tile;
import xyz.mrsherobrine.ShogiCraft.utils.ArmorStandCreator;
import xyz.mrsherobrine.ShogiCraft.utils.LocationChecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {

    private JavaPlugin plugin;
    private Logger logger;

    private LocationChecker locCheck;
    private ArmorStandCreator creator;
    private static final Map<UUID, Tile[][]> boardList = new HashMap<>();

    private Game game;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.locCheck = new LocationChecker(logger);
        this.game = new Game();
        this.creator = new ArmorStandCreator(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            logger.info(Arrays.toString(strings));
            Player player = (Player) commandSender;
            if (strings.length != 0) {

                //TODO main command handling
                switch (strings[0]) {
                    case "create":
                        if (locCheck.checkLocation(player.getLocation())) {
                            boardList.put(player.getUniqueId(), new Board().createNewBoard(player.getUniqueId(), player.getLocation()));
                            player.sendMessage("New board has been created at "+Arrays.toString(locCheck.getBounds()));
                        } else {
                            player.sendMessage("Invalid location! Please check if it's all planks and 9x9.");
                        }
                        break;
                    case "remove":
                        commandSender.sendMessage("not implemented yet lol");
                        break;
                    case "play":
                       //game.setupGame(boardList.get(player.getUniqueId()));
                        break;
                    case "test":

                        if (boardList.containsKey(player.getUniqueId())) {
                            for (int x = 0; x < 9; x++) {
                                boardList.get(player.getUniqueId())[0][x].setPiece(creator.createPiece("P", boardList.get(player.getUniqueId())[0][x], player.getUniqueId()));
                                boardList.get(player.getUniqueId())[1][x].setPiece(creator.createPiece("L", boardList.get(player.getUniqueId())[1][x], player.getUniqueId()));
                                boardList.get(player.getUniqueId())[2][x].setPiece(creator.createPiece("GK", boardList.get(player.getUniqueId())[2][x], player.getUniqueId()));
                            }
//                        creator.createPiece("L", boardList.get(player.getUniqueId())[0][0], player.getUniqueId());
//                        creator.createPiece("L", boardList.get(player.getUniqueId())[0][8], player.getUniqueId());
//                        creator.createPiece("L", boardList.get(player.getUniqueId())[8][0], player.getUniqueId());
//                        creator.createPiece("L", boardList.get(player.getUniqueId())[8][8], player.getUniqueId());
                        } else {
                            player.sendMessage(Component.text("Hey, you don't have a board yet!", NamedTextColor.YELLOW));
                        }
                        break;
                    default:
                        commandSender.sendMessage(Component.text("Hmm, that doesn't look like a known command to me...", NamedTextColor.RED));
                        return false;
                }

            } else {
                return false;
            }
            return true;
        } else {
            commandSender.sendMessage("Wait a minute, you're not a player!");
            return true;
        }
    }

    public Map<UUID, Tile[][]> getBoardList() {
        return boardList;
    }


}