#Percolation

The program `Percolation Stats` receives two inputs, _n_ which is going to determine the size of the grid n-by-n and _T_ which represents the trials that the algorithm is going to be executed. As output the PercolationStats program prints the mean, standard deviation, and the 95% confience interval for percolation of the given grid. Full description of the project [here][1].

##HOW TO COMPILE AND RUN
Windows
    $ javac -cp ../lib/* Percolation.java PercolationStats.java
    $ java -cp "../lib/*;" PercolationStats 200 1000
Linux
    $ javac -cp ../lib/* Percolation.java PercolationStats.java
    $ java -cp "../lib/*:" PercolationStats 200 1000

[1]: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
