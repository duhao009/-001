package com.project.project002.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private PrintWriter pwrite;
    public MyResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new MyServletOutputStream();
    }
    @Override
    public PrintWriter getWriter() throws IOException {
        OutputStreamWriter outputStreamWriter=null;
        try{
            outputStreamWriter= new OutputStreamWriter(bytes, "utf-8");
            pwrite = new PrintWriter(outputStreamWriter);
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        }
        return pwrite;
    }
    /**
     * 
     * 获取响应数据
     * @param 
     * @return byte[]
     * @throws
     * @author DH
     */
    public byte[] getBytes() {
        if(null != pwrite) {
            pwrite.close();
            return bytes.toByteArray();
        }
        if(null != bytes) {
            try {
                bytes.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return bytes.toByteArray();
    }
    private class MyServletOutputStream extends ServletOutputStream {
        @Override
        public void write(int b) throws IOException {
            bytes.write(b); // 将数据写到 stream　中
        }
        @Override
        public boolean isReady() {
            return false;
        }
        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }
}
