package com.epam.cm.dto;

/**
 * Created by Lev_Serba on 2/10/2017.
 */
public class AttachFileDTO {
    private String filePath;
    private String fileName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileNameFromPath(String filePath) {
        this.fileName = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length());
    }
}
