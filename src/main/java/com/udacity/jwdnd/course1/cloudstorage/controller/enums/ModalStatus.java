package com.udacity.jwdnd.course1.cloudstorage.controller.enums;

public enum ModalStatus {
    NOTE_INSERT_SUCCESS(0), NOTE_INSERT_FAILURE(1), NOTE_SAVE_SUCCESS(2), NOTE_SAVE_FAILURE(3);

    private int status;

    private ModalStatus(int status) {
        this.status = status;
    }

    public int getIntModalStatus() {
        return status;
    }
}
