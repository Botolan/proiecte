package GUI;

import Simulation.SimulationManager;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Objects.Queue;
import Objects.Customer;
import Simulation.TimeCounter;

public class GUI extends JFrame {

    private JFrame frame;
    private static JTextArea log;

    public class Animation extends JPanel{
        private int nrQ;
        private List<Queue> queues;

        Animation() {
            setPreferredSize(new Dimension(frame.getSize().width, frame.getSize().height / 2));
        }

        public void setNrQ(int nrQ){
            this.nrQ = nrQ;
        }

        public void setQueueList(List<Queue> queues) {
            this.queues = queues;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int i = 0;i < nrQ;i++) {
                g.setColor(Color.yellow);
                g.fillRect((((900/nrQ)/2) + i * (900/nrQ)) - 20,300,40,15);
                g.drawString(""+(i + 1),(((900 / nrQ) / 2) + i * (900 / nrQ) - 5),330);
                for(int k = 0;k < queues.get(i).getQueueSize();k++){
                    Customer customer= queues.get(i).getQueue().get(k);
                    g.setColor(Color.red);
                    g.fillOval((((900/nrQ)/2) + i * (900/nrQ) - 20), 300 - (k + 1) * 20, 20, 20);
                    g.setColor(Color.white);
                    g.drawString("Timpul simularii = " + TimeCounter.getTimeElapsed(),400,360);
                    g.drawString(""+ customer.getWaitingTime(), (((900 / nrQ) / 2) + i * (900 / nrQ) - 15) + 20, 315 - (k + 1) * 20);
                    g.setColor(Color.black);
                    g.drawString(""+customer.getCustomerId(),(((900/nrQ)/2) + i * (900/nrQ) - 25) + 10,315 - (k + 1) * 20);
                }
            }
        }

    }

    private void createUI(){
        String[] strategy = {"Random", "Cel mai scurt timp de asteptare la coada", "Cea mai libera coada"};
        String[] speed = {"1x", "2x", "4x", "8x", "16x"};
        JComboBox<String> strategyList = new JComboBox<>(strategy);
        JComboBox<String> simulationSpeed = new JComboBox<>(speed);


        frame = new JFrame("Management cozi");
        frame.setMaximumSize(new Dimension(900,800));
        frame.setPreferredSize(new Dimension(900,800));
        frame.setMinimumSize(new Dimension(900,800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        JPanel mainPanel = new JPanel();

        JPanel input = new JPanel(new GridLayout(3,5));
        JPanel arrival = new JPanel(new FlowLayout());
        JPanel service = new JPanel(new FlowLayout());
        JPanel queue = new JPanel(new FlowLayout());
        JPanel time = new JPanel(new FlowLayout());
        JPanel strategySelection = new JPanel();
        JPanel speedSelection = new JPanel();
        JPanel start = new JPanel();
        JPanel logArea = new JPanel();


        Animation animationPanel = new Animation();

        JPanel labelParameterPanel1 = new JPanel();
        JPanel textParameterPanel1 = new JPanel(new FlowLayout());
        JPanel labelParameterPanel2 = new JPanel();
        JPanel textParameterPanel2 = new JPanel(new FlowLayout());
        JPanel labelParameterPanel3 = new JPanel();
        JPanel textParameterPanel3 = new JPanel(new FlowLayout());
        JPanel labelParameterPanel4 = new JPanel();
        JPanel textParameterPanel4 = new JPanel(new FlowLayout());


        JTextField inputMinArrivalTime = new JTextField("2");
        inputMinArrivalTime.setPreferredSize(new Dimension(20,20));
        JLabel minMaxArrivalTime = new JLabel("Min/Max Arrival Time");

        JTextField inputMaxArrivalTime = new JTextField("4");
        inputMaxArrivalTime.setPreferredSize(new Dimension(20,20));

        JTextField inputMinServiceTime = new JTextField("5");
        inputMinServiceTime.setPreferredSize(new Dimension(20,20));
        JLabel minMaxServiceTime = new JLabel("Min/Max Service Time");

        JTextField inputMaxServiceTime = new JTextField("6");
        inputMaxServiceTime.setPreferredSize(new Dimension(20,20));

        JTextField inputQueues = new JTextField("2");
        inputQueues.setPreferredSize(new Dimension(20,20));
        JLabel queueNumber = new JLabel("Queues");

        JTextField inputSimulationTime = new JTextField("30");
        inputSimulationTime.setPreferredSize(new Dimension(50,20));
        JLabel simulationTime = new JLabel("Simulation time");


        log = new JTextArea(10, 50);
        JScrollPane scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setEditable(false);

        logArea.add(scroll);


        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int minA = Integer.parseInt(inputMinArrivalTime.getText());
                int maxA = Integer.parseInt(inputMaxArrivalTime.getText());
                int minS = Integer.parseInt(inputMinServiceTime.getText());
                int maxS = Integer.parseInt(inputMaxServiceTime.getText());
                int nrQ = Integer.parseInt(inputQueues.getText());
                int simT = Integer.parseInt(inputSimulationTime.getText());
                Pattern number = Pattern.compile("(\\d*)");
                Matcher getSpeed = number.matcher(Objects.requireNonNull(simulationSpeed.getSelectedItem()).toString());
                getSpeed.find();
                double simS = 1.0 / Double.parseDouble(getSpeed.group(1));
                String strategy = Objects.requireNonNull(strategyList.getSelectedItem()).toString();
                //int minA = 2;
                //int maxA = 6;
                //int minS = 1;
                //int maxS = 1;
                //int nrQ = 1;
                //int simT = 20;
                try {
                    new SimulationManager(minA, maxA, minS, maxS, nrQ, simT, simS, animationPanel, strategy);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });


        labelParameterPanel1.add(minMaxArrivalTime);
        textParameterPanel1.add(inputMinArrivalTime);
        textParameterPanel1.add(inputMaxArrivalTime);
        labelParameterPanel2.add(minMaxServiceTime);
        textParameterPanel2.add(inputMinServiceTime);
        textParameterPanel2.add(inputMaxServiceTime);
        labelParameterPanel3.add(queueNumber);
        textParameterPanel3.add(inputQueues);
        labelParameterPanel4.add(simulationTime);
        textParameterPanel4.add(inputSimulationTime);
        start.add(startButton);

        arrival.add(labelParameterPanel1);
        arrival.add(textParameterPanel1);
        service.add(labelParameterPanel2);
        service.add(textParameterPanel2);
        queue.add(labelParameterPanel3);
        queue.add(textParameterPanel3);
        time.add(labelParameterPanel4);
        time.add(textParameterPanel4);
        strategySelection.add(new JLabel("Strategy"));
        strategySelection.add(strategyList);
        speedSelection.add(new JLabel("Speed"));
        speedSelection.add(simulationSpeed);

        input.add(arrival);
        input.add(service);
        input.add(queue);
        input.add(time);
        input.add(strategySelection);
        input.add(start);
        input.add(speedSelection);


        animationPanel.setBackground(new java.awt.Color(0, 0, 0));
        animationPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(input, BorderLayout.PAGE_END);




        frame.add(mainPanel);
        frame.add(animationPanel);
        frame.add(logArea);
        frame.setVisible(true);
    }

    public static void writeInLog(String message){
        log.append(message + "\n");
    }




    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().createUI();
            }
        });
    }
}
