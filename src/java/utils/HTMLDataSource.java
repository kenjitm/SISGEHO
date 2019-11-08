/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

/**
 *
 * @author Raul A. Hernandez
 */
public class HTMLDataSource implements DataSource {

    private final String html;

    public HTMLDataSource(String htmlString) {
        html = htmlString;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (html == null) {
            throw new IOException("html message is null!");
        }
        return new ByteArrayInputStream(html.getBytes());
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("This DataHandler cannot write HTML");
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getName() {
        return "HTMLDataSource";
    }
}
