/*

MIT License

Copyright (c) 2024 Luí­s Vázquez Lema

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 

*/

package jxmlDixitAll;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 *
 * @author Luís Vázquez Lema
 */

public class jxmlDixitAll extends JFrame {
    private HashMap<String, String> datos;
    private JTextField claveEntry, consultarEntry;
    private JTextArea valorEntry, salida;
    private JList<String> lista;
    private DefaultListModel<String> listModel;

    public jxmlDixitAll() {
        super("jxmlDixitAll");
        verificarCarpetaDatos();
        cargarDatos();

        setLayout(new FlowLayout());

        add(new JLabel("Término:"));
        claveEntry = new JTextField(20);
        add(claveEntry);

        add(new JLabel("Definición:"));
        valorEntry = new JTextArea(10, 50);
        valorEntry.setWrapStyleWord(true);
        valorEntry.setLineWrap(true);
        JScrollPane valorScroll = new JScrollPane(valorEntry);
        add(valorScroll);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> guardar());
        add(guardarButton);

        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(e -> modificar());
        add(modificarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> eliminar());
        add(eliminarButton);

        add(new JLabel("Término a consultar:"));
        consultarEntry = new JTextField(20);
        add(consultarEntry);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(e -> consultar());
        add(consultarButton);

        add(new JLabel("Definición:"));
        salida = new JTextArea(10, 50);
        salida.setEditable(false);
        salida.setWrapStyleWord(true);
        salida.setLineWrap(true);
        JScrollPane salidaScroll = new JScrollPane(salida);
        add(salidaScroll);

        add(new JLabel("Términos almacenados:"));
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        JScrollPane listaScroll = new JScrollPane(lista);
        listaScroll.setPreferredSize(new Dimension(500, 180));
        add(listaScroll);

        actualizarLista();
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void verificarCarpetaDatos() {
        File dir = new File("src/jxmlDixitAll/datos");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void cargarDatos() {
        File xmlFile = new File("src/jxmlDixitAll/datos/datos.xml");
        if (!xmlFile.exists()) {
            datos = new HashMap<>();
            return;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            datos = new HashMap<>();
            NodeList nList = doc.getElementsByTagName("Término");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String clave = eElement.getElementsByTagName("Clave").item(0).getTextContent();
                    String valor = eElement.getElementsByTagName("Valor").item(0).getTextContent();
                    datos.put(clave, valor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardar() {
        String clave = claveEntry.getText();
        String valor = valorEntry.getText();
        datos.put(clave, valor);
        guardarDatos();
        actualizarLista();
    }

    private void modificar() {
        String clave = claveEntry.getText();
        String valor = valorEntry.getText();
        if (datos.containsKey(clave)) {
            datos.put(clave, valor);
            guardarDatos();
            actualizarLista();
        } else {
            salida.setText("El término no existe");
        }
    }

    private void eliminar() {
        String clave = claveEntry.getText();
        if (datos.containsKey(clave)) {
            datos.remove(clave);
            guardarDatos();
            actualizarLista();
        } else {
            salida.setText("El término no existe");
        }
    }

    private void consultar() {
        String clave = consultarEntry.getText();
        if (datos.containsKey(clave)) {
            salida.setText(datos.get(clave));
        } else {
            salida.setText("El término no existe");
        }
    }

    private void guardarDatos() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Lista");
            doc.appendChild(rootElement);

            for (Map.Entry<String, String> entry : datos.entrySet()) {
                Element termino = doc.createElement("Término");
                rootElement.appendChild(termino);

                Element clave = doc.createElement("Clave");
                clave.appendChild(doc.createTextNode(entry.getKey()));
                termino.appendChild(clave);

                Element valor = doc.createElement("Valor");
                valor.appendChild(doc.createTextNode(entry.getValue()));
                termino.appendChild(valor);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/jxmlDixitAll/datos/datos.xml"));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarLista() {
        listModel.clear();
        datos.keySet().stream().sorted().forEach(listModel::addElement);
    }

    public static void main(String[] args) {
        new jxmlDixitAll();
    }
}
