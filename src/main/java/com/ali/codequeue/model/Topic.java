package com.ali.codequeue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Topic {
    @Id
    private String name;
    private String notes;

    public Topic(String name){
        this.name = name;
    }
    public Topic(){}



    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public String getName() {
        return name;
    }
}
/*
 * WHY @Entity CLASSES NEED TWO CONSTRUCTORS:
 *
 * public Topic() {}
 *   → Required by Hibernate. When reading a row from the database,
 *     Hibernate creates an empty object first, then fills in the fields.
 *     It needs a no-arg constructor to do step 1.
 *     You never call this yourself — Hibernate calls it internally.
 *     Java provides this for free ONLY if you define no other constructors.
 *     Once you add Topic(String name), Java removes the default — so you must add it back manually.
 *
 * public Topic(String name) { this.name = name; }
 *   → Required by YOU. Since name has no setter (it's the primary key, it should never change),
 *     the only way to set the name when creating a new Topic is through this constructor.
 *     Used in updateNotes() when a topic row doesn't exist yet: new Topic(topic)
 */
