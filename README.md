# JXMLDixitAll

## Summary

JXMLDixitAll is a Java application with a graphical user interface designed to manage a dictionary of terms and their definitions. It provides functionalities to add, modify, delete, and consult terms and their associated definitions. It features the capability to access data in XML file format, allowing easy import and export.

## Demo

[![JXMLDixitAll_video_demo](https://img.youtube.com/vi/8OkPpUZHCUE?si=xjQU5QVhrpFNrkWf/0.jpg)](https://youtu.be/8OkPpUZHCUE?si=xjQU5QVhrpFNrkWf)

## Installation

To use JXMLDixitAll, you need to have Java installed on your system. Then you can download the source code of this repo and compile it using any Java IDE or compile it directly from the command line.

1. Clone the repository:

   ```bash
   git clone https://github.com/luisvazle/JXMLDixitAll.git
   ```
2. Compile the source code:

   ```bash
   javac -d out src/JXMLDixitAll/JXMLDixitAll.java

   ```
This command compiles the Java source file and places the compiled class files in the `out` directory, preserving the package structure.

3. Run the application:

   ```bash
   java -cp out JXMLDixitAll.JXMLDixitAll

   ```

This command runs the main class of the application from the `out` directory.

## Usage

When you run the application, a graphical user interface will be displayed where you can perform actions described in the action buttons. Its functionality is highly intuitive.

The first time a term is entered, a folder with the file `data.xml` will be created. In any subsequent use, this file will be updated.

## Author

The author of this programme is Luís Vázquez Lema. You can contact me by sending an email to the following address: <luisvazquezlema@gmail.com>

## Licence

A copy of the MIT licence is included if you clone the repository: it is the `LICENSE` file.
