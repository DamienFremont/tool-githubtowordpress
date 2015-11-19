package com.damienfremont.blog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class Job {

  enum Extension {
    java("java"), xml("xml"), html("html"), js("javascript"), css("css");

    private String language = "";

    Extension(String language) {
      this.language = language;
    }

    public String geLanguage() {
      return language;
    }

  }

  public static void main(String[] args) throws IOException {
    StringBuffer sbf = new StringBuffer();
    // GETT
    List<File> files = new ArrayList<>();
    listf(args[1], files);
    // FILTER BY EXCLUDE
    Stream<File> fileFiltered = files.stream() //
        .filter(file -> {
          String[] excludes = args[5].split(",");
          for (String exclude : excludes) {
            if (file.toString().contains(exclude))
              return false;
          }
          return true;
        }).filter(file -> {
          Extension[] excludes = Extension.values();
          for (Extension exclude : excludes) {
            if (file.toString().contains("." + exclude.toString())) {

              // WRITE
              String text;
              try {
                text = Files.toString(file, Charsets.UTF_8);
              } catch (IOException e) {
                throw Throwables.propagate(e);
              }
              String R = "\n";
              sbf.append(" " + R);
              sbf.append(file.getName() + R);
              sbf.append("[code language=" + exclude.language + "]" + R);
              sbf.append(text + R);
              sbf.append("[/code]" + R);
              return true;
            }
          }
          return false;
        });
    // GET
    List<File> files2 = fileFiltered.collect(Collectors.toList());
    System.out.println(sbf.toString());
    // WRITE
    File file = new File(args[3]);
    Files.write(sbf.toString(), file, Charsets.UTF_8);
  }

  public static void listf(String directoryName, List<File> files) {
    File directory = new File(directoryName);

    // get all the files from a directory
    File[] fList = directory.listFiles();
    for (File file : fList) {
      if (file.isFile()) {
        files.add(file);
      } else if (file.isDirectory()) {
        listf(file.getAbsolutePath(), files);
      }
    }
  }
}
