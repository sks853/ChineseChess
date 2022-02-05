package chess;

/**
 * 在该项目中，规定从左上角开始为(0,0)，水平向右方向为Y轴正方向，用列或Y表示，垂直向下方向为X轴正方向，用行或X表示
 *
 * @author THDMI
 * @version 0.0.1
 * @date 2022/01/28
 */
public class PieceRule extends RuleDefined {
    @Override
    public boolean ruleGeneral(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY, short flagPlayer) {
        byte[] tmpStepAbs = this.calStepAbs(posSrcX, posSrcY, posDstX, posDstY);
        if (1 != tmpStepAbs[0] + tmpStepAbs[1]) {
            return false;
        }
        return Const.FLAG_R == flagPlayer && this.isOverRange(posDstX, posDstY, Const.RANGE_SUDOKU[2], Const.RANGE_SUDOKU[0]) ||
                Const.FLAG_B == flagPlayer && this.isOverRange(posDstX, posDstY, Const.RANGE_SUDOKU[1], Const.RANGE_SUDOKU[0]);
    }

    @Override
    public boolean ruleLifeguard(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY, short flagPlayer) {
        byte[] tmpStepAbs = this.calStepAbs(posSrcX, posSrcY, posDstX, posDstY);
        if (tmpStepAbs[0] != tmpStepAbs[1]) {
            return false;
        }
        return Const.FLAG_R == flagPlayer && this.isOverRange(posDstX, posDstY, Const.RANGE_SUDOKU[2], Const.RANGE_SUDOKU[0]) ||
                Const.FLAG_B == flagPlayer && this.isOverRange(posDstX, posDstY, Const.RANGE_SUDOKU[1], Const.RANGE_SUDOKU[0]);
    }

    @Override
    public boolean rulePrimeMinister(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY, short flagPlayer) {
        byte[] tmpStepAbs = this.calStepAbs(posSrcX, posSrcY, posDstX, posDstY);
        if (Const.LIMIT_STEP_PRIME_MINISTER == tmpStepAbs[0] + tmpStepAbs[1] && tmpStepAbs[0] == tmpStepAbs[1]) {
            // 步长之和绝对值必须是4，且步长绝对值必须相等
            if (Const.BLOCK != chessboard[(posSrcX + posDstX) / (1 + 1)][(posSrcY + posDstY) / (1 + 1)]) {
                // 判断到目标点的中间点是否有棋子阻碍
                return false;
            }
            return Const.FLAG_R == flagPlayer && Const.RANGE_CAMP[0][1] < posDstX||
                    Const.FLAG_B == flagPlayer && posDstX < Const.RANGE_CAMP[1][0];
        }
        return false;
    }

    @Override
    public boolean ruleChariot(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY) {
        if (posDstX != posSrcX && posDstY == posSrcY) {
            // 判断是否为垂直移动
            if (posSrcX < posDstX) {
                return 0 == getHinderCount(posSrcX, posDstX, posSrcY, Const.MOVE_VERTICAL);
            } else {
                return 0 == getHinderCount(posDstX, posSrcX, posSrcY, Const.MOVE_VERTICAL);
            }
        } else if (posDstX == posSrcX && posDstY != posSrcY) {
            // 判断是否为水平移动
            if (posSrcY < posDstY) {
                return 0 == getHinderCount(posSrcY, posDstY, posSrcX, Const.MOVE_HORIZONTAL);
            } else {
                return 0 == getHinderCount(posDstY, posSrcY, posSrcX, Const.MOVE_HORIZONTAL);
            }
        }
        return false;
    }

    @Override
    public boolean ruleCavalry(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY) {
        byte[] tmpStep = this.calStep(posSrcX, posSrcY, posDstX, posDstY);
        byte[] tmpStepAbs = new byte[]{(byte) Math.abs(tmpStep[0]), (byte) Math.abs(tmpStep[1])};
        if (Const.LIMIT_STEP_CAVALRY != tmpStepAbs[0] + tmpStepAbs[1] || 0 == tmpStepAbs[0] || 0 == tmpStepAbs[1]) {
            return false;
        }
        // 判断棋子目标位置是水平运动还是垂直运动
        if (tmpStepAbs[0] < tmpStepAbs[1]) {
            // 由于calStep()函数是利用目标位置和源位置作差，故大于零的情况说明目标位置在右
            if (0 < tmpStep[1]) {
                // 判断棋子原位置右边是否为空
                return Const.BLOCK == chessboard[posSrcX][posSrcY + 1];
            } else {
                return Const.BLOCK == chessboard[posSrcX][posSrcY - 1];
            }
        } else if (tmpStepAbs[1] < tmpStepAbs[0]) {
            // 由于calStep()函数是利用目标位置和源位置作差，故大于零的情况说明目标位置在下方
            if (0 < tmpStep[0]) {
                return Const.BLOCK == chessboard[posSrcX + 1][posSrcY];
            } else {
                return Const.BLOCK == chessboard[posSrcX - 1][posSrcY];
            }
        }
        return false;
    }

    @Override
    public boolean ruleBattery(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY) {
        byte hinderCount = -1;
        if (posDstX != posSrcX && posDstY == posSrcY) {
            // 判断是否为垂直移动
            if (posSrcX < posDstX) {
                hinderCount = getHinderCount(posSrcX, posDstX, posSrcY, Const.MOVE_VERTICAL);
            } else {
                hinderCount = getHinderCount(posDstX, posSrcX, posSrcY, Const.MOVE_VERTICAL);
            }
        } else if (posDstX == posSrcX && posDstY != posSrcY) {
            // 判断是否为水平移动
            if (posSrcY < posDstY) {
                hinderCount = getHinderCount(posSrcY, posDstY, posSrcX, Const.MOVE_HORIZONTAL);
            } else {
                hinderCount = getHinderCount(posDstY, posSrcY, posSrcX, Const.MOVE_HORIZONTAL);
            }
        }
        return 1 == hinderCount && Const.BLOCK != chessboard[posDstX][posDstY] ||
                0 == hinderCount && Const.BLOCK == chessboard[posDstX][posDstY];
    }

    @Override
    public boolean ruleInfantry(byte posSrcX, byte posSrcY, byte posDstX, byte posDstY, short flagPlayer) {
        byte[] tmpStepAbs = this.calStepAbs(posSrcX, posSrcY, posDstX, posDstY);
        // 步数和必须等于1
        if (1 != tmpStepAbs[0] + tmpStepAbs[1]) {
            return false;
        }
        // 如果只是垂直移动是允许的，但是只许进不许退
        if (0 == tmpStepAbs[1]) {
            return Const.FLAG_R == flagPlayer && posDstX < posSrcX ||
                    Const.FLAG_B == flagPlayer && posSrcX < posDstX;
        }
        // 如果想要水平移动必须跨过楚河汉界
        return Const.FLAG_R == flagPlayer && posSrcX < Const.RANGE_CAMP[1][0] ||
                Const.FLAG_B == flagPlayer && Const.RANGE_CAMP[0][1] < posSrcX;
    }

}
