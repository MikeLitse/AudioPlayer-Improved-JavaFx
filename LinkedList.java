package com.example.demo;


import java.io.File;

public class LinkedList{
    private Node first;
    private Node last;
    private Node current=first;

    public LinkedList(){}

    public LinkedList(Node f,Node l){
        first=f;
        last=l;
        current=first;
    }

    public void setFirst(Node f){
        first=f;
    }
    public Node getFirst(){
        return first;
    }
    public void setLast(Node l){
        last=l;
    }
    public Node getLast(){
        return last;
    }
    public Node getCurrent(){
        return current;
    }
    public void setCurrent(Node c){
        current=c;
    }
    public boolean isEmpty(){
        return first==null;
    }
    public void insertFirst(Track data){
        if(isEmpty()){
            first=last=current=new Node(data,null,null);
        }else{
            first=new Node(data,first,null);
        }
    }
    public void insertLast(Track data){
        if(isEmpty()){
            first=last=current=new Node(data,null,null);
        }else{
            Node temp=new Node(data,null,last);
            last.setNext(temp);
            last=temp;
        }
    }
    public void printCurrent(){
        System.out.println("Current Node: "+current.getItem().toString());
    }
    public Object removeFirst(){
        if(isEmpty()){
            System.out.println("Exception");
        }
        Object remove=first.getItem();
        if(first==last){
            first=last=null;
        }else{
            first=first.getNext();
        }
        return remove;
    }
    public Object removeLast(){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }
        Object remove=last.getItem();
        if(first==last){
            first=last=null;
        }else{
            Node position;
            for(position=first;position.getNext()!=last;position=position.getNext()){
                last=position;
            }
            position.setNext(null);
        }
        return remove;
    }
    public int size(){
        int size=0;
        Node position=first;
        while(position!=null){
            position=position.getNext();
            size++;
        }
        return size;
    }
    public void printList(){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }else{
            for(Node position=first;position!=null;position=position.getNext()){
                System.out.println(position.getItem().toString());
            }
        }
    }
    public void emptyList(){
        Node position=first;
        while(position!=null){
            position.setItem(null);
            position=position.getNext();
        }
        first=last=current=null;
    }
    /*public Object maxOfList(){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }else{
            Object max=first.getItem();
            Node position=first;
            while (position!=null){
                Comparable CoMax=(Comparable)max;
                Comparable CoItem=(Comparable)position.getItem();
                if((CoMax.compareTo(CoItem))<0){
                    max=position.getItem();
                }
                position=position.getNext();
            }
            return max;
        }
    }*/
    public boolean exists(Object data){
        boolean flag=false;
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }else{  
            for(Node position=first;position!=null;position=position.getNext()){
                if(data==position.getItem()){
                    flag=true;
                }
            }
        }
        return flag;
    }
    /*public void sort(){
        Node trace=getFirst(),current,min;
        while(trace!=null){
            current=trace;
            min=trace;
            while(current!=null){
                if(((String)(current.getItem())).compareTo((String)(min.getItem()))<0){
                    min=current;
                }else{
                    current=current.getNext();
                }
            }
            String temp=(String)trace.getItem();
            trace.setItem(min.getItem());
            min.setItem(temp);
            trace=trace.getNext();
        }
    }
    public void bubbleSort(){
        Node current=getFirst();
        while(current!=null){
            Node second=current.getNext();
            while(second!=null){
                if(((String)current.getItem()).compareTo((String)second.getItem())>0){
                    String temp=(String)current.getItem();
                    current.setItem(second.getItem());
                    second.setItem(temp);
                }
                second=second.getNext();
            }
            current=current.getNext();
        }
    }*/

    /*public void insertPreLast(Track s){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }else{
            Node prelast=first;
            for(Node position=first;position.getNext()!=null;position=position.getNext()){
                prelast=position;
            }
            prelast.setNext(new Node(s,last));

        }
    }

     */

    public Object removePreLast(){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }
        Node prelast=first;
        for(Node position=first;position.getNext()!=null;position=position.getNext()){
            prelast=position;
        }
        for(Node position=first;position.getNext()!=null;position=position.getNext()){
            if(position.getNext()==prelast){
                position.setNext(last);
            }
        } 
        Object data=prelast.getItem();
        prelast=null;
        return data;
    }
    /*public Object removeNiggaLast(){
        if(isEmpty()){
            System.out.println("Exception. The list is empty");
        }
        Node prelast=first;
        Node position;
        for(position=getFirst();position.getNext()!=last;position=position.getNext()){
            prelast=position;
        }
        insertLast(prelast);
        return removeLast();
    }*/
}

