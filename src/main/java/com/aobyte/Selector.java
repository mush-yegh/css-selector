package com.aobyte;

import java.util.ArrayList;
import java.util.Collections;

class Selector {
    private String query;
    private ArrayList<Tag> selectorTag;

    Selector(String query) {
        this.query = query.trim();
        this.selectorTag = new ArrayList<>();
        init();
    }

    private void init() {
        analyzeSelector();
        Collections.reverse(this.selectorTag);
    }

    private void analyzeSelector() {
        if (query.contains(" ")) {
            descendantSelector(query);
        } else if (query.length() > 1 && query.substring(1).contains("#") || query.substring(1).contains(".")) {
            combinedSelector(query);
        } else {
            simpleSelector(query);
        }
    }

    private void simpleSelector(String simpleSelector) {
        if (simpleSelector.startsWith(".")) {
            selectorTag.add(new Tag(-1, "", "", simpleSelector.substring(1), "", null));
        } else if (simpleSelector.startsWith("#")) {
            selectorTag.add(new Tag(-1, "", simpleSelector.substring(1), "", "", null));
        } else {
            selectorTag.add(new Tag(-1, simpleSelector, "", "", "", null));
        }
    }

    private void combinedSelector(String combinedSelector) {
        if (combinedSelector.contains("#")) {
            selectorTag.add(new Tag(-1, combinedSelector.split("#")[0], combinedSelector.split("#")[1], "", "", null));
        } else {
            selectorTag.add(new Tag(-1, combinedSelector.split("\\.")[0], "", combinedSelector.split("\\.")[1], "", null));
        }
    }

    private void descendantSelector(String descSelector) {
        String[] selectorItem = descSelector.split(" ");
        for (String item : selectorItem) {
            if (item.substring(1).contains("#") || item.substring(1).contains(".")) {
                combinedSelector(item);
                continue;
            }
            simpleSelector(item);
        }
    }

    ArrayList<Tag> getSelectorTag() {
        return selectorTag;
    }

    @Override
    public String toString() {
        return "Selector{" +
                "query='" + query + '\'' +
                ", selectorTag=" + selectorTag +
                '}';
    }
}
