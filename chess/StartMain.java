package chess;

import java.util.Scanner;

/**
 * @author THDMI
 * @version 0.0.1
 * @date 2022/01/28
 */
public class StartMain {
    public static void main(String[] args) {
        while (true) {
            PieceRule pieceRule = new PieceRule();
            Scanner sc = new Scanner(System.in);
            while (true) {
                Const.printPiece(pieceRule.chessboard, pieceRule.getFlagPlayer());
                if (null == pieceRule.getPosGeneral(pieceRule.getFlagPlayer())) {
                    System.err.println("游戏结束！是否重开？");
                    String choice = sc.next();
                    if ("Y".equals(choice) || "y".equals(choice) || "Yes".equals(choice) || "yes".equals(choice)) {
                        break;
                    }
                    System.exit(0);
                }
                System.out.format("当前阵营：%s方\t", Const.PIECE_FORMAT.getProperty(String.valueOf(pieceRule.getFlagPlayer())));
                System.out.print("请选择棋子行坐标X、列坐标Y、落子行坐标X、列坐标Y（空格间隔）：");
                byte posSrcX = sc.nextByte();
                byte posSrcY = sc.nextByte();
                byte posDstX = sc.nextByte();
                byte posDstY = sc.nextByte();
                if (posSrcX < 0 || posSrcY < 0 || posDstX < 0 || posDstY < 0) {
                    System.err.println("输入不合法，请重新输入！");
                    continue;
                }
                if (pieceRule.isPermitRuleMove(posSrcX, posSrcY, posDstX, posDstY, pieceRule.getFlagPlayer())) {
                    pieceRule.moveToPos(posSrcX, posSrcY, posDstX, posDstY);
                    System.out.println("移动成功！");
                    if (pieceRule.isCheckmate(posDstX, posDstY, pieceRule.getFlagPlayer())) {
                        System.err.println("将军！");
                    }
                    pieceRule.exchangeFlagPlayer();
                } else {
                    System.err.println("无法移动！");
                }
            }
        }
    }
}
