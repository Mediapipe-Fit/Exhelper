package com.gauravk.bubblebarsample.Dto;

public interface InfoChangeListener {
    void onInfoGetSuccesse();
    void onInfoCreated(int position);
    void onInfoChanged(int position);
    void onInfoDeleted(int position);

}
