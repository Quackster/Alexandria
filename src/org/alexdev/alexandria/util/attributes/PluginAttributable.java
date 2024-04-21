package org.alexdev.alexandria.util.attributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PluginAttributable {
    private Map<String, Object> attributeMap;

    public PluginAttributable() {
        this.attributeMap = new ConcurrentHashMap<>();
    }

    public void set(String attributeKey, Object attributeValue) {
        this.attributeMap.put(attributeKey, attributeValue);
    }

    public <T> T get(String attributeKey) {
        if (!this.attributeMap.containsKey(attributeKey)) {
            return null;
        }

        return (T)this.attributeMap.get(attributeKey);
    }

    public <T> T getOrDefault(String attributeKey, Object object) {
        if (!this.attributeMap.containsKey(attributeKey)) {
            return object != null ? (T)object : null;
        }

        return (T)this.attributeMap.get(attributeKey);
    }

    public boolean has(String attributeKey) {
        return this.attributeMap.containsKey(attributeKey);
    }

    public void remove(String attributeKey) {
        this.attributeMap.remove(attributeKey);
    }
}