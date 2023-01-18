#Deque and Randomized Queues

The program `Deque` receives zero inputs. As output the Deque program prints the execution of some premade tests. The client program ´Permutation.java´ takes a command-line integer _k_; reads in a sequence of _N_ strings from stardard input using local libraries; prints out exactly _k_ of them, uniformly random. Each item from the sequence can be printed out at most once.
It is assumed that _0 ≤ k ≤ n_, where _n_ is the number of string on stdin. Full description of the project [here][1].

##HOW TO COMPILE AND RUN
Windows
    $ javac -cp ../lib/* Deque.java RandomizedQueue.java Permutation.java
    $ echo A B C D E F G H I | java -cp "../lib/*;." Permutation 3
    $ echo AA BB BB BB BB BB CC CC | java -cp "../lib/*;." Permutation 8

Linux
    $ javac -cp ../lib/* Deque.java RandomizedQueue.java Permutation.java
    $ echo A B C D E F G H I | java -cp ".;../lib/*" Permutation 3
    $ echo AA BB BB BB BB BB CC CC | java -cp ".;../lib/*" Permutation 8

[1]: https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
