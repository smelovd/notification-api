package com.smelovd.notification_api.service;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class CreateFile {

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.csv"));
        for (int i = 0; i < 11; i++) {
            writer.write(i + "," + i + "@gmail.com," + "MAIL\n");
        }
        writer.close();
    }
}
