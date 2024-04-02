import javax.swing.JOptionPane;

public class Logic {
    /*
     * Name Number
     * Yellow 1
     * Green 2
     * Black 3
     * Red 4
     */
    int carryOver = 0;
    int currentlyPlaying = 1;
    Boolean yellowOnBoard = false;
    Boolean greenOnBoard = false;
    Boolean redOnBoard = false;
    Boolean blackOnBoard = false;
    int targetColumn = 0;
    int diceRoll = 0;
    Boolean rollNotComplete = true;
    int targetRow = 0;
    int gameField[][] = {
            { 1, 1, 0, 0, 0, 0, -2, 0, 0, 2, 2 },
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 2, 2 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, -8, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 3, 3, 0, 0, 0, 0, 0, 0, 0, 4, 4 },
            { 3, 3, 0, 0, -3, 0, 0, 0, 0, 4, 4 } };

    public int[][] getArray() {
        return (gameField);
    }

    public void rollDice() {
        while (rollNotComplete) {
            diceRoll = (int) (Math.random() * 10);
            if (diceRoll > 0 && diceRoll < 7) {
                rollNotComplete = false;
            }
        }
        rollNotComplete = true;
        diceRoll = diceRoll + 10;
        gameField[5][5] = diceRoll;
    }

    public void logicSelector(int btnname) {
        int row = btnname % 100;
        int column = btnname / 100;
        int piece = gameField[row][column];
        checkBoard();
        switch (piece) {
            case 1:
                logicYellow(row, column);
                cleanup();
                break;
            case 2:
                logicGreen(row, column);
                cleanup();
                break;
            case 3:
                logicBlack(row, column);
                cleanup();
                break;
            case 4:
                logicRed(row, column);
                cleanup();
                break;
            case -8:
                rollDice();
                checkBoard();
                rollThriceCheck();
                break;
            default:
                break;
        }
    }

    public void checkBoard() {
        for (int i = 0; i < 11; i++) {
            if (i == 5) {
                i++;
            } else {
                if (gameField[4][i] == 1 || gameField[6][i] == 1 || gameField[i][6] == 1
                        || gameField[i][4] == 1 | gameField[0][5] == 1 || gameField[10][5] == 1 || gameField[5][0] == 1
                        || gameField[5][10] == 1) {
                    yellowOnBoard = true;
                }
                if (gameField[4][i] == 2 || gameField[6][i] == 2 || gameField[i][6] == 2
                        || gameField[i][4] == 2 | gameField[0][5] == 2 || gameField[10][5] == 2 || gameField[5][0] == 2
                        || gameField[5][10] == 2) {
                    greenOnBoard = true;
                }
                if (gameField[4][i] == 3 || gameField[6][i] == 3 || gameField[i][6] == 3 || gameField[i][4] == 3
                        || gameField[0][5] == 3 || gameField[10][5] == 3 || gameField[5][0] == 3
                        || gameField[5][10] == 3) {
                    blackOnBoard = true;
                }
                if (gameField[4][i] == 4 || gameField[6][i] == 4 || gameField[i][6] == 4 || gameField[i][4] == 4
                        || gameField[0][5] == 4 || gameField[10][5] == 4 || gameField[5][0] == 4
                        || gameField[5][10] == 4) {
                    redOnBoard = true;
                }
            }
        }
    }

    public void logicYellow(int row, int column) {
        if ((gameField[4][0] == 1 && row == 4 && column == 0) || gameField[4][0] == -1) {
            if ((diceRoll == 16)
                    && (gameField[0][0] == 1 || gameField[0][1] == 1 || gameField[1][0] == 1 || gameField[1][1] == 1)
                    && (row == 1 || row == 0) && (column == 0 || column == 1) && (gameField[4][0] != 1)) {
                gameField[4][0] = 1;
                gameField[row][column] = 0;
            } else {
                movePiece(row, column);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please clear your starting area", "Invalid Action",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void logicRed(int row, int column) {
        if ((gameField[6][10] == 4 && row == 6 && column == 10) || gameField[6][10] == -4) {
            if ((diceRoll == 16)
                    && (gameField[10][10] == 4 || gameField[10][9] == 4 || gameField[9][10] == 4
                            || gameField[9][9] == 4)
                    && (row == 10 || row == 9) && (column == 9 || column == 10) && (gameField[6][10] != 4)) {
                gameField[6][10] = 4;
                gameField[row][column] = 0;
            } else {
                movePiece(row, column);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please clear your starting area", "Invalid Action",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void logicGreen(int row, int column) {
        if ((gameField[0][6] == 2 && row == 0 && column == 6) || (gameField[0][6] == -2)) {
            if ((diceRoll == 16)
                    && (gameField[0][10] == 2 || gameField[0][9] == 2 || gameField[1][10] == 2 || gameField[1][9] == 2)
                    && (row == 1 || row == 0) && (column == 9 || column == 10) && (gameField[0][6] != 2)) {
                gameField[0][6] = 2;
                gameField[row][column] = 0;
            } else {
                movePiece(row, column);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please clear your starting area", "Invalid Action",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void logicBlack(int row, int column) {
        if ((gameField[10][5] == 3 && row == 10 && column == 5) || (gameField[10][5] == -3)) {
            if ((diceRoll == 16)
                    && (gameField[10][0] == 3 || gameField[10][1] == 3 || gameField[9][1] == 3 || gameField[9][0] == 3)
                    && (row == 1 || row == 0) && (column == 9 || column == 10) && (gameField[10][5] != 3)) {
                gameField[10][5] = 2;
                gameField[row][column] = 0;
            } else {
                movePiece(row, column);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please clear your starting area", "Invalid Action",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void rollThriceCheck() {
        if (yellowOnBoard == false && currentlyPlaying == 1) {
            roll3Times();
        }
        if (greenOnBoard == false && currentlyPlaying == 2) {
            roll3Times();
        }
        if (blackOnBoard == false && currentlyPlaying == 3) {
            roll3Times();
        }
        if (redOnBoard == false && currentlyPlaying == 4) {
            roll3Times();
        }
    }

    public void roll3Times() {
        if (carryOver < 3) {
            if (diceRoll == 16) {
                carryOver = 0;
            } else {
                carryOver++;
                cleanup();
            }
        } else {
            carryOver = 0;
            currentlyPlaying++;
            cleanup();
        }
    }

    public void cleanup() {
        gameField[5][5] = -8;
        if (currentlyPlaying == 5) {
            currentlyPlaying = 1;
        }
        if (gameField[10][4] == 0) {
            gameField[10][4] = -3;
        }
        if (gameField[0][6] == 0) {
            gameField[0][6] = -2;
        }
        if (gameField[4][10] == 0) {
            gameField[4][10] = -3;
        }
        if (gameField[4][0] == 0) {
            gameField[4][0] = -1;
        }
    }

    public void movePiece(int row, int column) {
        targetColumn = column;
        targetRow = row;
        for (int i = 10; i < diceRoll; i++) {
            if ((targetRow == 4 && targetColumn != 4 && targetColumn != 10)
                    || (targetRow == 0 && (targetColumn == 4 || targetColumn == 5))) {
                targetColumn++;
            } else if ((targetRow == 6 && targetColumn != 6 && targetColumn != 0)
                    || (targetRow == 10 && (targetColumn == 6 || targetColumn == 5))) {
                targetColumn--;
            } else if ((targetColumn == 4 && targetRow != 0 && targetRow != 6)
                    || (targetColumn == 0 && (targetRow == 6 || targetRow == 5))) {
                targetRow--;
            } else if ((targetColumn == 6 && targetRow != 4 && targetRow != 10)
                    || (targetColumn == 10 && (targetRow == 4 || targetRow == 5))) {
                targetRow++;
            }

        }
        if (gameField[targetRow][targetColumn] != currentlyPlaying) {
            if (gameField[targetRow][targetColumn] > 0) {
                returnCapturedPieces(targetRow, targetColumn);
            }
            gameField[targetRow][targetColumn] = 1;
            gameField[row][column] = 0;
            currentlyPlaying++;
        } else {
            JOptionPane.showMessageDialog(null, "Field already has your own Pawn", "Invalid Action",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void returnCapturedPieces(int row, int column) {
        int tempHoldingVar = gameField[row][column];
        switch (tempHoldingVar) {
            case 1:
                if (gameField[0][0] == 0) {
                    gameField[0][0] = 1;
                } else if (gameField[0][1] == 0) {
                    gameField[0][1] = 1;
                } else if (gameField[1][0] == 0) {
                    gameField[1][0] = 1;
                } else if (gameField[1][1] == 0) {
                    gameField[1][1] = 1;
                }
                break;
            case 2:
                if (gameField[0][9] == 0) {
                    gameField[0][9] = 2;
                } else if (gameField[0][10] == 0) {
                    gameField[0][10] = 2;
                } else if (gameField[1][9] == 0) {
                    gameField[1][9] = 2;
                } else if (gameField[1][10] == 0) {
                    gameField[1][10] = 2;
                }
                break;
            case 3:
                if (gameField[10][0] == 0) {
                    gameField[10][0] = 3;
                } else if (gameField[9][1] == 0) {
                    gameField[9][1] = 3;
                } else if (gameField[9][0] == 0) {
                    gameField[9][0] = 3;
                } else if (gameField[10][1] == 0) {
                    gameField[10][1] = 3;
                }
                break;
            case 4:
                if (gameField[10][10] == 0) {
                    gameField[10][10] = 4;
                } else if (gameField[10][9] == 0) {
                    gameField[10][9] = 4;
                } else if (gameField[9][9] == 0) {
                    gameField[9][9] = 4;
                } else if (gameField[9][10] == 0) {
                    gameField[9][10] = 4;
                }
                break;

            default:
                break;
        }
    }
}
