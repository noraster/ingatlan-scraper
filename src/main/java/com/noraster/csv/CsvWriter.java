package com.noraster.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvWriter {

    private final FileWriter writer;

    public CsvWriter(String outputFilePath) throws IOException {
        writer = new FileWriter(outputFilePath);
    }

    public void write(List<Map<String, String>> rawData) throws IOException {
        List<String> headers = determineHeaders(rawData);
        List<String> rows = prepareRows(headers, rawData);

        writer.append(String.join(",", headers));
        writer.append(System.lineSeparator());
        for (String row : rows) {
            writer.append(row);
            writer.append(System.lineSeparator());
        }
        writer.close();
    }

    private List<String> prepareRows(List<String> headers, List<Map<String, String>> rawData) {
        List<String> rows = new LinkedList<>();
        for (Map<String, String> item : rawData) {
            String row = prepareRow(headers, item);
            rows.add(row);
        }
        return rows;
    }

    private String prepareRow(List<String> headers, Map<String, String> item) {
        ArrayList<String> rowData = new ArrayList<>(headers.size());
        for (String header : headers) {
            rowData.add(item.getOrDefault(header, ""));
        }
        return rowData.stream()
                .map(r -> r.replaceAll("\"", "'"))
                .map(r -> String.format("\"%s\"", r))
                .collect(Collectors.joining(","));
    }

    private List<String> determineHeaders(List<Map<String, String>> listOfMap) {
        return listOfMap.stream()
                .flatMap(list -> list.keySet().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
