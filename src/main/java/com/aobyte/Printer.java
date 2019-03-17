package com.aobyte;

import java.util.List;

class Printer {

    void printSelectedTags(List<Tag> tagsToPrint, List<Tag> html) {
        if (tagsToPrint.size() == 0) return;
        for (Tag tagItem : tagsToPrint) {
            printTag(tagItem, html, "\n", 0);
        }
    }

    private void printTag(Tag tagItem, List<Tag> html, String newLine, int depth) {
        String attrId = (tagItem.getAttrId() == null || tagItem.getAttrId().isEmpty()) ? "" : " id='" + tagItem.getAttrId() + "'";
        String attrClass = (tagItem.getAttrClass() == null || tagItem.getAttrClass().isEmpty()) ? "" : " class='" + tagItem.getAttrClass() + "'";

        System.out.print(newLine + "" + getDepth(depth) + "<" + tagItem.getName() + "" + attrId + "" + attrClass + ">" + tagItem.getText());
        boolean hasChild = false;
        for (Tag t : html) {
            if (t.getParent().getId() == tagItem.getId()) {
                hasChild = true;
                printTag(t, html, "\n", depth + 1);
            }
        }
        if (hasChild) {
            System.out.print(newLine + "" + getDepth(depth) + "</" + tagItem.getName() + ">");
        } else {
            System.out.print("</" + tagItem.getName() + ">");
        }
    }

    private String getDepth(int d) {
        StringBuilder depth = new StringBuilder();
        for (int i = 0; i < d; i++) {
            depth.append("\t");
        }
        return depth.toString();
    }

}
