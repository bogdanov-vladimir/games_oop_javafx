package ru.job4j.puzzle;

import ru.job4j.puzzle.firuges.Cell;
import ru.job4j.puzzle.firuges.Figure;

import java.util.Arrays;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Logic {
    private final int size;
    private final Figure[] figures;
    private int index = 0;

    public Logic(int size) {
        this.size = size;
        this.figures = new Figure[size * size];
    }

    public void add(Figure figure) {
        this.figures[this.index++] = figure;
    }

    public boolean move(Cell source, Cell dest) {
        boolean rst = false;
        int index = this.findBy(source);
        if (index != -1) {
            Cell[] steps = this.figures[index].way(source, dest);
            if (this.isFree(steps)) {
                rst = true;
                this.figures[index] = this.figures[index].copy(dest);
            }
        }
        return rst;
    }

    public boolean isFree(Cell ... cells) {
        boolean result = cells.length > 0;
        for (Cell cell : cells) {
            if (this.findBy(cell) != -1) {
               result = false;
               break;
            }
        }
        return result;
    }

    public void clean() {
        for (int position = 0; position != this.figures.length; position++) {
            this.figures[position] = null;
        }
        this.index = 0;
    }

    private int findBy(Cell cell) {
        int rst = -1;
        for (int index = 0; index != this.figures.length; index++) {
            if (this.figures[index] != null && this.figures[index].position().equals(cell)) {
                rst = index;
                break;
            }
        }
        return rst;
    }

    public boolean isWin() {
        int[][] table = this.convert();
        boolean result = false;
        int iLen = this.size;
        /*int idxOneX = -1;
        int idxOneY = -1;
        int iRes = this.size;

        for (int iRow = 0; iRow < this.size; iRow++) {
            for(int iCol = 0; iCol < this.size; iCol++) {
                if(table[iRow][iCol] == 1) {

                    if(idxOneX == -1) {
                        idxOneX = iRow;
                    }

                    if(idxOneY == -1) {
                        idxOneY = iCol;
                    }

                    if(idxOneX == iRow) {
                        iRes--;
                    } else if(idxOneY == iCol) {
                        iRes--;
                    }
                }

            }
        }

        if(iRes == 0) {
            result = true;
        }

        for (int iCurRowIdx = 0; iCurRowIdx < iLen; iCurRowIdx++) {
            if(table[iCurRowIdx][iCurRowIdx] == 1) {
                for(int j = 0; j < iLen; j++) {
                    if(table[iCurRowIdx][0] == 1) {
                        if(table[iCurRowIdx][j] != 1) {
                            result = false;
                            break;
                        } else if(result == false) {
                            result = true;
                        }
                    } else if(table[0][iCurRowIdx] == 1) {
                        if(table[j][iCurRowIdx] != 1) {
                            result = false;
                            break;
                        } else if(result == false) {
                            result = true;
                        }
                    }
               }

                break;
            }
        }*/

        for (int iCurRowIdx = 0; iCurRowIdx < iLen && result == false; iCurRowIdx++) {
            int curVal = table[iCurRowIdx][iCurRowIdx];
            int idxX = 0;
            int idxY = 0;

            for (int j = 0; j < iLen && curVal == 1; j++) {
                idxX += table[iCurRowIdx][j];
                idxY += table[j] [iCurRowIdx];

                if (idxX == iLen || idxY == iLen) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

   /* private boolean checkToOne(int[][] table, int idxX, int idxY) {

        boolean result = false;
        if(idxX == -1) {


        } else {

        }

        for (int i = 0; i < table.length; i++) {
            if(table[i] != 1) {
                return false;
            }
        }

        return true;
    }*/

    public int[][] convert() {
        int[][] table = new int[this.size][this.size];
        for (int row = 0; row != table.length; row++) {
            for (int cell = 0; cell != table.length; cell++) {
                int position = this.findBy(new Cell(row, cell));
                if (position != -1 && this.figures[position].movable()) {
                    table[row][cell] = 1;
                }
            }
        }
        return table;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.convert());
    }
}
