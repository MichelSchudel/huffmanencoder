# Huffman encoder and decoder

This project demonstrates how Huffman compression and decompression works.

This project is partly based on the explanation explained in this article:

https://www.techiedelight.com/huffman-coding/

You can find more information on the wikipedia page:

https://en.wikipedia.org/wiki/Huffman_coding



## How the algorithm works

### Compression

1. Given an arbritary sequence of bytes, count for each unique byte value in the sequence how often it occurs. Store these tuples as leaf nodes (byte value, frequency) in a collection.
2. Construct a Huffman tree:
   1. Take the two nodes with the *lowest* frequency out out the collection
   2. Create a new node that points to the two nodes. Give the node an frequency value that is the sum of the frequency values of the two nodes.
   3. Put the new node in the collection.
   4. Continue this process until there is only one node left. This is the root node of the tree.
3. Determine the Huffman code for each byte value and store it into a table:
   1. Start with the root node and completely traverse the tree. For each "left" turn, add a 0 bit. For each right turn, add a "1".
   2. Do this until all leaf nodes have been reached.
4. Encode the original byte stream:
   1. For each byte, look up the Huffman code in the table. This is the code in the new byte stream.
   2. Continue until the end of the stream has been reached.

After this, you end up with:

- a Huffman tree
- the length of the compressed byte stream (in bits)
- The compressed byte stream

### Decompression
1. Walk through the Huffman tree using the bits in the compressed byte stream. 0 means take a left turn, 1 means take a right turn.
    1. If you encounter a leaf node with a byte value, add that value to the decompressed byte stream.
    2. Go back to the root of the Huffman tree.
2. Repeat until you reach the end of the compressed byte stream.   
