class RC4 {
    private final byte[] permutations = new byte[256];

    RC4(final byte[] key) {
        if (key.length < 8 || key.length > 256) {
            throw new IllegalArgumentException("key must be between 8 and 256 bytes");
        }

        byte[] t = new byte[256];

        for (int i = 0; i < 256; i++) {
            permutations[i] = (byte) i;
            t[i] = key[i % key.length];
        }

        int j = 0;

        for (int i = 0; i < 256; i++) {
            j = (j + permutations[i] + t[i]) & 0xFF;
            swap(permutations, i, j);
        }

    }

    byte[] decrypt(final byte[] text) {
        return encrypt(text);
    }

    byte[] encrypt(final byte[] plaintext) {
        final byte[] text = new byte[plaintext.length];
        int i = 0, j = 0, k, t;

        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + permutations[i]) & 0xFF;

            swap(permutations, i, j);
            t = (permutations[i] + permutations[j]) & 0xFF;
            k = permutations[t];

            text[counter] = (byte) (plaintext[counter] ^ k);
        }
        return text;
    }

    private static void swap(byte[] array, int index, int _index){
        byte temp;
        temp = array[index];
        array[index] = array[_index];
        array[_index] = temp;
    }
}