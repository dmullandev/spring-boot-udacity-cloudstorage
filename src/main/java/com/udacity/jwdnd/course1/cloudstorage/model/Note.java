package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    /**
     * @param noteId
     * @param noteTitle
     * @param noteDescription
     * @param userId
     */
    public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }
}
