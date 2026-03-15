import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GuessNumberApp extends JFrame {

    private JTextField minField;
    private JTextField maxField;

    private JLabel guessLabel;
    private JLabel statusLabel;

    private JButton startButton;
    private JButton greaterButton;
    private JButton lessButton;
    private JButton equalButton;

    private JRadioButton metalRadio;
    private JRadioButton nimbusRadio;
    private JRadioButton systemRadio;

    private int low;
    private int high;
    private int guess;
    private boolean gameActive = false;

    public GuessNumberApp() {
        setTitle("Игра \"Больше-Меньше\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initLayout();
        initActions();

        setGameButtonsEnabled(false);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        minField = new JTextField(8);
        maxField = new JTextField(8);

        guessLabel = new JLabel("Сначала задайте диапазон и нажмите \"Начать игру\"");
        guessLabel.setFont(new Font("Arial", Font.BOLD, 16));
        guessLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        startButton = new JButton("Начать игру");
        greaterButton = new JButton("Больше");
        lessButton = new JButton("Меньше");
        equalButton = new JButton("Угадал");

        metalRadio = new JRadioButton("Metal");
        nimbusRadio = new JRadioButton("Nimbus");
        systemRadio = new JRadioButton("System");

        ButtonGroup lafGroup = new ButtonGroup();
        lafGroup.add(metalRadio);
        lafGroup.add(nimbusRadio);
        lafGroup.add(systemRadio);

        systemRadio.setSelected(true);
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel rangePanel = new JPanel(new GridBagLayout());
        rangePanel.setBorder(new TitledBorder("Настройка диапазона"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rangePanel.add(new JLabel("Минимум:"), gbc);

        gbc.gridx = 1;
        rangePanel.add(minField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        rangePanel.add(new JLabel("Максимум:"), gbc);

        gbc.gridx = 1;
        rangePanel.add(maxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rangePanel.add(startButton, gbc);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(new TitledBorder("Игра"));

        JPanel guessPanel = new JPanel(new BorderLayout());
        guessPanel.add(guessLabel, BorderLayout.CENTER);
        guessPanel.add(statusLabel, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(lessButton);
        buttonsPanel.add(equalButton);
        buttonsPanel.add(greaterButton);

        centerPanel.add(guessPanel, BorderLayout.CENTER);
        centerPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JPanel lafPanel = new JPanel();
        lafPanel.setLayout(new BoxLayout(lafPanel, BoxLayout.Y_AXIS));
        lafPanel.setBorder(new TitledBorder("Внешний вид (PLaF)"));
        lafPanel.add(metalRadio);
        lafPanel.add(Box.createVerticalStrut(5));
        lafPanel.add(nimbusRadio);
        lafPanel.add(Box.createVerticalStrut(5));
        lafPanel.add(systemRadio);

        mainPanel.add(rangePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(lafPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    private void initActions() {
        startButton.addActionListener(e -> startGame());

        greaterButton.addActionListener(e -> handleGreater());
        lessButton.addActionListener(e -> handleLess());
        equalButton.addActionListener(e -> handleEqual());

        metalRadio.addActionListener(e -> applyLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()));

        nimbusRadio.addActionListener(e -> applyNimbusLookAndFeel());

        systemRadio.addActionListener(e -> applyLookAndFeel(UIManager.getSystemLookAndFeelClassName()));
    }

    private void startGame() {
        try {
            int min = Integer.parseInt(minField.getText().trim());
            int max = Integer.parseInt(maxField.getText().trim());

            if (min > max) {
                JOptionPane.showMessageDialog(
                        this,
                        "Минимальное значение не может быть больше максимального.",
                        "Ошибка ввода",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            low = min;
            high = max;
            gameActive = true;

            setGameButtonsEnabled(true);
            statusLabel.setText("Загадайте число из диапазона и отвечайте на вопросы программы.");
            makeGuess();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Введите корректные целые числа для диапазона.",
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void makeGuess() {
        if (low > high) {
            finishGame(
                    "Вы жульничаете! Ваши ответы противоречат друг другу.",
                    "Жульничество"
            );
            return;
        }

        guess = low + (high - low) / 2;
        guessLabel.setText("Ваше число: " + guess + "?");
        statusLabel.setText("Нажмите: \"Больше\", \"Меньше\" или \"Угадал\".");
    }

    private void handleGreater() {
        if (!gameActive) {
            return;
        }

        low = guess + 1;
        makeGuess();
    }

    private void handleLess() {
        if (!gameActive) {
            return;
        }

        high = guess - 1;
        makeGuess();
    }

    private void handleEqual() {
        if (!gameActive) {
            return;
        }

        finishGame(
                "Программа угадала число: " + guess + "!",
                "Победа"
        );
    }

    private void finishGame(String message, String title) {
        gameActive = false;
        setGameButtonsEnabled(false);
        guessLabel.setText(message);
        statusLabel.setText(" ");

        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\nСыграть еще раз?",
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }

    private void resetGame() {
        gameActive = false;
        setGameButtonsEnabled(false);
        guessLabel.setText("Сначала задайте диапазон и нажмите \"Начать игру\"");
        statusLabel.setText(" ");
        minField.requestFocusInWindow();
    }

    private void setGameButtonsEnabled(boolean enabled) {
        greaterButton.setEnabled(enabled);
        lessButton.setEnabled(enabled);
        equalButton.setEnabled(enabled);
    }

    private void applyLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
            pack();
            setLocationRelativeTo(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Не удалось применить выбранный стиль оформления.",
                    "Ошибка PLaF",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void applyNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    applyLookAndFeel(info.getClassName());
                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Nimbus Look and Feel недоступен в данной системе.",
                    "PLaF",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Не удалось применить Nimbus.",
                    "Ошибка PLaF",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            GuessNumberApp app = new GuessNumberApp();
            app.setVisible(true);
        });
    }
}