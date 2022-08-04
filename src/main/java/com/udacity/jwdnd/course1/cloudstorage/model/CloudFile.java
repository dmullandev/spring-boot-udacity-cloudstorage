package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class CloudFile {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    /**
     * @param fileId
     * @param filename
     * @param contenttype
     * @param filesize
     * @param userid
     * @param filedata
     */
    public CloudFile(Integer fileId, String filename, String contenttype, String filesize, Integer userid,
            byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }
}
