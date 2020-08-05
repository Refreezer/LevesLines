package com.company;

import java.io.*;

public class InputStreamConsumerThread extends Thread {
    private final InputStream is;
    private final boolean sysOut;
    private final StringBuilder output = new StringBuilder();

    public InputStreamConsumerThread(InputStream is, boolean sysOut) {
        super();
        this.is = is;
        this.sysOut = sysOut;
    }

    public void run() {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (sysOut)
                    System.out.println(line);
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getOutput() {
        return output.toString();
    }
}