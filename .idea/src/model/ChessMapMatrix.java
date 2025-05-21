package model;

/**
 * 扩展为棋盘专用矩阵，值说明：
 * - 0: 普通格子
 * - 1: 特殊格子（+2步）
 */
public class ChessMapMatrix {
    private final int[][] matrix;

    public ChessMapMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSpecialEffect(int position) {
        int row = (position - 1) / 10;
        int col = (position - 1) % 10;
        return matrix[row][col];
    }

    // 生成默认棋盘
    public static ChessMapMatrix createDefaultBoard() {
        int[][] matrix = new int[10][10];
        matrix[0][6] = 1; // 第7格（0行6列）
        matrix[4][9] = 1;
        //matrix[1][4] = 2; // 新类型格子
        //todo: add special cell here
        return new ChessMapMatrix(matrix);
    }
}
