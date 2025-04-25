// Tic Tac Toe is fun!
import java.awt.*;
import java.awt.event.*;
public class tictactoe extends Frame implements ActionListener { Button[][] buttons = new Button[3][3]; Label statusLabel = new Label("Player X's turn"); Button newGameButton = new Button("New Game"); char currentPlayer = 'X'; boolean gameOver = false;
Color lightPurple = new Color(216, 191, 216); 
Color lightGreen = new Color(144, 238, 144);

public tictactoe() {
    setTitle("Tic Tac Toe");
    setSize(400, 450);
    setLayout(new BorderLayout());

    Panel boardPanel = new Panel(new GridLayout(3, 3));

    Font font = new Font("Arial", Font.BOLD, 40);

    // Create 3x3 grid buttons
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            buttons[row][col] = new Button("");
            buttons[row][col].setFont(font);
            buttons[row][col].addActionListener(this);
            boardPanel.add(buttons[row][col]);
        }
    }

    // Bottom panel with status and new game button
    Panel bottomPanel = new Panel(new BorderLayout());
    newGameButton.addActionListener(e -> resetGame());
    bottomPanel.add(statusLabel, BorderLayout.CENTER);
    bottomPanel.add(newGameButton, BorderLayout.EAST);

    add(boardPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    setVisible(true);

    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            System.exit(0);
        }
    });
}

@Override
public void actionPerformed(ActionEvent e) {
    if (gameOver) return;

    Button clicked = (Button) e.getSource();
    if (!clicked.getLabel().equals("")) return;

    clicked.setLabel(String.valueOf(currentPlayer));
    if (currentPlayer == 'X') {
        clicked.setForeground(lightPurple);
    } else {
        clicked.setForeground(lightGreen);
    }

    if (checkWinner()) {
        String message = "Player " + currentPlayer + " wins!";
        statusLabel.setText(message);
        gameOver = true;
        showPopup(message);
    } else if (isDraw()) {
        String message = "It's a draw!";
        statusLabel.setText(message);
        gameOver = true;
        showPopup(message);
    } else {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }
}

private boolean checkWinner() {
    for (int i = 0; i < 3; i++) {
        // Check rows
        if (!buttons[i][0].getLabel().equals("") &&
            buttons[i][0].getLabel().equals(buttons[i][1].getLabel()) &&
            buttons[i][0].getLabel().equals(buttons[i][2].getLabel())) return true;

        // Check columns
        if (!buttons[0][i].getLabel().equals("") &&
            buttons[0][i].getLabel().equals(buttons[1][i].getLabel()) &&
            buttons[0][i].getLabel().equals(buttons[2][i].getLabel())) return true;
    }

    // Diagonals
    if (!buttons[0][0].getLabel().equals("") &&
        buttons[0][0].getLabel().equals(buttons[1][1].getLabel()) &&
        buttons[0][0].getLabel().equals(buttons[2][2].getLabel())) return true;

    if (!buttons[0][2].getLabel().equals("") &&
        buttons[0][2].getLabel().equals(buttons[1][1].getLabel()) &&
        buttons[0][2].getLabel().equals(buttons[2][0].getLabel())) return true;

    return false;
}

private boolean isDraw() {
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            if (buttons[row][col].getLabel().equals("")) {
                return false;
            }
        }
    }
    return true;
}

private void resetGame() {
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            buttons[row][col].setLabel("");
            buttons[row][col].setForeground(Color.BLACK);
        }
    }
    currentPlayer = 'X';
    statusLabel.setText("Player X's turn");
    gameOver = false;
}

private void showPopup(String message) {
    Dialog dialog = new Dialog(this, "Game Over", true);
    dialog.setLayout(new BorderLayout());
    Label msgLabel = new Label(message, Label.CENTER);
    msgLabel.setFont(new Font("Arial", Font.BOLD, 20));
    dialog.add(msgLabel, BorderLayout.CENTER);

    Button okButton = new Button("OK");
    okButton.addActionListener(e -> dialog.setVisible(false));
    Panel buttonPanel = new Panel();
    buttonPanel.add(okButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

public static void main(String[] args) {
    new tictactoe();
}

}
