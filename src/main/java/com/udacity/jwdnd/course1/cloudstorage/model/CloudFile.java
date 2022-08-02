package com.udacity.jwdnd.course1.cloudstorage.model;

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

    /**
     * @return the fileId
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the fileName to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the contenttype
     */
    public String getContenttype() {
        return contenttype;
    }

    /**
     * @param contenttype the contenttype to set
     */
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    /**
     * @return the filesize
     */
    public String getFilesize() {
        return filesize;
    }

    /**
     * @param filesize the filesize to set
     */
    public void setFileSize(String filesize) {
        this.filesize = filesize;
    }

    /**
     * @return the userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userId the userid to set
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return the filedata
     */
    public byte[] getFiledata() {
        return filedata;
    }

    /**
     * @param filedata the filedata to set
     */
    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

}
