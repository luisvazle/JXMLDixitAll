package JXMLDixitAll;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
public class JXMLDixitAll extends JFrame {
    private HashMap<String, String> data;
    private JTextField keyEntry, consultEntry;
    private JTextArea valueEntry, output;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    public JXMLDixitAll() {
        super("JXMLDixitAll");
        verifyDataFolder();
        loadData();

        setLayout(new FlowLayout());

        add(new JLabel("Term:"));
        keyEntry = new JTextField(20);
        add(keyEntry);

        add(new JLabel("Definition:"));
        valueEntry = new JTextArea(10, 50);
        valueEntry.setWrapStyleWord(true);
        valueEntry.setLineWrap(true);
        JScrollPane valueEntryScroll = new JScrollPane(valueEntry);
        add(valueEntryScroll);

        JButton storeButton = new JButton("Store");
        storeButton.addActionListener(e -> store());
        add(storeButton);

        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(e -> modify());
        add(modifyButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> delete());
        add(deleteButton);

        add(new JLabel("Term to consult:"));
        consultEntry = new JTextField(20);
        add(consultEntry);

        JButton consultButton = new JButton("Consult");
        consultButton.addActionListener(e -> consult());
        add(consultButton);

        add(new JLabel("Definition:"));
        output = new JTextArea(10, 50);
        output.setEditable(false);
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        JScrollPane outputScroll = new JScrollPane(output);
        add(outputScroll);

        add(new JLabel("Stored terms:"));
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(500, 180));
        add(listScroll);

        refreshList();
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void verifyDataFolder() {
        File dir = new File("src/JXMLDixitAll/data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void loadData() {
        File xmlFile = new File("src/JXMLDixitAll/data/data.xml");
        if (!xmlFile.exists()) {
            data = new HashMap<>();
            return;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            data = new HashMap<>();
            NodeList nList = doc.getElementsByTagName("Term");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String key = eElement.getElementsByTagName("Key").item(0).getTextContent();
                    String value = eElement.getElementsByTagName("Value").item(0).getTextContent();
                    data.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void store() {
        String key = keyEntry.getText();
        String value = valueEntry.getText();
        data.put(key, value);
        storeData();
        refreshList();
    }

    private void modify() {
        String key = keyEntry.getText();
        String value = valueEntry.getText();
        if (data.containsKey(key)) {
            data.put(key, value);
            storeData();
            refreshList();
        } else {
            output.setText("The term does not exist");
        }
    }

    private void delete() {
        String key = keyEntry.getText();
        if (data.containsKey(key)) {
            data.remove(key);
            storeData();
            refreshList();
        } else {
            output.setText("The term does not exist");
        }
    }

    private void consult() {
        String key = consultEntry.getText();
        if (data.containsKey(key)) {
            output.setText(data.get(key));
        } else {
            output.setText("The term does not exist");
        }
    }

    private void storeData() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("List");
            doc.appendChild(rootElement);

            for (Map.Entry<String, String> entry : data.entrySet()) {
                Element term = doc.createElement("Term");
                rootElement.appendChild(term);

                Element key = doc.createElement("Key");
                key.appendChild(doc.createTextNode(entry.getKey()));
                term.appendChild(key);

                Element value = doc.createElement("Value");
                value.appendChild(doc.createTextNode(entry.getValue()));
                term.appendChild(value);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/JXMLDixitAll/data/data.xml"));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshList() {
        listModel.clear();
        data.keySet().stream().sorted().forEach(listModel::addElement);
    }

    public static void main(String[] args) {
        new JXMLDixitAll();
    }
}