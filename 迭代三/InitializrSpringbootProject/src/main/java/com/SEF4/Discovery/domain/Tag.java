/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class Tag {
    private String GrobalTag="";
    private List<String> Lines=new ArrayList<String>();//Every String is a series of points
    private List<String> AreaTags=new ArrayList<String>();//Every String is a tag

    public String getGrobalTag() {
        return GrobalTag;
    }

    public void setGrobalTag(String GrobalTag) {
        this.GrobalTag = GrobalTag;
    }

    public List<String> getLines() {
        return Lines;
    }

    public void setLines(List<String> Lines) {
        this.Lines = Lines;
    }

    public List<String> getAreaTags() {
        return AreaTags;
    }

    public void setAreaTags(List<String> AreaTags) {
        this.AreaTags = AreaTags;
    }
    
}
