package com.iasmar.rome.data.modules;

import com.iasmar.rome.util.GeneralUtils;

import static com.iasmar.rome.util.GeneralUtils.isNullOrEmpty;
//TODO  implementation

public  class Main implements BaseModule<String> {

    @Override
    public String getId() {
        return "1";
    }


    @Override
    public String getHash() {
        return GeneralUtils.getHash(toString());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        String obj1Hash = ((BaseModule) obj).getHash();
        String obj2Hash = this.getHash();

        return obj1Hash != null && obj1Hash.equals(obj2Hash);

    }


    @Override
    public boolean isEmpty() {
        return isNullOrEmpty(getId());
    }


    @Override
    public String getSortBy() {
        return getId();
    }

    @Override
    public String toString() {
        return "id: " + getId();
    }

}
