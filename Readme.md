This project is an implementation of the Simple-Pass In-Memory Indexing (SPIMI)

Objectives: Implement SPIMI. Implement rudimentary information retrieval using your SPIMI indexer. Test and
analyze your system, discuss how your design decisions in
uence the results.

Description:
1. implement the SPIMI algorithm with disk block merging
SPIMI is an algorithm and has to be implemented as described in the book, no reimagining or shortcuts. To
simulate saving and reading to disk, make your program create a directory called DISK. You will need to
demonstrate two things to the markers: (a) Assuming a disk block size of 500 Reuter's articles, make it possible
to inspect the blocks your SPIMI creates (call simulated disk blocks BLOCK1 ... BLOCK7 ... ) (b) make it
possible to inspect the merged index vocabulary (simulate distributing your nal index over two blocks). Make
sure you can demonstrate merging from n blocks.
2. compile an inverted index for Reuters21578 without using any compression techniques, still distributing your
index over two blocks and accessing it from there (no hash!)
3. implement the lossy dictionary compression techniques of Table 5.1 in the textbook and compile a similar table
for Reuters-21578.
4. implement a simple scheme to retrieve matching documents for a few queries (not only single word queries).
Techniques from Chapters 1-3 are suitable. Show the queries you used and discuss your ndings.

Test queries:
  1. design three test queries:
    (a) a single keyword query,
    (b) a multiple keywords query returning documents containing all the keywords (AND),
    (c) a multiple keywords query returning documents containing at least one keyword (OR), where documents
        are ordered by how many keywords they contain)
  2. run your three test queries to showcase your code and comment on the results in your report
