package ru.ccfit.nsu.bogush.md5bf;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class SocketReader {
    public static final char[] TEST_CHAR_SIZE_SIMBOL = new char[1];
    private InputStream inputStream;

    public SocketReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static int getCharSize(Charset charset) {
        return new String(TEST_CHAR_SIZE_SIMBOL).getBytes(charset).length;
    }

    public byte readByte() throws IOException {
        int theByte;
        if (-1 == (theByte = inputStream.read())) {
            throw new EOFException();
        }
        return (byte) theByte;
    }

    public int readInt() throws IOException {
        return  readByte() << 24 +
                readByte() << 16 +
                readByte() << 8 +
                readByte();
    }

    public byte[] readBytesFully(int numberOfBytes) throws IOException {
        byte[] bytes = new byte[numberOfBytes];
        for (int i = 0; i < numberOfBytes; i++) {
            bytes[i] = readByte();
        }
        return bytes;
    }

    public String readString(Charset charset, int length) throws IOException {
        length = length * getCharSize(charset);
        return new String(readBytesFully(length), charset);
    }

    public UUID readUUID() throws IOException {
        return UUID.nameUUIDFromBytes(readBytesFully(16));
    }
}