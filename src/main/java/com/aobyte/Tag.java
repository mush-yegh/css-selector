package com.aobyte;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private int id;
    private String name;
    private String attrId;
    private String attrClass;
    //private List<String> className; //TO DO
    private String text;
    private Tag parent;

   boolean isEqual(Tag t){
       if (!t.getName().isEmpty()) {
           if (!this.getName().equals(t.getName()))
           return false;
       }
       if (!t.getAttrId().isEmpty()) {
           if (!this.getAttrId().equals(t.getAttrId()))
               return false;
       }
       if (!t.getAttrClass().isEmpty()) {
           if (!this.getAttrClass().equals(t.getAttrClass()))
               return false;
       }
       return true;
   }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attrId='" + attrId + '\'' +
                ", attrClass='" + attrClass + '\'' +
                ", text='" + text + '\'' +
                ", parent=" + parent +
                '}';
    }

}
