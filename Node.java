package com.example.demo;

import java.io.File;

public class Node {
    private Track s;
    private Node n;
    private Node p;

    public Node(){}

    public Node(Track s_, Node n_,Node p_){
        s=s_;
        n=n_;
        p=p_;
    }

    public Track getItem(){
        return s;
    }
    public Node getNext(){
        return n;
    }
    public void setItem(Track s1){
        s=s1;
    }
    public void setNext(Node n1){
        n=n1;
    }
    public void setPrevious(Node p_){
        p=p_;
    }
    public Node getPrevious(){
        return p;
    }
    public String toString(){
        return ("Item: "+s+" Next: "+n);
    }
}
