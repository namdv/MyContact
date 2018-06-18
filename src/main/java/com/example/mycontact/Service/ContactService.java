package com.example.mycontact.Service;

import com.example.mycontact.model.Contact;


import java.util.List;
import java.util.Optional;

public interface ContactService {
    Iterable<Contact> findAll();
    List<Contact> search(String s);
    Contact findOne(int id);
    void save(Contact contact);
    void delete (int id);
}
