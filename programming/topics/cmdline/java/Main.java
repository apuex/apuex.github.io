package com.github.apuex.jee.saaj;

import org.apache.commons.cli.*;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String args[]) throws Exception {
        final Options options = options();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("soap-client <options>", options);
            printOptions(defaultOptions());
        } else {
            final Map<String, String> params = defaultOptions();

            options.getOptions().stream()
                    .forEach(o -> {
                        if (cmd.hasOption(o.getOpt())) {
                            params.put(o.getLongOpt(), cmd.getOptionValue(o.getOpt()));
                        }
                    });

            System.out.println(invoke(params));
        }
    }

    public static Map<String, String> defaultOptions() {
        return new TreeMap<String, String>() {{
            put("endpoint-url", "http://my-service.example.com");
            put("namespace-uri", "http://my-service.example.com");
            put("method", "invoke");
            put("output", "invokeResponse");
            put("return", "invokeReturn");
            put("soap-version", "1.1");
            put("parameter-name", "xmlData");
            put("request-xml-file", "GetInfoRequest.xml");
        }};
    }

    public static void printOptions(Map<String, String> options) {
        System.out.println("current options are:");
        int maxLength = options.entrySet().stream()
                .map(x -> x.getKey().length())
                .max(Integer::compare)
                .get() + 1;

        options.entrySet().forEach(e -> System.out.printf("  %s = %s\n", paddingRight(e.getKey(), maxLength), e.getValue()));
    }

    public static String paddingRight(String s, int maxWidth) {
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        if (length < maxWidth) {
            for (int i = length; i < maxWidth; ++i) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static Options options() {
        final Options options = new Options();
        options.addOption(new Option("e", "endpoint-url", true, "SOAP service endpoint URL."));
        options.addOption(new Option("n", "namespace-uri", true, "SOAP service namespace URI."));
        options.addOption(new Option("m", "method", true, "method to be invoked."));
        options.addOption(new Option("p", "parameter-name", true, "method parameter."));
        options.addOption(new Option("s", "soap-version", true, "soap specification version, valid options are 1.1, 1.2, 1.1, default is 1.1"));
        options.addOption(new Option("f", "request-xml-file", true, "name of file contains request xml content."));
        options.addOption(new Option("x", "request-xml-content", true, "request xml content."));
        options.addOption(new Option("v", "verbose", false, "print out options and transport details."));
        options.addOption(new Option("h", "help", false, "print help message."));
        return options;
    }

    public static String invoke(Map<String, String> params) throws Exception {
	throw new RuntimeException("not implemented.");
    }

}
