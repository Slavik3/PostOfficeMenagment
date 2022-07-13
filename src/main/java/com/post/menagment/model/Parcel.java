package com.post.menagment.model;

import javax.persistence.*;

@Entity
public class Parcel {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int weight;
    private long idFrom;
    private long idTo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sender sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipient recipient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(long idFrom) {
        this.idFrom = idFrom;
    }

    public long getIdTo() {
        return idTo;
    }

    public void setIdTo(long idTo) {
        this.idTo = idTo;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Parcel() {
    }

    public Parcel(Long id, String name, int weight, long idFrom, long idTo, Sender sender, Recipient recipient) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.sender = sender;
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", idFrom=" + idFrom +
                ", idTo=" + idTo +
                ", sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }
}
