package com.example.dsaca2;

import java.util.ArrayList;
import java.util.Collections;

public class GraphNode<T> {

    public T data;
    public int nodeValue = Integer.MAX_VALUE;
    public ArrayList<GraphNode.GraphLink> adjList = new ArrayList<>(); //Adjacency list now contains link objects = key!

    //Could use any concrete List implementation
    public GraphNode(T data) {
        this.data = data;
    }

    public class GraphLink {
        public GraphNode<?> destNode; //Could also store source node if required
        public int cost; //Other link attributes could be similarly stored

        public GraphLink(GraphNode<?> destNode, int cost) {
            this.destNode = destNode;
            this.cost = cost;
        }
    }

    public void connectToNodeDirected(GraphNode<T> destNode, int cost) {
        adjList.add(new GraphLink(destNode, cost)); //Add new link object to source adjacency list
    }

    public void connectToNodeUndirected(GraphNode<T> destNode, int cost) {
        adjList.add(new GraphLink(destNode, cost)); //Add new link object to source adjacency list
        destNode.adjList.add(new GraphLink(this, cost)); //Add new link object to destination adjacency list
    }

    public static <T> ArrayList<GraphNode<?>> findPathBreathFirst(GraphNode<?> startNode, T lookingfor){

        return null;
    }

    //Interface method to allow just the starting node and the goal node data to match to be specified
    public static <T> ArrayList<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode, T lookingfor) {
        ArrayList<ArrayList<GraphNode<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
        ArrayList<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }


    //Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
    public static <T> ArrayList<GraphNode<?>> findPathBreadthFirst(ArrayList<ArrayList<GraphNode<?>>> agenda, ArrayList<GraphNode<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null; //Search failed
        ArrayList<GraphNode<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNode<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
        if (currentNode.data.equals(lookingfor))
            return nextPath; //If that's the goal, we've found our path (so return it)
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for (GraphNode.GraphLink adjLink : currentNode.adjList) //For each adjacent node
            if (!encountered.contains(adjLink)) { //If it hasn't already been encountered
                ArrayList<GraphNode<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of the current/next path
                newPath.add(0, adjLink.destNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }
}

