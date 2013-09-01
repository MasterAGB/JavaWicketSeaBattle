package seabattleWicketPackage;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 25.08.13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class GameMap implements Serializable {


    public static int cellWidth = 200;
    public static int cellHeight = 100;

    private static final long serialVersionUID = 1L;

    //private String map;

    static GameCellRenderer map[][] = new GameCellRenderer[8][8];


    public GameMap() {
        System.out.println("!!!init game map");
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                //System.out.println("setting new cell");
                double random=Math.random();
                if (random > 0.8) {

                    if (random > 0.9) {
                        map[x][y] = new GameCellRenderer(x + "x" + y, x, y, GameCellRenderer.CellState.HAS_ISLAND);
                    } else {
                        map[x][y] = new GameCellRenderer(x + "x" + y, x, y, GameCellRenderer.CellState.HAS_BARREL);
                    }
                } else {
                    map[x][y] = new GameCellRenderer(x + "x" + y, x, y, GameCellRenderer.CellState.IDLE);
                }


            }
        }

    }


    public GameCellRenderer GetMapCell(int x, int y) {
        return map[x][y];
    }


    public void setMap(int x, int y, GameCommand.CommandTypes command, int playerId) {
        map[x][y].setCell(command, playerId);
    }

    public LinkedList<GameCellRenderer> getMapCells() {

        LinkedList<GameCellRenderer> gameCellRenderers = new LinkedList<GameCellRenderer>();


        //GameCellRenderer gameCell = new GameCellRenderer("1x1");

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                gameCellRenderers.add(GetMapCell(x, y));
            }
        }

        return gameCellRenderers;

    }

    public int getIncome() {
        int income = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                GameCellRenderer gameCell = GetMapCell(x, y);
                if (gameCell.cellState == GameCellRenderer.CellState.HAS_SHIP) {
                    if (gameCell.ownerPlayerId == GameSession.get().GetPlayerId()) {
                        income += 100;
                        if (gameCell.level > 1) {
                            income += 200;
                            if (gameCell.level > 2) {
                                income += 300;
                            }
                        }
                    }
                }
            }
        }
        return income;
    }
}
