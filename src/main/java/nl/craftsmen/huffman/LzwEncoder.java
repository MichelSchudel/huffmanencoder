package nl.craftsmen.huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class LzwEncoder {

    private Map<String, Short> map = new HashMap<>();

    public LzwEncoder() {
        for (short i = 0; i<256; i++) {
            byte[] value = new byte[1];
            value[0] = (byte)i;
            map.put(new String(value), i);
        }
    }

    public Short[] encode(byte[] data) {
        ArrayList<Short> output = new ArrayList<>();
        int i = 1;
        short codePoint = 256;
        byte firstByte = data[0];
        byte[] helperArray = new byte[1];
        helperArray[0] = firstByte;
        String p = new String(helperArray);
        while (i < data.length) {
            byte c = data[i];
            String s = constructString(p, c);
            if (map.containsKey(s)) {
                p = constructString(p, c);
            } else {
                output.add(map.get(p));
                map.put(s, codePoint);
                codePoint++;
                p = constructString(c);
            }
            i++;
        }
        output.add(map.get(p));
        return output.toArray(new Short[0]);
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
