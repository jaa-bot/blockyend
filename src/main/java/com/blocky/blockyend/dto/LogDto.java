package com.blocky.blockyend.dto;

import javax.validation.constraints.NotBlank;

public class LogDto {
    @NotBlank
    private int userId;

    @NotBlank
    private String accion;

    

    public LogDto(@NotBlank int userId, @NotBlank String accion) {
        this.userId = userId;
        this.accion = accion;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    
    
}
