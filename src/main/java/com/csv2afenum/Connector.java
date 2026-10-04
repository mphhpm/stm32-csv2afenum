package com.csv2afenum;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Connector {

    private final String      TAB = "    ";
    private String[]          alternate;
    private ArrayList<String> functions;
    private String            name;
    private String            namespace;
    
    public Connector(String[] alternate, ArrayList<String> functions, String namespace) {
        this.alternate = alternate;
        this.functions = functions;
        this.namespace = namespace;
        if (alternate.length > 0) {
            name = alternate[0];
        }
    }
    
    public void printFunctionEnum(Writer writer) {
        // enum class PB10 {
        //   TIM2_CH3  = Stm32::Enum::GPIOx::AFR::AF1,
        // };

        try {
            if (alternate.length-1 != functions.size()) {
                writer.append("###########################################################################\n"); 
                writer.append(String.format("#    %s size mismatch of alternate functions (AF) %d and functions %d\n", 
                        name, alternate.length, functions.size()));
                writer.append("###########################################################################\n"); 
                return;
            }
            writer.append(String.format("%senum class %s {\n", TAB, name));
            for (var func = 1; func < alternate.length; func++) {
                if (! alternate[func].equals("-")) {
                    for (var s : alternate[func].split("/")) {
                       writer.append(String.format("%s%s%s = %s%s,\n", TAB, TAB, s, namespace, functions.get(func-1)));
                    }
                }
            }
            writer.append(String.format("%s};\n\n", TAB, name));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String name() {
        return name;
    }
}
