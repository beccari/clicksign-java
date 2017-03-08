package com.clicksign.models;

import com.clicksign.exception.ClicksignException;

import java.io.*;

/**
 * File abstraction creating an indirection layer for FileSystem files, Input/Output Streams or byte[]
 *
 * @author Marcelo Beccari
 * @see java.io.File
 */
public class DocumentFile {

    String filename;

    byte[] bytes;


    public DocumentFile(File file) throws ClicksignException {
        this.filename = file.getName();
        this.bytes = convertToBytes(file);
    }

    public DocumentFile(String filename, byte[] bytes) {
        this.filename = filename;
        this.bytes = bytes;
    }

    private static byte[] convertToBytes(File file) throws ClicksignException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            try {
                byte[] buffer = new byte[4096];
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }


            return os.toByteArray();
        } catch (IOException ex) {
            throw new ClicksignException("Falha na leitura do Arquivo " + file, ex);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFilename() {
        return filename;
    }
}
