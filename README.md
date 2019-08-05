# scanlib-java

This repository is based on https://code.google.com/archive/p/scanlib-java/

Detection of third-party libraries in Java Projects

Introduction

Scanlib is an experimental prototype written in Java designed to scan a Java project and detect the third-party libraries it uses.

Background

The principle is straightforward. Given a dictionary of pairs "keyword - library", e.g. org.apache.commons.logging :: commons-logging org.junit. :: junit

Scanlib will attempt to match in any source code directory the set of libraries that are used.

We have build a database of regular expressions to detect the libraries, based on the packages name of their JAR files. The current index is available here.

Usage

Installation

To start using Scanlib, you have to include first the following in the pom.xml :

``` se Sphere repository http://se.labri.fr/maven
fr.labri.scanlib scanlib 0.1 ```

First usage

The basic usage of Scanlib stands in two simple lines :

String dir = "/path/to/mydirectory/"; Set<String> libs = ScanLib.getInstance().computeLibraries(dir);

First call to "ScanLib.getInstance()" will load the self-contained database.

Add/Remove Content

It is possible to dynamically add or remove entries of the database :

ScanLib.getInstance().remove("org.junit."); ScanLib.getInstance().add("org.testng.","testng");

Search Content

You can also search either a keyword or a library in the database :

Set<String> res = ScanLib.getInstance().searchKeyword("org.junit."); Set<String> res = ScanLib.getInstance().searchLibraries("testng");

Build your own Dictionary

If you prefer to provide your own database of keywords, you only have to write an XML file with the following format :

<scanlib> <lib id="JDK"> <kw>java.util.</kw> <kw>java.lang.</kw> </lib> </scanlib>

To then use it you code, just consider the following code :

ScanLib.buildFromFile("scanlib-dico.xml"); //As previously Set<String> libs = ScanLib.getInstance().computeLibraries(dir);

You can also add and remove entries in the database and make the new dictionary persistent :

ScanLib.buildFromFile("mydico.xml"); ScanLib.getInstance().add("org.testng.","testng"); ScanLib.getInstance().saveDB("mydico.xml");

You will obtain a new version of the XML File :

<scanlib> <lib id="JDK"> <kw>java.util.</kw> <kw>java.lang.</kw> </lib> <lib id="testng"> <kw>org.testng.</kw> </lib> </scanlib>

Notes

This utility tool is developed by Cédric Teyton, a Phd student at Software Engineering research group, LaBRI, Bordeaux, France.

Feel free to contact us, cteyton AT labri.fr, for any remark, suggestion or feedback.