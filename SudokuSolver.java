package Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolver extends JFrame implements ActionListener {
    private JTextField[][] fields;
    private JButton solveButton;
    private JButton clearButton;
    private JPanel sudokuPanel, buttonPanel;
    private final int SIZE = 9;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(SIZE, SIZE));
        fields = new JTextField[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields[i][j] = new JTextField(1);
                fields[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                sudokuPanel.add(fields[i][j]);
            }
        }
        
        buttonPanel = new JPanel();

        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);
        
        clearButton=new JButton("Clear");
        clearButton.addActionListener(this);
        
         buttonPanel.add(solveButton);
         buttonPanel.add(clearButton);

        add(sudokuPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            int[][] sudoku = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    String text = fields[i][j].getText();
                    sudoku[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }

            if (solveSudoku(sudoku)) {
                updateFields(sudoku);
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists for this Sudoku.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==clearButton){
            for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields[i][j].setText("");
            }
        }
        }
    }

    private boolean solveSudoku(int[][] sudoku) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sudoku[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true; // No empty space left
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(sudoku, row, col, num)) {
                sudoku[row][col] = num;
                if (solveSudoku(sudoku)) {
                    return true;
                } else {
                    sudoku[row][col] = 0; // Backtrack
                }
            }
        }

        return false;
    }

    private boolean isSafe(int[][] sudoku, int row, int col, int num) {
        return !usedInRow(sudoku, row, num) &&
                !usedInColumn(sudoku, col, num) &&
                !usedInBox(sudoku, row - row % 3, col - col % 3, num);
    }

    private boolean usedInRow(int[][] sudoku, int row, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (sudoku[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInColumn(int[][] sudoku, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (sudoku[i][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int[][] sudoku, int boxStartRow, int boxStartCol, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sudoku[i + boxStartRow][j + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateFields(int[][] sudoku) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields[i][j].setText(String.valueOf(sudoku[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuSolver();
        });
    }
}
