package com.example.veterinerkullanici.Models;

public class LoginModel {
    private boolean tf;
    private String mailAdres;
    private String parola;
    private String id;
    private String text;
    private String userName;

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    public boolean isTf() {
        return tf;
    }

    public void setMailAdres(String mailAdres) {
        this.mailAdres = mailAdres;
    }

    public String getMailAdres() {
        return mailAdres;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getParola() {
        return parola;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return
                "LoginModel{" +
                        "tf = '" + tf + '\'' +
                        ",mailAdres = '" + mailAdres + '\'' +
                        ",parola = '" + parola + '\'' +
                        ",id = '" + id + '\'' +
                        ",text = '" + text + '\'' +
                        ",userName = '" + userName + '\'' +
                        "}";
    }
}
