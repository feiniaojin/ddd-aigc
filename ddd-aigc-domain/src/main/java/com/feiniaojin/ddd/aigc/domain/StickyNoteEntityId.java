package com.feiniaojin.ddd.aigc.domain;

import com.feiniaojin.ddd.AbstractEntityId;

/**
 * 便利贴ID
 */
public class StickyNoteEntityId extends AbstractEntityId<String> {

    public StickyNoteEntityId(String value) {
        super(value);
    }
}
