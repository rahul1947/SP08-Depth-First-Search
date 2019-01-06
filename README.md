## Short Project SP08: Depth First Search

1. Implementation of Depth First Search algorithm for a Graph, using rbk/Graph.java. 

#### Author
* [Rahul Nalawade](https://github.com/rahul1947)

#### Date
* October 28, 2018

_______________________________________________________________________________
### Problems:

#### A. Team Task: 

**Problem 1.**
   Implement topologicalOrdering1().
   This is the DFS-based algorithm for finding the topological ordering
   of a directed acyclic graph.

**Solution:** [DFS.java](https://github.com/rahul1947/SP08-Depth-First-Search/blob/master/DFS.java)


#### B. Individual Tasks (optional):

**Problem 2.**
   Implement topologicalOrdering2(g).
   In this algorithm discussed in class, we keep removing
   nodes with no incoming edges.

**Solution:** [DFS.java](https://github.com/rahul1947/SP08-Depth-First-Search/blob/master/DFS.java)


**Problem 3.**
   Implement connectedComponents().
   In this algorithm, use DFS to find the number of connected components
   of a given undirected graph. Each node gets a cno (component number).
   All nodes in the same connected component receive the same cno.

**Solution:** [DFS.java](https://github.com/rahul1947/SP08-Depth-First-Search/blob/master/DFS.java)

```
Sample Output: 
# SP08 - CONNECTED COMPONENTS AND TOPOLOGICAL ORDERINGS: 
____________________________________________________________
Graph: n: 10, m: 12, directed: true, Edge weights: false
1 :  (1,3) (1,8)
2 :  (2,4)
3 :  (3,2)
4 :  (4,7)
5 :  (5,10) (5,4)
6 :  (6,8) (6,10)
7 : 
8 :  (8,2) (8,5)
9 : 
10 :  (10,9)
____________________________________________________________
Number of connected components: 2
u	cno
1	1
2	1
3	1
4	1
5	1
6	2
7	1
8	1
9	1
10	1

Topological Ordering 1: 
6 1 8 5 10 9 3 2 4 7 

Topological Ordering 2: 
1 6 3 8 2 5 10 4 9 7 
```
_______________________________________________________________________________