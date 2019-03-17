package com.aobyte;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

class Parser {
    private File file;
    private List<Tag> htmlTags;
    private int id = 0;

    Parser(File file) {
        this.file = file;
        htmlTags = new ArrayList<Tag>();
        init();
    }

    private void init() {
        String html = getFileContent();
        String body = html.substring(getBodyStartIndex("body", html), getBodyEndIndex("body", html));

        getTags(body.trim()
                .replaceAll("\t", "")
                .replaceAll("\n", "")
                .replaceAll("\\s+", " "), null);

    }

    //Read file
    private String getFileContent() {
        StringBuilder html = new StringBuilder();
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                html.append(line + "\n");
            }
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("1 " + e.getMessage());
        } catch (IOException e) {
            System.out.println("2 " + e.getMessage());
        } catch (Exception e) {
            System.out.println("3 " + e.getMessage());
        }
        return html.toString();
    }

    private void getTags(String html, Tag parent) {
        int tagDeclStartIndex = 0;
        int tagDeclEndIndex = html.indexOf('>') + 1;
        String currTag = html.substring(tagDeclStartIndex, tagDeclEndIndex);

        String tagName = currTag.substring(currTag.indexOf("<") + 1, currTag.length() - 1).split(" ")[0];
        String attrClass = getAttr(currTag, "class");
        String attrId = getAttr(currTag, "id");
        String text = "";

        //only for body
        if (parent == null) {
            parent = new Tag(id++, "html", "", "", "", null);
        }

        int tagEndIndex = getTagEndIndex(html);

        String tagContent = getTagContent(html, tagDeclEndIndex, tagEndIndex, tagName);

        Tag currentTag = new Tag(id++, tagName, attrId, attrClass, text, parent);

        //check if there is another tag except what we get
        String restOfHtml = html.substring(tagEndIndex + ("</" + tagName + ">").length());

        if (tagContent.contains("<")) {
            //create new tag obj with empty text and add it to the list
            htmlTags.add(currentTag);
            getTags(tagContent, currentTag);
        } else {
            //there is no child inside tagContent, just get text
            currentTag.setText(tagContent);
            htmlTags.add(currentTag);
        }
        if (restOfHtml.contains("<")) {
            getTags(restOfHtml, parent);
        }
    }

    private String getTagContent(String s, int tagDeclEndIndex, int tagEndIndex, String tagName) {
        return s.substring(tagDeclEndIndex, tagEndIndex);
    }

    private String getAttr(String tag, String attrName) {
        String attrValue = "";
        if (tag.contains(attrName)) {
            attrValue = tag.split("\"", tag.indexOf(attrName))[1];
        }
        return attrValue;
    }

    private int getTagEndIndex(String str) {
        String s = str.trim().replaceAll("\t", "").replaceAll("\n", "");

        int tagEndIndex = -1;
        int nestedTagCounter = 0;
        char[] charArray = s.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '<') {
                if (charArray[i + 1] == '/') {
                    nestedTagCounter--;
                } else {
                    nestedTagCounter++;
                }
            }
            if (nestedTagCounter == 0) {
                tagEndIndex = i;
                break;
            }
        }
        return tagEndIndex;
    }



    ArrayList<Integer> getMatchingTagIds(List<Tag> queriedTags) {
        ArrayList<Integer> matchingId = new ArrayList<>();
        for (Tag h : this.htmlTags) {
            for (int i = 0; i < queriedTags.size(); i++) {
                Tag s = queriedTags.get(i);
                if (h.isEqual(s)) {
                    if (queriedTags.size() > 1) {
                        boolean hasNeededParent = checkForParent(h.getParent(), queriedTags.subList(i + 1, queriedTags.size()));
                        if (hasNeededParent)
                            matchingId.add(h.getId());
                    } else {
                        matchingId.add(h.getId());
                    }
                }
            }//if there is no match {break:outerLoop}
        }
        return matchingId;
    }

    private boolean checkForParent(Tag h, List<Tag> selectorTag) {
        if (h == null) {
            return false;
        }
        for (Tag selectorItem : selectorTag) {
            if (h.isEqual(selectorItem)) {
                return true;
            } else {
                checkForParent(h.getParent(), selectorTag.subList(1, selectorTag.size()));
            }
        }
        return false;
    }

    List<Tag> getTagsToPrint(ArrayList<Integer> matchingTagIds) {
        return htmlTags
                .stream()
                .filter(t -> matchingTagIds.contains(t.getId()))
                .collect(Collectors.toList());
    }

    List<Tag> getHtmlTags() {
        return htmlTags;
    }

    //only for body
    private int getBodyStartIndex(String tagName, String html) {
        return html.indexOf("<" + tagName);
    }

    private int getBodyEndIndex(String tagName, String html) {
        return html.indexOf("</" + tagName + ">") + ("</" + tagName + ">").length();
    }
}
