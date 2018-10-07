import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class GUI {

    private static final String INPUT_FILE= "Activities.txt";
    private static final String OUTPUT_T1= "ActivitiesT1.txt";
    private static final String OUTPUT_T2= "ActivitiesT2.txt";
    private static final String OUTPUT_T3= "ActivitiesT3.txt";
    private static final String OUTPUT_T4= "ActivitiesT4.txt";
    private static final String OUTPUT_T5= "ActivitiesT5.txt";
    private static final String[] activities = {"Leaving", "Toileting", "Showering", "Sleeping", "Breakfast", "Lunch", "Dinner", "Snack", "Spare_Time/TV", "Grooming"};

    private List<MonitoredData> data = new LinkedList<>();

    private void createGUI() throws IOException, ParseException {
        readData();


        JFrame frame = new JFrame("Tema 5");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,100);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JButton button1 = new JButton("Task1");
        JButton button2 = new JButton("Task2");
        JButton button3 = new JButton("Task3");
        JButton button4 = new JButton("Task4");
        JButton button5 = new JButton("Task5");

        JPanel panel = new JPanel();

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);

        frame.add(panel);

        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeDataT1(T1());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeDataT2(T2());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeDataT3(T3());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeDataT4(T4());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeDataT5(T5());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }



    private void readData() {
        try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
            data = stream.map(line -> line.split("\t\t")).map(data-> new MonitoredData(data[0], data[1], data[2]))
                    .collect(Collectors.toCollection(LinkedList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long T1() {
        return data.stream().map(p -> p.getStartTime().substring(0, 10)).distinct().count();
    }

    private void writeDataT1(Long a) throws IOException {
        FileWriter fw = new FileWriter(OUTPUT_T1);
        try {
            fw.write(a.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fw.close();

    }

    private Map<String, Integer> T2(){
        return data.stream().collect(toMap(p->p.getActivity(), p->1, (a, b)->a + 1));
    }

    private void writeDataT2(Map<String, Integer> map) throws IOException {
        Path path = Paths.get(OUTPUT_T2);
        Files.write(path, ()->map.entrySet().stream().<CharSequence>map(p->p.getKey() + "\t" + p.getValue()).iterator());
    }

    private Map<String, Map<String, Integer>> T3() {
        return data.stream().collect(Collectors.toMap(p->p.getStartTime().substring(0, 10), //luam data
               p->data.stream().filter(q->q.getStartTime().substring(0,10).equals(p.getStartTime().substring(0, 10)))//filtram dupa data
                       .collect(toMap(q->q.getActivity(), q->1, (a, b)->a + 1)),  (a, b) -> a));//numaram activitatiile
    }

    private void writeDataT3(Map<String, Map<String, Integer>>map) throws IOException {
        Path path = Paths.get(OUTPUT_T3);
        Files.write(path, ()->map.entrySet().stream().<CharSequence>map(p->p.getKey() + "\t" + p.getValue()).iterator());
    }

    private Map<String, Long> T4() {
        DateFormat format = new SimpleDateFormat("");
        Map<String, Long> map = data.stream().collect(Collectors.groupingBy(p -> p.getActivity(),
                        Collectors.summingLong(p -> {
                            try {
                                return format.parse(p.getEndTime()).getTime() - format.parse(p.getStartTime()).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        })));
        return map.entrySet().stream().filter(p->p.getValue() > 10 * 3600 * 1000).collect(Collectors.toMap(p->p.getKey(), p->p.getValue() / 3600 / 1000));
    }


    private void writeDataT4(Map<String, Long> map) throws IOException {
        Path path = Paths.get(OUTPUT_T4);
        Files.write(path, ()->map.entrySet().stream().<CharSequence>map(p->p.getKey() + "\t" + p.getValue()).iterator());
    }

    private List<String> T5(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Integer> occurance = T2();
        Map<String, Integer> map = data.stream().filter(p->{
            try {
                return (format.parse(p.getEndTime()).getTime() - format.parse(p.getStartTime()).getTime()) < 5 * 60 * 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toMap(p->p.getActivity(), p->1, (oldValue, newValue)->oldValue + 1));
        return data.stream().filter(p->{
            if(map.containsKey(p.getActivity())) {
                return map.get(p.getActivity()) >= 0.9 * occurance.get(p.getActivity());
            }
            return false;
        }).map(p->p.getActivity()).distinct().collect(Collectors.toList());
    }


    private void writeDataT5(List<String> list) throws IOException {
        FileWriter fw = new FileWriter(OUTPUT_T5);
        list.forEach(p-> {
            try {
                fw.write(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }




    public static void main(String[] args) throws IOException, ParseException {
        GUI gui = new GUI();
        gui.createGUI();
    }




}
