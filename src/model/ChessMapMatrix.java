package model;

import view.login.UserFileManager;

import java.util.Objects;
import java.util.Random;

/**
 * 棋盤專用矩陣，值說明：
 * - 0: 普通格子
 * - 1: 特殊格子（+2步）
 * - 2: 新增特殊格子（例如-1步）
 */
public class ChessMapMatrix {
    private int[][] matrix;
    private static final Random random = new Random();

    public ChessMapMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSpecialEffect(int position) {
        int row = (position - 1) / 10;
        int col = (position - 1) % 10;
        return matrix[row][col];
    }

    // 生成默認棋盤
    public static ChessMapMatrix createDefaultBoard() {
        int[][] matrix = new int[10][10];
        matrix[0][6] = 1; // 第7格（0行6列）
        matrix[4][9] = 1;
        matrix[1][4] = 2; // 新類型格子
        matrix[2][3] = 2;
        matrix[7][5] = 2;
        matrix[8][8] = 2;
        return new ChessMapMatrix(matrix);
    }

    //新建随机棋盘
    public static ChessMapMatrix createRandomBoard(){
        int[][] matrix = new int[10][10];

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(i*j==0||i*j==81){
                    continue;
                }
                if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "1")){
                    if(random.nextInt(5)==0){
                        matrix[i][j] = random.nextInt(2)+1;
                    }
                }
                if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "2")){
                    if(random.nextInt(5)==0){
                        matrix[i][j] = random.nextInt(20)-10;
                    }
                }
            }
        }
        return new ChessMapMatrix(matrix);
    }

    public void resetMatrix(){
        int[][] matrix = new int[10][10];

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(i*j==0||i*j==81){
                    continue;
                }
                if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "1")){
                    if(random.nextInt(5)==0){
                        matrix[i][j] = random.nextInt(2)+1;
                    }
                }
                if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "2")){
                    if(random.nextInt(5)==0){
                        matrix[i][j] = random.nextInt(20)-10;
                    }
                }
            }
        }

        this.matrix = matrix;
    }

    // 顯示棋盤（用於調試）
    public void printMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}