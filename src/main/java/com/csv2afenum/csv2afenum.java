package com.csv2afenum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class csv2afenum implements Runnable {

    @Option(names = {"-f", "--File"}, description = "Path to svd file", required = true)
    private String filename;

    @Option(names = {"-n", "--Namespace"}, description = "Namespace prefix e.g. Stm32::Enum::GPIOx::AFR::")
    private String namespace = "Stm32::Enum::GPIOx::AFR::";

	@Override
	public void run() {
        try {
        	//
        	// read svd file
            String userDirectory = Paths.get("")
                    .toAbsolutePath()
                    .toString();
            var csvFile = new File(filename);
            var records = new ArrayList<>();
            var functions = new ArrayList<String>();
            var connectors = new ArrayList<Connector>();
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length > 0) {
                        records.add(Arrays.asList(values));
                        if (values[0].isEmpty()) {
                            var m = Pattern.compile("AF(1[0-5]{1})|AF([0-9])").matcher(line); 
                            while (m.find()) {
                                functions.add(m.group(0));
                            }
                        }
                        else {
                            if (values.length > 0) {
                                var connector = new Connector(values, functions, namespace);
                                connectors.add(connector);
                            }
                        }
                    }
                }
                var bwriter = new BufferedWriter(new OutputStreamWriter(System.out));
                for (var connector : connectors) {
                    connector.printFunctionEnum(bwriter);
                    if (connector.name().equals("PH15")) {
                        var n = connector.name();
                    }
                    bwriter.flush();
                }
            }            
        } catch (Exception e) {
            System.err.println("Fehler beim Lesen der csv-Datei: " + filename + e.getMessage());
            e.printStackTrace();
        }
	}
	

    public static void main(String[] args) {
        int exitCode = new CommandLine(new csv2afenum()).execute(args);
        System.exit(exitCode);
    }
}