package com.example.dsaca2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphNodeAL<T> {

    public T data;

    public List<GraphNodeAL<T>> adjList = new ArrayList<>(); //Could use any List implementation

    public GraphNodeAL(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
    }

    public void connectToNodeUndirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    //Regular recursive depth-first graph traversal
    public static void traverseGraphDepthFirst(GraphNodeAL<?> from, List<GraphNodeAL<?>> encountered) {
        System.out.println(from.data);
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        for (GraphNodeAL<?> adjNode : from.adjList)
            if (!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered);
    }


    //Agenda list based breadth-first graph traversal (tail recursive)
    public static void traverseGraphBreadthFirst(List<GraphNodeAL<?>> agenda, List<GraphNodeAL<?>> encountered) {
        if (agenda.isEmpty()) return;
        GraphNodeAL<?> next = agenda.remove(0);
        System.out.println(next.data);
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for (GraphNodeAL<?> adjNode : next.adjList)
            if (!encountered.contains(adjNode) && !agenda.contains(adjNode))
                agenda.add(adjNode); //Add children to end of agenda
        traverseGraphBreadthFirst(agenda, encountered); //Tail call
    }


    //Agenda list based depth-first graph traversal (tail recursive)
    public static <T> void traverseGraphDepthFirst(List<GraphNodeAL<?>> agenda, List<GraphNodeAL<?>> encountered) {
        if (agenda.isEmpty()) return;
        GraphNodeAL<?> next = agenda.remove(0);
        System.out.println(next.data);
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for (GraphNodeAL<?> adjNode : next.adjList)
            if (!encountered.contains(adjNode) && !agenda.contains(adjNode))
                agenda.add(0, adjNode); //Add children to front of agenda (order unimportant here)
        traverseGraphDepthFirst(agenda, encountered); //Tail call
    }


    //Recursive depth-first search of graph (node returned if found)
    public static <T> GraphNodeAL<?> searchGraphDepthFirst(GraphNodeAL<?> from, List<GraphNodeAL<?>> encountered, T lookingfor) {
        if (from.data.equals(lookingfor)) return from;
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        for (GraphNodeAL<?> adjNode : from.adjList)
            if (!encountered.contains(adjNode)) {
                GraphNodeAL<?> result = searchGraphDepthFirst(adjNode, encountered, lookingfor);
                if (result != null) return result;
            }
        return null;
    }


    //Recursive depth-first search of graph (all paths identified returned)
    public static <T> List<List<GraphNodeAL<?>>> findAllPathsDepthFirst(GraphNodeAL<?> from, List<GraphNodeAL<?>> encountered, T lookingfor) {
        List<List<GraphNodeAL<?>>> result = null, temp2;
        if (from.data.equals(lookingfor)) { //Found it
            List<GraphNodeAL<?>> temp = new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result = new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list
        for (GraphNodeAL<?> adjNode : from.adjList) {
            if (!encountered.contains(adjNode)) {
                temp2 = findAllPathsDepthFirst(adjNode, new ArrayList<>(encountered), lookingfor); //Use clone of encountered list for recursive call!
                if (temp2 != null) { //Result of the recursive call contains one or more paths to the solution node
                    for (List<GraphNodeAL<?>> x : temp2) //For each partial path list returned
                        x.add(0, from); //Add the current node to the front of each path list
                    if (result == null)
                        result = temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }

    //Interface method to allow just the starting node and the goal node data to match to be specified
    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(GraphNodeAL<?> startNode, T lookingfor) {
        List<List<GraphNodeAL<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNodeAL<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }


    //Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(List<List<GraphNodeAL<?>>> agenda,
                                                                List<GraphNodeAL<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null; //Search failed
        List<GraphNodeAL<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
        if (currentNode.data.equals(lookingfor))
            return nextPath; //If that's the goal, we've found our path (so return it)
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for (GraphNodeAL<?> adjNode : currentNode.adjList) //For each adjacent node
            if (!encountered.contains(adjNode)) { //If it hasn't already been encountered
                List<GraphNodeAL<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of the current/next path
                newPath.add(0, adjNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }

    public class GraphLinkAL {
        public GraphNodeAL2<?> destNode; //Could also store source node if required
        public int cost; //Other link attributes could be similarly stored

        public GraphLinkAL(GraphNodeAL2<?> destNode, int cost) {
            this.destNode = destNode;
            this.cost = cost;
        }
    }

    public class GraphNodeAL2<T> {

        public T data;
        public List<GraphLinkAL> adjList = new ArrayList<>(); //Adjacency list now contains link objects = key!

        //Could use any concrete List implementation
        public GraphNodeAL2(T data) {
            this.data = data;
        }

        public void connectToNodeDirected(GraphNodeAL2<T> destNode, int cost) {
            adjList.add(new GraphLinkAL(destNode, cost)); //Add new link object to source adjacency list
        }

        public void connectToNodeUndirected(GraphNodeAL2<T> destNode, int cost) {
            adjList.add(new GraphLinkAL(destNode, cost)); //Add new link object to source adjacency list
            destNode.adjList.add(new GraphLinkAL(this, cost)); //Add new link object to destination adjacency list
        }

        //Regular recursive depth-first graph traversal
        public void traverseGraphDepthFirst(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered) {
            System.out.println(from.data);
            if (encountered == null)
                encountered = new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from);
            for (GraphLinkAL adjLink : from.adjList)
                if (!encountered.contains(adjLink.destNode)) traverseGraphDepthFirst(adjLink.destNode, encountered);
        }

        //Regular recursive depth-first graph traversal with total cost
        public void traverseGraphDepthFirstShowingTotalCost(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, int totalCost) {
            System.out.println(from.data + " (Total cost of reaching node: " + totalCost + ")");
            if (encountered == null)
                encountered = new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from); //Could sort adjacency list here based on cost – see next slide for more info!
            for (GraphLinkAL adjLink : from.adjList)
                if (!encountered.contains(adjLink.destNode))
                    traverseGraphDepthFirstShowingTotalCost(adjLink.destNode, encountered, totalCost + adjLink.cost);
        }
        //Sort adjacency list of edges based on cost (lowest first)
        // TODO Collections.sort(from.adjList,(a,b)->a.cost-b.cost);

        //New class to hold a CostedPath object i.e. a list of GraphNodeAL2 objects and a total cost attribute
        public class CostedPath{
            public int pathCost=0;
            public List<GraphNodeAL2<?>> pathList=new ArrayList<>();
        }
        //Retrieve the cheapest path by expanding all paths recursively depth-first
        public <T> CostedPath searchGraphDepthFirstCheapestPath(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered,
                                                                       int totalCost, T lookingfor){
            if(from.data.equals(lookingfor)){ //Found it - end of path
                CostedPath cp=new CostedPath(); //Create a new CostedPath object
                cp.pathList.add(from); //Add the current node to it - only (end of path) element
                cp.pathCost=totalCost; //Use the current total cost
                return cp; //Return the CostedPath object
            }
            if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from);
            List<CostedPath> allPaths=new ArrayList<>(); //Collection for all candidate costed paths from this node
            for(GraphLinkAL adjLink : from.adjList) //For every adjacent node
                if(!encountered.contains(adjLink.destNode)) //That has not yet been encountered
                {//Create a new CostedPath from this node to the searched for item (if a valid path exists)
                    CostedPath temp=searchGraphDepthFirstCheapestPath(adjLink.destNode,new ArrayList<>(encountered),
                            totalCost+adjLink.cost,lookingfor);
                    if(temp==null) continue; //No path was found, so continue to the next iteration
                    temp.pathList.add(0,from); //Add the current node to the front of the path list
                    allPaths.add(temp); //Add the new candidate path to the list of all costed paths
                }//If no paths were found then return null. Otherwise, return the cheapest path (i.e. the one with min pathCost)
            return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1,p2)->p1.pathCost-p2.pathCost);
        }//TODO pg 58 in slides so far


    }
}

