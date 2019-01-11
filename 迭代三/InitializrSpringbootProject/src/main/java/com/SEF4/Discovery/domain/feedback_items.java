/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class feedback_items {
    private Integer cash;
    private Date date;
    private Integer nums;
    private List<actor_item> actors;

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public List<actor_item> getActors() {
        return actors;
    }

    public void setActors(List<actor_item> actors) {
        this.actors = actors;
    }
    
}
