import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EquationPanel extends JPanel implements ActionListener {
    private int numberOfQuestions = 20;
    private int minimumNumber = 1;
    private int maximumNumber = 10;

    EquationGenerator equationGenerator = new EquationGenerator(1, 10);
    Equation currentEquation = equationGenerator.generateLinearEquation();

    private JLabel equationLabel;
    private JLabel answerLabel;

    private JButton generateButton;
    private JButton showAnswerButton;
    private JButton generateWorksheetButton;

    private JSpinner minNumberSpinner;
    private JSpinner maxNumberSpinner;

    public EquationPanel() {
        setLayout(new BorderLayout());

        createUI();
    }

    private void createUI() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1));

        equationLabel = new JLabel(currentEquation.toString());
        equationLabel.setFont(getFont().deriveFont(Font.ITALIC, 40));
        equationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textPanel.add(equationLabel);

        answerLabel = new JLabel(getCurrentEquationAnswerText());
        answerLabel.setFont(getFont().deriveFont(Font.PLAIN, 16));
        answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        answerLabel.setVerticalAlignment(SwingConstants.NORTH);
        answerLabel.setVisible(false);
        textPanel.add(answerLabel);

        add(textPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel currentQuestionPanel = new JPanel();
        currentQuestionPanel.setLayout(new GridLayout(2, 1));

        generateButton = createButton("Generate", "generateEquation");
        showAnswerButton = createButton("Show Answer", "showAnswer");

        currentQuestionPanel.add(generateButton);
        currentQuestionPanel.add(showAnswerButton);

        controlPanel.add(currentQuestionPanel, BorderLayout.WEST);

        JPanel worksheetPanel = new JPanel();
        worksheetPanel.setLayout(new GridLayout(2, 1));

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new GridLayout());

        minNumberSpinner = new JSpinner(new SpinnerNumberModel(minimumNumber, 1, 20, 1));
        minNumberSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                minimumNumber = (int) minNumberSpinner.getValue();
                equationGenerator = new EquationGenerator(minimumNumber, maximumNumber);
            }
        });
        minNumberSpinner.setToolTipText("Change minimum number.");
        spinnerPanel.add(minNumberSpinner);

        maxNumberSpinner = new JSpinner(new SpinnerNumberModel(maximumNumber, 1, 20, 1));
        maxNumberSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                maximumNumber = (int) maxNumberSpinner.getValue();
                equationGenerator = new EquationGenerator(minimumNumber, maximumNumber);
            }
        });
        maxNumberSpinner.setToolTipText("Change maximum number.");
        spinnerPanel.add(maxNumberSpinner);

        worksheetPanel.add(spinnerPanel);

        generateWorksheetButton = createButton("Generate Worksheet", "generateWorksheet");

        worksheetPanel.add(generateWorksheetButton);

        controlPanel.add(worksheetPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    private JButton createButton(String name, String cmd) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(cmd);
        return button;
    }

    private void generateWorksheet() {
        String saveFileDirectory = Tools.chooseDirectoryWindow();
        Equation[] equations = new Equation[numberOfQuestions];
        for (int i = 0; i < numberOfQuestions; i++) {
            equations[i] = equationGenerator.generateLinearEquation();
        }

        String questionsSheet = "";
        String answersSheet = "";
        for (int i = 0; i < equations.length; i++) {
            Equation equation = equations[i];
            questionsSheet += "Question " + (i+1) + ": " + equation.toString() + "\n\n";
            answersSheet += "Question " + (i+1) + ": " + equation.toString() + "\n";
            answersSheet += getEquationAnswerText(equation) + "\n\n";
        }

        try {
            Tools.saveToFile(questionsSheet, saveFileDirectory + "questions.txt");
            Tools.saveToFile(answersSheet, saveFileDirectory + "answers.txt");
            Tools.showPopup("Successfully saved worksheets to: " + saveFileDirectory);
        } catch(Exception e) {
            Tools.showPopup("Unable to save worksheet at: " + saveFileDirectory);
        }
    }

    private String getCurrentEquationAnswerText() {
        return getEquationAnswerText(currentEquation);
    }

    private String getEquationAnswerText(Equation equation) {
        String[] answers = equation.getSimplifiedAnswerStrings();
        String text = "Answer: ";
        if (answers.length > 1)
            text = "Allowed answers: ";

        text += answers[0];
        for (int i = 1; i < answers.length; i++) {
            text += ", " + answers[i];
        }
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("generateEquation")) {
            currentEquation = equationGenerator.generateLinearEquation();
            equationLabel.setText(currentEquation.toString());
            answerLabel.setText(getCurrentEquationAnswerText());
        }
        if (cmd.equals("showAnswer")) {
            if (answerLabel.isVisible())
                showAnswerButton.setText("Show Answer");
            else
                showAnswerButton.setText("Hide Answer");
            answerLabel.setVisible(!answerLabel.isVisible());
        }
        if (cmd.equals("generateWorksheet")) {
            generateWorksheet();
        }
    }
}