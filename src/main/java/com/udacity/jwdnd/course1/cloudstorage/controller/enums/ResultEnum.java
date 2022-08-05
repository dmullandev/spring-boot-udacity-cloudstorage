package com.udacity.jwdnd.course1.cloudstorage.controller.enums;

public enum ResultEnum {
    FILE_INSERT_SUCCESS(0),
    FILE_INSERT_FAILURE(1),
    FILE_INSERT_DUPLICATE(2),
    FILE_INSERT_EXCEPTION(3),
    FILE_DELETE_SUCCESS(4),
    FILE_DELETE_EXCEPTION(5),
    NOTE_INSERT_SUCCESS(6),
    NOTE_INSERT_FAILURE(7),
    NOTE_INSERT_EXCEPTION(8),
    NOTE_SAVE_SUCCESS(9),
    NOTE_SAVE_FAILURE(10),
    NOTE_SAVE_EXCEPTION(11),
    NOTE_DELETE_SUCCESS(12),
    NOTE_DELETE_EXCEPTION(13),
    CREDENTIAL_INSERT_SUCCESS(14),
    CREDENTIAL_INSERT_FAILURE(15),
    CREDENTIAL_INSERT_EXCEPTION(16),
    CREDENTIAL_SAVE_SUCCESS(17),
    CREDENTIAL_SAVE_FAILURE(18),
    CREDENTIAL_SAVE_EXCEPTION(19),
    CREDENTIAL_DELETE_SUCCESS(20),
    CREDENTIAL_DELETE_EXCEPTION(21);

    private int status;

    private ResultEnum(int status) {
        this.status = status;
    }

    public int getIntModalStatus() {
        return status;
    }
}
