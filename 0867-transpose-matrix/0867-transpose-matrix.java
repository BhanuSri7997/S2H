class Solution {
    public int[][] transpose(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] tMatrix = new int[n][m];

        for(int row=0;row<n;row++){
            for(int col=0;col<m;col++){
                tMatrix[row][col] = matrix[col][row];
            }
        }
        return tMatrix;
    }
}