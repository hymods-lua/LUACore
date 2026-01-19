package com.lua.core.managers.assets.live;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LuaLiveStorage<S extends LuaLiveState> {
    private final ConcurrentHashMap<String, S> states = new ConcurrentHashMap<>();

    public void save(S state) {
        states.put(state.getUniqueId(), state);
    }

    public Optional<S> get(String id) {
        return Optional.ofNullable(states.get(id));
    }

    public Collection<S> getAll() {
        return states.values();
    }

    public void remove(String id) {
        states.remove(id);
    }

    public boolean exists(String id) {
        return states.containsKey(id);
    }
}
