# Pattern Recognition

Given a set of _n_ distinct points in the plane, find every (maximal) line
segment that connects a subset of 4 or more of the points. For more
informations, check [the official assignment description][1].

## How to compile

Mac OS / Linux

    $ javac -cp ".:../lib/*" BruteCollinearPoints.java Point.java LineSegment.java FastCollinearPoints.java
    $ java -cp "../lib/*:." FastCollinearPoints input8.txt

Windows

    $ javac -cp ../lib/* BruteCollinearPoints.java Point.java LineSegment.java FastCollinearPoints.java
    $ java -cp "../lib/*;." FastCollinearPoints input8.txt

[1]: http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
