package nl.craftsmen.huffman;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LzwDecoder {

    private Map<Short, String> map = new HashMap<>();

    public LzwDecoder() {
        for (short i = 0; i<256; i++) {
            byte[] value = new byte[1];
            value[0] = (byte)i;
            map.put(i, new String(value));
        }
    }

    public byte[] encode(Short[] data) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        short old = data[0];
        output.write(map.get(old).getBytes());
        int i = 1;
        while (i < data.length) {
            short next = data[i];
            if (!map.containsKey(next)) {
                String s = map.get(old);
                s = s + "";
            } else {
                String s = map.get(next);
                output.write(s.getBytes());
            }
            i++;
        }
        return null;

    }

    private String constructString(byte b) {
        byte[] helperArray = new byte[1];
        helperArray[0] = b;
        String p = new String(helperArray);
        return p;
    }

    private String constructString (String p, byte c) {
        byte[] pArray = p.getBytes();
        byte[] composite = new byte[pArray.length + 1];
        System.arraycopy(pArray, 0, composite, 0, pArray.length);
        composite[composite.length - 1] = c;
        return new String(composite);
    }
}
