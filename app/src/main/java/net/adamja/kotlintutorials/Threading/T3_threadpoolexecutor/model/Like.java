package net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model;


import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.Activity;

import java.util.Date;

public class Like implements Activity {
    private Date createdDate;

    public Like(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Like{" +
                "createdDate=" + createdDate +
                '}';
    }
}