package com.recore.tishkcare.Activitys.Model;

public class Patient {

    private String email,password,name,phone,pateintId,work,education,dateOfBirth,location,patientImg;

    public Patient() {
    }

    public Patient(String email, String password, String name, String phone, String pateintId, String work, String education, String dateOfBirth, String location, String patientImg) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.pateintId = pateintId;
        this.work = work;
        this.education = education;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.patientImg = patientImg;
    }

    public Patient(String email, String password, String name, String phone, String pateintId, String work, String education, String dateOfBirth, String location) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.pateintId = pateintId;
        this.work = work;
        this.education = education;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
    }

    public Patient(String email, String password, String name, String phone, String pateintId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.pateintId=pateintId;
    }

    public String getPateintId() {
        return pateintId;
    }

    public void setPateintId(String pateintId) {
        this.pateintId = pateintId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPatientImg() {
        return patientImg;
    }

    public void setPatientImg(String patientImg) {
        this.patientImg = patientImg;
    }
}
