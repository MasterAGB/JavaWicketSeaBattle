package seabattleWicketPackage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */


public class GameCellRenderer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cell;

    private int x;
    private int y;
    public int ownerPlayerId;


    private static int cellNumber = 0;
    private static int MAX_LIVES = 3;
    private static int MAX_LEVEL = 3;


    public CellState cellState = CellState.IDLE;
    private int lives = 0;
    public int level = 0;

    public enum CellState {
        IDLE(0), HAS_ISLAND(1), HAS_SHIP(2), HAS_WRECK(3), HAS_BARREL(4);

        private final int value;

        private CellState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String ToCssClass(int ownerPlayerId) {
            StringBuilder cssClass = new StringBuilder(256);
            switch (value) {
                case 1:
                    cssClass.append(" has_island");
                    break;
                case 2:
                    if (ownerPlayerId == GameSession.get().GetPlayerId()) {

                        cssClass.append(" has_ship");
                    } else {

                        cssClass.append(" has_enemy_ship");
                    }
                    break;
                case 3:

                    cssClass.append(" has_wreck");
                    break;
                case 4:

                    cssClass.append(" has_barrel");
                    break;
                default:

                    cssClass.append(" idle");
                    break;

            }

            return cssClass.toString();
        }


        public String getLivesLevelHtml(int ownerPlayerId, int lives, int level) {
            StringBuilder cssClass = new StringBuilder(256);

            switch (value) {
                case 1:
                case 2:
                case 3:
                    cssClass.append("<span class='lives" + lives + "'></span>");
                    break;

            }

            switch (value) {
                case 2:
                    if (ownerPlayerId == GameSession.get().GetPlayerId()) {
                        if (level > 1)
                            cssClass.append("<span class='level" + level + "'></span>");
                    }
                    break;
            }

            return cssClass.toString();
        }

    }


    public GameCellRenderer(String cell, int x, int y, CellState state) {
        super();
        //System.out.println("cell " + cell+":"+cellNumber);
        cellNumber++;

        this.cellState = state;
        if (this.cellState == CellState.HAS_ISLAND) {
            this.lives = 2;
        }
        this.cell = "Start Cell:" + cell;
        this.x = x;
        this.y = y;
    }


    //eti metodi vizivajem iz  populateItem  - new PropertyModel<String>(message, "player"));
    //avtomatom budet vizivatj getPlayerName
    public String getCell() {
        //System.out.println("PropertyModel GameCellRenderer getCell method: cell " + cell);
        return "<div " +
                "onMouseMove='moveCell(" + x + "," + y + ");' " +
                "onClick='clickCell(" + x + "," + y + ");' " +
                "class='realCell " + cellState.ToCssClass(ownerPlayerId) + "' " +
                "style='width:" + GameMap.cellWidth + "px;height:" + GameMap.cellHeight + "px;left:" + (GameMap.cellWidth / 2 * (x - y) + GameMap.cellWidth / 2 * 7) + "px;top:" + (GameMap.cellHeight / 2 * (x + y)) + "px;'>" +
                cellState.getLivesLevelHtml(ownerPlayerId, lives, level) +
                //cell + "\n" + new SimpleDateFormat("HH:mm:ss").format(new Date()) +
                "</div>";
        //return cell;
    }


    public void setCell(GameCommand.CommandTypes command, int playerId) {

        switch (command) {

            case AUTO:


                switch (cellState) {

                    case IDLE:
                        if(GameSession.get().Consume(100)){
                        cellState = CellState.HAS_SHIP;
                        ownerPlayerId = playerId;
                        lives = 3;
                        level = 1;
                        }
                        break;
                    case HAS_ISLAND:
                        lives--;
                        if (lives <= 0) {
                            if(GameSession.get().Consume(50)){
                            cellState = CellState.IDLE;
                            }
                        }
                        break;
                    case HAS_BARREL:

                            if(GameSession.get().Consume(-100)){
                            cellState = CellState.IDLE;
                            }
                        break;
                    case HAS_SHIP:
                        if (ownerPlayerId == playerId) {
                            if (lives < MAX_LIVES) {
                                if(GameSession.get().Consume(50)){
                                lives++;
                                }
                            } else {
                                if (level < MAX_LEVEL) {
                                    if(GameSession.get().Consume(100)){
                                    level++;
                                    }
                                }
                            }
                        } else {
                            lives--;
                            if (lives <= 0) {
                                if(GameSession.get().Consume(100)){
                                cellState = CellState.HAS_WRECK;
                                lives = 2;
                                }
                            }
                        }
                        break;
                    case HAS_WRECK:
                        lives--;
                        if (lives <= 0) {
                            if(GameSession.get().Consume(50)){
                                cellState = CellState.IDLE;
                            }
                        }
                        break;
                }


                break;
            case DESTROY_OWN:
                cellState = CellState.IDLE;
                break;
            case DESTROY_ENEMY:
                cellState = CellState.IDLE;
                break;
            case SET_OWN:
                cellState = CellState.HAS_SHIP;
                break;
            case SET_ENEMY:
                cellState = CellState.HAS_SHIP;
                break;
            case SET_ISLAND:
                cellState = CellState.HAS_ISLAND;
                break;
            case DESTROY_ISLAND:
                cellState = CellState.IDLE;
                break;
            case SET_BARREL:
                cellState = CellState.HAS_BARREL;
                break;
        }

        cell = "" + GameSession.get().getPlayerNameById(playerId) + ":" + command.ToString();
    }
}