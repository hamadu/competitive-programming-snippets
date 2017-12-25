# Competitive Programming Snippets (Java Edition)

[![CircleCI](https://circleci.com/gh/hamadu/competitive-programming-snippets.svg?style=svg)](https://circleci.com/gh/hamadu/competitive-programming-snippets)

[![Coverage Status](https://coveralls.io/repos/github/hamadu/competitive-programming-snippets/badge.svg?branch=master)](https://coveralls.io/github/hamadu/competitive-programming-snippets?branch=master)

### Contents

* Combinatorics
  * Bell Number
  * Stirling Number
  * Iterate SubSet
  * Permutation
* Data Structures
  * Fenwick trees
    * FenwickTree(Point Add, Range Sum) 
    * FenwickRange(Range Add, Range Sum) 
  * Persistent version
    * PersistentArray
    * PersistentSegmentTree
    * PersistentStack
  * Segment trees
    * SegmtntTreePURMQ(Point Update, Range Minimum Query)
    * SegmtntTreeRARMQ(Range Add, Range Minimum Query)
    * SegmtntTreeRARMQWithIndex(Range Add, Range Minimum Query With Index)
    * SegmtntTreeRURSQ(Range Update, Range Sum Query)
* Geometry
  * ClosestPoint
  * ConvexHull
* Graphs
  * Network-flow algorithms
    * Hungarian
    * Matching(Edmonds cardinality matching algorithm)
    * MaxFlowDinic
    * MaxFlowFord(Ford-Fulkerson algorithm)
    * MinCostFlow
  * Shortest-path algorithms
    * Dijkstra
    * WarshallFloyd
    * ZeroOneBFS(0-1 BFS)
  * Tree algorithms
    * EulerTour
    * HeavyLightDecomposition
    * LCA(Lowest Common Ancestor)
    * LCASparseTable(Lowest Common Ancestor using Sparse table)
    * ParentCountOrder(computes parent, subtree size, and dfs order for each vertex on specific root)
    * TreeLCAQuery(?)
  * BatchedDynamicConnectivity
  * CenteroidDecomposition
  * DirectedGraph(mutable graph for memory-saving)
  * EulerianPath
  * LowLink
  * MinimumSpanningArborescence
  * MinimumSteinerTree
  * SAT2(2-sat)
  * SCC1(Decompose directed graph into Strongly connected components)
  * SCC2(Decompose undirected graphs into component-bridge based tree)
  * SCC3(Decompose graph edges into components such that each component doesn't contain an articulation point)
  * TopologicalSort1
  * TopologicalSort2(Topological Sort with lexical smallest order. (Kahn's algorithm))
* Strings
  * ErrorFunction(largest S < idx s.t. [0,S) == [idx-S, idx))
  * Manachar
  * PrefixAutomaton
  * RollingHash
  * SuffixArray
  * ZFunction(longest common prefix of ([idx,n), [0,n) for each idx)
* Utils
  * I/O
    * GraphBuilder
    * InputReader
  * Random
    * XorShift
  * BitVector
  * ConvexHullTechMax
  * ConvexHullTechMin
  * CountRangeCoveringRange
  * CountRangeCoveringRangeAnother
  * CountRangeCoveringRangeOnlinePersistent
  * CountRangeCoveringRangeOnlineWavelet
  * Dice
  * DistinctNumberRange(counts distinct number for given range)
    * DistinctNumberRange1
    * DistinctNumberRange2
  * FastFourierTransformation
  * MeldableSegment
  * RangeSegment
  * SlideMinValue
  * StockSpan

### How to use

For IntteliJ IDEA: Copy and paste **idea_live_templates.xml** to IntteliJ IDEA.
