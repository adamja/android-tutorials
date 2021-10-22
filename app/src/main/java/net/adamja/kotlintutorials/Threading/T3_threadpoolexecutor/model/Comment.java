package net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model;


import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.Activity;

import java.util.Date;

public class Comment implements Activity {

    private Date createdDate;

    public Comment(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "createdDate=" + createdDate +
                '}';
    }
}