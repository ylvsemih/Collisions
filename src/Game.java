import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private Dimension boardDimension;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private int time;
    private boolean begin;
    private boolean startGame;

    public Game() {
        this.begin = Boolean.TRUE;
        this.startGame = Boolean.TRUE;
        players = new ArrayList<>();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void readRules(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        settings(list);
    }

    public void writeResult(String fileName, String result) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settings(List<String> arrayList) {
        Player player = null;
        String[] strings = null;
        for (String line : arrayList) {
            if (!begin) {
                strings = line.split(";");
                player = new Player();
                player.setX(Integer.parseInt(strings[0]));
                player.setY(Integer.parseInt(strings[1]));
                player.setDirection(strings[2]);
                players.add(player);

            } else {
                strings = line.split(";");
                boardDimension = new Dimension();
                boardDimension.setX(Integer.parseInt(strings[0]));
                boardDimension.setY(Integer.parseInt(strings[1]));
                this.numberOfPlayers = Integer.parseInt(strings[2]);
                this.time = Integer.parseInt(strings[3]);
                this.begin = Boolean.FALSE;
            }
        }
    }

    public void startGame() {
        int stepCount = -1;
        while (time > 0) {
            stepCount++;
            for (Player player : players) {
                if ((boardDimension.getX() - player.getX()) == 0 || (boardDimension.getX() - player.getX()) == (boardDimension.getX() - 1) ||
                        (boardDimension.getY() - player.getY()) == 0 || (boardDimension.getY() - player.getY()) == (boardDimension.getY() - 1)) {
                    time = 0;
                    break;
                } else {
                    switch (player.getDirection()) {
                        case "L":
                            player.setX(player.getX() - 1);
                            break;
                        case "R":
                            player.setX(player.getX() + 1);
                            break;
                        case "U":
                            player.setY(player.getY() - 1);
                            break;
                        case "D":
                            player.setY(player.getY() + 1);
                            break;
                    }
                }
            }
            time--;
        }
        String result = "";
        if (time == 0) result = "No collision";
        else result = String.valueOf(stepCount);
        writeResult("simulation.out.txt", result);
    }
}
