import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class kalkulatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String operator;
    private boolean startNewNumber;

    public kalkulatorGUI() {
        setTitle("Kalkulator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centruje okno na ekranie
        setLayout(new BorderLayout());

        currentInput = new StringBuilder();
        result = 0;
        operator = "";
        startNewNumber = true;

        // Tworzenie wyświetlacza
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        // Tworzenie panelu z przyciskami
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5)); // 4x4 siatka z odstępami

        String[] przyciski = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String tekst : przyciski) {
            JButton button = new JButton(tekst);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String komenda = e.getActionCommand();

        if (komenda.matches("[0-9]")) { // Jeśli naciśnięto cyfrę
            if (startNewNumber) {
                display.setText(komenda);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + komenda);
            }
            currentInput.append(komenda);
        } else if (komenda.equals(".")) { // Jeśli naciśnięto kropkę
            if (startNewNumber) {
                display.setText("0.");
                currentInput.append("0.");
                startNewNumber = false;
            } else if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
                currentInput.append(".");
            }
        } else if (komenda.matches("[+\\-*/]")) { // Jeśli naciśnięto operator
            calculate(Double.parseDouble(display.getText()));
            operator = komenda;
            startNewNumber = true;
        } else if (komenda.equals("=")) { // Jeśli naciśnięto równa się
            calculate(Double.parseDouble(display.getText()));
            operator = "";
            display.setText(String.valueOf(result));
            startNewNumber = true;
        }
    }

    private void calculate(double number) {
        switch (operator) {
            case "":
                result = number;
                break;
            case "+":
                result += number;
                break;
            case "-":
                result -= number;
                break;
            case "*":
                result *= number;
                break;
            case "/":
                if (number != 0) {
                    result /= number;
                } else {
                    JOptionPane.showMessageDialog(this, "Nie można dzielić przez zero!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    result = 0;
                }
                break;
        }
        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        // Ustawienie wyglądu systemowego (opcjonalne)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new kalkulatorGUI();
    }
}
