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
    boolean isMovePossible = false;
    Boolean isValidMove = true;
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

    public int getCurrentPlayer() {
        return currentlyPlaying;
    }

    public int[][] getArray() {
        return (gameField);
    }

    private void fullStartingArea() {
        JOptionPane.showMessageDialog(guiframe.frame, "Please clear your starting area", "Invalid Action",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void rollDice() {
        while (rollNotComplete) {
            diceRoll = (int) (Math.random() * 10);
            if (diceRoll > 0 && diceRoll < 7) {
                rollNotComplete = false;
            }
        }
        rollNotComplete = true;
        System.out.println("Dice Roll: " + diceRoll);
        diceRoll = diceRoll + 10;
        gameField[5][5] = diceRoll;
    }

    // TODO Improvements
    ///// Multi class split ??

    public void logicSelector(int btnname) {

        int row = btnname % 100;
        int column = btnname / 100;
        int piece = gameField[row][column];
        if (piece > 0 && piece != currentlyPlaying) {
            JOptionPane.showMessageDialog(guiframe.frame, "Its not your Turn", "Invalid Action",
                    JOptionPane.WARNING_MESSAGE);

        } else {
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
                    isMovePossible();
                    if (isMovePossible == false) {

                        diceRoll = diceRoll - 10;
                        String message = "No Valid Move For Dice Roll " + Integer.toString(diceRoll);
                        JOptionPane.showMessageDialog(
                                guiframe.frame, message, "No Valid Move",
                                JOptionPane.INFORMATION_MESSAGE);
                        cleanup();
                    }
                    break;
                default:
                    break;
            }
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
            fullStartingArea();
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
            fullStartingArea();
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
            fullStartingArea();
        }
    }

    public void logicBlack(int row, int column) {
        if ((gameField[10][4] == 3 && row == 10 && column == 4) || (gameField[10][4] == -3)) {
            if ((diceRoll == 16)
                    && (gameField[10][0] == 3 || gameField[10][1] == 3 || gameField[9][1] == 3 || gameField[9][0] == 3)
                    && (row == 10 || row == 9) && (column == 0 || column == 1) && (gameField[10][4] != 3)) {
                gameField[10][4] = 3;
                gameField[row][column] = 0;
            } else {
                movePiece(row, column);
            }
        } else {
            fullStartingArea();
        }
    }

    public void rollThriceCheck() {
        if (yellowOnBoard == false && currentlyPlaying == 1) {
            roll3Times();
        } else if (greenOnBoard == false && currentlyPlaying == 2) {
            roll3Times();
        } else if (blackOnBoard == false && currentlyPlaying == 3) {
            roll3Times();
        } else if (redOnBoard == false && currentlyPlaying == 4) {
            roll3Times();
        }
    }

    public void roll3Times() {
        if (carryOver < 2) {
            if (diceRoll == 16) {
                carryOver = 0;
            } else {
                carryOver++;

            }
        } else {
            carryOver = 0;
            currentlyPlaying++;

        }
    }

    public void cleanup() {
        gameField[5][5] = -8;
        diceRoll = 0;
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
        if (diceRoll > 0) {
            for (int i = 10; i < diceRoll; i++) {

                if ((targetRow == 4 && targetColumn != 4 && targetColumn != 10)
                        || (targetRow == 0 && (targetColumn == 4 || targetColumn == 5))
                        || (targetRow == 5 && targetColumn < 5 && currentlyPlaying == 1)) {
                    targetColumn++;
                } else if ((targetRow == 6 && targetColumn != 6 && targetColumn != 0)
                        || (targetRow == 10 && (targetColumn == 6 || targetColumn == 5))
                        || (targetRow == 5 && targetColumn > 5 && currentlyPlaying == 4)) {
                    targetColumn--;
                } else if ((targetColumn == 4 && targetRow != 0 && targetRow != 6)
                        || (targetColumn == 0 && (targetRow == 6 || targetRow == 5))
                        || (targetRow == 5 && targetColumn > 5 && currentlyPlaying == 3)) {
                    targetRow--;
                } else if ((targetColumn == 6 && targetRow != 4 && targetRow != 10)
                        || (targetColumn == 10 && (targetRow == 4 || targetRow == 5))
                        || (targetColumn == 5 && targetRow < 5 && currentlyPlaying == 4)) {
                    targetRow++;
                }
                if (targetColumn == 5 && targetRow == 5) {
                    JOptionPane.showMessageDialog(guiframe.frame, "Impossible Move", "Invalid Action",
                            JOptionPane.ERROR_MESSAGE);
                    isValidMove = false;
                    break;
                }

            }
            if (gameField[targetRow][targetColumn] != currentlyPlaying && isValidMove == true) {
                if (gameField[targetRow][targetColumn] > 0) {
                    returnCapturedPieces(targetRow, targetColumn);
                }
                gameField[targetRow][targetColumn] = currentlyPlaying;
                gameField[row][column] = 0;
                currentlyPlaying++;
            } else if (isValidMove == true) {
                JOptionPane.showMessageDialog(guiframe.frame, "Field already has your own Pawn", "Invalid Action",
                        JOptionPane.ERROR_MESSAGE);

            }
            isValidMove = true;
        } else {
            JOptionPane.showMessageDialog(guiframe.frame, "Please Roll the dice first");
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

    public void checkWin() {
        if (gameField[5][1] == 1 && gameField[5][2] == 1 && gameField[5][3] == 1 && gameField[5][4] == 1) {
            JOptionPane.showMessageDialog(guiframe.frame, "The Winner is player 1 (Yellow) ", "Game Win",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (gameField[1][5] == 2 && gameField[2][5] == 2 && gameField[3][5] == 2 && gameField[4][5] == 2) {
            JOptionPane.showMessageDialog(guiframe.frame, "The Winner is player 2 (Green)", "Game Win",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (gameField[5][9] == 4 && gameField[5][8] == 4 && gameField[5][7] == 4 && gameField[5][6] == 4) {
            JOptionPane.showMessageDialog(guiframe.frame, "The Winner is player 4 (Red)", "Game Win",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (gameField[9][5] == 3 && gameField[8][5] == 3 && gameField[7][5] == 3 && gameField[6][5] == 3) {
            JOptionPane.showMessageDialog(guiframe.frame, "The Winner is player 3 (Black)", "Game Win",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void isMovePossible() {
        isMovePossible = false;
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if (gameField[row][column] == currentlyPlaying) {
                    targetColumn = column;
                    targetRow = row;
                    if (diceRoll > 0) {
                        for (int i = 10; i < diceRoll; i++) {

                            if ((targetRow == 4 && targetColumn != 4 && targetColumn != 10)
                                    || (targetRow == 0 && (targetColumn == 4 || targetColumn == 5))
                                    || (targetRow == 5 && targetColumn < 5 && currentlyPlaying == 1)) {
                                targetColumn++;
                            } else if ((targetRow == 6 && targetColumn != 6 && targetColumn != 0)
                                    || (targetRow == 10 && (targetColumn == 6 || targetColumn == 5))
                                    || (targetRow == 5 && targetColumn > 5 && currentlyPlaying == 4)) {
                                targetColumn--;
                            } else if ((targetColumn == 4 && targetRow != 0 && targetRow != 6)
                                    || (targetColumn == 0 && (targetRow == 6 || targetRow == 5))
                                    || (targetRow == 5 && targetColumn > 5 && currentlyPlaying == 3)) {
                                targetRow--;
                            } else if ((targetColumn == 6 && targetRow != 4 && targetRow != 10)
                                    || (targetColumn == 10 && (targetRow == 4 || targetRow == 5))
                                    || (targetColumn == 5 && targetRow < 5 && currentlyPlaying == 4)) {
                                targetRow++;
                            }
                            if (targetColumn == 5 && targetRow == 5) {
                                isValidMove = false;
                                break;
                            }

                        }
                        if (gameField[targetRow][targetColumn] != currentlyPlaying && isValidMove == true) {
                            isMovePossible = true;
                        }
                        if ((diceRoll == 16)
                                && (gameField[0][0] == 1 || gameField[0][1] == 1 || gameField[1][0] == 1
                                        || gameField[1][1] == 1)
                                && (currentlyPlaying == 1) && (gameField[4][0] != 1)) {
                            isMovePossible = true;
                        }
                        if ((diceRoll == 16)
                                && (gameField[0][10] == 2 || gameField[0][9] == 2 || gameField[1][10] == 2
                                        || gameField[1][9] == 2)
                                && (currentlyPlaying == 2) && (gameField[0][6] != 2)) {
                            isMovePossible = true;
                        }

                        if ((diceRoll == 16)
                                && (gameField[10][0] == 3 || gameField[10][1] == 3 || gameField[9][1] == 3
                                        || gameField[9][0] == 3)
                                && (currentlyPlaying == 3) && (gameField[10][4] != 3)) {
                            isMovePossible = true;
                        }
                        if ((diceRoll == 16)
                                && (gameField[10][10] == 4 || gameField[10][9] == 4 || gameField[9][10] == 4
                                        || gameField[9][9] == 4)
                                && (currentlyPlaying == 4) && (gameField[6][10] != 4)) {
                            isMovePossible = true;
                        }
                        isValidMove = true;
                    }
                }
            }
        }
    }
}
