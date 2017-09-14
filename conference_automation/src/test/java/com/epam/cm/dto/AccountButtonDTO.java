package com.epam.cm.dto;

public class AccountButtonDTO {
    String link;
    String btnName;

    public AccountButtonDTO(String btnName, String link) {
        this.link = link;
        this.btnName = btnName;
    }

    public AccountButtonDTO() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AccountButtonDTO))
            return false;

        AccountButtonDTO that = (AccountButtonDTO) o;

        if (!link.equals(that.link))
            return false;
        return btnName.equals(that.btnName);

    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + btnName.hashCode();
        return result;
    }
}
