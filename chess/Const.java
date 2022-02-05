package chess;

import java.util.Properties;

/**
 * 在该项目中，规定从左上角开始为(0,0)，水平向右方向为Y轴正方向，用列或Y表示，垂直向下方向为X轴正方向，用行或X表示
 *
 * @author THDMI
 * @version 0.0.1
 * @date 2022/01/28
 */
public class Const {

    /**
     * 单一阵营棋子类别总数
     */
    public final static short AMOUNT_PIECE_TYPE = 7;

    /**
     * 红方
     */
    public final static short FLAG_R = 0xFF;

    /**
     * 黑方
     */
    public final static short FLAG_B = 0xFE;

    /**
     * 水平移动
     */
    public final static boolean MOVE_HORIZONTAL = true;

    /**
     * 垂直移动
     */
    public final static boolean MOVE_VERTICAL = false;

    /**
     * 空位
     */
    public final static short BLOCK = 0x00;

    /**
     * 红方棋子标识：帅:0x01 仕:0x02 相:0x03 車: 0x04 馬:0x05 砲:0x06 兵:0x07
     */
    public final static short[] PIECE_R = new short[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

    /**
     * 黑方棋子标识：将:0x11 士:0x12 象:0x13 车: 0x14 马:0x15 炮:0x16 卒:0x17
     */
    public final static short[] PIECE_B = new short[]{0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};

    /**
     * 棋子文字：帅 仕 相 車 馬 砲 兵 将 士 象 车 马 炮 卒
     */
    public final static String[] PIECE_NAME = new String[]{"帅", "仕", "相", "車", "馬", "砲", "兵", "将", "士", "象", "车", "马", "炮", "卒"};

    /**
     * 棋子标识和棋名之间的对应关系属性
     */
    public final static Properties PIECE_FORMAT = initPieceCorresponding();

    /**
     * 垂直于楚河汉界的黑方阵营和红方阵营纵向起止坐标范围X
     */
    public final static byte[][] RANGE_CAMP = new byte[][]{{0, 4}, {5, 9}};

    /**
     * 平行于楚河汉界的棋盘宽度（横向）起止范围Y、垂直于楚河汉界的棋盘长度（纵向）起止坐标范围X
     */
    public final static byte[][] RANGE_CHESSBOARD = new byte[][]{{0, 8}, {0, 9}};

    /**
     * 红黑双方九宫的列坐标范围Y、黑方九宫的行坐标范围X、红方九宫的行坐标范围X
     */
    public final static byte[][] RANGE_SUDOKU = new byte[][]{{3, 5}, {0, 2}, {7, 9}};

    /**
     * 棋子马的位移距离之和限制
     */
    public final static byte LIMIT_STEP_CAVALRY = 3;

    /**
     * 棋子象的位移距离之和限制
     */
    public final static byte LIMIT_STEP_PRIME_MINISTER = 4;

    /**
     * 初始化棋子标识和棋子名字之间的对应关系属性
     *
     * @return properties 对应属性的键值对
     */
    protected static Properties initPieceCorresponding() {
        Properties properties = new Properties();
        for (int i = 0; i < AMOUNT_PIECE_TYPE; ++i) {
            properties.setProperty(String.valueOf(PIECE_R[i]), PIECE_NAME[i]);
        }
        for (int i = AMOUNT_PIECE_TYPE; i < PIECE_NAME.length; ++i) {
            properties.setProperty(String.valueOf(PIECE_B[i - AMOUNT_PIECE_TYPE]), PIECE_NAME[i]);
        }
        properties.setProperty(String.valueOf(BLOCK), "〇");
        properties.setProperty(String.valueOf(FLAG_R), "红");
        properties.setProperty(String.valueOf(FLAG_B), "黑");
        return properties;
    }

    /**
     * 控制台打印输出棋盘各棋子
     *
     * @param posPiece 二维数组形式的棋盘棋子位置
     */
    public static void printPiece(short[][] posPiece, short flagPlayer) {
        String[] numberId = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09"};
        if (FLAG_R == flagPlayer) {
            System.out.println("-- 零 一 二 三 四 五 六 七 八");
            for (int i = RANGE_CHESSBOARD[1][0]; i < RANGE_CHESSBOARD[1][1] + 1; ++i) {
                System.out.print(numberId[i] + " ");
                for (int j = RANGE_CHESSBOARD[0][0]; j < RANGE_CHESSBOARD[0][1] + 1; ++j) {
                    System.out.print(PIECE_FORMAT.getProperty(String.valueOf(posPiece[i][j])) + " ");
                }
                System.out.println();
            }
        } else if (FLAG_B == flagPlayer) {
            System.out.println("-- 八 七 六 五 四 三 二 一 零");
            for (int i = RANGE_CHESSBOARD[1][1]; i >= RANGE_CHESSBOARD[1][0]; --i) {
                System.out.print(numberId[i] + " ");
                for (int j = RANGE_CHESSBOARD[0][1]; j >= RANGE_CHESSBOARD[0][0]; --j) {
                    System.out.print(PIECE_FORMAT.getProperty(String.valueOf(posPiece[i][j])) + " ");
                }
                System.out.println();
            }
        }
    }
}
