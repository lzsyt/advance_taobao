package com.kzq.advance.domain.vo;

import java.io.Serializable;
import java.util.Date;

public class ImageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdateStart;
    private Date createdateEnd;

    public Date getCreatedateStart() {
        return createdateStart;
    }

    public void setCreatedateStart(Date createdateStart) {
        this.createdateStart = createdateStart;
    }

    public Date getCreatedateEnd() {
        return createdateEnd;
    }

    public void setCreatedateEnd(Date createdateEnd) {
        this.createdateEnd = createdateEnd;
    }
}
